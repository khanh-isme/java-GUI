package DAO;

import project.*;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;





public class StudentDAO {

    public Student getStudentByMssv(Connection conn, int mssv) throws SQLException {
        String query = "SELECT * FROM students WHERE mssv = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, mssv);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) return null;

        String pass = rs.getString("pass");
        String username = rs.getString("name");
        String lop = rs.getString("lop");
        LocalDate date = rs.getDate("ngaysinh").toLocalDate();
        String ngaysinhString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Student student = new Student(username, pass, mssv, ngaysinhString, lop);

        student.setQuizHistory(getQuizHistory(conn, mssv));
        student.setRegisteredSubjects(getRegisteredSubjects(conn, mssv));

        return student;
    }

    public static List<Result> getQuizHistory(Connection conn, int mssv) throws SQLException {
        List<Result> history = new ArrayList<>();
        String query = "SELECT mssv, subjectName, quizId, score, dateTaken FROM results WHERE mssv = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, mssv);
        ResultSet rs = ps.executeQuery();

        Timestamp timestamp = rs.getTimestamp("dateTaken");
        LocalDateTime dateTime = timestamp.toLocalDateTime();


        while (rs.next()) {
            Result result = new Result(
                    rs.getInt("mssv"),
                    rs.getString("subjectName"),
                    rs.getInt("quizId"),
                    rs.getInt("score"),
                    dateTime
            );
            history.add(result);
        }
        return history;
    }

    public  static List<Subject> getRegisteredSubjects(Connection conn, int mssv) throws SQLException {
        List<Subject> subjects = new ArrayList<>();

        String query = "SELECT s.subjectName, s.thoiGianMoDe, s.teacherId, s.thoiGianChoVao, s.id, t.name AS teacherName " +
                "FROM registered_subjects r " +
                "JOIN subjects s ON r.subjectName = s.subjectName " +
                "LEFT JOIN teachers t ON s.teacherId = t.teacherId " +
                "WHERE r.mssv = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, mssv);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String subjectName = rs.getString("subjectName");
            LocalDateTime thoiGianMoDe = rs.getTimestamp("thoiGianMoDe").toLocalDateTime();
            int time = rs.getInt("thoiGianChoVao"); // thời gian mở đề
            String teacherName = rs.getString("teacherName");

            Subject subject = new Subject(
                    id,
                    subjectName,
                    teacherName,
                    null, // nếu muốn, có thể gọi thêm getQuizzesBySubject(conn, subjectName)
                    thoiGianMoDe,
                    time
            );

            subjects.add(subject);
        }

        return subjects;
    }

    public static boolean updateStudent(Student student) {
        String sql = "UPDATE Students SET name = ?, pass = ?, ngaysinh = ?, lop = ? WHERE mssv = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getUsername());
            stmt.setString(2, student.getPassword());

            // Format ngaysinh ví dụ "15/01/2002"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(student.getNgaySinh());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            stmt.setDate(3, sqlDate);

            stmt.setString(4, student.getLop());
            stmt.setInt(5, student.getMssv());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getLastStudentIdFromDB() {
        String sql = "SELECT mssv FROM students ORDER BY mssv DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("mssv");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Đã xảy ra lỗi khi truy cập dữ liệu:\n" + e.getMessage(),
                    "Lỗi Hệ Thống",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return -1;
    }


    public static boolean saveStudentToDB(Student student) {
        String sql = "INSERT INTO students (mssv, name, pass, ngaysinh, lop) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, student.getMssv());
            stmt.setString(2, student.getUsername());
            stmt.setString(3, student.getPassword());

            LocalDate ld = LocalDate.parse(student.getNgaySinh());
            stmt.setDate(4, java.sql.Date.valueOf(ld));


            stmt.setString(5, student.getLop());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Đã xảy ra lỗi khi lưu sinh viên:\n" + e.getMessage(),
                    "Lỗi Hệ Thống",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return false;
    }




}
