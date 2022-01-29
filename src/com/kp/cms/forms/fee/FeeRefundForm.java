package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeRefundTo;

public class FeeRefundForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String challanNo;
	private String refundAmount;
	private String refundDate;
	private String refundMode;
	private List<FeeRefundTo> studentList;
	private String selectedStudent;
	private String studentIdForDisplay;
	private Map<Integer, FeeRefundTo> refundMap;
	private Map<Integer, String> paymentModeMap;
	private String refundId;
	private String challanAmount;
	private String studentId;
	private String challanDate;
	
	

	
	public String getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getChallanAmount() {
		return challanAmount;
	}
	public void setChallanAmount(String challanAmount) {
		this.challanAmount = challanAmount;
	}
	
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public Map<Integer, String> getPaymentModeMap() {
		return paymentModeMap;
	}
	public void setPaymentModeMap(Map<Integer, String> paymentModeMap) {
		this.paymentModeMap = paymentModeMap;
	}
	public String getStudentIdForDisplay() {
		return studentIdForDisplay;
	}
	public void setStudentIdForDisplay(String studentIdForDisplay) {
		this.studentIdForDisplay = studentIdForDisplay;
	}
	public String getSelectedStudent() {
		return selectedStudent;
	}
	public void setSelectedStudent(String selectedStudent) {
		this.selectedStudent = selectedStudent;
	}
	public List<FeeRefundTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<FeeRefundTo> studentList) {
		this.studentList = studentList;
	}
	public Map<Integer, FeeRefundTo> getRefundMap() {
		return refundMap;
	}
	public void setRefundMap(Map<Integer, FeeRefundTo> refundMap) {
		this.refundMap = refundMap;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public String getRefundMode() {
		return refundMode;
	}
	public void setRefundMode(String refundMode) {
		this.refundMode = refundMode;
	}
	
	
	public void reset() {
       this.challanNo=null;
       this.refundAmount=null;
       this.refundDate=null;
       this.refundMode=null;
       this.refundMap=null;
       this.studentList=null;
       this.selectedStudent=null;
       this.studentIdForDisplay=null;
       this.refundId=null;
       this.challanAmount=null;
       this.studentId=null;
       this.challanDate=null;
       super.clear3();
	}
	
	
}
