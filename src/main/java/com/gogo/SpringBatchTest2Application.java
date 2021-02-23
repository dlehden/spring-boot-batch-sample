package com.gogo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class SpringBatchTest2Application {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job2;
	
	@Autowired
	private Job job1;

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchTest2Application.class, args);
	}
	
	@Bean
	public void run1() throws Exception{
		System.out.println("여기 실행1");
		JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
    jobLauncher.run(job1, params);

	}

	
	@Bean
	public void run2() throws Exception{
		System.out.println("여기 실행2");
		JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
    jobLauncher.run(job2, params);

	}
}
