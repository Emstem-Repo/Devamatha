package com.kp.cms.actions.admin;

import java.util.ArrayList;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.MasterReportForm;
import com.kp.cms.handlers.admin.MasterReportHandler;


public class MasterReportAction   extends BaseDispatchAction {
	
	private static Log log = LogFactory.getLog(MasterReportAction.class);

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMasterReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initMasterReport..");
		MasterReportForm masterReportForm = (MasterReportForm)form;
//		setRequiredDataToForm(attendanceSummaryReportForm,request);
		masterReportForm.resetFields();
		HttpSession session = request.getSession();
		session.removeAttribute("masterReport");
		log.info("Exit initMasterReport..");
		return mapping.findForward("initMasterReport");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitMasterReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitMasterReport..");	
		MasterReportForm masterReportForm = (MasterReportForm)form;
		
		 ActionMessages errors = masterReportForm.validate(mapping, request);

		if (errors.isEmpty()) {	
			try {
			
			HttpSession session = request.getSession();
			 List masterSearch = MasterReportHandler.getInstance().getMasterResult(masterReportForm,request);
			if(masterSearch !=null){			
			session.setAttribute("masterReport",masterSearch );
			}

			} catch (Exception e) {
				log.error("error occured in submitMasterReport of MasterReportAction class",e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					masterReportForm.setErrorMessage(msg);
					masterReportForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward("initMasterReport");
		}
	

		
		log.info("Exit submitMasterReport..");
		return mapping.findForward("submitMasterReport");
	}

}
