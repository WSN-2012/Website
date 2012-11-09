package server;

import java.util.List;


public class SQLQueries {
	public static User login(String username, String password)
	{
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		
		//Open the database connection
		database.Open();
		
		//Create a user instance
		User user = database.login(username, password);
		database.Close();
		return user;

	}
	
	public static User register(String username, String password, String email, String name)
	{
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		
		//Open the database connection
		database.Open();
		
		//Create a user instance
		User user = database.register(username, password, email, name);
		database.Close();
		return user;

	}
	
	//Check if user already exists in database
	public static boolean usernameExistance(String username)
	{
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		
		//Open the database connection
		database.Open();
		
		//Create a boolean indicating user existence 
		boolean flag = database.exist(username);
		database.Close();
		return flag;

	}
	
	public static List<Data> getAllData()
	{
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Data> listData =  database.getAllData();
		database.Close();
		return listData;

	}
	
	public static List<Gateway> getAllGateway()
	{
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Gateway> listGateway =  database.getAllGateways();
		database.Close();
		return listGateway;

	}
	
	public static List<Data> getGatewayData(int gatewayID)
	{
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		
		//Open the database connection
		database.Open();
		
		//Create a list of Data taken from the database
		List<Data> listData =  database.getGatewayData(gatewayID);
		database.Close();
		return listData;

	}
}
