
public class Sorts {
	
	//*******************************************************************
	// Sorts
	//
	// A Class made to seperate sorting algorithms from the main class. 
	// Constructor used for testing purposes, but it is empty.
	//*******************************************************************
	
	public Sorts(){
		
//		int[] A = {18, 25, 6, 9, 15, 12, 5, 20, 11, 30,5};
//		int[] A = {9, 5, 6, 9, 7, 5, 5, 1, 4, 0,1};
//		int[] B1 = {903, 521, 666, 981, 722, 535, 540, 109, 404, 112,157};
//		QuickSort(A,0,A.length-1);
//		PrintArray(A);
		
//		int [] B = new int [A.length];
//		int [] X = new int [A.length];
//		int k= FindLargest(B1);
//		int d = Integer.toString(k).length();
//		RadixSort(B1,d);
		
//		CountingSort(A,B,X,k);
//		PrintArray(B);
		
//		int n = 10; //N is the number you are dividing by to sort into buckets. Can be a set range as well
//		BucketSort(B,n);
//		PrintArray(B);
	}
	
	//*******************************************************************
	// Bucket Sort
	//
	// Method which creates and sorts based on the input n.
	// N is a divisor which the input is divided by. Then however many buckets are created. 
	//*******************************************************************
	
	public void BucketSort(int[] A,int n) { //problem, converting psudocode from array within another array, fixed with 2d array
		
		int [][] B = new int [A.length][A.length]; //changed from n-1 to n also changed to make 10 consistant buckets
	
		//int [][] B = new int [n][n];
		
		int v= n;
		n=A.length;
	
		//for(int i =0; i<n-1; i++) {				//clears and makes each array full of zeros.
			
		//	B[i]= new int [n] ;
		//}
		
		for(int i=0; i<n;i++) {					//moves all integers into their respective buckets
			int j = A[i]/v; 
			System.out.println("In bucket :"+j+", "+A[i]+" is stored"); //View the poisition and number of the SSN
			B[j][i]=A[i]; 
		}
		//  System.out.println(Arrays.deepToString(B).replace("], ", "]\n"));
		
		for(int i=0; i<n-1; i++) {				//Sorts each bucket with insertion sort.
			insertionSort( B[i]);
		}
		
		int counter =0;
		
		for(int i=0;i<B.length;i++) {			//Checks each bucket and puts it back into the array. Ignores empty ones
			
			for(int j=0;j<B[i].length;j++) {
				
				if(B[i][j]!=0) {//problem when there is integer 0 in the array wont check for it
					A[counter]=B[i][j];
					counter++;
				}

			}
		
		}
	
	}
	
	//*******************************************************************
	// Radix Sort
	//
	// Method made to sort by digits. 
	// Sorts each digit by counting sort.
	//*******************************************************************	
	
	public void RadixSort(int[] A,int d) {
		//sort the first integer, then the 2nd using counting sort
		//problem, how to get the first integer of every single number, then next. DONE
		//problem, mapping the first digit of every number with the rest. DONE
		//sort the large number with the integer;	
		
		int digitIncrementer=1;//multiplies by 10 every time to correspond with digit
		
		while(d>0){//does this as many times as there are digits.
			
			 
			int digitContainer [] = new int[A.length];// contains the row of single digits 
			int newPositions [] = new int[A.length]; //holds the new positions of the moved A[] numbers
			int B [] = new int [A.length];
			for(int x=0;x<A.length;x++) {

				digitContainer[x]=A[x]%(10*digitIncrementer)/digitIncrementer; 	//for each iteration, uses mod to find digit
			}																	// truncates the leftover integers
																				//Ie: 521's 2nd iteration becomes 21 
																				//Then 1 is truncated leaving 2
			CountingSort (digitContainer,B,newPositions,9);						//Calls Counting Sort here
			
			CountDigitAmount(B,digitIncrementer);								//tester function to see if Counting sort works
			
			
			digitIncrementer=digitIncrementer*10;
			
			for(int x=0;x<A.length;x++) {
				 B[newPositions[x]]=A[x];
			}
			for(int x=0;x<A.length;x++) {										//replaces old Array ints with new array ints
				 A[x]= B[x];
			}
		
			
			d--;
		}	
		
	}

