package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание RelativeLayout
 * Демонстрирует позиционирование элементов относительно друг друга
 */
public class ProgrammaticRelativeLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        float density = getResources().getDisplayMetrics().density;
        int paddingPx = (int)(16 * density);
        
        // Главный контейнер
        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        mainLayout.setBackgroundColor(0xFFF5F5F5);
        mainLayout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        // === ЗАГОЛОВОК ===
        TextView titleView = createTextView(this, "📍 Программное создание RelativeLayout", 100);
        titleView.setTextSize(18);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setBackgroundColor(0xFF1565C0);
        titleView.setGravity(android.view.Gravity.CENTER);
        titleView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleView.setLayoutParams(titleParams);
        mainLayout.addView(titleView);
        
        // === ПРИМЕР: Позиционирование относительно parent и друг друга ===
        
        // Базовый элемент слева
        TextView leftView = createTextView(this, "ЛЕВЫЙ", 200);
        leftView.setBackgroundColor(0xFF4CAF50);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(
            (int)(100 * density),
            (int)(60 * density)
        );
        leftParams.addRule(RelativeLayout.BELOW, titleView.getId());
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftParams.topMargin = (int)(16 * density);
        leftView.setLayoutParams(leftParams);
        mainLayout.addView(leftView);
        
        // Элемент справа от левого
        TextView rightView = createTextView(this, "СПРАВА", 201);
        rightView.setBackgroundColor(0xFF2196F3);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(
            (int)(100 * density),
            (int)(60 * density)
        );
        rightParams.addRule(RelativeLayout.RIGHT_OF, leftView.getId());
        rightParams.addRule(RelativeLayout.ALIGN_TOP, leftView.getId());
        rightParams.leftMargin = (int)(16 * density);
        rightView.setLayoutParams(rightParams);
        mainLayout.addView(rightView);
        
        // Элемент снизу слева
        TextView bottomLeftView = createTextView(this, "СНИЗУ", 202);
        bottomLeftView.setBackgroundColor(0xFFFF6F00);
        RelativeLayout.LayoutParams bottomLeftParams = new RelativeLayout.LayoutParams(
            (int)(100 * density),
            (int)(60 * density)
        );
        bottomLeftParams.addRule(RelativeLayout.BELOW, leftView.getId());
        bottomLeftParams.addRule(RelativeLayout.ALIGN_START, leftView.getId());
        bottomLeftParams.topMargin = (int)(16 * density);
        bottomLeftView.setLayoutParams(bottomLeftParams);
        mainLayout.addView(bottomLeftView);
        
        // Элемент в центре экрана
        TextView centerView = createTextView(this, "ЦЕНТР", 203);
        centerView.setBackgroundColor(0xFFD32F2F);
        centerView.setTextSize(14);
        centerView.setTypeface(null, android.graphics.Typeface.BOLD);
        RelativeLayout.LayoutParams centerParams = new RelativeLayout.LayoutParams(
            (int)(120 * density),
            (int)(80 * density)
        );
        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerView.setLayoutParams(centerParams);
        mainLayout.addView(centerView);
        
        setContentView(mainLayout);
        
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    private TextView createTextView(Context context, String text, int id) {
        TextView tv = new TextView(context);
        tv.setId(id);
        tv.setText(text);
        tv.setTextSize(12);
        tv.setTextColor(0xFFFFFFFF);
        tv.setGravity(android.view.Gravity.CENTER);
        return tv;
    }
}
