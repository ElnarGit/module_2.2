package org.elnar.crudapp.util;

import org.elnar.crudapp.exception.JdbcRepositoryException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
	private static final String PROPERTIES_FILE = "src/main/resources/application.properties";
	private static Connection connection;
	
	
	private static Connection getConnection(){
		if(connection == null){
			try {
				Properties properties = new Properties();
				properties.load(new FileInputStream(PROPERTIES_FILE));
				
				String url = properties.getProperty("db.url");
				String username = properties.getProperty("db.username");
				String password = properties.getProperty("db.password");
				
				Class.forName(properties.getProperty("db.driver"));
				
				connection = DriverManager.getConnection(url, username, password);
			} catch (IOException | ClassNotFoundException | SQLException e) {
				throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
			}
		}
		return connection;
	}
	
	public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}
	
	public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
		return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
}