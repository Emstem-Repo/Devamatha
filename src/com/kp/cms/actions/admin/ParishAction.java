package com.kp.cms.actions.admin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DioceseForm;
import com.kp.cms.forms.admin.ParishForm;

import com.kp.cms.handlers.admin.DioceseHandler;
import com.kp.cms.handlers.admin.ParishHandler;

import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.ParishTo;


public class ParishAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(DioceseAction.class);
	public ActionForward initParish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		setDioceseListToRequest(request);
		getParish(form,request);
		return mapping.findForward(CMSConstants.PARISH_ENTRY);
	}
	public void setDioceseListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setSubReligionListToRequest in Action");
		List<DioceseTo> dioceseList = DioceseHandler.getInstance().getDiocese();
		request.setAttribute("dioceseList", dioceseList);
		log.debug("leaving setDioceseListToRequest in Action");
	}
	
	public ActionForward addParish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ParishForm dsForm=(ParishForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		errors = dsForm.validate(mapping, request);
		boolean result;
		if(dsForm.getDioceseId()==0 )
		{
			errors.add("error",new ActionError("knowledgepro.admin.dioceseId.entry"));
			saveErrors(request, errors);
		}
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				setDioceseListToRequest(request);
				getParish(form,request);
				saveErrors(request, errors);
				//if any space is entered in text box then, assigning as null.
				if(dsForm.getParishName().trim().isEmpty()){       
					dsForm.setParishName(null);
				}
				return mapping.findForward(CMSConstants.PARISH_ENTRY);
			}
			final boolean isSpcl=nameValidate(dsForm.getParishName().trim()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				setDioceseListToRequest(request);
				getParish(form,request);
				saveErrors(request, errors);
				//if any space is entered in text box then, assigning as null.
				if(dsForm.getParishName().trim().isEmpty()){       
					dsForm.setParishName(null);
				}
				return mapping.findForward(CMSConstants.PARISH_ENTRY);
			}
			
			
			 result=ParishHandler.getInstance().addParishhand(dsForm,"Add");
			//addSubReligion  method is using for add & edit. second param is used to identify add or edit
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.parish.exists"));
			saveErrors(request, errors);
			setDioceseListToRequest(request);
			getParish(form,request);
			return mapping.findForward(CMSConstants.PARISH_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.SUBREL_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setDioceseListToRequest(request);
			getParish(form,request);
			return mapping.findForward(CMSConstants.PARISH_ENTRY);
		} catch (Exception e) {
			log.error("error in update state page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				dsForm.setErrorMessage(msg);
				dsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				dsForm.setErrorMessage(msg);
				dsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		
		
		setDioceseListToRequest(request);
		getParish(form,request);
		
		if (result) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.parish.addsuccess", dsForm.getParishName());
			messages.add("messages", message);
			saveMessages(request, messages);
			dsForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.parish.addfailure", dsForm.getParishName()));
			saveErrors(request, errors);
		}
		
		return mapping.findForward(CMSConstants.PARISH_ENTRY);
	}
	
	public ActionForward updateParish(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

ParishForm pForm = (ParishForm) form;
ActionMessages messages = new ActionMessages();
ActionErrors errors = pForm.validate(mapping, request);
boolean isUpdated = false;
try {
if (!errors.isEmpty()) {
	setDioceseListToRequest(request);
	getParish(form,request);;
    saveErrors(request, errors);
if(pForm.getParishName().trim().isEmpty()){
	pForm.setParishName(null);
}
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.PARISH_ENTRY);
}
boolean isSpcl=nameValidate(pForm.getParishName().trim()); //validation checking for special characters
if(isSpcl)
{
errors.add("error", new ActionError("knowledgepro.admin.special"));
}
if (!errors.isEmpty()) {
	setDioceseListToRequest(request);
	getParish(form,request);
saveErrors(request, errors);
if(pForm.getParishName().trim().isEmpty()){
	pForm.setParishName(null);
}
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.PARISH_ENTRY);
}

