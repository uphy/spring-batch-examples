package com.github.uphy.springbatchexample.chunk.job;

import java.util.Locale;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CustomerJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Customer> customerItemReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("/data/customer.csv"));

        DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "firstName", "lastName", "birthdate");

        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(fieldSet -> new Customer(fieldSet.readLong("id"),
                                                                      fieldSet.readString("firstName"),
                                                                      fieldSet.readString("lastName"),
                                                                      fieldSet.readDate("birthdate",
                                                                                        "yyyy-MM-dd HH:mm:ss")));
        customerLineMapper.afterPropertiesSet();

        reader.setLineMapper(customerLineMapper);

        return reader;
    }

    @Bean
    public ItemProcessor<Customer, Customer> customerItemProcessor() {
        return customer -> new Customer(customer.getId(),
                                        customer.getFirstName().toUpperCase(Locale.ROOT),
                                        customer.getLastName().toUpperCase(),
                                        customer.getBirthdate());
    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return items -> {
            for (Customer item : items) {
                System.out.println(item.toString());
            }
        };
    }

    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
                                 .<Customer, Customer>chunk(10)
                                 .reader(customerItemReader())
                                 .processor(customerItemProcessor())
                                 .writer(customerItemWriter())
                                 .build();
    }

    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get("chunkJob")
                                .start(chunkStep())
                                .build();
    }

}
