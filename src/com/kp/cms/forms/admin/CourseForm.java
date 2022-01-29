package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.SeatAllocationTO;

public class CourseForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseName;
	private String maxIntake;
	private String isAutonomous;
	private List <SeatAllocationTO> seatAllocationList; 
	private String courseCode;
	private int total;
	private String courseId;
	private String programId;
	private String programTypeId;
	private String origCourseName;
	private String origCourseCode;
	private String payCode;
	private String amount;
	private String isWorkExperienceRequired;
	private int duplId;
	private String origProgTypeId;
	private String origProgId;
	private String isDetailMarkPresent;
	private String certificateCourseName;
	private String isWorkExpMandatory;
	private String isAppearInOnline;
	private String isApplicationProcessSms;
	private String onlyForApplication;
	private String courseMarksCard;
	private String bankName;
	private String bankNameFull;
	private String bankIncludeSection;
	private String commencementDate;
	private String intApplicationFees;
	private String currencyId;
	private List <CourseTO> deptList; 
	private String courseid;
	private boolean isSelected;
	private Map<Integer,String> workLocationMap;
	private String noOfMidSemAttempts;
	private String dateTime;
	private String generalFee;
	private String casteFee;
	private String aided;
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getGeneralFee() {
		return generalFee;
	}
	public void setGeneralFee(String generalFee) {
		this.generalFee = generalFee;
	}
	public String getCasteFee() {
		return casteFee;
	}
	public void setCasteFee(String casteFee) {
		this.casteFee = casteFee;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
	}
	public String getIsAutonomous() {
		return isAutonomous;
	}
	public void setIsAutonomous(String isAutonomous) {
		this.isAutonomous = isAutonomous;
	}
	
	public List<SeatAllocationTO> getSeatAllocationList() {
		return seatAllocationList;
	}
	public void setSeatAllocationList(List<SeatAllocationTO> seatAllocationList) {
		this.seatAllocationList = seatAllocationList;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	
	public String getOrigCourseName() {
		return origCourseName;
	}
	public void setOrigCourseName(String origCourseName) {
		this.origCourseName = origCourseName;
	}
	public String getOrigCourseCode() {
		return origCourseCode;
	}
	public void setOrigCourseCode(String origCourseCode) {
		this.origCourseCode = origCourseCode;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		courseName = null;
		maxIntake = null;
		isAutonomous = null;
		courseCode = null;
		total = 0;
		courseId = null;
		programId = null;
		programTypeId = null;
		payCode = null;
		amount = null;
		isWorkExperienceRequired = null;
		isDetailMarkPresent = null;
		certificateCourseName = null;
		isWorkExpMandatory="false";
		isAppearInOnline="true";
		isApplicationProcessSms="true";
		onlyForApplication="false";
		courseMarksCard=null;
		bankName=null;
		bankNameFull=null;
		bankIncludeSection="false";
		commencementDate=null;
		intApplicationFees=null;
		currencyId=null;
		noOfMidSemAttempts =null;
		dateTime=null;
		casteFee=null;
		generalFee=null;

		
	}
	
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIsWorkExperienceRequired() {
		return isWorkExperienceRequired;
	}
	public void setIsWorkExperienceRequired(String isWorkExperienceRequired) {
		this.isWorkExperienceRequired = isWorkExperienceRequired;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigProgTypeId() {
		return origProgTypeId;
	}
	public void setOrigProgTypeId(String origProgTypeId) {
		this.origProgTypeId = origProgTypeId;
	}
	public String getOrigProgId() {
		return origProgId;
	}
	public void setOrigProgId(String origProgId) {
		this.origProgId = origProgId;
	}
	public String getIsDetailMarkPresent() {
		return isDetailMarkPresent;
	}
	public void setIsDetailMarkPresent(String isDetailMarkPresent) {
		this.isDetailMarkPresent = isDetailMarkPresent;
	}
	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}
	public String getCertificateCourseName() {
		return certificateCourseName;
	}
	public String getIsWorkExpMandatory() {
		return isWorkExpMandatory;
	}
	public void setIsWorkExpMandatory(String isWorkExpMandatory) {
		this.isWorkExpMandatory = isWorkExpMandatory;
	}
	public String getIsAppearInOnline() {
		return isAppearInOnline;
	}
	public void setIsAppearInOnline(String isAppearInOnline) {
		this.isAppearInOnline = isAppearInOnline;
	}
	public String getIsApplicationProcessSms() {
		return isApplicationProcessSms;
	}
	public void setIsApplicationProcessSms(String isApplicationProcessSms) {
		this.isApplicationProcessSms = isApplicationProcessSms;
	}
	public String getOnlyForApplication() {
		return onlyForApplication;
	}
	public void setOnlyForApplication(String onlyForApplication) {
		this.onlyForApplication = onlyForApplication;
	}
	public String getCourseMarksCard() {
		return courseMarksCard;
	}
	public void setCourseMarksCard(String courseMarksCard) {
		this.courseMarksCard = courseMarksCard;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNameFull() {
		return bankNameFull;
	}
	public void setBankNameFull(String bankNameFull) {
		this.bankNameFull = bankNameFull;
	}
	public String getBankIncludeSection() {
		return bankIncludeSection;
	}
	public void setBankIncludeSection(String bankIncludeSection) {
		this.bankIncludeSection = bankIncludeSection;
	}
	public String getCommencementDate() {
		return commencementDate;
	}
	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}
	public String getIntApplicationFees() {
		return intApplicationFees;
	}
	public void setIntApplicationFees(String intApplicationFees) {
		this.intApplicationFees = intApplicationFees;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public List<CourseTO> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<CourseTO> deptList) {
		this.deptList = deptList;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getNoOfMidSemAttempts() {
		return noOfMidSemAttempts;
	}
	public void setNoOfMidSemAttempts(String noOfMidSemAttempts) {
		this.noOfMidSemAttempts = noOfMidSemAttempts;
	}
	public String getAided() {
		return aided;
	}
	public void setAided(String aided) {
		this.aided = aided;
	}	
}
