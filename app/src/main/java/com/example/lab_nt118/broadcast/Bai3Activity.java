package com.example.lab_nt118.broadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lab_nt118.R;

import java.util.ArrayList;

public class Bai3Activity extends AppCompatActivity {

    public static boolean isRunning = false;

    private static final String PREF_NAME = "EmergencyPref";
    private static final String KEY_AUTO_RESPONSE = "auto_response";
    private static final int SMS_PERMISSION_CODE = 103;

    private ListView lvMessages;
    private Button btnSafe, btnMayday, btnBack;
    private SwitchCompat swAutoResponse;
    private LinearLayout llResponseButtons;

    private ArrayList<String> addressesList;
    private ArrayAdapter<String> adapter;
    private BroadcastReceiver internalReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3_emergency);

        findViewsByIds();
        initVariables();
        handleOnClickListener();
        initBroadcastReceiver();

        processIntentData(getIntent());
    }

    private void findViewsByIds() {
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
        btnBack = findViewById(R.id.btn_back);
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llResponseButtons = findViewById(R.id.ll_response_buttons);
    }

    private void initVariables() {
        addressesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addressesList);
        lvMessages.setAdapter(adapter);

        // Khôi phục trạng thái Switch từ SharedPreferences
        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isAuto = pref.getBoolean(KEY_AUTO_RESPONSE, false);
        swAutoResponse.setChecked(isAuto);
        updateAutoResponseUI(isAuto);
    }

    private void handleOnClickListener() {
        btnSafe.setOnClickListener(v -> respond(true));
        btnMayday.setOnClickListener(v -> respond(false));
        btnBack.setOnClickListener(v -> finish());

        // Lắng nghe sự kiện bật/tắt Auto Response Switch
        swAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Lưu trạng thái vào SharedPreferences
            SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            pref.edit().putBoolean(KEY_AUTO_RESPONSE, isChecked).apply();

            updateAutoResponseUI(isChecked);

            // Nếu bật Auto Response và đang có danh sách số điện thoại chờ -> tự động trả lời ngay
            if (isChecked && !addressesList.isEmpty()) {
                respond(true);
            }
        });
    }

    private void updateAutoResponseUI(boolean isAuto) {
        if (isAuto) {
            // Khi bật Auto Response: Ẩn 2 nút trả lời thủ công
            llResponseButtons.setVisibility(View.GONE);
        } else {
            // Khi tắt Auto Response: Hiện lại 2 nút trả lời thủ công
            llResponseButtons.setVisibility(View.VISIBLE);
        }
    }

    private void initBroadcastReceiver() {
        internalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (SmsReceiver.ACTION_EMERGENCY_SMS_RECEIVED.equals(intent.getAction())) {
                    ArrayList<String> newAddresses = intent.getStringArrayListExtra("addresses");
                    processReceiveAddresses(newAddresses);
                }
            }
        };
    }

    private void processIntentData(Intent intent) {
        if (intent != null && intent.hasExtra("addresses")) {
            ArrayList<String> newAddresses = intent.getStringArrayListExtra("addresses");
            processReceiveAddresses(newAddresses);
        }
    }

    public void processReceiveAddresses(ArrayList<String> newAddresses) {
        if (newAddresses != null && !newAddresses.isEmpty()) {
            for (String addr : newAddresses) {
                if (addr != null && !addr.isEmpty() && !addressesList.contains(addr)) {
                    addressesList.add(addr);
                }
            }
            adapter.notifyDataSetChanged();

            // Nếu Auto Response đang bật -> tự động phản hồi ngay lập tức
            if (swAutoResponse.isChecked() && !addressesList.isEmpty()) {
                respond(true);
            }
        }
    }

    public void respond(boolean isSafe) {
        if (addressesList.isEmpty()) {
            Toast.makeText(this, "Không có số điện thoại nào trong danh sách cần phản hồi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_CODE
            );
            return;
        }

        String responseMessage = isSafe ? "I am safe and well. Worry not!" : "Tell my mother I love her.";
        int count = 0;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            for (String phone : addressesList) {
                smsManager.sendTextMessage(phone, null, responseMessage, null, null);
                count++;
            }

            Toast.makeText(this, "Đã gửi phản hồi tới " + count + " số điện thoại!", Toast.LENGTH_LONG).show();

            // Gửi thành công -> Xóa danh sách số điện thoại đã được phản hồi
            addressesList.clear();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi gửi SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                respond(true);
            } else {
                Toast.makeText(this, "Quyền gửi SMS bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processIntentData(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        IntentFilter filter = new IntentFilter(SmsReceiver.ACTION_EMERGENCY_SMS_RECEIVED);
        ContextCompat.registerReceiver(this, internalReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        if (internalReceiver != null) {
            try {
                unregisterReceiver(internalReceiver);
            } catch (Exception ignored) {
            }
        }
    }
}