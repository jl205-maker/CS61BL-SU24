package PlusWorld;

import byowTools.TileEngine.*;

import java.util.HashSet;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public static void fillWithBlackTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void addPlus(TETile[][] tiles, Plus newPlus, String hatch) {
        HashSet<Point> colorPoints = newPlus.getPlusPoints();
        switch (hatch) {
            case "FLOWER":
                for (Point p : colorPoints) {
                    tiles[p.getX()][p.getY()] = Tileset.FLOWER;
                }
                break;
            case "GRASS":
                for (Point p : colorPoints) {
                    tiles[p.getX()][p.getY()] = Tileset.GRASS;
                }
                break;
            case "SAND":
                for (Point p : colorPoints) {
                    tiles[p.getX()][p.getY()] = Tileset.SAND;
                }
                break;
            case "MOUNTAIN":
                for (Point p : colorPoints) {
                    tiles[p.getX()][p.getY()] = Tileset.MOUNTAIN;
                }
                break;
            case "TREE":
                for (Point p : colorPoints) {
                    tiles[p.getX()][p.getY()] = Tileset.TREE;
                }
                break;
            case "WATER":
                for (Point p : colorPoints) {
                    tiles[p.getX()][p.getY()] = Tileset.WATER;
                }
                break;
        }

    }

    public static void main(String[] args) {
        //initialize a TE renderer
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        //create a tile array and fill it with black tiles
        TETile[][] canvas = new TETile[WIDTH][HEIGHT];
        fillWithBlackTiles(canvas);

        //instantiate a series of pluses of various sizes and arrange
        Plus p1 = new Plus(1);
        Plus p2 = new Plus(2);
        Plus p3 = new Plus(3);
        p1.offsetPlus(5, 5);
        p2.offsetPlus(10, 10);
        p3.offsetPlus( WIDTH/2, HEIGHT/2);

        //add the pluses to the canvas
        addPlus(canvas, p1, "WATER");
        addPlus(canvas, p2, "GRASS");
        addPlus(canvas, p3, "FLOWER");

        // draws the canvas to the screen
        ter.renderFrame(canvas);
    }
}
