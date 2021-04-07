package com.kp.cms.transactionsimpl.serviceLearning;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.forms.serviceLearning.ProgrammeEntryForm;
import com.kp.cms.transactions.serviceLearning.IProgrammeEntryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ProgrammeEntryTransactionImpl implements IProgrammeEntryTransaction{
	
	private static final Log log = LogFactory.getLog(ProgrammeEntryTransactionImpl.class);
	
	public List<ProgrammeEntryBo> getProgrammeNameEntry(){
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<ProgrammeEntryBo> programmeEntryBoList = session.createQuery("from ProgrammeEntryBo h where h.isActive=1").list();
			session.flush();
			//session.close();
			return programmeEntryBoList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}
	public List<ProgrammeEntryBo> getProgrammeNameEntrysd(){
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<ProgrammeEntryBo> programmeEntryBoList = session.createQuery("from ProgrammeEntryBo h where h.isActive=1").list();
			session.flush();
			//session.close();
			return programmeEntryBoList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}
	public ProgrammeEntryBo isDuplicateProgrammeEntryDetail(
			ProgrammeEntryForm programmeEntryForm, String mode) throws ApplicationException {
		Session session=null;
		String query = null;
		ProgrammeEntryBo programmeEntryBo;
		try{
			session=HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("edit")) {				
			   query="from ProgrammeEntryBo a where a.programmeCode = :programmeCode and extraCreditActivityType.id = :extraCreditActivityType and a.id !=  "+programmeEntryForm.getId() ;
			}
			
			else{
				query="from ProgrammeEntryBo where programmeCode = :programmeCode and extraCreditActivityType.id = :extraCreditActivityType";
			}
				
			Query que=session.createQuery(query)
							  .setString("programmeCode", programmeEntryForm.getProgrammeCode())
							  .setInteger("extraCreditActivityType", Integer.parseInt(programmeEntryForm.getExtraCreditActivityType()));
			
			programmeEntryBo = (ProgrammeEntryBo) que.uniqueResult();
			session.flush();
			session.close();
			//if(attendanceEntryDay!=null)
				return programmeEntryBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public boolean addProgrammeNameEntry(ProgrammeEntryBo programmeEntryBo ,String mode)
	throws Exception {
		Session session = null;
		Transaction transaction= null;
		boolean isAdded=false;
		try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(programmeEntryBo);
			}else if(mode.equalsIgnoreCase("edit")){
				session.update(programmeEntryBo);
			}
			transaction.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			if(transaction!=null)
				transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
				transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new ApplicationException(e);
		}
		return isAdded;


}
	
	public boolean deleteProgrammeNameEntry(int id, boolean activate,
			ProgrammeEntryForm programmeEntryForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isDeleted= false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			ProgrammeEntryBo programmeEntryBo= (ProgrammeEntryBo)session.get(ProgrammeEntryBo.class, id);
			if(activate){
				programmeEntryBo.setIsActive(true);
			}else{
				programmeEntryBo.setIsActive(false);
			}
			programmeEntryBo.setLastModifiedDate(new Date());
			programmeEntryBo.setModifiedBy(programmeEntryForm.getUserId());
			session.update(programmeEntryBo);
			transaction.commit();
			session.flush();
			//session.close();
			 isDeleted = true;
		}catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Department data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Department data..." ,e);
			throw new ApplicationException(e);
		}
		return isDeleted;
	}
	
	public ProgrammeEntryBo getProgrammeNameEntryDetails(String id) throws Exception {
		Session session=null;
		ProgrammeEntryBo programmeEntryBo=null;
		try{
			session=HibernateUtil.getSession();
			String str="from ProgrammeEntryBo a where a.id="+id;
			Query query=session.createQuery(str);
			programmeEntryBo=(ProgrammeEntryBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting ProgrammeEntryBo by id..." , e);
			//session.flush();
			//session.close();
			
		}
		return programmeEntryBo;
		
	
	}
	
	public boolean reActivateProgrammeNameEntry(ProgrammeEntryBo programmeEntryBo,String userId) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			programmeEntryBo.setModifiedBy(userId);
			programmeEntryBo.setLastModifiedDate(new Date());
			programmeEntryBo.setIsActive(true);
			session.update(programmeEntryBo);
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
	public List<ProgrammeEntryBo> getProgrammeNameEntryRestrictionByDate(){
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String date = CommonUtil.getTodayDate();
			Date curDate = CommonUtil.ConvertStringToSQLDate(date);
			List<ProgrammeEntryBo> programmeEntryBoList = session.createQuery("select e from ProgrammeEntryBo e where (CURDATE() between e.startDate and e.endDate )  and  e.isActive=1 ").list();
			session.flush();
			//session.close();
			return programmeEntryBoList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}

}
