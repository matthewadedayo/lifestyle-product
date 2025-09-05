
package com.lifestyle.product.utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author maanifannu
 */

public class DataConnection {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    //Default is 10 incase property file is empty
    @Value("${spring.datasource.hikari.maximum-pool-size:10}")
    private int dbMaxPoolSize;

    //Default is 5 incase property file is empty
    @Value("${spring.datasource.hikari.minimum-idle:5}")
    private int dbMinIdleTime;

    //Default is 10 incase property file is empty
    @Value("${spring.datasource.hikari.idle-timeout:30000}")
    private long dbIdleTimeOut;

    private final HikariDataSource dataSource;


    public DataConnection() {
        
        HikariConfig config = new HikariConfig();
        //config.setJdbcUrl(nipDBUrl + "?currentSchema=" + nipDBSchema);
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        config.setMaximumPoolSize(dbMaxPoolSize);
        config.setMinimumIdle(dbMinIdleTime);
        config.setIdleTimeout(dbIdleTimeOut);

        // Create HikariCP DataSource
        dataSource = new HikariDataSource(config);
    }

    public Connection getDatabaseConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("SQLException in Connection: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("DataSource closed.");
        }
    }
}



