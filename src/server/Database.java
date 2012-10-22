package server;

import java.sql.*;

public class Database {
	/*
	 * String connectionURL = "jdbc:postgresql://localhost:5432/serverdb";
	 * String user= "postgres"; String password= "1234567890";
	 */

	Connection connection;
	Statement statement;
	String connectionURL;
	String driverName;
	String user;
	String password;

	public Database(String connectionURL, String user, String password,
			String driverName) {
		this.connectionURL = connectionURL;
		this.driverName = driverName;
		this.user = user;
		this.password = password;
	}

	/**
	 * Connect to the database
	 * 
	 * @param connectionURL
	 *            The URL for the database connection
	 * @param user
	 *            The username of the database
	 * @param password
	 *            The password of the database
	 * @param driverName
	 *            The database driver class Name
	 */
	public void Open() {

		try {
			// Load the Driver class.
			Class.forName(driverName);
			// Create the connection using the static getConnection method
			connection = DriverManager.getConnection(connectionURL, user,
					password);

			// Create a Statement class to execute the SQL statement
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
	}

	public User login(String username, String password) {
		try {
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select username from admins where username = '"
							+ username + "' and password = '" + password + "'");

			if (rs.next()) {
				User user = new User(rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("name"));
				rs.close();
				return user;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}

}
