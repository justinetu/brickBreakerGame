import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
public class BrickBreaker extends JFrame{
    

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        frame.setBounds(10, 10, 700, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Brick Breaker");
        frame.setResizable(false);
        Gameplay gameplay = new Gameplay();
        frame.add(gameplay);
        
    }
    
}
