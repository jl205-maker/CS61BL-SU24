package byowTools.TileEngine;

public class Point {
    public int xCoord;
    public int yCoord;

    public Point(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public void setX(int newX) {
        this.xCoord = newX;
    }

    public void setY(int newY) {
        this.yCoord = newY;
    }
}
