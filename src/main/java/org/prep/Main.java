package org.prep;


import org.prep.cost.CostStrategy;
import org.prep.cost.FlatRateCostStrategy;
import org.prep.vehicle.Vehicle;
import org.prep.vehicle.VehicleType;

import java.util.*;

/**
 * Main class to demonstrate the usage of the parking lot and its methods.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the parking lot
        System.out.print("Enter number of floors: ");
        int numberOfFloors = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Map<VehicleType, Integer> spacesPerTypePerFloor = new HashMap<>();
        for (VehicleType type : VehicleType.values()) {
            System.out.print("Enter number of spaces per floor for " + type + ": ");
            int spaces = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            spacesPerTypePerFloor.put(type, spaces);
        }

        ParkingLot parkingLot = new ParkingLot(numberOfFloors);
        parkingLot.init(numberOfFloors, spacesPerTypePerFloor);

        // Configure cost strategy
        Map<VehicleType, Double> ratePerHour = new HashMap<>();
        for (VehicleType type : VehicleType.values()) {
            System.out.print("Enter rate per hour for " + type + ": ");
            double rate = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character
            ratePerHour.put(type, rate);
        }

        System.out.print("Enter currency code (e.g., INR, USD): ");
        String currencyCode = scanner.nextLine();
        Currency currency = Currency.getInstance(currencyCode);

        CostStrategy costStrategy = new FlatRateCostStrategy(ratePerHour, currency);
        parkingLot.configureCostStrategy(costStrategy);

        Map<String, String> vehicleTokenMap = new HashMap<>(); // For searching tokens by registration number

        // Interactive menu
        while (true) {
            System.out.println("\nParking Lot Menu:");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Check Availability");
            System.out.println("4. Display Status");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    // Add Vehicle
                    addVehicle(scanner, parkingLot, vehicleTokenMap);
                    break;
                case 2:
                    // Remove Vehicle
                    removeVehicle(scanner, parkingLot, vehicleTokenMap);
                    break;
                case 3:
                    // Check Availability
                    checkAvailability(scanner, parkingLot);
                    break;
                case 4:
                    // Display Status
                    parkingLot.displayStatus();
                    break;
                case 5:
                    // Exit
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addVehicle(Scanner scanner, ParkingLot parkingLot, Map<String, String> vehicleTokenMap) {
        try {
            System.out.print("Enter Vehicle Type (");
            for (VehicleType type : VehicleType.values()) {
                System.out.print(type + " ");
            }
            System.out.print("): ");
            String typeInput = scanner.nextLine().toUpperCase();
            VehicleType vehicleType = VehicleType.valueOf(typeInput);

            System.out.print("Enter Registration Number: ");
            String regNumber = scanner.nextLine();

            System.out.print("Enter Color: ");
            String color = scanner.nextLine();

            Vehicle vehicle = new Vehicle(regNumber, color, vehicleType);
            Date entryTime = new Date(); // Current time

            String token = parkingLot.addVehicle(vehicle, entryTime);
            vehicleTokenMap.put(regNumber, token);
            System.out.println("Vehicle added. Token ID: " + token);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeVehicle(Scanner scanner, ParkingLot parkingLot, Map<String, String> vehicleTokenMap) {
        System.out.println("Remove Vehicle by:");
        System.out.println("1. Token ID");
        System.out.println("2. Registration Number");
        System.out.print("Enter choice: ");
        int removeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String tokenId = null;

        if (removeChoice == 1) {
            System.out.print("Enter Token ID: ");
            tokenId = scanner.nextLine();
        } else if (removeChoice == 2) {
            System.out.print("Enter Registration Number: ");
            String regNumber = scanner.nextLine();
            tokenId = vehicleTokenMap.get(regNumber);
            if (tokenId == null) {
                System.out.println("Vehicle with registration number " + regNumber + " not found.");
                return;
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        Date exitTime = new Date(); // Current time
        try {
            double cost = parkingLot.removeVehicle(tokenId, exitTime);
            Currency currency = ((FlatRateCostStrategy) parkingLot.getCostStrategy()).getCurrency();
            System.out.println("Vehicle removed. Parking cost: " + cost + " " + currency.getSymbol());
            vehicleTokenMap.values().remove(tokenId); // Remove from the map
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkAvailability(Scanner scanner, ParkingLot parkingLot) {
        try {
            System.out.print("Enter Floor Number: ");
            int floorNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            System.out.print("Enter Vehicle Type (");
            for (VehicleType type : VehicleType.values()) {
                System.out.print(type + " ");
            }
            System.out.print("): ");
            String vehicleTypeInput = scanner.nextLine().toUpperCase();
            VehicleType type = VehicleType.valueOf(vehicleTypeInput);

            boolean available = parkingLot.checkAvailability(floorNumber, type);
            System.out.println("Availability: " + (available ? "Yes" : "No"));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}