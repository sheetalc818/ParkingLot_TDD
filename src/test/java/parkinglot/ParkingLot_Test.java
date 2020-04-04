package parkinglot;

import org.junit.*;

import java.util.ArrayList;

public class ParkingLot_Test
{
    Vehicle vehicle;
    ParkingLotOwner owner;
    AirportSecurity security;

    @Before
    public void setUp() {
        vehicle = new Vehicle("MH41 R1234", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
        owner = new ParkingLotOwner();
        security = new AirportSecurity();
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        Object vehicle1 = new Object();
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldUnparked() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            Object isVehicleParked = parkingLotSystem.unPark(vehicle);
            Assert.assertEquals(null, isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenUnparkingUnavailableVehicle_shouldThrowVehicleNotFoundException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        Vehicle vehicle1 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new HandicapParkingStrategy());
            parkingLotSystem.park(vehicle1, new NormalParkingStrategy());
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_shouldReturnFalse() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle Available",e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenAttemptToUnparkDifferentVehicle_shouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.unPark(new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA));
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle Available",e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            Vehicle vehicle2 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle2, new NormalParkingStrategy());
            parkingLotSystem.park(new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA), new NormalParkingStrategy());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
        } catch (ParkingLotException e) {
            Assert.assertTrue(owner.isParkingLotFull());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformAirportSecurity() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.registerObserver(security);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
        } catch (ParkingLotException e) {
            Assert.assertTrue(security.isParkingLotFull());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetEmptyAfterFull_ShouldInformOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA), new NormalParkingStrategy());
            parkingLotSystem.park(new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA), new NormalParkingStrategy());
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
        }
        boolean lotFull = owner.isParkingLotEmpty();
        Assert.assertFalse(lotFull);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkCar() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotHavingAttendant_IfVehicleIsNtParked_ShouldReturnFalse() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(new Vehicle("MH18 2400", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA));
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_IfDemandedForSlot_shouldBeAbleToGetEmptySlotList() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        parkingLotSystem.registerObserver(owner);
        ArrayList emptySlots = (ArrayList) parkingLotSystem.findEmptySlots();
        Integer slotPosition = (int) (Math.random() * emptySlots.size());
        Assert.assertNotNull(slotPosition);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkInDecidedSlot() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 5);
            parkingLotSystem.registerObserver(owner);
            ArrayList emptySlots = (ArrayList) parkingLotSystem.findEmptySlots();
            Integer slotPosition = (int) (Math.random() * emptySlots.size());
            Vehicle vehicle1 = new Vehicle("MH18 2400", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            Vehicle vehicle2 = new Vehicle("MH18 2400", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            Vehicle vehicle3 = new Vehicle("MH18 2400", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle1, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle2, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle3, new NormalParkingStrategy());
            VehicleLocation vehicleLocation = parkingLotSystem.findMyCar(vehicle);
            Assert.assertEquals((Integer) 0, vehicleLocation.parkinglot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingSlots_ifVehicleParked_ShouldBeAbleToCharge()
    {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleParkingLots_ifVehicleComes_ShouldUseEvenDistributionForParking() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 4);
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            VehicleLocation pos1 = parkingLotSystem.findMyCar(vehicle);

            Vehicle vehicle2 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle2, new HandicapParkingStrategy());
            VehicleLocation pos2 = parkingLotSystem.findMyCar(vehicle2);

            Vehicle vehicle3 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle3, new NormalParkingStrategy());
            VehicleLocation pos3 = parkingLotSystem.findMyCar(vehicle3);

            Vehicle vehicle4 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.OTHER, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle4, new NormalParkingStrategy());
            VehicleLocation pos4 = parkingLotSystem.findMyCar(vehicle4);

            Assert.assertEquals((Integer) 0, pos1.parkinglot);
            Assert.assertEquals((Integer) 0, pos1.parkingSlot);
            Assert.assertEquals((Integer) 0, pos2.parkinglot);
            Assert.assertEquals((Integer) 1, pos2.parkingSlot);
            Assert.assertEquals((Integer) 1, pos3.parkinglot);
            Assert.assertEquals((Integer) 0, pos3.parkingSlot);
            Assert.assertEquals((Integer) 2, pos4.parkinglot);
            Assert.assertEquals((Integer) 0, pos4.parkingSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleParkingLotsWithCars_IfWhiteCarFound_ShouldReturnVehicle() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 4);
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());

            Vehicle vehicle2 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.OTHER, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle2, new HandicapParkingStrategy());

            Vehicle vehicle3 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle3, new LargeVehicleParkingStrategy());

            Vehicle vehicle4 = new Vehicle("MH41 R6007", Vehicle.VehicleColor.OTHER, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle4, new LargeVehicleParkingStrategy());

            ArrayList<VehicleDTO> listOfVehicles = parkingLotSystem.findCarsWithColor(Vehicle.VehicleColor.WHITE, Vehicle.VehicleType.TOYOTA);
            Assert.assertEquals(vehicle.vehicleType,listOfVehicles.get(0).vehicleType);
            Assert.assertEquals(vehicle3.vehicleType,listOfVehicles.get(1).vehicleType);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleParkingLotsWithCars_IfFoundBlueToyota_ShouldReturnItsInformation() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 4);
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());

            Vehicle vehicle2 = new Vehicle("MH41 R6007",Vehicle.VehicleColor.BLUE,Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle2, new HandicapParkingStrategy());

            Vehicle vehicle3 = new Vehicle("MH41 R6789", Vehicle.VehicleColor.OTHER, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle3, new NormalParkingStrategy());

            Vehicle vehicle4 = new Vehicle("MH41 R6543", Vehicle.VehicleColor.BLUE, Vehicle.VehicleType.TOYOTA);
            parkingLotSystem.park(vehicle4, new NormalParkingStrategy());

            ArrayList<VehicleDTO> listOfVehicles = parkingLotSystem.findCarsWithColor(Vehicle.VehicleColor.BLUE, Vehicle.VehicleType.TOYOTA);
            Assert.assertEquals(vehicle2.vehicleType,listOfVehicles.get(0).vehicleType);
            Assert.assertEquals(vehicle4.vehicleType,listOfVehicles.get(1).vehicleType);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
