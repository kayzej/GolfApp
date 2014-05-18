package com.example.golfmap;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kayzej.services.DataBaseHelper;
import com.kayzej.services.GPSTracker;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.PopupMenu;
//import android.widget.Toast;

public class MapView extends Activity {
	private GoogleMap map;
	private GPSTracker gps;
	private double defLatitude;
	private double defLongitude;
	private double currLatitude;
	private double currLongitude;
	private double[] points;
	List<Item> Items;
	List<MarkerOptions> markers;
	Button defLoc;
	Button curLoc;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gps = new GPSTracker(this);
		points = GPSRefresh();
		currLatitude = points[0];
		currLongitude = points[1];
		
		setContentView(R.layout.activity_main);
		
		defLoc = (Button) findViewById(R.id.DefLoc);
		curLoc = (Button) findViewById(R.id.CurLoc);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		//Retrieving information sent via the bundle from MainActivity
		Bundle b = getIntent().getExtras();
		int course_id = b.getInt("key");
		
		DataBaseHelper db = new DataBaseHelper(this);
		double[] course_points = db.getCourseDefaults(course_id);
		defLatitude = course_points[0];
		defLongitude = course_points[1];
		
		markers = new ArrayList<MarkerOptions>();
		
		Items = db.getAllItems();
		
		//Moving the Map to the proper LatLng
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(defLatitude, defLongitude)).zoom(18).build();
 
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		
		LatLng position = new LatLng(defLatitude, defLongitude);
		Marker marker = map.addMarker(new MarkerOptions()
		                          .position(position)
		                          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		setMarkers();
	}
	
	public void setMarkers(){
		
		float color;
		
		for(Item point : Items){
			
			if(point.getType().equals("Tee_Box1")){
				color = BitmapDescriptorFactory.HUE_ORANGE;
			}
			else if(point.getType().equals("Bunker1")){
				color = BitmapDescriptorFactory.HUE_YELLOW;
			}
			else if(point.getType().equals("Green")){
				color = BitmapDescriptorFactory.HUE_GREEN;
			}
			else if(point.getType().equals("Perimeter")){
				color = BitmapDescriptorFactory.HUE_RED;
			}
			else{
				color = BitmapDescriptorFactory.HUE_BLUE;
			}
			
			LatLng position = new LatLng(point.getLatitude(), point.getLongitude());
			MarkerOptions marker = new MarkerOptions().position(position).flat(true).icon(BitmapDescriptorFactory.defaultMarker(color));
			map.addMarker(marker);
			//markers.add(marker);
		}
		
//		for(MarkerOptions marker : markers){
//			map.addMarker(marker);
//		}
	}
	
	public void setDefLoc(View v){
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(defLatitude, defLongitude)).zoom(18).build();
 
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	public void setCurLoc(View v){
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(currLatitude, currLongitude)).zoom(18).build();
 
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	//Get the user's current location, default location is Secaucus NJ
	public double[] GPSRefresh(){
		
		double[] point = {0, 0};
		double latitude;
		double longitude;
		gps.getLocation();
		
		if(gps.canGetLocation()){
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
				
		}
		else{			
			gps.showSettingsAlert();
			latitude = 40.783723;
			longitude = -74.083018;
		}
		
		point[0] = latitude;
		point[1] = longitude;	
		
		return point;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
