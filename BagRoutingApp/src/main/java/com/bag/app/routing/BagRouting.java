package com.bag.app.routing;
import com.bag.app.model.Bag;
import com.bag.app.model.BagRoute;
import com.bag.app.model.Departure;
import com.bag.app.model.Graph;
import com.bag.app.model.GraphTraversal;
import com.bag.app.util.ArrayOperation;
import com.bag.app.util.DataInput;
import com.bag.app.util.GraphOperation;


public class BagRouting {

	public static int[][] populateConveyorSystemGraph(ConveyorSystem[] conveyorSystems, String[] flightGates){
		int[][] ConveyorSystemGraph = new int[flightGates.length][flightGates.length];
		for(int i=0;i<conveyorSystems.length;i++){
			String node1 = conveyorSystems[i].getNode1();
			String node2 = conveyorSystems[i].getNode2();
			int travelTime = conveyorSystems[i].getTravelTime();
			
			int nodeNumber1 = ArrayOperation.getNodeNumber(flightGates,node1);
			int nodeNumber2 = ArrayOperation.getNodeNumber(flightGates,node2);
			
			ConveyorSystemGraph[nodeNumber1][nodeNumber2] = travelTime;
			ConveyorSystemGraph[nodeNumber2][nodeNumber1] = travelTime;
		}
		return ConveyorSystemGraph;
	}
	
	public static BagRoute[] populateBagRoute(Bag[] bags, Departure[] departures,String[] flightGates, int[][] ConveyorSystemGraph){
		BagRoute[] bagRoutes= new BagRoute[bags.length];
		int bagRouteCount = 0;
		for(Bag bag : bags)
		{
			BagRoute bagRoute = new BagRoute();
			//. Bag Number
			bagRoute.setBagNumber(bag.getBagNumber());
			//. Bag Source
			bagRoute.setSource(bag.getEntryPoint());
			//. Bag Destination
			bagRoute.setDestination(bagDestination(bag,departures));
			//. Bag Shortest Route Details
			String source = bagRoute.getSource();
			String destination = bagRoute.getDestination();
			//.
			Graph graph = new Graph(flightGates,ConveyorSystemGraph);
			GraphTraversal graphTraversal = GraphOperation.findShortestPathInGraph(graph,source, destination);
			int totalTravelTime = graphTraversal.getTravelTime();
			String routeDetails = graphTraversal.getPath();
			//
			//. Total Travel Time
			bagRoute.setTotalTravelTime(totalTravelTime);
			//. Shortest Route
			bagRoute.setRouteDetails(routeDetails);
			//
			bagRoutes[bagRouteCount] = bagRoute;
			bagRouteCount++;
		}
		return bagRoutes;
	}
	
	public static String bagDestination(Bag bag, Departure[] departures){
		String flightGate = "";
		
		if(bag.getFlightId().equals("ARRIVAL"))
			return "BaggageClaim";
		
		for(Departure departure : departures)
			{
				flightGate = departure.getFlightGate();
				break;
			}
		return flightGate;
	}

	public static void main(String[] args) {
		
		ConveyorSystem[] conveyorSystems = DataInput.dataInitConveyorSystem();
		Departure[] departures = DataInput.dataInitDeparture();
		Bag[] bags = DataInput.dataInitBag();
		
		String[] flightGates = ConveyorSystem.populateFlightGates(conveyorSystems);
		//ConsoleOperation.show(flightGates);
		
		int[][] ConveyorSystemGraph = populateConveyorSystemGraph(conveyorSystems,flightGates);
		//ConsoleOperation.show(ConveyorSystemGraph);
		
		BagRoute[] bagRoutes = populateBagRoute(bags, departures, flightGates, ConveyorSystemGraph);
		BagRoute.show(bagRoutes);
	}
}