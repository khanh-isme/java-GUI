package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestMySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/thitracnghiem";
        String user = "root";
        String password = "123456789";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối MySQL thành công!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
