package com.kp.cms.to.phd;





public class PhdResearchDescriptionTO implements Comparable<PhdResearchDescriptionTO>{
	
	private Integer id;
	private String researchPublication;
	private String issn;
	private String issueNumber;
	private String level;
	private String nameJournal;
	private String title;
	private String datePhd;
	private String volumeNo;
	private String description;
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResearchPublication() {
		return researchPublication;
	}
	public void setResearchPublication(String researchPublication) {
		this.researchPublication = researchPublication;
	}
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getNameJournal() {
		return nameJournal;
	}
	public void setNameJournal(String nameJournal) {
		this.nameJournal = nameJournal;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDatePhd() {
		return datePhd;
	}
	public void setDatePhd(String datePhd) {
		this.datePhd = datePhd;
	}
	public String getVolumeNo() {
		return volumeNo;
	}
	public void setVolumeNo(String volumeNo) {
		this.volumeNo = volumeNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(PhdResearchDescriptionTO arg0) {
		if(arg0 instanceof PhdResearchDescriptionTO && arg0.getId()>0 ){
			return this.getId().compareTo(arg0.id);
	}else
		return 0;
}

	
}
