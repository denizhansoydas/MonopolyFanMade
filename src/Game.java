import java.util.ArrayList;

public class Game {
    ArrayList<Square> squares;
    Die die1;
    Die die2;
    Player player1;
    Player player2;
    Game(){
        this(new ArrayList<>());
    }
    Game(ArrayList<Square> squares){
        this.squares = squares;
        die1 = new Die();
        die2 = new Die();
        player1 = new Player();
        player2 = new Player();
    }
    public void appendSquare(Square square){
        squares.add(square);
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public void setSquares(ArrayList<Square> squares) {
        this.squares = squares;
    }
}
