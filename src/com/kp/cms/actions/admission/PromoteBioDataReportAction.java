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
import com.kp.cms.forms.admission.PromoteBioDataUploadForm;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.handlers.admission.AdmissionBioDataReportHandler;
import com.kp.cms.handlers.admission.PromoteBioDataUploadHandler;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admission.PromoteBioDataUploadTo;

public class PromoteBioDataReportAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(PromoteBioDataReportAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPromoteBioDataFirstPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteBioDataUploadForm promoteBioDataForm = (PromoteBioDataUploadForm) form;
		promoteBioDataForm.setCourseId(null);
		HttpSession session = request.getSession(false);
		session.removeAttribute("ProBioSearch");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		setRequiredDataTOForm(promoteBioDataForm);
		return mapping.findForward(CMSConstants.INIT_PROMOTE_MARKS_FIRST_PAGE);
	}
	
	/**
	 * @param dataMigrationForm
	 * @throws Exception
	 */
	private void setRequiredDataTOForm(PromoteBioDataUploadForm promoteBioDataForm) throws Exception{
		String mode = "bioData";
		Map<String,String> courses = PromoteMarksUploadHandler.getInstance().getCourses(mode);
		promoteBioDataForm.setCourses(courses);
		//PromoteBioDataUploadHandler.getInstance().getCoursePageData(promoteBioDataForm);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchPromoteBiodata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteBioDataUploadForm promoteBioDataForm = (PromoteBioDataUploadForm) form;
		ActionMessages errors = new ActionErrors();
		promoteBioDataForm.setTheFile(null);
		if (promoteBioDataForm.getAcademicYear()!=null && !promoteBioDataForm.getAcademicYear().isEmpty()) {	
			try {
			HttpSession session = request.getSession();
			List<PromoteBioDataUploadTo> proBioSearch = PromoteBioDataUploadHandler.getInstance().getProBioSearch(promoteBioDataForm,request);
			if(proBioSearch !=null){	
				session.setAttribute("ProBioSearch",proBioSearch );
			}

			} catch (Exception exception) {	
				
				String msg = super.handleApplicationException(exception);
				promoteBioDataForm.setErrorMessage(msg);
				promoteBioDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			errors.add("error", new ActionError("knowledgepro.exam.examDefinition.academicYear.required"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_PROMOTE_MARKS_FIRST_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PROMOTE_MARKS_SECOND_PAGE);
	}
		
	}
