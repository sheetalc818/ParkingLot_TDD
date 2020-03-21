package parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLot_Test {
    @Test
    public void givenVehicle_AfterParked_ShouldReturnTrue() {
        ParkingSystem parkingSystem = new ParkingSystem();
        boolean isPark = parkingSystem.park(new Object());
        Assert.assertTrue(isPark);
    }
}
