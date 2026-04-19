package com.example.thirdpractice;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.adapters.ProductAdapter;
import com.example.thirdpractice.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {
    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> products;
    private int categoryId;
    private String categoryName;
    private Button addButton, scrollViewButton, spinnerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categoryId = getIntent().getIntExtra("categoryId", -1);
        categoryName = getIntent().getStringExtra("categoryName");

        setTitle(categoryName);

        initViews();
        initRecyclerView();
    }

    private void initViews() {
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        addButton = findViewById(R.id.addButton);
        scrollViewButton = findViewById(R.id.scrollViewButton);
        spinnerButton = findViewById(R.id.spinnerButton);

        addButton.setOnClickListener(v -> showAddProductDialog());
        scrollViewButton.setOnClickListener(v -> startActivity(new android.content.Intent(this, ScrollViewActivity.class)));
        spinnerButton.setOnClickListener(v -> startActivity(new android.content.Intent(this, SpinnerActivity.class)));
    }

    private void initRecyclerView() {
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = getProductsByCategory(categoryId);

        productAdapter = new ProductAdapter(products);
        productAdapter.setOnProductLongClickListener((product, position) -> showDeleteDialog(product, position));
        productsRecyclerView.setAdapter(productAdapter);
    }

    private List<Product> getProductsByCategory(int categoryId) {
        List<Product> productList = new ArrayList<>();

        if (categoryId == 1) { // Яблоки
            productList.add(new Product(1, "Гала", "Сладкие и сочные яблоки", R.drawable.ic_apple_gala));
            productList.add(new Product(2, "Фуджи", "Плотные и сладкие", R.drawable.ic_apple_fuji));
            productList.add(new Product(3, "Гренни Смит", "Кислые зеленые яблоки", R.drawable.ic_apple_granny));
            productList.add(new Product(4, "Айдаред", "Красные и сладкие", R.drawable.ic_apple_red));
        } else if (categoryId == 2) { // Апельсины
            productList.add(new Product(1, "Валенсия", "Сладкие апельсины", R.drawable.ic_orange_valencia));
            productList.add(new Product(2, "Пупок", "С характерным пупком", R.drawable.ic_orange_navel));
            productList.add(new Product(3, "Кровяной апельсин", "Красное мясо", R.drawable.ic_orange_blood));
        } else if (categoryId == 3) { // Бананы
            productList.add(new Product(1, "Кавендиш", "Классические бананы", R.drawable.ic_banana_cavendish));
            productList.add(new Product(2, "Платано", "Крупные бананы", R.drawable.ic_banana_platano));
        } else if (categoryId == 4) { // Ягоды
            productList.add(new Product(1, "Клубника", "Сладкие ягоды", R.drawable.ic_berry_strawberry));
            productList.add(new Product(2, "Черника", "Полезные ягоды", R.drawable.ic_berry_blueberry));
            productList.add(new Product(3, "Малина", "Нежные ягоды", R.drawable.ic_berry_raspberry));
        }

        return productList;
    }

    private void showAddProductDialog() {
        if (categoryId != 1) {
            Toast.makeText(this, "Добавление доступно только для категории 'Яблоки'", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить яблоко");

        EditText nameInput = new EditText(this);
        nameInput.setHint("Название");
        EditText descriptionInput = new EditText(this);
        descriptionInput.setHint("Описание");

        builder.setView(nameInput);
        builder.setMessage("Введите данные нового яблока");
        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();

            if (!name.isEmpty()) {
                Product newProduct = new Product(
                    products.size() + 1,
                    name,
                    description.isEmpty() ? "Новый сорт яблок" : description,
                    R.drawable.ic_apple_red
                );
                products.add(newProduct);
                productAdapter.updateList(products);
                Toast.makeText(this, "Яблоко добавлено!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void showDeleteDialog(Product product, int position) {
        if (categoryId != 1) {
            Toast.makeText(this, "Удаление доступно только для категории 'Яблоки'", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить?");
        builder.setMessage("Вы уверены, что хотите удалить '" + product.getName() + "'?");
        builder.setPositiveButton("Удалить", (dialog, which) -> {
            products.remove(position);
            productAdapter.updateList(products);
            Toast.makeText(CategoryDetailActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
}
