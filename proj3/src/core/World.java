package core;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class World implements Serializable {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final int MAX_ROOM_HEIGHT = 16;
    private static final int MAX_ROOM_WIDTH = 16;
    private static final int ROOM_COUNT = 9;
    private ArrayList<Room> rooms;
    private HashSet<Corridor> corridors;
    private TETile[][] world;
    private Avatar avatar;
    private TETile currAvatarTile;
    private ArrayList<Position> roomPositions;
    public HashMap<Position, Room> roomMap;
    public boolean isDark;

    public World() {
        return;
    }

    public World(TETile[][] world, Avatar avatar, ArrayList<Position> roomPositions, HashMap<Position, Room> roomMap) {
        this.world = world;
        this.avatar = avatar;
        this.roomMap = roomMap;
        this.roomPositions = roomPositions;
        this.isDark = true;

    }

    public ArrayList<Position> getRoomPositions() {
        return roomPositions;
    }

    public HashMap<Position, Room> getRoomMap() {
        return roomMap;
    }


    private long seed;

    public String DEFAULT_lANGUAGE = "English";
    public String DEFAULT_DISPLAY = "Dark";
    private static final Font fontSmall = new Font("Monaco", Font.PLAIN, 20); //top bar setting font
    private static final Font fontTitle = new Font("Broadway", Font.BOLD, 60); //game entry name font
    private static final Font fontMedium = new Font("Broadway", Font.PLAIN, 40); //menu and prompt msg font


    public void removeAvatar() {
        world[getAvatarPos().getX()][getAvatarPos().getY()] = avatar.getAvatarTile();
    }

    public void showAvatar() {
        world[getAvatarPos().getX()][getAvatarPos().getY()] = currAvatarTile;
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public TETile getCurrAvatarTile() {
        return currAvatarTile;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Position getAvatarPos() {
        return avatar.getAvatarPos();
    }

    public TETile getAvatarTile() {
        return avatar.getAvatarTile();
    }

    public TETile[][] generateRandomWorld(int width, int height, long seed) {
        this.seed = seed;
        //initialize an empty world of the specified dimension
        world = new TETile[width][height];

        //fill the world with black tiles
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //generate rooms
        rooms = new ArrayList<>();
        corridors = new HashSet<>();
        roomPositions = new ArrayList<>();
        roomMap = new HashMap<>();

        generateRooms(seed);
        generateCorridor(seed);
        //render the rooms
        for (Room r : rooms) {
            for (Position p : r.getRange()) {
                world[p.getX()][p.getY()] = Tileset.FLOOR;
                roomPositions.add(p);
            }
        }

        //generate corridors
        for (Corridor corridor : corridors) {
            for (Position p : corridor.getRange()) {
                world[p.getX()][p.getY()] = Tileset.FLOOR;
            }
        }

        for (Corridor corridor : corridors) {
            for (Position p : corridor.getRange()) {
                if (p.getX() > 0 && p.getX() < world.length - 1 && p.getY() > 0 && p.getY() < world[0].length - 1) {
                    if (!world[p.getX()][p.getY() + 1].equals(Tileset.FLOOR)) {
                        world[p.getX()][p.getY() + 1] = Tileset.WALL;
                    }
                    if (!world[p.getX() + 1][p.getY() + 1].equals(Tileset.FLOOR)) {
                        world[p.getX() + 1][p.getY() + 1] = Tileset.WALL;
                    }
                    if (!world[p.getX() + 1][p.getY()].equals(Tileset.FLOOR)) {
                        world[p.getX() + 1][p.getY()] = Tileset.WALL;
                    }
                    if (!world[p.getX() + 1][p.getY() - 1].equals(Tileset.FLOOR)) {
                        world[p.getX() + 1][p.getY() - 1] = Tileset.WALL;
                    }
                    if (!world[p.getX()][p.getY() - 1].equals(Tileset.FLOOR)) {
                        world[p.getX()][p.getY() - 1] = Tileset.WALL;
                    }
                    if (!world[p.getX() - 1][p.getY() - 1].equals(Tileset.FLOOR)) {
                        world[p.getX() - 1][p.getY() - 1] = Tileset.WALL;
                    }
                    if (!world[p.getX() - 1][p.getY()].equals(Tileset.FLOOR)) {
                        world[p.getX() - 1][p.getY()] = Tileset.WALL;
                    }
                    if (!world[p.getX() - 1][p.getY() + 1].equals(Tileset.FLOOR)) {
                        world[p.getX() - 1][p.getY() + 1] = Tileset.WALL;
                    }
                }
            }
        }
        //put walls
        for (Position p : getWallPositions()) {
            world[p.getX()][p.getY()] = Tileset.WALL;
        }

        for (Room r : rooms) {
            Position light = r.setLightPos(seed);
            roomMap.put(light, r);
                world[light.getX()][light.getY()] = Tileset.LIGHTBULB;
        }

        generateAvatar(seed);
        //System.out.println(avatar.getAvatarTile().id());

        return world;
    }

    public World changeWorldToLightMode() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (this.getWorld()[x][y] == Tileset.NOTHING){
                    this.getWorld()[x][y] = Tileset.WHITETILE;
                } else if (this.getWorld()[x][y] == Tileset.FLOOR) {
                    this.getWorld()[x][y] = Tileset.FLOORLIGHT;
                } else if (this.getWorld()[x][y] == Tileset.WALL) {
                    this.getWorld()[x][y] = Tileset.WALLLIGHT;
                }
            }
        }
        this.isDark = false;
        return this;
    }

    private void generateCorridor(long seed) {
        Random random = new Random(seed);
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            int j = (i + 1) / ROOM_COUNT;
            Room room1 = rooms.get(j);
            Position pos1 = room.getRandomPos(seed);
            Position pos2 = room1.getRandomPos(seed);
            Corridor corridor = new Corridor(pos1, pos2, seed);
            corridors.add(corridor);
        }
    }

    /** Generate a series of rooms at n random positions, and add to the room collection */
    private void generateRooms(long seed) {
        Random random = new Random(seed);
        while (rooms.size() < ROOM_COUNT) {
            int w = random.nextInt(3, MAX_ROOM_WIDTH);
            int h = random.nextInt(3, MAX_ROOM_HEIGHT);
            int bpX = random.nextInt(2, WIDTH - w - 2);
            int bpY = random.nextInt(2, HEIGHT - h - 2);
            Position bp = new Position(bpX, bpY);
            Room room = new Room(w, h, bp);
            if (isValidRoom(room)) {
                rooms.add(room);
            }
        }
    }

    /**get positions to put walls for rooms*/
    private HashSet<Position> getWallPositions() {
        HashSet<Position> validWallPos = new HashSet<>();
        for (Room r : rooms) {
            for (Position p : r.getProfile()) {
                if (isEmpty(p)) {
                    validWallPos.add(p);
                }
            }
        }
        return validWallPos;
    }

    /**generate a series of corridors, and add to the corridor collection*/

    /**get positions to put walls for corridors*/
    //private HashSet<Position> getCorridorBorderPositions(HashSet<Corridor> corridors) {return null;}

    /**check if a given location is empty*/
    private boolean isEmpty(Position p) {
        return world[p.getX()][p.getY()].equals(Tileset.NOTHING);
    }


    private boolean isValidRoom(Room room) {
        for (Room r : rooms) {
            if (room.overlapsWith(r)) {
                return false;
            }
        }
        return true;
    }


    public void generateAvatar(long seed) {
        Random random = new Random(seed);
        int i = random.nextInt(0, ROOM_COUNT);
        Position pos = rooms.get(i).getRandomPos(seed);
        currAvatarTile = Tileset.avatars.get(random.nextInt(Tileset.avatars.size())); // Store the generated avatar tile
        avatar = new Avatar(pos, world[pos.getX()][pos.getY()]);
        world[pos.getX()][pos.getY()] = currAvatarTile;
    }


    public void moveAvatar(char instruction) {
        Position avatarPos = avatar.getAvatarPos();
        if (instruction == 'w' || instruction == 'W') {
            if (!world[avatarPos.getX()][avatarPos.getY() + 1].equals(Tileset.WALL)) {
                world[avatarPos.getX()][avatarPos.getY()] = avatar.getAvatarTile();
                avatarPos = new Position(avatarPos.getX(), avatarPos.getY() + 1);
                avatar = new Avatar(avatarPos, world[avatarPos.getX()][avatarPos.getY()]);
                world[avatarPos.getX()][avatarPos.getY()] = currAvatarTile;
            }
        } else if (instruction == 's' || instruction == 'S') {
            if (!world[avatarPos.getX()][avatarPos.getY() - 1].equals(Tileset.WALL)) {
                world[avatarPos.getX()][avatarPos.getY()] = avatar.getAvatarTile();
                avatarPos = new Position(avatarPos.getX(), avatarPos.getY() - 1);
                avatar = new Avatar(avatarPos, world[avatarPos.getX()][avatarPos.getY()]);
                world[avatarPos.getX()][avatarPos.getY()] = currAvatarTile;
            }
        } else if (instruction == 'a' || instruction == 'A') {
            if (!world[avatarPos.getX() - 1][avatarPos.getY()].equals(Tileset.WALL)) {
                world[avatarPos.getX()][avatarPos.getY()] = avatar.getAvatarTile();
                avatarPos = new Position(avatarPos.getX() - 1, avatarPos.getY());
                avatar = new Avatar(avatarPos, world[avatarPos.getX()][avatarPos.getY()]);
                world[avatarPos.getX()][avatarPos.getY()] = currAvatarTile;
            }
        } else if (instruction == 'd' || instruction == 'D') {
            if (!world[avatarPos.getX() + 1][avatarPos.getY()].equals(Tileset.WALL)) {
                world[avatarPos.getX()][avatarPos.getY()] = avatar.getAvatarTile();
                avatarPos = new Position(avatarPos.getX() + 1, avatarPos.getY());
                avatar = new Avatar(avatarPos, world[avatarPos.getX()][avatarPos.getY()]);
                world[avatarPos.getX()][avatarPos.getY()] = currAvatarTile;
            }
        }
    }


    private ArrayList<Position> surroundings(Position pos, int round) {
        ArrayList<Position> list = new ArrayList<>();

        for (Position p : roomMap.get(pos).getRange()) {
            if (Math.max(Math.abs(p.getX() - pos.getX()), Math.abs(p.getY() - pos.getY())) == round) {
                list.add(p);
            }
        }

        return list;
    }

    public void turnOnLight(Position pos) {
        int round = 0;
        Room currRoom = roomMap.get(pos);
        currRoom.lightOn = true;
        while (round < Math.max(currRoom.width, currRoom.height)) {
            if (this.isDark) {
                if (round == 0) {
                    for (Position p : surroundings(pos, 0)) {
                        if (roomPositions.contains(p)) {
                            avatar = new Avatar(p, Tileset.LIGHTFLOORA);
                        }
                    }
                } else {
                    for (Position p : surroundings(pos, round)) {
                        if (roomPositions.contains(p)) {
                            world[p.getX()][p.getY()] = Tileset.blueGradients.get(round);
                        }
                    }
                }

            } else {
                if (round == 0) {
                    for (Position p : surroundings(pos, 0)) {
                        if (roomPositions.contains(p)) {
                            avatar = new Avatar(p, Tileset.LIGHTFLOORA);
                        }
                    }
                } else {
                    for (Position p : surroundings(pos, round)) {
                        if (roomPositions.contains(p)) {
                            world[p.getX()][p.getY()] = Tileset.yellowGradients.get(round);
                        }
                    }
                }

            }
            round++;
        }
    }

    public void turnOffLight(Position pos) {
        avatar = new Avatar(pos, Tileset.LIGHTBULB);
        for (Position p : roomMap.get(pos).getRange()) {
            if (p.equals(pos)) {
                continue;
            }
            if (isDark) {
                world[p.getX()][p.getY()] = Tileset.FLOOR;
            } else {
                world[p.getX()][p.getY()] = Tileset.FLOORLIGHT;
            }
        }
        Room currRoom = roomMap.get(pos);
        currRoom.lightOn = false;
    }

    public Boolean getDisplaySetting() {
        return isDark;
    }

    /**default entry page*/
    public void drawDefaultEnterPage() {
        /* Draws the new game entry page of the game.*/
        /* TITLE.*/
        isDark = true;
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.MAGENTA);

        StdDraw.setFont(fontTitle);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 8, "BUILD YOUR OWN WORLD!");

        /* Game options.*/
        StdDraw.setFont(fontMedium);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH / 2, 6, "Quit (Q)");
        StdDraw.text(this.WIDTH / 2, 12, "Load World (L)");
        StdDraw.text(this.WIDTH / 2, 18, "New World (N)");

        /* Setting options.*/
        StdDraw.setFont(fontSmall);
        StdDraw.line(0, this.HEIGHT + 1, this.WIDTH, this.HEIGHT + 1);
        StdDraw.textLeft(1, this.HEIGHT + 2, "Language: " + DEFAULT_lANGUAGE);
        StdDraw.textRight(this.WIDTH - 1, this.HEIGHT + 2, "Display Mode: " + DEFAULT_DISPLAY + " (P)");

        StdDraw.show();
    }
    /**light mode entry page*/
    public void drawLightModeEnterPage() {
        /* Draws the new game entry page of the game.*/
        /* TITLE.*/
        isDark = false;
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLUE);

        StdDraw.setFont(fontTitle);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 8, "BUILD YOUR OWN WORLD!");

        /* Game options.*/
        StdDraw.setFont(fontMedium);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, 6, "Quit (Q)");
        StdDraw.text(this.WIDTH / 2, 12, "Load World (L)");
        StdDraw.text(this.WIDTH / 2, 18, "New World (N)");

        /* Setting options.*/
        StdDraw.setFont(fontSmall);
        StdDraw.line(0, this.HEIGHT + 1, this.WIDTH, this.HEIGHT + 1);
        StdDraw.textLeft(1, this.HEIGHT + 2, "Language: " + DEFAULT_lANGUAGE);
        StdDraw.textRight(this.WIDTH - 1, this.HEIGHT + 2, "Display Mode: Light (P)");

        StdDraw.show();
    }
    public void toggleEntryPageDisplay() {
        isDark = !isDark;
        if (isDark) {
            drawDefaultEnterPage();
        } else {
            drawLightModeEnterPage();
        }
    }

    public void drawSeedPageDark(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        /* Setting options.*/
        StdDraw.setFont(fontSmall);
        StdDraw.line(0, this.HEIGHT + 1, this.WIDTH, this.HEIGHT + 1);
        StdDraw.text(WIDTH/2, this.HEIGHT + 2, "Building a new world....");
        StdDraw.textLeft(1, this.HEIGHT + 2, "Language: " + DEFAULT_lANGUAGE);
        StdDraw.textRight(this.WIDTH - 1, this.HEIGHT + 2, "Display Mode: " + DEFAULT_DISPLAY + " (P)");

        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH/2, HEIGHT/2 + 10, "Please enter a seed:");
        StdDraw.setPenColor(Color.MAGENTA);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);

        StdDraw.show();
    }
    public void drawSeedPageLight(String s) {
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);

        /* Setting options.*/
        StdDraw.setFont(fontSmall);
        StdDraw.line(0, this.HEIGHT + 1, this.WIDTH, this.HEIGHT + 1);
        StdDraw.text(WIDTH/2, this.HEIGHT + 2, "Building a new world....");
        StdDraw.textLeft(1, this.HEIGHT + 2, "Language: " + DEFAULT_lANGUAGE);
        StdDraw.textRight(this.WIDTH - 1, this.HEIGHT + 2, "Display Mode: Light (P)");

        StdDraw.setFont(fontMedium);
        StdDraw.text(WIDTH/2, HEIGHT/2 + 10, "Please enter a seed:");
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);

        StdDraw.show();
    }
    public void toggleSeedPageDisplay(String s) {
        isDark = !isDark;
        if (isDark) {
            drawSeedPageDark(s);
        } else {
            drawSeedPageLight(s);
        }
    }

    public String readSeed() {
        StringBuilder seedInput = new StringBuilder();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = StdDraw.nextKeyTyped();
                if (temp == 'p' || temp == 'P') {
                    toggleSeedPageDisplay(seedInput.toString());
                    continue;
                }
                if (temp == 's' || temp == 'S') {
                    seedInput.append('s');
                    break;
                }
                if (Character.isDigit(temp)) {
                    seedInput.append(temp);
                } else if (temp == 'q' || temp == 'Q') {
                    System.exit(0);
                }
                if (this.isDark) {
                    drawSeedPageDark(seedInput.toString());
                } else {
                    drawSeedPageLight(seedInput.toString());
                }
                if (temp == 'q' || temp == 'Q') {
                    System.exit(0);
                }
            }
        }
        return seedInput.toString();
    }

    public void toggleDisplayMode() {
        if (isDark) {
            changeWorldToLightMode();
        } else {
            changeWorldToDarkMode();
        }

        isDark = !isDark;
    }

    public World changeWorldToDarkMode() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (this.getWorld()[x][y] == Tileset.WHITETILE){
                    this.getWorld()[x][y] = Tileset.NOTHING;
                } else if (this.getWorld()[x][y] == Tileset.FLOORLIGHT) {
                    this.getWorld()[x][y] = Tileset.FLOOR;
                } else if (this.getWorld()[x][y] == Tileset.WALLLIGHT) {
                    this.getWorld()[x][y] = Tileset.WALL;
                } else if (Tileset.yellowGradients.contains(this.getWorld()[x][y])) {
                    int yellowIndex = Tileset.yellowGradients.indexOf(this.getWorld()[x][y]);
                    this.getWorld()[x][y] = Tileset.blueGradients.get(yellowIndex);
                }
            }
        }
        this.isDark = true;
        return this;
    }


}
