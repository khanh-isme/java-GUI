package project;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Bank {
    private List<Subject> subjects;
    private String date;

    public Bank(List<Subject> subjects, String date) {
        this.subjects = subjects != null ? subjects : new ArrayList<>();
        this.date = date;
    }

    public void writeDataToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Ghi thông tin project.Bank
            writer.write("Bank: " + date);
            writer.newLine();

            // Lặp qua các project.Subject
            for (Subject subject : subjects) {
                writer.write("Subject: " + subject.getName() + ", " + subject.getNgayThi() + ", " + subject.getTime());
                writer.newLine();

                // Lặp qua các project.Quiz
                for (Quiz quiz : subject.getQuizzes()) {
                    writer.write("  Quiz: " + quiz.getTitle());
                    writer.newLine();
                    writer.write("    TimeLimit: " + quiz.getTimeLimit());
                    writer.newLine();

                    // Lặp qua các project.Question
                    for (Question question : quiz.getQuestions()) {
                        writer.write("    project.Question: " + question.getContent());
                        writer.newLine();
                        writer.write("      Options: " + String.join(",", question.getOptions()));
                        writer.newLine();
                        writer.write("      CorrectAnswer: " + question.getCorrectAnswer());
                        writer.newLine();
                        writer.write("      Difficulty: " + question.getDifficulty());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public Subject findSubjectByName(String name) {
        for (Subject subject : subjects) { // 'subjects' represents the list or collection of available subjects in 'bank'
            if (subject.getName().equalsIgnoreCase(name)) {
                return subject;
            }
        }
        return null; // Return null if the subject is not found
    }


    // Getter cho subjects
    public List<Subject> getSubjects() {
        return subjects;
    }

    // Setter cho subjects
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // Getter cho date
    public String getDate() {
        return date;
    }

    // Setter cho date
    public void setDate(String date) {
        this.date = date;
    }

    // Phương thức thêm project.Subject vào danh sách
    public void addSubject(Subject subject) {
        if (subject != null) {
            this.subjects.add(subject);
        }
    }

    // Phương thức xoá project.Subject khỏi danh sách
    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
    }

    // Override phương thức toString() để dễ dàng in thông tin của project.Bank
    @Override
    public String toString() {
        return "Bank{subjects=" + subjects.toString() + ", date=" + date + "}";
    }

    public void printAllInfo() {
        System.out.println("Bank Date: " + date);
        for (Subject subject : subjects) {
            System.out.println("\nSubject: " + subject.getName());
            subject.printInfo();
        }
    }
}
