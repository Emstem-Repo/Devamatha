package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificateRequestOnlineTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.employee.EmployeeInfoTONew;
import com.kp.cms.to.pettycash.StudentDetailsTo;

public class CertificateRequestOnlineForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String month;
	private String year;
	private String semester;
	private int halfLength;
	private boolean feesNotConfigured;
	private double totalFees;
	private String msg;
	private int finId;
	private int studentId;
	private int onlinePaymentId;
	private boolean printCertificateReq;
	private String printData;
	private boolean displayButton;
	private String marksCardLength;
	private String mode;
	private String focusValue;
	private String dummyAppliedDate;
	private String dummyRegisterNo;
	private String dummyFirstName;
	private String dummyClassName;

	private Map<Integer,String> classMap;

	private List<CertificateRequestOnlineTO> studentToList;
	
	
	private String tempClassId;
	private String tempClassName;
	private String tempRegisterNo;
	private String tempcourseId;
	private String tempProgId;
	private String tempFirstName;
	private String tempProgTypeId;
	private String certificateId;
	private String startDate;
	private String endDate;
	private String searchCertificate;
	private String regNo;
	private String addMoreCertId;
	private String selectedCertId;
	private String downloadAvailable;
	private String rejectId;
	private String certId;
	private String rejectReason;
	private String isReject;
	private String description;
	private String certDescId;
	private String templateId;
	private String printPage;
	private List<String> messageList;
	
	private List<CertificateDetailsTo> certificateDetails;
	private CertificateRequestOnlineTO certificateRequestOnlineTO;
	private List<ProgramTypeTO> programTypeList;
	private String remarkId;
	private String adminRemarks;
	private String searchType;
	private Map<String,String> searchTypeMap;
	private String tempRegNo;
	private String countSelection;
	private String isIdCardTest;
	private String isDescriptionDisplayed;
	
	
	
	public CertificateRequestOnlineForm() {
	    this.certificateRequestOnlineTO=new CertificateRequestOnlineTO();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public CertificateRequestOnlineTO getCertificateRequestOnlineTO() {
		return certificateRequestOnlineTO;
	}
	public void setCertificateRequestOnlineTO(
			CertificateRequestOnlineTO certificateRequestOnlineTO) {
		this.certificateRequestOnlineTO = certificateRequestOnlineTO;
	}
	public List<CertificateDetailsTo> getCertificateDetails() {
		return certificateDetails;
	}
	public void setCertificateDetails(List<CertificateDetailsTo> certificateDetails) {
		this.certificateDetails = certificateDetails;
	}
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	public boolean isFeesNotConfigured() {
		return feesNotConfigured;
	}
	public void setFeesNotConfigured(boolean feesNotConfigured) {
		this.feesNotConfigured = feesNotConfigured;
	}
	public double getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getFinId() {
		return finId;
	}
	public void setFinId(int finId) {
		this.finId = finId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getOnlinePaymentId() {
		return onlinePaymentId;
	}
	public void setOnlinePaymentId(int onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}
	public boolean isPrintCertificateReq() {
		return printCertificateReq;
	}
	public void setPrintCertificateReq(boolean printCertificateReq) {
		this.printCertificateReq = printCertificateReq;
	}
	public String getPrintData() {
		return printData;
	}
	public void setPrintData(String printData) {
		this.printData = printData;
	}
	public boolean isDisplayButton() {
		return displayButton;
	}
	public void setDisplayButton(boolean displayButton) {
		this.displayButton = displayButton;
	}
	
	public void resetFields(){
//		this.supplementaryImprovement=null;
		
		super.setCourseId(null);
		
		this.feesNotConfigured=false;
		this.halfLength=0;
		this.totalFees=0;
		this.msg=null;
		this.finId=0;
		this.onlinePaymentId=0;
		this.printCertificateReq=false;
		this.printData=null;
		this.displayButton=false;
		super.setSmartCardNo(null);
		super.setValidThruMonth(null);
		super.setValidThruYear(null);
		this.semester=null;
		this.month=null;
		this.type=null;
		this.dummyAppliedDate=null;
		this.dummyClassName=null;
		this.dummyFirstName=null;
		this.dummyRegisterNo=null;
		this.tempFirstName=null;
		this.startDate=null;
		this.endDate=null;
		this.tempRegisterNo=null;
		this.certificateId=null;
		this.studentId=0;
		this.printData=null;
		this.focusValue=null;
		this.marksCardLength=null;
		this.regNo=null;
		this.downloadAvailable="0";
		this.rejectId=null;
		this.rejectReason=null;
		this.printPage = null;
		this.searchType="pending";
		this.tempRegNo=null;
		this.countSelection="0";
		this.isIdCardTest="false";
		this.isDescriptionDisplayed="false";
		this.description=null;
		super.setDate(null);
	}
	
	public String getMarksCardLength() {
		return marksCardLength;
	}
	public void setMarksCardLength(String marksCardLength) {
		this.marksCardLength = marksCardLength;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}

	public String getDummyAppliedDate() {
		return dummyAppliedDate;
	}

	public void setDummyAppliedDate(String dummyAppliedDate) {
		this.dummyAppliedDate = dummyAppliedDate;
	}

	public String getDummyRegisterNo() {
		return dummyRegisterNo;
	}

	public void setDummyRegisterNo(String dummyRegisterNo) {
		this.dummyRegisterNo = dummyRegisterNo;
	}

	public String getDummyFirstName() {
		return dummyFirstName;
	}

	public void setDummyFirstName(String dummyFirstName) {
		this.dummyFirstName = dummyFirstName;
	}

	public String getDummyClassName() {
		return dummyClassName;
	}

	public void setDummyClassName(String dummyClassName) {
		this.dummyClassName = dummyClassName;
	}

	public String getTempRegisterNo() {
		return tempRegisterNo;
	}

	public void setTempRegisterNo(String tempRegisterNo) {
		this.tempRegisterNo = tempRegisterNo;
	}

	public String getTempClassId() {
		return tempClassId;
	}

	public void setTempClassId(String tempClassId) {
		this.tempClassId = tempClassId;
	}

	public String getTempClassName() {
		return tempClassName;
	}

	public void setTempClassName(String tempClassName) {
		this.tempClassName = tempClassName;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public String getTempcourseId() {
		return tempcourseId;
	}

	public void setTempcourseId(String tempcourseId) {
		this.tempcourseId = tempcourseId;
	}

	public String getTempProgId() {
		return tempProgId;
	}

	public void setTempProgId(String tempProgId) {
		this.tempProgId = tempProgId;
	}

	public String getTempFirstName() {
		return tempFirstName;
	}

	public void setTempFirstName(String tempFirstName) {
		this.tempFirstName = tempFirstName;
	}

	public String getTempProgTypeId() {
		return tempProgTypeId;
	}

	public void setTempProgTypeId(String tempProgTypeId) {
		this.tempProgTypeId = tempProgTypeId;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
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

	public String getSearchCertificate() {
		return searchCertificate;
	}

	public void setSearchCertificate(String searchCertificate) {
		this.searchCertificate = searchCertificate;
	}

	public List<CertificateRequestOnlineTO> getStudentToList() {
		return studentToList;
	}

	public void setStudentToList(List<CertificateRequestOnlineTO> studentToList) {
		this.studentToList = studentToList;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getAddMoreCertId() {
		return addMoreCertId;
	}

	public void setAddMoreCertId(String addMoreCertId) {
		this.addMoreCertId = addMoreCertId;
	}

	public String getSelectedCertId() {
		return selectedCertId;
	}

	public void setSelectedCertId(String selectedCertId) {
		this.selectedCertId = selectedCertId;
	}

	public String getDownloadAvailable() {
		return downloadAvailable;
	}

	public void setDownloadAvailable(String downloadAvailable) {
		this.downloadAvailable = downloadAvailable;
	}

	
	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	
	public String getRejectId() {
		return rejectId;
	}

	public void setRejectId(String rejectId) {
		this.rejectId = rejectId;
	}

	public String getIsReject() {
		return isReject;
	}

	public void setIsReject(String isReject) {
		this.isReject = isReject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCertDescId() {
		return certDescId;
	}

	public void setCertDescId(String certDescId) {
		this.certDescId = certDescId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getPrintPage() {
		return printPage;
	}

	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public String getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(String remarkId) {
		this.remarkId = remarkId;
	}

	public String getAdminRemarks() {
		return adminRemarks;
	}

	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public Map<String, String> getSearchTypeMap() {
		return searchTypeMap;
	}

	public void setSearchTypeMap(Map<String, String> searchTypeMap) {
		this.searchTypeMap = searchTypeMap;
	}

	public String getTempRegNo() {
		return tempRegNo;
	}

	public void setTempRegNo(String tempRegNo) {
		this.tempRegNo = tempRegNo;
	}

	public String getCountSelection() {
		return countSelection;
	}

	public void setCountSelection(String countSelection) {
		this.countSelection = countSelection;
	}

	public String getIsIdCardTest() {
		return isIdCardTest;
	}

	public void setIsIdCardTest(String isIdCardTest) {
		this.isIdCardTest = isIdCardTest;
	}

	public String getIsDescriptionDisplayed() {
		return isDescriptionDisplayed;
	}

	public void setIsDescriptionDisplayed(String isDescriptionDisplayed) {
		this.isDescriptionDisplayed = isDescriptionDisplayed;
	}

}
