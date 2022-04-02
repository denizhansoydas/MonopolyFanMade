public class ActionSquare extends Square{
    ActionSquare(String name, int cost){
        super(name, cost);
    }
    public String drawCard(Game game, Player caller){
        String str = null;
        Card card = null;
        if(name.equals("community chest")){
            str = "draw Community Chest";
            card = (Card) game.getCards()[0].get(0);
            if(card.getText().equals("Advance to Go (Collect $200)")){
                caller.setLocation(1);
                caller.setMoney(caller.getMoney() + SideSquare.GO_LAND_MONEY);
                str += " -advance to go";
            }
        }
        else{
            card = (Card) game.getCards()[1].get(0);
            if(card.getText().equals("Advance to Go (Collect $200)")){
                caller.setLocation(1);
                caller.setMoney(caller.getMoney() + SideSquare.GO_LAND_MONEY);
                str = "advance to go (collect 200)";
            }
        }


        return str;
    }

}
