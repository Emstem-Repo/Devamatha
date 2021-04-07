package com.kp.cms.helpers.sap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.ExamScheduleDate;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.to.sap.ExamRegistrationDetailsTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtils;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

public class ExamRegistrationDetailsHelper {
	public static final String MSISDN = "MSISDN";
	public static final String PTRefId = "PTRefId";
	public static final String PTDateTime = "PTDateTime";
	public static final String Amount = "Amount";
	public static final String PTVendorId = "PTVendorId";
	public static final String PTOrderId = "PTOrderId";
	public static final String SPID = "SPID";
	public static final String TranType = "TranType";
private static volatile ExamRegistrationDetailsHelper helper;
private static final Log log = LogFactory.getLog(ExamRegistrationDetailsHelper.class);
public static ExamRegistrationDetailsHelper getInstance(){
	if(helper == null){
		helper = new ExamRegistrationDetailsHelper();
		return helper;
	}
	return helper;
}

/**
 * @param objects
 * @return
 * @throws Exception
 */
public List<ExamRegistrationDetailsTo> populateObjListToTOList( List<Object[]> boList) throws Exception{
	 List<ExamRegistrationDetailsTo> sessionDetailsList = new ArrayList<ExamRegistrationDetailsTo>();
	if(boList !=null && !boList.isEmpty()){
		Iterator<Object[]> iterator = boList.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next(); 
			ExamRegistrationDetailsTo to = new ExamRegistrationDetailsTo();
			if(obj[0]!=null && !obj[0].toString().isEmpty()){
				to.setSessionId(Integer.valueOf(obj[0].toString()));
			}
			if(obj[1]!=null && !obj[1].toString().isEmpty()){
				to.setExamDate(CommonUtil.formatSqlDate(obj[1].toString()));
			}
			if(obj[2]!=null && !obj[2].toString().isEmpty()){
				to.setSessionName(obj[2].toString());
			}
			if(obj[3]!=null && !obj[3].toString().isEmpty()){
				to.setCapacity(Integer.valueOf(obj[3].toString()));
			}
			sessionDetailsList.add(to);
		}
	}
	return sessionDetailsList;
	}
/**
 * @param objects
 * @return
 * @throws Exception
 */
public Map<Integer, Integer> convertObjListToMap(List<Object[]> objects) throws Exception{
	Map<Integer, Integer> sessionCountMap = new HashMap<Integer, Integer>();
	if(objects !=null && !objects.isEmpty()){
		Iterator<Object[]> iterator= objects.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			sessionCountMap.put(Integer.valueOf(obj[0].toString()), Integer.valueOf(obj[1].toString()));
		}
	}
	return sessionCountMap;
}

/** put the examDate and their session which are under the workLocation into the Map 
 * Before setting the details in to the map checking that the select session has available venue ,
 * if the venue is available make that session as a active ,otherwise make the session as a inactive 
 * by set true or false to the IsHideSession property.
 *In a Map, examdate is a Key and value is a list of examdate related sessionDetails To.
 * 
 * @param sessionDetailsList
 * @param sessionCountMap
 * @return
 * @throws Exception
 */
public Map<String, List<ExamRegistrationDetailsTo>> copyDetailsToMap( List<ExamRegistrationDetailsTo> sessionDetailsList,
		Map<Integer, Integer> sessionCountMap)throws Exception {
	Map<String, List<ExamRegistrationDetailsTo>> displaySessionDetailsMap = new LinkedHashMap<String, List<ExamRegistrationDetailsTo>>();
	if(sessionDetailsList!=null && !sessionDetailsList.isEmpty()){
		Iterator<ExamRegistrationDetailsTo> iterator = sessionDetailsList.iterator();
		while (iterator.hasNext()) {
			ExamRegistrationDetailsTo to = (ExamRegistrationDetailsTo) iterator .next();
			List<ExamRegistrationDetailsTo> toList = null;
			/* In sessionCountMap Map sessionId is a key and value is their capacity count */
			if(sessionCountMap.containsKey(to.getSessionId())){   					// if the sessionId is not available in sessionCountMap means that has available venue for allotment.
				Integer allotedVenueCount = sessionCountMap.get(to.getSessionId()); // thats why make the isHidesession propery false.
				int totalCount = to.getCapacity();
				int remainingAvaliable = totalCount - allotedVenueCount;
				if(remainingAvaliable>0){					// if venue is available set isHidesession false ,Otherwise true.
					to.setIsHideSession("false");
				}else {
					to.setIsHideSession("true");
				}
			}else{
				to.setIsHideSession("false");
			}
			if(!displaySessionDetailsMap.containsKey(to.getExamDate())){
				toList = new ArrayList<ExamRegistrationDetailsTo>();
				toList.add(to);
			}else{
				toList = displaySessionDetailsMap.get(to.getExamDate());
				toList.add(to);
			}
			displaySessionDetailsMap.put(to.getExamDate(), toList);
		}
	}
	return displaySessionDetailsMap;
}
/**
 * @param sVenueBoList
 * @param allotedVenuesMap
 * @param objForm 
 * @throws Exception
 */
