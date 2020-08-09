package cmd;

//package pathfinder;
//
//import java.util.ArrayList;
//
//public class Test {
//
//    public static void main(String[] args) {
//        int grid[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
//                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
//        
//        Algorithm algo = new Astar();
//        ArrayList<Tile> path = algo.start(grid, 1, 1, 8, 8);
//        
//        for(Tile j: path)
//            grid[j.getX()][j.getY()] = 9;
//
//        for(int x = 0; x < grid.length; x++) {
//            for(int y = 0; y < grid[0].length; y++)
//                System.out.print(grid[x][y]);
//            System.out.println();
//        }
//    }
//}
