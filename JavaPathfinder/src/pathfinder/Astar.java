package pathfinder;

import java.util.ArrayList;

public class Astar {
    public static void start(int grid[][], int startX, int startY, int endX, int endY) {
 
        Node start_node = new Node(startX, startY);
        Node end_node = new Node(endX, endY);
        ArrayList<Node> open = new ArrayList();
        ArrayList<Node> closed = new ArrayList();
        open.add(start_node);
        Node current = start_node;
        
        boolean pathfound = false;
        
        while(!pathfound && !open.isEmpty()) {
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
                ArrayList<Node> neighbours = getNeighbours(current, grid);
                boolean flag = false;
                for(Node i: neighbours) {
                    flag = false;
                    for(Node j: closed) {
                        if(i.equals(j))
                            flag = true;
                            break;
                    }

                    if(!flag) {
                        for(Node h: open) {
                            if(i.equals(h) && (h.getG() > i.getG())) {
                                h.setH(getStraightDist(i, endX, endY));
                                h.setParent(current);
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

        for(Node j: closed)
            grid[j.getX()][j.getY()] = 9;

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[0].length; y++)
                System.out.print(grid[x][y]);
            System.out.println();
        }
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
