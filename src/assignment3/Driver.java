package assignment3;

public class Driver {

	public static void main(String[] args) {

		String sFirst = "AGGCTATCACCTGACCTCCAGGCCGATGCCC";
		String sSecond = "TAGCTATCACGACCGCGGTCGATTTGCCCGAC";
		
		MedSolver medSolver = new MedSolver(sFirst, sSecond);
		medSolver.computeCost();
	}

}
