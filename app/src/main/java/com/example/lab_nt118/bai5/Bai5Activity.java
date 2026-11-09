package com.example.lab_nt118.bai5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai5Activity extends AppCompatActivity {

    private EditText etDishName;
    private Spinner spThumbnail;
    private CheckBox cbPromotion;
    private Button btnAdd;
    private GridView gvDishes;

    private ArrayList<Dish> dishList;
    private DishAdapter dishAdapter;
    private ThumbnailAdapter thumbnailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai5);

        initViews();
        setupSpinner();
        setupGridView();
        setupListeners();
    }

    private void initViews() {
        etDishName = findViewById(R.id.et_dish_name);
        spThumbnail = findViewById(R.id.sp_thumbnail);
        cbPromotion = findViewById(R.id.cb_promotion);
        btnAdd = findViewById(R.id.btn_add);
        gvDishes = findViewById(R.id.gv_dishes);
    }

    private void setupSpinner() {
        // Khởi tạo ThumbnailAdapter với các giá trị từ Enum Thumbnail
        thumbnailAdapter = new ThumbnailAdapter(this, Thumbnail.values());
        spThumbnail.setAdapter(thumbnailAdapter);
    }

    private void setupGridView() {
        dishList = new ArrayList<>();

        // Dữ liệu mẫu ban đầu
        dishList.add(new Dish("bún chả đặc biệt thơm ngon", Thumbnail.BUN_CHA, true));
        dishList.add(new Dish("cơm tấm", Thumbnail.COM_TAM, false));
        dishList.add(new Dish("phở bò", Thumbnail.PHO_BO, true));
        dishList.add(new Dish("bánh mì thịt nướng", Thumbnail.BANH_MI, false));

        dishAdapter = new DishAdapter(this, R.layout.item_dish, dishList);
        gvDishes.setAdapter(dishAdapter);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String dishName = etDishName.getText().toString().trim();
            Thumbnail selectedThumb = (Thumbnail) spThumbnail.getSelectedItem();
            boolean isPromoted = cbPromotion.isChecked();

            if (dishName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên món ăn!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng món ăn mới
            Dish newDish = new Dish(dishName, selectedThumb, isPromoted);

            // Thêm vào danh sách và cập nhật GridView
            dishList.add(newDish);
            dishAdapter.notifyDataSetChanged();

            // Reset trạng thái các ô nhập về mặc định
            etDishName.setText("");
            spThumbnail.setSelection(0);
            cbPromotion.setChecked(false);
            etDishName.requestFocus();

            // Hiển thị Toast thông báo thành công
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
        });
    }
}