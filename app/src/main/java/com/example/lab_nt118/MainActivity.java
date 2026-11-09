package com.example.lab_nt118;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnBai1, btnBai2, btnBai4;
    private Button btnLab2Bai1, btnLab2Bai2, btnLab2Bai3, btnLab2Bai4, btnLab2Bai5, btnLab2Bai6, btnLab2Bai7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
    }

    private void initViews() {
        // 3 nút giao diện theo yêu cầu cho Lab Broadcast Receiver
        btnBai1 = findViewById(R.id.btn_bai1);
        btnBai2 = findViewById(R.id.btn_bai2);
        btnBai4 = findViewById(R.id.btn_bai4);
    }

    private void setupListeners() {
        // Nút Bài 1: Mở Bai1Activity (BroadcastReceiver SMS)
        btnBai1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.lab_nt118.broadcast.Bai1Activity.class);
            startActivity(intent);
        });

        // Nút Bài 2: Mở Bai2Activity (PowerStateChangeReceiver trong AndroidManifest)
        btnBai2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.lab_nt118.broadcast.Bai2Activity.class);
            startActivity(intent);
        });

        // Nút Bài 4 / Bài 3 (Tin nhắn khẩn cấp): Mở Bai3Activity
        btnBai4.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.lab_nt118.broadcast.Bai3Activity.class);
            startActivity(intent);
        });

        // Giữ lại chuyển màn hình cho các bài thực hành trước đó nếu có
        if (btnLab2Bai1 != null) {
            btnLab2Bai1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai1.Bai1Activity.class)));
        }
        if (btnLab2Bai2 != null) {
            btnLab2Bai2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai2.Bai2Activity.class)));
        }
        if (btnLab2Bai3 != null) {
            btnLab2Bai3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai3.Bai3Activity.class)));
        }
        if (btnLab2Bai4 != null) {
            btnLab2Bai4.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai4.Bai4Activity.class)));
        }
        if (btnLab2Bai5 != null) {
            btnLab2Bai5.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai5.Bai5Activity.class)));
        }
        if (btnLab2Bai6 != null) {
            btnLab2Bai6.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai6.Bai6Activity.class)));
        }
        if (btnLab2Bai7 != null) {
            btnLab2Bai7.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, com.example.lab_nt118.bai7.Bai7Activity.class)));
        }
    }
}