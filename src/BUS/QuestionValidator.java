package BUS;
import DAO.*;
import project.*;

import javax.swing.*;

public class QuestionValidator {

    public static Question validateQuestion(int questionId,int quizId,String text, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {


        if (text == null || text.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nội dung câu hỏi không được để trống.", "Lỗi câu hỏi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra các đáp án không được để trống
        if (answerA == null || answerA.trim().isEmpty() ||
                answerB == null || answerB.trim().isEmpty() ||
                answerC == null || answerC.trim().isEmpty() ||
                answerD == null || answerD.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tất cả các đáp án (A, B, C, D) phải được nhập đầy đủ.", "Lỗi đáp án", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Kiểm tra đáp án đúng phải là A, B, C hoặc D
        if (correctAnswer == null || !correctAnswer.matches("[ABCD]")) {
            JOptionPane.showMessageDialog(null, "Đáp án đúng phải là một trong các giá trị: A, B, C hoặc D.", "Lỗi đáp án đúng", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Question question = new Question(questionId,quizId,text,answerA,answerB,answerC,answerD,correctAnswer);
        return question;
    }
}

