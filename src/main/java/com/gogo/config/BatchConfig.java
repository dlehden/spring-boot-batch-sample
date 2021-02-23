package com.gogo.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.gogo.listener.JobCompletionListener;
import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;
import com.gogo.service.LinerOneService;
import com.gogo.service.LinerScheduleService;
import com.gogo.service.LinerTwoService;
import com.gogo.step.Processor;
import com.gogo.step.Reader;
import com.gogo.step.Reader2;
import com.gogo.step.Writer;

@Configuration
@EnableBatchProcessing
//@EnableJpaRepositories(basePackages = "com.gogo.repository") 
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
	@Autowired
	private LinerOneService linerOneService;
	@Autowired
	private LinerTwoService linerTwoService;
	

	  @Bean(name = "job1")
	public Job processJob() {
		System.out.println("---------------------job1실행");
			return jobBuilderFactory.get("processJob")
								    .incrementer(new RunIdIncrementer())
								    .listener(listener())
								     .start(CrawlingStep1("02","KBS","JTK"))
								    // .next(CrawlingStep2("02","KBS","HHG"))
								     .preventRestart()
								    //.end()
								    .build();
	}

	
	 @Bean(name = "job2")
	public Job processJob2() {
		System.out.println("-----------------job2실행");
			return jobBuilderFactory.get("processJob")
								    .incrementer(new RunIdIncrementer())
								  //  .repository(jobRepository)
								    .listener(listener())
								    .flow(CrawlingStep3("02","KBS","HHG")).end().build();
	}
	@Bean
	public Step CrawlingStep1(String month, String pol , String pod) {
			return stepBuilderFactory.get("CrawlingStep1")
					.<LinerSchedule,LinerSchedule>chunk(10)	
					//chunk 100 - >  5s690ms  //chunk 1000 ->5s419ms
				      .reader(new Reader(linerOneService , month , pol , pod))
				      .processor(new Processor(linerScheduleRepository))
				      .writer(new Writer(linerScheduleRepository))
				      .build();
	}

	public Step CrawlingStep3(String month, String pol , String pod) {
		return stepBuilderFactory.get("CrawlingStep1")
				.<LinerSchedule,LinerSchedule>chunk(10)	
			      .reader(new Reader2(linerTwoService , month , pol , pod))
			      .processor(new Processor(linerScheduleRepository))
			      .writer(new Writer(linerScheduleRepository))
			      .build();
}


	@Bean
	public JobExecutionListener listener() {
			return new JobCompletionListener();
	}
	
	
    @Bean

    public JobLauncher jobLauncher() throws Exception {

        // async

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(jobRepository);

        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        jobLauncher.afterPropertiesSet();

        return jobLauncher;

    }

 
//
//    @Bean(name = "job1")
//
//    public Job job1() {
//
//        return jobBuilderFactory.get("job1").incrementer(new RunIdIncrementer())
//
//                .start(stepExecProc("proc_name1")).build();
//
//    }
//
//
//
//    public Step stepExecProc(String procName) {
//
//        return stepBuilderFactory.get("stepExecProc").tasklet((stepContribution, chunkContext) -> {
//
//        	System.out.println(procName + " ------------------");
//
//            return RepeatStatus.FINISHED;
//
//        }).build();
//
//    }
//
//    
//    @Bean(name = "job2")
//
//    public Job job2() {
//
//        return jobBuilderFactory.get("job2").incrementer(new RunIdIncrementer())
//
//                .start(stepExecProc2("proc_name2")).build();
//
//    }
//
//
//
//    public Step stepExecProc2(String procName) {
//
//        return stepBuilderFactory.get("stepExecProc2").tasklet((stepContribution, chunkContext) -> {
//
//        	System.out.println(procName + " ------------------");
//
//            return RepeatStatus.FINISHED;
//
//        }).build();
//
//    }
//	
	

}
