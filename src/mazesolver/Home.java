package mazesolver;
import java.util.Scanner;

public class Home {

	private static String[][] initialGraph = {
			{"[]", "00", "[]", "[]", "[]", "[]"},
			{"[]", "##", "##", "##", "##", "[]"},
			{"[]", "##", "[]", "GG", "##", "[]"},
			{"[]", "##", "[]", "[]", "[]", "[]"},
			{"[]", "[]", "[]", "[]", "[]", "[]"}
		};
	        
	        private String[][] expectedIDGraph = {
			{"01", "00", "02", "03", "04", "05"},
			{"[]", "##", "##", "##", "##", "06"},
			{"[]", "##", "[]", "GG", "##", "07"},
			{"[]", "##", "[]", "13", "09", "08"},
			{"[]", "[]", "{}", "12", "11", "10"}
		};

	        public static void main(String[] args){
	            Scanner keyboardin = new Scanner(System.in);
	            
	            System.out.println("Select an option");
	            System.out.println("1-Iterative deepening search");
	            System.out.println("2-A* search");
	            System.out.println("3-Quit");
	            int choice=keyboardin.nextInt();
	            
	            if (choice == 1){
	                IterativeDeepening IDSearch = new IterativeDeepening();
	                System.out.println("Enter a depth limit");
	                printGraph(IDSearch.search(initialGraph, 6, keyboardin.nextInt()), 6);
	            }
	            else if(choice==2){
	            AStar Astar=new AStar(5,6,0,1,2,3,new int[][]{{1,1},{1,2},{1,3},{1,4},{2,1},{2,4},{3,1}});
	            Astar.display();
	            Astar.process();
	            Astar.displayWeights();
	            Astar.displaySolution();
	            }
	            else{
	                System.out.println("Quitting...");
	            }
	        }
	        
	        public static void printGraph(String[][] graph, int columns){
	            System.out.println("\n");
	            for(int i = 0; i < graph.length; i++){
	                for(int j = 0; j < columns; j++){
	                    System.out.print(graph[i][j] + "  ");
	                }
	                System.out.print("\n");
	            }
	            
	        }

}
