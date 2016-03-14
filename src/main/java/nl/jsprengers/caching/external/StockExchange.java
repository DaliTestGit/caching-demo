package nl.jsprengers.caching.external;

import nl.jsprengers.caching.RotatingValue;
import nl.jsprengers.caching.SharesService;
import nl.jsprengers.caching.Shares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StockExchange {

    private Map<String, RotatingValue> shares = new HashMap<>();

    @PostConstruct
    void init() {
        for (Shares share : Shares.values())
            shares.put(share.name(), new RotatingValue());
    }

    @Autowired
    SharesService sharesService;

    public float getValue(String share) {
        return shares.get(validateName(share).name()).increment();
    }

    public void invalidateAllPrices() {
        shares.entrySet().stream().forEach(entry -> {
            float nextValue = entry.getValue().increment();
            sharesService.setNewSharePrice(entry.getKey(), nextValue);
        });
    }

    private Shares validateName(String name) {
        try {
            return Shares.valueOf(name);
        } catch (Exception e) {
            throw new RuntimeException("Not a valid share");
        }
    }


}
