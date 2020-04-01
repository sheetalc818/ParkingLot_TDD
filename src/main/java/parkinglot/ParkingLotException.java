package parkinglot;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
        VEHICLE_NOT_FOUND,PARKING_LOT_FULL,VEHICLE_ALREADY_PARKED
    }
    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType vehicleNotFound) {
        super(message);
    }
}
