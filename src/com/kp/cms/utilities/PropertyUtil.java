package com.kp.cms.utilities;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.BulkSMStoEmployeeMobileMessaging;
import com.kp.cms.bo.admin.BulkSMStoStudentMobileMessaging;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.MobileMessagingSchedule;
import com.kp.cms.bo.admin.SendBulkSmsToStudentParentsBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentBulkAttendanceSMS;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;

public class PropertyUtil {
	private static final Log log = LogFactory.getLog(PropertyUtil.class);
	/**
	 * Singleton object of ScoreSheetHandler
	 */
	private static volatile PropertyUtil propertyUtil = null;
	public PropertyUtil() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static PropertyUtil getInstance() {
		if (propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		return propertyUtil;
	}
	/* 
	 * saving the object to database
	 */
	public boolean save(Object object) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(object);
			transaction.commit();
			//session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	public boolean saveMobile(Object object) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(object);
			//transaction.commit();
			//session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	/* 
	 * updating the object in database
	 */
	public boolean update(Object object)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(object);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	/* 
	 * Deleting the object in database
	 */
	public boolean delete(Object object)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.delete(object);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	/**
	 * get the list of data for query from the database
	 */
	@SuppressWarnings("unchecked")
	public List getListOfData(String SearchCriteria) throws Exception{
		Session session = null;
		List selectedList = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query selectedQuery=session.createQuery(SearchCriteria);
			selectedList = selectedQuery.list();
			return selectedList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		
	}
	 /**
	  * saving to table
	  */
	public boolean saveSMSList(List<MobileMessaging>  tcBOList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		MobileMessaging tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<MobileMessaging> tcIterator = tcBOList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	
	/**
	 * @param messaging
	 * @return
	 * @throws Exception
	 */
	public boolean saveSMSForSingleStudent(MobileMessaging  messaging) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		MobileMessaging tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(messaging);
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * @param mobList
	 * @return
	 * @throws Exception
	 */
	public boolean saveSMSSheduledList(List<MobileMessagingSchedule> mobList)throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		MobileMessagingSchedule tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<MobileMessagingSchedule> tcIterator = mobList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * @param SearchCriteria
	 * @return
	 * @throws Exception
	 */
	public static String getDataForUnique(String SearchCriteria) throws Exception{
		Session session = null;
		String selectedList = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query selectedQuery=session.createQuery(SearchCriteria);
			selectedList = (String)selectedQuery.uniqueResult();
			return selectedList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		
	}
	
	
	public boolean deleteOrReactivate(String boName,int id,String mode,String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query=session.createQuery("update "+boName+" bo set bo.isActive=:active,bo.modifiedBy=:userId,bo.lastModifiedDate=:date  where bo.id=:id");
			if(mode.equalsIgnoreCase("delete"))
				query.setBoolean("active",false);
			else
				query.setBoolean("active",true);
			query.setString("userId", userId);
			query.setDate("date",new Date());
			query.setInteger("id",id);
			query.executeUpdate();
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	/**
	 * @param bOList
	 * @return
	 * @throws Exception
	 */
	public boolean saveList(List  bOList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		MobileMessaging tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator tcIterator = bOList.iterator();
			while (tcIterator.hasNext()) {
				Object object = (Object) tcIterator.next();
				session.save(object);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * @param SearchCriteria
	 * @return
	 * @throws Exception
	 */
	public static Object getDataForUniqueObject(String SearchCriteria) throws Exception{
		Session session = null;
		Object selectedList = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query selectedQuery=session.createQuery(SearchCriteria);
			selectedList = selectedQuery.uniqueResult();
			return selectedList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		
	}
	
	/**
	 * @param SearchCriteria
	 * @return
	 * @throws Exception
	 */
	public int getAcademicyear()throws Exception{
		Session session = null;
		int year=0;
		try {
			session = HibernateUtil.getSession();
			year = (Integer)session.createQuery("select a.year from AcademicYear a where a.isActive=1 and a.isCurrent=1").uniqueResult();
			return year;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}
	public Student getStudent(String regNoTo) throws Exception{
		Session session = null;
		Student student=null;
		try {
			session = HibernateUtil.getSession();
			student = (Student)session.createQuery("from Student a where a.isActive=1 and a.registerNo='"+regNoTo+"'").uniqueResult();
			return student;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} 
	}
	
	public boolean saveBulkSMSList(List<BulkSMStoStudentMobileMessaging> mobileMessagings) throws Exception {
		log.debug("call of saveBulkSMSList method in PropertyUtil.class");
		Session session = null;
		Transaction transaction = null;
		BulkSMStoStudentMobileMessaging tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<BulkSMStoStudentMobileMessaging> tcIterator = mobileMessagings.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}

			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}

	}
	
	
	public boolean saveBulkSMSEmployeeList(List<BulkSMStoEmployeeMobileMessaging> mobileMessagings) throws BusinessException, ApplicationException {
		log.debug("call of saveBulkSMSList method in PropertyUtil.class");
		Session session = null;
		Transaction transaction = null;
		BulkSMStoEmployeeMobileMessaging tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<BulkSMStoEmployeeMobileMessaging> tcIterator = mobileMessagings.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}

			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
		
	}
	
	
	/**
	  * saving to table
	  */
	public boolean saveSMSListStudentBulkAttendanceSMS(List<StudentBulkAttendanceSMS>  tcBOList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		StudentBulkAttendanceSMS tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentBulkAttendanceSMS> tcIterator = tcBOList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	
	
	
	/**
	 * @param applicationNumber
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int getStudentId(String applicationNumber, String mode) throws Exception{
		Session session = null;
		int stuId=0;
		try {
			session = HibernateUtil.getSession();
			String str="select stu.id from Student stu ";
			if(mode.equalsIgnoreCase("appln")){
				str=str+"where stu.admAppln.applnNo=:applicationNumber";
			}else{
				str=str+"where stu.registerNo=:applicationNumber";
			}
			Query query = session.createQuery(str);
			query.setString("applicationNumber", applicationNumber);
			if(query.uniqueResult()!=null){
			  stuId =(Integer) query.uniqueResult();
			}return stuId;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} 
	}
	
	public static String getDataBySql(String SearchCriteria) throws Exception{
		Session session = null;
		int data = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query selectedQuery=session.createSQLQuery(SearchCriteria);
			data = (Integer) selectedQuery.uniqueResult();
			
			return String.valueOf(data);
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		
	}
	public boolean saveSMS(List<SendBulkSmsToStudentParentsBo>  tcBOList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		SendBulkSmsToStudentParentsBo tcLChecklist;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<SendBulkSmsToStudentParentsBo> tcIterator = tcBOList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	
}
