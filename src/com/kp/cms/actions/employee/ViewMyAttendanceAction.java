package com.kp.cms.actions.employee;

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
import com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm;
import com.kp.cms.forms.employee.ViewMyAttendanceForm;
import com.kp.cms.handlers.employee.ViewMyAttendanceHandler;
import com.kp.cms.to.admin.EmpAttendanceTo;
import com.kp.cms.to.employee.EmpApplyLeaveTO;

public class ViewMyAttendanceAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ViewMyAttendanceAction.class);
	/**
	 * setting the details of attendance of logged in employee for the current month
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMyAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the viewMyAttendance in ViewMyAttendanceAction");
		ViewMyAttendanceForm viewAttForm=(ViewMyAttendanceForm) form;
		ActionErrors errors = new ActionErrors();
		viewAttForm.resetFields();
		setUserId(request,viewAttForm);
		if(viewAttForm.getViewEmpAttendance()){
			validateForm(viewAttForm,errors);
		}
		if(errors.isEmpty()){
		try{
			List<EmpAttendanceTo> empAttTo=ViewMyAttendanceHandler.getInstance().getEmployeeAttendance(viewAttForm);
	//	List<EmpApplyLeaveTO> empLeaveTo=ViewMyAttendanceHandler.getInstance().getEmployeeLeaveDetails(viewAttForm);
		if(empAttTo==null || empAttTo.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			viewAttForm.resetFields();
			log.info("Exit ViewMyAttendanceAction - viewMyAttendance size 0");
			return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE);
		}
		viewAttForm.setEmpAttTo(empAttTo);
	//	viewAttForm.setEmpLeaveTo(empLeaveTo);
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			viewAttForm.setErrorMessage(msg);
			viewAttForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		else{
			addErrors(request, errors);
			viewAttForm.resetFields();
			viewAttForm.setEmpCode(null);
			viewAttForm.setFingerPrintId(null);
			log.info("Exit ViewMyAttendanceAction -  errors not empty ");
			return mapping.findForward(CMSConstants.VIEW_EMP_ATTENDANCE_INIT);
		
		}
		log.info("Exit from the viewMyAttendance in ViewMyAttendanceAction");
		return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE);
	}
	
	/**
	 * if viewEmployeeAttendance then validating the form
	 * @param viewAttForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateForm(ViewMyAttendanceForm viewAttForm,ActionErrors errors) throws Exception {
		if((viewAttForm.getFingerPrintId()==null || viewAttForm.getFingerPrintId().trim().isEmpty())
				&& (viewAttForm.getEmpCode()==null || viewAttForm.getEmpCode().trim().isEmpty())){
			errors.add("error", new ActionError("knowledgepro.employee.empId.or.Code.required"));
		}
		if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().trim().isEmpty()
				&& viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().trim().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.employee.empId.or.Code.required.either"));
		}
	}

	/**
	 * method to display previous month attendance
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewPreviousMonthAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		log.info("Entering into the viewPreviousMonthAttendance in ViewMyAttendanceAction");
		ViewMyAttendanceForm viewAttForm=(ViewMyAttendanceForm)form;
		ActionErrors errors = new ActionErrors();
		viewAttForm.setEmpPreviousAttTo(null);
		viewAttForm.setEmpPreviousLeaveTo(null);
		viewAttForm.setPrevious(true);
		setUserId(request,viewAttForm);
		try{
			if(viewAttForm.getYear()==null || viewAttForm.getYear().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.year.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE_PREVIOUS);
			}
			if(viewAttForm.getMonths()==null || viewAttForm.getMonths().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.month.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE_PREVIOUS);
			}
			List<EmpAttendanceTo> empAttPreviousTo=ViewMyAttendanceHandler.getInstance().getEmployeePreviousMonthAttendance(viewAttForm);
		//	List<EmpApplyLeaveTO> empLeavePreviousTo=ViewMyAttendanceHandler.getInstance().getEmployeePreviousLeaveDetails(viewAttForm);
			if(empAttPreviousTo==null || empAttPreviousTo.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				log.info("Exit viewPreviousMonthAttendance - viewMyAttendance size 0");
				return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE_PREVIOUS);
			}
			viewAttForm.setEmpPreviousAttTo(empAttPreviousTo);
		//	viewAttForm.setEmpPreviousLeaveTo(empLeavePreviousTo);
			}catch(Exception e){
				String msg = super.handleApplicationException(e);
				viewAttForm.setErrorMessage(msg);
				viewAttForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Exit from the viewPreviousMonthAttendance in ViewMyAttendanceAction");
		return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE_PREVIOUS);
	}
	

		/**
		 * method to redirect to initViewEmpAttendance jsp
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initViewEmployeeAttendance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered ViewMyAttendanceAction Batch input");
			ViewMyAttendanceForm viewMyAttendanceForm = (ViewMyAttendanceForm) form;
			viewMyAttendanceForm.resetFields();
			viewMyAttendanceForm.setFingerPrintId(null);
			viewMyAttendanceForm.setEmpCode(null);
			viewMyAttendanceForm.setEmpName(null);
			log.info("Exit Init ViewMyAttendanceAction input");
			
			return mapping.findForward(CMSConstants.VIEW_EMP_ATTENDANCE_INIT);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward getPreviousMonthsAttnDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			@SuppressWarnings("unused")
			ViewMyAttendanceForm viewMyAttendanceForm = (ViewMyAttendanceForm) form;
			viewMyAttendanceForm.setMonths(null);
			viewMyAttendanceForm.setEmpPreviousAttTo(null);
			viewMyAttendanceForm.setEmpPreviousLeaveTo(null);
			return mapping.findForward(CMSConstants.VIEW_MY_ATTENDANCE_PREVIOUS);
		}
}
