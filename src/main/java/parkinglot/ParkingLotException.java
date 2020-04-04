package parkinglot;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
        VEHICLE_NOT_FOUND,PARKING_LOT_FULL,UNPARKING_WRONG_VEHICLE;
    }
    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type)
    {
        super(message);
    }
}
