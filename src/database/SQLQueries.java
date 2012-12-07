package database;

import java.util.List;

//Class to access database class and place queries
public class SQLQueries {
	
	//database specifications, change to face your database requirements accordingly
	private static String mConnectionURL = "jdbc:postgresql://localhost:5432/serverdb";
	private static String mUser = "postgres";
	private static String mPassword = "postgres";
	private static String mDriver = "org.postgresql.Driver";
	
	/**
	 * Call the login method of Database class
	 * @param username
	 * 			username set by user in the web interface
	 * @param password
	 * 			password set by user in the web interface
	 * @return
	 * 			a User instance
	 */
	public static User login(String username, String password)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a user instance
		User user = database.login(username, password);
		database.Close();
		return user;

	}
	
	/**
	 * Call the register method in the Database class
	 * @param username
	 * 			username set by user in the web interface
	 * @param password
	 * 			password set by user in the web interface
	 * @param email
	 * 			email set by user in the web interface
	 * @param name
	 * 			name set by user in the web interface
	 * @return
	 * 			a User instance
	 */
	public static User register(String username, String password, String email, String name)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a user instance from the newly registered user
		User user = database.register(username, password, email, name);
		database.Close();
		return user;

	}
	
	/**
	 * Call the exist method in the Database class
	 * @param username
	 * 			username selected by user in the web interface when trying to register
	 * @return
	 * 			True if username exists or else false
	 */
	public static boolean usernameExistance(String username)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a boolean indicating user existence 
		boolean flag = database.exist(username);
		database.Close();
		return flag;

	}
	
	/**
	 * Change user account settings without password field
	 * Call the changeAccountSettings method, without password parameter, in the Database class
	 * @param name
	 * 			name changed by user in the web interface		
	 * @param oldUsername
	 * 			old username to retrieve account information from the database
	 * @param newUsername
	 * 			new username selected by user in the web interface
	 * @param email
	 * 			email selected by user in the web interface
	 * @return
	 * 			an updated User instance
	 */
	public static User changeAccountSettings(String name, String oldUsername, String newUsername, String email)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
			
		//Open the database connection
		database.Open();
			
		//call changeAccountSettings method in the DB 
		User user = database.changeAccountSettings(name, oldUsername, newUsername, email);
		database.Close();
		return user;
	}
	
	/**
	 * Change user account settings with password field
	 * Call the changeAccountSettings method, without password parameter, in the Database class
	 * @param name
	 * 			name changed by user in the web interface		
	 * @param oldUsername
	 * 			old username to retrieve account information from the database
	 * @param newUsername
	 * 			new username selected by user in the web interface
	 * @param email
	 * 			email selected by user in the web interface
	 * @param password
	 * 			new password selected by user in the web interface
	 * @return
	 * 			an updated User instance
	 */
	public static User changeAccountSettings(String name, String oldUsername, String newUsername, String email, String password)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
					
		//Open the database connection
		database.Open();
				
		//call changeAccountSettings method in the DB 
		User user = database.changeAccountSettings(name, oldUsername, newUsername, email,password);
		database.Close();
		return user;
	}
	
	/**
	 * Retrieve all gateway information from the Database
	 * Call getAllGateways method from the Database class
	 * @return
	 * 			a List of all Gateways
	 */
	public static List<Gateway> getAllGateway()
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Gateways taken from the database
		List<Gateway> listGateway =  database.getAllGateways();
		database.Close();
		return listGateway;

	}
	
	/**
	 * Get all sensors from the DB registered in a specific gateway 
	 * Call getAllSensors method from the Database class
	 * @param gatewayID
	 * 			gateway id that we want to retrieve its registered sensors
	 * @return
	 * 			a List of Sensor instances
	 */
	public static List<Sensor> getAllSensor(int gatewayID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Sensors taken from the database
		List<Sensor> listSensors =  database.getAllSensors(gatewayID);
		database.Close();
		return listSensors;

	}
	
	/**
	 * Get sensor object based on its id 
	 * Call getSensor method of Database class
	 * @param sensorId
	 * 			sensor id to get record from DB
	 * @return
	 * 			sensor instance
	 */
	public static Sensor getSensor(String sensorId)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a Gateway instance from a gateway record in the database
		Sensor sensor =  database.getSensor(sensorId);
		database.Close();
		return sensor;

	}
	
	/**
	 * Get a specific gateway based on its id
	 * Call getGateway method from database class
	 * @param gatewayID
	 * 			gateway id 
	 * @return
	 */
	public static Gateway getGateway(int gatewayID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a Gateway instance from a gateway record in the database
		Gateway gateway =  database.getGateway(gatewayID);
		database.Close();
		return gateway;

	}
	
	/**
	 * Set gateway record
	 * Call setGateway method from the Database class
	 * @param gatewayName
	 * 			gateway name to store a new Gateway in the DB
	 * @return
	 * 			a Gateway instance
	 */
	public static Gateway setGateway(String gatewayName)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create Gateway instance from the newly registered gateway record in the DB
		Gateway gateway =  database.setGateway(gatewayName);
		database.Close();
		return gateway;

	}
	
	/**
	 * Set a sensor to a specific gateway in the DB 
	 * Call setSensor method from the Database class
	 * @param sensorID
	 * 			sensor id
	 * @param gatewayID
	 * 			gateway id
	 * @return
	 * 			a Sensor instance
	 */
	public static Sensor setSensor(String sensorID, String sensorName, String gatewayID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create Sensor instance from the newly registered gateway record in the DB
		Sensor sensor =  database.setSensor(sensorID, sensorName, gatewayID);
		database.Close();
		return sensor;

	}
	
	/**
	 * Get sensor data from a specific sensor
	 * Call getSensorData method of the Database class
	 * @param sensorID
	 * 			sensor id to get its data
	 * @return
	 * 		a List of Data instances for a specific sensor
	 */
	public static List<Data> getSensorData(String sensorID)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Data> listData =  database.getSensorData(sensorID);
		database.Close();
		return listData;

	}
	
	/**
	 * Set data for a specific sensor taken from a file containing the bundles' payloads
	 * received in the web server 
	 * Call setData method of the Database class
	 * @param id
	 * 			sensor id
	 * @param data
	 * 			sensor data
	 */
	public static void setData(String id, Data data)
	{
		//Create a new database connection
		Database database = new Database(mConnectionURL, mUser, mPassword, mDriver);
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		database.setData(id, data);
		database.Close();

	}
}
