package com.gogo.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;

@Component
public class Writer implements ItemWriter<LinerSchedule>{
	
	 private LinerScheduleRepository linerScheduleRepository;
	@Autowired
	public  Writer(LinerScheduleRepository linerScheduleRepository) {
		this.linerScheduleRepository = linerScheduleRepository;
	}
	
	
	@Override
	public void write(List<? extends LinerSchedule>  linerschedule) throws Exception {
	
		linerschedule.forEach(liner->{
			 //System.out.println(liner.getRemark());
			// System.out.println("writer--------------");
			linerScheduleRepository.save(liner);
			//System.out.println(linerScheduleRepository.findAll());
		});
		
			
	//	}
	}

}
