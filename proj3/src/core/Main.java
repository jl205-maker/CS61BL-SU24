package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.apache.commons.lang3.SerializationUtils.serialize;


public class Main {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    private static final Font fontSmall = new Font("Monaco", Font.PLAIN, 20); //top bar setting font
    private static final Font smallTitle = new Font("Broadway", Font.PLAIN,20);
    private static final Font fontTitle = new Font("Broadway", Font.BOLD, 60); //game entry name font
    private static final Font fontMedium = new Font("Broadway", Font.PLAIN, 40); //menu and prompt msg font

    public static final File WORLD = new File("world.txt");
    public static final File AVATAR = new File("avatar.txt");
    public static final File SEED = new File("seed.txt");
    public static final File DISPLAYMODE = new File("displayMode.txt");

    public static void main(String[] args) {



        //create a new world object
        World world1 = new World();

        //initialize a new canvas
        TERenderer ter = new TERenderer();
        ter.initialize(world1, WIDTH, HEIGHT + 3);

        //enter the default entry page of the game (dark mode)
        world1.drawDefaultEnterPage();
        //enable double buffering for more smooth rendering
        StdDraw.enableDoubleBuffering();

        //tune settings of the game on entry page
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {

                char next = StdDraw.nextKeyTyped();

                //quit
                if (next == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            next = StdDraw.nextKeyTyped();
                            if (next == 'q' || next == 'Q') {
                                //FileUtils.writeContents(WORLD, world1);
                                //StdDraw.save("world.txt");
                                //world1.drawFrame("");
                                System.exit(0);
                            } else {
                                break;
                            }
                        }
                    }
                }


                //toggle display setting
                if (next == 'p' || next == 'P') {
                    world1.toggleEntryPageDisplay();
                    continue;
                }


                //solicit seed input and generate new world
                long seed = 0;
                if (next == 'n' || next == 'N') {
                    if (world1.isDark) {
                        world1.drawSeedPageDark("");
                    } else {
                        world1.drawSeedPageLight("");
                    }

                    String seedString = world1.readSeed();
                    StdDraw.pause(400);

                    String newStr = seedString.substring(0, seedString.length() - 1);
                    seed = Long.parseLong(newStr);

                    TETile[][] world = world1.generateRandomWorld(80, 40, seed);

                } else if (next == 'l' || next == 'L') {
                    TETile[][] world = FileUtils.readObject(WORLD, TETile[][].class);
                    Position p = FileUtils.readObject(AVATAR, Position.class);


                    String newStr = FileUtils.readObject(SEED, String.class);
                    seed = Long.parseLong(newStr);
                    world1.generateRandomWorld(80, 40, seed);

                    world1.isDark = FileUtils.readObject(DISPLAYMODE, Boolean.class);

                    //reinitialize the renderer
                    TERenderer newter = new TERenderer();
                    newter.initialize(world1, WIDTH, HEIGHT + 3);

                    world1.setWorld(world);
                    Avatar avatar = new Avatar(p, world[p.getX()][p.getY()]);
                    world1.setAvatar(avatar);
                    world1.showAvatar();



                    if (world1.isDark) {
                        ter.renderFrame(world1, "Dark");
                    } else {
                        ter.renderFrame(world1, "Light");
                    }

                } else {
                    continue;
                }

                if (world1.isDark) {
                    ter.renderFrame(world1, "Dark");
                } else {
                    ter.renderFrame(world1.changeWorldToLightMode(), "Light");
                }

                while (true) {
                    if (StdDraw.hasNextKeyTyped()) {
                        char nextChar = StdDraw.nextKeyTyped();

                        //quit
                        if (nextChar == ':') {
                            while (true) {
                                if (StdDraw.hasNextKeyTyped()) {
                                    char nextChara = StdDraw.nextKeyTyped();
                                    if (nextChara == 'q' || nextChara == 'Q') {
                                        world1.removeAvatar();
                                        FileUtils.writeContents(WORLD, serialize(world1.getWorld()));
                                        FileUtils.writeContents(AVATAR, serialize(world1.getAvatarPos()));
                                        String seedStr = Long.toString(seed);
                                        FileUtils.writeContents(SEED, serialize(seedStr));
                                        FileUtils.writeContents(DISPLAYMODE, serialize(world1.isDark));

                                        System.exit(0);
                                    } else {
                                        break;
                                    }
                                }

                            }
                        }



                        //turn on or off the light
                        if (nextChar == 'b' || nextChar == 'B') {
                            if (world1.getAvatarTile().equals(Tileset.LIGHTFLOORA)) {
                                world1.turnOffLight(world1.getAvatarPos());
                                if (world1.isDark) {
                                    ter.renderFrame(world1, "Dark");
                                } else {
                                    ter.renderFrame(world1.changeWorldToLightMode(), "Light");
                                }
                            } else if (world1.getAvatarTile().equals(Tileset.LIGHTBULB)) {
                                world1.turnOnLight(world1.getAvatarPos());
                                if (world1.isDark) {
                                    ter.renderFrame(world1, "Dark");
                                } else {
                                    ter.renderFrame(world1, "Light");
                                }
                            }
                            continue;
                        }

                        //move avatar
                        world1.moveAvatar(nextChar);
                        if (world1.isDark) {
                            ter.renderFrame(world1, "Dark");
                        } else {
                            ter.renderFrame(world1, "Light");
                        }
                    }

                    //mouse motion handling
                    double mouseX = StdDraw.mouseX();
                    double mouseY = StdDraw.mouseY();
                    int x = (int) mouseX;
                    int y = (int) mouseY;

                    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                        TETile tile = world1.getWorld()[x][y];
                        String tileDescription = tile.description();

                        if (world1.isDark) {
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.filledRectangle(WIDTH / 2, HEIGHT + 2, 20, 1);
                            //display tile description
                            StdDraw.setFont(fontSmall);
                            StdDraw.setPenColor(StdDraw.MAGENTA);
                            StdDraw.text(WIDTH/2, HEIGHT + 2, tileDescription);
                        } else {
                            StdDraw.setPenColor(StdDraw.WHITE);
                            StdDraw.filledRectangle(WIDTH / 2, HEIGHT + 2, 20, 1);
                            //display tile description
                            StdDraw.setFont(fontSmall);
                            StdDraw.setPenColor(StdDraw.BLUE);
                            StdDraw.text(WIDTH/2, HEIGHT + 2, tileDescription);
                        }

                        StdDraw.show();
                    }


                }
            }

        }
    }
}