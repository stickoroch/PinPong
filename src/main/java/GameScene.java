import java.util.List;
import java.util.Random;

public class GameScene {

    private Ball ball;
    private Random rnd;
    private Racket r1;
    private Racket r2;


    public GameScene(){
        rnd = new Random(1);
        ball = new Ball();
        ball.y = 0.5;
        ball.x = 0.5;
        ball.dy = rnd.nextDouble(-0.001, 0.001) + 0.001;
        ball.dx = rnd.nextDouble(-0.003, 0.003) + 0.001;
        r1 = new Racket();
        r2 = new Racket();
    }

    public Ball getBall(){
        return ball;
    }

    public Racket getRacket1(){
        return r1;
    }

    public Racket getRacket2(){
        return r2;
    }

    public void restart() {
        ball.y = 0.5;
        ball.x = 0.5;
        int as = allScore();
        ball.dy = rnd.nextDouble(-(0.001 + as*0.0005), 0.001 + as*0.0005);
        ball.dx = rnd.nextDouble(-(0.003 + as*0.001), 0.003 + as*0.001);
    }

    private int allScore(){
        return getRacket2().score + getRacket1().score;
    }
}
