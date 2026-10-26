package main;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
	
	private Properties props;
	private Connection connection;
	private Statement statement;
	
	public DBManager() throws IOException, SQLException {
		this.readDatabaseProperties();
		this.connect();
	}

	private Connection connect() throws SQLException {
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		System.out.println("Connected to database"+url);
		return DriverManager.getConnection(url, username, password);
	}

	private void readDatabaseProperties() throws IOException {
		props = new Properties();
		
		try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
			props.load(in);
		}
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null) {
			System.setProperty("jdbc.drivers", drivers);
		}
	}
	
	public void createTable(String sqlQuery) {
		try {
			System.out.println("Tworzę tabelę!");
			connection = connect();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			}catch(SQLException ex1) {
				
			}
			System.out.println("Błąd podczas tworzenia tabel\n"+ex);
		}
	}
	
	public ResultSet doQuery(String query) throws SQLException {
		connection.setAutoCommit(true);
		statement = connection.createStatement();
		return statement.executeQuery(query);
	}
	
	public void doUpdate(String query) throws SQLException{
		statement.executeUpdate(query);
	}
}
