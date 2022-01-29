package com.kp.cms.actions.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.ListofSubjectGroupsForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.ListofSubjectGroupHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupTO;

@SuppressWarnings("deprecation")
public class ListofSubjectGroups extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(ListofSubjectGroups.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initListofSubjectGroup(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered initListofSubjectGroup");
		ListofSubjectGroupsForm suGroupsForm = (ListofSubjectGroupsForm)form;
		setRequiredDataToForm(request);
		suGroupsForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute("subGroupList");
		log.info("Exit initListofSubjectGroup");
		return mapping.findForward(CMSConstants.LIST_OF_SUBJECT_GROUPS_REPORT);
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
	public ActionForward searchSubjectGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("inside searchSubjectGroup");
		ListofSubjectGroupsForm subForm = (ListofSubjectGroupsForm) form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("subGroupList")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = subForm.validate(mapping, request);
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					setRequiredDataToForm(request);
					return mapping.findForward(CMSConstants.LIST_OF_SUBJECT_GROUPS_REPORT);
				}		
				List<SubjectGroupTO> subGroupTOList  = ListofSubjectGroupHandler.getInstance().getSubjectGroupDetails(subForm);
				session.setAttribute("subGroupList",subGroupTOList);
			}catch (BusinessException businessException) {
				log.info("Exception submitPerformaReport");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				subForm.setErrorMessage(msg);
				subForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.debug("exit searchSubjectGroup");
		return mapping.findForward(CMSConstants.SUBMIT_SUBJECT_GROUP_REPORT);
	}	
	/**
	 * This method sets the required data to the form
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(HttpServletRequest request) throws Exception{
		log.info("Entered setRequiredDataToForm");	
	    
		//setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);

		log.info("Exit setRequiredDataToForm");	
	}	
}
