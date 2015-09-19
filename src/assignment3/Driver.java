package assignment3;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.print("Input original text: ");
		String sFirst = s.nextLine();
		
		System.out.print("Input target text: ");
		String sSecond = s.nextLine();
		
		MedSolver medSolver = new MedSolver(sFirst, sSecond);
		medSolver.computeCost();
	}

}
