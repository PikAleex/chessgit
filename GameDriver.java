package A2c;
import java.io.*;

/**
 * Driver of the whole game. This is where the game starts
 * @author Alexander Sui
 * @version 1.0
 */
public class GameDriver {
    
    /** Game object */
    private static Game game;
    
    /**
     * Saves the state of the game
     * @param game Game - game object to be saved
     */
    public static void saveGame(Game game) {
        game.setNotNewGame();
        game.savePositions();
        game.saveTurn();
        try {
           FileOutputStream fileOut =
           new FileOutputStream("./3Dchess.ser");
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(game);
           out.close();
           fileOut.close();
           System.out.printf("Game saved to ./3Dchess.ser");
        } catch (IOException i) {
           System.out.println("Save error" + i);
        }
    }

    /**
     * Drives the whole program.
     * @param args String[] - initial arguments to start program
     */
    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream("./3Dchess.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
            game.createGUI();
            game.createBoards();
         } catch (IOException i) {
            game = new Game(args, true);
         } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
         }
        game.showGame(args);

    }

}
