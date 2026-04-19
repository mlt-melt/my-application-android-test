package com.example.thirdpractice.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.R;
import com.example.thirdpractice.adapters.CarAdapter;
import com.example.thirdpractice.models.Car;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView favoritesRecyclerView;
    private TextView emptyMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        emptyMessage = view.findViewById(R.id.emptyMessageFav);
        
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Car> favoriteCars = new ArrayList<>();
        favoriteCars.add(new Car(1, "Ferrari F8", "Ferrari", 2023, "Coupe", R.drawable.ic_car_sport, "Мечта - Ferrari F8"));
        favoriteCars.add(new Car(2, "Lamborghini Revuelto", "Lamborghini", 2023, "Supercar", R.drawable.ic_car_sport, "Первая гибридная суперкар"));
        favoriteCars.add(new Car(3, "Tesla Model S", "Tesla", 2023, "Sedan", R.drawable.ic_car_sedan, "Электрический седан"));

        if (favoriteCars.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);
            CarAdapter carAdapter = new CarAdapter(favoriteCars);
            favoritesRecyclerView.setAdapter(carAdapter);
        }
    }
}
