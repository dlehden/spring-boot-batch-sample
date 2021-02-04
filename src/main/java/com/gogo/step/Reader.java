package com.gogo.step;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemReader;

import com.gogo.model.LinerSchedule;

public class Reader implements ItemReader<LinerSchedule> {

	private String[] messages = {"test1", "test2","test3"};
	Map<String,LinerSchedule> liner = new HashMap<>();
	LinerSchedule linerschedule  = new LinerSchedule("abc","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");

	private  int count = 0;
	
	@Override
	public LinerSchedule read() throws Exception{
		if(count  < messages.length) {
			if(count==1) {
				linerschedule  = new LinerSchedule("abc1","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");
			}
			if(count==2) {
				linerschedule  = new LinerSchedule("abc2","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");
			}
			
			count++;
			return linerschedule;
		}else {
			count = 0;
		}
			return null;
		
		//if(linerschedule.toString().length())
		
		
	}
	

}
