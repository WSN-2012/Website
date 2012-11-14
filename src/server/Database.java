package server;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
					.executeQuery("select  username, password, email, name from admins where username = '"
							+ username + "' and password = '" + password + "'");

			if (rs.next()) {
				User user = new User(rs.getString("username").trim(),
						rs.getString("password").trim(), rs.getString("email").trim(),
						rs.getString("name").trim());
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
	
	public User register(String username, String password, String email, String name) {
		try {
			int id = 0;
			ResultSet rs = statement
					.executeQuery("select max(id) as id from admins");
			if (rs.next()) {
				id = rs.getInt("id");
			}
			
			// Submit a query, creating a ResultSet object
			statement.executeUpdate("insert into admins values('" + (++id) + "','" + username + "','" + password + "','" 
							+ email + "','" + name + "')");
			
			rs = statement
					.executeQuery("select username, password, email, name from admins where id = '" + id + "'");

			if (rs.next()) {
				User user = new User(rs.getString("username").trim(),
						rs.getString("password").trim(), rs.getString("email").trim(),
						rs.getString("name").trim());
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
	
	//Check if username already exists in the DB
	public boolean exist(String username) {
		boolean exist = false;//indicate that username exists in DB
		try {
			
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select username from admins where username = '"
							+ username + "'");

			if (rs.next()) {
				exist = true;
			} 
				rs.close();
				return exist;
			
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return exist;
	}
	
	public User changeAccountSettings(String name, String oldUsername, String newUsername, String email) {
		try {
			int accountID = -1;//ID of the account that we want to modify
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select id from admins where username = '"
							+ oldUsername + "'");
			while(rs.next()){
				accountID = rs.getInt("id");
				System.out.println("AccountID: "+accountID);
			}
			statement
					.executeUpdate("UPDATE admins SET name = '" + name + "', username = '" + newUsername + "', email = '" + email + "' WHERE id = " + accountID);

			rs = statement
					.executeQuery("select * from admins where username = '"
							+ newUsername + "'");
			if (rs.next()) {
				User user = new User(rs.getString("username").trim(),
						rs.getString("password").trim(), rs.getString("email").trim(),
						rs.getString("name").trim());
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
	
	public User changeAccountSettings(String name, String oldUsername, String newUsername, String email, String password) {
		try {
			int accountID = -1;//ID of the account that we want to modify
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select id from admins where username = '"
							+ oldUsername + "'");
			while(rs.next()){
				accountID = rs.getInt("id");
			}
			
			statement
					.executeUpdate("UPDATE admins SET name = '" + name + "', username = '" + newUsername + "', email = '" + email + "', password = '" + password + "' WHERE id = " + accountID);

			rs = statement
					.executeQuery("select * from admins where username = '"
							+ newUsername + "'");
			if (rs.next()) {
				User user = new User(rs.getString("username").trim(),
						rs.getString("password").trim(), rs.getString("email").trim(),
						rs.getString("name").trim());
				rs.close();
				return user;
			} else {
				rs.close();
				return null;
			}
			
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception (Password):  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	
	public List<Gateway> getAllGateways() {
		try {
			
			List<Gateway> listGateway = new ArrayList<Gateway>();
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select * from gateway ");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name").trim();
				boolean publicity = rs.getBoolean("publicity");
				Gateway gateway = new Gateway(id, name, publicity);
				
				listGateway.add(gateway);	
			}
			
			rs.close();
			return listGateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	public List<Sensor> getAllSensors(int gatewayID) {
		try {
			
			List<Sensor> listGateway = new ArrayList<Sensor>();
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select * from sensor where gateway_id = " + gatewayID );

			while (rs.next()) {
				String id = rs.getString("id").trim();
				String name = rs.getString("name").trim();
				int gatewayId = rs.getInt("gateway_id");
				Sensor sensor = new Sensor(id, name, gatewayId);
				
				listGateway.add(sensor);	
			}
			
			rs.close();
			return listGateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	public Gateway getGateway(int gatewayID) {
		try {
			
			Gateway gateway = null;
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select * from gateway where id = " +gatewayID);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name").trim();
				boolean publicity = rs.getBoolean("publicity");
				gateway = new Gateway(id, name, publicity);
			}
			
			rs.close();
			return gateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	public List<Data> getSensorData(String sensorID) {
		try {
			
			List<Data> listData = new ArrayList<Data>();
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select * from wsndata W, sensor S where W.id = '" + sensorID + "' AND S.id = '" + sensorID + "'");

			while (rs.next()) {
				Data data = new Data (rs.getTimestamp("utimestamp"), rs.getInt("ut"), rs.getDouble("t"),
						rs.getDouble("ps"), rs.getDouble("t_mcu"), rs.getDouble("v_mcu"), rs.getString("up").trim(), rs.getDouble("rh"),
						rs.getDouble("v_in"), rs.getDouble("v_a1"), rs.getString("name").trim());
				
				listData.add(data);//add data object to the list to be returned
			}
			
			rs.close();
			return listData;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	//Static Test of database
	/*public static void main(String args[]){
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
				
		//Open the database connection
		database.Open();
	
		//Data display
		/*List<Data> data = database.getAllData();
		for (int i=0; i< data.size(); i++)
			System.out.println("ID: " + data.get(i).getId() + " Utimestamp: " + data.get(i).getUtimestamp() + " UT: " + data.get(i).getUt()
					+ " T: " + data.get(i).getT() + " PS: " + data.get(i).getPs() + " T_MCU: " + data.get(i).getT_mcu() + " V_MCU: " + data.get(i).getV_mcu() 
					+ " UP: " + data.get(i).getUp() + " RH: " + data.get(i).getRh() + " V_IN: " + data.get(i).getV_in() + " V_A1: " + data.get(i).getV_a1()
					+ " GatewaName: " + data.get(i).getGatewayName());
		
	}*/

}
