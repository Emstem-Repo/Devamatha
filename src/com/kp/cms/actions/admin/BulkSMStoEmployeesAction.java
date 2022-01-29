package com.kp.cms.actions.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.BulkSMStoEmployeesForm;
import com.kp.cms.handlers.admin.BulkSMStoEmployeesHandler;
import com.kp.cms.handlers.admin.BulkSMStoStudentHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class BulkSMStoEmployeesAction  extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(BulkSMStoEmployeesAction.class);
	/**
	 *  sending generic sms to selected class students parent mobile number
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	
	public ActionForward initBulkSMStoEmployee(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of initBulkSMStoEmployee method in BulkSMStoEmployeesAction.class");
		BulkSMStoEmployeesForm bulkSMStoEmployeesForm =(BulkSMStoEmployeesForm)form;
		ActionMessages messages = new ActionMessages();
		try
		{
			bulkSMStoEmployeesForm.clearAll();
			setRequiredDataToForm(request,bulkSMStoEmployeesForm);
		}
		catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			bulkSMStoEmployeesForm.setErrorMessage(msg);
			bulkSMStoEmployeesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in BulkSmsToStudentAction.class.." + e.getMessage());
			String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		
		log.debug("end of initBulkSMStoEmployee method in BulkSMStoEmployeesAction.class");
		return mapping.findForward(CMSConstants.BULK_SMS_TO_EMPLOYEE);
	}
	private void setRequiredDataToForm(HttpServletRequest request,BulkSMStoEmployeesForm bulkSMStoEmployeesForm)throws Exception 
	{
		log.debug("call of setRequiredDataToForm method  in BulkSMStoEmployeesAction.class");
		try
		{
		Map<Integer, String> rollMap = new HashMap<Integer, String>();
		rollMap =  BulkSMStoEmployeesHandler.getInstance().getRollMap();
		bulkSMStoEmployeesForm.setRollMap(rollMap);
		}
		catch (Exception e) {
			throw new ApplicationException(e);
		}
		log.debug("end of setRequiredDataToForm method  in BulkSMStoEmployeesAction.class");
		
	}
	
	public ActionForward getEmployeeList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of getEmployeeList method in BulkSMStoEmployeesAction.class");
		BulkSMStoEmployeesForm bulkSMStoEmployeesForm =(BulkSMStoEmployeesForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = bulkSMStoEmployeesForm.validate(mapping, request);
		try
		{
			if(errors.isEmpty())
			{
				
				List<UserInfoTO> userList = new ArrayList<UserInfoTO>();
				userList =  BulkSMStoEmployeesHandler.getInstance().getEmployeeList(bulkSMStoEmployeesForm);
				if(userList!=null && userList.size()>0)
				{
					String rollNames ="";
					for(String id :bulkSMStoEmployeesForm.getRolls())
					{
						rollNames = rollNames + bulkSMStoEmployeesForm.getRollMap().get(Integer.parseInt(id))+ ",";
					}
					bulkSMStoEmployeesForm.setRollNames(rollNames);
					bulkSMStoEmployeesForm.setUserList(userList);
					return mapping.findForward(CMSConstants.BULK_SMS_TO_EMPLOYEE_SECOND_PAGE);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSMStoEmployeesForm.clearAll();
					setRequiredDataToForm(request, bulkSMStoEmployeesForm);
					return mapping.findForward(CMSConstants.BULK_SMS_TO_EMPLOYEE);
				}
				
				
			}
			else
			{
				addErrors(request, errors);
				setRequiredDataToForm(request,bulkSMStoEmployeesForm);
				log.error("Exit getEmployeeList - BulkSMStoEmployeesAction.class");
				return mapping.findForward(CMSConstants.BULK_SMS_TO_EMPLOYEE);
			}
		}
		catch (Exception e) {
			addErrors(request, errors);
			setRequiredDataToForm(request,bulkSMStoEmployeesForm);
			log.error("Exit getEmployeeList - BulkSMStoEmployeesAction.class");
			return mapping.findForward(CMSConstants.BULK_SMS_TO_EMPLOYEE);
		}
	}
	public ActionForward sendBulkSMStoEmployee(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of sendBulkSMStoEmployee method in BulkSMStoEmployeesAction.class");
		BulkSMStoEmployeesForm bulkSMStoEmployeesForm =(BulkSMStoEmployeesForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = bulkSMStoEmployeesForm.validate(mapping, request);
		boolean isSmsSent=false;
		int userConuts =0;
		try
		{
			if(!errors.isEmpty())
			{
				addErrors(request, errors);

				setRequiredDataToForm(request,bulkSMStoEmployeesForm);
				log.error("Exit sendBulkSMStoStudent - BulkSmsToStudentAction.class");
			}
			else
			{
				// sent
				List<UserInfoTO> usersLiseNew = new ArrayList<UserInfoTO>();
				Iterator<UserInfoTO> iterator = bulkSMStoEmployeesForm.getUserList().iterator();
				while(iterator.hasNext())
				{
					UserInfoTO  infoTO = iterator.next();
					if(infoTO.isChecked())
					{
						usersLiseNew.add(infoTO);
						userConuts = userConuts+1;
					}
				}
				if(userConuts<=0)
				{
					ActionMessage message = new ActionMessage("knowledgepro.new.attendance.new.sms.sending.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSMStoEmployeesForm.clearAll();
					setRequiredDataToForm(request, bulkSMStoEmployeesForm);
				}
				else
				{

					isSmsSent  = BulkSMStoEmployeesHandler.getInstance().sentSMStoEmployee(bulkSMStoEmployeesForm,usersLiseNew);
				}


				if(isSmsSent)
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.bulk.sms.employee.sms.sending.success",userConuts);
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSMStoEmployeesForm.clearAll();
					setRequiredDataToForm(request, bulkSMStoEmployeesForm);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.sms.marks.student.failure");
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSMStoEmployeesForm.clearAll();
					setRequiredDataToForm(request, bulkSMStoEmployeesForm);
				}
			}
		}
		catch (Exception e) {
			
		}
		
		log.debug("end of sendBulkSMStoEmployee method in BulkSMStoEmployeesAction.class");
		return mapping.findForward(CMSConstants.BULK_SMS_TO_EMPLOYEE);
	}
	
	
}
