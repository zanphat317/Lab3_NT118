package com.example.lab_nt118.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.lab_nt118.R;

public class Bai2Activity extends AppCompatActivity {

    private ImageView imgBatteryStatus;
    private TextView tvPowerStatus, tvEventExplanation;
    private BroadcastReceiver uiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2_power);

        initViews();
        initUiReceiver();
    }

    private void initViews() {
        imgBatteryStatus = findViewById(R.id.img_battery_status);
        tvPowerStatus = findViewById(R.id.tv_power_status);
        tvEventExplanation = findViewById(R.id.tv_event_explanation);
        Button btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());
    }

    private void initUiReceiver() {
        // Lắng nghe các sự kiện sạc pin để cập nhật trực tiếp giao diện màn hình
        uiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) return;
                String action = intent.getAction();

                if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
                    updateUI(true);
                } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
                    updateUI(false);
                } else if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                    boolean isCharging = isDeviceCharging(intent);
                    updateUI(isCharging);
                }
            }
        };
    }

    private boolean isDeviceCharging(Intent intent) {
        if (intent == null) return false;

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        // Thiết bị đang sạc nếu status là CHARGING/FULL HOẶC có nguồn sạc plugged > 0 (AC/USB/Wireless)
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL ||
                plugged == BatteryManager.BATTERY_PLUGGED_AC ||
                plugged == BatteryManager.BATTERY_PLUGGED_USB ||
                plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS ||
                plugged > 0;
    }

    private void checkCurrentBatteryState() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);

        if (batteryStatus != null) {
            boolean isCharging = isDeviceCharging(batteryStatus);
            updateUI(isCharging);
        }
    }

    private void updateUI(boolean isCharging) {
        if (isCharging) {
            imgBatteryStatus.setImageResource(R.drawable.ic_power_connected);
            tvPowerStatus.setText("Trạng thái hiện tại: Đang sạc ⚡");
            tvPowerStatus.setTextColor(Color.parseColor("#1B5E20"));
            tvPowerStatus.setBackgroundColor(Color.parseColor("#C8E6C9"));
            tvEventExplanation.setText("Thiết bị đang được cắm sạc.\n\nReceiver trong AndroidManifest (PowerStateChangeReceiver) phát Toast 'Power connected', đồng thời màn hình duy trì trạng thái Đang sạc ⚡.");
        } else {
            imgBatteryStatus.setImageResource(R.drawable.ic_power_disconnected);
            tvPowerStatus.setText("Trạng thái hiện tại: Không sạc 🔋");
            tvPowerStatus.setTextColor(Color.parseColor("#B71C1C"));
            tvPowerStatus.setBackgroundColor(Color.parseColor("#FFCDD2"));
            tvEventExplanation.setText("Thiết bị đang ngắt kết nối sạc.\n\nReceiver trong AndroidManifest (PowerStateChangeReceiver) phát Toast 'Power disconnected', đồng thời màn hình duy trì trạng thái Không sạc 🔋.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCurrentBatteryState();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        ContextCompat.registerReceiver(this, uiReceiver, filter, ContextCompat.RECEIVER_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (uiReceiver != null) {
            unregisterReceiver(uiReceiver);
        }
    }
}