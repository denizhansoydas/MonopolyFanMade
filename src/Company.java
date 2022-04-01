public class Company extends PropertySquare{
    Company(String name, int cost) {
        super(name, cost);
    }

    @Override
    public int getRent(int[] diceVal, Player[] players, Player caller) {
        return 4 * diceVal[0] * diceVal[1];
    }

}
