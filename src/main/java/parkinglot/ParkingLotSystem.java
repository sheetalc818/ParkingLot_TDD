package parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private int currentCapacity;
    private int actualCapacity;

    private Object vehicle;
    public List vehicles;
    public List<ParkingLotObserver> observers;

    public ParkingLotSystem(int capacity) {
        this.observers = new ArrayList<>();
        vehicles = new ArrayList();
        this.currentCapacity = 0;
        this.actualCapacity = capacity;
        setCapacity(actualCapacity);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
        parkingLotInitialize();
    }

    public int parkingLotInitialize() {
        this.vehicles = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).forEach(slots -> vehicles.add(null));
        return vehicles.size();
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public void park(Object vehicle,int... slots) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("Vehicle is already parked", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        }
        if (this.vehicles.size() == this.actualCapacity) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException("Parking Lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        this.vehicles.add(vehicle);
        getParkingLocation(vehicle,slots);
    }

    public void getParkingLocation(Object vehicle,int... slots) {
        if (slots.length == 0) {
            int autoParkingLocation = (int) vehicles.get(0);
            this.vehicles.set(autoParkingLocation, vehicle);
            return;
        }
        this.vehicles.set(slots[0], vehicle);
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

    public int findVehicle(Object vehicle) throws ParkingLotException {
        if (this.vehicles.contains(vehicle))
            return this.vehicles.indexOf(vehicle);
        throw new ParkingLotException("Vehicle not found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public ArrayList getEmptyParkingSlot() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptySlots.add(slot));
        return emptySlots;
    }
}
