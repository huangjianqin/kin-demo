package org.kin.demo.spring.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author huangjianqin
 * @date 2021/6/5
 */
@Component
public class MongoRunner implements ApplicationRunner {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //init
//        for (int i = 0; i < 100; i++) {
//            Person person = new Person();
//            person.setAge(ThreadLocalRandom.current().nextInt(150));
//            person.setName(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + "");
//
//            template.save(person);
//        }
        personRepository.findByAgeBetweenOrderByAgeAsc(1, 50, Pageable.ofSize(10)).forEach(System.out::println);
        System.out.println(">>>>>>>>>>>>>>>>>>>>");
        template.find(Query.query(where("age").gte(0).lte(10)).with(Sort.by("age").ascending()), Person.class).forEach(System.out::println);
    }
}
