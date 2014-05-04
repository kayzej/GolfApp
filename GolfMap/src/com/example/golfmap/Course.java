package com.example.golfmap;

public class Course {
	int course_id;
	String course_name;
	double def_lat;
	double def_long;
	
	public Course(){
		course_id = 0;
		course_name = null;
		def_lat = 0.0;
		def_long = 0.0;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public double getDef_lat() {
		return def_lat;
	}

	public void setDef_lat(double def_lat) {
		this.def_lat = def_lat;
	}

	public double getDef_long() {
		return def_long;
	}

	public void setDef_long(double def_long) {
		this.def_long = def_long;
	}
}
