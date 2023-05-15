package com.flyaway.model;

import java.math.BigDecimal;

public class FlightTicket {
	
	private int bookingid;
	private int userid;
	private int bookingStatusid;
	private int triptypeid;
	private int flightNumber;
	private int travelClassid;
	private String srcAirportCode;
	private String srcAiportFullName;
	private String destAirportCode;
	private String destAirportFullName;
	private String travelDay;
	private String travelDate;
	private int totalPassengers;
	private BigDecimal totalFare;
	
	
	
	/**
	 * 
	 */
	public FlightTicket() {
		
	}
	/**
	 * @param bookingid
	 * @param userid
	 * @param bookingStatusid
	 * @param triptypeid
	 * @param flightNumber
	 * @param travelClassid
	 * @param srcAirportCode
	 * @param srcAiportFullName
	 * @param destAirportCode
	 * @param destAirportFullName
	 * @param travelDay
	 * @param travelDate
	 * @param totalPassengers
	 * @param totalFare
	 */
	public FlightTicket(int flightbookingid, int userid, int flightbookStatusid, int triptypeid, int flightNumber,
			int travelClassid, String srcAirportCode, String srcAiportFullName, String destAirportCode,
			String destAirportFullName, String travelDay, String travelDate, int totalPassengers,
			BigDecimal totalFare) {
		super();
		this.bookingid = flightbookingid;
		this.userid = userid;
		this.bookingStatusid = flightbookStatusid;
		this.triptypeid = triptypeid;
		this.flightNumber = flightNumber;
		this.travelClassid = travelClassid;
		this.srcAirportCode = srcAirportCode;
		this.srcAiportFullName = srcAiportFullName;
		this.destAirportCode = destAirportCode;
		this.destAirportFullName = destAirportFullName;
		this.travelDay = travelDay;
		this.travelDate = travelDate;
		this.totalPassengers = totalPassengers;
		this.totalFare = totalFare;
	}
	public int getFlightbookingid() {
		return bookingid;
	}
	public void setFlightbookingid(int flightbookingid) {
		this.bookingid = flightbookingid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getFlightbookStatusid() {
		return bookingStatusid;
	}
	public void setFlightbookStatusid(int flightbookStatusid) {
		this.bookingStatusid = flightbookStatusid;
	}
	public int getTriptypeid() {
		return triptypeid;
	}
	public void setTriptypeid(int triptypeid) {
		this.triptypeid = triptypeid;
	}
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}
	public int getTravelClassid() {
		return travelClassid;
	}
	public void setTravelClassid(int travelClassid) {
		this.travelClassid = travelClassid;
	}
	public String getSrcAirportCode() {
		return srcAirportCode;
	}
	public void setSrcAirportCode(String srcAirportCode) {
		this.srcAirportCode = srcAirportCode;
	}
	public String getSrcAiportFullName() {
		return srcAiportFullName;
	}
	public void setSrcAiportFullName(String srcAiportFullName) {
		this.srcAiportFullName = srcAiportFullName;
	}
	public String getDestAirportCode() {
		return destAirportCode;
	}
	public void setDestAirportCode(String destAirportCode) {
		this.destAirportCode = destAirportCode;
	}
	public String getDestAirportFullName() {
		return destAirportFullName;
	}
	public void setDestAirportFullName(String destAirportFullName) {
		this.destAirportFullName = destAirportFullName;
	}
	public String getTravelDay() {
		return travelDay;
	}
	public void setTravelDay(String travelDay) {
		this.travelDay = travelDay;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public int getTotalPassengers() {
		return totalPassengers;
	}
	public void setTotalPassengers(int totalPassengers) {
		this.totalPassengers = totalPassengers;
	}
	public BigDecimal getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(BigDecimal totalFare) {
		this.totalFare = totalFare;
	}
	@Override
	public String toString() {
		return "FlightTicket [bookingid=" + bookingid + ", userid=" + userid + ", flightbookStatusid="
				+ bookingStatusid + ", triptypeid=" + triptypeid + ", flightNumber=" + flightNumber
				+ ", travelClassid=" + travelClassid + ", srcAirportCode=" + srcAirportCode + ", srcAiportFullName="
				+ srcAiportFullName + ", destAirportCode=" + destAirportCode + ", destAirportFullName="
				+ destAirportFullName + ", travelDay=" + travelDay + ", travelDate=" + travelDate + ", totalPassengers="
				+ totalPassengers + ", totalFare=" + totalFare + "]";
	}
	
	
	
}
