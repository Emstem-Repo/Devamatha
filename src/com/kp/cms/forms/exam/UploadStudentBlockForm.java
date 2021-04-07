package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;

public class UploadStudentBlockForm extends BaseActionForm {
	
	private String examId;
	private String typeId;
	private String[] classIds;
	private String type;
	private List<KeyValueTO> listExamName;
	private Map<Integer,String> mapClass;
	private FormFile thefile;
	private boolean notUploaded;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String[] getClassIds() {
		return classIds;
	}
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}
	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}
	public Map<Integer, String> getMapClass() {
		return mapClass;
	}
	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}
	
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	public boolean isNotUploaded() {
		return notUploaded;
	}
	public void setNotUploaded(boolean notUploaded) {
		this.notUploaded = notUploaded;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void resetFields() {
		this.type=null;
		this.typeId=null;
		this.examId=null;
		this.classIds=null;
		super.setIsPreviousExam("true");
		this.notUploaded=false;
	}
	
}
