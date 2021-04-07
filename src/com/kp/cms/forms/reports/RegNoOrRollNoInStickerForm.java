package com.kp.cms.forms.reports;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class RegNoOrRollNoInStickerForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regNoFrom;
	private String regNoTo;
	private String isRollNo;
	private List<StudentTO> studentList;
	
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public String getIsRollNo() {
		return isRollNo;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	public void setIsRollNo(String isRollNo) {
		this.isRollNo = isRollNo;
	}
	
	public List<StudentTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		regNoFrom = null;
		regNoTo = null;
		super.setProgramTypeId(null);
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	

}
