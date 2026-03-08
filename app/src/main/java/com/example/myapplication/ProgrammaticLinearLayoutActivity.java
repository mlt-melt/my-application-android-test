package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание LinearLayout в MainActivity
 * Демонстрирует:
 * - Programmatic creation of LinearLayout
 * - Layout weight для распределения пространства
 * - android:layout_gravity для выравнивания элементов
 */
public class ProgrammaticLinearLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        // Получаем плотность экрана для конвертации dp в px
        float density = getResources().getDisplayMetrics().density;
        
        // СОЗДАНИЕ ГЛАВНОГО КОНТЕЙНЕРА LinearLayout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(0xFFF5F5F5);
        mainLayout.setPadding(
            (int)(16 * density), (int)(16 * density),
            (int)(16 * density), (int)(16 * density)
        );
        
        // === ЗАГОЛОВОК ===
        TextView titleView = new TextView(this);
        titleView.setText("📐 Программное создание LinearLayout");
        titleView.setTextSize(18);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setBackgroundColor(0xFF1565C0);
        titleView.setPadding(
            (int)(12 * density), (int)(12 * density),
            (int)(12 * density), (int)(12 * density)
        );
        titleView.setGravity(android.view.Gravity.CENTER);
        
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.bottomMargin = (int)(16 * density);
        titleView.setLayoutParams(titleParams);
        mainLayout.addView(titleView);
        
        // === ПРИМЕР 1: Горизонтальный layout с весом (вес 1, 1, 1) ===
        TextView example1Label = createLabel("1️⃣ Вес (1, 1, 1) - равномерное распределение:");
        mainLayout.addView(example1Label);
        
        // Контейнер для примера 1 (горизонтальный)
        LinearLayout horizontalLayout1 = new LinearLayout(this);
        horizontalLayout1.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout1.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams horizontalParams1 = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(60 * density)
        );
        horizontalParams1.bottomMargin = (int)(12 * density);
        horizontalLayout1.setLayoutParams(horizontalParams1);
        
        // Элементы с весом
        for (int i = 1; i <= 3; i++) {
            TextView child = new TextView(this);
            child.setText("W:" + i);
            child.setTextSize(12);
            child.setTextColor(0xFFFFFFFF);
            child.setGravity(android.view.Gravity.CENTER);
            child.setBackgroundColor(getColorForIndex(i));
            
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                0, // width = 0 когда используется weight
                ViewGroup.LayoutParams.MATCH_PARENT,
                1  // weight = 1 для всех (равномерное распределение)
            );
            childParams.setMargins(
                (int)(4 * density), (int)(4 * density),
                (int)(4 * density), (int)(4 * density)
            );
            child.setLayoutParams(childParams);
            horizontalLayout1.addView(child);
        }
        mainLayout.addView(horizontalLayout1);
        
        // === ПРИМЕР 2: Горизонтальный layout с разными весами (вес 1, 2, 3) ===
        TextView example2Label = createLabel("2️⃣ Вес (1, 2, 3) - неравномерное распределение:");
        mainLayout.addView(example2Label);
        
        LinearLayout horizontalLayout2 = new LinearLayout(this);
        horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout2.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams horizontalParams2 = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(60 * density)
        );
        horizontalParams2.bottomMargin = (int)(12 * density);
        horizontalLayout2.setLayoutParams(horizontalParams2);
        
        // Элементы с разными весами
        int[] weights = {1, 2, 3};
        for (int i = 0; i < weights.length; i++) {
            TextView child = new TextView(this);
            child.setText("W:" + weights[i]);
            child.setTextSize(12);
            child.setTextColor(0xFFFFFFFF);
            child.setGravity(android.view.Gravity.CENTER);
            child.setBackgroundColor(getColorForIndex(i + 1));
            
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                weights[i]  // Разные веса
            );
            childParams.setMargins(
                (int)(4 * density), (int)(4 * density),
                (int)(4 * density), (int)(4 * density)
            );
            child.setLayoutParams(childParams);
            horizontalLayout2.addView(child);
        }
        mainLayout.addView(horizontalLayout2);
        
        // === ПРИМЕР 3: Вертикальный layout с весом ===
        TextView example3Label = createLabel("3️⃣ Вертикальное распределение (weight):");
        mainLayout.addView(example3Label);
        
        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(150 * density)
        );
        verticalParams.bottomMargin = (int)(12 * density);
        verticalLayout.setLayoutParams(verticalParams);
        
        int[] vertWeights = {1, 2, 3};
        for (int i = 0; i < vertWeights.length; i++) {
            TextView child = new TextView(this);
            child.setText("Weight " + vertWeights[i] + " (" + getPercentage(vertWeights[i], 6) + "%)");
            child.setTextSize(11);
            child.setTextColor(0xFFFFFFFF);
            child.setGravity(android.view.Gravity.CENTER);
            child.setBackgroundColor(getColorForIndex(i + 1));
            
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                vertWeights[i]
            );
            childParams.setMargins(
                (int)(4 * density), (int)(4 * density),
                (int)(4 * density), (int)(4 * density)
            );
            child.setLayoutParams(childParams);
            verticalLayout.addView(child);
        }
        mainLayout.addView(verticalLayout);
        
        // === ПРИМЕР 4: layout_gravity демонстрация ===
        TextView example4Label = createLabel("4️⃣ android:layout_gravity (выравнивание в контейнере):");
        mainLayout.addView(example4Label);
        
        LinearLayout gravityLayout = new LinearLayout(this);
        gravityLayout.setOrientation(LinearLayout.HORIZONTAL);
        gravityLayout.setBackgroundColor(0xFFF0F0F0);
        LinearLayout.LayoutParams gravityLayoutParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(100 * density)
        );
        gravityLayoutParams.bottomMargin = (int)(12 * density);
        gravityLayout.setLayoutParams(gravityLayoutParams);
        
        // layout_gravity="top"
        TextView topView = new TextView(this);
        topView.setText("top");
        topView.setTextSize(11);
        topView.setTextColor(0xFFFFFFFF);
        topView.setGravity(android.view.Gravity.CENTER);
        topView.setBackgroundColor(0xFF4CAF50);
        LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(
            (int)(60 * density),
            (int)(50 * density)
        );
        topParams.gravity = android.view.Gravity.TOP;
        topParams.setMargins((int)(4 * density), (int)(4 * density), (int)(4 * density), (int)(4 * density));
        topView.setLayoutParams(topParams);
        gravityLayout.addView(topView);
        
        // layout_gravity="center_vertical"
        TextView centerView = new TextView(this);
        centerView.setText("center_v");
        centerView.setTextSize(11);
        centerView.setTextColor(0xFFFFFFFF);
        centerView.setGravity(android.view.Gravity.CENTER);
        centerView.setBackgroundColor(0xFF2196F3);
        LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(
            (int)(60 * density),
            (int)(50 * density)
        );
        centerParams.gravity = android.view.Gravity.CENTER_VERTICAL;
        centerParams.setMargins((int)(4 * density), (int)(4 * density), (int)(4 * density), (int)(4 * density));
        centerView.setLayoutParams(centerParams);
        gravityLayout.addView(centerView);
        
        // layout_gravity="bottom"
        TextView bottomView = new TextView(this);
        bottomView.setText("bottom");
        bottomView.setTextSize(11);
        bottomView.setTextColor(0xFFFFFFFF);
        bottomView.setGravity(android.view.Gravity.CENTER);
        bottomView.setBackgroundColor(0xFFFF6F00);
        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
            (int)(60 * density),
            (int)(50 * density)
        );
        bottomParams.gravity = android.view.Gravity.BOTTOM;
        bottomParams.setMargins((int)(4 * density), (int)(4 * density), (int)(4 * density), (int)(4 * density));
        bottomView.setLayoutParams(bottomParams);
        gravityLayout.addView(bottomView);
        
        mainLayout.addView(gravityLayout);
        
        // === ПРИМЕР 5: Смешанное использование weight и фиксированных размеров ===
        TextView example5Label = createLabel("5️⃣ Смешанное использование weight и фиксированных размеров:");
        mainLayout.addView(example5Label);
        
        LinearLayout mixedLayout = new LinearLayout(this);
        mixedLayout.setOrientation(LinearLayout.HORIZONTAL);
        mixedLayout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams mixedParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(50 * density)
        );
        mixedParams.bottomMargin = (int)(12 * density);
        mixedLayout.setLayoutParams(mixedParams);
        
        // Фиксированный размер 80dp
        TextView fixedView = new TextView(this);
        fixedView.setText("80dp");
        fixedView.setTextSize(11);
        fixedView.setTextColor(0xFFFFFFFF);
        fixedView.setGravity(android.view.Gravity.CENTER);
        fixedView.setBackgroundColor(0xFFD32F2F);
        LinearLayout.LayoutParams fixedParams = new LinearLayout.LayoutParams(
            (int)(80 * density),
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        fixedParams.setMargins((int)(4 * density), (int)(4 * density), (int)(4 * density), (int)(4 * density));
        fixedView.setLayoutParams(fixedParams);
        mixedLayout.addView(fixedView);
        
        // Занимает оставшееся место
        TextView flexibleView = new TextView(this);
        flexibleView.setText("Оставшееся место");
        flexibleView.setTextSize(11);
        flexibleView.setTextColor(0xFFFFFFFF);
        flexibleView.setGravity(android.view.Gravity.CENTER);
        flexibleView.setBackgroundColor(0xFF7B1FA2);
        LinearLayout.LayoutParams flexibleParams = new LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
            1  // weight = 1
        );
        flexibleParams.setMargins((int)(4 * density), (int)(4 * density), (int)(4 * density), (int)(4 * density));
        flexibleView.setLayoutParams(flexibleParams);
        mixedLayout.addView(flexibleView);
        
        mainLayout.addView(mixedLayout);
        
        // Устанавливаем главный layout
        setContentView(mainLayout);
        
        // Window insets слушатель
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    // Вспомогательный метод для создания label
    private TextView createLabel(String text) {
        float density = getResources().getDisplayMetrics().density;
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(13);
        label.setTextColor(0xFF356B2C);
        label.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin = (int)(8 * density);
        label.setLayoutParams(params);
        return label;
    }
    
    // Получить цвет по индексу
    private int getColorForIndex(int index) {
        int[] colors = {0xFF4CAF50, 0xFF2196F3, 0xFFFF6F00, 0xFFD32F2F};
        return colors[(index - 1) % colors.length];
    }
    
    // Вычислить процент
    private String getPercentage(int weight, int totalWeight) {
        return String.valueOf((weight * 100) / totalWeight);
    }
}
