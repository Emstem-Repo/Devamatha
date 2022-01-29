package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamInvigilatorExcemptionDatewiseTO;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;

public class ExamInvigilatorExcemptionDatewiseForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer,String> examMap;
	private Map<Integer,String> deanaryMap;
	private Map<Integer,String> deptMap;
	private Map<Integer,String> workLocationMap;
	private String examId;
	private String deanaryId;
	private String deptId;
	private List<ExamInvigilatorExcemptionDatewiseTO> list;
	private String toDate;
	private String fromDate;
	private String session;
	private Map<Integer,Integer> examInvigilatorsMap;
	private List<ExamInvigilatorExcemptionDatewiseTO> alreadyExemptedlist;
	private String isAdd;
	private String selectedId;
	private String teacherName;
	private String teacherId;
	private String editDate;
	private boolean flag;
	private Map<Integer,String> sessionMap;
	private String tempAcademicYear;
	
	
	public String getTempAcademicYear() {
		return tempAcademicYear;
	}
	public void setTempAcademicYear(String tempAcademicYear) {
		this.tempAcademicYear = tempAcademicYear;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public Map<Integer, String> getDeanaryMap() {
		return deanaryMap;
	}
	public void setDeanaryMap(Map<Integer, String> deanaryMap) {
		this.deanaryMap = deanaryMap;
	}
	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getDeanaryId() {
		return deanaryId;
	}
	public void setDeanaryId(String deanaryId) {
		this.deanaryId = deanaryId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<ExamInvigilatorExcemptionDatewiseTO> getList() {
		return list;
	}
	public void setList(List<ExamInvigilatorExcemptionDatewiseTO> list) {
		this.list = list;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public Map<Integer, Integer> getExamInvigilatorsMap() {
		return examInvigilatorsMap;
	}
	public void setExamInvigilatorsMap(Map<Integer, Integer> examInvigilatorsMap) {
		this.examInvigilatorsMap = examInvigilatorsMap;
	}
	public List<ExamInvigilatorExcemptionDatewiseTO> getAlreadyExemptedlist() {
		return alreadyExemptedlist;
	}
	public void setAlreadyExemptedlist(
			List<ExamInvigilatorExcemptionDatewiseTO> alreadyExemptedlist) {
		this.alreadyExemptedlist = alreadyExemptedlist;
	}
	public String getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}