package database;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;


public class Data {
	
	private Date utimestamp;
	private String sensorName;
	private Map<String,String> wsdata;
	
	
	public Data() {
		super();
	}

	public Data(Date utimestamp, String sensorName, Map<String,String> wsdata) {
		super();
		this.utimestamp = utimestamp;
		this.sensorName = sensorName;
		this.wsdata = wsdata;
	}

	public Date getUtimestamp() {
		return utimestamp;
	}

	public void setUtimestamp(Date utimestamp) {
		this.utimestamp = utimestamp;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public Map<String, String> getWSData() {
		return wsdata;
	}

	public void setWSData(Map<String, String> wsdata) {
		this.wsdata = wsdata;
	}
	
}
