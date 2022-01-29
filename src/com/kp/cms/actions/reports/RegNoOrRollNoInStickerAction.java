package com.kp.cms.actions.reports;

import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.RegNoOrRollNoInStickerForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.RegNoOrRollNoInStickerHandler;
import com.kp.cms.to.admin.ProgramTypeTO;

@SuppressWarnings("deprecation")
public class RegNoOrRollNoInStickerAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(RegNoOrRollNoInStickerAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrintRegNo(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegNoOrRollNoInStickerForm stickerForm = (RegNoOrRollNoInStickerForm) form;
		ActionMessages messages = new ActionMessages();
		stickerForm.reset(mapping, request);
		try {
			setProgramTypeListToRequest(request);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			stickerForm.setErrorMessage(msg);
			stickerForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PRINT_REG_NO);
		
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
	public ActionForward printRegNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegNoOrRollNoInStickerForm stickerForm = (RegNoOrRollNoInStickerForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = stickerForm.validate(mapping, request);
		
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				return mapping.findForward(CMSConstants.PRINT_REG_NO);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(stickerForm.getRegNoFrom().trim(), stickerForm.getRegNoTo().trim()); 
			if(!isValidRegdNo){
				if(stickerForm.getIsRollNo()!= null && "true".equalsIgnoreCase(stickerForm.getIsRollNo())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_ROLLNO_TYPE));
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				}
				setProgramTypeListToRequest(request);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.NO_RECORD_REG_ROLL);
			}
			RegNoOrRollNoInStickerHandler.getInstance().getRegNoRollNoPrint(stickerForm);
			if(stickerForm.getStudentList() == null || stickerForm.getStudentList().size() <= 0){
				return mapping.findForward(CMSConstants.NO_RECORD_PASSWORD);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stickerForm.setErrorMessage(msg);
				stickerForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		stickerForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.SHOW_REG_NO_ROLL_NO);
	
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
	
	/**
	 * 
	 * @param request
	 *            This method sets the program type list to Request useful in
	 *            populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.debug("leaving setProgramTypeListToRequest");
	}
	
}
