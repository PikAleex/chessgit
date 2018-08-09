package A2c;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.*;

/**
 * The GUI that will display everything.
 * @author Alexander Sui
 * @version 1.0
 */
public class GUI extends Application {

    /** The game object */
    static private Game game;
    /** Game boards */
    private Board[] boards;
    /** List of nodes on the board */
    private ObservableList<Node> onBoard;
    
    /**
     * Creates and colours the default board tiles
     * @param i int - x coordinate
     * @param j int - y coordinate
     * @return the coloured tile
     */
    public Rectangle setBoardTile(int i, int j) {
        Rectangle boardTile = new Rectangle(50, 50);
        boardTile.setFill(((i + j) % 2 == 0) ? Color.WHITE : Color.GREY);
        return boardTile;
    }
    
    /**
     * Instantiate the game and board
     * @param game Game - game object
     * @param board Board - game board
     */
    public void setUp(Game game, Board[] boards) {
        this.game = game;
        this.boards = boards;
    }
    
    /**
     * Saves the state of the game
     * @param a ActionEvent - the click of the Save option
     */
    public void save(ActionEvent a) {
        GameDriver.saveGame(game);
    }
    
    /**
     * Menu item that exits the game
     * @param a ActionEvent - click of the item
     */
    public void exit(ActionEvent a) {
        System.exit(0);
    }
    
    /**
     * Dialog box for when the wrong player tries to play
     * @param player String - player whose turn it is
     */
    public void notYourTurn(String player) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Incorrect selection");
        alert.setHeaderText(null);
        alert.setContentText("It is " + player + "'s turn.");
        alert.showAndWait();
    }
    
    /**
     * Dialog box when player does an illegal move
     */
    public void invalidMove() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Illegal Movement");
        alert.setHeaderText(null);
        alert.setContentText("Invalid move!");
        alert.showAndWait();
    }
    
    /**
     * Displays dialog box that says a player has won
     * @param player String - the player that won
     */
    public void endGame(String player) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("End of Game");
        alert.setHeaderText(player + " player has won");
        alert.setContentText("The game will now exit. Please delete previous save file for a new game.");
        alert.showAndWait();
        System.exit(0);
    }
    
    /**
     * Creates the 3 boards and displays them along with a toolbar
     * @param arg0 unused
     * @throws Exception any exceptions when displaying board
     */
    @Override
    public void start(Stage arg0) throws Exception {
        MenuBar menubar = new MenuBar();
        Menu file = new Menu("File");
        menubar.getMenus().add(file);
        MenuItem saveGame = new MenuItem("Save");
        file.getItems().add(saveGame);
        MenuItem exitGame = new MenuItem("Exit");
        file.getItems().add(exitGame);
        
        saveGame.setOnAction(this::save);
        exitGame.setOnAction(this::exit);
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k <= 2; k++) {
                    if (k == 0)
                        onBoard = game.getBotPane().getChildren();
                    else if (k == 1)
                        onBoard = game.getMidPane().getChildren();
                    else if (k == 2)
                        onBoard = game.getTopPane().getChildren();
                    for (Node node : onBoard) {
                        if (GridPane.getRowIndex(node) == j && GridPane.getColumnIndex(node) == i && (node instanceof Tile)) {
                            ((Tile)node).setSymbol(((Tile)node).getPiece());
                        }
                    }
                }
            }
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        if (game.checkGame()) {
            alert.setTitle("Starting New Game...");
            alert.setHeaderText("New Game");
            alert.setContentText("No save file detected, starting new game.");
        } else {
            alert.setTitle("Loading Existing Game...");
            alert.setHeaderText("Save Detected");
            alert.setContentText("Save file detected. To start a new game, please delete the save file and restart this game.");
        }
        
        alert.showAndWait();
        
        GridPane theBoards = new GridPane();
        GridPane together = new GridPane();
        theBoards.setHgap(10);
        theBoards.setStyle("-fx-background-color: firebrick");
        theBoards.add(game.getBotPane(), 0, 0);
        theBoards.add(game.getMidPane(), 1, 0);
        theBoards.add(game.getTopPane(), 2, 0);
        
        together.add(menubar, 0, 0);
        together.add(theBoards, 0, 1);
        
        Scene scene = new Scene(together, 1220, 425);
        
        Stage stage = new Stage();
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * Launch Javafx things.
     * @param args String[] - arguments from main of driver class
     */
    public void display(String[] args) {
        launch(args);
    }
 
}
