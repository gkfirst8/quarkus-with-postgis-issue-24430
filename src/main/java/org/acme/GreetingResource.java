package org.acme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import net.postgis.jdbc.PGgeography;

@Path("/hello")
public class GreetingResource {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingResource.class);

    private final AgroalDataSource dataSource;

    @Inject
    public GreetingResource(@DataSource("gis") AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        doSomeJDBC();
        return "Hello from THE RESTEasy Reactive";
    }

    private void doSomeJDBC() {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement read = connection.prepareStatement("select id, city, geolocation from locations")) {
            try (ResultSet resultSet = read.executeQuery()) {
                while (resultSet.next()) {
                    dumpGeolocation(resultSet);
                }
            }
        } catch (SQLException e) {
            LOG.error("Failed to query PostGIS", e);
        }
    }

    private void dumpGeolocation(ResultSet resultSet) throws SQLException {
        var object = resultSet.getObject("geolocation");
        LOG.info("Do: {}:{}:(class: {}){}", //
                Long.valueOf(resultSet.getLong("id")), //
                resultSet.getString("city"), //
                object.getClass(), //
                object);
        PGgeography geography = (net.postgis.jdbc.PGgeography) object;
        LOG.info("For real: {}", geography);
    }
}
