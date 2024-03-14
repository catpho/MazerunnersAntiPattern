package com.mazerunners.maze;

import java.io.File;

public class MazeSolver {

    private static File exampleFile = new File("maze two solutions.txt");

  
    //current choice of traversal, avoids redund
    public boolean traverseMazeV3(Maze maze, char[][] grid ,int x, int y, CharStack solution) {

        int maxHeightIndex = maze.getMazeHeight() - 1;
        int maxWidthIndex = maze.getMazeWidth() - 1;

        grid[x][y] = 'x';
        solution.push( x + "," + y);
     
        if (x == maze.getEndCoordX() && y == maze.getEndCoordY()) {
 
            return true;
            }
        
            try {
                // Check if path above exists
                if (y + 1 <= maxHeightIndex && grid[x][y + 1] == '1' && traverseMazeV3(maze, grid, x, y + 1, solution)) {
                    return true;
                }

                // Check if path below exists
                if (y - 1 >= 0 && grid[x][y - 1] == '1' && traverseMazeV3(maze, grid, x, y - 1, solution)) {
                    return true;
                }

                // Check if path to the left exists
                if (x - 1 >= 0 && grid[x - 1][y] == '1' && traverseMazeV3(maze, grid, x - 1, y, solution)) {
                    return true;
                }

                // Check if path to the right exists
                if (x + 1 <= maxWidthIndex && grid[x + 1][y] == '1' && traverseMazeV3(maze, grid, x + 1, y, solution)) {
                    return true;
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("An index is out of bounds!");
            }

            // If no path is found, backtrack
            grid[x][y] = 'v'; // Mark as visited
            solution.pop();   // Remove from solution stack
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
