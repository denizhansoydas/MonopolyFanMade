public abstract class PropertySquare extends Square{
    PropertySquare(String name, int cost){
        super(name, cost);
    }
    public abstract int getRent(int[] diceVal, Player[] players, Player caller);
}
