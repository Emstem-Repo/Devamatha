package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.to.usermanagement.RolesTO;

public class CertificateDetailsForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String certificateName;
	private String fees;
	private String marksCard;
	private List certificateList;
	private List<RolesTO> assignToRolesList;
	private List<CertificatePurposeTO> assignPurposeList;
	private String roleId;
	private String purposeId;
	private int halfLength;
	private int certificateDetailsId;
	private int countPurpose;
	private int oldCountPurpose;
	private int countt;
	private int oldCountt;
	private String isTemplateRequired ;
	private String description;
	public String templateDescription;
	public List<CertificateDetailsTemplateTO> templateList;
	public String selectedCertificateName;
	public String templateId;
	public String isReasonRequired;
	public String isIdCard;
	
	public int getHalfLength() {
		return halfLength;
	}

	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}

	public List<RolesTO> getAssignToRolesList() {
		return assignToRolesList;
	}

	public void setAssignToRolesList(List<RolesTO> assignToRolesList) {
		this.assignToRolesList = assignToRolesList;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public String getMarksCard() {
		return marksCard;
	}
	public void setMarksCard(String marksCard) {
		this.marksCard = marksCard;
	}
	public List getCertificateList() {
		return certificateList;
	}
	@SuppressWarnings("unchecked")
	public void setCertificateList(List certificateList) {
		this.certificateList = certificateList;
	}
	public void reset(){
		certificateName=null;
		fees=null;
		marksCard="No";	
		isTemplateRequired="No";
		isReasonRequired="No";
		isIdCard="No";
		id=0;
		description=null;
		templateDescription=null;
		templateList=null;
		certificateDetailsId=0;
		selectedCertificateName=null;
	}
	public void clear(){
		this.roleId=null;
		this.purposeId=null;
		
	}
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		String formName=request.getParameter("formName");
		ActionErrors errors=super.validate(mapping, request,formName);
		return errors;
	}

	public int getCertificateDetailsId() {
		return certificateDetailsId;
	}

	public void setCertificateDetailsId(int certificateDetailsId) {
		this.certificateDetailsId = certificateDetailsId;
	}

	public int getCountt() {
		return countt;
	}

	public void setCountt(int countt) {
		this.countt = countt;
	}

	public int getOldCountt() {
		return oldCountt;
	}

	public void setOldCountt(int oldCountt) {
		this.oldCountt = oldCountt;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<CertificatePurposeTO> getAssignPurposeList() {
		return assignPurposeList;
	}

	public void setAssignPurposeList(List<CertificatePurposeTO> assignPurposeList) {
		this.assignPurposeList = assignPurposeList;
	}

	public String getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
	}

	public int getCountPurpose() {
		return countPurpose;
	}

	public void setCountPurpose(int countPurpose) {
		this.countPurpose = countPurpose;
	}

	public int getOldCountPurpose() {
		return oldCountPurpose;
	}

	public void setOldCountPurpose(int oldCountPurpose) {
		this.oldCountPurpose = oldCountPurpose;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getIsTemplateRequired() {
		return isTemplateRequired;
	}

	public void setIsTemplateRequired(String isTemplateRequired) {
		this.isTemplateRequired = isTemplateRequired;
	}

	public String getSelectedCertificateName() {
		return selectedCertificateName;
	}

	public void setSelectedCertificateName(String selectedCertificateName) {
		this.selectedCertificateName = selectedCertificateName;
	}

	public List<CertificateDetailsTemplateTO> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<CertificateDetailsTemplateTO> templateList) {
		this.templateList = templateList;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	

	public String getIsIdCard() {
		return isIdCard;
	}

	public void setIsIdCard(String isIdCard) {
		this.isIdCard = isIdCard;
	}

	public String getIsReasonRequired() {
		return isReasonRequired;
	}

	public void setIsReasonRequired(String isReasonRequired) {
		this.isReasonRequired = isReasonRequired;
	}

	


}
