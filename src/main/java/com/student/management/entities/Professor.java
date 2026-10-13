package com.student.management.entities;

public class Professor extends User {
    private double salary;

    // Getters and Setters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}