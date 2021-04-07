package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ActivityLearning;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.ActivityLearningForm;
import com.kp.cms.to.admin.ActivityLearningTO;
import com.kp.cms.transactions.admin.IActivityLearningTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ActivityLearningImpl implements IActivityLearningTransaction{
	private static final Log log = LogFactory.getLog(ActivityLearningImpl.class);
	
	private static ActivityLearningImpl activityLearningImpl=null;
	
	public static ActivityLearningImpl getInstance(){
		if(activityLearningImpl==null){
			activityLearningImpl = new ActivityLearningImpl();
			return activityLearningImpl;
		}
		return activityLearningImpl;
	}

	@Override
	public List<ExtraCreditActivityType> getActivities() throws Exception {
		
		Session session =null;
		try{
			session = HibernateUtil.getSession();
			String string = "from ExtraCreditActivityType e where e.isActive=1"; 
			Query query = session.createQuery(string);
			List<ExtraCreditActivityType> list = query.list(); 
			return list;
		}catch (Exception e) {
			if(session!=null){
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	@Override
	public Map<Integer, String> getCourseMap() throws Exception {
		
		Session session = null;
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from Course course where course.isActive =1";
			Query query = session.createQuery(str);
			List<Course> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Course> iterator = list.iterator();
				while (iterator.hasNext()) {
					Course course = (Course) iterator.next();
					courseMap.put(course.getId(), course.getName());
				}
			}
			courseMap = CommonUtil.sortMapByValue(courseMap);
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
			return courseMap;
	}

	@Override
	public boolean checkDuplicate(ActivityLearningForm activityLearningForm)
			throws Exception {
		Session session = null;
		boolean duplicate = false;
		try{
			session  = HibernateUtil.getSession();
			String str = "select c.id from ActivityLearning c where c.isActive = 1";
			str = str + " and c.appliedYear="+activityLearningForm.getYear();
			str = str + " and c.extraCreditActivityType.id="+activityLearningForm.getExtraCreditActivityType();
			Query query =  session.createQuery(str);
			List list = query.list();			
			if(list.size()!=0){
				String hqlquery = "select c.course.id from ActivityLearning c where c.isActive = 1";				
				Query newQuery =  session.createQuery(hqlquery);				
				//newQuery.setParameterList("sessionList", list);
				List<Integer> courseIds = newQuery.list();
				if(courseIds != null && !courseIds.isEmpty()){
					String courseNames ="";
					for (int i = 0; i < activityLearningForm.getCourseIds().length; i++) {
						if(activityLearningForm.getCourseIds()[i] != null){
							if(courseIds.contains(Integer.parseInt(activityLearningForm.getCourseIds()[i]))){
								duplicate = true;
								if(courseNames.isEmpty()){
									courseNames = activityLearningForm.getCourseMap().get(Integer.parseInt(activityLearningForm.getCourseIds()[i]));
								}else{
									courseNames = courseNames +" ;    "+activityLearningForm.getCourseMap().get(Integer.parseInt(activityLearningForm.getCourseIds()[i]));
								}
							}
						}
					}
					activityLearningForm.setErrorMessage(courseNames);
				}
			}			
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return duplicate;
	}

	@Override
	public ActivityLearning isDupEntry(ActivityLearning activityLearning)
			throws Exception {
		log.debug("impl: inside isCDuplicated");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ActivityLearning c where c.course.id = :cid and c.appliedYear = :Year and c.isActive=0");
			query.setInteger("cid",activityLearning.getCourse().getId());
			query.setInteger("Year",activityLearning.getAppliedYear());
			activityLearning = (ActivityLearning) query.uniqueResult();
			session.flush();
			log.debug("impl: leaving isConvocationDuplicated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return activityLearning;
	}

	@Override
	public List<ActivityLearning> addDetails(ActivityLearning activityLearning)
										throws Exception{
		Session session = null;
		Transaction tx = null;
		List<ActivityLearning> list=null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(activityLearning);
			tx.commit();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		
	}
		return list;
	}

	@Override
	public List<ActivityLearning> getCourseAndActivityList() throws Exception {

		Session session =null;
		
		try{
			session = HibernateUtil.getSession();
			String string = "from ActivityLearning e where e.isActive=1"; 
			Query query = session.createQuery(string);
			List<ActivityLearning> list = query.list(); 
			return list;
		}catch (Exception e) {
			if(session!=null){
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	@Override
	public ActivityLearning getList(ActivityLearningForm activityLearningForm)throws Exception {
		Session session = null;
		ActivityLearning  learning = null;
		try{
			session = HibernateUtil.getSession();
			Query query=  session.createQuery("from ActivityLearning a where a.id= :id");
			query.setInteger("id", activityLearningForm.getActivityLearningId());
			learning = (ActivityLearning)query.uniqueResult();
		}catch (Exception e) {
			if(session!=null){
				session.flush();
			}
		}
		return learning;
	}

	@Override
	public boolean updateActivity(ActivityLearning activityLearning)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(activityLearning);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}

	@Override
	public ActivityLearning isActivityDuplicated(ActivityLearning learning1) {
		Session session =null;
		ActivityLearning activityLearning = null;
		try{
			session=HibernateUtil.getSession();
			String str = " from ActivityLearning a where a.appliedYear= :appyear and a.extraCreditActivityType.id= :id and a.course.id= :cid and a.minMark= :minmark and a.maxMark = :maxmark and a.creditInfo=:credit";
			Query query = session.createQuery(str)
									.setInteger("appyear", learning1.getAppliedYear())
									.setInteger("id",learning1.getExtraCreditActivityType().getId())
									.setInteger("cid", learning1.getCourse().getId())
									.setString("maxmark", learning1.getMaxMark())
									.setString("minmark", learning1.getMinMark())
									.setString("credit", learning1.getCreditInfo());
			activityLearning = (ActivityLearning)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return activityLearning;
	}

	@Override
	public boolean deleteActivity(ActivityLearningForm activityLearningForm)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ActivityLearning activityType = (ActivityLearning) session.get(ActivityLearning.class, activityLearningForm.getActivityLearningId());
			activityType.setIsActive(false);
			activityType.setLastModifiedDate(new Date());
			activityType.setModifiedBy(activityLearningForm.getUserId());
			session.update(activityType);
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

	@Override
	public boolean reactive(int reactId, boolean activate,
			ActivityLearningForm activityLearningForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			ActivityLearning learning = (ActivityLearning) session.get(ActivityLearning.class, reactId);
			if(activate)
			{
				learning.setIsActive(true);
			}
			else
			{
				learning.setIsActive(false);
			}
			learning.setModifiedBy(activityLearningForm.getUserId());
			learning.setLastModifiedDate(new Date());
			session.update(learning);
			tx.commit();
			session.flush();
			return true;
		}
		catch(ConstraintViolationException e) 
		{
			if(tx!=null)
				tx.rollback();
			throw new BusinessException(e);
		}
		catch(Exception e) 
		{
			if(tx!=null)
			     tx.rollback();
			throw new ApplicationException(e);
		}
	}

	
  

	


	
	
	

}
