package com.kp.cms.actions.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.forms.reports.SecondLanguageAbstractForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.SecondLanguageAbstractHandler;
import com.kp.cms.handlers.reports.SecondLanguageHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.SecLanguageAbstractTO;

@SuppressWarnings("deprecation")
public class SecondLanguageAbstractAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SecondLanguageAbstractAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSecLanguageAbstract(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecondLanguageAbstractForm abstractForm = (SecondLanguageAbstractForm) form;
		ActionMessages messages = new ActionMessages();
		abstractForm.reset(mapping, request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("abstractList");
		try {
			setProgramTypeListToRequest(request);
			setSecondLanguageListToRequest(request);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			abstractForm.setErrorMessage(msg);
			abstractForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SEC_LAN_ABSTRACT);
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecondLanguageAbstractForm abstractForm = (SecondLanguageAbstractForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = abstractForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		if(session.getAttribute("abstractList") == null){
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					setSecondLanguageListToRequest(request);
					return mapping.findForward(CMSConstants.INIT_SEC_LAN_ABSTRACT);
				}
				
				List<SecLanguageAbstractTO> abstractList = SecondLanguageAbstractHandler.getInstance().getStudents(abstractForm);
				session.setAttribute("abstractList", abstractList);
			} catch (Exception e) {
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					abstractForm.setErrorMessage(msg);
					abstractForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}		
			abstractForm.reset(mapping, request);
		}
		return mapping.findForward(CMSConstants.SHOW_SEC_LAN_ABSTRACT);
	
	}

	
	/**
	 * 
	 * @param request
	 *            This method sets the program type list to Request useful in
	 *            populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.debug("leaving setProgramTypeListToRequest");
	}
	
	/**
	 * setting second language to request
	 * @param request
	 * @throws Exception
	 */
	public void setSecondLanguageListToRequest(HttpServletRequest request)	throws Exception {
		Map<String,String> secondLanguageMap = SecondLanguageHandler.getInstance().getAllSecondLanguages();
		if(secondLanguageMap.get("All")!= null){
			secondLanguageMap.remove("All");
		}
		request.setAttribute("secondLanguageMap", secondLanguageMap);		
	}
}
