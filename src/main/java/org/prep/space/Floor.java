package org.prep.space;


import org.prep.vehicle.VehicleType;

import java.util.*;

/**
 * Represents a floor in the parking lot with vehicle spaces for different vehicle types.
 */
public class Floor {
    private int floorNumber;
    private Map<VehicleType, Integer> capacityPerVehicleType;
    private Map<VehicleType, List<VehicleSpace>> vehicleSpacesPerType;

    public Floor(int floorNumber, Map<VehicleType, Integer> capacityPerVehicleType) {
        this.floorNumber = floorNumber;
        this.capacityPerVehicleType = capacityPerVehicleType;
        vehicleSpacesPerType = new HashMap<>();

        // Initialize vehicle spaces for each vehicle type
        for (VehicleType type : capacityPerVehicleType.keySet()) {
            int capacity = capacityPerVehicleType.get(type);
            List<VehicleSpace> spaces = new ArrayList<>();
            for (int i = 0; i < capacity; i++) {
                VehicleSpace space = new VehicleSpace(i + 1, type);
                spaces.add(space);
            }
            vehicleSpacesPerType.put(type, spaces);
        }
    }

    public VehicleSpace findAvailableSpace(VehicleType vehicleType) {
        List<VehicleSpace> spaces = vehicleSpacesPerType.get(vehicleType);
        if (spaces != null) {
            for (VehicleSpace space : spaces) {
                if (space.isAvailable()) {
                    return space;
                }
            }
        }
        return null;
    }

    public boolean hasAvailableSpace(VehicleType vehicleType) {
        return findAvailableSpace(vehicleType) != null;
    }

    public void displayStatus() {
        for (VehicleType type : vehicleSpacesPerType.keySet()) {
            List<VehicleSpace> spaces = vehicleSpacesPerType.get(type);
            long available = spaces.stream().filter(VehicleSpace::isAvailable).count();
            long occupied = spaces.size() - available;
            System.out.println("  Vehicle Type: " + type);
            System.out.println("    Occupied Spaces: " + occupied);
            System.out.println("    Available Spaces: " + available);
        }
    }


    public int getFloorNumber() {
        return floorNumber;
    }
}