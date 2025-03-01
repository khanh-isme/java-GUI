import javax.swing.*;
import java.awt.*;

public class ShowQuizPanel extends JPanel {


    public ShowQuizPanel(){
         setLayout( new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel containerpanel = new JPanel();
        containerpanel.setLayout(new GridLayout(4 ,1 ,20,20));
        for (int i=0 ; i <=4 ;i++){
            JButton button = new JButton("Quiz 1");
            containerpanel.add( button, BorderLayout.CENTER);
        }

    }

}
