package com.kp.cms.to.exam;

import java.io.Serializable;

public class ValuationStatisticsTO implements Serializable {
	
	private int id;
	private int totalSubjects;
	private int valuationNotStarted;
	private int valuationInProcess;
	private int valuationCompleted;
	
	private String deaneryName;
	private String departmentName;
	private int streamId;
	private int departmentId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalSubjects() {
		return totalSubjects;
	}
	public void setTotalSubjects(int totalSubjects) {
		this.totalSubjects = totalSubjects;
	}
	public int getValuationNotStarted() {
		return valuationNotStarted;
	}
	public void setValuationNotStarted(int valuationNotStarted) {
		this.valuationNotStarted = valuationNotStarted;
	}
	public int getValuationInProcess() {
		return valuationInProcess;
	}
	public void setValuationInProcess(int valuationInProcess) {
		this.valuationInProcess = valuationInProcess;
	}
	public int getValuationCompleted() {
		return valuationCompleted;
	}
	public void setValuationCompleted(int valuationCompleted) {
		this.valuationCompleted = valuationCompleted;
	}
	public String getDeaneryName() {
		return deaneryName;
	}
	public void setDeaneryName(String deaneryName) {
		this.deaneryName = deaneryName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getStreamId() {
		return streamId;
	}
	public void setStreamId(int streamId) {
		this.streamId = streamId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
}