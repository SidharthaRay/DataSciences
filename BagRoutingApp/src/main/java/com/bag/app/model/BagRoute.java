package com.bag.app.model;

public class BagRoute {
	private String bagNumber;
	private String source;
	private String destination;
	private String routeDetails;
	private int totalTravelTime;
	
	public BagRoute() {
		super();
		this.bagNumber = "";
		this.source = "";
		this.destination = "";
		this.routeDetails = "";
		this.totalTravelTime = 0;
	}
	public BagRoute(String bagNumber, String source, String destination,
			String routeDetails, int totalTravelTime) {
		super();
		this.bagNumber = bagNumber;
		this.source = source;
		this.destination = destination;
		this.routeDetails = routeDetails;
		this.totalTravelTime = totalTravelTime;
	}
	public String getRouteDetails() {
		return routeDetails;
	}
	public void setRouteDetails(String routeDetails) {
		this.routeDetails = routeDetails;
	}
	public int getTotalTravelTime() {
		return totalTravelTime;
	}
	public void setTotalTravelTime(int totalTravelTime) {
		this.totalTravelTime = totalTravelTime;
	}
	public String getBagNumber() {
		return bagNumber;
	}
	public void setBagNumber(String bagNumber) {
		this.bagNumber = bagNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void show(){
		System.out.print("<bagNumber = " + bagNumber + ">");
		System.out.print("<source = " + source + ">");
		System.out.print("<destination = " + destination + ">");
		System.out.print("<routeDetails = " + routeDetails + ">");
		System.out.print("<totalTravelTime = " + totalTravelTime + ">");
		System.out.println();
	}
	
	public void showCustomised(){
		System.out.print(bagNumber + " ");
		System.out.print(routeDetails + " : ");
		System.out.print(totalTravelTime);
		System.out.println();
	}
	
	public static void show(BagRoute[] bagRoutes){
		for(BagRoute bagRoute : bagRoutes)
		{
			bagRoute.showCustomised();
		}
	}
}
