package org.prep.cost;


import org.prep.vehicle.Vehicle;
import org.prep.vehicle.VehicleType;

import java.util.Currency;
import java.util.Map;

/**
 * Implements a flat rate cost strategy based on vehicle type.
 */
public class FlatRateCostStrategy implements CostStrategy {
    private Map<VehicleType, Double> ratePerHour;
    private Currency currency;

    public FlatRateCostStrategy(Map<VehicleType, Double> ratePerHour, Currency currency) {
        this.ratePerHour = ratePerHour;
        this.currency = currency;
    }

    @Override
    public double calculateCost(Vehicle vehicle, long durationInHours) {
        Double rate = ratePerHour.get(vehicle.getVehicleType());
        if (rate == null) {
            throw new IllegalArgumentException("Rate not defined for vehicle type: " + vehicle.getVehicleType());
        }
        return rate * durationInHours;
    }

    public Currency getCurrency() {
        return currency;
    }
}
