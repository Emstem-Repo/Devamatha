package com.kp.cms.actions.admission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CertificateCourseCopyForm;
import com.kp.cms.handlers.admission.CertificateCourseCopyHandler;

public class CertificateCourseCopyAction extends BaseDispatchAction {

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initCopyCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CertificateCourseCopyForm copyForm = (CertificateCourseCopyForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			copyForm.reset(mapping, request);
			copyForm.setFromAcademicYear(" ");
			copyForm.setToAcademicYear(" ");
			copyForm.setFromSemType("");
			copyForm.setToSemType("");
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			copyForm.setErrorMessage(msg);
			copyForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_COPY);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward certificateCourseCopy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionMessages messages = new ActionMessages();
		CertificateCourseCopyForm copyForm = (CertificateCourseCopyForm) form;
		ActionErrors errors = copyForm.validate(mapping, request);
		 if(copyForm.getFromAcademicYear().equalsIgnoreCase(copyForm.getToAcademicYear()))
			{
				errors.add("error", new ActionError(
				"knowledgepro.admin.coursecopy.same.year"));
		       // saveErrors(request, errors);
			}
		if(errors.isEmpty())
		{
		try {
			setUserId(request, copyForm);
			boolean certificate = CertificateCourseCopyHandler.getInstance()
					.certificateCourseCopy(copyForm);
			if (certificate) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.certificatecoursecopy.copied.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.admin.coursecopy.name.exists"));
				saveErrors(request, errors);
			}
		} 
		catch (BusinessException exception1) {
		 	errors.add("error", new ActionError(
			"knowledgepro.admin.coursecopy.name.exists"));
		 	saveErrors(request, errors);
			return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_COPY);
		}
		catch (ApplicationException exception1) {
			 	errors.add("error", new ActionError(
				"knowledgepro.admin.coursecopy.nodata.exists"));
			 	saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_COPY);
			}
		catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			copyForm.setErrorMessage(msg);
			copyForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}
		else
		{
			saveErrors(request, errors);
		}
		return mapping
		.findForward(CMSConstants.CERTIFICATE_COURSE_COPY);
	}
	
}