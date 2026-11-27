package com.kietvanthex.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;

public class FloatView {
    private final Context ctx;
    private WindowManager wm;
    private View root;
    private WindowManager.LayoutParams lp;

    public FloatView(Context c) { this.ctx = c; }

    public void show() {
        wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);

        LinearLayout box = new LinearLayout(ctx);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setBackgroundColor(0xE6000000);
        box.setPadding(30, 30, 30, 30);

        TextView title = new TextView(ctx);
        title.setText("KIỆT VANTHEX");
        title.setTextColor(0xFFFFD700);
        title.setTextSize(18);
        title.setGravity(Gravity.CENTER);
        box.addView(title);

        TextView sub = new TextView(ctx);
        sub.setText("Menu v1.0");
        sub.setTextColor(Color.GRAY);
        sub.setTextSize(11);
        sub.setGravity(Gravity.CENTER);
        box.addView(sub);

        box.addView(btn("Aim Nhẹ", v -> touchCenter()));
        box.addView(btn("Bám Đầu", v -> touchCenter()));
        box.addView(btn("Fix Rung", v -> {}));
        box.addView(btn("Fix Lag", v -> {}));

        Button close = new Button(ctx);
        close.setText("TẮT");
        close.setOnClickListener(v -> hide());
        box.addView(close);

        root = box;
        lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.TOP | Gravity.START;
        lp.x = 80;
        lp.y = 200;

        root.setOnTouchListener(new DragTouch());
        wm.addView(root, lp);
    }

    private Button btn(String label, View.OnClickListener cb) {
        Button b = new Button(ctx);
        b.setText(label);
        b.setOnClickListener(cb);
        return b;
    }

    private void touchCenter() {
        TouchService ts = TouchService.get();
        if (ts == null) return;
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        ts.tap(dm.widthPixels / 2f, dm.heightPixels / 2f);
    }

    public void hide() {
        if (root != null && wm != null) wm.removeView(root);
        root = null;
    }

    private class DragTouch implements View.OnTouchListener {
        int initX, initY;
        float initTx, initTy;
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initX = lp.x; initY = lp.y;
                    initTx = e.getRawX(); initTy = e.getRawY();
                    return false;
                case MotionEvent.ACTION_MOVE:
                    lp.x = initX + (int)(e.getRawX() - initTx);
                    lp.y = initY + (int)(e.getRawY() - initTy);
                    wm.updateViewLayout(root, lp);
                    return true;
            }
            return false;
        }
    }
}
