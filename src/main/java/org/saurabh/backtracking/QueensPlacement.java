package org.saurabh.backtracking;

/**
 * We have to place N chess queens on an N × N chessboard, so that no
 * two queens attack each other
 *
 * @author Saurabh, Chitransh
 */
public class QueensPlacement {

    private final int   size;
    private boolean[][] board;

    public QueensPlacement (int size) {
        this.size = size;
        this.board = new boolean[size][size];
    }

    public boolean placeQueens () {
        return placeQueensInternal(0);
    }

    /**
     * Attempts to place the queen at @param row, and then row + 1, row + 2 and so on recursively
     *
     * @return true if successful in placing the queen at @param row
     */
    private boolean placeQueensInternal (int row) {                       // place queen in @param row
        if (row >= size) {
            return true;
        } else {
            for (int column = 0; column < size; column++) {
                if (isSafe(row, column)) {
                    place(row, column);                                   // chose
                    boolean finished = placeQueensInternal(row + 1);      // explore
                    if (finished) {
                        return true;
                    }
                    remove(row, column);                                  // un-chose
                }
            }
        }
        return false;
    }

    public void place (int row, int column) {
        board[row][column] = true;
    }

    public void remove (int row, int column) {
        board[row][column] = false;
    }

    public boolean isSafe (int row, int column) {
        return isRowSafe(row) && isColumnSafe(column) && isDiagonalSafe(row, column);
    }

    private boolean isRowSafe (int row) {
        for (int i = 0; i < size; i++) {
            if (board[row][i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnSafe (int column) {
        for (int i = 0; i < size; i++) {
            if (board[i][column]) {
                return false;
            }
        }
        return true;
    }

    private boolean isDiagonalSafe (int row, int column) {
        int row1 = row - 1, column1 = column - 1;
        int row2 = row - 1, column2 = column + 1;
        int row3 = row + 1, column3 = column - 1;
        int row4 = row + 1, column4 = column + 1;

        while (row1 >= 0 && row1 < size && column1 >= 0 && column1 < size) {
            if (board[row1][column1]) {
                return false;
            }
            row1--;
            column1--;
        }

        while (row2 >= 0 && row2 < size && column2 >= 0 && column2 < size) {
            if (board[row2][column2]) {
                return false;
            }
            row2--;
            column2++;
        }

        while (row3 >= 0 && row3 < size && column3 >= 0 && column3 < size) {
            if (board[row3][column3]) {
                return false;
            }
            row3++;
            column3--;
        }

        while (row4 >= 0 && row4 < size && column4 >= 0 && column4 < size) {
            if (board[row4][column4]) {
                return false;
            }
            row4++;
            column4++;
        }
        return true;
    }

    public void printBoard () {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (board[row][column]) {
                    System.out.print("Q ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main (String[] args) {
        QueensPlacement queensPlacement = new QueensPlacement(6);
        boolean placed = queensPlacement.placeQueens();
        if (placed) {
            queensPlacement.printBoard();
        } else {
            System.out.println("Unable to place queens");
        }
    }

}
