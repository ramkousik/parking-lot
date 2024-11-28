package org.prep;


import org.prep.space.VehicleSpace;
import org.prep.vehicle.Vehicle;

import java.util.Date;

/**
 * Represents a parking ticket with a token ID, associated vehicle, parking space, and entry time.
 */
public class ParkingTicket {
    private String tokenId;
    private Vehicle vehicle;
    private VehicleSpace space;
    private Date entryTime;

    public ParkingTicket(String tokenId, Vehicle vehicle, VehicleSpace space, Date entryTime) {
        this.tokenId = tokenId;
        this.vehicle = vehicle;
        this.space = space;
        this.entryTime = entryTime;
    }

    public String getTokenId() {
        return tokenId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleSpace getSpace() {
        return space;
    }

    public Date getEntryTime() {
        return entryTime;
    }
}
