package com.gogo.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LinerScheduleId implements Serializable {
	String month;
	String linercode;
	String vesselname;

}
