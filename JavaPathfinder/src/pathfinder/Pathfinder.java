package pathfinder;

import java.util.ArrayList;

public class Pathfinder {

    public static void main(String[] args) {
        int grid[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        
        ArrayList<Node> path = Astar.start(grid, 0, 0, 7, 9);
        
        for(Node j: path)
            grid[j.getX()][j.getY()] = 9;

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[0].length; y++)
                System.out.print(grid[x][y]);
            System.out.println();
        }
    }
}
