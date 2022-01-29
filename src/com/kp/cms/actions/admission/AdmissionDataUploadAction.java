package com.kp.cms.actions.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmissionDataUploadForm;
import com.kp.cms.utilities.AdmissionCSVUpdater;

/**
 * Action class for Admission Data Upload
 */
@SuppressWarnings("deprecation")
public class AdmissionDataUploadAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(AdmissionDataUploadAction.class);
	
	/**
	 * Admission Data Upload initialization
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCSVUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initCSVUpload");
		AdmissionDataUploadForm admissionDataUploadForm = (AdmissionDataUploadForm)form;
		admissionDataUploadForm.clear();
		log.info("Exit initCSVUpload");
		
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVADMISSIONUPLOADPAGE);
	}
	/**
	 * Upload Admission Data To DB
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCSV(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered updateCSV");
		AdmissionDataUploadForm admissionDataUploadForm = (AdmissionDataUploadForm)form;
		FormFile csvfile = admissionDataUploadForm.getCsvFile();
		
		ActionMessages messages = new ActionMessages();
		
		ActionErrors errors = admissionDataUploadForm.validate(mapping, request);
		validateProgramCourse(errors, admissionDataUploadForm);
		
		try {
			//validate the csv type
			if(csvfile!=null){
				if(csvfile.getFileName()!=null && !StringUtils.isEmpty(csvfile.getFileName().trim())){
					String extn="";
					int index = csvfile.getFileName().lastIndexOf(".");
					if(index != -1){
						extn = csvfile.getFileName().substring(index, csvfile.getFileName().length());
					}
					if(!extn.equalsIgnoreCase(".CSV")){
						if(errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR).hasNext()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR));
							saveErrors(request, errors);
						}
					}
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED).hasNext()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_OMR_REQUIRED));
						saveErrors(request, errors);
					}
				}
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVADMISSIONUPLOADPAGE);
			}	
			List<AdmAppln> applications=null;
			if(csvfile!=null){
				setUserId(request, admissionDataUploadForm);
				applications = AdmissionCSVUpdater.parseFile(csvfile.getInputStream(),Integer.parseInt(admissionDataUploadForm.getApplicationYear()),Integer.parseInt(admissionDataUploadForm.getCourseId()),admissionDataUploadForm.getUserId(),Integer.parseInt(admissionDataUploadForm.getProgramId()),Integer.parseInt(admissionDataUploadForm.getProgramTypeId()));
			}
			if(applications!=null && !applications.isEmpty()){
				Map<String, List<String>> duplicateNumberMap =	AdmissionCSVUpdater.persistCompleteApplicationData(applications);
				if(duplicateNumberMap != null && !duplicateNumberMap.values().isEmpty()){
					request.setAttribute("duplicateNumberMap", duplicateNumberMap);
					errors.add(CMSConstants.ADMISSIONDATAUPLOAD_FAILURE, new ActionError(CMSConstants.ADMISSIONDATAUPLOAD_FAILURE));
					saveErrors(request, errors);
				}else{
					ActionMessage message = new ActionMessage(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRM);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
				}
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			exception.printStackTrace();
			String msg = super.handleApplicationException(exception);
			admissionDataUploadForm.setErrorMessage(msg);
			admissionDataUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("Exit updateCSV");
		admissionDataUploadForm.clear();
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVADMISSIONUPLOADPAGE);
	}
	
	/**
	 * validate programtype, course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionErrors validateProgramCourse(ActionErrors errors,
			AdmissionDataUploadForm admForm) {
		log.info("Entered validate program course");
		if(admForm.getProgramTypeId() == null || admForm.getProgramTypeId().length() == 0)
		{
			ActionError error= new ActionError(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
		}
		if(admForm.getProgramId() ==null || admForm.getProgramId().length()==0)
		{
			ActionError error= new ActionError(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
		}
		if(admForm.getCourseId()==null ||admForm.getCourseId().length()==0 )
		{
			ActionError error= new ActionError(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
		}
		log.info("Exit validate program course");
		return errors;
	}
}