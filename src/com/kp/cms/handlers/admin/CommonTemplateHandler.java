package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetailFeegroup;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.helpers.admin.CommonTemplateHelper;
import com.kp.cms.to.admin.SportsTO;
import com.kp.cms.transactions.admin.ICommonTemplateTransaction;
import com.kp.cms.transactionsimpl.admin.CommonTemplateTransactionImpl;

public class CommonTemplateHandler {
	private static final Log log = LogFactory.getLog(CommonTemplateHandler.class);
	public static volatile CommonTemplateHandler commonTemplateHandler =null;
	/**
	 * @return
	 */
	private CommonTemplateHandler(){
		
	}
	public static CommonTemplateHandler getInstance(){
		if(commonTemplateHandler == null){
			commonTemplateHandler = new CommonTemplateHandler();
			return commonTemplateHandler;
		}
		return commonTemplateHandler;
	}
	/**
	 * @param addressPrintForm
	 * @param request
	 * @throws Exception
	 */
	public void getNoDuePrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception {
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		//String year = iDateOfBirthPrintTransaction.getStudentAcademicYear(dateOfBirthPrintForm);
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToList(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getVisaPrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception {
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForVisa(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getAttemptCertificatePrintDetails(
			CommonTemplateForm commonTemplateForm, HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForAttemptCertificate(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getFeePrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		Iterator<Student> studentList = stuList.iterator();
		List<String> messageList = new ArrayList<String>();
		if(studentList != null){
			while (studentList.hasNext()) {
				Student student = (Student) studentList.next();
				List<Object[]> feeList = iCommonTemplateTransaction.getFeeHeadingDetails(student.getRegisterNo(), commonTemplateForm);
				List<String> message = new ArrayList<String>();
//				List<FeePayment> fee = iCommonTemplateTransaction.getFeeDetails(stuId, commonTemplateForm);
//				List<FeePaymentDetailFeegroup> feegroups = iCommonTemplateTransaction.getFeeGroupDetails(stuId,commonTemplateForm);
//				List<String> message = new ArrayList<String>();
//				if(fee != null && !fee.isEmpty() && feegroups != null && !feegroups.isEmpty()){
//					message = CommonTemplateHelper.getInstance().copyBosToListForFeeDetails(student, request, commonTemplateForm, fee,feegroups);
//				}
				message = CommonTemplateHelper.getInstance().copyBosToListForFeeDetails(student, request, commonTemplateForm,feeList);
				messageList.addAll(message);
			}
		}
		commonTemplateForm.setMessageList(messageList);
	}
	
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getScholarshipPrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForScholarship(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getMediumPrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForMedium(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getSportsPrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForSports(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getForeignNOCPrintDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForForeignNOC(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getTutionFeeDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		Iterator<Student> studentList = stuList.iterator();
		List<String> messageList = new ArrayList<String>();
		if(studentList != null){
			while (studentList.hasNext()) {
				Student student = (Student) studentList.next();
				String stuId = String.valueOf(student.getId());
				List<FeePaymentDetailFeegroup> feegroups = iCommonTemplateTransaction.getFeeGroupDetails(stuId,commonTemplateForm);
				//BigDecimal tuitionFee = iCommonTemplateTransaction.getTuitionFeeDetails(stuId, commonTemplateForm);
				//String tuitionfee =tuitionFee.toString();
				List<FeeAccountAssignment> feeAccountAssignments = iCommonTemplateTransaction.getTuitionFeeForClass(student, commonTemplateForm);
				List<String> message = CommonTemplateHelper.getInstance().copyBosToListForTutionFee(student, request, commonTemplateForm,feegroups,feeAccountAssignments);
				messageList.addAll(message);
			}
		}
		commonTemplateForm.setMessageList(messageList);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<SportsTO> getSportsList() throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		List<Sports> sportsList = iCommonTemplateTransaction.getSportsList();
		List<SportsTO> getSportsList = CommonTemplateHelper.getInstance().convertBOstoTos(sportsList);
		return getSportsList;
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getMarkTranscriptDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		Iterator<Student> iterator = stuList.iterator();
		List<String> messageList = new ArrayList<String>();
		while (iterator.hasNext()) {
			Student student = (Student) iterator.next();
			List<Object[]> marksDetails = iCommonTemplateTransaction.getStudentMarksDetails(student);
			List<String> message = CommonTemplateHelper.getInstance().copyBosToListForMarkTranscript(student, request, commonTemplateForm, marksDetails);
			messageList.addAll(message);
		}
		commonTemplateForm.setMessageList(messageList);
	}
	public void getAddressAndDOBDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception {
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForAddressAndDOB(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getSecondPUMarkTranscriptDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception {
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		Student student = iCommonTemplateTransaction.getStudent(commonTemplateForm);
		if(student != null){
			List<Object[]> marksDetails = iCommonTemplateTransaction.getStudentMarksDetails(student);
			List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForSecondPUMarkTranscript(student, commonTemplateForm , marksDetails);
			commonTemplateForm.setMessageList(messageList);
		}
	}
	/**
	 * @param commonTemplateForm
	 * @param request
	 * @throws Exception
	 */
	public void getPUMarkTranscriptDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception {
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		Student student = iCommonTemplateTransaction.getStudent(commonTemplateForm);
		if(student != null){
			List<Object[]> marksDetails = iCommonTemplateTransaction.getStudentMarksDetails(student);
			List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForPUMarkTranscript(student, commonTemplateForm , marksDetails);
			commonTemplateForm.setMessageList(messageList);
		}
	}
	
	public void getNCCDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForNCC(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
	public void getMotherTongueDetails(CommonTemplateForm commonTemplateForm,
			HttpServletRequest request) throws Exception{
		ICommonTemplateTransaction iCommonTemplateTransaction = CommonTemplateTransactionImpl.getInstance();
		 List<Student> stuList = iCommonTemplateTransaction.getRequiredRegdNos(commonTemplateForm.getRegNoFrom(), commonTemplateForm.getRegNoTo());
		List<String> messageList = CommonTemplateHelper.getInstance().copyBosToListForMotherTongue(stuList, request, commonTemplateForm);
		commonTemplateForm.setMessageList(messageList);
	}
}
