package parkinglot;

import javax.lang.model.util.Types;
import java.sql.Struct;
import java.util.List;

public class ParkingAttendant implements ParkingLotObserver {
    boolean isFullCapacity;
    private List vehicles;
    public List<ParkingLotObserver> observers;

    private int actualCapacity;

    @Override
    public void capacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public void lotIsAvailable() {
        isFullCapacity = false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public void park(Object vehicle) throws ParkingLotException {
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
    }
}
