package org.kin.demo.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.*;

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
                        "outs/out.txt"))
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

    @Bean
    public Job partitionJob() {
        Step step2 = stepBuilderFactory.get("step2")
                .partitioner("step3", new Partitioner() {
                    @Override
                    public Map<String, ExecutionContext> partition(int gridSize) {
                        //自定义分区逻辑
                        Map<String, ExecutionContext> result = new HashMap<>(gridSize);
                        int range = 100;
                        for (int i = 0; i < gridSize; i++) {
                            ExecutionContext executionContext = new ExecutionContext();
                            executionContext.putInt("fromId", i * range);
                            executionContext.putInt("toId", (i + 1) * range);

                            result.put("partition" + i, executionContext);
                        }
                        return result;
                    }
                })
                .step(partitionStep())
                .gridSize(5)
                .build();
        return jobBuilderFactory.get("job2").start(step2).build();
    }

    @Bean
    public Step partitionStep() {
        return stepBuilderFactory.get("step3")
                //commit num
                .<Integer, Integer>chunk(33)
                .reader(partitionReader(0, 0))
                .writer(partitionWriter(0))
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Integer> partitionWriter(@Value("#{stepExecutionContext[fromId]}") final int fromId) {
        //按分区写入文件
        FlatFileItemWriter<Integer> writer = new FlatFileItemWriterBuilder<Integer>()
                .name("writer")
                .resource(new FileSystemResource(
                        "outs/out" + fromId + ".txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
        //!!!!! bug, 通过@StepScope 注入, FlatFileItemWriter不会调用open()方法, 这个硬调用
        writer.open(new ExecutionContext());
        return writer;
    }

    /**
     * @StepScope 标识step范围内的bean
     * #{stepExecutionContext[fromId]}  获取stepExecutionContext的某个值
     */
    @Bean
    @StepScope
    public ItemReader<Integer> partitionReader(@Value("#{stepExecutionContext[fromId]}") final int fromId,
                                               @Value("#{stepExecutionContext[toId]}") final int toId) {
        //模拟分区数据返回
        List<Integer> sources = new ArrayList<>(toId - fromId + 1);
        for (int i = fromId; i < toId; i++) {
            sources.add(i);
        }
        return new ListItemReader<>(sources);
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(JobConfiguration1.class, args);
        //10s后程序exit
        Thread.sleep(10 * 1000);
        context.close();
    }
}
