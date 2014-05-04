package com.example.golfmap;

public class Item {
	int course_id;
	int hole;
	String type;
	double latitude;
	double longitude;
	
	public Item(){
		
	}
	
	public Item(int cid, int aHole, String aType, double aLatitude, double aLongitude){
		this.course_id = cid;
		this.hole = aHole;
		this.type = aType;
		this.latitude = aLatitude;
		this.longitude = aLongitude;
	}
	
	public int getCourse() {
		return course_id;
	}

	public void setCourse(int i) {
		this.course_id = i;
	}

	public int getHole() {
		return hole;
	}

	public void setHole(int hole) {
		this.hole = hole;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
