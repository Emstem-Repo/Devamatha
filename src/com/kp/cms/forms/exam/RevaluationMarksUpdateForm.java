package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.RevaluationMarksUpdateTo;

public class RevaluationMarksUpdateForm extends BaseActionForm{
	private String examId;
	private String revaluation;
	private Map<Integer, String> examNameMap;
	private String examType;
	private List<RevaluationMarksUpdateTo> studentDetailsList;
	private boolean flag;
	private int studentid;
	private int subjectid;
	private int classid;
	private String comment;
	private int examRevaluationAppId;
	private int examMarksEntryId;
	private int examMarksEntryDetailsId;
	private int examMarksEntryIdForSecondEvl2;
	private int examMarksEntryDetailsIdForSecondEvl2;
	private int examMarksEntryIdForNoEvl;
	private int examMarksEntryDetailsIdForNoEvl;
	private String oldMarks;
	private String oldMark1;
	private String oldMark2;
	private String newMarks;
	private String newMark1;
	private String newMark2;
	private String avgMarks;
	private Map<Integer, Double> maxMarksMap;
	private int courseid;
	private int schemeNumber;
	private int examRevaluationAppIdForEvL1;
	private int examRevaluationAppIdForEvL2;
	private int count;
	private String academicYear;
	private String option;
	private String subjectname;
	private List<RevaluationMarksUpdateTo> verificationListHavingMarksForUpdateButton;
	private String oldAvgMarks;
	private List<String> errorList;
	private List<Integer> classList;
	private Map<Integer, List<Integer>> studentListClassWise;
	
