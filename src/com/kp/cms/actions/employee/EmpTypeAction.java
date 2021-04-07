package com.kp.cms.actions.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.handlers.employee.EmpTypeHandler;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class EmpTypeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmpTypeAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("Entering into intEmpType");
		EmpTypeForm empTypeForm=(EmpTypeForm)form;
		getEmpTypeList(empTypeForm);
		empTypeForm.reset();
		return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
	}
	
	
	/**
	 * @param empTypeForm
	 * @throws Exception
	 */
	public void getEmpTypeList(EmpTypeForm empTypeForm)throws Exception{
		List<EmpTypeTo> emptypeTo=EmpTypeHandler.getInstance().getEmpTypeList(empTypeForm);
		if(emptypeTo!=null && emptypeTo.size()!=0)
			empTypeForm.setEmpTypeToList(emptypeTo);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addEmpType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("Entering into EmpTypeAction");
		EmpTypeForm empTypeForm=(EmpTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=empTypeForm.validate(mapping, request);
		validateTime(empTypeForm, errors);
		Boolean flag=false;
		EmpType empType=null;
		Map<String,EmpType> resetMap=null;
		if(errors.isEmpty()){
		try {
			setUserId(request, empTypeForm);
			EmpTypeHandler.getInstance().resetEmpType(empTypeForm);
				if(empTypeForm.getEmpTypeList()!=null){
				if(empTypeForm.getEmpTypeList().containsKey(empTypeForm.getName()))
					empType=empTypeForm.getEmpTypeList().get(empTypeForm.getName());
			}
			if(empType!=null && !empType.getIsActive()){
				getEmpTypeList(empTypeForm);
				if(empType.getId()>0)
					empTypeForm.setId(String.valueOf(empType.getId()));
					empTypeForm.reset();
					errors.add(CMSConstants.EMPLOYEE_TYPE_REACTIVATE, new ActionError(CMSConstants.EMPLOYEE_TYPE_REACTIVATE));
					saveErrors(request, errors);
			}else {
				flag=EmpTypeHandler.getInstance().addEmpType(empTypeForm);
				if(flag && empTypeForm.getId()==null){
					getEmpTypeList(empTypeForm);
					empTypeForm.resetId();
					empTypeForm.reset();
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_TYPE_ADDED));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
				}else if(flag && empTypeForm.getId()!=null && !empTypeForm.getId().isEmpty()){
					getEmpTypeList(empTypeForm);
					empTypeForm.resetId();
					empTypeForm.reset();
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_TYPE_UPDATE));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
				}else{
					getEmpTypeList(empTypeForm);
					empTypeForm.resetId();
					empTypeForm.reset();
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_TYPE_EXIST));
					saveMessages(request, messages);
					mapping.findForward(CMSConstants.INIT_EMP_TYPE);
				}
			}
		} catch (Exception exception) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else{
			saveErrors(request, errors);
		}
		log.info("Leaving addEmpType");
		return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editEmpType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		EmpTypeForm empTypeForm=(EmpTypeForm)form;
		try {
			setUserId(request, empTypeForm);
			EmpTypeHandler.getInstance().editEmpType(empTypeForm);
			empTypeForm.setButton("Update");
			request.setAttribute("editType","edit");
			return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
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
	public ActionForward deleteEmpType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		EmpTypeForm empTypeForm=(EmpTypeForm)form;
		ActionMessages messages = new ActionMessages();
		Boolean flag=false;
		try {
			setUserId(request, empTypeForm);
			flag=EmpTypeHandler.getInstance().deleteEmpType(empTypeForm);
			if(flag){
				getEmpTypeList(empTypeForm);
				empTypeForm.resetId();
				empTypeForm.reset();
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_TYPE_DELETE));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
			}else{
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_TYPE_EXIST));
				saveMessages(request, messages);
				mapping.findForward(CMSConstants.INIT_EMP_TYPE);
			}
		} catch (Exception exception) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving addEmpType");
		return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetEmpType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		EmpTypeForm empTypeForm=(EmpTypeForm)form;
		ActionMessages messages=new ActionMessages();
		boolean flag=false;
		try{
//		EmpTypeHandler.getInstance().resetEmpType(empTypeForm);
		flag=EmpTypeHandler.getInstance().addEmpType(empTypeForm);
		if(flag){
			getEmpTypeList(empTypeForm);
			empTypeForm.resetId();
			empTypeForm.reset();
			messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMPLOYEE_TYPE_RESTORE));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
		}else{
			getEmpTypeList(empTypeForm);
			empTypeForm.resetId();
			empTypeForm.reset();
			messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_TYPE_NOT_RESTORE));
			saveMessages(request, messages);
			mapping.findForward(CMSConstants.INIT_EMP_TYPE);
		}
		}catch (Exception e) {
			// TODO: handle exception
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EMP_TYPE);
	}
	
	
	/**
	 * @param form
	 * @param errors
	 */
	public void validateTime(EmpTypeForm form,ActionErrors errors){
		
		if(CommonUtil.checkForEmpty(form.getTimeIn())){
			if(!StringUtils.isNumeric(form.getTimeIn())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeInMin())){
			if(!StringUtils.isNumeric(form.getTimeInMin())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeInEnds())){
			if(!StringUtils.isNumeric(form.getTimeInEnds())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeInEndMIn())){
			if(!StringUtils.isNumeric(form.getTimeInEndMIn())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeOut())){
			if(!StringUtils.isNumeric(form.getTimeOut())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeOutMin())){
			if(!StringUtils.isNumeric(form.getTimeOutMin())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getSaturdayTimeOut())){
			if(!StringUtils.isNumeric(form.getSaturdayTimeOut())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getSaturdayTimeOutMin())){
			if(!StringUtils.isNumeric(form.getSaturdayTimeOutMin())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDayStartTime())){
			if(!StringUtils.isNumeric(form.getHalfDayStartTime())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDayStartTimeMin())){
			if(!StringUtils.isNumeric(form.getHalfDayStartTimeMin())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDatyEndTime())){
			if(!StringUtils.isNumeric(form.getHalfDatyEndTime())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDayEndTimeMin())){
			if(!StringUtils.isNumeric(form.getHalfDayEndTimeMin())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeIn())){
			if(Integer.parseInt(form.getTimeIn())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeInEnds())){
			if(Integer.parseInt(form.getTimeInEnds())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeOut())){
			if(Integer.parseInt(form.getTimeOut())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getSaturdayTimeOut())){
			if(Integer.parseInt(form.getSaturdayTimeOut())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDayStartTime())){
			if(Integer.parseInt(form.getHalfDayStartTime())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDatyEndTime())){
			if(Integer.parseInt(form.getHalfDatyEndTime())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeInMin())){
			if(Integer.parseInt(form.getTimeInMin())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeInEndMIn())){
			if(Integer.parseInt(form.getTimeInEndMIn())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeOutMin())){
			if(Integer.parseInt(form.getTimeOutMin())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getSaturdayTimeOutMin())){
			if(Integer.parseInt(form.getSaturdayTimeOutMin())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(form.getHalfDayStartTimeMin())){
			if(Integer.parseInt(form.getHalfDayStartTimeMin())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(form.getHalfDayEndTimeMin())){
			if(Integer.parseInt(form.getHalfDayEndTimeMin())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		Integer h1=0;
		Integer h2=0;
		Integer m1=0;
		Integer m2=0;
		if(CommonUtil.checkForEmpty(form.getTimeIn()) && CommonUtil.checkForEmpty(form.getTimeInMin()) 
				&& CommonUtil.checkForEmpty(form.getTimeOut()) && CommonUtil.checkForEmpty(form.getTimeOutMin())){
			h1=Integer.parseInt(form.getTimeIn());
			h2=Integer.parseInt(form.getTimeOut());
			m1=Integer.parseInt(form.getTimeInMin());
			m2=Integer.parseInt(form.getTimeOutMin());
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
		
		if(CommonUtil.checkForEmpty(form.getTimeIn()) && CommonUtil.checkForEmpty(form.getTimeInMin())
				&& CommonUtil.checkForEmpty(form.getTimeInEnds()) && CommonUtil.checkForEmpty(form.getTimeInEndMIn())){
			h1=Integer.parseInt(form.getTimeIn());
			h2=Integer.parseInt(form.getTimeInEnds());
			m1=Integer.parseInt(form.getTimeInMin());
			m2=Integer.parseInt(form.getTimeInEndMIn());
			if(h1<24 && h2<24){
				if(m1<60 && m2<60){
					if(h1>h2){
						/*
						m1=(m1==0)?60:m1;
						m2=(m2==0)?60:m2;
						if(m1>m2)*/
							errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_TIMEINEND));	
					}else if(h1==h2){
						if(m1>m2)
							errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_TIMEINEND));	
						
					}
				}
			}
			
		}
		
		if(CommonUtil.checkForEmpty(form.getTimeIn()) && CommonUtil.checkForEmpty(form.getTimeInMin())
				&& CommonUtil.checkForEmpty(form.getSaturdayTimeOut()) && CommonUtil.checkForEmpty(form.getSaturdayTimeOutMin())){
			h1=Integer.parseInt(form.getTimeIn());
			h2=Integer.parseInt(form.getSaturdayTimeOut());
			m1=Integer.parseInt(form.getTimeInMin());
			m2=Integer.parseInt(form.getSaturdayTimeOutMin());
			if(h1<24 && h2<24){
				if(m1<60 && m2<60){
					if(h1>h2){
						/*m1=(m1==0)?60:m1;
						m2=(m2==0)?60:m2;
						if(m1>m2)*/
							errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_SATTIMEOUT));	
					}else if(h1==h2){
						if(m1>m2)
							errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_SATTIMEOUT));	
					}
					
				}
			}
		}
			
			if(CommonUtil.checkForEmpty(form.getHalfDayStartTime()) && CommonUtil.checkForEmpty(form.getHalfDayStartTimeMin())
					&& CommonUtil.checkForEmpty(form.getHalfDatyEndTime()) && CommonUtil.checkForEmpty(form.getHalfDayEndTimeMin())){
				h1=Integer.parseInt(form.getHalfDayStartTime());
				h2=Integer.parseInt(form.getHalfDatyEndTime());
				m1=Integer.parseInt(form.getHalfDayStartTimeMin());
				m2=Integer.parseInt(form.getHalfDayEndTimeMin());
				if(h1<24 && h2<24){
					if(m1<60 && m2<60){
						if(h1>h2){
							/*m1=(m1==0)?60:m1;
							m2=(m2==0)?60:m2;
							if(m1>m2)*/
								errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_HALFDAY_LESS));	
						}else if(h1==h2){
							if(m1>m2)
								errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_HALFDAY_LESS));	
							
						}
					}
				}
		}
	}
}	
	



