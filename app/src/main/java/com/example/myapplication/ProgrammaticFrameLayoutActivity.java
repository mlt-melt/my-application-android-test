package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание FrameLayout
 * Демонстрирует создание слоистых интерфейсов с использованием
 * FrameLayout и layout_gravity для позиционирования элементов
 */
public class ProgrammaticFrameLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        float density = getResources().getDisplayMetrics().density;
        int paddingPx = (int)(16 * density);
        
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(0xFFF5F5F5);
        mainLayout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        // Заголовок
        TextView titleView = new TextView(this);
        titleView.setText("FrameLayout (программно)");
        titleView.setTextSize(18);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        titleView.setTextColor(0xFF1565C0);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.bottomMargin = (int)(16 * density);
        titleView.setLayoutParams(titleParams);
        mainLayout.addView(titleView);
        
        // ===== ПРИМЕР 1: Базовое слоистое расположение =====
        mainLayout.addView(createExampleLabel("Пример 1: Слои с позиционированием", density));
        mainLayout.addView(createBasicLayeredFrame(density));
        
        // ===== ПРИМЕР 2: Все 9 позиций gravity =====
        mainLayout.addView(createExampleLabel("Пример 2: Все 9 позиций gravity", density));
        mainLayout.addView(createNinePositionsFrame(density));
        
        // ===== ПРИМЕР 3: Z-order с прозрачностью (карточки) =====
        mainLayout.addView(createExampleLabel("Пример 3: Z-order с прозрачностью", density));
        mainLayout.addView(createTransparentLayersFrame(density));
        
        setContentView(mainLayout);
        
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + paddingPx, systemBars.top + paddingPx,
                         systemBars.right + paddingPx, systemBars.bottom + paddingPx);
            return insets;
        });
    }
    
    /**
     * Пример 1: Фоновый слой + несколько элементов на разных позициях
     */
    private FrameLayout createBasicLayeredFrame(float density) {
        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(150 * density)
        );
        frameParams.bottomMargin = (int)(12 * density);
        frameLayout.setLayoutParams(frameParams);
        
        // Фоновый слой
        TextView bgLayer = new TextView(this);
        bgLayer.setText("Background\n(Z-index: 1)");
        bgLayer.setTextColor(0xFFFFFFFF);
        bgLayer.setBackgroundColor(0xFF4CAF50);
        bgLayer.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        bgLayer.setLayoutParams(bgParams);
        frameLayout.addView(bgLayer);
        
        // Левый верхний слой
        TextView topLeftLayer = new TextView(this);
        topLeftLayer.setText("Top\nLeft");
        topLeftLayer.setTextColor(0xFFFFFFFF);
        topLeftLayer.setBackgroundColor(0xFF2196F3);
        topLeftLayer.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams topLeftParams = new FrameLayout.LayoutParams(
            (int)(70 * density),
            (int)(50 * density),
            android.view.Gravity.TOP | android.view.Gravity.START
        );
        topLeftParams.setMargins((int)(8 * density), (int)(8 * density), 0, 0);
        topLeftLayer.setLayoutParams(topLeftParams);
        frameLayout.addView(topLeftLayer);
        
        // Центральный слой
        TextView centerLayer = new TextView(this);
        centerLayer.setText("Center");
        centerLayer.setTextColor(0xFFFFFFFF);
        centerLayer.setBackgroundColor(0xFFFF6F00);
        centerLayer.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams centerParams = new FrameLayout.LayoutParams(
            (int)(70 * density),
            (int)(50 * density),
            android.view.Gravity.CENTER
        );
        centerLayer.setLayoutParams(centerParams);
        frameLayout.addView(centerLayer);
        
        // Правый нижний слой
        TextView bottomRightLayer = new TextView(this);
        bottomRightLayer.setText("Bottom\nRight");
        bottomRightLayer.setTextColor(0xFFFFFFFF);
        bottomRightLayer.setBackgroundColor(0xFFD32F2F);
        bottomRightLayer.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams bottomRightParams = new FrameLayout.LayoutParams(
            (int)(70 * density),
            (int)(50 * density),
            android.view.Gravity.BOTTOM | android.view.Gravity.END
        );
        bottomRightParams.setMargins(0, 0, (int)(8 * density), (int)(8 * density));
        bottomRightLayer.setLayoutParams(bottomRightParams);
        frameLayout.addView(bottomRightLayer);
        
        return frameLayout;
    }
    
    /**
     * Пример 2: 9 позиций gravity - все комбинации
     */
    private FrameLayout createNinePositionsFrame(float density) {
        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(200 * density)
        );
        frameParams.bottomMargin = (int)(12 * density);
        frameLayout.setLayoutParams(frameParams);
        frameLayout.setBackgroundColor(0xFFEEEEEE);
        
        // 9 позиций с соответствующим gravity
        int[] gravities = {
            android.view.Gravity.TOP | android.view.Gravity.START,
            android.view.Gravity.TOP | android.view.Gravity.CENTER_HORIZONTAL,
            android.view.Gravity.TOP | android.view.Gravity.END,
            android.view.Gravity.CENTER_VERTICAL | android.view.Gravity.START,
            android.view.Gravity.CENTER,
            android.view.Gravity.CENTER_VERTICAL | android.view.Gravity.END,
            android.view.Gravity.BOTTOM | android.view.Gravity.START,
            android.view.Gravity.BOTTOM | android.view.Gravity.CENTER_HORIZONTAL,
            android.view.Gravity.BOTTOM | android.view.Gravity.END
        };
        
        String[] labels = {
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9"
        };
        
        int[] colors = {
            0xFF1976D2, 0xFF1976D2, 0xFF1976D2,
            0xFF1976D2, 0xFFF57C00, 0xFF1976D2,
            0xFF1976D2, 0xFF1976D2, 0xFF1976D2
        };
        
        for (int i = 0; i < 9; i++) {
            TextView posView = new TextView(this);
            posView.setText(labels[i]);
            posView.setTextColor(0xFFFFFFFF);
            posView.setBackgroundColor(colors[i]);
            posView.setGravity(android.view.Gravity.CENTER);
            posView.setTypeface(null, android.graphics.Typeface.BOLD);
            posView.setTextSize(12);
            
            FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(
                (int)(40 * density),
                (int)(40 * density),
                gravities[i]
            );
            posParams.setMargins((int)(4 * density), (int)(4 * density), 
                                (int)(4 * density), (int)(4 * density));
            posView.setLayoutParams(posParams);
            frameLayout.addView(posView);
        }
        
        return frameLayout;
    }
    
    /**
     * Пример 3: Слои с прозрачностью (эффект глубины)
     */
    private FrameLayout createTransparentLayersFrame(float density) {
        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(180 * density)
        );
        frameLayout.setLayoutParams(frameParams);
        frameLayout.setBackgroundColor(0xFFF5F5F5);
        
        // Слой 1: Фоновый
        TextView layer1 = new TextView(this);
        layer1.setText("Слой 1\nФон");
        layer1.setTextColor(0xFF000000);
        layer1.setBackgroundColor(0xFFC8E6C9);
        layer1.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams layer1Params = new FrameLayout.LayoutParams(
            (int)(280 * density),
            (int)(120 * density),
            android.view.Gravity.CENTER
        );
        layer1.setLayoutParams(layer1Params);
        frameLayout.addView(layer1);
        
        // Слой 2: С прозрачностью 80%
        TextView layer2 = new TextView(this);
        layer2.setText("Слой 2\nПрозрачность");
        layer2.setTextColor(0xFFFFFFFF);
        layer2.setBackgroundColor(0xCC2196F3); // CC = 80% opacity
        layer2.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams layer2Params = new FrameLayout.LayoutParams(
            (int)(240 * density),
            (int)(100 * density),
            android.view.Gravity.CENTER
        );
        layer2Params.topMargin = (int)(20 * density);
        layer2Params.leftMargin = (int)(20 * density);
        layer2.setLayoutParams(layer2Params);
        frameLayout.addView(layer2);
        
        // Слой 3: В самом верху (Z-index максимальный) с еще большей прозрачностью
        TextView layer3 = new TextView(this);
        layer3.setText("Слой 3\nВерх");
        layer3.setTextColor(0xFFFFFFFF);
        layer3.setBackgroundColor(0x99D32F2F); // 99 = 60% opacity
        layer3.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams layer3Params = new FrameLayout.LayoutParams(
            (int)(200 * density),
            (int)(80 * density),
            android.view.Gravity.CENTER
        );
        layer3Params.topMargin = (int)(40 * density);
        layer3Params.leftMargin = (int)(40 * density);
        layer3.setLayoutParams(layer3Params);
        frameLayout.addView(layer3);
        
        return frameLayout;
    }
    
    private TextView createExampleLabel(String text, float density) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(12);
        label.setTextColor(0xFF0D47A1);
        label.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin = (int)(8 * density);
        label.setLayoutParams(params);
        return label;
    }
}
