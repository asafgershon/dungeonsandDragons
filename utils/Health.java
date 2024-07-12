package utils;

public class Health {
    private int capacity;
    private int current;

    public Health(int capacity) {
        this.capacity = capacity;
        this.current = capacity;
    }

    public int takeDamage(int damageTaken) {
        damageTaken = Math.max(0, damageTaken);
        damageTaken = Math.min(current, damageTaken);
        current -= damageTaken;
        return damageTaken;
    }

    public int getCurrent() {
        return current;
    }

    public void setCapacity(int pool) {
        this.capacity = pool;
    }

    public int getCapacity() {
        return capacity;
    }

    public void increaseMax(int healthGain) {
        capacity += healthGain;
    }

    public void heal() {
        current = capacity;
    }

    public void increaseCurrentHealth(int amount) {
        if(current + amount <= capacity) {
            current += amount;
        }
        else{
            current = capacity;
        }
    }
    public void decreaseCurrentHealth(int amount) {
        if(current - amount < 0)
            current = 0;
        else
            current -= amount;
    }

    public void setCurrent(int i) {
        this.current = i;
    }
}
