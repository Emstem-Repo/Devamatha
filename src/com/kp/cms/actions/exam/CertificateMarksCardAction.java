package com.kp.cms.actions.exam;

import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.CertificateMarksCardForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.exam.CertificateMarksCardHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;

public class CertificateMarksCardAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AdminMarksCardAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdminCertificateMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAdminMarksCard input");
		CertificateMarksCardForm certificateMarksCardForm = (CertificateMarksCardForm) form;
		certificateMarksCardForm.resetFields();
		setRequiredDatatoForm(certificateMarksCardForm);
		log.info("Exit initAdminMarksCard input");
		
		return mapping.findForward(CMSConstants.INIT_ADMIN_CERITIFICATE_MARKS_CARD);
	}

	/**
	 * @param certificateMarksCardForm
	 */
	private void setRequiredDatatoForm( CertificateMarksCardForm certificateMarksCardForm) throws Exception {
		List<CourseTO> courseList=CourseHandler.getInstance().getCourses();
		if(courseList!=null && !courseList.isEmpty()){
			certificateMarksCardForm.setCourseList(courseList);
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
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		CertificateMarksCardForm certificateMarksCardForm = (CertificateMarksCardForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = certificateMarksCardForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<ConsolidateMarksCardTO> studentList=CertificateMarksCardHandler.getInstance().getStudentForInput(certificateMarksCardForm);// getting the student list for input search
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(certificateMarksCardForm);
					certificateMarksCardForm.setPrint(false);
					return mapping.findForward(CMSConstants.INIT_ADMIN_CERITIFICATE_MARKS_CARD);
				}
				certificateMarksCardForm.setStudentList(studentList);
				certificateMarksCardForm.setPrint(true);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				certificateMarksCardForm.setErrorMessage(msg);
				certificateMarksCardForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(certificateMarksCardForm);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_ADMIN_CERITIFICATE_MARKS_CARD);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_ADMIN_CERITIFICATE_MARKS_CARD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMIN_CERITIFICATE_MARKS_CARD_RESULT);
	}
}
