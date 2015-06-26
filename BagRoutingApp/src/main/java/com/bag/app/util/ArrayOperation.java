package com.bag.app.util;

public class ArrayOperation {
	public static int getNodeNumber(String[] flightGates, String flightGate){
		for(int i=0;i<flightGates.length;i++)
			if(flightGates[i].equals(flightGate))
				return i;
		return -1;
	}
}
