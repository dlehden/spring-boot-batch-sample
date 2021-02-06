package com.gogo.step;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import com.gogo.model.LinerSchedule;
import com.gogo.service.LinerScheduleService;

public class Reader implements ItemReader<LinerSchedule> {

	private LinerScheduleService linerScheduleService;
	
	public Reader(LinerScheduleService linerScheduleService) {
		this.linerScheduleService = linerScheduleService;
	}
	
	
	List<LinerSchedule> scheduleData = new ArrayList<>();

	LinerSchedule linerschedule  = new LinerSchedule("2","abc","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");
	//LinerSchedule linerschedule2 = new LinerSchedule("abc1","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");

	private  int count = 0;

	@Override
	public LinerSchedule read() throws Exception{
		scheduleData = linerScheduleService.TestCrawling();
		if(count<scheduleData.size()) {
			//System.out.println(scheduleData.get(count).getLinercode());
			
			linerschedule = new LinerSchedule
					(scheduleData.get(count).getMonth(),
					 scheduleData.get(count).getLinercode(),
					 scheduleData.get(count).getVesselname(),
					 scheduleData.get(count).getPol(),
					 scheduleData.get(count).getPod(),
					 scheduleData.get(count).getEtd(),
					 scheduleData.get(count).getEta(),
					 scheduleData.get(count).getRemark());
			count++;		 
			return linerschedule;
			
		}else {
			count=0;
		}

		return null;

		
	}
	

}