public void assignVenueForStudent(List<ExamScheduleVenue> sVenueBoList, Map<Integer, Integer> allotedVenuesMap, ExamRegistrationDetailsForm objForm) throws Exception{
	if(sVenueBoList!=null && !sVenueBoList.isEmpty()){
		Iterator<ExamScheduleVenue> iterator = sVenueBoList.iterator();
		while (iterator.hasNext()) {
			ExamScheduleVenue bo = (ExamScheduleVenue) iterator .next();
			/* If selected Session has one or more venues  
			 * allotment has done by priority wise ,
			 * Suppose first venue is filled then automatically goes to another venue according to priority
			 * and check that venue has any vacancy.If it is available alloting that venue to the candidate.
			 */
			if(bo.getVenue()!=null){
				if(allotedVenuesMap.containsKey(bo.getVenue().getId())){                // if the venue is there in map, then take the alloted count and check
					int totalCapacity = 0;												// whether that venue available for allotment .
					int allotedVenueCapacity =0;
					if(bo.getVenue().getCapacity()>0){
						 totalCapacity = bo.getVenue().getCapacity();	
					}
					if(bo.getVenue().getId()>0){
						allotedVenueCapacity = allotedVenuesMap.get(bo.getVenue().getId());
					}
					if(allotedVenueCapacity >= totalCapacity){
						continue;
					}else{		
						setVenueDetailsTOForm(objForm, bo);
						break;
					}
				}else{													 //OtherWise assume that venue has no allotment till now and
					setVenueDetailsTOForm(objForm, bo);
					break;
				}
				
			}
			
		}
	}
	
}

/**
 * @param objForm
 * @param bo
 * @throws Exception
 */
private void setVenueDetailsTOForm(ExamRegistrationDetailsForm objForm, ExamScheduleVenue bo) throws Exception{
	if(bo.getVenue().getId()>0){
		objForm.setVenueId(bo.getVenue().getId());
	}
	if(bo.getVenue().getVenueName()!=null && !bo.getVenue().getVenueName().isEmpty()){
		objForm.setVenueName(bo.getVenue().getVenueName());
	}
	if(bo.getExamScheduleDate().getExamDate()!=null && !bo.getExamScheduleDate().getExamDate().toString().isEmpty()){
		objForm.setExamDate(CommonUtil.formatDates(bo.getExamScheduleDate().getExamDate()));
	}
	if(bo.getExamScheduleDate().getSession()!=null && !bo.getExamScheduleDate().getSession().isEmpty()){
		objForm.setSessionName(bo.getExamScheduleDate().getSession());
	}
	if(bo.getVenue().getWorkLocationId()!=null && bo.getVenue().getWorkLocationId().getId()>0){
		objForm.setWorkLocationName(bo.getVenue().getWorkLocationId().getName());
	}
	
}

/**Method for detacting the amount from the account through smart card.
 * @param paymentReciepts
 * @param objForm
 * @throws Exception 
 */
