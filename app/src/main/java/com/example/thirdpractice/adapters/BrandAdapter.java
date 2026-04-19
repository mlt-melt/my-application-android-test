package com.example.thirdpractice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.R;
import com.example.thirdpractice.models.Brand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private List<Brand> brands;
    private OnBrandClickListener listener;

    public interface OnBrandClickListener {
        void onBrandClick(Brand brand);
    }

    public BrandAdapter(List<Brand> brands) {
        this.brands = brands;
    }

    public void setOnBrandClickListener(OnBrandClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brands.get(position);
        holder.bind(brand);
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandIcon;
        TextView brandName;
        TextView brandCountry;

        BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandIcon = itemView.findViewById(R.id.brandIcon);
            brandName = itemView.findViewById(R.id.brandName);
            brandCountry = itemView.findViewById(R.id.brandCountry);
        }

        void bind(Brand brand) {
            brandIcon.setImageResource(brand.getIconResId());
            brandName.setText(brand.getName());
            brandCountry.setText("Страна: " + brand.getCountry());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBrandClick(brand);
                }
            });
        }
    }
}
