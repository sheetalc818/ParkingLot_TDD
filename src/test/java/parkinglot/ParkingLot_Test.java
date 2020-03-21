package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLot_Test {

    ParkingSystem parkingSystem = null;

    @Before
    public void setUp(){
        parkingSystem = new ParkingSystem();
    }

    @Test
    public void givenVehicle_AfterParked_ShouldReturnTrue() {
        boolean isParked = parkingSystem.park(new Object());
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_AfterUnParked_ShouldReturnTrue() {
        Object vehicle = new Object();
        parkingSystem.park(vehicle);
        boolean isUnPark = parkingSystem.unPark(vehicle);
        Assert.assertTrue(isUnPark);
    }
}
