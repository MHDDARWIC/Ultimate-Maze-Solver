package mazesolver;

import java.util.LinkedList;

public class IterativeDeepening {
	
	public String[][] search (String[][] startingGraph, int columns, int startingSearchDepth){
            
		int[] currentPoint = {-1, -1, -1, -1, -1};
		int currentPointNumber = 0;
		int searchDepth = startingSearchDepth;

		String[][] workingGraph = startingGraph;
		LinkedList<int[]> visitedList, frontierList;

		visitedList = new LinkedList<int[]>();
		frontierList = new LinkedList<int[]>();

		for(int i = 0; i < workingGraph.length; i++){
			boolean foundStart = false;
			for(int j = 0; j < columns; j++){
				if(workingGraph[i][j].equalsIgnoreCase("00")){
					currentPoint = new int[] {i, j, -1, -1, 0};
					foundStart = true;
				}
				if(foundStart)
					break;
			}
			if(foundStart)
				break;
		}

		if(currentPoint[0] < 0 || currentPoint[1] < 0){
			System.out.println("No starting point found. Terminating Iterative Deepening Search");
			return workingGraph;
		}

		boolean goalFound = false;
		
		do{
			if(searchDepth == 0 && !frontierList.isEmpty()){
				if(!visitedList.contains(currentPoint))
					visitedList.add(currentPoint);
				if(!frontierList.isEmpty()){
					currentPoint = frontierList.getLast();
					frontierList.removeLast();
				}
				searchDepth = startingSearchDepth - currentPoint[4];
			}
			if(workingGraph[currentPoint[0]][currentPoint[1]].equalsIgnoreCase("GG")){
				System.out.println("Found the goal");
				goalFound = true;
				break;
			}
			for(int i = 0; i < searchDepth; i++){
				if(workingGraph[currentPoint[0]][currentPoint[1]].equalsIgnoreCase("GG")){
					System.out.println("Found the goal");
					goalFound = true;
					break;
				} else {

					int[] westPoint = {currentPoint[0], currentPoint[1] - 1, currentPoint[0], currentPoint[1], currentPoint[4] + 1};
					int[] northPoint = {currentPoint[0] - 1, currentPoint[1], currentPoint[0], currentPoint[1], currentPoint[4] + 1};
					int[] eastPoint = {currentPoint[0], currentPoint[1] + 1, currentPoint[0], currentPoint[1], currentPoint[4] + 1};
					int[] southPoint = {currentPoint[0] + 1, currentPoint[1], currentPoint[0], currentPoint[1], currentPoint[4] + 1};

					//Handle west point
					if(!(westPoint[1] < 0) && !frontierList.contains(westPoint) && (workingGraph[westPoint[0]][westPoint[1]].equalsIgnoreCase("[]") || workingGraph[westPoint[0]][westPoint[1]].equalsIgnoreCase("GG"))){
						if(workingGraph[westPoint[0]][westPoint[1]].equalsIgnoreCase("GG")){
							visitedList.add(currentPoint);
							currentPoint = westPoint;
							System.out.println("Found the goal");
							goalFound = true;
							break;
						}
						currentPointNumber++;
						workingGraph[westPoint[0]][westPoint[1]] = (currentPointNumber < 10) ? "0" + Integer.toString(currentPointNumber) : Integer.toString(currentPointNumber);
						frontierList.add(westPoint);
					}

					//Handle north point
					if(!(northPoint[0] < 0) && !frontierList.contains(northPoint) && (workingGraph[northPoint[0]][northPoint[1]].equalsIgnoreCase("[]") || workingGraph[northPoint[0]][northPoint[1]].equalsIgnoreCase("GG"))){
						if(workingGraph[northPoint[0]][northPoint[1]].equalsIgnoreCase("GG")){
							visitedList.add(currentPoint);
							currentPoint = northPoint;
							System.out.println("Found the goal");
							goalFound = true;
							break;
						}
						currentPointNumber++;
						workingGraph[northPoint[0]][northPoint[1]] = (currentPointNumber < 10) ? "0" + Integer.toString(currentPointNumber) : Integer.toString(currentPointNumber);
						frontierList.add(northPoint);
					}

					//Handle east point
					if(!(eastPoint[1] >= columns) && !frontierList.contains(eastPoint) && (workingGraph[eastPoint[0]][eastPoint[1]].equalsIgnoreCase("[]") || workingGraph[eastPoint[0]][eastPoint[1]].equalsIgnoreCase("GG"))){
						if(workingGraph[eastPoint[0]][eastPoint[1]].equalsIgnoreCase("GG")){
							visitedList.add(currentPoint);
							currentPoint = eastPoint;
							System.out.println("Found the goal");
							goalFound = true;
							break;
						}
						currentPointNumber++;
						workingGraph[eastPoint[0]][eastPoint[1]] = (currentPointNumber < 10) ? "0" + Integer.toString(currentPointNumber) : Integer.toString(currentPointNumber);
						frontierList.add(eastPoint);
					}

					//Handle south point
					if(!(southPoint[0] >= workingGraph.length) && !frontierList.contains(southPoint) && (workingGraph[southPoint[0]][southPoint[1]].equalsIgnoreCase("[]") || workingGraph[southPoint[0]][southPoint[1]].equalsIgnoreCase("GG"))){
						if(workingGraph[southPoint[0]][southPoint[1]].equalsIgnoreCase("GG")){
							visitedList.add(currentPoint);
							currentPoint = southPoint;
							System.out.println("Found the goal");
							goalFound = true;
							break;
						}
						currentPointNumber++;
						workingGraph[southPoint[0]][southPoint[1]] = (currentPointNumber < 10) ? "0" + Integer.toString(currentPointNumber) : Integer.toString(currentPointNumber);
						frontierList.add(southPoint);
					}
				}
				if(!visitedList.contains(currentPoint))
					visitedList.add(currentPoint);
				if(!frontierList.isEmpty()){
					currentPoint = frontierList.getLast();
					frontierList.removeLast();
				}
				Home.printGraph(workingGraph, columns);
			}
			if(!frontierList.isEmpty()){
				searchDepth = startingSearchDepth - currentPoint[4];
			}
		} while(!frontierList.isEmpty() && !goalFound);
		
		if(goalFound)
			displayFoundPath(visitedList, currentPoint);
		else
			System.out.println("No goal found");
		
		return workingGraph;
		
	}
        
        public void printIntArrayList(LinkedList<int[]> list){
                System.out.println("contents of list");
                for(int[] element : list){
                    System.out.println(element[0] + " " + element[1]);
                }
        }
		
		public void displayFoundPath(LinkedList<int[]> list, int[] goalPoint){
			
			int[] currentPoint = goalPoint;
			int distance = 0;
			
			while(currentPoint[2] >= 0 && currentPoint[3] >=0){
				System.out.print(currentPoint[0] + "," + currentPoint[1] + " > ");
				
				if(currentPoint[0] > currentPoint[2])
					distance += 3;
				if(currentPoint[0] < currentPoint[2])
					distance += 1;
				if(currentPoint[1] - currentPoint[3] != 0)
					distance += 2;
				
				for(int i = 0; i < list.size(); i++){
					int[] element = list.get(i);
					if(element[0] == currentPoint[2] && element[1] == currentPoint[3]){
						currentPoint = element;
						break;
					}
				}
			}
			
			System.out.println("\nDistance traveled: " + distance);
			
		}
	
}
