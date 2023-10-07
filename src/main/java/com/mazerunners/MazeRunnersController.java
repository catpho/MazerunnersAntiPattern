package com.mazerunners;

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
    private MazeUtil mazeUtil;
    private MazeSolver mazeSolver;
    private Maze maze;
    private char[][] grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing
        extensionFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads"));
        mazeUtil = new MazeUtil();
        mazeSolver = new MazeSolver();
        messagePopupBox = new Alert(Alert.AlertType.INFORMATION);
        mazeGrid.setStyle("-fx-background-color: inherit");

        // gridbox

    }

    @FXML public void selectMazeFile() {
        file = fileChooser.showOpenDialog(null);

        if(file != null) {

            maze = mazeUtil.importMaze(file);
            grid = maze.getMazeGrid();
            System.out.println(maze.getMazeHeight() + " x " + maze.getMazeWidth());
            mazeGrid.setStyle("-fx-background-color: black");

            for (int i = 0; i < maze.getMazeHeight(); i++) {
                for(int j = 0; j < maze.getMazeWidth(); j++) {
                    if(j == maze.getStartCoordY() && i == maze.getStartCoordX()) {
                        Rectangle rect = new Rectangle(40, 40);
                        rect.setFill(Color.BLUE);

                        StackPane stackPane = new StackPane();
                        Text text = new Text("Start");
                        stackPane.getChildren().addAll(rect, text);


                        mazeGrid.add(stackPane, j, i);
                    } else if (j == maze.getEndCoordY() && i == maze.getEndCoordX()) {
                        Rectangle rect = new Rectangle(40, 40);
                        rect.setFill(Color.GREEN);

                        StackPane stackPane = new StackPane();
                        Text text = new Text("Finish");
                        stackPane.getChildren().addAll(rect, text);

                        mazeGrid.add(stackPane, j, i);
                    }
                    else {
                        Rectangle rect = new Rectangle(40, 40);
                        rect.setFill(grid[i][j] == '1' ? Color.WHITE : Color.BLACK);
                        mazeGrid.add(rect, j, i);
                    }
                }
            }

            messagePopupBox.setTitle("Maze Importer");
            messagePopupBox.setContentText(file.getName() + " imported successfully!");
            messagePopupBox.showAndWait();
        } else {
            messagePopupBox.setTitle("Maze Importer");
            messagePopupBox.setContentText("Import operation canceled.");
            messagePopupBox.showAndWait();
        }
    }
}