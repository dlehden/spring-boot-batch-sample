package com.gogo.step;

import org.springframework.batch.item.ItemReader;

public class Reader implements ItemReader<String> {
	
	private String[] messages = {"test1", "test2","test3"};
	
	private  int count = 0;
	
	@Override
	public String read() throws Exception{
		if(count  < messages.length) {
			return messages[count++];
		}else {
			count = 0;
		}
			return null;
	}
	

}
