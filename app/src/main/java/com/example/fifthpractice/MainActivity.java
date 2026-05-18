package com.example.fifthpractice;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_FILENAME = "key_filename";
    private static final String KEY_CONTENT = "key_content";
    private static final int REQUEST_STORAGE = 101;

    private EditText etFilename;
    private EditText etContent;
    private TextView tvFileContents;

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

        etFilename = findViewById(R.id.etFilename);
        etContent = findViewById(R.id.etContent);
        tvFileContents = findViewById(R.id.tvFileContents);

        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnAppend = findViewById(R.id.btnAppend);
        Button btnRead = findViewById(R.id.btnRead);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnExternal = findViewById(R.id.btnExternal);

        btnCreate.setOnClickListener(v -> createFile());
        btnAppend.setOnClickListener(v -> appendToFile());
        btnRead.setOnClickListener(v -> readFile());
        btnDelete.setOnClickListener(v -> confirmDelete());
        btnExternal.setOnClickListener(v -> openExternalReader());

        if (savedInstanceState != null) {
            etFilename.setText(savedInstanceState.getString(KEY_FILENAME, ""));
            etContent.setText(savedInstanceState.getString(KEY_CONTENT, ""));
            tvFileContents.setText(savedInstanceState.getString("displayed", ""));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FILENAME, etFilename.getText().toString());
        outState.putString(KEY_CONTENT, etContent.getText().toString());
        outState.putString("displayed", tvFileContents.getText().toString());
    }

    private void createFile() {
        String filename = etFilename.getText().toString().trim();
        String content = etContent.getText().toString();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Файл создан", Toast.LENGTH_SHORT).show();
            tvFileContents.setText(content);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при создании файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void appendToFile() {
        String filename = etFilename.getText().toString().trim();
        String content = etContent.getText().toString();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Новый текст добавлен в конец файла", Toast.LENGTH_SHORT).show();
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при записи в файл", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        String filename = etFilename.getText().toString().trim();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = openFileInput(filename)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            }
            String contents = stringBuilder.toString();
            tvFileContents.setText(contents);
            Toast.makeText(this, "Файл прочитан", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            tvFileContents.setText("");
            Toast.makeText(this, "Не удалось прочитать файл", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDelete() {
        String filename = etFilename.getText().toString().trim();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Удалить файл")
                .setMessage("Вы уверены, что хотите удалить файл '" + filename + "' ?")
                .setPositiveButton("Да", (dialog, which) -> deleteFileInternal(filename))
                .setNegativeButton("Нет", null)
                .show();
    }

    private void deleteFileInternal(String filename) {
        File dir = getFilesDir();
        File file = new File(dir, filename);
        boolean deleted = file.delete();
        if (deleted) {
            tvFileContents.setText("");
            Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Не удалось удалить файл", Toast.LENGTH_SHORT).show();
        }
    }

    private void openExternalReader() {
        // Проверяем разрешения при необходимости
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                return;
            }
        }
        startActivity(new Intent(this, ExternalReaderActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            boolean granted = true;
            for (int r : grantResults) {
                if (r != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                startActivity(new Intent(this, ExternalReaderActivity.class));
            } else {
                Toast.makeText(this, "Нужны разрешения для работы с внешней памятью", Toast.LENGTH_SHORT).show();
            }
        }
    }
}