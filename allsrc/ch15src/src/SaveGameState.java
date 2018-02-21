import java.io.*;

class SaveGameState {

private final static String GAME_ROOT   = "MyGame";
private final static String SAVED_GAMES = "saved";

private void saveGame() {

    File gameDirectory = createGameDirectory();

    saveGameStateTo(gameDirectory);
}

private File createGameDirectory() {

    String userHome = System.getProperty("user.home");

    File dir = new File(userHome);

    dir = new File(dir, GAME_ROOT);
    if ( ! dir.exists() ) {
        dir.mkdir();
    }

    dir = new File(dir, SAVED_GAMES);
    if ( ! dir.exists() ) {
        dir.mkdir();
    }

    return dir;
}

private void saveGameStateTo(File gameDirectory) {
    // serialize and store all game state objects to specified dir.
}

public static void main (String[] args) {
    new SaveGameState().saveGame();
}

}
