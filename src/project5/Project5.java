package project5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
* COP 3530: Project 5 – Hash Tables
* <p>
* This project implements a hash table using the unicode values of the country name and capital and then modulus the size of the array to find the hash key "(name + capital) % 307". The array size of
* of 307 is obtained by taking the total number of inputs (153 countries) and multiplies it by 2 and then converts to the closest prime number above that result which is 307. This Hash Table is using the 
* Separate Chaining method to deal with collisions by using linked lists to hold the data that collide at the same index.
* <p>
* The main method creates a buffered reader that reads a csv file line by line into a temporary string array. The data is then parsed to the correct type and is then inserted into the hash table.
* The hash table is then displayed, certain countries are deleted, more countries are then found and and their CFR's are printed. After that 5 more countries are deleted and the hash table is once again
* printed. At the end, the total number of empty cells is printed as well as the number of cells that have collisions (more than 1 country in the linked list).
*
* @author Nathanial Smith
* @version 12/03/2020
*/
public class Project5 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("COP3530 Project5\nName: Nathanial Smith N01232886\nInstructor: Xudong Liu\nHash Table\n");
		System.out.printf("Enter the name of the file: ");
		
		HashTable hash = new HashTable();
		
		BufferedReader read = null;
		String split = ",";
		String line = "";
		
		
		String name, capital;
		Double cfr, deaths2, cases2;
		Long cases, deaths;
		
		int i = 0;
		while(i == 0) {
			
			String file = input.nextLine();
			try {
			
				read = new BufferedReader(new FileReader(file));
				read.readLine();
				while ((line = read.readLine()) != null) {
					String[] countries = line.split(split);
				
					
					name = countries[0];
					capital = countries[1];
					cases = Long.parseLong(countries[4]);
					deaths = Long.parseLong(countries[5]);
					
					deaths2 = (double)deaths;
					cases2 = (double)cases;
					cfr = deaths2 / cases2;
					
					hash.insert(name, capital, cfr);
					i++;
				}
				
				 
				
				read.close();
				input.close();
			}
			
			catch(FileNotFoundException e) {
			System.out.println("Can't open file");
			System.out.print("Enter the name of the file: ");
			}
			
			catch(IOException e) {
			System.out.println("Problem with reading file");
			}
		}
		
		hash.display();
		
		System.out.println();
		
		hash.delete("Australia", "Canberra");
		hash.delete("Tunisia", "Tunis");
		hash.delete("Norway", "Oslo");
		
		Double cfr1;
		
		System.out.println();
		
		cfr1 = hash.find("Sri Lanka", "Colombo");
		if (cfr1 == -1){
			System.out.println("Sri Lanka was not found");
		} else {
			System.out.printf("Sri Lanka was found with a CFR of %.6f\n", cfr1);
		}
		
		cfr1 = hash.find("Cyprus", "Nicosia");
		if (cfr1 == -1){
			System.out.println("Cyprus was not found");
		} else {
			System.out.printf("Cyprus was found with a CFR of %.6f\n", cfr1);
		}
		
		cfr1 = hash.find("Tunisia", "Tunis");
		if (cfr1 == -1){
			System.out.println("Tunisia was not found");
		} else {
			System.out.printf("Tunisia was found with a CFR of %.6f\n", cfr1);
		}
		
		System.out.println();
		
		hash.delete("Malawi", "Lilongwe");
		hash.delete("Germany", "Berlin");
		hash.delete("Ireland", "Dublin");
		hash.delete("Yemen", "Sanaa");
		hash.delete("India", "New Delhi");
		
		System.out.println();
		
		hash.display();
		hash.printFreeAndCollisions();

	}

}
