package parkinglot;

public abstract interface ParkingLotObserver {
    public void capacityIsFull();
    public void lotIsAvailable();

    void setParkingTime(int minute);
}
