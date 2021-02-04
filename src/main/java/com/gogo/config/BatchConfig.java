package com.gogo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import com.gogo.listener.JobCompletionListener;
import com.gogo.model.LinerSchedule;
import com.gogo.step.Reader;
import com.gogo.step.Writer;

@Configuration
@EnableJpaRepositories(basePackages = "com.gogo.repository") 
public class BatchConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job processJob() {
			return jobBuilderFactory.get("processJob")
								    .incrementer(new RunIdIncrementer()).listener(listener())
								    .flow(orderStep1()).end().build();
	}
	
	@Bean
	public Step orderStep1() {
			return stepBuilderFactory.get("orderStep1").<LinerSchedule,LinerSchedule>chunk(1)
				    //  .reader(new Reader()).processor(new Processor())
				      .reader(new Reader())
				      .writer(new Writer()).build();
	}
	
	@Bean
	public JobExecutionListener listener() {
			return new JobCompletionListener();
	}
	
	
	

}
