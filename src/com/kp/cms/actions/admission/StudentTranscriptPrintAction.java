package com.kp.cms.actions.admission;

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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.StudentTranscriptPrintForm;
import com.kp.cms.handlers.admission.StudentTranscriptPrintHandler;
import com.kp.cms.to.admission.StudentTranscriptPrintTO;

/**
 * @author dIlIp
 *
 */
public class StudentTranscriptPrintAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(StudentTranscriptPrintAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initStudentTranscriptPrint(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		StudentTranscriptPrintForm certificateForm=(StudentTranscriptPrintForm)form;
		try
		{
			certificateForm.resetFields();
			
		}
		catch (Exception e) {
			 String msg = super.handleApplicationException(e);
			 certificateForm.setErrorMessage(msg);
			 certificateForm.setErrorStack(e.getMessage());
	         return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("studentTranscriptPrintInit");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public ActionForward studentTranscriptPrintSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		StudentTranscriptPrintForm certificateForm=(StudentTranscriptPrintForm)form;
		ActionErrors errors = new ActionErrors();
		//certificateForm.clearStuDetails();
		
			try{
				
				setUserId(request, certificateForm);
				Student student = StudentTranscriptPrintHandler.getInstance().verifyRegisterNumberAndGetDetails(certificateForm);
				if(student == null){
					certificateForm.setSemesterList(null);
					return mapping.findForward("printStudentTranscriptPrintPG");
				}
				Map<Integer, List<StudentTranscriptPrintTO>> semesterMap = StudentTranscriptPrintHandler.getInstance().getStudentForInput(certificateForm,request);

				if(semesterMap==null || semesterMap.isEmpty()){
					certificateForm.setSemesterList(null);
					return mapping.findForward("printStudentTranscriptPrintPG");
				}
				certificateForm.setSemesterList(semesterMap);
				
				StudentCertificateDetails details = StudentTranscriptPrintHandler.getInstance().checkForAlreadyPrinted(certificateForm);
				if(details!=null){
					//dont save record
					if(certificateForm.getDescription().equalsIgnoreCase(CMSConstants.TRANSCRIPT_DESCRIPTION_PG))
						return mapping.findForward("printStudentTranscriptPrintPG");
					else
						return mapping.findForward("printStudentTranscriptPrintUG");
				}
				else{
					//save record
					boolean isSaved;
					isSaved = StudentTranscriptPrintHandler.getInstance().saveStudentTranscriptPrint(certificateForm);
					if(isSaved){
						
						boolean isCurrentNoSaved = StudentTranscriptPrintHandler.getInstance().saveStudentCertificateNumberCurrentNumber(certificateForm);
						if(isCurrentNoSaved){
							if(certificateForm.getDescription().equalsIgnoreCase(CMSConstants.TRANSCRIPT_DESCRIPTION_PG))
								return mapping.findForward("printStudentTranscriptPrintPG");
							else
								return mapping.findForward("printStudentTranscriptPrintUG");
						}
					}
					else{
						errors.add("error", new ActionError("knowledgepro.petticash.save.failed"));
						addErrors(request, errors);
						return mapping.findForward("studentTranscriptPrintInit");
					}
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}catch (NoSuchFieldError e) {
				certificateForm.setNoPrefixFound("YES");
				return mapping.findForward("printStudentTranscriptPrintPG");
			}
		addErrors(request, errors);
		certificateForm.setSemesterList(null);
		return mapping.findForward("printStudentTranscriptPrintPG");
		
		
	}

}
