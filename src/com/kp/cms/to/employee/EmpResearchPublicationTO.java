package com.kp.cms.to.employee;

public class EmpResearchPublicationTO {
	private int id;
	private String paperTitle;
	private String journalName;
	private String year;
	private String ISBNISSNNo;
	private String UGCNonUGC;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaperTitle() {
		return paperTitle;
	}
	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getISBNISSNNo() {
		return ISBNISSNNo;
	}
	public void setISBNISSNNo(String iSBNISSNNo) {
		ISBNISSNNo = iSBNISSNNo;
	}
	public String getUGCNonUGC() {
		return UGCNonUGC;
	}
	public void setUGCNonUGC(String uGCNonUGC) {
		UGCNonUGC = uGCNonUGC;
	}
}
