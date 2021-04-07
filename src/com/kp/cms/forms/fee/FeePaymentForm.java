package com.kp.cms.forms.fee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.FeePaymentDetailAmount;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.fee.FeeAmountTO;
import com.kp.cms.to.fee.FeeCategoryTo;
import com.kp.cms.to.fee.FeeDisplayTO;
import com.kp.cms.to.fee.FeePaymentDetailFeeGroupTO;
import com.kp.cms.to.fee.FeePaymentDisplayTO;
import com.kp.cms.to.fee.FeePaymentEditTO;
import com.kp.cms.to.fee.FeeStudentDetailTO;
import com.kp.cms.to.fee.PrintChalanTO;

/**
 * This form is for FeePayments related.
 */
public class FeePaymentForm extends BaseActionForm{

	private static final long serialVersionUID = 1L;
	
	private String studentId;
	private String applicationId;
	private String registrationNo;
	private String paymentDate;
	private String admittedThrough;
	private String admittedThroughName;
	private String feeGroupSelectedIndex;
	private String optionalFeeSelectedIndex;
	private String semeterSelectedIndex;
	private Map<Integer,Integer> semMap = new HashMap<Integer,Integer>();
	private String feeGroupId;
	private Map<Integer,String> feeGroupMap;
	private Map<Integer,FeePaymentDisplayTO> semFeeGroupAmountMap = new HashMap<Integer,FeePaymentDisplayTO>();
	
	private List<FeePaymentDisplayTO> feePaymentDisplayList = new ArrayList<FeePaymentDisplayTO>();
	private FeePaymentEditTO feePaymentEditTO;
	private String[] selectedSems;
	private String[] selectedfeeGroup;
	private Map<Integer,String> allFeeAccountMap ;
	private Map<Integer,Double> fullAccountWiseTotal ;
	private Map<Integer,Double> fullAccountWiseExemptionTotal ;
    private List<FeeAmountTO> feeAccountWiseAmountList;
    private List<FeeAmountTO> feeAccountWiseExemptionAmountList;
    private List<FeeAmountTO> feeAccountWiseConcessionList;
    private List<FeeAmountTO> feeAccountWiseInstallmentAmountPaidList;
    private List<FeeAmountTO> feeAccountWiseAmountBalanceList;
    private List<FeeAmountTO> feeAccountWiseExcessShortList;
    private List<FeeAmountTO> feeAccountWiseScholarshipAmountPaidList;
    
	private double grandTotal;
	private double grandExemptionTotal;
	private double totalConcession;
	private double totalInstallment;
	private double totalScholarship;
	private double netPayable;
	private double totalBalance;
	
	private String paymentMode;
	private String concessionReferenceNo;
	private String installmentReferenceNo;
	private String scholarshipReferenceNo;
	private String[] selectedfeeOptionalGroup;
	private Map<Integer,String> feeOptionalGroupMap;	
	private Set<Integer> selectedOptionalFeeGroup;
	private Set<Integer> applicantSubjectgroup;
	private String studentName;
	private Map<Integer,String> paymentModeMap;
	private String academicYear;
	private String admissionYear;
	private String dateTime;
	private boolean ligExemption;
	private boolean casteExemption;
	private String feeDivisionId;
	private String feeDivisionName;
	private Set<Integer> feeAdditional;
	private List<AdmittedThroughTO> admittedThroughList = null;
	
