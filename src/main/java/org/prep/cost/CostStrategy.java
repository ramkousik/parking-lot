package org.prep.cost;


import org.prep.vehicle.Vehicle;

/**
 * Interface for cost strategy, allowing for varying costs based on the vehicle type.
 */
public interface CostStrategy {
    double calculateCost(Vehicle vehicle, long durationInHours);
}