import java.util.Random;

/** A class that represents a path via pursuit curves. */
public class Path {

    // TODO
    //instance variables
    private static Point curr = new Point();
    private static Point next = new Point();
    //constructor
    public Path(double x, double y) {
        next.setX(x);
        next.setY(y);
        curr.setX(0);
        curr.setY(0);
    }
    public double getCurrX() {
        return curr.getX();
    }
    public double getCurrY() {
        return curr.getY();
    }
    public double getNextX() {
        return next.getX();
    }
    public double getNextY() {
        return next.getY();
    }
    public Point getCurrentPoint() {
        return curr;
    }
    public void setCurrentPoint(Point newPoint) {
        curr.setX(newPoint.getX());
        curr.setY(newPoint.getY());
    }
    public void iterate(double dx, double dy) {
        curr.setX(next.getX());
        curr.setY(next.getY());
        double newX = next.getX() + dx;
        double newY = next.getY() + dy;
        next.setX(newX);
        next.setY(newY);
    }
}
