package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.ValuationScheduleTO;

public class ValuationScheduleForm extends BaseActionForm {
	
	private int id;
	private String examType;
	private String examId;
	private String subjectId;
	private String subjectType;
	private String employeeName;
	private Map<Integer, String> examNameList;
	private ArrayList<KeyValueTO> subjectList;
	private Map<String, String> subjectTypeList;
	private Map<Integer, String>  evaluatorsMap;
	private Map<Integer, String>  externalEvaluatorsMap;
	private String examName;
	private String subjectName;
	private String subjectCode;
	private String date;
	private String employeeId;
	private String externalEmployeeId;
	private List<ValuationScheduleTO> valuationDetails;
	private String valuatorName;
	private Map<Integer, String> subjectMap;
	private String displaySubType;
	private String otherEmpId;
	private String isReviewer;
	private String valuationFrom;
	private String valuationTo;
	private String examDate;
	private String[] selectedEmployeeId;
	private String[] selectedExternalId;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getValuationFrom() {
		return valuationFrom;
	}

	public void setValuationFrom(String valuationFrom) {
		this.valuationFrom = valuationFrom;
	}

	public String getValuationTo() {
		return valuationTo;
	}

	public void setValuationTo(String valuationTo) {
		this.valuationTo = valuationTo;
	}

	public String getIsReviewer() {
		return isReviewer;
	}

	public void setIsReviewer(String isReviewer) {
		this.isReviewer = isReviewer;
	}

	public String getOtherEmpId() {
		return otherEmpId;
	}

	public void setOtherEmpId(String otherEmpId) {
		this.otherEmpId = otherEmpId;
	}

	public String getDisplaySubType() {
		return displaySubType;
	}

	public void setDisplaySubType(String displaySubType) {
		this.displaySubType = displaySubType;
	}

	/*public int getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(int deleteId) {
		this.deleteId = deleteId;
	}*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}

	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	public ArrayList<KeyValueTO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(ArrayList<KeyValueTO> subjectList) {
		this.subjectList = subjectList;
	}

	public Map<String, String> getSubjectTypeList() {
		return subjectTypeList;
	}

	public void setSubjectTypeList(Map<String, String> subjectTypeList) {
		this.subjectTypeList = subjectTypeList;
	}

	public Map<Integer, String> getEvaluatorsMap() {
		return evaluatorsMap;
	}

	public void setEvaluatorsMap(Map<Integer, String> evaluatorsMap) {
		this.evaluatorsMap = evaluatorsMap;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<ValuationScheduleTO> getValuationDetails() {
		return valuationDetails;
	}

	public void setValuationDetails(List<ValuationScheduleTO> valuationDetails) {
		this.valuationDetails = valuationDetails;
	}

	public String getValuatorName() {
		return valuatorName;
	}

	public void setValuatorName(String valuatorName) {
		this.valuatorName = valuatorName;
	}

	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	
	public void resetFields(){
		this.examId=null;
		this.examType="Regular";
		this.subjectId=null;
		this.subjectType=null;
		this.employeeId=null;
		this.valuationDetails=null;
		this.otherEmpId=null;
		this.subjectName = null;
		this.isReviewer="Valuator";
		this.date = null;
		this.displaySubType="sCode";
		this.valuationFrom=null;
		this.valuationTo=null;
		this.externalEmployeeId=null;
		this.selectedEmployeeId=null;
		this.selectedExternalId=null;
		this.evaluatorsMap=null;
		this.externalEvaluatorsMap=null;
	}
	
	public void resetSomeFields(){
		this.subjectId=null;
		this.subjectType=null;
		this.employeeId=null;
		this.otherEmpId=null;
		this.subjectName = null;
		this.isReviewer="Valuator";
		this.date = null;
		this.valuationFrom=null;
		this.valuationTo=null;
		this.displaySubType="sCode";
		this.externalEmployeeId=null;
		this.selectedEmployeeId=null;
		this.selectedExternalId=null;
	}

	public String getExternalEmployeeId() {
		return externalEmployeeId;
	}

	public void setExternalEmployeeId(String externalEmployeeId) {
		this.externalEmployeeId = externalEmployeeId;
	}

	public String[] getSelectedEmployeeId() {
		return selectedEmployeeId;
	}

	public void setSelectedEmployeeId(String[] selectedEmployeeId) {
		this.selectedEmployeeId = selectedEmployeeId;
	}

	public String[] getSelectedExternalId() {
		return selectedExternalId;
	}

	public void setSelectedExternalId(String[] selectedExternalId) {
		this.selectedExternalId = selectedExternalId;
	}

	public Map<Integer, String> getExternalEvaluatorsMap() {
		return externalEvaluatorsMap;
	}

	public void setExternalEvaluatorsMap(Map<Integer, String> externalEvaluatorsMap) {
		this.externalEvaluatorsMap = externalEvaluatorsMap;
	}

}