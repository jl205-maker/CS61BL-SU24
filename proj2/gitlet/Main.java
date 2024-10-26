package gitlet;


/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Hengxuan Wu
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */

    public static void main(String[] args) {
        Repository git = new Repository();
        if (args.length == 0) {
            exitWithError("Please enter a command.");
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                git.init();
                break;
            case "add":
                git.add(args[1]);
                break;
            case "commit":
                git.commit(args[1]);
                break;
            case "rm":
                git.rm(args[1]);
                break;
            case "log":
                git.log();
                break;
            case "global-log":
                git.globalLog();
                break;
            case "restore":
                if (args[2].equals("++")) {
                    exitWithError("Incorrect operands.");
                }
                if (args[1].equals("--")) {
                    git.restore(args[2]);
                } else if (args[2].equals("--")) {
                    git.restorePrev(args[1], args[3]);
                }
                break;
            case "status":
                git.status();
                break;
            case "branch":
                git.branch(args[1]);
                break;
            case "find":
                git.find(args[1]);
                break;
            case "reset":
                git.reset(args[1]);
                break;
            case "switch":
                git.switchTo(args[1]);
                break;
            case "rm-branch":
                git.rmBranch(args[1]);
                break;
            case "merge":
                git.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }


    static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(0);
    }
}