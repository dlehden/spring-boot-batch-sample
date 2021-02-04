package com.gogo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
//선사 스케줄 가지고기위한 model 
@Entity
public class LinerSchedule {
	@Id
	private final String linercode;
	private final String vesselname;
	private final String pol;
	private final String pod;
	private final String etd;
	private final String eta;
	private final String remark;
	 
 
	
	public LinerSchedule(String linercode,String vesselname,String pol, String pod, String etd, String eta, String remark) {
		this.linercode = linercode;
		this.vesselname = vesselname;
		this.pol = pol;
		this.pod = pod;
		this.etd = etd;
		this.eta = eta;
		this.remark = remark;
	}



	public String getLinercode() {
		return linercode;
	}



	public String getVesselname() {
		return vesselname;
	}



	public String getPol() {
		return pol;
	}



	public String getPod() {
		return pod;
	}



	public String getEtd() {
		return etd;
	}



	public String getEta() {
		return eta;
	}



	public String getRemark() {
		return remark;
	}

	

}
