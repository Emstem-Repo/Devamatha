package com.kp.cms.to.exam;

import java.io.Serializable;
import java.math.BigDecimal;

import com.kp.cms.to.phd.PhdStudentPanelMemberTO;


public class ExamMidsemRepeatTO implements Serializable ,Comparable<ExamMidsemRepeatTO>{
	
	private int id;
	private int studentId;
	private String studentName;
	private int classId;
	private String className;
	private int midsemExamId;
	private String midsemExamName;
	private int examId;
	private String examName;
	private BigDecimal totalAmount;
	private String isDownload;
	private String isFeePaid;
	private String subject;
	private int subjectId;
	private String subjectCode;
	private String isApplied;
	private BigDecimal amount;
	private String isApproved;
	private String checked;
	private String tempChecked;
	private Float attenPersent;
	private String tempCheckedApprove;
	private String checkedApprove;
	private int onlinePaymentReceipt;
	private String isPaymentFail;
	private String isApplyOnline;
	private String tempCheckedReject;
	private String checkedReject;
	
	 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getMidsemExamId() {
		return midsemExamId;
	}
	public void setMidsemExamId(int midsemExamId) {
		this.midsemExamId = midsemExamId;
	}
	public String getMidsemExamName() {
		return midsemExamName;
	}
	public void setMidsemExamName(String midsemExamName) {
		this.midsemExamName = midsemExamName;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}
	public String getIsFeePaid() {
		return isFeePaid;
	}
	public void setIsFeePaid(String isFeePaid) {
		this.isFeePaid = isFeePaid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getIsApplied() {
		return isApplied;
	}
	public void setIsApplied(String isApplied) {
		this.isApplied = isApplied;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public Float getAttenPersent() {
		return attenPersent;
	}
	public void setAttenPersent(Float attenPersent) {
		this.attenPersent = attenPersent;
	}
	public String getTempCheckedApprove() {
		return tempCheckedApprove;
	}
	public void setTempCheckedApprove(String tempCheckedApprove) {
		this.tempCheckedApprove = tempCheckedApprove;
	}
	public String getCheckedApprove() {
		return checkedApprove;
	}
	public void setCheckedApprove(String checkedApprove) {
		this.checkedApprove = checkedApprove;
	}
	public int getOnlinePaymentReceipt() {
		return onlinePaymentReceipt;
	}
	public void setOnlinePaymentReceipt(int onlinePaymentReceipt) {
		this.onlinePaymentReceipt = onlinePaymentReceipt;
	}
	public String getIsPaymentFail() {
		return isPaymentFail;
	}
	public void setIsPaymentFail(String isPaymentFail) {
		this.isPaymentFail = isPaymentFail;
	}
	public String getIsApplyOnline() {
		return isApplyOnline;
	}
	public void setIsApplyOnline(String isApplyOnline) {
		this.isApplyOnline = isApplyOnline;
	}
	@Override
	public int compareTo(ExamMidsemRepeatTO arg0) {
		if(arg0 instanceof ExamMidsemRepeatTO && arg0.getSubjectCode()!=null){
			return this.getSubjectCode().compareTo(arg0.subjectCode);
		}
		return 0;
	}
	public String getTempCheckedReject() {
		return tempCheckedReject;
	}
	public void setTempCheckedReject(String tempCheckedReject) {
		this.tempCheckedReject = tempCheckedReject;
	}
	public String getCheckedReject() {
		return checkedReject;
	}
	public void setCheckedReject(String checkedReject) {
		this.checkedReject = checkedReject;
	}
}
