public class Player extends User{
    public static final int INITIAL_MONEY = 15000;
    private int location; // start: 1
    int jailCount;
    Player(Game game){
        super(game,INITIAL_MONEY);
        location = 1;
        jailCount = 0;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;

    }

    public int getJailCount() {
        return jailCount;
    }

    public void setJailCount(int jailCount) {
        this.jailCount = jailCount;
    }
    //    public void performAction(int location){
//
//    }
}
