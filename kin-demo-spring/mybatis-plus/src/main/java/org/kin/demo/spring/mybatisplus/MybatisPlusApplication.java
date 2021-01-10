package org.kin.demo.spring.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import org.kin.demo.spring.mybatisplus.dao.UserDao;
import org.kin.demo.spring.mybatisplus.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author huangjianqin
 * @date 2021/1/10
 */
@SpringBootApplication
public class MybatisPlusApplication {
    @Autowired
    private UserDao userDao;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
        interceptor.setDbType(DbType.MYSQL);
        interceptor.setDialect(new MySqlDialect());
        mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mpInterceptor;
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisPlusApplication.class, args);
        MybatisPlusApplication application = context.getBean(MybatisPlusApplication.class);
        application.demo1();
        application.demo2();
        application.demo3();

        Thread.sleep(10 * 1000);

        context.close();
    }

    public void demo1() {
        System.out.println(("----- demo1 -> selectAll method test ------"));
        List<User> userList = userDao.selectList(null);
        userList.forEach(System.out::println);
    }

    public void demo2() {
        System.out.println(("----- demo2 -> condtition page query test ------"));
        QueryWrapper<User> c = new QueryWrapper<>();
        c.gt("age", 20);
        LambdaQueryWrapper<User> lc = c.lambda();
        lc.le(User::getAge, 25);

        Page<User> page = new Page<>(0, 1);
        userDao.selectPage(page, c);
        page.getRecords().forEach(System.out::println);
    }

    public void demo3() {
        System.out.println(("----- demo3 -> condtition query test ------"));
        System.out.println(userDao.getById(1));
    }
}
