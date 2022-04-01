public class Die {
    public final int sideCount;
    public static final int defaultSideCount = 6;

    Die(){
        sideCount = defaultSideCount;
    }
    Die(int sideCount){
        this.sideCount = sideCount;
    }
    public int roll(){
        return (int) ((Math.random() * (sideCount - 1)) + 1);
    }
}
