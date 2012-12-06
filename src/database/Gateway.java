package database;

public class Gateway {
	
	private int id;
	private String name;
	private boolean publicity;
	
	
	public Gateway(int id, String name, boolean publicity) {
		super();
		this.id = id;
		this.name = name;
		this.publicity = publicity;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isPublicity() {
		return publicity;
	}


	public void setPublicity(boolean publicity) {
		this.publicity = publicity;
	}
	

}
