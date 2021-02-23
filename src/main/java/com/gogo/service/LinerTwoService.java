package com.gogo.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection;
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
public class LinerTwoService {  //선사 1번 크롤링 
	
	public  List<LinerSchedule> PAN_SCHEDULE() {
		   List<LinerSchedule> events = new ArrayList<LinerSchedule>();
		   LinerSchedule event = new LinerSchedule();
		try {
			setSSL(); //SSL 보안 이슈 오류시 추가 하단에 class 만들어놈
			Connection.Response PAN_REQ =Jsoup.connect("https://container.panocean.com/HP2101/hp2101Result.stx?layoutType=&")
					  // .ignoreContentType(true).ignoreHttpErrors(true)      
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
			            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
			            .header("accept-encoding", "gzip, deflate, br")
			            .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
			            .header("Connection", "keep-alive")
			            .header("Host", "container.panocean.com")
			            .header("Content-Type", "application/x-www-form-urlencoded")
			            .header("Cookie", "JSESSIONID=0D4F2971FB86682B4A4865D09EC81BA5; menuHistory5=https%3A%2F%2Fcontainer.panocean.com%2FHP2101%2Fhp2101.stx%7C%EA%B5%AC%EA%B0%84%EB%B3%84%20%EC%8A%A4%EC%BC%80%EC%A4%84")
			            .header("Referer", "https://container.panocean.com/HP2101/hp2101.stx")
			            .data("originCode","KRPUS")
			            .data("destinCode","VNHPH")
			            .data("year","2020")
			            .data("month","12")
			            .data("scheduleFlag","TD")
			            .data("searchFlag","M")
			            .method(Connection.Method.GET)
			            .execute();
			   Document PAN_DOC = PAN_REQ.parse();
			 //  System.out.println(PAN_DOC.toString());
			   Elements PAN_ELE2 = PAN_DOC.select("table[id=jqTblShipList]").select("td");
			   int PAN_CHK = 0;
			   String PAN_DT_KEY = "";
			   HashMap<String, ArrayList<String>> PAN_MAP_DT =  new HashMap<String, ArrayList<String>>();
			   ArrayList<String>PAN_DT_TIME = new ArrayList<String>();
			   for(Element PAN_VALUE : PAN_ELE2) {
				   if(PAN_CHK==1) {
					   PAN_DT_KEY= PAN_VALUE.text().trim().replace("<br>", "").replace(" ", ""); //VSL_NM 
				   }
				   if(PAN_CHK==5) {
					  PAN_DT_TIME.add(PAN_VALUE.text()); //DOC CLOSE
				   }
				   if(PAN_CHK==6) {
					   PAN_DT_TIME.add(PAN_VALUE.text()); // CNTR CLOSE
				   }
				   if(PAN_CHK == 9 ) {
					   PAN_MAP_DT.put(PAN_DT_KEY, PAN_DT_TIME);
					   PAN_DT_TIME = new ArrayList<String>();
					   PAN_CHK = 0;
				   }
				   
				   else {
					   PAN_CHK++;
				   }
			   }
				  // System.out.println(PAN_MAP_DT.get("DONGJINVENUS044W").get(0));
			   Elements PAN_ELE = PAN_DOC.select("tr>td[id=weekOfDayVsl]");
			   String PAN_DATA = null;
			   String KEY = "";
			   String PAN_DESC = "";
			   for(Element PAN_VALUE : PAN_ELE) {
				   
				   Elements PAN_HREF  = PAN_VALUE.select("a[href]");
				   for(Element PAN_VALUE2 : PAN_HREF) {
					   //System.out.println(PAN_VALUE2.attr("href").trim().toString());
					   PAN_DATA = PAN_VALUE2.attr("href").trim().toString();
					   String[] PAN_COMMA = PAN_DATA.replace("javascript:fnSrCyInfo(", "").replace(");", "").replace("'", "").split(",");
					   
					  // System.out.println(PAN_COMMA[1].trim()+PAN_COMMA[2].trim()+PAN_COMMA[5].trim()+PAN_COMMA[50].trim().substring(0,10)+PAN_COMMA[51].trim());
					   //for(int i=0 ; i < PAN_COMMA.length; i++) {
						//   System.out.println(PAN_COMMA[i].trim());
					  // }
					   KEY = PAN_COMMA[5]+PAN_COMMA[1]+PAN_COMMA[2];
					   KEY = KEY.replace(" ", "").trim();
					   PAN_DESC = PAN_DESC +"선명 : " +  PAN_COMMA[5] +"<br>";
					   PAN_DESC = PAN_DESC + "서류마감 : " +PAN_MAP_DT.get(KEY).get(0).replace("/", "-") +"<br>";
					   PAN_DESC = PAN_DESC + "Container 마감 : " + PAN_MAP_DT.get(KEY).get(1).replace("/", "-")+"<br>";
					   PAN_DESC = PAN_DESC + "ETA : " +PAN_COMMA[51].trim().replace("/", "-")+"<br>";
					   PAN_DESC = PAN_DESC + "마감 : " +(PAN_COMMA[54].equals("Y")?"마감":"BOOKING");
					   System.out.println(KEY);
					   event = new LinerSchedule();
					   event.setLinercode("PAN");
					   event.setVesselname(KEY);
					   event.setMonth("02");
					   event.setEtd(PAN_COMMA[50].trim().substring(0,10).replace("/", "-"));
					   event.setEta(PAN_COMMA[51].trim().replace("/", "-"));
					   event.setPol("KRPUS");
					   event.setPod("TBTBK");
					   event.setRemark("");
//					   event.setTitle("(PAN)-"+PAN_COMMA[1].trim()+PAN_COMMA[2].trim());
//					   event.setStart(PAN_COMMA[50].trim().substring(0,10).replace("/", "-"));
//					   event.setDescription(PAN_DESC);
//					   event.setLine("PAN");
					   events.add(event);
					   PAN_DESC = "";
					   KEY="";
				   }
			   }
				
		}catch(Exception e) {
			
		}

		return events;
	}


	   public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }

				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
	            
	        } };
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                @Override
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    }
		public List<LinerSchedule>  TestCrawling2() {

			List<LinerSchedule> scheduleData = new ArrayList<>();
			for(int i=0 ; i< 3 ; i ++) {
			
			scheduleData.add(new LinerSchedule("2","DJSC","202"+Integer.toString(i),"T2","T2","2021-02-03","2021-02-03","remark"));
			}
			return scheduleData;
		}
}
