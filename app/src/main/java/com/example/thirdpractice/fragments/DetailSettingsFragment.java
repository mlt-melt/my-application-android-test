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

public class DetailSettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        TextView settingsTitle = view.findViewById(R.id.detailSettingsTitle);
        settingsTitle.setText("Дополнительные настройки");
        
        TextView settingsInfo = view.findViewById(R.id.detailSettingsInfo);
        settingsInfo.setText(
            "Настройки уведомлений:\n" +
            "• Получать уведомления о скидках\n" +
            "• Получать предложения по новым моделям\n\n" +
            "Настройки видимости:\n" +
            "• Показывать мой гараж\n" +
            "• Показывать избранное\n\n" +
            "Настройки синхронизации:\n" +
            "• Автосохранение данных\n" +
            "• Синхронизация между устройствами\n\n" +
            "Информация и поддержка:\n" +
            "• Помощь и FAQ\n" +
            "• Контактная информация\n" +
            "• Отзывы и предложения"
        );
    }
}
