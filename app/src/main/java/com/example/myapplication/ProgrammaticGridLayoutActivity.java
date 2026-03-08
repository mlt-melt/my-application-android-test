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
import androidx.gridlayout.widget.GridLayout;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание GridLayout
 * Демонстрирует создание сеток в Java коде с использованием
 * GridLayout.LayoutParams, layout_row, layout_column и spans
 */
public class ProgrammaticGridLayoutActivity extends AppCompatActivity {

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
        titleView.setText("GridLayout (программно)");
        titleView.setTextSize(18);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        titleView.setTextColor(0xFF1976D2);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.bottomMargin = (int)(16 * density);
        titleView.setLayoutParams(titleParams);
        mainLayout.addView(titleView);
        
        // ===== ПРИМЕР 1: Базовая сетка 3x3 =====
        mainLayout.addView(createExampleLabel("Пример 1: Базовая сетка 3x3", density));
        GridLayout grid1 = createBasicGrid(density);
        LinearLayout.LayoutParams grid1Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        grid1Params.bottomMargin = (int)(12 * density);
        grid1.setLayoutParams(grid1Params);
        mainLayout.addView(grid1);
        
        // ===== ПРИМЕР 2: Сетка со spans (объединение ячеек) =====
        mainLayout.addView(createExampleLabel("Пример 2: Сетка со spans", density));
        GridLayout grid2 = createGridWithSpans(density);
        LinearLayout.LayoutParams grid2Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        grid2Params.bottomMargin = (int)(12 * density);
        grid2.setLayoutParams(grid2Params);
        mainLayout.addView(grid2);
        
        // ===== ПРИМЕР 3: Сетка с weighted размерами =====
        mainLayout.addView(createExampleLabel("Пример 3: Сетка с layout_columnWeight", density));
        GridLayout grid3 = createWeightedGrid(density);
        LinearLayout.LayoutParams grid3Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        grid3.setLayoutParams(grid3Params);
        mainLayout.addView(grid3);
        
