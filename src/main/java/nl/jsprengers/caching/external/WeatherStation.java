package nl.jsprengers.caching.external;

import nl.jsprengers.caching.RotatingValue;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherStation {

    private Map<Integer, RotatingValue> measurements = new HashMap<>();

    @PostConstruct
    public void init() {
        for (int i = 10; i < 100; i++)
            measurements.put(i, new RotatingValue());
    }

    public float getForCoordinate(int coordinate) {
        return measurements.get(coordinate).increment();
    }

}
