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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.ResetLeavesForm;
import com.kp.cms.handlers.employee.ApplyLeaveHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandler;
import com.kp.cms.handlers.employee.ResetLeavesHandler;
import com.kp.cms.helpers.employee.ResetLeavesHelper;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;
import com.kp.cms.transactions.employee.IResetLeavesTransaction;
import com.kp.cms.transactionsimpl.employee.ResetLeavesTransactionImpl;

public class ResetLeavesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ResetLeavesAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initResetLeaves(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initResetLeaves input");
		ResetLeavesForm resetLeavesForm = (ResetLeavesForm) form;
		resetLeavesForm.resetFields();
		setRequiredDatatoForm(resetLeavesForm, request);
		log.info("Exit initResetLeaves input");
		return mapping.findForward(CMSConstants.INIT_RESET_LEAVES);
	}

	/**
	 * @param resetLeavesForm
	 * @param request
	 */
	private void setRequiredDatatoForm(ResetLeavesForm resetLeavesForm,
			HttpServletRequest request) throws Exception{
		List<EmpLeaveTypeTO> leaveList=ApplyLeaveHandler.getInstance().getLeaveTypeList();
		resetLeavesForm.setLeaveList(leaveList);
		List<EmployeeKeyValueTO> listEmployeeType = EmployeeInfoHandler.getInstance().getEmployeeType();
		if (listEmployeeType != null && listEmployeeType.size() > 0) {
			resetLeavesForm.setListEmployeeType(listEmployeeType);
		}
	}
	/**
	 * Method to resetLeavesOFEmployee based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetLeavesOFEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ScoreSheetAction - getCandidates");
		ResetLeavesForm resetLeavesForm = (ResetLeavesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = resetLeavesForm.validate(mapping, request);
		setUserId(request, resetLeavesForm);
		if(errors.isEmpty()){
			String empInitQuery=ResetLeavesHelper.getInstance().getEmpInitalizeQuery(resetLeavesForm);
			IResetLeavesTransaction transaction=new ResetLeavesTransactionImpl();
			List<EmpInitializeLeaves> initList=transaction.getEmpInitializeLeavesByEmpTypeId(empInitQuery);
			if(initList==null || initList.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.reset.leave.initilize.required"));
			}
		}
		if (errors.isEmpty()) {
			try {
				boolean isUpdated=ResetLeavesHandler.getInstance().updateResetLeavesOfEmployee(resetLeavesForm);
				if (!isUpdated) {
					resetLeavesForm.resetFields();
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.leaves.reseted.failure"));
					saveErrors(request, errors);
				} else{
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.employee.leaves.reseted.successfully"));
					saveMessages(request, messages);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				resetLeavesForm.setErrorMessage(msg);
				resetLeavesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(resetLeavesForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_RESET_LEAVES);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		setRequiredDatatoForm(resetLeavesForm, request);
		return mapping.findForward(CMSConstants.INIT_RESET_LEAVES);
	}
}