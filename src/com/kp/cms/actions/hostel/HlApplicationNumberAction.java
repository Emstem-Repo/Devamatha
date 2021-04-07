package com.kp.cms.actions.hostel;

import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HlApplicationNumberForm;
import com.kp.cms.handlers.hostel.HlApplicationNumberHandler;
import com.kp.cms.to.hostel.HlApplicationFeeTO;

@SuppressWarnings("deprecation")
public class HlApplicationNumberAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(HlApplicationNumberAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initNumberEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HlApplicationNumberForm hlApplicationNumberForm=(HlApplicationNumberForm)form;
		try{
			resetFields(hlApplicationNumberForm);
			Map<Integer, String> hostelMap = HlApplicationNumberHandler.getInstance().getHosteList();
			hlApplicationNumberForm.setHostelMap(hostelMap );
			List<HlApplicationFeeTO> applicationNoList = HlApplicationNumberHandler.getInstance().getApplicationNoList();
			hlApplicationNumberForm.setApplicationNoList(applicationNoList);
			log.info("called initNumberEntry method");
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hlApplicationNumberForm.setErrorMessage(msg);
			hlApplicationNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_APPLICATION_NUMBER_ENTRY);
	}
	/**
	 * @param hlApplicationNumberForm
	 */
	private void resetFields(HlApplicationNumberForm hlApplicationNumberForm) {
		hlApplicationNumberForm.setStartNumber("");
		hlApplicationNumberForm.setPreFix("");
		hlApplicationNumberForm.setHostelId("");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HlApplicationNumberForm hlApplicationNumberForm=(HlApplicationNumberForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		log.info("called saveNumber method");
		try{
			if(hlApplicationNumberForm.getHostelId() == null || hlApplicationNumberForm.getHostelId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(""));
			}
			if(hlApplicationNumberForm.getPreFix() == null || hlApplicationNumberForm.getPreFix().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(""));
			}
			if(hlApplicationNumberForm.getStartNumber() == null || hlApplicationNumberForm.getStartNumber().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(""));
			}
			if(errors.isEmpty()){
				boolean isDuplicate=false;
				if(isDuplicate){
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.exists","Application Number"));
					saveErrors(request, errors);
				}else {
					boolean save = HlApplicationNumberHandler.getInstance().save(hlApplicationNumberForm,"ADD");
					if(save){
						ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess","Application Number");
						messages.add("messages", message);
						saveMessages(request, messages);
						resetFields(hlApplicationNumberForm);
						List<HlApplicationFeeTO> applicationNoList = HlApplicationNumberHandler.getInstance().getApplicationNoList();
						hlApplicationNumberForm.setApplicationNoList(applicationNoList);
					}else {
						errors.add("error", new ActionError("knowledgepro.admin.programtype.name.addfail","Application Number"));
						saveErrors(request, errors);
					}
				}
					
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hlApplicationNumberForm.setErrorMessage(msg);
			hlApplicationNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_APPLICATION_NUMBER_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HlApplicationNumberForm hlApplicationNumberForm=(HlApplicationNumberForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		log.info("called updateNumber method");
		try{
			if(hlApplicationNumberForm.getHostelId() == null || hlApplicationNumberForm.getHostelId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(""));
			}
			if(hlApplicationNumberForm.getPreFix() == null || hlApplicationNumberForm.getPreFix().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(""));
			}
			if(hlApplicationNumberForm.getStartNumber() == null || hlApplicationNumberForm.getStartNumber().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(""));
			}
			if(errors.isEmpty()){
			 boolean isUpdate = HlApplicationNumberHandler.getInstance().save(hlApplicationNumberForm,"UPDATE");
			 if(isUpdate){
					ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.updatesuccess","Application Number");
					messages.add("messages", message);
					saveMessages(request, messages);
					resetFields(hlApplicationNumberForm);
					List<HlApplicationFeeTO> applicationNoList = HlApplicationNumberHandler.getInstance().getApplicationNoList();
					hlApplicationNumberForm.setApplicationNoList(applicationNoList);
				}else {
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.updatefail","Application Number"));
					saveErrors(request, errors);
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hlApplicationNumberForm.setErrorMessage(msg);
			hlApplicationNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_APPLICATION_NUMBER_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HlApplicationNumberForm hlApplicationNumberForm=(HlApplicationNumberForm)form;
		ActionMessages messages = new ActionMessages();
		log.info("called deleteNumber method");
		try{
			 boolean isDeleted = HlApplicationNumberHandler.getInstance().delete(hlApplicationNumberForm.getId());
			 if (isDeleted) {
					ActionMessage message = new ActionMessage("knowledgepro.hostel.fineEntry.delete.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					resetFields(hlApplicationNumberForm);
					List<HlApplicationFeeTO> applicationNoList = HlApplicationNumberHandler.getInstance().getApplicationNoList();
					hlApplicationNumberForm.setApplicationNoList(applicationNoList);
				} else {
					ActionMessage message = new ActionMessage("knowledgepro.hostel.fineEntry.delete.fail");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hlApplicationNumberForm.setErrorMessage(msg);
			hlApplicationNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_APPLICATION_NUMBER_ENTRY);
	}
}
