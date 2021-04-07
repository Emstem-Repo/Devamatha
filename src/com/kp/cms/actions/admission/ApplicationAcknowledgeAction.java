package com.kp.cms.actions.admission;

import java.util.HashMap;
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
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.ApplicationAcknowledgeForm;
import com.kp.cms.handlers.admission.ApplicationAcknowledgeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.admission.ApplicationAcknowledgeHelper;
import com.kp.cms.to.admission.ApplnAcknowledgementTo;
import com.kp.cms.transactions.admission.IApplicationAcknowledgeTxn;
import com.kp.cms.transactionsimpl.admission.ApplicationAcknowledgeTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class ApplicationAcknowledgeAction extends BaseDispatchAction{
	private static final Log log = LogFactory
	.getLog(ApplicationAcknowledgeAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAppAcknowledgement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ApplicationAcknowledgeForm appnAcknowledgementForm = (ApplicationAcknowledgeForm)form;
		appnAcknowledgementForm.reset();
		appnAcknowledgementForm.setPrintReceipt("false");
		appnAcknowledgementForm.setSlipRequred("true");
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Application_Acknowledgement");
		
		appnAcknowledgementForm.setApplnTo(null);
//		appnAcknowledgementForm.setCoursesMap(new HashMap<Integer, String>());
		return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveApplnAcknowledgement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ApplicationAcknowledgeForm appnAcknowledgementForm = (ApplicationAcknowledgeForm)form;
		setUserId(request, appnAcknowledgementForm);
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		ActionErrors errors = appnAcknowledgementForm.validate(mapping, request);
		/** validating tracking no reqd in case of no slip generation */
		if(appnAcknowledgementForm.getSlipRequred()!=null && !appnAcknowledgementForm.getSlipRequred().isEmpty() && appnAcknowledgementForm.getSlipRequred().equalsIgnoreCase("false")){
			if(appnAcknowledgementForm.getTrackingNo()==null || appnAcknowledgementForm.getTrackingNo().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.acknowledge.tracking.no.reqd"));
			}
		}
		setUserId(request, appnAcknowledgementForm);
		if (errors.isEmpty()) {
		   try{
			   IApplicationAcknowledgeTxn txn= new ApplicationAcknowledgeTxnImpl();
		   	
			   boolean isAdded = false;
			   		isAdded = ApplicationAcknowledgeHandler.getInstance().saveApplnAcknowledge(appnAcknowledgementForm,request,errors);
				    if(isAdded) {
				    	AdmAppln admAppln=txn.getAdmApplnId(appnAcknowledgementForm);
				    	if(admAppln!=null){
				   		ApplicationAcknowledgeHelper.getInstance().convertAdmApplnToForm(admAppln,appnAcknowledgementForm);
					   		if(appnAcknowledgementForm.getMode().equalsIgnoreCase("save") && appnAcknowledgementForm.getInterviewSelectionDate()!=null && appnAcknowledgementForm.getApplnMode().equalsIgnoreCase("Online")){
					    		ApplicationAcknowledgeHandler.getInstance().addUploadInterviewSelectedData(appnAcknowledgementForm, request);
					    	}
				    	}
						//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
				    	if(appnAcknowledgementForm.getApplnTo()!=null){
				    		messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.admission.acknowledge.update"));
				    	}else{
				    		messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.admission.acknowledge.addsuccess"));
				    	}
						saveMessages(request, messages);
						session.setAttribute("applnTO", "");
					} else {
						errors.add("error", new ActionError( "knowledgepro.admission.acknowledge.addfailure"));
						addErrors(request, errors);
						appnAcknowledgementForm.reset();
						return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
					}
				    if(appnAcknowledgementForm.getFlag().equals("false")){
				    	appnAcknowledgementForm.setPrintReceipt("false");
				    	appnAcknowledgementForm.reset();
						}
					else{
						appnAcknowledgementForm.setPrintReceipt("true");
						session.setAttribute("savedAppNo", appnAcknowledgementForm.getAppNo());
					}
				    appnAcknowledgementForm.setName("");  
		   }catch(DuplicateException duplicate){
			   log.error("Duplicate Application Acknowledgement Entry", duplicate);
			   addErrors(request, errors);
			   return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
		   }
		   catch(BusinessException businessException){
			   log.error("BusinessException in Application Acknowledgement Entry", businessException);
			   messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.acknowledge.addsuccess"));
			   errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.acknowledge.no.interview.defined"));
			   addErrors(request, errors);
			   saveMessages(request, messages);
			   return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
		   }
		   catch(Exception exception){
			   log.error("Error occured in saveApplnAcknowledgement Action", exception);
				String msg = super.handleApplicationException(exception);
				appnAcknowledgementForm.setErrorMessage(msg);
				appnAcknowledgementForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		   }
	    }else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
		}
		appnAcknowledgementForm.reset();
		return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
	}
	public ActionForward printReceiptAfterSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering dispalyReceipt of ApplicationAcknowledgeAction ");
		 	ApplicationAcknowledgeForm appnAcknowledgementForm = (ApplicationAcknowledgeForm)form;
		 	try {
		 		HttpSession session= request.getSession(false);
		 		String appNo = session.getAttribute("savedAppNo").toString();
		 		appnAcknowledgementForm.setAppNo(appNo);
		 		ApplicationAcknowledgeHandler.getInstance().setLogoToForm(appnAcknowledgementForm);
		 		session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, appnAcknowledgementForm.getLogo());
		 		session.setAttribute("savedAppNo", "");
		 		appnAcknowledgementForm.setPrintReceipt("false");
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				appnAcknowledgementForm.setErrorMessage(msg);
				appnAcknowledgementForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Exiting dispalyReceipt of ApplicationAcknowledgeAction ");
		 	return mapping.findForward(CMSConstants.PRINT_SLIP);
		 	
	}	
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ApplicationAcknowledgeForm appnAcknowledgementForm = (ApplicationAcknowledgeForm)form;
		appnAcknowledgementForm.resetFields();
		try{
			if(appnAcknowledgementForm.getAppNo()!=null && !appnAcknowledgementForm.getAppNo().isEmpty()){
				ApplnAcknowledgementTo applnTO = ApplicationAcknowledgeHandler.getInstance().getDetails(appnAcknowledgementForm);
				if(applnTO!=null){
				     appnAcknowledgementForm.setApplnTo(applnTO);
				     if(applnTO.getName()!=null)
				         appnAcknowledgementForm.setName(applnTO.getName());
				     if(applnTO.getReceivedThrough()!=null)
				    	 appnAcknowledgementForm.setReceivedThrough(applnTO.getReceivedThrough());
				     if(applnTO.getRemarks()!=null)
				    	 appnAcknowledgementForm.setRemarks(applnTO.getRemarks());
				     if(applnTO.getReceivedDate()!=null)
				    	 appnAcknowledgementForm.setReceivedDate(applnTO.getReceivedDate());
				     if(applnTO.getCourseId()!=null)
				     appnAcknowledgementForm.setCourseId(applnTO.getCourseId());
				     if(applnTO.getDob()!=null)
				     appnAcknowledgementForm.setDob(applnTO.getDob());
				     if(applnTO.getTrackingNo()!=null)
				     appnAcknowledgementForm.setTrackingNo(applnTO.getTrackingNo());
				     if(applnTO.getMobileNo()!=null)
				     appnAcknowledgementForm.setMobileNo(applnTO.getMobileNo());
				}else{
					appnAcknowledgementForm.setApplnTo(null);
					if(!appnAcknowledgementForm.isAvailability()){
						appnAcknowledgementForm.setName("");
						appnAcknowledgementForm.setMobileNo("");
						appnAcknowledgementForm.setCourseId(null);
						appnAcknowledgementForm.setDob("");
					}
					appnAcknowledgementForm.setRemarks("");
					appnAcknowledgementForm.setReceivedThrough("");
				}
				Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByApplnNoYear(appnAcknowledgementForm.getAppNo(),appnAcknowledgementForm.getYear());
				appnAcknowledgementForm.setCoursesMap(courseMap);
			}else{
				appnAcknowledgementForm.setApplnTo(null);
			}
		}catch(Exception exp){
			String msg = super.handleApplicationException(exp);
			appnAcknowledgementForm.setErrorMessage(msg);
			appnAcknowledgementForm.setErrorStack(exp.getMessage());
		}
		appnAcknowledgementForm.setSetFocus(true);
		appnAcknowledgementForm.setIsPhotoRequired(true);
		return mapping.findForward(CMSConstants.INIT_APPLN_ACKNOWLEDGEMENT);
	}
}
