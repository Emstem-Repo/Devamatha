package com.kp.cms.actions.admin;

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
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ResetStudentPasswordHandler;
import com.kp.cms.to.admin.OrganizationTO;

public class ResetStudentPasswordAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ResetStudentPasswordAction.class);
	/**
	 * initializes regenerate password search
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
		setUserId(request, gnForm);
		gnForm.setProgramTypeId(null);
		gnForm.setProgramId(null);
		gnForm.setStudentMailid(false);
		gnForm.setStudentRegNo(false);
		gnForm.setStudentRollNo(false);
		gnForm.setRollNumber(false);
		gnForm.setRegisterNoEntry(null);
		gnForm.setSendMail(null);
		return mapping.findForward(CMSConstants.RESET_STUDENT_PASSWORD);
	}
	
	/**
	 * regenerate password for students
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
			validateUsernameConfigs(gnForm,errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.RESET_STUDENT_PASSWORD);
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
			ResetStudentPasswordHandler handler=ResetStudentPasswordHandler.getInstance();
			handler.updateOnlyPasswordInReset(gnForm);
			gnForm.setMessage(null);
			gnForm.setMessage(CMSConstants.RESET_PASSWORD_CONFIRM);
			return mapping.findForward(CMSConstants.RESET_PASSWORD_CONFIRM);
		
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
	 * generates password and email will be sent 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetPasswordAndSendMail(ActionMapping mapping,
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
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.RESET_STUDENT_PASSWORD);
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
			ResetStudentPasswordHandler handler=ResetStudentPasswordHandler.getInstance();
			handler.updateResetPassword(gnForm);
			gnForm.setMessage(null);
			gnForm.setMessage(CMSConstants.RESET_PASSWORD_MAIL_CONFIRM);
			return mapping.findForward(CMSConstants.RESET_PASSWORD_CONFIRM);
		
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
