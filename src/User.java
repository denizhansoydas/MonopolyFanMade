public abstract class User {
    private int money;
    private Game game;
    User(Game game, int money){
        this.money = money;
        this.game = game;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public void takeMoney(int money){
        this.money += money;
    }
    public boolean giveMoney(int money){
        if(this.money < money)
            return false;
        this.money += money;
        return true;
    }
}
