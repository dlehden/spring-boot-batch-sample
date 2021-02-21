package com.gogo.step;

import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;

public class Processor implements ItemProcessor<LinerSchedule, LinerSchedule>{
	
	 private LinerScheduleRepository linerScheduleRepository;
	@Autowired
	public  Processor(LinerScheduleRepository linerScheduleRepository) {
		this.linerScheduleRepository = linerScheduleRepository;
	}
	
	@Override
	public LinerSchedule process(LinerSchedule data) throws Exception{
		
		Optional<LinerSchedule> LinerProcess  = Optional.ofNullable(linerScheduleRepository.findByLinercodeAndVesselname(
				data.getLinercode(), data.getVesselname()));
	
		System.out.println("process--------" + data.getVesselname());
		LinerProcess.ifPresent(liner-> {
			System.out.println(liner.getEtd());
		});
//		if(data.getLinercode().equals("No.5")) {
//			data.setRemark("uptatd2222");
//		}
//		
		return data;
	}

}