	// needed in print challan.
	private String printChallan;
	private String billNo;
	private Map<Integer,Double> accountWiseNonOptionalAmount = new HashMap<Integer,Double>();
	private Map<Integer,Double> accountWiseOptionalAmount = new HashMap<Integer,Double>();
	private String challanPrintDate;
	private String semesterData;
	private String error;
	private String rollNumber;
	private List<PrintChalanTO> printChalanList;
	private String accwiseTotalPrintString;
	private String currencyCode;
	private Boolean isSinglePrint;
	private String currencyId;
	private Boolean isReprintChalan;
	private String chalanCreatedTime;
	private String financialYearId;
	private Map<Integer,  List<FeePaymentDetailFeeGroupTO>> feeGroupAmountsMap;
	private boolean feePaid;
	private int lastNo;
	private String isExemption;
	private Map<Integer, List<FeeDisplayTO>> feeDispMap;
	private List<FeePaymentDisplayTO> feePaymentDisplayTOList;
	private List<FeePaymentDisplayTO> feePaymentAdditionalList;
	private double totalPaidAmt;
	private String isFeeExemption;
	private boolean oldReceiptFound;
	private boolean isAidedStudent;
	private List<FeePaymentDetailAmount> feePaymentDetailAmountList;
	private Map<Integer, List<PrintChalanTO>> feePaymentDetails;
	private String secondLanguage;
	private String fatherName;
	private String address;
	private String admApplnId;
	private List<FeeStudentDetailTO> feePayStudentList;
	private String casteId;
	private List<CasteTO> casteList = null;
	private String personalDataId;
	private HashMap<Integer, String> secondLanguageList;
	private String secLanguageId;
	private String consAplha;
	private String programType;
	private String manualClassName;
	private String college;
	private String isCurrent;
	private String preYear;
	private String curYear;
	private String preFinId;
	private String curFinId;
	private String rePrint;
	private String preAmountFinancialYearId;
	private String semAcademicYear;
	private String admittedThroughCode;
	private String casteName;
	private List<FeeStudentDetailTO> studentListForFee;
	private String feeCategory;
	private String feeCategoryName;
	private List<FeeCategoryTo> feeCategoryList = null;
	private String selectFeePayYear;
	
