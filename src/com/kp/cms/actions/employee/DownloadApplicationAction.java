package com.kp.cms.actions.employee;

import java.util.ArrayList;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.DownloadApplicationForm;
import com.kp.cms.handlers.employee.DownloadApplicationHandler;
import com.kp.cms.handlers.employee.DownloadEmployeeResumeHandler;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;

public class DownloadApplicationAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(DownloadApplicationAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDownloadApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DownloadApplicationForm downloadApplicationForm = (DownloadApplicationForm) form;
		downloadApplicationForm.resetFields();
		downloadApplicationForm.setStatus("Forwarded");
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		downloadApplicationForm.setIsCjc(isCjc);
		try{
			DownloadApplicationHandler.getInstance().getEmployeeDetailsTOForm(downloadApplicationForm);
		}catch (Exception e) {
			return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
		}
		return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmpOnlineResumeDetails(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DownloadApplicationForm downloadApplicationForm = (DownloadApplicationForm) form;
		ActionErrors errors = new ActionErrors();
		validateFields(downloadApplicationForm, errors,request);
		setUserId(request, downloadApplicationForm);
		try{
			if(errors.isEmpty()){
				downloadApplicationForm.setDownloadEmployeeResumeTOs(null);
				//DownloadApplicationHandler.getInstance().setEmployeeDetailsToForm(downloadApplicationForm);
				List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadApplicationHandler.getInstance().getEmpDetails(downloadApplicationForm);
				if(downloadEmployeeResumeTOs == null || downloadEmployeeResumeTOs.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
				}
				downloadApplicationForm.setDownloadEmployeeResumeTOs(downloadEmployeeResumeTOs);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
			}
		}catch (Exception e) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
		}
		log.info("Exit from the getEmpOnlineResumeDetails in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
	}
	/**
	 * @param downloadEmployeeResumeForm
	 * @param errors
	 * @param request
	 */
	private void validateFields( DownloadApplicationForm downloadApplicationForm, ActionErrors errors, HttpServletRequest request) {
		if(downloadApplicationForm.getStartDate() != null && !downloadApplicationForm.getStartDate().isEmpty()){
			if(downloadApplicationForm.getEndDate() == null || downloadApplicationForm.getEndDate().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.enter.endDate"));
				saveErrors(request, errors);
			}
		}
		if(downloadApplicationForm.getEndDate() != null && !downloadApplicationForm.getEndDate().isEmpty()){
			if(downloadApplicationForm.getStartDate() == null || downloadApplicationForm.getStartDate().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.enter.startDate"));
				saveErrors(request, errors);
			}
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
	public ActionForward getEmpOnlineDetailsForPrint (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DownloadApplicationForm downloadApplicationForm = (DownloadApplicationForm) form;
		setUserId(request, downloadApplicationForm);
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		session.setAttribute("PhotoBytes", null);
		try{
			DownloadApplicationHandler.getInstance().getResumeDetails(downloadApplicationForm,session,request);
			downloadApplicationForm.setPrintPage("true");
		}catch (Exception e) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
		}
		DownloadApplicationHandler.getInstance().getEmployeeDetailsTOForm(downloadApplicationForm);
		return mapping.findForward(CMSConstants.INIT_DOWNLOAD_APPLICATION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DownloadApplicationForm downloadApplicationForm = (DownloadApplicationForm) form;
		HttpSession session = request.getSession();
		try{
			List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs;
			if(downloadApplicationForm.getDownloadEmployeeResumeTOs() != null && !downloadApplicationForm.getDownloadEmployeeResumeTOs().isEmpty()){
				downloadEmployeeResumeTOs = downloadApplicationForm.getDownloadEmployeeResumeTOs();
			}else{
				downloadEmployeeResumeTOs = DownloadApplicationHandler.getInstance().getEmpDetails(downloadApplicationForm);
			}
			session.setAttribute("EmployeeResume", downloadEmployeeResumeTOs);
		}catch (Exception e) {
			// TODO: handle exception
		}
		downloadApplicationForm.setPrintPage(null);
		
		DownloadApplicationHandler.getInstance().getEmployeeDetailsTOForm(downloadApplicationForm);
		return mapping.findForward(CMSConstants.DOWNLOAD_APP_EXPORT_TO_EXCEL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printResume(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DownloadApplicationForm downloadApplicationForm = (DownloadApplicationForm) form;
		downloadApplicationForm.setPrintPage(null);
		DownloadApplicationHandler.getInstance().getEmployeeDetailsTOForm(downloadApplicationForm);
		return mapping.findForward(CMSConstants.PRINT_RESUME);
	}
}
