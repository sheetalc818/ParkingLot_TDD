package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingSystem {
    private int currentCapacity;
    private int actualCapacity;

    private Object vehicle;
    private ParkingLotOwner parkingLotOwner;
    public List vehicles;

    public ParkingSystem(int capacity) {
        vehicles = new ArrayList();
        this.currentCapacity = 0;
        this.actualCapacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.actualCapacity) {
            parkingLotOwner.capacityIsFull();
            throw new ParkingLotException("Parking Lot is full");
        }

        if (isVehicleParked(vehicle)){
            throw new ParkingLotException("Vehicle is already parked");
        }
        this.vehicles.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if (vehicle == null) return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }

    public void registerOwner(ParkingLotOwner parkingLotOwner) {
        this.parkingLotOwner = parkingLotOwner;
    }
}
