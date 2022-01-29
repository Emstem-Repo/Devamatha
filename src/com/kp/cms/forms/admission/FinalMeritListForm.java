package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.FinalMeritListMailTO;
import com.kp.cms.to.admission.FinalMeritListSearchTo;

public class FinalMeritListForm extends BaseActionForm {

	private String courseId;

	private String casteCategoryId;
	
	private String subReligionId;
	
	private String nationalityId;
	
	private String residentCategoryId;
	
	private Character belongsTo;
	
	private String gender;
		
	private String instituteId;
	
	private String year;	
	
	private String programTypeName;
	
	private String programName;
	
	private String courseName;
	
	private List<FinalMeritListSearchTo> finalMeritList;
	
	private String selectedCandidates[];
	
	private String univercityName;

	private String instituteName;
	
	private String religionName;	

	private String residentCategoryName;
	
	private String univercity;
	
	private Character isRural;
	
	private int maxIntake;
	

	private String weightageFrom;	
	
	private String weightageTo;	
	
	private boolean isInterviewPresentForCourse;

	private Boolean sportsPerson;
	
	private Boolean handicapped;
	
	private boolean needApproval;
	
	private Map<Integer, Integer> maxIntakeMap;
	
	private int programWise;
	
	private List<String> intakeExcessList;
	private List<FinalMeritListMailTO> mailList;
	private String applnNo;
	
	public List<String> getIntakeExcessList() {
		return intakeExcessList;
	}

	public void setIntakeExcessList(List<String> intakeExcessList) {
		this.intakeExcessList = intakeExcessList;
	}

	public int getProgramWise() {
		return programWise;
	}

	public void setProgramWise(int programWise) {
		this.programWise = programWise;
	}
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCasteCategoryId() {
		return casteCategoryId;
	}

	public void setCasteCategoryId(String casteCategoryId) {
		this.casteCategoryId = casteCategoryId;
	}

	public String getSubReligionId() {
		return subReligionId;
	}

	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getResidentCategoryId() {
		return residentCategoryId;
	}

	public void setResidentCategoryId(String residentCategoryId) {
		this.residentCategoryId = residentCategoryId;
	}	


	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	

	public Character getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Character belongsTo) {
		this.belongsTo = belongsTo;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}


	public List<FinalMeritListSearchTo> getFinalMeritList() {
		return finalMeritList;
	}

	public void setFinalMeritList(List<FinalMeritListSearchTo> finalMeritList) {
		this.finalMeritList = finalMeritList;
	}


	public String[] getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(String[] selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	
	public String getUnivercityName() {
		return univercityName;
	}

	public void setUnivercityName(String univercityName) {
		this.univercityName = univercityName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getResidentCategoryName() {
		return residentCategoryName;
	}

	public void setResidentCategoryName(String residentCategoryName) {
		this.residentCategoryName = residentCategoryName;
	}

	public String getUnivercity() {
		return univercity;
	}

	public void setUnivercity(String univercity) {
		this.univercity = univercity;
	}

	public Character getIsRural() {
		return isRural;
	}

	public void setIsRural(Character isRural) {
		this.isRural = isRural;
	}
	

	public void resetFields(FinalMeritListForm finalMeritListForm) {
		finalMeritListForm.setProgramId(null);
		finalMeritListForm.setProgramTypeId(null);
		finalMeritListForm.setCourseId(null);
		finalMeritListForm.setCasteCategoryId(null);
		finalMeritListForm.setReligionId(null);
		finalMeritListForm.setSubReligionId(null);
		finalMeritListForm.setNationalityId(null);
		finalMeritListForm.setResidentCategoryId(null);
		finalMeritListForm.setBelongsTo(null);
		finalMeritListForm.setGender(null);
		finalMeritListForm.setUniversityId(null);
		finalMeritListForm.setInstituteId(null);
		finalMeritListForm.setWeightageFrom(null);
		finalMeritListForm.setSportsPerson(null);
		finalMeritListForm.setHandicapped(null);
		finalMeritListForm.setWeightageTo(null);
		finalMeritListForm.setYear(null);
		finalMeritListForm.setApplnNo(null);
		this.setIsRural(null);
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
//		this.setBelongsTo(null);
//		this.setReligionId(null);
//		this.setResidentCategoryId(null);
//		this.setUniversityId(null);
//		this.setUnivercity(null);
	}
	
	

	public int getMaxIntake() {
		return maxIntake;
	}

	public void setMaxIntake(int maxIntake) {
		this.maxIntake = maxIntake;
	}

	public String getWeightageFrom() {
		return weightageFrom;
	}

	public void setWeightageFrom(String weightageFrom) {
		this.weightageFrom = weightageFrom;
	}

	public String getWeightageTo() {
		return weightageTo;
	}

	public void setWeightageTo(String weightageTo) {
		this.weightageTo = weightageTo;
	}

	public boolean isInterviewPresentForCourse() {
		return isInterviewPresentForCourse;
	}

	public void setInterviewPresentForCourse(boolean isInterviewPresentForCourse) {
		this.isInterviewPresentForCourse = isInterviewPresentForCourse;
	}

	public Boolean getSportsPerson() {
		return sportsPerson;
	}

	public void setSportsPerson(Boolean sportsPerson) {
		this.sportsPerson = sportsPerson;
	}

	public Boolean getHandicapped() {
		return handicapped;
	}

	public void setHandicapped(Boolean handicapped) {
		this.handicapped = handicapped;
	}

	public boolean isNeedApproval() {
		return needApproval;
	}

	public void setNeedApproval(boolean needApproval) {
		this.needApproval = needApproval;
	}

	public Map<Integer, Integer> getMaxIntakeMap() {
		return maxIntakeMap;
	}

	public void setMaxIntakeMap(Map<Integer, Integer> maxIntakeMap) {
		this.maxIntakeMap = maxIntakeMap;
	}

	public List<FinalMeritListMailTO> getMailList() {
		return mailList;
	}

	public void setMailList(List<FinalMeritListMailTO> mailList) {
		this.mailList = mailList;
	}

	public String getApplnNo() {
		return applnNo;
	}

	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}

	
}
