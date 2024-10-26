package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Blob implements Serializable {
    private File sourceFile;
    private String tempName;
    private String trueName;
    private Date timestamp;

    public Blob(File f, String trueName) {
        //this.sourceFile = f;
        this.trueName = trueName;
        this.timestamp = new Date();

        Object[] objects = new Object[2];
        objects[0] = this.trueName;
        objects[1] = Utils.serialize(this.timestamp);
        String sha1 = Utils.sha1(objects);

        this.tempName = trueName + sha1;
        this.sourceFile = new File(tempName);
        Utils.writeContents(this.sourceFile, Utils.readContents(f));
    }

    public File sourceFile() {
        return sourceFile;
    }

    public String tempName() {
        return tempName;
    }

    public String trueName() {
        return trueName;
    }

    public Date timestamp() {
        return timestamp;
    }

    public String getSha1() {
        Object[] objects = new Object[4];
        objects[0] = this.trueName;
        objects[1] = Utils.serialize(this.sourceFile);
        objects[2] = Utils.serialize(this.timestamp);
        objects[3] = this.tempName;
        return Utils.sha1(objects);
    }

}