package nl.jsprengers.caching;

import java.util.Random;

public class RotatingValue {
    private float value = 10.0f;
    private float increment = 0.1f;

    public RotatingValue() {
        value = 10.0f + (float) new Random(System.nanoTime()).nextInt(5);
    }

    public float increment() {
        increment = value < 10.0 || value > 20.0 ? -increment : increment;
        value += increment;
        return value;
    }
}
