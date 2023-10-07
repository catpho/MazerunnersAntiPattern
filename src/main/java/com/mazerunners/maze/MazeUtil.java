package com.mazerunners.maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MazeUtil {

    public Maze importMaze(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            Maze maze = new Maze();

            // Set Maze Dimensions
            String line = reader.readLine();
            setMazeDimensions(maze, line);
            //System.out.println("Maze Size: " + maze.getMazeWidth() + " x " + maze.getMazeHeight());

            // Set Start Coordinates
            line = reader.readLine();
            setStartCoordinates(maze, line);
            //System.out.println("Start Coordinates: " + maze.getStartCoordX() + ", " + maze.getStartCoordY());

            // Set End Coordinates
            line = reader.readLine();
            setEndCoordinates(maze, line);
            //System.out.println("End Coordinates: " + maze.getEndCoordX() + ", " + maze.getEndCoordY());

            // Reads maze grid from file and recreates it in an array.
            buildMazeGrid(maze, reader);

            return maze;
        } catch (IOException e) {
            System.out.println("Error reading maze file");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in maze file");
        } catch (Exception e) {
            System.out.println("Error in reading file.");
        }
        return null;
    }

    private static void setMazeDimensions(Maze maze, String line) throws NumberFormatException{
        String[] dimensions = line.split(" ");
        maze.setMazeWidth(Integer.parseInt(dimensions[0]));
        maze.setMazeHeight(Integer.parseInt(dimensions[1]));
    }

    private static void setStartCoordinates(Maze maze, String line) throws NumberFormatException{
        String[] coordinates = line.split(" ");
        maze.setStartCoordX(Integer.parseInt(coordinates[0]));
        maze.setStartCoordY(Integer.parseInt(coordinates[1]));
    }

    private static void setEndCoordinates(Maze maze, String line) throws NumberFormatException{
        String[] coordinates = line.split(" ");
        maze.setEndCoordX(Integer.parseInt(coordinates[0]));
        maze.setEndCoordY(Integer.parseInt(coordinates[1]));
    }

    private static void buildMazeGrid(Maze maze, BufferedReader reader) throws IOException, NumberFormatException {
        char[][] grid = new char[maze.getMazeWidth()][maze.getMazeHeight()];
        String line;
        String[] mazeData;
        int row = 0;

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            mazeData = line.split(" ");
            for (int i = 0; i < mazeData.length; i++) {
                grid[row][i] = mazeData[i].charAt(0);
                //System.out.print(grid[row][i] + " ");
            }
            //System.out.println();
            row++;
        }

        maze.setMazeGrid(grid);
    }
}
