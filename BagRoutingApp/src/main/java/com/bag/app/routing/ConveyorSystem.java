package com.bag.app.routing;
import java.util.ArrayList;

public class ConveyorSystem {
	private String node1;
	private String node2;
	private int travelTime;
	
	public ConveyorSystem(String node1, String node2, int travelTime) {
		super();
		this.node1 = node1;
		this.node2 = node2;
		this.travelTime = travelTime;
	}
	
	public ConveyorSystem(String conveyorSystemFormat) {
        String[] parts = conveyorSystemFormat.split(" ");
		this.node1 = parts[0];
		this.node2 = parts[1];
		this.travelTime = Integer.parseInt(parts[2]);
	}
	
	public String getNode1() {
		return node1;
	}
	public void setNode1(String node1) {
		this.node1 = node1;
	}
	public String getNode2() {
		return node2;
	}
	public void setNode2(String node2) {
		this.node2 = node2;
	}
	public int getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}
	
	public void show(){
		System.out.print("<node1 = " + node1 + ">");
		System.out.print("<node2 = " + node2 + ">");
		System.out.print("<travelTime = " + travelTime + ">");
		System.out.println();
	}
	
	public static String[] populateFlightGates(ConveyorSystem[] conveyorSystems)
	{
		
		ArrayList<String> flightGateList=new ArrayList<String>();
	    
		int flightGateCount = 0;
		for(ConveyorSystem conveyorSystem : conveyorSystems)
		{
			//. Adding node1 if unique
			String node1 = conveyorSystem.node1;
			int i = 0;
			for(i=0;i<flightGateCount;i++)
				if(flightGateList.get(i).equals(node1))
					break;
			if(i==flightGateCount){
				flightGateList.add(node1);
				flightGateCount++;
			}
			
			//. Adding node2 if unique
			String node2 = conveyorSystem.node2;
			for(i=0;i<flightGateCount;i++)
				if(flightGateList.get(i).equals(node2))
					break;
			if(i==flightGateCount){
				flightGateList.add(node2);
				flightGateCount++;
			}
		}
		
		String[] flightGates = new String[flightGateList.size()];
		for(int i=0;i<flightGateList.size();i++)
		{
			flightGates[i] = flightGateList.get(i);
		}
		
		return flightGates;
	}
}
