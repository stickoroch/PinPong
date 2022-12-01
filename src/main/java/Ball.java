public class Ball {
    public double x;
    public double y;
    public double dx;
    public double dy;
    public int lastPlayer = -1;

    public void step(){
        x += dx;
        y += dy;
    }
}
