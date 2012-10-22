package server;


public class Authentication {
	public static User login(String username, String password)
	{
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
		User user = database.login(username, password);
		return user;

	}
}
