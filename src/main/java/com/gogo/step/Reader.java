package com.gogo.step;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import com.gogo.model.LinerSchedule;
import com.gogo.service.LinerOneService;
import com.gogo.service.LinerScheduleService;

public class Reader implements ItemReader<LinerSchedule> {

	private LinerScheduleService linerScheduleService;
	private LinerOneService linerOneService;
	private String month;
	private String pol;
	private String pod;
	
	public Reader(LinerOneService linerOneService, String month , String pol , String pod) {
		this.linerOneService = linerOneService;
		this.month = month;
		this.pol = pol;
		this.pod = pod;
	}
	List<LinerSchedule> scheduleData = new ArrayList<>();

	LinerSchedule linerschedule  = new LinerSchedule("2","abc","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");

	private  int count = 0;

	@Override
	public LinerSchedule read() throws Exception{
		if(count==0) {
			System.out.println("0일대 한번만 실행");
			//scheduleData = linerOneService.TestCrawling();
			scheduleData = linerOneService.CheckingLiner2(month, pol, pod);
		}
		//
		System.out.println(scheduleData.size() + "------------"+ "읽어옴" + " " + count +" <--현자 카운트");
		if(count<scheduleData.size()) {
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
