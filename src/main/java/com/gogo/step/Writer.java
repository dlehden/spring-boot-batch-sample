package com.gogo.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;

@Component
public class Writer implements ItemWriter<LinerSchedule>{
	
	@Autowired
	 //LinerScheduleService linerScheduleService;
	 LinerScheduleRepository linerScheduleRepository;

	public void write(List<? extends LinerSchedule>  linerschedule) throws Exception {
		//for(LinerSchedule msg : messages) {
			LinerSchedule liner = new LinerSchedule
					(linerschedule.get(0).getLinercode(),
					 linerschedule.get(0).getVesselname(),
					 linerschedule.get(0).getPod(),
					 linerschedule.get(0).getPol(),
					 linerschedule.get(0).getEtd(),
					 linerschedule.get(0).getEta(),
					 linerschedule.get(0).getRemark());
	
			//linerScheduleService.save(liner);
			linerScheduleRepository.save(liner);
			System.out.println("Writing the data" + linerschedule.get(0).getLinercode());
	//	}
	}

}
