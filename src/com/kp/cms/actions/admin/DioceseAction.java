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
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.handlers.admin.DioceseHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.ReligionSectionTO;

public class DioceseAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(DioceseAction.class);
	public ActionForward initDiocese(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		setSubReligionListToRequest(request);
		getDiocese(form,request);
		return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
	}
	
	public ActionForward addDiocese(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		DioceseForm dsForm=(DioceseForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		errors = dsForm.validate(mapping, request);
		boolean result = false;
		try {
			if (!errors.isEmpty()) {
				setSubReligionListToRequest(request);
				getDiocese(form,request);
				saveErrors(request, errors);
				//if any space is entered in text box then, assigning as null.
				if(dsForm.getDioceseName().trim().isEmpty()){       
					dsForm.setDioceseName(null);
				}
				return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
			}
			final boolean isSpcl=nameValidate(dsForm.getDioceseName().trim()); //validation checking for special characters
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				setSubReligionListToRequest(request);
				getDiocese(form,request);
				saveErrors(request, errors);
				//if any space is entered in text box then, assigning as null.
				if(dsForm.getDioceseName().trim().isEmpty()){       
					dsForm.setDioceseName(null);
				}
				return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
			}
			
			
		 result=DioceseHandler.getInstance().addDiocesehand(dsForm,"Add");
			//addSubReligion  method is using for add & edit. second param is used to identify add or edit
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.diocese.exists"));
			saveErrors(request, errors);
			setSubReligionListToRequest(request);
			getDiocese(form,request);
			return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.SUBREL_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setSubReligionListToRequest(request);
			getDiocese(form,request);
			return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
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
		
		
		setSubReligionListToRequest(request);
		getDiocese(form,request);
		
		if (result) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.diocese.addsuccess", dsForm.getDioceseName());
			messages.add("messages", message);
			saveMessages(request, messages);
			dsForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.diocese.addfailure", dsForm.getDioceseName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
	}
	
	
public ActionForward updateDiocese(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

DioceseForm dsForm = (DioceseForm) form;
ActionMessages messages = new ActionMessages();
ActionErrors errors = dsForm.validate(mapping, request);
boolean isUpdated = false;
try {
if (!errors.isEmpty()) {
	setSubReligionListToRequest(request);
	getDiocese(form,request);
    saveErrors(request, errors);
if(dsForm.getDioceseName().trim().isEmpty()){
	dsForm.setDioceseName(null);
}
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
}
boolean isSpcl=nameValidate(dsForm.getDioceseName().trim()); //validation checking for special characters
if(isSpcl)
{
errors.add("error", new ActionError("knowledgepro.admin.special"));
}
if (!errors.isEmpty()) {
	setSubReligionListToRequest(request);
	getDiocese(form,request);
saveErrors(request, errors);
if(dsForm.getDioceseName().trim().isEmpty()){
	dsForm.setDioceseName(null);
}
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
}

isUpdated = DioceseHandler.getInstance().addDiocesehand(dsForm,"Edit");
//addSubReligion  method is using for add & edit. second param is used to identify add or edit
} catch (DuplicateException e1) {
errors.add("error", new ActionError("knowledgepro.admin.diocese.exists"));
saveErrors(request, errors);
setSubReligionListToRequest(request);
getDiocese(form,request);
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
} catch (ReActivateException e1) {
errors.add("error", new ActionError(CMSConstants.DIOCESE_EXIST_REACTIVATE));
saveErrors(request, errors);
setSubReligionListToRequest(request);
getDiocese(form,request);
request.setAttribute("subRelOperation", "edit");
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
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
setSubReligionListToRequest(request);
getDiocese(form,request);
if (isUpdated) {
// success .
ActionMessage message = new ActionMessage("knowledgepro.admin.diocese.updatesuccess", dsForm.getDioceseName());
messages.add("messages", message);
saveMessages(request, messages);
dsForm.reset(mapping, request);
} else {
// failed
errors.add("error", new ActionError("knowledgepro.admin.diocese.updatefailure", dsForm.getDioceseName()));
saveErrors(request, errors);
}
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
}
	

public ActionForward deleteDiocese(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

DioceseForm dsForm = (DioceseForm) form;
ActionMessages messages = new ActionMessages();
ActionErrors errors = new ActionErrors();
boolean isDeleted = false;
try {
if (dsForm.getDioceseId() != 0) {
int dioceseId = dsForm.getDioceseId();
isDeleted = DioceseHandler.getInstance().deleteDiocese(dioceseId, false, dsForm);
}
} catch (Exception e) {
log.error("error in Delete Diocese Action", e);
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
throw e;
}
}

setSubReligionListToRequest(request);
getDiocese(form,request);
if (isDeleted) {
// success deleted
ActionMessage message = new ActionMessage("knowledgepro.admin.diocese.deletesuccess", dsForm
	.getDioceseName());
messages.add("messages", message);
saveMessages(request, messages);
dsForm.reset(mapping, request);
} else {
// failure error message.
errors.add("error", new ActionError("knowledgepro.admin.diocese.delete failure", dsForm
			.getDioceseName()));
saveErrors(request, errors);
}
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
}

public ActionForward activateDiocese(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

DioceseForm dsForm = (DioceseForm) form;
ActionErrors errors = new ActionErrors();
ActionMessages messages = new ActionMessages();
boolean isActivated = false;
try {
int dioceseId = dsForm.getDuplId();
isActivated = DioceseHandler.getInstance().deleteDiocese(dioceseId, true, dsForm); 
} catch (Exception e) {
errors.add("error", new ActionError(CMSConstants.DIOCESE_ACTIVATE_FAILURE));
saveErrors(request, errors);
}
setSubReligionListToRequest(request);
getDiocese(form,request);
if (isActivated) {
ActionMessage message = new ActionMessage(CMSConstants.DIOCESE_ACTIVATE_SUCCESS);
messages.add("messages", message);
saveMessages(request, messages);
}
log.debug("leaving activate diocese in Action");
return mapping.findForward(CMSConstants.DIOCESE_ENTRY);
}

	public void getDiocese(ActionForm form, HttpServletRequest request)
	throws Exception {
		DioceseForm dsForm=(DioceseForm)form;
		List<DioceseTo> dioceseList=DioceseHandler.getInstance().getDiocesehand(dsForm);
		request.setAttribute("dioceseList", dioceseList);
		
	}
	public void setSubReligionListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setSubReligionListToRequest in Action");
		List<ReligionSectionTO> subReligionList = SubReligionHandler.getInstance().getSubReligion();
		request.setAttribute("subReligionList", subReligionList);
		log.debug("leaving setSubReligionListToRequest in Action");
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
