package gui;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pathfinder.Tile;

public class Pathfinder extends Application {
    
    private static final int TILE_SIZE = 40;
    private static final int W = 800;
    private static final int H = 600;

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private int startX, startY, endX, endY;
    private Button findPath, reset;
    private ToggleButton start, end, wall;
    private boolean startSearch = false;
    private Scene scene;
    
    private Parent createContent() {
        HBox root = new HBox();
        Pane pane = new Pane();
        root.setPrefSize(W, H);

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y);
                tile.setOnMouseClicked(e -> updateTile(tile));

                grid[x][y] = tile;
                pane.getChildren().add(tile);
            }
        }
        
        VBox options = new VBox(10);
        findPath = new Button("Find Path");
        findPath.setOnAction(event ->
        {
            if(startX > -1 && endX > -1) {
                startSearch = true;
                astar();
            }
        });
        reset = new Button("Reset");
        reset.setOnAction(event ->
        {
            for (int y = 0; y < Y_TILES; y++) {
                for (int x = 0; x < X_TILES; x++) {
                    Tile tile = grid[x][y];
                    tile.setStatus(0);
                }
            }
            
            startX = -1;
            startY = -1;
            endX = -1;
            endY = -1;
            startSearch = false;
        });
        
        ToggleGroup toggleGroup = new ToggleGroup();
        start = new ToggleButton("Start");
        start.setToggleGroup(toggleGroup);
        end = new ToggleButton("End");
        end.setToggleGroup(toggleGroup);
        wall = new ToggleButton("Wall");
        wall.setToggleGroup(toggleGroup);
        options.getChildren().addAll(findPath,start, end, wall, reset);
        
        root.getChildren().addAll(options, pane);
        return root;
    }
    
    // Check if start/end selected
    public void updateTile(Tile tile) {
        if (!startSearch) {
            if(start.isSelected()) {
                if(startX != -1) {
                    grid[startX][startY].setStatus(0);
                }

                tile.setStatus(1);
                startX = tile.getX();
                startY = tile.getY();
            }

            if(end.isSelected()) {
                if(endX != -1) {
                    grid[endX][endY].setStatus(0);
                }

                tile.setStatus(2);
                endX = tile.getX();
                endY = tile.getY();
            }

            int status = tile.getStatus();
            if(wall.isSelected() && status != 1 && status != 2) {
                tile.setStatus(3);
            }
        }
    }
    
    public void astar() {
        
        // Create start and end Tile
        Tile start_Tile = grid[startX][startY];
        Tile end_Tile = grid[endX][endY];
        
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
            
//            if(current.getStatus() != 1 && current.getStatus() !=2)
//                grid[current.getX()][current.getY()].setStatus(5);
            
            //Remove current from open and add to closed
            open.remove(current);
            closed.add(current);
            
            //Found goal Tile
            if(current.equals(end_Tile)) {
                System.out.println("Found!");
                while(current.getStatus() != 1) {

                    if(current.getStatus() !=2)
                        grid[current.getX()][current.getY()].setStatus(4);

                    current = current.getPrev();
                }
                return;
            }
            else {
                //Get traversable neighbour Tiles
                ArrayList<Tile> neighbours = getNeighbours(current);
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
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Pathfinder.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return;
    }
    
    //Get straight line distance to goal Tile
    public double getStraightDist(Tile current, int goalX, int goalY) {
        int xdif = Math.abs(current.getX()-goalX);
        int ydif = Math.abs(current.getY()-goalY);
        return Math.sqrt((xdif*xdif)+(ydif*ydif));
    }
    
    //Get traversable neighbour Tiles
    public ArrayList<Tile> getNeighbours(Tile current) {
        ArrayList<Tile> neighbours = new ArrayList();
        int x = current.getX();
        int y = current.getY();
        
        for(int a = -1; a <= 1; a++) {
            for(int b = -1; b <= 1; b++) {
                int xbound = x + a;
                int ybound = y + b;
                if((xbound > -1 && xbound < X_TILES) && (ybound > -1 && ybound < Y_TILES) && !(xbound == x && ybound == y)) {	//Not outside grid or current
                    Tile neighbour = grid[xbound][ybound];
                    if(neighbour.getStatus() != 3) {
                        neighbour.setG(current.getG() + getStraightDist(current, xbound, ybound));
                        neighbours.add(grid[xbound][ybound]);
                    }
                }
            }
        }
        
        return neighbours;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());
        stage.setTitle("Pathfinder");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {launch(args);}
}
