package com.flyaway.model;

import java.io.Serializable;
public class SourceDestination implements Serializable {
	
	private int flightdetailsid;
	private String flightnumber;
	private int airlineid;
	private String src_airportCode;
	private String dest_airportCode;
	private int totalNumSeats;
	private int availFirstClassSeats;
	private int availBusinessClassSeats;
	private int availEconomySeats;
	
	/**
	 * @param flightdetailsid
	 * @param flightnumber
	 * @param airlineid
	 * @param src_airportCode
	 * @param dest_airportCode
	 * @param totalNumSeats
	 * @param availFirstClassSeats
	 * @param availBusinessClassSeats
	 * @param availEconomySeats
	 */
	public SourceDestination(int flightdetailsid, String flightnumber, int airlineid, String src_airportCode,
			String dest_airportCode, int totalNumSeats, int availFirstClassSeats, int availBusinessClassSeats,
			int availEconomySeats) {
		super();
		this.flightdetailsid = flightdetailsid;
		this.flightnumber = flightnumber;
		this.airlineid = airlineid;
		this.src_airportCode = src_airportCode;
		this.dest_airportCode = dest_airportCode;
		this.totalNumSeats = totalNumSeats;
		this.availFirstClassSeats = availFirstClassSeats;
		this.availBusinessClassSeats = availBusinessClassSeats;
		this.availEconomySeats = availEconomySeats;
	}

	/**
	 * @param flightnumber
	 * @param airlineid
	 * @param src_airportCode
	 * @param dest_airportCode
	 * @param totalNumSeats
	 * @param availFirstClassSeats
	 * @param availBusinessClassSeats
	 * @param availEconomySeats
	 */
	public SourceDestination(String flightnumber, int airlineid, String src_airportCode, String dest_airportCode,
			int totalNumSeats, int availFirstClassSeats, int availBusinessClassSeats, int availEconomySeats) {
		super();
		this.flightnumber = flightnumber;
		this.airlineid = airlineid;
		this.src_airportCode = src_airportCode;
		this.dest_airportCode = dest_airportCode;
		this.totalNumSeats = totalNumSeats;
		this.availFirstClassSeats = availFirstClassSeats;
		this.availBusinessClassSeats = availBusinessClassSeats;
		this.availEconomySeats = availEconomySeats;
	}

	public int getFlightdetailsid() {
		return flightdetailsid;
	}

	public void setFlightdetailsid(int flightdetailsid) {
		this.flightdetailsid = flightdetailsid;
	}

	public String getFlightnumber() {
		return flightnumber;
	}

	public void setFlightnumber(String flightnumber) {
		this.flightnumber = flightnumber;
	}

	public int getAirlineid() {
		return airlineid;
	}

	public void setAirlineid(int airlineid) {
		this.airlineid = airlineid;
	}

	public String getSrc_airportCode() {
		return src_airportCode;
	}

	public void setSrc_airportCode(String src_airportCode) {
		this.src_airportCode = src_airportCode;
	}

	public String getDest_airportCode() {
		return dest_airportCode;
	}

	public void setDest_airportCode(String dest_airportCode) {
		this.dest_airportCode = dest_airportCode;
	}

	public int getTotalNumSeats() {
		return totalNumSeats;
	}

	public void setTotalNumSeats(int totalNumSeats) {
		this.totalNumSeats = totalNumSeats;
	}

	public int getAvailFirstClassSeats() {
		return availFirstClassSeats;
	}

	public void setAvailFirstClassSeats(int availFirstClassSeats) {
		this.availFirstClassSeats = availFirstClassSeats;
	}

	public int getAvailBusinessClassSeats() {
		return availBusinessClassSeats;
	}

	public void setAvailBusinessClassSeats(int availBusinessClassSeats) {
		this.availBusinessClassSeats = availBusinessClassSeats;
	}

	public int getAvailEconomySeats() {
		return availEconomySeats;
	}

	public void setAvailEconomySeats(int availEconomySeats) {
		this.availEconomySeats = availEconomySeats;
	}

	@Override
	public String toString() {
		return "SourceDestination [flightdetailsid=" + flightdetailsid + ", flightnumber=" + flightnumber
				+ ", airlineid=" + airlineid + ", src_airportCode=" + src_airportCode + ", dest_airportCode="
				+ dest_airportCode + ", totalNumSeats=" + totalNumSeats + ", availFirstClassSeats="
				+ availFirstClassSeats + ", availBusinessClassSeats=" + availBusinessClassSeats + ", availEconomySeats="
				+ availEconomySeats + "]";
	}
	
	
}

