package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AdmissionStatusTO;



public class AdmissionStatusForm extends AdmissionFormForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String applicationNo;
	private String dateOfBirth;
	private int appliedYear;
	private AdmissionStatusTO admissionStatusTO;
	private AdmissionStatusTO statusTO;
	private String templateDescription;
	private boolean displaySemister;
	private String admStatus;
	private boolean onlineAcknowledgement;
	private String serverDownMessage;
	//vibin for status change
	private boolean memo;
	private int maxallotment;
	private int isChallanRecieved;
	private List<ProgramTypeTO> programTypeList;

	public String getServerDownMessage() {
		return serverDownMessage;
	}

	public void setServerDownMessage(String serverDownMessage) {
		this.serverDownMessage = serverDownMessage;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public AdmissionStatusTO getStatusTO() {
		return statusTO;
	}

	public void setStatusTO(AdmissionStatusTO statusTO) {
		this.statusTO = statusTO;
	}

	public AdmissionStatusTO getAdmissionStatusTO() {
		return admissionStatusTO;
	}

	public void setAdmissionStatusTO(AdmissionStatusTO admissionStatusTO) {
		this.admissionStatusTO = admissionStatusTO;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clear()
	{
		this.applicationNo=null;
		this.dateOfBirth=null;
		this.templateDescription = "";
		this.displaySemister=false;
		this.serverDownMessage=null;
	}
	public boolean getDisplaySemister() {
		return displaySemister;
	}

	public void setDisplaySemister(boolean displaySemister) {
		this.displaySemister = displaySemister;
	}

	public void clearadmissionStatusTO(){
		this.admissionStatusTO=null;
	}
	public void clearstatusTO(){
		this.statusTO=null;
	}

	public String getAdmStatus() {
		return admStatus;
	}

	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}

	public boolean isOnlineAcknowledgement() {
		return onlineAcknowledgement;
	}

	public void setOnlineAcknowledgement(boolean onlineAcknowledgement) {
		this.onlineAcknowledgement = onlineAcknowledgement;
	}

	public void setMemo(boolean memo) {
		this.memo = memo;
	}

	public boolean isMemo() {
		return memo;
	}

	public void setMaxallotment(int maxallotment) {
		this.maxallotment = maxallotment;
	}

	public int getMaxallotment() {
		return maxallotment;
	}

	public void setIsChallanRecieved(int isChallanRecieved) {
		this.isChallanRecieved = isChallanRecieved;
	}

	public int getIsChallanRecieved() {
		return isChallanRecieved;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	
}
