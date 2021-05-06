package com.example.serviceReminder.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceReminder.R;
import com.example.serviceReminder.utilities.VehicleRecyclerView;
import com.example.serviceReminder.database.VehicleViewModel;


public class FavoritesScreenFragment extends Fragment {

    public FavoritesScreenFragment(){
        super (R.layout.main_activity_favorites_fragment);
    }

    private VehicleRecyclerView recyclerCustom;
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
        vehicleViewModelFavorites = new ViewModelProvider(this).get(VehicleViewModel.class);
        vehicleViewModelFavorites.getFavoritesScreenVehicles().observe(getViewLifecycleOwner(), vehicles -> recyclerCustom.setVehicles(vehicles));
    }

    private void setRecyclerOnClick() {
        recyclerCustom.setOnItemClickListener(vehicle -> {
            new MainActivity().RecyclersOnClick(vehicle, getContext());
//            Intent formEditActivity = new Intent(getActivity(), EditForm.class);
//            formEditActivity.putExtra("isForEdit", true);
//            formEditActivity.putExtra("vehicleForEdit", vehicle);
//            startActivityForResult(formEditActivity, REQUEST_EDIT_FORM);
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