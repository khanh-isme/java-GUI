package BUS;
import project.*;
import DAO.*;
import javax.swing.JOptionPane;

public class TeacherBUS {


    public static Teacher validateTeacher(String name, String password) {
        // Kiểm tra tên
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên giáo viên không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Tên không được chứa số
        if (!name.matches("^[^\\d]+$")) {
            JOptionPane.showMessageDialog(null, "Tên giáo viên không được chứa số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra mật khẩu
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (password.length() < 3) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 3 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Tạo và trả về đối tượng Teacher hợp lệ
        return new Teacher(name, password, null, 0);
    }



}
