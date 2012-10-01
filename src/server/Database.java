package server;

public class Database {
	
	public static User login(String username, String password){
		//this method checks the database and returns the User object if there if a match and null otherwise
		
		//just for testing:
		if(username.equals("fabio") && password.equals("mypwd")){
			User user = new User("fabio","mypwd","fabio@gmail.com","Fabio Viggiani");
			return user;
		}
		
		return null;
	}
	
}
