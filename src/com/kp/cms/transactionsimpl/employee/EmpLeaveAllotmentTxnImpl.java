package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.transactions.employee.IEmpLeaveAllotmentTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmpLeaveAllotmentTxnImpl implements IEmpLeaveAllotmentTxn{
	private static final Log log = LogFactory
	.getLog(EmpLeaveAllotmentTxnImpl.class);
	@Override
	public List<EmpLeaveAllotment> getLeaveAllotments() throws Exception {
		Session session=null;
		List<EmpLeaveAllotment> empLeaveAllot=null;
		try{
			String query="from EmpLeaveAllotment leave where leave.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			empLeaveAllot=querry.list();
		}catch(Exception exception){
			log.error("Error during getting empLeaveAllotment list..." , exception);
		}
		finally{
			session.flush();
		}
		return empLeaveAllot;
		
	}
	@Override
	public List<EmpType> getEmpType() throws Exception {
		Session session=null;
		List<EmpType> empType=null;
		try{
			String query="from EmpType emptype where emptype.isActive=1";
			session=HibernateUtil.getSession();
			Query quer=session.createQuery(query);
			empType=quer.list();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
		}
			return empType;
		
	}
	@Override
	public boolean addLeaveAllot(EmpLeaveAllotment leaveAllot) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(leaveAllot);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
		
	}
	@Override
	public EmpLeaveAllotment getLeaveAllotmentById(int id) throws Exception {
		Session session=null;
		EmpLeaveAllotment leaveAllotment=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmpLeaveAllotment leave where leave.id="+id;
			Query query=session.createQuery(str);
			leaveAllotment=(EmpLeaveAllotment)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
			
		}
		return leaveAllotment;
	}
	@Override
	public boolean updateLeaveAllotment(EmpLeaveAllotment leaveAllotment)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(leaveAllotment);
			transaction.commit();
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating LeaveAllotment data...", e);
		}
		return true;
	
	}
	@Override
	public boolean deleteLeaveAllotment(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from EmpLeaveAllotment leave where leave.id="+id;
      	  EmpLeaveAllotment leaveAllotment=(EmpLeaveAllotment)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  leaveAllotment.setIsActive(false);
      	    session.update(leaveAllotment);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting leaveAllotment data...", e);
      	
      }
return true;
	
	}
	@Override
	public boolean duplicateCheck(String empType, String leaveType,
			HttpSession hSession,ActionErrors errors,int id,EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception {
 		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from EmpLeaveAllotment leave where leave.empType.id="+empType+" and leave.empLeaveType.id="+leaveType;
			
			Query query=session.createQuery(quer);
			EmpLeaveAllotment leaveAllot=(EmpLeaveAllotment)query.uniqueResult();
			if(leaveAllot!=null && !leaveAllot.toString().isEmpty()){
				if(id!=0){
			if(leaveAllot.getId()==id){
				flag=false;
			}
			else if(leaveAllot.getIsActive()){
				flag=true;
				errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.duplicate"));
			}else{
				flag=true;
				empLeaveAllotForm.setId(leaveAllot.getId());
				throw new ReActivateException(leaveAllot.getId());
			}
			}
				else if(leaveAllot.getIsActive()){
				flag=true;
				errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.duplicate"));
				}
				else{
					flag=true;
					empLeaveAllotForm.setId(leaveAllot.getId());
					throw new ReActivateException(leaveAllot.getId());
				}
			}
			else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.EMPLOYEE_LEAVEALLOTMENT_REACTIVATE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", empLeaveAllotForm.getId());
			}
		}
		return flag;

	}
	@Override
	public int getEmpSettingsLeaveType() throws Exception {
	    Session session=null;
	    int settingsLeave=0;
	    try{
	    	session=HibernateUtil.getSession();
	    	String str="select sett.accumulateLeaveType.id from EmployeeSettings sett";
	    	Query query=session.createQuery(str);
	    	settingsLeave=Integer.parseInt(query.uniqueResult().toString());
	    }catch(Exception e){
	    	log.error("Error during getting EmpSettings LeaveType..." , e);
	    	
	    }
		return settingsLeave;
	}
	@Override
	public List<EmpLeaveType> getEmpLeaveType(int settingsLeave) throws Exception {
		Session session=null;
		List<EmpLeaveType> empLeaveType=null;
		try{
			String query="from EmpLeaveType empleave where empleave.isActive=1 and empleave.isLeave=1 and empleave.id!= "+settingsLeave;
			session=HibernateUtil.getSession();
			Query quer=session.createQuery(query);
			empLeaveType=quer.list();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
		}
			return empLeaveType;
		
	}
	@Override
	public boolean reactivateLeaveAllotment(
			EmpLeaveAllotmentForm empLeaveAllotForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from EmpLeaveAllotment allot where allot.id="+empLeaveAllotForm.getId();
		   Query query=session.createQuery(quer);
		   EmpLeaveAllotment leave=(EmpLeaveAllotment)query.uniqueResult();
		   leave.setIsActive(true);
		   leave.setModifiedBy(empLeaveAllotForm.getUserId());
		   leave.setLastModifiedDate(new Date());
		   session.update(leave);
		   transaction.commit();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
	}
	
	
}
