package gitlet;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Instant;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Main.exitWithError;


import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  @author Hengxuan Wu
 */
public class Repository implements Serializable {
    /**

     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File BLOBS_DIR = join(GITLET_DIR, ".blobs");

    public static final File COMMITS_DIR = join(GITLET_DIR, ".commits");

    public static final File STAGEDBLOB_DIR = join(GITLET_DIR, ".staged");

    public static final File HEAD = join(GITLET_DIR, ".head");

    public static final File REMOVED_DIR = join(GITLET_DIR, ".removed");

    public static final File BRANCHES_DIR = join(GITLET_DIR, ".branches");

    private static final int TIME = -8;

    private Commit head;



    public Repository() {
        head = new Commit("msg");
    }

    public void init() {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        } else {
            exitWithError("A Gitlet version-control system already exists in the current directory.");
        }
        if (!BLOBS_DIR.exists()) {
            BLOBS_DIR.mkdir();
        }
        if (!COMMITS_DIR.exists()) {
            COMMITS_DIR.mkdir();
        }
        if (!STAGEDBLOB_DIR.exists()) {
            STAGEDBLOB_DIR.mkdir();
        }
        if (!REMOVED_DIR.exists()) {
            REMOVED_DIR.mkdir();
        }
        if (!BRANCHES_DIR.exists()) {
            BRANCHES_DIR.mkdir();
        }

        Commit initialCommit = new Commit("initial commit");
        Utils.writeContents(HEAD, Utils.serialize(initialCommit));
        File file = Utils.join(COMMITS_DIR, initialCommit.getSha1());

        Utils.writeContents(file, Utils.readContents(HEAD));


        /** branch */
        //initialize a "Main" branch as the default branch, set it active
        Branch defaultBranch = new Branch("main", initialCommit);
        defaultBranch.setActive();
        File bFile = Utils.join(BRANCHES_DIR, "main");
        Utils.writeContents(bFile, Utils.serialize(defaultBranch));

    }


    public void add(String fileName) {
        for (File f : REMOVED_DIR.listFiles()) {
            f.delete();
        }

        List<String> names = Utils.plainFilenamesIn(CWD);

        File fileToAdd = join(CWD, fileName);
        //File fileToAdd = new File(fileName);

        if (!names.contains(fileName)) {
            exitWithError("File does not exist.");
        }


        String tempName = fileName;
        head = Utils.readObject(HEAD, Commit.class);

        for (Blob blob : head.blobs()) {
            if (blob.trueName().equals(fileName)) {
                if (Utils.readContentsAsString(blob.sourceFile()).equals(Utils.readContentsAsString(fileToAdd))) {
                    return;
                }
            }
        }

        for (Blob blob : head.blobs()) {
            if (blob.trueName().equals(fileName)) {
                tempName += "c";
            }

        }

        Blob stagedBlob = new Blob(fileToAdd, fileName);

        File stagedFile = join(STAGEDBLOB_DIR, stagedBlob.tempName());
        Utils.writeContents(stagedFile, Utils.serialize(stagedBlob));

    }

    public void commit(String message) {
        if (Objects.equals(message, "")) {
            exitWithError("Please enter a commit message.");
        }

        Commit origin = Utils.readObject(HEAD, Commit.class);
        head = new Commit(message, origin);


        List<String> removedNames = Utils.plainFilenamesIn(REMOVED_DIR);
        if (!removedNames.isEmpty()) {
            for (File removedFile : REMOVED_DIR.listFiles()) {
                Blob blob = Utils.readObject(removedFile, Blob.class);
                head.blobs().remove(blob);
                removedFile.delete();
            }
        }

        List<String> stagedNames = Utils.plainFilenamesIn(STAGEDBLOB_DIR);
        if (stagedNames.isEmpty() && removedNames.isEmpty()) {

            exitWithError("No changes added to the commit.");
        }

        for (String fileName : stagedNames) {

            File file = Utils.join(STAGEDBLOB_DIR, fileName);
            Blob stagedBlob = Utils.readObject(file, Blob.class);

            Blob blob = new Blob(stagedBlob.sourceFile(), stagedBlob.trueName());

            File file1 = Utils.join(BLOBS_DIR, blob.getSha1());
            Utils.writeContents(file1, Utils.serialize(blob));

            for (Blob b : head.blobs()) {
                if (blob.trueName().equals(b.trueName())) {
                    head.blobsRemove(b);
                }
            }
            head.blobs().add(blob);


        }

        /** branch */
        //set the active branch to point to the current head commit
        for (File bFile : BRANCHES_DIR.listFiles()) {
            Branch b = Utils.readObject(bFile, Branch.class);
            if (b.isActive()) {
                b.setBranchHead(head);
                Utils.writeContents(bFile, Utils.serialize(b));
            }
        }




        Utils.writeContents(HEAD, Utils.serialize(head));

        File file = Utils.join(COMMITS_DIR, head.getSha1());
        Utils.writeContents(file, Utils.serialize(head));

        for (File f : STAGEDBLOB_DIR.listFiles()) {
            f.delete();
        }
    }

    public void rm(String fileName) {
        File f = Utils.join(CWD, fileName);


        boolean inStagedArea = false;

        for (File blobFile : STAGEDBLOB_DIR.listFiles()) {
            Blob blob = Utils.readObject(blobFile, Blob.class);
            if (blob.trueName().equals(fileName)) {
                inStagedArea = true;
                blobFile.delete();
            }
        }

        Commit currCommit = Utils.readObject(HEAD, Commit.class);


        for (Blob blob : currCommit.blobs()) {
            if (blob.trueName().equals(fileName)) {
                Blob newBlob = new Blob(blob.sourceFile(), blob.trueName());
                File newFile = join(REMOVED_DIR, newBlob.tempName() + "hhh");
                Utils.writeContents(newFile, Utils.serialize(newBlob));

                Utils.restrictedDelete(f);

                currCommit.blobs().remove(blob);
                Utils.restrictedDelete(fileName);
                return;
            }
        }

        if (!inStagedArea) {
            exitWithError("No reason to remove the file.");
        }

    }

    public void log() {
        head = Utils.readObject(HEAD, Commit.class);
        Commit curr = head;
        while (curr != null) {
            System.out.println("===");
            System.out.println("commit " + curr.getSha1());

            // Convert the Date object to a LocalDateTime object
            Instant instant = curr.timestamp().toInstant();
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());


            // Set the time zone offset to -0800 (Pacific Standard Time)
            ZoneOffset offset = ZoneOffset.ofHours(TIME);
            ZoneId zoneId = ZoneId.ofOffset("UTC", offset);

            // Format the date and time using a custom pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss yyyy Z");
            String formattedDateTime = dateTime.atZone(zoneId).format(formatter);

            System.out.println("Date: " + formattedDateTime);
            System.out.println(curr.message());
            System.out.println();
            curr = curr.parent();
        }
    }

    public void globalLog() {
        List<String> names = Utils.plainFilenamesIn(COMMITS_DIR);
        System.out.println(names.size());
        for (String name : names) {
            Commit curr = Utils.readObject(Utils.join(COMMITS_DIR, name), Commit.class);
            System.out.println("===");
            System.out.println("commit " + curr.getSha1());

            // Convert the Date object to a LocalDateTime object
            Instant instant = curr.timestamp().toInstant();
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());


            // Set the time zone offset to -0800 (Pacific Standard Time)
            ZoneOffset offset = ZoneOffset.ofHours(TIME);
            ZoneId zoneId = ZoneId.ofOffset("UTC", offset);

            // Format the date and time using a custom pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss yyyy Z");
            String formattedDateTime = dateTime.atZone(zoneId).format(formatter);

            System.out.println("Date: " + formattedDateTime);
            System.out.println(curr.message());
            System.out.println();
        }

    }


    public void restore(String fileName) {
        head = Utils.readObject(HEAD, Commit.class);

        File file = Utils.join(CWD, fileName);

        for (Blob blob : head.blobs()) {
            if (blob.trueName().equals(fileName)) {
                Utils.writeContents(file, Utils.readContentsAsString(blob.sourceFile()));
                return;
            }
        }
        exitWithError("File does not exist in that commit.");
    }

    private boolean sameShaid(String longId, String shortId) {
        for (int i = 0; i < shortId.length(); i++) {
            if (longId.charAt(i) != shortId.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public void restorePrev(String id, String fileName) {

        File file0 = null;
        boolean exists = false;
        for (File commitFile : COMMITS_DIR.listFiles()) {
            Commit commit = Utils.readObject(commitFile, Commit.class);
            if (sameShaid(commit.getSha1(), id)) {
                exists = true;
                file0 = commitFile;
            }
        }
        if (!exists) {
            exitWithError("No commit with that id exists.");
        }

        Commit curr = Utils.readObject(file0, Commit.class);

        File file = Utils.join(CWD, fileName);
        for (Blob blob : curr.blobs()) {
            if (blob.trueName().equals(fileName)) {
                Utils.writeContents(file, Utils.readContentsAsString(blob.sourceFile()));
                return;
            }
        }
        exitWithError("File does not exist in that commit.");
    }

    public void status() {
        if (!GITLET_DIR.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
        System.out.println("=== Branches ===");
        for (File branchFile : BRANCHES_DIR.listFiles()) {
            Branch branch = Utils.readObject(branchFile, Branch.class);
            if (branch.isActive()) {
                System.out.println("*" + branch.name());
            }
        }

        for (File branchFile : BRANCHES_DIR.listFiles()) {
            Branch branch = Utils.readObject(branchFile, Branch.class);
            if (!branch.isActive()) {
                System.out.println(branch.name());
            }
        }


        System.out.println("\n=== Staged Files ===");
        List<String> names = Utils.plainFilenamesIn(STAGEDBLOB_DIR);
        for (String s : names) {
            Blob b = Utils.readObject(Utils.join(STAGEDBLOB_DIR, s), Blob.class);
            System.out.println(b.trueName());
        }

        System.out.println("\n=== Removed Files ===");
        List<String> namess = Utils.plainFilenamesIn(REMOVED_DIR);
        for (String s : namess) {
            Blob b = Utils.readObject(Utils.join(REMOVED_DIR, s), Blob.class);
            System.out.println(b.trueName());
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        System.out.println("\n=== Untracked Files ===");
    }


    /** Create a branch with the given name.
     * and default it to inactive.*/
    public void branch(String branchName) {
        //exit doing nothing if a branch with the same name already exists in BRANCHES_DIR.
        List<String> currentBranchList = Utils.plainFilenamesIn(BRANCHES_DIR);
        if (currentBranchList.contains(branchName)) {
            exitWithError("A branch with that name already exists.");
        }


        head = Utils.readObject(HEAD, Commit.class);
        //create a new branch with the given name.
        Branch newBranch = new Branch(branchName, head);

        //add the new branch to branches directory.
        File bFile = Utils.join(BRANCHES_DIR, branchName);
        Utils.writeContents(bFile, Utils.serialize(newBranch));
    }

    public void find(String message) {
        boolean exists = false;
        List<String> commits = Utils.plainFilenamesIn(COMMITS_DIR);
        for (File f : COMMITS_DIR.listFiles()) {
            Commit commit = Utils.readObject(f, Commit.class);
            if (commit.message().equals(message)) {
                System.out.println(commit.getSha1());
                exists = true;
            }
        }

        if (!exists) {
            exitWithError("Found no commit with that message.");
        }
    }

    public void reset(String id) {
        boolean commitExists = false;
        for (File f : COMMITS_DIR.listFiles()) {
            Commit commit = Utils.readObject(f, Commit.class);
            if (commit.getSha1().equals(id)) {
                commitExists = true;

                String branchName;
                for (File branchFile : BRANCHES_DIR.listFiles()) {
                    Branch branch = Utils.readObject(branchFile, Branch.class);
                    if (branch.isActive()) {
                        branchName = branch.name();
                        branch.setInactive();
                        branchFile.delete();
                        branch = new Branch("temp" + branchName + "temp", branch.branchHead());
                        branch.setActive();
                        File tempBranch = Utils.join(BRANCHES_DIR, branch.name());
                        Utils.writeContents(tempBranch, Utils.serialize(branch));
                        Branch newBranch = new Branch(branchName, commit);
                        File newCommit = Utils.join(BRANCHES_DIR, branchName);
                        Utils.writeContents(newCommit, Utils.serialize(newBranch));

                        switchTo(branchName);
                        tempBranch.delete();

                    }
                }






            }
        }
        if (!commitExists) {
            exitWithError("No commit with that id exists.");
        }

    }



    public void switchTo(String branchName) {
        // Check if the target branch exists; if not, exit
        List<String> currentBranchList = Utils.plainFilenamesIn(BRANCHES_DIR);
        if (!currentBranchList.contains(branchName)) {
            exitWithError("No such branch exists.");
        }

        // Obtain the target branch
        File targetBranchFile = Utils.join(BRANCHES_DIR, branchName);
        Branch targetBranch = Utils.readObject(targetBranchFile, Branch.class);

        // Check if the target branch is the current active branch; if so, exit
        if (targetBranch.isActive()) {
            exitWithError("No need to switch to the current branch.");
        }

        // Check for untracked files before performing any operations
        head = Utils.readObject(HEAD, Commit.class);
        Set<String> trackedFiles = new HashSet<>();
        for (Blob b : head.blobs()) {
            trackedFiles.add(b.trueName());
        }

        for (Blob b : targetBranch.branchHead().blobs()) {
            File fileInCWD = Utils.join(CWD, b.trueName());
            if (fileInCWD.exists() && !trackedFiles.contains(b.trueName())) {
                exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }

        // Delete any files in the current branch head but absent from the target branch head from CWD
        List<Blob> targetBranchBlobs = targetBranch.branchHead().blobs();
        List<String> names = new ArrayList<>();
        for (Blob bl :targetBranchBlobs) {
            names.add(bl.trueName());
        }

        for (Blob b : head.blobs()) {
            String name = b.trueName();
            if (!names.contains(name)) {
                File fileToDelete = Utils.join(CWD, b.trueName());
                //System.out.println(Utils.readContentsAsString(fileToDelete));
                if (fileToDelete.exists()) {
                    Utils.restrictedDelete(name);
                }
            }
        }

        // Copy all files from the target branch head to the CWD, overwriting if necessary
        for (Blob bta : targetBranchBlobs) {
            File fileToAdd = Utils.join(CWD, bta.trueName()); //file name conflict?
            byte[] content = readContents(bta.sourceFile());
            //String contentFromTargetBranch = Utils.readContentsAsString(bta.sourceFile);
            Utils.writeContents(fileToAdd, content);
        }

        // Set the target branch active and all other branches inactive
        for (File f : BRANCHES_DIR.listFiles()) {
            Branch br = Utils.readObject(f, Branch.class);
            if (br.name().equals(targetBranch.name())) {
                br.setActive();
            } else {
                br.setInactive();
            }
            Utils.writeContents(f, Utils.serialize(br));
        }

        // Modify the head pointer to the target branch head
        head = targetBranch.branchHead();
        Utils.writeContents(HEAD, Utils.serialize(head));

        //clear the staging area
        for (File f : STAGEDBLOB_DIR.listFiles()) {
            f.delete();
        }

    }

    public void rmBranch(String branchName) {
        boolean branchExists = false;
        for (File f : BRANCHES_DIR.listFiles()) {
            Branch branch = Utils.readObject(f, Branch.class);
            if (branch.name().equals(branchName)) {
                branchExists = true;
                if (branch.isActive()) {
                    exitWithError("Cannot remove the current branch.");
                }

                f.delete();

            }
        }

        if (!branchExists) {
            exitWithError("A branch with that name does not exist.");
        }
    }

    /**find the split point of current head commit and another commit,
     * which is the latest common ancestor of the current and given branch heads
     */
    private Commit findSplitPoint(Commit currentHead, Commit other) {
        //instantiate a new list storing the stream of current commit head
        List<String> currentHeadStream = new ArrayList<>();
        while (currentHead != null) {
            currentHeadStream.add(currentHead.getSha1());
            currentHead = currentHead.parent();
        }
        //locate a common ancestor with other branch in current head stream
        while (other != null) {
            if (currentHeadStream.contains(other.getSha1())) {
                return other;
            }
            other = other.parent();
        }
        return null;
    }

    /**check if a file exists in a given commit*/
    private boolean fileExists(Commit c, String filename) {
        List<Blob> cBlobs = c.blobs();
        for (Blob b : cBlobs) {
            String bName = b.trueName();
            if (bName.equals(filename)) {
                return true;
            }
        }
        return false;
    }

    /** check if the content of two files are equal */
    private boolean contentEqual(File file1, File file2) {
        return Utils.readContents(file1).equals(Utils.readContents(file2));
    }

    /**create a map that maps source file name to file*/
    private Map<String, File> createBlobMap(List<Blob> blobs) {
        Map<String, File> blobMap = new HashMap<>();
        for (Blob b : blobs) {
            blobMap.put(b.trueName(), b.sourceFile());
        }
        return blobMap;
    }


    /**merge two branches*/
    public void merge(String branchName) {

        List<String> names = Utils.plainFilenamesIn(STAGEDBLOB_DIR);
        if (!names.isEmpty()) {
            exitWithError("You have uncommitted changes.");
        }

        //grab the branch to merge from the given branch name
        File otherBranchFile = Utils.join(BRANCHES_DIR, branchName);
        if (!otherBranchFile.exists()) {
            exitWithError("A branch with that name does not exist.");
        }

        Branch otherBranch = Utils.readObject(otherBranchFile, Branch.class);

        //find the split point of two branches
        head = Utils.readObject(HEAD, Commit.class);
        Commit splitPoint = findSplitPoint(head, otherBranch.branchHead());
        Commit otherHead = otherBranch.branchHead();

        Branch currBranch = null;

        for (File f : BRANCHES_DIR.listFiles()) {
            Branch b = Utils.readObject(f, Branch.class);
            if (b.isActive()) {
                currBranch = b;
            }
        }

        /** error message */
        if (otherBranch.name().equals(currBranch.name())) {
            exitWithError("Cannot merge a branch with itself.");
        }

        Set<String> trackedFiles = new HashSet<>();
        for (Blob b : head.blobs()) {
            trackedFiles.add(b.trueName());
        }
        for (Blob b : otherBranch.branchHead().blobs()) {
            File fileInCWD = Utils.join(CWD, b.trueName());
            if (fileInCWD.exists() && !trackedFiles.contains(b.trueName())) {
                exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }

        //given branch is an ancestor of current branch
        if (splitPoint.equals(otherBranch.branchHead())) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }

        if (splitPoint.equals(head)) {
            // Fast-forward merge (current branch is an ancestor of given branch)
            Utils.writeContents(HEAD, otherBranch.branchHead());
            System.out.println("Current branch fast-forwarded.");
            return;
        }


        //grab lists of blobs from splitPoint, head, and otherBranchHead

        Map<String, File> splitPointMap = createBlobMap(splitPoint.blobs());
        Map<String, File> headBlobMap = createBlobMap(head.blobs());
        Map<String, File> otherBlobMap = createBlobMap(otherHead.blobs());

    }
}