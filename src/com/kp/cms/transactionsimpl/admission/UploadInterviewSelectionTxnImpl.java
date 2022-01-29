package com.kp.cms.transactionsimpl.admission;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.UploadBypassInterviewForm;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.jms.MailTO;

public class UploadInterviewSelectionTxnImpl implements IUploadInteviewSelectionTxn{

	private static final Log log = LogFactory.getLog(UploadInterviewSelectionTxnImpl.class);
	
	/**
	 *	This method is used to get AdmAppln details from database based on courseId,year. 
	 */
	
	@Override
	public Map<Integer, Integer> getAdmApplnDetails(int year, UploadBypassInterviewForm  bypassInterviewForm) throws Exception {
		log.info("call of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		Map<Integer,Integer> intPrgCoursemap = new HashMap<Integer, Integer>();
		List<Integer> tempList = new ArrayList<Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select a.id,a.applnNo, interviewProgramCourse.id, a.courseBySelectedCourseId.id" +
					"  from AdmAppln a " +
					" inner join a.courseBySelectedCourseId.interviewProgramCourses interviewProgramCourse inner join a.students  student  " +
					" where a.appliedYear= :year and interviewProgramCourse.sequence = 2 and (a.isCancelled=0 or a.isCancelled=null)" +
					"  and  (student.isAdmitted=0 or student.isAdmitted=null) ");
//			query.setInteger("courseId",courseId);
			query.setInteger("year",year);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					//AdmAppln adm=(AdmAppln)obj[0];
					map.put((Integer)obj[1],(Integer)obj[0]);
					tempList.add((Integer)obj[1]);
				//	InterviewProgramCourse interviewProgramCourse=(InterviewProgramCourse)obj[1];
					intPrgCoursemap.put((Integer)obj[0],(Integer)obj[2]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		bypassInterviewForm.setIntPrgCourseMap(intPrgCoursemap);
		return map;
	}
	
	@Override
	public boolean addUploadedData(List<InterviewSelected> interviewSelectedList, String user,List<SingleFieldMasterTO> notSelectedList) throws Exception {
		log.info("call of interviewSelectedList method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		InterviewSelected interviewSelected;
		InterviewSelected oldInterviewSelected=null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			if (interviewSelectedList != null && !interviewSelectedList.isEmpty()) {
				Iterator<InterviewSelected> itr = interviewSelectedList.iterator();
				int count = 0;
				while (itr.hasNext()) {
					interviewSelected = itr.next();
					interviewSelected.setCreatedBy(user);
					interviewSelected.setModifiedBy(user);
					interviewSelected.setLastModifiedDate(new Date());
		//			oldInterviewSelected = getOldInterviewSelected(interviewSelected.getAdmAppln().getId(), interviewSelected.getInterviewProgramCourse().getId(), session);
					
					Query query = session.createQuery("FROM InterviewSelected e" +
							" where e.admAppln.id = " + interviewSelected.getAdmAppln().getId() + " and e.interviewProgramCourse.id = " + interviewSelected.getInterviewProgramCourse().getId());
					List<InterviewSelected> selectedList = query.list();
					if(selectedList!= null && selectedList.size() > 0){
						oldInterviewSelected = selectedList.get(0);
					}
					if(oldInterviewSelected!= null){
						interviewSelected.setId(oldInterviewSelected.getId());
						session.merge(interviewSelected);
					}
					else{
						session.save(interviewSelected);
					}
					AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,interviewSelected.getAdmAppln().getId());
					admAppln.setAdmStatus(null);
					session.update(admAppln);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
			}
			if (notSelectedList != null && notSelectedList.size() > 0) {
				Iterator<SingleFieldMasterTO> listitr = notSelectedList.iterator();
				int count = 0;
				SingleFieldMasterTO singleFieldMasterTO;
				AdmAppln admAppln;
				while (listitr.hasNext()) {
					singleFieldMasterTO = listitr.next();
					admAppln = (AdmAppln) session.get(AdmAppln.class, singleFieldMasterTO.getId());
					admAppln.setAdmStatus(singleFieldMasterTO.getName());
					session.update(admAppln);
					if (++count % 20 == 0) {
						session.flush();
//						session.clear();
					}
					if(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
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
						if(mobileNo.length()==12){
							UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_INTERVIEW_STATUS,admAppln);
						}
					}
				}
			}
			
			transaction.commit();
			isAdded = true;

		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			isAdded = false;
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			//	session.close();
			}
		}
		log.info("end of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		return isAdded;
	}
	
