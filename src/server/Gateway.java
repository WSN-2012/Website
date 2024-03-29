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

package server;

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
