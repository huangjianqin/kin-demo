package org.kin.demo.spring.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author huangjianqin
 * @date 2021/6/5
 */
public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByAgeBetweenOrderByAgeAsc(int from, int to, Pageable page);
}
