package BUS;
import project.*;
import DAO.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StudentValidator {



    public static Student validateStudent(int mssv,String username, String password, String ngaySinhStr, String lop) {
        if(mssv ==0){
            mssv = StudentDAO.getLastStudentIdFromDB() + 1;
        }


        // Kiểm tra username
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Không cho phép tên chứa số hoặc ký tự đặc biệt
        if (!username.matches("^[\\p{L}\\s]+$")) {
            JOptionPane.showMessageDialog(null, "Tên sinh viên không được chứa số hoặc ký tự đặc biệt.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra password
        if (password == null || password.length() < 3) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 3 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra định dạng ngày sinh
        LocalDate ngaySinh;
        try {
            ngaySinh = LocalDate.parse(ngaySinhStr);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ. Định dạng hợp lệ: yyyy-MM-dd", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra ngày sinh không được trong tương lai
        if (ngaySinh.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không được nằm trong tương lai.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra lớp
        if (lop == null || lop.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lớp không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Nếu hợp lệ, tạo và trả về đối tượng Student
        return new Student(username, password, mssv, ngaySinhStr, lop);
    }



    private static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Bắt buộc đúng format và giá trị hợp lệ
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
