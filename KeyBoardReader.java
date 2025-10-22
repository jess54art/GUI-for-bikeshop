
import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.*;


class KeyBoardReader
{
	// Declare an object of the BufferredReader class to better handle 
	// the inputs from the keyboard
	// The BufferedReader class is part of java.io.* package
	private BufferedReader myBR; // Declaration only
	private InputStreamReader myISR;
	
	// Constructor
	KeyBoardReader()
	{
		// Creating the object and initializing it automatically to read from keyboard
		myISR = new InputStreamReader(System.in); // Hey look at the standard input aka keyboard
		myBR = new BufferedReader(myISR);
	}
	
	// API for the outside use
	
	public String getString()
	{
		return sanityCheck(); // Call this method and return what ever the call returns
	}
	
	public int getInt()
	{
		return Integer.parseInt(sanityCheck());
	}
	
	public double getDouble()
	{
		return Double.parseDouble(sanityCheck());
	}
	
	
	private String sanityCheck()
	{
		// To temporarily hold what was read from the keyboard
		String line;
		
		// When we deal with an external input there is no guarantee that 
		// sane inputs are supplied
		// therefore we do the necessary checks here inside the 
		// private method hidden from the user
		
		// Problematic (potentially) code - we need to do exception handeling
		try
		{
			// put potentially problematic code here
			line = myBR.readLine(); 
			// from what was read from the keyboard get a line				
		}
		catch(IOException exp) // this exception if it happens
		{
			// put the response to an exception here
			// i.e. how to handle the exception
			
			// How to gracefully handle the exception and exit in an orderly manner
			// put the solution for the problematic code here
			line = "Unable to read from keyboard - ERROR #92187310984";
			exp.printStackTrace(); // puts meaningful error message on the screen
		}
		
		return line;
	}
	
	
	
}
