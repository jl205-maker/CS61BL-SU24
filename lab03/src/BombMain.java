public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct passwords to each phase using debugging techniques.
        //       Tip: if you don't know where a method is defined, hover your mouse over
        //              the method name, and CMD + click (or CTRL + click). This will
        //              take you to the method definition.
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226"); // Figure this out. I wonder where the phases are defined...
        }
        if (phase >= 1) {
            int[] password1 = new int[]{0,9,3,0,8};
            b.phase1(password1); // Figure this out next
        }
        if (phase >= 2) {
            int passwordLength = 1338;
            String myPasswordString = "";
            for (int i = 0; i<passwordLength; i++) {
                myPasswordString += "-81201430 ";
            }
            b.phase2(myPasswordString);
        }
    }
}
