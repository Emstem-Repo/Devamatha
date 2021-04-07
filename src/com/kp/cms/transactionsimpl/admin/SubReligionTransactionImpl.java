package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SubReligionTransactionImpl implements ISubReligionTransaction {
	private static final Log log = LogFactory.getLog(SubReligionTransactionImpl.class);
	public static volatile SubReligionTransactionImpl subReligionTransactionImpl = null;

	public static SubReligionTransactionImpl getInstance() {
		if (subReligionTransactionImpl == null) {
			subReligionTransactionImpl = new SubReligionTransactionImpl();
			return subReligionTransactionImpl;
		}
		return subReligionTransactionImpl;
	}

	/**
	 * This method add new Sub Religion to Database.
	 *  @return true / false based on result.
	 * @throws BusinessException 
	 */
	
	public boolean addSubReligion(ReligionSection religionSection, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			     tx = session.beginTransaction();
			     tx.begin();
			     if (mode.equalsIgnoreCase("Add")) { //add mode
				     session.save(religionSection);
			     } else if (mode.equalsIgnoreCase("Edit")) {  //edit mode
				 session.update(religionSection);
			       }
			     tx.commit();
			     session.flush();
			     session.close();
			     return true;
			}else 
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
	 * This will retrieve all the Sub Religions from database.
	 *  @return all religion 
	 */
	
	public List<ReligionSection> getSubReligion() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			   // Query query = session.createQuery("from ReligionSection r where r.isActive = 1 and r.religion.isActive = 1 order by r.name");
			    Query query = session.createQuery("from ReligionSection r where r.isActive = 1  order by r.name");
			    List<ReligionSection> list = query.list();
				session.flush();
				//session.close();
			    return list;
			}else
				return null;
		} catch (Exception e) {
			log.error("Error in getSubReligion.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	
	/**
	 * This will delete a single Sub Religion from database.
	 *  @return all religion 
	 * @throws Exception 
	 */
	
	public boolean deleteSubReligion(int relId, Boolean activate, SubReligionForm subReligionForm) throws Exception{
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			ReligionSection religionSection = (ReligionSection) session.get(ReligionSection.class, relId);
			if (activate) {
				religionSection.setIsActive(true);
			} else {
				religionSection.setIsActive(false);
			}
			religionSection.setModifiedBy(subReligionForm.getUserId());
			religionSection.setLastModifiedDate(new Date());
			session.update(religionSection);
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting Sub Religion...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during deleting Sub Religion...", e);
			throw new ApplicationException(e);
		}
	 
	 }

	public ReligionSection isSubReligionDuplicated(ReligionSection duplReligionSection) throws Exception {
		Session session = null;
		ReligionSection religionSection;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			//StringBuffer sqlString = new StringBuffer("from ReligionSection a where name = :subRelName and religion.id = :relId");
			StringBuffer sqlString = new StringBuffer("from ReligionSection a where name = :subRelName ");
			
			if(duplReligionSection.getId() !=0){
				sqlString = sqlString.append(" and id != :subRelId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("subRelName", duplReligionSection.getName());
			//query.setInteger("relId", duplReligionSection.getReligion().getId());
			if(duplReligionSection.getId() !=0){
				query.setInteger("subRelId", duplReligionSection.getId());
			}
			
			religionSection = (ReligionSection) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return religionSection;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IReligionTransaction#checkSubReligionExists(int)
	 */
	@Override
	public boolean checkSubReligionExists(int religionId) throws Exception {
		Session session = null;
		boolean result=false;
		try {
			session = HibernateUtil.getSession();
			//String studenttypeHibernateQuery = "from ReligionSection r where r.religion.id=:ID and r.isActive=1";
			String studenttypeHibernateQuery = "from ReligionSection r where  r.isActive=1";
			
			Query query = session.createQuery(studenttypeHibernateQuery);
			//query.setInteger("ID", religionId);
			List<ReligionSection> subreligions=query.list();
			if(subreligions!=null && !subreligions.isEmpty()){
				result=true;
			}
			session.flush();
			//session.close();
			
			
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
		return result;
	}
	
}
