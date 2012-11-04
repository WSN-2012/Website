package server;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Database {
	/*
	 * String connectionURL = "jdbc:postgresql://localhost:5432/serverdb";
	 * String user= "postgres"; String password= "1234567890";
	 */

	Connection connection;
	Statement statement;
	String connectionURL;
	String driverName;
	String user;
	String password;

	public Database(String connectionURL, String user, String password,
			String driverName) {
		this.connectionURL = connectionURL;
		this.driverName = driverName;
		this.user = user;
		this.password = password;
	}

	/**
	 * Connect to the database
	 * 
	 * @param connectionURL
	 *            The URL for the database connection
	 * @param user
	 *            The username of the database
	 * @param password
	 *            The password of the database
	 * @param driverName
	 *            The database driver class Name
	 */
	public void Open() {

		try {
			// Load the Driver class.
			Class.forName(driverName);
			// Create the connection using the static getConnection method
			connection = DriverManager.getConnection(connectionURL, user,
					password);

			// Create a Statement class to execute the SQL statement
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
	}

	public User login(String username, String password) {
		try {
			
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select username, password, email, name from admins where username = '"
							+ username + "' and password = '" + password + "'");

			if (rs.next()) {
				User user = new User(rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("name"));
				rs.close();
				return user;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	public User register(String username, String password, String email, String name) {
		try {
			int id = 0;
			ResultSet rs = statement
					.executeQuery("select max(id) as id from admins");
			if (rs.next()) {
				id = rs.getInt("id");
			}
			
			// Submit a query, creating a ResultSet object
			statement.executeUpdate("insert into admins values('" + (++id) + "','" + username + "','" + password + "','" 
							+ email + "','" + name + "')");
			
			rs = statement
					.executeQuery("select username, password, email, name from admins where id = '" + id + "'");

			if (rs.next()) {
				User user = new User(rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("name"));
				rs.close();
				return user;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	//Check if username already exists in the DB
	public boolean exist(String username) {
		boolean exist = false;//indicate that username exists in DB
		try {
			
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select username from admins where username = '"
							+ username + "'");

			if (rs.next()) {
				exist = true;
			} 
				rs.close();
				return exist;
			
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return exist;
	}
	
	public List<Data> getAllData() {
		try {
			
			List<Data> listData = new ArrayList<Data>();
			// Submit a query, creating a ResultSet object
			ResultSet rs = statement
					.executeQuery("select * from wsndata ");

			while (rs.next()) {
				Data data = new Data();
				double t = -100;
				double ps = -100;
				double t_mcu = -100;
				double v_mcu = -100;
				String up = null;
				double rh = -100;
				double v_in = -100;
				double v_a1 = -100;
				data.setId(rs.getString("id"));
				data.setUtimestamp(rs.getTimestamp("utimestamp"));
				data.setUt(rs.getInt("ut"));
				if (rs.getDouble("t")!= -100) {t = rs.getDouble("t"); data.setT(t);}
				if (rs.getDouble("ps")!= -100) {ps = rs.getDouble("ps"); data.setPs(ps);}
				if (rs.getDouble("t_mcu")!= -100) {t_mcu = rs.getDouble("t_mcu"); data.setT_mcu(t_mcu);}
				if (rs.getDouble("v_mcu")!= -100) {v_mcu = rs.getDouble("v_mcu"); data.setV_mcu(v_mcu);}
				if (rs.getString("up")!= null) {up = rs.getString("up"); data.setUp(up);}
				if (rs.getDouble("rh")!= -100) {rh = rs.getDouble("rh"); data.setRh(rh);}
				if (rs.getDouble("v_in")!= -100) {v_in = rs.getDouble("v_in"); data.setV_in(v_in);}
				if (rs.getDouble("v_a1")!= -100) {v_a1 = rs.getDouble("v_a1"); data.setV_a1(v_a1);}
				listData.add(data);
				
			} 
			rs.close();
			return listData;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return null;
	}
	
	//Static Test of database
	/*public static void main(String args[]){
		//Create a new database connection
		Database database = new Database("jdbc:postgresql://localhost:5432/serverdb", "postgres", "1234567890", "org.postgresql.Driver");
				
		//Open the database connection
		database.Open();
	
		//Register a new user
		List<Data> data = database.getAllData();
		for (int i=0; i< data.size(); i++)
			System.out.println("ID: " + data.get(i).getId() + " Utimestamp: " + data.get(i).getUtimestamp() + " UT: " + data.get(i).getUt()
					+ " T: " + data.get(i).getT() + " PS: " + data.get(i).getPs() + " T_MCU: " + data.get(i).getT_mcu() + " V_MCU: " + data.get(i).getV_mcu() 
					+ " UP: " + data.get(i).getUp() + " RH: " + data.get(i).getRh() + " V_IN: " + data.get(i).getV_in() + " V_A1: " + data.get(i).getV_a1());
	}*/

}
