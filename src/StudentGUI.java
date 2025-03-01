import com.sun.nio.sctp.SctpStandardSocketOptions;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI {
    private JFrame frame;
    private Student student;
    private JPanel contentPanel;

    public StudentGUI() {
        //tạm thời chưa thêm học sinh
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900);
        frame.setLayout(new BorderLayout());

        // Sidebar panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(7, 1));
        sidebar.setBackground(new Color(30, 90, 180));
        sidebar.setPreferredSize(new Dimension(200, frame.getHeight()));

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Menu items
        String[] menuItems = {"Take Quiz", "review Info", "view ExamSchedule", "View Detailed Quiz History", "register Subject", "edit account", "Logout"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setFocusPainted(false);
            button.setBackground(new Color(60, 120, 200));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.addActionListener(new MenuActionListener(item));
            sidebar.add(button);
        }

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class MenuActionListener implements ActionListener {
        private String menuName;

        public MenuActionListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(menuName == "Take Quiz"){
                contentPanel.removeAll();
                contentPanel.add(new QuizPanel(), BorderLayout.CENTER);

                contentPanel.revalidate();//yêu cầu swing tính toán lại bố cục để hiển thị
                contentPanel.repaint();//yêu cầu hệ thống vẽ lại nội dung của contentPanel
            }


        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGUI::new);
    }


}
