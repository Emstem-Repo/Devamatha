package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdSettingBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.bo.phd.PhdSynopsisDefenseBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.phd.PhdSettingForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.transactions.phd.IPhdSettingTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class PhdSettingImpl implements IPhdSettingTransactions {
	private static Log log = LogFactory.getLog(PhdSettingImpl.class);
	public static volatile PhdSettingImpl examCceFactorImpl = null;
	public static PhdSettingImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdSettingImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdSettingTransactions#getSettingDetails(com.kp.cms.forms.phd.PhdSettingForm)
	 */
	@Override
	public List<PhdSettingBO> getSettingDetails(PhdSettingForm objForm) throws Exception {
		Session session=null;
		List<PhdSettingBO> list=null;
		try {
			session=HibernateUtil.getSession();
			String student=" from PhdSettingBO setting "+
                           " where setting.isActive=1 ";
		Query query=session.createQuery(student);
		list=query.list();
		return list;	
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdSettingTransactions#duplicateCheck(com.kp.cms.forms.phd.PhdSettingForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean duplicateCheck(PhdSettingForm objForm, ActionErrors errors,HttpSession sessions) throws Exception {
		Session session=null;
		boolean flag=false;
		PhdSettingBO setting;
		try{
			session=HibernateUtil.getSession();
			String quer="from PhdSettingBO setting  where setting.isActive=1";
			Query query=session.createQuery(quer);
			setting=(PhdSettingBO)query.uniqueResult();
			if(setting!=null && !setting.toString().isEmpty()){
				if(objForm.getId()!=0){
			      if(setting.getId()==objForm.getId()){
			    	  if(!setting.getIsActive()){
						     flag=true;
						     throw new ReActivateException(setting.getId());
			    	  }else{
			    		  flag=false;
			    	  }
				    
			      }else if(setting.getIsActive()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.phd.Setting.nameexit"));
			       }
			      else{
					   flag=true;
					   objForm.setId(setting.getId());
					   throw new ReActivateException(setting.getId());
				   }
				}else if(setting.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.phd.Setting.nameexit"));
				}
				else{
					  flag=true;
					  setting.setId(setting.getId());
					  objForm.setId(setting.getId());
					  throw new ReActivateException(setting.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.PHD_SETTING_REACTIVATE));
				sessions.setAttribute("ReactivateId", objForm.getId());
			}
		}
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdSettingTransactions#addSetting(com.kp.cms.bo.phd.PhdSettingBO, java.lang.String)
	 */
	@Override
	public boolean addSetting(PhdSettingBO settingBO, String mode)	throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(settingBO);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(settingBO);
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
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdSettingTransactions#getPhdSettingById(int)
	 */
	@Override
	public PhdSettingBO getPhdSettingById(int id) throws Exception {
        Session session = null;
        PhdSettingBO settingBO = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from PhdSettingBO a where a.id=")).append(id).toString()+" and a.isActive=1";
            Query query = session.createQuery(str);
            settingBO = (PhdSettingBO)query.uniqueResult();
            session.flush();
        }
        catch(Exception e)
        {
            log.error("Error during getting getFeedBackQuestionById by id...", e);
            session.flush();
            session.close();
        }
        return settingBO;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteSetting(int id)throws Exception {
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from PhdSettingBO a where a.id=")).append(id).toString();
        PhdSettingBO setting = (PhdSettingBO)session.createQuery(str).uniqueResult();
        transaction = session.beginTransaction();
        setting.setIsActive(false);
        session.update(setting);
        transaction.commit();
        session.close();
    }
    catch(Exception e)
    {
        if(transaction != null)
        {
            transaction.rollback();
        }
        log.debug("Error during deleting deleteFeedBackQuestion data...", e);
    }
    return true;
   }
	
	@Override
	public boolean reactivateSetting(PhdSettingForm objForm)throws Exception {
		log.info("Entering into ValuatorChargesImpl of reactivateDocExamType");
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from PhdSettingBO setting where setting.id="+objForm.getId();
		   Query query=session.createQuery(quer);
		   PhdSettingBO setting=(PhdSettingBO)query.uniqueResult();
		   setting.setIsActive(true);
		   setting.setModifiedBy(objForm.getUserId());
		   setting.setLastModifiedDate(new Date());
		   session.update(setting);
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
