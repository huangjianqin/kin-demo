package org.kin.demo.reactor.r2dbc;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

/**
 * @author huangjianqin
 * @date 2022/11/18
 */
public class R2dbcMysqlTest {
    public static void main(String[] args) throws InterruptedException {
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host("127.0.0.1")
                .user("root")
                .port(3306)
                .password("123456")
                .database("kin_demo")
                .build();

        ConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration);

        Mono.from(connectionFactory.create())
                .flatMapMany(connection -> connection
                        .createStatement("SELECT * FROM USER WHERE age > ?")
                        .bind(0, 5)
                        .execute())
                .flatMap(result -> result
                        .map((row, rowMetadata) -> row.get("name", String.class)))
                .doOnNext(v -> {
                    System.out.println(Thread.currentThread().getName() + ": " + v);
                })
                .subscribe();

        Thread.sleep(5_000);
    }
}
