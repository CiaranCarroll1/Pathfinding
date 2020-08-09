package gui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Pathfinder extends Application {
    
    private static final int TILE_SIZE = 40;
    private static final int W = 1000;
    private static final int H = 800;

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private int startX, startY, endX, endY;
    private Button findPath, reset;
    private ToggleButton astar, dijkstra, start, end, wall;
    private boolean startSearch = false;
    private Scene scene;
    
    //Display grid and options
    private Parent createContent() {
        HBox root = new HBox();
        Pane pane = new Pane();
        root.setPrefSize(W, H);

        //Create Tiles
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = new Tile(x, y);
                tile.setOnMouseClicked(e -> updateTile(tile));
                tile.setOnDragDetected(e -> {
                    Dragboard db = tile.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("Wall");
                    db.setContent(content);
                    e.consume();
                 });
                tile.setOnDragOver(e -> updateTile(tile));

                grid[x][y] = tile;
                pane.getChildren().add(tile);
            }
        }
        
        //Create option buttons
        VBox options = new VBox(10);
        findPath = new Button("Find Path!");
        findPath.setOnAction(e ->
        {
            if(startX > -1 && endX > -1 && !startSearch) {
                startSearch = true;
                if(astar.isSelected())
                    runAstar();
            }
        });
        reset = new Button("Reset Grid!");
        reset.setOnAction(e ->
        {
            for (int x = 0; x < X_TILES; x++) {
                for (int y = 0; y < Y_TILES; y++) {
                    grid[x][y].setStatus(0);
                }
            }
            
            startX = -1;
            startY = -1;
            endX = -1;
            endY = -1;
            startSearch = false;
        });
        
        Label algoOptions = new Label("Algorithm:");
        ToggleGroup algoGroup = new ToggleGroup();
        astar = new RadioButton("A*");
        astar.setSelected(true);
        astar.setToggleGroup(algoGroup);
        dijkstra = new RadioButton("Dijkstra");
        dijkstra.setToggleGroup(algoGroup);
        
        Label tileOptions = new Label("Add Tile:");
        ToggleGroup tileGroup = new ToggleGroup();
        start = new RadioButton("Start Node");
        start.setToggleGroup(tileGroup);
        end = new RadioButton("End Node");
        end.setToggleGroup(tileGroup);
        wall = new RadioButton("Wall Node");
        wall.setToggleGroup(tileGroup);
        options.getChildren().addAll(findPath, algoOptions, astar, dijkstra, tileOptions, start, end, wall, reset);
        
        root.getChildren().addAll(options, pane);
        return root;
    }
    
    // Change tile status
    public void updateTile(Tile tile) {
        if (!startSearch) {
            if(start.isSelected()) {
                if(startX != -1) {
                    grid[startX][startY].setStatus(0);
                }

                startX = tile.getX();
                startY = tile.getY();
                grid[startX][startY].setStatus(1);
            }

            if(end.isSelected()) {
                if(endX != -1) {
                    grid[endX][endY].setStatus(0);
                }

                endX = tile.getX();
                endY = tile.getY();
                grid[endX][endY].setStatus(2);
            }

            int status = tile.getStatus();
            if(wall.isSelected() && status != 1 && status != 2) {
                grid[tile.getX()][tile.getY()].setStatus(3);
            }
        }
    }
    
    //A star search algorithm
    public void runAstar() {
        
        // Create start and end Tile
//        Tile start_Tile = grid[startX][startY];
//        Tile end_Tile = grid[endX][endY];

        Tile start_Tile = new Tile(startX, startY);
        Tile end_Tile = new Tile(endX, endY);
        
        //Initialize open,closed and path lists
        ArrayList<Tile> open = new ArrayList();
        ArrayList<Tile> closed = new ArrayList();
        
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
                while(grid[current.getX()][current.getY()].getStatus() != 1) {
                    if(grid[current.getX()][current.getY()].getStatus() !=2) {
                        grid[current.getX()][current.getY()].setStatus(4);
                    }

                    current = current.getPrev();
                }
                return;
            }

            //Get traversable neighbour Tiles
            ArrayList<Tile> neighbours = getNeighbours(current);
            
            boolean flag;
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
                    int xdif = current.getX()-endX;
                    int ydif = current.getY()-endY;
                    i.setH(Math.sqrt((xdif*xdif)+(ydif*ydif)));
                    open.add(i);
                }
            }
        }
    }
    
    public ArrayList<Tile> getNeighbours(Tile current) {
        ArrayList<Tile> neighbours = new ArrayList();
        int x = current.getX();
        int y = current.getY();

        for(int a = -1; a <= 1; a++) {
            for(int b = -1; b <= 1; b++) {
                int xbound = x + a;
                int ybound = y + b;
                if((xbound > -1 && xbound < X_TILES) && (ybound > -1 && ybound < Y_TILES) && !(xbound == x && ybound == y)) {
                    Tile neighbour = new Tile(xbound, ybound);
                    if(grid[xbound][ybound].getStatus() != 3) {
                        if(a * b == 0)
                            neighbour.setG(current.getG() + 1);
                        else
                            neighbour.setG(current.getG() + 1.4);

                        neighbour.setPrev(current);
                        neighbours.add(neighbour);
                    }
                }
            }
        }
        return neighbours;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent(), W + 200, H);
        stage.setTitle("Pathfinder");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {launch(args);}
}
