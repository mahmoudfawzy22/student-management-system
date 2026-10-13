package com.student.management.services;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.student.management.utils.DatabaseConnection; 

public class ViewerService {
    private final Connection connection;

    public ViewerService() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void viewStudents() {
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
}