

import java.util.*;
import java.math.*;
import java.io.*;
import java.lang.*;

// Inherited/sub/child/derived class inherited from the Root/super/parent/base class
// Base/super/parent/root class: BasicBike
// Derived/sub/child/inherited class: RoadBike

class RoadBike extends BasicBike
{
	// Put here the things that are unique to this class (i.e. RoadBike)
	// These are properties or variable and methods that are specific 
	// to the RoadBike class. These are in addition to 
	// the inherited ones from the base class (i.e. BasicBike). 
	
	// Variable inherited from base class are: 
	// maxSpeed, noOfGears, noOfWheels, paintColor, safetyFeatures, currentSpeed
	
	// public variables
	public boolean flatHandleBar;
		
	// private member variables 
	
	// protected member variables 
	
	// Constructors  - only one is used here - you can have 
	// multiple constructors if you want to
	RoadBike(int MS, int NG, int NW, String PC, String SF, boolean FHB)
	{
		// send the base class variables to the base class' constructor
		super(MS, NG, NW, PC, SF);
		
		this.flatHandleBar = FHB;		
	}
	
	
	// Methods
	
	// Getters and Setter
	
	// Utility methods
		// Remember we have inherited two methods from the super class 
		// however they are suited for the use with super class objects only
	
	// Method OVERRIDING - base class' method get overriden 
	// by the inherited class' method
	// Polymorphism type (method overriding)
	@Override
	public void showContents()
	{
		// To display the variable values on the screen
		String msg;
		msg = "Road Bike Info:\n";
		msg = msg + "\tMax speed setting - " + maxSpeed + "\n";
		msg = msg + "\tNo of gears - " + noOfGears + "\n";
		msg = msg + "\tNo of wheels - " + noOfWheels + "\n";
		msg = msg  + "\tFrame color - " + paintColor + "\n";
		msg = msg + "\tSafety features - " + safetyFeatures + "\n";		
		msg = msg + "\tFlat Handlebars - " + flatHandleBar + "\n";		
		System.out.println(msg);		
	}
	
	public String [] getInfo()
	{
		// To return the variable values as an array (String) to the caller
		String msg;
		msg = maxSpeed + "::" + noOfGears + "::" + noOfWheels + "::" + paintColor + "::" + safetyFeatures;
		msg = msg + "::" + flatHandleBar;
		return msg.split("::");		
	}

}
