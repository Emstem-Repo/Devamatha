package com.kp.cms.transactionsimpl.usermanagement;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Modules;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.usermanagement.ModuleForm;
import com.kp.cms.transactions.usermanagement.IModuleTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ModuleTransactionImpl implements IModuleTransaction {
	public static volatile ModuleTransactionImpl moduleTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ModuleTransactionImpl.class);

	public static ModuleTransactionImpl getInstance() {
		if (moduleTransactionImpl == null) {
			moduleTransactionImpl = new ModuleTransactionImpl();
			return moduleTransactionImpl;
		}
		return moduleTransactionImpl;
	}

	/**
	 * This will retrieve all the Modules from database.
	 * 
	 * @return all Modules
	 * @throws Exception
	 */

	public List<Modules> getModule() throws Exception {
		log.debug("inside getModule");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Modules m where isActive=1 order by m.position");
			List<Modules> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getModule");
			return list;
		} catch (Exception e) {
			log.error("Error during getting Modules..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method add/edit new Module to Database.
	 * 
	 * @return true / false based on result.
	 * @throws Exception
	 */

	public boolean addModules(Modules modules, String mode) throws Exception {
		log.debug("inside addModules");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if ("Add".equalsIgnoreCase(mode)) {
				session.save(modules);
			} else if ("Edit".equalsIgnoreCase(mode)) {
				session.update(modules);
			}
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving addModules");
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving modules data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving modules data...",  e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will delete a single Module from database.
	 * 
	 * @return
	 * @throws Exception
	 */

	public boolean deleteModule(int modId,ModuleForm moduleForm,Boolean activate ) throws Exception {
		log.debug("inside deleteModule");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Modules modules=(Modules)session.get(Modules.class, modId);
			modules.setIsActive(activate);
			modules.setLastModifiedDate(new Date());
			modules.setModifiedBy(moduleForm.getUserId());
			session.update(modules);
			//session.delete(modules);
			tx.commit();
			session.flush();
			//session.close();
			log.debug("leaving deleteModule");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving modules data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving modules data..." , e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * module name duplication checking
	 */
	public Modules isModuleNameDuplcated(Modules oldModule, Boolean nameChanged, Boolean reActivate) throws Exception {
		log.debug("inside isModuleNameDuplcated");
		Session session = null;
		Modules modules = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(reActivate){
				Query query1 = session.createQuery("from Modules a where (position = :pos OR displayName = :dispName) and a.isActive = 0");
				query1.setString("dispName", oldModule.getDisplayName());
				query1.setInteger("pos", oldModule.getPosition());
				modules = (Modules) query1.uniqueResult();
			}
			else if (nameChanged) {
				Query query = session.createQuery("from Modules a where displayName = :moduleName and a.isActive = 1");
				query.setString("moduleName", oldModule.getDisplayName());
				modules = (Modules) query.uniqueResult();
			} else if(!nameChanged){
				Query query2 = session.createQuery("from Modules a where position = :pos and a.isActive = 1");
				query2.setInteger("pos", oldModule.getPosition());
				modules = (Modules) query2.uniqueResult();
			}
			
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isModuleNameDuplcated");
		} catch (Exception e) {
			log.error("Error in duplication module name checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return modules;
	}

	/**
	 * reactivate checking for module
	 * @param moduleForm
	 * @return
	 * @throws Exception
	 */
	public Modules isModuleToBeActivated(ModuleForm moduleForm)	throws Exception {
		log.debug("inside isModuleToBeActivated");
		Session session = null;
		Modules modules;
		Modules result = modules = new Modules();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Modules a where displayName = :moduleName and position = :pos and isActive=0");
			query.setString("moduleName", moduleForm.getName());
			query.setString("pos", moduleForm.getPosition());

			modules = (Modules) query.uniqueResult();

			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isModuleToBeActivated");
			if (modules != null) {
				result = modules;
			}
		} catch (Exception e) {
			log.error("Error in Re activate checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}

}