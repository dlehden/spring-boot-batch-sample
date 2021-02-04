package com.gogo.model;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;

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
	
	//builder 패턴 적용
	static public class Builder{
		private  String linercode;
		private  String vesselname;
		private  String pol;
		private  String pod;
		private  String etd;
		private  String eta;
		private  String remark;
		
		public Builder(LinerSchedule linerschedule) {
			this.vesselname = linerschedule.vesselname;
			this.linercode = linerschedule.linercode;
			this.pol = linerschedule.pol;
			this.pod = linerschedule.pod;
			this.etd = linerschedule.etd;
			this.eta = linerschedule.eta;
			this.remark = linerschedule.remark;
		}
		
		public Builder vesselname(String vesselname) {
			this.vesselname = vesselname;
			return this;
		}
		public Builder linercode(String linercode) {
			this.linercode = linercode;
			return this;
		}
		public Builder pol(String pol) {
			this.pol = pol;
			return this;
		}
		public Builder pod(String pod) {
			this.pod = pod;
			return this;
		}
		public Builder etd(String etd) {
			this.etd = etd;
			return this;
		}
		public Builder eta(String eta) {
			this.eta = eta;
			return this;
		}
		public Builder remark(String remark) {
			this.remark = remark;
			return this;
		}
		
		public LinerSchedule build() {
			return new LinerSchedule(linercode,vesselname,pol,pod,etd,eta,remark);
		}
	}
	
	

}
