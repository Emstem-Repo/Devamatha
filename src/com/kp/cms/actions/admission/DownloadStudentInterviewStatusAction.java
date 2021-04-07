package com.kp.cms.actions.admission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.reports.ScoreSheetAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DownloadInterviewFormatForm;
import com.kp.cms.forms.admission.DownloadStudentInterviewStatusForm;
import com.kp.cms.forms.reports.ScoreSheetForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.DownloadInterviewFormatHandler;
import com.kp.cms.handlers.admission.DownloadStudentInterviewStatusHandler;
import com.kp.cms.handlers.admission.UploadInterviewResultHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewResultTO;

public class DownloadStudentInterviewStatusAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(DownloadStudentInterviewStatusAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initStudentStatus input");
		DownloadStudentInterviewStatusForm downloadStudentInterviewStatusForm = (DownloadStudentInterviewStatusForm) form;
		downloadStudentInterviewStatusForm.resetFields();
		setRequiredDatatoForm(downloadStudentInterviewStatusForm, request);
		log.info("Exit initStudentStatus input");
		
		return mapping.findForward(CMSConstants.INIT_STUDENT_STATUS);
	}

	/**
	 * @param downloadStudentInterviewStatusForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(DownloadStudentInterviewStatusForm downloadStudentInterviewStatusForm,HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			downloadStudentInterviewStatusForm.setProgramTypeList(programTypeList);
		}
		if(downloadStudentInterviewStatusForm.getProgramId()!= null  &&	downloadStudentInterviewStatusForm.getProgramId().length() >0 ){
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.valueOf(downloadStudentInterviewStatusForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		if(downloadStudentInterviewStatusForm.getProgramTypeId()!=null && !downloadStudentInterviewStatusForm.getProgramTypeId().isEmpty()){
			int ptid = Integer.parseInt(downloadStudentInterviewStatusForm.getProgramTypeId());
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(ptid);
			request.setAttribute("programMap",programMap);
		}
	}
	
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		DownloadStudentInterviewStatusForm downloadStudentInterviewStatusForm = (DownloadStudentInterviewStatusForm) form;
		downloadStudentInterviewStatusForm.setExport(false);
		 ActionErrors errors = downloadStudentInterviewStatusForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<AdmApplnTO> selectedCandidates =DownloadStudentInterviewStatusHandler.getInstance().getCandidates(downloadStudentInterviewStatusForm,request);
				downloadStudentInterviewStatusForm.resetFields();
				if(selectedCandidates==null || selectedCandidates.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(downloadStudentInterviewStatusForm,request);
					return mapping.findForward(CMSConstants.INIT_STUDENT_STATUS);
				}else{
					DownloadStudentInterviewStatusHandler.getInstance().exportToCSV(selectedCandidates,request);
					downloadStudentInterviewStatusForm.setExport(true);
				}
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				downloadStudentInterviewStatusForm.setErrorMessage(msg);
				downloadStudentInterviewStatusForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(downloadStudentInterviewStatusForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_STUDENT_STATUS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_STUDENT_STATUS);
	}
}
