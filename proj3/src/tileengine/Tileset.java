package tileengine;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static ArrayList<TETile> avatars = new ArrayList<>();
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you", 0);
    public static final TETile ALIEN = new TETile(' ', Color.white, Color.black, "you", "avatar_alien.png", 17);
    public static final TETile FROG = new TETile(' ', Color.white, Color.black, "you", "avatar_frog.png", 18);
    public static final TETile ANXIOUS = new TETile(' ', Color.white, Color.black, "you", "avatar_anxiousFace.png", 19);
    public static final TETile BRAIN = new TETile(' ', Color.white, Color.black, "you", "avatar_brainExplosion.png", 20);
    public static final TETile FLAME = new TETile(' ', Color.white, Color.black, "you", "avatar_flame.png", 21);
    public static final TETile GHOST = new TETile(' ', Color.white, Color.black, "you", "avatar_ghost.png", 22);
    public static final TETile PENGUIN = new TETile(' ', Color.white, Color.black, "you", "avatar_penguin.png", 23);
    public static final TETile SKULL2 = new TETile(' ', Color.white, Color.black, "you", "avatar_skull2.png", 24);
    public static final TETile DEVIL = new TETile(' ', Color.white, Color.black, "you", "avatar_skullDevil.png", 25);
    public static final TETile TV = new TETile(' ', Color.white, Color.black, "you", "avatar_TV.png", 26);
    public static final TETile DRAGON = new TETile(' ', Color.white, Color.black, "you", "avatar_dragon.png", 27);
    public static final TETile CLOWN = new TETile(' ', Color.white, Color.black, "you", "avatar_clown.png", 28);
    static {
        avatars.add(ALIEN);
        avatars.add(FROG);
        avatars.add(ANXIOUS);
        avatars.add(BRAIN);
        avatars.add(FLAME);
        avatars.add(GHOST);
        avatars.add(PENGUIN);
        avatars.add(SKULL2);
        avatars.add(DEVIL);
        avatars.add(TV);
        avatars.add(DRAGON);
        avatars.add(CLOWN);
    }

    public static final TETile WALL = new TETile('#', new Color(100, 100, 216), Color.darkGray,
            "wall", 1);
    public static final TETile WALLLIGHT = new TETile('#', new Color(230, 185, 166), Color.lightGray,
            "wall", 1);
    public static final TETile LIGHTBULB = new TETile(' ', Color.white, Color.black, "lightbulb", "lightbulb.png", 27);
    public static final TETile WHITETILE = new TETile(' ', Color.white, Color.white, "nothing", 158);
    public static final TETile FLOOR = new TETile('·', new Color(50, 50, 50), Color.black, "floor", 2);
    public static final TETile FLOORLIGHT = new TETile('-', Color.lightGray, Color.WHITE, "floor", 2);
    public static final TETile GOLD = new TETile('$', Color.black, new Color(255, 175, 0), "coin", 16);

    public static final TETile LIGHTFLOORA = new TETile('·', Color.white, new Color(255, 251, 218), "emissive", 100);
    public static final TETile LIGHTFLOORB = new TETile('·', Color.white, new Color(146, 144, 195), "emissive", 101);
    public static final TETile LIGHTFLOORC = new TETile('·', Color.white, new Color(83, 92, 145), "emissive", 102);
    public static final TETile LIGHTFLOORD = new TETile('·', Color.white, new Color(27, 26, 85), "emissive", 103);
    public static final TETile LIGHTFLOORE = new TETile('·', Color.white, new Color(7, 15, 80), "emissive", 104);


    public static final TETile LIGHT100 = new TETile('-', Color.white, new Color(255, 251, 218), "emissive", 100);
    public static final TETile LIGHT80 = new TETile('-', Color.white, new Color(254, 255, 210), "emissive", 101);
    public static final TETile LIGHT60 = new TETile('-', Color.white, new Color(255, 238, 169), "emissive", 102);
    public static final TETile LIGHT40 = new TETile('-', Color.white, new Color(249, 214, 137), "emissive", 103);
    public static final TETile LIGHT20 = new TETile('-', Color.white, new Color(245, 231, 178), "emissive", 104);




    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing", 3);
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass", 4);
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water", 5);
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower", 6);
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door", 7);
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door", 8);
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand", 9);
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain", 10);
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree", 11);

    public static final TETile CELL = new TETile('█', Color.white, Color.black, "cell", 12);

    public static final ArrayList<TETile> blueGradients = new ArrayList<>();
    public static final ArrayList<TETile> yellowGradients = new ArrayList<>();

    static {
        blueGradients.add(new TETile('·', Color.white, new Color(104, 222, 242), "Room Lit", 100));
        blueGradients.add(new TETile('·', Color.white, new Color(99, 209, 228), "Room Lit", 101));
        blueGradients.add(new TETile('·', Color.white, new Color(94, 197, 214), "Room Lit", 102));
        blueGradients.add(new TETile('·', Color.white, new Color(89, 184, 200), "Room Lit", 103));
        blueGradients.add(new TETile('·', Color.white, new Color(85, 171, 186), "Room Lit", 104));
        blueGradients.add(new TETile('·', Color.white, new Color(80, 158, 172), "Room Lit", 105));
        blueGradients.add(new TETile('·', Color.white, new Color(75, 146, 158), "Room Lit", 106));
        blueGradients.add(new TETile('·', Color.white, new Color(70, 133, 144), "Room Lit", 107));
        blueGradients.add(new TETile('·', Color.white, new Color(65, 120, 130), "Room Lit", 108));
        blueGradients.add(new TETile('·', Color.white, new Color(60, 107, 116), "Room Lit", 109));
        blueGradients.add(new TETile('·', Color.white, new Color(55, 95, 102), "Room Lit", 110));
        blueGradients.add(new TETile('·', Color.white, new Color(50, 82, 88), "Room Lit", 111));
        blueGradients.add(new TETile('·', Color.white, new Color(46, 69, 74), "Room Lit", 112));
        blueGradients.add(new TETile('·', Color.white, new Color(41, 56, 60), "Room Lit", 113));
        blueGradients.add(new TETile('·', Color.white, new Color(36, 44, 46), "Room Lit", 114));
        blueGradients.add(new TETile('·', Color.white, new Color(31, 31, 32), "Room Lit", 115));
    }

    static {
        yellowGradients.add(new TETile('-', Color.white, new Color(242, 203, 104), "Room Lit", 100));
        yellowGradients.add(new TETile('-', Color.white, new Color(242, 206, 113), "Room Lit", 101));
        yellowGradients.add(new TETile('-', Color.white, new Color(243, 209, 122), "Room Lit", 102));
        yellowGradients.add(new TETile('-', Color.white, new Color(243, 212, 131), "Room Lit", 103));
        yellowGradients.add(new TETile('-', Color.white, new Color(244, 215, 140), "Room Lit", 104));
        yellowGradients.add(new TETile('-', Color.white, new Color(244, 218, 149), "Room Lit", 105));
        yellowGradients.add(new TETile('-', Color.white, new Color(244, 221, 158), "Room Lit", 106));
        yellowGradients.add(new TETile('-', Color.white, new Color(245, 224, 167), "Room Lit", 107));
        yellowGradients.add(new TETile('-', Color.white, new Color(245, 226, 175), "Room Lit", 108));
        yellowGradients.add(new TETile('-', Color.white, new Color(246, 229, 184), "Room Lit", 109));
        yellowGradients.add(new TETile('-', Color.white, new Color(246, 232, 193), "Room Lit", 110));
        yellowGradients.add(new TETile('-', Color.white, new Color(246, 235, 202), "Room Lit", 111));
        yellowGradients.add(new TETile('-', Color.white, new Color(247, 238, 211), "Room Lit", 112));
        yellowGradients.add(new TETile('-', Color.white, new Color(247, 241, 220), "Room Lit", 113));
        yellowGradients.add(new TETile('-', Color.white, new Color(248, 244, 229), "Room Lit", 114));
        yellowGradients.add(new TETile('-', Color.white, new Color(248, 247, 238), "Room Lit", 115));
    }

}