isUpdated = ParishHandler.getInstance().addParishhand(pForm,"Edit");
//addSubReligion  method is using for add & edit. second param is used to identify add or edit
} catch (DuplicateException e1) {
errors.add("error", new ActionError("knowledgepro.admin.parish.exists"));
saveErrors(request, errors);
setDioceseListToRequest(request);
getParish(form,request);
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.PARISH_ENTRY);
} catch (ReActivateException e1) {
errors.add("error", new ActionError(CMSConstants.PARISH_EXIST_REACTIVATE));
saveErrors(request, errors);
setDioceseListToRequest(request);
getParish(form,request);
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.PARISH_ENTRY);
} catch (Exception e) {
log.error("error in update state page...", e);
if (e instanceof BusinessException) {
String msgKey = super.handleBusinessException(e);
ActionMessage message = new ActionMessage(msgKey);
messages.add("messages", message);
return mapping.findForward(CMSConstants.ERROR_PAGE);
} else if (e instanceof ApplicationException) {
String msg = super.handleApplicationException(e);
pForm.setErrorMessage(msg);
pForm.setErrorStack(e.getMessage());
return mapping.findForward(CMSConstants.ERROR_PAGE);
} else {
String msg = super.handleApplicationException(e);
pForm.setErrorMessage(msg);
pForm.setErrorStack(e.getMessage());
return mapping.findForward(CMSConstants.ERROR_PAGE);
}
}
setDioceseListToRequest(request);
getParish(form,request);
if (isUpdated) {
// success .
ActionMessage message = new ActionMessage("knowledgepro.admin.parish.updatesuccess", pForm.getParishName());
messages.add("messages", message);
saveMessages(request, messages);
pForm.reset(mapping, request);
} else {
// failed
errors.add("error", new ActionError("knowledgepro.admin.parish.updatefailure", pForm.getParishName()));
saveErrors(request, errors);
}
return mapping.findForward(CMSConstants.PARISH_ENTRY);
}
	
	public ActionForward deleteParish(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	ParishForm pForm = (ParishForm) form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = new ActionErrors();
	boolean isDeleted = false;
	try {
	if (pForm.getParishId() != 0) {
	int parishId = pForm.getParishId();
	isDeleted = ParishHandler.getInstance().deleteParish(parishId, false, pForm);
	}
	} catch (Exception e) {
	log.error("error in Delete parish Action", e);
	if (e instanceof BusinessException) {
	String msgKey = super.handleBusinessException(e);
	ActionMessage message = new ActionMessage(msgKey);
	messages.add("messages", message);
	return mapping.findForward(CMSConstants.ERROR_PAGE);
	} else if (e instanceof ApplicationException) {
	String msg = super.handleApplicationException(e);
	pForm.setErrorMessage(msg);
	pForm.setErrorStack(e.getMessage());
	return mapping.findForward(CMSConstants.ERROR_PAGE);
	} else {
	throw e;
	}
	}

	setDioceseListToRequest(request);
	getParish(form,request);
	if (isDeleted) {
	// success deleted
	ActionMessage message = new ActionMessage("knowledgepro.admin.parish.deletesuccess", pForm
		.getParishName());
	messages.add("messages", message);
	saveMessages(request, messages);
	pForm.reset(mapping, request);
	} else {
	// failure error message.
	errors.add("error", new ActionError("knowledgepro.admin.parish.deletefailure", pForm
				.getParishName()));
	saveErrors(request, errors);
	}
	return mapping.findForward(CMSConstants.PARISH_ENTRY);
	}
	
	public ActionForward activateParish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

	ParishForm pForm = (ParishForm) form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	boolean isActivated = false;
	try {
	int dioceseId = pForm.getDuplId();
	isActivated = ParishHandler.getInstance().deleteParish(dioceseId, true, pForm); 
	} catch (Exception e) {
	errors.add("error", new ActionError(CMSConstants.PARISH_ACTIVATE_FAILURE));
	saveErrors(request, errors);
	}
	setDioceseListToRequest(request);
	getParish(form,request);
	if (isActivated) {
	ActionMessage message = new ActionMessage(CMSConstants.PARISH_ACTIVATE_SUCCESS);
	messages.add("messages", message);
	saveMessages(request, messages);
	}
	log.debug("leaving activate parish in Action");
	return mapping.findForward(CMSConstants.PARISH_ENTRY);
	}
	
	public void getParish(ActionForm form, HttpServletRequest request)
	throws Exception {
		ParishForm dsForm=(ParishForm)form;
		List<ParishTo> parishList=ParishHandler.getInstance().getParishhand(dsForm);
		request.setAttribute("parishList", parishList);
		
	}
	
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher matcher = pattern.matcher(name);
        result = matcher.find();
        return result;

	}
	}
