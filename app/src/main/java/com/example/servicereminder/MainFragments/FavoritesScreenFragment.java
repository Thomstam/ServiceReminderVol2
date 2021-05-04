package com.example.servicereminder.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servicereminder.FormsPackage.FormSetup;
import com.example.servicereminder.R;
import com.example.servicereminder.Utilities.VehicleRecyclerView;
import com.example.servicereminder.database.VehicleViewModel;

public class FavoritesScreenFragment extends Fragment {

    public FavoritesScreenFragment(){
        super (R.layout.main_activity_favorites_fragment);
    }

    private VehicleRecyclerView recyclerCustom;
    private final static int REQUEST_EDIT_FORM = 102;
    private VehicleViewModel vehicleViewModelFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.main_activity_favorites_fragment, container, false);
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
        RecyclerView recyclerView = requireView().findViewById(R.id.RecyclerFavoritesScreen);
        recyclerCustom = new VehicleRecyclerView();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerCustom);
    }

    private void setViewModel() {
        vehicleViewModelFavorites = ViewModelProviders.of(this).get(VehicleViewModel.class);
        vehicleViewModelFavorites.getFavoritesScreenVehicles().observe(getViewLifecycleOwner(), vehicles -> recyclerCustom.setVehicles(vehicles));
    }

    private void setRecyclerOnClick() {
        recyclerCustom.setOnItemClickListener(vehicle -> {
            Intent formEditActivity = new Intent(getActivity(), FormSetup.class);
            formEditActivity.putExtra("isForEdit", true);
            formEditActivity.putExtra("vehicleForEdit", vehicle);
            startActivityForResult(formEditActivity, REQUEST_EDIT_FORM);
        });
    }

    private void setFavoriteOnClick() {
        recyclerCustom.setOnFavoriteClickListener(vehicle -> {
            vehicle.setFavorite(!vehicle.isFavorite());
            vehicleViewModelFavorites.update(vehicle);
            recyclerCustom.notifyData();
        });
    }
}
