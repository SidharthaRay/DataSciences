package com.bag.app.model;

public class Bag {
	private String bagNumber;
	private String entryPoint;
	private String flightId;
	
	public Bag(String bagNumber, String entryPoint, String flightId) {
		super();
		this.bagNumber = bagNumber;
		this.entryPoint = entryPoint;
		this.flightId = flightId;
	}
	public Bag(String bagFormat) {
		String[] parts = bagFormat.split(" ");
		this.bagNumber = parts[0];
		this.entryPoint = parts[1];
		this.flightId = parts[2];
	}
	
	public String getBagNumber() {
		return bagNumber;
	}
	public void setBagNumber(String bagNumber) {
		this.bagNumber = bagNumber;
	}
	public String getEntryPoint() {
		return entryPoint;
	}
	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	
	public void show(){
		System.out.print("<bagNumber = " + bagNumber + ">");
		System.out.print("<entryPoint = " + entryPoint + ">");
		System.out.print("<flightId = " + flightId + ">");
		System.out.println();
	}
	
	public static String[] populateBagNumber(Bag[] bags)
	{
		String[] bagNumbers = new String[bags.length];
		int bagCount = 0;
		for(Bag bag : bags)
			bagNumbers[bagCount++] = bag.bagNumber;
		return bagNumbers;
	}
}
