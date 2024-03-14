package com.mazerunners.maze;

import java.io.File;

public class MazeSolver {

    private static File exampleFile = new File("maze two solutions.txt");


   
    public boolean traverseMazeV3(Maze maze, char[][] grid ,int x, int y, CharStack solution) {

            int maxHeightIndex = maze.getMazeHeight() - 1;
            int maxWidthIndex = maze.getMazeWidth() - 1;

            
            if (x < 0 || x > maxWidthIndex || y < 0 || y > maxHeightIndex || grid[x][y] != '1') {
                return false; // out of bounds or visited cell
            }
            
            grid[x][y] = 'x';
            solution.push( x + "," + y);
            
            //found solution
            if (x == maze.getEndCoordX() && y == maze.getEndCoordY()) {
     
                return true;
            }
           
         // explore all possible directions
            if (traverseMazeV3(maze, grid, x, y + 1, solution) || // Down
                traverseMazeV3(maze, grid, x, y - 1, solution) || // Up
                traverseMazeV3(maze, grid, x + 1, y, solution) || // Right
                traverseMazeV3(maze, grid, x - 1, y, solution)) { // Left
                return true; 
            }

            // none lead to end, backtrack
            grid[x][y] = '0';
            solution.pop();
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
