package com.mazerunners.maze;

import java.io.File;

public class MazeSolver {

    private static File exampleFile = new File("maze two solutions.txt");


    public boolean traverseMazeV3(Maze maze, char[][] grid ,int x, int y, CharStack solution, CharStack pathsAvailable) {

        int maxHeightIndex = maze.getMazeHeight() - 1;
        int maxWidthIndex = maze.getMazeWidth() - 1;

        grid[x][y] = 'x';
        solution.push( x + "," + y);
        if (x == maze.getEndCoordX() && y == maze.getEndCoordY()) {
           
            return true;
        }
        try {
            // y + 1, y - 1
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && y - 1 >= 0 && grid[x][y-1] == '1') {
                pathsAvailable.push( x + " " + y);
            }

            // y + 1, x + 1
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && x + 1 <= maxWidthIndex && grid[x+1][y] == '1') {
                pathsAvailable.push( x + " " + y);
            }

            // y + 1, x - 1
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && x - 1 >= 0 && grid[x-1][y] == '1') {
                pathsAvailable.push( x + " " + y);
            }

            // y - 1, x + 1
            if(y - 1 >= 0 && grid[x][y-1] == '1' && x + 1 <= maxWidthIndex && grid[x+1][y] == '1') {
                pathsAvailable.push( x + " " + y);
            }

            // y - 1, x - 1
            if(y - 1 >= 0 && grid[x][y-1] == '1' && x - 1 >= 0 && grid[x-1][y] == '1') {
                pathsAvailable.push( x + " " + y);
            }

            // x - 1, x + 1
            if(x - 1 >= 0 && grid[x-1][y] == '1' && x + 1 <= maxWidthIndex && grid[x+1][y] == '1') {
                pathsAvailable.push( x + " " + y);
            }

            // if path above exists
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && traverseMazeV3(maze, grid, x, y + 1, solution, pathsAvailable)){
                return true;
            }

            // if path below exists
            if(y - 1 >= 0 && grid[x][y-1] == '1' && traverseMazeV3(maze, grid, x, y - 1, solution, pathsAvailable)){
                return true;
            }

            // if path to left exists
            if(x - 1 >= 0 && grid[x-1][y] == '1' && traverseMazeV3(maze, grid, x - 1, y, solution, pathsAvailable)){
                return true;
            }

            // if path to right exists
            if(x + 1 <= maxWidthIndex && grid[x+1][y] == '1' && traverseMazeV3(maze, grid, x + 1, y, solution, pathsAvailable)){
                return true;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("An index is out of bounds!");
        }
        // If no path is found, it'll jump back in time
        //System.out.println("Current: " + x + "," + y);

        // Sets visited and unsuccessful locations with this value.
        grid[x][y] = 'v';

        // Jumping back in time
        String[] coord = String.valueOf(pathsAvailable.peek()).split(" ");
        x = Integer.valueOf(coord[0]);
        y = Integer.valueOf(coord[1]);
        //System.out.println("X: " + x + ", Y: " + y);
        //System.out.println("Whoops! Heading back to " + x + "," + y);

        // Removes non-solution items from stack
        if(!solution.isEmpty()) {
            solution.pop();
        }
        return false;
    }

    public static void main(String... args) {
        MazeUtil mazeUtil = new MazeUtil();
        Maze maze = mazeUtil.importMaze(exampleFile);
        CharStack mazeSolution = new CharStack();
        CharStack mazePaths = new CharStack();

        char[][] grid = maze.getMazeGrid();

    }

}
