package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingSystem {
    private int currentCapacity;
    private int actualCapacity;

    private Object vehicle;
    public List vehicles;
    public List<ParkingLotObserver> observers;

    public ParkingSystem(int capacity) {
        this.observers = new ArrayList<>();
        vehicles = new ArrayList();
        this.currentCapacity = 0;
        this.actualCapacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("Vehicle is already parked");
        }
        if (this.vehicles.size() == this.actualCapacity) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full");
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
            for (ParkingLotObserver observer : observers) {
                observer.lotIsAvailable();
            }
            return true;
        }
        return false;
    }
}
