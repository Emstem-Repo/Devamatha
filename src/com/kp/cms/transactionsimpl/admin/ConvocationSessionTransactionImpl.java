package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ConvocationCourse;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.ConvocationSessionForm;
import com.kp.cms.transactions.admin.IConvocationSessionTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ConvocationSessionTransactionImpl implements IConvocationSessionTransaction{
	public static volatile ConvocationSessionTransactionImpl impl = null;
	public static ConvocationSessionTransactionImpl getInstance(){
		if(impl == null){
			impl = new ConvocationSessionTransactionImpl();
			return impl;
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IConvocationSessionTransaction#getCourseMap()
	 */
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IConvocationSessionTransaction#addDetails(com.kp.cms.bo.admin.ConvocationSession)
	 */
	@Override
	public boolean addDetails(ConvocationSession convocationSession)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(convocationSession);
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		
	}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IConvocationSessionTransaction#getConvocationSessionList()
	 */
	@Override
	public List<ConvocationSession> getConvocationSessionList()
			throws Exception {
		Session session = null;
		List<ConvocationSession> list = null;
		try{
			session  = HibernateUtil.getSession();
			String str = "from ConvocationSession course where course.isActive = 1";
			Query query =  session.createQuery(str);
			list = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return list;
	}
	@Override
	public ConvocationSession getConvocationSession(int convocationSessionId)
			throws Exception {
		Session session = null;
		ConvocationSession convocationSession = null;
		try{
			session  = HibernateUtil.getSession();
			String str = "from ConvocationSession course where course.id ="+convocationSessionId;
			Query query =  session.createQuery(str);
			convocationSession = (ConvocationSession) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return convocationSession;
	}
	@Override
	public boolean deleteConvocationSession(int convocationSessionId, String user)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			ConvocationSession convocationSession = (ConvocationSession)session.get(ConvocationSession.class, convocationSessionId);
			if(convocationSession != null){
				convocationSession.setIsActive(false);
				convocationSession.setLastModifiedDate(new Date());
				convocationSession.setModifiedBy(user);
				if(convocationSession.getCourses() != null){
					Iterator<ConvocationCourse> iterator = convocationSession.getCourses().iterator();
					while (iterator.hasNext()) {
						ConvocationCourse convocationCourse = (ConvocationCourse) iterator.next();
						convocationCourse.setIsActive(false);
						convocationCourse.setLastModifiedDate(new Date());
						convocationCourse.setModifiedBy(user);
					}
				}
			}
			session.update(convocationSession);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDeleted;
	}
	@Override
	public boolean checkDuplicate(ConvocationSessionForm convocationSessionForm)
			throws Exception {
		Session session = null;
		boolean dulicate = false;
		try{
			session  = HibernateUtil.getSession();
			String str = "from ConvocationSession c where c.isActive = 1 and c.date='"+CommonUtil.ConvertStringToSQLDate(convocationSessionForm.getDate())+"'";
			str = str + " and c.amPM='"+convocationSessionForm.getAmOrpm()+"'";
			Query query =  session.createQuery(str);
			List<ConvocationSession> list = query.list();
			if(list != null && !list.isEmpty()){
				dulicate = true;
			}
			String hqlquery = "select c.course.id from ConvocationCourse c where c.isActive = 1 ";
			Query newQuery =  session.createQuery(hqlquery);
			List<Integer> courseIds = newQuery.list();
			if(courseIds != null && !courseIds.isEmpty()){
				String courseNames ="";
				for (int i = 0; i < convocationSessionForm.getCourseIds().length; i++) {
					if(convocationSessionForm.getCourseIds()[i] != null){
						if(courseIds.contains(Integer.parseInt(convocationSessionForm.getCourseIds()[i]))){
							dulicate = true;
							if(courseNames.isEmpty()){
								courseNames = convocationSessionForm.getCourseMap().get(Integer.parseInt(convocationSessionForm.getCourseIds()[i]));
							}else{
								courseNames = courseNames +" ;    "+convocationSessionForm.getCourseMap().get(Integer.parseInt(convocationSessionForm.getCourseIds()[i]));
							}
						}
					}
				}
				convocationSessionForm.setErrorMessage(courseNames);
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
		return dulicate;
	}
	
}

