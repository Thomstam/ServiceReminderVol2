package com.example.servicereminder.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servicereminder.FormsPackage.FormSetup;
import com.example.servicereminder.R;
import com.example.servicereminder.Utilities.Vehicle;
import com.example.servicereminder.Utilities.VehicleRecyclerView;
import com.example.servicereminder.database.VehicleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class HomeScreenFragments extends Fragment {



    public HomeScreenFragments(){
        super (R.layout.main_activity_home_screen_fragment);
    }

    private VehicleRecyclerView recyclerCustom;
    private final static int REQUEST_EDIT_FORM = 102;
    private VehicleViewModel vehicleViewModel;
    private FloatingActionButton favoriteButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.main_activity_home_screen_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerCustom();

        setViewModel();

        setRecyclerOnClick();

        setFavoriteOnClick();
    }

    private void setRecyclerCustom() {
        RecyclerView recyclerView = requireView().findViewById(R.id.RecyclerHomeScreen);
        recyclerCustom = new VehicleRecyclerView();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerCustom);
    }

    private void setViewModel() {
        vehicleViewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        vehicleViewModel.getReminders().observe(getViewLifecycleOwner(), vehicles -> recyclerCustom.setVehicles(vehicles));
    }

    private void setRecyclerOnClick() {
        recyclerCustom.setOnItemClickListener(vehicle -> {
            Intent formEditActivity = new Intent(getActivity(), FormSetup.class);
            formEditActivity.putExtra("isForEdit", true);
            formEditActivity.putExtra("vehicleForEdit", vehicle);
            startActivityForResult(formEditActivity, REQUEST_EDIT_FORM);
        });
    }

    private void setFavoriteOnClick(){
        recyclerCustom.setOnFavoriteClickListener(vehicle -> {
            vehicle.setFavorite(!vehicle.isFavorite());
            vehicleViewModel.update(vehicle);
            recyclerCustom.notifyData();
        });
    }
}
