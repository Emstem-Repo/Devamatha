package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.List;



public class CandidatePreferenceTO implements Serializable,Comparable<CandidatePreferenceTO> {
	private int id;
	private String coursId;
	private String coursName;
	private String progId;
	private String programtypeId;
	private int admApplnid;
	private int prefNo;
	private List<CourseTO> prefcourses;
	private List<ProgramTypeTO> prefProgramtypes;
	private List<ProgramTO> prefprograms;
	private Boolean isMandatory;
	private String category;
	private String dateAndTime;
	private String status;
	private String generalfees;
	private String castefees;
	private String fees;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public String getGeneralfees() {
		return generalfees;
	}
	public void setGeneralfees(String generalfees) {
		this.generalfees = generalfees;
	}
	public String getCastefees() {
		return castefees;
	}
	public void setCastefees(String castefees) {
		this.castefees = castefees;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public String getCoursId() {
		return coursId;
	}
	public void setCoursId(String coursId) {
		this.coursId = coursId;
	}
	public String getProgId() {
		return progId;
	}
	public void setProgId(String progId) {
		this.progId = progId;
	}
	public String getProgramtypeId() {
		return programtypeId;
	}
	public void setProgramtypeId(String programtypeId) {
		this.programtypeId = programtypeId;
	}
	public int getPrefNo() {
		return prefNo;
	}
	public void setPrefNo(int prefNo) {
		this.prefNo = prefNo;
	}
	public List<CourseTO> getPrefcourses() {
		return prefcourses;
	}
	public void setPrefcourses(List<CourseTO> prefcourses) {
		this.prefcourses = prefcourses;
	}
	public List<ProgramTypeTO> getPrefProgramtypes() {
		return prefProgramtypes;
	}
	public void setPrefProgramtypes(List<ProgramTypeTO> prefProgramtypes) {
		this.prefProgramtypes = prefProgramtypes;
	}
	public List<ProgramTO> getPrefprograms() {
		return prefprograms;
	}
	public void setPrefprograms(List<ProgramTO> prefprograms) {
		this.prefprograms = prefprograms;
	}
	public int getAdmApplnid() {
		return admApplnid;
	}
	public void setAdmApplnid(int admApplnid) {
		this.admApplnid = admApplnid;
	}
	public String getCoursName() {
		return coursName;
	}
	public void setCoursName(String coursName) {
		this.coursName = coursName;
	}
	@Override
	public int compareTo(CandidatePreferenceTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getPrefNo() > arg0.getPrefNo())
				return 1;
			else if(this.getPrefNo() < arg0.getPrefNo())
				return -1;
			else
				return 0;
		}
		return 0;
	}
	public Boolean getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
		
}
