package com.kp.cms.to.admin;

public class SyllabusEntryTo implements Comparable<SyllabusEntryTo>{
	private int id;
	private String subjectName;
	private String status;
	private int subjId;
	private String semester;
	private String subjectCode;
	private String display;
	private String checked;
	private String rejectReason;
	
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getSubjId() {
		return subjId;
	}

	public void setSubjId(int subjId) {
		this.subjId = subjId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int compareTo(SyllabusEntryTo arg0) {
		if(arg0 instanceof SyllabusEntryTo && arg0.getSemester()!=null ){
			return this.getSemester().compareTo(arg0.getSemester());
	}else
		return 0;
	}
}
