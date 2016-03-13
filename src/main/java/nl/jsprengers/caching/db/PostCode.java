package nl.jsprengers.caching.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "postcode")
public class PostCode implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private int coordinate;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getCoordinate() {
        return coordinate;
    }
}
