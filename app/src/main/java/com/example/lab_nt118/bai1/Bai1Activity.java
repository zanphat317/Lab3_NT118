package com.example.lab_nt118.bai1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nt118.R;

public class Bai1Activity extends AppCompatActivity {

    private TextView tvSelection;
    private ListView lvPerson;

    private final String[] arr = {"Teo", "Ty", "Bin", "Bo"};
    private int selectedPosition = -1; // Vị trí item duy nhất đang được chọn (-1 là chưa chọn)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        tvSelection = findViewById(R.id.tv_selection);
        lvPerson = findViewById(R.id.lv_person);

        // Thiết lập ArrayAdapter tùy chỉnh màu sắc cho ô được chọn
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arr
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);

                // Kiểm tra xem vị trí hiện tại có phải là vị trí được chọn hay không
                if (position == selectedPosition) {
                    // CHỈ ô được chọn mới hiện màu xanh dương + nền xanh nhạt + in đậm
                    textView.setTextColor(Color.parseColor("#000000")); // Chữ màu xanh dương
                    textView.setTypeface(null, Typeface.BOLD);
                    view.setBackgroundColor(Color.parseColor("#0000FF")); // Nền xanh lam nhạt
                } else {
                    // Các ô còn lại không được chọn sẽ trở lại màu chữ bình thường + nền trong suốt
                    textView.setTextColor(Color.parseColor("#212121")); // Chữ màu đen/xám mặc định
                    textView.setTypeface(null, Typeface.NORMAL);
                    view.setBackgroundColor(Color.TRANSPARENT); // Bỏ màu nền
                }
                return view;
            }
        };

        lvPerson.setAdapter(adapter);

        // Bắt sự kiện khi click vào một ô bất kỳ trong ListView
        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1. Cập nhật vị trí chọn mới (ô cũ sẽ tự động bỏ chọn)
                selectedPosition = position;

                // 2. Gọi notifyDataSetChanged() để ListView vẽ lại và áp dụng màu xanh dương cho DUY NHẤT ô mới chọn
                adapter.notifyDataSetChanged();

                // 3. Hiển thị thông tin lên TextView phía trên
                String value = arr[position];
                tvSelection.setTextColor(Color.parseColor("#1976D2"));
                tvSelection.setText("position: " + position + " ; value: " + value);
            }
        });
    }
}