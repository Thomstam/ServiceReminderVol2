package com.example.servicereminder;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.servicereminder.Utilities.Vehicle;
import com.example.servicereminder.Utilities.database.DBUtilities;
import com.example.servicereminder.Utilities.database.VehicleDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.servicereminder", appContext.getPackageName());
    }


    @Test
    public void useAppContextDBTest() {// When running it inserts a new vehicle to the DB and test if the vehicle was inserted
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Vehicle v1 = new Vehicle("18127",1,"car",100,180,5,3,7,"idk","29/5/2021");
        DBUtilities.saveVehicle(v1,appContext);

        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) DBUtilities.loadAllVehicles(appContext);

        assertEquals(vehicles.get(0).getPlatesOfVehicle(),v1.getPlatesOfVehicle());
    }
    @Test
    public void useAppContextDBTestDelete(){ // When able to find deleted vehicle
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Vehicle v1 = new Vehicle("18127",1,"car",100,180,5,3,7,"idk","29/5/2021");
        assertEquals(DBUtilities.deleteVehicleByPlates("18127",appContext),true);
    }

    @Test
    public void useAppContextDBTestDeleteFails(){// When not able to find deleted vehicle
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Vehicle v1 = new Vehicle("18127",1,"car",100,180,5,3,7,"idk","29/5/2021");
        assertEquals(DBUtilities.deleteVehicleByPlates("18127",appContext),false);
    }

}