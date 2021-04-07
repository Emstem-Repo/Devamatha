package com.kp.cms.actions.reports;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.StudentUploadPhotoForm;
import com.kp.cms.forms.reports.ScoreSheetForm;
import com.kp.cms.forms.reports.StudentVehicleForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.StudentVehicleHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.reports.StudentVehicleTO;

public class StudentVehicleReportAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentVehicleReportAction.class);

	/**
	 * Method to set the required data to the form to display it in studentVehicleReport.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentVehicle(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		log.info("Entered Student vehicle Report input");
		StudentVehicleForm studentVehicleForm = (StudentVehicleForm) form;
		studentVehicleForm.resetFields();
		setRequiredDatatoForm(studentVehicleForm, request);
		log.info("Exit Student vehicle Report input");
	return mapping.findForward("initStudentVehicle");
	}
	/**
	 * @param studentVehicleForm
	 * @param request
	 * Setting the required Data to the request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(StudentVehicleForm studentVehicleForm, HttpServletRequest request) throws Exception {
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			studentVehicleForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}	
	}
	
	public ActionForward submitStudentVehicle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submit student vehicle  type action");	
		StudentVehicleForm studentVehicleForm = (StudentVehicleForm)form;
		 ActionErrors errors = studentVehicleForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);		
			return mapping.findForward("initStudentVehicle");
		}
		try {
			StudentVehicleHandler studentVehicleHandler=StudentVehicleHandler.getInstance();
			List<StudentVehicleTO> StudentVehicleList=studentVehicleHandler.getStudentVehicleDetails(studentVehicleForm);
			if (StudentVehicleList.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setRequiredDatatoForm(studentVehicleForm, request);
				log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
				return mapping.findForward("initStudentVehicle");
			} 
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				studentVehicleForm.setOrganizationName(orgTO.getOrganizationName());
			}
			request.getSession().setAttribute("StudentVehicleList", StudentVehicleList);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			studentVehicleForm.setErrorMessage(msg);
			studentVehicleForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward("studentVehicleDetails");
	}
}
