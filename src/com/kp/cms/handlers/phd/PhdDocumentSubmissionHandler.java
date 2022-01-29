package com.kp.cms.handlers.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdDocumentSubmissionBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionForm;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.helpers.phd.PhdDocumentSubmissionHelper;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionTO;
import com.kp.cms.to.phd.PhdEmployeeTo;
import com.kp.cms.to.phd.PhdResearchPublicationTo;
import com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions;
import com.kp.cms.transactionsimpl.phd.PhdDocumentSubmissionImpl;
import com.kp.cms.utilities.CommonUtil;

public class PhdDocumentSubmissionHandler {
	private static final Log log = LogFactory.getLog(PhdDocumentSubmissionHandler.class);
	private static volatile PhdDocumentSubmissionHandler documentSubmissionHandler = null;

	public static PhdDocumentSubmissionHandler getInstance() {
		if (documentSubmissionHandler == null) {
			documentSubmissionHandler = new PhdDocumentSubmissionHandler();
		}
		return documentSubmissionHandler;
	}
	IPhdDocumentSubmissionTransactions trancations=new PhdDocumentSubmissionImpl();

	/**
	 * @param objForm
	 * @param errors
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public List<PhdDocumentSubmissionTO> getStudentDetailsList(PhdDocumentSubmissionForm objForm, ActionErrors errors, HttpServletRequest request) throws Exception{
		List<Object[]> studentdetails=trancations.getStudentDetails(objForm,errors);
		PhdDocumentSubmissionBO submissionBo=trancations.getSubmissionDetails(objForm,errors);
		List<PhdDocumentSubmissionTO> phdSynopsisto=PhdDocumentSubmissionHelper.getInstance().setStudentdatatolist(studentdetails,objForm,submissionBo,request);
		return phdSynopsisto;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<PhdResearchPublicationTo> setResearchPublication() throws Exception{
		List<PhdResearchPublication> researchBOs=trancations.getResearchPublication();
		List<PhdResearchPublicationTo> guideTO=PhdDocumentSubmissionHelper.getInstance().convertBOsToResearchPublication(researchBOs);
		return guideTO;
		}
	/**
	 * @param objForm 
	 * @param mode 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public String getPanelNameById(int id, PhdDocumentSubmissionForm objForm, String mode) throws Exception{
		String name=trancations.getPanelNameById(id,objForm,mode);
		return name;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<PhdEmployeeTo> setGuideDetails() throws Exception {
		List<PhdEmployee> guideBOs=trancations.getGuideDetails();
		List<PhdEmployeeTo> guideTO=PhdDocumentSubmissionHelper.getInstance().convertBOsToTO(guideBOs);
		return guideTO;
		}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<PhdEmployeeTo> setCoGuideDetails()  throws Exception{
		List<PhdEmployee> guideBOs=trancations.getCoGuideDetails();
		List<PhdEmployeeTo> guideTO=PhdDocumentSubmissionHelper.getInstance().convertBOsToTO(guideBOs);
		return guideTO;
		}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<PhdDocumentSubmissionScheduleTO> getDocumentSubmissionByReg(PhdDocumentSubmissionForm objForm) throws Exception{
		List<PhdDocumentSubmissionSchedule> subStudentBo=trancations.getDocumentSubmissionByReg(objForm);
		List<PhdDocumentSubmissionScheduleTO> groupDetailsTos = PhdDocumentSubmissionHelper.getInstance().SetDocumentSubmissionByReg(subStudentBo,objForm);
		return groupDetailsTos;
}
    /**
     * @param objForm
     * @param session 
     * @param errors 
     * @return
     * @throws Exception
     */
    public boolean addPhdDocumentSubmission(PhdDocumentSubmissionForm objForm, ActionErrors errors, HttpSession session) throws Exception{
    	boolean isAdded=true;
    	List<PhdDocumentSubmissionSchedule> documentList=PhdDocumentSubmissionHelper.getInstance().documentFormToBO(objForm);
    	boolean isUpdated=trancations.addDocumentList(documentList);
    	PhdDocumentSubmissionBO synopsisBO = PhdDocumentSubmissionHelper.getInstance().convertFormToBos(objForm);
    	
    	if(synopsisBO==null || !isUpdated){
    		return isAdded=false;
    	}
        isAdded = trancations.addPhdDocumentSubmission(synopsisBO,objForm,errors,session);
        return isAdded;
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
		List<PhdDocumentSubmissionScheduleTO> documentToList = PhdDocumentSubmissionHelper.getInstance().copyBotoToguide(guideList);
		objForm.setStudentDetailsList(documentToList);
		log.debug("Handler : Leaving feePaymentSearch");
	}
	/**
	 * @param id
	 * @param userId
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deletesynopsisList(int id, String userId, String mode) throws Exception{
		 boolean isDeleted = trancations.deletesynopsisList(id, userId,mode);
		 return isDeleted;
	}
	public boolean deleteResearchPublication(PhdDocumentSubmissionForm objForm) throws Exception{
		 boolean isDeleted = trancations.deleteResearchPublication(objForm);
		 return isDeleted;
	}
	public List<PhdEmployeeTo> setInternalGuideDetails() throws Exception{
		List<PhdInternalGuideBo> guideBOs=trancations.setInternalGuideDetails();
		List<PhdEmployeeTo> guideTO=PhdDocumentSubmissionHelper.getInstance().convertinternalBOsToTO(guideBOs);
		return guideTO;
		}
	public List<PhdEmployeeTo> setInternalCoGuideDetails() throws Exception{
		List<PhdInternalGuideBo> guideBOs=trancations.setInternalGuideDetails();
		List<PhdEmployeeTo> guideTO=PhdDocumentSubmissionHelper.getInstance().convertinternalBOsToTO(guideBOs);
		return guideTO;
		}
	public String getPanelNameEmployeeById(int id,PhdDocumentSubmissionForm objForm) throws Exception{
		String name=trancations.getPanelNameEmployeeById(id,objForm);
		return name;
	}
	public void validateNoofGuides(PhdDocumentSubmissionForm objForm,ActionErrors errors) throws Exception{
		trancations.validateNoofGuides(objForm,errors);
		
	}
	public boolean deleteConference(PhdDocumentSubmissionForm objForm) throws Exception{
		 boolean isDeleted = trancations.deleteConference(objForm);
		 return isDeleted;
	}
}
