package com.example.golfmap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.kayzej.services.GPSTracker;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;

public class MapView extends Activity {
	private GoogleMap map;
	private GPSTracker gps;
	private double latitude;
	private double longitude;
	private double[] points;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		points = GPSRefresh();
		latitude = points[0];
		longitude = points[1];
		setContentView(R.layout.activity_main);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		Bundle b = getIntent().getExtras();
		int value = b.getInt("key");
		
		System.out.println("value: " + value);
		
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(18).build();
 
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	public double[] GPSRefresh(){
		
		double[] point = {0, 0};
		gps.getLocation();
		
		if(gps.canGetLocation()){
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			
			point[0] = latitude;
			point[1] = longitude;			
		}
		else{			
			gps.showSettingsAlert();
		}
		return point;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
