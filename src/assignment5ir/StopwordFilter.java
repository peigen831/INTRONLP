package assignment5ir;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StopwordFilter {
	
	public String filename = "stopword.txt";
	
	public ArrayList<String> getStopwords(){
		ArrayList<String> lStopword = new ArrayList<String>();
	    try {

	        Scanner sc = new Scanner(getClass().getResourceAsStream(filename));

	        while (sc.hasNextLine()) {
	        	String temp = sc.nextLine();
	        	temp = temp.trim();
	            lStopword.add(temp);
	        }
	        sc.close();
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lStopword;
	}
	
	
	public static void main(String args[]){
		StopwordFilter s = new StopwordFilter();
		
		ArrayList<String> stop = s.getStopwords();
		
		ArrayList<String> nList = new ArrayList<String>();
		
		String[] newjoin = {"at", "atin", "gogo", "ating", "ay", "lets"};
		
		for(String a: newjoin){
			if(!stop.contains(a))
				nList.add(a);
		}
		for(String a: nList){
			System.out.println(a);
		}
	}
}
