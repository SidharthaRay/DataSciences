package com.bag.app.util;

import java.util.ArrayList;

import com.bag.app.model.Graph;
import com.bag.app.model.GraphTraversal;

public class GraphOperation {
	public static GraphTraversal findShortestPathInGraph(Graph graph, String source, String destination){
		String[] vertices = graph.getVertices();
		int[][] incidenceMatrix = graph.getIncidenceMatrix();
		//
		GraphTraversal graphTraversal = new GraphTraversal();
		//.
		ArrayList<String> vertexList=new ArrayList<String>();
		//.
		int[] distance = new int[vertices.length];
		int[] prevNode = new int[vertices.length];
		//
		int nodeCount = 0;
		int infinity = 999999;
		//
		for(String node : vertices){
			vertexList.add(node);
			//
			if(!node.equals(source)){
				distance[nodeCount] = infinity;
				prevNode[nodeCount] = -1;
			}
			else
			{
				distance[nodeCount] = 0;
				prevNode[nodeCount] = -1;
			}
			nodeCount++;
		}
		
		while(vertexList.size()>0){
			int minDistance = infinity;
			int minNode = infinity;
			for(int i=0;i<vertices.length;i++){
				if(vertexList.contains(vertices[i]))
					if(distance[i]<minDistance){
						minDistance = distance[i];
						minNode = i;
					}
			}
			//.Delete All nodes if the branch is broken wrt source
			if(minNode == infinity){
				vertexList = null;
				break;
			}
			//.
			vertexList.remove(vertices[minNode]);
			int currentNode = minNode;
			int currentNodeCost = minDistance;
			//.
			boolean isNeighbourPresent = false;
			for(int i=0;i<vertices.length;i++){
				if(vertexList.contains(vertices[i])){
					if(currentNode!=i){
						isNeighbourPresent = true;
						int distanceFromCurrNode = incidenceMatrix[currentNode][i];
						if(distanceFromCurrNode>0)
							if(currentNodeCost + distanceFromCurrNode < distance[i]){
								distance[i] = currentNodeCost + distanceFromCurrNode;
								prevNode[i] = currentNode;
							}
					}
				}
			}
			//Delete last Node
			if(!isNeighbourPresent)
				vertexList.remove(vertices[currentNode]);
		}
		//. Travel Time
		int destinationNodeIdx = -1;
		for(int i=0;i<vertices.length;i++){
			if(vertices[i].equals(destination)){
				destinationNodeIdx = i;
				graphTraversal.setTravelTime(distance[i]);
				break;
			}
		}
		//. Shortest path Details
		String path = "";
		int CurrNodeIdx = destinationNodeIdx;
		while(CurrNodeIdx!=-1){
			if(path.equals(""))
				path = vertices[CurrNodeIdx];
			else
				path = vertices[CurrNodeIdx] + " " + path;
			CurrNodeIdx = prevNode[CurrNodeIdx];
		}
		graphTraversal.setPath(path);
		return graphTraversal;
	}
}
