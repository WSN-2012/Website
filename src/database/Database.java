package database;

import java.math.BigInteger;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Database {

	Connection connection;
	Statement statement;
	String connectionURL;
	String driverName;
	String user;
	String password;

	/**
	 * Constructor creating database object representing a database connection
	 * @param connectionURL 
	 * 			URL to connect to the database
	 * @param user
	 * 			username to access the database
	 * @param password
	 * 			password to access the database
	 * @param driverName
	 * 			to load correctly the database type
	 */
	public Database(String connectionURL, String user, String password,
			String driverName) {
		this.connectionURL = connectionURL;
		this.driverName = driverName;
		this.user = user;
		this.password = password;
	}

	/**
	 * Connect to the database
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
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close the database connection
	 */
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
	
	/**
	 * Authenticate user in the web interface
	 * @param username
	 * @param password
	 * @return 
	 * 		a User instance
	 */
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
	
	/**
	 * Register a user in the web interface
	 * @param username
	 * @param password
	 * @param email
	 * @param name
	 * @return
	 * 		A User instance
	 */
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
			
			//Get new User record from DB
			rs = statement
					.executeQuery("select username, password, email, name from admins where id = '" + id + "'");
			
			//Create User instance
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
	
	/**
	 * Check if username already exists in the DB 
	 * (called when registering a User)
	 * @param username
	 * @return
	 * 		True if the username exist or false if it doesn't
	 */
	public boolean exist(String username) {
		boolean exist = false;//indicate that username does not exist in DB
		try {
			
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select username from admins where username = '"
							+ username + "'");
			
			//Check if username exists
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
	
	/**
	 * Change User's personal data without changing password
	 * @param name
	 * 			new name
	 * @param oldUsername
	 * 			old username to find user in the DB
	 * @param newUsername
	 * 			new username
	 * @param email
	 * 			new email
	 * @return
	 * 			updated User instance
	 */
	public User changeAccountSettings(String name, String oldUsername, String newUsername, String email) {
		try {
			int accountID = -1;//ID of the account that we want to modify
			
			// Submit a query, creating a ResultSet object
			// Get the id for the account that we want to modify 
			ResultSet rs = statement
					.executeQuery("select id from admins where username = '"
							+ oldUsername + "'");
			
			while(rs.next()){
				accountID = rs.getInt("id");
			}
			
			// Execute an SQL UPDATE to update the account data
			statement
					.executeUpdate("UPDATE admins SET name = '" + name + "', username = '" + newUsername + "', email = '" + email + "' WHERE id = " + accountID);
			
			// Get updated account record from database
			rs = statement
					.executeQuery("select * from admins where username = '"
							+ newUsername + "'");
			
			// Create a new updated User instance
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
	
	/**
	 * Change User's personal data with password changing
	 * @param name
	 * 			new name
	 * @param oldUsername
	 * 			old username to find user in the DB
	 * @param newUsername
	 * 			new username
	 * @param email
	 * 			new email
	 * @param password
	 * 			new password
	 * @return
	 * 			updated User instance
	 */
	public User changeAccountSettings(String name, String oldUsername, String newUsername, String email, String password) {
		try {
			int accountID = -1;//ID of the account that we want to modify
			
			// Submit a query, creating a ResultSet object
			// Get the id for the account that we want to modify
			ResultSet rs = statement
					.executeQuery("select id from admins where username = '"
							+ oldUsername + "'");
			while(rs.next()){
				accountID = rs.getInt("id");
			}
			
			// Execute an SQL UPDATE to update the account data
			statement
					.executeUpdate("UPDATE admins SET name = '" + name + "', username = '" + newUsername + "', email = '" + email + "', password = '" + password + "' WHERE id = " + accountID);

			// Get updated account record from database
			rs = statement
					.executeQuery("select * from admins where username = '"
							+ newUsername + "'");
			
			// Create a new updated User instance
			if (rs.next()) {
				User user = new User(rs.getString("username").trim(),
						rs.getString("password").trim(), rs.getString("email").trim(),
						rs.getString("name").trim());
				rs.close();//close result set
				return user;
			} else {
				rs.close();//close result set
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
	
	/**
	 * Get all gateway records stored in the DB
	 * @return
	 * 		A list of all Gateways
	 */
	public List<Gateway> getAllGateways() {
		try {
			
			List<Gateway> listGateway = new ArrayList<Gateway>();
			
			// Submit a query, creating a ResultSet object
			// Get all gateway records from DB
			ResultSet rs = statement
					.executeQuery("select * from gateway ");
			
			// Create Gateway objects from the result set and add them to the returned list 
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name").trim();
				boolean publicity = rs.getBoolean("publicity");
				Gateway gateway = new Gateway(id, name, publicity);
				
				listGateway.add(gateway);	
			}
			
			rs.close();//close result set
			return listGateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	/**
	 * Get sensor records registered to a specific gateway
	 * @param gatewayID
	 * 			id of the gateway that we want to get its sensors
	 * @return
	 * 			a List of Sensor objects 
	 */
	public List<Sensor> getAllSensors(int gatewayID) {
		try {
			
			List<Sensor> listGateway = new ArrayList<Sensor>();
			// Submit a query, creating a ResultSet object
			// Get all sensors registered in a specific gateway
			ResultSet rs = statement
					.executeQuery("select * from sensor where gateway = " + gatewayID + " and name != 'No sensor name'");
			
			// Create sensor objects from the Result set and add them to the sensor list
			while (rs.next()) {
				String id = rs.getString("id").trim();
				String name = rs.getString("name").trim();
				int gatewayId = rs.getInt("gateway");
				Sensor sensor = new Sensor(id, name, gatewayId);
				
				listGateway.add(sensor);	
			}
			
			rs.close();//close Result set
			return listGateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	/**
	 * Get a sensor instance based on its id
	 * @param sensorId
	 * 			sensor id to get sensor object from DB
	 * @return
	 * 			a sensor instance
	 */
	public Sensor getSensor(String sensorId) {
		try {
			
			Sensor sensor = null;
			// Submit a query, creating a ResultSet object
			// Get gateway with a specific id
			ResultSet rs = statement
					.executeQuery("select * from sensor where id = '" + sensorId + "'");

			// Create gateway instance from result set
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name").trim();
				int gateway = rs.getInt("gateway");
				sensor = new Sensor(id, name, gateway);
			}
			
			rs.close();//close Result Set
			return sensor;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	/**
	 * Get all data related to a specific sensor
	 * @param sensorID
	 * 			the id of the sensor that we want to retrieve its data
	 * @return
	 * 			a List of Data objects
	 */
	public List<Data> getSensorData(String sensorID) {
		try {
			
			List<Data> listData = new ArrayList<Data>();
			// Submit a query, creating a ResultSet object
			// Get different timestamp records from a specific sensor (to get different datatypes and values registered on the exact timestamp)
			ResultSet rs = statement
					.executeQuery("select distinct(D.utimestamp), S.name from data D, sensor S where D.sensor = '" + sensorID + "' AND S.id = '" + sensorID + "' order by utimestamp");
			
			// for each specific timestamp get data values and datatype names and create Data objects
			while (rs.next()) {
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2
						.executeQuery("select D.datavalue as datavalue, T.name from data D, types T where D.sensor = '" + sensorID + "' AND D.utimestamp = '" + rs.getTimestamp("utimestamp") + "' AND D.datatype = T.id");
				Map<String, String> mdata = new HashMap<String, String>();
				while(rs2.next()){
					mdata.put(rs2.getString("name"), rs2.getString("datavalue"));
				}
				
				Data data = new Data (rs.getTimestamp("utimestamp"), rs.getString("name").trim(), mdata);
				
				listData.add(data);//add data object to the list to be returned
				rs2.close();//close Inner Result Statement
				
			}
			
			rs.close();//close Outer Result Statement
			return listData;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	
	/**
	 * Check if specific data already exists in the DB
	 * @param id
	 * @param utimestamp
	 * @return
	 * 			True if data record exists or else false
	 */
	//TODO test this method
	public boolean duplicateRecord(String id, Date utimestamp) {
		boolean exist = false;//indicate that no such record exists in DB
		try {
			if(utimestamp!=null){
				java.sql.Timestamp timestamp = new java.sql.Timestamp(utimestamp.getTime());
				// Submit a query, creating a ResultSet object
				ResultSet rs = statement
						.executeQuery("select * from data where sensor = '"
								+ id + "' AND utimestamp = '" + utimestamp + "'");
	
				if (rs.next()) {
					exist = true;
				} 
				rs.close();//close Result Set
			}else{
				ResultSet rs = statement
						.executeQuery("select * from data where sensor = '"
								+ id + "'");
	
				if (rs.next()) {
					exist = true;
				} 
				rs.close();//close Result Set
			}
			
			
			return exist;
				
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return exist;
	}
	
	/**
	 * Check if gateway already exists in the DB
	 * @param name
	 * 			gateway name
	 * @return
	 * 			True if gateway exists or else false
	 */
	public boolean duplicateGateway(String name) {
		boolean exist = false;//indicate that no such record exists in DB
		try {
						
			// Submit a query, creating a ResultSet object
			// Get gateway with a specific name
			ResultSet rs = statement
					.executeQuery("select * from gateway where name = '"
							+ name + "'");

			if (rs.next()) {
				exist = true;
			} 
					
			rs.close();//close Result Set
			return exist;
						
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex.printStackTrace();
				ex = ex.getNextException();
				}
		}
		return exist;
	}
	
	/**
	 * Get a specific gateway based on gateway ID
	 * @param gatewayID
	 * 			gateway id
	 * @return
	 * 			Gateway instance if gateway is found
	 */
	public Gateway getGateway(int gatewayID) {
		try {
			
			Gateway gateway = null;
			// Submit a query, creating a ResultSet object
			// Get gateway with a specific id
			ResultSet rs = statement
					.executeQuery("select * from gateway where id = " +gatewayID);

			// Create gateway instance from result set
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name").trim();
				boolean publicity = rs.getBoolean("publicity");
				gateway = new Gateway(id, name, publicity);
			}
			
			rs.close();//close Result Set
			return gateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	/**
	 * Get a specific gateway based on gateway name
	 * @param gatewayName
	 * 			gateway name
	 * @return
	 * 			Gateway instance if gateway is found
	 */
	public Gateway getGateway(String gatewayName) {
		try {
			
			Gateway gateway = null;
			// Submit a query, creating a ResultSet object
			// Get gateway with a specific name
			ResultSet rs = statement
					.executeQuery("select * from gateway where name = '" +gatewayName +"'");
			
			// Create gateway instance from result set
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name").trim();
				boolean publicity = rs.getBoolean("publicity");
				gateway = new Gateway(id, name, publicity);
			}
			
			rs.close();//close Result Set
			return gateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
		
	/**
	 * Set gateway object to the database 
	 * @param name
	 * 			name of the gateway
	 * @return
	 * 			gateway instance
	 */
	public Gateway setGateway(String name) {
		Gateway gateway = null;
		int gatewayID=0;
		ResultSet rs;
		try {
			if(!duplicateGateway(name)){
					//take the last getawayID 
					rs = statement.executeQuery("select MAX(id) as id from gateway");
					while (rs.next()) {
						gatewayID = rs.getInt("id");
					}
					// Submit a query, creating a ResultSet object
					// Insert gateway record to the database
					statement.executeUpdate("insert into gateway values(" + (++gatewayID) + ", '" + name + "', true)");
					rs.close();//close Result Set
				}
				// Get gateway instance based on the name
				gateway = getGateway(name);
					
				return gateway;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		
		return gateway;
	}
	
	/**
	 * Check if sensor already exists in the DB
	 * @param id
	 * 			id of the sensor in order to check if exists in the DB
	 * @return
	 * 			True if sensor exists or else false 
	 */
	public boolean duplicateSensor(String id) {
		boolean exist = false;//indicate that no such record exists in DB
		try {
					
			// Submit a query, creating a ResultSet object
			// get sensor based on its id
			ResultSet rs = statement
					.executeQuery("select * from sensor where id = '"
							+ id + "'");
			// if result set returns a record the sensor exists
			if (rs.next()) {
				exist = true;
			} 
				
			rs.close();//close Result Set
			return exist;
					
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return exist;
	}
	
	/**
	 * set a sensor to the database 
	 * @param sensorID
	 * 			sensor id
	 * @param gatewayName
	 * 			gateway name
	 * @return
	 * 			return sensor instance
	 */
	public Sensor setSensor(String sensorID, String sensorName, String gatewayName) {
		Sensor sensor = null;
		try {
			//check if sensor exists in order to avoid duplicate records in the DB
			if(!duplicateSensor(sensorID)){
				
				//get gateway object to get the gateway ID
				Gateway gateway = getGateway(gatewayName);
				// Submit a query, creating a ResultSet object
				// insert sensor record with sensor id and gateway id
				statement.executeUpdate("insert into sensor values('" + sensorID + "', '" + sensorName + "', " + gateway.getId() + ")");
			}
			
			//get sensor record based on sensor id
			ResultSet rs = statement
					.executeQuery("select * from sensor where id = '" + sensorID + "'");
			
			// create sensor instance
			while (rs.next()) {
				sensor = new Sensor(rs.getString("id"), rs.getString("name"), rs.getInt("gateway"));
			}
				
			rs.close();// close result set
			return sensor;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		
		return sensor;
	}
	
	/**
	 * Set data objects to the database 
	 * @param id
	 * 			sensor id
	 * @param data
	 * 			data object
	 */
	public void setData(String id, Data data) {
		
		if(!duplicateRecord(id, data.getUtimestamp())){
			try {
				//Map containing data send from a specific wsn and collected at a specific time
				Map<String, String> mdata = data.getWSData();
				//Map representing data to be stored in the database
				Map<Integer, String> dbmap = new HashMap<Integer,String>();
				// Get a set of the entries
				Set set = mdata.entrySet();
				// Get an iterator
				Iterator it = set.iterator();
				// Display elements
				while(it.hasNext()) {
					Map.Entry me = (Map.Entry)it.next();
					if(me.getKey().equals("t")){
						dbmap.put(1,me.getValue().toString());
					}else if(me.getKey().equals("ps")){
						dbmap.put(2,me.getValue().toString());
					}else if(me.getKey().equals("t_mcu")){
						dbmap.put(3,me.getValue().toString());
					}else if(me.getKey().equals("v_mcu")){
						dbmap.put(4,me.getValue().toString());
					}else if(me.getKey().equals("up")){
						dbmap.put(5,me.getValue().toString());
					}else if(me.getKey().equals("rh")){
						dbmap.put(6,me.getValue().toString());
					}else if(me.getKey().equals("v_in")){
						dbmap.put(7,me.getValue().toString());
					}else if(me.getKey().equals("ut")){
						dbmap.put(8,me.getValue().toString());
					}else{
						dbmap.put(9,me.getValue().toString());
					}
				} 
				
				// Get a set of the entries
				Set s = dbmap.entrySet();
				// Get an iterator
				Iterator iter = s.iterator();
				// Display elements
				int t = 1;
				while(iter.hasNext()) {
					Map.Entry mentry = (Map.Entry)iter.next();
					// Submit a query, creating a ResultSet object
					if(data.getUtimestamp()!=null){
						statement
								.executeUpdate("INSERT INTO data VALUES "
												+ "('" + id 
												+ "', '" + data.getUtimestamp()
												+ "', " + Integer.parseInt(mentry.getKey().toString())
												+ ", '" + mentry.getValue().toString()
												+ "')");
					}else{
						statement
								.executeUpdate("INSERT INTO data VALUES "
												+ "('" + id 
												+ "', DEFAULT" 
												+ ", " + Integer.parseInt(mentry.getKey().toString())
												+ ", '" + mentry.getValue().toString()
												+ "')");
					}
				}
				
	
			} catch (SQLException ex) {
				while (ex != null) {
					System.out.println("SQL Exception:  " + ex.getMessage());
					ex.printStackTrace();
					ex = ex.getNextException();
				}
			}
		}
	}
	
	
	
	//Static Test of database
	/*public static void main(String args[]){
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
				
		//Open the database connection
		database.Open();
	
		//Data display
		List<Data> data = database.getSensorData("28461fef030000fd");
		for (int i=0; i< data.size(); i++){
			System.out.print("Utimestamp: " + data.get(i).getUtimestamp()
					+ " SensorName: " + data.get(i).getSensorName());
			// Get a set of the entries
			Set set = data.get(i).getWSData().entrySet();
			// Get an iterator
			Iterator it = set.iterator();
			// Display elements
			while(it.hasNext()) {
				Map.Entry me = (Map.Entry)it.next();
				System.out.print(" " + me.getKey() + "= " + me.getValue());
			} 
			System.out.println("");
		}
		
	}*/

}
