package com.kp.cms.actions.hostel;

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
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelDamageForm;
import com.kp.cms.handlers.hostel.HostelDamageHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HostelDamageTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelDamageAction extends BaseDispatchAction
{
	private static Log log = LogFactory.getLog(HlDamage.class);
	/**
	 * initialize
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelDamageEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		log.info("start init Hostel Damage");
		HostelDamageForm hostelDamageForm = (HostelDamageForm) form;
		hostelDamageForm.resetFields1();
		setAllHostelsToForm(hostelDamageForm);
		setUserId(request, hostelDamageForm);
		log.info("exit init Hostel Damage");
		return mapping.findForward(CMSConstants.HOSTEL_DAMAGE_ENTRY_INIT_PAGE);
	}
	
	/**
	 * setting the required data to Form
	 */
	public void setAllHostelsToForm(HostelDamageForm hostelDamageForm)throws Exception 
	{
		log.info("start setAllHostelsToForm Hostel Damage");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		hostelDamageForm.setHostelTOList(hostelList);
		log.info("exit setAllHostelsToForm Hostel Damage");
	}
	/**
	 *search the data based on Hostel and student
	 */
	public ActionForward getHostelDamageEntryPage(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("start getHostelDamageEntryPage");
		HostelDamageForm hostelDamageForm = (HostelDamageForm) form;
		ActionErrors errors = hostelDamageForm.validate(mapping, request);
		validateDamageEntry(hostelDamageForm,errors);
		try {
			if (errors.isEmpty()) {
				HostelDamageTO damageTo = HostelDamageHandler.getInstance( )
						.getHostelDamageTO(hostelDamageForm);
				if (damageTo == null) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.HOSTEL_DAMAGE_ENTRY_INIT_PAGE);
				} else {
					hostelDamageForm.resetFields2();
					hostelDamageForm.setHostelDamageTO(damageTo);
					//getting all Hostel Damage Entry details 
					HostelDamageTO hostelDamageTO = HostelDamageHandler.getInstance().getHostelDamageTO(hostelDamageForm);
					List<HlDamageTO> hlDamageTOList=hostelDamageTO.getHlDamageTOList();
					hostelDamageForm.setHlDamageTOList(hlDamageTOList);
					log.info("exit setAllHostelsToForm Hostel Damage");
					return mapping.findForward(CMSConstants.HOSTEL_DAMAGE_ENTRY_PAGE);
				}
			} 
			else {
				saveErrors(request, errors);
				setAllHostelsToForm(hostelDamageForm);
				log.info("exit setAllHostelsToForm Hostel Damage with errors");
				return mapping.findForward(CMSConstants.HOSTEL_DAMAGE_ENTRY_INIT_PAGE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelDamageForm.setErrorMessage(msg);
			hostelDamageForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	public ActionForward addHostelDamageEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("start addHostelDamageEntry");
		HostelDamageForm hostelDamageForm = (HostelDamageForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelDamageForm.validate(mapping, request);
		validateTimeAndAmount(hostelDamageForm,errors);
		boolean isDamageEntryAdded;
		try
		{
			if(errors.isEmpty()){
				isDamageEntryAdded=HostelDamageHandler.getInstance().addHostelDamageEntry(hostelDamageForm);
				if (isDamageEntryAdded) {
					ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_DAMAGE_ENTRY_SUCCESS);// Adding success message.
					messages.add("messages", message);
					saveMessages(request, messages);
					hostelDamageForm.resetFields2();	
				}
			}
			else{
				saveErrors(request, errors);
			}
		}
		catch (Exception exception) {
			log.info("Exception addHostelDamageEntry");
			String msgKey = super.handleBusinessException(exception);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//getting all Hostel Damage Entry details.
		
		HostelDamageTO hostelDamageTO = HostelDamageHandler.getInstance().getHostelDamageTO(hostelDamageForm);
		List<HlDamageTO> hlDamageTOList=hostelDamageTO.getHlDamageTOList();
		hostelDamageForm.setHlDamageTOList(hlDamageTOList);
		return mapping.findForward(CMSConstants.HOSTEL_DAMAGE_ENTRY_PAGE);
	}
	
     public ActionForward deleteHostelDamageEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteHostelDamageEntry Action");
		HostelDamageForm hostelDamageForm = (HostelDamageForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		int hlDamageId=Integer.parseInt(hostelDamageForm.getHlDamageId());
	    try{
		    	if(errors.isEmpty()){	
		    	isDeleted=HostelDamageHandler.getInstance().deleteHostelDamage(hlDamageId,hostelDamageForm);
				if(isDeleted){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_DAMAGE_ENTRY_DELETE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					hostelDamageForm.reset(mapping, request);
					hostelDamageForm.resetFields2();
				}if(!isDeleted){
					//if deletion failed.
					ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_DAMAGE_ENTRY_DELETE_FAILED);
					messages.add("messages", message);
					saveMessages(request, messages);
					hostelDamageForm.reset(mapping, request);
					hostelDamageForm.resetFields2();
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			hostelDamageForm.setErrorMessage(msg);
			hostelDamageForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		HostelDamageTO hostelDamageTO = HostelDamageHandler.getInstance().getHostelDamageTO(hostelDamageForm);
		List<HlDamageTO> hlDamageTOList=hostelDamageTO.getHlDamageTOList();
		hostelDamageForm.setHlDamageTOList(hlDamageTOList);
		return mapping.findForward(CMSConstants.HOSTEL_DAMAGE_ENTRY_PAGE);
	}

	private void validateDamageEntry(HostelDamageForm hostelDamageForm,
			ActionErrors errors) {
		if((hostelDamageForm.getApplicationNo().trim()==null || hostelDamageForm.getApplicationNo().trim().isEmpty()) && (hostelDamageForm.getRegisterNo().trim()==null || hostelDamageForm.getRegisterNo().trim().isEmpty())
			&& (hostelDamageForm.getStaffId().trim()==null || hostelDamageForm.getStaffId().trim().isEmpty())
			&&(hostelDamageForm.getRollNo().trim()==null || hostelDamageForm.getRollNo().trim().isEmpty())){
			if (errors.get(CMSConstants.APPLICATION_REG_ROLLNO_STAFF) != null&& !errors.get(CMSConstants.APPLICATION_REG_ROLLNO_STAFF).hasNext()) {
				errors.add(CMSConstants.APPLICATION_REG_ROLLNO_STAFF,new ActionError(CMSConstants.APPLICATION_REG_ROLLNO_STAFF));
			}
		}
	}
	private void validateTimeAndAmount(HostelDamageForm hostelDamageForm, ActionErrors errors) {
		if(hostelDamageForm.getAmount().trim()!=null && !hostelDamageForm.getAmount().trim().isEmpty() && !CommonUtil.isValidDecimal(hostelDamageForm.getAmount().trim())){
			if (errors.get(CMSConstants.AMOUNT_INVALID ) != null&& !errors.get(CMSConstants.AMOUNT_INVALID ).hasNext()) {
				errors.add(CMSConstants.AMOUNT_INVALID ,new ActionError(CMSConstants.AMOUNT_INVALID ));
			}
		}
		if(CommonUtil.checkForEmpty(hostelDamageForm.getTimeHours())){
			if(!StringUtils.isNumeric(hostelDamageForm.getTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(hostelDamageForm.getTimeMins())){
			if(!StringUtils.isNumeric(hostelDamageForm.getTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(hostelDamageForm.getTimeHours()) && StringUtils.isNumeric(hostelDamageForm.getTimeHours())){
			if(Integer.parseInt(hostelDamageForm.getTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(hostelDamageForm.getTimeMins()) && StringUtils.isNumeric(hostelDamageForm.getTimeMins())){
			if(Integer.parseInt(hostelDamageForm.getTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
}
