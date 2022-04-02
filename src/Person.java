import java.util.ArrayList;

public abstract class Person {
    protected int money;
    protected Game game;
    protected ArrayList<PropertySquare> properties;
    protected String name;
    Person(Game game, int money){
        this.money = money;
        this.game = game;
        properties = new ArrayList<>();
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
        this.money -= money;
        return true;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ArrayList<PropertySquare> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<PropertySquare> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
