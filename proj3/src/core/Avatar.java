package core;

import tileengine.TETile;

import java.io.Serializable;

public class Avatar implements Serializable {
    private Position pos;
    private TETile tile;

    public Avatar(Position pos, TETile tile) {
        this.pos = pos;
        this.tile = tile;
    }

    public Position getAvatarPos() {
        return pos;
    }

    public TETile getAvatarTile() {
        return tile;
    }

}