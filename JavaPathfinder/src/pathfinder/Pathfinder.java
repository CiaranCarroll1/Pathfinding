package pathfinder;

import java.util.ArrayList;

public class Pathfinder {

    public static void main(String[] args) {
        int grid[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}};
        
        Algorithm algo = new Astar();
        ArrayList<Node> path = algo.start(grid, 4, 4, 9, 9);
        
        for(Node j: path)
            grid[j.getX()][j.getY()] = 9;

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[0].length; y++)
                System.out.print(grid[x][y]);
            System.out.println();
        }
    }
}
