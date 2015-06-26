package com.bag.app.util;

public class ConsoleOperation {
	public static void show(String[] str){
		for(int i=0;i<str.length;i++){
			System.out.println(str[i]);
		}
	}
	
	public static void show(int[][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
