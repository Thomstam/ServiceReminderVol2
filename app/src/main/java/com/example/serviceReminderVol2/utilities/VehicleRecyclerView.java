package com.example.serviceReminderVol2.utilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceReminderVol2.R;

import java.util.ArrayList;
import java.util.List;

public class VehicleRecyclerView extends RecyclerView.Adapter<VehicleRecyclerView.ViewHolder> {

    List<Vehicle> vehicles = new ArrayList<>();
    private onItemClickListener listener;
    private onFavoriteClickListener favoriteListener;
    private onEditClickListener forEditListener;

    public VehicleRecyclerView() {
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
        notifyDataSetChanged();
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void notifyData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.brandImg.setImageResource(vehicles.get(position).getBrandIcon());
        holder.platesNum.setText(vehicles.get(position).getPlatesOfVehicle());
        holder.date.setText(vehicles.get(position).getDateOfTheService());
        if (vehicles.get(position).getTypeOfVehicle().equals("Car")) {
            holder.vehicleImg.setImageResource(R.drawable.recycler_view_car);
        } else if (vehicles.get(position).getTypeOfVehicle().equals("Truck")) {
            holder.vehicleImg.setImageResource(R.drawable.recycler_view_truck);
        } else {
            holder.vehicleImg.setImageResource(R.drawable.recycler_view_bike);
        }
        if (vehicles.get(position).isFavorite()) {
            holder.isFavorite.setImageResource(R.drawable.recycler_view_favorite_true);
        } else {
            holder.isFavorite.setImageResource(R.drawable.recycler_view_favorite_false);
        }
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView brandImg;
        final ImageView vehicleImg;
        final TextView platesNum;
        final TextView date;
        final ImageButton isFavorite;
        final ImageButton forEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.vehicle_brand);
            vehicleImg = itemView.findViewById(R.id.vehicle_img);
            platesNum = itemView.findViewById(R.id.plates_num);
            date = itemView.findViewById(R.id.date);
            isFavorite = itemView.findViewById(R.id.favorites_button);
            forEdit = itemView.findViewById(R.id.editIcon);
            itemView.setOnClickListener(v -> listener.onItemClick(vehicles.get(getAbsoluteAdapterPosition())));
            isFavorite.setOnClickListener(v -> favoriteListener.onFavoriteClick(vehicles.get(getAbsoluteAdapterPosition())));
            forEdit.setOnClickListener(v -> forEditListener.onEditClick(vehicles.get(getAbsoluteAdapterPosition())));
        }
    }

    public interface onItemClickListener {
        void onItemClick(Vehicle vehicle);
    }

    public interface onFavoriteClickListener {
        void onFavoriteClick(Vehicle vehicle);
    }

    public interface onEditClickListener {
        void onEditClick(Vehicle vehicle);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnFavoriteClickListener(onFavoriteClickListener favoriteListener) {
        this.favoriteListener = favoriteListener;
    }

    public void setForEditListener(onEditClickListener editListener) {
        this.forEditListener = editListener;
    }


}
