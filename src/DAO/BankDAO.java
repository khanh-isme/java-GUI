package DAO;

import project.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankDAO {

    public Bank loadBankData(Connection conn) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT s.subjectName, s.thoiGianMoDe, s.teacherId, t.name as teacherName ,s.id ,s.thoiGianChoVao " +
                "FROM subjects s LEFT JOIN teachers t ON s.teacherId = t.teacherId";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id =rs.getInt("id");
            String subjectName = rs.getString("subjectName");
            LocalDateTime ngayThi = rs.getTimestamp("thoiGianMoDe").toLocalDateTime();
            String teacher = rs.getString("teacherName");
            int time = rs.getInt("thoiGianChoVao");

            List<Quiz> quizzes = getQuizzesBySubject(conn, subjectName);
            Subject subject = new Subject(id,subjectName, teacher, quizzes, ngayThi, time);

            subjects.add(subject);
        }

        String today = java.time.LocalDate.now().toString();
        return new Bank(subjects, today);
    }

    private List<Quiz> getQuizzesBySubject(Connection conn, String subjectName) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        String query = "SELECT q.quizId, q.quizName, q.dateCreated, q.createdBy, t.name AS creatorName, q.thoiGianLamBai " +
                "FROM quizzes q LEFT JOIN teachers t ON q.createdBy = t.teacherId " +
                "WHERE q.subjectName = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, subjectName);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int quizId = rs.getInt("quizId");
            String quizName = rs.getString("quizName");
            LocalDateTime createdDate = rs.getTimestamp("dateCreated").toLocalDateTime();
            String createBy = rs.getString("creatorName");
            int timelambai = rs.getInt("thoiGianLamBai");

            List<Question> questions = getQuestionsByQuiz(conn, quizId);
            quizzes.add(new Quiz(quizId, quizName, createBy,subjectName,createdDate, questions, timelambai));
        }

        return quizzes;
    }

    private List<Question> getQuestionsByQuiz(Connection conn, int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();

        String query = "SELECT * FROM questions WHERE quizId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, quizId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("questionId");
            String text = rs.getString("questionText");
            String a = rs.getString("answerA");
            String b = rs.getString("answerB");
            String c = rs.getString("answerC");
            String d = rs.getString("answerD");
            String correct = rs.getString("correctAnswer");

            questions.add(new Question(id, quizId,text, a, b, c, d, correct));
        }

        return questions;
    }
}
