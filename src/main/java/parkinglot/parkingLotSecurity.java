package parkinglot;

public class parkingLotSecurity implements ParkingLotObserver  {
    boolean isFullCapacity;

    public void capacityIsFull() {
        isFullCapacity = true;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
