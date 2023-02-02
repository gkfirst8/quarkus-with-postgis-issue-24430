package org.acme;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSourceMetrics;
import io.agroal.api.AgroalPoolInterceptor;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;

final class MockAgroalDataSource implements AgroalDataSource {
    private static final long serialVersionUID = 1L;

    private final Connection jdbcConnection;

    public MockAgroalDataSource(Connection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return jdbcConnection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return jdbcConnection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // Not needed
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        // Not needed
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        // Not needed
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        // Not needed
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // Not needed
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // Not needed
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // Not needed
        return false;
    }

    @Override
    public AgroalDataSourceConfiguration getConfiguration() {
        // Not needed
        return null;
    }

    @Override
    public AgroalDataSourceMetrics getMetrics() {
        // Not needed
        return null;
    }

    @Override
    public void flush(FlushMode mode) {
        // Not needed
    }

    @Override
    public void setPoolInterceptors(Collection<? extends AgroalPoolInterceptor> interceptors) {
        // Not needed
    }

    @Override
    public List<AgroalPoolInterceptor> getPoolInterceptors() {
        // Not needed
        return null;
    }

    @Override
    public void close() {
        // Not needed
    }
}