package com.kayzej.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.golfmap.Course;
import com.example.golfmap.Item;

public class DataBaseHelper extends SQLiteOpenHelper{
		 
    //The Android's default system path of your application database.
    
	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/GolfMap/databases/";
    		
    private static String DB_NAME = "coordinates.db";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
   
   /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
 * @return 
     */
    
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
    	//delete(context);
        this.myContext = context;
        try {
			
			 this.createDataBase();
			 System.out.println("db created succesfully");
	 
	 	} catch (IOException ioe) {
	 		
	 		System.out.println("Io excpetion: " + ioe.getMessage());
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		this.openDataBase();
	 		System.out.println("db opened succesfully");
	 
	 	}catch(SQLException sqle){
	 		System.out.println("sql exception: " + sqle.getLocalizedMessage());
	 		throw sqle;	 
	 	}
    }	
    
  /**
    * Creates a empty database on the system and rewrites it with your own database.
    * */
    public void createDataBase() throws IOException{
    	
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
    	DB_PATH = myContext.getFilesDir().getPath();
    	
    	try{
    		
    		String myPath = DB_PATH + "/" + DB_NAME;    	
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {

	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	public List<Item> getAllItems() {
		List<Item> ItemList = new ArrayList<Item>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + "Items";
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item Item = new Item();
				
				Item.setCourse(Integer.parseInt(cursor.getString(1)));
				Item.setHole(Integer.parseInt(cursor.getString(2)));
				Item.setType(cursor.getString(3));
				Item.setLatitude(Double.parseDouble(cursor.getString(4)));
				Item.setLongitude(Double.parseDouble(cursor.getString(5)));
				// Adding Item to list
				ItemList.add(Item);
				
			} while (cursor.moveToNext());
		}

		// return Item list
		return ItemList;
	}
	
	public double[] getCourseDefaults(int cid){
		double[] points = {0.0, 0.0};
		
		//Setting sql queries for gettin def_lat and def_long
		String latQuery = "SELECT def_lat FROM Courses Where _id = " + cid;
		String longQuery = "SELECT def_long FROM Courses Where _id = " + cid;

		//Cursors don't execute until .moveToFirst() is called
		Cursor latCursor = myDataBase.rawQuery(latQuery, null);
		if(latCursor.moveToFirst()){
			points[0] = Double.parseDouble((latCursor.getString(0)));
		}
		
		Cursor longCursor = myDataBase.rawQuery(longQuery, null);
		if(longCursor.moveToFirst()){
			points[1] = Double.parseDouble((longCursor.getString(0)));
		}
		
		
		return points;
	}
	
	public List<Course> getAllCourses() {
		List<Course> CourseList = new ArrayList<Course>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + "Courses";
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Course course = new Course();
				course.setCourse_id(Integer.parseInt(cursor.getString(0)));
				course.setCourse_name(cursor.getString(1));
				course.setDef_lat(Double.parseDouble(cursor.getString(2)));
				course.setDef_long(Double.parseDouble(cursor.getString(3)));
				
				// Adding Course to list
				CourseList.add(course);
				
			} while (cursor.moveToNext());
		}

		// return Item list
		return CourseList;
	}	
}