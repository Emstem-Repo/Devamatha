package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSubjectTimeTableTO implements Serializable,Comparable<ExamSubjectTimeTableTO>{
	private Integer id;
	private Integer subid;
	private String subjectName;
	private String subjectCode;
	private String startTimeHour;
	private String endTimeHour;
	private String startTimeMin;
	private String endTimeMin;
	private String date;
	private String sessionId;
	public ExamSubjectTimeTableTO() {
		super();
	}

	

	public ExamSubjectTimeTableTO(Integer id, Integer subid,
			String subjectName, String subjectCode, String startTimeHour,
			String endTimeHour, String startTimeMin, String endTimeMin,
			String date) {
		super();
		this.id = id;
		this.subid = subid;
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.startTimeHour = startTimeHour;
		this.endTimeHour = endTimeHour;
		this.startTimeMin = startTimeMin;
		this.endTimeMin = endTimeMin;
		this.date = date;
	}



	public int getSubid() {
		return subid;
	}

	public void setSubid(Integer subid) {
		this.subid = subid;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getStartTimeHour() {
		return startTimeHour;
	}

	public void setStartTimeHour(String startTimeHour) {
		this.startTimeHour = startTimeHour;
	}

	public String getEndTimeHour() {
		return endTimeHour;
	}

	public void setEndTimeHour(String endTimeHour) {
		this.endTimeHour = endTimeHour;
	}

	public String getStartTimeMin() {
		return startTimeMin;
	}

	public void setStartTimeMin(String startTimeMin) {
		this.startTimeMin = startTimeMin;
	}

	public String getEndTimeMin() {
		return endTimeMin;
	}

	public void setEndTimeMin(String endTimeMin) {
		this.endTimeMin = endTimeMin;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	@Override
	public int compareTo(ExamSubjectTimeTableTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null
				 && this.getSubjectName()!=null){
			return this.getSubjectName().compareTo(arg0.getSubjectName());
		}else
		return 0;
	}



	public String getSessionId() {
		return sessionId;
	}



	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}