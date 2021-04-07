package com.kp.cms.transactionsimpl.phd;

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

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.PhdGuideDetailsForm;
import com.kp.cms.transactions.phd.IPhdGuideDetailsTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class PhdGuideDetailsImpl implements IPhdGuideDetailsTransactions {
	private static Log log = LogFactory.getLog(PhdGuideDetailsImpl.class);
	public static volatile PhdGuideDetailsImpl examCceFactorImpl = null;
	
	public static PhdGuideDetailsImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdGuideDetailsImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	@Override
	public boolean addSynopsisDefense(PhdGuideDetailsBO guideBO,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(guideBO);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(guideBO);
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
	public PhdGuideDetailsBO getGuideDetailsById(int id) throws Exception  {
        Session session = null;
        PhdGuideDetailsBO guideDetails = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from PhdGuideDetailsBO a where a.id=")).append(id).toString()+" and a.isActive=1";
            Query query = session.createQuery(str);
            guideDetails = (PhdGuideDetailsBO)query.uniqueResult();
            session.flush();
        }
        catch(Exception e)
        {
            log.error("Error during getting getFeedBackQuestionById by id...", e);
            session.flush();
            session.close();
        }
        return guideDetails;
    }
    /**
     * @param id
     * @return
     * @throws Exception
     */
	public boolean deleteGuideDetails(int id) throws Exception {
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from PhdGuideDetailsBO a where a.id=")).append(id).toString();
        PhdGuideDetailsBO guide = (PhdGuideDetailsBO)session.createQuery(str).uniqueResult();
        transaction = session.beginTransaction();
        guide.setIsActive(false);
        session.update(guide);
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
	public List<PhdGuideDetailsBO> getPhdGuideDetails(PhdGuideDetailsForm objForm) throws Exception {
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" select phd from PhdGuideDetailsBO phd"
					                       +" where phd.isActive=1");
					                      
			List<PhdGuideDetailsBO> list=query.list();
			log.info("end of getPhdSynopsisdefence in PhdSynopsisDefenseImpl class.");
		    return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean duplicateCheck(PhdGuideDetailsForm objForm,ActionErrors errors, HttpSession sessions) {
		Session session=null;
		boolean flag=false;
		PhdGuideDetailsBO guideDetails;
		PhdGuideDetailsBO guidename;
		try{
			session=HibernateUtil.getSession();
			String quer="select guideDetails from PhdGuideDetailsBO guideDetails "+
			" where guideDetails.empanelmentNo=:empanelmentNo";
			Query query=session.createQuery(quer);
			query.setString("empanelmentNo", objForm.getEmpanelmentNo());
			guideDetails=(PhdGuideDetailsBO)query.uniqueResult();
			
			String str="select guideDetails from PhdGuideDetailsBO guideDetails "+
			" where guideDetails.name=:name ";
			Query queryy=session.createQuery(str);
			queryy.setString("name", objForm.getName());
			guidename=(PhdGuideDetailsBO)queryy.uniqueResult();
			
			if(guideDetails!=null && !guideDetails.toString().isEmpty()){
				if(objForm.getId()!=0){
			      if(guideDetails.getId()==objForm.getId()){
			    	  if(!guideDetails.getIsActive()){
						     flag=true;
						     throw new ReActivateException(guideDetails.getId());
			    	  }else{
			    		  flag=false;
			    	  }
				    
			      }else if(guideDetails.getIsActive()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.phd.guideemno.nameexit"));
			       }
			      else{
					   flag=true;
					   objForm.setId(guideDetails.getId());
					   throw new ReActivateException(guideDetails.getId());
				   }
				}else if(guideDetails.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.phd.guideemno.nameexit"));
				}
				else{
					  flag=true;
					  guideDetails.setId(guideDetails.getId());
					  objForm.setId(guideDetails.getId());
					  throw new ReActivateException(guideDetails.getId());
				 } 
			}else if(guidename!=null && !guidename.toString().isEmpty()){
				if(objForm.getId()!=0){
				      if(guidename.getId()==objForm.getId()){
				    	  if(!guidename.getIsActive()){
							     flag=true;
							     throw new ReActivateException(guidename.getId());
				    	  }else{
				    		  flag=false;
				    	  }
					    
				      }else if(guidename.getIsActive()){
					     flag=true;
					     errors.add("error", new ActionError("knowledgepro.phd.guidedetails.nameexit"));
				       }
				      else{
						   flag=true;
						   objForm.setId(guidename.getId());
						   throw new ReActivateException(guidename.getId());
					   }
					}else if(guidename.getIsActive()){
						flag=true;
						errors.add("error", new ActionError("knowledgepro.phd.guidedetails.nameexit"));
					}
					else{
						  flag=true;
						  objForm.setId(guidename.getId());
						  throw new ReActivateException(guidename.getId());
					 } 
				}else 
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.PHD_GUIDE_DETAILS_REACTIVATE));
				//saveErrors(request, errors);
				sessions.setAttribute("ReactivateId", objForm.getId());
			}
		}
		return flag;
	}
	@Override
	public boolean reactivateSynopsisDefense(PhdGuideDetailsForm objForm)throws Exception {
		log.info("Entering into ValuatorChargesImpl of reactivateDocExamType");
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from PhdGuideDetailsBO subcategory where subcategory.id="+objForm.getId();
		   Query query=session.createQuery(quer);
		   PhdGuideDetailsBO leave=(PhdGuideDetailsBO)query.uniqueResult();
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
