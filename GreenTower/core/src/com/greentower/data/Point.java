package com.greentower.data;

public class Point implements Comparable<Point>{
	String name;
	int point;
	
	public Point(String name, int points){
		this.name = name;
		this.point = points;
	}

	@Override
	public int compareTo(Point o) {
		if(this.point > o.point){
			return 1;
		}else if(this.point == o.point){
			return 0;
		}else{
			return -1;
		}
	}
	
	@Override
	public String toString(){
		return this.name + "-" + this.point;
	}
	
	public int getPoint(){
		return this.point;
	}
	
	public String getName(){
		return this.name;
	}
}
