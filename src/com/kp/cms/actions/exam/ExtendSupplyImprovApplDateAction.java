package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Date;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExtendSupplyImprovApplDateForm;
import com.kp.cms.forms.sap.AssignSapKeyForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExtendSupplyImprApplDateHandler;
import com.kp.cms.handlers.exam.PublishSupplementaryImpApplicationHandler;
import com.kp.cms.to.exam.ExtendSupplyImprApplDateTo;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExtendSupplyImprovApplDateAction extends BaseDispatchAction{
	ExtendSupplyImprApplDateHandler handler=ExtendSupplyImprApplDateHandler.getInstance();
	private static final Log log=LogFactory.getLog(ExamMidsemExemptionAction.class);
	/**
	 * init method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExtendSuppluImprApplDate(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		log.info("end of initExamMidsemExemption method in ExamMidsemExemptionAction class.");
		ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm=(ExtendSupplyImprovApplDateForm) form;
		reset(extendSupplyImprovApplDateForm);
		setRequiredDatatoForm(extendSupplyImprovApplDateForm);
		log.debug("Leaving initExamMidsemExemption");
		return mapping.findForward(CMSConstants.INIT_EXAM_SUPPLY_IMPROV_APPL_DATE);
	}
	private void reset(ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm) {
		extendSupplyImprovApplDateForm.setExamId(null);
		extendSupplyImprovApplDateForm.setExamType("Supplementary");
		extendSupplyImprovApplDateForm.setYear(null);
	}
	/** 
	 * set hte initial data while page load
	 * @param extendSupplyImprovApplDateForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm) throws Exception{
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(extendSupplyImprovApplDateForm.getYear()!=null && !extendSupplyImprovApplDateForm.getYear().isEmpty()){
			year=Integer.parseInt(extendSupplyImprovApplDateForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(extendSupplyImprovApplDateForm.getExamType(),year); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		extendSupplyImprovApplDateForm.setExamMap(examNameMap);// setting the examNameMap to form
		extendSupplyImprovApplDateForm.setExamType("Regular");
	}
	/**
	 * to get the student details who are registered for sap exam
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExamsToExtend(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm=(ExtendSupplyImprovApplDateForm) form;
		extendSupplyImprovApplDateForm.setExtendedEndDate(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
			if(extendSupplyImprovApplDateForm.getExamId()!=null && !extendSupplyImprovApplDateForm.getExamId().isEmpty()){
				List<ExtendSupplyImprApplDateTo> list=handler.getExamsToExtend(extendSupplyImprovApplDateForm);
				if(list!=null && !list.isEmpty()){
					extendSupplyImprovApplDateForm.setToList(list);
					return mapping.findForward("examsRecordsToExtend");
				}else{
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}else{
				errors.add("error", new ActionError("admissionFormForm.education.exam.required"));
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				extendSupplyImprovApplDateForm.setErrorMessage(msg);
				extendSupplyImprovApplDateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_SUPPLY_IMPROV_APPL_DATE);
	}
	/**
	 * for selected students the keys are updated in sap registration table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatetheExtendedDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExtendSupplyImprovApplDateForm extendSupplyImprovApplDateForm=(ExtendSupplyImprovApplDateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		ActionErrors dateErrors=extendSupplyImprovApplDateForm.validate(mapping, request);
		boolean flag1 = false;
		
		if(extendSupplyImprovApplDateForm.getExtendedEndDate()==null || extendSupplyImprovApplDateForm.getExtendedEndDate().equalsIgnoreCase("")){
			   errors.add("error", new ActionError("knowledgepro.exam.exammidsem.extended.enddate"));
		       saveErrors(request, dateErrors);
		}
		
	  if((extendSupplyImprovApplDateForm.getSuperFineAmount()!=null && !extendSupplyImprovApplDateForm.getSuperFineAmount().equalsIgnoreCase(""))&&
					(extendSupplyImprovApplDateForm.getFineAmount()==null || extendSupplyImprovApplDateForm.getFineAmount().equalsIgnoreCase(""))&&
					(extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate().equalsIgnoreCase(""))&&
					(extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate().equalsIgnoreCase(""))&&
					(extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate().equalsIgnoreCase(""))&&
					(extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate().equalsIgnoreCase(""))){
				
			if(extendSupplyImprovApplDateForm.getFineAmount()==null || extendSupplyImprovApplDateForm.getFineAmount().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.superfine.amount.fine"));
		        saveErrors(request, errors);
			}
			if(extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.fine.startdate.required"));
		        saveErrors(request, errors);
		}
			if(extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.fine.enddate.required"));
		        saveErrors(request, errors);
		}
			if(extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.fine.startdate.required"));
		        saveErrors(request, errors);
		}
			if(extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.fine.enddate.required"));
		        saveErrors(request, errors);
		}		
			
		}
	
		if((extendSupplyImprovApplDateForm.getFineAmount()!=null && !extendSupplyImprovApplDateForm.getFineAmount().equalsIgnoreCase(""))&&
				(extendSupplyImprovApplDateForm.getSuperFineAmount()==null || extendSupplyImprovApplDateForm.getSuperFineAmount().equalsIgnoreCase(""))&&
				(extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate().equalsIgnoreCase(""))&&
				(extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate().equalsIgnoreCase(""))){
			
				if(extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase("")){
					errors.add("error", new ActionError("knowledgepro.fee.publish.fine.startdate.required"));
			        saveErrors(request, errors);
			}
				if(extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")){
					errors.add("error", new ActionError("knowledgepro.fee.publish.fine.enddate.required"));
			        saveErrors(request, errors);
			}	
				
			
		}
		if(extendSupplyImprovApplDateForm.getSuperFineAmount()!=null && !extendSupplyImprovApplDateForm.getSuperFineAmount().equalsIgnoreCase("")){
			if(extendSupplyImprovApplDateForm.getFineAmount()!=null && !extendSupplyImprovApplDateForm.getFineAmount().equalsIgnoreCase("")
					&& extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase("")
					&& extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")){

				if(extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.superfine.startdate.required"));
		        saveErrors(request, errors);
		}
			if(extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.superfine.enddate.required"));
		        saveErrors(request, errors);
		}	
			if(extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.fine.startdate.required"));
		        saveErrors(request, errors);
		}
			if(extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.fine.enddate.required"));
		        saveErrors(request, errors);
		}	
	}
}
		
		
if((extendSupplyImprovApplDateForm.getSuperFineAmount()==null || extendSupplyImprovApplDateForm.getSuperFineAmount().equalsIgnoreCase("")) &&( (extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate()!=null && !extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate().equalsIgnoreCase(""))||(extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate()!=null && !extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate().equalsIgnoreCase("")))){
			if(extendSupplyImprovApplDateForm.getSuperFineAmount()==null || extendSupplyImprovApplDateForm.getSuperFineAmount().equalsIgnoreCase("")){
			    errors.add("error", new ActionError("knowledgepro.fee.publish.superfine.fineamount.required"));
	            saveErrors(request, errors);
			}
			if(extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineEndDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.superfine.enddate.required"));
	            saveErrors(request, errors);
			}
			if(extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedSuperFineStartDate().equalsIgnoreCase("")){
				errors.add("error", new ActionError("knowledgepro.fee.publish.superfine.startdate.required"));
	            saveErrors(request, errors);
			}
			
		}

if((extendSupplyImprovApplDateForm.getFineAmount()==null || extendSupplyImprovApplDateForm.getFineAmount().equalsIgnoreCase("")) &&( (extendSupplyImprovApplDateForm.getExtendedFineStartDate()!=null && !extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase(""))||(extendSupplyImprovApplDateForm.getExtendedFineEndDate()!=null && !extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")))){
	if(extendSupplyImprovApplDateForm.getFineAmount()==null || extendSupplyImprovApplDateForm.getFineAmount().equalsIgnoreCase("")){
	     errors.add("error", new ActionError("knowledgepro.fee.publish.fineamount.required"));
         saveErrors(request, errors);
	}
	if(extendSupplyImprovApplDateForm.getExtendedFineStartDate()==null || extendSupplyImprovApplDateForm.getExtendedFineStartDate().equalsIgnoreCase("")){
		errors.add("error", new ActionError("knowledgepro.fee.publish.fine.startdate.required"));
        saveErrors(request, errors);
	}
   if(extendSupplyImprovApplDateForm.getExtendedFineEndDate()==null || extendSupplyImprovApplDateForm.getExtendedFineEndDate().equalsIgnoreCase("")){
	   errors.add("error", new ActionError("knowledgepro.fee.publish.fine.enddate.required"));
       saveErrors(request, errors);
	}
}
		
		
		try{
			dateErrors = validateDate(extendSupplyImprovApplDateForm,errors);
			saveErrors(request, dateErrors);
			if(errors.isEmpty()){
				boolean flag=handler.updatetheExtendedDate(extendSupplyImprovApplDateForm,request);
				if(flag){
					extendSupplyImprovApplDateForm.setExtendedEndDate(null);
					extendSupplyImprovApplDateForm.setExamId(null);
					ActionMessage message = new ActionMessage("knowledgepro.exam.exammidsem.extended.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					extendSupplyImprovApplDateForm.clear();
				}else{
					errors.add("error", new ActionError("knowledgepro.exam.exammidsem.extended.update.fail"));
					saveErrors(request, errors);
				}
			}
			else{
				
				return mapping.findForward("examsRecordsToExtend");
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(request.getAttribute("keys")!=null && request.getAttribute("keys").equals("key")){
				errors.add("error", new ActionError("knowledgepro.exam.exammidsem.extended.enddate.greater"));
				saveErrors(request, errors);
				return mapping.findForward("examsRecordsToExtend");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				extendSupplyImprovApplDateForm.setErrorMessage(msg);
				extendSupplyImprovApplDateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_SUPPLY_IMPROV_APPL_DATE);
	}
	private ActionErrors validateDate(ExtendSupplyImprovApplDateForm  objform,ActionErrors error) throws ApplicationException {
		int flag = 0;
		ActionErrors errors = new ActionErrors();
		Date startDate=null;
		Date endDate=null;
		Date sqlstartDate=null;
		Date sqlendDate=null;
		Date fineStartDate=null;
		Date fineEndDate=null;
		Date sqlfineStartDate=null;
		Date sqlfineEndDate=null;
		Date superFineStartDate=null;
		Date superFineEndDate=null;
		Date sqlsuperFineStartDate=null;
		Date sqlsuperFineEndDate=null;
		
		
		 sqlfineStartDate = CommonUtil.ConvertStringToSQLDate(objform
					.getExtendedFineStartDate());
			sqlfineEndDate = CommonUtil.ConvertStringToSQLDate(objform
					.getExtendedFineEndDate());
		if ((objform.getExtendedFineStartDate() != null && objform.getExtendedFineStartDate().trim().length() > 0)&& !objform.getExtendedFineStartDate().equalsIgnoreCase("")
				&& objform.getExtendedFineEndDate() != null && objform.getExtendedFineEndDate().trim().length() > 0 && !objform.getExtendedFineEndDate().equalsIgnoreCase("")) {
			fineStartDate = new Date(objform.getExtendedFineStartDate());
			fineEndDate = new Date(objform.getExtendedFineEndDate());		
		sqlfineStartDate = CommonUtil.ConvertStringToSQLDate(objform
				.getExtendedFineStartDate());
		sqlfineEndDate = CommonUtil.ConvertStringToSQLDate(objform
				.getExtendedFineEndDate());
		flag  = CommonUtil.getDaysDiff(sqlfineStartDate,sqlfineEndDate);

		if (flag < 0) {
			error.add("error", new ActionError( "knowledgepro.fee.publish.fine.startdate.validDate"));

		 }
		}
		sqlsuperFineStartDate = CommonUtil.ConvertStringToSQLDate(objform.getExtendedSuperFineStartDate());
		sqlsuperFineEndDate = CommonUtil.ConvertStringToSQLDate(objform.getExtendedSuperFineEndDate());
		if ((objform.getExtendedSuperFineStartDate() != null && !objform.getExtendedSuperFineStartDate().equalsIgnoreCase("")&& objform.getExtendedSuperFineStartDate().trim().length() > 0)
				&& !objform.getExtendedSuperFineEndDate().equalsIgnoreCase("")&&objform.getExtendedSuperFineEndDate() != null && objform.getExtendedSuperFineEndDate().trim().length() > 0) {
		startDate = new Date(objform.getExtendedSuperFineStartDate());
		endDate = new Date(objform.getExtendedSuperFineEndDate());		
		sqlsuperFineStartDate = CommonUtil.ConvertStringToSQLDate(objform.getExtendedSuperFineStartDate());
		sqlsuperFineEndDate = CommonUtil.ConvertStringToSQLDate(objform.getExtendedSuperFineEndDate());
		flag  = CommonUtil.getDaysDiff(sqlsuperFineStartDate, sqlsuperFineEndDate);
		if (flag < 0) {
			error.add("error", new ActionError( "knowledgepro.fee.publish.superfine.startdate.validDate"));

		  }
		}
		
		 if(objform.getExtendedSuperFineStartDate() != null && !objform.getExtendedSuperFineStartDate().equalsIgnoreCase("") && objform.getExtendedSuperFineStartDate().trim().length()>0
	        		&& objform.getExtendedFineEndDate() != null && !objform.getExtendedFineEndDate().equalsIgnoreCase("")&&objform.getExtendedFineEndDate().trim().length()>0 ){
		flag  = CommonUtil.getDaysDiff(sqlfineEndDate,sqlsuperFineStartDate);	
		if (flag < 0) {
			error.add("error", new ActionError( "knowledgepro.fee.Extended"));

		}
		}
		return error;
		
	}
}
