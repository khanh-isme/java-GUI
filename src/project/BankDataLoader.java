package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankDataLoader {
    public static Bank loadBankFromFile(String fileName) {
        Bank bank = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Subject currentSubject = null;
            Quiz currentQuiz = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Bank:")) {
                    String date = line.split(":")[1].trim();
                    bank = new Bank(null, date);

                } else if (line.startsWith("Subject:")) {
                    String[] parts = line.substring(8).trim().split(",");
                    if (parts.length == 3) {
                        String subjectName = parts[0].trim();
                        String subjectDay = parts[1].trim();
                        int time = Integer.parseInt(parts[2].trim());
                        currentSubject = new Subject(subjectName, null, subjectDay, time);
                    } else {
                        System.out.println("Lỗi: Dữ liệu Subject không đúng định dạng.");
                        continue;
                    }

                    if (bank != null) bank.addSubject(currentSubject);

                } else if (line.startsWith("Quiz:")) {
                    String quizTitle = line.split(":")[1].trim();
                    currentQuiz = new Quiz(quizTitle, 0);
                    if (currentSubject != null) currentSubject.addQuiz(currentQuiz);

                } else if (line.startsWith("TimeLimit:")) {
                    int timeLimit = Integer.parseInt(line.split(":")[1].trim());
                    if (currentQuiz != null) currentQuiz.setTimeLimit(timeLimit);

                } else if (line.startsWith("Question:")) {
                    String questionContent = line.split(":")[1].trim();
                    List<String> options = new ArrayList<>();
                    String correctAnswer = "";
                    String difficulty = "";

                    // Đọc tiếp để lấy các thông tin khác của câu hỏi
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();

                        if (line.startsWith("Options:")) {
                            options = Arrays.asList(line.split(":")[1].trim().split(","));
                        } else if (line.startsWith("CorrectAnswer:")) {
                            correctAnswer = line.split(":")[1].trim();
                        } else if (line.startsWith("Difficulty:")) {
                            difficulty = line.split(":")[1].trim();
                            break; // Kết thúc thông tin câu hỏi
                        }
                    }

                    Question question = new Question(questionContent, options, correctAnswer, difficulty);
                    if (currentQuiz != null) currentQuiz.addQuestion(question);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bank;
    }




    public static List<User> readFileAndParseUsers(String filePath) {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            User currentUser = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Kiểm tra dòng chính (project.Admin, project.Student, project.Teacher)
                if (line.startsWith("Admin") || line.startsWith("Student") || line.startsWith("Teacher")) {
                    String[] data = line.split(",");
                    String userType = data[0];

                    switch (userType) {
                        case "Admin":
                            if (data.length >= 3) {
                                Admin admin = new Admin(data[1], data[2]);
                                users.add(admin);
                                currentUser = admin;
                            } else {
                                System.out.println("Invalid project.Admin data: " + line);
                            }
                            break;

                        case "Student":
                            if (data.length >= 5) {
                                String username = data[1];
                                String password = data[2];
                                String role = data[3];
                                String mssv = data[4];
                                List<Result> quizHistory = new ArrayList<>();
                                List<Subject> registeredSubjects = new ArrayList<>();
                                Student student = new Student(username, password, role, mssv, quizHistory, registeredSubjects);
                                users.add(student);
                                currentUser = student;
                            } else {
                                System.out.println("Invalid project.Student data: " + line);
                            }
                            break;

                        case "Teacher":
                            if (data.length >= 4) {
                                String username = data[1];
                                String password = data[2];
                                String role = data[3];
                                List<Quiz> quizzesCreated = new ArrayList<>();
                                Teacher teacher = new Teacher(username, password, role, quizzesCreated);
                                users.add(teacher);
                                currentUser = teacher;
                            } else {
                                System.out.println("Invalid project.Teacher data: " + line);
                            }
                            break;

                        default:
                            System.out.println("Unknown user type: " + userType);
                            break;
                    }
                }
                // Kiểm tra dòng phụ (registered Subjects)
                else if (line.startsWith("registered Subjects:") && currentUser instanceof Student) {
                    String subjectsStr = line.substring("registered Subjects:".length()).trim();
                    String[] subjects = subjectsStr.split(",");
                    List<Subject> registeredSubjects = new ArrayList<>();
                    for (String subjectName : subjects) {
                        if (!subjectName.isEmpty()) {
                            Subject subject = new Subject(subjectName.trim(), new ArrayList<>(), "", 0);
                            registeredSubjects.add(subject);
                        }
                    }
                    ((Student) currentUser).setRegisteredSubjects(registeredSubjects);
                }
                // Đọc lịch sử bài thi
                else if (line.startsWith("Subject Name:") && currentUser instanceof Student) {
                    String subjectName = line.substring("Subject Name:".length()).trim();
                    String quizName = br.readLine().trim().substring("Quiz Name:".length()).trim();
                    String scoreStr = br.readLine().trim().substring("Score:".length()).trim();
                    double score = Double.parseDouble(scoreStr);
                    String dateTaken = br.readLine().trim().substring("Date Taken:".length()).trim();

                    Result result = new Result(subjectName, quizName, (int) score, 0, 0, dateTaken, new ArrayList<>());
                    ((Student) currentUser).getQuizHistory().add(result);
                }
                // Đọc các bài kiểm tra đã tạo cho project.Teacher
                else if (line.startsWith("Quizzes Created:") && currentUser instanceof Teacher) {
                    String quizName = line.substring("Quizzes Created:".length()).trim();
                    Quiz quiz = new Quiz(quizName, null); // Giả sử không có thông tin giới hạn thời gian
                    ((Teacher) currentUser).getQuizzesCreated().add(quiz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }



}