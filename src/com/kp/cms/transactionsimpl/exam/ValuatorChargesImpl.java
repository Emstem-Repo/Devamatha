package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
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

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.transactions.exam.IValuatorChargesTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ValuatorChargesImpl implements IValuatorChargesTransaction{
	private static final Log log=LogFactory.getLog(ValuatorChargesImpl.class);
	public static volatile ValuatorChargesImpl valuatorChargesImpl=null;
	public static ValuatorChargesImpl getInstance()
	{
		if(valuatorChargesImpl==null)
		{
			valuatorChargesImpl=new ValuatorChargesImpl();
			return valuatorChargesImpl;
		}
		return valuatorChargesImpl;

    }
	@Override
	public List<ValuatorChargesBo> getValuatorChargeList() throws Exception {
		log.info("call of getValuatorChargeList in ValuatorChargesImpl class.");
		Session session=null;
		List<ValuatorChargesBo> ValuatorList=null;
		try{
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ValuatorChargesBo valuatorCharge where valuatorCharge.isActive=1");
			 ValuatorList=query.list();
//			session.close();
		}catch(Exception e){
			log.error("Unable to getValuatorChargeList",e);
			 throw e;
		}
		log.info("end of getValuatorChargeList in ValuatorChargesImpl class.");
		return ValuatorList;
	}
	@Override
	public boolean duplicateCheck(ValuatorChargesForm valuatorChargesForm,int id) throws Exception {
		Session session=null;
		boolean flag=false;
		
		
		return false;
	}
	@Override
	public boolean addValuator(ValuatorChargesBo valuatorBo,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(valuatorBo);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.update(valuatorBo);
			}
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
	public ValuatorChargesBo getValuatorChargesById(int id) throws Exception {
		Session session=null;
		ValuatorChargesBo valuatorBo=null;
		try{
			session=HibernateUtil.getSession();
			String str="from ValuatorChargesBo valuator where valuator.id="+id;
			Query query=session.createQuery(str);
			valuatorBo=(ValuatorChargesBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
			
		}
		return valuatorBo;
		
	}
	@Override
	public boolean deleteValuatorCharges(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from ValuatorChargesBo valuator where valuator.id="+id;
      	  ValuatorChargesBo valuatorBo=(ValuatorChargesBo)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  valuatorBo.setIsActive(false);
      	    session.update(valuatorBo);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting ValuatorCharges data...", e);
      	
      }
return true;
		
	}
	@Override
	public boolean reactivatePayScale(ValuatorChargesForm valuatorChargesForm)throws Exception {
		log.info("Entering into ValuatorChargesImpl of reactivateDocExamType");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from ValuatorChargesBo valuator where valuator.id="+valuatorChargesForm.getId();
		   Query query=session.createQuery(quer);
		   ValuatorChargesBo leave=(ValuatorChargesBo)query.uniqueResult();
		   leave.setIsActive(true);
		   leave.setModifiedBy(valuatorChargesForm.getUserId());
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

	@Override
	public boolean duplicateCheck(ValuatorChargesForm valuatorChargesForm,String programTypeId, ActionErrors errors, HttpSession hsession)
			throws Exception {
		Session session=null;
		boolean flag=false;
		ValuatorChargesBo valuatorBo;
		try{
			session=HibernateUtil.getSession();
			String quer="from ValuatorChargesBo valuator where valuator.programType.id=:programTypeId";
			Query query=session.createQuery(quer);
			query.setString("programTypeId", programTypeId);
			//query.setString("scales", payScaleForm.getScale());
			valuatorBo=(ValuatorChargesBo)query.uniqueResult();
			if(valuatorBo!=null && !valuatorBo.toString().isEmpty()){
				if(valuatorChargesForm.getId()!=0){
			      if(valuatorBo.getId()==valuatorChargesForm.getId()){
				    flag=false;
			      }else if(valuatorBo.getIsActive()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.exam.ValuatorCharges.duplicate"));
			       }
			      else{
					   flag=true;
					   valuatorChargesForm.setProgramTypeId(String.valueOf(valuatorBo.getProgramType().getId()));
					   valuatorChargesForm.setId(valuatorBo.getId());
					   throw new ReActivateException(valuatorBo.getId());
				   }
				}else if(valuatorBo.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.exam.ValuatorCharges.duplicate"));
				}
				else{
					  flag=true;
					  valuatorBo.setId(valuatorBo.getId());
					  valuatorChargesForm.setId(valuatorBo.getId());
					  throw new ReActivateException(valuatorBo.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.EXAM_Valuator_Charge_REACTIVATE));
				//saveErrors(request, errors);
				hsession.setAttribute("ReactivateId", valuatorChargesForm.getId());
			}
		}
		return flag;
	}	
	
}
