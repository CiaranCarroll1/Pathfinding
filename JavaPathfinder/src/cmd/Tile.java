package cmd;

public class Tile{
    int x, y;
    double g, h;
    int status; // 0 = empty, 1 = start, 2 = end, 3 = wall, 4 = path, 5 closed
    Tile parent;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = 0;
    }
    
    public int getX() {return x;} 
    public int getY() {return y;}
    
    public void setG(double g) {this.g = g;} 
    public double getG() {return g;}
    
    public void setH(double h) {this.h = h;} 
    public double getH() {return h;}
    
    public double getF() {return g + h;}
    
    public void setPrev(Tile parent) {this.parent = parent;}
    public Tile getPrev() {return parent;}

    public boolean equals(Tile n) {
        if((getX() == n.getX()) && (getY() == n.getY()))
            return true;
        else
            return false;
    }
}
