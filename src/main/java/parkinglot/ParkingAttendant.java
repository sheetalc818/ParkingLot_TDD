package parkinglot;
import java.util.List;

public class ParkingAttendant implements ParkingLotObserver {
    boolean isFullCapacity;
    private List vehicles;
    public List<ParkingLotObserver> observers;

    private int actualCapacity;

    @Override
    public void setCapacityFull() {

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
                observer.isCapacityFull();
            }
            throw new ParkingLotException("Parking Lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        this.vehicles.add(vehicle);
    }
}
