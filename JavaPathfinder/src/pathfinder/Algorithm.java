package pathfinder;

import java.util.ArrayList;

public interface Algorithm {
    public ArrayList<Tile> start(int grid[][], int startX, int startY, int endX, int endY);
}
