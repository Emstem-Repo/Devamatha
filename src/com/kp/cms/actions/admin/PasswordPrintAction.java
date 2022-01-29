package com.kp.cms.actions.admin;

import java.net.Inet4Address;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.PrintPasswordForm;
import com.kp.cms.handlers.admin.PasswordPrintHandler;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class PasswordPrintAction  extends BaseDispatchAction{
	private static final String CLASSMAP = "classMap";
	private static final Log log = LogFactory.getLog(PasswordPrintAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrintPassword(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintPasswordForm printPasswordForm=(PrintPasswordForm) form;
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		//IPADDRESS CHECK
		if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
			
			//return mapping.findForward("logout");
		}
	
		printPasswordForm.reset(mapping, request);
		setRequiredDataToForm(printPasswordForm,request);
		return mapping.findForward(CMSConstants.PRINT_PASSWORD);
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintPasswordForm prForm = (PrintPasswordForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = prForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("LogoBytes");
		
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.NO_RECORD_PASSWORD);
			}
			boolean isValidRegdNo;
			if(prForm.getRegNoFrom()!=null && !prForm.getRegNoFrom().isEmpty() && prForm.getRegNoTo() != null && !prForm.getRegNoTo().isEmpty())
			{
			isValidRegdNo = validateRegdNos(prForm.getRegNoFrom().trim(), prForm.getRegNoTo().trim()); 
			if(!isValidRegdNo){
				if(prForm.getIsRollNo()!= null && "true".equalsIgnoreCase(prForm.getIsRollNo())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_ROLLNO_TYPE));
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				}
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.NO_RECORD_PASSWORD);
			}
				PasswordPrintHandler.getInstance().getPasswordPrintDetails(prForm, request);
				if(prForm.getMessageList() == null || prForm.getMessageList().size() <= 0){
					return mapping.findForward(CMSConstants.NO_RECORD_PASSWORD);
				} 		
			}
			else if(prForm.getAcademicYear()!=null && !prForm.getAcademicYear().isEmpty() && prForm.getProgramm()!=null && !prForm.getProgramm().isEmpty() && prForm.getSemester()!=null && !prForm.getSemester().isEmpty())
			{
				PasswordPrintHandler.getInstance().getPasswordPrintDetailsByProgrammAndSemOrClass(prForm, request);
				if(prForm.getMessageList() == null || prForm.getMessageList().size() <= 0){
					return mapping.findForward(CMSConstants.NO_RECORD_PASSWORD);
				} 
			}
			else if(prForm.getAcademicYear()!=null && !prForm.getAcademicYear().isEmpty() && prForm.getClasses()!=null && !prForm.getClasses().isEmpty())
			{
				PasswordPrintHandler.getInstance().getPasswordPrintDetailsByProgrammAndSemOrClass(prForm, request);
				if(prForm.getMessageList() == null || prForm.getMessageList().size() <= 0){
					return mapping.findForward(CMSConstants.NO_RECORD_PASSWORD);
				}
			}
			
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				prForm.setErrorMessage(msg);
				prForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		return mapping.findForward(CMSConstants.SHOW_PASSWORD);
	
	}

	/**
	 * 
	 * @param regdNoFrom
	 * @param regdNoTo
	 * @return Used to validate regd nos.
	 * Only alphanumeric is allowed
	 * @throws Exception
	 */
	private boolean validateRegdNos(String regdNoFrom, String regdNoTo) throws Exception{
		log.info("Entering into validateRegdNos");
		if(StringUtils.isAlphanumeric(regdNoFrom) && StringUtils.isAlphanumeric(regdNoTo)){
			return true;
		}
		else{
			log.info("Leaving into validateRegdNos");
			return false;
		}
	}	
	
	public void setRequiredDataToForm(PrintPasswordForm printPasswordForm,HttpServletRequest request) throws Exception{
		try {
		if (printPasswordForm.getAcademicYear() == null || printPasswordForm.getAcademicYear().isEmpty()) {
			// Below statements is used to get the current year and for the
			// year get the class Map.
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);

			// code by hari
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			request.setAttribute(CLASSMAP, classMap);
			printPasswordForm.setAcademicYear(String.valueOf(currentYear));
		}
		// Otherwise get the classMap for the selected year
		else {
			int year = Integer.parseInt(printPasswordForm.getAcademicYear().trim());
			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
			request.setAttribute(CLASSMAP, classMap);
			printPasswordForm.setAcademicYear(String.valueOf(year));
		}
		List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
		Collections.sort(programList);
		printPasswordForm.setProgramToList(programList);
	} catch (Exception e) {
		log.error("Error occured at setClassMapToRequest in SubjectGroupDetailsAction",e);
		throw new ApplicationException(e);
	}
	}
}
