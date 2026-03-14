package org.example;

import factory.Vehicle;
import factory.VehicleFactory;
import singleton.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

//        Logger log1 = Logger.getInstance();
//        Logger log2 = Logger.getInstance();
//
//        log1.log("Hello Singleton");
//
//        if (log1 == log2) {
//            System.out.println("Chi co 1 instance!");
//        }

        //factory
        Vehicle v1 = VehicleFactory.getVehicle("car");
        v1.run();

        Vehicle v2 = VehicleFactory.getVehicle("bike");
        v2.run();
    }
}