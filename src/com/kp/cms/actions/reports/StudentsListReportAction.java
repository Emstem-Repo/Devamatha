package com.kp.cms.actions.reports;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.StudentsListReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.StudentsListHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.StudentsListReportTO;

public class StudentsListReportAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(StudentsListReportAction.class);
	private static final String STUDENTS_LIST_REPORT = "studentsListReport";
	
	/**
	 * This method is used to display the leave report when link is clicked.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initStudentsListReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initStudentsListReport method of StudentsListReportAction class..");
		StudentsListReportForm listReportForm = (StudentsListReportForm)form;
		listReportForm.clear();
		setRequiredDataToForm(listReportForm,request);
		HttpSession session = request.getSession(false);
		session.removeAttribute(STUDENTS_LIST_REPORT);
		log.info("Exit initStudentsListReport method of StudentsListReportAction class..");
		return mapping.findForward(CMSConstants.LIST_STUDENTS);
	}
	
	/**
	 * This method is used to set Program Type to form.
	 * @param leaveReportForm
	 * @param request
	 * @throws Exception
	 */
	
	private void setRequiredDataToForm(StudentsListReportForm listReportForm,
			HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	}
	/**
	 * This method is used to get the leave report details from database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitStudentsListReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered submitStudentsListReport method of StudentsListReportAction class..");
		StudentsListReportForm listReportForm = (StudentsListReportForm)form;
		HttpSession session = request.getSession();
		if(session.getAttribute(STUDENTS_LIST_REPORT)==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = listReportForm.validate(mapping, request);
			if (errors.isEmpty()) {	
				try {
				List<OrganizationTO> list = OrganizationHandler.getInstance().getOrganizationDetails();
				if(list != null && list.size() != 0){
					Iterator<OrganizationTO> iterator = list.iterator();
					while (iterator.hasNext()) {
						OrganizationTO organizationTO = (OrganizationTO) iterator
								.next();
						listReportForm.setOrganizationName(organizationTO.getOrganizationName());
					}
				}
					//call of handler.
				List<StudentsListReportTO> studentsList = StudentsListHandler.getInstance().getStudentsListReportDetails(listReportForm);
					if(studentsList != null){
						session.setAttribute(STUDENTS_LIST_REPORT,studentsList);
					}
				}catch (BusinessException businessException) {
					log.info("Exception submitStudentsListReport");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					listReportForm.setErrorMessage(msg);
					listReportForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				}else {
					addErrors(request, errors);
					setRequiredDataToForm(listReportForm, request);
					return mapping.findForward(CMSConstants.LIST_STUDENTS);
				}
		}	
		log.info("Exit of submitStudentsListReport method of StudentsListReportAction class..");
		return mapping.findForward(CMSConstants.STUDENTS_REPORT_LIST);
	}
}