package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.HonoursCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.SubjectEntryForm;
import com.kp.cms.transactions.admin.ISubjectTransaction;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class SubjectTransactionImp extends ExamGenImpl implements ISubjectTransaction {
	private static Log log = LogFactory.getLog(SubjectTransactionImp.class);
	private static volatile SubjectTransactionImp obj;
	public static SubjectTransactionImp getInstance() {
		if(obj == null) {
			obj = new SubjectTransactionImp();
		}
		return obj;
	}
	/**
	 *getting all the subjects from table 
	 */
	public List<Subject> getSubjects(String schemeNo) throws Exception 
	{
		
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			String subjectHibernateQuery = "from Subject where isActive=1 and schemeNo="+schemeNo;
			List<Subject> subjects = session.createQuery(subjectHibernateQuery).list();
			session.flush();
			//session.close();
			return subjects;
		} catch (Exception e) {
			
			log.error("Error during getting Subject loading...",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
			
		}
		
	}
	/**
	 * duplication checking
	 */
	public Subject existanceCheck(String code,String subjectName,String subType)throws Exception 
	{
		Session session = null;
		Subject subject;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			String studenttypeHibernateQuery = "from Subject s where s.code=:Name and s.name=:subjectName and s.isTheoryPractical=:theoryOrPractical";
			Query query = session.createQuery(
					studenttypeHibernateQuery);
			query.setString("Name", code);
			query.setString("subjectName", subjectName);
			query.setString("theoryOrPractical", subType);
			subject = (Subject)query.uniqueResult();
			session.flush();
			//session.close();
				
		} catch (Exception e) {
			log.error("Error during loading...",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
		}
		return subject;
		
	}
	//basim added
	public Subject subjectCodeDuplicated(String code) throws Exception
	{
		Session session=null;
		Subject subject;
		try
		{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Subject where code=:Code");
			query.setString("Code", code);
			subject=(Subject)query.uniqueResult();
		}
		catch(Exception e)
		{
			log.error("Error during loading...",e);
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		return subject;
	}
	//basim end
	/**
	 * this will add subject to the table
	 */
	public boolean addSubject(Subject subject) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(subject);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			      transaction.rollback();
			log.error("Error during saving admitted Through data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving admitted Through data...", e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 */
	public Subject loadSubject(Subject subject)throws Exception
	{
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			subject=(Subject) session.get(Subject.class, subject.getId());
			session.flush();
			//session.close();
			return subject;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.error("Error in loadSubject method of implclass..." , e);
		}
		return null;
	}
	/**
	 * this method is used for updating subject based on the id
	 */
	public boolean updateSubject(Subject subject)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
//			session.update(subject);
			session.merge(subject);
			transaction.commit();
			session.flush();
			session.close();
			return true;
			} catch (ConstraintViolationException e) {
				if(transaction!=null)
				     transaction.rollback();
				log.error("Error in update subject of implclass...", e);
				throw new BusinessException(e);
			} catch (Exception e) {
				if(transaction!=null)
				     transaction.rollback();
				log.error("Error during deleting Admitted Through data...",e);
				throw new ApplicationException(e);
			}
		
	}
	/**
	 * method used for reactivating subject
	 */
	public boolean reActivateSubjectEntry(String code, String userId,SubjectEntryForm subjectEntryForm)throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isActivated = false;
			try {
				//SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from Subject s where s.code = :code");
				query.setString("code",code);
				Subject subject = (Subject) query.uniqueResult();
				transaction = session.beginTransaction();
				subject.setIsActive(true);
				subject.setModifiedBy(userId);
				subject.setLastModifiedDate(new Date());
				subjectEntryForm.setSchemeNo(Integer.toString(subject.getSchemeNo()));
				session.update(subject);
				isActivated = true;
				transaction.commit();
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
					}
					throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
				}
		return isActivated;
		}
	// getting year by selected CertificateCourse id
	public int getYear(int id){
		Session session=null;
		int year=0;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from CertificateCourse cc where cc.id = "+id);
			//query.setString(id, "id");
			CertificateCourse certificateCourse=(CertificateCourse)query.uniqueResult();
			year=certificateCourse.getYear();
		}catch(Exception exception){
			if (session != null) {
				session.flush();
				
			}
			log.error("Error on getting certificate course record ..." , exception);
		}
		return year;
	}
	
	public Map<String, String> getDepartmentMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department d where d.isActive=true");
			List<Department> list=query.list();
			if(list!=null){
				Iterator<Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department department=iterator.next();
					if(department.getId()!=0 && department.getName()!=null && !department.getName().isEmpty())
					map.put(String.valueOf(department.getId()),department.getName());
				}
			}
		}
        catch (RuntimeException runtime) {
			
			throw new ApplicationException();
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISubjectTransaction#getEligibleCourseMap()
	 */
	@Override
	public Map<Integer, String> getEligibleCourseMap() throws Exception {
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HonoursCourse h where h.isActive=1");
			List<HonoursCourse> list=query.list();
			if(list!=null){
				Iterator<HonoursCourse> iterator=list.iterator();
				while(iterator.hasNext()){
					HonoursCourse honoursCourse=iterator.next();
					if(honoursCourse.getHonoursCourse() != null && honoursCourse.getHonoursCourse().getId() != 0 && honoursCourse.getHonoursCourse().getName() != null)
						map.put(honoursCourse.getHonoursCourse().getId(),honoursCourse.getHonoursCourse().getName());
				}
			}
		}
        catch (RuntimeException runtime) {
			
			throw new ApplicationException();
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISubjectTransaction#getSubjectCodeGroupMap()
	 * added by mehaboob to get Subject Code Group Map
	 */
	@Override
	public List<SubjectCodeGroup> getSubjectCodeGroupMap() throws Exception {
		Session session=null;
		List<SubjectCodeGroup> subjectCodeGroupList=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from SubjectCodeGroup code where code.isActive=1");
			subjectCodeGroupList=query.list();
		}catch(Exception exception){
			if (session != null) {
				session.flush();
				
			}
			log.error("Error on getting getSubjectCodeGroupMap record ..." , exception);
		}
		return subjectCodeGroupList;
	}
	@Override
	public List<String> getSubjectCodes() throws Exception {
		Session session=null;
		List<String> subjectCodeList=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select subject.code from Subject subject where subject.isActive=1");
			subjectCodeList=query.list();
		}catch(Exception exception){
			if (session != null) {
				session.flush();
				
			}
			log.error("Error on getting getSubjectCodes record ..." , exception);
		}
		return subjectCodeList;
	}
	@Override
	public List<String> getSubjectNameList() throws Exception {
		Session session=null;
		List<String> subjectNameList=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select subject.name from Subject subject where subject.isActive=1");
			subjectNameList=query.list();
		}catch(Exception exception){
			if (session != null) {
				session.flush();
				
			}
			log.error("Error on getting getSubjectNameList record ..." , exception);
		}
		return subjectNameList;
	}
}
