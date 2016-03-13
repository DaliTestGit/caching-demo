package nl.jsprengers.caching.integration;

import com.google.common.cache.Cache;
import nl.jsprengers.caching.Application;
import nl.jsprengers.caching.CacheConfig;
import nl.jsprengers.caching.Controller;
import nl.jsprengers.caching.db.PostCode;
import nl.jsprengers.caching.TemperatureService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TemperatureIntegrationTest {
    @Autowired
    CacheManager cacheManager;
    @Autowired
    TemperatureService temperatureService;

    @Autowired
    Controller controller;

    Cache postCodeCache;
    Cache temperatureCache;

    @Before
    public void setup() {
        postCodeCache = getAndInvalidate(CacheConfig.POSTCODE_CACHE);
        temperatureCache = getAndInvalidate(CacheConfig.TEMPERATURE_CACHE);
    }

    @Test
    public void testGetTemperatureStorePostCode() {
        String postcode = "1000AA";
        String temperatureForPostcode = controller.getTemperature(postcode).getBody();
        PostCode cachedPostcode = (PostCode) postCodeCache.asMap().get(postcode);
        assertThat(temperatureCache.asMap()).containsKey(cachedPostcode.getCoordinate());
        for (int i = 0; i < 100; i++) {
            controller.getTemperature("1234AA");
            assertThat(temperatureForPostcode).isEqualTo(controller.getTemperature(postcode).getBody());
        }
    }

    @Test
    public void testNullPostcodeIsAlsoCached() {
        assertThat(controller.getTemperature("1234XX").getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(postCodeCache.asMap().get("1234XX")).isInstanceOf(NullValue.class);

    }

    private Cache getAndInvalidate(String name) {

        Cache guavaCache = (Cache) cacheManager.getCache(name)
                                               .getNativeCache();


        assertThat(guavaCache).isNotNull();
        guavaCache.invalidateAll();
        return guavaCache;
    }
}
