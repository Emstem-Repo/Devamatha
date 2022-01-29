package com.kp.cms.actions.employee;

import java.util.Iterator;
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
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.employee.HodApproverForm;
import com.kp.cms.handlers.employee.EmployeeApproverHandler;
import com.kp.cms.handlers.employee.EmployeeOnlineLeaveHandler;
import com.kp.cms.handlers.employee.HodApproverHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.utilities.CommonUtil;

public class HodApproverAction extends BaseDispatchAction {
		
		private static final Log log = LogFactory.getLog(HodApproverAction.class);
		
		public ActionForward initHodApproverPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered initPage");
			HodApproverForm hodApproverForm = (HodApproverForm) form;
			hodApproverForm.resetFields();
			try{
				setDataToForm(hodApproverForm);
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				hodApproverForm.setErrorMessage(msg);
				hodApproverForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("Exit initOnlineLeave");
			return mapping.findForward(CMSConstants.INIT_HOD_LEAVE_APPROVER);
		}
		
		public ActionForward getHodDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered initPage");
			HodApproverForm hodApproverForm = (HodApproverForm) form;
			ActionErrors errors = new ActionErrors();
			try{
				if(hodApproverForm.getDepartmentId() == null || hodApproverForm.getDepartmentId().isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Department "));
				}
				if(errors.isEmpty()){
					List<EmployeeTO> employeeToList = HodApproverHandler.getInstance().getEmployeeDetailsDeptWiseForHodApprover(hodApproverForm);
					hodApproverForm.setEmployeeToList(employeeToList);
					Map<Integer, String> employeeMap = EmployeeOnlineLeaveHandler.getInstance().getEmployeeMap();
					employeeMap = CommonUtil.sortMapByValue(employeeMap);
					request.getSession().setAttribute("approversMap", employeeMap);
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_HOD_LEAVE_APPROVER);
				}
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				hodApproverForm.setErrorMessage(msg);
				hodApproverForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("Exit initOnlineLeave");
			return mapping.findForward(CMSConstants.INIT_HOD_LEAVE_APPROVER);
		}
		public ActionForward saveHodDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered initPage");
			HodApproverForm hodApproverForm = (HodApproverForm) form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			setUserId(request, hodApproverForm);
			int count=0;
			try{
				if(hodApproverForm.getApproverId() == null || hodApproverForm.getApproverId().isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Approver "));
				}
				if(errors.isEmpty()){
					List<EmployeeTO> employeeToList = hodApproverForm.getEmployeeToList();
					Iterator<EmployeeTO> iterator = employeeToList.iterator();
					while (iterator.hasNext()) {
						EmployeeTO employeeTO = (EmployeeTO) iterator.next();
						if(employeeTO.getChecked1()!=null && !employeeTO.getChecked1().isEmpty() ){
							count++;
						}
					}
					if(count==0){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.select.record"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_HOD_LEAVE_APPROVER);
					}
					boolean save = HodApproverHandler.getInstance().saveHodDetails(employeeToList,hodApproverForm);
					if(save){
						hodApproverForm.setEmployeeToList(null);
						List<EmployeeTO> employeeToList1 = HodApproverHandler.getInstance().getEmployeeDetailsDeptWiseForHodApprover(hodApproverForm);
						hodApproverForm.setEmployeeToList(employeeToList1);
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.addsuccess","Approver "));
						saveMessages(request, messages);
					}
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_HOD_LEAVE_APPROVER);
				}
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				hodApproverForm.setErrorMessage(msg);
				hodApproverForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("Exit initOnlineLeave");
			return mapping.findForward(CMSConstants.INIT_HOD_LEAVE_APPROVER);
		}
		
		public void setUserId(HttpServletRequest request, BaseActionForm form){
			HttpSession session = request.getSession(false);
			if(session.getAttribute("uid")!=null){
				form.setUserId(session.getAttribute("uid").toString());
			}
			request.getSession().removeAttribute("baseActionForm");
		}
		
		private void setDataToForm(HodApproverForm hodApproverForm) throws Exception {
			Map<Integer, String> departmentMap=UserInfoHandler.getInstance().getDepartment();
			hodApproverForm.setDepartmentMap(departmentMap);
		}

}
