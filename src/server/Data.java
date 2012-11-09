package server;

import java.util.Date;


public class Data {
	
	private String id;
	private Date utimestamp;
	private int ut;
	private double t;
	private double ps;
	private double t_mcu;
	private double v_mcu;
	private String up;
	private double rh;
	private double v_in;
	private double v_a1;
	private String gateway_name;
	
	
	public Data() {
		super();
	}

	public Data(String id, Date utimestamp, int ut, double t, double ps,
			double t_mcu, double v_mcu, String up, double rh, double v_in,
			double v_a1, String gateway_name) {
		super();
		this.id = id;
		this.utimestamp = utimestamp;
		this.ut = ut;
		this.t = t;
		this.ps = ps;
		this.t_mcu = t_mcu;
		this.v_mcu = v_mcu;
		this.up = up;
		this.rh = rh;
		this.v_in = v_in;
		this.v_a1 = v_a1;
		this.gateway_name = gateway_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUtimestamp() {
		return utimestamp;
	}

	public void setUtimestamp(Date utimestamp) {
		this.utimestamp = utimestamp;
	}

	public int getUt() {
		return ut;
	}

	public void setUt(int ut) {
		this.ut = ut;
	}

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public double getPs() {
		return ps;
	}

	public void setPs(double ps) {
		this.ps = ps;
	}

	public double getT_mcu() {
		return t_mcu;
	}

	public void setT_mcu(double t_mcu) {
		this.t_mcu = t_mcu;
	}

	public double getV_mcu() {
		return v_mcu;
	}

	public void setV_mcu(double v_mcu) {
		this.v_mcu = v_mcu;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public double getRh() {
		return rh;
	}

	public void setRh(double rh) {
		this.rh = rh;
	}

	public double getV_in() {
		return v_in;
	}

	public void setV_in(double v_in) {
		this.v_in = v_in;
	}

	public double getV_a1() {
		return v_a1;
	}

	public void setV_a1(double v_a1) {
		this.v_a1 = v_a1;
	}

	public String getGateway_id() {
		return gateway_name;
	}

	public void setGateway_id(String gateway_name) {
		this.gateway_name = gateway_name;
	}
	
	
}
