package com.bag.app.model;

public class Departure {
	private String flightId;
	private String flightGate;
	private String destination;
	private String flightTime;
	
	public Departure(String flightId, String flightGate, String destination,
			String flightTime) {
		super();
		this.flightId = flightId;
		this.flightGate = flightGate;
		this.destination = destination;
		this.flightTime = flightTime;
	}
	
	public Departure(String departureFormat) {
		String[] parts = departureFormat.split(" ");
		this.flightId = parts[0];
		this.flightGate = parts[1];
		this.destination = parts[2];
		this.flightTime = parts[3];
	}
	
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getFlightGate() {
		return flightGate;
	}
	public void setFlightGate(String flightGate) {
		this.flightGate = flightGate;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}
	
	public void show(){
		System.out.print("<flightId = " + flightId + ">");
		System.out.print("<flightGate = " + flightGate + ">");
		System.out.print("<destination = " + destination + ">");
		System.out.print("<flightTime = " + flightTime + ">");
		System.out.println();
	}
}
