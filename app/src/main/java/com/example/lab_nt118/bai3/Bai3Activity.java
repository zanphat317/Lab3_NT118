package com.example.lab_nt118.bai3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai3Activity extends AppCompatActivity {

    private EditText etEmpId, etEmpName;
    private RadioGroup rgType;
    private Button btnAdd;
    private ListView lvEmployees;

    private ArrayList<Employee> employeeList;
    private ArrayAdapter<Employee> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);

        initViews();
        setupAdapter();
        setupListeners();
    }

    private void initViews() {
        etEmpId = findViewById(R.id.et_emp_id);
        etEmpName = findViewById(R.id.et_emp_name);
        rgType = findViewById(R.id.rg_type);
        btnAdd = findViewById(R.id.btn_add);
        lvEmployees = findViewById(R.id.lv_employees);
    }

    private void setupAdapter() {
        employeeList = new ArrayList<>();

        // Dữ liệu mẫu ban đầu
        employeeList.add(new EmployeeFullTime("NV01", "Nguyễn Văn An"));
        employeeList.add(new EmployeePartTime("NV02", "Trần Thị Bình"));

        // ArrayAdapter<Employee> tự động gọi phương thức toString() của Employee để hiển thị chuỗi lên ListView
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                employeeList
        );

        lvEmployees.setAdapter(adapter);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String id = etEmpId.getText().toString().trim();
            String name = etEmpName.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Mã và Tên nhân viên!", Toast.LENGTH_SHORT).show();
                return;
            }

            Employee employee;
            // Dùng getCheckedRadioButtonId() để kiểm tra RadioButton nào được chọn
            int checkedId = rgType.getCheckedRadioButtonId();
            if (checkedId == R.id.rb_fulltime) {
                // Tạo đối tượng nhân viên Chính thức (Đa hình: tham chiếu kiểu Employee trỏ tới đối tượng EmployeeFullTime)
                employee = new EmployeeFullTime(id, name);
            } else {
                // Tạo đối tượng nhân viên Thời vụ (Đa hình: tham chiếu kiểu Employee trỏ tới đối tượng EmployeePartTime)
                employee = new EmployeePartTime(id, name);
            }

            // Thêm đối tượng vào ArrayList<Employee>
            employeeList.add(employee);

            // Cập nhật giao diện ListView
            adapter.notifyDataSetChanged();

            // Xóa dữ liệu cũ trên EditText
            etEmpId.setText("");
            etEmpName.setText("");
            etEmpId.requestFocus();

            Toast.makeText(this, "Đã thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
        });
    }
}