	@Override
	public Map<Integer, Integer> getAdmApplnDetailsForNotSelected(int year, UploadBypassInterviewForm  bypassInterviewForm) throws Exception {
		log.info("call of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		String qry="";
		try {
			session = HibernateUtil.getSession();
				qry="select a.admAppln.applnNo, a.admAppln.id, a.admAppln.courseBySelectedCourseId.id  from Student a " +
				    " where (a.admAppln.isCancelled=0 or a.admAppln.isCancelled=null) " +
				    " and (a.isAdmitted=0 or a.isAdmitted=null) and a.admAppln.appliedYear= :year";
			
			Query query = session.createQuery(qry);
//			query.setInteger("courseId",courseId);
			query.setInteger("year",year);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put((Integer)obj[0],(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		return map;
	}
	/**
	 * 
	 * @param admApplnId
	 * @param intProgramCourseId
	 * @return
	 * @throws Exception
	 */
	public InterviewSelected getOldInterviewSelected(int admApplnId, int intProgramCourseId, Session session) throws Exception {
		InterviewSelected interviewSelected = null;
		try {
			Query query = session.createQuery("FROM InterviewSelected e" +
					" where e.admAppln.id = " + admApplnId + " and e.interviewProgramCourse.id = " + intProgramCourseId);
			List<InterviewSelected> selectedList = query.list();
			if(selectedList!= null && selectedList.size() > 0){
				interviewSelected = selectedList.get(0);
			}
		} catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return interviewSelected;
	}

	/* (non-Javadoc)
	 * getting the InterviewSchedule BO for date,time,venue for a student
	 * @see com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn#getInterviewSchedule(com.kp.cms.to.admission.InterviewCardTO)
	 */
	@Override
	public InterviewSchedule getInterviewSchedule(InterviewCardTO interviewCardTO,String user) throws Exception {
		InterviewSchedule interviewSchedule = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewSchedule e" +
					" where e.date= '" +CommonUtil.ConvertStringToSQLDate(interviewCardTO.getInterviewDate())  + "' and e.startTime = '" + interviewCardTO.getTime()+
					"' and e.venue='"+interviewCardTO.getVenue()+"' and e.interview.interviewProgramCourse.id="+interviewCardTO.getInterviewPrgCrsId());
			interviewSchedule=(InterviewSchedule) query.uniqueResult();
			if(interviewSchedule==null){
				Interview interview=null;
				//Set<InterviewSchedule> interviewScheduleSet;
				Query que = session.createQuery("from Interview e" +
						" where  e.interviewProgramCourse.id=" +interviewCardTO.getInterviewPrgCrsId()+
						" and e.year="+interviewCardTO.getAppliedYear());
				List<Interview> intList=que.list();
				if(intList!=null && !intList.isEmpty()){
				 interview=intList.get(0);
				}
				else{
					transaction = session.beginTransaction();
					interview=new Interview();
					InterviewProgramCourse interviewProgramCourse=new InterviewProgramCourse();
					interviewProgramCourse.setId(interviewCardTO.getInterviewPrgCrsId());
					interview.setInterviewProgramCourse(interviewProgramCourse);
					interview.setYear(interviewCardTO.getAppliedYear());
					interview.setCreatedBy(user);
					interview.setCreatedDate(new Date());
					interview.setModifiedBy(user);
					interview.setLastModifiedDate(new Date());	
					 //interviewScheduleSet=new HashSet<InterviewSchedule>();
					session.save(interview);
					transaction.commit();
				}
				if(interview!=null && interview.getId()>0){
					transaction = session.beginTransaction();
					interviewSchedule=new InterviewSchedule();
					interviewSchedule.setInterview(interview);
					interviewSchedule.setDate(CommonUtil.ConvertStringToSQLDate(interviewCardTO.getInterviewDate()));
					interviewSchedule.setStartTime(interviewCardTO.getTime());
					DateFormat sdf=new SimpleDateFormat("hh:mm");
					Calendar gc = new GregorianCalendar();
					gc.setTime(sdf.parse(interviewCardTO.getTime()));
					gc.add(Calendar.HOUR, 1);
					interviewSchedule.setEndTime(CommonUtil.getTimeByDate(gc.getTime()));
					interviewSchedule.setVenue(interviewCardTO.getVenue());
					interviewSchedule.setCreatedBy(Integer.parseInt(user));
					interviewSchedule.setCreatedDate(new Date());
					interviewSchedule.setLastModifiedDate(new Date());
					interviewSchedule.setModifiedBy(user);
					session.save(interviewSchedule);
					transaction.commit();
				}
			}
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			//	session.close();
			}
		}
		return interviewSchedule;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn#addSelectionProcessWorkflowData(java.util.List)
	 */
	@Override
	public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave,String user,Map<Integer, Integer> intPrgCourseMap) throws Exception {

		log.info("call of addSelectionProcessWorkflowData method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdd=false;
		InterviewCard oldInterviewCard=null;
		InterviewSelected interSelected=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			if(interviewCardsToSave!=null && !interviewCardsToSave.isEmpty()){
				Iterator it = interviewCardsToSave.iterator();
				int count = 0;
				while (it.hasNext()) {
					InterviewCard card = (InterviewCard) it.next();
					if(card!=null){
						Query query = session.createQuery("FROM InterviewCard e" +
								" where e.admAppln.id = " + card.getAdmAppln().getId() + 
								" and e.interview.interview.interviewProgramCourse.id = " +intPrgCourseMap.get(card.getAdmAppln().getId()));
						List<InterviewCard> cardList = query.list();
						if(cardList!= null && cardList.size() > 0){
							oldInterviewCard = cardList.get(0);
						}
						if(oldInterviewCard!= null){
							card.setId(oldInterviewCard.getId());
							session.merge(card);
						}
						else{
							session.save(card);
						}
						
						Query que = session.createQuery("FROM InterviewSelected e" +
								" where e.admAppln.id = " + card.getAdmAppln().getId() +
								" and e.interviewProgramCourse.id = " + intPrgCourseMap.get(card.getAdmAppln().getId()));
						List<InterviewSelected> selectedList = que.list();
						if(selectedList!= null && selectedList.size() > 0){
							interSelected = selectedList.get(0);
							interSelected.setIsCardGenerated(true);
							interSelected.setLastModifiedDate(new Date());
							interSelected.setModifiedBy(user);
							session.update(interSelected);
						}
						if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
				}
			}	
			transaction.commit();
			isAdd=true;
			}
		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			isAdd=false;
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addSelectionProcessWorkflowData method in  UploadInterviewResultTransactionImpl class.");
		return isAdd;
	
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn#getInterviewCardBo(int, int)
	 */
	@Override
	public InterviewCard getInterviewCardBo(int admApplnId, int interviewScheduleId) throws Exception {
		InterviewCard cardBo = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewCard e" +
					" where e.admAppln.id= " + admApplnId+" and e.interview.id="+interviewScheduleId);
			cardBo = (InterviewCard)query.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return cardBo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn#sendSmsinBulk(java.util.List)
	 */
	@Override
	public void sendSmsinBulk(List<MobileMessaging> mobileMessagesList) throws Exception {
		Session session=null;
		Transaction transaction = null;
		try {
			if(mobileMessagesList!=null && !mobileMessagesList.isEmpty()){
				Iterator<MobileMessaging> itr=mobileMessagesList.iterator();
				int count = 0;
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				while (itr.hasNext()) {
					MobileMessaging message = (MobileMessaging) itr.next();
					session.save(message);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
				transaction.commit();
			}
			
		}  catch (Exception e) {
				if ( transaction != null){
					transaction.rollback();
				}
				if (session != null){
					session.flush();
					session.close();
				}
			}
		}
}
