package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private static final String KEY_TEXT = "KEY_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextMessage = findViewById(R.id.editText_message);
        Button buttonSend = findViewById(R.id.button_send);

        // Кнопка для открытия UINotebookActivity
        Button buttonUINotebook = new Button(this);
        buttonUINotebook.setText("1. Размеры экрана (Java код)");
        buttonUINotebook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UINotebookActivity.class);
            startActivity(intent);
        });

        // Кнопка для открытия Padding/Margin примеров
        Button buttonPaddingMargin = new Button(this);
        buttonPaddingMargin.setText("2. Padding и Margin (XML)");
        buttonPaddingMargin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PaddingMarginActivity.class);
            startActivity(intent);
        });

        // Кнопка для открытия ConstraintLayout примеров
        Button buttonConstraint = new Button(this);
        buttonConstraint.setText("3. ConstraintLayout (XML)");
        buttonConstraint.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConstraintLayoutActivity.class);
            startActivity(intent);
        });

        // Кнопка для открытия программного создания ConstraintLayout
        Button buttonProgrammaticConstraint = new Button(this);
        buttonProgrammaticConstraint.setText("4. ConstraintLayout (Java код)");
        buttonProgrammaticConstraint.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgrammaticConstraintActivity.class);
            startActivity(intent);
        });

        // Добавляем кнопки в layout
        LinearLayout mainLayout = findViewById(R.id.main);
        if (mainLayout != null) {
            mainLayout.addView(buttonUINotebook, 0);
            mainLayout.addView(buttonPaddingMargin, 1);
            mainLayout.addView(buttonConstraint, 2);
            mainLayout.addView(buttonProgrammaticConstraint, 3);
        }

            // ===== ЧАСТЬ 2: Layout контейнеры =====
            Button buttonLinearWeight = new Button(this);
            buttonLinearWeight.setText("5. LinearLayout (Weight) - XML");
            buttonLinearWeight.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LinearLayoutWeightActivity.class)));

            Button buttonLinearProgrammatic = new Button(this);
            buttonLinearProgrammatic.setText("5b. LinearLayout - Java код");
            buttonLinearProgrammatic.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgrammaticLinearLayoutActivity.class)));

            Button buttonRelative = new Button(this);
            buttonRelative.setText("6. RelativeLayout - XML");
            buttonRelative.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RelativeLayoutActivity.class)));

            Button buttonRelativeProgrammatic = new Button(this);
            buttonRelativeProgrammatic.setText("6b. RelativeLayout - Java код");
            buttonRelativeProgrammatic.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgrammaticRelativeLayoutActivity.class)));

            Button buttonTable = new Button(this);
            buttonTable.setText("7. TableLayout - XML");
            buttonTable.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TableLayoutActivity.class)));

            Button buttonTableProgrammatic = new Button(this);
            buttonTableProgrammatic.setText("7b. TableLayout - Java код");
            buttonTableProgrammatic.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgrammaticTableLayoutActivity.class)));

            Button buttonFrame = new Button(this);
            buttonFrame.setText("8. FrameLayout - XML");
            buttonFrame.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FrameLayoutActivity.class)));

            Button buttonFrameProgrammatic = new Button(this);
            buttonFrameProgrammatic.setText("8b. FrameLayout - Java код");
            buttonFrameProgrammatic.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgrammaticFrameLayoutActivity.class)));

            Button buttonGrid = new Button(this);
            buttonGrid.setText("9. GridLayout - XML");
            buttonGrid.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GridLayoutActivity.class)));

            Button buttonGridProgrammatic = new Button(this);
            buttonGridProgrammatic.setText("9b. GridLayout - Java код");
            buttonGridProgrammatic.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgrammaticGridLayoutActivity.class)));

            Button buttonScroll = new Button(this);
            buttonScroll.setText("10. ScrollView - XML");
            buttonScroll.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScrollViewActivity.class)));

            Button buttonScrollProgrammatic = new Button(this);
            buttonScrollProgrammatic.setText("10b. ScrollView - Java код");
            buttonScrollProgrammatic.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgrammaticScrollViewActivity.class)));

            Button buttonGravity = new Button(this);
            buttonGravity.setText("11. Gravity - Позиционирование");
            buttonGravity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GravityActivity.class)));

            Button buttonNested = new Button(this);
            buttonNested.setText("12. Вложенные Layouts");
            buttonNested.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NestedLayoutActivity.class)));

            Button buttonAdvanced = new Button(this);
            buttonAdvanced.setText("13. Все контейнеры (программно)");
            buttonAdvanced.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AdvancedLayoutsProgrammaticActivity.class)));

            // Добавляем все кнопки в layout
            if (mainLayout != null) {
                int index = 4;
                mainLayout.addView(buttonLinearWeight, index++);
                mainLayout.addView(buttonLinearProgrammatic, index++);
                mainLayout.addView(buttonRelative, index++);
                mainLayout.addView(buttonRelativeProgrammatic, index++);
                mainLayout.addView(buttonTable, index++);
                mainLayout.addView(buttonTableProgrammatic, index++);
                mainLayout.addView(buttonFrame, index++);
                mainLayout.addView(buttonFrameProgrammatic, index++);
                mainLayout.addView(buttonGrid, index++);
                mainLayout.addView(buttonGridProgrammatic, index++);
                mainLayout.addView(buttonScroll, index++);
                mainLayout.addView(buttonScrollProgrammatic, index++);
                mainLayout.addView(buttonGravity, index++);
                mainLayout.addView(buttonNested, index++);
                mainLayout.addView(buttonAdvanced, index);
            }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Код для Android 8+
                } else {
                    // Код для старых версий
                }
                String message = editTextMessage.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("message", message);
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            String text = savedInstanceState.getString(KEY_TEXT);
            editTextMessage.setText(text);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT, editTextMessage.getText().toString());
    }
}