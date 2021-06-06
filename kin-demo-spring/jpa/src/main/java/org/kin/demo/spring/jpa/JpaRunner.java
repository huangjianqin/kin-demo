package org.kin.demo.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huangjianqin
 * @date 2021/6/6
 */
@Component
public class JpaRunner implements ApplicationRunner {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //init
        for (int i = 0; i < 100; i++) {
            Person person = new Person();
            person.setAge(ThreadLocalRandom.current().nextInt(150));
            person.setName(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + "");

            personRepository.save(person);
        }

        personRepository.findByAgeBetweenOrderByAgeAsc(1, 50, Pageable.ofSize(10)).forEach(System.out::println);
    }
}