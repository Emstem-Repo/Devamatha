package com.kp.cms.actions.hostel;

import java.util.List;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.ViewReqStatusForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.hostel.ViewRequisitionHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.hostel.VReqStatusTo;

public class ViewReqStatusAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ViewReqStatusAction.class);
	
	
	/**
	 * setting the requested data for search option
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewReqStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initViewRequisitions. of ViewReqStatusAction");
		ViewReqStatusForm viewReqStatusForm = (ViewReqStatusForm) form;
		try {
			HttpSession session = request.getSession(false);
			if(session.getAttribute("vReqStatusTo")!=null){
				session.removeAttribute("vReqStatusTo");
			}
			viewReqStatusForm.resetFields( mapping,request);
			setUserId(request, viewReqStatusForm);

		} catch (Exception e) {
			log.error("Error in initViewRequisitions in ViewReqStatusAction",e);
			String msg = super.handleApplicationException(e);
			viewReqStatusForm.setErrorMessage(msg);
			viewReqStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("leaving into initViewRequisitions. of ViewReqStatusAction");
		return mapping.findForward(CMSConstants.VIEW_REQSTATUS);

	}
	/**
	 * getting the requested data occureding to the input
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitviewReqStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitViewRequisitions. of ViewReqStatusAction");	
		ViewReqStatusForm viewReqStatusForm = (ViewReqStatusForm)form;
		
		HttpSession session = request.getSession(false);
		 ActionMessages errors = viewReqStatusForm.validate(mapping, request);
		if(session.getAttribute("vReqStatusTo")==null){
			try {
				if(errors.isEmpty()){
					List<VReqStatusTo> list = ViewRequisitionHandler.getInstance().getRequisitionStatusDetails(viewReqStatusForm,request);
					if(list==null ||list.isEmpty() ){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						viewReqStatusForm.resetFields(mapping, request);
								return mapping.findForward(CMSConstants.VIEW_REQSTATUS);
						
					}
					request.getSession().setAttribute("vReqStatusTo", list);
				}
				else{
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.VIEW_REQSTATUS);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Error in showRequisitions in ViewReqStatusAction",e);
				String msg = super.handleApplicationException(e);
				viewReqStatusForm.setErrorMessage(msg);
				viewReqStatusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.info("Leaving into submitViewRequisitions. of ViewReqStatusAction");
		return mapping.findForward(CMSConstants.VIEW_REQ_STATUS_RESULT);
	}	
	
	
	public ActionForward printReqStatusResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered printCollectinsResult input");
		request.getSession().setAttribute("vReqStatusTo", request.getSession().getAttribute("vReqStatusTo"));
		OrganizationHandler orgHandler= OrganizationHandler.getInstance();
		List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
		if(tos!=null && !tos.isEmpty())
		{
			OrganizationTO orgTO=tos.get(0);
			String s= orgTO.getOrganizationName();
			request.setAttribute("orgName", s);
		}
			return mapping.findForward(CMSConstants.PRINT_REQ_STATUS_RESULT);
	}
	
	
	
	
	
	
	
}
