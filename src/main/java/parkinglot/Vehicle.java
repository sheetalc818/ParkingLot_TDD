package parkinglot;

public class Vehicle {

    public final VehicleType vehicleType;
    public final String thisVehicleNumberPlate;
    public VehicleColor vehicleColor;

    public enum VehicleColor {WHITE, BLUE, OTHER}
    public enum VehicleType {TOYOTA, BMW}

    Vehicle(String vehicleNumberPlate, VehicleColor color, VehicleType vehicleType) {
        this.thisVehicleNumberPlate = vehicleNumberPlate;
        this.vehicleColor = color;
        this.vehicleType = vehicleType;
    }
}
