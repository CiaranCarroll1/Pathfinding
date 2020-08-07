package pathfinder;

public class Node {
    int x, y;
    double g, h;
    Node parent;
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {return x;} 
    public int getY() {return y;}
    
    public void setG(double g) {this.g = g;} 
    public double getG() {return g;}
    
    public void setH(double h) {this.h = h;} 
    public double getH() {return h;}
    
    public double getF() {return g + h;}
    
    public void setParent(Node parent) {this.parent = parent;}
    public Node getParent() {return parent;}
    
    public boolean equals(Node n) {
        if((getX() == n.getX()) && (getY() == n.getY()))
            return true;
        else
            return false;
    }
}
