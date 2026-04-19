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

public class GarageFragment extends Fragment {
    private RecyclerView garageRecyclerView;
    private TextView emptyMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_garage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        garageRecyclerView = view.findViewById(R.id.garageRecyclerView);
        emptyMessage = view.findViewById(R.id.emptyMessage);
        
        garageRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Car> garageCars = new ArrayList<>();
        garageCars.add(new Car(1, "BMW M4", "BMW", 2023, "Coupe", R.drawable.ic_car_sport, "Мой спортивный BMW M4"));
        garageCars.add(new Car(2, "Mercedes-AMG", "Mercedes", 2022, "SUV", R.drawable.ic_car_suv, "Мой люкс внедорожник"));

        if (garageCars.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            garageRecyclerView.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            garageRecyclerView.setVisibility(View.VISIBLE);
            CarAdapter carAdapter = new CarAdapter(garageCars);
            garageRecyclerView.setAdapter(carAdapter);
        }
    }
}
