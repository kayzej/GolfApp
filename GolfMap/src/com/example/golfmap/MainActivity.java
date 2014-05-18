package com.example.golfmap;

import java.util.ArrayList;
import java.util.List;
import com.kayzej.services.DataBaseHelper;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	Context ctx;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		//On create, set the layout to the course list which is pulled from the local DB
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);

		ctx = this.getBaseContext();
		ListView menuList = (ListView) findViewById(R.id.ListView_Menu);
		
		//Creating a new DBHelper instance
		ArrayList<String> items = new ArrayList<String>();
		DataBaseHelper db = new DataBaseHelper(ctx);
		final List<Course> CourseList = db.getAllCourses();
		
		//Loading each course into the list view
		for (Course course : CourseList){
			items.add(course.getCourse_name());
		}

		//Creating the list view
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);
		menuList.setAdapter(adapt);
		
		//Setting the listener to find which course was clicked and open a MapView for that course
		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	         public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
	        	 
                 Intent intent = new Intent(MainActivity.this, MapView.class);
                 int cid = CourseList.get(position).getCourse_id();
                 //Bundle used to send data to the next activity, sending course id 
                 Bundle b = new Bundle();
	             b.putInt("key", cid); //Your id
	             intent.putExtras(b); //Put your id to your next Intent
	             startActivity(intent);
	             finish();
	         }
	     });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

