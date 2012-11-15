package server;

import java.math.BigInteger;
import java.util.Date;


public class Data {
	
	private Date utimestamp;
	private BigInteger ut;
	private double t;
	private double ps;
	private double t_mcu;
	private double v_mcu;
	private String up;
	private double rh;
	private double v_in;
	private String sensorName;
	
	
	public Data() {
		super();
	}

	public Data(Date utimestamp, BigInteger ut, double t, double ps,
			double t_mcu, double v_mcu, String up, double rh, double v_in, String sensorName) {
		super();
		this.utimestamp = utimestamp;
		this.ut = ut;
		this.t = t;
		this.ps = ps;
		this.t_mcu = t_mcu;
		this.v_mcu = v_mcu;
		this.up = up;
		this.rh = rh;
		this.v_in = v_in;
		this.sensorName = sensorName;
	}

	public Date getUtimestamp() {
		return utimestamp;
	}

	public void setUtimestamp(Date utimestamp) {
		this.utimestamp = utimestamp;
	}

	public BigInteger getUt() {
		return ut;
	}

	public void setUt(BigInteger ut) {
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

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	
	
}
