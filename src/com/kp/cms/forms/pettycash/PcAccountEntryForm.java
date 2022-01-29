package com.kp.cms.forms.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.PcAccountTo;

public class PcAccountEntryForm extends BaseActionForm {
	
	private String accountNo;
	private FormFile thefile;
	private List<PcAccountTo> accList;
	private String accountId;
	private boolean isPhoto;
	private PcAccountTo accountTo;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	public List<PcAccountTo> getAccList() {
		return accList;
	}
	public void setAccList(List<PcAccountTo> accList) {
		this.accList = accList;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public boolean getIsPhoto() {
		return isPhoto;
	}
	public void setIsPhoto(boolean isPhoto) {
		this.isPhoto = isPhoto;
	}
	public PcAccountTo getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(PcAccountTo accountTo) {
		this.accountTo = accountTo;
	}
	public void reset() {
		this.accountNo = null;
		this.thefile=null;
		this.accList=null;
		this.accountId=null;
		this.isPhoto=false;
		this.accountTo=null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
