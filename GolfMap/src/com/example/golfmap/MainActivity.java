package com.example.golfmap;


import java.util.ArrayList;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_list);

		ctx = this.getBaseContext();
		ListView menuList = (ListView) findViewById(R.id.ListView_Menu);
		ArrayList<String> items = new ArrayList<String>();
		
		items.add("Black Oak");
		items.add("Francis Byrne");
		items.add("Bethpage Black");
		
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

