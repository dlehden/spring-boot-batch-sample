package com.gogo.service;

import org.springframework.stereotype.Service;

import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;

public class LinerScheduleService {
	
	private final LinerScheduleRepository linerScheduleRepository;
	
	public LinerScheduleService(LinerScheduleRepository linerScheduleRepository) {
		this.linerScheduleRepository = linerScheduleRepository;
	}
	
	public void save(LinerSchedule linerschedule){
		System.out.println("여기 실행");
		//linerScheduleRepository.save(linerschedule);
	}
}
