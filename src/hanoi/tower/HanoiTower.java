package hanoi.tower;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HanoiTower {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final Scanner SC = new Scanner(System.in);
    private static final int N_COLUMNS = 3; // number of columns of the tower
    private static final int COLUMNS_HEIGHT = setColsHeight(); // height of the tower's columns
    private final int[][] hanoi = new int[N_COLUMNS][COLUMNS_HEIGHT];
    private final Instant startTime;

    private HanoiTower() {
        // auto-fill first column with disks (disks are represented by numbers)
        for (int i = 0; i < COLUMNS_HEIGHT; i++) {
            hanoi[0][i] = COLUMNS_HEIGHT - i;
        }

        // save start time
        startTime = Instant.now();
        System.out.println("Chronometer started!");
    }

    // sets the height of the tower's columns
    private static int setColsHeight() {
        System.out.println("[?] Input number of disks:");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim();

            // if is not a number between 3 and 9 (both included), isn't a valid input
            if (Pattern.compile("[3-9]").matcher(input).matches()) {
                return Integer.parseInt(input);
            }
        }
    }

    // returns true if input matches one of the possible disk movements
    private boolean isValidInput(String input) {
        String[] validMovements = { "12", "13", "21", "23", "31", "32" };
        for (String validMov : validMovements) {
            if (input.equals(validMov)) return true;
        }
        return false;
    }

    private void printTower() {
        StringBuilder print = new StringBuilder();
        for (int j = COLUMNS_HEIGHT - 1; j >= 0; j--) {
            for (int i = 0; i < N_COLUMNS; i++) {
                // colorize 0's
                if (hanoi[i][j] == 0) print.append(ANSI_BLUE);
                print.append(hanoi[i][j]).append(ANSI_RESET);

                // add space between columns
                if (i != N_COLUMNS - 1) print.append(" ");
            }
            print.append("\n");
        }
        System.out.print(print);
    }

    // print how much the game took to finish
    private void printGameTime() {
        int sec = (int) Duration.between(startTime, Instant.now()).toSeconds();
        int min = 0;
        while (sec >= 60) {
            sec -= 60;
            min++;
        }
        System.out.println("Game lasted " + min + " min, " + sec + " sec!!");
    }

    // returns if last column has at least 1 zero
    private boolean lastColHasZero() {
        for (int i = 0; i < COLUMNS_HEIGHT; i++) {
            if (hanoi[2][i] == 0) return true;
        }
        return false;
    }

    public void run() {
        int round = 0;
        while (true) {
            System.out.println("--------------");

            // if last column doesn't have any 0, game finishes
            if (!lastColHasZero()) {
                System.out.println("Good game :)");
                printTower();
                printGameTime();
                break;
            }

            round++;
            System.out.println("Round " + round + ":");
            printTower();

            // make the user move the disks
            while (true) {
                System.out.print("> ");
                String input = SC.nextLine().trim();
                if (isValidInput(input)) {
                    String[] split = input.split("");
                    int src = Integer.parseInt(split[0]) - 1;
                    int tgt = Integer.parseInt(split[1]) - 1;

                    if (topDiskCanBeMoved(src, tgt)) {
                        moveTopDisk(src, tgt);
                        break;
                    }
                }
            }
        }
    }

    // move a disk from source col to target col
    private void moveTopDisk(int src, int tgt) {
        // get disk on top of src col and move it to tgt
        int[] arr = getTopDisk(src);
        int topDiskPos = arr[0];
        int topDisk = arr[1];

        for (int i = 0; i < COLUMNS_HEIGHT; i++) {
            if (hanoi[tgt][i] == 0) {
                hanoi[tgt][i] = topDisk;
                hanoi[src][topDiskPos] = 0;
                break;
            }
        }
    }

    // returns position and number of disk on top of parsed column
    private int[] getTopDisk(int col) {
        for (int i = COLUMNS_HEIGHT - 1; i >= 0; i--) {
            if (hanoi[col][i] != 0) return new int[] { i, hanoi[col][i] };
        }
        return new int[] { 0, 0 };
    }

    // returns true if disk on top of source column can be moved to target column
    private boolean topDiskCanBeMoved(int src, int tgt) {
        // src top disk can be moved to tgt col if:
        // src col has disks (if srcTopDisk != 0 is true means it has disks)
        // and if one or both of those conditions are true:
        // tgt top disk is bigger than src top disk (tgtTopDisk > srcTopDisk) (game rule)
        // tgt col hasn't disks (if tgtTopDisk == 0 is true means it has space for another disk)
        int tgtTopDisk = getTopDisk(tgt)[1];
        int srcTopDisk = getTopDisk(src)[1];
        return srcTopDisk != 0 && (tgtTopDisk > srcTopDisk || tgtTopDisk == 0);
    }

    public static void main(String[] args) {
        new HanoiTower().run();
    }
}
