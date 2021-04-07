package com.kp.cms.transactionsimpl.phd;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.phd.PhdQualificationLevelBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.phd.PhdQualificationLevelForm;
import com.kp.cms.transactions.phd.IPhdQualificationLevel;
import com.kp.cms.utilities.HibernateUtil;

public class PhdQualificationLevelImpl implements IPhdQualificationLevel {
	private static final Log log = LogFactory.getLog(PhdQualificationLevelImpl.class);
	public static volatile PhdQualificationLevelImpl phdQualificationLevelImpl=null;
	
	public static PhdQualificationLevelImpl getInstance(){
		if(phdQualificationLevelImpl == null){
			phdQualificationLevelImpl = new PhdQualificationLevelImpl();
			return phdQualificationLevelImpl;
		}
		return phdQualificationLevelImpl;
	}

	@Override
	public List<PhdQualificationLevelBo> getQualificationLevel()
			throws Exception {
		log.debug("inside getQualificationLevel");
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PhdQualificationLevelBo q where q.isActive = 1");
			List<PhdQualificationLevelBo> list= query.list();
			session.flush();
			log.debug("Leave getQualificationLevel");
			return list;
		}catch (Exception e) {
			 log.error("Error in getEducationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	@Override
	public PhdQualificationLevelBo isDuplicateDisplayOrder(
			PhdQualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		PhdQualificationLevelBo bo;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PhdQualificationLevelBo q where q.displayOrder = :displayOrder and q.isActive=1 ");
			query.setInteger("displayOrder", qualificationLevelForm.getDisplayOrder());
			bo=(PhdQualificationLevelBo) query.uniqueResult();
			session.flush();
			session.close();
		}catch (Exception exception) {
			log
			.error("Error during duplcation isDuplicated checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
		return bo;
	}

	@Override
	public PhdQualificationLevelBo isDuplicated(
			PhdQualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PhdQualificationLevelBo q where q.name = :name and q.isActive=1");
			query.setString("name", qualificationLevelForm.getName());
			PhdQualificationLevelBo bo=(PhdQualificationLevelBo) query.uniqueResult();
			session.flush();
			session.close();
			return bo;
		}catch (Exception exception) {
			log
			.error("Error during duplication isDuplicated checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
	}

	@Override
	public PhdQualificationLevelBo isReactivate(
			PhdQualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		PhdQualificationLevelBo bo;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PhdQualificationLevelBo q where q.name = :name and q.displayOrder = :displayOrder and q.isActive=0 ");
			query.setString("name", qualificationLevelForm.getName());
			query.setInteger("displayOrder", qualificationLevelForm.getDisplayOrder());
			bo=(PhdQualificationLevelBo) query.uniqueResult();
			session.flush();
			session.close();
		}catch (Exception exception) {
			log
			.error("Error during duplcation isDuplicated checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
		return bo;
	}

	@Override
	public boolean addQualificationLevel(PhdQualificationLevelBo qualificationLevelBO, String mode)throws Exception {
		Session session=null;
		boolean result=false;
		Transaction tx=null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(qualificationLevelBO);
			}else if (mode.equalsIgnoreCase("Edit")){
				session.update(qualificationLevelBO);
			}
			tx.commit();
			session.flush();
			session.close();
			result=true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Qualification Level data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Qualification Level data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public boolean deleteQualificationLevel(int id, boolean activate,PhdQualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		Transaction tx=null;
		boolean isDeleted=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			PhdQualificationLevelBo qualificationLevelBO=(PhdQualificationLevelBo)session.get(PhdQualificationLevelBo.class, id);
			if(activate){
				qualificationLevelBO.setIsActive(true);
			}else{
				qualificationLevelBO.setIsActive(false);
			}
			qualificationLevelBO.setModifiedBy(qualificationLevelForm.getUserId());
			qualificationLevelBO.setLastModifiedDate(new Date());
			session.update(qualificationLevelBO);
			tx.commit();
			session.flush();
			session.close();
			isDeleted=true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteQualificationLevel..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteQualificationLevel.." , e);
			throw new ApplicationException(e);
		}
		return isDeleted;
	}

	@Override
	public PhdQualificationLevelBo editQualificationLevel(int id)
			throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String str="from PhdQualificationLevelBo q where q.isActive = 1 and q.id=" +id;
			Query query=session.createQuery(str);
			PhdQualificationLevelBo list=(PhdQualificationLevelBo) query.uniqueResult();
			session.flush();
			return list;
		}catch (Exception exception) {
			
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
	}
		
	}

}
