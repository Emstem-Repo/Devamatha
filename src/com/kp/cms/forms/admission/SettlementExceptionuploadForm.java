package com.kp.cms.forms.admission;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class SettlementExceptionuploadForm extends BaseActionForm{
	private FormFile thefile;
	private Boolean duplicanRefNo;
	private String canRefNoMsg;
	private String canRefNo;
	private String exceptedCanRefNo;
	
	public void resetFields(){
		this.thefile=null;
		this.duplicanRefNo=false;
		this.canRefNoMsg=null;
		this.canRefNo=null;
		this.exceptedCanRefNo=null;
	}
	public void reset(){
		this.duplicanRefNo=false;
		this.canRefNoMsg=null;
		this.canRefNo=null;
		this.exceptedCanRefNo=null;
		
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	public Boolean getDuplicanRefNo() {
		return duplicanRefNo;
	}
	public void setDuplicanRefNo(Boolean duplicanRefNo) {
		this.duplicanRefNo = duplicanRefNo;
	}
	public String getCanRefNoMsg() {
		return canRefNoMsg;
	}
	public void setCanRefNoMsg(String canRefNoMsg) {
		this.canRefNoMsg = canRefNoMsg;
	}
	public String getCanRefNo() {
		return canRefNo;
	}
	public void setCanRefNo(String canRefNo) {
		this.canRefNo = canRefNo;
	}
	public String getExceptedCanRefNo() {
		return exceptedCanRefNo;
	}
	public void setExceptedCanRefNo(String exceptedCanRefNo) {
		this.exceptedCanRefNo = exceptedCanRefNo;
	}
}
