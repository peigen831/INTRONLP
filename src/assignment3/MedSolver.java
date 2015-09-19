package assignment3;

public class MedSolver {
	String sFirst;
	String sSecond;
	int[][] cost;
	
	public MedSolver(String sFirst, String sSecond){
		this.sFirst = "#" + sFirst;
		this.sSecond = "#" + sSecond;
	}
	
	public void computeCost(){
		
		initialize();
		
		computeMinEdit();
		
		alignStrings();
		
		//printTable();
	}
	
	public void alignStrings(){
		int currentY = sFirst.length()-1;
		int currentX = sSecond.length()-1;
		int currentCost = cost[sFirst.length()-1][sSecond.length()-1];
		
		StringBuilder ans1 = new StringBuilder();
		StringBuilder ans2 = new StringBuilder();
		
		while(currentY > 0 || currentX > 0){
			if(currentY == 0){
				ans1.append("*");
				ans2.append(sSecond.charAt(currentX));
				currentX--;
				System.out.println("left");
			}
			
			else if(currentX == 0){
				ans1.append(sFirst.charAt(currentY));
				currentY--;
				ans2.append("*");
				System.out.println("down");
			}
				
			else{
				//BUG: Need to check the arrow, instead of simple comparing values
				//diagonal

				System.out.print(currentX + " " + currentY + " " + currentCost);
				if((currentCost == cost[currentY-1][currentX-1] && sFirst.charAt(currentY) == sSecond.charAt(currentX))|| (currentCost-2) == cost[currentY-1][currentX-1]){
					if(currentX != 0 && currentY != 0)
						currentCost = cost[currentY-1][currentX-1];
					
					ans1.append(sFirst.charAt(currentY));
					currentY--;
					ans2.append(sSecond.charAt(currentX));
					currentX--;
					System.out.println(" diagonal "+ currentCost);
				}
					
				//leftward
				else if(currentCost-1 == cost[currentY][currentX-1]){
					if(currentX != 0)
						currentCost = cost[currentY][currentX-1];
					ans1.append("*");
					ans2.append(sSecond.charAt(currentX));
					currentX--;
					System.out.println(" leftward " + currentCost);
				}
					
				//downward
				else{
					ans1.append(sFirst.charAt(currentY));
					if(currentY != 0)
						currentCost = cost[currentY-1][currentX];
					currentY--;
					ans2.append("*");
					System.out.println(" downward " + currentCost);
				}
			}
		}
		System.out.println(ans1.reverse().toString());
		System.out.println(ans2.reverse().toString());
	}
	
	public void computeMinEdit(){
		for(int i = 1; i < sFirst.length(); i++)
		{
			for(int j = 1; j < sSecond.length(); j++)
			{
				int left = cost[i-1][j] + 1;
				int buttom = cost[i][j-1] + 1;
				int diagonal = cost[i-1][j-1] + (sFirst.charAt(i)==sSecond.charAt(j) ? 0 : 2);
				
				cost[i][j] = getMinimum(left, buttom, diagonal);
			}
		}
	}
	
	//order matters
	public int getMinimum(int a, int b, int c){
		if(a <= b && a <= c)
			return a;
		
		else if (b <= a && b <= c)
			return b;
		
		return c;
	}
	
	public void initialize(){
		cost = new int[sFirst.length()+1][sSecond.length()+1];
		
		for(int i = 0; i < sFirst.length(); i++)
			cost[i][0] = i;
		
		for(int j = 0; j < sSecond.length(); j++){
			cost[0][j] = j;
		}
	}
	
	public void printTable(){
		for(int i = 0 ; i < sFirst.length(); i++)
		{
			
			System.out.print("Cost: " + i + " :" );
			
			for(int j = 0; j < sSecond.length(); j++)
			{
				System.out.print(cost[i][j]+ " ");
			}
			
			System.out.println();
		}
	}
	
}
