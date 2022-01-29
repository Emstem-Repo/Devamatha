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

import com.kp.cms.bo.phd.PhdCompletionDetailsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.PhdCompletionDetailsForm;
import com.kp.cms.transactions.phd.IPhdCompletionDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PhdCompletionDetailsTransactionImpl implements IPhdCompletionDetailsTransaction {
	
	private static Log log = LogFactory.getLog(PhdCompletionDetailsTransactionImpl.class);
	public static volatile PhdCompletionDetailsTransactionImpl completionImpl = null;
	public static PhdCompletionDetailsTransactionImpl getInstance() {
		if (completionImpl == null) {
			completionImpl = new PhdCompletionDetailsTransactionImpl();
			return completionImpl;
		}
		return completionImpl;
	}
	
	@Override
	public boolean addCompletionDetails(PhdCompletionDetailsBO synopsisBO,PhdCompletionDetailsForm objForm,
			ActionErrors errors, HttpSession sessions) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=true;
		try{
			
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
		   String str=" from PhdCompletionDetailsBO com where com.studentId.id="+synopsisBO.getStudentId().getId();
		   Query qury=session.createQuery(str);
		   PhdCompletionDetailsBO complete=(PhdCompletionDetailsBO) qury.uniqueResult();
		   if(complete!=null){
			   synopsisBO.setId(complete.getId());
		   }
		 if(complete!=null && complete.getIsActive()){
				session.merge(synopsisBO);
			}
		 else if(complete!=null && !complete.getIsActive()){
			 objForm.setId(synopsisBO.getId());
			 throw new ReActivateException(synopsisBO.getId());
		   }
			else if(complete!=null && objForm.getId()!=synopsisBO.getId()){
				session.save(synopsisBO);
			  }
			else{
				session.merge(synopsisBO);
			}
		transaction.commit();
		}
		   catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			if(exception instanceof ReActivateException){
				errors.add("error", new ActionError("knowledgepro.phd.PhdCompletionDetails.reactivate"));
				flag=false;
				sessions.setAttribute("ReactivateId", objForm.getId());
				
			}
			log.debug("Error during saving data...", exception);
			
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
	}
	
	@Override
	public List<Object[]> getStudentDetails(PhdCompletionDetailsForm objForm,ActionErrors errors) throws Exception {
		Session session=null;
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();
			String student=" select per.first_name,per.middle_name,per.last_name,s.id,adm_appln.applied_year,co.name,co.id as co, "+
            " DATE_FORMAT(study.viva_voice,'%d/%m/%Y') as dt,study.id as i,study.title,study.discipline"+
            " from student s"+
            " inner join adm_appln ON s.adm_appln_id = adm_appln.id"+
            " and s.is_admitted=1"+
            " and adm_appln.is_cancelled=0"+
            " inner join personal_data per ON adm_appln.personal_data_id = per.id"+
            " inner join course co on adm_appln.course_id = co.id"+
            " left join phd_completion_details study on study.student_id = s.id"+
            " and study.student_id = s.id"+
            " and study.is_active=1" +
            " where s.register_no='"+objForm.getRegisterNo()+"'"+
            " and s.is_hide=0"+
            " and s.id not in (select student_id from EXAM_student_detention_rejoin_details "+
            " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))";
		Query query=session.createSQLQuery(student);
		list=query.list();
		if(list.isEmpty() && !objForm.getRegisterNo().isEmpty()){
		 errors.add("error", new ActionError("knowledgepro.phd.StudyAggrement.valid"));
		}if(list.isEmpty() && objForm.getRegisterNo().isEmpty()){
		 errors.add("error", new ActionError("knowledgepro.phd.StudyAggrement.notBlank"));
		}
		return list;	
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public List<PhdCompletionDetailsBO> getPhdCompletionDetails(PhdCompletionDetailsForm objForm) throws Exception {
		Session session=null;
		log.info("start of getPhdCompletionDetails in PhdCompletionDetailsImpl class.");
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" select phd from PhdCompletionDetailsBO phd"
					                       +" join phd.studentId s"
					                       +" where s.registerNo='"+objForm.getRegisterNo()+"'"
			                               +" and phd.isActive=1");
					                      
			List<PhdCompletionDetailsBO> list=query.list();
			log.info("end of getPhdCompletionDetails in PhdCompletionDetailsImpl class.");
		    return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleteCompletionDetails(int id) throws Exception {
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from PhdCompletionDetailsBO a where a.id=")).append(id).toString();
        PhdCompletionDetailsBO examCceFactor = (PhdCompletionDetailsBO)session.createQuery(str).uniqueResult();
        transaction = session.beginTransaction();
        examCceFactor.setIsActive(false);
        session.update(examCceFactor);
        transaction.commit();
        session.close();
    }
    catch(Exception e)
    {
        if(transaction != null)
        {
            transaction.rollback();
        }
        log.debug("Error during deleting deleteCompletionDetails data...", e);
    }
    return true;
   }
	
	@Override
	public boolean reactivateCompletionDetails(PhdCompletionDetailsForm objForm)throws Exception {
		log.info("Entering into reactivateCompletionDetails of PhdCompletionDetailsImpl");
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from PhdCompletionDetailsBO phd where phd.id="+objForm.getId();
		   Query query=session.createQuery(quer);
		   PhdCompletionDetailsBO leave=(PhdCompletionDetailsBO)query.uniqueResult();
		   leave.setIsActive(true);
		   leave.setModifiedBy(objForm.getUserId());
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
