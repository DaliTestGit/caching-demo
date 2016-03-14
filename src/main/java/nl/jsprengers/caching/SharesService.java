package nl.jsprengers.caching;

import nl.jsprengers.caching.external.StockExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SharesService {
    private static Logger LOGGER = LoggerFactory.getLogger(SharesService.class);
    @Autowired
    StockExchange exchange;

    @CachePut(cacheNames = CacheConfig.SHARES_CACHE, key = "#share")
    public float setNewSharePrice(String share, float nextValue) {
        LOGGER.info("Share {} was updated to {}", share, nextValue);
        return nextValue;
    }

    @Cacheable(CacheConfig.SHARES_CACHE)
    public float getValue(String shareName) {
        LOGGER.info("Fetching share {} from exchange", shareName);
        return exchange.getValue(shareName);
    }
}
