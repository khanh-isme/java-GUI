package Student;
import project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShowQuizPanel extends JPanel {
    private Quiz quiz;
    private  Student student;
    private AtomicBoolean flag;

    public ShowQuizPanel(Student student, Bank bank, AtomicBoolean flag){
        this.student= student;
        this.flag = flag;

        setLayout( new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel containerpanel = new JPanel();
        containerpanel.setLayout(new GridLayout(4 ,1 ,20,20));

        List<Subject> subjects = bank.getSubjects();

        for ( Subject subject : subjects){
            JButton button = new JButton(subject.getName());
            button.setBackground( Color.gray);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 14));


            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    removeAll();
                    add(new QuizPanel(subject.getRandomQuiz(),student,flag), BorderLayout.CENTER);
                    revalidate();//yêu cầu swing tính toán lại bố cục để hiển thị
                    repaint();//yêu cầu hệ thống vẽ lại nội dung của contentPanel
                }
            });

            containerpanel.add( button);
        }

        add(containerpanel);

    }

}
