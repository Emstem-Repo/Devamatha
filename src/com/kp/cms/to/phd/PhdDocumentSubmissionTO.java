package com.kp.cms.to.phd;

import java.io.Serializable;

public class PhdDocumentSubmissionTO implements Serializable{
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	
	private int id;
	private String registerNo;
	private String studentId;
	private String studentName;
	private String courseName;
	private String courseId;
	private String batch;
	private String guide;
	private String guideId;
	private String guideEmpaneNo;
	private String coGuide;
	private String coGuideId;
	private String coGuideEmpaneNo;
	private String signedOn;
	private String tempChecked;
	private String checked;
	private String tempChecked1;
	private String checked1;
	private String tempChecked2;
	private String checked2;
	private String vivaDate;
	private String vivaTitle;
	private String vivaDiscipline;
	private String researchDescriptionLength;
	private String researchPublication;
	private String synopsis;
	private String finalViva;
	private boolean guideDisplay;
	private boolean coGuideDisplay;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getGuideEmpaneNo() {
		return guideEmpaneNo;
	}
	public void setGuideEmpaneNo(String guideEmpaneNo) {
		this.guideEmpaneNo = guideEmpaneNo;
	}
	public String getCoGuide() {
		return coGuide;
	}
	public void setCoGuide(String coGuide) {
		this.coGuide = coGuide;
	}
	public String getCoGuideEmpaneNo() {
		return coGuideEmpaneNo;
	}
	public void setCoGuideEmpaneNo(String coGuideEmpaneNo) {
		this.coGuideEmpaneNo = coGuideEmpaneNo;
	}
	public void setSignedOn(String signedOn) {
		this.signedOn = signedOn;
	}
	public String getSignedOn() {
		return signedOn;
	}
	public String getGuideId() {
		return guideId;
	}
	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}
	public String getCoGuideId() {
		return coGuideId;
	}
	public void setCoGuideId(String coGuideId) {
		this.coGuideId = coGuideId;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked1() {
		return tempChecked1;
	}
	public void setTempChecked1(String tempChecked1) {
		this.tempChecked1 = tempChecked1;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public String getTempChecked2() {
		return tempChecked2;
	}
	public void setTempChecked2(String tempChecked2) {
		this.tempChecked2 = tempChecked2;
	}
	public String getChecked2() {
		return checked2;
	}
	public void setChecked2(String checked2) {
		this.checked2 = checked2;
	}
	public String getVivaDate() {
		return vivaDate;
	}
	public void setVivaDate(String vivaDate) {
		this.vivaDate = vivaDate;
	}
	public String getVivaTitle() {
		return vivaTitle;
	}
	public void setVivaTitle(String vivaTitle) {
		this.vivaTitle = vivaTitle;
	}
	public String getVivaDiscipline() {
		return vivaDiscipline;
	}
	public void setVivaDiscipline(String vivaDiscipline) {
		this.vivaDiscipline = vivaDiscipline;
	}
	public String getResearchDescriptionLength() {
		return researchDescriptionLength;
	}
	public void setResearchDescriptionLength(String researchDescriptionLength) {
		this.researchDescriptionLength = researchDescriptionLength;
	}
	public String getResearchPublication() {
		return researchPublication;
	}
	public void setResearchPublication(String researchPublication) {
		this.researchPublication = researchPublication;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getFinalViva() {
		return finalViva;
	}
	public void setFinalViva(String finalViva) {
		this.finalViva = finalViva;
	}
	public boolean isGuideDisplay() {
		return guideDisplay;
	}
	public void setGuideDisplay(boolean guideDisplay) {
		this.guideDisplay = guideDisplay;
	}
	public boolean isCoGuideDisplay() {
		return coGuideDisplay;
	}
	public void setCoGuideDisplay(boolean coGuideDisplay) {
		this.coGuideDisplay = coGuideDisplay;
	}
	
}
