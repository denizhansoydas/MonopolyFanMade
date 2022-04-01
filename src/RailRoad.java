public class RailRoad extends PropertySquare{
    RailRoad(String name, int cost) {
        super(name, cost);
    }

    @Override
    public int getRent(int[] diceVal, Player[] players, Player caller) {
        int count = 0;
        for(Player player: players){
            if(player == caller)
                continue;
            for(PropertySquare ps : player.getProperties()){
                if(ps instanceof RailRoad)
                    count++;
            }
        }
        return count;
    }
}
