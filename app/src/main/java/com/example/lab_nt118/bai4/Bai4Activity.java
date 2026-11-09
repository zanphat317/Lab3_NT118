package com.example.lab_nt118.bai4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai4Activity extends AppCompatActivity {

    private EditText etId, etFullName;
    private CheckBox cbIsManager;
    private Button btnAdd;
    private ListView lvEmployees;

    private ArrayList<Employee> employeeList;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);

        initViews();
        setupAdapter();
        setupListeners();
    }

    private void initViews() {
        etId = findViewById(R.id.et_id);
        etFullName = findViewById(R.id.et_full_name);
        cbIsManager = findViewById(R.id.cb_is_manager);
        btnAdd = findViewById(R.id.btn_add);
        lvEmployees = findViewById(R.id.lv_employees);
    }

    private void setupAdapter() {
        employeeList = new ArrayList<>();

        // Thêm dữ liệu mẫu ban đầu
        employeeList.add(new Employee("1", "Nguyễn Văn An", true));
        employeeList.add(new Employee("2", "Trần Thị Bình", false));
        employeeList.add(new Employee("3", "Lê Hoàng Cường", true));
        employeeList.add(new Employee("4", "Phạm Minh Dung", false));

        // Khởi tạo EmployeeAdapter sử dụng item layout item_employee_bai4
        adapter = new EmployeeAdapter(this, R.layout.item_employee_bai4, employeeList);
        lvEmployees.setAdapter(adapter);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            String fullName = etFullName.getText().toString().trim();
            boolean isManager = cbIsManager.isChecked();

            if (id.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ ID và Full Name!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng Employee mới
            Employee emp = new Employee(id, fullName, isManager);

            // Thêm vào danh sách và cập nhật ListView
            employeeList.add(emp);
            adapter.notifyDataSetChanged();

            // Reset dữ liệu nhập
            etId.setText("");
            etFullName.setText("");
            cbIsManager.setChecked(false);
            etId.requestFocus();

            Toast.makeText(this, "Đã thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
        });
    }
}