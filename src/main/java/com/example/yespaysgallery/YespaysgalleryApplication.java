package com.example.yespaysgallery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.prefs.PreferenceChangeListener;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class YespaysgalleryApplication extends SpringBootServletInitializer{


	private final static String db_user = "spring";

	private final static String db_pass = "spring";

	private final static String db_name = "yesdb";
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PreferenceChangeListener.class);
	}

    
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(String.format("jdbc:postgresql://localhost:5432/%s",db_name));
        dataSourceBuilder.username(db_user);
        dataSourceBuilder.password(db_pass);
        return dataSourceBuilder.build();
    }

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(YespaysgalleryApplication.class);
		Connection connection = null;
        Statement statement = null;
        try {
            logger.debug("Creating database if not exist...");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+"postgres", db_user, db_pass);
            statement = connection.createStatement();
            statement.executeQuery(String.format("SELECT count(*) FROM pg_database WHERE datname = '%s'", db_name));
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count <= 0) {
                statement.executeUpdate(String.format("CREATE DATABASE %s", db_name));
                logger.debug("Database created "+ db_name);
            } else {
                logger.debug("Database already exist.");
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    logger.debug("Closed Statement.");
                }
                if (connection != null) {
                    logger.debug("Closed Connection.");
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        }


		SpringApplication.run(YespaysgalleryApplication.class, args);
    }

	
}

