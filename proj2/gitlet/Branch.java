package gitlet;

import java.io.Serializable;

public class Branch implements Serializable {
    //Branch instance variables
    private String name;
    private Commit branchHead;
    private Boolean isActive;

    public String name() {
        return name;
    }

    public void setBranchHead(Commit head) {
        this.branchHead = head;
    }

    public Commit branchHead() {
        return branchHead;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        isActive = true;
    }

    public void setInactive() {
        isActive = false;
    }

    //constructor
    public Branch(String branchName, Commit currentHead) {
        this.name = branchName;
        this.branchHead = currentHead;
        this.isActive = false;
    }


}