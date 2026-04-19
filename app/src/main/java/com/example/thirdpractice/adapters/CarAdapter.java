package com.example.thirdpractice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpractice.R;
import com.example.thirdpractice.models.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> cars;
    private OnCarClickListener listener;

    public interface OnCarClickListener {
        void onCarClick(Car car);
    }

    public CarAdapter(List<Car> cars) {
        this.cars = cars;
    }

    public void setOnCarClickListener(OnCarClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView carModel;
        TextView carBrand;
        TextView carInfo;

        CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.carImage);
            carModel = itemView.findViewById(R.id.carModel);
            carBrand = itemView.findViewById(R.id.carBrand);
            carInfo = itemView.findViewById(R.id.carInfo);
        }

        void bind(Car car) {
            carImage.setImageResource(car.getImageResId());
            carModel.setText(car.getModel());
            carBrand.setText(car.getBrand());
            carInfo.setText(car.getYear() + " • " + car.getType());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCarClick(car);
                }
            });
        }
    }
}
