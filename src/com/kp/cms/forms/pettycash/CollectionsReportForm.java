package com.kp.cms.forms.pettycash;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.AccountNOTo;
import com.kp.cms.to.pettycash.CollectionAmntTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CollectionsReportForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String allORCancel;
	private String today;
	private String userType;
	private String orderofReport;
	private String otherName;
	private List<String> applNoList;
	private List<String> regNoList;
	private String[] regNo1;
	private String[] appiNo;
	private List<AccountNOTo> accNOList;
	private List<AccountNOTo> finalAccList;
	private List<CollectionAmntTO> totalAmounts;
	private List<AccountHeadTO> accList = new ArrayList<AccountHeadTO>();
	private  double totamount=0.0;
	private List<Double> totalAmountList;
	List<AccountHeadTO> totalAccountList;
	private String startDate;
	private String endDate;
	private String applicationNo;
	private String regNo;
	
	public double getTotamount() {
		return totamount;
	}

	public void setTotamount(double totamount) {
		this.totamount = totamount;
	}

	public List<AccountHeadTO> getAccList() {
		return accList;
	}

	public void setAccList(List<AccountHeadTO> accList) {
		this.accList = accList;
	}

	
	public String[] getAppiNo() {
		return appiNo;
	}

	public String[] getRegNo1() {
		return regNo1;
	}

	public void setRegNo1(String[] regNo1) {
		this.regNo1 = regNo1;
	}

	
	public List<CollectionAmntTO> getTotalAmounts() {
		return totalAmounts;
	}

	public void setTotalAmounts(List<CollectionAmntTO> totalAmounts) {
		this.totalAmounts = totalAmounts;
	}

	public List<AccountNOTo> getAccNOList() {
		return accNOList;
	}

	public void setAccNOList(List<AccountNOTo> accNOList) {
		this.accNOList = accNOList;
	}

	public void setAppiNo(String[] appiNo) {
		this.appiNo = appiNo;
	}

	public List<String> getApplNoList() {
		return applNoList;
	}

	

	public void setApplNoList(List<String> applNoList) {
		this.applNoList = applNoList;
	}

	

	

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getAllORCancel() {
		return allORCancel;
	}

	public void setAllORCancel(String allORCancel) {
		this.allORCancel = allORCancel;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOrderofReport() {
		return orderofReport;
	}

	public void setOrderofReport(String orderofReport) {
		this.orderofReport = orderofReport;
	}

	private String[] accNo;
	
	public List<AccountNOTo> getFinalAccList() {
		return finalAccList;
	}

	public void setFinalAccList(List<AccountNOTo> finalAccList) {
		this.finalAccList = finalAccList;
	}

	public List<Double> getTotalAmountList() {
		return totalAmountList;
	}

	public void setTotalAmountList(List<Double> totalAmountList) {
		this.totalAmountList = totalAmountList;
	}

	public List<AccountHeadTO> getTotalAccountList() {
		return totalAccountList;
	}

	public void setTotalAccountList(List<AccountHeadTO> totalAccountList) {
		this.totalAccountList = totalAccountList;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void resetFields() {
		this.accNo=null;
		this.allORCancel=null;
		this.today=null;
		this.orderofReport=null;
		this.userType=null;
		this.appiNo=null;
		this.regNo1=null;
		this.accList=null;
		this.applNoList=null;
		this.accList = new ArrayList<AccountHeadTO>();
		this.accNOList= new  ArrayList<AccountNOTo>();
		this.otherName=null;
		this.endDate=null;	
		this.startDate=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		if(actionErrors.isEmpty()) {
			Date startDate = CommonUtil.ConvertStringToDate(this.startDate);
			Date curdate = new Date();
			if ((startDate.compareTo(curdate) == 1) ||(startDate.compareTo(curdate) == 1)) {
				actionErrors.add("error", new ActionError(CMSConstants.FEE_NO_FUTURE_DATE_));
			}
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
				
		}
		return actionErrors;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String[] getAccNo() {
		return accNo;
	}

	public void setAccNo(String[] accNo) {
		this.accNo = accNo;
	}

	public List<String> getRegNoList() {
		return regNoList;
	}

	public void setRegNoList(List<String> regNoList) {
		this.regNoList = regNoList;
	}

	
	
	
}
