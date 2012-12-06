package database;

public class Sensor {
	
	private String id;
	private String name;
	private int gatewayId;
	
	public int getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(int gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Sensor(String id, String name, int gatewayID) {
		super();
		this.id = id;
		this.name = name;
		this.gatewayId = gatewayID;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}


/**
natalia
*/