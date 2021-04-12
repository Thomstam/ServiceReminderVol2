package com.example.servicereminder.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servicereminder.R;

import java.util.ArrayList;

public class VehicleRecyclerView extends RecyclerView.Adapter<VehicleRecyclerView.ViewHolder> {

    ArrayList<Vehicle> vehicles = new ArrayList<>();
    private onItemClickListener listener;

    private Context context;

    public VehicleRecyclerView(){
    }


    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.brandImg.setImageResource(vehicles.get(position).getBrandIcon());
        holder.platesNum.setText(vehicles.get(position).getPlatesOfVehicle());
        holder.date.setText(vehicles.get(position).getDateOfTheService());
        if (vehicles.get(position).getTypeOfVehicle().equals("Car")){
            holder.vehicleImg.setImageResource(R.drawable.ic_car_vehicle);
        }
        else if (vehicles.get(position).getTypeOfVehicle().equals("Truck")){
            holder.vehicleImg.setImageResource(R.drawable.ic_truck_vehicle);
        }
        else {
            holder.vehicleImg.setImageResource(R.drawable.ic_bike_vehicle);
        }
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImg;
        ImageView vehicleImg;
        TextView platesNum;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.vehicle_brand);
            vehicleImg = itemView.findViewById(R.id.vehicle_img);
            platesNum = itemView.findViewById(R.id.plates_num);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(v -> listener.onItemClick(vehicles.get(getAdapterPosition())));
        }
    }
    public interface onItemClickListener {
        void onItemClick(Vehicle vehicle);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
