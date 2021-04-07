package com.kp.cms.forms.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;

public class OnlineExamSuppApplicationForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int onlinePaymentId;
	private int studentId;
	private int stdClassId;
	private String venue ;
	private String venueEngg;
	private String venueOther;
	private String venueMBA;
	private String time;
	private String dateOfExam;
	private String dateOfExamPrev15;
	private String dateOfExamPrev16;
	private String dateOfExamPrev18;
	private String dateOfExamPrev19;
	private String dateOfExamEngg;
	private String dateOfExamMBA;
	private double totalFees;
	private String appliedDate;
	private boolean printCertificateReq;
	private String printData;
	private boolean feesNotConfigured;
	private String msg;
	private int finId;
	private boolean printReceipt;
	private boolean alreadyApplied;
	private List<String> messageList;
	List<OnlinePaymentRecieptsTo> paymentList;
	private int tempOnlinePayId;
	private Boolean participatingConvocation;
	private Boolean guestPassRequired;
	private String relationshipWithGuest;
	private int convocationId;
	private boolean convocationRelation;
	private String convocationDate;
	private String participation;
	private double guestAmount;
	private int passes;
	private Boolean recordExist;
	private int convocationSessionId;
	private String studentName;
	private Boolean passAvailable;
	private Boolean onepassAvailbale;

	
		
	public int getOnlinePaymentId() {
		return onlinePaymentId;
	}
	public void setOnlinePaymentId(int onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}
	
	public double getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
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
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public boolean isFeesNotConfigured() {
		return feesNotConfigured;
	}
	public void setFeesNotConfigured(boolean feesNotConfigured) {
		this.feesNotConfigured = feesNotConfigured;
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
	public int getStdClassId() {
		return stdClassId;
	}
	public void setStdClassId(int stdClassId) {
		this.stdClassId = stdClassId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	
	
	public void resetFields(){
		this.appliedDate=null;
		this.stdClassId=0;
		this.studentId=0;
		this.finId=0;
		this.feesNotConfigured=false;
		this.printData=null;
		this.printCertificateReq=false;
		this.messageList=null;
		this.passes=0;
		super.setSmartCardNo(null);
		super.setValidThruMonth(null);
		super.setValidThruYear(null);
		
	}
	public List<OnlinePaymentRecieptsTo> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<OnlinePaymentRecieptsTo> paymentList) {
		this.paymentList = paymentList;
	}
	public boolean isPrintReceipt() {
		return printReceipt;
	}
	public void setPrintReceipt(boolean printReceipt) {
		this.printReceipt = printReceipt;
	}
	public boolean isAlreadyApplied() {
		return alreadyApplied;
	}
	public void setAlreadyApplied(boolean alreadyApplied) {
		this.alreadyApplied = alreadyApplied;
	}
	public String getDateOfExam() {
		return dateOfExam;
	}
	public void setDateOfExam(String dateOfExam) {
		this.dateOfExam = dateOfExam;
	}
	public int getTempOnlinePayId() {
		return tempOnlinePayId;
	}
	public void setTempOnlinePayId(int tempOnlinePayId) {
		this.tempOnlinePayId = tempOnlinePayId;
	}
	public String getDateOfExamPrev15() {
		return dateOfExamPrev15;
	}
	public void setDateOfExamPrev15(String dateOfExamPrev15) {
		this.dateOfExamPrev15 = dateOfExamPrev15;
	}
	public String getDateOfExamPrev16() {
		return dateOfExamPrev16;
	}
	public void setDateOfExamPrev16(String dateOfExamPrev16) {
		this.dateOfExamPrev16 = dateOfExamPrev16;
	}
	public String getDateOfExamPrev18() {
		return dateOfExamPrev18;
	}
	public void setDateOfExamPrev18(String dateOfExamPrev18) {
		this.dateOfExamPrev18 = dateOfExamPrev18;
	}
	public String getDateOfExamPrev19() {
		return dateOfExamPrev19;
	}
	public void setDateOfExamPrev19(String dateOfExamPrev19) {
		this.dateOfExamPrev19 = dateOfExamPrev19;
	}
	public Boolean getParticipatingConvocation() {
		return participatingConvocation;
	}
	public void setParticipatingConvocation(Boolean participatingConvocation) {
		this.participatingConvocation = participatingConvocation;
	}
	public Boolean getGuestPassRequired() {
		return guestPassRequired;
	}
	public void setGuestPassRequired(Boolean guestPassRequired) {
		this.guestPassRequired = guestPassRequired;
	}
	public String getRelationshipWithGuest() {
		return relationshipWithGuest;
	}
	public void setRelationshipWithGuest(String relationshipWithGuest) {
		this.relationshipWithGuest = relationshipWithGuest;
	}
	public int getConvocationId() {
		return convocationId;
	}
	public void setConvocationId(int convocationId) {
		this.convocationId = convocationId;
	}
	public boolean isConvocationRelation() {
		return convocationRelation;
	}
	public void setConvocationRelation(boolean convocationRelation) {
		this.convocationRelation = convocationRelation;
	}
	public String getConvocationDate() {
		return convocationDate;
	}
	public void setConvocationDate(String convocationDate) {
		this.convocationDate = convocationDate;
	}
	public String getParticipation() {
		return participation;
	}
	public void setParticipation(String participation) {
		this.participation = participation;
	}
	public double getGuestAmount() {
		return guestAmount;
	}
	public void setGuestAmount(double guestAmount) {
		this.guestAmount = guestAmount;
	}
	public int getPasses() {
		return passes;
	}
	public void setPasses(int passes) {
		this.passes = passes;
	}
	public Boolean getRecordExist() {
		return recordExist;
	}
	public void setRecordExist(Boolean recordExist) {
		this.recordExist = recordExist;
	}
	public int getConvocationSessionId() {
		return convocationSessionId;
	}
	public void setConvocationSessionId(int convocationSessionId) {
		this.convocationSessionId = convocationSessionId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Boolean getPassAvailable() {
		return passAvailable;
	}
	public void setPassAvailable(Boolean passAvailable) {
		this.passAvailable = passAvailable;
	}
	public Boolean getOnepassAvailbale() {
		return onepassAvailbale;
	}
	public void setOnepassAvailbale(Boolean onepassAvailbale) {
		this.onepassAvailbale = onepassAvailbale;
	}
	public String getDateOfExamEngg() {
		return dateOfExamEngg;
	}
	public void setDateOfExamEngg(String dateOfExamEngg) {
		this.dateOfExamEngg = dateOfExamEngg;
	}
	public String getVenueEngg() {
		return venueEngg;
	}
	public void setVenueEngg(String venueEngg) {
		this.venueEngg = venueEngg;
	}
	public String getVenueOther() {
		return venueOther;
	}
	public void setVenueOther(String venueOther) {
		this.venueOther = venueOther;
	}
	public String getVenueMBA() {
		return venueMBA;
	}
	public void setVenueMBA(String venueMBA) {
		this.venueMBA = venueMBA;
	}
	public String getDateOfExamMBA() {
		return dateOfExamMBA;
	}
	public void setDateOfExamMBA(String dateOfExamMBA) {
		this.dateOfExamMBA = dateOfExamMBA;
	}
	
	

}
