package server;


public class Authentication {
	public static User login(String username, String password)
	{
		User user = Database.login(username, password);
		return user;

	}
}
