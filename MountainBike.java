

import java.util.*;
import java.math.*;
import java.io.*;
import java.lang.*;

// Inherited/sub/child/derived class inherited from the Root/super/parent/base class
// Base/super/parent/root class: BasicBike
// Derived/sub/child/inherited class: MountainBike

class MountainBike extends BasicBike
{
	// Put here the things that are unique to this class (i.e. MountainBike)
	// These are properties or variable and methods that are specific 
	// to the MountainBike class. These are in addition to 
	// the inherited ones from the base class (i.e. BasicBike). 
	
	// Variable inherited from base class are: 
	// maxSpeed, noOfGears, noOfWheels, paintColor, safetyFeatures, currentSpeed
	
	public int seatHeight;
	public boolean fullSuspension;
	public boolean flatProofTires;
	
	// private member variables 
	
	// protected member variables 
	
	// Constructors  - only one is used here - you can have 
	// multiple constructors if you want to
	MountainBike(int MS, int NG, int NW, String PC, String SF, int SH, boolean FS, boolean FPT)
	{
		// send the base class variables to the base class' constructor
		super(MS, NG, NW, PC, SF);
		
		this.seatHeight = SH;
		this.fullSuspension = FS;
		this.flatProofTires = FPT;
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
		msg = "Mountain Bike Info:\n";
		msg = msg + "\tMax speed setting - " + maxSpeed + "\n";
		msg = msg + "\tNo of gears - " + noOfGears + "\n";
		msg = msg + "\tNo of wheels - " + noOfWheels + "\n";
		msg = msg  + "\tFrame color - " + paintColor + "\n";
		msg = msg + "\tSafety features - " + safetyFeatures + "\n";		
		msg = msg + "\tSeat Height - " + seatHeight + "\n";
		msg = msg + "\tFull Suspension - " + fullSuspension + "\n";
		msg = msg + "\tFlat Proof Tires - " + flatProofTires + "\n";		
		System.out.println(msg);		
	}
	
	public String [] getInfo()
	{
		// To return the variable values as an array (String) to the caller
		String msg;
		msg = maxSpeed + "::" + noOfGears + "::" + noOfWheels + "::" + paintColor + "::" + safetyFeatures;
		msg = msg + "::" + seatHeight + "::" + fullSuspension + "::" + flatProofTires;
		return msg.split("::");		
	}
	
	
	
}
