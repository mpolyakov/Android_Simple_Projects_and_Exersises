package com.kt.std.dagger2dependencyinjectiondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CarComponent carComponent = DaggerCarComponent.create();
        CarComponent carComponent = DaggerCarComponent.builder().carChassisModule(new CarChassisModule(1000)).build();
//        car = carComponent.getCar();
        carComponent.inject(this);
//        EngineFlap engineFlap = new EngineFlap();
//        CarBattery carBattery = new CarBattery();
//        CarChassis carChassis = new CarChassis();
//        CarEngine carEngine = new CarEngine(engineFlap);

//        Car car = new Car(carEngine, carChassis, carBattery);
        car.move();


    }
}