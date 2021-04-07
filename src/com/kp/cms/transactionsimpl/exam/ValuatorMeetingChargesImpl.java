package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.forms.exam.ValuatorMeetingChargesForm;
import com.kp.cms.transactions.exam.IValuatorMeetingChargesTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ValuatorMeetingChargesImpl implements IValuatorMeetingChargesTransaction{
	private static final Log log=LogFactory.getLog(ValuatorMeetingChargesImpl.class);
	public static volatile ValuatorMeetingChargesImpl valuatorChargesImpl=null;
	public static ValuatorMeetingChargesImpl getInstance()
	{
		if(valuatorChargesImpl==null)
		{
			valuatorChargesImpl=new ValuatorMeetingChargesImpl();
			return valuatorChargesImpl;
		}
		return valuatorChargesImpl;

    }
	@Override
	public List<ValuatorMeetingChargesBo> getValuatorChargeList() throws Exception {
		log.info("call of getValuatorChargeList in ValuatorChargesImpl class.");
		Session session=null;
		List<ValuatorMeetingChargesBo> ValuatorList=null;
		try{
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ValuatorMeetingChargesBo valuatorCharge where valuatorCharge.isActive=1");
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
	public ValuatorMeetingChargesBo getValuatorChargesById(int id) throws Exception {
		Session session=null;
		ValuatorMeetingChargesBo valuatorBo=null;
		try{
			session=HibernateUtil.getSession();
			String str="from ValuatorMeetingChargesBo valuator where valuator.id="+id;
			Query query=session.createQuery(str);
			valuatorBo=(ValuatorMeetingChargesBo)query.uniqueResult();
			
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
      	    String str="from ValuatorMeetingChargesBo valuator where valuator.id="+id;
      	  ValuatorMeetingChargesBo valuatorBo=(ValuatorMeetingChargesBo)session.createQuery(str).uniqueResult();
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
	public boolean duplicateCheck(ValuatorMeetingChargesForm valuatorMeetingChargesForm, int id,ActionErrors errors, HttpSession hsession) throws Exception {
		Session session=null;
		boolean flag=false;
		ValuatorMeetingChargesBo valuatorBo;
		try{
			session=HibernateUtil.getSession();
			String quer="from ValuatorMeetingChargesBo valuator where valuator.isActive=1";
			Query query=session.createQuery(quer);
			//query.setString("scales", payScaleForm.getScale());
			valuatorBo=(ValuatorMeetingChargesBo)query.uniqueResult();
			if(valuatorBo!=null){
				 errors.add("error", new ActionError("knowledgepro.exam.ValuatorCharges.duplicate"));
				 flag = true;
			}
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
		}
		return flag;
	
	}
	@Override
	public boolean addValuator(ValuatorMeetingChargesBo valuatorBo, String mode)throws Exception {
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
	}
