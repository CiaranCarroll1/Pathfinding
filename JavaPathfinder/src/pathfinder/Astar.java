package pathfinder;

import java.util.ArrayList;

public class Astar {
    public static void start(int grid[][], int startX, int startY, int endX, int endY) {
        int cols = 10;
        int rows = 10;
        
        
        Node start_node = new Node(startX, startY);
        Node end_node = new Node(endX, endY);
        Node current = start_node;
        ArrayList<Node> open = new ArrayList();
        ArrayList<Node> closed = new ArrayList();
        open.add(start_node);
        
        boolean pathfound = false;
        boolean pathimpossible = false;
        
        while(!pathfound && !pathimpossible) {
            current = open.get(0);
            for(Node i: open) {
                if(current.getF() > i.getF()) 
                    current = i;
            }
            
            open.remove(current);
            closed.add(current);
            
            if(current.equals(end_node)) {
                pathfound = true;
            }
            else {
                ArrayList<Node> neighbours = getNeighbours(current, cols, rows);
                for(Node i: neighbours) {
                    boolean flag = false;
                    for(Node y: closed) {
                        if(i.equals(y))
                            flag = true;
                    }

                    if(!flag) {
                        for(Node j: open) {
                            if(i.equals(j) && (j.getG() > (current.getG() + getStraightDist(current, i.getX(), i.getY())))) {
                                j.setG(current.getG() + getStraightDist(current, i.getX(), i.getY()));
                                j.setH(getStraightDist(i, endX, endY));
                                j.setParent(current);
                                flag = true;
                            }
                        }
                    }
                    
                    if(!flag) {
                        i.setG(current.getG() + getStraightDist(current, i.getX(), i.getY()));
                        i.setH(getStraightDist(i, endX, endY));
                        i.setParent(current);
                        open.add(i);
                    }
                }
            }
        }

        for(Node j: closed)
            grid[j.getX()][j.getY()] = 1;

        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++)
                System.out.print(grid[x][y]);
            System.out.println();
        }
    }
    
    public static double getStraightDist(Node current, int goalX, int goalY) {
        int xdif = Math.abs(current.getX()-goalX);
        int ydif = Math.abs(current.getY()-goalY);
        return Math.sqrt((xdif*xdif)+(ydif*ydif));
    }
    
    public static ArrayList<Node> getNeighbours(Node current, int cols, int rows) {
        ArrayList<Node> neighbours = new ArrayList();
        int x = current.getX();
        int y = current.getY();
        
        for(int a = -1; a <= 1; a++) {
            for(int b = -1; b <= 1; b++) {
                int xbound = x + a;
                int ybound = y + b;
                if((xbound > -1 && xbound < cols) && (ybound > -1 && ybound < rows) && !(xbound == x && ybound == y)) {	//Not outside grid or current
                        Node neighbour = new Node(xbound, ybound);
                        neighbours.add(neighbour);
                }
            }
        }
        
        return neighbours;
    }
}
