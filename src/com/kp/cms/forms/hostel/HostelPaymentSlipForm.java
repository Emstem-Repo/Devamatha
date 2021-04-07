package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelPaymentSlipTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelPaymentSlipForm extends BaseActionForm{
	
	private String applicationNo;
	private String registerNo;
	private String staffId;
	private String rollNo;
	private List<HostelTO> hostelTOList;
	private String searchBy;
	private String isHostelFee;
	private String isHostelFine;
	private String billNo;
	private String date; // date of slip generation
	private boolean feeChallanPrinted;
	private boolean fineChallanPrinted;
	private String hlApplicationFormId;
	private String studentOrStaffId;
	private String studentOrStaffName;
	private List<HostelPaymentSlipTO>billNumberList;
	private HostelPaymentSlipTO hostelPaymentSlipTO;
	
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}
	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}
	
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public String getIsHostelFee() {
		return isHostelFee;
	}
	public void setIsHostelFee(String isHostelFee) {
		this.isHostelFee = isHostelFee;
	}
	public String getIsHostelFine() {
		return isHostelFine;
	}
	public void setIsHostelFine(String isHostelFine) {
		this.isHostelFine = isHostelFine;
	}
	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isFeeChallanPrinted() {
		return feeChallanPrinted;
	}
	public void setFeeChallanPrinted(boolean feeChallanPrinted) {
		this.feeChallanPrinted = feeChallanPrinted;
	}
	public boolean isFineChallanPrinted() {
		return fineChallanPrinted;
	}
	public void setFineChallanPrinted(boolean fineChallanPrinted) {
		this.fineChallanPrinted = fineChallanPrinted;
	}
	public HostelPaymentSlipTO getHostelPaymentSlipTO() {
		return hostelPaymentSlipTO;
	}
	public void setHostelPaymentSlipTO(HostelPaymentSlipTO hostelPaymentSlipTO) {
		this.hostelPaymentSlipTO = hostelPaymentSlipTO;
	}
	
	public String getHlApplicationFormId() {
		return hlApplicationFormId;
	}
	public void setHlApplicationFormId(String hlApplicationFormId) {
		this.hlApplicationFormId = hlApplicationFormId;
	}
	
	public String getStudentOrStaffId() {
		return studentOrStaffId;
	}
	public void setStudentOrStaffId(String studentOrStaffId) {
		this.studentOrStaffId = studentOrStaffId;
	}
	public String getStudentOrStaffName() {
		return studentOrStaffName;
	}
	public void setStudentOrStaffName(String studentOrStaffName) {
		this.studentOrStaffName = studentOrStaffName;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() 
	{
		super.setHostelId(null);
		applicationNo =null;
		registerNo =null;
		staffId =null;
		rollNo =null;
		searchBy=null;
	}
	public void resetFields2() 
	{
		billNo=null;
		this.searchBy=null;
	}
	public List<HostelPaymentSlipTO> getBillNumberList() {
		return billNumberList;
	}
	public void setBillNumberList(List<HostelPaymentSlipTO> billNumberList) {
		this.billNumberList = billNumberList;
	}

	
}
