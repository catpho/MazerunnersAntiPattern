package com.mazerunners.maze;

import java.io.File;

public class MazeSolver {

    private static File exampleFile = new File("maze two solutions.txt");

    public static void traverseMazeV1(Maze maze, char[][] grid ,int x, int y, CharStack stack) {

        int maxHeightIndex = maze.getMazeHeight() - 1;
        int maxWidthIndex = maze.getMazeWidth() - 1;

        grid[x][y] = 'x';
        stack.push( x + "," + y);
        if (x == maze.getEndCoordX() && y == maze.getEndCoordY()) {
                System.out.println("The maze has been solved!");
                System.out.println("Path back:");
                while (!stack.isEmpty()) {
                    System.out.println(stack.pop());
                }
        }
        try {
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1'){
                //System.out.println("There is a path to the right: " + x + "," + (y+1));
                traverseMazeV1(maze, grid, x, y + 1, stack);

            }
            if(y - 1 >= 0 && grid[x][y-1] == '1'){
                //System.out.println("There is a path to the left: " + x + "," + (y-1));
                traverseMazeV1(maze, grid, x, y - 1, stack);
            }
            if(x - 1 >= 0 && grid[x-1][y] == '1'){
                //System.out.println("There is a path above: " + (x-1) + "," + y);
                traverseMazeV1(maze, grid, x - 1, y, stack);

            }
            if(x + 1 <= maxWidthIndex && grid[x+1][y] == '1'){
                //System.out.println("There is a path below: " + (x+1) + "," + y);
                traverseMazeV1(maze, grid, x + 1, y, stack);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        grid[x][y] = '1';
        if(!stack.isEmpty()) {
            stack.pop();
        }
    }

    public static boolean traverseMazeV2(Maze maze, char[][] grid ,int x, int y, CharStack solution, CharStack pathsAvailable) {

        int maxHeightIndex = maze.getMazeHeight() - 1;
        int maxWidthIndex = maze.getMazeWidth() - 1;

        grid[x][y] = 'x';
        solution.push( x + "," + y);
        if (x == maze.getEndCoordX() && y == maze.getEndCoordY()) {
            System.out.println("The maze has been solved!");
            System.out.println("Path back:");
            while (!solution.isEmpty()) {
                System.out.println(solution.pop());
            }

            System.out.println("Multiple Paths:");
            while (!pathsAvailable.isEmpty()) {
                System.out.println(pathsAvailable.pop());
            }

            System.out.println("Map:");
            for(int i = 0; i < maxHeightIndex + 1; i++ ) {
                for(int j = 0; j < maxWidthIndex + 1; j++ ) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
            return true;
        }
        try {
            // y + 1, y - 1
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && y - 1 >= 0 && grid[x][y-1] == '1') {
                //System.out.println("Multiple paths!");
                pathsAvailable.push( x + "," + y);
            }

            // y + 1, x + 1
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && x + 1 <= maxWidthIndex && grid[x+1][y] == '1') {
                //System.out.println("Multiple paths!");
                pathsAvailable.push( x + "," + y);
            }

            // y + 1, x - 1
            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && x - 1 >= 0 && grid[x-1][y] == '1') {
                //System.out.println("Multiple paths!");
                pathsAvailable.push( x + "," + y);
            }

            // y - 1, x + 1
            if(y - 1 >= 0 && grid[x][y-1] == '1' && x + 1 <= maxWidthIndex && grid[x+1][y] == '1') {
                //System.out.println("Multiple paths!");
                pathsAvailable.push( x + "," + y);
            }

            // y - 1, x - 1
            if(y - 1 >= 0 && grid[x][y-1] == '1' && x - 1 >= 0 && grid[x-1][y] == '1') {
                //System.out.println("Multiple paths!");
                pathsAvailable.push( x + "," + y);
            }

            // x - 1, x + 1
            if(x - 1 >= 0 && grid[x-1][y] == '1' && x + 1 <= maxWidthIndex && grid[x+1][y] == '1') {
                //System.out.println("Multiple paths!");
                pathsAvailable.push( x + "," + y);
            }


            if(y + 1 <= maxHeightIndex && grid[x][y+1] == '1' && traverseMazeV2(maze, grid, x, y + 1, solution, pathsAvailable)){
                //System.out.println("There is a path to the right: " + x + "," + (y+1));
                //traverseMazeV2(maze, grid, x, y + 1, stack);
                return true;

            }
            if(y - 1 >= 0 && grid[x][y-1] == '1' && traverseMazeV2(maze, grid, x, y - 1, solution, pathsAvailable)){
                //System.out.println("There is a path to the left: " + x + "," + (y-1));
                //traverseMazeV2(maze, grid, x, y - 1, stack);
                return true;
            }
            if(x - 1 >= 0 && grid[x-1][y] == '1' && traverseMazeV2(maze, grid, x - 1, y, solution, pathsAvailable)){
                //System.out.println("There is a path above: " + (x-1) + "," + y);
                //traverseMazeV2(maze, grid, x - 1, y, stack);
                return true;

            }
            if(x + 1 <= maxWidthIndex && grid[x+1][y] == '1' && traverseMazeV2(maze, grid, x + 1, y, solution, pathsAvailable)){
                //System.out.println("There is a path below: " + (x+1) + "," + y);
                //traverseMazeV2(maze, grid, x + 1, y, stack);
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        // If no path is found, it'll backtrack
        System.out.println("Current: " + x + "," + y);
        System.out.println("Whoops! Heading back ...");
        grid[x][y] = '1';
        if(!solution.isEmpty()) {
            solution.pop();
        }
        return false;
    }

    public boolean traverseMazeV3(Maze maze, char[][] grid ,int x, int y, CharStack solution, CharStack pathsAvailable) {

        int maxHeightIndex = maze.getMazeHeight() - 1;
        int maxWidthIndex = maze.getMazeWidth() - 1;

        grid[x][y] = 'x';
        solution.push( x + "," + y);
        if (x == maze.getEndCoordX() && y == maze.getEndCoordY()) {
            /*System.out.println("The maze has been solved!");
            System.out.println("Path back:");

            // Prints out solution
            while (!solution.isEmpty()) {
                System.out.println(solution.pop());
            }

            // prints out locations where multiple path options exist
            System.out.println("Multiple Paths:");
            while (!pathsAvailable.isEmpty()) {
                System.out.println(pathsAvailable.pop());
            }

            // Prints out map
            System.out.println("Map:");
            for(int i = 0; i < maxHeightIndex + 1; i++ ) {
                for(int j = 0; j < maxWidthIndex + 1; j++ ) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }*/
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
        //Maze maze = MazeUtil.importMazeFromFile(exampleFile);
        MazeUtil mazeUtil = new MazeUtil();
        Maze maze = mazeUtil.importMaze(exampleFile);
        CharStack mazeSolution = new CharStack();
        CharStack mazePaths = new CharStack();

        char[][] grid = maze.getMazeGrid();

         //traverseMazeV3(maze, grid, maze.getStartCoordX(),maze.getStartCoordY(), mazeSolution, mazePaths);
    }

}
