package org.acme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import io.agroal.api.AgroalDataSource;

class GreetingResourceNoQuarkusTest {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingResourceNoQuarkusTest.class);

    @Test
    @SuppressWarnings("resource")
    void testHelloEndpoint() throws Exception {
        DockerImageName imageName = DockerImageName.parse("postgis/postgis:14-master") //
                .asCompatibleSubstituteFor("postgres");
        try (JdbcDatabaseContainer<?> container = new PostgreSQLContainer<>(imageName) //
                .withLogConsumer(outputFrame ->
                {})) {
            container.start();

            String jdbcUrl = container.getJdbcUrl();
            LOG.info("JDBC URL: {}", jdbcUrl);

            doFlyway(createConnection( //
                    jdbcUrl, //
                    container.getUsername(), //
                    container.getPassword()));

            AgroalDataSource mockDataSource = new MockAgroalDataSource(createConnection( //
                    jdbcUrl, //
                    container.getUsername(), //
                    container.getPassword()));
            GreetingResource resource = new GreetingResource(mockDataSource);

            resource.hello();
        }
    }

    private void doFlyway(Connection jdbcConnection) {
        LOG.info("Init the database using FlyWay");
        String datasetLocation = "db/migration";
        Flyway flyway = Flyway.configure() //
                .dataSource(new MockAgroalDataSource(jdbcConnection)) //
                .locations(datasetLocation) //
                .load();
        flyway.migrate();
        LOG.info("Done FlyWay migrating");
    }

    public static Connection createConnection( //
            String jdbcUrl, //
            String username, //
            String password) {
        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (Exception e) {
            LOG.error("Failed to connect", e);
            throw new IllegalStateException(MessageFormat.format("Failed to connect to {0}@{1}", username, jdbcUrl), e);
        }
    }
}
