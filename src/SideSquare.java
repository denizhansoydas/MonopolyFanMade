public class SideSquare extends Square{
    public static final int GO_LAND_MONEY = 200;
    public static final int JAIL_COST = 0;
    public static final int GO_TO_JAIL_COST = 0;
    public static final int FREE_PARKING_COST = 0;

    public static final String GO = "go";
    public static final String JAIL = "jail";
    public static final String GO_TO_JAIL = "go to jail";
    public static final String FREE_PARKING = "free parking";
    public static final int[] JAIL_COUNT = {0,0};

    public static final int JAIL_TURNS = 3;

    SideSquare(String name, int cost) {
        super(name, cost);
    }

    public void perform(Player player){
        switch (name){
            case "go":
                player.takeMoney(GO_LAND_MONEY);
                break;
            case "free parking":
                break;
            case "go to jail":
                player.setLocation(Game.JAIL_LOCATION);
                break;
            case "jail":
                if(player.getJailCount() >= JAIL_TURNS)
                    System.out.println("release");
                else{
                    System.out.println("wait");
                    player.setJailCount(player.getJailCount() + 1);
                }break;
            default:
                System.out.println("Unknown Side");

        }
    }
}
