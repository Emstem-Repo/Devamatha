package com.kp.cms.helpers.admission;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.FeePaymentDetailFeegroup;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.FeePaymentOptionalFeeGroup;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.NewStudentCertificateCourseForm;
import com.kp.cms.handlers.admin.AcademicYearHandler;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.PropertyUtil;

public class NewStudentCertificateCourseHelper {
	/**
	 * Singleton object of NewStudentCertificateCourseHelper
	 */
	private static volatile NewStudentCertificateCourseHelper newStudentCertificateCourseHelper = null;
	private static final Log log = LogFactory.getLog(NewStudentCertificateCourseHelper.class);
	private NewStudentCertificateCourseHelper() {
		
	}
	/**
	 * return singleton object of NewStudentCertificateCourseHelper.
	 * @return
	 */
	public static NewStudentCertificateCourseHelper getInstance() {
		if (newStudentCertificateCourseHelper == null) {
			newStudentCertificateCourseHelper = new NewStudentCertificateCourseHelper();
		}
		return newStudentCertificateCourseHelper;
	}
	/**
	 * @param bo
	 * @param newStudentCertificateCourseForm
	 * @return
	 * @throws Exception
	 */
	public FeePayment getFeePaymentFromStudentCC(int id, NewStudentCertificateCourseForm newStudentCertificateCourseForm,Object[] obj) throws Exception {
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		StudentCertificateCourse bo=(StudentCertificateCourse)transaction.getMasterEntryDataById(StudentCertificateCourse.class,id);
		
		FeePayment feePayment=new FeePayment();
		feePayment.setApplicationNo(Integer.toString(bo.getStudent().getAdmAppln().getApplnNo()));
		feePayment.setRegistrationNo(bo.getStudent().getRegisterNo());
		feePayment.setTotalAmount(new BigDecimal(obj[0].toString()));
		feePayment.setTotalFeePaid(new BigDecimal(obj[0].toString()));
		feePayment.setIsCancelChallan(false);
		feePayment.setIsChallenPrinted(true);
		feePayment.setChallenPrintedDate(new Date());
		feePayment.setIsFeePaid(true);
		feePayment.setFeePaidDate(new Date());
		feePayment.setIsCompletlyPaid(true);
		feePayment.setTotalConcessionAmount(new BigDecimal("0.0"));
		//feePayment.setTotalInstallmentAmount(new BigDecimal("0.0"));
		feePayment.setTotalBalanceAmount(new BigDecimal("0.0"));
		feePayment.setCourse(bo.getStudent().getAdmAppln().getCourseBySelectedCourseId());
		feePayment.setAcademicYear(CurrentAcademicYear.getInstance().getAcademicyear());
		feePayment.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
		feePayment.setChallanCreatedTime(new Date());
		feePayment.setConcessionVoucherNo("");
		//feePayment.setInstallmentVoucherNo("");
		//feePayment.setSchlorshipVoucherNo("");
		//feePayment.setTotalSchlorshipAmount(new BigDecimal("0"));
		//feePayment.setConversionRate(new BigDecimal("0"));
		feePayment.setRollNo("");
		
		
		FeeDivision division=new FeeDivision();
		division.setId(Integer.parseInt(obj[2].toString()));
		//feePayment.setFeeDivision(division);
		feePayment.setStudent(bo.getStudent());
		FeePaymentMode feePaymentMode=new FeePaymentMode();
		feePaymentMode.setId(Integer.parseInt(CMSConstants.smartCardPaymentMode));
		feePayment.setFeePaymentMode(feePaymentMode);
		
		Set<FeePaymentDetail> feePaymentDetails=new HashSet<FeePaymentDetail>();
		
		FeePaymentDetail feePaymentDetail=new FeePaymentDetail();
		FeeAccount feeAccount=new FeeAccount();
		feeAccount.setId(Integer.parseInt(obj[3].toString()));
		
		feePaymentDetail.setFeeAccount(feeAccount);
		feePaymentDetail.setTotalAmount(new BigDecimal(obj[0].toString()));
		feePaymentDetail.setConcessionAmount(new BigDecimal("0"));
		//feePaymentDetail.setInstallmentAmount(new BigDecimal("0"));
		feePaymentDetail.setAmountPaid(new BigDecimal(obj[0].toString()));
		feePaymentDetail.setAmountBalance(new BigDecimal("0"));
		//feePaymentDetail.setExcessShortAmount(new BigDecimal("0"));
		feePaymentDetail.setTotalAdditionalAmount(new BigDecimal(obj[0].toString()));
		//feePaymentDetail.setSchlorshipAmount(new BigDecimal("0"));
		feePaymentDetail.setPaidDate(new Date());
		feePaymentDetail.setFeePaymentMode(feePaymentMode);
		//feePaymentDetail.setIsInstallmentPayment(false);
		String finanicalYearQuery="select concat(f.id,'') from FeeFinancialYear f where f.isActive=1 and f.isCurrent=1";
		String financialYearId=PropertyUtil.getDataForUnique(finanicalYearQuery);
		FeeFinancialYear feeFinancialYear=new FeeFinancialYear();
		feeFinancialYear.setId(Integer.parseInt(financialYearId));
		newStudentCertificateCourseForm.setFeeFinancialYearId(Integer.parseInt(financialYearId));
		feePaymentDetail.setFeeFinancialYear(feeFinancialYear);
		feePaymentDetail.setDiscountAmt(new BigDecimal("0"));
		
		Set<FeePaymentDetailFeegroup> feePaymentDetailFeegroups=new HashSet<FeePaymentDetailFeegroup>();
		FeePaymentDetailFeegroup feePaymentDetailFeegroup=new FeePaymentDetailFeegroup();
		feePaymentDetailFeegroup.setIsOptional(true);
		feePaymentDetailFeegroup.setIsActive(true);
		feePaymentDetailFeegroup.setAmount(new BigDecimal(obj[0].toString()));
		FeeGroup feeGroup=new FeeGroup();
		feeGroup.setId(Integer.parseInt(obj[1].toString()));
		feePaymentDetailFeegroup.setFeeGroup(feeGroup);
		feePaymentDetailFeegroups.add(feePaymentDetailFeegroup);
		//feePaymentDetail.setFeePaymentDetailFeegroups(feePaymentDetailFeegroups);
		
		feePaymentDetails.add(feePaymentDetail);
		
		feePayment.setFeePaymentDetails(feePaymentDetails);
		
		Set<FeePaymentOptionalFeeGroup> feePaymentOptionalFeeGroups=new HashSet<FeePaymentOptionalFeeGroup>();
		FeePaymentOptionalFeeGroup feePaymentOptionalFeeGroup=new FeePaymentOptionalFeeGroup();
		feePaymentOptionalFeeGroup.setSemesterNo(bo.getSchemeNo());
		FeeAdditional feeAdditional=new FeeAdditional();
		feeAdditional.setId(Integer.parseInt(obj[4].toString()));
		feePaymentOptionalFeeGroup.setFeeAdditional(feeAdditional);
		feePaymentOptionalFeeGroups.add(feePaymentOptionalFeeGroup);
		//raghu
		//feePayment.setFeePaymentOptionalFeeGroups(feePaymentOptionalFeeGroups);
		return feePayment;
	}
}
