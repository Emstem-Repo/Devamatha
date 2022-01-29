package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelStudentCheckOutForm;
import com.kp.cms.handlers.hostel.HostelStudentCheckOutHandler;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelStudentCheckOutAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(HostelStudentCheckOutAction.class);
//	IHostelStudentCheckOutTransaction transaction = new HostelStudentCheckOutImpl();
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	public ActionForward initHostelStudentCheckOut(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHostelStudentCheckOut method in HostelStudentCheckOutAction class.");
		HostelStudentCheckOutForm checkOutForm=(HostelStudentCheckOutForm) form;
		checkOutForm.clearAll();
		checkOutForm.reset();
	
		log.debug("Leaving initHostelStudentCheckOut");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT);
	}
	
	public ActionForward HostelStudentCheckOutSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		HostelStudentCheckOutForm checkOutForm=(HostelStudentCheckOutForm) form;
		ActionErrors errors=new ActionErrors();

		if(checkOutForm.getRegNo()==null || checkOutForm.getRegNo().isEmpty())
		{
			errors.add("error", new ActionError( "knowledgepro.admission.cancelPromotion.isrequired"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT);
		}
		else{
			try{
				HlAdmissionBo hlAdmissionBo= HostelStudentCheckOutHandler.getInstance().verifyRegisterNumberAndGetStudentDetails(checkOutForm);
				if(hlAdmissionBo==null){
					errors.add("error", new ActionError( "knowledgepro.norecords"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT);
				}else{
					
					setStudentDetailsDataToForm(checkOutForm);
					setStudentFacilitiesDataToForm(checkOutForm);
					
					checkOutForm.setId(hlAdmissionBo.getId());
					if(hlAdmissionBo.getIsFacilityVerified()!=null){
						checkOutForm.setChecked1(hlAdmissionBo.getIsFacilityVerified() ? "on": null);
						checkOutForm.setEditMode("edit");
					}
					if((hlAdmissionBo.getCheckOut()!=null && !hlAdmissionBo.getCheckOut().toString().isEmpty())){
						checkOutForm.setChecked2(hlAdmissionBo.getCheckOut() ? "on": null);
					}
					if(hlAdmissionBo.getCheckOutDate()!=null){
						String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(hlAdmissionBo.getCheckOutDate()), HostelStudentCheckOutAction.SQL_DATEFORMAT,	HostelStudentCheckOutAction.FROM_DATEFORMAT);
						checkOutForm.setDate(date);
					}
					if(hlAdmissionBo.getCheckOutRemarks()!=null)
						checkOutForm.setCheckOutRemarks(hlAdmissionBo.getCheckOutRemarks());
					
					
				return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT_DETAILS);
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT);
		
	}
	
	private void setStudentDetailsDataToForm(HostelStudentCheckOutForm checkOutForm) throws Exception{
		
		try{
			HlAdmissionBo hlAdmissionBo= HostelStudentCheckOutHandler.getInstance().verifyRegisterNumberAndGetStudentDetails(checkOutForm);
			
			checkOutForm.setId(hlAdmissionBo.getId());
			if(hlAdmissionBo.getStudentId()!=null 
					&& hlAdmissionBo.getStudentId().getAdmAppln()!=null 
					&& hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData()!=null
					&& hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null 
					&& !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
				checkOutForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			if(hlAdmissionBo.getHostelId()!=null 
					&& hlAdmissionBo.getHostelId().getName()!=null && !hlAdmissionBo.getHostelId().getName().isEmpty())
				checkOutForm.setHostelName(hlAdmissionBo.getHostelId().getName());
			if(hlAdmissionBo.getRoomTypeId()!=null 
					&& hlAdmissionBo.getRoomTypeId().getName()!=null && !hlAdmissionBo.getRoomTypeId().getName().isEmpty())
				checkOutForm.setRoomType(hlAdmissionBo.getRoomTypeId().getName());
			if(hlAdmissionBo.getBedId()!=null 
					&& hlAdmissionBo.getBedId().getBedNo()!=null && !hlAdmissionBo.getBedId().getBedNo().isEmpty())
				checkOutForm.setBedNo(hlAdmissionBo.getBedId().getBedNo());
			
			if(hlAdmissionBo.getRoomId()!=null){
			if(hlAdmissionBo.getRoomId().getHlBlock()!=null)
				checkOutForm.setBlockName(hlAdmissionBo.getRoomId().getHlBlock().getName());
			if(hlAdmissionBo.getRoomId().getHlUnit()!=null)
				checkOutForm.setUnitName(hlAdmissionBo.getRoomId().getHlUnit().getName());
			if(hlAdmissionBo.getRoomId().getFloorNo()!=null)
				checkOutForm.setFloorNo(String.valueOf(hlAdmissionBo.getRoomId().getFloorNo()));
			if(hlAdmissionBo.getRoomId().getName()!=null && !hlAdmissionBo.getRoomId().getName().isEmpty())
				checkOutForm.setRoomNo(hlAdmissionBo.getRoomId().getName());
			}
			
			if(hlAdmissionBo.getCheckInDate()!=null)
			checkOutForm.setCheckInDate(CommonUtil.formatDates(hlAdmissionBo.getCheckInDate()));
			
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	private void setStudentFacilitiesDataToForm(HostelStudentCheckOutForm checkOutForm) throws Exception{

		try{
			List<HlStudentFacilityAllotted> facilityList =  HostelStudentCheckOutHandler.getInstance().getStudentFacilitiesAlloted(checkOutForm);
			
			FacilityTO facilityTO = null;
				List<FacilityTO> newFacilityList = new ArrayList<FacilityTO>();
				if (facilityList != null && !facilityList.isEmpty()) {
					Iterator<HlStudentFacilityAllotted> iterator = facilityList.iterator();
					while (iterator.hasNext()) {
						HlStudentFacilityAllotted facilityAllotted = iterator.next();
						facilityTO = new FacilityTO();
						if (facilityAllotted.getHlFacilityId()!=null)
							facilityTO.setName(facilityAllotted.getHlFacilityId().getName());
						if(facilityAllotted.getDescription()!=null && !facilityAllotted.getDescription().isEmpty())
							facilityTO.setDescription(facilityAllotted.getDescription());
						newFacilityList.add(facilityTO);
					}
				}
				
		checkOutForm.setFacilityList(newFacilityList);
			
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	public ActionForward addCheckOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- HostelStudentCheckOutAction --- addCheckOut");
		HostelStudentCheckOutForm checkOutForm=(HostelStudentCheckOutForm)form;
		ActionErrors errors = checkOutForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			if(isCancelled(request))
			{
				checkOutForm.reset();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
				if(checkOutForm.getDate().isEmpty() || checkOutForm.getChecked1()==null || checkOutForm.getChecked2()==null){
					setStudentDetailsDataToForm(checkOutForm);
					setStudentFacilitiesDataToForm(checkOutForm);
					
					errors.add("error", new ActionError( "knowledgepro.hostel.student.checkout.all.required"));
					addErrors(request, errors);
					checkOutForm.reset();
					return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT_DETAILS);
					
				}
				
			setUserId(request, checkOutForm);
						
			boolean isCheckedOut;

			isCheckedOut = HostelStudentCheckOutHandler.getInstance().addCheckOut(checkOutForm);
			//If add operation is success then display the success message.
			if(isCheckedOut)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_STUDENT_CHECK_OUT_ADD_SUCCESS));
				saveMessages(request, messages);
				checkOutForm.reset();
				return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_STUDENT_CHECK_OUT_ADD_FAILED));
				saveErrors(request, errors);
				}
			}
		}catch (Exception e) {
				log.error("Error in adding addCheckOut in HostelStudentCheckOut Action",e);
				String msg = super.handleApplicationException(e);
				checkOutForm.setErrorMessage(msg);
				checkOutForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- HostelStudentCheckOut --- addCheckOut");
//		checkOutForm.reset();
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_CHECK_OUT_DETAILS);
	}
	

}
