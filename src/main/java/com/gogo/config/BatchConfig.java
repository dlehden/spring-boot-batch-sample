package com.gogo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gogo.listener.JobCompletionListener;
import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;
import com.gogo.service.LinerScheduleService;
import com.gogo.step.Reader;
import com.gogo.step.Writer;

@Configuration
@EnableJpaRepositories(basePackages = "com.gogo.repository") 
public class BatchConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	@Autowired
	protected JobRepository jobRepository;
	
	@Autowired
	private LinerScheduleRepository linerScheduleRepository;
	
	@Autowired
	private LinerScheduleService linerScheduleService;
	
	

	@Bean
	public Job processJob() {
			return jobBuilderFactory.get("processJob")
								    .incrementer(new RunIdIncrementer())
								  //  .repository(jobRepository)
								    .listener(listener())
								    .flow(CrawlingStep1()).end().build();
	}
	
//	@Bean
//	public Job processJob2() {
//			return jobBuilderFactory.get("processJob2")
//								    .incrementer(new RunIdIncrementer())
//								  //  .repository(jobRepository)
//								    .listener(listener())
//								    .flow(CrawlingStep1()).end().build();
//	}
	
	@Bean
	public Step CrawlingStep1() {
			return stepBuilderFactory.get("CrawlingStep1")
					//  .repository(jobRepository)
					.<LinerSchedule,LinerSchedule>chunk(100)
				    //  .reader(new Reader()).processor(new Processor())
				      .reader(new Reader(linerScheduleService))
				      .writer(new Writer(linerScheduleRepository)).build();
	}
	
	@Bean
	public JobExecutionListener listener() {
			return new JobCompletionListener();
	}
	
	
	

}
