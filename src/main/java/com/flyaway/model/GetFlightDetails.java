package com.flyaway.model;

public class GetFlightDetails {
	
	private int flightNumber;	
	private String flightName;
	private String flightday;
	private String srcAirport;
	private String srcAiportFullName;
	private String destAirport;
	private String destAiportFullName;
	/**
	 * @param flightNumber
	 * @param flightName
	 * @param flightday
	 * @param srcAirport
	 * @param srcAiportFullName
	 * @param destAirport
	 * @param destAiportFullName
	 */
	public GetFlightDetails(int flightNumber, String flightName, String flightday, String srcAirport,
			String srcAiportFullName, String destAirport, String destAiportFullName) {
		super();
		this.flightNumber = flightNumber;
		this.flightName = flightName;
		this.flightday = flightday;
		this.srcAirport = srcAirport;
		this.srcAiportFullName = srcAiportFullName;
		this.destAirport = destAirport;
		this.destAiportFullName = destAiportFullName;
	}
	/**
	 * @param flightNumber
	 * @param flightName
	 * @param flightday
	 * @param srcAirport
	 * @param destAirport
	 */
	public GetFlightDetails(int flightNumber, String flightName, String flightday, String srcAirport,
			String destAirport) {
		super();
		this.flightNumber = flightNumber;
		this.flightName = flightName;
		this.flightday = flightday;
		this.srcAirport = srcAirport;
		this.destAirport = destAirport;
	}
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getFlightday() {
		return flightday;
	}
	public void setFlightday(String flightday) {
		this.flightday = flightday;
	}
	public String getSrcAirport() {
		return srcAirport;
	}
	public void setSrcAirport(String srcAirport) {
		this.srcAirport = srcAirport;
	}
	public String getSrcAiportFullName() {
		return srcAiportFullName;
	}
	public void setSrcAiportFullName(String srcAiportFullName) {
		this.srcAiportFullName = srcAiportFullName;
	}
	public String getDestAirport() {
		return destAirport;
	}
	public void setDestAirport(String destAirport) {
		this.destAirport = destAirport;
	}
	public String getDestAiportFullName() {
		return destAiportFullName;
	}
	public void setDestAiportFullName(String destAiportFullName) {
		this.destAiportFullName = destAiportFullName;
	}
	@Override
	public String toString() {
		return "GetFlightDetails [flightNumber=" + flightNumber + ", flightName=" + flightName + ", flightday="
				+ flightday + ", srcAirport=" + srcAirport + ", srcAiportFullName=" + srcAiportFullName
				+ ", destAirport=" + destAirport + ", destAiportFullName=" + destAiportFullName + "]";
	}
	
	
}
