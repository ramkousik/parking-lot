package org.prep.space;


import org.prep.vehicle.Vehicle;
import org.prep.vehicle.VehicleType;

/**
 * Represents a parking space with attributes such as space number, availability, and vehicle type.
 */
public class VehicleSpace {
    private int spaceNumber;
    private boolean isAvailable;
    private VehicleType vehicleType;
    private Vehicle parkedVehicle;

    public VehicleSpace(int spaceNumber, VehicleType vehicleType) {
        this.spaceNumber = spaceNumber;
        this.vehicleType = vehicleType;
        this.isAvailable = true;
    }

    public void parkVehicle(Vehicle vehicle) {
        if (!isAvailable) {
            throw new IllegalStateException("Space is already occupied");
        }
        this.parkedVehicle = vehicle;
        this.isAvailable = false;
    }

    public void removeVehicle() {
        if (isAvailable) {
            throw new IllegalStateException("Space is already empty");
        }
        this.parkedVehicle = null;
        this.isAvailable = true;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }
}