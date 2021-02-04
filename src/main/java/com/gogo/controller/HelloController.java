package com.gogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;
@RestController
public class HelloController {
	@Autowired
	 private LinerScheduleRepository linerScheduleRepository;
	@GetMapping("/save")
	public String index() {
		
		System.out.println("실행");
		LinerSchedule linerschedule  = new LinerSchedule("abc","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");

		linerScheduleRepository.save(linerschedule);
		
		return "member변경! 로그를 확인해보세요.";
	}
}
