package org.essaadani.springbatchbankapp.controllers;

import org.essaadani.springbatchbankapp.BankTransactionItemAnalyticsProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    BankTransactionItemAnalyticsProcessor bankTransactionItemAnalyticsProcessor;


    @GetMapping("/startLoadJob")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put("time", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(parameterMap);

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        while(jobExecution.isRunning()){
            System.out.println("Job is loading data...");
        }

        return jobExecution.getStatus();

    }

    @GetMapping("/analytics")
    public Map<String, Double> analytics(){
        Map<String, Double> analytics = new HashMap<>();
        analytics.put("totalCredit", bankTransactionItemAnalyticsProcessor.getTotalCredit());
        analytics.put("totalDebit", bankTransactionItemAnalyticsProcessor.getTotalDebit());

        return analytics;
    }
}