public void dedactAmountFromAccount(OnlinePaymentReciepts paymentReciepts, ExamRegistrationDetailsForm objForm) throws Exception {
	boolean netOrIOExceptionRaised = false;
	StringBuffer response=new StringBuffer();
	try {
		String requestUrl=formatRequestForBank(objForm.getRegNo(),paymentReciepts.getId(),objForm.getFeeAmount()).toString();
		response=SMSUtils.send_request(false,requestUrl);
		
	}catch (TimeoutException e) {
		e.printStackTrace();
		netOrIOExceptionRaised = true;
	}catch (Exception e) {
		e.printStackTrace();
		netOrIOExceptionRaised = true;
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	formatResponse(paymentReciepts, response, netOrIOExceptionRaised);
	//Update the bo, after setting the response which comes from the bank. 
	PropertyUtil.getInstance().update(paymentReciepts);
}
/** method for sending the request for Bank to detact the amount from the account.
 * @param registerNo
 * @param id
 * @param feeAmount
 * @return
 * @throws Exception
 */
	public static StringBuffer formatRequestForBank(String registerNo, int id,
			String feeAmount) throws Exception {
		StringBuffer str = new StringBuffer();
		// get the bankLink from the application.properties to send the request
		// to the bank.
		str.append(CMSConstants.bankLink);
		str.append("&");
		str.append(SMSUtils.getNVP(MSISDN, "REG" + registerNo/* "9633300817" */,
				false));
		str.append(SMSUtils.getNVP(PTRefId, String.valueOf(id), false));
		str.append(SMSUtils.getNVP(PTDateTime, CommonUtil
				.formatDateToDesiredFormatString(CommonUtil.getTodayDate(),
						"dd/MM/yyyy", "dd-MM-yyyy"), false));
		str.append(SMSUtils.getNVP(Amount, String.valueOf(feeAmount)/* 1.0 */,
				false));// in real time uncomment feeAmount and remove 1.00
						// ruppee
		str.append(SMSUtils.getNVP(PTVendorId, String.valueOf("SIB"), false));
		str.append(SMSUtils.getNVP(PTOrderId, String.valueOf(id), false));
		str.append(SMSUtils.getNVP(SPID, "FEESCOLLECT", false));
		str.append(TranType + "=" + "FTD");

		return str;
}

/** method for get the response from the bank and set the information to Bo.
 * @param bo
 * @param response
 * @param netOrIOException
 */
public static void formatResponse(OnlinePaymentReciepts bo, StringBuffer response, boolean netOrIOException){
	boolean isPaymentFailed=true;
	// Process error if any
	if (netOrIOException || !response.toString().contains("MSISDN=")){
		bo.setStatus("Payment Failed Due to TimeOut");
		bo.setIsPaymentFailed(isPaymentFailed);
	}else{
		int x1=response.indexOf("MSISDN=");
		int x2 = x1 + "MSISDN=".length();
		int x3 = response.indexOf("&", x2);
		
		int x4=x3+1+"PTRefId=".length();
		int x5 = response.indexOf("&", x4);
		
		int x6=x5+1+"PTDateTime=".length();
		int x7 = response.indexOf("&", x6);
		bo.setTransactionDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(response.substring(x6,x7), "dd-MM-yyyy","dd/MM/yyyy")));
		
		int x8=x7+1+"status=".length();
		int x9 = response.indexOf("&", x8);
		String status=response.substring(x8,x9);
		if(status.equalsIgnoreCase("000") /*|| status.equalsIgnoreCase("412") || status.equalsIgnoreCase("414") || status.equalsIgnoreCase("409")*/){
			bo.setStatus("success");	
			isPaymentFailed=false;				
		}else if(status.equalsIgnoreCase("114")){
			bo.setStatus("Invalid Account Number");
		}else if(status.equalsIgnoreCase("116")){
			bo.setStatus("Insufficient Balance");
		}else if(status.equalsIgnoreCase("119")){
			bo.setStatus("Transaction Not allowed");
		}else if(status.equalsIgnoreCase("121")){
			bo.setStatus("with drawal limit exceeded");
		}else if(status.equalsIgnoreCase("902")){
			bo.setStatus("Invalid function code");
		}else if(status.equalsIgnoreCase("909")){
			bo.setStatus("system Malfunction");
		}else if(status.equalsIgnoreCase("913")){
			bo.setStatus("Duplicate transaction");
		}else
			bo.setStatus("Payment Failed");
			
		int x10=x9+1+"BankConfirmationId=".length();
		int x11= response.length();
		bo.setBankConfirmationId(response.substring(x10,x11));
		bo.setIsPaymentFailed(isPaymentFailed);
	}
}

