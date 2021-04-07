package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTypeTO;


public class StudentTypeForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private String studentTypeId;
	private String typeName;
	private String typeDesc;
	private String editedName;
	private String hiddendesc;
	private List<StudentTypeTO> studentTypeList;
	
	
	public String getStudentTypeId() {
		return studentTypeId;
	}
	public void setStudentTypeId(String studentTypeId) {
		this.studentTypeId = studentTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public List<StudentTypeTO> getStudentTypeList() {
		return studentTypeList;
	}
	public void setStudentTypeList(List<StudentTypeTO> studentTypeList) {
		this.studentTypeList = studentTypeList;
	}
	
	public String getEditedName() {
		return editedName;
	}
	public void setEditedName(String editedName) {
		this.editedName = editedName;
	}
	
	public String getHiddendesc() {
		return hiddendesc;
	}
	public void setHiddendesc(String hiddendesc) {
		this.hiddendesc = hiddendesc;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.typeName = null;
		this.typeDesc=null;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
