package com.kp.cms.actions.smartcard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm;
import com.kp.cms.handlers.smartcard.ScLostCorrectionProcessedHandler;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessedTO;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessedAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionProcessedAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initScLostCorrectionProcessed(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("start of initScLostCorrectionProcessed method in initScLostCorrectionProcessed class.");
		ScLostCorrectionProcessedForm scForm=(ScLostCorrectionProcessedForm) form;
		scForm.clear();
		log.debug("Leaving initScLostCorrectionProcessed");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScLostCorrectionProcessedSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		
		ScLostCorrectionProcessedForm scForm=(ScLostCorrectionProcessedForm) form;
		ActionErrors errors=new ActionErrors();

			try{
				List<ScLostCorrectionProcessedTO> scProcessed = ScLostCorrectionProcessedHandler.getInstance().getDetailsList(scForm);
				if(scProcessed==null || scProcessed.isEmpty() || scProcessed.size()==0){
					errors.add("error", new ActionError("knowledgepro.norecords"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED);
				}else{
					scForm.setScList(scProcessed);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS);
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addScLostCorrectionProcessed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- ScLostCorrectionProcessedAction --- addScLostCorrectionProcessed");
		ScLostCorrectionProcessedForm scForm=(ScLostCorrectionProcessedForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, scForm);
			
			List<ScLostCorrectionProcessedTO> checkedList = ScLostCorrectionProcessedHandler.getInstance().getAnySelected(scForm);
			if(checkedList==null || checkedList.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.smartcard.lostcorrection.processed.selectany.continue"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS);
			}
			
			boolean isScProcessed = ScLostCorrectionProcessedHandler.getInstance().addScLostCorrectionProcessed(scForm, checkedList);
			//If add operation is success then display the success message.
			if(isScProcessed)
			{
				if(scForm.getStatus().equalsIgnoreCase("Processed")){
				Iterator<ScLostCorrectionProcessedTO> scItr=checkedList.iterator();
				while(scItr.hasNext()){
					ScLostCorrectionProcessedTO scTO = scItr.next();
					ScLostCorrectionProcessedHandler.getInstance().sendSMSToSelected(scTO, scForm);
					ScLostCorrectionProcessedHandler.getInstance().sendEmailToSelected(scTO, scForm);
					
				}
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_PROCESSED_ADD_SUCCESS));
				}
				else if(scForm.getStatus().equalsIgnoreCase("Received")){
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_PROCESSED_RECEIVED_ADD_SUCCESS));
				}else if(scForm.getStatus().equalsIgnoreCase("Rejected")){
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_PROCESSED_RESEND_SUCCESS));
				}
				
				saveMessages(request, messages);
				List<ScLostCorrectionProcessedTO> scProcessed = ScLostCorrectionProcessedHandler.getInstance().getDetailsList(scForm);
				scForm.setScList(scProcessed);
				
				return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SC_LOST_CORRECTION_PROCESSED_ADD_FAIL));
				saveErrors(request, errors);
				}
			
		}catch (Exception e) {
				log.error("Error in adding addScLostCorrectionProcessed in ScLostCorrectionProcessed Action",e);
				String msg = super.handleApplicationException(e);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- ScLostCorrectionProcessedAction --- addScLostCorrectionProcessed");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editRemarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- ScLostCorrectionProcessedAction --- addScLostCorrectionProcessed");
		ScLostCorrectionProcessedForm scForm=(ScLostCorrectionProcessedForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, scForm);
			
			boolean isScEdited = ScLostCorrectionProcessedHandler.getInstance().editRemarks(scForm);
			//If add operation is success then display the success message.
			if(isScEdited)
			{
				saveMessages(request, messages);
				List<ScLostCorrectionProcessedTO> scProcessed = ScLostCorrectionProcessedHandler.getInstance().getDetailsList(scForm);
				scForm.setScList(scProcessed);
				
				return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS);
			}
			
		}catch (Exception e) {
				log.error("Error in adding addScLostCorrectionProcessed in ScLostCorrectionProcessed Action",e);
				String msg = super.handleApplicationException(e);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- ScLostCorrectionProcessedAction --- addScLostCorrectionProcessed");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS);
	}
	
	

}
