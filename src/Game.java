import java.util.ArrayList;

public class Game {
    ArrayList<Square> squares;
    Game(){
        squares = new ArrayList<>();
    }
    Game(ArrayList<Square> squares){
        this.squares = squares;
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
