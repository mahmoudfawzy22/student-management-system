package com.student.management.cli;

import com.student.management.services.AdminService;
import com.student.management.services.ViewerService;

import java.util.Scanner;

public class Application {
    private final AdminService adminService;
    private final ViewerService viewerService;
    private final Scanner scanner;

    public Application() {
        this.adminService = new AdminService();
        this.viewerService = new ViewerService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("1. Admin Login\n2. Viewer Access\n3. Exit");
        System.out.print("> ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> adminLogin();
            case 2 -> viewerAccess();
            case 3 -> exit();
            default -> System.out.println("Invalid option.");
        }
    }

    private void adminLogin() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        if (adminService.verifyPassword(password)) {
            adminService.adminMenu(scanner);
        } else {
            System.out.println("Incorrect password.");
        }
    }

    private void viewerAccess() {
        viewerService.viewStudents();
    }

    private void exit() {
        System.out.println("Exiting the system. Goodbye!");
        System.exit(0);
    }
}