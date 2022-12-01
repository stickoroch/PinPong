import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable , KeyListener {

    private Thread thread;
    private final int DELAY = 10;
    private double time = 0.0;

    private GameScene scene;


    public Game(){
        scene = new GameScene();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int screenSize =  Math.min(getHeight(), getWidth());
        int startXPoint = (getWidth() - screenSize) / 2 ;

        g.setColor(Color.BLACK);
        g.fillRect(startXPoint, 0, screenSize, screenSize);

        g.setColor(Color.RED);

        int ballX = (int) ((scene.getBall().x - 0.02) * screenSize) + startXPoint;
        int ballY = (int) ((scene.getBall().y - 0.02) * screenSize);

        g.fillRect(ballX, ballY, (int)(0.04*screenSize), (int)(0.04*screenSize));

        g.setColor(Color.WHITE);

        int r1p = (int) ((scene.getRacket1().pos - scene.getRacket1().length / 2.0) * screenSize) + startXPoint;
        int r2p = (int) ((scene.getRacket2().pos - scene.getRacket2().length / 2.0) * screenSize) + startXPoint;

        g.fillRect(r1p, (int)(0.9 *screenSize), (int)(scene.getRacket1().length*screenSize), (int)(0.005*screenSize)+1);
        g.fillRect(r2p, (int)(0.1 *screenSize), (int)(scene.getRacket2().length*screenSize), (int)(0.005*screenSize)+1);

        g.drawString(scene.getRacket1().score +"", (int)(startXPoint + screenSize * 0.1), (int)(screenSize * 0.9));
        g.drawString(scene.getRacket2().score +"", (int)(startXPoint + screenSize * 0.1), (int)(screenSize * 0.1));
    }

    private void tick() {
        if(time < 1)return;


        if(scene.getRacket1().pos >= 1.0){
            scene.getRacket1().pos = 1.0;
        }
        if(scene.getRacket1().pos <= 0.0){
            scene.getRacket1().pos = 0.0;
        }


        Ball ball = scene.getBall();

        scene.getRacket2().pos = ball.x;

        double rack_p1 = scene.getRacket1().pos - scene.getRacket1().length / 2.0;
        double rack_p2 = rack_p1 + scene.getRacket1().length;

        if((ball.y + ball.dy + 0.02 >= 0.9)
        && ball.x >= rack_p1 && ball.x <= rack_p2){ //bounce p1
            ball.dy = -(ball.dy * 1.2);
            ball.dx *= 1.2;
            ball.lastPlayer = 1;
        }

        rack_p1 = scene.getRacket2().pos - scene.getRacket2().length / 2.0;
        rack_p2 = rack_p1 + scene.getRacket2().length;

        if((ball.y + ball.dy - 0.02 <= 0.1)
                && ball.x >= rack_p1 && ball.x <= rack_p2){ //bounce p2
            ball.dy = -(ball.dy * 1.2);
            ball.dx *= 1.2;
            ball.lastPlayer = 2;
        }

        if(ball.y +0.02 >= 0.99){
            scene.getRacket2().score++;
            System.out.println("p2");
            time = 0;
            scene.restart();
        }

        if(ball.y - 0.02 <= 0.01){
            scene.getRacket1().score++;
            System.out.println("p1");
            time = 0;
            scene.restart();
        }

        if(ball.x + ball.dx + 0.02 >= 1.0 || ball.x + ball.dx - 0.02 <= 0.0 ) {
            ball.dx = -ball.dx;
        }

        scene.getBall().step();

    }

    @Override
    public void run() {

        long startTime, timeDiff, sleep;

        startTime = System.currentTimeMillis();

        while (true) {
            tick();
            repaint();

            timeDiff = System.currentTimeMillis() - startTime;
            sleep = DELAY - timeDiff;

            time += 0.01;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            startTime = System.currentTimeMillis();
        }
    }


    @Override
    public void addNotify() {
        super.addNotify();

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
            scene.getRacket1().pos -=0.02;
        }
        if(c == KeyEvent.VK_RIGHT){
            scene.getRacket1().pos +=0.02;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
          scene.getRacket1().pos -=0.07;
        }
        if(c == KeyEvent.VK_RIGHT){
            scene.getRacket1().pos +=0.07;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
