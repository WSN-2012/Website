/*
 * Copyright 2012 KTH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

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
