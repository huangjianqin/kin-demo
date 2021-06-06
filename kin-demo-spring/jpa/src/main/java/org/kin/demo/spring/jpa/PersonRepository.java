package org.kin.demo.spring.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@link SimpleJpaRepository}默认父类, spring data jpa会自动帮我们构建
 *
 * @author huangjianqin
 * @date 2021/6/6
 */
public interface PersonRepository extends JpaRepository<Person, String> {
    List<Person> findByAgeBetweenOrderByAgeAsc(int from, int to, Pageable page);
}
