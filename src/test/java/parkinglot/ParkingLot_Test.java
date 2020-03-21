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
        boolean isParked = parkingSystem.park(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenAlreadyParked_ShouldReturnFalse() {
        parkingSystem.park(vehicle);
        boolean isParked = parkingSystem.park(vehicle);
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicle_AfterUnParked_ShouldReturnTrue() {
        parkingSystem.park(vehicle);
        boolean isUnPark = parkingSystem.unPark(vehicle);
        Assert.assertTrue(isUnPark);
    }

    @Test
    public void noGivenVehicle_AfterUnParked_ShouldReturnFalse() {
        boolean isUnPark = parkingSystem.unPark(null);
        Assert.assertFalse(isUnPark);
    }
}
