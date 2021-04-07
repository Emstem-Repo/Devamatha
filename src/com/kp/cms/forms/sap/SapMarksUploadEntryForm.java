package com.kp.cms.forms.sap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.sap.SapMarksUploadEntryTO;

public class SapMarksUploadEntryForm extends BaseActionForm {
	  
	private String examId;
	private String subjectId;
	private String subjectType;
	private FormFile uploadedFile;
	private String examType;
	private Map<Integer, String> listExamName;
    private boolean regular;
	private boolean internal;
	private String validateMsg;
	private List<SapMarksUploadEntryTO> marksUploadEntryTOList;
	
	
	public List<SapMarksUploadEntryTO> getMarksUploadEntryTOList() {
		return marksUploadEntryTOList;
	}
	public void setMarksUploadEntryTOList(
			List<SapMarksUploadEntryTO> marksUploadEntryTOList) {
		this.marksUploadEntryTOList = marksUploadEntryTOList;
	}
	public String getValidateMsg() {
		return validateMsg;
	}
	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
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
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public Map<Integer, String> getListExamName() {
		return listExamName;
	}
	public void setListExamName(Map<Integer, String> listExamName) {
		this.listExamName = listExamName;
	}
	
	public boolean isRegular() {
		return regular;
	}
	public void setRegular(boolean regular) {
		this.regular = regular;
	}
	public boolean isInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields()
	{
		this.examType="Regular";
		this.examId=null;
		this.subjectId=null;
		this.subjectType=null;
		this.uploadedFile=null;
		this.listExamName=null;
		this.regular=false;
		this.internal=false;
		this.marksUploadEntryTOList=null;
		this.validateMsg=null;
	}
}
