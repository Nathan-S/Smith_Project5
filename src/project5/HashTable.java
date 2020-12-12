package project5;

/**
* This class acts as a hash table implementing the separate chaining method of hashing the data. This class contains a class "SortedList" that acts as a double ended singly Linked list and a Node
* class that acts as the links. The primary functions of this hashTable class are to find specific nodes, delete nodes, insert nodes, display the table, and to display the number of cells with collisions
* and the number of empty cells.
*
* @author Nathanial Smith
* @version 12/4/20
*/
public class HashTable {
	class SortedList {
		private Node first;
		private Node last;
		public int size = 0;
		/**
		* This is the constructor for the SortedList class that operates as a linked list. It sets
		* first and last = null;
		*/
		public SortedList() {
			first = null;
			last = null;
		}
		/**
		* This method checks if the Linked List is empty by returning whether the value of "first is == to null.
		* @return first == null - Returns true if first == null or false if it is not
		*/
		public boolean isEmpty() {
			return first == null;
		}
		/**
		* This method checks to see if the Linked List is full, it can not fill up.
		* @return false - Returns false because a Linked list will never be full unless limited by memory
		*/
		public boolean isFull() {
			return false;
		}
		/**
		* This method inserts a Node at the end of the linked list by pushing the Node to the end of the list. 
		* @param Node thisNode - a Node object that will be passed into the Linked List.
		*/
		public void pushLast(Node thisNode) {
			if (isEmpty()) {
				first = thisNode;
			} else {
				last.nextNode = thisNode;
			}
			last = thisNode;
			size++;
		}
		/**
		* This method deletes a specific Node from the current Linked List. It first iterates through the linked list until the Node is found by comparing the name and capital passed in to
		* the name and capitol associated with the Node. If it is not found, null is returned. If it is found, then it is checked to see if it is first, and if it is not, then the previous.nextNode
		* is equal to current.nextnode and current is returned.
		* @param String name - The name that is being searched for.
		* @param String capital - The capital that is being compared.
		* @return null - returns null because the correct Node was not found
		* @return current - The Node that was being searched for
		*/
		public Node delete(String name, String capital) {
			Node current = first;
			Node previous = first;
			while(current.name.compareToIgnoreCase(name) != 0 && current.capitol.compareToIgnoreCase(capital) != 0) {
				if(current.nextNode == null) {
					return null;
				} else {
					previous = current;
					current = current.nextNode;	
				}
			}
			if(current == first) {
				first = first.nextNode;
			} else {
				previous.nextNode = current.nextNode;
			}
			size--;
			return current;
		}
		/**
		* This method finds a specific Node from the current Linked List. It first iterates through the linked list until the Node is found by comparing the name and capital passed in to
		* the name and capitol associated with the Node. If it is not found, null is returned. If it is found, then the current Node is returned.
		* @param String name - The name that is being searched for.
		* @param String capital - The capital that is being compared.
		* @return null - returns null because the correct Node was not found
		* @return current - The Node that was being searched for
		*/
		public Node find(String name, String capitol) {
			Node current = first;
				while(current.name.compareToIgnoreCase(name) != 0 && current.capitol.compareToIgnoreCase(capitol) != 0) {
						if(current.nextNode == null) {
							return null;
						} else {
							current = current.nextNode;
						}
				}
				return current;
		}
		/**
		* This method displays the link list by looping until current is == to null. It calls the printNode method in the Node class and then sets current = current.next.
		*/
		public void display() {
			Node current = first;
			while(current != null) {
				System.out.print("\t");
				current.printNode();
				current = current.nextNode;
			}
		}
	}
	/**
	* This is the Node class that operates as a link for the sortedList class. It holds the data for a country in the form of name, capitol, and CFR. This creates the Node object and can print the Node information.
	*
	* @author Nathanial Smith
	* @version 12/4/20
	*/
	private class Node {
		String name;
		String capitol;
		double CFR;
		Node nextNode;
		/**
		* This is the Node constructor that takes in the country name, capital, and CFR as a parameter. It sets the objects data place holders equal to their respective parameters.
		*
		* @param String name - This acts as the placeholder for the country name
		* @param String capitol - This acts as the placeholder for the country's capital
		* @param double CFR - This acts as a placeholder for the country's CFR
		*/
		public Node(String name, String capitol, double CFR) {
			this.name = name;
			this.capitol = capitol;
			this.CFR = CFR;
		}
		/**
		* This prints the data located in the Node in a formatted manner.
		*/
		public void printNode() {
			System.out.printf("%-40s %-20s %-30.6f\n", name, capitol, CFR);
		}
	}
	
	
	private SortedList[] array;
	private int arraySize = 307;
	/**
	* This is the constructor for the HashTable class. It sets SortedList[] array = to an array of size "arraySize". It then populates the entire array with new SortedLists using a for loop.
	*/
	public HashTable() {
		  array = new SortedList[arraySize];
		  for(int i = 0; i < arraySize; i++) {
			  array[i] = new SortedList();
		  }
	}
	/**
	* This method calculates the hash key by converting the two strings to character arrays and then looping through those arrays and converting the characters to their unicode/ASCII values.
	* Then those ascii values are all added into the value and then modulus by 307 (the size of the array) and the value is returned.
	*
	* @param String name - This acts as the placeholder for the country name and will be used to calculate the key
	* @param String capitol - This acts as the placeholder for the country's capital and will be used to calculate the key
	* @return value - this is the final value that is calculated for the hash key
	*/
	public int hashFunc(String name, String capitol) {
		int value = 0;
		int asciival;
		char[] nameChar = name.toCharArray();
		char[] capitolChar = capitol.toCharArray();
		for(int i = 0; i < nameChar.length; i++) {
			asciival = (int) nameChar[i];
			value += asciival;
		}
		for(int j = 0; j < capitolChar.length; j++) {
			asciival = (int) capitolChar[j];
			value += asciival;
		}
		value = value % 307;
		return value;
	}
	/**
	* This inserts a Node that is created based on the passed in values into the respective SortedList whose index is determined based on the calculated hash key.
	*
	* @param String name - This acts as the placeholder for the country name
	* @param String capitol - This acts as the placeholder for the country's capital
	* @param double CFR - This acts as a placeholder for the country's CFR
	*/
	public void insert(String name, String capital, double CFR) {
		Node node = new Node(name, capital, CFR);
		int hashKey = hashFunc(node.name, node.capitol);
		array[hashKey].pushLast(node);
	}
	/**
	* This finds a Node that is found by determining the hash key to find the appropriate SortedList and if it is empty, then the country is not there, otherwise, the find function is called
	* and if it is found, the CFR is returned and -1 if it was not found.
	*
	* @param String name - This acts as the placeholder for the country name
	* @param String capitol - This acts as the placeholder for the country's capital
	* @return -1 - returned if the Node was not found or does not exist.
	* @return a.CFR - the cfr of the located Node
	*/
	public double find(String name, String capitol) {
		int hashKey = hashFunc(name, capitol);
		if(array[hashKey].isEmpty() == true) { 
			return -1;
		} else {
			Node a = array[hashKey].find(name, capitol);
			if(a == null) {
				return -1;
			} else {
				return a.CFR;
			}
		}
	}
	/**
	* This deletes a Node that is searched by calling the SortedList delete method and first the SortedList is found by calculating the hash key and then checking if the list is empty. If it is found,
	* then it prints that it was deleted, otherwise, it prints it was not found.
	*
	* @param String name - This acts as the placeholder for the country name
	* @param String capitol - This acts as the placeholder for the country's capital
	*/
	public void delete(String name, String capitol) {
		int cfr = 0;
		int hashKey = hashFunc(name, capitol);
		if(array[hashKey].isEmpty() == true) { 
			System.out.println(name + " was not found to be deleted");
		} else {
			Node a = array[hashKey].delete(name, capitol);
			if(a == null) {
				System.out.println(name + " was not found to be deleted");
			} else {
				System.out.println(name + " was deleted from the hash table");
			}
		}
	}
	/**
	* This displays the contents of the hash table by looping through the entire table and calling the sortedList display method. If the List is empty, "Empty" is printed.
	*/
	public void display() {
		System.out.println("\nHash Table Contents:\n");
		for(int i = 0; i < array.length; i++) {
			if(array[i].first == null) {
				System.out.printf("%03d.", i);
				System.out.println("\tEmpty");
			} else {
				System.out.printf("%03d.", i);
				array[i].display();
			}
		}
	}
	/**
	* This method determines how many cells were empty and how many cells had collisions. The SortedList class had a size variable that was increased every time a node was inserted and it
	* was decremented every time a node was deleted. That size value determined if it was empty or if there were collisions. If that value was 0 then it was empty, If it was above 1 then it had a 
	* collision. The results were then printed.
	*/
	public void printFreeAndCollisions() {
		int empty = 0;
		int collision = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i].size == 0) {
				empty++;
			} 
			if(array[i].size > 1) {
				collision++;
			}
		}
		System.out.println("The Hash table has " + empty + " empty spaces and " + collision + " collisions");
	}
}
