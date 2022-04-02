import static java.lang.Integer.sum;

public class Player extends Person {
    public static final int INITIAL_MONEY = 15000;
    private int location; // start: 1
    int jailCount;
    boolean inFreeParking;

    Player(Game game){
        super(game,INITIAL_MONEY);
        location = 1;
        jailCount = -1;
        inFreeParking = false;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
    public String goTo(int location){
        this.location = location;
        String res = null;
        //triggerAction(location);
        if(game.getSquares()[location - 1] instanceof PropertySquare){
            if(game.getBanker().getProperties().contains((PropertySquare)game.getSquares()[location - 1])){
                res = buyProperty(location);
            }
            else{
                for(Player player : game.getPlayers()){
                    if(player.getProperties().contains((PropertySquare)game.getSquares()[location - 1])){
                        payRent(player, game.getSquares()[location - 1].getCost());
                        res = "paid rent for " + game.getSquares()[location - 1];
                    }
                }
            }
        }
        else if(game.getSquares()[location - 1] instanceof ActionSquare){
            res = ((ActionSquare)game.getSquares()[location - 1]).drawCard(game, this);
        }
        else if(game.getSquares()[location - 1] instanceof SideSquare){
            res = ((SideSquare)game.getSquares()[location - 1]).perform(this);
        }
        return res;
    }

    public int getJailCount() {
        return jailCount;
    }

    public void setJailCount(int jailCount) {
        this.jailCount = jailCount;
    }
    public String buyProperty(int location){
        PropertySquare square = (PropertySquare)game.getSquares()[location - 1];
        if(money >= square.getCost()) {
            giveMoney(square.getCost());
            properties.add((PropertySquare)game.getSquares()[location - 1]);
            game.getBanker().getProperties().remove(square);
            game.getBanker().setMoney(game.getBanker().getMoney() + game.getSquares()[location - 1].getCost());
            return "bought " + square.getName();
        }
        return "not enough to buy";
    }
    public void payRent(Player player, int cost){
        if(money < cost){
            System.out.println("goes bankrupt");
        }
        else{
            giveMoney(cost);
            player.takeMoney(cost);
        }
    }
    public String move(int dice){
        //int[] dice = game.rollDice();
        int proceedTo = dice + location;
        if(proceedTo > Game.SQUARE_COUNT){
            proceedTo -= Game.SQUARE_COUNT;
        }
        if(jailCount != -1 && jailCount < SideSquare.JAIL_TURNS){
            jailCount++;
            return name + "\t" + dice + "\t" + location + "\t" + game.getPlayers()[0].getMoney() + "\t" + game.getPlayers()[1].getMoney() + "\t" + name + "\t" + "in jail (count=" + jailCount + ")";
        }
        if(isInFreeParking())
            inFreeParking = false;
        String process = goTo(proceedTo);

        return name + "\t" + dice + "\t" + location + "\t" + game.getPlayers()[0].getMoney() + "\t" + game.getPlayers()[1].getMoney() + "\t" + name + "\t" + process;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name=" + name +
                ", money='" + money + '\'' +
                ", location=" + location +
                '}';
    }

    public boolean isInFreeParking() {
        return inFreeParking;
    }

    public void setInFreeParking(boolean inFreeParking) {
        this.inFreeParking = inFreeParking;
    }
}
