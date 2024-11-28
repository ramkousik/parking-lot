package org.prep.vehicle;

/**
 * Represents a vehicle with attributes such as vehicle type, registration number, and color.
 */
public class Vehicle {
    private String registrationNumber;
    private String color;
    private VehicleType vehicleType;

    public Vehicle(String registrationNumber, String color, VehicleType vehicleType) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.vehicleType = vehicleType;
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}