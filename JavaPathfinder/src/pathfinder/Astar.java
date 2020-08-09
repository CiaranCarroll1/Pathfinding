package pathfinder;

import java.util.ArrayList;

public class Astar implements Algorithm{
    @Override
    public ArrayList<Tile> start(int grid[][], int startX, int startY, int endX, int endY) {
        
        // Create start and end Tile
        Tile start_Tile = new Tile(startX, startY);
        Tile end_Tile = new Tile(endX, endY);
        
        //Initialize open,closed and path lists
        ArrayList<Tile> open = new ArrayList();
        ArrayList<Tile> closed = new ArrayList();
        ArrayList<Tile> path = new ArrayList();
        
        //Add start Tile to open
        open.add(start_Tile);
        Tile current = start_Tile;
        
        //Loop until end is found
        while(!open.isEmpty()) {
            //Get current Tile
            current = open.get(0);
            for(Tile i: open) {
                if(current.getF() > i.getF()) 
                    current = i;
            }
            
            //Remove current from open and add to closed
            open.remove(current);
            closed.add(current);
            
            //Found goal Tile
            if(current.equals(end_Tile)) {
                while(current != null) {
                    path.add(current);
                    current = current.getPrev();
                }
                return path;
            }
            else {
                //Get traversable neighbour Tiles
                ArrayList<Tile> neighbours = getNeighbours(current, grid);
                boolean flag = false;
                for(Tile i: neighbours) {
                    flag = false;
                    
                    //Skip to next neighbour if in closed list
                    for(Tile j: closed) {
                        if(i.equals(j)) {
                            flag = true;
                            break;
                        }
                    }

                    //Skip to next neighbour if in open list and neighbour G cost is higher
                    if(!flag) {
                        for(Tile h: open) {
                            if(i.equals(h) && (i.getG() > h.getG())) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    
                    //Set H cost and parent Tile
                    if(!flag) {
                        i.setH(getStraightDist(i, endX, endY));
                        i.setPrev(current);
                        open.add(i);
                    }
                }
            }
        }
        return path;
    }
    
    //Get straight line distance to goal Tile
    public static double getStraightDist(Tile current, int goalX, int goalY) {
        int xdif = Math.abs(current.getX()-goalX);
        int ydif = Math.abs(current.getY()-goalY);
        return Math.sqrt((xdif*xdif)+(ydif*ydif));
    }
    
    //Get traversable neighbour Tiles
    public static ArrayList<Tile> getNeighbours(Tile current, int grid[][]) {
        int cols = grid.length;
        int rows = grid[0].length;
        ArrayList<Tile> neighbours = new ArrayList();
        int x = current.getX();
        int y = current.getY();
        
        for(int a = -1; a <= 1; a++) {
            for(int b = -1; b <= 1; b++) {
                int xbound = x + a;
                int ybound = y + b;
                if((xbound > -1 && xbound < cols) && (ybound > -1 && ybound < rows) && !(xbound == x && ybound == y) && grid[xbound][ybound] != 1) {	//Not outside grid or current
                        Tile neighbour = new Tile(xbound, ybound);
                        neighbour.setG(current.getG() + getStraightDist(current, xbound, ybound));
                        neighbours.add(neighbour);
                }
            }
        }
        
        return neighbours;
    }
}
