package com.kp.cms.actions.employee;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmpLeaveTypeForm;
import com.kp.cms.handlers.employee.EmpLeaveTypeHandler;

public class EmpLeaveTypeAction extends BaseDispatchAction {
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpLeaveType(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		EmpLeaveTypeForm empLeaveTypeForm=(EmpLeaveTypeForm)form;
		empLeaveTypeForm.clear();
		setUserId(request, empLeaveTypeForm);
		setDataToForm(empLeaveTypeForm);
		return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
	}

	/**
	 * @param empLeaveTypeForm
	 * @throws Exception
	 */
	private void setDataToForm(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		EmpLeaveTypeHandler.getInstance().getEmpLeaveTypeData(empLeaveTypeForm);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitEvent(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		EmpLeaveTypeForm empLeaveTypeForm=(EmpLeaveTypeForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=empLeaveTypeForm.validate(mapping, request);
		EmpLeaveType leaveType=null;
		boolean flag=false;
		if(errors.isEmpty()){
			try{
				if(empLeaveTypeForm.getLeaveType()!=null && !empLeaveTypeForm.getLeaveType().isEmpty()){
					String name=empLeaveTypeForm.getLeaveType().toUpperCase();
					Map<String,EmpLeaveType> leaveNameMap=EmpLeaveTypeHandler.getInstance().getEmpLeaveTypeNames(empLeaveTypeForm);
					if(leaveNameMap!=null){
						if(leaveNameMap.containsKey(name))
						leaveType=leaveNameMap.get(name);
					}
				}
					if(leaveType!=null && !leaveType.getIsActive()){
						empLeaveTypeForm.setEmpLeaveType(leaveType);
						errors.add(CMSConstants.EMPLOYEE_LEAVE_REACTIVE,new ActionError(CMSConstants.EMPLOYEE_LEAVE_REACTIVE));
						saveErrors(request, errors);
					}else if(leaveType!=null && leaveType.getIsActive()
							 && empLeaveTypeForm.getEditOrReset()!=null && !empLeaveTypeForm.getEditOrReset().isEmpty()&& !empLeaveTypeForm.getEditOrReset().equalsIgnoreCase("edit")){
						errors.add(CMSConstants.EMPLOYEE_LEAVE_EXIST,new ActionError(CMSConstants.EMPLOYEE_LEAVE_EXIST));
						saveErrors(request, errors);
					}else if(empLeaveTypeForm.getEditOrReset()!=null && !empLeaveTypeForm.getEditOrReset().isEmpty() && empLeaveTypeForm.getEditOrReset().equalsIgnoreCase("edit")){
						if(leaveType!=null && leaveType.getId()>0){
							if(empLeaveTypeForm.getId()!=null && !empLeaveTypeForm.getId().isEmpty())
							if(leaveType.getId()==Integer.parseInt(empLeaveTypeForm.getId())){
								if(empLeaveTypeForm.getCode()!=null && !empLeaveTypeForm.getCode().isEmpty()&& empLeaveTypeForm.getCodeMap()!=null ){
									String code="";
									if(empLeaveTypeForm.getCodeMap().containsKey(empLeaveTypeForm.getId()))
										code=empLeaveTypeForm.getCodeMap().get(empLeaveTypeForm.getId());
									if(!code.isEmpty() && !empLeaveTypeForm.getCode().equalsIgnoreCase(code) && empLeaveTypeForm.getCodeMap().containsValue(empLeaveTypeForm.getCode())){
										errors.add(CMSConstants.EMP_CODE_EXISTS,new ActionError(CMSConstants.EMP_CODE_EXISTS));
										saveErrors(request, errors);
										return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
									}
								}
								
								flag=EmpLeaveTypeHandler.getInstance().saveLeaveType(empLeaveTypeForm);
								if(flag){
									empLeaveTypeForm.setEditOrReset("Add");
									messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMPLOYEE_LEAVE_UPDATE));
									saveMessages(request, messages);
								}
							}else{
								errors.add(CMSConstants.EMPLOYEE_LEAVE_EXIST,new ActionError(CMSConstants.EMPLOYEE_LEAVE_EXIST));
								saveErrors(request, errors);
							}
						}else{
							if(empLeaveTypeForm.getCode()!=null && !empLeaveTypeForm.getCode().isEmpty()&& empLeaveTypeForm.getCodeMap()!=null ){
								String code="";
								if(empLeaveTypeForm.getCodeMap().containsKey(empLeaveTypeForm.getId()))
									code=empLeaveTypeForm.getCodeMap().get(empLeaveTypeForm.getId());
								if(!code.isEmpty() && !empLeaveTypeForm.getCode().equalsIgnoreCase(code) && empLeaveTypeForm.getCodeMap().containsValue(empLeaveTypeForm.getCode())){
									errors.add(CMSConstants.EMP_CODE_EXISTS,new ActionError(CMSConstants.EMP_CODE_EXISTS));
									saveErrors(request, errors);
									return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
								}
							}
							
							flag=EmpLeaveTypeHandler.getInstance().saveLeaveType(empLeaveTypeForm);
							if(flag){
								empLeaveTypeForm.setEditOrReset("Add");
								messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMPLOYEE_LEAVE_UPDATE));
								saveMessages(request, messages);
							}
						}
					}else{
						if(empLeaveTypeForm.getCodeMap()!=null && empLeaveTypeForm.getCode()!=null 
								&& !empLeaveTypeForm.getCode().isEmpty()&& empLeaveTypeForm.getCodeMap().containsValue(empLeaveTypeForm.getCode())){
							errors.add(CMSConstants.EMP_CODE_EXISTS,new ActionError(CMSConstants.EMP_CODE_EXISTS));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
						}
						flag=EmpLeaveTypeHandler.getInstance().saveLeaveType(empLeaveTypeForm);
						if(flag){
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMPLOYEE_LEAVE_ADDED));
							saveMessages(request, messages);
						}
					}
				
			}catch (Exception exception) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			empLeaveTypeForm.clear();
		}else{
			saveErrors(request, errors);
		}
		setDataToForm(empLeaveTypeForm);
		
		return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetEventType(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		EmpLeaveTypeForm empLeaveTypeForm=(EmpLeaveTypeForm)form;
		ActionMessages messages=new ActionMessages();
		boolean flag=false;
		empLeaveTypeForm.setEditOrReset("reset");
		flag=EmpLeaveTypeHandler.getInstance().saveLeaveType(empLeaveTypeForm);
		if(flag){
			empLeaveTypeForm.setEditOrReset("Add");
			messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMPLOYEE_LEAVE_RESTORE));
			saveMessages(request, messages);
		}
		setDataToForm(empLeaveTypeForm);
		return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editEmpLeaveType(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		EmpLeaveTypeForm empLeaveTypeForm=(EmpLeaveTypeForm)form;
		request.setAttribute("eventEdit","edit");
		EmpLeaveTypeHandler.getInstance().getEmpLeaveEdit(empLeaveTypeForm);
		return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteEvent(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		EmpLeaveTypeForm empLeaveTypeForm=(EmpLeaveTypeForm)form;
		ActionMessages messages=new ActionMessages();
		boolean flag=false;
		flag=EmpLeaveTypeHandler.getInstance().getEmpLeaveDelete(empLeaveTypeForm);
		if(flag){
			messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMPLOYEE_LEAVE_DELETE));
			saveMessages(request, messages);
		}
		setDataToForm(empLeaveTypeForm);
		return mapping.findForward(CMSConstants.EMPLOYEE_LEAVE_TYPE);
	}

}
