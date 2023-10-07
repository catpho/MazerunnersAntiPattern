package com.mazerunners;

import com.mazerunners.maze.CharStack;
import com.mazerunners.maze.Maze;
import com.mazerunners.maze.MazeSolver;
import com.mazerunners.maze.MazeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MazeRunnersController implements Initializable {

    @FXML private Label headerLabel;
    @FXML private GridPane mazeGrid;
    @FXML private Button mazeFileChooser;
    @FXML private Button solveMaze;
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilter;
    private File file;
    private Alert messagePopupBox;
    private Alert errorPopupBox;
    private MazeUtil mazeUtil;
    private MazeSolver mazeSolver;
    private Maze maze;
    private char[][] grid;
    private CharStack solutionStack;
    private CharStack multiplePaths;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing
        extensionFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads"));
        mazeUtil = new MazeUtil();
        mazeSolver = new MazeSolver();
        messagePopupBox = new Alert(Alert.AlertType.INFORMATION);
        errorPopupBox = new Alert(Alert.AlertType.ERROR);
        mazeGrid.setStyle("-fx-background-color: inherit");
        solutionStack = new CharStack();
        multiplePaths = new CharStack();

        // gridbox - default maze
        maze = mazeUtil.importMaze(new File("src/main/resources/mazes/maze.txt"));
        grid = maze.getMazeGrid();

        Rectangle rect;
        StackPane stackPane;

        for (int i = 0; i < maze.getMazeHeight(); i++) {
            for(int j = 0; j < maze.getMazeWidth(); j++) {
                rect = new Rectangle(40, 40);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(0.5);
                Text text = new Text("");
                text.setStyle("-fx-font-size: 18px;");
                if(j == maze.getStartCoordY() && i == maze.getStartCoordX()) {

                    rect.setFill(Color.ORANGE);
                    stackPane = new StackPane();
                    text.setText("S");
                    stackPane.getChildren().addAll(rect, text);


                    mazeGrid.add(stackPane, j, i);
                } else if (j == maze.getEndCoordY() && i == maze.getEndCoordX()) {

                    rect.setFill(Color.LIGHTGREEN);
                    stackPane = new StackPane();
                    text.setText("F");
                    stackPane.getChildren().addAll(rect, text);

                    mazeGrid.add(stackPane, j, i);
                }
                else {

                    stackPane = new StackPane();
                    text.setText("");
                    stackPane.getChildren().addAll(rect, text);

                    if(grid[i][j] == '1') {
                        rect.setFill(Color.WHITE);
                    } else if (grid[i][j] == '0') {
                        rect.setFill(Color.DARKGREY);
                        text.setText("*");
                    }

                    mazeGrid.add(stackPane, j, i);
                }
            }
        }

    }

    @FXML public void selectMazeFile() {

        mazeGrid.getChildren().clear();

        file = fileChooser.showOpenDialog(null);

        if(file != null) {

            try {
                maze = mazeUtil.importMaze(file);

            grid = maze.getMazeGrid();
            StackPane stackPane;
            Rectangle rect;

            for (int i = 0; i < maze.getMazeHeight(); i++) {
                for(int j = 0; j < maze.getMazeWidth(); j++) {
                    rect = new Rectangle(40, 40);
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(0.5);
                    Text text = new Text("");
                    text.setStyle("-fx-font-size: 18px;");

                    if(j == maze.getStartCoordY() && i == maze.getStartCoordX()) {

                        rect.setFill(Color.ORANGE);
                        stackPane = new StackPane();
                        text.setText("S");
                        stackPane.getChildren().addAll(rect, text);

                        mazeGrid.add(stackPane, j, i);
                    } else if (j == maze.getEndCoordY() && i == maze.getEndCoordX()) {

                        rect.setFill(Color.LIGHTGREEN);
                        stackPane = new StackPane();
                        text.setText("F");
                        stackPane.getChildren().addAll(rect, text);

                        mazeGrid.add(stackPane, j, i);
                    }
                    else {

                        if(grid[i][j] == '1') {
                            rect.setFill(Color.WHITE);
                        } else if (grid[i][j] == '0') {
                            rect.setFill(Color.DARKGREY);
                            text.setText("*");
                        }
                        stackPane = new StackPane();
                        stackPane.getChildren().addAll(rect, text);

                        mazeGrid.add(stackPane, j, i);
                    }
                }
            }

            messagePopupBox.setTitle("Maze Importer");
            messagePopupBox.setContentText(file.getName() + " imported successfully!");
            messagePopupBox.showAndWait();
            } catch (Exception e) {
                errorPopupBox.setTitle("File Selection Error");
                errorPopupBox.setContentText("Error.  The file you selected is not in the correct format.");
                errorPopupBox.showAndWait();
            }

        } else {

            messagePopupBox.setTitle("Maze Importer");
            messagePopupBox.setContentText("Import operation canceled.");
            messagePopupBox.showAndWait();

        }
    }

    @FXML private void solveMaze() {

        mazeGrid.getChildren().clear();

        if(file != null) {
            maze = mazeUtil.importMaze(file);
            grid = maze.getMazeGrid();
        } else {
            maze = mazeUtil.importMaze(new File("src/main/resources/mazes/maze.txt"));
            grid = maze.getMazeGrid();
        }

        mazeSolver.traverseMazeV3(maze, grid, maze.getStartCoordX(), maze.getStartCoordY(), solutionStack, multiplePaths);

        StackPane stackPane;
        Rectangle rect;

        for (int i = 0; i < maze.getMazeHeight(); i++) {
            for(int j = 0; j < maze.getMazeWidth(); j++) {

                rect = new Rectangle(40, 40);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(0.5);
                Text text = new Text("");
                text.setStyle("-fx-font-size: 18px;");

                if(j == maze.getStartCoordY() && i == maze.getStartCoordX()) {

                    rect.setFill(Color.ORANGE);

                    stackPane = new StackPane();
                    text.setText("S");
                    stackPane.getChildren().addAll(rect, text);

                    mazeGrid.add(stackPane, j, i);
                } else if (j == maze.getEndCoordY() && i == maze.getEndCoordX()) {
                    rect.setFill(Color.LIGHTGREEN);

                    stackPane = new StackPane();
                    text.setText("F");
                    stackPane.getChildren().addAll(rect, text);

                    mazeGrid.add(stackPane, j, i);
                }
                else {

                    stackPane = new StackPane();
                    stackPane.getChildren().addAll(rect, text);

                    if(grid[i][j] == 'x') {
                        rect.setFill(Color.LIGHTBLUE);
                    } else if(grid[i][j] == 'v') {
                        rect.setFill(Color.LIGHTGREY);
                    } else if (grid[i][j] == '0') {
                        rect.setFill(Color.DARKGREY);
                        text.setText("*");
                    } else if (grid[i][j] == '1') {
                        rect.setFill(Color.WHITE);
                    }
                    mazeGrid.add(stackPane, j, i);
                }
            }
        }

    }
}