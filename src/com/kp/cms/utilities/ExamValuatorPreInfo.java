package com.kp.cms.utilities;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.hostel.HostelWaitingListHandler;
import com.kp.cms.helpers.exam.ValuationStatusSubjectWiseHelper;
import com.kp.cms.to.exam.ExamValuatorPreInfoTO;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.jms.MailTO;

public class ExamValuatorPreInfo implements Job{
	private static final Log log = LogFactory.getLog(StudentSendingMailJob.class);	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//String todaysDate = dateFormat.format(cal.getTime());
			dateFormat.format(cal.getTime());
			cal.add(Calendar.DATE, -1);
			String yesterdayDate = dateFormat.format(cal.getTime()); // getting perviousDay Date
			Date previousDayDate = CommonUtil .ConvertStringToSQLDate(yesterdayDate);
			/* Based on previousDay date we are fetching the data from ExamTImeTable 
			 * */
			List<Object[]> examTimeTableBo = AdmissionFormTransactionImpl .getInstance().getExamTimeTableDetailsForPreviousDay( previousDayDate);
			if (examTimeTableBo != null && !examTimeTableBo.isEmpty()) {
				Map<Integer, ExamValuatorPreInfoTO> valuatorDetailsMap = new HashMap<Integer, ExamValuatorPreInfoTO>();
				Map<Integer, ExamValuatorPreInfoTO> externalDetailsMap = new HashMap<Integer, ExamValuatorPreInfoTO>();
				/*converting the list of Objects into the Map ,ExamId as a KEY and list of SubjectIds as a VALUE*/
				Map<Integer, List<Integer>> examTimeTableMap = convertListToMap(examTimeTableBo);
				/* Iterating the Map 
				 * and fetching the data from Valuation Schedule Details based on examId and list of subjectId for that Exam.
				 */
				Iterator<Map.Entry<Integer, List<Integer>>> iterator = examTimeTableMap .entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<java.lang.Integer, java.util.List<java.lang.Integer>> examSubjectsMap = (Map.Entry<java.lang.Integer, java.util.List<java.lang.Integer>>) iterator
							.next();
					int examId = examSubjectsMap.getKey();
					List<Integer> subjectIds = examSubjectsMap.getValue();
					List<ExamValuationScheduleDetails> valuationScheduleDetails = AdmissionFormTransactionImpl .getInstance().getvaluationScheduleDeatils(examId, subjectIds);
					if(valuationScheduleDetails!=null && !valuationScheduleDetails.isEmpty()){ 
					sendSMSAndMailsToValuators(valuationScheduleDetails, valuatorDetailsMap, externalDetailsMap);
					}
				}		
					//send mail to applicant
					Properties prop = new Properties();
					String templateName = CMSConstants.VALUATION_SCHEDULE_REMAINDER;
					InputStream inStr = CommonUtil.class.getClassLoader() .getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					prop.load(inStr);
					String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
					String fromAddress=CMSConstants.MAIL_USERID;
					String desc ="";
					//send sms to applicant
					Properties prop1 = new Properties();
					InputStream in1 = ExamValuatorPreInfo.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
					prop1.load(in1);
					String senderNumber=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
					String senderName=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
					String desc1="";
					/*Here iterating the Valuator Map and sending the Mail and Sms to the appropiate persons*/
				if(valuatorDetailsMap!=null && !valuatorDetailsMap.isEmpty()){
					Iterator<Map.Entry<Integer, ExamValuatorPreInfoTO>> iterator2 = valuatorDetailsMap.entrySet().iterator();
					while (iterator2.hasNext()) {
						Map.Entry<java.lang.Integer, com.kp.cms.to.exam.ExamValuatorPreInfoTO> entry = (Map.Entry<java.lang.Integer, com.kp.cms.to.exam.ExamValuatorPreInfoTO>) iterator2
								.next();
						//send mail to applicant
						
						List<GroupTemplate> list=null;
						//get template and replace dynamic data
						TemplateHandler temphandle=TemplateHandler.getInstance();
						 list= temphandle.getDuplicateCheckList(templateName);
						if(list != null && !list.isEmpty()) {
							desc = list.get(0).getTemplateDescription();
						}
						desc=desc.replace(CMSConstants.EXAM_DATE,yesterdayDate);
						//
						//send sms to applicant
						SMSTemplateHandler temphandle1=SMSTemplateHandler.getInstance();
						List<SMSTemplate> list1= temphandle1.getDuplicateCheckList(templateName);
						if(list1 != null && !list1.isEmpty()) {
							desc1 = list1.get(0).getTemplateDescription();
						}
						desc1=desc1.replace(CMSConstants.EXAM_DATE,yesterdayDate);
						//
						ExamValuatorPreInfoTO infoTO = entry.getValue();
						//String mobileNO= "91"+ infoTO.getToMobileNo();
						String mobileNO = infoTO.getToMobileNo();
						if(infoTO.getEmail()!=null && !infoTO.getEmail().isEmpty()){
								String msg ="<table width='50%' border='0' cellpadding='0' cellspacing='0'>" +
								"<tr>" +
								"<td align='left' width='5%'> Sl.No</td>" +
								"<td align='left' width='20%'> Subject Name</td>" +
								"<td align='left' width='20%'> Subject Code</td>" +
								"<td align='left' width='5%'> No of Scripts </td>" +
								"</tr><tr></tr>"+infoTO.getMessage()+
								"</table>";
						
								MailTO mailTO = new MailTO();
								desc= desc.replace(CMSConstants.SUBJECT_DETAILS,msg);
								mailTO.setFromAddress(fromAddress);
								mailTO.setFromName(fromName);
								mailTO.setMessage(desc);
								mailTO.setSubject(infoTO.getSubject());
								mailTO.setToAddress(infoTO.getEmail());
								//sending tha mail
								CommonUtil.sendMail(mailTO);
						}
						if(StringUtils.isNumeric(mobileNO) && (mobileNO.length()==12 && desc1.length()<=160)){
							if(CMSConstants.VALUATION_SCHEDULE_REMAINDER_SMS_SENT){
								MobileMessaging mob=new MobileMessaging();
								mob.setDestinationNumber(mobileNO);
								mob.setMessageBody(desc1);
								mob.setMessagePriority(3);
								mob.setSenderName(senderName);
								mob.setSenderNumber(senderNumber);
								mob.setMessageEnqueueDate(new java.util.Date());
								mob.setIsMessageSent(false);
								PropertyUtil.getInstance().save(mob);
							}
						}
					}
				}
				/*Here iterating the externalValuators Map and sending the Mail and Sms to the appropiate persons*/
				if(externalDetailsMap!=null && !externalDetailsMap.isEmpty()){
					Iterator<Map.Entry<Integer, ExamValuatorPreInfoTO>> iterator3 = externalDetailsMap.entrySet().iterator();
						while (iterator3.hasNext()) {
							Map.Entry<java.lang.Integer, com.kp.cms.to.exam.ExamValuatorPreInfoTO> entry = (Map.Entry<java.lang.Integer, com.kp.cms.to.exam.ExamValuatorPreInfoTO>) iterator3
							.next();
								ExamValuatorPreInfoTO infoTO = entry.getValue();
								List<GroupTemplate> list=null;
								//get template and replace dynamic data
								TemplateHandler temphandle=TemplateHandler.getInstance();
								 list= temphandle.getDuplicateCheckList(templateName);
								if(list != null && !list.isEmpty()) {
									desc = list.get(0).getTemplateDescription();
								}
								desc=desc.replace(CMSConstants.EXAM_DATE,yesterdayDate);
								//
								//send sms to applicant
								SMSTemplateHandler temphandle1=SMSTemplateHandler.getInstance();
								List<SMSTemplate> list1= temphandle1.getDuplicateCheckList(templateName);
								if(list1 != null && !list1.isEmpty()) {
									desc1 = list1.get(0).getTemplateDescription();
								}
								desc1=desc1.replace(CMSConstants.EXAM_DATE,yesterdayDate);
								//
								String mobileNO= infoTO.getToMobileNo();
								if(infoTO.getEmail()!=null && !infoTO.getEmail().isEmpty()){
										String msg ="<table width='50%' border='0' cellpadding='0' cellspacing='0'>" +
										"<tr>" +
										"<td align='left' width='5%'> Sl.No</td>" +
										"<td align='left' width='20%'> Subject Name</td>" +
										"<td align='left' width='20%'> Subject Code</td>" +
										"<td align='left' width='5%'> No of Scripts </td>" +
										"</tr><tr></tr>"+infoTO.getMessage()+
										"</table>";
										MailTO mailTO = new MailTO();
										desc= desc.replace(CMSConstants.SUBJECT_DETAILS,msg);
										mailTO.setFromAddress(fromAddress);
										mailTO.setFromName(fromName);
										mailTO.setMessage(desc);
										mailTO.setSubject(infoTO.getSubject());
										mailTO.setToAddress(infoTO.getEmail());
										//sending the mail
										CommonUtil.sendMail(mailTO);
								}
								if(StringUtils.isNumeric(mobileNO) && (mobileNO.length()==12 && desc1.length()<=160)){
									if(CMSConstants.VALUATION_SCHEDULE_REMAINDER_SMS_SENT){
										MobileMessaging mob=new MobileMessaging();
										mob.setDestinationNumber(mobileNO);
										mob.setMessageBody(desc1);
										mob.setMessagePriority(3);
										mob.setSenderName(senderName);
										mob.setSenderNumber(senderNumber);
										mob.setMessageEnqueueDate(new java.util.Date());
										mob.setIsMessageSent(false);
										PropertyUtil.getInstance().save(mob);
									}
								}
						}
				}
			}
		} catch (Exception e) {
			log.error("Error in ExamValuatorPreInfo"
					+ Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
	}
	/**
	 * @param examValuationScheduleDetails
	 * @return
	 * @throws Exception
	 */
	private int getCalculatedTotalNoOfAnswerScripts( ExamValuationScheduleDetails examValuationScheduleDetails) throws Exception{
		if(examValuationScheduleDetails.getSubject().getId() == 3968){
			int i=0;
		}
		String absentStudentQuery = getAbsentStudentList(examValuationScheduleDetails);
		List<Integer> absentStudentList = getDataQuery(absentStudentQuery);
		String examType = getExamTypeByExamId(examValuationScheduleDetails.getExam().getId());
		int totalAnswerScripts = 0;
		if(!examType.equalsIgnoreCase("Supplementary")){
			String currentStudentQuery = getQueryForCurrentClassStudents(examValuationScheduleDetails);
			List<Integer> currentStudentIdsList=getDataQuery(currentStudentQuery);
			if(currentStudentIdsList!=null && !currentStudentIdsList.isEmpty()){
				totalAnswerScripts = getTotalAnswerScripts(absentStudentList,currentStudentIdsList,totalAnswerScripts);
			}
			String previousStudentQuery = getQueryForPreviousClassStudents(examValuationScheduleDetails);
			List<Integer> previousStudentList=getDataQuery(previousStudentQuery);
			if(previousStudentList!=null && !previousStudentList.isEmpty()){
				totalAnswerScripts = getTotalAnswerScripts(absentStudentList,previousStudentList,totalAnswerScripts);
			}
		}else{
			String supCurrentStudentQuery = getQueryForSupplementaryCurrentClassStudents(examValuationScheduleDetails);
			List<Integer> supCurrentStudentIds = getDataQuery(supCurrentStudentQuery);
			if(supCurrentStudentIds!=null && !supCurrentStudentIds.isEmpty()){
				totalAnswerScripts = getTotalAnswerScripts(absentStudentList,supCurrentStudentIds,totalAnswerScripts);
			}
			String supPreviousStudentQuery = getQueryForSupplementaryPreviousClassStudents(examValuationScheduleDetails);
			List<Integer> supPreviousStudentIds = getDataQuery(supPreviousStudentQuery);
			if(supPreviousStudentIds!=null && !supPreviousStudentIds.isEmpty()){
				totalAnswerScripts = getTotalAnswerScripts(absentStudentList,supPreviousStudentIds,totalAnswerScripts);
			}
		}
		return totalAnswerScripts;
	}
	/**
	 * @param examValuationScheduleDetails
	 * @return
	 * @throws Exception
	 */
	private String getQueryForSupplementaryPreviousClassStudents( ExamValuationScheduleDetails examValuationScheduleDetails) throws Exception{
		String query="select s.id from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +examValuationScheduleDetails.getSubject().getId()+
		" and supImp.examDefinition.id=" +examValuationScheduleDetails.getExam().getId()+
		" where s.admAppln.isCancelled=0 and subSet.isActive=1" +
		" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e" +
		" where e.id="+examValuationScheduleDetails.getExam().getId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+examValuationScheduleDetails.getSubject().getSchemeNo()+
		" and subSet.subject.id="+examValuationScheduleDetails.getSubject().getId();
	if(examValuationScheduleDetails.getSubject().getSubjectType()!=null && !examValuationScheduleDetails.getSubject().getSubjectType().toString().isEmpty()){
		if(examValuationScheduleDetails.getSubject().getSubjectType().getName().equalsIgnoreCase("Theory")){
			query=query+" and supImp.isAppearedTheory=1";
		}else if(examValuationScheduleDetails.getSubject().getSubjectType().getName().equalsIgnoreCase("Practical")){
			query=query+" and supImp.isAppearedPractical=1";
		}else{
			query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
		}
	}
	return query;
}
	/**
	 * @param examValuationScheduleDetails
	 * @return
	 * @throws Exception
	 */
	private String getQueryForSupplementaryCurrentClassStudents( ExamValuationScheduleDetails examValuationScheduleDetails) throws Exception{
		String query="select s.id from Student s" +
		" join s.classSchemewise.classes c" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+examValuationScheduleDetails.getSubject().getId()+
		" and supImp.examDefinition.id="+examValuationScheduleDetails.getExam().getId()+
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e where e.id="+examValuationScheduleDetails.getExam().getId()+")" +
		" and subGroup.subject.id="+examValuationScheduleDetails.getSubject().getId()+
		" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+examValuationScheduleDetails.getSubject().getSchemeNo();
		if(examValuationScheduleDetails.getSubject().getSubjectType()!=null && !examValuationScheduleDetails.getSubject().getSubjectType().toString().isEmpty()){
			if(examValuationScheduleDetails.getSubject().getSubjectType().getName().equalsIgnoreCase("Theory")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(examValuationScheduleDetails.getSubject().getSubjectType().getName().equalsIgnoreCase("Practical")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and (supImp.isAppearedTheory=1 or supImp.isAppearedPractical=1)";
			}
		}
		return query;
	}
	/**
	 * @param examValuationScheduleDetails
	 * @return
	 * @throws Exception
	 */
	private String getQueryForPreviousClassStudents( ExamValuationScheduleDetails examValuationScheduleDetails) throws Exception{
		String query="select s.id from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0" +
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+examValuationScheduleDetails.getExam().getId()+")" +
		" and subSet.subject.id="+examValuationScheduleDetails.getSubject().getId()+" and classHis.schemeNo=subjHist.schemeNo";
		return query;
	}
	/**
	 * @param absentStudentList
	 * @param currentStudentIdsList
	 * @param totalAnswerScripts
	 * @return
	 * @throws Exception
	 */
	private int getTotalAnswerScripts(List<Integer> absentStudentList, List<Integer> currentStudentIdsList, int totalAnswerScripts)throws Exception {
		if(currentStudentIdsList!=null && !currentStudentIdsList.isEmpty()){
			Iterator<Integer> iterator = currentStudentIdsList.iterator();
			while (iterator.hasNext()) {
				Integer studentId = (Integer) iterator.next();
				if(!absentStudentList.contains(studentId)){
					totalAnswerScripts = totalAnswerScripts + 1;
				}
			}
		}
		return totalAnswerScripts;
	}
	/**
	 * @param examValuationScheduleDetails
	 * @return
	 * @throws Exception
	 */
	private String getQueryForCurrentClassStudents( ExamValuationScheduleDetails examValuationScheduleDetails) throws Exception{
		String query="select s.id from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0" +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+examValuationScheduleDetails.getExam().getId()+") " +
		" and subGroup.subject.id=" +examValuationScheduleDetails.getSubject().getId();
		return query;
	
	}
	/**
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	private String getExamTypeByExamId(int examId) throws Exception{ 
		Session session =null;
		String examType ="";
		try{
			session = HibernateUtil.getSession();
			String query = " select examDefBo.examTypeUtilBO.name from ExamDefinitionBO " +
							" examDefBo where examDefBo.isActive =1 and examDefBo.id="+examId;
		Query query1 = session.createQuery(query);
		examType = (String) query1.uniqueResult();
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		return examType;
	}
	/**
	 * @param absentStudentQuery
	 * @return
	 */
	private List<Integer> getDataQuery(String absentStudentQuery)throws Exception {
		Session session = null;
		List<Integer> absentStudentList = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(absentStudentQuery);
			absentStudentList = query.list();
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return absentStudentList;
	}
	/**
	 * @param examValuationScheduleDetails
	 * @return
	 * @throws Exception
	 */
	private String getAbsentStudentList( ExamValuationScheduleDetails examValuationScheduleDetails) throws Exception{
		String query="select m.marksEntry.student.id from MarksEntryDetails m"+
		" where m.subject.id=" +examValuationScheduleDetails.getSubject().getId()+
		" and m.marksEntry.exam.id="+examValuationScheduleDetails.getExam().getId()+
		" and (m.marksEntry.evaluatorType is null or m.marksEntry.evaluatorType = 1)";
		if(examValuationScheduleDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
			query=query+" and m.theoryMarks = 'AA'";
		}else if(examValuationScheduleDetails.getSubject().getSubjectType().getName().equalsIgnoreCase("P")){
			query=query+" and m.practicalMarks = 'AA'";
		}else if(examValuationScheduleDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
			query=query+" and (m.theoryMarks = 'AA' or m.practicalMarks = 'AA')";
		}
		query = query + " and m.marksEntry.student.admAppln.isCancelled=0" +
				" and (m.marksEntry.student.isHide=0 or m.marksEntry.student.isHide is null) " +
				" and m.marksEntry.student.isActive=1";
	
	return query;
	
		
	}
	/** In these method ,separating the valuators and external valuators and
	 * putting into two different Maps
	 * @param valuationScheduleDetails
	 * @param valuatorDetailsMap 
	 * @throws Exception
	 */
	private void sendSMSAndMailsToValuators( List<ExamValuationScheduleDetails> valuationScheduleDetails, Map<Integer, ExamValuatorPreInfoTO> valuatorDetailsMap,
			Map<Integer, ExamValuatorPreInfoTO> externalDetailsMap) throws Exception{
		if(valuationScheduleDetails!=null && !valuationScheduleDetails.isEmpty()){
			Iterator<ExamValuationScheduleDetails> iterator = valuationScheduleDetails.iterator();
			while (iterator.hasNext()) {
				ExamValuationScheduleDetails examValuationScheduleDetails = (ExamValuationScheduleDetails) iterator .next();
				// Getting the total  No of Papers of the Exam and subject
				Integer noOfPapers = getCalculatedTotalNoOfAnswerScripts(examValuationScheduleDetails);
				//
				int valuatorOrReviewerId = 0;
				if(examValuationScheduleDetails.getUsers()!=null && !examValuationScheduleDetails.getUsers().toString().isEmpty()){
					if(examValuationScheduleDetails.getUsers().getId()!=0){
						valuatorOrReviewerId = examValuationScheduleDetails.getUsers().getId();
						if(valuatorDetailsMap.containsKey(valuatorOrReviewerId)){
							ExamValuatorPreInfoTO infoTO = valuatorDetailsMap.get(valuatorOrReviewerId);
							String msg = infoTO.getMessage();
							String sujectCode = examValuationScheduleDetails.getSubject().getCode();
							String subjectName = examValuationScheduleDetails.getSubject().getName();
							int slno = Integer.parseInt(infoTO.getSlno());
							slno++;
							infoTO.setSlno(String.valueOf(slno));
							msg = msg + "<tr>" +
							"<td align='left'>"+slno+"</td>" +
							"<td align='left'>"+subjectName+"</td>" +
							"<td align='left'>"+sujectCode+"</td>" +
							"<td align='left'>"+noOfPapers+"</td></tr>";
							infoTO.setMessage(msg);
							valuatorDetailsMap.put(valuatorOrReviewerId, infoTO);
						}else{
							ExamValuatorPreInfoTO infoTO = new ExamValuatorPreInfoTO();
							infoTO.setSubject("Valuation Schedule Reminder");
							String msg = "";
							String emailId ="";
							String mobileNo ="91";
							int slno=0;
							if(examValuationScheduleDetails.getUsers().getEmployee()!=null){
								if(examValuationScheduleDetails.getUsers().getEmployee().getWorkEmail()!=null && 
										!examValuationScheduleDetails.getUsers().getEmployee().getWorkEmail().isEmpty()){
									emailId = examValuationScheduleDetails.getUsers().getEmployee().getWorkEmail();
									//emailId = "sudheerkrishnam@gmail.com";
								}
								if(examValuationScheduleDetails.getUsers().getEmployee().getCurrentAddressMobile1()!=null && 
										!examValuationScheduleDetails.getUsers().getEmployee().getCurrentAddressMobile1().isEmpty()){
									mobileNo = mobileNo+examValuationScheduleDetails.getUsers().getEmployee().getCurrentAddressMobile1();
									//mobileNo=mobileNo+"9611928560";
								}
							}else if(examValuationScheduleDetails.getUsers().getGuest()!=null){
								if(examValuationScheduleDetails.getUsers().getGuest().getWorkEmail()!=null && 
										!examValuationScheduleDetails.getUsers().getGuest().getWorkEmail().isEmpty()){
									emailId = examValuationScheduleDetails.getUsers().getGuest().getWorkEmail();
									//emailId = "sudheerkrishnam@gmail.com";
								}
								if(examValuationScheduleDetails.getUsers().getGuest().getCurrentAddressMobile1()!=null
										&& !examValuationScheduleDetails.getUsers().getGuest().getCurrentAddressMobile1().isEmpty()){
									mobileNo = mobileNo+examValuationScheduleDetails.getUsers().getGuest().getCurrentAddressMobile1();
									//mobileNo=mobileNo+"9611928560";
								}
							}
							slno++;
							infoTO.setSlno(String.valueOf(slno));
							String sujectCode = examValuationScheduleDetails.getSubject().getCode();
							String subjectName = examValuationScheduleDetails.getSubject().getName();
							msg = "<tr>" +
									"<td align='left'>"+slno+"</td>" +
									"<td align='left'>"+subjectName+"</td>" +
									"<td align='left'>"+sujectCode+"</td>" +
									"<td align='left'>"+noOfPapers+"</td></tr>";
							infoTO.setEmail(emailId);
							infoTO.setToMobileNo(mobileNo);
							infoTO.setMessage(msg);
							valuatorDetailsMap.put(valuatorOrReviewerId, infoTO);
						}
					}
				}else if(examValuationScheduleDetails.getExamValuators()!=null && !examValuationScheduleDetails.getExamValuators().toString().isEmpty()){
					if(examValuationScheduleDetails.getExamValuators().getId()!=0){
						valuatorOrReviewerId = examValuationScheduleDetails.getExamValuators().getId();
						if(externalDetailsMap.containsKey(valuatorOrReviewerId)){
							ExamValuatorPreInfoTO infoTO = externalDetailsMap.get(valuatorOrReviewerId);
							String msg = infoTO.getMessage();
							String sujectCode = examValuationScheduleDetails.getSubject().getCode();
							String subjectName = examValuationScheduleDetails.getSubject().getName();
							int slno = Integer.parseInt(infoTO.getSlno());
							slno++;
							infoTO.setSlno(String.valueOf(slno));
							msg = msg + "<tr>" +
							"<td align='left' width='5%' height='5'>"+slno+"</td>" +
							"<td align='left' width='20%' height='5'>"+subjectName+"</td>" +
							"<td align='left' width='20%' height='5'>"+sujectCode+"</td>" +
							"<td align='left' width='5%' height='5'>"+noOfPapers+"</td></tr>";
							infoTO.setMessage(msg);
							externalDetailsMap.put(valuatorOrReviewerId, infoTO);
						}else{
							ExamValuatorPreInfoTO infoTO = new ExamValuatorPreInfoTO();
							infoTO.setSubject("Valuation Schedule Reminder");
							String msg = "";
							String emailId ="";
							String mobileNo ="91";
							int slno=0;
							if(examValuationScheduleDetails.getExamValuators().getEmail()!=null && !examValuationScheduleDetails.getExamValuators().getEmail().isEmpty()){
								emailId = examValuationScheduleDetails.getExamValuators().getEmail();
							} 
							if(examValuationScheduleDetails.getExamValuators().getMobile()!=null && !examValuationScheduleDetails.getExamValuators().getMobile().isEmpty()){
								mobileNo =mobileNo+examValuationScheduleDetails.getExamValuators().getMobile();
							}
							String sujectCode = examValuationScheduleDetails.getSubject().getCode();
							String subjectName = examValuationScheduleDetails.getSubject().getName();
							slno++;
							infoTO.setSlno(String.valueOf(slno));
							msg = "<tr>" +
									"<td align='left' width='5%' height='5'>"+slno+"</td>" +
									"<td align='left' width='20%' height='5'>"+subjectName+"</td>" +
									"<td align='left' width='20%' height='5'>"+sujectCode+"</td>" +
									"<td align='left' width='5%' height='5'>"+noOfPapers+"</td></tr>";
							infoTO.setEmail(emailId);
							infoTO.setToMobileNo(mobileNo);
							infoTO.setMessage(msg);
							externalDetailsMap.put(valuatorOrReviewerId, infoTO);
						}
					}
				}
			}
		}
		
	}
	/** Converting the list of objects into HashMap
	 * @param examTimeTableBo
	 * @return
	 */
	private Map<Integer, List<Integer>> convertListToMap( List<Object[]> examTimeTableBo) {
		Map<Integer, List<Integer>> examAndSubjectIdMap = new HashMap<Integer, List<Integer>>();
		if(examTimeTableBo!=null && !examTimeTableBo.isEmpty()){
			Iterator<Object[]> iterator = examTimeTableBo.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj[0]!=null && obj[1]!=null){
					/*if the map contains examId ,we are getting the list of subjects of that exam and 
					 * adding the current subject to that list and again putting into the same Map,
					 * Otherwise Creating the new ArrayList and adding the current subject in to that list
					 * and putting into the Map.
					 */
					if(examAndSubjectIdMap.containsKey(Integer.parseInt(obj[0].toString()))){    
						List<Integer> subjectList =examAndSubjectIdMap.get(Integer.parseInt(obj[0].toString())) ;
						subjectList.add(Integer.parseInt(obj[1].toString()));
						examAndSubjectIdMap.put(Integer.parseInt(obj[0].toString()), subjectList); //here KEY is ExamId and Value is list of subjects
					}else{
						List<Integer> subjectList = new ArrayList<Integer>();
						subjectList.add(Integer.parseInt(obj[1].toString()));
						examAndSubjectIdMap.put(Integer.parseInt(obj[0].toString()),subjectList); //here KEY is ExamId and Value is list of subjects
					}
				}
			}
		}
		return examAndSubjectIdMap;
	}

}
