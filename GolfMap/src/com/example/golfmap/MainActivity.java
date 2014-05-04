package com.example.golfmap;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kayzej.services.DataBaseHelper;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	Context ctx;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);

		ctx = this.getBaseContext();
		ListView menuList = (ListView) findViewById(R.id.ListView_Menu);
		ArrayList<String> items = new ArrayList<String>();
		DataBaseHelper db = new DataBaseHelper(ctx);
		db = dbPrep(db);
		List<Course> CourseList = db.getAllCourses();
		//List<Item> Items = db.getAllItems();
		
		for (Course course : CourseList){
			items.add(course.getCourse_name());
		}

		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);
		menuList.setAdapter(adapt);
		
		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	         public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

	             // Note: if the list was built "by hand" the id could be used.
	             // As-is, though, each item has the same id
                 Intent intent = new Intent(MainActivity.this, MapView.class);
	             //Bundle b = new Bundle();
	             //b.putInt("key", 1); //Your id
	             //intent.putExtras(b); //Put your id to your next Intent
	             startActivity(intent);
	             finish();
	         }
	     });
	}
	
	public DataBaseHelper dbPrep(DataBaseHelper db){
		try {
			
			 db.createDataBase();
			 System.out.println("db created succesfully");
	 
	 	} catch (IOException ioe) {
	 		
	 		System.out.println("Io excpetion: " + ioe.getMessage());
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		db.openDataBase();
	 		System.out.println("db opened succesfully");
	 
	 	}catch(SQLException sqle){
	 		System.out.println("sql exception: " + sqle.getLocalizedMessage());
	 		throw sqle;	 
	 	}
	 	
	 	return db;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