	//new
	private String newMark3;
	private String oldMark3;
	private String diffOfMarks3;
	private Boolean gracedMark3;
	private int examRevaluationAppIdForEvL3;
	private int examMarksEntryIdForThirdEvl3;
	private int examMarksEntryDetailsIdForThirdEvl3;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getRevaluation() {
		return revaluation;
	}
	public void setRevaluation(String revaluation) {
		this.revaluation = revaluation;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public List<RevaluationMarksUpdateTo> getStudentDetailsList() {
		return studentDetailsList;
	}
	public void setStudentDetailsList(
			List<RevaluationMarksUpdateTo> studentDetailsList) {
		this.studentDetailsList = studentDetailsList;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public int getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getExamRevaluationAppId() {
		return examRevaluationAppId;
	}
	public void setExamRevaluationAppId(int examRevaluationAppId) {
		this.examRevaluationAppId = examRevaluationAppId;
	}
	public int getExamMarksEntryId() {
		return examMarksEntryId;
	}
	public void setExamMarksEntryId(int examMarksEntryId) {
		this.examMarksEntryId = examMarksEntryId;
	}
	public int getExamMarksEntryDetailsId() {
		return examMarksEntryDetailsId;
	}
	public void setExamMarksEntryDetailsId(int examMarksEntryDetailsId) {
		this.examMarksEntryDetailsId = examMarksEntryDetailsId;
	}
	public int getExamMarksEntryIdForSecondEvl2() {
		return examMarksEntryIdForSecondEvl2;
	}
	public void setExamMarksEntryIdForSecondEvl2(int examMarksEntryIdForSecondEvl2) {
		this.examMarksEntryIdForSecondEvl2 = examMarksEntryIdForSecondEvl2;
	}
	public int getExamMarksEntryDetailsIdForSecondEvl2() {
		return examMarksEntryDetailsIdForSecondEvl2;
	}
	public void setExamMarksEntryDetailsIdForSecondEvl2(
			int examMarksEntryDetailsIdForSecondEvl2) {
		this.examMarksEntryDetailsIdForSecondEvl2 = examMarksEntryDetailsIdForSecondEvl2;
	}
	public int getExamMarksEntryIdForNoEvl() {
		return examMarksEntryIdForNoEvl;
	}
	public void setExamMarksEntryIdForNoEvl(int examMarksEntryIdForNoEvl) {
		this.examMarksEntryIdForNoEvl = examMarksEntryIdForNoEvl;
	}
	public int getExamMarksEntryDetailsIdForNoEvl() {
		return examMarksEntryDetailsIdForNoEvl;
	}
	public void setExamMarksEntryDetailsIdForNoEvl(
			int examMarksEntryDetailsIdForNoEvl) {
		this.examMarksEntryDetailsIdForNoEvl = examMarksEntryDetailsIdForNoEvl;
	}
	public String getOldMarks() {
		return oldMarks;
	}
	public void setOldMarks(String oldMarks) {
		this.oldMarks = oldMarks;
	}
	public String getOldMark1() {
		return oldMark1;
	}
	public void setOldMark1(String oldMark1) {
		this.oldMark1 = oldMark1;
	}
	public String getOldMark2() {
		return oldMark2;
	}
	public void setOldMark2(String oldMark2) {
		this.oldMark2 = oldMark2;
	}
	public String getNewMarks() {
		return newMarks;
	}
	public void setNewMarks(String newMarks) {
		this.newMarks = newMarks;
	}
	public String getNewMark1() {
		return newMark1;
	}
	public void setNewMark1(String newMark1) {
		this.newMark1 = newMark1;
	}
	public String getNewMark2() {
		return newMark2;
	}
	public void setNewMark2(String newMark2) {
		this.newMark2 = newMark2;
	}
	public String getAvgMarks() {
		return avgMarks;
	}
	public void setAvgMarks(String avgMarks) {
		this.avgMarks = avgMarks;
	}
	public Map<Integer, Double> getMaxMarksMap() {
		return maxMarksMap;
	}
	public void setMaxMarksMap(Map<Integer, Double> maxMarksMap) {
		this.maxMarksMap = maxMarksMap;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public int getSchemeNumber() {
		return schemeNumber;
	}
	public void setSchemeNumber(int schemeNumber) {
		this.schemeNumber = schemeNumber;
	}
	public int getExamRevaluationAppIdForEvL1() {
		return examRevaluationAppIdForEvL1;
	}
	public void setExamRevaluationAppIdForEvL1(int examRevaluationAppIdForEvL1) {
		this.examRevaluationAppIdForEvL1 = examRevaluationAppIdForEvL1;
	}
	public int getExamRevaluationAppIdForEvL2() {
		return examRevaluationAppIdForEvL2;
	}
	public void setExamRevaluationAppIdForEvL2(int examRevaluationAppIdForEvL2) {
		this.examRevaluationAppIdForEvL2 = examRevaluationAppIdForEvL2;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public void resetFields(){
		this.examType="Regular";
		this.studentDetailsList=null;
		this.revaluation=null;
		this.examId=null;
		this.examNameMap=null;
		this.flag=false;
		this.option="NotUpdated";
		this.academicYear=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	public List<RevaluationMarksUpdateTo> getVerificationListHavingMarksForUpdateButton() {
		return verificationListHavingMarksForUpdateButton;
	}
	public void setVerificationListHavingMarksForUpdateButton(
			List<RevaluationMarksUpdateTo> verificationListHavingMarksForUpdateButton) {
		this.verificationListHavingMarksForUpdateButton = verificationListHavingMarksForUpdateButton;
	}
	public String getOldAvgMarks() {
		return oldAvgMarks;
	}
	public void setOldAvgMarks(String oldAvgMarks) {
		this.oldAvgMarks = oldAvgMarks;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public List<Integer> getClassList() {
		return classList;
	}
	public void setClassList(List<Integer> classList) {
		this.classList = classList;
	}
	public Map<Integer, List<Integer>> getStudentListClassWise() {
		return studentListClassWise;
	}
	public void setStudentListClassWise(
			Map<Integer, List<Integer>> studentListClassWise) {
		this.studentListClassWise = studentListClassWise;
	}
	public String getNewMark3() {
		return newMark3;
	}
	public void setNewMark3(String newMark3) {
		this.newMark3 = newMark3;
	}
	public String getOldMark3() {
		return oldMark3;
	}
	public void setOldMark3(String oldMark3) {
		this.oldMark3 = oldMark3;
	}
	public String getDiffOfMarks3() {
		return diffOfMarks3;
	}
	public void setDiffOfMarks3(String diffOfMarks3) {
		this.diffOfMarks3 = diffOfMarks3;
	}
	public Boolean getGracedMark3() {
		return gracedMark3;
	}
	public void setGracedMark3(Boolean gracedMark3) {
		this.gracedMark3 = gracedMark3;
	}
	public int getExamRevaluationAppIdForEvL3() {
		return examRevaluationAppIdForEvL3;
	}
	public void setExamRevaluationAppIdForEvL3(int examRevaluationAppIdForEvL3) {
		this.examRevaluationAppIdForEvL3 = examRevaluationAppIdForEvL3;
	}
	public int getExamMarksEntryIdForThirdEvl3() {
		return examMarksEntryIdForThirdEvl3;
	}
	public void setExamMarksEntryIdForThirdEvl3(int examMarksEntryIdForThirdEvl3) {
		this.examMarksEntryIdForThirdEvl3 = examMarksEntryIdForThirdEvl3;
	}
	public int getExamMarksEntryDetailsIdForThirdEvl3() {
		return examMarksEntryDetailsIdForThirdEvl3;
	}
	public void setExamMarksEntryDetailsIdForThirdEvl3(
			int examMarksEntryDetailsIdForThirdEvl3) {
		this.examMarksEntryDetailsIdForThirdEvl3 = examMarksEntryDetailsIdForThirdEvl3;
	}
	
	
	
}
