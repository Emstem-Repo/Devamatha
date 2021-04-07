package com.kp.cms.forms.pettycash;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.PettyCashAccountHeadGroupCodeTO;
import com.kp.cms.to.pettycash.PettyCashAccountHeadsTO;
import com.kp.cms.to.pettycash.PettyCashAccountNumberTO;

public class PettyCashAccountHeadsForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private Integer id=null;
	private String pcBankAccNumberId=null;
	private String pcAccHeadGroupCodeId=null;
	private String accCode;
	private String accName;
	private String bankAccNo;
	private Boolean isFixedAmount;
	private String amount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String method;
	private PettyCashAccountHeadsTO pettyCashAccountHeadsTO;
	private List<PettyCashAccountHeadsTO> pcAccountHeadTOList;
	private List<PettyCashAccountNumberTO> pcAccountNumberTOList;
	private List<PettyCashAccountHeadGroupCodeTO> pcAccountHeadGroupCodeTOList;
	private String oldAccountCode;
	private Integer oldId;
	private String startDate;
	private String endDate;
	private String programName;
	
	public Integer getOldId() {
		return oldId;
	}
	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getOldAccountCode() {
		return oldAccountCode;
	}
	public void setOldAccountCode(String oldAccountCode) {
		this.oldAccountCode = oldAccountCode;
	}
	public List<PettyCashAccountHeadsTO> getPcAccountHeadTOList() {
		return pcAccountHeadTOList;
	}
	public void setPcAccountHeadTOList(
			List<PettyCashAccountHeadsTO> pcAccountHeadTOList) {
		this.pcAccountHeadTOList = pcAccountHeadTOList;
	}
	public List<PettyCashAccountNumberTO> getPcAccountNumberTOList() {
		return pcAccountNumberTOList;
	}
	public void setPcAccountNumberTOList(
			List<PettyCashAccountNumberTO> pcAccountNumberTOList) {
		this.pcAccountNumberTOList = pcAccountNumberTOList;
	}
	public List<PettyCashAccountHeadGroupCodeTO> getPcAccountHeadGroupCodeTOList() {
		return pcAccountHeadGroupCodeTOList;
	}
	public void setPcAccountHeadGroupCodeTOList(
			List<PettyCashAccountHeadGroupCodeTO> pcAccountHeadGroupCodeTOList) {
		this.pcAccountHeadGroupCodeTOList = pcAccountHeadGroupCodeTOList;
	}
	public PettyCashAccountHeadsTO getPettyCashAccountHeadsTO() {
		return pettyCashAccountHeadsTO;
	}
	public void setPettyCashAccountHeadsTO(
			PettyCashAccountHeadsTO pettyCashAccountHeadsTO) {
		this.pettyCashAccountHeadsTO = pettyCashAccountHeadsTO;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getAccCode() {
		return accCode;
	}
	
	
	public String getPcBankAccNumberId() {
		return pcBankAccNumberId;
	}
	public void setPcBankAccNumberId(String pcBankAccNumberId) {
		this.pcBankAccNumberId = pcBankAccNumberId;
	}
	public String getPcAccHeadGroupCodeId() {
		return pcAccHeadGroupCodeId;
	}
	public void setPcAccHeadGroupCodeId(String pcAccHeadGroupCodeId) {
		this.pcAccHeadGroupCodeId = pcAccHeadGroupCodeId;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public Boolean getIsFixedAmount() {
		return isFixedAmount;
	}
	public void setIsFixedAmount(Boolean isFixedAmount) {
		this.isFixedAmount = isFixedAmount;
	}
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void clear()
	{
		this.accCode=null;
		this.id=null;
		this.accName=null;
		this.amount=null;
		this.bankAccNo=null;
		this.createdBy=null;
		this.createdDate=null;
		this.modifiedBy=null;
		this.lastModifiedDate=null;
		this.isActive=null;
		this.isFixedAmount=null;
		this.pcBankAccNumberId=null;
		this.pcAccHeadGroupCodeId=null;
		this.startDate=null;
		this.endDate=null;
		this.programName=null;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	super.reset(mapping, request);
	this.accCode=null;
	this.id=null;
	this.accName=null;
	this.amount=null;
	this.bankAccNo=null;
	this.createdBy=null;
	this.createdDate=null;
	this.modifiedBy=null;
	this.lastModifiedDate=null;
	this.isActive=null;
	this.isFixedAmount=null;
	this.pcBankAccNumberId=null;
	this.pcAccHeadGroupCodeId=null;
}

public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter("formName");
	ActionErrors actionErrors = super.validate(mapping, request, formName);
	
	return actionErrors;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getProgramName() {
	return programName;
}
public void setProgramName(String programName) {
	this.programName = programName;
}
	
	
}
