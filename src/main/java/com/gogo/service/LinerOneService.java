package com.gogo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import com.gogo.model.LinerSchedule;

@Service
public class LinerOneService {  //선사 1번 크롤링 
	
	public List<LinerSchedule> CheckingLiner(String month , String pol, String pod) {
		String url = "http://korea.djship.co.kr/dj/servlet/kr.eservice.action.sub3_1_Action";
		Document doc = null;
		String text = "";
		String text2 = "";
		List<LinerSchedule> scheduleData = new ArrayList<>();
		System.out.println("실행");
		try {
			doc = Jsoup.connect(url)
					.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
					.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
				    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				    .header("Content-Type", "application/x-www-form-urlencoded")
				    .header("Origin", "http://korea.djship.co.kr")
                    .header("Referer", "http://korea.djship.co.kr/dj/ui/kr/eservice/sub3_1_1.jsp")
					
                    .data("mode", "R")
                    .data("SEL_YEAR", "2021")
                    .data("SEL_MONTH", month)
                    .data("pol_cd", pol)
                    .data("pod_cd", pod)
                    .data("searchGbn","1")
                    .data("cargoGbn","C")
                    .get();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		Elements elements = doc.select("a[href]");
		
		for(Element element : elements) {
			System.out.println(element.toString().substring(9,108));
			String[] split_data = element.toString().substring(9,108).split("'");
			for(int i=0; i < split_data.length; i++) {
				System.out.println( i + "====== " + split_data[i]);
			}
		}
		
		return scheduleData;
	}
	
	public List<LinerSchedule> CheckingLiner2(String month , String pol, String pod) {
		String url = "http://korea.djship.co.kr/dj/ui/kr/eservice/sub3_1_0.jsp?PROGRAM_ID=sub3_1";
		String url_jsoup = "http://korea.djship.co.kr/dj/servlet/kr.eservice.action.sub3_1_Action";
		WebDriver driver = null;
		WebElement element = null;
		
		Document doc = null;
		String text = "";
		String text2 = "";
		List<LinerSchedule> scheduleData = new ArrayList<>();
		System.out.println("실행");
	
		try {
			
			//System.setProperty("webdriver.chrome.driver","C:\\crawling\\chromedriver_win32\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
			
			driver= new ChromeDriver();
			driver.get(url);
			
//			driver.findElement(By.cssSelector("body > form > div:nth-child(5) > div:nth-child(3) > table:nth-child(2) > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(2) > table:nth-child(10) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td.tb_con > select:nth-child(1)")).sendKeys("2021");
//			driver.findElement(By.cssSelector("body > form > div:nth-child(5) > div:nth-child(3) > table:nth-child(2) > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(2) > table:nth-child(10) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td.tb_con > select:nth-child(2)")).sendKeys("02");
//			Thread.sleep(1000);
//			
//			driver.findElement(By.id("pol_cn")).sendKeys("KR");
//			driver.findElement(By.id("pol_cn")).click();
//
//			Thread.sleep(1000);
//			Select polcd = new Select(driver.findElement(By.id("pol_cd")));
//			polcd.selectByValue("KBS");
//			Thread.sleep(1000);
//			driver.findElement(By.id("pod_cn")).sendKeys("CN");
//			Thread.sleep(1000);
//			Select podcd = new Select(driver.findElement(By.id("pod_cd")));
//			podcd.selectByValue("CQD");
//
//			Thread.sleep(1000);
//			driver.findElement(By.cssSelector("body > form > div:nth-child(5) > div:nth-child(3) > table:nth-child(2) > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(2) > table:nth-child(10) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(13) > td.tb_con > table > tbody > tr > td:nth-child(3) > a > img")).click();;
//			Thread.sleep(1000);
//			//달력 안쪽 프레임으로 들어가서 정보를 가지오온다.
			 driver.switchTo().frame(driver.findElement(By.cssSelector("body > form > div:nth-child(5) > div:nth-child(3) > table:nth-child(2) > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(2) > table:nth-child(12) > tbody > tr > td > iframe")));
//			
			 element = driver.findElement(By.cssSelector("body > form"));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			
			
			try {
				doc = Jsoup.connect(url_jsoup)
						.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
						.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
					    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
					    .header("Content-Type", "application/x-www-form-urlencoded")
					    .header("Origin", "http://korea.djship.co.kr")
	                    .header("Referer", "http://korea.djship.co.kr/dj/ui/kr/eservice/sub3_1_1.jsp")
						
	                    .data("mode", "R")
	                    .data("SEL_YEAR", "2021")
	                    .data("SEL_MONTH", month)
	                    .data("pol_cd", pol)
	                    .data("pod_cd", pod)
	                    .data("searchGbn","1")
	                    .data("cargoGbn","C")
	                    .get();
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			Elements elements = doc.select("a[href]");
			
			
	  for(Element jsoup_dt : elements) {
			//System.out.println(jsoup_dt.toString().substring(9,108));
			js.executeScript(jsoup_dt.toString().substring(9,108).replaceAll("\"",""), element );

			//멘처음 부모 창으로 빠져나온다음
			//2번째 정보 프레임으로 들어가서 데이터를 읽어온다.
			driver.switchTo().defaultContent();
			Thread.sleep(500);
			
			driver.switchTo().frame("jobFrame1");
			List<WebElement> info_details = driver.findElements(By.cssSelector("body"));
			
			String detail_data=null;
			for(WebElement info_detail : info_details) {
				detail_data = info_detail.getText();
			}
			String[] split_data = detail_data.split("\\n");
			String[] split_data_vesselname = jsoup_dt.toString().substring(9,108).split("'");
//		  System.out.println(split_data[0]);  //마감여주
//		  System.out.println(split_data[1]); 
//		  System.out.println(split_data[2]);
			scheduleData.add(new LinerSchedule(
				                "2",
				                "DJSC",
				                 split_data_vesselname[5]+split_data_vesselname[7]+split_data_vesselname[9],//vessel_name
				                 pol,
				                 pod,
				                 split_data[5].substring(6,22), //ETD
				                 split_data[4].substring(34,50), //ETA
				                "remark"));
   
			driver.switchTo().defaultContent();
			Thread.sleep(500);
			driver.switchTo().frame(driver.findElement(By.cssSelector("body > form > div:nth-child(5) > div:nth-child(3) > table:nth-child(2) > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(2) > table:nth-child(12) > tbody > tr > td > iframe")));

          } //for
		}catch(Throwable e) {
			e.printStackTrace();
			driver.quit();
		}finally {
			driver.quit();
		}
		
		
		
		//driver.close();
		
		return scheduleData;
	}
	public List<LinerSchedule>  TestCrawling() {

		List<LinerSchedule> scheduleData = new ArrayList<>();
		for(int i=0 ; i< 3 ; i ++) {
		
		scheduleData.add(new LinerSchedule("2","PAN","202"+Integer.toString(i),"T2","T2","2021-02-03","2021-02-03","remark"));
		}
		return scheduleData;
	}
	public List<LinerSchedule>  TestCrawling2() {

		List<LinerSchedule> scheduleData = new ArrayList<>();
		for(int i=0 ; i< 3 ; i ++) {
		
		scheduleData.add(new LinerSchedule("2","DJSC","202"+Integer.toString(i),"T2","T2","2021-02-03","2021-02-03","remark"));
		}
		return scheduleData;
	}
}
