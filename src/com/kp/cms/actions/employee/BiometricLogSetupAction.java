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
import com.kp.cms.bo.admin.EmpBiometricLogSetupBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.BiometricLogSetupForm;
import com.kp.cms.handlers.employee.BiometricLogSetupHandler;
import com.kp.cms.to.employee.EmpBiometricSetUpTo;

@SuppressWarnings("deprecation")
public class BiometricLogSetupAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(WorkDiaryAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initBiometricLogSetup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BiometricLogSetupForm objForm = (BiometricLogSetupForm) form;
		objForm.resetFields();
		setRequestedDataToForm(objForm);
		return mapping.findForward(CMSConstants.BIOMETRIC_LOG_SETUP);
	}

	private void setRequestedDataToForm(BiometricLogSetupForm objForm) throws Exception{
		List<EmpBiometricSetUpTo> list=BiometricLogSetupHandler.getInstance().getBiometricList();
		objForm.setList(list);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBiometricLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("inside addWorkDiary Action");
		BiometricLogSetupForm objForm = (BiometricLogSetupForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.BIOMETRIC_LOG_SETUP);
			}
			setUserId(request, objForm);
			boolean isDuplicate=false;
			if(objForm.getId()==0){
				List<EmpBiometricSetUpTo> list=BiometricLogSetupHandler.getInstance().getBiometricList();
				if(list!=null && !list.isEmpty()){
					isDuplicate=true;
					errors.add("errors",new ActionError("knowledgepro.employee.biometric.duplicate"));
				}
			}
			if(!isDuplicate){
				isAdded = BiometricLogSetupHandler.getInstance().addBiometricLog(objForm);
				if (isAdded) {
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess", "Biometric Log Setup");
					messages.add("messages", message);
					saveMessages(request, messages);
					objForm.resetFields();
				} else {
					// failed
					errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Biometric Log Setup"));
					saveErrors(request, errors);
				}
			}

		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		setRequestedDataToForm(objForm);
		log.debug("Leaving addWorkDiary Action");
		return mapping.findForward(CMSConstants.BIOMETRIC_LOG_SETUP);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editBiometricLogSetup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BiometricLogSetupForm objForm = (BiometricLogSetupForm) form;
		int id=objForm.getId();
		if(id>0)
		objForm = BiometricLogSetupHandler.getInstance().getDetails(objForm,id);
		return mapping.findForward(CMSConstants.BIOMETRIC_LOG_SETUP);
	}

}
