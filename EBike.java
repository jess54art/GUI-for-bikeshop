
import java.util.*;
import java.math.*;
import java.io.*;
import java.lang.*;

// Interface (fake class/snapshot)
// Looks similar to the actual class definition
// Will include all the variable that are to be "inherited"
// Will not have any method body (only header) for methods that are to be "inherited"
// Does not have a constructor

// Since no constructors will cover the interface variables, the interface definition
// MUST provide final values for all the variables in the interface

// By ONLY including the method headers for the all the methods intented for "inheriting"
// We force the class that implements an interface to provide the body for these
// header only methods.

interface EBikeInterface
{
	// public variables
	public String batteryType = "Li-ion"; // Li-ion, NiMH, SLA, AGM, LiPo
	public int batterySize = 75; // Ampere-hours
	public int batteryVolt = 24; // 12/24/36/48 volts
	public int rangeMiles = 50; // Range in miles
	public double motorPower = 0.75; // in KW 0.5. 1.0 etc
	
	public void showContents();
	public String [] getInfo();
	
}

// Inherited/sub/child/derived class inherited from the Root/super/parent/base class
// Base/super/parent/root class: MountainBike
// Derived/sub/child/inherited class: EBike

// This is a multi-level inheritance 
// BasicBike <---- MountainBike <----- EBike

class EBike extends MountainBike
{	
	// Put here the things that are unique to this class
	// These are properties or variable and methods that are specific 
	// to the EBike class. These are in addition to 
	// the inherited ones from the base class (MountainBike). 
	
	// Variable inherited from base class are: 
	// maxSpeed, noOfGears, noOfWheels, paintColor, safetyFeatures, currentSpeed, 
	// seatHeight, fullSuspension, flatProofTires
	
	// public variables
	public String batteryType; // Li-ion, NiMH, SLA, AGM, LiPo
	public int batterySize; // Ampere-hours
	public int batteryVolt; // 12/24/36/48 volts
	public int rangeMiles; // Range in miles
	public double motorPower; // in KW 0.5. 1.0 etc
	
	// Constructor 
	
	EBike(int MS, int NG, int NW, String PC, String SF, int SH, boolean FS, boolean FPT, String BYT, int BYS, int BYV, int RM, double MPW)
	{
		super(MS, NG, NW, PC, SF, SH, FS, FPT); // Mountain Bike class constructor
		
		this.batteryType = BYT;
		this.batterySize = BYS;
		this.batteryVolt = BYV;
		this.rangeMiles = RM;
		this.motorPower = MPW;
		
	}
	
	// Methods
	
	// Setters and Getters 
	
	// Utility methods
	
	// Method OVERRIDING - base class' method get overriden 
	// by the inherited class' method
	// Polymorphism type (method overriding)
	@Override
	public void showContents()
	{
		// To display the variable values on the screen
		String msg;
		msg = "E Bike Info:\n";
		msg = msg + "\tMax speed setting - " + maxSpeed + "\n";
		msg = msg + "\tNo of gears - " + noOfGears + "\n";
		msg = msg + "\tNo of wheels - " + noOfWheels + "\n";
		msg = msg  + "\tFrame color - " + paintColor + "\n";
		msg = msg + "\tSafety features - " + safetyFeatures + "\n";		
		msg = msg + "\tSeat Height - " + seatHeight + "\n";
		msg = msg + "\tFull Suspension - " + fullSuspension + "\n";
		msg = msg + "\tFlat Proof Tires - " + flatProofTires + "\n";
		msg = msg + "\tBattery Type - " + batteryType + "\n";
		msg = msg + "\tBattery Size - " + batterySize + "\n";
		msg = msg + "\tBattery Voltage - " + batteryVolt + "\n";
		msg = msg + "\tRange in Miles - " + rangeMiles + "\n";
		msg = msg + "\tMotor power in KW - " + motorPower + "\n";				
		System.out.println(msg);		
	}
	
	public String [] getInfo()
	{
		// To return the variable values as an array (String) to the caller
		String msg;
		msg = maxSpeed + "::" + noOfGears + "::" + noOfWheels + "::" + paintColor + "::" + safetyFeatures;
		msg = msg + "::" + seatHeight + "::" + fullSuspension + "::" + flatProofTires;
		msg = msg + "::" + batteryType + "::" + batterySize + "::" + batteryVolt;
		msg = msg + "::" + rangeMiles + "::" + motorPower;
		return msg.split("::");		
	}	
	
}
