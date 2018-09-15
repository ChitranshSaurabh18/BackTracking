package org.saurabh.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * A Maze is given as N x N binary matrix of blocks where source block is the upper left most block
 * i.e. maze[0][0] and destination block is lower rightmost block i.e. maze[N-1][N-1]
 *
 * A rat starts from source and has to reach destination.
 * The rat can move only in two directions: forward and down.
 *
 * In the maze matrix,
 *      a) 0 means the block is dead end, and
 *      b) 1 means the block can be used in the path from source to destination.
 *
 * @author Saurabh, Chitransh
 */
public class PathFinder {

    private int         noOfRows;
    private int         noOfColumns;
    private int[][]     board;
    private Position    source;
    private Position    destination;
    private Position    currentPosition;

    private List<Position> stepsTaken;

    public PathFinder (int[][] board, int startX, int startY, int destinationX, int destinationY) {
        this.source = new Position(startX, startY);
        this.destination = new Position(destinationX, destinationY);
        this.currentPosition = source;
        this.stepsTaken = new ArrayList<>();
        this.stepsTaken.add(this.source);
        this.noOfRows = board.length;
        this.noOfColumns = board[0].length;
        this.board = board;
    }

    public boolean findPath () {
        if (this.currentPosition.equals(this.destination)) {
            return true;
        }

        for (Position nextPossiblePosition : nextPossiblePositions()) {
            if (!isBlocked(nextPossiblePosition)) {
                Position oldCurrentPosition = currentPosition;

                // chose step
                this.currentPosition = nextPossiblePosition;
                this.stepsTaken.add(nextPossiblePosition);

                // explore step
                boolean finished = findPath();
                if (finished) {
                    return true;
                }

                // un-chose step
                this.currentPosition = oldCurrentPosition;
                this.stepsTaken.remove(nextPossiblePosition);
            }
        }
        return false;
    }

    public List<Position> nextPossiblePositions () {
        List<Position> nextPossiblePositions = new ArrayList<>();
        int currentX = this.currentPosition.x;
        int currentY = this.currentPosition.y;

        if (currentX < this.noOfRows - 1) {
            nextPossiblePositions.add(new Position(currentX + 1, currentY));
        }

        if (currentY < this.noOfColumns - 1) {
            nextPossiblePositions.add(new Position(currentX, currentY + 1));
        }
        return nextPossiblePositions;
    }

    public boolean isBlocked (Position position) {
        return this.board[position.x][position.y] == 0;
    }


    public void printStepsTaken () {
        for (int i = 1; i < stepsTaken.size(); i++) {
            Position previous = stepsTaken.get(i - 1);
            Position current = stepsTaken.get(i);
            System.out.println("Move from " + previous.toString() + " to " + current.toString());
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
        int[][] maze = {
                {1, 0, 0, 0},
                {1, 1, 0, 1},
                {0, 1, 0, 0},
                {1, 1, 1, 1}
        };

        PathFinder pathFinder = new PathFinder(maze, 0, 0, 3, 3);
        boolean findPath = pathFinder.findPath();
        if (findPath) {
            pathFinder.printStepsTaken();
        }
    }
}
