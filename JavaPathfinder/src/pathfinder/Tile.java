package pathfinder;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    int x, y;
    double g, h;
    int status; // 0 = empty, 1 = start, 2 = end, 3 = wall, 4 = path, 5 closed
    Tile parent;
    
    private Rectangle border;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = 0;
        
        int TILE_SIZE = 40;
        border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        border.setFill(Color.WHITE);

        getChildren().addAll(border);

        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);
    }
    
    public int getX() {return x;} 
    public int getY() {return y;}
    
    public void setG(double g) {this.g = g;} 
    public double getG() {return g;}
    
    public void setH(double h) {this.h = h;} 
    public double getH() {return h;}
    
    public double getF() {return g + h;}
    
    public void setStatus(int status) {this.status = status; updateColor();} 
    public int getStatus() {return status;}
    
    public void setPrev(Tile parent) {this.parent = parent;}
    public Tile getPrev() {return parent;}
    
    public void updateColor() {
        if(status == 0)
            border.setFill(Color.WHITE);
        if(status == 1)
            border.setFill(Color.GREEN);
        if(status == 2)
            border.setFill(Color.RED);
        if(status == 3)
            border.setFill(Color.BLACK);
        if(status == 4)
            border.setFill(Color.YELLOW);
        if(status == 5)
            border.setFill(Color.ORANGE);
    }

    public boolean equals(Tile n) {
        if((getX() == n.getX()) && (getY() == n.getY()))
            return true;
        else
            return false;
    }
}
