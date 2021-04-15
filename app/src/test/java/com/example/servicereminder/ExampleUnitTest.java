package com.example.servicereminder;

import com.example.servicereminder.Utilities.Vehicle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void databasetest(){
        Vehicle v1 = new Vehicle("18127",1,"car",100,180,5,3,7,"idk","29/5/2021");

    }
}