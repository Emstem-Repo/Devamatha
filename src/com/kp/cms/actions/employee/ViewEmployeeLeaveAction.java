package com.kp.cms.actions.employee;

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
import com.kp.cms.forms.employee.ViewEmployeeLeaveForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.ViewEmployeeLeaveHandler;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.transactions.employee.IApplyLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.ApplyLeaveTransactionImpl;

public class ViewEmployeeLeaveAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ApplyLeaveAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewEmployeeLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initEmpApplyLeave in ApplyLeaveAction");
		ViewEmployeeLeaveForm objform = (ViewEmployeeLeaveForm) form;
		objform.resetFields();
		objform.setListOfEmployee(CommonAjaxHandler.getInstance().getEmployeeCodeName("eCode"));
		//objform.setListOfEmployee(ViewEmployeeLeaveHandler.getInstance().getEmployeeNmaes());
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.VEW_EMPLOYEE_LEAVE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewEmployeeLeaveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initEmpApplyLeave in ApplyLeaveAction");
		ViewEmployeeLeaveForm objform = (ViewEmployeeLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			List<EmployeeLeaveTO> listOfEmployeeLeave=ViewEmployeeLeaveHandler.getInstance().getEmployeeNmaes(objform.getAcademicYear(),objform.getEmployeeName());
			if (listOfEmployeeLeave.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				objform.resetFields();
				String eCodeName = objform.geteCodeName();
				objform.setListOfEmployee(CommonAjaxHandler.getInstance().getEmployeeCodeName(eCodeName));
				log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
				return mapping.findForward(CMSConstants.VEW_EMPLOYEE_LEAVE);
			} 
			objform.setTemp(true);
			objform.setListOfEmployeeLeave(listOfEmployeeLeave);
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.VEW_EMPLOYEE_LEAVE_DETAILS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewMyLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.VEW_MY_LEAVE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ViewMyLeaveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initEmpApplyLeave in ApplyLeaveAction");
		ViewEmployeeLeaveForm objform = (ViewEmployeeLeaveForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request, objform);
		try {
			IApplyLeaveTransaction transaction=new ApplyLeaveTransactionImpl();
			int eid=transaction.getemployeeId(objform.getUserId());
			List<EmployeeLeaveTO> listOfEmployeeLeave=ViewEmployeeLeaveHandler.getInstance()
			.getEmployeeNmaes(objform.getAcademicYear(),
					String.valueOf(eid));
			  
			if (listOfEmployeeLeave.isEmpty()) {
				objform.resetFields();
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
				return mapping.findForward(CMSConstants.VEW_MY_LEAVE);
			} 
			objform.setTemp(false);
			objform.setListOfEmployeeLeave(listOfEmployeeLeave);
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("Exit from the initEmpApplyLeave in ApplyLeaveAction");
		return mapping.findForward(CMSConstants.VEW_EMPLOYEE_LEAVE_DETAILS);
	}

}
