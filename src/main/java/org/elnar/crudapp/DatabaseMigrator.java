package org.elnar.crudapp;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseMigrator {
	public static void main(String[] args) throws Exception {
		
		final String PROPERTIES_FILE = "src/main/resources/application.properties";
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(PROPERTIES_FILE));
		
		String url = properties.getProperty("db.url");
		String username = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");
		
		Connection connection = DriverManager.getConnection(url, username, password);
		
		DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
		liquibase.database.Database database =
				databaseFactory.findCorrectDatabaseImplementation(new JdbcConnection(connection));
		
		Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml",
				new ClassLoaderResourceAccessor(), database);
		
		liquibase.update();
	}
}
