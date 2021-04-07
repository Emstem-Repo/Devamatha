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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.AdmissionDataReUploadForm;
import com.kp.cms.utilities.AdmissionDataReUploadCSVUpdater;

public class AdmissionDataReUploadAction extends BaseDispatchAction {
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
	public ActionForward initCSVReUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initCSVReUpload");
		AdmissionDataReUploadForm admissionDataReUploadForm = (AdmissionDataReUploadForm)form;
		admissionDataReUploadForm.clear();
		log.info("Exit initCSVReUpload");
		
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVADMISSION_RE_UPLOADPAGE);
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
	public ActionForward reUpdateCSV(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered updateCSV");
		AdmissionDataReUploadForm admissionDataReUploadForm = (AdmissionDataReUploadForm)form;
		FormFile csvfile = admissionDataReUploadForm.getCsvFile();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = admissionDataReUploadForm.validate(mapping, request);
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
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVADMISSION_RE_UPLOADPAGE);
			}	
			Map<Integer,Student> stuList=AdmissionDataReUploadCSVUpdater.getStudentByCourse(Integer.parseInt(admissionDataReUploadForm.getCourseId()), Integer.parseInt(admissionDataReUploadForm.getApplicationYear()));
			List<Student> finalList=AdmissionDataReUploadCSVUpdater.parseCSVFile(stuList,csvfile.getInputStream(),Integer.parseInt(admissionDataReUploadForm.getApplicationYear()),Integer.parseInt(admissionDataReUploadForm.getCourseId()),admissionDataReUploadForm.getUserId(),Integer.parseInt(admissionDataReUploadForm.getProgramId()),Integer.parseInt(admissionDataReUploadForm.getProgramTypeId()));
			boolean result=AdmissionDataReUploadCSVUpdater.updatePersonalData(finalList);
			if(!result){
				errors.add(CMSConstants.ADMISSIONDATAUPLOAD_FAILURE, new ActionError(CMSConstants.ADMISSIONDATAUPLOAD_FAILURE));
				saveErrors(request, errors);
			}else{
				ActionMessage message = new ActionMessage(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRM);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}
		}  catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admissionDataReUploadForm.setErrorMessage(msg);
			admissionDataReUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("Exit updateCSV");
		admissionDataReUploadForm.clear();
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVADMISSION_RE_UPLOADPAGE);
	}
}
