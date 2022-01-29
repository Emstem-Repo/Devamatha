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
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.transactions.employee.IEmpAllowanceTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmpAllowanceTxnImpl implements IEmpAllowanceTxn{
	private static final Log log = LogFactory
	.getLog(EmpAllowanceTxnImpl.class);
	public static volatile EmpAllowanceTxnImpl allowanceType=null;
	public static EmpAllowanceTxnImpl getInstance(){
		if(allowanceType == null){
			allowanceType = new EmpAllowanceTxnImpl();
			return allowanceType;
		}
		return allowanceType;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmpAllowance> getEmpAllowance() throws Exception {
		Session session=null;
		List<EmpAllowance> allowanceList=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from EmpAllowance al where al.isActive=1";
			Query query=session.createQuery(quer);
			allowanceList=query.list();
		}catch(Exception e){
			log.debug("Error during saving data...", e);
		}
		return allowanceList;
	}
	@Override
	public boolean duplicateCheck(String name, HttpSession hSession,
			ActionErrors errors, EmpAllowanceForm empAllowanceForm) {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from EmpAllowance a where a.name=:allowanceName or a.displayOrder=:order";
			Query query=session.createQuery(quer);
			query.setString("allowanceName", name);
			query.setString("order", empAllowanceForm.getDisplayOrder());
			//query.setString("scales", payScaleForm.getScale());
			EmpAllowance allowance=(EmpAllowance)query.uniqueResult();
			if(allowance!=null && !allowance.toString().isEmpty()){
				if(empAllowanceForm.getId()!=0){
			      if(allowance.getId()==empAllowanceForm.getId()){
				    flag=false;
			      }else if(allowance.getIsActive()){
				     flag=true;
			         errors.add("error", new ActionError("knowledgepro.employee.allowance.duplicate"));
			       }
			      else{
					   flag=true;
					   empAllowanceForm.setId(allowance.getId());
					   throw new ReActivateException(allowance.getId());
				   }
				}else if(allowance.getIsActive()){
					flag=true;
			        errors.add("error", new ActionError("knowledgepro.employee.allowance.duplicate"));
				}
				else{
					  flag=true;
					  empAllowanceForm.setId(allowance.getId());
					  throw new ReActivateException(allowance.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			flag=true;
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.EMP_ALLOWANCE_TYPE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", empAllowanceForm.getId());
			}else{
				errors.add("error", new ActionError("knowledgepro.employee.allowance.fail"));
			}
		}
		return flag;
	}
	@Override
	public boolean addAllowance(EmpAllowance allowance) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(allowance);
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
	public EmpAllowance getAllowanceById(int id) throws Exception {
		Session session=null;
		EmpAllowance allowance=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmpAllowance a where a.id="+id;
			Query query=session.createQuery(str);
			allowance=(EmpAllowance)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting allowance by id..." , e);
			session.flush();
			session.close();
			
		}
		return allowance;
	}
	@Override
	public boolean updateAllowance(EmpAllowance allowance) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(allowance);
			transaction.commit();
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating Allowance data...", e);
		}
		return true;
	}
	@Override
	public boolean deleteAllowance(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from EmpAllowance a where a.id="+id;
      	    EmpAllowance allowance=(EmpAllowance)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    allowance.setIsActive(false);
      	    session.update(allowance);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting allowance data...", e);
      	
         }
       return true;
	}
	@Override
	public boolean reactivateAllowance(EmpAllowanceForm empAllowanceForm)
			throws Exception {
		log.info("Entering into EmpAllowanceTxnImpl of reactivateAllowance");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from EmpAllowance a where a.id="+empAllowanceForm.getId();
		   Query query=session.createQuery(quer);
		   EmpAllowance allowance=(EmpAllowance)query.uniqueResult();
		   allowance.setIsActive(true);
		   allowance.setModifiedBy(empAllowanceForm.getUserId());
		   allowance.setLastModifiedDate(new Date());
		   session.update(allowance);
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
