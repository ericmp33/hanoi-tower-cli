package hanoi.tower;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HanoiTower {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private static final Scanner sc = new Scanner(System.in);
    private final int colHeight;
    private final int nColumns;
    private final int[][] hanoi;
    private final Instant startTime;

    public HanoiTower() {
        colHeight = askColsHeight();
        nColumns = 3;

        // create hanoi's tower with its 3 columns and inputted number of disks
        hanoi = new int[nColumns][colHeight];

        // auto-fill first column with disks
        for (int i = 0; i < colHeight; i++) {
            hanoi[0][i] = colHeight - i;
        }

        // save start time
        startTime = Instant.now();
        System.out.println("Chronometer started!");
    }

    // asks and returns the columns height
    private int askColsHeight() {
        System.out.println("[?] Input number of disks:");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim();

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
        StringBuilder printCol = new StringBuilder();
        for (int j = colHeight - 1; j >= 0; j--) {
            for (int i = 0; i < nColumns; i++) {
                // colorize 0's
                if (hanoi[i][j] == 0) printCol.append(ANSI_BLUE);
                printCol.append(hanoi[i][j]).append(ANSI_RESET);

                // add space between columns
                if (i != nColumns - 1) printCol.append(" ");
            }
            printCol.append("\n");
        }
        System.out.print(printCol);
    }

    private boolean colHasZero(int col) {
        for (int i = 0; i < colHeight; i++) {
            if (hanoi[col][i] == 0) return true;
        }
        return false;
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

    public void run() {
        int round = 0;
        while (true) {
            System.out.println("--------------");

            // if last column doesn't have any 0, game finishes
            if (!colHasZero(nColumns - 1)) {
                System.out.println("Good game :)");
                printTower();
                printGameTime();
                break;
            }

            round++;
            System.out.println("Round " + round + ":");
            printTower();

            while (true) {
                System.out.print("> ");
                String input = sc.nextLine().trim();
                if (isValidInput(input)) {
                    String[] tt = input.split("");
                    int src = Integer.parseInt(tt[0]) - 1;
                    int tgt = Integer.parseInt(tt[1]) - 1;

                    if (topDiskCanBeMoved(src, tgt)) {
                        moveTopDisk(src, tgt);
                        break;
                    }
                }
            }
        }
    }

    // move a disk from column src (source) to tgt (target)
    private void moveTopDisk(int src, int tgt) {
        // get disk on top of src column and move it to tgt
        int topDisk = getTopDisk(src);
        int topDiskPos = getTopDiskPos(src);

        for (int i = 0; i < colHeight; i++) {
            if (hanoi[tgt][i] == 0) {
                hanoi[tgt][i] = topDisk;
                hanoi[src][topDiskPos] = 0;
                break;
            }
        }
    }

    // returns disk on top of parsed column
    private int getTopDisk(int col) {
        for (int i = colHeight - 1; i >= 0; i--) {
            if (hanoi[col][i] != 0) return hanoi[col][i];
        }
        return 0;
    }

    // returns position of disk on top of parsed column
    private int getTopDiskPos(int col) {
        for (int i = colHeight - 1; i >= 0; i--) {
            if (hanoi[col][i] != 0) return i;
        }
        return 0;
    }

    // returns true if disk on top of source column can be moved to target column
    private boolean topDiskCanBeMoved(int src, int tgt) {
        // else, if tgt top disk is greater than src top disk and is not 0, it can be moved
        int tgtTopDisk = getTopDisk(tgt);
        int srcTopDisk = getTopDisk(src);

        if (srcTopDisk > 0 && tgtTopDisk == 0) return true;

        return srcTopDisk < tgtTopDisk;
    }

    //V2
    /*
    // returns true if disk on top of source column can be moved to target column
    private boolean topDiskCanBeMoved(int src, int tgt) {
        // src disk can be moved to tgt col if
        // src col has disks (srcTopDisk != 0)
        // and
        // tgt col has space to put that disk (tgtTopDisk == 0)
        // or
        // tgt top disk is greater than src top disk (tgtTopDisk > srcTopDisk)
        int tgtTopDisk = getTopDisk(tgt);
        int srcTopDisk = getTopDisk(src);

        return srcTopDisk != 0 && (tgtTopDisk == 0 || tgtTopDisk > srcTopDisk);
    }*/
}
