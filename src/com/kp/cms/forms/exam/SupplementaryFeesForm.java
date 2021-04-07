package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.exam.InternalRedoFeesTO;
import com.kp.cms.to.exam.RegularExamFeesTo;
import com.kp.cms.to.exam.RevaluationExamFeesTo;
import com.kp.cms.to.exam.SupplementaryFeesTo;

public class SupplementaryFeesForm extends BaseActionForm {
	
	private String[] programIds;
	private String theoryFees;
	private String practicalFees;
	private List<ProgramTO> programList;
	private int id;
	private String mode;
	private List<SupplementaryFeesTo> toList;
	//basim
	private String improvementTheoryFees;
	private String improvementPracticalFees;
	private String applicationFees;
	private String cvCampFees;
	private String marksListFees;
	private String onlineServiceChargeFees;
	List<ProgramTypeTO> programTypeList;
	private String programTypeId;
	private String[] selectedCourse;
	private Map<Integer,String> courseMap;
	private List<RegularExamFeesTo> regularExamToList;
	private List<ReligionSectionTO> religionSectionList;
	private String sectionId;
	private String[] selectedClass;
	private String examFees;
	private String paperFees;
	private String registerationFees;
	
	private List<RevaluationExamFeesTo> revaluationExamToList;
	private String marksCopyFees;
	private String revaluationFees;
	private String scrutinyFees;
	private String onlineSevriceFees;
	private String challengeValuationFees;
	
	private List<InternalRedoFeesTO> internalRedoTOList;
	
	public String[] getProgramIds() {
		return programIds;
	}
	public void setProgramIds(String[] programIds) {
		this.programIds = programIds;
	}
	public String getTheoryFees() {
		return theoryFees;
	}
	public void setTheoryFees(String theoryFees) {
		this.theoryFees = theoryFees;
	}
	public String getPracticalFees() {
		return practicalFees;
	}
	public void setPracticalFees(String practicalFees) {
		this.practicalFees = practicalFees;
	}
	
	public List<ProgramTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramTO> programList) {
		this.programList = programList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<SupplementaryFeesTo> getToList() {
		return toList;
	}
	public void setToList(List<SupplementaryFeesTo> toList) {
		this.toList = toList;
	}
	
	public String getImprovementTheoryFees() {
		return improvementTheoryFees;
	}
	public void setImprovementTheoryFees(String improvementTheoryFees) {
		this.improvementTheoryFees = improvementTheoryFees;
	}
	public String getImprovementPracticalFees() {
		return improvementPracticalFees;
	}
	public void setImprovementPracticalFees(String improvementPracticalFees) {
		this.improvementPracticalFees = improvementPracticalFees;
	}
	public String getApplicationFees() {
		return applicationFees;
	}
	public void setApplicationFees(String applicationFees) {
		this.applicationFees = applicationFees;
	}
	public String getCvCampFees() {
		return cvCampFees;
	}
	public void setCvCampFees(String cvCampFees) {
		this.cvCampFees = cvCampFees;
	}
	public String getMarksListFees() {
		return marksListFees;
	}
	public void setMarksListFees(String marksListFees) {
		this.marksListFees = marksListFees;
	}
	public String getOnlineServiceChargeFees() {
		return onlineServiceChargeFees;
	}
	public void setOnlineServiceChargeFees(String onlineServiceChargeFees) {
		this.onlineServiceChargeFees = onlineServiceChargeFees;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String[] getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(String[] selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public List<RegularExamFeesTo> getRegularExamToList() {
		return regularExamToList;
	}
	public void setRegularExamToList(List<RegularExamFeesTo> regularExamToList) {
		this.regularExamToList = regularExamToList;
	}
	
	public List<ReligionSectionTO> getReligionSectionList() {
		return religionSectionList;
	}
	public void setReligionSectionList(List<ReligionSectionTO> religionSectionList) {
		this.religionSectionList = religionSectionList;
	}
	
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String[] getSelectedClass() {
		return selectedClass;
	}
	public void setSelectedClass(String[] selectedClass) {
		this.selectedClass = selectedClass;
	}
	
	public String getExamFees() {
		return examFees;
	}
	public void setExamFees(String examFees) {
		this.examFees = examFees;
	}
	
	public String getPaperFees() {
		return paperFees;
	}
	public void setPaperFees(String paperFees) {
		this.paperFees = paperFees;
	}
	public String getRegisterationFees() {
		return registerationFees;
	}
	public void setRegisterationFees(String registerationFees) {
		this.registerationFees = registerationFees;
	}
	/**
	 * 
	 */
	public void resetFields(){
		this.programIds=null;
		this.theoryFees=null;
		this.practicalFees=null;
		this.cvCampFees=null;
		this.applicationFees=null;
		this.improvementPracticalFees=null;
		this.improvementTheoryFees=null;
		this.marksListFees=null;
		this.onlineServiceChargeFees=null;
		this.selectedCourse=null;
		this.examFees=null;
		this.registerationFees=null;
		this.paperFees=null;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public List<RevaluationExamFeesTo> getRevaluationExamToList() {
		return revaluationExamToList;
	}
	public void setRevaluationExamToList(
			List<RevaluationExamFeesTo> revaluationExamToList) {
		this.revaluationExamToList = revaluationExamToList;
	}
	public String getMarksCopyFees() {
		return marksCopyFees;
	}
	public void setMarksCopyFees(String marksCopyFees) {
		this.marksCopyFees = marksCopyFees;
	}
	public String getRevaluationFees() {
		return revaluationFees;
	}
	public void setRevaluationFees(String revaluationFees) {
		this.revaluationFees = revaluationFees;
	}
	public String getScrutinyFees() {
		return scrutinyFees;
	}
	public void setScrutinyFees(String scrutinyFees) {
		this.scrutinyFees = scrutinyFees;
	}
	public String getOnlineSevriceFees() {
		return onlineSevriceFees;
	}
	public void setOnlineSevriceFees(String onlineSevriceFees) {
		this.onlineSevriceFees = onlineSevriceFees;
	}
	public String getChallengeValuationFees() {
		return challengeValuationFees;
	}
	public void setChallengeValuationFees(String challengeValuationFees) {
		this.challengeValuationFees = challengeValuationFees;
	}
	public List<InternalRedoFeesTO> getInternalRedoTOList() {
		return internalRedoTOList;
	}
	public void setInternalRedoTOList(List<InternalRedoFeesTO> internalRedoTOList) {
		this.internalRedoTOList = internalRedoTOList;
	}
}
