package com.kp.cms.forms.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.PettyCashAccountHeadGroupCodeTO;


public class PettyCashAccountHeadGroupCodeForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupCode;
	private String newGroupCode;
	private String oldGroupCode;
	private String groupName;
	private String oldGroupName;
	private String method;
	private Integer id;
	private Integer oldId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private List<PettyCashAccountHeadGroupCodeTO> pettyCashAccountHeadGroupCodeListTO=new ArrayList<PettyCashAccountHeadGroupCodeTO>();
	
	
	public Integer getOldId() {
		return oldId;
	}
	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}
	public String getOldGroupName() {
		return oldGroupName;
	}
	public void setOldGroupName(String oldGroupName) {
		this.oldGroupName = oldGroupName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<PettyCashAccountHeadGroupCodeTO> getPettyCashAccountHeadGroupCodeListTO() {
		return pettyCashAccountHeadGroupCodeListTO;
	}
	public void setPettyCashAccountHeadGroupCodeListTO(
			List<PettyCashAccountHeadGroupCodeTO> pettyCashAccountHeadGroupCodeListTO) {
		this.pettyCashAccountHeadGroupCodeListTO = pettyCashAccountHeadGroupCodeListTO;
	}

	public String getCreatedBy() {
		return createdBy;
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
	
	
	
	public String getOldGroupCode() {
		return oldGroupCode;
	}
	public void setOldGroupCode(String oldGroupCode) {
		this.oldGroupCode = oldGroupCode;
	}
	public String getNewGroupCode() {
		return newGroupCode;
	}
	public void setNewGroupCode(String newGroupCode) {
		this.newGroupCode = newGroupCode;
	}
	public void clear() {
		// TODO Auto-generated method stub
		this.id = null;
		this.groupCode=null;
		this.groupName=null;
		
		//this.pettyCashAccountHeadGroupCodeListTO=null;
		//this.feeDivisionId = null;
				
	}
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	super.reset(mapping, request);
	
	this.id = null;
	this.groupCode=null;
	this.groupName=null;
	
	
}

}
