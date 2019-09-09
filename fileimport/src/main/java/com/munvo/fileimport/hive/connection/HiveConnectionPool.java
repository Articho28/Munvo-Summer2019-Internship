package com.munvo.fileimport.hive.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import com.munvo.fileimport.configuration.AppConfiguration;
import com.munvo.fileimport.utils.Constants;

/**
 * 
 * @author Artsiom Skliar
 * Class to manage connection pool to Hive Database
 */
public class HiveConnectionPool {
	
	private static BasicDataSource dataSource; 
	private static HiveConnectionPool connectionPool;
	
	//TODO configure max and min 
	private HiveConnectionPool() throws SQLException {
		dataSource = new BasicDataSource();
		AppConfiguration appConfig = AppConfiguration.getAppConfigInstance();
		dataSource.setDriverClassName(appConfig.getProperty(Constants.CONFIG_HIVE_JDBC_DRIVER));
		dataSource.setUrl(appConfig.getProperty(Constants.CONFIG_HIVE_JDBC_URL));
		dataSource.setUsername(appConfig.getProperty(Constants.CONFIG_HIVE_JDBC_USERNAME));
		dataSource.setPassword(appConfig.getProperty(Constants.CONFIG_HIVE_JDBC_PASSWORD));
		dataSource.setInitialSize(Integer.parseInt(appConfig.getProperty(Constants.CONFIG_HIVE_JDBC_INITIAL_SIZE)));
		dataSource.setMinIdle(Integer.parseInt(appConfig.getProperty(Constants.CONFIG_HIVE_JDBC_MIN_IDLE)));
	}
	
	public static HiveConnectionPool getHiveConnectionPool() {
		if (connectionPool == null) {
			try {
				connectionPool = new HiveConnectionPool();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return connectionPool;
	}
	
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	

}
