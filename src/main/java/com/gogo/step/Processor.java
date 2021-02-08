package com.gogo.step;

import org.springframework.batch.item.ItemProcessor;

import com.gogo.model.LinerSchedule;

public class Processor implements ItemProcessor<LinerSchedule, LinerSchedule>{
	
	@Override
	public LinerSchedule process(LinerSchedule data) throws Exception{
		
		System.out.println("process--------");
		System.out.println(data.getLinercode());
		if(data.getLinercode().equals("No.5")) {
			data.setRemark("uptatd");
		}
		
		return data;
	}

}
