package nl.jsprengers.caching.db;

import org.springframework.data.repository.CrudRepository;

public interface PostcodeDao extends CrudRepository<PostCode, Long> {

    PostCode findByCode(String code);

}
