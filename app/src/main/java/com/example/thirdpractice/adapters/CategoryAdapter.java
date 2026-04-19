package com.example.thirdpractice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.R;
import com.example.thirdpractice.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIcon;
        TextView categoryName;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryName = itemView.findViewById(R.id.categoryName);
        }

        void bind(Category category) {
            categoryIcon.setImageResource(category.getIconResId());
            categoryName.setText(category.getName());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
