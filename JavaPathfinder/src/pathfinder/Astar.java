package pathfinder;

import java.util.ArrayList;

public class Astar {
    public static ArrayList<Node> start(int grid[][], int startX, int startY, int endX, int endY) {
        
        // Create start and end node
        Node start_node = new Node(startX, startY);
        Node end_node = new Node(endX, endY);
        
        //Initialize open,closed and path lists
        ArrayList<Node> open = new ArrayList();
        ArrayList<Node> closed = new ArrayList();
        ArrayList<Node> path = new ArrayList();
        
        //Add start node to open
        open.add(start_node);
        Node current = start_node;
        
        //Loop until end is found
        while(!open.isEmpty()) {
            //Get current node
            current = open.get(0);
            for(Node i: open) {
                if(current.getF() > i.getF()) 
                    current = i;
            }
            
            //Remove current from open and add to closed
            open.remove(current);
            closed.add(current);
            
            //Found goal node
            if(current.equals(end_node)) {
                while(current != null) {
                    path.add(current);
                    current = current.getParent();
                }
                return path;
            }
            else {
                ArrayList<Node> neighbours = getNeighbours(current, grid);
                boolean flag = false;
                for(Node i: neighbours) {
                    flag = false;
                    for(Node j: closed) {
                        if(i.equals(j)) {
                            flag = true;
                            break;
                        }
                    }

                    if(!flag) {
                        for(Node h: open) {
                            if(i.equals(h) && (i.getG() > h.getG())) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    
                    if(!flag) {
                        i.setH(getStraightDist(i, endX, endY));
                        i.setParent(current);
                        open.add(i);
                    }
                }
            }
        }
        return path;
    }
    
    public static double getStraightDist(Node current, int goalX, int goalY) {
        int xdif = Math.abs(current.getX()-goalX);
        int ydif = Math.abs(current.getY()-goalY);
        return Math.sqrt((xdif*xdif)+(ydif*ydif));
    }
    
    public static ArrayList<Node> getNeighbours(Node current, int grid[][]) {
        int cols = grid.length;
        int rows = grid[0].length;
        ArrayList<Node> neighbours = new ArrayList();
        int x = current.getX();
        int y = current.getY();
        
        for(int a = -1; a <= 1; a++) {
            for(int b = -1; b <= 1; b++) {
                int xbound = x + a;
                int ybound = y + b;
                if((xbound > -1 && xbound < cols) && (ybound > -1 && ybound < rows) && !(xbound == x && ybound == y) && grid[xbound][ybound] != 1) {	//Not outside grid or current
                        Node neighbour = new Node(xbound, ybound);
                        neighbour.setG(current.getG() + getStraightDist(current, xbound, ybound));
                        neighbours.add(neighbour);
                }
            }
        }
        
        return neighbours;
    }
}
