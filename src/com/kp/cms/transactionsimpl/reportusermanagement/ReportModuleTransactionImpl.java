package com.kp.cms.transactionsimpl.reportusermanagement;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.ReportModules;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reportusermanagement.ReportModuleForm;
import com.kp.cms.forms.usermanagement.ModuleForm;
import com.kp.cms.transactions.reportusermanagement.IReportModuleTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ReportModuleTransactionImpl implements IReportModuleTransaction {
	public static volatile ReportModuleTransactionImpl moduleTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ReportModuleTransactionImpl.class);

	public static ReportModuleTransactionImpl getInstance() {
		if (moduleTransactionImpl == null) {
			moduleTransactionImpl = new ReportModuleTransactionImpl();
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

	public List<ReportModules> getModule() throws Exception {
		log.debug("inside getModule");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ReportModules m where isActive=1 order by m.position");
			List<ReportModules> list = query.list();
			session.flush();
			log.debug("leaving getModule");
			return list;
		} catch (Exception e) {
			log.error("Error during getting Modules..." , e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method add/edit new Module to Database.
	 * 
	 * @return true / false based on result.
	 * @throws Exception
	 */

	public boolean addModules(ReportModules modules, String mode) throws Exception {
		log.debug("inside addModules");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
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

	public boolean deleteModule(int modId,ReportModuleForm moduleForm,Boolean activate ) throws Exception {
		log.debug("inside deleteModule");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			ReportModules modules=(ReportModules)session.get(ReportModules.class, modId);
			modules.setIsActive(activate);
			modules.setLastModifiedDate(new Date());
			modules.setModifiedBy(moduleForm.getUserId());
			session.update(modules);
			tx.commit();
			session.flush();
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
	public ReportModules isModuleNameDuplcated(ReportModules oldModule, Boolean nameChanged, Boolean reActivate) throws Exception {
		log.debug("inside isModuleNameDuplcated");
		Session session = null;
		ReportModules modules = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(reActivate){
				Query query1 = session.createQuery("from ReportModules a where displayName = :dispName and a.isActive = 0");
				query1.setString("dispName", oldModule.getDisplayName());
				modules = (ReportModules) query1.uniqueResult();
			}
			else if (nameChanged) {
				Query query = session.createQuery("from ReportModules a where displayName = :moduleName and a.isActive = 1");
				query.setString("moduleName", oldModule.getDisplayName());
				modules = (ReportModules) query.uniqueResult();
			} else if(!nameChanged){
				Query query2 = session.createQuery("from ReportModules a where position = :pos and a.isActive = 1");
				query2.setInteger("pos", oldModule.getPosition());
				modules = (ReportModules) query2.uniqueResult();
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


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reportusermanagement.IReportModuleTransaction#ReactivateModule(int, com.kp.cms.bo.admin.ReportModules, boolean)
	 */
	@Override
	public boolean ReactivateModule(int id, ReportModules module, boolean activate) throws Exception {
		log.debug("inside ReactivateModule");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Query query = session.createQuery("from ReportModules a where a.id = :moduleID");
			query.setString("moduleID", String.valueOf(id));
			ReportModules modules = (ReportModules) query.uniqueResult();
			Query query1 = session.createQuery("select a.position from ReportModules a");
			List<Integer> countList =  query1.list();
			int count = modules.getPosition();
			if(countList.contains(modules.getPosition())){
				boolean isExist=false;
				do{
					if(countList.contains(count)){
						isExist=true;
						count = count+1;
					}else{
						isExist=false;
					}
				}while(isExist);
			}
			
			
			modules.setIsActive(activate);
			modules.setPosition(count);
			session.update(modules);
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

}