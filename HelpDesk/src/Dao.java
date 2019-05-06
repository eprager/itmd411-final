/**
 * @author Emma Prager
 * @date 05 May 2019
 * @title Final Project
 * @file Dao.java
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Dao stands for Data Access Object. This will allow for database connectivity and 
 * CRUD (Create Read Update Delete) like operations including insert, update, read, and delete tickets.
*/

public class Dao {
	static Connection connect = null;
	Statement statement = null;
	
	// Code database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/tickets?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "fp411", PASS = "411";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
	
	// CRUD implementation
	
	//INSERT
	public int insertRecords(String ticketName, String ticketDesc) {
		int id = 0;
		try {
			statement = connect().createStatement();
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			statement.executeUpdate("Insert into eprag_tickets" + "(ticket_issuer, ticket_description, opened, status) values(" + " '" + ticketName + "','" + ticketDesc + "','" + timeStamp  + "','" + "OPEN" + "')", Statement.RETURN_GENERATED_KEYS);

			// retrieve ticket id number newly auto generated upon record insertion
			ResultSet resultSet = null;
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// retrieve first field in table
				id = resultSet.getInt(1);
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}

	//VIEW RECORDS
	public ResultSet readRecords() {

		ResultSet results = null;
		try {
			statement = connect().createStatement();
			results = statement.executeQuery("SELECT * FROM eprag_tickets");
			connect().close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return results;
	}
	
	//VIEW a RECORD
		public ResultSet selectRecords(int tid) {
			ResultSet results = null;
			try {
				statement = connect().createStatement();
				results = statement.executeQuery("SELECT * FROM eprag_tickets WHERE id = " + tid);
				connect().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return results;
		}
		     
	// continue coding for updateRecords implementation
	//UPDATE
	public void updateRecords(String tid, String desc, String status) {
		//UPDATE `eprag_tickets` SET `ticket_description` = 'This thing is broken and I need it to be fixed.
		//\r\n\r\nUpdate: fixed', `status` = 'CLOSED' WHERE `eprag_tickets`.`id` = 7;
		try {
			Statement stmt = connect().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ticket_description FROM eprag_tickets WHERE id = " + tid);
			connect().close();
			String results = null;
			while (rs.next()) {
				  results = rs.getString("ticket_description");
				}
		    
		   PreparedStatement ps = connect().prepareStatement("UPDATE eprag_tickets SET ticket_description = ?, status = ? WHERE id = ?");
		    	    // set the preparedstatement parameters
		   String descUp = results + "\nUpdate: " + desc;
		    	    ps.setString(1,descUp);
		    	    ps.setString(2,status);
		    	    ps.setString(3,tid);
		    	    ps.executeUpdate();
		    	    ps.close();
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// continue coding for deleteRecords implementation
	//DELETE
	public int deleteRecords(int tid) {
		try {
			statement = connect().createStatement();
		    String sql = "DELETE FROM eprag_tickets WHERE eprag_tickets.id = " + tid;
		    statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tid;
	}
}
