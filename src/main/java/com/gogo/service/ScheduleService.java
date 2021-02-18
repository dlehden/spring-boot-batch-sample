package com.gogo.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Service;

import com.mert.model.Calendar;

@Service("ScheduleService")
public class ScheduleService {
	
	public  List<Calendar> SINO_SCHEDULE() throws Exception{
		   List<Calendar> events = new ArrayList<Calendar>();
		   Calendar event = new Calendar();
		try {
			Connection.Response SINO_REQ =Jsoup.connect("http://sinotrans.co.kr/eService/es_schedule01.asp?tid=100&sid=1")
					  // .ignoreContentType(true).ignoreHttpErrors(true)      
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
			            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
			            .header("accept-encoding", "gzip, deflate")
			            .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
			            .header("Connection", "keep-alive")
			            .header("Host", "sinotrans.co.kr")
			            .header("Content-Type", "application/x-www-form-urlencoded")
			            .header("Cookie", "_ga=GA1.3.170234306.1594252126; _gid=GA1.3.1720704228.1604880385; ASPSESSIONIDASCQAAAT=FGONKENAIGNFPBDKIFCNNBEJ; referURL=%252FeService%252Fes%255Fschedule01%252Easp%253Ftid%253D100%2526sid%253D1%2526POL01%253DKR%2526POL02%253DPUS%2526POD01%253DCN%2526POD02%253DSHA%2526sYear%253D2020%2526sMonth%253D11%2526dOption%253Dout")
			            .header("Referer", "http://sinotrans.co.kr/eService/es_schedule01.asp?tid=100&sid=1")
			            .data("POL01","KR")
			            .data("POL02","PUS")
			            .data("POD01","CN")
			            .data("POD02","NKG")
			            .data("sYear","2020")
			            .data("sMonth","12")
			            .data("dOption","out")
			            .method(Connection.Method.GET)
			            .execute();
				Document SINO_DOC = SINO_REQ.parse();
			//선명항차 추출
				Elements link_function_value = SINO_DOC.select("font"); //width 에 선명항차 가지고옴
				Map<String,String>VSL_NM_MATCH = new HashMap<>();
				for(Element function_value : link_function_value) {
					if(function_value.toString().contains("onmouseover")) {
						VSL_NM_MATCH.put(function_value.text(),function_value.toString().substring(12,22));
					}
					
				}
			Elements link_sino = SINO_DOC.select("td[width]"); //width 에 선명항차 가지고옴
			String VSL_NM = "";
			String ETD = "";
			String ETA = "";
			String DOC_CLOSE = "";
			String CNTR_CLOSE = "";
			String ALL_CLOSE = "";
			String SINO_DESC = "";
			int i= 0 ;
				for(Element element : link_sino) {
					
					event = new Calendar();
					if(element.toString().contains("188")) {  //vsl_nm
						if(!element.text().equals("Vessel/Voyage")){
							//System.out.println(element.text());
							VSL_NM = element.text();
						}
					}
					if(element.toString().contains("158")) {  //vsl_nm
						if(element.text().length()!=2){
							ETD  = element.text().substring(element.text().length()-16,element.text().length()-6).trim();
						}
					}
				if(element.toString().contains("159")) {  //ETA
						if(element.text().length()!=2){
							ETA  = element.text();//.substring(element.text().length()-16,element.text().length());
						}
					}
					if(element.toString().contains("119")) {
						if(!element.toString().contains("#CCCCCC")) {	
							if(i==2) {
								Connection.Response closing  =Jsoup.connect("http://www.sinotrans.co.kr/eservice/es_schedule01_detail.asp")
										  // .ignoreContentType(true).ignoreHttpErrors(true)      
									.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
								            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
								            .header("accept-encoding", "gzip, deflate")
								            .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
								            .header("Connection", "keep-alive")
								            .header("Host", "sinotrans.co.kr")
								            .header("Content-Type", "application/x-www-form-urlencoded")
								            .header("Cookie", "_ga=GA1.3.170234306.1594252126; _gid=GA1.3.1720704228.1604880385; ASPSESSIONIDASCQAAAT=FGONKENAIGNFPBDKIFCNNBEJ; referURL=%252FeService%252Fes%255Fschedule01%252Easp%253Ftid%253D100%2526sid%253D1%2526POL01%253DKR%2526POL02%253DPUS%2526POD01%253DCN%2526POD02%253DSHA%2526sYear%253D2020%2526sMonth%253D11%2526dOption%253Dout")
								            .header("Referer", "http://sinotrans.co.kr/eService/es_schedule01.asp?tid=100&sid=1")
								            .data("VSL",VSL_NM_MATCH.get(VSL_NM).substring(0,4))
								            .data("VOY",VSL_NM_MATCH.get(VSL_NM).substring(4,9))
								            .data("SEQ",VSL_NM_MATCH.get(VSL_NM).substring(9,10))
								            .method(Connection.Method.GET)
								            .execute();

								Document CLOSING_DOC = closing.parse();
								
								Elements close = CLOSING_DOC.select("td[width]"); //width 에 선명항차 가지고옴
								 int j=0;
									for(Element element2 : close) {
										if(element2.toString().contains("195")) {
											if(element2.toString().contains("Booking 마감된 선박입니다.")) {
												ALL_CLOSE = "마감";
											}else {
												ALL_CLOSE = "BOOKING";
											}
											break;
										}
									}
								 
									for(Element element2 : close) {

										if(element2.toString().contains("124")) {
											if(j==0) {
												DOC_CLOSE = element2.text().substring(0,22);
												CNTR_CLOSE = element2.text().substring(22,53);
												//System.out.println(element2.text());
												//System.out.println(element2.text().substring(22,53));
												j++;
											}else {
												break;
											}
											
										}
									}
								SINO_DESC = SINO_DESC +"선명: " + VSL_NM.substring(0,VSL_NM.indexOf("/")) +"<br>";
								SINO_DESC = SINO_DESC +DOC_CLOSE+"<br>";
								SINO_DESC = SINO_DESC +CNTR_CLOSE+"<br>";
								SINO_DESC = SINO_DESC +ETA + "<br>";
								SINO_DESC = SINO_DESC +"마감여부 : " + ALL_CLOSE;
								
								event.setTitle("(SINO)"+VSL_NM.substring(VSL_NM.lastIndexOf("-")));
								event.setStart(ETD);	
								event.setDescription(SINO_DESC);
								event.setLine("SINO");
								//System.out.println(SINO_DESC);
								 events.add(event);
							
								SINO_DESC="";
								i=0;
						    }else {
						    	i++;
						    }
						}
					}
				}
				
		}catch(Exception e) {
			
		}

		return events;
	}
	
	
	
