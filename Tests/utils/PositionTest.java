package utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PositionTest {
    private Position position1;
    private Position position2;
    private Position position3;

    @Before
    public void setUp() {
        position1 = new Position(0, 0); // Initialize position1 with coordinates (0, 0)
        position2 = new Position(3, 4); // Initialize position2 with coordinates (3, 4)
        position3 = new Position(1, 1); // Initialize position3 with coordinates (1, 1)
    }

    @Test
    public void testRange() {
        // Test cases for range method
        double delta = 0.0001; // Delta for floating-point comparison

        // Test range between position1 and position2
        Assert.assertEquals("Range between position1 and position2 should be 5", 5.0, position1.range(position2), delta);

        // Test range between position1 and position3
        Assert.assertEquals("Range between position1 and position3 should be sqrt(2)", Math.sqrt(2), position1.range(position3), delta);

        // Test range between position2 and position3
        Assert.assertEquals("Range between position2 and position3 should be sqrt(18)", Math.sqrt(18), position2.range(position3), delta);
    }
}
