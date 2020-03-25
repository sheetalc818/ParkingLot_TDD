package parkinglot;

public class AirportSecurity implements ParkingLotObserver  {
    boolean isFullCapacity;
    private int parkingTime;

    public void capacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public void lotIsAvailable() {
        isFullCapacity = false;
    }

    @Override
    public void setParkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
