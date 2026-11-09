package com.example.lab_nt118.bai3;

public abstract class Employee {
    private String id;
    private String name;

    public Employee() {
    }

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Phương thức trừu tượng tính lương (mỗi lớp con sẽ tự cài đặt cách tính riêng)
    public abstract double tinhLuong();

    @Override
    public String toString() {
        return id + " - " + name;
    }
}