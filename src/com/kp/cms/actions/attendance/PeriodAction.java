package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.Iterator;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.PeriodForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ClassEntryHandler;
import com.kp.cms.handlers.attendance.PeriodHandler;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("deprecation")
public class PeriodAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(PeriodAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response setting classlist, periodlist
	 * @return forward to PeriodEntry
	 * @throws Exception
	 */
	public ActionForward initPeriod(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("Entering initPeriod");
		PeriodForm periodForm = (PeriodForm) form;
		try {
			periodForm.reset(mapping, request);
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			/*Calendar calendar = Calendar.getInstance();
			 int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}// end
			setClassListToForm(periodForm, currentYear);*/
			//periodForm.setYear(Integer.toString(currentYear));
			setPeriodListToForm(periodForm);
			//initializing start & end time
			periodForm.setStartHours("00");
			periodForm.setStartMins("00");
			periodForm.setEndHours("00");
			periodForm.setEndMins("00");
			periodForm.setSession(null);
		}
		catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				periodForm.setErrorMessage(msg);
				periodForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				String msg = super.handleApplicationException(e);
				periodForm.setErrorMessage(msg);
				periodForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Leaving initPeriod ");
		return mapping.findForward(CMSConstants.PERIOD_ENTRY);
	}
	/**
	 * method used to add new period
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. addPeriod Action");
		PeriodForm periodForm = (PeriodForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = periodForm.validate(mapping, request);
		periodForm.setId(0);
		boolean isAdded;
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		try {
			//mandatory message for start & end time
			if(periodForm.getStartHours().trim().equals("00") || periodForm.getStartHours().trim().equals("0") || periodForm.getStartHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.startHours",new ActionError("knowledgepro.attn.period.startHours"));
			}
			
			if(periodForm.getEndHours().trim().equals("00") || periodForm.getEndHours().trim().equals("0") || periodForm.getEndHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.endHours",new ActionError("knowledgepro.attn.period.endHours"));
			}
			if(periodForm.getSession()==null){
				errors.add("knowledgepro.attn.period.session",new ActionError("knowledgepro.attn.period.session"));
			}
		
			//time validation
			if(errors.isEmpty()){
				validateTime(periodForm, errors);
			}				
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				int tempYear; 
				if(periodForm.getYear() != null && !periodForm.getYear().isEmpty()){
					tempYear = Integer.parseInt(periodForm.getYear());
				}
				else
				{
					tempYear = currentYear; 
				}
				setClassListToForm(periodForm, tempYear);	//setting classList to form based on the year			
				return mapping.findForward(CMSConstants.PERIOD_ENTRY);
			}
				
			setUserId(request, periodForm);  //setting user id to update last changed details
			isAdded = PeriodHandler.getInstance().addPeriod(periodForm, "Add");
			setPeriodListToForm(periodForm);
			currentYear=CurrentAcademicYear.getInstance().getAcademicyear();
			if(currentYear>0)
			setClassListToForm(periodForm, currentYear);	
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.attn.duplicate"));
			saveErrors(request, errors);
			int tempYear = currentYear; 
			if(periodForm.getYear() != null && !periodForm.getYear().isEmpty()){
				tempYear = Integer.parseInt(periodForm.getYear());
			}
			setClassListToForm(periodForm, tempYear);				
			return mapping.findForward(CMSConstants.PERIOD_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				periodForm.setErrorMessage(msg);
				periodForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.attn.period.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			periodForm.reset(mapping, request);
			periodForm.setStartHours("00");
			periodForm.setStartMins("00");
			periodForm.setEndHours("00");
			periodForm.setEndMins("00");
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.attn.period.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving action class addPeriod");
		return mapping.findForward(CMSConstants.PERIOD_ENTRY);

	}

	
	/**
	 * method used to update period
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. updatePeriod Action");
		PeriodForm periodForm = (PeriodForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = periodForm.validate(mapping, request);
		boolean isUpdated = false;
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		try {
			if(isCancelled(request)){
				setRequiredDataToForm(periodForm);
				request.setAttribute("periodOperation", "edit");
				return mapping.findForward(CMSConstants.PERIOD_ENTRY);
			}
			//mandatory checking for start & end time
			if(periodForm.getStartHours().trim().equals("00") || periodForm.getStartHours().trim().equals("0") || periodForm.getStartHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.startHours",new ActionError("knowledgepro.attn.period.startHours"));
			}
			
			if(periodForm.getEndHours().trim().equals("00") || periodForm.getEndHours().trim().equals("0") || periodForm.getEndHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.endHours",new ActionError("knowledgepro.attn.period.endHours"));
			}
			if(periodForm.getSession()==null){
				errors.add("knowledgepro.attn.period.session",new ActionError("knowledgepro.attn.period.session"));
			}
			if(errors.isEmpty()){
				validateTime(periodForm, errors);
			}				
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				int tempYear;  // if year is selected then loading classes based on the selected year, otherwise loading based on the current year
				if(periodForm.getYear() != null && !periodForm.getYear().isEmpty()){
					tempYear = Integer.parseInt(periodForm.getYear());
				}
				else
				{
					tempYear = currentYear;
				}
				setClassListToForm(periodForm, tempYear);				
				request.setAttribute("periodOperation", "edit");
				return mapping.findForward(CMSConstants.PERIOD_ENTRY);
			}
			setUserId(request, periodForm);
			isUpdated = PeriodHandler.getInstance().addPeriod(periodForm, "Edit");
			setPeriodListToForm(periodForm);
			if(periodForm.getYear() != null && !periodForm.getYear().isEmpty()){
				currentYear = Integer.parseInt(periodForm.getYear());
			}
			setClassListToForm(periodForm, currentYear);				
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.attn.duplicate"));
			saveErrors(request, errors);
			int tempYear = currentYear; // if year is selected then loading classes based on the selected year, otherwise loading based on the current year
			if(periodForm.getYear() != null && !periodForm.getYear().isEmpty()){
				tempYear = Integer.parseInt(periodForm.getYear());
			}
			setClassListToForm(periodForm, tempYear);			
			request.setAttribute("periodOperation", "edit");
			return mapping.findForward(CMSConstants.PERIOD_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of period page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				periodForm.setErrorMessage(msg);
				periodForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.attn.period.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			periodForm.reset(mapping, request);
			//initializing time after update
			periodForm.setStartHours("00");
			periodForm.setStartMins("00");
			periodForm.setEndHours("00");
			periodForm.setEndMins("00");
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.attn.period.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute("periodOperation", "add");
		log.debug("Leaving action class updatePeriod");
		return mapping.findForward(CMSConstants.PERIOD_ENTRY);

	}	
	

	/**
	 * setting class list to form for populating in class multiselect box
	 * @param periodForm
	 * @throws Exception
	 */
	public void setClassListToForm(PeriodForm periodForm, int year) throws Exception{
		log.info("Entering into setpClassMapToRequest of CreatePracticalBatchAction");
		try {
			Map<Integer, String> classMap = null;
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
			periodForm.setClassMap(classMap);
		} catch (Exception e) {
			log.error("Error occured in setpClassMapToRequest of CreatePracticalBatchAction");
		}
		log.info("Leaving into setpClassMapToRequest of CreatePracticalBatchAction");
	}
	/**
	 * setting period list to form to display in the UI
	 * @param periodForm
	 * @throws Exception
	 */
	public void setPeriodListToForm(PeriodForm periodForm) throws Exception{
		log.debug("Action class. inside setPeriodListToForm");
		if (periodForm.getYear() == null
				|| periodForm.getYear().isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			periodForm.setYear(Integer.toString(currentYear));
			List<PeriodTO> periodToList = PeriodHandler.getInstance().getAllPeriod(periodForm); 
			setClassListToForm(periodForm, currentYear);
			if(periodToList != null)
			periodForm.setPeriodTOList(periodToList);
		} else {
			int year = Integer.parseInt(periodForm.getYear().trim());
			periodForm.setYear(Integer.toString(year));
			List<PeriodTO> periodToList = PeriodHandler.getInstance().getAllPeriod(periodForm); 
			setClassListToForm(periodForm, year);
			if(periodToList != null)
			periodForm.setPeriodTOList(periodToList);
		}
		
		log.debug("Action Class. leaving setPeriodListToForm");
		
	}	
	
	/**
	 * method used for loading period in edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
						HttpServletResponse response ) throws Exception{
		log.debug("Action. inside loadPeriod");
		request.setAttribute("periodOperation", "edit");
		PeriodForm periodForm = (PeriodForm)form;
		setRequiredDataToForm(periodForm);  //setting data to form in edit
		log.debug("Action. Leaving loadPeriod");
		return mapping.findForward(CMSConstants.PERIOD_ENTRY);
	}
	
	/**
	 * setting required data to form while clicking on the edit button
	 * @param periodForm
	 * @throws Exception
	 */
	public void setRequiredDataToForm(PeriodForm periodForm) throws Exception {
		log.debug("Action. inside setRequiredDataToForm");
		List<PeriodTO> periodToList = PeriodHandler.getInstance().getPeriod(periodForm.getId(), periodForm);
		
		if(periodToList.size() <= 0){
			return;
		}
		Iterator<PeriodTO> itr = periodToList.iterator();
		while(itr.hasNext()){
			PeriodTO periodTO = itr.next();
			periodForm.setPeriodName(periodTO.getPeriodName());
			periodForm.setStartHours(periodTO.getStartTime().substring(0, 2));
			periodForm.setStartMins(periodTO.getStartTime().substring(3,5));
			periodForm.setEndHours(periodTO.getEndTime().substring(0, 2));
			periodForm.setEndMins(periodTO.getEndTime().substring(3,5));
			periodForm.setYear(Integer.toString(periodTO.getClassSchemewiseTO().getCurriculumSchemeDurationTO().getAcademicYear()));
		if(periodTO.getSession().equalsIgnoreCase("afternoon")){
			periodForm.setSession("pm");
		}else{
			periodForm.setSession("am");
		}
			String ClassesIdArray[] = new String[1];
			ClassesIdArray[0] = Integer.toString(periodTO.getClassSchemewiseTO().getId());
			periodForm.setSelectedClasses(ClassesIdArray);
			setClassListToForm(periodForm, periodTO.getClassSchemewiseTO().getCurriculumSchemeDurationTO().getAcademicYear());			
		}
		log.debug("Action. Leaving setRequiredDataToForm");
		
	}

	/**
	 * time validation. checking time format, start time should not be greater than end time.
	 * @param periodForm
	 * @param errors
	 */
	private void validateTime(PeriodForm periodForm, ActionErrors errors){
//		if (errors == null){
//			errors = new ActionErrors();}
			//time format checking
		if(CommonUtil.checkForEmpty(periodForm.getStartHours())){
			if(Integer.parseInt(periodForm.getStartHours())>=24){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}
		
		//time format checking
		if(CommonUtil.checkForEmpty(periodForm.getStartMins())){
			if(Integer.parseInt(periodForm.getStartMins())>=60){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}		
		
		//time format checking
		if(CommonUtil.checkForEmpty(periodForm.getEndHours())){
			if(Integer.parseInt(periodForm.getEndHours())>=24){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}		
		
		//time format checking
		if(CommonUtil.checkForEmpty(periodForm.getEndMins())){
			if(Integer.parseInt(periodForm.getEndMins())>=60){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}
		//if start time greater than end time then showing error message
		if(CommonUtil.checkForEmpty(periodForm.getStartHours()) && CommonUtil.checkForEmpty(periodForm.getEndHours())){
			if(Integer.parseInt(periodForm.getStartHours())> Integer.parseInt(periodForm.getEndHours())){
				if(errors.get("knowledgepro.attn.start.greater")!=null && !errors.get("knowledgepro.attn.start.greater").hasNext()){									
					errors.add("knowledgepro.attn.start.greater",new ActionError("knowledgepro.attn.start.greater"));
					return;
				}
			}			
		}
		
		
		
		String startMins; 
		String endMins;
		if(periodForm.getStartMins() != null && !periodForm.getStartMins().isEmpty()){
			startMins = periodForm.getStartMins();
		}
		else{
			startMins = "00";
		}
		if(periodForm.getEndMins() != null && !periodForm.getEndMins().isEmpty()){
			endMins = periodForm.getEndMins();
		}
		else{
			endMins = "00";
		}					

		String startTime = CommonUtil.getTime(periodForm.getStartHours(), startMins).trim();
		String endTime = CommonUtil.getTime(periodForm.getEndHours(), endMins).trim();
		
		int startTimeInMins = 0;
		int endTimeInMins = 0;
		if(periodForm.getStartHours()!= null && !periodForm.getStartHours().trim().isEmpty()){
			startTimeInMins = Integer.parseInt(periodForm.getStartHours()) * 60;
		}
		if(periodForm.getEndHours()!= null && !periodForm.getEndHours().trim().isEmpty()){
			endTimeInMins = Integer.parseInt(periodForm.getEndHours()) * 60;
		}
		if(periodForm.getStartMins()!= null && !periodForm.getStartMins().trim().isEmpty()){
			startTimeInMins = startTimeInMins + Integer.parseInt(periodForm.getStartMins());
		}
		if(periodForm.getEndMins()!= null && !periodForm.getEndMins().trim().isEmpty()){
			endTimeInMins = endTimeInMins + Integer.parseInt(periodForm.getEndMins());
		}
		
		if(CommonUtil.checkForEmpty(periodForm.getStartHours()) && CommonUtil.checkForEmpty(periodForm.getEndHours())){
			 if(startTime.equals(endTime) ){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						if(errors.get("knowledgepro.attn.start.greater")!=null && !errors.get("knowledgepro.attn.start.greater").hasNext()){
							errors.add("knowledgepro.attn.start.end.equal",new ActionError("knowledgepro.attn.start.end.equal"));
							return;
						}
					}
			 }
			
			 if(startTimeInMins > endTimeInMins){
				errors.add("knowledgepro.attn.start.greater",new ActionError("knowledgepro.attn.start.greater"));
			 }
		}
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response to delete record from period table
	 * @return forward to periodEntry
	 * @throws Exception
	 */
	public ActionForward deletePeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {

		log.debug("inside DeletedeletePeriod Action");
		PeriodForm periodForm = (PeriodForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
		if (periodForm.getId() != 0) {
			int periodId = periodForm.getId();
			setUserId(request, periodForm);
			isDeleted = PeriodHandler.getInstance().deletePeriod(periodId,false,periodForm); 
			setPeriodListToForm(periodForm);
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			if(periodForm.getYear()!=null && !periodForm.getYear().isEmpty()){
				currentYear=Integer.parseInt(periodForm.getYear());
			}
			setClassListToForm(periodForm, currentYear); //after delete loading classes based on the current academic year	
			}
		} catch (Exception e) {
			log.error("error in delete deletePeriod...", e);
			errors.add("error", new ActionError("knowledgepro.attn.period.deletefailure"));
    		saveErrors(request,errors);			
		}
		
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.attn.period.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			periodForm.reset(mapping, request);
			//initializing time after delete
			periodForm.setStartHours("00");
			periodForm.setStartMins("00");
			periodForm.setEndHours("00");
			periodForm.setEndMins("00");
		} /*else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.attn.period.deletefailure"));
			saveErrors(request, errors);
		}*/
		log.debug("inside deletePeriod");
		return mapping.findForward(CMSConstants.PERIOD_ENTRY);
	}
	
	public ActionForward setPeriodEntry(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			log.debug("Entering initPeriod");
			PeriodForm periodForm = (PeriodForm) form;
			try {
			//periodForm.reset(mapping, request);
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setUserId(request, periodForm);
			// period list accourding to year
			setPeriodListToForm(periodForm);
			periodForm.setStartHours("00");
			periodForm.setStartMins("00");
			periodForm.setEndHours("00");
			periodForm.setEndMins("00");
			}
			catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			periodForm.setErrorMessage(msg);
			periodForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
			String msg = super.handleApplicationException(e);
			periodForm.setErrorMessage(msg);
			periodForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			}
			log.debug("Action class. Leaving initPeriod ");
			return mapping.findForward(CMSConstants.PERIOD_ENTRY);
			}
	
	
}
