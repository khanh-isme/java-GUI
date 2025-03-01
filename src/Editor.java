import java.util.List;
import java.util.Scanner;

public class Editor {
    private  Scanner scanner = new Scanner(System.in);

    // Hàm để chỉnh sửa các thuộc tính của lớp Bank
    public void editBank(Bank bank) {
        while(true) {
            System.out.println("Select attribute to edit in Bank:");
            System.out.println("1. Date");
            System.out.println("2. Subjects");
            System.out.println("3. exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự xuống dòng

            switch (choice) {
                case 1:
                    System.out.print("Enter new date: ");
                    String newDate = scanner.nextLine();
                    bank.setDate(newDate);
                    break;
                case 2:
                    System.out.println("Editing subjects...");
                    for (int i = 0; i < bank.getSubjects().size(); i++) {
                        System.out.println((i + 1) + ". " + bank.getSubjects().get(i).getName());
                    }
                    System.out.print("(Type 0 to exit) ");
                    System.out.print("Select subject to edit (choose number): ");
                    int subjectIndex = scanner.nextInt() - 1;
                    if(subjectIndex < 0) {
                        break;
                    }
                    scanner.nextLine(); // Đọc ký tự xuống dòng
                    editSubject(bank.getSubjects().get(subjectIndex));
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

        }

    }

    // Hàm để chỉnh sửa các thuộc tính của lớp Subject
    public void editSubject(Subject subject) {
        while(true) {
            System.out.println("Select attribute to edit in Subject (chose number):");
            System.out.println("1. Name");
            System.out.println("2. ngaythi");
            System.out.println("3. thoi gian mở đề");
            System.out.println("4. Quizzes");
            System.out.println("5. exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự xuống dòng

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    subject.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new day time (yyyy-MM-dd HH:mm): ");
                    String newday = scanner.nextLine();
                    subject.setNgayThi(newday);
                    break;
                case 3:
                    System.out.print("Enter new time (minute): ");
                    int newTime = Integer.parseInt(scanner.nextLine());
                    subject.setTime(newTime);
                case 4:
                    System.out.println("Editing quizzes...");
                    for (int i = 0; i < subject.getQuizzes().size(); i++) {
                        System.out.println((i + 1) + ". " + subject.getQuizzes().get(i).getTitle());
                    }
                    System.out.print("(Type 0 to exit) ");
                    System.out.print("Select quiz to edit: ");
                    int quizIndex = scanner.nextInt() - 1;
                    if(quizIndex < 0) {
                        break;
                    }
                    scanner.nextLine(); // Đọc ký tự xuống dòng
                    editQuiz(subject.getQuizzes().get(quizIndex));
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

        }
    }

    // Hàm để chỉnh sửa các thuộc tính của lớp Quiz
    public void editQuiz(Quiz quiz) {
        while(true) {
            System.out.println("Select attribute to edit in Quiz (chose number):");
            System.out.println("1. Title");
            System.out.println("2. Time Limit");
            System.out.println("3. Questions");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự xuống dòng

            switch (choice) {
                case 1:
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    quiz.setTitle(newTitle);
                    break;
                case 2:
                    System.out.print("Enter new time limit: ");
                    int newTimeLimit = scanner.nextInt();
                    quiz.setTimeLimit(newTimeLimit);
                    break;
                case 3:
                    System.out.println("Editing questions...");
                    for (int i = 0; i < quiz.getQuestions().size(); i++) {
                        System.out.println((i + 1) + ". " + quiz.getQuestions().get(i).getContent());
                    }
                    System.out.println("(Type 0 to exit) ");
                    System.out.print("Select question to edit: ");
                    int questionIndex = scanner.nextInt() - 1;
                    if(questionIndex < 0) {
                        break;
                    }
                    scanner.nextLine(); // Đọc ký tự xuống dòng
                    editQuestion(quiz.getQuestions().get(questionIndex));
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

        }
    }

    // Hàm để chỉnh sửa các thuộc tính của lớp Question
    public void editQuestion(Question question) {
        while(true) {
            System.out.println("Select attribute to edit in Question (chose number) :");
            System.out.println("1. Content");
            System.out.println("2. Options");
            System.out.println("3. Correct Answer");
            System.out.println("4. Difficulty");
            System.out.println("5. exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự xuống dòng

            switch (choice) {
                case 1:
                    System.out.print("Enter new content: ");
                    String newContent = scanner.nextLine();
                    question.setContent(newContent);
                    break;
                case 2:
                    System.out.println("Enter new options (comma-separated): ");
                    String newOptions = scanner.nextLine();
                    question.setOptions(List.of(newOptions.split(",")));
                    break;
                case 3:
                    System.out.print("Enter new correct answer: ");
                    String newCorrectAnswer = scanner.nextLine();
                    question.setCorrectAnswer(newCorrectAnswer);
                    break;
                case 4:
                    System.out.print("Enter new difficulty: ");
                    String newDifficulty = scanner.nextLine();
                    question.setDifficulty(newDifficulty);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }





    public void deleteData(Bank bank) {
        while(true) {
            System.out.println("Select option to delete:");
            System.out.println("1. Delete Subject");
            System.out.println("2. Delete Quiz");
            System.out.println("3. Delete Question");
            System.out.println("4. exit");
            System.out.print(" choose an option (number): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự xuống dòng

            switch (choice) {
                case 1:
                    System.out.println("Deleting a subject...");
                    for (int i = 0; i < bank.getSubjects().size(); i++) {
                        System.out.println((i + 1) + ". " + bank.getSubjects().get(i).getName());
                    }
                    System.out.print("Select subject to delete: ");
                    int subjectIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Đọc ký tự xuống dòng
                    if (subjectIndex >= 0 && subjectIndex < bank.getSubjects().size()) {
                        bank.getSubjects().remove(subjectIndex);
                        System.out.println("Subject deleted.");
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;
                case 2:
                    System.out.println("Select subject containing the quiz (choose number):");
                    for (int i = 0; i < bank.getSubjects().size(); i++) {
                        System.out.println((i + 1) + ". " + bank.getSubjects().get(i).getName());
                    }
                    int subjIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Đọc ký tự xuống dòng
                    if (subjIndex >= 0 && subjIndex < bank.getSubjects().size()) {
                        Subject selectedSubject = bank.getSubjects().get(subjIndex);
                        for (int i = 0; i < selectedSubject.getQuizzes().size(); i++) {
                            System.out.println((i + 1) + ". " + selectedSubject.getQuizzes().get(i).getTitle());
                        }
                        System.out.print("Select quiz to delete (choose number): ");
                        int quizIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Đọc ký tự xuống dòng
                        if (quizIndex >= 0 && quizIndex < selectedSubject.getQuizzes().size()) {
                            selectedSubject.getQuizzes().remove(quizIndex);
                            System.out.println("Quiz deleted.");
                        } else {
                            System.out.println("Invalid index.");
                        }
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;
                case 3:
                    System.out.println("Select subject containing the quiz with the question:");
                    for (int i = 0; i < bank.getSubjects().size(); i++) {
                        System.out.println((i + 1) + ". " + bank.getSubjects().get(i).getName());
                    }
                    int subjectIdx = scanner.nextInt() - 1;
                    scanner.nextLine(); // Đọc ký tự xuống dòng
                    if (subjectIdx >= 0 && subjectIdx < bank.getSubjects().size()) {
                        Subject selectedSubj = bank.getSubjects().get(subjectIdx);
                        for (int i = 0; i < selectedSubj.getQuizzes().size(); i++) {
                            System.out.println((i + 1) + ". " + selectedSubj.getQuizzes().get(i).getTitle());
                        }
                        System.out.print("Select quiz to delete a question from: ");
                        int quizIdx = scanner.nextInt() - 1;
                        scanner.nextLine(); // Read newline
                        if (quizIdx >= 0 && quizIdx < selectedSubj.getQuizzes().size()) {
                            Quiz selectedQuiz = selectedSubj.getQuizzes().get(quizIdx);
                            for (int i = 0; i < selectedQuiz.getQuestions().size(); i++) {
                                System.out.println((i + 1) + ". " + selectedQuiz.getQuestions().get(i).getContent());
                            }
                            System.out.print("Select question to delete: ");
                            int questionIdx = scanner.nextInt() - 1;
                            scanner.nextLine(); // Read newline
                            if (questionIdx >= 0 && questionIdx < selectedQuiz.getQuestions().size()) {
                                selectedQuiz.getQuestions().remove(questionIdx);
                                System.out.println("Question deleted.");
                            } else {
                                System.out.println("Invalid index.");
                            }
                        } else {
                            System.out.println("Invalid index.");
                        }
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    public void updateStudent(Student student,Ssystem system) {
        System.out.print("new username: ");
        String newname =scanner.nextLine();
        // Kiểm tra xem username mới có bị trùng không
        if (system.isUsernameExists(newname)) { // Gọi hàm kiểm tra trong hệ thống
            System.out.println("Error: Username already exists. Please try a different username.");
            return; // Thoát nếu username bị trùng
        }
        System.out.print("pass: ");
        String newpass = scanner.nextLine();
        student.updateUserAttributes(newname, newpass);
    }


    public void deleteQuiz(Student student) {
        System.out.println("Select a quiz to delete from student's quizzes taken(choose 0 to exit):");
        for (int i = 0; i < student.getQuizHistory().size(); i++) {
            System.out.println((i + 1) + ". " + student.getQuizHistory().get(i).getQuizName());
        }
        int choice = scanner.nextInt() - 1;
        if(choice < 0) {
            return;
        }
        scanner.nextLine(); // Read newline
        if (choice >= 0 && choice < student.getQuizHistory().size()) {
            student.getQuizHistory().remove(choice);
            System.out.println("Quiz removed from student's quizzes taken.");
        } else {
            System.out.println("Invalid index.");
        }
    }


    public void editMSSV(Student student) {
        System.out.print("Enter new MSSV: ");
        String newMSSV = scanner.nextLine();
        student.setMssv(newMSSV);
        System.out.println("MSSV updated.");
    }


    public void deleteResult(Student student) {
        System.out.println("Select a result to delete from student's quiz history(choose 0 to exit):");
        for (int i = 0; i < student.getQuizHistory().size(); i++) {
            System.out.println((i + 1) + ". " + student.getQuizHistory().get(i).toString()); // Customize toString() as needed
        }
        System.out.print("your choice: ");
        int choice = scanner.nextInt() - 1;
        if(choice < 0) {
            return;
        }
        scanner.nextLine(); // Read newline
        if (choice >= 0 && choice < student.getQuizHistory().size()) {
            student.getQuizHistory().remove(choice);
            System.out.println("Result removed from student's quiz history.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    public void deleteRegisteredSubjects( Student student) {
        System.out.println("Select a subject to delete from student's registered Subjects(choose 0 to exit):");
        for (int i = 0; i < student.getRegisteredSubjects().size(); i++) {
            System.out.println((i + 1) + ". " + student.getRegisteredSubjects().get(i).getName()); // Customize toString() as needed
        }
        System.out.print("your choice: ");
        int choice = scanner.nextInt() - 1;
        if(choice < 0) {
            return;
        }
        scanner.nextLine(); // Read newline
        if (choice >= 0 && choice < student.getRegisteredSubjects().size()) {
            student.getRegisteredSubjects().remove(choice);
            System.out.println("Result removed from student's quiz history.");
        } else {
            System.out.println("Invalid index.");
        }

    }


    public void updateTeacher(Teacher teacher,Ssystem system) {
        System.out.print("new username: ");
        String newname =scanner.nextLine();

        // Kiểm tra xem username mới có bị trùng không
        if (system.isUsernameExists(newname)) { // Gọi hàm kiểm tra trong hệ thống
            System.out.println("Error: Username already exists. Please try a different username.");
            return; // Thoát nếu username bị trùng
        }

        System.out.print("pass: ");
        String newpass = scanner.nextLine();
        System.out.print("role: ");
        String newrole = scanner.nextLine();

        teacher.updateUserAttributes(newname, newpass,newrole);
    }

    public void deleteQuiz(Teacher teacher) {
        System.out.println("Select a quiz to delete from teacher's quizzes created (choose 0 to exit):");
        for (int i = 0; i < teacher.getQuizzesCreated().size(); i++) {
            System.out.println((i + 1) + ". " + teacher.getQuizzesCreated().get(i).getTitle());
        }
        int choice = scanner.nextInt() - 1;
        if(choice < 0) {
            return;
        }
        scanner.nextLine(); // Read newline
        if (choice >= 0 && choice < teacher.getQuizzesCreated().size()) {
            teacher.getQuizzesCreated().remove(choice);
            System.out.println("Quiz removed from teacher's quizzes created.");
        } else {
            System.out.println("Invalid index.");
        }
    }
}



