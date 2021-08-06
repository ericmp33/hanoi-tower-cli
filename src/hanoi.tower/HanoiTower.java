package hanoi.tower;

import java.util.Scanner;

public class HanoiTower {
    private static final Scanner SC = new Scanner(System.in);
    private final int[][] hanoi;

    public HanoiTower() {
        // create hanoi's tower with its 3 columns and inputted number of disks
        hanoi = new int[3][setDisks()];

        // auto-fill first column
        for (int i = 0; i < hanoi[1].length; i++) {
            hanoi[0][i] = hanoi[1].length - i;
        }
    }

    private void print() {
        StringBuilder printColumn = new StringBuilder();
        for (int j = hanoi[1].length - 1; j >= 0; j--) {
            for (int i = 0; i < hanoi.length; i++) {
                printColumn.append(hanoi[i][j]);
                // add space between columns
                if (i != hanoi.length - 1) printColumn.append(" ");
            }
            // terminate the line
            if (j != 0) printColumn.append("\n");
        }
        System.out.println(printColumn);
    }

    private int setDisks() {
        System.out.println("Input number of disks");
        //todo make validations etc etc

        return Integer.parseInt(SC.nextLine());
    }

    public void run() {
        int round = 0;
        while (true) {
            round++;
            if (gameOver()) {
                print();
                System.out.println("game finished");
                break;
            }

            System.out.println("Round " + round + ":");

            print();

            String s = SC.nextLine();
            String[] tt = s.split(" ");
            int or = Integer.parseInt(tt[0]);
            int de = Integer.parseInt(tt[1]);
            moveItem(or, de);
        }
    }

    private boolean gameOver() {
        // if last column has zeros game isn't over
        if (columnHasZero(hanoi.length - 1)) return false;

        // else, check if last column contains all elements sorted
        int temp = hanoi.length - 1;
        for (int i = 0; i < hanoi.length; i++) {
            int hanoiTemp = hanoi[hanoi.length - 1][i];
            if (hanoiTemp <= temp) temp = hanoiTemp;
        }
        return temp == 1;
    }

    private boolean columnHasZero(int column) {
        // check if parsed int is out of bounds
        if (column < 0 || column > hanoi.length) return false;

        for (int i = 0; i < hanoi.length; i++) {
            if (hanoi[column][i] == 0) return true;
        }
        return false;
    }

    private boolean columnHasItems(int column) {
        // check if parsed int is out of bounds
        if (column < 0 || column > hanoi.length) return false;

        for (int i = 0; i < hanoi.length; i++) {
            if (hanoi[column][i] != 0) return true;
        }
        return false;
    }

    // returns top item in parsed column
    private int getColumnTopItem(int column) {
        int highest = 0;
        for (int i = hanoi.length - 1; i >= 0; i--) {
            int temp = hanoi[column][i];
            if (temp > highest) highest = temp;
        }
        return highest;
    }

    // returns true if top item of origin column can be placed at top of destination column
    private boolean itemCanBePlaced(int originColumn, int destinationColumn) {
        int topItem = getColumnTopItem(originColumn);
        for (int i = 0; i < hanoi.length; i++) {
            int temp = hanoi[destinationColumn][i];
            if (topItem < temp && temp != 0 || !columnHasItems(destinationColumn)) return true;
        }
        return false;
    }

    // moves an item from a column to another one
    private void moveItem(int originColumn, int destinationColumn) {
        // check if origin column has not items
        if (!columnHasItems(originColumn)) {
            System.out.println("column " + originColumn + " doesn't have items");
            return;
        }

        // if top item can't be placed
        if (!itemCanBePlaced(originColumn, destinationColumn)) {
            System.out.println("item can't be placed to column " + destinationColumn);
            return;
        }

        // else, move the highest num to the destination column
        // get the item on top of the column
        int topItem = 0;
        for (int i = hanoi.length - 1; i >= 0; i--) {
            if (hanoi[originColumn][i] != 0) {
                topItem = hanoi[originColumn][i];
                hanoi[originColumn][i] = 0;
                break;
            }
        }

        // put top item to the destination column
        for (int i = 0; i < hanoi.length; i++) {
            if (hanoi[destinationColumn][i] == 0) {
                hanoi[destinationColumn][i] = topItem;
                break;
            }
        }

        // send the items to the ground of the column
        for (int j = hanoi.length - 1; j >= 0; j--) {
            for (int i = 0; i < hanoi.length; i++) {
                if (hanoi[j][i] == 0 && i + 1 < hanoi.length && hanoi[j][i + 1] != 0 && columnHasZero(j)) {
                    hanoi[j][i] = hanoi[j][i + 1];
                    hanoi[j][i + 1] = 0;
                }
            }
        }

        System.out.println("top item moved from column " + originColumn + " to " + destinationColumn);
    }
}

// todo: ask length of array (min 3 and max, idk 9 to prevent bad-print?), one dimension must be 3!!! which one is it?
// todo: ask game mode, programmer or human noob
// todo: validate user input
// todo: better comments on code
// todo: optimize code
// todo: make it more modular
