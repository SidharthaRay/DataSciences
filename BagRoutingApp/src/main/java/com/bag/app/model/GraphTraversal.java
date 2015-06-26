package com.bag.app.model;

public class GraphTraversal {
	private int travelTime;
	private String path;
	public GraphTraversal() {
		super();
		this.travelTime = 0;
		this.path = "";
	}
	public GraphTraversal(int travelTime, String path) {
		super();
		this.travelTime = travelTime;
		this.path = path;
	}
	public int getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
