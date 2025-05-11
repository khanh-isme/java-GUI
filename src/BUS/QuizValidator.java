package BUS;
import project.*;
import DAO.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class QuizValidator {

    public static Quiz validateQuiz(int Quizid,String name, String createBy, String subjectName,String createdDate, List<Question> questions, String timelambaiStr) {

        LocalDateTime ngaytao;
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ngaytao = LocalDateTime.parse(createdDate, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày tạo không đúng định dạng yyyy-MM-dd HH:mm (ví dụ: 2025-04-14 17:30:30).", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (Quizid < 0) {
            JOptionPane.showMessageDialog(null, "Không lấy được ID quiz từ CSDL.", "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            return null;
        }


        if(subjectName == null || subjectName.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Tên subject không được để trống", "Lỗi tên subject", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (name == null || name.trim().isEmpty() || name.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Tên quiz không được để trống và không được chứa số.", "Lỗi tên Quiz", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (createBy == null || createBy.trim().isEmpty() || createBy.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Tên người tạo không được để trống hoặc chứa số.", "Lỗi người tạo", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        timelambaiStr = timelambaiStr.trim();
        if (!timelambaiStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Thời gian thi chỉ được nhập bằng số.", "Lỗi thời gian", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int timelambai = Integer.parseInt(timelambaiStr);
        if (timelambai <= 0) {
            JOptionPane.showMessageDialog(null, "Thời gian làm bài phải lớn hơn 0.", "Lỗi thời gian", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (createdDate == null) {
            ngaytao = LocalDateTime.now();
        }

        if (questions == null) {
            questions = new ArrayList<>();
        }

        return new Quiz(Quizid, name, createBy, subjectName,ngaytao, questions, timelambai);
    }
}
