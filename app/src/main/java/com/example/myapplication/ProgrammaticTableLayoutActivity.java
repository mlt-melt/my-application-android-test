package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ПРАКТИЧЕСКАЯ РАБОТА Ч.2: Программное создание TableLayout
 * Демонстрирует создание таблиц в Java коде с использованием
 * TableLayout и TableRow элементов
 */
public class ProgrammaticTableLayoutActivity extends AppCompatActivity {

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
        titleView.setText("TableLayout (программно)");
        titleView.setTextSize(18);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        titleView.setTextColor(0xFF1B5E20);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        titleParams.bottomMargin = (int)(16 * density);
        titleView.setLayoutParams(titleParams);
        mainLayout.addView(titleView);
        
        // ===== ПРИМЕР 1: Базовая таблица =====
        mainLayout.addView(createExampleLabel("Пример 1: Базовая таблица (3x3)", density));
        TableLayout table1 = createBasicTable(density);
        LinearLayout.LayoutParams table1Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        table1Params.bottomMargin = (int)(12 * density);
        table1.setLayoutParams(table1Params);
        mainLayout.addView(table1);
        
        // ===== ПРИМЕР 2: Таблица с объединением ячеек =====
        mainLayout.addView(createExampleLabel("Пример 2: Таблица со слиянием ячеек", density));
        TableLayout table2 = createMergedCellsTable(density);
        LinearLayout.LayoutParams table2Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        table2Params.bottomMargin = (int)(12 * density);
        table2.setLayoutParams(table2Params);
        mainLayout.addView(table2);
        
        // ===== ПРИМЕР 3: Стилизованная таблица данных =====
        mainLayout.addView(createExampleLabel("Пример 3: Стилизованная таблица", density));
        TableLayout table3 = createStyledDataTable(density);
        LinearLayout.LayoutParams table3Params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        table3.setLayoutParams(table3Params);
        mainLayout.addView(table3);
        
