package com.example.lab_nt118.bai3;

public class EmployeePartTime extends Employee {

    public EmployeePartTime() {
        super();
    }

    public EmployeePartTime(String id, String name) {
        super(id, name);
    }

    @Override
    public double tinhLuong() {
        // Giả định mức lương cố định của nhân viên Thời vụ (Part-Time) là 150 $
        return 150.0;
    }

    @Override
    public String toString() {
        return super.toString() + " --> PartTime=" + tinhLuong() + " $";
    }
}