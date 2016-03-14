package nl.jsprengers.caching.integration;

import com.google.common.cache.Cache;
import nl.jsprengers.caching.Application;
import nl.jsprengers.caching.CacheConfig;
import nl.jsprengers.caching.Controller;
import nl.jsprengers.caching.external.StockExchange;
import nl.jsprengers.caching.Shares;
import nl.jsprengers.caching.SharesService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest
public class SharesIntegrationTest {
    @Autowired
    CacheManager cacheManager;
    Cache sharesCache;

    @Autowired
    SharesService sharesService;

    @Autowired
    StockExchange stockExchange;

    @Before
    public void setup() {
        sharesCache = getAndInvalidate(CacheConfig.SHARES_CACHE);
    }

    @Test
    public void testShares() {
        float value = sharesService.getValue(Shares.AKZO.name());
        assertThat(sharesCache.asMap()).containsKey("AKZO");
        //retrieves from cache:
        assertThat(sharesService.getValue(Shares.AKZO.name())).isEqualTo(value);
        stockExchange.invalidateAllPrices();
        float updatedValue = sharesService.getValue(Shares.AKZO.name());
        assertThat(value).isNotEqualTo(updatedValue);
        //retrieves from cache:
        assertThat(sharesService.getValue(Shares.AKZO.name())).isEqualTo(updatedValue);
    }

    private Cache getAndInvalidate(String name) {
        Cache guavaCache = (Cache) cacheManager.getCache(name)
                                               .getNativeCache();
        assertThat(guavaCache).isNotNull();
        guavaCache.invalidateAll();
        return guavaCache;
    }
}
