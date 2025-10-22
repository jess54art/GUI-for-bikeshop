
import java.util.*;
import java.math.*;
import java.io.*;
import java.lang.*;

// Define the properties and features of the basic bike type 
// This file is not intended for execution so there is no need for a PSVM

// BasicBike is the (base class) or (parent class) or (super class) or (root class) 
// for all the other 4 classes we will be writing

// This base class is used to describe the common attributes for all the bikes 
// we want to include in our code

// In general the base class must be the least common demonimator of sorts 
// for all the classes one would want to define. 
// This would depend on the problem one is attempting to solve.

// What is the problem we are trying to solve?
// We want to create an inventory management system for a bike shop 
// This implies we need a way to model a bike that is for sale in the shop
// We also need to know the types and number of bikes that are in the shop

// We can then create a class for each bike type and then create objects when 
// ever new inventory arrives at the shop or delete the objects when the bikes 
// are sold or lost

// Base or Root or Parent or Super class are name that are all used interchangeably
// Derived or Inherited or Child or Sub class are name that are all used interchangeably

class BasicBike 
{
	// public member variables, there are the ones that are 
	// intended to be inhertied by any class that uses this class 
	// as its base class
	
	public int maxSpeed; // This is the max safe operating speed
	public int noOfGears; // No of gears for the bike
	public int noOfWheels; // No of gears for the bike
	public String paintColor; // Frame color for the bike
	public String safetyFeatures; // List of safety features
	public int currentSpeed; //A variable to hold the current speed (when testing)
	
	// Private member variable - NOT INHERITABLE
	// Only objects of this class can access these private variables, that too
	// not outside of this class definition
	private int privateVariableExample;
	
	// Protected member variable - Selectively inheritable
	// Only objects of this class or objects of child classes can access these 
	// protected variables
	protected int protectedVariableExample;
	
	// We can also have private, public and protected methods 
	// depending upon the needs of the software being developed
	
	// Constructors
	
	// We can have more than one method (here its the constructor) with the 
	// same name BUT the methods must have different method signature i.e. 
	// the number and type of arguments that are being passed to the 
	// methods - THIS IS AN EXAMPLE FOR POLYMORPHISM.
	// POLY = many
	// MORPH = form
	// POLYMORPHISM = the ability to express or be present in many forms
	
	BasicBike() // form #1
	{
		// Null constructor - default values 
		maxSpeed = 0;
		noOfGears = 0;
		noOfWheels = 0;
		paintColor = "null";
		safetyFeatures = "null";
		currentSpeed = 0;
		
		privateVariableExample = 0;
		protectedVariableExample = 0;
	}
	
	BasicBike(int MS) // form #2
	{
		// Null constructor - default values 
		maxSpeed = MS;
		noOfGears = 0;
		noOfWheels = 0;
		paintColor = "null";
		safetyFeatures = "null";
		currentSpeed = 0;
		
		privateVariableExample = 0;
		protectedVariableExample = 0;
	}
	
	BasicBike(int MS, int NG) // form #3
	{
		// Null constructor - default values 
		maxSpeed = MS;
		noOfGears = NG;
		noOfWheels = 0;
		paintColor = "null";
		safetyFeatures = "null";
		currentSpeed = 0;
		
		privateVariableExample = 0;
		protectedVariableExample = 0;
	}
	
	BasicBike(int MS, int NG, int NW) // form #4
	{
		// Null constructor - default values 
		maxSpeed = MS;
		noOfGears = NG;
		noOfWheels = NW;
		paintColor = "null";
		safetyFeatures = "null";
		currentSpeed = 0;
		
		privateVariableExample = 0;
		protectedVariableExample = 0;
	}
	
	BasicBike(int MS, int NG, int NW, String PC) // form #5
	{
		// Null constructor - default values 
		maxSpeed = MS;
		noOfGears = NG;
		noOfWheels = NW;
		paintColor = PC;
		safetyFeatures = "null";
		currentSpeed = 0;
		
		privateVariableExample = 0;
		protectedVariableExample = 0;
	}
	
	BasicBike(int MS, int NG, int NW, String PC, String SF) // form #6
	{
		// Null constructor - default values 
		maxSpeed = MS;
		noOfGears = NG;
		noOfWheels = NW;
		paintColor = PC;
		safetyFeatures = SF;
		currentSpeed = 0;
		
		privateVariableExample = 0;
		protectedVariableExample = 0;
	}
	
	
	// Methods
	
	// Getters and Setter
	// None needed here because we do not have any useful private variable - just 
	// example variable are shown here
	
	// Utility methods
	public void showContents()
	{
		// To display the variable values on the screen
		String msg;
		msg = "Basic Bike Info:\n";
		msg = msg + "\tMax speed setting - " + maxSpeed + "\n";
		msg = msg + "\tNo of gears - " + noOfGears + "\n";
		msg = msg + "\tNo of wheels - " + noOfWheels + "\n";
		msg = msg  + "\tFrame color - " + paintColor + "\n";
		msg = msg + "\tSafety features - " + safetyFeatures + "\n";
		System.out.println(msg);		
	}
	
	public String [] getInfo()
	{
		// To return the variable values as an array (String) to the caller
		String msg;
		msg = maxSpeed + "::" + noOfGears + "::" + noOfWheels + "::" + paintColor + "::" + safetyFeatures;
		return msg.split("::");		
	}
	
	
}
