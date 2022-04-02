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
    SideSquare(String name){
        this(name, 0);
        if(name.equals(GO))
            cost = GO_LAND_MONEY;
    }

    public String perform(Player player){
        String res = null;
        switch (name){
            case "go":
                player.takeMoney(GO_LAND_MONEY);
                res = "went to go";
                break;
            case "free parking":
                res = "is in Free Parking";
                player.setInFreeParking(true);
                break;
            case "go to jail":
                res = "went to jail";
                player.setLocation(Game.JAIL_LOCATION);
                player.setJailCount(0);
                break;
            case "jail":
                if(player.getJailCount() >= JAIL_TURNS - 1)
                    player.setJailCount(-1);
                else{
                    if(player.getJailCount() == -1){
                        res = "went to jail";
                        player.setJailCount(0);
                    }else{
                        res = "in jail (count=" + player.jailCount + ")";
                        player.setJailCount(player.getJailCount() + 1);
                    }
                }break;
            default:
                System.out.println("Unknown Side");
        }
        return res;
    }
}
