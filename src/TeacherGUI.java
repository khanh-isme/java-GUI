
import Teacher.*;
import project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatClientProperties;

public class TeacherGUI {
    private JFrame frame;
    private Teacher teacher;
    private Ssystem system;
    private Bank bank;
    private JPanel contentPanel;
    private AtomicBoolean flag = new AtomicBoolean(false);

    public TeacherGUI(Ssystem system, Bank bank, Teacher teacher) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.system = system;
        this.teacher = teacher;
        this.bank = bank;

        initialize();
    }

    private void initialize() {
        frame = new JFrame("Teacher Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel sidebar = createSidebar();
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 246, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(236, 240, 241));
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(54, 57, 63));
        sidebar.setPreferredSize(new Dimension(260, frame.getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel title = new JLabel("TEACHER MENU", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        sidebar.add(title);

        String[] menuItems = {
                "Create Quiz", "Information Teacher", "View Bank",
                "Bank Manager", "User Management", "Statistic", "Log Out"
        };

        for (String item : menuItems) {
            JButton button = createSidebarButton(item);
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        return sidebar;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 68, 75));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(90, 100, 110));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(64, 68, 75));
            }
        });

        button.addActionListener(new MenuActionListener(text));
        return button;
    }

    private class MenuActionListener implements ActionListener {
        private final String menuName;

        public MenuActionListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (flag.get()) return;

            switch (menuName) {
                case "Create Quiz":
                    setContentPanel(new CreateQuizPanel(bank, teacher));
                    break;
                case "Information Teacher":
                    setContentPanel(new TeacherEditPanel(teacher));
                    break;
                case "View Bank":
                    setContentPanel(new BankInfoPanel(bank));
                    break;
                case "Bank Manager":
                    setContentPanel(new BankManagerPanel(bank));
                    break;
                case "User Management":
                    setContentPanel(new UserManagementPanel(system, bank));
                    break;
                case "Statistic":
                    setContentPanel(new StudentScorePanel(system, bank));
                    break;
                case "Log Out":
                    frame.dispose();
                    SwingUtilities.invokeLater(() -> {
                        Login loginFrame = new Login(system, bank);
                        loginFrame.setLocationRelativeTo(null);
                        loginFrame.setVisible(true);
                    });
                    break;
            }
        }
    }

    private void setContentPanel(JPanel newPanel) {
        contentPanel.removeAll();
        contentPanel.add(newPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
