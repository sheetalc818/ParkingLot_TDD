package parkinglot;

public class ParkingLotOwner implements ParkingLotObserver {
    boolean isFullCapacity;

    @Override
    public void setCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return isFullCapacity;
    }
}
