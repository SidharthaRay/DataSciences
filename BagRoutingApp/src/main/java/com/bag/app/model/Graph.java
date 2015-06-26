package com.bag.app.model;

public class Graph {
	private String[] vertices;
	private int[][] incidenceMatrix;
	public Graph(String[] vertices, int[][] incidenceMatrix) {
		super();
		this.vertices = vertices;
		this.incidenceMatrix = incidenceMatrix;
	}
	public String[] getVertices() {
		return vertices;
	}
	public void setVertices(String[] vertices) {
		this.vertices = vertices;
	}
	public int[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}
	public void setIncidenceMatrix(int[][] incidenceMatrix) {
		this.incidenceMatrix = incidenceMatrix;
	}
}
