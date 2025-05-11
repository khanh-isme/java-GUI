package DAO;

import project.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SystemDAO {

    public Ssystem loadAllUsers(Connection conn) throws SQLException {
        List<User> users = new ArrayList<>();

        // Load students
        String studentQuery = "SELECT * FROM students";
        PreparedStatement psStudent = conn.prepareStatement(studentQuery);
        ResultSet rsStudent = psStudent.executeQuery();

        while (rsStudent.next()) {
            String name = rsStudent.getString("name");
            int mssv = rsStudent.getInt("mssv");// chỗ này ảo thế nào ý trong DB là kiểu int nhưng lại getString đc
            String pass = rsStudent.getString("pass");
            String ngaysinh = rsStudent.getDate("ngaysinh").toString();
            String lop = rsStudent.getString("lop");

            Student student = new Student(name, pass, mssv, ngaysinh, lop);


            student.setQuizHistory(loadQuizHistory(conn, mssv));

            student.setRegisteredSubjects(StudentDAO.getRegisteredSubjects(conn,mssv));
            users.add(student);
        }

        // Load teachers
        String teacherQuery = "SELECT * FROM teachers";
        PreparedStatement psTeacher = conn.prepareStatement(teacherQuery);
        ResultSet rsTeacher = psTeacher.executeQuery();

        while (rsTeacher.next()) {
            int teacherId = rsTeacher.getInt("teacherId");
            String name = rsTeacher.getString("name");
            String pass = rsTeacher.getString("pass");

            List<Quiz> quizzesCreated = loadQuizzesByTeacher(conn, teacherId);

            Teacher teacher = new Teacher(name, pass, quizzesCreated, teacherId);
            users.add(teacher);
        }

        return new Ssystem(users);
    }

    private List<Result> loadQuizHistory(Connection conn, int mssv) throws SQLException {
        List<Result> quizHistory = new ArrayList<>();

        String query = "SELECT r.quizId, r.score, r.dateTaken, s.subjectName " +
                "FROM results r " +
                "JOIN quizzes q ON r.quizId = q.quizId " +
                "JOIN subjects s ON q.subjectName = s.subjectName " +
                "WHERE r.mssv = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, mssv);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    int quizId = rs.getInt("quizId");
                    double score = rs.getDouble("score");

                    Timestamp timestamp = rs.getTimestamp("dateTaken");
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    Date dateTaken = timestamp != null ? new Date(timestamp.getTime()) : null;

                    String subjectName = rs.getString("subjectName");

                    Result result = new Result(mssv,subjectName, quizId, score, dateTime);
                    quizHistory.add(result);
                }
            }
        }

        return quizHistory;
    }


    // chỗ này đang trùng với bên subjectDAO đã cải tiến nhưng để lại coi có thiếu sót gì không
    private List<Subject> loadRegisteredSubjects(Connection conn,int mssv) throws SQLException {
        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT rs.subjectName, s.thoiGianMoDe, t.name, s.thoiGianChoVao, s.id " +
                "FROM registered_subjects rs " +
                "JOIN subjects s ON rs.subjectName = s.subjectName " +
                "JOIN teachers t ON s.teacherId = t.teacherId " + // dấu cách ở cuối
                "WHERE rs.mssv = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mssv);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subjectName");
                Timestamp timestamp = rs.getTimestamp("thoiGianMoDe");
                LocalDateTime ngaythi = timestamp.toLocalDateTime();
                String teacher = rs.getString("name");
                int time = rs.getInt("thoiGianChoVao");

                List<Quiz> quizzes = loadQuizzesBySubjectName(conn,name);
                Subject subject = new Subject(id,name, teacher, quizzes, ngaythi, time);
                subjects.add(subject);
            }
        }

        return subjects;
    }

    private List<Quiz> loadQuizzesBySubjectName(Connection conn, String subjectname) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        String query = "SELECT * FROM quizzes WHERE subjectName = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, subjectname);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("quizId");
            int teacherId = rs.getInt("createdBy");
            String name = rs.getString("quizName");
            Timestamp createdDate = rs.getTimestamp("dateCreated");
            int time = rs.getInt("thoiGianLamBai");

            List<Question> questions = loadQuestions(conn, id);
            Quiz quiz = new Quiz(id, name, String.valueOf(teacherId),subjectname,createdDate.toLocalDateTime(), questions, time);

            quizzes.add(quiz);
        }

        return quizzes;
    }


    private List<Quiz> loadQuizzesByTeacher(Connection conn, int teacherId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();

        String query = "SELECT * FROM quizzes WHERE createdBy = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, teacherId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("quizId");
            String name = rs.getString("quizName");
            String subjectName = rs.getString("subjectName");
            Timestamp createdDate = rs.getTimestamp("dateCreated");
            int time = rs.getInt("thoiGianLamBai");

            List<Question> questions = loadQuestions(conn, id);
            Quiz quiz = new Quiz(id, name, String.valueOf(teacherId),subjectName,createdDate.toLocalDateTime(), questions, time);

            quizzes.add(quiz);
        }

        return quizzes;
    }

    private List<Question> loadQuestions(Connection conn, int quizId) throws SQLException {
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
