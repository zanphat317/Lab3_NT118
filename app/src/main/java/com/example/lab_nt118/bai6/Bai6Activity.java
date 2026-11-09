package com.example.lab_nt118.bai6;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai6Activity extends AppCompatActivity {

    private RecyclerView rvHeroes;
    private ArrayList<Hero> heroList;
    private HeroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai6);

        initViews();
        setupData();
        setupRecyclerView();
    }

    private void initViews() {
        rvHeroes = findViewById(R.id.rv_heroes);
    }

    private void setupData() {
        heroList = new ArrayList<>();

        // Danh sách 4 Hero cơ bản
        Hero thor = new Hero("Thor", R.drawable.thor);
        Hero ironman = new Hero("Iron Man", R.drawable.iron);
        Hero hulk = new Hero("Hulk", R.drawable.hulk);
        Hero spiderman = new Hero("Spider-Man", R.drawable.spider);

        // Lặp lại 4 lần (16 phần tử) để kiểm tra tính năng cuộn (scroll) của RecyclerView
        for (int i = 1; i <= 4; i++) {
            heroList.add(new Hero("Thor #" + i, thor.getImageResId()));
            heroList.add(new Hero("Iron Man #" + i, ironman.getImageResId()));
            heroList.add(new Hero("Hulk #" + i, hulk.getImageResId()));
            heroList.add(new Hero("Spider-Man #" + i, spiderman.getImageResId()));
        }
    }

    private void setupRecyclerView() {
        // Cấu hình LinearLayoutManager hiển thị danh sách dạng cuộn dọc (Vertical)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHeroes.setLayoutManager(layoutManager);

        // Khởi tạo và gán HeroAdapter cho RecyclerView
        adapter = new HeroAdapter(this, heroList);
        rvHeroes.setAdapter(adapter);
    }
}