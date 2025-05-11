package DAO;

import project.Result;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ResultsDAO {
    java.sql.Timestamp dateTaken;
    // Thêm kết quả
    public static boolean addResult( Result result) {
        String sql = "INSERT INTO results (mssv, subjectName, quizId, score, dateTaken) VALUES (?, ?, ?, ?, ?)";



        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, result.getMssv());
            stmt.setString(2, result.getSubjectName());
            stmt.setInt(3, result.getQuizId());
            stmt.setDouble(4, result.getScore());


            LocalDateTime dateTaken = result.getDateTaken();
            Timestamp timestamp = Timestamp.valueOf(dateTaken);
            stmt.setTimestamp(5, timestamp);


            System.out.printf("Inserting result: mssv=%d, subject=%s, quizId=%d, score=%.2f, date=%s%n",
                    result.getMssv(), result.getSubjectName(), result.getQuizId(), result.getScore(), timestamp.toString());

            return stmt.executeUpdate() > 0;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật điểm (và ngày làm bài nếu cần)
    public static boolean updateResult(Result result) {
        String sql = "UPDATE results SET score = ?, dateTaken = ? WHERE mssv = ? AND subjectName = ? AND quizId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, result.getScore());
            LocalDateTime dateTaken = result.getDateTaken();
            Timestamp timestamp = Timestamp.valueOf(dateTaken);
            stmt.setTimestamp(2, timestamp);
            stmt.setInt(3, result.getMssv());
            stmt.setString(4, result.getSubjectName());
            stmt.setInt(5, result.getQuizId());

            return stmt.executeUpdate() > 0;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xoá kết quả
    public static boolean deleteResult(Result result) {
        String sql = "DELETE FROM results WHERE mssv = ? AND subjectName = ? AND quizId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, result.getMssv());
            stmt.setString(2, result.getSubjectName());
            stmt.setInt(3, result.getQuizId());

            return stmt.executeUpdate() > 0;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
