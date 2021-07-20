package mazesolver;

import java.util.PriorityQueue;
import java.util.Scanner;

public class AStar {
   
    private Node[][] maze;
    private PriorityQueue<Node> unvisitedNodes; // a priority queue for the nodes to be evaluated
    private boolean[][] visitedNodes; // set of nodes already visited
    
    private int sourceRow, sourceCol; // coordinations for starting node
    private int goalRow, goalCol; // coordinations for goal node
    
    public AStar(int width, int height, int startR, int startC, int destR, int destC, int[][] walls){
        maze = new Node[width][height]; // create a maze with the given number of rows and columns
        visitedNodes=new boolean[width][height]; // all nodes are initially unvisited
        unvisitedNodes= new PriorityQueue<Node>((Node n1, Node n2)->{
            return n1.finalCost<n2.finalCost ? -1 : n1.finalCost> n2.finalCost ? 1 :0;
        });
        
        startNode(startR,startC);
        endNode(destR,destC);
                
        // calculate initial heuristic
        for(int i=0; i<maze.length;i++){
            for(int j=0; j<maze[i].length;j++){
                maze[i][j]=new Node(i,j);
                maze[i][j].costHeuristic= Math.abs(i-destR)+Math.abs(j-destC); // formula to calculate initial heuristic estimations
            }
        }
        maze[sourceRow][sourceCol].finalCost=0;
        
        for(int i=0; i<walls.length;i++){
            addBlockOnCell(walls[i][0],walls[i][1]);
        }
    }
        
        public void addBlockOnCell(int i,int j){
            maze[i][j]=null;
        }
        
        public void startNode(int i,int j){
            sourceRow=i;
            sourceCol=j;
        }
        
        public void endNode(int i,int j){
            goalRow=i;
            goalCol=j;
        }
        
        public void updateCost(Node current, Node t, int cost){ // if we need to update cost
            if(t==null || visitedNodes[t.i][t.j]){
                return;
            }
            
            int tCost=t.costHeuristic+cost;
            boolean isOpen=unvisitedNodes.contains(t);
            
            if(!isOpen || tCost<t.finalCost){
                t.finalCost=tCost;
                t.parent=current;
                
                if(!isOpen){
                    unvisitedNodes.add(t);
                }
            }
        }
        
        public void process(){
            unvisitedNodes.add(maze[sourceRow][sourceCol]);
            Node current;
            while(true){
                current=unvisitedNodes.poll();
                if(current==null){
                    break;
                }
                visitedNodes[current.i][current.j]=true;
                if(current.equals(maze[goalRow][goalCol])){
                    return;
                }
                Node t;
                if(current.i-1>=0){
                    t=maze[current.i-1][current.j];
                    updateCost(current,t,current.finalCost + Constants.WEST_COST);
                }
                
                if(current.j-1>=0){
                    t=maze[current.i][current.j-1];
                    updateCost(current,t,current.finalCost + Constants.NORTH_COST);
                }
                
                if(current.j+1 < maze[0].length){
                        t=maze[current.i][current.j+1];
                        updateCost(current,t,current.finalCost + Constants.SOUTH_COST);
                    }
                
                if(current.i+1 < maze.length){
                        t=maze[current.i+1][current.j];
                        updateCost(current,t,current.finalCost + Constants.EAST_COST);
                    }
            }
        }
        
        public void display(){
            System.out.println("Maze:");
            
            for(int i=0; i<maze.length;i++){
                for(int j=0; j<maze[i].length;j++){
                    if(i==sourceRow && j==sourceCol){
                        System.out.print("S  "); //source
                    }
                    
                    else if(i==goalRow && j==goalCol){
                        System.out.print("GG  "); // goa;;
                    }
                    
                    else if(maze[i][j] !=null){
                        System.out.printf("%-3d",0); // open node
                    }
                    else{
                        System.out.print("##  "); // wall
                     }
                    
                }
                System.out.println();
            }
            System.out.println();
        }
        
        public void displayWeights(){
            System.out.println("\n Weights for nodes:");
            for(int i=0;i<maze.length;i++){
                for(int j=0;j<maze[i].length;j++){
                    if(maze[i][j]!=null)
                        System.out.printf("%-3d",maze[i][j].finalCost);
                    else
                        System.out.print("X "); // blocker
                }
                System.out.println();
            }
            System.out.println();
        }
        
        public void displaySolution(){
            if(visitedNodes[goalRow][goalCol]){
                System.out.println("Path: (backtracking) (from goal to start)");
                Node current=maze[goalRow][goalCol];
                System.out.print(current);
                maze[current.i][current.j].isSolution=true;
                
                while(current.parent !=null){
                    System.out.print("->"+current.parent);
                    maze[current.parent.i][current.parent.j].isSolution=true;
                    current=current.parent;
                }
                System.out.println("\n");
                
                System.out.println("Solved ");
                 for(int i=0; i<maze.length;i++){
                for(int j=0; j<maze[i].length;j++){
                    if(i==sourceRow && j==sourceCol){
                        System.out.print("00  "); //source
                    }
                    
                    else if(i==goalRow && j==goalCol){
                        System.out.print("GG  "); // goa;;
                    }
                    
                    else if(maze[i][j] !=null){
                        System.out.printf("%-3s",maze[i][j].isSolution ? maze[i][j].finalCost : "[]"); // open node
                    }
                    else{
                        System.out.print("##  "); // wall
                     }
                    
                }
                System.out.println();
            }
            System.out.println();
            }
            else{
                System.out.println("there is no possible shortest path");
            }
        }
        
}
