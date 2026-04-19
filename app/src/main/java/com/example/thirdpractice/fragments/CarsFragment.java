package com.example.thirdpractice.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class CarsFragment extends Fragment {
    private RecyclerView carsRecyclerView;
    private CarAdapter carAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        carsRecyclerView = view.findViewById(R.id.carsRecyclerView);
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Car> cars = new ArrayList<>();
        cars.add(new Car(1, "M4", "BMW", 2023, "Coupe", R.drawable.ic_car_sport, "Спортивный автомобиль"));
        cars.add(new Car(2, "3 Series", "BMW", 2022, "Sedan", R.drawable.ic_car_sedan, "Седан премиум класса"));
        cars.add(new Car(3, "X5", "BMW", 2023, "SUV", R.drawable.ic_car_suv, "Люкс-класс внедорожник"));
        cars.add(new Car(4, "i8", "BMW", 2021, "Hybrid", R.drawable.ic_car_sport, "Гибридный спорткар"));

        carAdapter = new CarAdapter(cars);
        carsRecyclerView.setAdapter(carAdapter);
    }
}
