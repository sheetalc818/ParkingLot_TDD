package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLot_Test {

    ParkingSystem parkingSystem = null;
    Object vehicle = null;

    @Before
    public void setUp() {
        parkingSystem = new ParkingSystem(1);
        vehicle = new Object();
    }

    @Test
    public void givenVehicle_AfterParked_ShouldReturnTrue() {
        boolean isParked = false;
        try {
            parkingSystem.park(vehicle);
            isParked = parkingSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenAlreadyParked_ShouldReturnFalse() {
        try {
            parkingSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_AfterUnParked_ShouldReturnTrue() {
        try {
            parkingSystem.park(vehicle);
            boolean isUnPark = parkingSystem.unPark(vehicle);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) { }
    }

    @Test
    public void givenNoVehicle_AfterUnParked_ShouldReturnFalse() {
        boolean isUnPark = parkingSystem.unPark(null);
        Assert.assertFalse(isUnPark);
    }

    @Test
    public void givenWhenParkingLotIfFull_ShouldInformTheOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingSystem.registerOwner(parkingLotOwner);
        try {
            parkingSystem.park(vehicle);
            parkingSystem.park(new Object());
        } catch (ParkingLotException e) { }
        boolean capacityFull = parkingLotOwner.isCapacityFull();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenCapacityIs2_OwnerShouldAbleToPark2Vehicles() {
        Object vehicle2  = new Object();
        parkingSystem.setCapacity(2);
        try {
            parkingSystem.park(vehicle);
            parkingSystem.park(vehicle2);
            boolean isParked1 = parkingSystem.isVehicleParked(vehicle);
            boolean isParked2 = parkingSystem.isVehicleParked(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        }catch (Exception e){}
    }
}
