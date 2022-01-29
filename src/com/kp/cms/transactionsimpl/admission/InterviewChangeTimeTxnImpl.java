package com.kp.cms.transactionsimpl.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewScheduleHistory;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.helpers.admission.InterviewHelper;
import com.kp.cms.to.admission.InterviewTimeChangeTO;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactions.admission.IInterviewChangeTimeTxn;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author kalyan.c
 * DAO Class for Edit interview Schedule
 */
public class InterviewChangeTimeTxnImpl implements IInterviewChangeTimeTxn{
	private static final Log log = LogFactory.getLog(InterviewChangeTimeTxnImpl.class);
	private static volatile InterviewChangeTimeTxnImpl interviewChangeTimeTxnImpl = null;

	private InterviewChangeTimeTxnImpl() {

	}

	/**
	 * @return
	 * This method will return instance of InterviewChangeTimeTxnImpl
	 */
	public static InterviewChangeTimeTxnImpl getInstance() {
		if (interviewChangeTimeTxnImpl == null) {
			interviewChangeTimeTxnImpl = new InterviewChangeTimeTxnImpl();
		}
		return interviewChangeTimeTxnImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewChangeTimeTxn#getSelectedCandidates(com.kp.cms.to.admission.InterviewTimeChangeTO)
	 * The method will get selected candidates
	 */
	public List getSelectedCandidates(InterviewTimeChangeTO interviewBatchEntryTO,Set<Integer>interviewTypSet) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery;
			String startTime = interviewBatchEntryTO.getStartTimeHours() +":"+ interviewBatchEntryTO.getStartTimeMins();
			String endTime = interviewBatchEntryTO.getEndTimeHours() +":"+ interviewBatchEntryTO.getEndTimeMins();
			String appRange="";
			String selectedCheck="";
			if(interviewBatchEntryTO.getAppNoForm()>0){
				appRange=appRange+" and admAppln.applnNo>="+interviewBatchEntryTO.getAppNoForm();
			}
			if(interviewBatchEntryTO.getAppNoTo()>0){
				appRange=appRange+" and admAppln.applnNo<="+interviewBatchEntryTO.getAppNoTo();
			}
			if(!CMSConstants.LINK_FOR_CJC){
				selectedCheck=" and admAppln.isSelected=0 and (admAppln.admStatus is null or admAppln.admStatus='') ";
			}
			if(interviewBatchEntryTO.getInterviewDate() != null && !interviewBatchEntryTO.getInterviewDate().isEmpty() && (!startTime.equals("00:00") || !endTime.equals("00:00"))){
					if(!endTime.equals("00:00")){
						String strQuery="select admAppln.id, " +
						" admAppln.applnNo, " +
						" admAppln.appliedYear, " +
						" admAppln.personalData.firstName," +
						" admAppln.personalData.middleName, " +
						" admAppln.personalData.lastName, " +
						" admAppln.courseBySelectedCourseId.id, " +
						" admAppln.courseBySelectedCourseId.name, " +
						" interviewSelected.interviewProgramCourse, " +							
						" interviewCard.interview.date, " +
						" interviewCard.id, " +							
						" interviewCard.time, " +
						" admAppln.personalData.email,interviewCard.interview.startTime," +
						" interviewCard.interview.endTime," +
						" interviewCard.interviewer,interviewCard.interview.venue,interviewCard.interview.interview.id,admAppln.interScheduleSelection.id " +
						" from AdmAppln admAppln " +
						" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in (:interviewRound) and interviewSelected.isCardGenerated = 1 " +
						" join admAppln.interviewCards interviewCard with interviewCard.time = :startTime and interviewCard.time <:endTime " +
						" where admAppln.appliedYear = :appliedYear " +selectedCheck+
						" and interviewCard.interview.date = :interviewDate"+appRange;
						if(!interviewBatchEntryTO.getCourseId().isEmpty())
							strQuery+=" and admAppln.courseBySelectedCourseId.id = :courseId ";
						selectedCandidatesQuery = session.createQuery(strQuery);
						selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
						if(!interviewBatchEntryTO.getCourseId().isEmpty())
							selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
						selectedCandidatesQuery.setParameterList("interviewRound", interviewTypSet);
						selectedCandidatesQuery.setString("startTime", startTime);
						selectedCandidatesQuery.setString("endTime", endTime);
						selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
					}else{
						
						String strQuery="select admAppln.id, " +
						" admAppln.applnNo, " +
						" admAppln.appliedYear, " +
						" admAppln.personalData.firstName," +
						" admAppln.personalData.middleName, " +
						" admAppln.personalData.lastName, " +
						" admAppln.courseBySelectedCourseId.id, " +
						" admAppln.courseBySelectedCourseId.name, " +
						" interviewSelected.interviewProgramCourse, " +							
						" interviewCard.interview.date, " +
						" interviewCard.id, " +							
						" interviewCard.time, " +
						" admAppln.personalData.email,interviewCard.interview.startTime," +
						" interviewCard.interview.endTime," +
						" interviewCard.interviewer,interviewCard.interview.venue,interviewCard.interview.interview.id,admAppln.interScheduleSelection.id " +
						" from AdmAppln admAppln " +
						" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in (:interviewRound) and interviewSelected.isCardGenerated = 1 " +
						" join admAppln.interviewCards interviewCard with interviewCard.time = :startTime " +
						" where admAppln.appliedYear = :appliedYear " +selectedCheck+
						" and interviewCard.interview.date = :interviewDate"+appRange;
						if(!interviewBatchEntryTO.getCourseId().isEmpty())
							strQuery+=" and admAppln.courseBySelectedCourseId.id = :courseId ";
						selectedCandidatesQuery = session.createQuery(strQuery);
						selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
						if(!interviewBatchEntryTO.getCourseId().isEmpty())
							selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
						selectedCandidatesQuery.setParameterList("interviewRound", interviewTypSet);
						selectedCandidatesQuery.setString("startTime", startTime);
						//selectedCandidatesQuery.setString("endTime", endTime);
						selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
					}
					
			}else if((interviewBatchEntryTO.getInterviewDate() == null || interviewBatchEntryTO.getInterviewDate().isEmpty()) && !startTime.equals("00:00") && !endTime.equals("00:00")){
				String strQuery="select admAppln.id, " +
				" admAppln.applnNo, " +
				" admAppln.appliedYear, " +
				" admAppln.personalData.firstName," +
				" admAppln.personalData.middleName, " +
				" admAppln.personalData.lastName, " +
				" admAppln.courseBySelectedCourseId.id, " +
				" admAppln.courseBySelectedCourseId.name, " +
				" interviewSelected.interviewProgramCourse, " +							
				" interviewCard.interview.date, " +
				" interviewCard.id, " +							
				" interviewCard.time, " +
				" admAppln.personalData.email ,interviewCard.interview.startTime," +
				" interviewCard.interview.endTime," +
				" interviewCard.interviewer,interviewCard.interview.venue,interviewCard.interview.interview.id,admAppln.interScheduleSelection.id  " +
				" from AdmAppln admAppln" +
				" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in (:interviewRound) and interviewSelected.isCardGenerated = 1 " +
				" join admAppln.interviewCards interviewCard with interviewCard.time = :startTime and interviewCard.time <:endTime " +
				" where admAppln.appliedYear = :appliedYear "+selectedCheck+appRange;
				if(!interviewBatchEntryTO.getCourseId().isEmpty())
					strQuery+=" and admAppln.courseBySelectedCourseId.id = :courseId ";
				selectedCandidatesQuery = session.createQuery(strQuery);
				selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
				if(!interviewBatchEntryTO.getCourseId().isEmpty())
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
				selectedCandidatesQuery.setParameterList("interviewRound", interviewTypSet);
				selectedCandidatesQuery.setString("startTime", startTime);
				selectedCandidatesQuery.setString("endTime", endTime);
				
			}else if(interviewBatchEntryTO.getInterviewDate() != null && !interviewBatchEntryTO.getInterviewDate().isEmpty()){
				String strQuery="select admAppln.id, " +
				" admAppln.applnNo, " +
				" admAppln.appliedYear, " +
				" admAppln.personalData.firstName," +
				" admAppln.personalData.middleName, " +
				" admAppln.personalData.lastName, " +
				" admAppln.courseBySelectedCourseId.id, " +
				" admAppln.courseBySelectedCourseId.name, " +
				" interviewSelected.interviewProgramCourse, " +							
				" interviewCard.interview.date, " +
				" interviewCard.id, " +							
				" interviewCard.time, " +
				" admAppln.personalData.email,interviewCard.interview.startTime," +
				" interviewCard.interview.endTime," +
				" interviewCard.interviewer,interviewCard.interview.venue,interviewCard.interview.interview.id,admAppln.interScheduleSelection.id " +
				" from AdmAppln admAppln " +
				" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in (:interviewRound) and interviewSelected.isCardGenerated = 1 " +
				" join admAppln.interviewCards interviewCard " +
				" where admAppln.appliedYear = :appliedYear  " +selectedCheck+
				" and interviewCard.interview.date between '"+ CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate())+" 00:00:00.0'"+" and '"+CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate())+" 23:59:59.0'"+appRange;
				if(!interviewBatchEntryTO.getCourseId().isEmpty())
					strQuery+=" and admAppln.courseBySelectedCourseId.id = :courseId ";
				selectedCandidatesQuery = session.createQuery(strQuery);
				selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
				if(!interviewBatchEntryTO.getCourseId().isEmpty())
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
				selectedCandidatesQuery.setParameterList("interviewRound", interviewTypSet);
				//selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
			}		
			else{
				String strQuery="select admAppln.id, " +
				" admAppln.applnNo, " +
				" admAppln.appliedYear, " +
				" admAppln.personalData.firstName," +
				" admAppln.personalData.middleName, " +
				" admAppln.personalData.lastName, " +
				" admAppln.courseBySelectedCourseId.id, " +
				" admAppln.courseBySelectedCourseId.name, " +
				" interviewSelected.interviewProgramCourse, " +							
				" interviewCard.interview.date, " +
				" interviewCard.id, " +							
				" interviewCard.time, " +
				" admAppln.personalData.email,interviewCard.interview.startTime," +
				" interviewCard.interview.endTime," +
				" interviewCard.interviewer,interviewCard.interview.venue,interviewCard.interview.interview.id,admAppln.interScheduleSelection.id  " +							
				" from AdmAppln admAppln " +
				" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in (:interviewRound) and interviewSelected.isCardGenerated=1 " +
				" join admAppln.interviewCards interviewCard " +							
				" where admAppln.appliedYear = :appliedYear " +selectedCheck+
				" and interviewCard.interview.interview.interviewProgramCourse.id in (:interviewRound) "+appRange;
				if(!interviewBatchEntryTO.getCourseId().isEmpty())
					strQuery+=" and admAppln.courseBySelectedCourseId.id = :courseId ";
				selectedCandidatesQuery = session.createQuery(strQuery);
				selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
				if(!interviewBatchEntryTO.getCourseId().isEmpty())
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
				selectedCandidatesQuery.setParameterList("interviewRound", interviewTypSet);
				}
			selectedCandidatesList = selectedCandidatesQuery.list();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return selectedCandidatesList;
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewChangeTimeTxn#batchResultUpdate(java.util.List)
	 * This method will update the time
	 */
	public boolean batchResultUpdate(List<InterviewCard> batchUpdateList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			

			if (batchUpdateList != null) {
				int count = 0;
				Iterator<InterviewCard> iterator = batchUpdateList.iterator();
				while (iterator.hasNext()) {
					InterviewCard interviewCard = iterator.next();
					
					String interviewUpdate = "update InterviewCard set time = :interviewtime where id = :interviewid";
				Query query = session.createQuery(interviewUpdate);
				query.setInteger("interviewid", interviewCard.getId());
				query.setString("interviewtime", interviewCard.getTime());
				int temp = 	query.executeUpdate();
				}
			}
			transaction.commit();
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview card data.."+e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview card data ..."+e);
			throw new ApplicationException(e);
		} finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}

	@Override
	public boolean batchResultUpdate1(
			List<InterviewCard> updateInterviewCardList,
			List<InterviewCard> deleteinterviewCardList,
			List<InterviewSchedule> newSchedule,HttpServletRequest request,String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			int count = 0;
			Set<InterviewCard> sendMailAndSms=new HashSet<InterviewCard>();
			if (updateInterviewCardList != null) {
				Iterator<InterviewCard> iterator = updateInterviewCardList.iterator();
				while (iterator.hasNext()) {
					InterviewCard interviewCard = iterator.next();
					InterviewCard bo=(InterviewCard)session.get(InterviewCard.class, interviewCard.getId());
					bo.setTime(interviewCard.getTime());
					/* code added by sudhir */
					bo.setModifiedBy(userId);
					bo.setLastModifiedDate(new Date());
					/* code added by sudhir */
					sendMailAndSms.add(interviewCard);
					session.update(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			if (newSchedule != null) {
				Iterator<InterviewSchedule> iterator = newSchedule.iterator();
				while (iterator.hasNext()) {
					InterviewSchedule interviewSchedule = iterator.next();
					session.save(interviewSchedule);
					sendMailAndSms.addAll(interviewSchedule.getInterviewCards());
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		    Transaction	transaction1 = session.beginTransaction();
			if (deleteinterviewCardList != null) {
				Iterator<InterviewCard> iterator = deleteinterviewCardList.iterator();
				while (iterator.hasNext()) {
					InterviewCard interviewCard = iterator.next();
					session.delete(interviewCard);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction1.commit();
			
			
			// sending mail and sms
			session = sessionFactory.openSession();
			if(!sendMailAndSms.isEmpty()){

				Iterator<InterviewCard> itr=sendMailAndSms.iterator();
				Properties prop = new Properties();
				try {
					InputStream inStr = CommonUtil.class.getClassLoader()
							.getResourceAsStream(
									CMSConstants.APPLICATION_PROPERTIES);
					prop.load(inStr);
				} catch (FileNotFoundException e) {
					log.error("Unable to read properties file...", e);
					return false;
				} catch (IOException e) {
					log.error("Unable to read properties file...", e);
					return false;
				}
				ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
				List<GroupTemplate> templateList=ITemplatePassword.getGroupTemplate(0, CMSConstants.EDIT_INTERVIEW_SCHEDULE_TEMPLATE, 0); 
				String desc=null;
				if (templateList != null && !templateList.isEmpty()) {
					desc = templateList.get(0).getTemplateDescription();
				}
				while (itr.hasNext()) {
					InterviewCard ic = (InterviewCard) itr.next();
					InterviewCard interviewCard=(InterviewCard)session.get(InterviewCard.class,ic.getId());
					AdmAppln admAppln=(AdmAppln)session.get(AdmAppln.class,interviewCard.getAdmAppln().getId());
					Course course=(Course)session.get(Course.class,admAppln.getCourseBySelectedCourseId().getId());
					if(course.getIsApplicationProcessSms()){

						String mobileNo="";
						if(admAppln.getPersonalData().getMobileNo1()!=null && !admAppln.getPersonalData().getMobileNo1().isEmpty()){
							if(admAppln.getPersonalData().getMobileNo1().trim().equals("0091") || admAppln.getPersonalData().getMobileNo1().trim().equals("+91")
									|| admAppln.getPersonalData().getMobileNo1().trim().equals("091") || admAppln.getPersonalData().getMobileNo1().trim().equals("0"))
								mobileNo = "91";
							else
								mobileNo=admAppln.getPersonalData().getMobileNo1();
						}else{
							mobileNo="91";
						}
						if(admAppln.getPersonalData().getMobileNo2()!=null && !admAppln.getPersonalData().getMobileNo2().isEmpty()){
							mobileNo=mobileNo+admAppln.getPersonalData().getMobileNo2();
						}
						//Application No Added By Manu	
						if(mobileNo.length()==12){
							ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_EDIT_E_ADMITCARD,Integer.toString(admAppln.getApplnNo()));
						}
					
					}
					
					
					if (interviewCard.getAdmAppln() != null
							&& admAppln.getPersonalData() != null
							&& admAppln.getPersonalData()
									.getEmail() != null && desc!=null) {
						String message = desc;
						message = CommonUtil.replaceMessageText(desc, admAppln, request);
						String interviewDate = "";
						String interviewTime = "";
						String interviewVenue = "";
						String interviewType = "";
						String program = "";
						String academicyear = "";
						InterviewSchedule interviewSchedule=(InterviewSchedule)session.get(InterviewSchedule.class, interviewCard.getInterview().getId());
						Interview interview=(Interview)session.get(Interview.class,interviewSchedule.getInterview().getId());
						InterviewProgramCourse interviewProgramCourse=(InterviewProgramCourse)session.get(InterviewProgramCourse.class, interview.getInterviewProgramCourse().getId());
						if (interviewSchedule != null
								&& interviewSchedule.getDate() != null) {
							interviewDate = interviewSchedule.getDate()
									.toString();
						}

						if (interviewCard.getTime() != null) {
							interviewTime = interviewCard.getTime();
						}
						if (interviewSchedule != null
								&& interviewSchedule.getVenue() != null) {
							interviewVenue = interviewSchedule
									.getVenue();
						}
						if (interviewSchedule != null
								&& interview!= null
								&& interviewProgramCourse != null
								&& interviewProgramCourse.getName() != null) {
							interviewType = interviewProgramCourse
									.getName();
						}

						if (admAppln != null
								&& course!= null
								&&course
										.getProgram() != null
								&& course
										.getProgram().getName() != null) {
							program = course
									.getProgram().getName();
						}

						if (admAppln != null
								&& admAppln.getAppliedYear() != null) {
							academicyear = String.valueOf(admAppln.getAppliedYear())
									+ "-"
									+ (admAppln.getAppliedYear() + 1);
						}

						String subject = interviewType + " for " + program + " "
								+ academicyear;
						message = message
								.replace(CMSConstants.TEMPLATE_INTERVIEW_DATE,
										interviewDate);
						message = message
								.replace(CMSConstants.TEMPLATE_INTERVIEW_TIME,
										interviewTime);
						message = message.replace(
								CMSConstants.TEMPLATE_INTERVIEW_VENUE,
								interviewVenue);
						message = message
								.replace(CMSConstants.TEMPLATE_INTERVIEW_TYPE,
										interviewType);
						// send mail
						InterviewHelper.sendMail(interviewCard.getAdmAppln()
								.getPersonalData().getEmail(), subject,
								message, prop);
					
				}
			}
			}
			
			
		}catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview card data.."+e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview card data ..."+e);
			throw new ApplicationException(e);
		} finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}

	@Override
	public List<Integer> getAdmApplnResultEntered(InterviewTimeChangeTO interviewBatchEntryTO,Set<Integer>interviewTypSet) throws Exception {
		Session session = null;
		List<Integer> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery;
					selectedCandidatesQuery = session.createQuery("select ir.admAppln.id " +
							"from InterviewResult ir where ir.interviewProgramCourse.id in(:interviewRound) and ir.admAppln.appliedYear=:appliedYear");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setParameterList("interviewRound", interviewTypSet);
			selectedCandidatesList = selectedCandidatesQuery.list();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return selectedCandidatesList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewChangeTimeTxn#addHistory(java.util.List, java.lang.String)
	 */
	@Override
	public void addHistory(List<String> cardIds,String userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(cardIds != null && !cardIds.isEmpty()){
				Iterator<String> iterator = cardIds.iterator();
				while (iterator.hasNext()) {
					String string = (String) iterator.next();
					InterviewCard interviewCard = (InterviewCard) session.get(InterviewCard.class, Integer.parseInt(string));
					if(interviewCard.getInterview() != null){
						InterviewScheduleHistory history = new InterviewScheduleHistory();
						history.setDate(interviewCard.getInterview().getDate());
						history.setEndTime(interviewCard.getInterview().getEndTime());
						history.setInterview(interviewCard.getInterview().getInterview());
						history.setStartTime(interviewCard.getInterview().getStartTime());
						history.setTimeInterval(interviewCard.getInterview().getTimeInterval());
						history.setVenue(interviewCard.getInterview().getVenue());
						if(interviewCard.getAdmAppln().getInterScheduleSelection()!=null && interviewCard.getAdmAppln().getInterScheduleSelection().getId()>0){
							InterviewSelectionSchedule selectId=new InterviewSelectionSchedule();
							selectId.setId(interviewCard.getAdmAppln().getInterScheduleSelection().getId());
							history.setSelectionScheduleId(selectId);
						}
							InterviewCardHistory cardHistory = new InterviewCardHistory();
							cardHistory.setAdmAppln(interviewCard.getAdmAppln());
							cardHistory.setDate(interviewCard.getDate());
							cardHistory.setInterviewer(interviewCard.getInterviewer());
							cardHistory.setStartTime(interviewCard.getStartTime());
							cardHistory.setTime(interviewCard.getTime());
							/* modified by sudhir */
							cardHistory.setCreatedBy(interviewCard.getCreatedBy());
							cardHistory.setCreatedDate(interviewCard.getCreatedDate());
							cardHistory.setLastModifiedDate(interviewCard.getLastModifiedDate());
							cardHistory.setModifiedBy(interviewCard.getModifiedBy());
							/* modified by sudhir */
						Set<InterviewCardHistory> interviewSet = new HashSet<InterviewCardHistory>();
						interviewSet.add(cardHistory);
						history.setInterviewCards(interviewSet);
						/* modified by sudhir */
						history.setCreatedBy(interviewCard.getInterview().getCreatedBy());
						history.setCreatedDate(interviewCard.getInterview().getCreatedDate());
						history.setModifiedBy(interviewCard.getInterview().getModifiedBy());
						history.setLastModifiedDate(interviewCard.getInterview().getLastModifiedDate());
						/* modified by sudhir */
						session.save(history);
					}
					
				}
			}
			tx.commit();
		}catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewChangeTimeTxn#getHistoryMap()
	 */
	@Override
	public Map<Integer, String> getHistoryMap() throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select card.admAppln.id,count(card.id) from InterviewCardHistory card" +
											  " group by card.admAppln");
			List<Object[]> list = query.list();
			if(list != null && !list.isEmpty()){
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0] != null && objects[0].toString() != null &&
							objects[1] != null && objects[1].toString() != null){
						map.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
					}
				}
			}
			session.flush();
			session.close();
		}catch (Exception e) {
			if(session != null){
				session.close();
			}
			throw  new ApplicationException(e);
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewChangeTimeTxn#getScheduledHistory(java.lang.String)
	 */
	@Override
	public List<InterviewCardHistory> getScheduledHistory(String appNo)
			throws Exception {
		List<InterviewCardHistory> list = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String query = "from InterviewCardHistory i where admAppln.id="+appNo;
			list = session.createQuery(query).list();
		}catch (Exception e) {
			if(session != null){
				session.close();
			}
			throw  new ApplicationException(e);
		}
		return list;
	}
	

}
