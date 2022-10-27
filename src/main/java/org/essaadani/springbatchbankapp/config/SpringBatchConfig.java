package org.essaadani.springbatchbankapp.config;

import org.essaadani.springbatchbankapp.entities.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired
    private ItemWriter<BankTransaction> bankTransactionItemWriter;
    @Autowired
    private ItemProcessor<BankTransaction, BankTransaction> bankTransactionItemProcessor;

    @Bean
    public Job transactionJob(){
        Step step = stepBuilderFactory.get("step-load-transaction")
                .<BankTransaction, BankTransaction>chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();

        return jobBuilderFactory.get("job-load-transaction")
                .start(step)
                .build();
    }

    // Item Reader
    @Bean
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource inputFile){
        FlatFileItemReader<BankTransaction> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setName("CSV-READER");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setResource(inputFile);
        fileItemReader.setLineMapper(lineMapper());

        return fileItemReader;
    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "accountId", "strTransactionDate", "transactionType", "amount");

        lineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankTransaction.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }



}
