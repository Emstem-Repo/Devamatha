package com.kp.cms.actions.admin;
import java.net.Inet4Address;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.GeneratePasswordForm;
import com.kp.cms.handlers.admin.GeneratePasswordHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;

/**
 * 
 *
 * Class implemented to generate login credentials for students and parents
 * 
 */
public class GeneratePasswordAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(GeneratePasswordAction.class);
	/**
	 * initializes generate password search
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGeneratePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForm gnForm=(GeneratePasswordForm)form;
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		//IPADDRESS CHECK
		if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
			
			//return mapping.findForward("logout");
		}
		
		try {
			// initialize program type
			setUserId(request, gnForm);
			gnForm.setProgramTypeId(null);
			gnForm.setProgramId(null);
			gnForm.setStudentMailid(false);
			gnForm.setStudentRegNo(false);
			gnForm.setStudentRollNo(false);
			gnForm.setSendMail(null);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			gnForm.setProgramTypeList(programTypeList);
		
		
		}catch (ApplicationException e) {
			log.error("error in init application detail page...",e);
			
				String msg = super.handleApplicationException(e);
				gnForm.setErrorMessage(msg);
				gnForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in init application detail page...",e);
				throw e;
		}
		return mapping.findForward(CMSConstants.GENERATE_PASSWORD);
	}
	
	/**
	 * generates password for students of  selected program
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward generatePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForm gnForm=(GeneratePasswordForm)form;
		ActionMessages errors=gnForm.validate(mapping, request);
		try {
			validateUsernameConfigs(gnForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.GENERATE_PASSWORD);
			}
			//check parent and student having same userid and pwd
			List<OrganizationTO> organisations=OrganizationHandler.getInstance().getOrganizationDetails();
			if(organisations!=null){
				Iterator<OrganizationTO> orgItr=organisations.iterator();
				while (orgItr.hasNext()) {
					OrganizationTO orgTO = (OrganizationTO) orgItr.next();
					gnForm.setSameUseridPassword(orgTO.getSameUseridPassword());
				}
			}
			GeneratePasswordHandler handler=GeneratePasswordHandler.getInstance();
			handler.updateOnlyPassword(gnForm);
			gnForm.setMessage(null);
			gnForm.setMessage(CMSConstants.GENERATE_PASSWORD_CONFIRM);
			return mapping.findForward(CMSConstants.GENERATE_PASSWORD_CONFIRM);
		
		} catch (ApplicationException e) {
			log.error("error in init application detail page...",e);
			
			String msg = super.handleApplicationException(e);
			gnForm.setErrorMessage(msg);
			gnForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in init application detail page...",e);
				throw e;
		}	 
	}
	/**
	 * generates password and email will be sent 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward generatePasswordAndSendMail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForm gnForm=(GeneratePasswordForm)form;
		ActionMessages errors=gnForm.validate(mapping, request);
		try {
			
			validateUsernameConfigs(gnForm,errors);
			if(gnForm.getSendMail()==null)
			{
				ActionMessage error = new ActionMessage(
						CMSConstants.GENERATEPASSWORD_SEND_MAIL_FOR);
				errors.add(CMSConstants.GENERATEPASSWORD_SEND_MAIL_FOR, error);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.GENERATE_PASSWORD);
			}
			//check parent and student having same userid and pwd
			List<OrganizationTO> organisations=OrganizationHandler.getInstance().getOrganizationDetails();
			if(organisations!=null){
				Iterator<OrganizationTO> orgItr=organisations.iterator();
				while (orgItr.hasNext()) {
					OrganizationTO orgTO = (OrganizationTO) orgItr.next();
					gnForm.setSameUseridPassword(orgTO.getSameUseridPassword());
				}
			}
			//generate password
			GeneratePasswordHandler handler=GeneratePasswordHandler.getInstance();
			handler.updatePassword(gnForm);
			gnForm.setMessage(null);
			gnForm.setMessage(CMSConstants.GENERATE_PASSWORD_MAIL_CONFIRM);
			return mapping.findForward(CMSConstants.GENERATE_PASSWORD_CONFIRM);
		
		} catch (ApplicationException e) {
			log.error("error in init application detail page...",e);
			
			String msg = super.handleApplicationException(e);
			gnForm.setErrorMessage(msg);
			gnForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in init application detail page...",e);
				throw e;
		}	 
	}

	/**
	 * validation for student username i.e, either mailid/regno/rollno
	 * @param gnForm
	 * @param errors
	 */
	private void validateUsernameConfigs(GeneratePasswordForm gnForm,
			ActionMessages errors) {
		if(errors==null){
			errors= new ActionMessages();
		}
		if((gnForm.isStudentMailid() && gnForm.isStudentRegNo()) ||
				(gnForm.isStudentMailid() && gnForm.isStudentRollNo()) ||
				(gnForm.isStudentRollNo() && gnForm.isStudentRegNo())){
			if (errors.get(CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECTONE)!=null && !errors.get(CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECTONE).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECTONE);
				errors.add(CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECTONE, error);
			}
			
		}else if(!gnForm.isStudentMailid() && !gnForm.isStudentRegNo() && !gnForm.isStudentRollNo()){
			
			if (errors.get(CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECT)!=null && !errors.get(CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECT).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECT);
				errors.add(CMSConstants.GENERATEPASSWORD_STUDENTUSERNAME_SELECT, error);
			}
		}
		
		
	}
	
	
	/**
	 * init reset password
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initResetPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForm gnForm=(GeneratePasswordForm)form;
		gnForm.setUserName(null);
		gnForm.setResetPwd(null);
		setUserId(request, gnForm);
		return mapping.findForward(CMSConstants.RESET_PASSWORD);
			 
	}
	
	
	/**
	 * resets password for students of  selected program
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForm gnForm=(GeneratePasswordForm)form;
		ActionMessages errors=gnForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.RESET_PASSWORD);
			}
			//reset pasasword
			GeneratePasswordHandler handler=GeneratePasswordHandler.getInstance();
			boolean userexists=handler.checkUserExists(gnForm.getUserName());
			if(!userexists){
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(CMSConstants.RESETPWD_USER_NOTEXIST);
				messages.add(CMSConstants.MESSAGES, message);
				saveErrors(request, messages);
				return mapping.findForward(CMSConstants.RESET_PASSWORD);
			}
			boolean result=handler.resetPassword(gnForm);
			//send confirmation message
			if (result) {
				gnForm.setUserName(null);
				gnForm.setResetPwd(null);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(CMSConstants.RESETPWD_SUCCESS_STATUS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}else{
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(CMSConstants.RESETPWD_FAILURE_STATUS);
				messages.add(CMSConstants.MESSAGES, message);
				saveErrors(request, messages);
			}
			return mapping.findForward(CMSConstants.RESET_PASSWORD);
		
		} catch (ApplicationException e) {
			log.error("error in init application detail page...",e);
			String msg = super.handleApplicationException(e);
			gnForm.setErrorMessage(msg);
			gnForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in init application detail page...",e);
			throw e;
		}	 
	}
}
