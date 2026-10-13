package com.student.management.entities;

import com.student.management.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String department;
    private double gpa;
    private int level;

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Method to fetch courses for this student
    public List<Course> getCourses() {
        String query = """
            SELECT c.id, c.name, c.code, c.credits
            FROM courses c
            INNER JOIN student_courses sc ON c.id = sc.course_id
            WHERE sc.student_id = ?;
        """;

        List<Course> courses = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.getId()); // Use the student's ID

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getInt("id"));
                    course.setName(resultSet.getString("name"));
                    course.setCode(resultSet.getString("code"));
                    course.setCredits(resultSet.getInt("credits"));

                    courses.add(course); // Add each course to the list
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving courses for Student ID " + this.getId() + ": " + e.getMessage());
        }

        return courses; // Return the list of courses
    }
    
    public boolean enrollInCourse(int courseId) {
        String query = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setInt(1, this.getId());  // Set the student ID (assuming it's available from the User class)
            statement.setInt(2, courseId);      // Set the course ID
    
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Student ID " + this.getId() + " has been enrolled in course ID " + courseId);
                return true;  // Successfully enrolled
            } else {
                System.out.println("Failed to enroll student in the course.");
                return false; // Enrollment failed
            }
        } catch (SQLException e) {
            System.err.println("Error enrolling student: " + e.getMessage());
            return false; // Error occurred during enrollment
        }
    }
    
}
