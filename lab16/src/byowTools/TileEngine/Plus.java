package byowTools.TileEngine;

import java.util.HashSet;

public class Plus {

    public int size;
    private HashSet<Point> points;

    public Plus(int size) {
        this.size = size;
        this.points = calculatePlusPoints();
    }

    // break down the shape to a set of points expressed
    // by x and y coordinates
    private HashSet<Point> calculatePlusPoints() {
        HashSet<Point> points = new HashSet<>();
        for (int i = 0; i < 3 * size; i++) {
            for (int j = 0; j < 3 * size; j++) {
                // points of the vertical rectangular component
                if (i >= size && i <= size * 2 - 1) {
                    points.add(new Point(i, j));
                }
                // points of the horizontal rectangular component
                if (j >= size && j <= size * 2 - 1) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

    public HashSet<Point> getPlusPoints() {
        return this.points;
    }

    public void offsetPlus(int xVector, int yVector) {
        for (Point p : this.points) {
            p.setX(p.getX() + xVector);
            p.setY(p.getY() + yVector);
        }
    }
}
