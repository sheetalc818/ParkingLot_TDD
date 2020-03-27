package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot_Test {

    ParkingSystem parkingSystem = null;
    Object vehicle = null;

    @Before
    public void setUp() {
        parkingSystem = new ParkingSystem(2);
        vehicle = new Object();
    }

    //Usecase -1
    @Test
    public void givenVehicle_AfterParked_ShouldReturnTrue() {
        boolean isParked = false;
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            isParked = parkingSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenAlreadyParked_ShouldReturnFalse() {
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full", e.getMessage());
        }
    }

    //Usecase -2
    @Test
    public void givenVehicle_AfterUnParked_ShouldReturnTrue() {
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            boolean isUnPark = parkingSystem.unPark(vehicle);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenNoVehicle_AfterUnParked_ShouldReturnFalse() {
        boolean isUnPark = parkingSystem.unPark(null);
        Assert.assertFalse(isUnPark);
    }

    @Test
    public void givenVehicle_WhenVehicleNotFound_ShouldThrowVehicleNotFoundException() {
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.findVehicle(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle not found",e.getMessage());
        }
    }

    //Usecase -3
    @Test
    public void givenWhenParkingLotIfFull_ShouldInformTheOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingSystem.registerParkingLotObserver(parkingLotOwner);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = parkingLotOwner.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenCapacityIs2_OwnerShouldAbleToPark2Vehicles() {
        Object vehicle2 = new Object();
        parkingSystem.setCapacity(2);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(vehicle2, ParkingSystem.DriverType.NORMAL_DRIVER);
            boolean isParked1 = parkingSystem.isVehicleParked(vehicle);
            boolean isParked2 = parkingSystem.isVehicleParked(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        } catch (Exception e) {
        }
    }

    //Usecase -4
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheSecurity() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingSystem.registerParkingLotObserver(parkingLotOwner);
        AirportSecurity parkingLotSecurity = new AirportSecurity();
        parkingSystem.registerParkingLotObserver(parkingLotSecurity);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
        }
        boolean capacityFull = parkingLotSecurity.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    //Usecase -5
    @Test
    public void givenWhenParkingLotSpaceIsAvailableAfterFull_ShouldReturnTrue() {
        Object vehicle2 = new Object();
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingSystem.registerParkingLotObserver(parkingLotOwner);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(vehicle2, ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) { }

        parkingSystem.unPark(vehicle);
        boolean capacityFull = parkingLotOwner.isCapacityFull();
        Assert.assertFalse(capacityFull);
    }

    @Test
    public void givenVehicle_WhenLotSpaceIsAvailableAfterFull_ShouldInformTheAirPortSecurityAndReturnFalse() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingSystem.registerParkingLotObserver(airportSecurity);
        parkingSystem.registerParkingLotObserver(airportSecurity);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        parkingSystem.unPark(vehicle);
        Assert.assertFalse(airportSecurity.isCapacityFull());
    }

    //Usecase -6
    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        List vehicleList = new ArrayList();
        vehicleList.add(0);
        vehicleList.add(1);
        parkingSystem.setCapacity(2);
        parkingSystem.parkingLotInitialize();
        ArrayList emptySlotList = parkingSystem.getEmptyParkingSlot();
        Assert.assertEquals(vehicleList, emptySlotList);
    }

    @Test
    public void AfterParkingAndUnParkingVehicles_ShouldReturnAvailableSlots() {
        List expectedList = new ArrayList();
        expectedList.add(0);
        expectedList.add(2);
        parkingSystem.setCapacity(3);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER, 0);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER, 1);
        } catch (ParkingLotException e) {}
        parkingSystem.unPark(vehicle);
        ArrayList emptySlotList = parkingSystem.getEmptyParkingSlot();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenVehicleForParkingOnEmptySlot_WhenParkWithProvidedEmptySlot_ShouldReturnTrue() {
        parkingSystem.setCapacity(10);
        parkingSystem.parkingLotInitialize();
        ArrayList<Integer> emptySlotList = parkingSystem.getEmptyParkingSlot();
        try {
            parkingSystem.park(vehicle,ParkingSystem.DriverType.NORMAL_DRIVER,emptySlotList.get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        boolean vehiclePark = parkingSystem.isVehicleParked(vehicle);
        Assert.assertTrue(vehiclePark);
    }

    //Usecase -7
    @Test
    public void givenAVehicle_WhenVehicleIsFound_ShouldReturnVehicleParkingSlotNumber() {
        int slotNumber = 0;
        try {
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            slotNumber = parkingSystem.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(0, slotNumber);
    }

    @Test
    public void givenAVehicle_WhenVehicleNotFound_ShouldThrowException() {
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.findVehicle(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full",e.getMessage());
        }
    }

    //Usecase -8
    @Test
    public void givenAVehicle_WhenVehicleParked_ShouldReturnParkingTime() {
        int parkingTime = 0;
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            parkingTime = parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) { }
        Assert.assertEquals(parkingLotOwner.getParkingTime(), parkingTime);
    }

    //Usecase -9
    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        parkingSystem.setCapacity(5);
        try {
            parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
            parkingSystem.unPark(vehicle);
            parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
        } catch (ParkingLotException e) { }
        Object lastEmptySlot = parkingSystem.getEmptyParkingSlot().get(0);
        Assert.assertEquals(0, lastEmptySlot);
    }

    //Usecase -10
    @Test
    public void givenCarToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() throws ParkingLotException {
        parkingSystem.setCapacity(5);
        Object vehicle2 = new Object();
        parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
        parkingSystem.park(new Object(), ParkingSystem.DriverType.NORMAL_DRIVER);
        parkingSystem.park(vehicle2, ParkingSystem.DriverType.NORMAL_DRIVER);
        parkingSystem.park(new Object(), ParkingSystem.DriverType.HANDICAP_DRIVER);
        parkingSystem.unPark(vehicle2);
        parkingSystem.unPark(vehicle);
        parkingSystem.park(vehicle, ParkingSystem.DriverType.NORMAL_DRIVER);
        parkingSystem.park(vehicle2, ParkingSystem.DriverType.HANDICAP_DRIVER);
        int vehicleParkedLocation = parkingSystem.findVehicle(vehicle2);
        Assert.assertEquals(1, vehicleParkedLocation);
    }
}

