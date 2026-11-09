package com.example.lab_nt118.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {

    public static final String ACTION_EMERGENCY_SMS_RECEIVED = "com.example.lab_nt118.ACTION_EMERGENCY_SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            return;
        }

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        if (messages == null || messages.length == 0) {
            return;
        }

        String sender = messages[0].getDisplayOriginatingAddress();
        StringBuilder bodyBuilder = new StringBuilder();
        for (SmsMessage msg : messages) {
            bodyBuilder.append(msg.getMessageBody());
        }

        String body = bodyBuilder.toString();

        // Kiểm tra nội dung tin nhắn có chứa keyword "are you ok?" (không phân biệt hoa thường)
        if (body.toLowerCase().contains("are you ok?")) {
            ArrayList<String> addresses = new ArrayList<>();
            if (sender != null && !sender.isEmpty()) {
                addresses.add(sender);
            }

            Toast.makeText(context, "Phát hiện SMS khẩn cấp 'are you ok?' từ: " + sender, Toast.LENGTH_LONG).show();

            // Phân biệt 2 trường hợp:
            if (Bai3Activity.isRunning) {
                // Trường hợp 1: Bai3Activity đang chạy -> Phát broadcast nội bộ để Activity nhận
                Intent internalIntent = new Intent(ACTION_EMERGENCY_SMS_RECEIVED);
                internalIntent.setPackage(context.getPackageName());
                internalIntent.putStringArrayListExtra("addresses", addresses);
                context.sendBroadcast(internalIntent);
            } else {
                // Trường hợp 2: Bai3Activity không chạy -> Khởi động lại Bai3Activity và truyền dữ liệu qua Intent
                Intent activityIntent = new Intent(context, Bai3Activity.class);
                activityIntent.putStringArrayListExtra("addresses", addresses);
                activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(activityIntent);
            }
        }
    }
}