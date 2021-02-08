package com.gogo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.gogo.model.LinerSchedule;

@Service
public class LinerScheduleService {
	
	
	public List<LinerSchedule>  TestCrawling() {
		String url = "http://www.cgv.co.kr/movies/";
		Document doc = null;
		String text = "";
		String text2 = "";
		List<LinerSchedule> scheduleData = new ArrayList<>();
		try {
			doc = Jsoup.connect(url).get();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.select("div.sect-movie-chart");
		Iterator<Element> ie1 = element.select("strong.rank").iterator();
		Iterator<Element> ie2 = element.select("strong.title").iterator();
		while(ie1.hasNext()) {
			text = ie1.next().text();
			text2 = ie2.next().text();
			scheduleData.add(new LinerSchedule("2",text,"77","T2","T2","2021-02-03","2021-02-03","remark"));
			//System.out.println(scheduleData.get(0)  + "AAAAAAAAAAAAA");
		}
		return scheduleData;
	}
	
	
}
