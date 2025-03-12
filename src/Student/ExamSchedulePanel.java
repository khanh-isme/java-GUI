package Student;

import project.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExamSchedulePanel  extends JPanel {
    public ExamSchedulePanel(Bank bank){
        setLayout( new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Exeam Schedule", JLabel.CENTER);
        title.setFont( new Font("Arial", Font.BOLD,22));
        add(title,BorderLayout.NORTH);

        Font textFont = new Font("Arial", Font.PLAIN, 16);


        List<Subject> subjects = bank.getSubjects();
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        for( Subject subject : subjects){
            JLabel label = new JLabel(subject.getName()+ ":" + subject.getNgayThi());
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            label.setFont(textFont);
            infoPanel.add(label);
        }
        add(infoPanel);


    }
}
