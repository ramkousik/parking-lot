package org.prep;


import org.prep.cost.CostStrategy;
import org.prep.space.Floor;
import org.prep.space.VehicleSpace;
import org.prep.vehicle.Vehicle;
import org.prep.vehicle.VehicleType;

import java.util.*;

/**
 * Represents the parking lot and manages vehicle spaces across multiple floors.
 */
public class ParkingLot {
    private int numberOfFloors;
    private Map<Integer, Floor> floors;
    private CostStrategy costStrategy;
    private Map<String, ParkingTicket> activeTickets;

    public ParkingLot(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        floors = new HashMap<>();
        activeTickets = new HashMap<>();
    }

    /**
     * Initializes the parking lot with the given number of floors and vehicle spaces per floor for each vehicle type.
     */
    public void init(int numberOfFloors, Map<VehicleType, Integer> spacesPerTypePerFloor) {
        this.numberOfFloors = numberOfFloors;
        for (int i = 1; i <= numberOfFloors; i++) {
            Floor floor = new Floor(i, spacesPerTypePerFloor);
            floors.put(i, floor);
        }
    }

    /**
     * Adds a vehicle to the parking lot and returns a token ID.
     */
    public String addVehicle(Vehicle vehicle, Date entryTime) {
        // Find a space for the vehicle
        for (Floor floor : floors.values()) {
            VehicleSpace space = floor.findAvailableSpace(vehicle.getVehicleType());
            if (space != null) {
                // Park the vehicle
                space.parkVehicle(vehicle);
                // Generate a parking ticket
                String tokenId = UUID.randomUUID().toString();
                ParkingTicket ticket = new ParkingTicket(tokenId, vehicle, space, entryTime);
                activeTickets.put(tokenId, ticket);
                return tokenId;
            }
        }
        throw new IllegalStateException("No available space for vehicle type: " + vehicle.getVehicleType());
    }

    /**
     * Removes a vehicle from the parking lot using the token ID and calculates the parking cost.
     */
    public double removeVehicle(String tokenId, Date exitTime) {
        ParkingTicket ticket = activeTickets.get(tokenId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid token ID");
        }
        // Calculate duration
        long durationInHours = (exitTime.getTime() - ticket.getEntryTime().getTime()) / (1000 * 60 * 60);
        if (durationInHours == 0) durationInHours = 1; // Minimum 1 hour

        // Calculate cost
        double cost = costStrategy.calculateCost(ticket.getVehicle(), durationInHours);

        // Remove vehicle from space
        ticket.getSpace().removeVehicle();

        // Remove ticket
        activeTickets.remove(tokenId);

        return cost;
    }

    /**
     * Checks the availability of vehicle spaces on the specified floor for the given vehicle type.
     */
    public boolean checkAvailability(int floorNumber, VehicleType vehicleType) {
        Floor floor = floors.get(floorNumber);
        if (floor != null) {
            return floor.hasAvailableSpace(vehicleType);
        }
        return false;
    }

    /**
     * Displays the current status of the parking lot, including occupied and available spaces.
     */
    public void displayStatus() {
        for (Floor floor : floors.values()) {
            System.out.println("Floor " + floor.getFloorNumber() + ":");
            floor.displayStatus();
        }
    }

    /**
     * Configures the cost strategy for parking.
     */
    public void configureCostStrategy(CostStrategy costStrategy) {
        this.costStrategy = costStrategy;
    }

    /**
     * Retrieves the current cost strategy.
     */
    public CostStrategy getCostStrategy() {
        return this.costStrategy;
    }
}
