package com.kp.cms.actions.employee;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;
import com.kp.cms.handlers.employee.DownloadEmployeeResumeHandler;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;

public class DownloadEmployeeResumeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(DownloadEmployeeResumeAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDownloadEmployeeResume(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		downloadEmployeeResumeForm.resetFields();
		boolean isCjc= CMSConstants.LINK_FOR_CJC;
		downloadEmployeeResumeForm.setIsCjc(isCjc);
		try{
			DownloadEmployeeResumeHandler.getInstance().setEmployeeDetailsToForm(downloadEmployeeResumeForm);
		}catch (Exception e) {
			return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
		}
		log.info("Exit from the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayEmployeeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		ActionErrors errors = new ActionErrors();
		validateFields(downloadEmployeeResumeForm, errors,request);
		try{
			if(errors.isEmpty()){
				downloadEmployeeResumeForm.setDownloadEmployeeResumeTOs(null);
				DownloadEmployeeResumeHandler.getInstance().setEmployeeDetailsToForm(downloadEmployeeResumeForm);
				List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadEmployeeResumeHandler.getInstance().getEmpDetails(downloadEmployeeResumeForm);
				if(downloadEmployeeResumeTOs == null || downloadEmployeeResumeTOs.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
				}
				downloadEmployeeResumeForm.setDownloadEmployeeResumeTOs(downloadEmployeeResumeTOs);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
			}
		}catch (Exception e) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
		}
		log.info("Exit from the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
	}
	
	/**
	 * @param downloadEmployeeResumeForm
	 * @param errors
	 * @param request 
	 */
	private void validateFields(
			DownloadEmployeeResumeForm downloadEmployeeResumeForm, ActionErrors errors, HttpServletRequest request) {
		if(downloadEmployeeResumeForm.getStartDate() != null && !downloadEmployeeResumeForm.getStartDate().isEmpty()){
			if(downloadEmployeeResumeForm.getEndDate() == null || downloadEmployeeResumeForm.getEndDate().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.enter.endDate"));
				saveErrors(request, errors);
			}
		}
		if(downloadEmployeeResumeForm.getEndDate() != null && !downloadEmployeeResumeForm.getEndDate().isEmpty()){
			if(downloadEmployeeResumeForm.getStartDate() == null || downloadEmployeeResumeForm.getStartDate().isEmpty()){
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
	public ActionForward initSendMail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		ActionErrors errors = new ActionErrors();
		try{
			/*code added by sudhir*/
			downloadEmployeeResumeForm.setClear(true);
			downloadEmployeeResumeForm.setSelectedUserIdsArray(null);
			List<DownloadEmployeeResumeTO> tos = downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs();
			Iterator<DownloadEmployeeResumeTO> iterator = tos.iterator();
			int count = 0;
			while (iterator.hasNext()) {
				DownloadEmployeeResumeTO downloadEmployeeResumeTO = (DownloadEmployeeResumeTO) iterator .next();
				if(downloadEmployeeResumeTO.getChecked()!=null && !downloadEmployeeResumeTO.getChecked().isEmpty()){
					if(downloadEmployeeResumeTO.getChecked().equalsIgnoreCase("on")){
						count =0;
						break;
					}else{
						count++;
					}
				}else{
					count++;
				}
			}
			if(count!=0){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.select.atleastone"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
			}
			DownloadEmployeeResumeHandler.getInstance().getUsersDetailsMap(downloadEmployeeResumeForm);
			/*code added by sudhir ended here*/
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			downloadEmployeeResumeForm.setErrorMessage(msg);
			downloadEmployeeResumeForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME_SENDMAIL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the sendMail in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs;
		HttpSession session = request.getSession();
		session.setAttribute("PhotoBytes", null);
		downloadEmployeeResumeForm.setClear(true);
		setUserId(request, downloadEmployeeResumeForm);
		try{
			/* code added and modified by sudhir*/
			/*if(downloadEmployeeResumeForm.getEmailIds() == null || downloadEmployeeResumeForm.getEmailIds().trim().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.enter.emailId"));
				saveErrors(request, errors);
			}*/
			if(downloadEmployeeResumeForm.getSelectedUserIdsArray()==null || downloadEmployeeResumeForm.getSelectedUserIdsArray().toString().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.enter.emailId"));
				saveErrors(request, errors);
			}
			/*-----------------------------------*/
			if(errors != null && !errors.isEmpty()){
				downloadEmployeeResumeForm.setClear(false);
				return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME_SENDMAIL);
			}else {
				/* code added and modified by sudhir*/
				//String realPath = request.getRealPath("")+"//TempFiles//";
				//System.out.println(realPath);
				//boolean isSend = DownloadEmployeeResumeHandler.getInstance().sendMailToAdmin(downloadEmployeeResumeForm,realPath,session,request);
				boolean isSend = DownloadEmployeeResumeHandler.getInstance().sendMailTOAdmin1(downloadEmployeeResumeForm);
				/*-----------------------------------*/
				if(isSend){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.mail.confirm.label"));
					saveMessages(request, messages);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.sending.mail.fail"));
					saveMessages(request, messages);
				}
			}
			//downloadEmployeeResumeTOs = DownloadEmployeeResumeHandler.getInstance().getEmpDetails(downloadEmployeeResumeForm);
			 downloadEmployeeResumeTOs = uncheckCheckBoxes(downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs());
		}catch (Exception e) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.sending.mail.fail"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME_SENDMAIL);
		}
		DownloadEmployeeResumeHandler.getInstance().setEmployeeDetailsToForm(downloadEmployeeResumeForm);
		log.info("Exit from the sendMail in DownloadEmployeeResumeAction");
		downloadEmployeeResumeForm.resetFields();
		downloadEmployeeResumeForm.setDownloadEmployeeResumeTOs(downloadEmployeeResumeTOs);
		return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getResumeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		session.setAttribute("PhotoBytes", null);
		try{
			DownloadEmployeeResumeHandler.getInstance().getResumeDetails(downloadEmployeeResumeForm,session,request);
			downloadEmployeeResumeForm.setPrintPage("true");
		}catch (Exception e) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
		}
		DownloadEmployeeResumeHandler.getInstance().setEmployeeDetailsToForm(downloadEmployeeResumeForm);
		log.info("Exit from the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.DOWNLOAD_EMPLOYEE_RESUME);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printResume(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		
		downloadEmployeeResumeForm.setPrintPage(null);
		
		DownloadEmployeeResumeHandler.getInstance().setEmployeeDetailsToForm(downloadEmployeeResumeForm);
		log.info("Exit from the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.PRINT_EMP_RESUME);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm) form;
		HttpSession session = request.getSession();
		try{
			List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs;
			if(downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs() != null && !downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs().isEmpty()){
				downloadEmployeeResumeTOs = downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs();
			}else{
				downloadEmployeeResumeTOs = DownloadEmployeeResumeHandler.getInstance().getEmpDetails(downloadEmployeeResumeForm);
			}
			session.setAttribute("EmployeeResume", downloadEmployeeResumeTOs);
		}catch (Exception e) {
			// TODO: handle exception
		}
		downloadEmployeeResumeForm.setPrintPage(null);
		
		DownloadEmployeeResumeHandler.getInstance().setEmployeeDetailsToForm(downloadEmployeeResumeForm);
		log.info("Exit from the initDownloadEmployeeResume in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.EXPORT_TO_EXCEL);
	}
	/**
	 * @param downloadEmployeeResumeTOs
	 * @return
	 */
	public List<DownloadEmployeeResumeTO> uncheckCheckBoxes(List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs){
		List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs1=new ArrayList<DownloadEmployeeResumeTO>();
		Iterator<DownloadEmployeeResumeTO> itr=downloadEmployeeResumeTOs.iterator();
		while(itr.hasNext()){
			DownloadEmployeeResumeTO downloadTo=itr.next();
			downloadTo.setChecked(null);
			downloadEmployeeResumeTOs1.add(downloadTo);
		}
		return downloadEmployeeResumeTOs1;
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOnlineResumeStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		log.info("Entering into the initOnlineResumeStatus in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm)form;
		log.info("Exit from the initOnlineResumeStatus in DownloadEmployeeResumeAction");
		session.setAttribute("downloadResumeTo", null);
		downloadEmployeeResumeForm.resetValues();
		session.setAttribute("appNo", "");
		return mapping.findForward(CMSConstants.INIT_STATUS_CHANGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmployeeData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initMailStatusChange in DownloadEmployeeResumeAction");
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm)form;
		HttpSession session=request.getSession();
		ActionErrors errors = downloadEmployeeResumeForm.validate(mapping, request);
		if(errors.isEmpty()){
			try{
				DownloadEmployeeResumeTO downloadTo=DownloadEmployeeResumeHandler.getInstance().getEmpDetailsByAppNo(downloadEmployeeResumeForm);
				if(downloadTo!=null){
					session.setAttribute("downloadResumeTo", downloadTo);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.status.norecords"));
					saveErrors(request, errors);
				}
			}catch(Exception exception){
				String msg = super.handleApplicationException(exception);
				downloadEmployeeResumeForm.setErrorMessage(msg);
				downloadEmployeeResumeForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Exit from the getEmployeeData in DownloadEmployeeResumeAction");
		return mapping.findForward(CMSConstants.INIT_STATUS_CHANGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSelectedStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DownloadEmployeeResumeForm downloadEmployeeResumeForm = (DownloadEmployeeResumeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		HttpSession session=request.getSession();
		try{
			if(downloadEmployeeResumeForm.getStatus()!=null && !downloadEmployeeResumeForm.getStatus().isEmpty()){
				boolean isSubmit = DownloadEmployeeResumeHandler.getInstance().saveStatus(downloadEmployeeResumeForm);
				if(isSubmit){
					ActionMessage message = new ActionMessage("knowledgepro.employee.CurrentStatus.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					session.setAttribute("downloadResumeTo", null);
					downloadEmployeeResumeForm.resetValues();
				}else{
						errors.add("error", new ActionError("knowledgepro.employee.CurrentStatus.update.failed"));
						//saveErrors(request, errors);
						addErrors(request, errors);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.employee.status.required"));
				addErrors(request, errors);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//DownloadEmployeeResumeTO resumeTo=new DownloadEmployeeResumeTO();
		
		return mapping.findForward(CMSConstants.INIT_STATUS_CHANGE);
	}
}
