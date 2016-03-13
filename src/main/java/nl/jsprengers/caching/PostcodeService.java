package nl.jsprengers.caching;

import nl.jsprengers.caching.db.PostCode;
import nl.jsprengers.caching.db.PostcodeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PostcodeService {

    private static Logger LOGGER = LoggerFactory.getLogger(PostcodeService.class);

    @Autowired
    PostcodeDao postcodeDao;

    @Cacheable(CacheConfig.POSTCODE_CACHE)
    public PostCode getPostcode(String code) {
        LOGGER.info("Getting postcode {} from dbase", code);
        return postcodeDao.findByCode(code);
    }

}
