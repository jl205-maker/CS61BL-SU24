package core;

import utils.RandomUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Corridor implements Serializable {
    /**a similar getOccupied method*/

    private ArrayList<Position> range;

    public ArrayList<Position> getRange() {
        return range;
    }

    public Corridor(Position pos1, Position pos2, long seed) {
        Random random = new Random(seed);
        range = new ArrayList<>();
        long x = random.nextInt();
        generateL(pos1, pos2, seed);


    }

    public void generateL(Position pos1, Position pos2, long seed) {

        Random random = new Random(seed);

        if (pos1.getX() > pos2.getX()) {
            Position temp = pos1;
            pos1 = pos2;
            pos2 = temp;
        }

        if (pos1.getY() <= pos2.getY()) {

            int x0 = RandomUtils.uniform(random, pos1.getX(), pos2.getX() + 1);
            int y0 = RandomUtils.uniform(random, pos1.getY(), pos2.getY() + 1);

            if (random.nextInt() % 2 == 0) {
                for (int x = pos1.getX(); x <= x0; x++) {
                    range.add(new Position(x, pos1.getY()));
                }
                for (int y = pos1.getY(); y <= y0; y++) {
                    range.add(new Position(x0, y));
                }
                for (int x = x0; x <= pos2.getX(); x++) {
                    range.add(new Position(x, y0));
                }
                for (int y = y0; y <= pos2.getY(); y++) {
                    range.add(new Position(pos2.getX(), y));
                }
            } else {
                for (int x = pos1.getX(); x <= x0; x++) {
                    range.add(new Position(x, y0));
                }
                for (int y = pos1.getY(); y <= y0; y++) {
                    range.add(new Position(pos1.getX(), y));
                }
                for (int x = x0; x <= pos2.getX(); x++) {
                    range.add(new Position(x, pos2.getY()));
                }
                for (int y = y0; y <= pos2.getY(); y++) {
                    range.add(new Position(x0, y));
                }
            }
        } else {

            int x0 = RandomUtils.uniform(random, pos1.getX(), pos2.getX() + 1);
            int y0 = RandomUtils.uniform(random, pos2.getY(), pos1.getY() + 1);

            if (random.nextInt() % 2 == 0) {
                for (int x = pos1.getX(); x <= x0; x++) {
                    range.add(new Position(x, y0));
                }
                for (int y = pos1.getY(); y >= y0; y--) {
                    range.add(new Position(pos1.getX(), y));
                }
                for (int x = x0; x <= pos2.getX(); x++) {
                    range.add(new Position(x, pos2.getY()));
                }
                for (int y = y0; y >= pos2.getY(); y--) {
                    range.add(new Position(x0, y));
                }
            } else {
                for (int x = pos1.getX(); x <= x0; x++) {
                    range.add(new Position(x, pos1.getY()));
                }
                for (int y = pos1.getY(); y >= y0; y--) {
                    range.add(new Position(x0, y));
                }
                for (int x = x0; x <= pos2.getX(); x++) {
                    range.add(new Position(x, y0));
                }
                for (int y = y0; y >= pos2.getY(); y--) {
                    range.add(new Position(pos2.getX(), y));
                }
            }
        }

    }

    public void generateZ(Position pos1, Position pos2) {

    }



}