package utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HealthTest {
    private Health health;

    @Before
    public void initTest() {
        this.health = new Health(100); // Initialize Health object with capacity 100
    }

    @Test
    public void testTakeDamage() {
        // Test takeDamage method
        Assert.assertEquals("Damage taken should be 20", 20, health.takeDamage(20));
        Assert.assertEquals("Current health should be 80", 80, health.getCurrent());
    }

    @Test
    public void testGetCurrent() {
        // Test getCurrent method
        Assert.assertEquals("Current health should be equal to capacity initially", 100, health.getCurrent());
    }

    @Test
    public void testSetCapacity() {
        // Test setCapacity method
        health.setCapacity(150);
        Assert.assertEquals("Capacity should be updated to 150", 150, health.getCapacity());
    }

    @Test
    public void testIncreaseMax() {
        // Test increaseMax method
        health.increaseMax(50);
        Assert.assertEquals("Capacity should be increased by 50", 150, health.getCapacity());
    }

    @Test
    public void testHeal() {
        // Test heal method
        health.takeDamage(50); // Reduce health first
        health.heal();
        Assert.assertEquals("Current health should be restored to capacity", 100, health.getCurrent());
    }

    @Test
    public void testIncreaseCurrentHealth() {
        // Test increaseCurrentHealth method
        health.takeDamage(20); // Reduce health first
        health.increaseCurrentHealth(30);
        Assert.assertEquals("Current health should be increased to 100", 100, health.getCurrent());
    }

    @Test
    public void testDecreaseCurrentHealth() {
        // Test decreaseCurrentHealth method
        health.decreaseCurrentHealth(30);
        Assert.assertEquals("Current health should be decreased to 70", 70, health.getCurrent());
    }

    @Test
    public void testSetCurrent() {
        // Test setCurrent method
        health.setCurrent(80);
        Assert.assertEquals("Current health should be set to 80", 80, health.getCurrent());
    }
}
