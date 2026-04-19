package com.example.thirdpractice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.R;
import com.example.thirdpractice.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private OnProductClickListener listener;
    private OnProductLongClickListener longClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public interface OnProductLongClickListener {
        void onProductLongClick(Product product, int position);
    }

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    public void setOnProductLongClickListener(OnProductLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product, position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateList(List<Product> newList) {
        this.products = newList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDescription;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
        }

        void bind(Product product, int position) {
            productImage.setImageResource(product.getImageResId());
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onProductLongClick(product, position);
                    return true;
                }
                return false;
            });
        }
    }
}
