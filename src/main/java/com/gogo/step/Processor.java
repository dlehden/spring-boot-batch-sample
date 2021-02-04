package com.gogo.step;

import org.springframework.batch.item.ItemProcessor;

import com.gogo.model.LinerSchedule;

public class Processor implements ItemProcessor<LinerSchedule, LinerSchedule>{
	
	@Override
	public LinerSchedule process(LinerSchedule data) throws Exception{
		return data;
	}

}
