package com.student.management;
import com.student.management.cli.Application; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Main Class
public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        boolean welcomed = false;
        System.out.println("Welcome to the Student Management System");
        
        while(true) {
            if(welcomed) {
                System.out.println("===============================================");
            }
            if(!welcomed) {
                welcomed = true;
            }
            app.start();
        }
    }
}


