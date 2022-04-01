public class Land extends PropertySquare{
    Land(String name, int cost) {
        super(name, cost);
    }

    @Override
    public int getRent(int[] dice, Player[] players, Player caller){
        if (cost <= 2000)
            return (int) (cost * 0.4);
        else if(cost <= 3000)
            return (int) (cost * 0.3);
        else if(cost <= 4000)
            return (int) (cost * 0.35);
        return -1;
    }
}
