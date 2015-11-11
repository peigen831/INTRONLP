package assignment5ir;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StopwordFilter {
	
	public String filename = "stopword.txt";
	
	public String[] getStopwords(){
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
	    return lStopword.toArray(new String[lStopword.size()]);
	}
	
	
//	public static void main(String args[]){
//		StopwordFilter s = new StopwordFilter();
//		
//		String[] list = s.getStopwords();
//		
//		for(String a: list)
//			System.out.println(a);
//	}
}