        setContentView(mainLayout);
        
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + paddingPx, systemBars.top + paddingPx,
                         systemBars.right + paddingPx, systemBars.bottom + paddingPx);
            return insets;
        });
    }
    
    /**
     * Пример 1: Простая сетка 3x3 без специальных функций
     */
    private GridLayout createBasicGrid(float density) {
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(3);
        grid.setColumnCount(3);
        grid.setBackgroundColor(Color.WHITE);
        
        int cellSize = (int)(60 * density);
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                TextView cell = new TextView(this);
                cell.setText("R" + row + "\nC" + col);
                cell.setTextColor(0xFFFFFFFF);
                cell.setBackgroundColor(getColorForCell(row, col));
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setTextSize(10);
                
                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams();
                cellParams.rowSpec = GridLayout.spec(row);
                cellParams.columnSpec = GridLayout.spec(col);
                cellParams.width = cellSize;
                cellParams.height = cellSize;
                cellParams.setMargins(2, 2, 2, 2);
                
                cell.setLayoutParams(cellParams);
                grid.addView(cell);
            }
        }
        
        return grid;
    }
    
    /**
     * Пример 2: Сетка с объединением ячеек (rowSpan, columnSpan)
     */
    private GridLayout createGridWithSpans(float density) {
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(4);
        grid.setColumnCount(3);
        grid.setBackgroundColor(Color.WHITE);
        
        int cellSize = (int)(55 * density);
        
        // Заголовок (span 3 колонки)
        TextView header = new TextView(this);
        header.setText("Объединенный заголовок\n(columnSpan=3)");
        header.setTextColor(0xFFFFFFFF);
        header.setBackgroundColor(0xFF2196F3);
        header.setGravity(android.view.Gravity.CENTER);
        header.setTextSize(11);
        
        GridLayout.LayoutParams headerParams = new GridLayout.LayoutParams();
        headerParams.rowSpec = GridLayout.spec(0, 1);
        headerParams.columnSpec = GridLayout.spec(0, 3); // span 3 columns
        headerParams.width = cellSize * 3 + 6;
        headerParams.height = cellSize;
        headerParams.setMargins(2, 2, 2, 2);
        header.setLayoutParams(headerParams);
        grid.addView(header);
        
        // Левая колонка (span 2 строки)
        TextView leftCol = new TextView(this);
        leftCol.setText("Левая\n(rowSpan=2)");
        leftCol.setTextColor(0xFFFFFFFF);
        leftCol.setBackgroundColor(0xFF4CAF50);
        leftCol.setGravity(android.view.Gravity.CENTER);
        leftCol.setTextSize(10);
        
        GridLayout.LayoutParams leftColParams = new GridLayout.LayoutParams();
        leftColParams.rowSpec = GridLayout.spec(1, 2); // span 2 rows
        leftColParams.columnSpec = GridLayout.spec(0, 1);
        leftColParams.width = cellSize;
        leftColParams.height = cellSize * 2 + 2;
        leftColParams.setMargins(2, 2, 2, 2);
        leftCol.setLayoutParams(leftColParams);
        grid.addView(leftCol);
        
        // Обычные ячейки в правой части
        for (int row = 1; row < 3; row++) {
            for (int col = 1; col < 3; col++) {
                TextView cell = new TextView(this);
                cell.setText("(" + row + "," + col + ")");
                cell.setTextColor(0xFF000000);
                cell.setBackgroundColor((row + col) % 2 == 0 ? 0xFFF0F0F0 : 0xFFFFFFFF);
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setTextSize(9);
                
                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams();
                cellParams.rowSpec = GridLayout.spec(row);
                cellParams.columnSpec = GridLayout.spec(col);
                cellParams.width = cellSize;
                cellParams.height = cellSize;
                cellParams.setMargins(2, 2, 2, 2);
                
                cell.setLayoutParams(cellParams);
                grid.addView(cell);
            }
        }
        
        // Нижняя ячейка (span 2 колонки)
        TextView bottomCell = new TextView(this);
        bottomCell.setText("Нижняя\n(columnSpan=2)");
        bottomCell.setTextColor(0xFFFFFFFF);
        bottomCell.setBackgroundColor(0xFFFF6F00);
        bottomCell.setGravity(android.view.Gravity.CENTER);
        bottomCell.setTextSize(10);
        
        GridLayout.LayoutParams bottomParams = new GridLayout.LayoutParams();
        bottomParams.rowSpec = GridLayout.spec(3, 1);
        bottomParams.columnSpec = GridLayout.spec(1, 2); // span 2 columns
        bottomParams.width = cellSize * 2 + 2;
        bottomParams.height = cellSize;
        bottomParams.setMargins(2, 2, 2, 2);
        bottomCell.setLayoutParams(bottomParams);
        grid.addView(bottomCell);
        
        return grid;
    }
    
    /**
     * Пример 3: Сетка с layout_columnWeight для гибкого распределения
     */
    private GridLayout createWeightedGrid(float density) {
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(3);
        grid.setColumnCount(3);
        grid.setBackgroundColor(Color.WHITE);
        
        // Добавляем cells с weight
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                TextView cell = new TextView(this);
                
                if (row == 0) {
                    cell.setText("Header " + (col + 1));
                    cell.setTextColor(0xFFFFFFFF);
                    cell.setBackgroundColor(0xFF1565C0);
                    cell.setTypeface(null, android.graphics.Typeface.BOLD);
                } else {
                    cell.setText("Item " + (row) + "-" + (col + 1));
                    cell.setTextColor(0xFF333333);
                    cell.setBackgroundColor(row % 2 == 0 ? 0xFFF0F0F0 : 0xFFFFFFFF);
                }
                
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setPadding((int)(8 * density), (int)(8 * density), 
                               (int)(8 * density), (int)(8 * density));
                cell.setTextSize(11);
                
                GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams();
                cellParams.rowSpec = GridLayout.spec(row);
                // Используем weight для равномерного распределения ширины
                cellParams.columnSpec = GridLayout.spec(col, 1f); // 1f = weight
                cellParams.width = 0; // width=0 когда используется weight
                cellParams.height = (int)(50 * density);
                cellParams.setMargins(1, 1, 1, 1);
                
                cell.setLayoutParams(cellParams);
                grid.addView(cell);
            }
        }
        
        return grid;
    }
    
    // Вспомогательный метод для получения цвета ячейки
    private int getColorForCell(int row, int col) {
        int[][] colors = {
            {0xFF1976D2, 0xFF1976D2, 0xFF1976D2},
            {0xFF388E3C, 0xFF4CAF50, 0xFF66BB6A},
            {0xFFD32F2F, 0xFFE53935, 0xFFEF5350}
        };
        return colors[row][col];
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
