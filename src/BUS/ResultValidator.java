package BUS;

import DAO.ResultsDAO;
import DAO.SubjectDAO;
import project.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class ResultValidator {

    public static Result validateAndCreateResult(int mssv, String subjectName, String quizIdStr, String scoreStr, String dateTakenStr) {
        // Validate subject name
        if (subjectName == null || subjectName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên môn học không được để trống", "Thông báo hệ thống", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        if (!SubjectDAO.subjectExists(subjectName.trim())) {
            JOptionPane.showMessageDialog(null, "Tên môn học không tồn tại trong hệ thống", "Thông báo hệ thống", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        // Validate quiz ID
        int quizId;
        try {
            quizId = Integer.parseInt(quizIdStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quiz ID phải là số nguyên", "Thông báo hệ thống", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        
        // Validate score
        double score;
        try {
            score = Double.parseDouble(scoreStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Điểm phải là số hợp lệ", "Thông báo hệ thống", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Validate dateTaken (format: yyyy-MM-dd HH:mm:ss)
        LocalDateTime dateTaken;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTaken = LocalDateTime.parse(dateTakenStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày giờ không đúng định dạng (yyyy-MM-dd HH:mm:ss)", "Thông báo hệ thống", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Nếu hợp lệ, tạo và trả về Result
        return new Result(mssv, subjectName.trim(), quizId, score, dateTaken);
    }
}
