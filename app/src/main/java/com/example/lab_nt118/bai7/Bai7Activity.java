package com.example.lab_nt118.bai7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai7Activity extends AppCompatActivity {

    private EditText etId, etFullName;
    private CheckBox cbIsManager;
    private Button btnAdd;
    private RecyclerView rvEmployees;

    private ArrayList<Employee> employeeList;
    private EmployeeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai7);

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        etId = findViewById(R.id.et_id);
        etFullName = findViewById(R.id.et_full_name);
        cbIsManager = findViewById(R.id.cb_is_manager);
        btnAdd = findViewById(R.id.btn_add);
        rvEmployees = findViewById(R.id.rv_employees);
    }

    private void setupRecyclerView() {
        employeeList = new ArrayList<>();

        // Dữ liệu mẫu ban đầu
        employeeList.add(new Employee("1", "Nguyễn Văn An", true));
        employeeList.add(new Employee("2", "Trần Thị Bình", false));
        employeeList.add(new Employee("3", "Lê Hoàng Cường", true));
        employeeList.add(new Employee("4", "Phạm Minh Dung", false));

        // Cấu hình LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvEmployees.setLayoutManager(layoutManager);

        // Gán Adapter
        adapter = new EmployeeRecyclerAdapter(this, employeeList);
        rvEmployees.setAdapter(adapter);
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

            // Thêm phần tử mới vào ArrayList
            employeeList.add(emp);
            int insertedPosition = employeeList.size() - 1;

            // Sử dụng notifyItemInserted() để chèn thêm phần tử mượt mà kèm hiệu ứng
            adapter.notifyItemInserted(insertedPosition);

            // Cuộn mượt tới vị trí vừa thêm
            rvEmployees.smoothScrollToPosition(insertedPosition);

            // Reset dữ liệu nhập
            etId.setText("");
            etFullName.setText("");
            cbIsManager.setChecked(false);
            etId.requestFocus();

            Toast.makeText(this, "Đã thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
        });
    }
}