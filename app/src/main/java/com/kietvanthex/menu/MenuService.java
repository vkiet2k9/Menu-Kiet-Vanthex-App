package com.kietvanthex.menu;

import android.app.*;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class MenuService extends Service {
    private FloatView floatView;
    private static final String CH = "menu_ch";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel c = new NotificationChannel(CH, "Menu",
                    NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(c);
        }
        Notification n = new Notification.Builder(this, CH)
                .setContentTitle("Menu Kiệt Vanthex")
                .setContentText("Đang chạy overlay")
                .setSmallIcon(android.R.drawable.ic_menu_manage)
                .build();
        startForeground(1, n);
        floatView = new FloatView(this);
        floatView.show();
    }

    @Override
    public void onDestroy() {
        if (floatView != null) floatView.hide();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent i) { return null; }
}