	//*******************************************************************
	// Counting Sort (modified)
	//
	// A Method made to organize numbers based on their position in the array.
	//
	// Counting Sort is modified to return the new positions of the old values relative to the old array.
	// Example 9401 where 9 is in position 0, 4 is in 1, 0 is in 2, 1 is in 3 .
	// D returns as 3 in position 0, 2 in position 1, 0 in position 2, 1 in position 3; 
	// Unsorted Arr: 9401 Old positions 0123
	// Sorted array: 0149 New positions 3201
	//*******************************************************************
	
	private void CountingSort(int[] a, int[] b, int[]newValues, int k) {
		
		int[]C= new int[k+1]; //changed from k to k+1
		
		for(int i=0; i<k;i++) {
			C[i]=0;
		}
		
		for(int j=0; j<a.length;j++) {

			C[a[j]]=C[a[j]]+1;
		}
		
		for(int i=1; i<k+1;i++) { //changed from k to k+1
			C[i]=C[i]+C[i-1];
		}
		
		for(int j=a.length-1; j>=0;j--) { //changed from j>0 to j>=0
			
			b[C[a[j]]-1]=a[j];	//changed from C[a[j]] to C[a[j]]-1:  problem, converting Array's length to 0 from psudocode. DONE
			
			newValues[j]=C[a[j]]-1; //stores the position of the new integer, in the position of the old position.

			C[a[j]]=C[a[j]]-1;
		}
		
	}
	
	//*******************************************************************
	// InsertionSort 
	//
	// Basic Insertion Sort method
	// Used on each sorted bucket, so will be very fast.
	//*******************************************************************	
	private void insertionSort (int A[] ) {
		
		for(int j=1; j<A.length; j++) { //because Arrays always start at 0, 
										//j needs to be 1 instead of 2
			int key = A[j];
			int i=j-1;
			while (i>=0 && A[i] > key) { //since j is subtracted by 1, 
										 //i is changed to be able to accept A[0]
				A[i+1]=A[i];
				i=i-1;
				A[i+1]=key;						
			}
		
		}
		
	}
	//*******************************************************************
	// QuickSort
	//
	// Calls partition with a piviot, then calls itself on the left and rights side
	//*******************************************************************

	public void QuickSort(int[] a,int p, int r) {
		if(p<r) {
			
			int q= Partition(a,p,r);
			
			QuickSort(a,p,q-1);
			System.out.println("Left: p="+p+" q-1="+(q-1)+"");
			QuickSort(a,q+1,r);
			System.out.println("Right: q+1="+(q+1)+" r="+r);
		}
		
	}
	//*******************************************************************
	// Partition
	//
	// Swaps till all integers less then the piviot are on the left, and all integers greater are on the right.
	//*******************************************************************	
	public int Partition(int[] a, int p, int r) {

		int x= a[r];
		
		int i =p-1;
		for(int j = p; j<r;j++) {
			if(a[j]<=x) {
			i++;
			int save = a[i];
			a[i]=a[j];
			a[j]=save;
			}
		}
		int save2 = a[i+1];
		a[i+1]= a[r];
		a[r]=save2;
		return i+1;
	}
	//*******************************************************************
	// PrintArray 
	//
	// Testing method which prints an array of integers
	//*******************************************************************

	private void PrintArray(int A[]) {
		for(int x:A) {
			if(x!= Integer.MAX_VALUE) {
				System.out.print (x+" ");
			}

		}
		System.out.println (" ");
		
	}
	//*******************************************************************
	// CountDigitAmount
	//
	// Testing method which how many of each integer there are in which digit column
	//*******************************************************************
	private void CountDigitAmount(int[]a, int b) {
		int container [ ]= new int [10];
		for(int x : a) {
			container [x]++;
		}
		for (int x=0; x<container.length;x++) {
			int f = container[x];
			System.out.println("There are "+f+" amount of "+x+"'s in the "+b+"'s digit");
		}
	}

}
