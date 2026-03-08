package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание ScrollView
 * Демонстрирует создание прокручиваемого контента в Java коде
 * Включает примеры как вертикального, так и горизонтального скроллинга
 */
public class ProgrammaticScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        float density = getResources().getDisplayMetrics().density;
        int paddingPx = (int)(16 * density);
        
        // Главная ScrollView для всех примеров
        ScrollView mainScroll = new ScrollView(this);
        mainScroll.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(0xFFF5F5F5);
        mainLayout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        // Заголовок
        TextView titleView = new TextView(this);
        titleView.setText("ScrollView (программно)");
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
        
        // ===== ПРИМЕР 1: Вертикальный ScrollView =====
        mainLayout.addView(createExampleLabel("Пример 1: Вертикальный ScrollView", density));
        mainLayout.addView(createVerticalScrollExample(density));
        
        // ===== ПРИМЕР 2: Горизонтальный HorizontalScrollView =====
        mainLayout.addView(createExampleLabel("Пример 2: Горизонтальный HorizontalScrollView", density));
        mainLayout.addView(createHorizontalScrollExample(density));
        
        // ===== ПРИМЕР 3: Вложенный ScrollView с таблицей =====
        mainLayout.addView(createExampleLabel("Пример 3: ScrollView содержит таблицу", density));
        mainLayout.addView(createTableInScrollExample(density));
        
        mainScroll.addView(mainLayout);
        setContentView(mainScroll);
        
        ViewCompat.setOnApplyWindowInsetsListener(mainScroll, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    /**
     * Пример 1: Вертикальный ScrollView с LinearLayout содержимым
     */
    private ScrollView createVerticalScrollExample(float density) {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(150 * density)
        );
        scrollParams.bottomMargin = (int)(12 * density);
        scrollView.setLayoutParams(scrollParams);
        
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        
        // Добавляем несколько элементов для демонстрации скроллинга
        for (int i = 0; i < 10; i++) {
            TextView itemView = new TextView(this);
            itemView.setText("Элемент #" + (i + 1) + " - Этот текст демонстрирует вертикальный скроллинг");
            itemView.setTextColor(0xFF333333);
            itemView.setBackgroundColor(i % 2 == 0 ? Color.WHITE : 0xFFF5F5F5);
            itemView.setPadding((int)(12 * density), (int)(8 * density), 
                               (int)(12 * density), (int)(8 * density));
            itemView.setTextSize(11);
            
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            itemParams.bottomMargin = (int)(1 * density);
            itemView.setLayoutParams(itemParams);
            contentLayout.addView(itemView);
        }
        
        scrollView.addView(contentLayout);
        return scrollView;
    }
    
    /**
     * Пример 2: Горизонтальный HorizontalScrollView
     */
    private HorizontalScrollView createHorizontalScrollExample(float density) {
        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(120 * density)
        );
        scrollParams.bottomMargin = (int)(12 * density);
        scrollView.setLayoutParams(scrollParams);
        
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        
        // Горизонтальные "карточки"
        int[] colors = {0xFF2196F3, 0xFF4CAF50, 0xFFFF6F00, 0xFFD32F2F, 0xFF9C27B0};
        String[] titles = {"Карточка 1", "Карточка 2", "Карточка 3", "Карточка 4", "Карточка 5"};
        
        for (int i = 0; i < titles.length; i++) {
            TextView cardView = new TextView(this);
            cardView.setText(titles[i] + "\nСкролл →");
            cardView.setTextColor(0xFFFFFFFF);
            cardView.setBackgroundColor(colors[i]);
            cardView.setGravity(android.view.Gravity.CENTER);
            cardView.setTextSize(12);
            
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                (int)(100 * density),
                ViewGroup.LayoutParams.MATCH_PARENT
            );
            cardParams.setMargins((int)(8 * density), (int)(8 * density), 
                                 (int)(8 * density), (int)(8 * density));
            cardView.setLayoutParams(cardParams);
            contentLayout.addView(cardView);
        }
        
        scrollView.addView(contentLayout);
        return scrollView;
    }
    
    /**
     * Пример 3: ScrollView с таблицей (сетка элементов)
     */
    private ScrollView createTableInScrollExample(float density) {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(180 * density)
        );
        scrollView.setLayoutParams(scrollParams);
        
        LinearLayout tableLayout = new LinearLayout(this);
        tableLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        
        // Заголовок таблицы
        LinearLayout headerRow = new LinearLayout(this);
        headerRow.setOrientation(LinearLayout.HORIZONTAL);
        headerRow.setBackgroundColor(0xFF2196F3);
        String[] headers = {"№", "Название", "Статус"};
        for (String header : headers) {
            TextView headerCell = new TextView(this);
            headerCell.setText(header);
            headerCell.setTextColor(0xFFFFFFFF);
            headerCell.setGravity(android.view.Gravity.CENTER);
            headerCell.setTypeface(null, android.graphics.Typeface.BOLD);
            headerCell.setPadding((int)(8 * density), (int)(8 * density), 
                                 (int)(8 * density), (int)(8 * density));
            
            LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            headerCell.setLayoutParams(cellParams);
            headerRow.addView(headerCell);
        }
        tableLayout.addView(headerRow);
        
        // Строки таблицы
        String[][] data = {
            {"1", "Товар A", "✓"},
            {"2", "Товар B", "✗"},
            {"3", "Товар C", "✓"},
            {"4", "Товар D", "✓"},
            {"5", "Товар E", "✗"},
            {"6", "Товар F", "✓"}
        };
        
        for (int row = 0; row < data.length; row++) {
            LinearLayout dataRow = new LinearLayout(this);
            dataRow.setOrientation(LinearLayout.HORIZONTAL);
            dataRow.setBackgroundColor(row % 2 == 0 ? 0xFFF5F5F5 : Color.WHITE);
            
            for (int col = 0; col < data[row].length; col++) {
                TextView cell = new TextView(this);
                cell.setText(data[row][col]);
                cell.setTextColor(0xFF333333);
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setPadding((int)(8 * density), (int)(8 * density), 
                               (int)(8 * density), (int)(8 * density));
                cell.setTextSize(11);
                
                // Разный цвет для статуса
                if (col == 2) {
                    if ("✓".equals(data[row][col])) {
                        cell.setTextColor(0xFF4CAF50);
                        cell.setTypeface(null, android.graphics.Typeface.BOLD);
                    } else {
                        cell.setTextColor(0xFFD32F2F);
                        cell.setTypeface(null, android.graphics.Typeface.BOLD);
                    }
                }
                
                LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                cell.setLayoutParams(cellParams);
                dataRow.addView(cell);
            }
            tableLayout.addView(dataRow);
        }
        
        scrollView.addView(tableLayout);
        return scrollView;
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
