import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationGUI extends JFrame {

    public ApplicationGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu dataMenu = new JMenu("Data");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem logoutItem = new JMenuItem("Logout");

        menuBar.add(fileMenu);
        menuBar.add(dataMenu);
        menuBar.add(helpMenu);
        menuBar.add(Box.createHorizontalGlue()); // Right-align logout
        menuBar.add(logoutItem);

        // Add database ID
        JLabel dbIdLabel = new JLabel("Database ID: DB_12345");
        menuBar.add(dbIdLabel);

        // Left panel with buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "Forms Start", "Date Table", "My Data",
                "Items", "Sample Page", "Data"
        };

        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);
            leftPanel.add(btn);
        }

        // Main content area with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sample Page", new JPanel());
        tabbedPane.addTab("Data View", new JScrollPane(new JTable()));

        // Comments area
        JTextArea commentsArea = new JTextArea("Comments:");
        commentsArea.setRows(4);
        JScrollPane commentsScroll = new JScrollPane(commentsArea);

        // Add components to frame
        add(menuBar, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);
        add(commentsScroll, BorderLayout.SOUTH);

        // Logout action
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        ApplicationGUI.this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApplicationGUI().setVisible(true);
            }
        });
    }
}