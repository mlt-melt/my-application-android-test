package com.example.thirdpractice;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScrollViewActivity extends AppCompatActivity {
    private EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scroll_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("ScrollView Activity");

        textInput = findViewById(R.id.textInput);
        findViewById(R.id.submitButton).setOnClickListener(v -> {
            String text = textInput.getText().toString().trim();
            if (!text.isEmpty()) {
                Toast.makeText(this, "Введено: " + text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
