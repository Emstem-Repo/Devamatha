package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.ClassesTO;

public class NewUpdateProccessForm extends BaseActionForm {
	
	private String examId;
	private String process;
	private Map<Integer, String> examMap;
	private List<ClassesTO> classesList;
	private String isPreviousExam;
	private String promotionCritariaCheck;
	private String examFeePaidCheck;
	private String processName;
	private String examName;
	private boolean validateProcess;
	private String validateMsg;
	private List<String> errorList;
	private Map<Integer, String> batchYearList;
	private String batchYear;
	private Boolean isGracing;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public List<ClassesTO> getClassesList() {
		return classesList;
	}
	public void setClassesList(List<ClassesTO> classesList) {
		this.classesList = classesList;
	}
	
	public String getIsPreviousExam() {
		return isPreviousExam;
	}
	public void setIsPreviousExam(String isPreviousExam) {
		this.isPreviousExam = isPreviousExam;
	}
	public String getPromotionCritariaCheck() {
		return promotionCritariaCheck;
	}
	public void setPromotionCritariaCheck(String promotionCritariaCheck) {
		this.promotionCritariaCheck = promotionCritariaCheck;
	}
	public String getExamFeePaidCheck() {
		return examFeePaidCheck;
	}
	public void setExamFeePaidCheck(String examFeePaidCheck) {
		this.examFeePaidCheck = examFeePaidCheck;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public boolean isValidateProcess() {
		return validateProcess;
	}
	public void setValidateProcess(boolean validateProcess) {
		this.validateProcess = validateProcess;
	}
	public String getValidateMsg() {
		return validateMsg;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		this.examId=null;
		this.process=null;
		super.setYear(null);
		this.isPreviousExam="true";
		this.promotionCritariaCheck=null;
		this.examFeePaidCheck=null;
		this.validateProcess=false;
		this.validateMsg=null;
		this.batchYear=null;
		}
	public String getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(String batchYear) {
		this.batchYear = batchYear;
	}
	public Map<Integer, String> getBatchYearList() {
		return batchYearList;
	}
	public void setBatchYearList(Map<Integer, String> batchYearList) {
		this.batchYearList = batchYearList;
	}
	public Boolean getIsGracing() {
		return isGracing;
	}
	public void setIsGracing(Boolean isGracing) {
		this.isGracing = isGracing;
	}
	
}
