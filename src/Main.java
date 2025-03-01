import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



// thiếu lớp trừu tượng hàm trừu tượng và interface
public class Main {

    public static void main(String[] args) {
        // Đường dẫn đến file chứa dữ liệu
        String fileName = "data.txt"; // Đảm bảo rằng file này tồn tại và có dữ liệu đúng định dạng

        String fileName1="data1.txt";
        // Tạo Bank từ dữ liệu trong file
        Bank bank = BankDataLoader.loadBankFromFile(fileName);

        // Kiểm tra nếu bank đã được tải thành công
        if (bank != null) {
            System.out.println("Dữ liệu đã được tải thành công từ file:");
            System.out.println(bank.toString());
        } else {
            System.out.println("Không thể tải dữ liệu từ file.");
        }

        List<User> users =BankDataLoader.readFileAndParseUsers(fileName1);

        if (users != null) {
            System.out.println("Dữ liệu đã được tải thành công từ file:");
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("Không thể tải dữ liệu từ file.");
        }

    }
}
