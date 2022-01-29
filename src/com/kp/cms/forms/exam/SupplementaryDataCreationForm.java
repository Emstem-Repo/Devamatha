package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.ClassesTO;

public class SupplementaryDataCreationForm extends BaseActionForm{
	private int id;
	private String examId;
	private Map<Integer,String> suppExamNameMap;
	private List<ClassesTO> classesTOList;
	private String examName;
	private String processName;
	private boolean validateProcess;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, String> getSuppExamNameMap() {
		return suppExamNameMap;
	}
	public void setSuppExamNameMap(Map<Integer, String> suppExamNameMap) {
		this.suppExamNameMap = suppExamNameMap;
	}
	public List<ClassesTO> getClassesTOList() {
		return classesTOList;
	}
	public void setClassesTOList(List<ClassesTO> classesTOList) {
		this.classesTOList = classesTOList;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public boolean isValidateProcess() {
		return validateProcess;
	}
	public void setValidateProcess(boolean validateProcess) {
		this.validateProcess = validateProcess;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		this.validateProcess = false;
		this.examId=null;
		super.setAcademicYear(null);
		this.examName = null;
		this.processName=null;
		this.classesTOList = null;
		this.suppExamNameMap = null;
		
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
}
