public class Card {
    public static final int CHANCE_CARDS = 0;
    public static final int COMMUNITY_CHEST_CARDS = 1;
    private String text;


    Card(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void perform(Player player) {
        switch (text) {
            case "Advance to Go (Collect $200)":
                player.setLocation(1);
                break;
            default:
                System.out.println("Unknown Card.");
        }
    }

    @Override
    public String toString() {
        return text;
    }
}
