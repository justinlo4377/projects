
public class HashTable {
	boolean SwitchMethod;

	LinkedList[] Storage;
	//*******************************************************************
	// HashTable
	//
	// Class which holds all the people created
	//*******************************************************************

	public HashTable() {
		
		Storage = new LinkedList[13];	//creates an array of linked lists with size of 13
		   
	      for (int i = 0; i < Storage.length; i++)
	      {
	         Storage[i] = new LinkedList();
	      }
	      
		SwitchMethod=true;
		
	}
	//*******************************************************************
	// getSwitchMethod
	//
	// returns if the hash table is currently using division or multiplication methods for calculations 
	//*******************************************************************

	
	public boolean getSwitchMethod() {
		return SwitchMethod;
		
	}
	//*******************************************************************
	// setSwitchMethod
	//
	// forces this hash table to use either division(true) or multiplication(false)
	// methods as its way of calculating
	//*******************************************************************
	public void setSwitchMethod(Boolean b) {
		SwitchMethod= b;
		
	}

	
	private int divisionMethod(Person p) {

		 return p.hashCode()%13;
	}
	
	private int multiplicationMethod(Person p) {

		double A = (Math.sqrt(5)-1)/2;
		
		return  (int) ( 13*((p.hashCode()*A)%1));
				
				
	}
	//*******************************************************************
	// chainedHashDelete
	//
	// Method to delete a person from the table.
	// (delete is handled by the main panel and friends panel)
	//*******************************************************************
	public void chainedHashDelete(Person p) {
		//delete x from the list T
		int location;
		if ( SwitchMethod) {
			location = divisionMethod(p);
		}
		else {
			location = multiplicationMethod(p);
		}
		Storage[location].remove(p);

	}
	//*******************************************************************
	// chainedHashSearch
	//
	// Searches a person with the inputed string inside the hasharray. If none, then returns null
	//*******************************************************************
	public Person chainedHashSearch(String s) {
		//search for an element with key k in list T
		for(int i=0; i<Storage.length;i++) {
			
			
			if (Storage[i].find(s)!=null) {
			
				Person p = Storage[i].find(s).getData();
				return p;
			}
			
		}
			return null;
	}
	//*******************************************************************
	// chainedHashInsert
	//
	// Method to add a person to this table. This method is used 
	// when adding the 50 names.
	//*******************************************************************
	public void chainedHashInsert(Person p) {
		//insert x at the head of list T 
		
		int location;
		if ( SwitchMethod) {
			location = divisionMethod(p);
		}
		else {
			location = multiplicationMethod(p);
		}
		Storage[location].insertHead(p);
	}
	//*******************************************************************
	// HashTabletoStringArr()
	//
	// To string method but not overidding the to string method. 
	//*******************************************************************
	public String[] HashTabletoStringArr() {
		String all = "";
		for(int i=0; i<Storage.length;i++) {
			
			
			if (Storage[i].isEmpty()==false) {
				all+=Storage[i].toString();
				
			}	
		}
		
		return all.split(",");
	}
		
	//*******************************************************************
	// PrintArray
	//
	// Tester method to print out objects in an array
	//*******************************************************************
	private void PrintArray(int A[]) {
		for(int x:A) {
			if(x!= Integer.MAX_VALUE) {
				System.out.print (x+" ");
			}

		}
		System.out.println (" ");
		
	}

}
