package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ЗАДАЧА 13: Программное создание ConstraintLayout
 * Демонстрирует создание ConstraintLayout и его элементов в коде Java
 */
public class ProgrammaticConstraintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        // Создаем ConstraintLayout программно
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        constraintLayout.setBackgroundColor(0xFFF5F5F5);
        constraintLayout.setPadding(16, 16, 16, 16);
        
        // === ЭЛЕМЕНТ 1: Заголовок ===
        TextView titleView = new TextView(this);
        titleView.setId(1001); // Важно установить ID для ConstraintLayout
        titleView.setText("📐 Программное создание ConstraintLayout");
        titleView.setTextSize(18);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setBackgroundColor(0xFF1565C0);
        titleView.setPadding(16, 16, 16, 16);
        
        ConstraintLayout.LayoutParams titleParams = new ConstraintLayout.LayoutParams(
            0, // 0 означает match_constraint в ConstraintLayout
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleView.setLayoutParams(titleParams);
        constraintLayout.addView(titleView);
        
        // === ЭЛЕМЕНТ 2: Левый верхний прямоугольник ===
        TextView box1 = createColoredBox("Левый\nВерх", 0xFF4CAF50, 2001);
        ConstraintLayout.LayoutParams box1Params = new ConstraintLayout.LayoutParams(
            120, 100
        );
        box1.setLayoutParams(box1Params);
        constraintLayout.addView(box1);
        
        // === ЭЛЕМЕНТ 3: Правый верхний прямоугольник ===
        TextView box2 = createColoredBox("Правый\nВерх", 0xFFFF5722, 2002);
        ConstraintLayout.LayoutParams box2Params = new ConstraintLayout.LayoutParams(
            120, 100
        );
        box2.setLayoutParams(box2Params);
        constraintLayout.addView(box2);
        
        // === ЭЛЕМЕНТ 4: Центральный элемент ===
        TextView centerBox = createColoredBox("Центр", 0xFF2196F3, 2003);
        ConstraintLayout.LayoutParams centerParams = new ConstraintLayout.LayoutParams(
            140, 80
        );
        centerBox.setLayoutParams(centerParams);
        constraintLayout.addView(centerBox);
        
        // === ЭЛЕМЕНТ 5: Элемент со сдвигом вправо (bias) ===
        TextView biasBox = createColoredBox("Bias\n75%", 0xFFFF6F00, 2004);
        ConstraintLayout.LayoutParams biasParams = new ConstraintLayout.LayoutParams(
            100, 70
        );
        biasBox.setLayoutParams(biasParams);
        constraintLayout.addView(biasBox);
        
        // === ЭЛЕМЕНТ 6-8: Горизонтальная цепочка ===
        TextView chainItem1 = createColoredBox("A", 0xFF4CAF50, 2005);
        TextView chainItem2 = createColoredBox("B", 0xFF2196F3, 2006);
        TextView chainItem3 = createColoredBox("C", 0xFFFF6F00, 2007);
        
        ConstraintLayout.LayoutParams chainParams = new ConstraintLayout.LayoutParams(
            60, 60
        );
        chainItem1.setLayoutParams(new ConstraintLayout.LayoutParams(60, 60));
        chainItem2.setLayoutParams(new ConstraintLayout.LayoutParams(60, 60));
        chainItem3.setLayoutParams(new ConstraintLayout.LayoutParams(60, 60));
        
        constraintLayout.addView(chainItem1);
        constraintLayout.addView(chainItem2);
        constraintLayout.addView(chainItem3);
        
        // Описание элемента цепочки
        TextView chainLabel = new TextView(this);
        chainLabel.setId(2008);
        chainLabel.setText("Горизонтальная цепочка (spread):");
        chainLabel.setTextSize(12);
        chainLabel.setTextColor(0xFF616161);
        chainLabel.setTypeface(null, android.graphics.Typeface.BOLD);
        
        ConstraintLayout.LayoutParams labelParams = new ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        chainLabel.setLayoutParams(labelParams);
        constraintLayout.addView(chainLabel);
        
        // === ЭЛЕМЕНТ 9: Элемент внизу ===
        TextView bottomView = new TextView(this);
        bottomView.setId(2009);
        bottomView.setText("Элемент внизу экрана");
        bottomView.setTextSize(14);
        bottomView.setTextColor(0xFFFFFFFF);
        bottomView.setBackgroundColor(0xFF00897B);
        bottomView.setPadding(16, 16, 16, 16);
        bottomView.setGravity(android.view.Gravity.CENTER);
        
        ConstraintLayout.LayoutParams bottomParams = new ConstraintLayout.LayoutParams(
            0, // match_constraint
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        bottomView.setLayoutParams(bottomParams);
        constraintLayout.addView(bottomView);
        
        // === ПРИМЕНЕНИЕ ОГРАНИЧЕНИЙ (ConstraintSet) ===
        ConstraintSet constraintSet = new ConstraintSet();
        
        // Заголовок: top к parent, start и end к parent
        constraintSet.connect(titleView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
        constraintSet.connect(titleView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(titleView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        
        // Левый верхний box
        constraintSet.connect(box1.getId(), ConstraintSet.TOP, titleView.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(box1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        
        // Правый верхний box
        constraintSet.connect(box2.getId(), ConstraintSet.TOP, titleView.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(box2.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        
        // Центральный box
        constraintSet.connect(centerBox.getId(), ConstraintSet.TOP, box1.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(centerBox.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(centerBox.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        
        // Box со сдвигом (bias)
        constraintSet.connect(biasBox.getId(), ConstraintSet.TOP, centerBox.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(biasBox.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(biasBox.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.setHorizontalBias(biasBox.getId(), 0.75f); // 75% сдвиг вправо
        
        // Label для цепочки
        constraintSet.connect(chainLabel.getId(), ConstraintSet.TOP, biasBox.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(chainLabel.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        
        // Горизонтальная цепочка элементов (spread)
        constraintSet.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.END,
                new int[]{chainItem1.getId(), chainItem2.getId(), chainItem3.getId()},
                null,
                ConstraintSet.CHAIN_SPREAD);
        
        constraintSet.connect(chainItem1.getId(), ConstraintSet.TOP, chainLabel.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(chainItem2.getId(), ConstraintSet.TOP, chainLabel.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(chainItem3.getId(), ConstraintSet.TOP, chainLabel.getId(), ConstraintSet.BOTTOM, 8);
        
        // Элемент внизу
        constraintSet.connect(bottomView.getId(), ConstraintSet.TOP, chainItem1.getId(), ConstraintSet.BOTTOM, 24);
        constraintSet.connect(bottomView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(bottomView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(bottomView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 16);
        
        // Применяем ConstraintSet к ConstraintLayout
        constraintSet.applyTo(constraintLayout);
        
        setContentView(constraintLayout);
        
        ViewCompat.setOnApplyWindowInsetsListener(constraintLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    /**
     * Вспомогательный метод для создания цветного box с текстом
     */
    private TextView createColoredBox(String text, int backgroundColor, int id) {
        TextView view = new TextView(this);
        view.setId(id);
        view.setText(text);
        view.setTextSize(12);
        view.setTextColor(Color.WHITE);
        view.setBackgroundColor(backgroundColor);
        view.setGravity(android.view.Gravity.CENTER);
        view.setPadding(8, 8, 8, 8);
        return view;
    }
}
