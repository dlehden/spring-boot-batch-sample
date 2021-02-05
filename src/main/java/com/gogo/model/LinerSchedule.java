package com.gogo.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
//선사 스케줄 가지고기위한 model 
@Entity
@Table(name="liner_schedule")
@IdClass(LinerScheduleId.class)
public class LinerSchedule {
	@Id
	@Column(name="liner_code")
	private String linercode;
	@Id
	@Column(name="vessel_name")
	private String vesselname;
	private String pol;
	private String pod;
	private String etd;
	private String eta;
	private String remark;
	
	public LinerSchedule() {
		
	}
	
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
	public void setLinercode(String linercode) {
		this.linercode = linercode;
	}
	public String getVesselname() {
		return vesselname;
	}
	public void setVesselname(String vesselname) {
		this.vesselname = vesselname;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getPod() {
		return pod;
	}
	public void setPod(String pod) {
		this.pod = pod;
	}
	public String getEtd() {
		return etd;
	}
	public void setEtd(String etd) {
		this.etd = etd;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	 
 


}
