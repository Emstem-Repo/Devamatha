package com.kp.cms.handlers.sap;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.ExamScheduleDate;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.helpers.sap.ExamRegistrationDetailsHelper;
import com.kp.cms.to.sap.ExamRegistrationDetailsTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.transactionsimpl.sap.ExamRegistrationDetailsTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class ExamRegistrationDetailsHandler {
	public static final String FROM_DATEFORMAT="dd/MM/yyyy";
	public static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static volatile ExamRegistrationDetailsHandler handler = null;
	private static final Log log = LogFactory.getLog(ExamRegistrationDetailsHandler.class);
	public static ExamRegistrationDetailsHandler getInstance(){
		if(handler == null){
			handler = new ExamRegistrationDetailsHandler();
			return handler;
		}
		return handler;
	}
	IExamRegistrationDetailsTransaction transaction = ExamRegistrationDetailsTxnImpl.getInstance();
	/**
	 * @param objForm 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getWorkLocationMap(ExamRegistrationDetailsForm objForm) throws Exception{
		Map<Integer,String> workLocationMap = transaction.getWorkLocationDetailsList(objForm);
			if(workLocationMap!=null && !workLocationMap.isEmpty()){
				if(workLocationMap.size() ==1){
					Iterator<Entry<Integer, String>> iterator = workLocationMap.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<java.lang.Integer, java.lang.String> entry = (Map.Entry<java.lang.Integer, java.lang.String>) iterator .next();
						objForm.setWorkLocationId(entry.getKey());
						break;
					}
				}
			}
		return workLocationMap;
	}
	/**
	 * @param workLocationId
	 * @return
	 * @throws Exception
	 */
	public synchronized Map<String,List<ExamRegistrationDetailsTo>>  getDateAndSessionsOfWorkLocation( ExamRegistrationDetailsForm objForm) throws Exception{
		 int workLocationId = objForm.getWorkLocationId();
		 Map<String,List<ExamRegistrationDetailsTo>> examRegDetailsMap =null;
		 if(workLocationId >0){
			 List<Object[]> boList = transaction.getDateSessionDetailsOfWorkLocation(workLocationId);
			 List<ExamRegistrationDetailsTo> sessionDetailsList = ExamRegistrationDetailsHelper.getInstance().populateObjListToTOList(boList);
			 List<Object[]> objects = transaction.getPreviousDetailsOfWorkLocation(workLocationId);
			 Map<Integer,Integer> sessionCountMap = ExamRegistrationDetailsHelper.getInstance().convertObjListToMap(objects);
			 examRegDetailsMap = ExamRegistrationDetailsHelper.getInstance().copyDetailsToMap(sessionDetailsList,sessionCountMap);
		 }
		 return examRegDetailsMap;
	}
	/**Allot venue for the registering person, if the venue is available
	 * @param objForm
	 * @throws Exception
	 */
	public synchronized void allotVenueForStudent(ExamRegistrationDetailsForm objForm)throws Exception {
		int workLocationId = objForm.getWorkLocationId();
		int examSessionId = objForm.getExamScheduleDateId();
		List<ExamScheduleVenue> sVenueBoList = transaction.getVenueDetails(workLocationId,examSessionId);
		 Map<Integer, Integer> allotedVenuesMap= transaction.getAllotedVenuesForSession(workLocationId,examSessionId);
		 /*------------------ Assigning Venue to the Candidate who is applied for Exam Registration------------*/
		 ExamRegistrationDetailsHelper.getInstance().assignVenueForStudent(sVenueBoList,allotedVenuesMap,objForm);
		 /*------------------------------------------------------------------------------------------------------*/
		 /*SapVenue venue = transaction.getWorkLocationAndVenueByIds(objForm.getVenueId());
		 objForm.setWorkLocationName(venue.getWorkLocationId().getName());
		 objForm.setVenueName(venue.getVenueName());*/
	}
	/**Checking whether the entered smart card number is correct or not for that student.
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean verifySmartCard(ExamRegistrationDetailsForm objForm) throws Exception{
		String query="select s.smartCardNo from Student s where s.id="+objForm.getStudentId()+" and s.smartCardNo like '%"+objForm.getSmartCardNo()+"'";
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List list=txn.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			objForm.setPaymentRequired(true);
			return true;
		}else{
			return false;
		}
	}
	/**Setting  the required details in ExamRegistrationDetails as well as in  OnlinePaymentReciepts bos
	 * saving the bos and
	 * updating the status , transactiondate ,receipt no and bankConfirmationId in OnlinePaymentReciepts  if it success or fail
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveExamRegistrationDetails( ExamRegistrationDetailsForm objForm) throws Exception{
		ExamRegistrationDetails details = transaction .getCheckIsAlreadyRegisteredForExam(objForm.getStudentId());
		if(details!=null && !details.toString().isEmpty()){
			throw new  DuplicateException();
		}else{
			ExamRegistrationDetails registrationDetails = transaction.checkIsAlreadyCancelledRegistration(objForm.getStudentId());
			if (registrationDetails == null) {
				registrationDetails = new ExamRegistrationDetails();
			}
			OnlinePaymentReciepts onlinePaymentReciepts = new OnlinePaymentReciepts();
			setOnlinePaymentRecieptsBo(onlinePaymentReciepts,objForm);
			/* save the OnlinePaymentReciepts into database*/ 
			int onlineRegDetailsId = transaction.saveOnlinePaymentReciepts(onlinePaymentReciepts);
			/*-------------------------------------------------*/
			setExamRegistrationDetailsBo(registrationDetails,onlinePaymentReciepts,objForm);
			/* save the ExamRegistrationDetails into database*/ 
			int examRegDetailsId = transaction.saveRegistrationDetails(registrationDetails);
			/*-------------------------------------------------*/
			
			if(objForm.isPaymentRequired()){                    // if it is true, allow to make payment.
				if(examRegDetailsId>0 && onlineRegDetailsId>0){
					//objForm.setOnlinePaymentId(onlineRegDetailsId);
					ISingleFieldMasterTransaction iTransaction=SingleFieldMasterTransactionImpl.getInstance();
					ExamRegistrationDetails examRegistrationDetails=(ExamRegistrationDetails)iTransaction.getMasterEntryDataById(ExamRegistrationDetails.class,examRegDetailsId);
					OnlinePaymentReciepts paymentReciepts =(OnlinePaymentReciepts)iTransaction.getMasterEntryDataById(OnlinePaymentReciepts.class,onlineRegDetailsId);
					ExamRegistrationDetailsHelper.getInstance().dedactAmountFromAccount(paymentReciepts,objForm);
					if(!paymentReciepts.getIsPaymentFailed()/*true*/){ // whether the make payment is success  or fail update the related Bos.
						examRegistrationDetails.setIsPaymentFailed(false);
						examRegistrationDetails.setIsCancelled(false);
						PropertyUtil.getInstance().update(examRegistrationDetails);
						transaction.updateAndGenerateRecieptNoOnlinePaymentReciept(paymentReciepts);
					}else{
						objForm.setMessage(paymentReciepts.getStatus());
						examRegistrationDetails.setIsPaymentFailed(true);
						examRegistrationDetails.setIsCancelled(true);
						PropertyUtil.getInstance().update(examRegistrationDetails);
						return false;
					}
					
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		return true;
	}
	/**set the required details into the OnlinePaymentReciepts Bo. 
	 * @param onlinePaymentReciepts
	 * @param objForm
	 * @throws Exception
	 */
	private void setOnlinePaymentRecieptsBo( OnlinePaymentReciepts onlinePaymentReciepts, ExamRegistrationDetailsForm objForm) throws Exception{
		Student student =new Student();
		student.setId(objForm.getStudentId());
		onlinePaymentReciepts.setStudent(student);
		onlinePaymentReciepts.setTotalAmount(new BigDecimal(objForm.getFeeAmount()));
		onlinePaymentReciepts.setApplicationFor("SAP Exam Registration");
		PcFinancialYear pcFinancialYear=new PcFinancialYear();
		pcFinancialYear.setId(objForm.getFinancialYear());
		onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
		onlinePaymentReciepts.setIsPaymentFailed(true);
		onlinePaymentReciepts.setCreatedBy(objForm.getUserId());
		onlinePaymentReciepts.setCreatedDate(new Date());
		onlinePaymentReciepts.setModifiedBy(objForm.getUserId());
		onlinePaymentReciepts.setLastModifiedDate(new Date());
		onlinePaymentReciepts.setIsActive(true);
	}
	/**set the required details into the ExamRegistrationDetails Bo. 
	 * @param registrationDetails
	 * @param paymentReciepts 
	 * @param objForm
	 * @throws Exception
	 */
	private void setExamRegistrationDetailsBo( ExamRegistrationDetails registrationDetails, OnlinePaymentReciepts paymentReciepts, ExamRegistrationDetailsForm objForm) throws Exception{
		Student student = new Student();
		SapVenue sapVenue = new SapVenue();
		ExamScheduleDate scheduleDate = new ExamScheduleDate();
		student.setId(objForm.getStudentId());
		registrationDetails.setStudentId(student);
		sapVenue.setId(objForm.getVenueId());
		registrationDetails.setSapVenueId(sapVenue);
		scheduleDate.setId(objForm.getExamScheduleDateId());
		registrationDetails.setExamScheduleDateId(scheduleDate);
		registrationDetails.setOnlinePaymentReceipt(paymentReciepts);
		registrationDetails.setIsOnline(true);
		registrationDetails.setIsPresent(false);
		registrationDetails.setCreatedBy(objForm.getUserId());
		registrationDetails.setCreatedDate(new Date());
		registrationDetails.setLastModifiedDate(new Date());
		registrationDetails.setModifiedBy(objForm.getUserId());
		registrationDetails.setIsPaymentFailed(true);
		registrationDetails.setIsCancelled(false);
		registrationDetails.setIsActive(true);
	}
	/**
	 * @param objForm 
	 * @param examType 
	 * @return
	 * @throws Exception
	 */
	public void getFeeAmount(ExamRegistrationDetailsForm objForm, String examType) throws Exception{
		Double feeAmount = transaction.getFeeAmount(examType);
		String amount= removeFileExtension(String.valueOf(feeAmount));
		objForm.setFeeAmount(amount);
	}
	public void getFeeAmount(ExamRegistrationDetailsForm objForm) throws Exception{
		String examType = "regular";
		Double feeAmount = transaction.getFeeAmount(examType);
		String amount= removeFileExtension(String.valueOf(feeAmount));
		objForm.setFeeAmount(amount);
	}
	/**
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
	}
	/**Method for check whether already registered for exam .
	 * @param studentId 
	 * @return
	 * @throws Exception
	 * 
	 */
	public boolean checkIsAlreadyRegisteredForExam(
			ExamRegistrationDetailsForm objForm) throws Exception {
		boolean isExist = false;
		ExamRegistrationDetails details = transaction .getCheckIsAlreadyRegisteredForExam(objForm.getStudentId());
		if (details != null && !details.toString().isEmpty()) {
			isExist = true;
			objForm.setNameOfStudent(details.getStudentId().getAdmAppln() .getPersonalData().getFirstName());
			objForm.setRegNo(details.getStudentId().getRegisterNo());
			objForm.setClassName(details.getStudentId().getClassSchemewise() .getClasses().getName());
			objForm.setExamDate(CommonUtil.formatDates(details .getExamScheduleDateId().getExamDate()));
			objForm.setWorkLocationName(details.getSapVenueId() .getWorkLocationId().getName());
			objForm.setVenueName(details.getSapVenueId().getVenueName());
			objForm .setSessionName(details.getExamScheduleDateId() .getSession());
		}
		return isExist;
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void saveSAPSubjectGroup(ExamRegistrationDetailsForm objForm)
			throws Exception {
		int subjectId = Integer.parseInt(CMSConstants.UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID);// get sapSubjectId from application.properties
		ISingleFieldMasterTransaction iTransaction=SingleFieldMasterTransactionImpl.getInstance();
		Subject subjectBo =(Subject) iTransaction.getMasterEntryDataById(Subject.class, subjectId);
		if(subjectBo.getIsActive()){
			String getSubjectGrpQuery = ExamRegistrationDetailsHelper.getInstance().getSubjectGroupQuery(objForm, subjectId);
			SubjectGroup subjectGroupBo = (SubjectGroup) PropertyUtil.getDataForUniqueObject(getSubjectGrpQuery);
			if (subjectGroupBo == null || subjectGroupBo.toString().isEmpty()) {
				subjectGroupBo = new SubjectGroup();
				subjectGroupBo = ExamRegistrationDetailsHelper.getInstance() .createSubjectGroupBo(objForm, subjectGroupBo, subjectBo);
				CurriculumSchemeSubject currSchemeSubject = ExamRegistrationDetailsHelper .getInstance().createCurriculumSchemeSubjectBo(objForm,
						subjectGroupBo);
				PropertyUtil.getInstance().save(subjectGroupBo);
				PropertyUtil.getInstance().save(currSchemeSubject);
				
			}
			String checkCurriculumSchemeSubjectQuery = ExamRegistrationDetailsHelper.getInstance().checkCurriculumSchemeSubject(objForm.getCurriculumSchemeDurationId(),subjectGroupBo);
			CurriculumSchemeSubject cssBo = (CurriculumSchemeSubject) PropertyUtil.getDataForUniqueObject(checkCurriculumSchemeSubjectQuery);
			if(cssBo==null || cssBo.toString().isEmpty()){
				CurriculumSchemeSubject currSchemeSubject = ExamRegistrationDetailsHelper .getInstance().createCurriculumSchemeSubjectBo(objForm,
						subjectGroupBo);
				PropertyUtil.getInstance().save(currSchemeSubject);
			}
			String SubjectGrpAlreadyExist_Query = ExamRegistrationDetailsHelper.getInstance().checkSubGrpAlreadyExistForStudent(objForm,subjectGroupBo);
			ApplicantSubjectGroup duplicateAppSubGrp =  (ApplicantSubjectGroup) PropertyUtil.getDataForUniqueObject(SubjectGrpAlreadyExist_Query);
			if(duplicateAppSubGrp == null || duplicateAppSubGrp.toString().isEmpty()){
				ApplicantSubjectGroup appSubGrp = ExamRegistrationDetailsHelper .getInstance().setApplicantSubjectGroupBO(objForm, subjectGroupBo);
				if (!appSubGrp.toString().isEmpty()) {
					PropertyUtil.getInstance().save(appSubGrp);
				}
			}
		}
	}
	/**
	 * @param student 
	 * @param objForm
	 * @param session 
	 * @throws Exception
	 */
	public void getStudentDetails(Student student, ExamRegistrationDetailsForm objForm, HttpSession session) throws Exception {
		if (student != null) {
			objForm.setStudentId(student.getId());
			objForm .setNameOfStudent(student.getAdmAppln().getPersonalData() .getFirstName() + (student.getAdmAppln().getPersonalData()
									.getMiddleName() != null ? student .getAdmAppln().getPersonalData() .getMiddleName() : "") + (student.getAdmAppln().getPersonalData()
									.getLastName() != null ? student .getAdmAppln().getPersonalData() .getLastName() : "")); 
			objForm.setClassName(student.getClassSchemewise() != null ? student .getClassSchemewise().getClasses().getName() : "");
			if (student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()) {
				objForm.setRegNo(student.getRegisterNo());
			}
			objForm.setDob(null);
			if (student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null
					&& student.getAdmAppln().getPersonalData().getDateOfBirth() != null) {
				objForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat( CommonUtil.getStringDate(student.getAdmAppln()
								.getPersonalData().getDateOfBirth()), SQL_DATEFORMAT, FROM_DATEFORMAT));
			}
			if (student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null) {
				objForm.setCourseId(String.valueOf(student.getAdmAppln() .getCourseBySelectedCourseId().getId()));
			}
			if (student.getClassSchemewise() != null
					&& student.getClassSchemewise() .getCurriculumSchemeDuration() != null && student.getClassSchemewise()
							.getCurriculumSchemeDuration().getAcademicYear() > 0) {
				objForm.setAcademicYear(String.valueOf(student .getClassSchemewise().getCurriculumSchemeDuration() .getAcademicYear()));
				objForm.setCurriculumSchemeDurationId(student .getClassSchemewise().getCurriculumSchemeDuration() .getId());
			}
			if (student.getAdmAppln() != null && !student.getAdmAppln().toString().isEmpty()) {
				objForm.setAdmApplnId(student.getAdmAppln().getId());
			}
			/*if(objForm.getIsPrint().equalsIgnoreCase("print")){
				if(student.getAdmAppln()!=null && student.getAdmAppln().getApplnDocs()!=null){
					Iterator<ApplnDoc> docItr=student.getAdmAppln().getApplnDocs().iterator();
					while (docItr.hasNext()) {
						ApplnDoc doc = docItr.next();
						if(doc.getName()!=null && doc.getIsPhoto() && session != null){
//							session.setAttribute("PhotoBytes", doc.getDocument());
							 FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+"images/StudentPhotos/"+student.getId()+".jpg");
							 fos.write(doc.getDocument());
							 fos.close();
							 CMSConstants.STUDENT_IMAGE = "images/StudentPhotos/"+student.getId()+".jpg";
						}
					}
				}
			}*/
		}
	}
	/** check the regNo is valid or not
	 * if valid , get the receipt number.
	 * set the required details and receipt number in to PcReceipts Bo and Save .
	 * and finally save the ExamRegistrationDetails Bo by make isOnline 'false'.
	 * @param objForm
	 * @param session 
	 * @return
	 * @throws Exception
	 */
	public boolean saveOfflineSAPExamRegistraionDetails( ExamRegistrationDetailsForm objForm, HttpSession session) throws Exception{
		boolean isAdded = false;
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
		String getStuDetailsQuery = ExamRegistrationDetailsHelper.getInstance().getStudentDetailsByRegNO(objForm.getRegNo());
		Student student = (Student) PropertyUtil.getDataForUniqueObject(getStuDetailsQuery);
		if(student!=null && !student.toString().isEmpty()){
			getStudentDetails(student,objForm,session);
			ExamRegistrationDetails details = transaction .getCheckIsAlreadyRegisteredForExam(objForm.getStudentId());
			if(details!=null && !details.toString().isEmpty()){
				throw new  DuplicateException();
			}else{
				int subjectId = Integer.parseInt(CMSConstants.UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID);
				int sapMinMarks = Integer .parseInt(CMSConstants.SAP_EXAM_MIN_MARKS);
				int sapMarksOfStudent = ExamRegistrationDetailsHandler .getInstance().getSAPMarksForStudent(subjectId,objForm.getStudentId());
				if (sapMarksOfStudent > sapMinMarks) {
					throw new BusinessException();
				}
				Users user = cashCollectionTransaction.getUserFromUserId(objForm.getUserId());
				int receiptNumber = transaction.getReceiptNumber(objForm);
				String pcAccountHeadId = CMSConstants.GET_PC_ACCOUNT_HEAD_ID;
				PcReceipts pcReceipts = ExamRegistrationDetailsHelper.getInstance().createPcReceiptsBO(objForm,user,receiptNumber,student,pcAccountHeadId);
				PropertyUtil.getInstance().save(pcReceipts);
				ExamRegistrationDetailsHelper.getInstance().setPcReceiptDetailsToForm(pcReceipts,objForm);
				ExamRegistrationDetails examRegistrationDetails = ExamRegistrationDetailsHelper.getInstance().createOfflineSAPRegistrationBO(objForm,student,pcReceipts);
				isAdded = PropertyUtil.getInstance().save(examRegistrationDetails);
			}
		}else{
			throw new ApplicationException();
		}
		return isAdded;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception 
	 */
	public  int getSAPMarksForStudent(int sapSubject,int studentId) throws Exception {
		String query = ExamRegistrationDetailsHelper.getInstance().getSAPMarksQuery(sapSubject,studentId);
		int StudentSAPObtainedMarks = transaction.getSAPMarksOfStudent(query);
		return StudentSAPObtainedMarks;
	}
}
