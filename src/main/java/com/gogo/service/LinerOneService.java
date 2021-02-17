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
                    .data("SEL_MONTH", "02")
                    .data("pol_cd", "KBS")
                    .data("pod_cd", "JTK")
                    .data("searchGbn","1")
                    .data("cargoGbn","C")
                    .get();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		Elements elements = doc.select("a[href]");
		
		for(Element element : elements) {
			System.out.println(element.toString().substring(9,108));
		}
		

		return scheduleData;
	}
	
	public List<LinerSchedule> CheckingLiner2(String month , String pol, String pod) {
		String url = "http://korea.djship.co.kr/dj/ui/kr/eservice/sub3_1_0.jsp?PROGRAM_ID=sub3_1";
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
			
			js.executeScript("javascript:detail('','NBQ1','RG','1031','W','KRPUS','CNTAO','202102060700','','KBCT','','','N','Y')", element );
			
			//멘처음 부모 창으로 빠져나온다음
			//2번째 정보 프레임으로 들어가서 데이터를 읽어온다.
			driver.switchTo().defaultContent();
			Thread.sleep(1000);
			
			driver.switchTo().frame("jobFrame1");
			List<WebElement> info_details = driver.findElements(By.cssSelector("body"));
			
			for(WebElement info_detail : info_details) {
				System.out.println(info_detail.getText());
			}
			
			//System.out.println(driver.getPageSource());
			
		}catch(Throwable e) {
			e.printStackTrace();
			driver.quit();
		}
		
		
		
		//driver.close();
		
		return scheduleData;
	}
}
