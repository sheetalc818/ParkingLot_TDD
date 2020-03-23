package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLot_Test {

    ParkingSystem parkingSystem = null;
    Object vehicle = null;

    @Before
    public void setUp(){
        parkingSystem = new ParkingSystem();
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
            parkingSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is full",e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_AfterUnParked_ShouldReturnTrue() {
        try {
            parkingSystem.park(vehicle);
            boolean isUnPark = parkingSystem.unPark(vehicle);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noGivenVehicle_AfterUnParked_ShouldReturnFalse() {
        boolean isUnPark = parkingSystem.unPark(null);
        Assert.assertFalse(isUnPark);
    }
}
