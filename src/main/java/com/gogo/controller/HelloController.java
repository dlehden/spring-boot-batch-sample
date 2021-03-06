package com.gogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gogo.model.LinerSchedule;
import com.gogo.repository.LinerScheduleRepository;
import com.gogo.service.LinerOneService;
import com.gogo.service.LinerScheduleService;
import com.gogo.service.LinerTwoService;
@RestController
public class HelloController {
	@Autowired
	 private LinerScheduleRepository linerScheduleRepository;
	
	@Autowired
	private LinerScheduleService linerScheduleService;
	
	@Autowired
	private LinerOneService linerOneService;
	@Autowired
	private LinerTwoService linerTwoService;
	
	@GetMapping("/save")
	public String index() {
		
		System.out.println("실행");
		LinerSchedule linerschedule  = new LinerSchedule("2","abc","name","krpus","hkhkg","2021-02-04","2021-02-04","remar");

		linerScheduleRepository.save(linerschedule);
		
		return "member변경! 로그를 확인해보세요.";
	}
	
	@GetMapping("/crawltest")
	@ResponseBody
	public List<LinerSchedule> crawl() {
		
		//return linerOneService.CheckingLiner2("02", "KBS", "HHG");
		
		return linerTwoService.PAN_SCHEDULE();
		
		//linerOneService.CheckingLiner("02", "KBS", "HHG");
		
		//return linerScheduleRepository.findAll();
	}
	
	
	
}
