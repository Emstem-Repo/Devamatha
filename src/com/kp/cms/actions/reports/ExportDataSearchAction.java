package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.forms.reports.ExportDataSearchForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.ExportDataSearchHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.ExportDataSearchTO;

public class ExportDataSearchAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(ExportDataSearchAction.class);
	
	/**
	 * This method is used to load the data for display in UI.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initExportSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExportDataSearchForm dataSearchForm = (ExportDataSearchForm)form;
		dataSearchForm.resetFields();
		HttpSession session = request.getSession(false);
		if(session != null){
			session.removeAttribute(CMSConstants.ID_DATA_ATTR);
		}
		setRequiredData(dataSearchForm,request);
		return mapping.findForward(CMSConstants.EXPORT_SEARCH);
	}
	
	/**
	 * This method is used to display selected candidates in UI.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitExportSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExportDataSearchForm dataSearchForm = (ExportDataSearchForm)form;
		 ActionErrors errors = dataSearchForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request,dataSearchForm);
			validateForm(dataSearchForm, errors);
			 if (errors.isEmpty()) {
				 List<ExportDataSearchTO> list = ExportDataSearchHandler.getInstance().getAdmApplnDetails(dataSearchForm);
				 if(list != null && list.size() != 0){
					 dataSearchForm.setCandidateList(list);
				 }else{
					dataSearchForm.resetFields();
					messages.add(CMSConstants.ERROR,new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					addErrors(request, messages);
					setRequiredData(dataSearchForm,request);
					return mapping.findForward(CMSConstants.EXPORT_SEARCH);
				 }
			 }else{
					addErrors(request, errors);
					setRequiredData(dataSearchForm,request);
					setCourseMapValues(dataSearchForm, request);
					return mapping.findForward(CMSConstants.EXPORT_SEARCH);
			 }
			 }catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				dataSearchForm.setErrorMessage(msg);
				dataSearchForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		setRequiredData(dataSearchForm,request);
		return mapping.findForward(CMSConstants.EXPORT_SEARCH_RES);
	}
	
	/**
	 * This method is used to export the selected candidates from list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward selectedCandidateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExportDataSearchForm dataSearchForm = (ExportDataSearchForm)form;
		ActionMessages messages = new ActionMessages();
		try {
			List<ExportDataSearchTO> exportList = new ArrayList<ExportDataSearchTO>();  
			int count = 0;
			if(dataSearchForm.getCandidateList() != null && dataSearchForm.getCandidateList().size() != 0){
				Iterator<ExportDataSearchTO> list = dataSearchForm.getCandidateList().iterator();
				while (list.hasNext()) {
					ExportDataSearchTO dataSearchTO = (ExportDataSearchTO) list
							.next();
					if(!dataSearchTO.isUpdatedChecked()){
						dataSearchTO.setChecked(false);
						dataSearchTO.setUpdatedChecked(false);
						count++;
					}
					exportList.add(dataSearchTO);
				}
				if(count == exportList.size()){
					dataSearchForm.setCandidateList(exportList);
					messages.add(CMSConstants.ERROR,new ActionMessage(CMSConstants.EXPORT_RECORDS_REQUIRE));
					addErrors(request, messages);
					return mapping.findForward(CMSConstants.EXPORT_SEARCH_RES);
				}else{
				ExportDataSearchTO exportDataSearchTO = ExportDataSearchHandler.getInstance().setDisplayForFields();
				ExportDataSearchHandler.getInstance().convertListToExcel(exportDataSearchTO,dataSearchForm.getCandidateList(),request, dataSearchForm.getValidTill() );
				dataSearchForm.setFileDownload("download");
			}
		}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			dataSearchForm.setErrorMessage(msg);
			dataSearchForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredData(dataSearchForm,request);
		return mapping.findForward(CMSConstants.EXPORT_DATA_DOWNLOAD);
	}
	
	public void setCourseMapValues(ExportDataSearchForm dataSearchForm,HttpServletRequest request)
	throws Exception{
		log.info("entering into setCourseMapValues of ExportDataSearchAction class.");
		if(!StringUtils.isEmpty(dataSearchForm.getProgramTypeId()) && !StringUtils.isEmpty(dataSearchForm.getProgramId())){
			int ptid = Integer.parseInt(dataSearchForm.getProgramTypeId());
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(ptid);
			int pid = Integer.parseInt(dataSearchForm.getProgramId());
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(pid);
			
			request.setAttribute("programMap", programMap);
			request.setAttribute("courseMap", courseMap);
		}
		
		log.info("exit of setCourseMapValues of ExportDataSearchAction class.");
		
	}
	
	
	/**
	 * This method is used to set the program type to 
	 * @param dataSearchForm
	 * @param request
	 * @throws Exception
	 */
	
	public void setRequiredData(ExportDataSearchForm dataSearchForm,HttpServletRequest request)
		throws Exception {
		log.info("entering into setRequiredData of ExportDataSearchAction class.");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.info("exit of setRequiredData of ExportDataSearchAction class.");
	}
	
	public void validateForm(ExportDataSearchForm dataSearchForm,ActionErrors errors){
		if((dataSearchForm.getApplicationNo()==null || dataSearchForm.getApplicationNo().isEmpty()) && (dataSearchForm.getRegNo()==null || dataSearchForm.getRegNo().isEmpty())
				&& (dataSearchForm.getRollNo()==null || dataSearchForm.getRollNo().isEmpty()) &&(dataSearchForm.getProgramTypeId() == null
						|| dataSearchForm.getProgramTypeId().length() == 0) && (dataSearchForm.getProgramId() == null
								|| dataSearchForm.getProgramId().length() == 0) && (dataSearchForm.getCourseNames() == null || dataSearchForm.getCourseNames().length == 0)){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.senddata.idcard.search"));
		}else{
			if(dataSearchForm.getProgramTypeId() != null
					&& dataSearchForm.getProgramTypeId().length() > 0){
				if (dataSearchForm.getProgramTypeId() == null
						|| dataSearchForm.getProgramTypeId().length() == 0) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED,
								error);
				}
				if (dataSearchForm.getProgramId() == null
						|| dataSearchForm.getProgramId().length() == 0) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
				}
				if (dataSearchForm.getCourseNames() == null || dataSearchForm.getCourseNames().length == 0) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
				}
			}else{
				if((dataSearchForm.getApplicationNo()==null || dataSearchForm.getApplicationNo().isEmpty()) && (dataSearchForm.getRegNo()==null || dataSearchForm.getRegNo().isEmpty())
						&& (dataSearchForm.getRollNo()==null || dataSearchForm.getRollNo().isEmpty())){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","knowledgepro.petticash.Applno"));
				}
			}
		}
	}
}