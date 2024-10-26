package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Room implements Serializable {

    public int width;
    public int height;
    private Position basePoint; //represents the x and y coordinates of the lower left corner of a room
    private ArrayList<Position> range; //represents all positions occupied by a room
    private ArrayList<Position> expandedRange;
    public boolean lightOn;
    private Position lightPos;

    public ArrayList<Position> getRange() {
        return range;
    }

    /**a room is represented by a width, a height, and a basePoint.*/
    public Room(int width, int height, Position bp) {
        this.width = width;
        this.height = height;
        this.basePoint = bp;
        this.range = getOccupied();
        this.expandedRange = getExpandedRange();
        this.lightOn = false;

    }

    /**return a list of all positions occupied by a room inluding walls*/
    private ArrayList<Position> getExpandedRange() {
        ArrayList<Position> occupiedPosition = new ArrayList<>();
        for (int i = basePoint.getX() - 2; i < width + basePoint.getX() + 2; i++) {
            for (int j = basePoint.getY() - 2; j < height + basePoint.getY() + 2; j++) {
                occupiedPosition.add(new Position(i, j));
            }
        }
        return occupiedPosition;
    }

    /**return a list of all positions occupied by a room*/
    private ArrayList<Position> getOccupied() {
        ArrayList<Position> occupiedPosition = new ArrayList<>();
        for (int i = basePoint.getX(); i < width + basePoint.getX(); i++) {
            for (int j = basePoint.getY(); j < height + basePoint.getY(); j++) {
                occupiedPosition.add(new Position(i, j));
            }
        }
        return occupiedPosition;
    }
    /**return a list of all positions along the profile of a room*/
    public ArrayList<Position> getProfile() {

        ArrayList<Position> profile = new ArrayList<>();

        int xUpperBound = basePoint.getX() + width;
        int xLowerBound = basePoint.getX() - 1;
        int yUpperBound = basePoint.getY() + height;
        int yLowerBound = basePoint.getY() - 1;

        for (int x = xLowerBound; x <= xUpperBound; x++) {
            for (int y = yLowerBound; y <= yUpperBound; y++) {
                Position pos = new Position(x, y);
                if (!range.contains(pos)) {
                    profile.add(pos);
                }
            }
        }
        return profile;
    }

    /**check and reject overlapping room*/
    public boolean overlapsWith(Room other) {
        for (Position p : this.expandedRange) {
            if (other.expandedRange.contains(p)) {
                return true;
            }
        }
        return false;
    }

    /** get a random pos in Room */
    public Position getRandomPos(long seed) {
        Random random = new Random(seed);
        int num = random.nextInt(range.size());
        return range.get(num);
    }

    public Position setLightPos(long seed) {
        this.lightPos = getRandomPos(seed);
        return this.lightPos;
    }

}