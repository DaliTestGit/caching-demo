package nl.jsprengers.caching;

import nl.jsprengers.caching.external.StockExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SharesService {
    private static Logger LOGGER = LoggerFactory.getLogger(SharesService.class);
    @Autowired
    StockExchange exchange;

    @CacheEvict(CacheConfig.STOCKS_CACHE)
    public void invalidateShare(String stock) {
        LOGGER.info("Stock {} was updated", stock);
    }

    @Cacheable(CacheConfig.STOCKS_CACHE)
    public float getValue(String stockName) {
        LOGGER.info("Fetching stock {} from exchange", stockName);
        return exchange.getValue(stockName);
    }

}
