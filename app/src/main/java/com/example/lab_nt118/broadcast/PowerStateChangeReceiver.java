package com.example.lab_nt118.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PowerStateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        String action = intent.getAction();

        if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
            // Khi điện thoại được cắm sạc, hiển thị Toast "Power connected"
            Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            // Khi điện thoại bị rút sạc, hiển thị Toast "Power disconnected"
            Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}