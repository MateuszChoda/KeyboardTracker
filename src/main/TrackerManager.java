package main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackerManager extends DBManager {
	
	private String createTable1 = "CREATE TABLE if not exists keys";
	private String createTable2 = "(id BIGSERIAL NOT NULL PRIMARY KEY, "+"name VARCHAR(45), "
			+"amount FLOAT NOT NULL, "+"CONSTRAINT name UNIQUE(name)); ";

	public TrackerManager() throws IOException, SQLException {
		super();
		createTable("");
		bootManager();
	}
	
	public void bootManager() throws SQLException {
		String createQuery = createTable1+" "+createTable2;
		createTable(createQuery);
	}
	
	public void incrementKey(String key) throws NumberFormatException, SQLException {
		ResultSet amountResult = getAmount(key);
		double amount = 0;
		
		if(amountResult != null) {
			while (amountResult.next()) {
				double increase = Double.parseDouble(amountResult.getString(1));
				amount=increase+1;
			}
		}
		
		String query = "UPDATE keys SET amount = "+amount+" WHERE name = '"+key+"';";
		doUpdate(query);
	}
	
	public void addKey(String key) throws SQLException {
		String query = "INSERT INTO keys (name, amount) VALUES ('"+key+"', 0) ON CONFLICT(name) DO NOTHING;";
		doUpdate(query);
	}
	
	public ResultSet getAmount(String key) throws SQLException {
		String query = "SELECT amount FROM keys WHERE name = '"+key+"';";
		return doQuery(query);
	}
	
	public ResultSet getHighest() throws SQLException {
		String query = "SELECT name FROM keys ORDER BY amount ASC;";
		return doQuery(query);
	}

}
