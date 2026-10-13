package com.student.management.services;

import com.student.management.utils.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class AdminService {
    private static final String ADMIN_PASSWORD = System.getenv("ADMIN_PASSWORD");
    private final Connection connection;

    public AdminService() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean verifyPassword(String password) {
        return ADMIN_PASSWORD.equals(password);
    }

    public void adminMenu(Scanner scanner) {
        boolean exit = true;
        while (exit) {
            System.out.println("Admin Menu:");
            System.out.println(
                    "1. Create Student\n2. Create Professor\n3. Create Course\n4. View Students\n5. Print Student Courses\n6. Assign Student to Course\n7. Exit");
            System.out.print("> ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> createStudent(scanner);
                case 2 -> createProfessor(scanner);
                case 3 -> createCourse(scanner);
                case 4 -> viewStudents();
                case 5 -> printStudentCourses(scanner);
                case 6 -> assignStudentToCourse(scanner); // New option to assign student to course
                case 7 -> exit();
                default -> System.out.println("Invalid option.");
            }
        }

    }

    private void createStudent(Scanner scanner) {
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();
            System.out.print("Enter GPA: ");
            double gpa = scanner.nextDouble();
            System.out.print("Enter Level: ");
            int level = scanner.nextInt();

            String query = "INSERT INTO Student (Name, Email, Password, Phone_number, Address, Department, GPA, Level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, address);
            stmt.setString(6, department);
            stmt.setDouble(7, gpa);
            stmt.setInt(8, level);
            stmt.executeUpdate();

            System.out.println("Student created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createProfessor(Scanner scanner) {
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();

            String query = "INSERT INTO Professor (Name, Email, Password, Phone_number, Address, Salary) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, address);
            stmt.setDouble(6, salary);
            stmt.executeUpdate();

            System.out.println("Professor created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createCourse(Scanner scanner) {
        try {
            System.out.print("Enter Course Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Credits: ");
            int credits = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Code: ");
            String code = scanner.nextLine();

            String query = "INSERT INTO Course (Name, Credits, Code) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, credits);
            stmt.setString(3, code);
            stmt.executeUpdate();

            System.out.println("Course created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewStudents() {
        try {
            String query = "SELECT * FROM Student";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Email: %s, Department: %s, GPA: %.2f, Level: %d\n",
                        rs.getInt("Student_ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Department"),
                        rs.getDouble("GPA"),
                        rs.getInt("Level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to print the courses for a specific student
    private void printStudentCourses(Scanner scanner) {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = scanner.nextInt();

            String query = """
                    SELECT c.Course_ID, c.name, c.code, c.credits
                    FROM Course c
                    INNER JOIN Student_Course sc ON c.Course_ID = sc.Course_ID
                    WHERE sc.student_id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Courses for Student ID: " + studentId);
            if (!rs.isBeforeFirst()) {
                System.out.println("No courses found for this student.");
                return;
            }

            System.out.println("ID\tName\tCode\tCredits");
            while (rs.next()) {
                System.out.printf("%d\t%s\t%s\t%d\n",
                        rs.getInt("Course_ID"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("credits"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // New function to assign student to a course
    private void assignStudentToCourse(Scanner scanner) {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = scanner.nextInt();

            System.out.print("Enter Course ID: ");
            int courseId = scanner.nextInt();

            // Insert into Student_Course table
            String query = "INSERT INTO Student_Course (student_id, course_id) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();

            System.out.println("Student ID " + studentId + " has been assigned to Course ID " + courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exit(){
        System.out.println("Exiting the system. Goodbye!");
        System.exit(0);
    }
}