        setContentView(mainLayout);
        
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + paddingPx, systemBars.top + paddingPx,
                         systemBars.right + paddingPx, systemBars.bottom + paddingPx);
            return insets;
        });
    }
    
    /**
     * Пример 1: Простая таблица 3x3 с единообразными стилями
     */
    private TableLayout createBasicTable(float density) {
        TableLayout table = new TableLayout(this);
        table.setBackgroundColor(Color.WHITE);
        table.setStretchAllColumns(true);
        
        for (int row = 0; row < 3; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor((row == 0) ? 0xFF4CAF50 : Color.WHITE);
            
            for (int col = 0; col < 3; col++) {
                TextView cell = new TextView(this);
                if (row == 0) {
                    cell.setText("Заголовок " + (col + 1));
                    cell.setTextColor(0xFFFFFFFF);
                } else {
                    cell.setText("R" + row + "C" + (col + 1));
                    cell.setTextColor(0xFF000000);
                }
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setPadding((int)(8 * density), (int)(12 * density), 
                               (int)(8 * density), (int)(12 * density));
                cell.setTextSize(12);
                cell.setBackgroundColor((row % 2 == 1) ? 0xFFE8F5E9 : Color.WHITE);
                
                tableRow.addView(cell);
            }
            
            table.addView(tableRow);
        }
        
        return table;
    }
    
    /**
     * Пример 2: Таблица с ячейками разной ширины
     */
    private TableLayout createMergedCellsTable(float density) {
        TableLayout table = new TableLayout(this);
        table.setBackgroundColor(Color.WHITE);
        table.setStretchAllColumns(true); // Равномерно растягиваем 4 столбца
        
        // Row 1: Header
        TableRow headerRow = new TableRow(this);
        String[] headers = {"ID", "Название", "Цена", "Вес"};
        for (String header : headers) {
            TextView cell = new TextView(this);
            cell.setText(header);
            cell.setTextColor(0xFFFFFFFF);
            cell.setBackgroundColor(0xFF1565C0);
            cell.setGravity(android.view.Gravity.CENTER);
            cell.setPadding((int)(8 * density), (int)(10 * density), 
                           (int)(8 * density), (int)(10 * density));
            cell.setTextSize(11);
            headerRow.addView(cell);
        }
        table.addView(headerRow);
        
        // Data rows
        String[][] data = {
            {"1", "Товар A", "100 ₽", "500g"},
            {"2", "Товар B", "200 ₽", "1kg"},
            {"3", "Товар C", "150 ₽", "750g"}
        };
        
        for (int i = 0; i < data.length; i++) {
            TableRow dataRow = new TableRow(this);
            for (int j = 0; j < data[i].length; j++) {
                TextView cell = new TextView(this);
                cell.setText(data[i][j]);
                cell.setTextColor(0xFF333333);
                cell.setBackgroundColor((i % 2 == 0) ? 0xFFF5F5F5 : Color.WHITE);
                cell.setGravity((j == 0) ? android.view.Gravity.CENTER : android.view.Gravity.START);
                cell.setPadding((int)(8 * density), (int)(8 * density), 
                               (int)(8 * density), (int)(8 * density));
                cell.setTextSize(11);
                dataRow.addView(cell);
            }
            table.addView(dataRow);
        }
        
        return table;
    }
    
    /**
     * Пример 3: Стилизованная таблица с переменным цветом и разными шрифтами
     */
    private TableLayout createStyledDataTable(float density) {
        TableLayout table = new TableLayout(this);
        table.setBackgroundColor(Color.WHITE);
        table.setStretchAllColumns(true);
        
        // Header
        TableRow headerRow = new TableRow(this);
        String[] headers = {"Возраст", "Имя", "Статус"};
        int[] headerColors = {0xFF2196F3, 0xFF2196F3, 0xFF2196F3};
        for (int i = 0; i < headers.length; i++) {
            TextView cell = new TextView(this);
            cell.setText(headers[i]);
            cell.setTextColor(0xFFFFFFFF);
            cell.setBackgroundColor(headerColors[i]);
            cell.setGravity(android.view.Gravity.CENTER);
            cell.setTypeface(null, android.graphics.Typeface.BOLD);
            cell.setPadding((int)(10 * density), (int)(12 * density), 
                           (int)(10 * density), (int)(12 * density));
            headerRow.addView(cell);
        }
        table.addView(headerRow);
        
        // Data
        String[][] tableData = {
            {"25", "Алексей", "Active"},
            {"32", "Борис", "Inactive"},
            {"19", "Виктор", "Active"}
        };
        
        for (int row = 0; row < tableData.length; row++) {
            TableRow dataRow = new TableRow(this);
            for (int col = 0; col < tableData[row].length; col++) {
                TextView cell = new TextView(this);
                cell.setText(tableData[row][col]);
                cell.setTextColor(0xFF212121);
                
                // Альтернирующий фон
                if (row % 2 == 0) {
                    cell.setBackgroundColor(0xFFF0F4FF);
                } else {
                    cell.setBackgroundColor(0xFFFFFFFF);
                }
                
                cell.setGravity(android.view.Gravity.CENTER);
                cell.setPadding((int)(10 * density), (int)(10 * density), 
                               (int)(10 * density), (int)(10 * density));
                
                // Цветной текст для статуса
                if (col == 2) {
                    if ("Active".equals(tableData[row][col])) {
                        cell.setTextColor(0xFF4CAF50);
                        cell.setTypeface(null, android.graphics.Typeface.BOLD);
                    } else {
                        cell.setTextColor(0xFFD32F2F);
                    }
                }
                
                dataRow.addView(cell);
            }
            table.addView(dataRow);
        }
        
        return table;
    }
    
    private TextView createExampleLabel(String text, float density) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(12);
        label.setTextColor(0xFF2E7D32);
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
