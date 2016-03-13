package nl.jsprengers.caching;

import nl.jsprengers.caching.db.PostCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {
    private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    @Autowired
    PostcodeService postcodeService;

    @Autowired
    TemperatureService temperatureService;

    @Autowired
    SharesService shareService;

    @RequestMapping(value = "/temperature/{postcode}", method = RequestMethod.GET)
    public ResponseEntity<String> getTemperature(@PathVariable(value = "postcode") String postcode) {
        LOGGER.info("GET temperature for postcode {}", postcode);
        PostCode pcode = postcodeService.getPostcode(postcode);
        if (pcode == null)
            return new ResponseEntity<>("Unknown postcode", HttpStatus.NOT_FOUND);
        float temperatureForCoordinate = temperatureService
                .getTemperatureForCoordinate(pcode.getCoordinate());
        return new ResponseEntity<>("temperature: " + Float.toString(temperatureForCoordinate), HttpStatus.OK);
    }

    @RequestMapping(value = "/share/{share}", method = RequestMethod.GET)
    public ResponseEntity<String> getSharePrice(@PathVariable(value = "share") String share) {
        try {
            float value = shareService.getValue(share);
            return new ResponseEntity<>(share + "= " + Float.toString(value), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
