import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        initUI();
    }

    private void initUI() {

        add(new Game());

        setSize(500, 500);

        setTitle("PingPong");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });



    }
}

