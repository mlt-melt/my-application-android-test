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