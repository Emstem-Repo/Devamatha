package com.kp.cms.actions.reports;

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
import com.kp.cms.forms.reports.CertificateDueReportForm;
import com.kp.cms.handlers.reports.CertificateDueReportHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CertificateDueReportAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CertificateDueReportAction.class);
	private static String DUE_STUDENTS = "dueStudents";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns initializes the certificate due page
	 * @throws Exception
	 */
	public ActionForward initCertificateDue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initCertificateDue of CertificateDueReportAction");
			CertificateDueReportForm certificateForm = (CertificateDueReportForm) form;
			try {
				certificateForm.resetFields();
				HttpSession session = request.getSession(false);
				if (session.getAttribute(DUE_STUDENTS) != null) {
					session.removeAttribute(DUE_STUDENTS);
				}
			} catch (Exception e) {
				log.error("Error occured at initCertificateDue of CertificateDueReportAction",e);
				String msg = super.handleApplicationException(e);
				certificateForm.setErrorMessage(msg);
				certificateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initCertificateDue of CertificateDueReportAction");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_DUE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns List of certificate Due students
	 * @throws Exception
	 */
	public ActionForward getCerificateDueStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getCerificateDueStudents of CertificateDueReportAction");
		CertificateDueReportForm certificateForm=(CertificateDueReportForm)form;
		HttpSession session = request.getSession(false);
		ActionErrors errors = certificateForm.validate(mapping, request);
		if(session.getAttribute(DUE_STUDENTS)==null){
			try {
				if (errors.isEmpty()) {
					boolean isValidDueDate;
					isValidDueDate = CommonUtil.isValidDate(certificateForm.getDueDate().trim());
					if (!isValidDueDate) {
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_DUE);
					}
					else{			
						List<StudentTO> dueStudents = CertificateDueReportHandler.getInstance().getCerificateDueStudents(certificateForm);
						session.setAttribute(DUE_STUDENTS,dueStudents );
					}
				}
				else{
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_DUE);
				}
			} catch (Exception e) {
				log.error("Error occured at getCerificateDueStudents of CertificateDueReportAction",e);
				String msg = super.handleApplicationException(e);
				certificateForm.setErrorMessage(msg);
				certificateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving into getCerificateDueStudents of CertificateDueReportAction");
		return mapping.findForward(CMSConstants.CERTIFICATE_DUE_RESULT);
	}

}
