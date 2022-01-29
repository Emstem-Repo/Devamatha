package com.kp.cms.forms.idcard;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class DownloadIDcardPhotosForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String year;
	private String classId;
	private String pgmId;
	private String applnNo;
	
	
	

	public void resetAll()
	{
		this.year=null;
		this.classId=null;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getPgmId() {
		return pgmId;
	}


	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}


	public String getApplnNo() {
		return applnNo;
	}


	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}


	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}


}
