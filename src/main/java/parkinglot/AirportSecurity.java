package parkinglot;

public class AirportSecurity implements ParkingLotObserver  {
    boolean isFullCapacity;

    private Boolean parkingLotIsFull;


    public Boolean isParkingLotFull() {
        return parkingLotIsFull;
    }

    @Override
    public void parkingLotIsFull() {
        parkingLotIsFull = true;
    }

    @Override
    public void parkingLotIsEmpty() {
        parkingLotIsFull = false;
    }
}
