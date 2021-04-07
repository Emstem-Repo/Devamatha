package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CertificateCourseTO;

public class NewStudentCertificateCourseForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String semType;
	private boolean extraCurricularApplied;
	private boolean certificateApplied;
	List<CertificateCourseTO> mandatorycourseList;
	List<CertificateCourseTO> optionalCourseList;
	List<CertificateCourseTO> extraCurricularCourseList;
	private String optionalId;
	private String extraCurricularId;
	private int certificateCourseId;
	private String certificateCourseName;
	private int studentId;
	private int groupId;
	private String printCourse;
	private String searchItem;
	private boolean currentlyApplied;
	private String certificateName;
	private String extraCertificateName;
	private int feeFinancialYearId;
	private String smartCardNo;
	private boolean isPaymentRequired;
	private String msg;
	private double feeAmt;
	private boolean isOnline;
	private String dob;
	private String originalDob;
	private String validThruMonth;
	private String validThruYear;
	private boolean applyWithoutPayment;
	private boolean displayChallan;
	
	public String getSemType() {
		return semType;
	}

	public void setSemType(String semType) {
		this.semType = semType;
	}

	private Map<Integer,Integer> schemeMap;
	
	public Map<Integer, Integer> getSchemeMap() {
		return schemeMap;
	}

	public void setSchemeMap(Map<Integer, Integer> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public boolean isExtraCurricularApplied() {
		return extraCurricularApplied;
	}

	public void setExtraCurricularApplied(boolean extraCurricularApplied) {
		this.extraCurricularApplied = extraCurricularApplied;
	}

	public boolean isCertificateApplied() {
		return certificateApplied;
	}

	public void setCertificateApplied(boolean certificateApplied) {
		this.certificateApplied = certificateApplied;
	}

	public List<CertificateCourseTO> getMandatorycourseList() {
		return mandatorycourseList;
	}

	public void setMandatorycourseList(List<CertificateCourseTO> mandatorycourseList) {
		this.mandatorycourseList = mandatorycourseList;
	}

	public List<CertificateCourseTO> getOptionalCourseList() {
		return optionalCourseList;
	}

	public void setOptionalCourseList(List<CertificateCourseTO> optionalCourseList) {
		this.optionalCourseList = optionalCourseList;
	}

	public List<CertificateCourseTO> getExtraCurricularCourseList() {
		return extraCurricularCourseList;
	}

	public void setExtraCurricularCourseList(
			List<CertificateCourseTO> extraCurricularCourseList) {
		this.extraCurricularCourseList = extraCurricularCourseList;
	}

	public String getOptionalId() {
		return optionalId;
	}

	public void setOptionalId(String optionalId) {
		this.optionalId = optionalId;
	}

	public String getExtraCurricularId() {
		return extraCurricularId;
	}

	public void setExtraCurricularId(String extraCurricularId) {
		this.extraCurricularId = extraCurricularId;
	}

	public int getCertificateCourseId() {
		return certificateCourseId;
	}

	public void setCertificateCourseId(int certificateCourseId) {
		this.certificateCourseId = certificateCourseId;
	}

	public String getCertificateCourseName() {
		return certificateCourseName;
	}

	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getPrintCourse() {
		return printCourse;
	}

	public void setPrintCourse(String printCourse) {
		this.printCourse = printCourse;
	}
	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getExtraCertificateName() {
		return extraCertificateName;
	}

	public void setExtraCertificateName(String extraCertificateName) {
		this.extraCertificateName = extraCertificateName;
	}

	/**
	 * 
	 */
	public void resetFields(){
		super.setRegNo(null);
		super.setSchemeNo(null);
		this.schemeMap=null;
		this.semType=null;
		this.certificateApplied=false;
		this.extraCurricularApplied=false;
		this.mandatorycourseList=null;
		this.optionalCourseList=null;
		this.extraCurricularCourseList=null;
		this.optionalId=null;
		this.extraCurricularId=null;
		this.certificateCourseId=0;
		this.groupId=0;
		this.printCourse=null;
		this.searchItem=null;
		this.currentlyApplied=false;
		this.feeFinancialYearId=0;
		this.smartCardNo=null;
		this.isPaymentRequired=false;
		this.msg=null;
		this.feeAmt=0;
		this.isOnline=false;
		this.dob=null;
		this.originalDob=null;
		this.validThruMonth=null;
		this.validThruYear=null;
		this.applyWithoutPayment=false;
		this.displayChallan=false;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public boolean isCurrentlyApplied() {
		return currentlyApplied;
	}

	public void setCurrentlyApplied(boolean currentlyApplied) {
		this.currentlyApplied = currentlyApplied;
	}

	public int getFeeFinancialYearId() {
		return feeFinancialYearId;
	}

	public void setFeeFinancialYearId(int feeFinancialYearId) {
		this.feeFinancialYearId = feeFinancialYearId;
	}

	public String getSmartCardNo() {
		return smartCardNo;
	}

	public void setSmartCardNo(String smartCardNo) {
		this.smartCardNo = smartCardNo;
	}

	public boolean isPaymentRequired() {
		return isPaymentRequired;
	}

	public void setPaymentRequired(boolean isPaymentRequired) {
		this.isPaymentRequired = isPaymentRequired;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public double getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(double feeAmt) {
		this.feeAmt = feeAmt;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getOriginalDob() {
		return originalDob;
	}

	public void setOriginalDob(String originalDob) {
		this.originalDob = originalDob;
	}

	public String getValidThruMonth() {
		return validThruMonth;
	}

	public void setValidThruMonth(String validThruMonth) {
		this.validThruMonth = validThruMonth;
	}

	public String getValidThruYear() {
		return validThruYear;
	}

	public void setValidThruYear(String validThruYear) {
		this.validThruYear = validThruYear;
	}

	public boolean isApplyWithoutPayment() {
		return applyWithoutPayment;
	}

	public void setApplyWithoutPayment(boolean applyWithoutPayment) {
		this.applyWithoutPayment = applyWithoutPayment;
	}

	public boolean isDisplayChallan() {
		return displayChallan;
	}

	public void setDisplayChallan(boolean displayChallan) {
		this.displayChallan = displayChallan;
	}

}