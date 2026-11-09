package com.example.lab_nt118.bai3;

public class EmployeeFullTime extends Employee {

    public EmployeeFullTime() {
        super();
    }

    public EmployeeFullTime(String id, String name) {
        super(id, name);
    }

    @Override
    public double tinhLuong() {
        // Giả định mức lương cố định của nhân viên Chính thức (Full-Time) là 500 $
        return 500.0;
    }

    @Override
    public String toString() {
        return super.toString() + " --> FullTime=" + tinhLuong() + " $";
    }
}