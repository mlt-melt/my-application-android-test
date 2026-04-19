package com.example.thirdpractice.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.R;
import com.example.thirdpractice.DetailActivity;
import com.example.thirdpractice.adapters.BrandAdapter;
import com.example.thirdpractice.models.Brand;

import java.util.ArrayList;
import java.util.List;

public class BrandsFragment extends Fragment {
    private RecyclerView brandsRecyclerView;
    private BrandAdapter brandAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brands, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        brandsRecyclerView = view.findViewById(R.id.brandsRecyclerView);
        brandsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1, "BMW", "Германия", R.drawable.ic_brand_bmw));
        brands.add(new Brand(2, "Mercedes", "Германия", R.drawable.ic_brand_mercedes));
        brands.add(new Brand(3, "Ferrari", "Италия", R.drawable.ic_brand_ferrari));
        brands.add(new Brand(4, "Toyota", "Япония", R.drawable.ic_brand_toyota));
        brands.add(new Brand(5, "Tesla", "США", R.drawable.ic_brand_tesla));
        brands.add(new Brand(6, "Lamborghini", "Италия", R.drawable.ic_brand_lamborghini));

        brandAdapter = new BrandAdapter(brands);
        brandAdapter.setOnBrandClickListener(brand -> {
            Intent intent = new Intent(requireActivity(), DetailActivity.class);
            intent.putExtra("brandName", brand.getName());
            intent.putExtra("brandId", brand.getId());
            startActivity(intent);
        });

        brandsRecyclerView.setAdapter(brandAdapter);
    }
}
