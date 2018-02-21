import java.io.*;

public class ChessGameState implements Serializable {

private ChessBoard theBoard;
private ChessPlayer white;
private ChessPlayer black;
private int fiftyMovesCounter;

}

class ChessPlayer implements Serializable {
    private String name;
}

class ChessBoard implements Serializable {
    // private ChessPiece[][] squares;
}
