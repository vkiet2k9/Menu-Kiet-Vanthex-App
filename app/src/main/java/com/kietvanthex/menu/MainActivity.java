package com.kietvanthex.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(50, 100, 50, 50);

        TextView t = new TextView(this);
        t.setText("MENU KIỆT VANTHEX");
        t.setTextSize(24);
        t.setTextColor(0xFFFFD700);
        ll.addView(t);

        TextView s = new TextView(this);
        s.setText("\nAuthor: Kiệt Vanthex\nv1.0 — No-Root\n");
        ll.addView(s);

        Button b1 = new Button(this);
        b1.setText("1. Cấp quyền Overlay");
        b1.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this))
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName())));
            else Toast.makeText(this, "Đã có quyền", Toast.LENGTH_SHORT).show();
        });
        ll.addView(b1);

        Button b2 = new Button(this);
        b2.setText("2. Cấp quyền Accessibility");
        b2.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "Bật TouchService", Toast.LENGTH_LONG).show();
        });
        ll.addView(b2);

        Button b3 = new Button(this);
        b3.setText("3. Bật Menu");
        b3.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Cấp Overlay trước", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, MenuService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(i);
            else startService(i);
            Toast.makeText(this, "Menu đã bật", Toast.LENGTH_SHORT).show();
            finish();
        });
        ll.addView(b3);

        Button b4 = new Button(this);
        b4.setText("4. Tắt Menu");
        b4.setOnClickListener(v -> {
            stopService(new Intent(this, MenuService.class));
            Toast.makeText(this, "Menu đã tắt", Toast.LENGTH_SHORT).show();
        });
        ll.addView(b4);

        TextView f = new TextView(this);
        f.setText("\n© Kiệt Vanthex 2025");
        f.setTextSize(12);
        ll.addView(f);

        sv.addView(ll);
        setContentView(sv);
    }
}
