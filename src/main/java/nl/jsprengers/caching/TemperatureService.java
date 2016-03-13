package nl.jsprengers.caching;

import nl.jsprengers.caching.external.WeatherStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    private static Logger LOGGER = LoggerFactory.getLogger(TemperatureService.class);

    @Autowired
    WeatherStation weatherStation;

    @Cacheable(CacheConfig.TEMPERATURE_CACHE)
    public float getTemperatureForCoordinate(int coordinate) {
        LOGGER.info("Getting temperature from weather station {} ", coordinate);
        return weatherStation.getForCoordinate(coordinate);
    }

}
