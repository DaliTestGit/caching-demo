package nl.jsprengers.caching;

import nl.jsprengers.caching.external.WeatherStation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureServiceTest {

    @Mock
    WeatherStation weatherStation;

    @InjectMocks
    TemperatureService service;

    @Test
    public void testInvalidValues() {
        service.getTemperatureForCoordinate(12);
        verify(weatherStation).getForCoordinate(12);
    }
}