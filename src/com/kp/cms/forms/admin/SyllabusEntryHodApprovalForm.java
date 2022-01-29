package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SyllabusEntryHodApprovalTo;
import com.kp.cms.to.admin.SyllabusEntryPreviewTo;
import com.kp.cms.to.admin.SyllabusEntryTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;

public class SyllabusEntryHodApprovalForm extends BaseActionForm{
	private List<SyllabusEntryHodApprovalTo> list;
	private String listSize;
	private int id;
	private String courseObjective;
	private String lerningOutcome;
	private String textBooksAndRefBooks;
	private String freeText;
	private String totTeachHrsPerSem;
	private String noOfLectureHrsPerWeek;
	private String credits;
	private String maxMarks;
	private List<SyllabusEntryUnitsHoursTo> syllabusEntryUnitsHoursTos;
	private Integer num;
	private boolean unitsFlag;
	private FormFile theFile;
	private String batchYear;
	private Map<Integer,String> departmentMap;
	private Map<Integer,String> semesterMap;
	private String subjectId;
	private String subjectCode;
	private String subjectName;
	private String isFromSecondPageToFirstPage;
	private String semisterId;
	private String tempBatchYear;
	private String status;
	private String teacherFlag;
	private Map<String,List<SyllabusEntryTo>> subjectMapBySem;
	private String unitsFocus;
	private String headingsFocus;
	private String unitOrHead;
	private String previousYear;
	private String previousYearSubjectId;
	private String recommendedReading;
	private String position;
	private String changeInSyllabus;
	private String remarks;
	private String changeReason;
	private String briefDetalsAboutChange;
	private String briefDetailsExistingSyllabus;
	private String tempChangeInSyllabus;
	private String tempHeadingsFocus;
	private String departmentName;
	private String parentDepartment;
	private String questionBankRequired;
	private String theoryOrPractical;
	private String secondLanguage;
	private String tempRejectReason;
	private String rejectReason;
	private String tempOpen;
	private List<SyllabusEntryPreviewTo> hodPreviewList;
	private String departmentOverview;
	private String missionStatement;
	private String introductionProgramme;
	private String programObjective;
	private String assesmentPattern;
	private String examinationAndAssesments;
	private String courseName;
	private List<String> batchYearList;
	
	
	public List<String> getBatchYearList() {
		return batchYearList;
	}
	public void setBatchYearList(List<String> batchYearList) {
		this.batchYearList = batchYearList;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getDepartmentOverview() {
		return departmentOverview;
	}
	public void setDepartmentOverview(String departmentOverview) {
		this.departmentOverview = departmentOverview;
	}
	public String getMissionStatement() {
		return missionStatement;
	}
	public void setMissionStatement(String missionStatement) {
		this.missionStatement = missionStatement;
	}
	public String getIntroductionProgramme() {
		return introductionProgramme;
	}
	public void setIntroductionProgramme(String introductionProgramme) {
		this.introductionProgramme = introductionProgramme;
	}
	public String getProgramObjective() {
		return programObjective;
	}
	public void setProgramObjective(String programObjective) {
		this.programObjective = programObjective;
	}
	public String getAssesmentPattern() {
		return assesmentPattern;
	}
	public void setAssesmentPattern(String assesmentPattern) {
		this.assesmentPattern = assesmentPattern;
	}
	public String getExaminationAndAssesments() {
		return examinationAndAssesments;
	}
	public void setExaminationAndAssesments(String examinationAndAssesments) {
		this.examinationAndAssesments = examinationAndAssesments;
	}
	public List<SyllabusEntryPreviewTo> getHodPreviewList() {
		return hodPreviewList;
	}
	public void setHodPreviewList(List<SyllabusEntryPreviewTo> hodPreviewList) {
		this.hodPreviewList = hodPreviewList;
	}
	public String getTempOpen() {
		return tempOpen;
	}
	public void setTempOpen(String tempOpen) {
		this.tempOpen = tempOpen;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getTempRejectReason() {
		return tempRejectReason;
	}
	public void setTempRejectReason(String tempRejectReason) {
		this.tempRejectReason = tempRejectReason;
	}
	public List<SyllabusEntryHodApprovalTo> getList() {
		return list;
	}
	public void setList(List<SyllabusEntryHodApprovalTo> list) {
		this.list = list;
	}
	public String getListSize() {
		return listSize;
	}
	public void setListSize(String listSize) {
		this.listSize = listSize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseObjective() {
		return courseObjective;
	}
	public void setCourseObjective(String courseObjective) {
		this.courseObjective = courseObjective;
	}
	public String getLerningOutcome() {
		return lerningOutcome;
	}
	public void setLerningOutcome(String lerningOutcome) {
		this.lerningOutcome = lerningOutcome;
	}
	public String getTextBooksAndRefBooks() {
		return textBooksAndRefBooks;
	}
	public void setTextBooksAndRefBooks(String textBooksAndRefBooks) {
		this.textBooksAndRefBooks = textBooksAndRefBooks;
	}
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	public String getTotTeachHrsPerSem() {
		return totTeachHrsPerSem;
	}
	public void setTotTeachHrsPerSem(String totTeachHrsPerSem) {
		this.totTeachHrsPerSem = totTeachHrsPerSem;
	}
	public String getNoOfLectureHrsPerWeek() {
		return noOfLectureHrsPerWeek;
	}
	public void setNoOfLectureHrsPerWeek(String noOfLectureHrsPerWeek) {
		this.noOfLectureHrsPerWeek = noOfLectureHrsPerWeek;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public List<SyllabusEntryUnitsHoursTo> getSyllabusEntryUnitsHoursTos() {
		return syllabusEntryUnitsHoursTos;
	}
	public void setSyllabusEntryUnitsHoursTos(
			List<SyllabusEntryUnitsHoursTo> syllabusEntryUnitsHoursTos) {
		this.syllabusEntryUnitsHoursTos = syllabusEntryUnitsHoursTos;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public boolean isUnitsFlag() {
		return unitsFlag;
	}
	public void setUnitsFlag(boolean unitsFlag) {
		this.unitsFlag = unitsFlag;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public String getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(String batchYear) {
		this.batchYear = batchYear;
	}
	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public Map<Integer, String> getSemesterMap() {
		return semesterMap;
	}
	public void setSemesterMap(Map<Integer, String> semesterMap) {
		this.semesterMap = semesterMap;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getIsFromSecondPageToFirstPage() {
		return isFromSecondPageToFirstPage;
	}
	public void setIsFromSecondPageToFirstPage(String isFromSecondPageToFirstPage) {
		this.isFromSecondPageToFirstPage = isFromSecondPageToFirstPage;
	}
	public String getSemisterId() {
		return semisterId;
	}
	public void setSemisterId(String semisterId) {
		this.semisterId = semisterId;
	}
	public String getTempBatchYear() {
		return tempBatchYear;
	}
	public void setTempBatchYear(String tempBatchYear) {
		this.tempBatchYear = tempBatchYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTeacherFlag() {
		return teacherFlag;
	}
	public void setTeacherFlag(String teacherFlag) {
		this.teacherFlag = teacherFlag;
	}
	public Map<String, List<SyllabusEntryTo>> getSubjectMapBySem() {
		return subjectMapBySem;
	}
	public void setSubjectMapBySem(
			Map<String, List<SyllabusEntryTo>> subjectMapBySem) {
		this.subjectMapBySem = subjectMapBySem;
	}
	public String getUnitsFocus() {
		return unitsFocus;
	}
	public void setUnitsFocus(String unitsFocus) {
		this.unitsFocus = unitsFocus;
	}
	public String getHeadingsFocus() {
		return headingsFocus;
	}
	public void setHeadingsFocus(String headingsFocus) {
		this.headingsFocus = headingsFocus;
	}
	public String getUnitOrHead() {
		return unitOrHead;
	}
	public void setUnitOrHead(String unitOrHead) {
		this.unitOrHead = unitOrHead;
	}
	public String getPreviousYear() {
		return previousYear;
	}
	public void setPreviousYear(String previousYear) {
		this.previousYear = previousYear;
	}
	public String getPreviousYearSubjectId() {
		return previousYearSubjectId;
	}
	public void setPreviousYearSubjectId(String previousYearSubjectId) {
		this.previousYearSubjectId = previousYearSubjectId;
	}
	public String getRecommendedReading() {
		return recommendedReading;
	}
	public void setRecommendedReading(String recommendedReading) {
		this.recommendedReading = recommendedReading;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getChangeInSyllabus() {
		return changeInSyllabus;
	}
	public void setChangeInSyllabus(String changeInSyllabus) {
		this.changeInSyllabus = changeInSyllabus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getBriefDetalsAboutChange() {
		return briefDetalsAboutChange;
	}
	public void setBriefDetalsAboutChange(String briefDetalsAboutChange) {
		this.briefDetalsAboutChange = briefDetalsAboutChange;
	}
	public String getBriefDetailsExistingSyllabus() {
		return briefDetailsExistingSyllabus;
	}
	public void setBriefDetailsExistingSyllabus(String briefDetailsExistingSyllabus) {
		this.briefDetailsExistingSyllabus = briefDetailsExistingSyllabus;
	}
	public String getTempChangeInSyllabus() {
		return tempChangeInSyllabus;
	}
	public void setTempChangeInSyllabus(String tempChangeInSyllabus) {
		this.tempChangeInSyllabus = tempChangeInSyllabus;
	}
	public String getTempHeadingsFocus() {
		return tempHeadingsFocus;
	}
	public void setTempHeadingsFocus(String tempHeadingsFocus) {
		this.tempHeadingsFocus = tempHeadingsFocus;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getParentDepartment() {
		return parentDepartment;
	}
	public void setParentDepartment(String parentDepartment) {
		this.parentDepartment = parentDepartment;
	}
	public String getQuestionBankRequired() {
		return questionBankRequired;
	}
	public void setQuestionBankRequired(String questionBankRequired) {
		this.questionBankRequired = questionBankRequired;
	}
	public String getTheoryOrPractical() {
		return theoryOrPractical;
	}
	public void setTheoryOrPractical(String theoryOrPractical) {
		this.theoryOrPractical = theoryOrPractical;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
