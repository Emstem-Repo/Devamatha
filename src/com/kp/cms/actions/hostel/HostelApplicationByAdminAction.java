package com.kp.cms.actions.hostel;
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
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelApplicationByAdminForm;
import com.kp.cms.handlers.hostel.HostelApplicationByAdminHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.hostel.HostelApplicationByAdminTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;

public class HostelApplicationByAdminAction extends BaseDispatchAction{
	
	private static Log log = LogFactory.getLog(HlDamage.class);
	
	public ActionForward initHostelApplicationByAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		log.info("start initHostelApplicationByAdmin");
		HostelApplicationByAdminForm hostelApplicationForm = (HostelApplicationByAdminForm) form;
		hostelApplicationForm.resetFields();
		setAllHostelsToForm(hostelApplicationForm); 
		setUserId(request, hostelApplicationForm);
		log.info("exit init initHostelApplicationByAdmin");
		return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN_INIT);
	 }
	
	/**
	 * setting the required data to Form
	 */
	public void setAllHostelsToForm(HostelApplicationByAdminForm hostelApplicationByAdminForm)throws Exception {
		log.info("start setAllHostelsToForm in HostelApplicationByAdminAction");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		hostelApplicationByAdminForm.setHostelTOList(hostelList);
		log.info("exit setAllHostelsToForm in HostelApplicationByAdminAction");
	 }
	
	public ActionForward getHostelApplicationDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("start getHostelApplicationDetails");
		HostelApplicationByAdminForm hostelApplicationByAdminForm = (HostelApplicationByAdminForm) form;
		ActionErrors errors = hostelApplicationByAdminForm.validate(mapping, request);
		validateHostelApplication(hostelApplicationByAdminForm,errors);
		
		try {
			if (errors.isEmpty()) {
			int hostelId = Integer.valueOf(hostelApplicationByAdminForm.getHostelId());
			HostelApplicationByAdminTO hostelApplicationTO=null;
			
					hostelApplicationTO = HostelApplicationByAdminHandler.getInstance().getHostelApplicationTO(hostelApplicationByAdminForm);
					List<RoomTypeTO> roomTypeTOs=HostelApplicationByAdminHandler.getInstance().getRoomTypes(hostelApplicationByAdminForm);
					List<RoomTypeWithAmountTO> roomTypeWithAmountTOs=HostelApplicationByAdminHandler.getInstance().getRoomTypesWithFeeDetails(hostelId);
					if (hostelApplicationTO == null) {
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN_INIT);
					} else {
						//hostelApplicationByAdminForm.resetFields();
						hostelApplicationByAdminForm.setHostelApplicationTO(hostelApplicationTO);
						hostelApplicationByAdminForm.setRoomTypeNameList(roomTypeTOs);
						hostelApplicationByAdminForm.setRoomTypeWithAmountList(roomTypeWithAmountTOs);
						log.info("exit setAllHostelsToForm  HostelApplicationDetails");
						return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN);
					}
			  }	
			//} 
			else {
				saveErrors(request, errors);
				setAllHostelsToForm(hostelApplicationByAdminForm);
				log.info("exit setAllHostelsToForm HostelApplication with errors");
				return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN_INIT);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelApplicationByAdminForm.setErrorMessage(msg);
			hostelApplicationByAdminForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	public ActionForward saveHlApplicationFormByAdmin(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("start saveHostelApplicationByAdmin");
		HostelApplicationByAdminForm hostelApplicationByAdminForm = (HostelApplicationByAdminForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelApplicationByAdminForm.validate(mapping, request);
		validationWhileApply(errors,hostelApplicationByAdminForm);
		boolean isHlApplicationFormSaved;
		try
		{
			if(errors.isEmpty()){
				isHlApplicationFormSaved=HostelApplicationByAdminHandler.getInstance().saveHlApplicationForm(hostelApplicationByAdminForm);
				if (isHlApplicationFormSaved) {
					/*ActionMessage message = new ActionMessage(CMSConstants.HOSTELAPPLICATION_SAVE_SUCCESS);// Adding success message.
					messages.add("messages", message);
					saveMessages(request, messages);*/
					return mapping.findForward(CMSConstants.HOSTELAPPLICATION_REQUISITION_PAGE);
				}
			}
			else{
				saveErrors(request, errors);
			}
		}
		catch (Exception exception) {
			log.info("Exception saveHostelApplicationByAdmin");
			String msgKey = super.handleBusinessException(exception);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN);
	}
	
	private void validateHostelApplication(HostelApplicationByAdminForm hostelApplicationByAdminForm,ActionErrors errors) {
		
		if(( hostelApplicationByAdminForm.getApplicationNo().trim()==null ||  hostelApplicationByAdminForm.getApplicationNo().trim().isEmpty()) && 
			( hostelApplicationByAdminForm.getRegisterNo().trim()==null ||  hostelApplicationByAdminForm.getRegisterNo().trim().isEmpty())
			&& ( hostelApplicationByAdminForm.getStaffId().trim()==null ||  hostelApplicationByAdminForm.getStaffId().trim().isEmpty())
			&&( hostelApplicationByAdminForm.getRollNo().trim()==null ||  hostelApplicationByAdminForm.getRollNo().trim().isEmpty())){
			if (errors.get(CMSConstants.APPLICATION_REG_ROLLNO_STAFF) != null&& !errors.get(CMSConstants.APPLICATION_REG_ROLLNO_STAFF).hasNext()) {
				errors.add(CMSConstants.APPLICATION_REG_ROLLNO_STAFF,new ActionError(CMSConstants.APPLICATION_REG_ROLLNO_STAFF));
			}
		}
	}
	public ActionForward viewPersonalDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		log.info("start viewPersonalDetails HostelApplicationByAdminAction");
		HostelApplicationByAdminForm hostelApplicationByAdminForm = (HostelApplicationByAdminForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			if (errors.isEmpty()) {
				PersonalDataTO personalDataTO= HostelApplicationByAdminHandler.getInstance().viewPersonalDetails(hostelApplicationByAdminForm);	
				if (personalDataTO == null) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN);
				} else {
					hostelApplicationByAdminForm.setPersonalDataTO(personalDataTO);
					log.info("exit viewPersonalDetails  HostelApplicationByAdminAction");
					return mapping.findForward(CMSConstants.HOSTELAPPLICATION_PERSONAL_DETAILS);
				}
			} 
			else {
				saveErrors(request, errors);
				log.info("exit viewPersonalDetails HostelApplicationByAdminAction with errors");
				return mapping.findForward(CMSConstants.HOSTEL_APPLICATION_BY_ADMIN);
			}
		 } catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelApplicationByAdminForm.setErrorMessage(msg);
			hostelApplicationByAdminForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	public ActionErrors validationWhileApply(ActionErrors errors, HostelApplicationByAdminForm applicationForm)throws Exception{
		log.info("Entering into validateWhileApply of HostelApplicationAction");
		
			if(applicationForm.getRoomTypeSelect()== null || applicationForm.getRoomTypeSelect().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_REQUIRED));
			}
		
		log.info("Leaving into validateWhileApply of HostelApplicationAction");
		return errors;		
	}
	
		
  }
	

