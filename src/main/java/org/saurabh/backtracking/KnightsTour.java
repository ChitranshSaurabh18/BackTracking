package org.saurabh.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The knight is placed on the first block of an empty board and, moving according to
 * the rules of chess, must visit each square exactly once.
 *
 * @author Saurabh, Chitransh
 */
public class KnightsTour {

    private int         size;
    private int[][]     board;
    private int         stepCount;
    private Position    current;

    public KnightsTour (int size) {
        this.size = size;
        this.board = new int[size][size];

        for (int i = 0; i < size; i++) {
            Arrays.fill(board[i], -1);
        }

        board[0][0] = 0;
        this.current = new Position(0, 0);
        this.stepCount = 1;
    }

    public boolean travel () {
        if (tourComplete()) {
            return true;
        }

        for (Position nextPossiblePosition : nextPossiblePositions()) {
            if (!isVisited(nextPossiblePosition)) {
                Position oldPosition = current;
                // chose
                current = nextPossiblePosition;
                board[current.x][current.y] = stepCount;
                stepCount++;

                // explore
                boolean finished = travel();
                if (finished) {
                    return true;
                }

                // un-chose
                board[current.x][current.y] = -1;
                stepCount--;
                current = oldPosition;
            }
        }
        return false;
    }

    private boolean tourComplete () {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Position> nextPossiblePositions () {
        List<Position> nextPossiblePositions = new ArrayList<>();
        int currentX = current.x;
        int currentY = current.y;

        // maintain this order for faster convergence
        addIfValid(nextPossiblePositions, new Position(currentX + 2, currentY + 1));
        addIfValid(nextPossiblePositions, new Position(currentX + 1, currentY + 2));
        addIfValid(nextPossiblePositions, new Position(currentX - 1, currentY + 2));
        addIfValid(nextPossiblePositions, new Position(currentX - 2, currentY + 1));
        addIfValid(nextPossiblePositions, new Position(currentX - 2, currentY - 1));
        addIfValid(nextPossiblePositions, new Position(currentX - 1, currentY - 2));
        addIfValid(nextPossiblePositions, new Position(currentX + 1, currentY - 2));
        addIfValid(nextPossiblePositions, new Position(currentX + 2, currentY - 1));
        return nextPossiblePositions;
    }

    private void addIfValid (List<Position> positions, Position position) {
        if (position.x >= 0 && position.x < size && position.y >= 0 && position.y < size) {
            positions.add(position);
        }
    }

    private boolean isVisited (Position position) {
        return board[position.x][position.y] != -1;
    }

    public void printBoard () {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Integer step = board[i][j];
                String stepString = step < 10 ? " " + step.toString() : step.toString();
                System.out.print(stepString + " ");
            }
            System.out.println();
        }
        System.out.println();
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
        KnightsTour knightsTour = new KnightsTour(8);
        boolean travelComplete = knightsTour.travel();
        if (travelComplete) {
            knightsTour.printBoard();
        }
    }
}