/**
 * @param objForm
 * @param subjectGroupId
 * @return
 */
	public ApplicantSubjectGroup setApplicantSubjectGroupBO(
			ExamRegistrationDetailsForm objForm, SubjectGroup subjectGroupBo) {
		ApplicantSubjectGroup applicantSubjectGroup = null;
		applicantSubjectGroup = new ApplicantSubjectGroup();
		AdmAppln admAppln = new AdmAppln();
		admAppln.setId(objForm.getAdmApplnId());
		applicantSubjectGroup.setAdmAppln(admAppln);
		applicantSubjectGroup.setSubjectGroup(subjectGroupBo);
		applicantSubjectGroup.setCreatedBy(objForm.getUserId());
		applicantSubjectGroup.setCreatedDate(new Date());
		applicantSubjectGroup.setModifiedBy(objForm.getUserId());
		applicantSubjectGroup.setLastModifiedDate(new Date());
		return applicantSubjectGroup;
}



/**
 * @param objForm
 * @param subjectGroupBo
 * @param subjectBo
 * @return
 */
	public SubjectGroup createSubjectGroupBo(
			ExamRegistrationDetailsForm objForm, SubjectGroup subjectGroupBo,
			Subject subjectBo) {
		subjectGroupBo.setName(subjectBo.getCode());
		Course course = new Course();
		course.setId(Integer.parseInt(objForm.getCourseId()));
		subjectGroupBo.setCourse(course);
		subjectGroupBo.setIsCommonSubGrp(false);
		subjectGroupBo.setCreatedBy(objForm.getUserId());
		subjectGroupBo.setCreatedDate(new Date());
		subjectGroupBo.setModifiedBy(objForm.getUserId());
		subjectGroupBo.setLastModifiedDate(new Date());
		subjectGroupBo.setIsActive(true);
		/*----------------------------set SubjectGroupSubjects in SubjectGroupBO------------*/
		Set<SubjectGroupSubjects> subjectGroupSubjectsSet = new HashSet<SubjectGroupSubjects>();
		SubjectGroupSubjects subjectGroupSubjects = new SubjectGroupSubjects();
		subjectGroupSubjects.setSubjectGroup(subjectGroupBo);
		subjectGroupSubjects.setSubject(subjectBo);
		subjectGroupSubjects.setCreatedBy(objForm.getUserId());
		subjectGroupSubjects.setCreatedDate(new Date());
		subjectGroupSubjects.setModifiedBy(objForm.getUserId());
		subjectGroupSubjects.setLastModifiedDate(new Date());
		subjectGroupSubjects.setIsActive(true);
		subjectGroupSubjectsSet.add(subjectGroupSubjects);
		subjectGroupBo.setSubjectGroupSubjectses(subjectGroupSubjectsSet);
		return subjectGroupBo;
}

/**
 * @param objForm
 * @param subjectGroupBo
 * @return
 * @throws Exception
 */
	public CurriculumSchemeSubject createCurriculumSchemeSubjectBo(
			ExamRegistrationDetailsForm objForm, SubjectGroup subjectGroupBo)
			throws Exception {
		CurriculumSchemeSubject curriculumSchemeSubject = new CurriculumSchemeSubject();
		curriculumSchemeSubject.setSubjectGroup(subjectGroupBo);
		CurriculumSchemeDuration curriculumSchemeDuration = new CurriculumSchemeDuration();
		curriculumSchemeDuration.setId(objForm.getCurriculumSchemeDurationId());
		curriculumSchemeSubject .setCurriculumSchemeDuration(curriculumSchemeDuration);
		curriculumSchemeSubject.setCreatedBy(objForm.getUserId());
		curriculumSchemeSubject.setCreatedDate(new Date());
		curriculumSchemeSubject.setModifiedBy(objForm.getUserId());
		curriculumSchemeSubject.setLastModifiedDate(new Date());
		return curriculumSchemeSubject;
}

