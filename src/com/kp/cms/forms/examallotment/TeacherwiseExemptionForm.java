package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyEditTo;
import com.kp.cms.to.examallotment.TeacherwiseExemptionTo;

public class TeacherwiseExemptionForm extends BaseActionForm{

	private Map<Integer,String> examMap;
	private Map<Integer,String> deanaryMap;
	private Map<Integer,String> deptMap;
	private Map<Integer,String> workLocationMap;
	private String examId;
	private String deanaryId;
	private String deptId;
	private List<TeacherwiseExemptionTo> list;
	private Map<Integer,Integer> examInvigilatorsMap;
	private String facultyId;
	private Map<Integer,String> facultyMap;
	private List<TeacherwiseExemptionTo> addMorelist;
	private boolean addMoreFlag;
	private String focus;
	private int countValue;
	private int id;
	private String count;
	private String tempAcademicYear;
	
	
	public String getTempAcademicYear() {
		return tempAcademicYear;
	}
	public void setTempAcademicYear(String tempAcademicYear) {
		this.tempAcademicYear = tempAcademicYear;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCountValue() {
		return countValue;
	}
	public void setCountValue(int countValue) {
		this.countValue = countValue;
	}
	public List<TeacherwiseExemptionTo> getAddMorelist() {
		return addMorelist;
	}
	public void setAddMorelist(List<TeacherwiseExemptionTo> addMorelist) {
		this.addMorelist = addMorelist;
	}
	public boolean isAddMoreFlag() {
		return addMoreFlag;
	}
	public void setAddMoreFlag(boolean addMoreFlag) {
		this.addMoreFlag = addMoreFlag;
	}
	public String getFocus() {
		return focus;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	public String getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}
	public Map<Integer, String> getFacultyMap() {
		return facultyMap;
	}
	public void setFacultyMap(Map<Integer, String> facultyMap) {
		this.facultyMap = facultyMap;
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
	public List<TeacherwiseExemptionTo> getList() {
		return list;
	}
	public void setList(List<TeacherwiseExemptionTo> list) {
		this.list = list;
	}
	public Map<Integer, Integer> getExamInvigilatorsMap() {
		return examInvigilatorsMap;
	}
	public void setExamInvigilatorsMap(Map<Integer, Integer> examInvigilatorsMap) {
		this.examInvigilatorsMap = examInvigilatorsMap;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
