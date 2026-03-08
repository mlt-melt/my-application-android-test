package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание TableLayout, FrameLayout и GridLayout
 * Демонстрирует создание сложных layout в коде Java
 */
public class AdvancedLayoutsProgrammaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        float density = getResources().getDisplayMetrics().density;
        int paddingPx = (int)(16 * density);
        
        // Главная ScrollView для вмещения всех примеров
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        
        // Главный vertical container
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        mainLayout.setBackgroundColor(0xFFF5F5F5);
        mainLayout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        // === ЗАГОЛОВОК ===
        TextView titleView = new TextView(this);
        titleView.setText("🎬 Программное создание TableLayout, FrameLayout и GridLayout");
        titleView.setTextSize(16);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setBackgroundColor(0xFF1565C0);
        titleView.setGravity(android.view.Gravity.CENTER);
        titleView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.bottomMargin = (int)(12 * density);
        titleView.setLayoutParams(titleParams);
        mainLayout.addView(titleView);
        
        // === ПРИМЕР 1: TableLayout ===
        mainLayout.addView(createSectionLabel("1️⃣ TableLayout (программно)", density));
        mainLayout.addView(createTableLayoutProgrammatically(density));
        
        // === ПРИМЕР 2: FrameLayout ===
        mainLayout.addView(createSectionLabel("2️⃣ FrameLayout (программно)", density));
        mainLayout.addView(createFrameLayoutProgrammatically(density));
        
        // === ПРИМЕР 3: GridLayout ===
        mainLayout.addView(createSectionLabel("3️⃣ GridLayout (программно)", density));
        mainLayout.addView(createGridLayoutProgrammatically(density));
        
        scrollView.addView(mainLayout);
        setContentView(scrollView);
        
        ViewCompat.setOnApplyWindowInsetsListener(scrollView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    // === TABLEAU LAYOUT ===
    private ViewGroup createTableLayoutProgrammatically(float density) {
        LinearLayout tableContainer = new LinearLayout(this);
        tableContainer.setOrientation(LinearLayout.VERTICAL);
        tableContainer.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        containerParams.bottomMargin = (int)(12 * density);
        tableContainer.setLayoutParams(containerParams);
        
        // Заголовок строки
        LinearLayout headerRow = new LinearLayout(this);
        headerRow.setOrientation(LinearLayout.HORIZONTAL);
        String[] headers = {"Колонка 1", "Колонка 2", "Колонка 3"};
        for (String header : headers) {
            TextView headerCell = new TextView(this);
            headerCell.setText(header);
            headerCell.setTextColor(0xFFFFFFFF);
            headerCell.setBackgroundColor(0xFF4CAF50);
            headerCell.setGravity(android.view.Gravity.CENTER);
            headerCell.setPadding((int)(8 * density), (int)(8 * density), (int)(8 * density), (int)(8 * density));
            LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(0,
                (int)(50 * density), 1);
            cellParams.setMargins((int)(1 * density), (int)(1 * density), (int)(1 * density), (int)(1 * density));
            headerCell.setLayoutParams(cellParams);
            headerRow.addView(headerCell);
        }
        tableContainer.addView(headerRow);
        
        // Строки данных
        String[][] data = {{"1-1", "1-2", "1-3"}, {"2-1", "2-2", "2-3"}};
        for (int row = 0; row < data.length; row++) {
            LinearLayout dataRow = new LinearLayout(this);
            dataRow.setOrientation(LinearLayout.HORIZONTAL);
            for (int col = 0; col < data[row].length; col++) {
                TextView cell = new TextView(this);
                cell.setText(data[row][col]);
                cell.setTextColor(0xFF000000);
                cell.setBackgroundColor((row % 2 == 0) ? Color.WHITE : 0xFFE8F5E9);
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setPadding((int)(8 * density), (int)(8 * density), (int)(8 * density), (int)(8 * density));
                LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(0,
                    (int)(50 * density), 1);
                cellParams.setMargins((int)(1 * density), (int)(1 * density), (int)(1 * density), (int)(1 * density));
                cell.setLayoutParams(cellParams);
                dataRow.addView(cell);
            }
            tableContainer.addView(dataRow);
        }
        
        return tableContainer;
    }
    
    // === FRAMELAYOUT ===
    private ViewGroup createFrameLayoutProgrammatically(float density) {
        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (int)(120 * density)
        );
        frameParams.bottomMargin = (int)(12 * density);
        frameLayout.setLayoutParams(frameParams);
        frameLayout.setBackgroundColor(0xFFE8F5E9);
        
        // Фоновый слой
        TextView bgView = new TextView(this);
        bgView.setText("Background");
        bgView.setTextColor(0xFFFFFFFF);
        bgView.setBackgroundColor(0xFF4CAF50);
        bgView.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        bgView.setLayoutParams(bgParams);
        frameLayout.addView(bgView);
        
        // Передний слой (левый верхний)
        TextView topLeftView = new TextView(this);
        topLeftView.setText("Top-Left");
        topLeftView.setTextColor(0xFFFFFFFF);
        topLeftView.setBackgroundColor(0xFF2196F3);
        topLeftView.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams topLeftParams = new FrameLayout.LayoutParams(
            (int)(80 * density),
            (int)(50 * density),
            android.view.Gravity.TOP | android.view.Gravity.START
        );
        topLeftParams.setMargins((int)(8 * density), (int)(8 * density), 0, 0);
        topLeftView.setLayoutParams(topLeftParams);
        frameLayout.addView(topLeftView);
        
        // Передний слой (центр)
        TextView centerView = new TextView(this);
        centerView.setText("Center");
        centerView.setTextColor(0xFFFFFFFF);
        centerView.setBackgroundColor(0xFFFF6F00);
        centerView.setGravity(android.view.Gravity.CENTER);
        FrameLayout.LayoutParams centerParams = new FrameLayout.LayoutParams(
            (int)(80 * density),
            (int)(50 * density),
            android.view.Gravity.CENTER
        );
        centerView.setLayoutParams(centerParams);
        frameLayout.addView(centerView);
        
        // Передний слой (правый нижний)
        TextView bottomRightView = new TextView(this);
        bottomRightView.setText("Bottom-Right");
        bottomRightView.setTextColor(0xFFFFFFFF);
        bottomRightView.setBackgroundColor(0xFFD32F2F);
        bottomRightView.setGravity(android.view.Gravity.CENTER);
        bottomRightView.setTextSize(10);
        FrameLayout.LayoutParams bottomRightParams = new FrameLayout.LayoutParams(
            (int)(80 * density),
            (int)(50 * density),
            android.view.Gravity.BOTTOM | android.view.Gravity.END
        );
        bottomRightParams.setMargins(0, 0, (int)(8 * density), (int)(8 * density));
        bottomRightView.setLayoutParams(bottomRightParams);
        frameLayout.addView(bottomRightView);
        
        return frameLayout;
    }
    
    // === GRIDLAYOUT ===
    private ViewGroup createGridLayoutProgrammatically(float density) {
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setRowCount(3);
        gridLayout.setColumnCount(3);
        LinearLayout.LayoutParams gridParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        gridParams.bottomMargin = (int)(12 * density);
        gridLayout.setLayoutParams(gridParams);
        
        // Заголовок
        for (int col = 0; col < 3; col++) {
            TextView headerCell = new TextView(this);
            headerCell.setText("Col " + (col + 1));
            headerCell.setTextColor(0xFFFFFFFF);
            headerCell.setBackgroundColor(0xFF1976D2);
            headerCell.setGravity(android.view.Gravity.CENTER);
            headerCell.setPadding((int)(8 * density), (int)(8 * density), (int)(8 * density), (int)(8 * density));
            GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams();
            cellParams.rowSpec = GridLayout.spec(0);
            cellParams.columnSpec = GridLayout.spec(col, 1f); // Вес
            cellParams.width = 0;
            headerCell.setLayoutParams(cellParams);
            gridLayout.addView(headerCell);
        }
        
        // Ячейки данных
        for (int row = 1; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                TextView cell = new TextView(this);
                cell.setText((row) + "-" + (col + 1));
                cell.setTextColor(0xFF000000);
                cell.setBackgroundColor(((row + col) % 2 == 0) ? Color.WHITE : 0xFFE0E0E0);
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setPadding((int)(8 * density), (int)(8 * density), (int)(8 * density), (int)(8 * density));
                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams();
                cellParams.rowSpec = GridLayout.spec(row);
                cellParams.columnSpec = GridLayout.spec(col, 1f);
                cellParams.width = 0;
                cell.setLayoutParams(cellParams);
                gridLayout.addView(cell);
            }
        }
        
        return gridLayout;
    }
    
    // Вспомогательный метод для создания label секции
    private TextView createSectionLabel(String text, float density) {
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
}
