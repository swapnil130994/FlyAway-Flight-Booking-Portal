package com.flyaway.model;

public class Airport {
	
	private int airportid;
	private int countryid;	
	private String airportCode;
	private String airportName;
	/**
	 * 
	 */
	public Airport() {
		
	}
	/**
	 * @param airportid
	 * @param countryid
	 * @param airportCode
	 * @param airportName
	 */
	public Airport(int airportid, int countryid, String airportCode, String airportName) {
		super();
		this.airportid = airportid;
		this.countryid = countryid;
		this.airportCode = airportCode;
		this.airportName = airportName;
	}
	public int getAirportid() {
		return airportid;
	}
	public void setAirportid(int airportid) {
		this.airportid = airportid;
	}
	public int getCountryid() {
		return countryid;
	}
	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	@Override
	public String toString() {
		return "Airport [airportid=" + airportid + ", countryid=" + countryid + ", airportCode=" + airportCode
				+ ", airportName=" + airportName + "]";
	}
	
	
}
