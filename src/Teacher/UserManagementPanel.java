package Teacher;
import BUS.*;
import DAO.RegisteredSubjectDAO;
import DAO.ResultsDAO;
import project.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserManagementPanel extends JPanel {
    private Ssystem ssystem;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private Bank bank;
    private Student student;
    private Teacher teacher;

    private JTextField searchField;
    private JComboBox<String> filterBox;


    public UserManagementPanel(Ssystem ssystem, Bank bank) {
        this.ssystem = ssystem;
        this.bank = bank;
        setLayout(new BorderLayout());

        // Top Panel - Search & Filter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        filterBox = new JComboBox<>(new String[]{"All", "Student", "Teacher"});

        topPanel.add(new JLabel("Tìm kiếm:"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("Phân loại:"));
        topPanel.add(filterBox);

        add(topPanel, BorderLayout.NORTH);


        // Table Model
        String[] columnNames = {"Username", "Role", "ID/Lớp", "Ngày sinh/Quizs"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xoá");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> showUserDialog(null));
        editButton.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                User user = ssystem.getUsers().get(row);
                showUserDialog(user);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một người dùng để sửa.");
            }
        });

        deleteButton.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                ssystem.getUsers().remove(row);
                refreshTable();
            }
        });

        // Cập nhật bảng mỗi khi thay đổi tìm kiếm hoặc phân loại
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refreshTable(); }
        });

        filterBox.addActionListener(e -> refreshTable());

    }

    private void refreshTable() {
        String keyword = searchField.getText().trim().toLowerCase();
        String filterRole = (String) filterBox.getSelectedItem();

        tableModel.setRowCount(0);

        for (User user : ssystem.getUsers()) {
            boolean matchesSearch = user.getUsername().toLowerCase().contains(keyword);
            boolean matchesFilter = filterRole.equals("All") ||
                    (filterRole.equals("Student") && user instanceof Student) ||
                    (filterRole.equals("Teacher") && user instanceof Teacher);

            if (matchesSearch && matchesFilter) {
                if (user instanceof Student) {
                    Student s = (Student) user;
                    tableModel.addRow(new Object[]{s.getUsername(), "Student", s.getLop(), s.getNgaySinh()});
                } else if (user instanceof Teacher) {
                    Teacher t = (Teacher) user;
                    tableModel.addRow(new Object[]{t.getUsername(), "Teacher", t.getTeacherid(), "Quizzes: " + t.getQuizzesCreated().size()});
                }
            }
        }
    }



    private void showUserDialog(User userToEdit) {
        JDialog dialog = new JDialog((Frame) null, "User", true);
        dialog.setLayout(new GridLayout(0, 2));

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Student", "Teacher"});
        JTextField idOrClassField = new JTextField();
        JTextField extraField = new JTextField();

        dialog.add(new JLabel("Username:"));
        dialog.add(usernameField);
        dialog.add(new JLabel("Password:"));
        dialog.add(passwordField);
        dialog.add(new JLabel("Role:"));
        dialog.add(roleBox);
        dialog.add(new JLabel("Lớp (Student) hoặc ID (Teacher):"));
        dialog.add(idOrClassField);
        dialog.add(new JLabel("Ngày sinh (Student) hoặc Quizzes (bỏ qua):"));
        dialog.add(extraField);

        if (userToEdit != null) {
            usernameField.setText(userToEdit.getUsername());
            passwordField.setText(userToEdit.getPassword());
            JButton manageSubjectsButton = new JButton("Quản lý môn học đã đăng kí");
            JButton manageResultsButton = new JButton("Quản lý điểm số");
            if (userToEdit instanceof Student) {
                this.student = (Student) userToEdit;
                roleBox.setSelectedItem("Student");
                idOrClassField.setText(student.getLop());
                extraField.setText(student.getNgaySinh());
                //
                manageSubjectsButton.addActionListener(ev -> {
                    showSubjectManagerDialog(student);
                });

                manageResultsButton.addActionListener(ev -> {
                    showResultManagerDialog(student);
                });

                dialog.add(new JLabel());
                dialog.add(manageSubjectsButton);
                //
                dialog.add(new JLabel());
                dialog.add(manageResultsButton);

            }
            else {
                Teacher t = (Teacher) userToEdit;
                roleBox.setSelectedItem("Teacher");
                idOrClassField.setText(String.valueOf(t.getTeacherid()));
            }
            roleBox.setEnabled(false); // Không thay đổi role khi sửa
        }

        JButton saveButton = new JButton("Lưu");
        dialog.add(new JLabel());
        dialog.add(saveButton);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = (String) roleBox.getSelectedItem();

            if (role.equals("Student")) {
                String lop = idOrClassField.getText();
                String ngaysinh = extraField.getText();
                Student s = new Student(username, password, student.getMssv(), ngaysinh, lop);
                if (userToEdit != null) {
                    int index = ssystem.getUsers().indexOf(userToEdit);
                    ssystem.getUsers().set(index, s);
                } else {
                    ssystem.getUsers().add(s);
                }
            } else {
                int id = Integer.parseInt(idOrClassField.getText());
                Teacher t = new Teacher(username, password, null, id);
                if (userToEdit != null) {
                    int index = ssystem.getUsers().indexOf(userToEdit);
                    ssystem.getUsers().set(index, t);
                } else {
                    ssystem.getUsers().add(t);
                }
            }
            dialog.dispose();
            refreshTable();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void showSubjectManagerDialog(Student student) {
        JDialog subjectDialog = new JDialog((Frame) null, "Quản lý môn học", true);
        subjectDialog.setLayout(new BorderLayout());

        DefaultListModel<Subject> listModel = new DefaultListModel<>();
        for (Subject sub : student.getRegisteredSubjects()) {
            listModel.addElement(sub);
        }

        JList<Subject> subjectList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(subjectList);
        subjectDialog.add(scrollPane, BorderLayout.CENTER);

        List<Subject> allSubjects = bank.getSubjects();

        JButton addButton = new JButton("Thêm môn");
        JButton removeButton = new JButton("Xóa môn");

        addButton.addActionListener(e -> {
            Subject selected = (Subject) JOptionPane.showInputDialog(
                    subjectDialog,
                    "Chọn môn học để thêm:",
                    "Thêm môn",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    allSubjects.toArray(),
                    null
            );
            if (selected != null && !student.getRegisteredSubjects().contains(selected)) {
                RegisteredSubjectDAO.addRegistration(student.getMssv(),selected.getName());
                student.getRegisteredSubjects().add(selected);
                listModel.addElement(selected);
            }
        });

        removeButton.addActionListener(e -> {
            Subject selected = subjectList.getSelectedValue();
            if (selected != null) {
                RegisteredSubjectDAO.deleteRegistration(student.getMssv(),selected.getName());
                student.getRegisteredSubjects().remove(selected);
                listModel.removeElement(selected);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        subjectDialog.add(buttonPanel, BorderLayout.SOUTH);
        subjectDialog.setSize(400, 300);
        subjectDialog.setLocationRelativeTo(this);
        subjectDialog.setVisible(true);
    }


    private void showResultManagerDialog(Student student) {
        JDialog resultDialog = new JDialog((Frame) null, "Quản lý kết quả", true);
        resultDialog.setLayout(new BorderLayout());

        String[] columns = {"Môn học", "Quiz ID", "Điểm", "Ngày làm"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (Result r : student.getQuizHistory()) {
            model.addRow(new Object[]{
                    r.getSubjectName(),
                    r.getQuizId(),
                    r.getScore(),
                    r.getLocalDateTimeStr()
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        resultDialog.add(scrollPane, BorderLayout.CENTER);

        // Nút thao tác
        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");

        addButton.addActionListener(e -> {
            Result newResult = showResultInputDialog(null);
            if (newResult != null) {

                ResultsDAO.addResult(newResult);
                student.getQuizHistory().add(newResult);
                model.addRow(new Object[]{
                        newResult.getSubjectName(),
                        newResult.getQuizId(),
                        newResult.getScore(),
                        newResult.getDateTaken()
                });
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Result current = student.getQuizHistory().get(selectedRow);
                Result updated = showResultInputDialog(current);
                if (updated != null) {
                    student.getQuizHistory().set(selectedRow, updated);
                    model.setValueAt(updated.getSubjectName(), selectedRow, 0);
                    model.setValueAt(updated.getQuizId(), selectedRow, 1);
                    model.setValueAt(updated.getScore(), selectedRow, 2);
                    model.setValueAt(updated.getDateTaken(), selectedRow, 3);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn một kết quả để sửa.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {

                student.getQuizHistory().remove(selectedRow);
                Result result = student.getQuizHistory().get(selectedRow);
                ResultsDAO.deleteResult(result);
                model.removeRow(selectedRow);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        resultDialog.add(buttonPanel, BorderLayout.SOUTH);
        resultDialog.setSize(500, 300);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }


    private Result showResultInputDialog(Result existing) {
        JTextField subjectField = new JTextField();
        JTextField quizIdField = new JTextField();
        JTextField scoreField = new JTextField();
        JTextField dateField = new JTextField(); // yyyy-MM-dd

        if (existing != null) {
            subjectField.setText(existing.getSubjectName());
            quizIdField.setText(String.valueOf(existing.getQuizId()));
            scoreField.setText(String.valueOf(existing.getScore()));
            dateField.setText(existing.getLocalDateTimeStr());
        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Môn học:"));
        panel.add(subjectField);
        panel.add(new JLabel("Quiz ID:"));
        panel.add(quizIdField);
        panel.add(new JLabel("Điểm:"));
        panel.add(scoreField);
        panel.add(new JLabel("Ngày làm (yyyy-MM-dd):"));
        panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thông tin kết quả",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Result newResult= ResultValidator.validateAndCreateResult(student.getMssv(),subjectField.getText()
                        ,quizIdField.getText(),scoreField.getText(),dateField.getText());
                if(newResult != null){
                    ResultsDAO.updateResult(newResult);
                }
                return newResult ;
 
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.");
            }
        }

        return null;
    }


}

