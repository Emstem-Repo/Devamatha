package com.kp.cms.transactions.fee;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.FeeAdditionalAccountAssignment;
import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetailAmount;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.forms.fee.FeePaymentForm;
/**
 *
 */
public interface IFeePaymentTransaction {
	
	public int addNewPayment(FeePayment feePayment, int financialYear, boolean isBillNoSave) throws Exception,BillGenerationException;
	public Student getApplicantDetails(String applicationNumber,String year, String regOrRollNo, String rollNo) throws Exception;
	public List<ApplicantSubjectGroup> getApplicantSubjectGroup(int id) throws Exception;
	public List<FeePaymentMode> getAllPaymentMode() throws Exception;
	public List<Object[]> getChallanIdsForApplicant(Set<Integer> semSet,String applicationNo,String registerNo, String rollNo, int year) throws Exception;
	public FeePayment getChallanDetailsById(String billNo) throws Exception;
	public List<FeePayment> getFeePaymentsBetweenDates(Date startDate,Date endDate, String divId) throws Exception;
	public List<FeePayment> getFeePaymentsBetweenDatesForCancel(Date startDate,Date endDate) throws Exception;
	public List<FeeAdditionalAccountAssignment> getOptionalFeeCodes(String addIds) throws Exception;
	public int getFinancialYear() throws Exception;
	public String getClassNameByStudentId(int studentId) throws Exception;
	public List<FeeVoucher> getFeevoucherList(Integer financialYearId) throws Exception;
	public FeePayment getFeePaymentDetailsForEdit(int billNo, int financialYearId) throws Exception;
	public boolean feePaymentDetailsUpdate(FeePaymentForm feePaymentForm) throws Exception;
	public long getNoOfChallansForcourse(String query) throws Exception;
	public FeeCriteria getAdditionalFeeForInst(int instId) throws Exception;
	public FeeCriteria getAdditionalFeeForNationality(int natId) throws Exception;
	public FeeCriteria getAdditionalFeeForUniveristy(int uniId) throws Exception;
	public FeeCriteria getAdditionalFeeForResidentCategory(int resId) throws Exception;
	public FeeCriteria getAdditionalFeeForInstNationality(int instId, int natId ) throws Exception;
	public FeeCriteria getAdditionalFeeForInstUni(int instId, int uniId ) throws Exception;
	public FeeCriteria getAdditionalFeeForInstRes(int instId, int resId ) throws Exception;
	public FeeCriteria getAdditionalFeeForNatUni(int natId, int uniId ) throws Exception;
	public FeeCriteria getAdditionalFeeForNatRes(int natId, int resId ) throws Exception;
	public FeeCriteria getAdditionalFeeForResUni(int resId, int uniId ) throws Exception;
	public FeeCriteria getAdditionalFeeForInstNationalityUni(int instId, int natId, int uniId ) throws Exception;
	public FeeCriteria getAdditionalFeeForInstNatRes(int instId, int natId, int resId ) throws Exception;
	public FeeCriteria getAdditionalFeeForNatUniRes(int natId, int uniId, int resId ) throws Exception;
	public FeeCriteria getAdditionalFeeForUniResInst(int uniId, int resId, int instId ) throws Exception;
	public FeeCriteria getAdditionalFeeForInstNationalityUniRes(int instId, int natId, int uniId, int resId ) throws Exception;
	public List<EdnQualification> getEdnQualificationByPersonalData(int personalDataId ) throws Exception;
	public FeeCriteria getAdditionalFeeForSecondLanguage(String language) throws Exception;
	public List<FeePayment> getOldFeePaymentDetails(String applNo, String year) throws Exception;
	public boolean addFeePaymentDetailAmounts(List<FeePaymentDetailAmount> amountList) throws Exception;
	public boolean UpdateAdmittedThrough(int ApplnId, int admThroughId) throws Exception;
	public List<Student> getApplicantDetailsByName(String studentName, String year) throws Exception;
	public boolean isCasteExemption(int casteId) throws Exception;
	public boolean UpdatePersonalData(int id, int casteId, String secLanguage) throws Exception;
	public String getConcessionAlpha(String financialYearId) throws Exception;
	public String getFinancialYear(String financialYearId) throws Exception;
	public int getSemAcademicYear(Set<Integer> semSet,
			FeePaymentForm feePaymentForm)throws Exception;
	public int getFinancialYearByYear(String year) throws Exception;
	public List<Student> getApplicantDetailsList(FeePaymentForm feePaymentForm) throws Exception;
	public List<Student> getStudentsByClassName(FeePaymentForm feePayForm, List<Integer> detainedStudents) throws Exception;
	public List<FeePayment> getPendingPaymentVerification(FeePayment feePayment) throws Exception;
	public boolean saveFeePaymentDetailsStudentLogin(
			FeePayment feePaymentToBeSaved) throws Exception;
	public FeePayment getPaymentDetails(FeePaymentForm feePaymentForm) throws Exception;
	public List<FeePayment> getFeeDetailsForStudent(FeePaymentForm feePaymentForm) throws Exception;
	public boolean saveFeePaymentDetails(FeePaymentForm feePaymentForm) throws Exception;
}
