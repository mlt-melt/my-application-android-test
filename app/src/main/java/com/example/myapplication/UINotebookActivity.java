package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
 * ПРАКТИЧЕСКАЯ РАБОТА: Создание и управление интерфейсом в Android Studio
 * 
 * Задача 1: Изучить порядок выполнения приложения (lifecycle методы показаны в конце)
 * Задача 3: Создать интерфейс в коде Java (программное создание элементов)
 * Задача 6: Реализовать получение и управление визуальными элементами
 * Задача 7-8: Определить и установить размеры экрана
 * Задача 10: Программная установка ширины и высоты
 * Задача 12: Программная установка отступов (padding)
 */
public class UINotebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        // ===== ЗАДАЧА 7-8: ПОЛУЧЕНИЕ РАЗМЕРОВ ЭКРАНА =====
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float density = displayMetrics.density;
        
        // Переводим в DP (Device Independent Pixels)
        int screenWidthDP = (int) (screenWidth / density);
        int screenHeightDP = (int) (screenHeight / density);
        
        // ===== ЗАДАЧА 3: ПРОГРАММНОЕ СОЗДАНИЕ ИНТЕРФЕЙСА =====
        // Создаем главный контейнер LinearLayout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(0xFFF5F5F5); // Светло-серый фон
        
        // ===== ЗАДАЧА 10: ПРОГРАММНОЕ УСТАНОВЛЕНИЕ PADDING =====
        int paddingDP = 16;
        int paddingPx = (int) (paddingDP * density);
        mainLayout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        // Параметры для главного Layout
        FrameLayout.LayoutParams mainParams = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        mainLayout.setLayoutParams(mainParams);
        
        // ----- ЭЛЕМЕНТ 1: Заголовок с информацией об экране -----
        TextView screenInfoTitle = new TextView(this);
        screenInfoTitle.setText("📱 Информация об экране устройства");
        screenInfoTitle.setTextSize(18);
        screenInfoTitle.setTextColor(0xFF1565C0);
        screenInfoTitle.setBackgroundColor(0xFFE8F5E9);
        
        // ===== ЗАДАЧА 10: ПРОГРАММНАЯ УСТАНОВКА PADDING ДЛЯ ЭЛЕМЕНТА =====
        screenInfoTitle.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.bottomMargin = (int) (8 * density); // 8DP отступ снизу
        screenInfoTitle.setLayoutParams(titleParams);
        mainLayout.addView(screenInfoTitle);
        
        // ----- ЭЛЕМЕНТ 2: Размеры в пикселях -----
        TextView screenSizePx = new TextView(this);
        screenSizePx.setText("Размер в пикселях (px):\n" +
                           "Ширина: " + screenWidth + " px\n" +
                           "Высота: " + screenHeight + " px");
        screenSizePx.setTextSize(14);
        screenSizePx.setTextColor(0xFF424242);
        screenSizePx.setBackgroundColor(Color.WHITE);
        screenSizePx.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams sizeParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        sizeParams.bottomMargin = (int) (8 * density);
        screenSizePx.setLayoutParams(sizeParams);
        mainLayout.addView(screenSizePx);
        
        // ----- ЭЛЕМЕНТ 3: Размеры в DP -----
        TextView screenSizeDP = new TextView(this);
        screenSizeDP.setText("Размер в DP (Device Independent Pixels):\n" +
                            "Ширина: " + screenWidthDP + " dp\n" +
                            "Высота: " + screenHeightDP + " dp");
        screenSizeDP.setTextSize(14);
        screenSizeDP.setTextColor(0xFF424242);
        screenSizeDP.setBackgroundColor(Color.WHITE);
        screenSizeDP.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams sizeDP_Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        sizeDP_Params.bottomMargin = (int) (8 * density);
        screenSizeDP.setLayoutParams(sizeDP_Params);
        mainLayout.addView(screenSizeDP);
        
        // ----- ЭЛЕМЕНТ 4: Плотность пикселей -----
        TextView densityInfo = new TextView(this);
        densityInfo.setText("Плотность экрана (density): " + String.format("%.2f", density));
        densityInfo.setTextSize(14);
        densityInfo.setTextColor(0xFF424242);
        densityInfo.setBackgroundColor(Color.WHITE);
        densityInfo.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams densityParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        densityParams.bottomMargin = (int) (8 * density);
        densityInfo.setLayoutParams(densityParams);
        mainLayout.addView(densityInfo);
        
        // ----- ЭЛЕМЕНТ 5: Примеры размеров элементов -----
        TextView exampleTitle = new TextView(this);
        exampleTitle.setText("📐 Примеры размеров элементов");
        exampleTitle.setTextSize(16);
        exampleTitle.setTextColor(0xFFD32F2F);
        exampleTitle.setBackgroundColor(0xFFFFEBEE);
        exampleTitle.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams exTitleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        exTitleParams.topMargin = (int) (16 * density);
        exTitleParams.bottomMargin = (int) (8 * density);
        exampleTitle.setLayoutParams(exTitleParams);
        mainLayout.addView(exampleTitle);
        
        // ----- ЭЛЕМЕНТ 6: Кнопка с фиксированными размерами -----
        TextView buttonExample = new TextView(this);
        buttonExample.setText("Кнопка: 200px × 50px");
        buttonExample.setTextSize(14);
        buttonExample.setTextColor(Color.WHITE);
        buttonExample.setBackgroundColor(0xFF1976D2);
        buttonExample.setPadding(paddingPx, (int)(8*density), paddingPx, (int)(8*density));
        
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            (int)(200 * density),  // ===== ЗАДАЧА 10: ПРОГРАММНАЯ УСТАНОВКА ШИРИНЫ =====
            (int)(50 * density)    // ===== ЗАДАЧА 10: ПРОГРАММНАЯ УСТАНОВКА ВЫСОТЫ =====
        );
        buttonParams.bottomMargin = (int) (8 * density);
        buttonExample.setLayoutParams(buttonParams);
        mainLayout.addView(buttonExample);
        
        // ----- ЭЛЕМЕНТ 7: Элемент с match_parent (демонстрация вариантов) -----
        TextView matchParentExample = new TextView(this);
        matchParentExample.setText("Элемент: match_parent (ширина)");
        matchParentExample.setTextSize(12);
        matchParentExample.setTextColor(Color.WHITE);
        matchParentExample.setBackgroundColor(0xFF00897B);
        matchParentExample.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams matchParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(60 * density)
        );
        matchParams.bottomMargin = (int) (8 * density);
        matchParentExample.setLayoutParams(matchParams);
        mainLayout.addView(matchParentExample);
        
        // ----- ЭЛЕМЕНТ 8: Элемент с wrap_content -----
        TextView wrapContentExample = new TextView(this);
        wrapContentExample.setText("wrap_content - размер по содержимому");
        wrapContentExample.setTextSize(13);
        wrapContentExample.setTextColor(Color.WHITE);
        wrapContentExample.setBackgroundColor(0xFFF57C00);
        wrapContentExample.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        wrapParams.bottomMargin = (int) (8 * density);
        wrapContentExample.setLayoutParams(wrapParams);
        mainLayout.addView(wrapContentExample);
        
        // ===== ЗАДАЧА 10-12: ПРИМЕРЫ С MARGIN И PADDING =====
        // Контейнер с большим margin
        LinearLayout marginContainer = new LinearLayout(this);
        marginContainer.setBackgroundColor(0xFFE1F5FE);
        marginContainer.setPadding(paddingPx, paddingPx, paddingPx, paddingPx); // Padding внутри
        
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        containerParams.topMargin = (int)(12 * density);     // Margin сверху
        containerParams.bottomMargin = (int)(12 * density);  // Margin снизу
        marginContainer.setLayoutParams(containerParams);
        
        TextView marginLabel = new TextView(this);
        marginLabel.setText("Контейнер с margin (внешний отступ) и padding (внутренний отступ)");
        marginLabel.setTextSize(12);
        marginLabel.setTextColor(0xFF01579B);
        marginContainer.addView(marginLabel);
        
        mainLayout.addView(marginContainer);
        
        // Устанавливаем созданный layout как содержимое
        setContentView(mainLayout);
        
        // ===== ЗАДАЧА 1: ИЗУЧЕНИЕ LIFECYCLE =====
        // WindowInsets слушатель (Edge-to-Edge поддержка)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    // ===== ЗАДАЧА 1: LIFECYCLE МЕТОДЫ =====
    @Override
    protected void onStart() {
        super.onStart();
        // Activity стал видимым, но еще не в фокусе
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Activity получил фокус и готов к взаимодействию пользователя
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Activity теряет фокус (другая activity получила фокус)
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Activity больше не видима
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Activity перезапущена из состояния конца
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity удаляется из памяти
    }
}
