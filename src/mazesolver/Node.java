package mazesolver;

public class Node {
	public int i,j; // coordinates in the grid
    public Node parent;
    public int costHeuristic; //estimation manhattan distance
    public int finalCost; // G+H, which is the addition of the cost of the path from the start node to current, and H the heuristic estimation
    public boolean isSolution; // check if the node is part of the final path
    
    public Node(int x, int y){
        this.i=x;
        this.j=y;
    }
    
    @Override
    public String toString(){
        return"["+i+", "+j+"]";
    }
}


