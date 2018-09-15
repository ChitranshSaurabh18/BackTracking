package org.saurabh.backtracking;

/**
 * Given a partially filled 9×9 2D array ‘grid[9][9]’, the goal is to assign digits (from 1 to 9)
 * to the empty cells so that every row, column, and sub-grid of size 3 × 3 contains exactly one
 * instance of the digits from 1 to 9.
 *
 * @author Saurabh, Chitransh
 */
public class Sudoku {

    private int[][] board;

    public Sudoku (int[][] board) {
        this.board = board;                     // 0 indicates vacant position
    }

    public boolean solve () {
        Position vacantPosition = findVacantPosition();
        if (vacantPosition == null) {
            return true;
        } else {
            for (int num = 1; num <= 9; num++) {
                if (isSafe(vacantPosition, num)) {
                    //chose
                    board[vacantPosition.x][vacantPosition.y] = num;

                    // explore
                    boolean solveSudoku = solve();
                    if (solveSudoku) {
                        return true;
                    }

                    // un-chose
                    board[vacantPosition.x][vacantPosition.y] = 0;
                }
            }
        }
        return false;
    }

    private Position findVacantPosition () {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column] == 0) {
                    return new Position(row, column);
                }
            }
        }
        return null;
    }

    /**
     * Is it safe to place num in the vacant position
     */
    private boolean isSafe (Position vacantPosition, int num) {
        int row = vacantPosition.x;
        int column = vacantPosition.y;
        return isRowSafe(row, num) && isColumnSafe(column, num) && isBoxSafe(row, column, num);
    }

    private boolean isRowSafe (int row, int num) {
        for (int c = 0; c < 9; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnSafe (int column, int num) {
        for (int r = 0; r < 9; r++) {
            if (board[r][column] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the num to be inserted at row, column contains only one instance of 1 to 9 in the box
     */
    private boolean isBoxSafe (int row, int column, int num) {
        int boxStartRow = row - (row % 3);
        int boxStartColumn = column - (column % 3);
        for (int r = boxStartRow; r < 3; r++) {
            for (int c = boxStartColumn; c < 3; c++) {
                if (num == board[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard () {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }

    private static class Position {
        private int x, y;

        public Position (int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals (Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            Position that = (Position) object;
            return this.x == that.x && this.y == that.y;
        }

        @Override
        public String toString () {
            return "(" + x + ", " + y + ')';
        }
    }

    public static void main (String[] args) {
        int[][] grid = {
                {3, 0, 6, 5, 0, 8, 4, 0, 0},
                {5, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 8, 7, 0, 0, 0, 0, 3, 1},
                {0, 0, 3, 0, 1, 0, 0, 8, 0},
                {9, 0, 0, 8, 6, 3, 0, 0, 5},
                {0, 5, 0, 0, 9, 0, 6, 0, 0},
                {1, 3, 0, 0, 0, 0, 2, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 7, 4},
                {0, 0, 5, 2, 0, 6, 3, 0, 0}
        };

        Sudoku sudoku = new Sudoku(grid);
        boolean solve = sudoku.solve();
        if (solve) {
            sudoku.printBoard();
        }
    }
}
