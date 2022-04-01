import java.util.ArrayList;

public class Game {
    private ArrayList<Square> squares;
    private Die[] dice;
    private Player[] players;
    Game(){
        this(new ArrayList<>());
    }
    Game(ArrayList<Square> squares){
        dice = new Die[2];
        players = new Player[2];
        this.squares = squares;
        dice[0] = new Die();
        dice[1] = new Die();
        players[0] = new Player();
        players[1] = new Player();
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

    public int[] rollDice(){
        int[] res = new int[2];
        res[0] = dice[0].roll();
        res[1] = dice[1].roll();
        return res;
    }


}
