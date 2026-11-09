package com.example.lab_nt118.bai2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai2Activity extends AppCompatActivity {

    private EditText etName;
    private Button btnSubmit;
    private TextView tvSelection;
    private ListView lvNames;

    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;

    private int selectedPosition = -1; // Vị trí item được chọn hiện tại (-1 nếu chưa chọn)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        // Ánh xạ View
        initViews();

        // Khởi tạo danh sách dữ liệu và Adapter
        setupAdapter();

        // Bắt các sự kiện
        setupListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.et_name);
        btnSubmit = findViewById(R.id.btn_submit);
        tvSelection = findViewById(R.id.tv_selection);
        lvNames = findViewById(R.id.lv_names);
    }

    private void setupAdapter() {
        // Khởi tạo ArrayList<String>
        dataList = new ArrayList<>();
        dataList.add("Teo");
        dataList.add("Ty");
        dataList.add("Bin");
        dataList.add("Bo");

        // Khởi tạo ArrayAdapter
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dataList
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);

                if (position == selectedPosition) {
                    textView.setTextColor(Color.parseColor("#1976D2")); // Xanh dương
                    textView.setTypeface(null, Typeface.BOLD);
                    view.setBackgroundColor(Color.parseColor("#E3F2FD"));
                } else {
                    textView.setTextColor(Color.parseColor("#212121"));
                    textView.setTypeface(null, Typeface.NORMAL);
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                return view;
            }
        };

        // Gán Adapter cho ListView
        lvNames.setAdapter(adapter);
    }

    private void setupListeners() {
        // 1. Thêm dữ liệu khi bấm nút "Nhập"
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = etName.getText().toString().trim();

                // Kiểm tra không cho thêm chuỗi trống
                if (inputName.isEmpty()) {
                    Toast.makeText(Bai2Activity.this, "Vui lòng nhập tên, không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm phần tử mới vào ArrayList
                dataList.add(inputName);

                // Cập nhật giao diện ListView
                adapter.notifyDataSetChanged();

                // Xóa nội dung trong EditText
                etName.setText("");
                Toast.makeText(Bai2Activity.this, "Đã thêm: " + inputName, Toast.LENGTH_SHORT).show();
            }
        });

        // 2. Chọn phần tử trong ListView khi click ngắn
        lvNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                adapter.notifyDataSetChanged();

                String value = dataList.get(position);
                tvSelection.setText("position: " + position + " ; value: " + value);
            }
        });

        // 3. Xóa phần tử khỏi ArrayList và ListView khi nhấn giữ (Long click)
        lvNames.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String removedValue = dataList.get(position);

                // Xóa khỏi ArrayList
                dataList.remove(position);

                // Reset vị trí chọn nếu xóa đúng ô đang chọn
                if (selectedPosition == position) {
                    selectedPosition = -1;
                    tvSelection.setText("Chưa chọn phần tử nào");
                } else if (selectedPosition > position) {
                    selectedPosition--;
                }

                // Cập nhật danh sách hiển thị
                adapter.notifyDataSetChanged();

                Toast.makeText(Bai2Activity.this, "Đã xóa: " + removedValue, Toast.LENGTH_SHORT).show();
                return true; // Trả về true để đánh dấu sự kiện long click đã xử lý xong
            }
        });
    }
}