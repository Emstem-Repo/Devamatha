package com.kp.cms.actions.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admin.ConvocationSessionForm;
import com.kp.cms.handlers.admin.ConvocationSessionHandler;
import com.kp.cms.to.admin.ConvocationSessionTo;

public class ConvocationSessionAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ConvocationSessionAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initConvocationSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConvocationSessionForm convocationSessionForm = (ConvocationSessionForm)form;
		try{
			setCourseMapToForm(convocationSessionForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			convocationSessionForm.setErrorMessage(msg);
			convocationSessionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_Session_ENTRY);
	}
	/**
	 * @param convocationSessionForm
	 * @throws Exception
	 */
	private void setCourseMapToForm(ConvocationSessionForm convocationSessionForm) throws Exception{
		Map<Integer, String> courseMap = ConvocationSessionHandler.getInstance().getCourseMap();
		convocationSessionForm.setCourseMap(courseMap);
		List<ConvocationSessionTo> convocationSessionList = ConvocationSessionHandler.getInstance().getConvocationSessionList();
		convocationSessionForm.setConvocationDetails(convocationSessionList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSessionDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ConvocationSessionForm convocationSessionForm = (ConvocationSessionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = convocationSessionForm.validate(mapping, request);
		setUserId(request, convocationSessionForm);
		try{
			validateForm(errors,convocationSessionForm);
			if(errors != null && errors.isEmpty()) {
				boolean isDuplicate = ConvocationSessionHandler.getInstance().checkDuplicate(convocationSessionForm);
				if(!isDuplicate){
					boolean isAdded = ConvocationSessionHandler.getInstance().addSessionDetails(convocationSessionForm);
					if (isAdded) {
						ActionMessage message = new ActionError( "knowledgepro.admin.convocation.session.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						convocationSessionForm.reset(mapping, request);
						setCourseMapToForm(convocationSessionForm);
					} else {
						convocationSessionForm.reset(mapping, request);
						errors .add( "error", new ActionError( "knowledgepro.admin.convocation.session.failure"));
						saveErrors(request, errors);
					}
				}else{
					if(convocationSessionForm.getErrorMessage() != null && !convocationSessionForm.getErrorMessage().isEmpty()){
						errors .add( "error", new ActionError("knowledgepro.admin.convocation.session.already.exist.courses",convocationSessionForm.getErrorMessage()));
						saveErrors(request, errors);
					}else{
						errors .add( "error", new ActionError("knowledgepro.admin.convocation.session.already.exist"));
						saveErrors(request, errors);
					}
				}
			}else{
				saveErrors(request, errors);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			convocationSessionForm.setErrorMessage(msg);
			convocationSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_Session_ENTRY);
	}
	/**
	 * @param errors
	 * @param convocationSessionForm
	 */
	private void validateForm(ActionErrors errors,
			ConvocationSessionForm convocationSessionForm) throws Exception{
		if(convocationSessionForm.getDate() == null || convocationSessionForm.getDate().isEmpty()){
			errors .add( "error", new ActionError( "knowledgepro.admin.convocation.session.date"));
		}
		if(convocationSessionForm.getAmOrpm() == null || convocationSessionForm.getAmOrpm().isEmpty()){
			errors .add( "error", new ActionError( "knowledgepro.admin.convocation.session.amOrPm"));
		}
		if(convocationSessionForm.getCourseIds() == null || convocationSessionForm.getCourseIds().length==0){
			errors .add( "error", new ActionError( "knowledgepro.admin.convocation.session.courses"));
		}
		if(convocationSessionForm.getPassAmount() == null || convocationSessionForm.getPassAmount().isEmpty()){
			errors .add( "error", new ActionError( "knowledgepro.admin.convocation.session.guest.amount"));
		}
		if(convocationSessionForm.getMaxGuest() == null || convocationSessionForm.getMaxGuest().isEmpty()){
			errors .add( "error", new ActionError( "knowledgepro.admin.convocation.session.max.guest"));
		}
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConvocationSessionForm convocationSessionForm = (ConvocationSessionForm)form;
		try{
			ConvocationSessionHandler.getInstance().setEditDetailsToForm(convocationSessionForm);
			request.setAttribute("honoursCourse", "edit");
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			convocationSessionForm.setErrorMessage(msg);
			convocationSessionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_Session_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConvocationSessionForm convocationSessionForm = (ConvocationSessionForm)form;
		ActionMessages messages = new ActionMessages();
		//ActionErrors errors = convocationSessionForm.validate(mapping, request);
		setUserId(request, convocationSessionForm);
		try{
			boolean delete = ConvocationSessionHandler.getInstance().deleteConvocationSession(convocationSessionForm);
			if (delete) {
				ActionMessage message = new ActionError("knowledgepro.admin.convocation.session.delete");
				messages.add("messages", message);
				saveMessages(request, messages);
				convocationSessionForm.reset(mapping, request);
				setCourseMapToForm(convocationSessionForm);
			} 
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			convocationSessionForm.setErrorMessage(msg);
			convocationSessionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_Session_ENTRY);
	}
}
