#How to run the application?
Run com.bag.app.routing.BagRouting as java application.

#Where to put the input?
Paste your input in BagRoutingApp/src/main/resources/input.txt file.

#What is the solution approach being followed?
	i. Bag need to be routed. So need to find out the source node (flight gate number) to destination node (flight gate number or Baggage Claim).
	ii. Source Node for the Bag:  
Bag list string - Format: <bag_number> <entry_point> <flight_id>
	iii. Destination Node for the Bag:
Flight list string - Format: <flight_id> <flight_gate> <destination> <flight_time> for the associated flight of the said bag.
	iv. Populate the Conveyor System of the airport as a graph. Then find the shortest route between source and destination node in the graph using any graph algorithm.
