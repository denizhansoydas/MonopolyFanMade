public abstract class Square {
    protected String name;
    protected int cost;


    Square(String name, int cost){
        this.name = name;
        this.cost = cost;
    }
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
