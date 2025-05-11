package BUS;
import project.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class StudentValidator {

    public static Student validateStudent(String mssvStr, String username, String password, String ngaySinhStr, String lop) throws IllegalArgumentException {
        int mssv;

        // Kiểm tra mssv
        try {
            mssv = Integer.parseInt(mssvStr);
            if (mssv <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Mã số sinh viên phải là số nguyên dương.");
        }

        // Kiểm tra username
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sinh viên không được để trống.");
        }

        // Kiểm tra password
        if (password == null || password.length() < 2) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 3 ký tự.");
        }

        // Kiểm tra ngày sinh
        if (!isValidDate(ngaySinhStr)) {
            throw new IllegalArgumentException("Ngày sinh không hợp lệ. Định dạng hợp lệ: yyyy-MM-dd");
        }

        // Kiểm tra lớp
        if (lop == null || lop.trim().isEmpty()) {
            throw new IllegalArgumentException("Lớp không được để trống.");
        }

        // Tạo đối tượng Student hợp lệ
        Student student = new Student(username,password,mssv,ngaySinhStr,lop);
        return student;
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
