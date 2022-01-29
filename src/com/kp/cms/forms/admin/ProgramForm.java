package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class ProgramForm  extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String isActive;
	private String programTypeId;
	private String programCode;
	private String origProgramCode;
	private String origProgramName;
	private String origprogramTypeId;
	private int duplPgmId;
	private String motherTongue;
	private String secondLanguage;
	private String displayLanguageKnown;
	private String familyBackground;
	private String heightWeight;
	private String entranceDetails;
	private String lateralDetails;
	private String displayTrainingCourse;
	private String transferCourse;
	private String additionalInfo;
	private String extraDetails;
	private String isTcDisplay;
	private String isRegistartionNo;
	private String isOpen;
	private String isExamCenterRequired;
	private String stream;
	private String programNameCertificate;

	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	
	public String getOrigProgramCode() {
		return origProgramCode;
	}
	public void setOrigProgramCode(String origProgramCode) {
		this.origProgramCode = origProgramCode;
	}
	public String getOrigProgramName() {
		return origProgramName;
	}
	public void setOrigProgramName(String origProgramName) {
		this.origProgramName = origProgramName;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		name = null;
		programTypeId = null;
		programCode = null;
		motherTongue = null;
		secondLanguage = null;
		displayLanguageKnown = null;
		familyBackground = null;
		heightWeight = null;
		entranceDetails = null;
		lateralDetails = null;
		displayTrainingCourse = null;
		transferCourse = null;
		additionalInfo = null;
		extraDetails = null;
		isTcDisplay = null;
		isRegistartionNo = null;
		isExamCenterRequired = null;
		isOpen = null;
		stream = null;
		programNameCertificate = null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getOrigprogramTypeId() {
		return origprogramTypeId;
	}
	public void setOrigprogramTypeId(String origprogramTypeId) {
		this.origprogramTypeId = origprogramTypeId;
	}
	public int getDuplPgmId() {
		return duplPgmId;
	}
	public void setDuplPgmId(int duplPgmId) {
		this.duplPgmId = duplPgmId;
	}
	/**
	 * @return the motherTongue
	 */
	public String getMotherTongue() {
		return motherTongue;
	}
	/**
	 * @param motherTongue the motherTongue to set
	 */
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	/**
	 * @return the secondLanguage
	 */
	public String getSecondLanguage() {
		return secondLanguage;
	}
	/**
	 * @param secondLanguage the secondLanguage to set
	 */
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	/**
	 * @return the displayLanguageKnown
	 */
	public String getDisplayLanguageKnown() {
		return displayLanguageKnown;
	}
	/**
	 * @param displayLanguageKnown the displayLanguageKnown to set
	 */
	public void setDisplayLanguageKnown(String displayLanguageKnown) {
		this.displayLanguageKnown = displayLanguageKnown;
	}
	/**
	 * @return the familyBackground
	 */
	public String getFamilyBackground() {
		return familyBackground;
	}
	/**
	 * @param familyBackground the familyBackground to set
	 */
	public void setFamilyBackground(String familyBackground) {
		this.familyBackground = familyBackground;
	}
	/**
	 * @return the heightWeight
	 */
	public String getHeightWeight() {
		return heightWeight;
	}
	/**
	 * @param heightWeight the heightWeight to set
	 */
	public void setHeightWeight(String heightWeight) {
		this.heightWeight = heightWeight;
	}
	/**
	 * @return the entranceDetails
	 */
	public String getEntranceDetails() {
		return entranceDetails;
	}
	/**
	 * @param entranceDetails the entranceDetails to set
	 */
	public void setEntranceDetails(String entranceDetails) {
		this.entranceDetails = entranceDetails;
	}
	/**
	 * @return the lateralDetails
	 */
	public String getLateralDetails() {
		return lateralDetails;
	}
	/**
	 * @param lateralDetails the lateralDetails to set
	 */
	public void setLateralDetails(String lateralDetails) {
		this.lateralDetails = lateralDetails;
	}
	/**
	 * @return the displayTrainingCourse
	 */
	public String getDisplayTrainingCourse() {
		return displayTrainingCourse;
	}
	/**
	 * @param displayTrainingCourse the displayTrainingCourse to set
	 */
	public void setDisplayTrainingCourse(String displayTrainingCourse) {
		this.displayTrainingCourse = displayTrainingCourse;
	}
	/**
	 * @return the transferCourse
	 */
	public String getTransferCourse() {
		return transferCourse;
	}
	/**
	 * @param transferCourse the transferCourse to set
	 */
	public void setTransferCourse(String transferCourse) {
		this.transferCourse = transferCourse;
	}
	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	/**
	 * @return the extraDetails
	 */
	public String getExtraDetails() {
		return extraDetails;
	}
	/**
	 * @param extraDetails the extraDetails to set
	 */
	public void setExtraDetails(String extraDetails) {
		this.extraDetails = extraDetails;
	}
	public String getIsTcDisplay() {
		return isTcDisplay;
	}
	public void setIsTcDisplay(String isTcDisplay) {
		this.isTcDisplay = isTcDisplay;
	}
	public String getIsRegistartionNo() {
		return isRegistartionNo;
	}
	public void setIsRegistartionNo(String isRegistartionNo) {
		this.isRegistartionNo = isRegistartionNo;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getIsExamCenterRequired() {
		return isExamCenterRequired;
	}
	public void setIsExamCenterRequired(String isExamCenterRequired) {
		this.isExamCenterRequired = isExamCenterRequired;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public void setProgramNameCertificate(String programNameCertificate) {
		this.programNameCertificate = programNameCertificate;
	}
	public String getProgramNameCertificate() {
		return programNameCertificate;
	}
	
	
}
