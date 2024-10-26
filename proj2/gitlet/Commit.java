
package gitlet;

import java.io.Serializable;
import java.util.*;

/** Represents a gitlet commit object.
 *  It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *
 *  @author Hengxuan Wu
 */
public class Commit implements Serializable {
    /**
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** Parent reference */

    private Commit parent;

    /** Second reference, for merges. */
    private Commit secondParent;

    /** The message of this Commit. */
    private String message;

    /** Commit time. */
    private Date timestamp;

    private List<Blob> blobs;




    /** constructor */
    public Commit(String message) {
        this.message = message;
        this.parent = null;
        this.secondParent = null;
        this.timestamp = new Date(0);
        blobs = new LinkedList<>();

    }

    public Commit parent() {
        return parent;
    }

    public Date timestamp() {
        return timestamp;
    }

    public List<Blob> blobs() {
        return blobs;
    }

    public String message() {
        return message;
    }

    public Commit(String message, Commit parent) {
        this.message = message;
        this.parent = parent;
        this.secondParent = null;
        this.timestamp = new Date();
        blobs = new LinkedList<>();
        blobs.addAll(parent.blobs);

    }

    public String getSha1() {
        Object[] objects = new Object[5];
        objects[0] = this.message;
        objects[1] = Utils.serialize(this.parent);
        objects[2] = Utils.serialize(this.timestamp);
        objects[3] = Utils.serialize((Serializable) this.blobs);
        objects[4] = Utils.serialize(this.secondParent);
        return Utils.sha1(objects);
    }

    public void blobsRemove(Blob b) {
        if (!blobs.contains(b)) {
            return;
        }
        blobs.remove(b);
    }
}
