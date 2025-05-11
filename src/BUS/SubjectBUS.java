package BUS;

import DAO.*;
import project.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SubjectBUS {

    public static Subject validateSubject(int id,String name, String teacher, List<Quiz> quizzes, String ngayThiStr,String timeStr) {

        // Kiểm tra tên không chứa số
        if (name == null || name.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Tên môn học không được chứa số.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra định dạng ngày thi
        LocalDateTime ngayThi;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            ngayThi = LocalDateTime.parse(ngayThiStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày thi không đúng định dạng yyyy-MM-dd HH:mm.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra time chỉ chứa số, không được có chữ
        if (!timeStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Thời gian thi chỉ được nhập bằng số, không được chứa chữ cái hoặc ký tự đặc biệt.", "Lỗi thời gian", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Parse sang int để kiểm tra logic
        int time = Integer.parseInt(timeStr);
        if (time >= 30 || time <= 0) {
            JOptionPane.showMessageDialog(null, "Thời gian làm bài phải lớn hơn 0 và nhỏ hơn 30 phút.", "Lỗi thời gian", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // thiếu kiểm tra giáo viên có tồn tại trong database hay không
        Subject subject = SubjectDAO.getSubjectFromDB(name);
        if (subject ==null) {
            subject=new Subject(id,name,teacher,quizzes,ngayThi,time);
        }
        return subject;
    }



}
