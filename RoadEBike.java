
import java.util.*;
import java.math.*;
import java.io.*;
import java.lang.*;

// Inherited/sub/child/derived class inherited from the Root/super/parent/base class
// Base/super/parent/root class: RoadBike
// Derived/sub/child/inherited class: RoadEBike

// This is a multi-level and multiple inheritance 
// BasicBike <---- RoadBike <----- RoadEBike (multi-level)
// 					  EBike <~~~~~ RoadEBike (multiple)

class RoadEBike extends RoadBike implements EBikeInterface
{
	// Put here the things that are unique to this class
	// These are properties or variable and methods that are specific 
	// to the RoadEBike class. These are in addition to 
	// the inherited ones from the base class (RoadBike). 
	
	// Variable inherited from base class are: 
	// maxSpeed, noOfGears, noOfWheels, paintColor, safetyFeatures, currentSpeed, flatHandleBar
	
	public boolean GPSTrackEnabled; // This is found only in this class i.e. belongs to this class only
	
	// Constructor 
	
	RoadEBike(int MS, int NG, int NW, String PC, String SF, boolean FHB, boolean GPS)
	{
		super(MS, NG, NW, PC, SF, FHB); // RoadBike's constructor 
		
		this.GPSTrackEnabled = GPS;
	}
	
	
	@Override
	public void showContents()
	{
		// To display the variable values on the screen
		String msg;
		msg = "Road E Bike Info:\n";
		msg = msg + "\tMax speed setting - " + maxSpeed + "\n";
		msg = msg + "\tNo of gears - " + noOfGears + "\n";
		msg = msg + "\tNo of wheels - " + noOfWheels + "\n";
		msg = msg  + "\tFrame color - " + paintColor + "\n";
		msg = msg + "\tSafety features - " + safetyFeatures + "\n";
		msg = msg + "\tFlat Handlebar - " + flatHandleBar + "\n";
		msg = msg + "\tBattery Type - " + batteryType + "\n";
		msg = msg + "\tBattery Size - " + batterySize + "\n";
		msg = msg + "\tBattery Voltage - " + batteryVolt + "\n";
		msg = msg + "\tRange in Miles - " + rangeMiles + "\n";
		msg = msg + "\tDrive motor power - " + motorPower + "\n";
		msg = msg + "\tGPS Tracking enabled - " + GPSTrackEnabled + "\n";
		System.out.println(msg);		
	}
	
	@Override
	public String [] getInfo()
	{
		// To return the variable values as an array (String) to the caller
		String msg;
		msg = maxSpeed + "::" + noOfGears + "::" + noOfWheels + "::" + paintColor + "::" + safetyFeatures;
		msg = msg + "::" + flatHandleBar;
		msg = msg + "::" + batteryType + "::" + batterySize + "::" + batteryVolt;
		msg = msg + "::" + rangeMiles + "::" + motorPower;
		msg = msg + "::" + GPSTrackEnabled;
		return msg.split("::");		
	}
	
}
