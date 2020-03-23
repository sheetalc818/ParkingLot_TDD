package parkinglot;

public class parkingLotSecurity implements ParkingLotObserver  {
    boolean isFullCapacity;

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
}
