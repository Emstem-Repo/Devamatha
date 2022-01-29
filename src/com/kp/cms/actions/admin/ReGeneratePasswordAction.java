package com.kp.cms.actions.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.GeneratePasswordForm;
import com.kp.cms.forms.admin.ReGeneratePasswordForm;
import com.kp.cms.handlers.admin.GeneratePasswordHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ReGeneratePasswordHandler;
import com.kp.cms.to.admin.OrganizationTO;

public class ReGeneratePasswordAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ReGeneratePasswordAction.class);
	Locale locale=new Locale("en","IN");
	/**
	 * initializes generate password search
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initReGeneratePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReGeneratePasswordForm reGeneratePasswordForm=(ReGeneratePasswordForm)form;
		try {
			setUserId(request, reGeneratePasswordForm);
			reGeneratePasswordForm.resetFields();
		}catch (Exception e) {
			log.error("error in init application detail page...",e);
				String msg = super.handleApplicationException(e);
				reGeneratePasswordForm.setErrorMessage(msg);
				reGeneratePasswordForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		return mapping.findForward(CMSConstants.RE_GENERATE_PASSWORD);
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
	public ActionForward reGeneratePasswordAndSendMail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReGeneratePasswordForm reGeneratePasswordForm=(ReGeneratePasswordForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=reGeneratePasswordForm.validate(mapping, request);
		try {
			
			validateUsernameConfigs(reGeneratePasswordForm,errors);
			if(reGeneratePasswordForm.getSendMail()==null)
			{
				ActionMessage error = new ActionMessage(
						CMSConstants.GENERATEPASSWORD_SEND_MAIL_FOR);
				errors.add(CMSConstants.GENERATEPASSWORD_SEND_MAIL_FOR, error);
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.RE_GENERATE_PASSWORD);
			}
			//check parent and student having same userid and pwd
			List<OrganizationTO> organisations=OrganizationHandler.getInstance().getOrganizationDetails();
			if(organisations!=null){
				Iterator<OrganizationTO> orgItr=organisations.iterator();
				while (orgItr.hasNext()) {
					OrganizationTO orgTO = (OrganizationTO) orgItr.next();
					reGeneratePasswordForm.setSameUseridPassword(orgTO.getSameUseridPassword());
				}
			}
			String inputStr = reGeneratePasswordForm.getRegisterNoEntry();
			String patternStr = ",";
			String[] registerNoString = inputStr.split(patternStr);
			ArrayList<String> registerNoList = new ArrayList<String>();
			Set<String> registerNoSet = new HashSet<String>();
			
			for (String registerNo : registerNoString) {
				if (registerNo.trim().length() > 0 && registerNoSet.add(registerNo.trim())) {
					registerNoList.add(registerNo.trim().toUpperCase(locale));
				}
			}
			List<String> studentRegisterNo=ReGeneratePasswordHandler.getInstance().getStudentsByRegisterNo(registerNoList);
			validateStudentLogins(studentRegisterNo,registerNoString);
			List<StudentLogin> studentLogins=ReGeneratePasswordHandler.getInstance().getStudentLoginsByRegisterNo(registerNoList);
			
			//generate password
			ReGeneratePasswordHandler handler=ReGeneratePasswordHandler.getInstance();
			boolean isUpdate=handler.updatePassword(studentLogins,reGeneratePasswordForm);
			if(isUpdate){
				ActionMessage message = new ActionMessage("knowledgepro.admin.regenerate.password.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.regenerate.password.failure"));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.RE_GENERATE_PASSWORD);
		
		} catch (DuplicateException e) {
			log.error("error in init application detail page...",e);
			errors.add("error", new ActionError("knowledgepro.admin.regenerate.password", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.RE_GENERATE_PASSWORD);
		}catch (ApplicationException e) {
			log.error("error in init application detail page...",e);
			errors.add("error", new ActionError("knowledgepro.admin.regenerate.passwordExists", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.RE_GENERATE_PASSWORD);
		}
		catch (Exception e) {
			log.error("error in init application detail page...",e);
			String msg = super.handleApplicationException(e);
			reGeneratePasswordForm.setErrorMessage(msg);
			reGeneratePasswordForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	 
	}
	
	private void validateStudentLogins(List<String> studentRegisterNo,
			String[] registerNoString) throws Exception {
		StringBuffer intType =new StringBuffer();
		for(int i=0;i<registerNoString.length;i++){
			if(!studentRegisterNo.contains(registerNoString[i].trim().toUpperCase(locale))){
				 intType.append(registerNoString[i]);
			}
		}
		
		if(!intType.toString().trim().isEmpty()){
			throw new DuplicateException(intType.toString());
		}
		
	}
	/**
	 * validation for student username i.e, either mailid/regno/rollno
	 * @param gnForm
	 * @param errors
	 */
	private void validateUsernameConfigs(ReGeneratePasswordForm gnForm,
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
		
		if(gnForm.getRegisterNoEntry()==null || gnForm.getRegisterNoEntry().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.attendance.activityattendence.regno.required"));
		}
	}
}
