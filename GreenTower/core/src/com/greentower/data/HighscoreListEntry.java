package com.greentower.data;

public class HighscoreListEntry implements Comparable<HighscoreListEntry>{
	public String name;
	public float time;
	
	public HighscoreListEntry(String name, float time){
		this.name = name;
		this.time = time;
	}

	@Override
	public int compareTo(HighscoreListEntry o) {
		if(this.time > o.time){
			return 1;
		}else if(this.time == o.time){
			return 0;
		}else{
			return -1;
		}
	}
	
	@Override
	public String toString(){
		return this.name + "; " + this.time;
	}
}
