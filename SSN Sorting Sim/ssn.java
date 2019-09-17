import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ssn {
	
	//*******************************************************************
	// SSN
	//
	// A Class created to run the program, and condense all the sorting algorithms
	// with the user interface.
	//*******************************************************************
	
	public static void main(String[] args) {
		run();
	}

	private static void run() {
	
		askUser();
		//displayInterface(randomIntArray);		//tests if the user interface works
		
	}
	
	//*******************************************************************
	// Ask User
	//
	// Takes an array of 300 integers passed from generateRandomSSNArray
	// Converts the 300 integers into a string with SSN format. IE. ***-**-****
	// Prompts user for which sort they would like to use with the user interface.
	// Then creates a file containing the 300 SSN strings, or replaces an old file if it already exists
	//*******************************************************************
	
	private static void askUser() {
		int randomIntArray[] = generateRandomSSNArray();		//creates 300 random integers of SSN length
		
		File file = null;
		
		file = createFile(randomIntArray,"Random_SSN.txt");		//creates 300 random integers of SSN length
		
		Boolean run = true;
		
		Display frame = new Display();
		String g []= new String [randomIntArray.length];
		int counter=0;
		for(int x: randomIntArray) {
			String s =String.format("%09d", x);											//String format so integer 
																						//will account for leading zeros
			g[counter]=s.substring(0,3)+"-"+s.substring(3,5)+"-"+s.substring(5,9);		//Adding in hypens 
			counter++;
		}
		
		String v=" ";
		while (run == true) { //keeps looping the joption pane unless user chooses close or the red exit button
				
		 v= (String) JOptionPane.showInputDialog( null,frame.getContentPane(), " Choose an option ", JOptionPane.PLAIN_MESSAGE, null, g, g[0]);		
		 
			frame.dispose();	//exits the frame after user chooses an option

			if(v==null) {
				
				frame.dispose();
				run = false;
				break;
			}
			
			if (frame.s.equals("New Random SSN")) {		//creates a new random text file with same formatting as above
				
				frame.dispose();
				int counter2=0;
				int y[]=generateRandomSSNArray();
				for(int i:y) {
					randomIntArray[counter2]=i;
					counter2++;
				}
				counter2=0;
				for(int x: randomIntArray) {
					String s =String.format("%09d", x);
					g[counter2]=s.substring(0,3)+"-"+s.substring(3,5)+"-"+s.substring(5,9);		//make the integer always have 9 digits
					counter2++;
				}
				
				file = null;
				file = createFile(randomIntArray,"Random_SSN.txt");
				 
			}
			if (frame.s.compareTo("QuickSort")==0) {	//calls on the Quicksort method
				frame.dispose();
				QuickSort(file); 						
	
			}
			if (frame.s.compareTo("BucketSort")==0) {	//calls on the Bucketsort method
				frame.dispose();
				BucketSort(file); 
				
				
			}
			if (frame.s.compareTo("RadixSort")==0) {	//calls on the Radixsort method
				frame.dispose();
				RadixSort(file);
	
			}
			
			
		}

	}
	//*******************************************************************
	// QuickSort
	//
	// Calls read file to convert the Random_ssn file to an array of ints
	// Calls the sorting class with quicksort
	// Creates/replaces a file named quick_ssn.
	// Displays the interface
	//*******************************************************************

	
	private static void QuickSort(File file) {
		int ReadArray[] = readFile(file);
		Sorts s = new Sorts();
		
		s.QuickSort(ReadArray, 0, ReadArray.length-1);

		file = createFile(ReadArray,"Quick_SSN.txt");
		
		displayInterface(ReadArray, "QuickSort");	
		
	}
	//*******************************************************************
	// BucketSort
	//
	// Calls read file to convert the Random_ssn file to an array of ints
	// Calls the sorting class with Bucketsort. Creates an n to determine # of buckets
	// Creates/replaces a file named bucket_ssn.
	// Displays the interface
	//*******************************************************************
	
	private static void BucketSort(File file) {
		
		int ReadArray[] = readFile(file);
		Sorts s = new Sorts();
		int n = 100000000;								//100000000 is the number you are dividing by to sort into buckets.
		s.BucketSort(ReadArray, n); 					//This sorting by the first integer so there are 10 buckets of 0-9.
														//Ex: 988888885/100000000 = 9.88888885, belongs in bucket 9
											
		file = createFile(ReadArray,"Bucket_SSN.txt");
		displayInterface(ReadArray, "BucketSort");
		
	}
	//*******************************************************************
	// RadixSort
	//
	// Calls read file to convert the Random_ssn file to an array of ints
	// Finds the largest integer with FindLargest()
	// Converts that largest integer into a string, then finds the string's length to determine digits
	// Calls the sorting class with Radixsort.
	// Creates/replaces a file named Radix_ssn.
	// Displays the interface
	//*******************************************************************
	
	private static void RadixSort(File file) {
		int ReadArray[] = readFile(file);
		Sorts s = new Sorts();
		
		int k= FindLargest(ReadArray);
		//System.out.println(k);
		int d = Integer.toString(k).length();
		//System.out.println(d);
		
		s.RadixSort(ReadArray, d);
		PrintArray(ReadArray);
		file = createFile(ReadArray,"Radix_SSN.txt");
		
		
		displayInterface(ReadArray, "RadixSort");
		
	}
	
	//*******************************************************************
	// FindsLargest
	//
	// Finds and returns the largest integer in an array
	//*******************************************************************	
	
	private static int FindLargest(int [ ]A) {

		int largest = 0;
		for(int k:A) {
			if (k>largest) {
				largest=k;
			}
		}
		return largest;
	}
	
	//*******************************************************************
	// readFile
	//
	// Creates a file reader and buffered reader to convert a file into an array of SSN strings
	// Changes SSN strings into a string of numbers, then calls parse int to convert those strings into integers
	//*******************************************************************
	
	private static int[] readFile(File file) {

		int []container= new int[300];

		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String st; 
			
			int counter =0;
			while ((st = br.readLine()) != null) { 
				st= st.substring(0,3)+st.substring(4,6)+st.substring(7,11);
				
				
				container[counter]=Integer.parseInt(st);
				counter++;
			}	
			//PrintArray(container);
			br.close();
			fr.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return container;
	}
	
	//*******************************************************************
	// createFile
	//
	// Creates a file writer and print writer to convert the given array into a text file
	// *IMPORTANT* FILE IS CREATED WHERE THE RUNNABLE JAR IS 
	// If doesnt exist, creates a new file, then lets user know in a message dialog
	// If does exists, replaces the old file, then lets user know in a message dialog 
	// Then returns that file
	//*******************************************************************	

	private static File createFile(int[] randomIntArray,String name) {

		//File file = new File("SSN Text Files/"+name);
		
		File file = new File(System.getProperty("user.dir"), name);		//determines where the runnable jar is, then creates a file there
		
		try {
			
			if (file.createNewFile()){
			
				JOptionPane.showMessageDialog(null, "Created new file "+ name+" in "+file.getPath());
			}
			else{
				JOptionPane.showMessageDialog(null, "Overwritten old file "+ name+" in "+file.getPath()); //lets user know that file exists but is being modified
			}
			FileWriter writer = new FileWriter(file,false);
			PrintWriter pw = new PrintWriter(writer);
			
			for(int x: randomIntArray) {
				String s =String.format("%09d", x);
				pw.write(s.substring(0,3)+"-"+s.substring(3,5)+"-"+s.substring(5,9));		//make the integer always have 9 digits
				pw.println();
			}
			
			pw.close();
			writer.close();
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		return file;

	}
	//*******************************************************************
	// displayInterface
	//
	// Takes an array of integers and saves the area location of each integer
	// Creates a panel which displays the area location and how many integers are in each location
	// Then creates a joption panel and combines both panels 
	//*******************************************************************
	private static void displayInterface(int[] randomIntArray, String sort) {

		String s[]= new String[20];
		
		final int WIDTH= 200;
    	final int HEIGHT= 120;

    	JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        GridLayout layout = new GridLayout(5,1,0,10);
        panel2.setLayout(layout);
        
		int NEC=0;
		int SC=0;
		int MS=0;
		int NWC=0;
		int WC=0;
		
		for (int k=0;k<randomIntArray.length; k++) {
			int truncate6 = randomIntArray[k]/1000000;  //truncates to get the first 3 digits
														//IE: 999-34-552 becomes 999
			
			if(truncate6<199)
				NEC++;
			if(truncate6>=200 && truncate6<=399)
				SC++;
			if(truncate6>=400 && truncate6<=599)
				MS++;
			if(truncate6>=600 && truncate6<=799)
				NWC++;
			if(truncate6>=800 && truncate6<=999)
				WC++;
							
		}
		s[0]= "Northeast Coast States: " + NEC + " people";
		s[1]= "South Coast States: " + SC + " people";
		s[2]= "Middle States: " + MS + " people";
		s[3]= "Northwest Coast States: " + NWC + " people";
		s[4]= "West Coast States: " + WC + " people";
		
		panel2.add(new JLabel(s[0]));
		panel2.add(new JLabel(s[1]));
		panel2.add(new JLabel(s[2]));
		panel2.add(new JLabel(s[3]));
		panel2.add(new JLabel(s[4]));
		
		int counter = 0;
		String g[]= new String [randomIntArray.length];
		
		for(int x: randomIntArray) {
			String f =String.format("%09d", x);
			String z =String.format("%-40s", f.substring(0,3)+"-"+f.substring(3,5)+"-"+f.substring(5,9));
			g[counter]=(counter+1)+": "+z;		
			counter++;
		}
		
		JOptionPane.showInputDialog( null,panel2, "SSN sorted with "+sort,	//panel 2 is the previous panel created above
							JOptionPane.PLAIN_MESSAGE, null, g, g[0]);		//J option panel is combined with the previous panel	
	}
	
	//*******************************************************************
	// generateRandomSSNArray
	//
	// Creates a hashset, and puts random integers inside until the size is 300
	// Because an int's hashcode is just the int, uses an iterator to put random ints into an array
	// Then returns the array
	//*******************************************************************	

	private static int[] generateRandomSSNArray() {
		HashSet <Integer> h = new HashSet<Integer>();	 //uses hash set to make sure every ssn is unique
		
		while(h.size()<300) {
			int x=randomWithRange(1000000,999999999); 	//area code numbers cant start with 000, 
			 h.add(x);									//so range is from 001-00-0000 to 999-99-9999
		}
		Iterator<Integer> it = h.iterator();			//itterates through the hashset to get random ints
		int y=0;
		int[] randomIntArray = new int[300];
	    
	    while(it.hasNext()){
	    	
	    	randomIntArray[y]=it.next().intValue();
	       y++;
	       
	     }
	    
		//PrintArray(randomIntArray);
		
		return randomIntArray;
		
	}
	//*******************************************************************
	// randomWithRange
	//
	// returns a random integer with a preset minimum and maximum
	//*******************************************************************		
	
	static int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	//*******************************************************************
	// printArray
	//
	// Prints an array's contents with a given array. Tester method
	//*******************************************************************	
	private static void PrintArray(int A[]) {
		for(int x:A) {
			if(x!= Integer.MAX_VALUE) {
				System.out.print (x+" ");
			}
		
		}
		System.out.println (" ");
		
	}
	
}
