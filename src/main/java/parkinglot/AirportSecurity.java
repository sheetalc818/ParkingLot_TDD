package parkinglot;

public class AirportSecurity implements ParkingLotObserver  {
    boolean isFullCapacity;


    @Override
    public void setCapacityFull() {
        isFullCapacity = true;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