	/**
	 *  clear method for clearing form data.
	 */
	public void clear() {
		this.selectedSems = null;
		this.selectedfeeGroup = null;
		this.selectedfeeOptionalGroup = null;
		this.paymentMode = null;
		this.concessionReferenceNo = null;
		this.installmentReferenceNo = null;
		this.scholarshipReferenceNo = null;
		this.applicantSubjectgroup = null;
		this.printChallan = null;
		this.accountWiseNonOptionalAmount = null;
		this.challanPrintDate =null;
		this.semesterData = null;
		this.printChallan = null;
		this.challanPrintDate = null;
//		this.studentName = null;
		this.allFeeAccountMap = null;
		this.accountWiseOptionalAmount = null;
		this.paymentModeMap = null;
		this.feeOptionalGroupMap = null;
		this.applicantSubjectgroup = null;
		this.challanPrintDate = null;
		this.error = null;
		this.selectedOptionalFeeGroup = null;
		this.currencyId = null;
		this.ligExemption = false;
		this.casteExemption = false;
		this.feePaymentAdditionalList = null;
		this.feePaymentDisplayTOList = null;
		this.isFeeExemption = null;
		this.oldReceiptFound = false;
		
		this.grandTotal = 0;
		this.totalConcession = 0;
		this.netPayable = 0;
		this.totalBalance = 0;
		this.feePaymentDetailAmountList = null;
		this.casteId = null; 
		this.secLanguageId= null;
		this.admittedThroughCode=null;
		this.casteName=null;
		this.feePayStudentList=null;
	}
	public void clearMain() {
		this.applicationId = null;
		this.registrationNo = null;
		this.billNo = null;
		this.rollNumber = null;
		this.studentName=null;
	}
	public void clearVoucher() {
		this.concessionReferenceNo = null;
		this.installmentReferenceNo = null;
		this.scholarshipReferenceNo = null;
	}
	
	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}
	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * @return the registrationNo
	 */
	public String getRegistrationNo() {
		return registrationNo;
	}
	/**
	 * @param registrationNo the registrationNo to set
	 */
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	/**
	 * @return the paymentDate
	 */
	public String getPaymentDate() {
		return paymentDate;
	}
	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	/**
	 * @return the selectedSems
	 */
	public String[] getSelectedSems() {
		return selectedSems;
	}
	/**
	 * @param selectedSems the selectedSems to set
	 */
	public void setSelectedSems(String[] selectedSems) {
		this.selectedSems = selectedSems;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		if(this.semeterSelectedIndex != null && this.semeterSelectedIndex.equals("-1")) {
			this.selectedSems = null;
		}
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	/**
	 * @return the admittedThrough
	 */
	public String getAdmittedThrough() {
		return admittedThrough;
	}
	/**
	 * @param admittedThrough the admittedThrough to set
	 */
	public void setAdmittedThrough(String admittedThrough) {
		this.admittedThrough = admittedThrough;
	}
	/**
	 * @return the admittedThroughName
	 */
	public String getAdmittedThroughName() {
		return admittedThroughName;
	}
	/**
	 * @param admittedThroughName the admittedThroughName to set
	 */
	public void setAdmittedThroughName(String admittedThroughName) {
		this.admittedThroughName = admittedThroughName;
	}
	/**
	 * @return the feeGroupMap
	 */
	public Map<Integer, String> getFeeGroupMap() {
		return feeGroupMap;
	}
	/**
	 * @param feeGroupMap the feeGroupMap to set
	 */
	public void setFeeGroupMap(Map<Integer, String> feeGroupMap) {
		this.feeGroupMap = feeGroupMap;
	}
	/**
	 * @return the semMap
	 */
	public Map<Integer, Integer> getSemMap() {
		return semMap;
	}
	/**
	 * @param semMap the semMap to set
	 */
	public void setSemMap(Map<Integer, Integer> semMap) {
		this.semMap = semMap;
	}
	/**
	 * @return the feeGroupId
	 */
	public String getFeeGroupId() {
		return feeGroupId;
	}
	/**
	 * @param feeGroupId the feeGroupId to set
	 */
	public void setFeeGroupId(String feeGroupId) {
		this.feeGroupId = feeGroupId;
	}
	/**
	 * @return the semFeeGroupAmountMap
	 */
	public Map<Integer, FeePaymentDisplayTO> getSemFeeGroupAmountMap() {
		return semFeeGroupAmountMap;
	}
	/**
	 * @param semFeeGroupAmountMap the semFeeGroupAmountMap to set
	 */
	public void setSemFeeGroupAmountMap(
			Map<Integer, FeePaymentDisplayTO> semFeeGroupAmountMap) {
		this.semFeeGroupAmountMap = semFeeGroupAmountMap;
	}
	/**
	 * @return the feePaymentDisplayList
	 */
	public List<FeePaymentDisplayTO> getFeePaymentDisplayList() {
		return feePaymentDisplayList;
	}
	/**
	 * @param feePaymentDisplayList the feePaymentDisplayList to set
	 */
	public void setFeePaymentDisplayList(List<FeePaymentDisplayTO> feePaymentDisplayList) {
		this.feePaymentDisplayList = feePaymentDisplayList;
	}
	/**
	 * @return the selectedfeeGroup
	 */
	public String[] getSelectedfeeGroup() {
		return selectedfeeGroup;
	}
	/**
	 * @param selectedfeeGroup the selectedfeeGroup to set
	 */
	public void setSelectedfeeGroup(String[] selectedfeeGroup) {
		this.selectedfeeGroup = selectedfeeGroup;
	}
	/**
	 * @return the allFeeAccountMap
	 */
	public Map<Integer, String> getAllFeeAccountMap() {
		return allFeeAccountMap;
	}
	/**
	 * @param allFeeAccountMap the allFeeAccountMap to set
	 */
	public void setAllFeeAccountMap(Map<Integer, String> allFeeAccountMap) {
		this.allFeeAccountMap = allFeeAccountMap;
	}
	/**
	 * @return the fullAccountWiseTotal
	 */
	public Map<Integer, Double> getFullAccountWiseTotal() {
		return fullAccountWiseTotal;
	}
	/**
	 * @param fullAccountWiseTotal the fullAccountWiseTotal to set
	 */
	public void setFullAccountWiseTotal(Map<Integer, Double> fullAccountWiseTotal) {
		this.fullAccountWiseTotal = fullAccountWiseTotal;
	}
	/**
	 * @return the grandTotal
	 */
	public double getGrandTotal() {
		return grandTotal;
	}
	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	/**
	 * @return the feeAccountWiseAmountList
	 */
	public List<FeeAmountTO> getFeeAccountWiseAmountList() {
		return feeAccountWiseAmountList;
	}
	/**
	 * @param feeAccountWiseAmountList the feeAccountWiseAmountList to set
	 */
	public void setFeeAccountWiseAmountList(
			List<FeeAmountTO> feeAccountWiseAmountList) {
		this.feeAccountWiseAmountList = feeAccountWiseAmountList;
	}
	/**
	 * @return the feeAccountWiseAmountBalanceList
	 */
	public List<FeeAmountTO> getFeeAccountWiseAmountBalanceList() {
		return feeAccountWiseAmountBalanceList;
	}
	/**
	 * @param feeAccountWiseAmountBalanceList the feeAccountWiseAmountBalanceList to set
	 */
	public void setFeeAccountWiseAmountBalanceList(
			List<FeeAmountTO> feeAccountWiseAmountBalanceList) {
		this.feeAccountWiseAmountBalanceList = feeAccountWiseAmountBalanceList;
	}
	
	/**
	 * @return the feeAccountWiseConcessionList
	 */
	public List<FeeAmountTO> getFeeAccountWiseConcessionList() {
		return feeAccountWiseConcessionList;
	}
	/**
	 * @param feeAccountWiseConcessionList the feeAccountWiseConcessionList to set
	 */
	public void setFeeAccountWiseConcessionList(
			List<FeeAmountTO> feeAccountWiseConcessionList) {
		this.feeAccountWiseConcessionList = feeAccountWiseConcessionList;
	}
	/**
	 * @return the feeAccountWiseInstallmentAmountPaidList
	 */
	public List<FeeAmountTO> getFeeAccountWiseInstallmentAmountPaidList() {
		return feeAccountWiseInstallmentAmountPaidList;
	}
	/**
	 * @param feeAccountWiseInstallmentAmountPaidList the feeAccountWiseInstallmentAmountPaidList to set
	 */
	public void setFeeAccountWiseInstallmentAmountPaidList(
			List<FeeAmountTO> feeAccountWiseInstallmentAmountPaidList) {
		this.feeAccountWiseInstallmentAmountPaidList = feeAccountWiseInstallmentAmountPaidList;
	}
	public List<FeeAmountTO> getFeeAccountWiseScholarshipAmountPaidList() {
		return feeAccountWiseScholarshipAmountPaidList;
	}
	public void setFeeAccountWiseScholarshipAmountPaidList(
			List<FeeAmountTO> feeAccountWiseScholarshipAmountPaidList) {
		this.feeAccountWiseScholarshipAmountPaidList = feeAccountWiseScholarshipAmountPaidList;
	}
	/**
	 * @return the totalConcession
	 */
	public double getTotalConcession() {
		return totalConcession;
	}
	/**
	 * @param totalConcession the totalConcession to set
	 */
	public void setTotalConcession(double totalConcession) {
		this.totalConcession = totalConcession;
	}
	/**
	 * @return the totalInstallment
	 */
	public double getTotalInstallment() {
		return totalInstallment;
	}
	/**
	 * @param totalInstallment the totalInstallment to set
	 */
	public void setTotalInstallment(double totalInstallment) {
		this.totalInstallment = totalInstallment;
	}
	public double getTotalScholarship() {
		return totalScholarship;
	}
	public void setTotalScholarship(double totalScholarship) {
		this.totalScholarship = totalScholarship;
	}
	/**
	 * @return the netPayable
	 */
	public double getNetPayable() {
		return netPayable;
	}
	/**
	 * @param netPayable the netPayable to set
	 */
	public void setNetPayable(double netPayable) {
		this.netPayable = netPayable;
	}
	/**
	 * @return the feeAccountWiseExcessShortList
	 */
	public List<FeeAmountTO> getFeeAccountWiseExcessShortList() {
		return feeAccountWiseExcessShortList;
	}
	/**
	 * @param feeAccountWiseExcessShortList the feeAccountWiseExcessShortList to set
	 */
	public void setFeeAccountWiseExcessShortList(
			List<FeeAmountTO> feeAccountWiseExcessShortList) {
		this.feeAccountWiseExcessShortList = feeAccountWiseExcessShortList;
	}
	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}
	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	/**
	 * @return the concessionReferenceNo
	 */
	public String getConcessionReferenceNo() {
		return concessionReferenceNo;
	}
	/**
	 * @param concessionReferenceNo the concessionReferenceNo to set
	 */
	public void setConcessionReferenceNo(String concessionReferenceNo) {
		this.concessionReferenceNo = concessionReferenceNo;
	}
	/**
	 * @return the installmentReferenceNo
	 */
	public String getInstallmentReferenceNo() {
		return installmentReferenceNo;
	}
	/**
	 * @param installmentReferenceNo the installmentReferenceNo to set
	 */
	public void setInstallmentReferenceNo(String installmentReferenceNo) {
		this.installmentReferenceNo = installmentReferenceNo;
	}
	public String getScholarshipReferenceNo() {
		return scholarshipReferenceNo;
	}
	public void setScholarshipReferenceNo(String scholarshipReferenceNo) {
		this.scholarshipReferenceNo = scholarshipReferenceNo;
	}
	/**
	 * @return the totalBalance
	 */
	public double getTotalBalance() {
		return totalBalance;
	}
	/**
	 * @param totalBalance the totalBalance to set
	 */
	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}
	/**
	 * @return the selectedfeeOptionalGroup
	 */
	public String[] getSelectedfeeOptionalGroup() {
		return selectedfeeOptionalGroup;
	}
	/**
	 * @param selectedfeeOptionalGroup the selectedfeeOptionalGroup to set
	 */
	public void setSelectedfeeOptionalGroup(String[] selectedfeeOptionalGroup) {
		this.selectedfeeOptionalGroup = selectedfeeOptionalGroup;
	}
	/**
	 * @return the feeOptionalGroupMap
	 */
	public Map<Integer, String> getFeeOptionalGroupMap() {
		return feeOptionalGroupMap;
	}
	/**
	 * @param feeOptionalGroupMap the feeOptionalGroupMap to set
	 */
	public void setFeeOptionalGroupMap(Map<Integer, String> feeOptionalGroupMap) {
		this.feeOptionalGroupMap = feeOptionalGroupMap;
	}
	/**
	 * @return the selectedOptionalFeeGroup
	 */
	public Set<Integer> getSelectedOptionalFeeGroup() {
		return selectedOptionalFeeGroup;
	}
	/**
	 * @param selectedOptionalFeeGroup the selectedOptionalFeeGroup to set
	 */
	public void setSelectedOptionalFeeGroup(Set<Integer> selectedOptionalFeeGroup) {
		this.selectedOptionalFeeGroup = selectedOptionalFeeGroup;
	}
	/**
	 * @return the applicantSubjectgroup
	 */
	public Set<Integer> getApplicantSubjectgroup() {
		return applicantSubjectgroup;
	}
	/**
	 * @param applicantSubjectgroup the applicantSubjectgroup to set
	 */
	public void setApplicantSubjectgroup(Set<Integer> applicantSubjectgroup) {
		this.applicantSubjectgroup = applicantSubjectgroup;
	}
	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the paymentModeMap
	 */
	public Map<Integer, String> getPaymentModeMap() {
		return paymentModeMap;
	}
	/**
	 * @param paymentModeMap the paymentModeMap to set
	 */
	public void setPaymentModeMap(Map<Integer, String> paymentModeMap) {
		this.paymentModeMap = paymentModeMap;
	}
	/**
	 * @return the academicYear
	 */
	public String getAcademicYear() {
		return academicYear;
	}
	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	/**
	 * @return the printChallan
	 */
	public String getPrintChallan() {
		return printChallan;
	}
	/**
	 * @param printChallan the printChallan to set
	 */
	public void setPrintChallan(String printChallan) {
		this.printChallan = printChallan;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return the accountWiseNonOptionalAmount
	 */
	public Map<Integer, Double> getAccountWiseNonOptionalAmount() {
		return accountWiseNonOptionalAmount;
	}
	/**
	 * @param accountWiseNonOptionalAmount the accountWiseNonOptionalAmount to set
	 */
	public void setAccountWiseNonOptionalAmount(
			Map<Integer, Double> accountWiseNonOptionalAmount) {
		this.accountWiseNonOptionalAmount = accountWiseNonOptionalAmount;
	}
	/**
	 * @return the accountWiseOptionalAmount
	 */
	public Map<Integer, Double> getAccountWiseOptionalAmount() {
		return accountWiseOptionalAmount;
	}
	/**
	 * @param accountWiseOptionalAmount the accountWiseOptionalAmount to set
	 */
	public void setAccountWiseOptionalAmount(
			Map<Integer, Double> accountWiseOptionalAmount) {
		this.accountWiseOptionalAmount = accountWiseOptionalAmount;
	}
	/**
	 * @return the challanPrintDate
	 */
	public String getChallanPrintDate() {
		return challanPrintDate;
	}
	/**
	 * @param challanPrintDate the challanPrintDate to set
	 */
	public void setChallanPrintDate(String challanPrintDate) {
		this.challanPrintDate = challanPrintDate;
	}
	/**
	 * @return the semesterData
	 */
	public String getSemesterData() {
		return semesterData;
	}
	/**
	 * @param semesterData the semesterData to set
	 */
	public void setSemesterData(String semesterData) {
		this.semesterData = semesterData;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * @return the feeGroupSelectedIndex
	 */
	public String getFeeGroupSelectedIndex() {
		return feeGroupSelectedIndex;
	}
	/**
	 * @param feeGroupSelectedIndex the feeGroupSelectedIndex to set
	 */
	public void setFeeGroupSelectedIndex(String feeGroupSelectedIndex) {
		this.feeGroupSelectedIndex = feeGroupSelectedIndex;
	}
	/**
	 * @return the optionalFeeSelectedIndex
	 */
	public String getOptionalFeeSelectedIndex() {
		return optionalFeeSelectedIndex;
	}
	/**
	 * @param optionalFeeSelectedIndex the optionalFeeSelectedIndex to set
	 */
	public void setOptionalFeeSelectedIndex(String optionalFeeSelectedIndex) {
		this.optionalFeeSelectedIndex = optionalFeeSelectedIndex;
	}
	/**
	 * @return the semeterSelectedIndex
	 */
	public String getSemeterSelectedIndex() {
		return semeterSelectedIndex;
	}
	/**
	 * @param semeterSelectedIndex the semeterSelectedIndex to set
	 */
	public void setSemeterSelectedIndex(String semeterSelectedIndex) {
		this.semeterSelectedIndex = semeterSelectedIndex;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public List<PrintChalanTO> getPrintChalanList() {
		return printChalanList;
	}
	public void setPrintChalanList(List<PrintChalanTO> printChalanList) {
		this.printChalanList = printChalanList;
	}
	public String getAccwiseTotalPrintString() {
		return accwiseTotalPrintString;
	}
	public void setAccwiseTotalPrintString(String accwiseTotalPrintString) {
		this.accwiseTotalPrintString = accwiseTotalPrintString;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public boolean isLigExemption() {
		return ligExemption;
	}
	public void setLigExemption(boolean ligExemption) {
		this.ligExemption = ligExemption;
	}
	public boolean isCasteExemption() {
		return casteExemption;
	}
	public void setCasteExemption(boolean casteExemption) {
		this.casteExemption = casteExemption;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getFeeDivisionId() {
		return feeDivisionId;
	}
	public void setFeeDivisionId(String feeDivisionId) {
		this.feeDivisionId = feeDivisionId;
	}
	public String getFeeDivisionName() {
		return feeDivisionName;
	}
	public void setFeeDivisionName(String feeDivisionName) {
		this.feeDivisionName = feeDivisionName;
	}
	public Set<Integer> getFeeAdditional() {
		return feeAdditional;
	}
	public void setFeeAdditional(Set<Integer> feeAdditional) {
		this.feeAdditional = feeAdditional;
	}
	public Boolean getIsSinglePrint() {
		return isSinglePrint;
	}
	public void setIsSinglePrint(Boolean isSinglePrint) {
		this.isSinglePrint = isSinglePrint;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public Boolean getIsReprintChalan() {
		return isReprintChalan;
	}
	public void setIsReprintChalan(Boolean isReprintChalan) {
		this.isReprintChalan = isReprintChalan;
	}
	public String getChalanCreatedTime() {
		return chalanCreatedTime;
	}
	public void setChalanCreatedTime(String chalanCreatedTime) {
		this.chalanCreatedTime = chalanCreatedTime;
	}
	public String getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(String financialYearId) {
		this.financialYearId = financialYearId;
	}
	public Map<Integer, List<FeePaymentDetailFeeGroupTO>> getFeeGroupAmountsMap() {
		return feeGroupAmountsMap;
	}
	public void setFeeGroupAmountsMap(
			Map<Integer, List<FeePaymentDetailFeeGroupTO>> feeGroupAmountsMap) {
		this.feeGroupAmountsMap = feeGroupAmountsMap;
	}
	public Map<Integer, Double> getFullAccountWiseExemptionTotal() {
		return fullAccountWiseExemptionTotal;
	}
	public void setFullAccountWiseExemptionTotal(
			Map<Integer, Double> fullAccountWiseExemptionTotal) {
		this.fullAccountWiseExemptionTotal = fullAccountWiseExemptionTotal;
	}
	public double getGrandExemptionTotal() {
		return grandExemptionTotal;
	}
	public void setGrandExemptionTotal(double grandExemptionTotal) {
		this.grandExemptionTotal = grandExemptionTotal;
	}
	public List<FeeAmountTO> getFeeAccountWiseExemptionAmountList() {
		return feeAccountWiseExemptionAmountList;
	}
	public void setFeeAccountWiseExemptionAmountList(
			List<FeeAmountTO> feeAccountWiseExemptionAmountList) {
		this.feeAccountWiseExemptionAmountList = feeAccountWiseExemptionAmountList;
	}
	public FeePaymentEditTO getFeePaymentEditTO() {
		return feePaymentEditTO;
	}
	public void setFeePaymentEditTO(FeePaymentEditTO feePaymentEditTO) {
		this.feePaymentEditTO = feePaymentEditTO;
	}
	public boolean isFeePaid() {
		return feePaid;
	}
	public void setFeePaid(boolean feePaid) {
		this.feePaid = feePaid;
	}
	public int getLastNo() {
		return lastNo;
	}
	public void setLastNo(int lastNo) {
		this.lastNo = lastNo;
	}
	public String getIsExemption() {
		return isExemption;
	}
	public void setIsExemption(String isExemption) {
		this.isExemption = isExemption;
	}
	public Map<Integer, List<FeeDisplayTO>> getFeeDispMap() {
		return feeDispMap;
	}
	public void setFeeDispMap(Map<Integer, List<FeeDisplayTO>> feeDispMap) {
		this.feeDispMap = feeDispMap;
	}
	public List<FeePaymentDisplayTO> getFeePaymentDisplayTOList() {
		return feePaymentDisplayTOList;
	}
	public void setFeePaymentDisplayTOList(
			List<FeePaymentDisplayTO> feePaymentDisplayTOList) {
		this.feePaymentDisplayTOList = feePaymentDisplayTOList;
	}
	public List<FeePaymentDisplayTO> getFeePaymentAdditionalList() {
		return feePaymentAdditionalList;
	}
	public void setFeePaymentAdditionalList(
			List<FeePaymentDisplayTO> feePaymentAdditionalList) {
		this.feePaymentAdditionalList = feePaymentAdditionalList;
	}
	public double getTotalPaidAmt() {
		return totalPaidAmt;
	}
	public void setTotalPaidAmt(double totalPaidAmt) {
		this.totalPaidAmt = totalPaidAmt;
	}
	public String getIsFeeExemption() {
		return isFeeExemption;
	}
	public void setIsFeeExemption(String isFeeExemption) {
		this.isFeeExemption = isFeeExemption;
	}
	public boolean isOldReceiptFound() {
		return oldReceiptFound;
	}
	public void setOldReceiptFound(boolean oldReceiptFound) {
		this.oldReceiptFound = oldReceiptFound;
	}
	public boolean isAidedStudent() {
		return isAidedStudent;
	}
	public void setAidedStudent(boolean isAidedStudent) {
		this.isAidedStudent = isAidedStudent;
	}
	public List<FeePaymentDetailAmount> getFeePaymentDetailAmountList() {
		return feePaymentDetailAmountList;
	}
	public void setFeePaymentDetailAmountList(
			List<FeePaymentDetailAmount> feePaymentDetailAmountList) {
		this.feePaymentDetailAmountList = feePaymentDetailAmountList;
	}
	public Map<Integer, List<PrintChalanTO>> getFeePaymentDetails() {
		return feePaymentDetails;
	}
	public void setFeePaymentDetails(
			Map<Integer, List<PrintChalanTO>> feePaymentDetails) {
		this.feePaymentDetails = feePaymentDetails;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<AdmittedThroughTO> getAdmittedThroughList() {
		return admittedThroughList;
	}
	public void setAdmittedThroughList(List<AdmittedThroughTO> admittedThroughList) {
		this.admittedThroughList = admittedThroughList;
	}
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public List<FeeStudentDetailTO> getFeePayStudentList() {
		return feePayStudentList;
	}
	public void setFeePayStudentList(List<FeeStudentDetailTO> feePayStudentList) {
		this.feePayStudentList = feePayStudentList;
	}
	public String getCasteId() {
		return casteId;
	}
	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}
	public List<CasteTO> getCasteList() {
		return casteList;
	}
	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}
	public String getPersonalDataId() {
		return personalDataId;
	}
	public void setPersonalDataId(String personalDataId) {
		this.personalDataId = personalDataId;
	}
	public HashMap<Integer, String> getSecondLanguageList() {
		return secondLanguageList;
	}
	public void setSecondLanguageList(HashMap<Integer, String> secondLanguageList) {
		this.secondLanguageList = secondLanguageList;
	}
	public String getConsAplha() {
		return consAplha;
	}
	public void setConsAplha(String consAplha) {
		this.consAplha = consAplha;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public String getManualClassName() {
		return manualClassName;
	}
	public void setManualClassName(String manualClassName) {
		this.manualClassName = manualClassName;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getPreYear() {
		return preYear;
	}
	public void setPreYear(String preYear) {
		this.preYear = preYear;
	}
	public String getCurYear() {
		return curYear;
	}
	public void setCurYear(String curYear) {
		this.curYear = curYear;
	}
	public String getPreFinId() {
		return preFinId;
	}
	public void setPreFinId(String preFinId) {
		this.preFinId = preFinId;
	}
	public String getCurFinId() {
		return curFinId;
	}
	public void setCurFinId(String curFinId) {
		this.curFinId = curFinId;
	}
	public String getRePrint() {
		return rePrint;
	}
	public void setRePrint(String rePrint) {
		this.rePrint = rePrint;
	}
	public String getPreAmountFinancialYearId() {
		return preAmountFinancialYearId;
	}
	public void setPreAmountFinancialYearId(String preAmountFinancialYearId) {
		this.preAmountFinancialYearId = preAmountFinancialYearId;
	}
	public String getSemAcademicYear() {
		return semAcademicYear;
	}
	public void setSemAcademicYear(String semAcademicYear) {
		this.semAcademicYear = semAcademicYear;
	}
	public String getAdmittedThroughCode() {
		return admittedThroughCode;
	}
	public void setAdmittedThroughCode(String admittedThroughCode) {
		this.admittedThroughCode = admittedThroughCode;
	}
	public String getCasteName() {
		return casteName;
	}
	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}
	public List<FeeStudentDetailTO> getStudentListForFee() {
		return studentListForFee;
	}
	public void setStudentListForFee(List<FeeStudentDetailTO> studentListForFee) {
		this.studentListForFee = studentListForFee;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getFeeCategoryName() {
		return feeCategoryName;
	}
	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}
	public List<FeeCategoryTo> getFeeCategoryList() {
		return feeCategoryList;
	}
	public void setFeeCategoryList(List<FeeCategoryTo> feeCategoryList) {
		this.feeCategoryList = feeCategoryList;
	}
	
	public String getSelectFeePayYear() {
		return selectFeePayYear;
	}
	public void setSelectFeePayYear(String selectFeePayYear) {
		this.selectFeePayYear = selectFeePayYear;
	}
}
