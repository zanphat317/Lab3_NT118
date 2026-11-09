package com.example.lab_nt118.broadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lab_nt118.R;

public class Bai1Activity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 101;

    private TextView tvInstruction, tvSmsSender, tvSmsBody;
    private Button btnRequestPermission, btnBack;

    private BroadcastReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1_sms);

        initViews();
        checkAndRequestPermission();
        initSmsReceiver();
    }

    private void initViews() {
        tvInstruction = findViewById(R.id.tv_instruction);
        tvSmsSender = findViewById(R.id.tv_sms_sender);
        tvSmsBody = findViewById(R.id.tv_sms_body);
        btnRequestPermission = findViewById(R.id.btn_request_permission);
        btnBack = findViewById(R.id.btn_back);

        btnRequestPermission.setOnClickListener(v -> checkAndRequestPermission());
        btnBack.setOnClickListener(v -> finish()); // Quay về màn hình chính (MainActivity)
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                    SMS_PERMISSION_CODE
            );
        } else {
            tvInstruction.setText("Ứng dụng đang chờ tin nhắn SMS (Đã có quyền)");
            Toast.makeText(this, "Đã có quyền nhận SMS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tvInstruction.setText("Ứng dụng đang chờ tin nhắn SMS (Đã được cấp quyền)");
                Toast.makeText(this, "Đã cấp quyền nhận SMS thành công!", Toast.LENGTH_SHORT).show();
            } else {
                tvInstruction.setText("Quyền nhận SMS bị từ chối. Vui lòng bấm cấp quyền!");
                Toast.makeText(this, "Bạn đã từ chối quyền nhận SMS!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initSmsReceiver() {
        // Đăng ký động BroadcastReceiver bằng code (không đăng ký receiver trong AndroidManifest)
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                    if (messages != null && messages.length > 0) {
                        String sender = messages[0].getDisplayOriginatingAddress();
                        StringBuilder bodyBuilder = new StringBuilder();
                        for (SmsMessage msg : messages) {
                            bodyBuilder.append(msg.getMessageBody());
                        }

                        String body = bodyBuilder.toString();

                        // Cập nhật giao diện TextView
                        tvSmsSender.setText(sender);
                        tvSmsBody.setText(body);
                        tvInstruction.setText("Vừa nhận tin nhắn mới lúc " + java.text.DateFormat.getTimeInstance().format(new java.util.Date()));

                        // Hiển thị Toast thông báo khi có tin nhắn mới
                        Toast.makeText(context, "Có tin nhắn mới từ: " + sender, Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký linh hoạt Receiver khi Activity hoạt động (Resume) với cờ RECEIVER_EXPORTED tương thích Android mới
        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        ContextCompat.registerReceiver(this, smsReceiver, filter, ContextCompat.RECEIVER_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký Receiver khi Activity không còn hoạt động (Pause/Stop), đảm bảo không tiếp tục lắng nghe khi đóng Activity
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }
}