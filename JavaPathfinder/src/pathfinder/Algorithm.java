package pathfinder;

import java.util.ArrayList;

public interface Algorithm {
    public ArrayList<Node> start(int grid[][], int startX, int startY, int endX, int endY);
}
