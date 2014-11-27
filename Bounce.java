import java.awt.*;
import javax.swing.*;

public class Bounce extends JFrame {
    public Bounce() {
        super("Tennis");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BouncePanel2 boing = new BouncePanel2();
        Container pane = getContentPane();
        pane.add(boing);
        setContentPane(pane);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        Bounce frame = new Bounce();
    }
}