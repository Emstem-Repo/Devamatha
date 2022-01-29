package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.ProgramTypeForm;
import com.kp.cms.transactions.admin.IProgramTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for IProgramTypeTransaction.
 * @author 
 */
public class ProgramTypeTransactionImpl implements IProgramTypeTransaction {
	private static Log log = LogFactory.getLog(ProgramTypeTransactionImpl.class);	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IProgramTypeTransaction#addProgramType(java.lang.String)
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean addProgramType(ProgramType programType, String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(programType);
			}
			else
			{
				session.save(programType);
			}
			transaction.commit();
			return true;
		} catch (ConstraintViolationException e) {
			log.error("Error during saving program type data..." , e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving program type data...", e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally  {
			if (session != null){
				session.flush();
				session.close();
			}
			
		}
		
	}
	
	
	public ProgramType existanceCheck(String programTypeName, int id) throws Exception
	{
		ProgramType programType = null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer studenttypeHibernateQuery = new StringBuffer("from ProgramType where name=:Name");
			if(id!= 0){
				studenttypeHibernateQuery.append(" and id!= :id");
			}
			Query query = session.createQuery(studenttypeHibernateQuery.toString());
			query.setString("Name", programTypeName);
			if(id!=0){
				query.setInteger("id", id);
			}
			programType = (ProgramType) query.uniqueResult();
			session.flush();
			//session.close();
			return programType;
			
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
			
		
	}
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IProgramTypeTransaction#getProgramType()
	 */
	@Override
	public List<ProgramType> getProgramType() throws Exception  {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String programHibernateQuery = "from ProgramType where isActive=1";
			List<ProgramType> programTypes = session.createQuery(
					programHibernateQuery).list();
//			session.flush();
			//session.close();
			return programTypes;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IProgramTypeTransaction#editProgramType(int, java.lang.String)
	 */
	@Override
	public boolean editProgramType(int programTypeId, String programTypeName) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			ProgramType programType = new ProgramType();
			programType.setId(programTypeId);
			programType.setName(programTypeName);
			programType.setIsActive(true);
			programType.setLastModifiedDate(new Date());

			session.update(programType);
			transaction.commit();
			return true;
		} catch (ConstraintViolationException e) {
			log.error("Error during Updating program type data...",e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during updating program type data..." , e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally  {
			if (session != null){
				session.flush();
				session.close();
			}
		
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IProgramTypeTransaction#deleteProgramType(int)
	 */
	public boolean deleteProgramType(int programTypeId,ProgramTypeForm programTypeForm, Boolean activate) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ProgramType programType = (ProgramType) session.get(ProgramType.class, programTypeId);
			if (activate) {
				programType.setIsActive(true);
			}else
			{
				programType.setIsActive(false);
			}
			programType.setLastModifiedDate(new Date());
			programType.setModifiedBy(programTypeForm.getUserId());
			session.update(programType);
			transaction.commit();
			return true;
		} catch (ConstraintViolationException e) {
			log.error("Error during deleting program type data...",e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during deleting program type data..." + e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally  {
			
			if (session != null){
				session.flush();
				session.close();
			}
			
		}
			
	}


	@Override
	public List<ProgramType> getProgramTypeOnlineOpen() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String programHibernateQuery = " select ptype from ProgramType ptype " +
					                       " join ptype.programs pro" +
				                           " where ptype.isActive=1" +
				                           " and ptype.isOpen=1" +
				                           " group by ptype.id";
			List<ProgramType> programTypes = session.createQuery(
					programHibernateQuery).list();
//			session.flush();
			//session.close();
			return programTypes;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
	}
	@Override
	public List<UGCoursesBO> getUgCourses() throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String ugcoursequery = "from UGCoursesBO where isActive=1";
			List<UGCoursesBO> courses = session.createQuery(
					ugcoursequery).list();
//			session.flush();
			//session.close();
			return courses;
		} catch (Exception e) {
			if (session != null){
				//session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
	}
}