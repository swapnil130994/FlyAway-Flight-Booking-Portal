package com.flyaway.model;

import java.math.BigDecimal;

public class FlightFareDetails {
	private GetFlightDetails getFlightDetails;
	private String travelClass;
	private BigDecimal flightFare;
	/**
	 * @param getFlightDetails
	 * @param travelClass
	 * @param flightFare
	 */
	public FlightFareDetails(GetFlightDetails getFlightDetails, String travelClass, BigDecimal flightFare) {
		super();
		this.getFlightDetails = getFlightDetails;
		this.travelClass = travelClass;
		this.flightFare = flightFare;
	}
	public GetFlightDetails getGetFlightDetails() {
		return getFlightDetails;
	}
	public void setGetFlightDetails(GetFlightDetails getFlightDetails) {
		this.getFlightDetails = getFlightDetails;
	}
	public String getTravelClass() {
		return travelClass;
	}
	public void setTravelClass(String travelClass) {
		this.travelClass = travelClass;
	}
	public BigDecimal getFlightFare() {
		return flightFare;
	}
	public void setFlightFare(BigDecimal flightFare) {
		this.flightFare = flightFare;
	}
	@Override
	public String toString() {
		return "FlightFareDetails [getFlightDetails=" + getFlightDetails + ", travelClass=" + travelClass
				+ ", flightFare=" + flightFare + "]";
	}
	
	
}
