package server;


public class User {
	private String name;
	private String email;
	private String password;
	private String username;
	
	public User(String username, String password, String email, String name) {
		setUserName(username);
		setPassword(password);
		setEmail(email);
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass()) && 
				this.getUserName() != null && ((User)obj).getUserName() != null &&
				this.getUserName().equals(((User)obj).getUserName()))
		{
			return true;
		}
		return super.equals(obj);
	}
}