/**
 * @param objForm
 * @param subjectId
 * @return
 */
	public String getSubjectGroupQuery(ExamRegistrationDetailsForm objForm,
			int subjectId) {
		String hqlQuery = "select subGrp from SubjectGroup subGrp inner join subGrp.subjectGroupSubjectses sbs"
							+ " inner join subGrp.curriculumSchemeSubjects css where subGrp.course = " + Integer.parseInt(objForm.getCourseId())
							+ " and sbs.subject.id = " + subjectId
//							+ " and css.curriculumSchemeDuration.academicYear =" + Integer.parseInt(objForm.getAcademicYear())
							+ " and subGrp.isActive=1 ";
		return hqlQuery;
}

	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public String getStudentDetailsByRegNO(String regNo) throws Exception {
		String getStuDetailsQuery = " from Student s where s.isActive=1  and (s.isHide = 0 or s.isHide is null) "
									+ " and s.registerNo='" + regNo + "'";
		return getStuDetailsQuery;
}

	/**
	 * @param objForm
	 * @param student
	 * @param pcReceipts
	 * @return
	 * @throws Exception
	 */
	public ExamRegistrationDetails createOfflineSAPRegistrationBO( ExamRegistrationDetailsForm objForm, Student student, PcReceipts pcReceipts) throws Exception{
		ExamRegistrationDetails examRegistrationDetails = new ExamRegistrationDetails();
		examRegistrationDetails.setStudentId(student);
		/*--------SapVenue Bo-------------*/
		SapVenue sapVenue = new SapVenue();
		sapVenue.setId(objForm.getVenueId());
		examRegistrationDetails.setSapVenueId(sapVenue);
		/*------------------------------*/
		/*--------ExamScheduleDate Bo-------------*/
		ExamScheduleDate scheduleDate = new ExamScheduleDate();
		scheduleDate.setId(objForm.getExamScheduleDateId());
		examRegistrationDetails.setExamScheduleDateId(scheduleDate);
		/*------------------------------*/
		examRegistrationDetails.setOnlinePaymentReceipt(null);
		examRegistrationDetails.setPcReceipts(pcReceipts);
		examRegistrationDetails.setIsOnline(false);
		examRegistrationDetails.setIsPresent(false);
		examRegistrationDetails.setCreatedBy(objForm.getUserId());
		examRegistrationDetails.setCreatedDate(new Date());
		examRegistrationDetails.setLastModifiedDate(new Date());
		examRegistrationDetails.setModifiedBy(objForm.getUserId());
		examRegistrationDetails.setIsPaymentFailed(false);
		examRegistrationDetails.setIsCancelled(false);
		examRegistrationDetails.setIsActive(true);
		return examRegistrationDetails;
	}

	/**
	 * @param objForm
	 * @param user
	 * @param receiptNumber
	 * @param student
	 * @param pcAccountHeadId
	 * @return
	 * @throws Exception
	 */
	public PcReceipts createPcReceiptsBO(ExamRegistrationDetailsForm objForm, Users user, int receiptNumber, Student student, String pcAccountHeadId) throws Exception{
		PcReceipts pcReceipts = new PcReceipts();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		String currentDate = CommonUtil.formatDate(date, "dd/MM/yyyy");
		String finalTime =currentDate+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		java.util.Date paidDate = CommonUtil.ConvertStringToSQLDateTime(finalTime);
		pcReceipts.setNumber(receiptNumber);
		pcReceipts.setPaidDate(paidDate);
		pcReceipts.setRefNo(objForm.getRegNo());
		pcReceipts.setRefType("regNo");
		pcReceipts.setName(objForm.getNameOfStudent());
		pcReceipts.setUsers(user);
		pcReceipts.setAcademicYear(Integer.parseInt(objForm.getAcademicYear()));
		pcReceipts.setStudent(student);
		/*--------PcAccountHeadBo -------------------*/
		PcAccountHead pcAccountHead = new PcAccountHead();
		pcAccountHead.setId(Integer.parseInt(pcAccountHeadId));
		pcReceipts.setPcAccountHead(pcAccountHead);
		/*------------------------------------------------*/
		/*--------PcFinancialYear -------------------*/
		PcFinancialYear pcFinYear = new PcFinancialYear();
		pcFinYear.setId(objForm.getFinancialYear());
		pcReceipts.setPcFinancialYear(pcFinYear);
		/*------------------------------------------------*/
		/*----convert amount from String to BigDecimal and set to the property-------*/
		if(objForm.getTempNetAmount()!=null && !objForm.getTempNetAmount().isEmpty()){
			BigDecimal amount = new BigDecimal(objForm.getTempNetAmount());
			pcReceipts.setAmount(amount);
		}else if(objForm.getFeeAmount()!=null && !objForm.getFeeAmount().isEmpty()){
			BigDecimal amount = new BigDecimal(objForm.getFeeAmount());
			pcReceipts.setAmount(amount);
		}
		/*----------------------------------------------------------------------------*/
		pcReceipts.setIsCancelled(false);
		pcReceipts.setCancelComments("");
		pcReceipts.setCreatedBy(objForm.getUserId());
		pcReceipts.setCreatedDate(new Date());
		pcReceipts.setModifiedBy(objForm.getUserId());
		pcReceipts.setLastModifiedDate(new Date());
		pcReceipts.setIsActive(true);
		pcReceipts.setOffLineApplication(true);
		if(objForm.getConcessionAmount()!=null && !objForm.getConcessionAmount().isEmpty()){
			BigDecimal concessionAmount = new BigDecimal(objForm.getConcessionAmount());
			pcReceipts.setConcessionAmount(concessionAmount);
		}
		if(objForm.getConcessionDetails()!=null && !objForm.getConcessionDetails().isEmpty()){
			pcReceipts.setConcessionDetails(objForm.getConcessionDetails());
		}
		return pcReceipts;
	}

	/**
	 * @param pcReceipts
	 * @param objForm
	 */
	public void setPcReceiptDetailsToForm(PcReceipts pcReceipts, ExamRegistrationDetailsForm objForm) {
		double total=0.00;
		String time="";
		if(pcReceipts!=null && !pcReceipts.toString().isEmpty()){
			if(pcReceipts.getNumber()>0){
				objForm.setReceiptNumber(pcReceipts.getNumber());
			}
			if(pcReceipts.getName()!=null && !pcReceipts.getName().isEmpty()){
				objForm.setNameOfStudent(pcReceipts.getName());
			}
			if(pcReceipts.getRefNo()!=null && !pcReceipts.getRefNo().isEmpty()){
				objForm.setRegNo(pcReceipts.getRefNo());
			}
			if(pcReceipts.getPcAccountHead()!=null && !pcReceipts.getPcAccountHead().toString().isEmpty()){
				objForm.setPcAccHead(pcReceipts.getPcAccountHead().getAccName());
			}
			if(pcReceipts.getUsers()!=null && !pcReceipts.getUsers().toString().isEmpty()){
				objForm.setUserName(pcReceipts.getUsers().getUserName());
			}
			if(pcReceipts.getConcessionDetails()!=null && !pcReceipts.getConcessionDetails().isEmpty()){
				objForm.setDetails(pcReceipts.getConcessionDetails());
			}
			if(pcReceipts.getAmount()!=null){
				total = total + Double.valueOf(pcReceipts.getAmount().doubleValue());
			}
			if(pcReceipts.getPaidDate()!=null){
				time = String.valueOf(pcReceipts.getPaidDate());
			}
			if(!time.isEmpty()){
				String dateString = time.substring(0, 10);
				String inputDateFormat = "yyyy-mm-dd";
				String outPutdateFormat = "dd/mm/yyyy";
				objForm.setPaidDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
				String hour = time.substring(11, 13);
				String minute = time.substring(14, 16);
				objForm.setTime(hour+":"+minute);
				}
			
			objForm.setTotal(String.valueOf(total)+"0");
			objForm.setRupeesInWords(CommonUtil.numberToWord(Double.valueOf(total).intValue()));
			objForm.setReceiptListSize("1");
		}
		
	}

	/**
	 * @param objForm
	 * @param subjectGroupBo
	 * @return
	 * @throws Exception
	 */
	public String checkSubGrpAlreadyExistForStudent( ExamRegistrationDetailsForm objForm, SubjectGroup subjectGroupBo)
			throws Exception {
		String hql_query = "from ApplicantSubjectGroup appSubGrp where appSubGrp.admAppln.id="
							+ objForm.getAdmApplnId()
							+ " and appSubGrp.subjectGroup="
							+ subjectGroupBo.getId();
		return hql_query;
	}

	/**
	 * @param objForm
	 * @return
	 */
	public String getSAPMarksQuery(int sapSubjectId,int studentId) {
		String query = "select max(cast(theory_marks as UNSIGNED INTEGER)) as sap_mark "
						+ "from EXAM_marks_entry_details "
						+ " inner join EXAM_marks_entry ON EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id"
						+ " where student_id="+studentId+" and subject_id="+sapSubjectId;
		return query;
	}

	public String checkCurriculumSchemeSubject(int curriculumSchemeDurationId, SubjectGroup subjectGroupBo)throws Exception {
		String hqlQuery = "from CurriculumSchemeSubject css where css.curriculumSchemeDuration="
							+ curriculumSchemeDurationId + " and css.subjectGroup.id=" + subjectGroupBo.getId();
		return hqlQuery;
	}
}
