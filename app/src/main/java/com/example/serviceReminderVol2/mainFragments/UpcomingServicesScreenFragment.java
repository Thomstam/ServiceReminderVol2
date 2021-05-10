package com.example.serviceReminderVol2.mainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceReminderVol2.R;
import com.example.serviceReminderVol2.database.VehicleViewModel;
import com.example.serviceReminderVol2.formsPackage.EditForm;
import com.example.serviceReminderVol2.utilities.BottomSheetFragment;
import com.example.serviceReminderVol2.utilities.Vehicle;
import com.example.serviceReminderVol2.utilities.VehicleRecyclerView;

import java.util.ArrayList;

public class UpcomingServicesScreenFragment extends Fragment {

    public UpcomingServicesScreenFragment() {
        super(R.layout.main_activity_upcoming_services_fragment);
    }

    private VehicleRecyclerView recyclerCustom;
    private VehicleViewModel vehicleViewModelUpComingServices;
    private final static int REQUEST_EDIT_FORM = 102;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.main_activity_upcoming_services_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerCustom();

        setViewModel();

        setFavoriteOnClick();

        setRecyclerOnClick();

        setEditClickListener();
    }

    private void setRecyclerCustom() {
        RecyclerView recyclerView = requireView().findViewById(R.id.RecyclerUpComingServices);
        recyclerCustom = new VehicleRecyclerView();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerCustom);
    }

    private void setViewModel() {
        vehicleViewModelUpComingServices = new ViewModelProvider(this).get(VehicleViewModel.class);
        vehicleViewModelUpComingServices.getUpComingServices().observe(getViewLifecycleOwner(), vehicles -> recyclerCustom.setVehicles(vehicles));
    }

    private void setRecyclerOnClick() {
        recyclerCustom.setOnItemClickListener(vehicle -> {
            FragmentTransaction transaction = ((FragmentActivity) requireContext())
                    .getSupportFragmentManager()
                    .beginTransaction();

            BottomSheetFragment.newInstance(vehicle).show(transaction, "dialog_playback");
        });
    }

    private void setFavoriteOnClick() {
        recyclerCustom.setOnFavoriteClickListener(vehicle -> {
            vehicle.setFavorite(!vehicle.isFavorite());
            vehicleViewModelUpComingServices.update(vehicle);
            recyclerCustom.notifyData();
        });
    }

    private void setEditClickListener() {
        recyclerCustom.setForEditListener(vehicle -> {
            Intent formEditActivity = new Intent(getActivity(), EditForm.class);
            ArrayList<Vehicle> tempList = (ArrayList<Vehicle>) MainActivity.vehicleList();
            if (tempList.size() > 0) {
                formEditActivity.putParcelableArrayListExtra("vehicles", tempList);
                formEditActivity.putExtra("vehicleBoolean", true);
            }
            formEditActivity.putExtra("vehicleForEdit", vehicle);
            requireActivity().startActivityForResult(formEditActivity, REQUEST_EDIT_FORM);
        });
    }
}
