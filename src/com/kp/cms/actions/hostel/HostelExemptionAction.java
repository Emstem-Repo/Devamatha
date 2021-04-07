package com.kp.cms.actions.hostel;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.hostel.HostelExemptionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.handlers.hostel.HostelExemptionHandler;
import com.kp.cms.to.hostel.HostelExemptionTo;
import com.kp.cms.transactionsimpl.hostel.HostelExemptionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelExemptionAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(HostelExemptionAction.class);
	
	public ActionForward initHostelExemption(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelExemptionForm hostelExemptionForm=(HostelExemptionForm)form;
		hostelExemptionForm.reset();
		setDefaultValues(hostelExemptionForm);
		return mapping.findForward(CMSConstants.HOSTEL_Exemption);
	}
	public void setDefaultValues(HostelExemptionForm hostelExemptionForm)throws Exception{
		//hostelExemptionForm.setHolidaysFromSession("Evening");
		//hostelExemptionForm.setHolidaysToSession("Morning");
		Map<Integer,String> map= AbsentiesListHandler.getInstance().getHostelMap();
		hostelExemptionForm.setHostelMap(map);
	}
	
	
	public ActionForward getHostelStudentDataForExemption(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelExemptionForm hostelExemptionForm=(HostelExemptionForm)form;
				hostelExemptionForm.setHlExemptionList(null);
				hostelExemptionForm.setStudentExist(false);
				hostelExemptionForm.setFocus(null);
			ActionMessages messages = new ActionMessages();
			 ActionErrors errors = hostelExemptionForm.validate(mapping, request);
			boolean registerNo=false;
			try {
				if (errors.isEmpty()) {
					validateHostelExemptionDates(hostelExemptionForm,errors);
					if(errors.isEmpty()){
						if(hostelExemptionForm.getRegisterNo()!=null && !hostelExemptionForm.getRegisterNo().isEmpty()){
							registerNo=HostelExemptionTransactionImpl.getInstance().verifyRegisterNo(hostelExemptionForm);
							if(!registerNo){
								errors.add("error", new ActionError("knowledgepro.hostel.leave.student.notcheckedIn"));
								saveErrors(request, errors);
								setBlockAndUnitData(hostelExemptionForm);
								getSpecializationByCourse(hostelExemptionForm);
								return mapping.findForward(CMSConstants.HOSTEL_Exemption);
							}
						}
						if(hostelExemptionForm.getSemesterNo() !=null && !hostelExemptionForm.getSemesterNo().isEmpty()){
							if(!StringUtils.isNumeric(hostelExemptionForm.getSemesterNo())){
								errors.add("error", new ActionError("knowledgepro.exemption.semester.number.notNumeric"));
								saveErrors(request, errors);
								setBlockAndUnitData(hostelExemptionForm);
								getSpecializationByCourse(hostelExemptionForm);
								return mapping.findForward(CMSConstants.HOSTEL_Exemption);
							}
						}
						
							List<HostelExemptionTo>	toList=HostelExemptionHandler.getInstance().getHostelStudentDataForExemption(hostelExemptionForm);
							if(toList!=null && !toList.isEmpty()){
								hostelExemptionForm.setHlExemptionList(toList);
								hostelExemptionForm.setFlag(true);
							}else{
								errors.add("error", new ActionError("knowledgepro.norecords"));
								saveErrors(request, errors);
							}
						
					}else{
						saveErrors(request, errors);
					}
				}else{
					saveErrors(request, errors);
				}
			} catch (Exception e) {
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelExemptionForm.setErrorMessage(msg);
					hostelExemptionForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		setBlockAndUnitData(hostelExemptionForm);
		getSpecializationByCourse(hostelExemptionForm);
		return mapping.findForward(CMSConstants.HOSTEL_Exemption);
	}

	
	public ActionForward saveHostelExemptionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initPage");
		HostelExemptionForm hostelExemptionForm = (HostelExemptionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, hostelExemptionForm);
		int count=0;
		if(hostelExemptionForm.getReason()==null || hostelExemptionForm.getReason().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.reason.required"));
			saveErrors(request, errors);
		}
		try{
			
			if(errors.isEmpty()){
				List<HostelExemptionTo> hostelExemptionList = hostelExemptionForm.getHlExemptionList();
				Iterator<HostelExemptionTo> iterator = hostelExemptionList.iterator();
				while (iterator.hasNext()) {
					HostelExemptionTo hostelExemptionTO = (HostelExemptionTo) iterator.next();
					if(hostelExemptionTO.getChecked1()!=null && !hostelExemptionTO.getChecked1().isEmpty() ){
						count++;
					}
				}
				if(hostelExemptionForm.isStudentExist()==false){
					if(count==0){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.employee.select.record"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.HOSTEL_Exemption);
					}
				}
				boolean save = HostelExemptionHandler.getInstance().saveHostelExemptionDetails(hostelExemptionList,hostelExemptionForm);
				if(save){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.hostel.exemtion.details.saved.success"));
					saveMessages(request, messages);
					hostelExemptionForm.reset();
					setDefaultValues(hostelExemptionForm);
				}
			}else{
				List<HostelExemptionTo> hostelExemptionList = hostelExemptionForm.getHlExemptionList();
				Iterator<HostelExemptionTo> iterator = hostelExemptionList.iterator();
				while (iterator.hasNext()) {
					HostelExemptionTo hostelExemptionTO = (HostelExemptionTo) iterator.next();
					if(hostelExemptionTO.getChecked1()!=null && !hostelExemptionTO.getChecked1().isEmpty() ){
						hostelExemptionTO.setSelected(true);
						hostelExemptionTO.setChecked1(null);
					}else{
						hostelExemptionTO.setSelected(false);
					}
				}
				saveErrors(request, errors);
				hostelExemptionForm.setFocus("reason");
				return mapping.findForward(CMSConstants.HOSTEL_Exemption);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelExemptionForm.setErrorMessage(msg);
			hostelExemptionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initOnlineLeave");
		return mapping.findForward(CMSConstants.HOSTEL_Exemption);
	}
	
	private void validateHostelExemptionDates(HostelExemptionForm hostelExemptionForm,ActionErrors errors)
	{
		if(CommonUtil.checkForEmpty(hostelExemptionForm.getHolidaysFrom()) && CommonUtil.checkForEmpty(hostelExemptionForm.getHolidaysTo()) &&
				CommonUtil.isValidDate(hostelExemptionForm.getHolidaysFrom()) && CommonUtil.isValidDate(hostelExemptionForm.getHolidaysTo())){
			Date startDate = CommonUtil.ConvertStringToDate(hostelExemptionForm.getHolidaysFrom());
			Date endDate = CommonUtil.ConvertStringToDate(hostelExemptionForm.getHolidaysTo());
			Date currentDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			//int totalDays = cal1.get(Calendar.DAY_OF_YEAR)-cal.get(Calendar.DAY_OF_YEAR);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}else if(daysBetween==1){
				if(hostelExemptionForm.getHolidaysFromSession().equalsIgnoreCase("Evening") && hostelExemptionForm.getHolidaysToSession().equalsIgnoreCase("Morning"))
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.hostel.leave.select.session"));
			}
		}
	}
	/**
	 * @param hostelExemptionForm
	 * @throws Exception
	 */
	public void setBlockAndUnitData(HostelExemptionForm hostelExemptionForm)throws Exception{
		if(hostelExemptionForm.getHostelId()!=null && !hostelExemptionForm.getHostelId().isEmpty()){
			Map<Integer, String> blockMap=CommonAjaxHandler.getInstance().getBlockByHostel(Integer.parseInt(hostelExemptionForm.getHostelId()));
			hostelExemptionForm.setBlockMap(blockMap);
			if(hostelExemptionForm.getBlockId()!=null && !hostelExemptionForm.getBlockId().isEmpty()){
				Map<Integer, String> unitMap=CommonAjaxHandler.getInstance().getUnitByBlock(Integer.parseInt(hostelExemptionForm.getBlockId()));
				hostelExemptionForm.setUnitMap(unitMap);
			}
			
			Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCoursebyHostel(Integer.parseInt(hostelExemptionForm.getHostelId()));
			hostelExemptionForm.setCourseMap(courseMap);
			
			Map<Integer, String> classMap=CommonAjaxHandler.getInstance().getClassByHostel(Integer.parseInt(hostelExemptionForm.getHostelId()));
			hostelExemptionForm.setClassMap(classMap);
		}
	}
	/**
	 * @param hostelExemptionForm
	 * @throws Exception
	 */
	public void getSpecializationByCourse(HostelExemptionForm hostelExemptionForm)throws Exception{
		if(hostelExemptionForm.getCourseId()!=null && !hostelExemptionForm.getCourseId().isEmpty()){
			Map<Integer, String> spacialMap=CommonAjaxHandler.getInstance().getSpecializationByCourse(Integer.parseInt(hostelExemptionForm.getCourseId()));
			hostelExemptionForm.setSpacialMap(spacialMap);
		}
	}
}