package com.kp.cms.actions.employee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.ShiftEntry;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.forms.employee.ShiftEntryForm;
import com.kp.cms.handlers.employee.EmpTypeHandler;
import com.kp.cms.handlers.employee.ShiftEntryHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.to.employee.ShiftEntryTo;
import com.kp.cms.transactions.employee.IEmpTypeTransaction;
import com.kp.cms.transactionsimpl.employee.EmpTypeTransaction;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class ShiftEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ShiftEntryAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initShiftEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("Entering into intEmpType");
		ShiftEntryForm shiftEntryForm=(ShiftEntryForm)form;
		//HttpSession session = request.getSession();
		shiftEntryForm.reset();
		setUserId(request, shiftEntryForm);
		setDataToForm(shiftEntryForm,request);
		return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
	}
	
	
	/**
	 * @param shiftEntryForm
	 * @param request 
	 * @throws Exception 
	 */
	private void setDataToForm(ShiftEntryForm shiftEntryForm, HttpServletRequest request) throws Exception {
		Map<Integer, String> employeeMap =  ShiftEntryHandler.getInstance().getEmployeeList();
		HttpSession session = request.getSession();
		if(employeeMap != null){
			session .setAttribute("employeeMap", employeeMap);
		}else{
			session.setAttribute("employeeMap",new HashMap<Integer, String>());
		}
		List<ShiftEntryTo> tos = ShiftEntryHandler.getInstance().getShiftEntrys();
		shiftEntryForm.setEntryTos(tos);
		List<EmployeeTO> emplist = ShiftEntryHandler.getInstance().getEmpEntryList();
		shiftEntryForm.setEmpList(emplist);
		shiftEntryForm.setEmployeeId("");
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.actions.BaseDispatchAction#setUserId(javax.servlet.http.HttpServletRequest, com.kp.cms.forms.BaseActionForm)
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addShiftEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("Entering into addShiftEntry");
		ShiftEntryForm shiftEntryForm=(ShiftEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=shiftEntryForm.validate(mapping, request);
		validateTime(shiftEntryForm, errors);
		Boolean flag=false;
		if(errors.isEmpty()){
			try {	
					IEmpTypeTransaction empTypeTransaction=EmpTypeTransaction.getInstance();
					List<ShiftEntry> duplicateList = empTypeTransaction.getEmpShiftEntryList(shiftEntryForm.getEmployeeId(),"duplicate");
					if(duplicateList != null && !duplicateList.isEmpty()){
						errors.add(CMSConstants.EMPLOYEE_TYPE_REACTIVATE, new ActionError("knowledgepro.emp.shift.type.exist"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
					}
					List<ShiftEntry> list = empTypeTransaction.getEmpShiftEntryList(shiftEntryForm.getEmployeeId(),"reActivate");
					if(list != null && !list.isEmpty()){
						errors.add(CMSConstants.EMPLOYEE_TYPE_REACTIVATE, new ActionError("knowledgepro.employee.shift.reactivate"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
					}
					flag = ShiftEntryHandler.getInstance().saveShiftEntry(shiftEntryForm,"add");
					if(flag){
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.emp.shift.added.success"));
						saveMessages(request, messages);
						setDataToForm(shiftEntryForm, request);
						return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
					}
			}catch (Exception exception) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
		}
		log.info("Leaving addShiftEntry");
		return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateShiftEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("Entering into addShiftEntry");
		ShiftEntryForm shiftEntryForm=(ShiftEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=shiftEntryForm.validate(mapping, request);
		validateTime(shiftEntryForm, errors);
		Boolean flag=false;
		if(errors.isEmpty()){
			try {	
					flag = ShiftEntryHandler.getInstance().saveShiftEntry(shiftEntryForm,"update");
					if(flag){
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.emp.shift.update.success"));
						saveMessages(request, messages);
						setDataToForm(shiftEntryForm, request);
						return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
					}
			}catch (Exception exception) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
			shiftEntryForm.setButton("Update");
			request.setAttribute("editType","edit");
		}
		log.info("Leaving addShiftEntry");
		return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editShiftEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShiftEntryForm shiftEntryForm=(ShiftEntryForm)form;
		try {
			ShiftEntryHandler.getInstance().editShiftEntry(shiftEntryForm);
			shiftEntryForm.setButton("Update");
			request.setAttribute("editType","edit");
			return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
		} catch (Exception exception) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
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
	public ActionForward deleteShiftEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShiftEntryForm shiftEntryForm=(ShiftEntryForm)form;
		ActionMessages messages = new ActionMessages();
		Boolean flag=false;
		try {
			setUserId(request, shiftEntryForm);
			flag=ShiftEntryHandler.getInstance().deleteShiftEntry(shiftEntryForm);
			if(flag){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.emp.shift.delete.success"));
				saveMessages(request, messages);
				setDataToForm(shiftEntryForm, request);
				return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
			}
		} catch (Exception exception) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving addEmpType");
		setDataToForm(shiftEntryForm, request);
		return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetShiftEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShiftEntryForm shiftEntryForm=(ShiftEntryForm)form;
		ActionMessages messages=new ActionMessages();
		boolean flag=false;
		try{
			flag = ShiftEntryHandler.getInstance().saveShiftEntry(shiftEntryForm,"reActivate");
			if(flag){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.shift.restore"));
				saveMessages(request, messages);
				setDataToForm(shiftEntryForm, request);
				return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
			}
		}catch (Exception e) {
			// TODO: handle exception
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SHIFT_TYPE);
	}
	
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateTime(ShiftEntryForm shiftEntryForm,ActionErrors errors){
		List<ShiftEntryTo> list = shiftEntryForm.getEntryTos();
		
		if(shiftEntryForm.getEmployeeId() == null || shiftEntryForm.getEmployeeId().isEmpty()){
			errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.employee.shift.employee.required"));
		}
		if(list !=  null){
			Iterator<ShiftEntryTo> iterator = list.iterator();
			while (iterator.hasNext()) {
				ShiftEntryTo shiftEntryTo = (ShiftEntryTo) iterator.next();
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeIn())){
					if(!StringUtils.isNumeric(shiftEntryTo.getTimeIn())){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
						}
					}			
				}
				
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeInMin())){
					if(!StringUtils.isNumeric(shiftEntryTo.getTimeInMin())){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
						}
					}			
				}
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeOut())){
					if(!StringUtils.isNumeric(shiftEntryTo.getTimeOut())){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
						}
					}			
				}
				
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeOutMin())){
					if(!StringUtils.isNumeric(shiftEntryTo.getTimeOutMin())){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
						}
					}			
				}
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeIn())){
					if(Integer.parseInt(shiftEntryTo.getTimeIn())>=24){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
						}
					}			
				}
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeOut())){
					if(Integer.parseInt(shiftEntryTo.getTimeOut())>=24){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
						}
					}			
				}
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeInMin())){
					if(Integer.parseInt(shiftEntryTo.getTimeInMin())>=60){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
						}
					}			
				}
				
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeOutMin())){
					if(Integer.parseInt(shiftEntryTo.getTimeOutMin())>=60){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
							errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
						}
					}			
				}
				Integer h1=0;
				Integer h2=0;
				Integer m1=0;
				Integer m2=0;
				if(CommonUtil.checkForEmpty(shiftEntryTo.getTimeIn()) && CommonUtil.checkForEmpty(shiftEntryTo.getTimeInMin()) 
						&& CommonUtil.checkForEmpty(shiftEntryTo.getTimeOut()) && CommonUtil.checkForEmpty(shiftEntryTo.getTimeOutMin())){
					h1=Integer.parseInt(shiftEntryTo.getTimeIn());
					h2=Integer.parseInt(shiftEntryTo.getTimeOut());
					m1=Integer.parseInt(shiftEntryTo.getTimeInMin());
					m2=Integer.parseInt(shiftEntryTo.getTimeOutMin());
					if(h1<24 && h2<24){
						if(m1<60 && m2<60){
							if(h1>h2){
								/*m1=(m1==0)?60:m1;
						m2=(m2==0)?60:m2;
						if(m1>=m2)*/
								errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_LESS));	
							}else if(h1==h2){
								if(m1>m2)
									errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_LESS));	
							}
						}
					}
				}
			}
		}
	}
}	
	



