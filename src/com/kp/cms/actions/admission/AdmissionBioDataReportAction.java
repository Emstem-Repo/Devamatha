package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.List;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmissionBioDataReportForm;
import com.kp.cms.handlers.admission.AdmissionBioDataReportHandler;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.to.admin.AdmBioDataCJCTo;


public class AdmissionBioDataReportAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(AdmissionBioDataReportAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDataMigration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return mapping.findForward(CMSConstants.INIT_DATA_MIGRATION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward dataMigrationReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	// TODO Auto-generated method stub
	return mapping.findForward(CMSConstants.INIT_DATA_MIGRATION_REPORT);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initDataMigrationFirstPage(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AdmissionBioDataReportForm dataMigrationForm = (AdmissionBioDataReportForm) form;
	dataMigrationForm.setCourseName(null);
	HttpSession session = request.getSession(false);
	session.removeAttribute("AdmBioSearch");
	session.removeAttribute(CMSConstants.EXCEL_BYTES);
	setRequiredDataTOForm(dataMigrationForm);
	return mapping.findForward(CMSConstants.INIT_DATA_MIGRATION_FIRST_PAGE);
}
/**
 * @param dataMigrationForm
 * @throws Exception
 */
	private void setRequiredDataTOForm(AdmissionBioDataReportForm dataMigrationForm) throws Exception {
		String mode = "AdmBiodata";
		Map<String,String> courses=PromoteMarksUploadHandler.getInstance().getCourses(mode);
		dataMigrationForm.setCourses(courses);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAdmissionBioData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmissionBioDataReportForm dataMigrationForm = (AdmissionBioDataReportForm) form;
		ActionErrors errors = new ActionErrors();
		dataMigrationForm.setMode(null);
		dataMigrationForm.setDownloadExcel(null);
		if (dataMigrationForm.getAcademicYear()!=null && !dataMigrationForm.getAcademicYear().isEmpty()) {	
			try {
			HttpSession session = request.getSession();
			 List<AdmBioDataCJCTo> admBioSearch = AdmissionBioDataReportHandler.getInstance().getAdmBioSearch(dataMigrationForm,request);
			if(!admBioSearch.isEmpty()){	
				dataMigrationForm.setAdmBioDataList(admBioSearch);
				session.setAttribute("AdmBioSearch",admBioSearch );
			}

			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				dataMigrationForm.setErrorMessage(msg);
				dataMigrationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			errors.add("error", new ActionError("knowledgepro.exam.examDefinition.academicYear.required"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_DATA_MIGRATION_FIRST_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_DATA_MIGRATION_SECOND_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initexportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initexportToExcel..");
		AdmissionBioDataReportForm dataMigrationForm = (AdmissionBioDataReportForm) form;
		dataMigrationForm.setSelectedColumnsArray(null);
		dataMigrationForm.setUnselectedColumnsArray(null);
		log.info("exit initexportToExcel..");
		return mapping.findForward(CMSConstants.INIT_EXCEL_ADM_BIODATA_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered exportToExcel..");
		AdmissionBioDataReportForm dataMigrationForm = (AdmissionBioDataReportForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		dataMigrationForm.setMode(null);
		dataMigrationForm.setDownloadExcel(null);
		
			try {
				if(dataMigrationForm.getSelectedColumnsArray()!=null && !dataMigrationForm.getSelectedColumnsArray().toString().isEmpty()){
					setUserId(request, dataMigrationForm);
					boolean isUpdated =	AdmissionBioDataReportHandler.getInstance().exportTOExcel(dataMigrationForm,request);
			 		if(isUpdated){
			 			dataMigrationForm.setMode("excel");
						dataMigrationForm.setDownloadExcel("download");
						ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				}else {
					errors.add("error", new ActionError("knowledgepro.select.atleast.onecolumn"));
					addErrors(request, errors);			
					return mapping.findForward(CMSConstants.INIT_EXCEL_ADM_BIODATA_REPORT);
				}
		
		}catch (ApplicationException ae) {
			log.error("error occured in exportToExcel in AdmissionBioDataReportAction",ae);
			String msg = super.handleApplicationException(ae);
			dataMigrationForm.setErrorMessage(msg);
			dataMigrationForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToExcel in AdmissionBioDataReportAction",e);
			String msg = super.handleApplicationException(e);
			dataMigrationForm.setErrorMessage(msg);
		dataMigrationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.INIT_DATA_MIGRATION_SECOND_PAGE);
}
}
