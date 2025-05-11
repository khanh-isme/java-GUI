package Teacher;
import project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentEditorPanel extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtMSSV;
    private JTextField txtNgaySinh;
    private JTextField txtLop;
    private JButton btnSave;

    private Student student;

    public StudentEditorPanel(Student student) {
        this.student = student;
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Username:"));
        txtUsername = new JTextField(student.getUsername());
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField(student.getPassword());
        add(txtPassword);

        add(new JLabel("MSSV:"));
        txtMSSV = new JTextField(String.valueOf(student.getMssv()));
        add(txtMSSV);

        add(new JLabel("Ngày sinh:"));
        txtNgaySinh = new JTextField(student.getNgaySinh());
        add(txtNgaySinh);

        add(new JLabel("Lớp:"));
        txtLop = new JTextField(student.getLop());
        add(txtLop);

        btnSave = new JButton("Lưu thay đổi");
        add(btnSave);
        add(new JLabel()); // placeholder

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentInfo();
            }
        });
    }

    private void updateStudentInfo() {
        try {
            student.setUsername(txtUsername.getText());
            student.setPassword(new String(txtPassword.getPassword()));
            student.setMssv(Integer.parseInt(txtMSSV.getText()));
            student.setNgaySinh(txtNgaySinh.getText());
            student.setLop(txtLop.getText());

            JOptionPane.showMessageDialog(this, "Cập nhật thông tin sinh viên thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin: " + ex.getMessage());
        }
    }
}
