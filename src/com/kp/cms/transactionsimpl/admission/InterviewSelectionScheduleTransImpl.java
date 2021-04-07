package com.kp.cms.transactionsimpl.admission;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.actions.exam.NewStudentMarksCorrectionAction;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.InterviewSelectionScheduleForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.transactions.admission.IInterviewSelectionScheduleTrans;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PropertyUtil;

public class InterviewSelectionScheduleTransImpl implements IInterviewSelectionScheduleTrans{
	public static volatile InterviewSelectionScheduleTransImpl interviewSelectionScheduleTransImpl=null;
	//private constructor
	private InterviewSelectionScheduleTransImpl(){
		
	}
	//singleton object
	public static InterviewSelectionScheduleTransImpl getInstance(){
		if(interviewSelectionScheduleTransImpl==null){
			interviewSelectionScheduleTransImpl=new InterviewSelectionScheduleTransImpl();
			return interviewSelectionScheduleTransImpl;
		}
		return interviewSelectionScheduleTransImpl;
	}
	@Override
	public boolean checkDuplicate(String query) throws Exception {
		boolean flag=false;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(query);
			InterviewSelectionSchedule interviewSelectionSchedule=(InterviewSelectionSchedule)hqlQuery.uniqueResult();
			if(interviewSelectionSchedule!=null){
				flag=true;
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return flag;
	
	}
	@Override
	public boolean add(InterviewSelectionSchedule interviewSelectionSchedule)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(interviewSelectionSchedule);
			tx.commit();
			session.close();
			flag=true;
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return flag;
	}
	@Override
	public List<InterviewSelectionSchedule> getAllRecords(String academicYear) throws Exception {
		List<InterviewSelectionSchedule> list=new ArrayList<InterviewSelectionSchedule>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from InterviewSelectionSchedule e where e.isActive=1 and e.academicYear='"+academicYear+"' order by e.programId.name,e.selectionProcessDate");
			list=hqlQuery.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public boolean delete(int id,String userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				InterviewSelectionSchedule interviewSelectionSchedule = (InterviewSelectionSchedule) session.get(InterviewSelectionSchedule.class, id);
				interviewSelectionSchedule.setIsActive(false);
				interviewSelectionSchedule.setModifiedBy(userId);
				interviewSelectionSchedule.setLastModifiedDate(new Date());
				session.update(interviewSelectionSchedule);
			tx.commit();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
	}
	@Override
	public Map<Integer, String> getprogramMap()
			throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from Program p where p.isActive=1");
			List<Program> list=hqlQuery.list();
			session.flush();
			if(list!=null && !list.isEmpty()){
				Iterator<Program> iterator=list.iterator();
				while (iterator.hasNext()) {
					Program program = (Program) iterator.next();
					map.put(program.getId(), program.getName());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return map;
	}
	@Override
	public Map<Integer, String> getVenues(int parseInt) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from ExamCenter p where p.isActive=1 and p.program.id="+parseInt);
			List<ExamCenter> list=hqlQuery.list();
			session.flush();
			if(list!=null && !list.isEmpty()){
				Iterator<ExamCenter> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamCenter examCenter = (ExamCenter) iterator.next();
					map.put(examCenter.getId(), examCenter.getCenter());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return map;
	}
	@Override
	public InterviewSelectionSchedule getRecord(int id) throws Exception {
		InterviewSelectionSchedule interviewSelectionSchedule=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from InterviewSelectionSchedule e where e.isActive=1 and e.id="+id);
			interviewSelectionSchedule=(InterviewSelectionSchedule)hqlQuery.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return interviewSelectionSchedule;
	
	}
	@Override
	public boolean update(InterviewSelectionSchedule interviewSelectionSchedule)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.update(interviewSelectionSchedule);
			tx.commit();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
	}
	
	public List<InterviewCard> getGeneratedCardDetails(int id,String selectionProcessDate)
			throws Exception {
		List<InterviewCard> interviewCard=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select c from InterviewCard c join c.admAppln a join c.interview i where a.isCancelled=0 and a.interScheduleSelection.id="+ id+" and i.date='" +CommonUtil.ConvertStringToSQLDate(selectionProcessDate)  + "'");
			interviewCard=hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return interviewCard;
		
		
	}
	
	@Override
	public void updateInterviewCard(String selectionProcessDate,InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws ApplicationException   
	{
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Query que = session.createQuery("select c from InterviewCard c " +
											"join c.admAppln a join c.interview i " +
											"where a.isCancelled=0 and i.date='" +CommonUtil.ConvertStringToSQLDate(selectionProcessDate)  + "'");
			List<InterviewCard> CardList = que.list();
			if(CardList!= null && CardList.size() > 0){
				Iterator<InterviewCard> itrcard =CardList.iterator();
				while (itrcard.hasNext()) {
					InterviewCard intercard = (InterviewCard)itrcard.next();
					//intercard.setDate(CommonUtil.ConvertStringToSQLDate(selectionProcessDate));
					intercard.setLastModifiedDate(new java.util.Date());
					intercard.setModifiedBy(interviewSelectionScheduleForm.getUserId());
				session.update(intercard);
				}
			}
		
			tx.commit();
			session.flush();
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		
	}
	@SuppressWarnings("unchecked")
	public void updateInterviewSchedule(String selectionProcessDate, int id,InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws ApplicationException {
		Session session, session2 = null;
		Transaction tx= null;
		Transaction tx2= null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Query que = session.createQuery("from InterviewSchedule s "
											+" where s.selectionScheduleId.id=" +id
											+" and s.selectionScheduleId.isActive=1 ");
					List<InterviewSchedule> scheduleList = que.list();
					if(scheduleList!= null && scheduleList.size() > 0){
					Iterator<InterviewSchedule> itrschedule =scheduleList.iterator();
					while (itrschedule.hasNext()) {
					InterviewSchedule interSchedule = (InterviewSchedule) itrschedule.next();
					interSchedule.setDate(CommonUtil.ConvertStringToSQLDate(interviewSelectionScheduleForm.getSelectionProcessDate()));
					interSchedule.setLastModifiedDate(new java.util.Date());
					interSchedule.setModifiedBy(interviewSelectionScheduleForm.getUserId());
					session.update(interSchedule);
						Query interCard=session.createQuery("from InterviewCard c where c.interview.id='" +interSchedule.getId() +"' and c.admAppln.isCancelled=0");
						List<InterviewCard> cardList = interCard.list();
						if(cardList!= null && cardList.size() > 0){
						Iterator<InterviewCard> itrcard =cardList.iterator();
						while (itrcard.hasNext()) {
							InterviewCard inCard = (InterviewCard) itrcard.next();
							sendSMSToStudent(inCard.getAdmAppln().getApplnNo(),inCard.getAdmAppln().getPersonalData().getMobileNo1(),inCard.getAdmAppln().getPersonalData().getMobileNo2(), interviewSelectionScheduleForm.getSelectionProcessDate());
						}
						}
					}
			}
			session.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
		
		public void sendSMSToStudent(int applnNo, String Mobile1, String Mobile2, String date) throws Exception{
			Properties prop = new Properties();
	        InputStream in1 = NewStudentMarksCorrectionAction.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
	        prop.load(in1);
			String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
			String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
			String desc="";
			SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
			List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.SMS_TEMPLATE_INTERVIEW_CARD_DATE_CHANGE);
			if(list != null && !list.isEmpty()) {
				desc = list.get(0).getTemplateDescription();
			}
			desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, String.valueOf(applnNo));
			desc =desc.replace("[SELECTIONDATE]", date);
			String mobileNo="";
			if(Mobile1!=null && !Mobile1.isEmpty()){
				mobileNo=Mobile1;
			}else{
				mobileNo="91";
			}
			if(Mobile2!=null && !Mobile2.isEmpty()){
				mobileNo=mobileNo+Mobile2;
			}
			if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
				MobileMessaging mob=new MobileMessaging();
				mob.setDestinationNumber(mobileNo);
				mob.setMessageBody(desc);
				mob.setMessagePriority(3);
				mob.setSenderName(senderName);
				mob.setSenderNumber(senderNumber);
				mob.setMessageEnqueueDate(new Date());
				mob.setIsMessageSent(false);
				PropertyUtil.getInstance().saveMobile(mob);
			}
		}
		
		public boolean checkingIsCardgenerated(InterviewSelectionScheduleForm interviewSelectionScheduleForm)throws Exception {
		
			List<InterviewSchedule> interviewSchedule=null;
			boolean cardgenerated=false;
			Session session = null;
			try{
				session = HibernateUtil.getSession();
				Query hqlQuery = session.createQuery("select s from InterviewSchedule s  where s.selectionScheduleId.id="+ interviewSelectionScheduleForm.getId());
				interviewSchedule=hqlQuery.list();
				if(interviewSchedule!=null && !interviewSchedule.isEmpty())
					cardgenerated=true;
				
			}catch (Exception e) {
				if (session != null) {
					session.flush();
				}
				throw new ApplicationException(e);
			}
			return cardgenerated;
	
		}
		
		public Map<Integer,Integer> getTotalNumberOfStudentsAppliedForAdmitcard(InterviewSelectionScheduleForm interviewSelectionScheduleForm)throws Exception {
			
			List<InterviewSchedule> interviewSchedule=null;
			Session session = null;
			Map<Integer,Integer> map = new HashMap<Integer, Integer>();
			try{
				session = HibernateUtil.getSession();
				Query hqlQuery = session.createQuery("select app.interScheduleSelection.id,count(app.id) from AdmAppln app where app.interScheduleSelection.academicYear='"+interviewSelectionScheduleForm.getAcademicYear()+
														"' and app.interScheduleSelection.isActive=1 group by app.interScheduleSelection.id"); 
				
				List<Object[]> list = hqlQuery.list();
	            if(list!=null && !list.isEmpty()){
	            	Iterator<Object[]> iterator = list.iterator();
	            	while (iterator.hasNext()) {
						Object[] objects = (Object[]) iterator.next();
						if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects[0].toString()), Integer.parseInt(objects[1].toString()));
						}
					}
	            }
				
			}catch (Exception e) {
				if (session != null) {
					session.flush();
				}
				throw new ApplicationException(e);
			}
			return map;
	
		}
		
	}
