package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Program;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.ProgramForm;
import com.kp.cms.transactions.admin.IProgramTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ProgramTransactionImpl implements IProgramTransaction {
	public static volatile ProgramTransactionImpl programTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ProgramTransactionImpl.class);

	public static ProgramTransactionImpl getInstance() {
		if (programTransactionImpl == null) {
			programTransactionImpl = new ProgramTransactionImpl();
			return programTransactionImpl;
		}
		return programTransactionImpl;
	}

	/**
	 * 
	 * @return This will retrieve all the Program from database.using to display in the UI
	 * @throws Exception
	 */

	public List<Program> getPrograme() throws Exception {
		log.debug("inside getPrograme");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Program p where isActive = 1 and programType.isActive = 1 ");
			List<Program> list = query.list();
			session.flush();
			//session.close();
//			sessionFactory.close();
			log.debug("leaving getPrograme");
			return list;
		 } catch (Exception e) {
			 log.error("Error during getting program...",e);
			// session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * 
	 * @return This method add/edit new Program to Database.
	 * @throws Exception 
	 */

	public boolean addProgram(Program program, String mode) throws Exception {
		log.debug("inside addProgram");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(program);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.merge(program);
			}
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving addProgram");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving program data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving program data...", e);
			throw new ApplicationException(e);
		}
	}


	/**
	 * This will delete a single Program from database.
	 * 
	 * @return all religion
	 * @throws Exception 
	 */

	public boolean deleteProgram(int programId, Boolean activate, ProgramForm programForm) throws Exception {
		log.debug("inside deleteProgram");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Program program = (Program) session.get(Program.class, programId);
			if(activate){
				program.setIsActive(true);
			}
			else
			{
				program.setIsActive(false);
			}
			program.setModifiedBy(programForm.getUserId());
			program.setLastModifiedDate(new Date());
			session.update(program);
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteProgram");
			return true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				//session.close();
				throw e;
			}
			return false;
		}
	}
	/**
	 * 
	 * checking for program name duplication in table
	 */
	public boolean isProgramNameDuplcated(ProgramForm programForm) throws Exception {
		log.debug("inside isProgramNameDuplcated");
		Session session = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Program a where name = :programName and programType.id = :programTypeId and isActive=1");
			query.setString("programName", programForm.getName());
			query.setString("programTypeId", programForm.getProgramTypeId());
			List<Program> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isProgramNameDuplcated");
			if (!list.isEmpty()) {
				result =  true;
			}
		} catch (Exception e) {
			log.error("Error in duplication program name checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * checking for program code duplication in table
	 */
	public boolean isProgramCodeDuplcated(String programCode) throws Exception {
		log.debug("inside isProgramCodeDuplcated");
		Session session = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Program a where code = :programCode and isActive=1");
			query.setString("programCode", programCode);
			List<Program> list = query.list();
			session.flush();
			//session.close();
//			sessionFactory.close();
			if (!list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			log.error("Error in duplication program code checking..." ,e);
		//	session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isProgramCodeDuplcated");
		return result;
	}	
	/**
	 * checking for reactivate
	 */
	public Program isProgramToBeActivated(ProgramForm programForm) throws Exception {
		log.debug("inside isProgramToBeActivated");
		Session session = null;
		Program program;
		Program result = program = new Program(); 
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Program a where code = :programCode and name = :programName and programType.id = :programTypeId  and isActive=0");
			query.setString("programCode", programForm.getProgramCode());
			query.setString("programName", programForm.getName());
			query.setString("programTypeId", programForm.getProgramTypeId());

			program = (Program) query.uniqueResult();
			session.flush();
			//session.close();
//			sessionFactory.close();
			if (program!= null) {
				result = program;
			}
		} catch (Exception e) {
			log.error("Error in Re activate checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isProgramToBeActivated");
		return result;
	}	
	
	/**
	 * 
	 * @return This will retrieve all the Program from database.
	 * @throws Exception
	 */

	public Program getProgramDetails(int id) throws Exception {
		log.debug("inside getProgramDetails");
		Session session = null;
		Program program;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			program = (Program)session.get(Program.class, id);
			session.flush();
			//session.close();
//			sessionFactory.close();
			log.debug("leaving getProgramDetails eith success");
			return program;
		 } catch (Exception e) {
			 log.error("Error during getting program..."+e);
			// session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	
}