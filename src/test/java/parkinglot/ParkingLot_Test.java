package parkinglot;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParkingLot_Test {
    ParkingLotSystem parkingLotSystem;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;
    Object vehicle;

    @Before
    public void setup() {
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem(2);
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotSystem.registerObserver(owner);
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        boolean isUnParked = false;
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            isUnParked = parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) { }
        assertTrue(isUnParked);
    }

    @Test
    public void givenVehicle_WhenIsNotAlreadyParked_ShouldThrowVehicleNotFoundException() {
        try {
            boolean isUnParked = parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vehicle() {
        Object vehicle2 = new Object();
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) { }

        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
        boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenWhenLotIsFull_ShouldInformTheSecurity() {
        parkingLotSystem.registerObserver(airportSecurity);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenVehicle_WhenLotSpaceIsAvailableAfterFull_ShouldInformTheOwnerAndReturnFalse() {
        Object vehicle2 = new Object();
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        assertFalse(owner.isCapacityFull());
    }

    @Test
    public void givenVehicle_WhenLotSpaceIsAvailableAfterFull_ShouldInformTheAirPortSecurityAndReturnFalse() {
        parkingLotSystem.registerObserver(airportSecurity);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        assertFalse(airportSecurity.isCapacityFull());
    }

    //Usecase -6
    @Test
    public void givenParkingLotCapacity_WhenInitialize_ShouldReturnParkingCapacity() {
        parkingLotSystem.setParkingLotCapacity(10);
        int parkingLotCapacity = parkingLotSystem.initializeParkingLot();
        assertEquals(10, parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        List expectedList = new ArrayList();
        expectedList.add(0);
        expectedList.add(1);
        parkingLotSystem.setParkingLotCapacity(2);
        parkingLotSystem.initializeParkingLot();
        List emptySlotList = parkingLotSystem.getListOfEmptyParkingSlots();
        assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void AfterParkingAndUnParkingVehicles_ShouldReturnAvailableSlots() {
        List expectedList = new ArrayList();
        expectedList.add(0);
        expectedList.add(2);
        parkingLotSystem.setParkingLotCapacity(3);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) { }

        List emptySlotList = parkingLotSystem.getListOfEmptyParkingSlots();
        assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenVehicleForParkingOnEmptySlot_WhenParkWithProvidedEmptySlot_ShouldReturnTrue() {
        parkingLotSystem.setParkingLotCapacity(10);
        parkingLotSystem.initializeParkingLot();
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) { }
        boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(vehiclePark);
    }

    //Usecase -7
    @Test
    public void givenVehicle_WhenVehicleFound_ShouldReturnVehicleParkingSlotNumber() {
        int slotNumber=0;
        try {
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            slotNumber = parkingLotSystem.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        assertEquals(0, slotNumber);
    }

    @Test
    public void givenVehicle_WhenVehicleNotFound_ShouldThrowVehicleNotFoundException() {
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.findVehicle(new Object());
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //Usecase -8
    @Test
    public void givenVehicleForParking_WhenVehicleParkedTimeIsSet_ShouldReturnParkingTime() {
        int parkingTime = (int)((System.currentTimeMillis() / (1000*60)) % 60);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) { }
        int vehicleParkingTime = parkingLotSystem.getVehicleParkingTime(vehicle);
        assertEquals(parkingTime, vehicleParkingTime);
    }

    //Usecase -9
    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        parkingLotSystem.setParkingLotCapacity(5);
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.unPark(vehicle);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        Object lastEmptySlot = parkingLotSystem.getListOfEmptyParkingSlots().get(0);
        assertEquals(0, lastEmptySlot);
    }

    //Usecase -10
    @Test
    public void givenCarToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLotSystem.setParkingLotCapacity(5);
        int vehicleParkedLocation=0 ;
        Object vehicle2 = new Object();
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.HANDICAP_DRIVER);
            parkingLotSystem.unPark(vehicle2);
            parkingLotSystem.unPark(vehicle);
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL_DRIVER);
            parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP_DRIVER);
            vehicleParkedLocation = parkingLotSystem.findVehicle(vehicle2);
        } catch (ParkingLotException e) { }
        assertEquals(1, vehicleParkedLocation);
    }
}
