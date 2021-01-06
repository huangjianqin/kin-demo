package org.kin.demo.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Arrays;

/**
 * @author huangjianqin
 * @date 2020/12/30
 */
@SpringBootApplication
@EnableBatchProcessing
public class JobConfiguration1 {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        ListItemReader<Integer> reader = new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5));
        FlatFileItemWriter<Integer> writer = new FlatFileItemWriterBuilder<Integer>()
                .name("writer")
                .resource(new FileSystemResource(
                        "out.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();

        TaskletStep step1 = stepBuilderFactory.get("step1")
                //commit num
                .<Integer, Integer>chunk(10)
                .reader(reader)
                .processor((ItemProcessor<Integer, Integer>) integer -> {
                    System.out.println(Thread.currentThread().getName() + "-----" + integer);
                    return integer;
                })
                .writer(writer)
                //one stpe 多线程
                .taskExecutor(new SimpleAsyncTaskExecutor())
                //线程数
                .throttleLimit(2)
                .build();
        return jobBuilderFactory.get("job1").start(step1).build();
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(JobConfiguration1.class, args);
        //10s后程序exit
        Thread.sleep(10 * 1000);
        context.close();
    }
}
