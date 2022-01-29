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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.transactions.serviceLearning.IDepartmentNameEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class DepartmentNameEntryImpl implements IDepartmentNameEntryTransaction {
	
	private static final Log log = LogFactory.getLog(DepartmentNameEntryImpl.class);
	public List<DepartmentNameEntry> getDepartmentNames(){
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<DepartmentNameEntry> casteList = session.createQuery("from DepartmentNameEntry h where h.isActive=1 ").list();
			session.flush();
			//session.close();
			return casteList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}
	
	public DepartmentNameEntry isDuplicateDepartmentNameEntryDetail(
			DepartmentNameEntryForm departmentNameEntryForm, String mode) throws ApplicationException {
		Session session=null;
		String query = null;
		DepartmentNameEntry departmentNameEntry;
		try{
			session=HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("edit")){
				
			   query="from DepartmentNameEntry a where departmentName = :name  and a.id = :id " ;
			}
			
			else{
				query="from DepartmentNameEntry a where  departmentName = :name  ";
			}
				
			Query que=session.createQuery(query);
			if(mode.equalsIgnoreCase("add")){
				que.setString("name", departmentNameEntryForm.getDepartmentName());					
							
			}
			
			if(mode.equalsIgnoreCase("edit")){
			  que.setString("id", departmentNameEntryForm.getId());
			que.setString("name", departmentNameEntryForm.getDepartmentName());					
			
			}
			departmentNameEntry = (DepartmentNameEntry) que.uniqueResult();
			session.flush();
			session.close();
			//if(attendanceEntryDay!=null)
				return departmentNameEntry;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public boolean addDepartmentNameEntry(DepartmentNameEntry departmentNameEntry ,String mode)
	throws Exception {
		Session session = null;
		Transaction transaction= null;
		boolean isAdded=false;
		try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(departmentNameEntry);
			}else if(mode.equalsIgnoreCase("edit")){
				session.update(departmentNameEntry);
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
	
	public boolean deleteDepartmentNameEntry(int id, boolean activate,
			DepartmentNameEntryForm departmentNameEntryForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isDeleted= false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			DepartmentNameEntry departmentNameEntry= (DepartmentNameEntry)session.get(DepartmentNameEntry.class, id);
			if(activate){
				departmentNameEntry.setIsActive(true);
			}else{
				departmentNameEntry.setIsActive(false);
			}
			departmentNameEntry.setLastModifiedDate(new Date());
			departmentNameEntry.setModifiedBy(departmentNameEntryForm.getUserId());
			session.update(departmentNameEntry);
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
	
	public DepartmentNameEntry getDepartmentNameEntryDetails(String id) throws Exception {
		Session session=null;
		DepartmentNameEntry departmentNameEntry=null;
		try{
			session=HibernateUtil.getSession();
			String str="from DepartmentNameEntry a where a.id="+id;
			Query query=session.createQuery(str);
			departmentNameEntry=(DepartmentNameEntry)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			//session.flush();
			//session.close();
			
		}
		return departmentNameEntry;
		
	
	}
	
	public boolean reActivateDepartmentNameEntry(DepartmentNameEntry departmentNameEntry,String userId) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			departmentNameEntry.setModifiedBy(userId);
			departmentNameEntry.setLastModifiedDate(new Date());
			departmentNameEntry.setIsActive(true);
			session.update(departmentNameEntry);
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

}