	public  List<Calendar> PAN_SCHEDULE() throws Exception{
		   List<Calendar> events = new ArrayList<Calendar>();
		   Calendar event = new Calendar();
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
					   //System.out.println(KEY);
					   event = new Calendar();
					   event.setTitle("(PAN)-"+PAN_COMMA[1].trim()+PAN_COMMA[2].trim());
					   event.setStart(PAN_COMMA[50].trim().substring(0,10).replace("/", "-"));
					   event.setDescription(PAN_DESC);
					   event.setLine("PAN");
					   events.add(event);
					   PAN_DESC = "";
					   KEY="";
				   }
			   }
				
		}catch(Exception e) {
			
		}

		return events;
	}

	
	
	public  List<Calendar> KMTC_SCHEDULE() throws Exception{
		   List<Calendar> events = new ArrayList<Calendar>();
		   Calendar event = new Calendar();
		try {
			//고려해운
			//메인에 ifrmae 단에 소스를 들어가기위해  셀리니움으로 추출 그후 소스를 jsoup 으로
			//묶어서 특정 링크에 데이터만 추출 .
//			JavascriptExecutor js = (JavascriptExecutor)driver2;
//			driver2.get(KMTC_URL);
//			js.executeScript("fnSearch('1')");
////           try { 
////				
////				Thread.sleep(30000);
////			}catch(Exception e) {
////				
////			}
//			driver2.switchTo().frame("searchLegScheduleFrame");
//			//System.out.println(driver2.findElement(By.xpath("//*[@id=\"calendar\"]/div")).getText()); //calender div 안에 text 뿌림
//			
//			String html_content = driver2.getPageSource();
//			Document doc1 = Jsoup.parse(html_content);
//			Elements link = doc1.select("a[href*=#shiptable]");
//			for(Element element : link) {
//				System.out.println(element);
//			}
			
			//Elements elm = doc1.getAllElements();
//			for(Element element : elm) {
//				
//				System.out.println(element);
//				
//			}
			
			Connection.Response auth =Jsoup.connect("http://www.ekmtc.com/VOSD100/searchLegScheduleFrame1.do")
			  // .ignoreContentType(true).ignoreHttpErrors(true)      
		.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
	            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
	            .header("accept-encoding", "gzip, deflate")
	            .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
	            .header("Connection", "keep-alive")
	            .header("Host", "www.ekmtc.com")
	            .header("Content-Type", "application/x-www-form-urlencoded")
	            .header("Origin", "http://www.ekmtc.com")
	            .header("Cookie", "WMONID=Ms0i4giL-jo; _ga=GA1.2.1048947876.1603963299; hiddestPlcCd=HKG; KMTC=AXG5BAIxbdJ/knRxfHbtSw$$; JSESSIONID=0001kwEjOEvw0CwObuwLx3QlgqX:196f1b0gl; _gid=GA1.2.1633503113.1605492823; hidstartPlcCd=PUS")
	            .header("Referer", "http://www.ekmtc.com/VOSD100/searchLegScheduleForm.do")
	            .data("SearchYN","Y")
	            .data("filterYN","N")
	            .data("polTrmlStr","N")
	            .data("podTrmlStr","N")
	            .data("filterDirect","N")
	            .data("filterTS","N")
	            .data("filterTranMin","0")
	            .data("filterTranMax","0")
	            .data("startCtrCd","KR")
	            .data("startPlcCd","PUS")
	            .data("destCtrCd","HK")
	            .data("destPlcCd","HKG")
	            .data("searchYear","2020")
	            .data("searchMonth","12")
	            .data("bound","O")
	            
	            
	            .method(Connection.Method.POST)
	            .execute();
	   Document document = auth.parse();
	   String  ALL_DATA = "";
	   String ETD = "";
	   
		Elements link = document.select("div.Board_line_eservice").select("table tbody tr td");
		int chk_vslnm = 0;
		event = new Calendar();
		HashMap<String, ArrayList<String>> KMTC_MAP_DT =  new HashMap<String, ArrayList<String>>();
		ArrayList<String>KMTC_DT= new ArrayList<String>();
		String VSL_NM = "";
		String VSL_ALL_NM= "";
		String KMTC_DESC = "";
		for(Element element : link) {
			ALL_DATA = element.text().toString();
			
			//System.out.println(ALL_DATA +"          "+chk_vslnm);
			
			if(chk_vslnm==0) {
				//System.out.println("VSLNM : " + ALL_DATA);
				  event.setTitle("(KMTC)-"+ALL_DATA.substring(ALL_DATA.length()-5));
				  VSL_NM = ALL_DATA.trim().substring(0,ALL_DATA.length()-5);
				  VSL_ALL_NM = ALL_DATA.trim();//KEY값
				  KMTC_DT.add(VSL_NM);
				  
			}
			
			if(chk_vslnm==1) {
				ETD = ALL_DATA.substring(ALL_DATA.indexOf("ETD"), ALL_DATA.length()).substring(4,14).replaceAll( "\\.", "-" );
				//System.out.println(ETD);
				//System.out.println(ETD.replaceAll(".", "-"));
				 //System.out.println(ALL_DATA.substring(ALL_DATA.indexOf("ETD"), ALL_DATA.length()));
				 event.setStart(ETD);
			}
			
			if(chk_vslnm==3) {
				KMTC_DT.add(ALL_DATA.substring(ALL_DATA.indexOf("ETA"),ALL_DATA.length()).replaceAll( "\\.", "-" ));
				//System.out.println(ALL_DATA +"          "+chk_vslnm);
				// System.out.println(ALL_DATA.substring(ALL_DATA.indexOf("ETA"),ALL_DATA.length()));
			}
			if(chk_vslnm==5) {
				KMTC_DT.add(ALL_DATA);
				 //System.out.println("서류마감 " + ALL_DATA);
			}
			if(chk_vslnm==6) {
				KMTC_DT.add(ALL_DATA);
				// System.out.println("컨테이너마감 " + ALL_DATA);
			}
			if(chk_vslnm==7) {
				//System.out.println("CLOSE " + ALL_DATA);
			    	KMTC_DT.add(ALL_DATA);

				  KMTC_MAP_DT.put(VSL_ALL_NM, KMTC_DT);
				   KMTC_DESC = KMTC_DESC +"선명 : " +KMTC_MAP_DT.get(VSL_ALL_NM).get(0) +"<br>";
				   KMTC_DESC = KMTC_DESC + "서류마감 : " +KMTC_MAP_DT.get(VSL_ALL_NM).get(2).trim().replaceAll( "\\.", "-" ) +"<br>";
				   KMTC_DESC = KMTC_DESC + "Container마감 : " + KMTC_MAP_DT.get(VSL_ALL_NM).get(3).trim().replaceAll( "\\.", "-" ).replaceAll( " : ", ":" )+"<br>";
				   KMTC_DESC = KMTC_DESC + KMTC_MAP_DT.get(VSL_ALL_NM).get(1).trim().replaceAll( "\\.", "-" )+"<br>";//ETA
				   KMTC_DESC = KMTC_DESC + "마감 : " +(KMTC_MAP_DT.get(VSL_ALL_NM).get(4).equals("마감")?"마감":"BOOKING");
				   	  event.setDescription(KMTC_DESC);
					  event.setLine("KMTC");
					  events.add(event);
					  KMTC_DESC = "";
					  
				  KMTC_DT = new ArrayList<String>();
				  KMTC_MAP_DT =  new HashMap<String, ArrayList<String>>();
				  event = new Calendar();
			}
			chk_vslnm++;
			if(chk_vslnm==8) {
				chk_vslnm =0;
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
	
	

}
