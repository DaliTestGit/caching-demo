package nl.jsprengers.caching;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    public static final String POSTCODE_CACHE = "postcode";
    public static final String TEMPERATURE_CACHE = "temperature";
    public static final String STOCKS_CACHE = "stocks";

    @Value("${cache.postcode.maximum.size:1000}")
    private int postcodeMaxSize;

    @Value("${cache.expire.temperature.seconds:600}")
    private int expiryTemperatureSeconds;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(
                buildPostCodeCache(),
                buildTemperatureCache(),
                buildStocksCache()
        ));
        return simpleCacheManager;
    }

    private GuavaCache buildTemperatureCache() {
        return new GuavaCache(TEMPERATURE_CACHE, CacheBuilder
                .newBuilder().expireAfterWrite(expiryTemperatureSeconds,
                        TimeUnit.SECONDS).build(),
                false);

    }


    private GuavaCache buildStocksCache() {
        return new GuavaCache(STOCKS_CACHE,
                CacheBuilder.newBuilder().build(), false);
    }

    private GuavaCache buildPostCodeCache() {
        return new GuavaCache(POSTCODE_CACHE, CacheBuilder
                .newBuilder()
                .maximumSize(postcodeMaxSize)
                .expireAfterAccess(1, TimeUnit.DAYS)
                .build(),
                true);
    }


}
