import javax.swing.*;
import java.awt.*;

public class Mypanel extends JPanel {
    public  Mypanel( String menuName){
        setLayout(new BorderLayout());
        JLabel label = new JLabel("You selected: " + menuName, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
