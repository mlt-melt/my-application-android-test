package com.example.thirdpractice.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thirdpractice.R;

public class MainSettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        TextView settingsTitle = view.findViewById(R.id.settingsTitle);
        settingsTitle.setText("Основные настройки приложения");
        
        TextView settingsInfo = view.findViewById(R.id.settingsInfo);
        settingsInfo.setText(
            "Версия приложения: 2.0\n\n" +
            "Тематика: Автомобили\n\n" +
            "Функции:\n" +
            "• Просмотр марок автомобилей\n" +
            "• Просмотр моделей\n" +
            "• Добавление в избранное\n" +
            "• Управление гаражом\n\n" +
            "Разработчик: AUTO APP Team"
        );
    }
}
