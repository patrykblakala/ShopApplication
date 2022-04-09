package com.patryk.shop.config;

import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.processor.ProductProcessor;
import com.patryk.shop.processor.ProductReader;
import com.patryk.shop.processor.ProductWriter;
import com.patryk.shop.processor.domain.ProductCsv;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final ProductProcessor productProcessor;
    private final ProductReader productReader;
    private final ProductWriter productWriter;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public Job productCsvToDataBaseJob(String fileName) {
        return jobBuilderFactory.get("productCsvToDataBAseJob")
                .incrementer(new RunIdIncrementer())
                .flow(stepBuilderFactory.get("productCsvToDataBaseStep")
                        .<ProductCsv, Product>chunk(5)
                        .reader(productReader.read(fileName))
                        .processor(productProcessor)
                        .writer(productWriter)
                        .taskExecutor(taskExecutor())
                        .build())

                .end()
                .build();
    }

    private TaskExecutor taskExecutor() {
        var simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(3);
        return simpleAsyncTaskExecutor;
    }

}
