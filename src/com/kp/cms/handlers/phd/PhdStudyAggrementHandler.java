package com.kp.cms.handlers.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.helpers.phd.PhdStudyAggrementHelper;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.to.phd.PhdStudyAggrementTO;
import com.kp.cms.transactions.phd.IPhdStudyAggrementTransactions;
import com.kp.cms.transactionsimpl.phd.PhdStudyAggrementImpl;
import com.kp.cms.utilities.CommonUtil;

public class PhdStudyAggrementHandler {
	private static final Log log = LogFactory.getLog(PhdStudyAggrementHandler.class);
	private static volatile PhdStudyAggrementHandler examCceFactorHandler = null;

	public static PhdStudyAggrementHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PhdStudyAggrementHandler();
		}
		return examCceFactorHandler;
	}
  	IPhdStudyAggrementTransactions trancations=new PhdStudyAggrementImpl();
   /**
 * @param objForm
 * @param errors
 * @param session 
 * @return
 */	public List<PhdGuideDetailsTO> setGuideDetails() throws Exception {
		List<PhdGuideDetailsBO> guideBOs=trancations.getGuideDetails();
		List<PhdGuideDetailsTO> guideTO=PhdStudyAggrementHelper.getInstance().convertBOsToTO(guideBOs);
		return guideTO;
		}
      /**
	 * @param objForm
	 * @param errors
	 * @throws Exception
	 */
	public List<PhdStudyAggrementTO> getStudentDetails(PhdStudyAggrementForm objForm, ActionErrors errors) throws Exception {
		List<Object[]> studentdetails=trancations.getStudentDetails(objForm,errors);
		List<PhdStudyAggrementTO> phdSynopsisto=PhdStudyAggrementHelper.getInstance().setdatatolist(studentdetails,objForm);
		return phdSynopsisto;
	}
    /**
     * @param objForm
     * @param session 
     * @param errors 
     * @return
     * @throws Exception
     */
    public boolean addStudyAggrement(PhdStudyAggrementForm objForm, ActionErrors errors, HttpSession session) throws Exception{
    	PhdStudyAggrementBO synopsisBO = PhdStudyAggrementHelper.getInstance().convertFormToBos(objForm);
    	boolean isAdded=true;
    	if(synopsisBO==null){
    		return isAdded=false;
    	}
        isAdded = trancations.addStudyAggrement(synopsisBO,objForm,errors,session);
        return isAdded;
    }

	 /**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteStudyAggrement(PhdStudyAggrementForm objForm)
      throws Exception{
      boolean isDeleted = trancations.deleteStudyAggrement(objForm.getId());
      return isDeleted;
  }

	/**
	 * @param objForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reactivateStudyAggrement(PhdStudyAggrementForm objForm,String userId) throws Exception{
	     return trancations.reactivateStudyAggrement(objForm);
	}
	/**
	 * @param objForm
	 * @param search
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void guideDetailsSearch(PhdDocumentSubmissionScheduleForm objForm) throws DataNotFoundException,Exception {
		log.debug("Handler : Entering feePaymentSearch");			  
		Date startDate = CommonUtil.ConvertStringToSQLDate(objForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(objForm.getEndDate());
		List<Object[]> guideList = trancations.guideDetailsSearch(startDate,endDate);
		List<PhdDocumentSubmissionScheduleTO> documentToList = PhdStudyAggrementHelper.getInstance().copyBotoToguide(guideList);
		objForm.setStudentDetailsList(documentToList);
		log.debug("Handler : Leaving feePaymentSearch");
	}
	public void setUpdateGeneratedDate(PhdDocumentSubmissionScheduleForm objForm) throws Exception{
		trancations.setUpdateGeneratedDate(objForm);
		
	}
}
