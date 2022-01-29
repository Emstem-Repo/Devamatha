package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.GuidelinesChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.GuideLinesCheckListForm;
import com.kp.cms.transactions.admin.IGuideLinesCheckListTransactions;
import com.kp.cms.utilities.HibernateUtil;


public class GuideLinesCheckListTransactionImpl implements IGuideLinesCheckListTransactions{
	private static final Log log = LogFactory.getLog(GuideLinesCheckListTransactionImpl.class);
	public static volatile GuideLinesCheckListTransactionImpl guideLinesCheckListTransactionImpl = null;

	public static GuideLinesCheckListTransactionImpl getInstance() {
		if (guideLinesCheckListTransactionImpl == null) {
			guideLinesCheckListTransactionImpl = new GuideLinesCheckListTransactionImpl();
			return guideLinesCheckListTransactionImpl;
		}
		return guideLinesCheckListTransactionImpl;
	}


	/**
	 * 
	 * @param guidelinesChecklist
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addGuidelinesCheckList(GuidelinesChecklist guidelinesChecklist, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
				tx = session.beginTransaction();
				tx.begin();
				if (mode.equalsIgnoreCase("Add")) { //add mode
					session.save(guidelinesChecklist);
				} else if (mode.equalsIgnoreCase("Edit")) {  //edit mode
					session.update(guidelinesChecklist);
				}
				tx.commit();
			    session.flush();
				session.close();
				return true;
			}
			else
				return false;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving addSubReligion data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving addSubReligion data...", e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public List<GuidelinesChecklist> getGuideLineCheckList() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			     Query query = session.createQuery("from GuidelinesChecklist g where g.isActive = 1 and g.organisation.isActive = 1 ");
			     List<GuidelinesChecklist> list = query.list();
				 session.flush();
				 //session.close();
			     //sessionFactory.close();
			     log.debug("leaving getGuideLineCheckList in impl");
			     return list;
			}
			else
				return null;
		} catch (Exception e) {
			log.error("Error in getGuideLineCheckList.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	

	/**
	 * 
	 * @param id
	 * @param activate
	 * @param guideLinesCheckListForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteGuidelinesChecklist(int id, Boolean activate, GuideLinesCheckListForm guideLinesCheckListForm) throws Exception{
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			GuidelinesChecklist guidelinesChecklist = (GuidelinesChecklist) session.get(GuidelinesChecklist.class, id);
			if (activate) {
				guidelinesChecklist.setIsActive(true);
			} else {
				guidelinesChecklist.setIsActive(false);
			}
			guidelinesChecklist.setModifiedBy(guideLinesCheckListForm.getUserId());
			guidelinesChecklist.setLastModifiedDate(new Date());
			session.update(guidelinesChecklist);
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting GuidelinesChecklist...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during deleting GuidelinesChecklist...", e);
			throw new ApplicationException(e);
		}
	 
	 }
	
	/**
	 * duplication checking
	 */
	public GuidelinesChecklist isGuideLinesDuplicated(GuidelinesChecklist duplChecklist) throws Exception {
		Session session = null;
		GuidelinesChecklist guidelinesChecklist;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from GuidelinesChecklist g where g.organisation.id = :orgId");
			if(duplChecklist.getId()!=0){
				sqlString = sqlString.append(" and id != :cId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setInteger("orgId", duplChecklist.getOrganisation().getId());
			if(duplChecklist.getId() !=0){
				query.setInteger("cId", duplChecklist.getId());
			}
			
			guidelinesChecklist = (GuidelinesChecklist) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return guidelinesChecklist;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	

}
