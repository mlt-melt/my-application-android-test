package com.example.fifthpractice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DataStorageActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "myPreferences";
    private static final String KEY_USERNAME = "username";

    private EditText etUsername;
    private TextView tvUsernameDisplay;

    private EditText etId, etTitle, etDesc, etQuantity, etCategory, etNote;
    private TextView tvDbOutput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        etUsername = findViewById(R.id.etUsername);
        tvUsernameDisplay = findViewById(R.id.tvUsernameDisplay);
        Button btnSaveUsername = findViewById(R.id.btnSaveUsername);
        Button btnLoadUsername = findViewById(R.id.btnLoadUsername);
        Button btnDeleteUsername = findViewById(R.id.btnDeleteUsername);

        btnSaveUsername.setOnClickListener(v -> saveUsername());
        btnLoadUsername.setOnClickListener(v -> loadUsername());
        btnDeleteUsername.setOnClickListener(v -> deleteUsername());

        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        etQuantity = findViewById(R.id.etQuantity);
        etCategory = findViewById(R.id.etCategory);
        etNote = findViewById(R.id.etNote);
        tvDbOutput = findViewById(R.id.tvDbOutput);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnFind = findViewById(R.id.btnFind);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnListAll = findViewById(R.id.btnListAll);

        dbHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(v -> addItem());
        btnFind.setOnClickListener(v -> findItem());
        btnUpdate.setOnClickListener(v -> updateItem());
        btnDelete.setOnClickListener(v -> deleteItem());
        btnListAll.setOnClickListener(v -> listAll());
    }

    // SharedPreferences methods
    private void saveUsername() {
        String u = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(u)) {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_USERNAME, u);
        editor.apply();
        Toast.makeText(this, "Имя сохранено", Toast.LENGTH_SHORT).show();
    }

    private void loadUsername() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String u = sp.getString(KEY_USERNAME, "(не задано)");
        tvUsernameDisplay.setText(u);
    }

    private void deleteUsername() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY_USERNAME);
        editor.apply();
        tvUsernameDisplay.setText("");
        Toast.makeText(this, "Имя удалено", Toast.LENGTH_SHORT).show();
    }

    // Database methods
    private void addItem() {
        String title = etTitle.getText().toString().trim();
        if (title.isEmpty()) { Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show(); return; }
        String desc = etDesc.getText().toString();
        int qty = 0;
        try { qty = Integer.parseInt(etQuantity.getText().toString().trim()); } catch (NumberFormatException ignored) {}
        String cat = etCategory.getText().toString();
        String note = etNote.getText().toString();
        Item item = new Item(title, desc, qty, cat, note);
        long id = dbHelper.addItem(item);
        if (id != -1) {
            etId.setText(String.valueOf(id));
            Toast.makeText(this, "Добавлено id="+id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
        }
    }

    private void findItem() {
        int id = parseId(); if (id==-1) return;
        Item item = dbHelper.getItem(id);
        if (item != null) {
            etTitle.setText(item.getTitle());
            etDesc.setText(item.getDescription());
            etQuantity.setText(String.valueOf(item.getQuantity()));
            etCategory.setText(item.getCategory());
            etNote.setText(item.getNote());
            tvDbOutput.setText(formatItem(item));
        } else {
            tvDbOutput.setText("Не найдено");
            Toast.makeText(this, "Не найдено", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateItem() {
        int id = parseId(); if (id==-1) return;
        String title = etTitle.getText().toString().trim(); if (title.isEmpty()) { Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show(); return; }
        String desc = etDesc.getText().toString();
        int qty = 0; try { qty = Integer.parseInt(etQuantity.getText().toString().trim()); } catch (NumberFormatException ignored){}
        String cat = etCategory.getText().toString();
        String note = etNote.getText().toString();
        Item item = new Item(id, title, desc, qty, cat, note);
        boolean ok = dbHelper.updateItem(item);
        Toast.makeText(this, ok?"Обновлено":"Не обновлено", Toast.LENGTH_SHORT).show();
    }

    private void deleteItem() {
        int id = parseId(); if (id==-1) return;
        boolean ok = dbHelper.deleteItem(id);
        if (ok) {
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
            clearFields();
        } else Toast.makeText(this, "Не удалось удалить", Toast.LENGTH_SHORT).show();
    }

    private void listAll() {
        List<Item> all = dbHelper.getAllItems();
        StringBuilder sb = new StringBuilder();
        for (Item it : all) {
            sb.append(formatItem(it)).append("\n---\n");
        }
        tvDbOutput.setText(sb.toString());
    }

    private int parseId() {
        String s = etId.getText().toString().trim();
        if (s.isEmpty()) { Toast.makeText(this, "Введите ID", Toast.LENGTH_SHORT).show(); return -1; }
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { Toast.makeText(this, "Неверный ID", Toast.LENGTH_SHORT).show(); return -1; }
    }

    private String formatItem(Item it) {
        return "id="+it.getId()+"\ntitle="+it.getTitle()+"\ndesc="+it.getDescription()+"\nqty="+it.getQuantity()+"\ncat="+it.getCategory()+"\nnote="+it.getNote();
    }

    private void clearFields() {
        etId.setText(""); etTitle.setText(""); etDesc.setText(""); etQuantity.setText(""); etCategory.setText(""); etNote.setText(""); tvDbOutput.setText("");
    }
}
