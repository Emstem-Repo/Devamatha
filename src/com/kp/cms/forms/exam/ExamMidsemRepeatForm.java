package com.kp.cms.forms.exam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamMidsemRepeatTO;

public class ExamMidsemRepeatForm extends BaseActionForm {
	
	private int id;
	private String year;
	private Map<Integer, String> examList;
	private Map<Integer, String> midsemExamList;
	private String examId;
	private String examName;
	private String midsemExamName;
	private String isTheory;
	private List<ExamMidsemRepeatTO> midSemRepeatList;
	private String midSemStudentName;
	private String midSemRepeatRegNo;
	private String midSemClassName;
	private String midSemRepeatId;
	private String midSemExamId;
	private String repeatExamId;
	private String isFeeExempted;
	private String feeEndDate;
	private String registerNo;
	private String feePaymentDescription;
	private BigDecimal totalFees;
	private String tempOfflineFeesPaid;
	private String offlineFeePaid;
	private String attemptsCompletedCount;
	private String attemptsAllowed;
	private String dataRejected;
	private String dataApproved;
	private String reason;
	private String isFeesPaid;
	private String onlinePaymentReceipt;
	private String isPaymentFail;
	private String ErrorsFeePaid;
	private String isApplyOnline;
	private String attendancePercentage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map<Integer, String> getExamList() {
		return examList;
	}

	public void setExamList(Map<Integer, String> examList) {
		this.examList = examList;
	}
	
	public Map<Integer, String> getMidsemExamList() {
		return midsemExamList;
	}

	public void setMidsemExamList(Map<Integer, String> midsemExamList) {
		this.midsemExamList = midsemExamList;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getMidsemExamName() {
		return midsemExamName;
	}

	public void setMidsemExamName(String midsemExamName) {
		this.midsemExamName = midsemExamName;
	}

	public String getIsTheory() {
		return isTheory;
	}

	public void setIsTheory(String isTheory) {
		this.isTheory = isTheory;
	}

	public void clearAll()
	{
		this.year=null;
		this.examId=null;
		this.examName=null;
		this.examList=null;
		this.midsemExamList=null;
		this.examName=null;
		this.midsemExamName=null;
		this.isTheory="Yes";
		this.midSemRepeatList=null;
		this.midSemStudentName=null;
		this.midSemRepeatRegNo=null;
		this.midSemClassName=null;
		this.midSemRepeatId=null;
		this.midSemExamId=null;
		this.repeatExamId=null;
		this.isFeeExempted="false";
		this.feePaymentDescription=null;
		this.offlineFeePaid="off";
		this.tempOfflineFeesPaid="off";
		this.isFeesPaid=null;
		this.reason=null;
		this.isPaymentFail=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public List<ExamMidsemRepeatTO> getMidSemRepeatList() {
		return midSemRepeatList;
	}

	public void setMidSemRepeatList(List<ExamMidsemRepeatTO> midSemRepeatList) {
		this.midSemRepeatList = midSemRepeatList;
	}

	public String getMidSemStudentName() {
		return midSemStudentName;
	}

	public void setMidSemStudentName(String midSemStudentName) {
		this.midSemStudentName = midSemStudentName;
	}

	public String getMidSemRepeatRegNo() {
		return midSemRepeatRegNo;
	}

	public void setMidSemRepeatRegNo(String midSemRepeatRegNo) {
		this.midSemRepeatRegNo = midSemRepeatRegNo;
	}

	public String getMidSemClassName() {
		return midSemClassName;
	}

	public void setMidSemClassName(String midSemClassName) {
		this.midSemClassName = midSemClassName;
	}

	public String getMidSemRepeatId() {
		return midSemRepeatId;
	}

	public void setMidSemRepeatId(String midSemRepeatId) {
		this.midSemRepeatId = midSemRepeatId;
	}

	public String getMidSemExamId() {
		return midSemExamId;
	}

	public void setMidSemExamId(String midSemExamId) {
		this.midSemExamId = midSemExamId;
	}

	public String getRepeatExamId() {
		return repeatExamId;
	}

	public void setRepeatExamId(String repeatExamId) {
		this.repeatExamId = repeatExamId;
	}

	public String getIsFeeExempted() {
		return isFeeExempted;
	}

	public void setIsFeeExempted(String isFeeExempted) {
		this.isFeeExempted = isFeeExempted;
	}

	public String getFeeEndDate() {
		return feeEndDate;
	}

	public void setFeeEndDate(String feeEndDate) {
		this.feeEndDate = feeEndDate;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getFeePaymentDescription() {
		return feePaymentDescription;
	}

	public void setFeePaymentDescription(String feePaymentDescription) {
		this.feePaymentDescription = feePaymentDescription;
	}

	public BigDecimal getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(BigDecimal totalFees) {
		this.totalFees = totalFees;
	}

	public String getTempOfflineFeesPaid() {
		return tempOfflineFeesPaid;
	}

	public void setTempOfflineFeesPaid(String tempOfflineFeesPaid) {
		this.tempOfflineFeesPaid = tempOfflineFeesPaid;
	}

	public String getOfflineFeePaid() {
		return offlineFeePaid;
	}

	public void setOfflineFeePaid(String offlineFeePaid) {
		this.offlineFeePaid = offlineFeePaid;
	}

	public String getAttemptsCompletedCount() {
		return attemptsCompletedCount;
	}

	public void setAttemptsCompletedCount(String attemptsCompletedCount) {
		this.attemptsCompletedCount = attemptsCompletedCount;
	}

	public String getAttemptsAllowed() {
		return attemptsAllowed;
	}

	public void setAttemptsAllowed(String attemptsAllowed) {
		this.attemptsAllowed = attemptsAllowed;
	}

	public String getDataRejected() {
		return dataRejected;
	}

	public void setDataRejected(String dataRejected) {
		this.dataRejected = dataRejected;
	}

	public String getDataApproved() {
		return dataApproved;
	}

	public void setDataApproved(String dataApproved) {
		this.dataApproved = dataApproved;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIsFeesPaid() {
		return isFeesPaid;
	}

	public void setIsFeesPaid(String isFeesPaid) {
		this.isFeesPaid = isFeesPaid;
	}

	public String getOnlinePaymentReceipt() {
		return onlinePaymentReceipt;
	}

	public void setOnlinePaymentReceipt(String onlinePaymentReceipt) {
		this.onlinePaymentReceipt = onlinePaymentReceipt;
	}

	public String getIsPaymentFail() {
		return isPaymentFail;
	}

	public void setIsPaymentFail(String isPaymentFail) {
		this.isPaymentFail = isPaymentFail;
	}

	public String getErrorsFeePaid() {
		return ErrorsFeePaid;
	}

	public void setErrorsFeePaid(String errorsFeePaid) {
		ErrorsFeePaid = errorsFeePaid;
	}

	public String getIsApplyOnline() {
		return isApplyOnline;
	}

	public void setIsApplyOnline(String isApplyOnline) {
		this.isApplyOnline = isApplyOnline;
	}

	public String getAttendancePercentage() {
		return attendancePercentage;
	}

	public void setAttendancePercentage(String attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}

		
}
