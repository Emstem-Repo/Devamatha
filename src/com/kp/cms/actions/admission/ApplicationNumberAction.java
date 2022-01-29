package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.ibm.icu.util.Calendar;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.ApplicationNumberForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.ApplicationNumberHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.CourseApplicationNoTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.ApplicationNumberTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;


/**
 * Date Created : 12 feb 2009 This action class used for
 *         Application No. Ranges for online and offline related config
 *         operation
 * 
 */
@SuppressWarnings("deprecation")
public class ApplicationNumberAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ApplicationNumberAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            setting courseList to request for showing in multiple selection, 
	 *            setting applicationNoList to request to display all items in the UI
	 * @return forward to Application Number Entry
	 * @throws Exception
	 */

	public ActionForward initApplicationNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("inside initApplicationNumber in Action");
		ApplicationNumberForm applicationNumberForm = (ApplicationNumberForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Application_No_Entry");
		
		try {
			final String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			applicationNumberForm.setYear(null);
			setApplicationToRequest(request, applicationNumberForm);
			applicationNumberForm.setProgramTypeId(null);
			applicationNumberForm.setSelectedProgram(null);
			applicationNumberForm.setCourseMap(null);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			applicationNumberForm.setProgramTypeList(programTypeList);
			setCourseList(applicationNumberForm);
			applicationNumberForm.setSelectedProgram(null);
			applicationNumberForm.setSelectedCourse(null);
			applicationNumberForm.setSelectedCourseIdNos(null);
			applicationNumberForm.setCourseApplicationNumberList(null);
			applicationNumberForm.setCurrentOfflineApplnNo(null);
			applicationNumberForm.setCurrentOnlineApplnNo(null);
			//applicationNumberForm.setYear(null);
			setUserId(request, applicationNumberForm);  //setting userID for updating last changed details
		} catch (Exception e) {
			log.error("error in initApplicationNumber method...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				applicationNumberForm.setErrorMessage(msg);
				applicationNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving initApplicationNumber in Action");
		return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method inserts a record into application number table. this
	 *         method will be called while submitting the form
	 * @throws Exception
	 */

	public ActionForward addApplicationNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside addApplicationNumber Action");
		ApplicationNumberForm applicationNumberForm = (ApplicationNumberForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = applicationNumberForm.validate(mapping, request);
		boolean isAdded;
		try {

			if (!errors.isEmpty()) {
				setCourseList(applicationNumberForm);
				saveErrors(request, errors);
				setApplicationToRequest(request, applicationNumberForm);
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			}
			String ErrMessage = isOnlineAndOfflineApplicationNoRangeValid(applicationNumberForm);  //online/offline starting ending no. validations
			if (!ErrMessage.isEmpty()) {
				ActionMessage message = new ActionMessage("knowledgepro.admission.empty.err.message", ErrMessage);
				messages.add("messages", message);
				saveErrors(request, messages);
				setCourseList(applicationNumberForm);
				setApplicationToRequest(request, applicationNumberForm);
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			}

			isAdded = ApplicationNumberHandler.getInstance().addApplicationNumber(applicationNumberForm, "Add");

			setCourseList(applicationNumberForm);
			setApplicationToRequest(request, applicationNumberForm);

		} catch (Exception e) {
			log.error("error in final submit of Application Number page...", e);
			if (e instanceof DuplicateException) {
				errors.add("error", new ActionError("knowledgepro.admission.applicationno.addexist",
						applicationNumberForm.getCourseName(), applicationNumberForm.getYear()));
				saveErrors(request, errors);
				setCourseList(applicationNumberForm);
				setApplicationToRequest(request, applicationNumberForm);
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			} else if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				applicationNumberForm.setErrorMessage(msg);
				applicationNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admission.applicationnumber.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			applicationNumberForm.reset(mapping, request);
			//applicationNumberForm.setYear(null);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admission.applicationnumber.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addApplicationNumber Action");
		return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);

	}

	/**
	 * 
	 * @return setting program type list to request for populating in the form
	 * 
	 */
	public void setProgramtypelist(HttpServletRequest request) throws Exception{
		log.debug("inside setProgramtypelist in Action");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		if (programTypeList != null) {
			request.setAttribute("programTypeList", programTypeList);

		} else {
			log.error("No records found :: List is empty");
		}
		log.debug("inside setProgramtypelist in Action");
	}

	/**
	 * 
	 * getting all the records from application number table and setting to request
	 * 
	 */
	public void setApplicationToRequest(HttpServletRequest request, ApplicationNumberForm applicationNumberForm)throws Exception {
		log.debug("inside setApplicationToRequest in Action");
		if(applicationNumberForm.getYear()== null ||applicationNumberForm.getYear().isEmpty()){
			Calendar calendar=Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getCurrentAcademicyear();
			if(year!=0){
				currentYear = year;
			}
			List<ApplicationNumberTO> applicationNoList = ApplicationNumberHandler.getInstance().getApplicationNumber(0, applicationNumberForm, currentYear);
			request.setAttribute("applicationNoList", applicationNoList);
		}else{
			int year = Integer.parseInt(applicationNumberForm.getYear().trim());
			List<ApplicationNumberTO> applicationNoList = ApplicationNumberHandler.getInstance().getApplicationNumber(0, applicationNumberForm,year);
			request.setAttribute("applicationNoList", applicationNoList);
		}
		//List<ApplicationNumberTO> applicationNoList = ApplicationNumberHandler.getInstance().getApplicationNumber(0, applicationNumberForm);
		//request.setAttribute("applicationNoList", applicationNoList);
		log.debug("leaving setApplicationToRequest in Action");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will load a particular record for edit according to the id.
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward loadApplicationNumberForEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
														HttpServletResponse response) throws Exception {

		log.debug("inside loadApplicationNumberForEdit in action");
		ApplicationNumberForm applicationNumberForm = (ApplicationNumberForm) form;
		applicationNumberForm.setCurrentOfflineApplnNo(null);
		applicationNumberForm.setCurrentOnlineApplnNo(null);
		setApplicationToRequest(request, applicationNumberForm);
		setRequiredDataToForm(applicationNumberForm, request);
		setCourseList(applicationNumberForm);
		request.setAttribute("applicationNoOperation", "edit");

		log.debug("leaving loadApplicationNumberForEdit in action");

		return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will set all the required data for edit.
	 * @return to mapping
	 * @throws Exception
	 */

	public void setRequiredDataToForm(ApplicationNumberForm applicationNumberForm, HttpServletRequest request) throws Exception {
		log.debug("inside setRequiredDataToForm in Action");
		applicationNumberForm.setProgramTypeId(null);
		applicationNumberForm.setSelectedProgram(null);
		applicationNumberForm.setSelectedCourse(null);
		applicationNumberForm.setSelectedCourseIdNos(null);
		applicationNumberForm.setCourseApplicationNumberList(null);
		int year = 0;
		if(applicationNumberForm.getYear()!=null){
			year=Integer.parseInt(applicationNumberForm.getYear());
		}
		int applid = Integer.parseInt(request.getParameter("id"));
		List<ApplicationNumberTO> applicationNoList = ApplicationNumberHandler.getInstance().getApplicationNumber(applid, applicationNumberForm,year);
		Iterator<ApplicationNumberTO> applNoIt = applicationNoList.iterator();
		setCourseList(applicationNumberForm);
		
		while (applNoIt.hasNext()) {
			ApplicationNumberTO applicationNumberTO = (ApplicationNumberTO) applNoIt.next();

			applicationNumberForm.setOrigYear(applicationNumberTO.getYear());
			applicationNumberForm.setYear(Integer.toString(applicationNumberTO.getYear()));
			applicationNumberForm.setOnlineAppNoFrom(applicationNumberTO.getOnlineAppNoFrom());
			applicationNumberForm.setOnlineAppNoTill(applicationNumberTO.getOnlineAppNoTill());
			applicationNumberForm.setOfflineAppNoFrom(applicationNumberTO.getOfflineAppNoFrom());
			applicationNumberForm.setOfflineAppNoTill(applicationNumberTO.getOfflineAppNoTill());

			applicationNumberForm.setOrigOnlineAppNoFrom(applicationNumberTO.getOnlineAppNoFrom());
			applicationNumberForm.setOrigOnlineAppNoTill(applicationNumberTO.getOnlineAppNoTill());
			applicationNumberForm.setOrigOfflineAppNoFrom(applicationNumberTO.getOfflineAppNoFrom());
			applicationNumberForm.setOrigOfflineAppNoTill(applicationNumberTO.getOfflineAppNoTill());
			if(applicationNumberTO.getCurrentOnlineApplnNo()!=null){
				applicationNumberForm.setCurrentOnlineApplnNo(applicationNumberTO.getCurrentOnlineApplnNo());
			}
			if(applicationNumberTO.getCurrentOfflineApplnNo()!=null){
				applicationNumberForm.setCurrentOfflineApplnNo(applicationNumberTO.getCurrentOfflineApplnNo());
			}
			int courseArrayLen = applicationNumberTO.getCourseApplicationNoTO().size();
			String courseIdarray[] = new String[courseArrayLen];
			Iterator<CourseApplicationNoTO> itr = applicationNumberTO.getCourseApplicationNoTO().iterator();
			List<CourseApplicationNoTO> OrigSelectedCourseList = new ArrayList<CourseApplicationNoTO>();
			int count = 0;
			List<CourseApplicationNoTO> tempList = new ArrayList<CourseApplicationNoTO>();
			String[] programId=new String[courseArrayLen];
			while (itr.hasNext()){
				CourseApplicationNoTO courseApplicationNoTO = itr.next();
				courseIdarray[count] = Integer.toString(courseApplicationNoTO.getCourseTO().getId());
				OrigSelectedCourseList.add(courseApplicationNoTO);
				tempList.add(courseApplicationNoTO);
				programId[count]=String.valueOf(courseApplicationNoTO.getSelectedProgram().getId());
				count = count + 1;
				applicationNumberForm.setProgramTypeId(String.valueOf(courseApplicationNoTO.getProgramTypeId()));
			}
			applicationNumberForm.setSelectedCourse(courseIdarray);
			applicationNumberForm.setCourseApplicationNumberList(tempList);
			applicationNumberForm.setSelectedProgram(programId);
		
		}
		log.debug("leaving setRequiredDataToForm in Action");

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method update a record in application number table.
	 * @throws Exception
	 */

	public ActionForward updateApplicationNumber(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		log.debug("inside updateApplicationNumber Action");
		ApplicationNumberForm applicationNumberForm = (ApplicationNumberForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = applicationNumberForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			
			if(isCancelled(request)){
				setApplicationToRequest(request, applicationNumberForm);
				setRequiredDataToForm(applicationNumberForm, request);
				request.setAttribute("applicationNoOperation", "edit");
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setCourseList(applicationNumberForm);
				setApplicationToRequest(request, applicationNumberForm);
				request.setAttribute("applicationNoOperation", "edit");
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			}

			String ErrMessage = isOnlineAndOfflineApplicationNoRangeValid(applicationNumberForm);
			ErrMessage=isCurrentOnlineAndCurrentOfflineNoIsValid(applicationNumberForm,ErrMessage);
			if (!ErrMessage.isEmpty()) {
				ActionMessage message = new ActionMessage("knowledgepro.admission.empty.err.message", ErrMessage);
				messages.add("messages", message);
				saveErrors(request, messages);
				setApplicationToRequest(request, applicationNumberForm);
				setCourseList(applicationNumberForm);
				request.setAttribute("applicationNoOperation", "edit");
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			}
			
			isAdded = ApplicationNumberHandler.getInstance().addApplicationNumber(applicationNumberForm, "Edit");

			setApplicationToRequest(request, applicationNumberForm);
			setCourseList(applicationNumberForm);

		} catch (Exception e) {
			log.error("error in final submit of Application Number page...", e);
			if (e instanceof DuplicateException) {
				errors.add("error", new ActionError("knowledgepro.admission.applicationno.addexist",
						applicationNumberForm.getCourseName(),applicationNumberForm.getYear()));
				saveErrors(request, errors);
				setCourseList(applicationNumberForm);
				setApplicationToRequest(request, applicationNumberForm);
				request.setAttribute("applicationNoOperation", "edit");
				return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
			} else if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				applicationNumberForm.setErrorMessage(msg);
				applicationNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admission.applicationnumber.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			applicationNumberForm.reset(mapping, request);
			//applicationNumberForm.setYear(null);
			applicationNumberForm.setCurrentOfflineApplnNo(null);
			applicationNumberForm.setCurrentOnlineApplnNo(null);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admission.applicationnumber.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute("applicationNoOperation", "add");
		log.debug("Leaving updateApplicationNumber Action");
		return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);

	}

	private String isCurrentOnlineAndCurrentOfflineNoIsValid(ApplicationNumberForm applicationNumberForm, String errMessage) throws Exception {
		
		if(applicationNumberForm.getCurrentOnlineApplnNo()==null || applicationNumberForm.getCurrentOnlineApplnNo().isEmpty()){
			errMessage=errMessage+" Please Enter Current Online Appln No";
		}
		if(applicationNumberForm.getCurrentOnlineApplnNo()!=null && !applicationNumberForm.getCurrentOnlineApplnNo().isEmpty()){
			if(!StringUtils.isNumeric(applicationNumberForm.getCurrentOnlineApplnNo())){
				errMessage=errMessage+" Current Online Appln No Should be Numeric ";
			}
		}
		if(applicationNumberForm.getCurrentOfflineApplnNo()!=null && !applicationNumberForm.getCurrentOfflineApplnNo().isEmpty()){
			if(!StringUtils.isNumeric(applicationNumberForm.getCurrentOfflineApplnNo())){
				errMessage=errMessage+" Current Offline Appln No Should be Numeric ";
			}
		}
		if(applicationNumberForm.getOnlineAppNoFrom()!=null && !applicationNumberForm.getOnlineAppNoFrom().isEmpty() && applicationNumberForm.getOnlineAppNoTill()!=null 
				&& !applicationNumberForm.getOnlineAppNoTill().isEmpty() && applicationNumberForm.getCurrentOnlineApplnNo()!=null 
				&& !applicationNumberForm.getCurrentOnlineApplnNo().trim().isEmpty() && StringUtils.isNumeric(applicationNumberForm.getCurrentOnlineApplnNo())){
			long onlineAppStartNo = Long.parseLong(applicationNumberForm.getOnlineAppNoFrom());
			long onlineAppEndNo = Long.parseLong(applicationNumberForm.getOnlineAppNoTill());
			long curOnlineAppNo=Long.parseLong(applicationNumberForm.getCurrentOnlineApplnNo())+1;
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			if(!(curOnlineAppNo>=onlineAppStartNo) || !(curOnlineAppNo<=onlineAppEndNo)){
				errMessage=errMessage+" Current Online App No should be Between Online Application No Range";
			}else{
				boolean unique=txn.checkApplicationNoUniqueForYear((int)curOnlineAppNo,Integer.parseInt(applicationNumberForm.getYear()));
				if(!unique){
					errMessage=errMessage+" Current Online App No is Already Exists for Selected Year ";
				}
			}
			
			
			long offlineAppStartNo = 0;
			long offlineAppEndNo = 0;
			long curOfflineAppNo=0;
			if(applicationNumberForm.getOfflineAppNoFrom() != null && !applicationNumberForm.getOfflineAppNoFrom().isEmpty()){
				offlineAppStartNo = Long.parseLong(applicationNumberForm.getOfflineAppNoFrom());
			}
			if(applicationNumberForm.getOfflineAppNoTill() != null && !applicationNumberForm.getOfflineAppNoTill().isEmpty()){
				offlineAppEndNo = Long.parseLong(applicationNumberForm.getOfflineAppNoTill());
			}
			if(applicationNumberForm.getCurrentOfflineApplnNo()!=null && !applicationNumberForm.getCurrentOfflineApplnNo().isEmpty() && StringUtils.isNumeric(applicationNumberForm.getCurrentOfflineApplnNo())){
				curOfflineAppNo=Long.parseLong(applicationNumberForm.getCurrentOfflineApplnNo())+1;
			}
			if(offlineAppStartNo>0 || offlineAppEndNo>0){
				if(curOfflineAppNo==0){
					errMessage=errMessage+" Please Enter Current Offline No";
				}else{
					if(!(curOfflineAppNo>=offlineAppStartNo) || !(curOfflineAppNo<=offlineAppEndNo)){
						errMessage=errMessage+" Current Offline App No should be Between Offline Application No Range";
					}else{
						boolean unique=txn.checkApplicationNoUniqueForYear((int)curOfflineAppNo,Integer.parseInt(applicationNumberForm.getYear()));
						if(!unique){
							errMessage=errMessage+" Current Offline App No is Already Exists for Selected Year ";
						}
					}
				}
			}
			
		}
		return errMessage;
	}

	/**
	 * 
	 * @return This method sets program map to request for setting in edit
	 *         option
	 * @throws Exception
	 */

	public void setprogramMapToRequest(HttpServletRequest request, ApplicationNumberForm applicationNumberForm) {
		log.debug("inside setprogramMapToRequest in Action");
		if (applicationNumberForm.getProgramTypeId() != null && (!applicationNumberForm.getProgramTypeId().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().
					getProgramsByProgramType(Integer.parseInt(applicationNumberForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
		log.debug("leaving setprogramMapToRequest in Action");
	}

	/**
	 * 
	 * @return This method sets course map to request for setting in edit option
	 * @throws Exception
	 */

	public void setpCourseMapToRequest(HttpServletRequest request,ApplicationNumberForm applicationNumberForm) {
		log.debug("inside setpCourseMapToRequest in Action");
		if (applicationNumberForm.getProgramId() != null
				&& (!applicationNumberForm.getProgramId().isEmpty())) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.parseInt(applicationNumberForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
		log.debug("leaving setpCourseMapToRequest in Action");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing application Entry
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteApplicationNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		log.debug("inside deleteApplicationNumber Action");
		ApplicationNumberForm applicationNumberForm = (ApplicationNumberForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (applicationNumberForm.getId() != 0) {
				int delId = applicationNumberForm.getId();
				isDeleted = ApplicationNumberHandler.getInstance().deleteApplicationNo(delId,false,applicationNumberForm);
			}
		} catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				applicationNumberForm.setErrorMessage(msg);
				applicationNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setCourseList(applicationNumberForm);
		setApplicationToRequest(request, applicationNumberForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage(
					"knowledgepro.admission.applicationnumber.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			applicationNumberForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError(
					"knowledgepro.admission.applicationnumber.deletefailure",
					applicationNumberForm.getCourseId()));
			saveErrors(request, errors);
		}
		log.debug("inside delete Application Number Action");
		return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
	}

	/**
	 * 
	 * @return validation for application no. ranges
	 */

	public String isOnlineAndOfflineApplicationNoRangeValid(ApplicationNumberForm applicationNumberForm) throws Exception {
		log.debug("inside isOnlineAndOfflineApplicationNoRangeValid in Action");
		String msg = "";
		
		if(applicationNumberForm.getOfflineAppNoFrom() != null && !applicationNumberForm.getOfflineAppNoFrom().trim().isEmpty()){
			if(applicationNumberForm.getOfflineAppNoTill() == null || applicationNumberForm.getOfflineAppNoTill().trim().isEmpty()){
				msg = "Please enter offline application no. till.";
				return msg;
			}
		}
		
		long onlineAppStartNo = Long.parseLong(applicationNumberForm.getOnlineAppNoFrom());
		long onlineAppEndNo = Long.parseLong(applicationNumberForm.getOnlineAppNoTill());
		
		long offlineAppStartNo = 0;
		long offlineAppEndNo = 0;
		if(applicationNumberForm.getOfflineAppNoFrom() != null && !applicationNumberForm.getOfflineAppNoFrom().isEmpty()){
			offlineAppStartNo = Long.parseLong(applicationNumberForm.getOfflineAppNoFrom());
		}
		if(applicationNumberForm.getOfflineAppNoTill() != null && !applicationNumberForm.getOfflineAppNoTill().isEmpty()){
			offlineAppEndNo = Long.parseLong(applicationNumberForm.getOfflineAppNoTill());
		}
		
		
		if(offlineAppStartNo == 0 && offlineAppEndNo ==0){
			if (onlineAppStartNo > onlineAppEndNo) {
				msg = "Online Application Start No. should not be greater than End No.";
			}
		}
		else
		{
			if (onlineAppStartNo > onlineAppEndNo) {
				msg = "Online Application Start No. should not be greater than End No.";
				}
			else if (onlineAppEndNo < onlineAppStartNo) {
				msg = (onlineAppStartNo) + "Online Application End No. should be greater than Start No.";
				} 
			else if (offlineAppStartNo > offlineAppEndNo) {
				msg = "Offline Application End No. should be greater than Start No.";
				} 
			else if (offlineAppEndNo < offlineAppStartNo) {
				msg = "Online Application End No. should be greater than Start No.";
				} 
			else if (!((offlineAppStartNo > onlineAppEndNo)
					|| (offlineAppStartNo < onlineAppStartNo))) {
				msg = "Please enter a valid Range. Range should be different for Online and Offline";
			}
			else if (!((offlineAppEndNo < onlineAppStartNo)
					|| (offlineAppEndNo > onlineAppEndNo))) {
				msg = "Please enter a valid Range. Range should be different for Online and Offline";
			} else if ((offlineAppStartNo >= onlineAppStartNo)
						&& offlineAppStartNo <= onlineAppEndNo) {
					msg = "Please enter a valid Range. Range should be different for Online and Offline";
			}
		}
		log.debug("leaving isOnlineAndOfflineApplicationNoRangeValid in Action");
		return msg;
	}

	/**
	 * 
	 * @param applicationNumberForm
	 * @throws Exception
	 */
	public void setCourseList(ApplicationNumberForm applicationNumberForm) throws Exception{
		log.debug("inside setCourseList in Action");
		
		List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
		applicationNumberForm.setProgramList(programList);
		Map<Integer, String> programMap = ProgramHandler.getInstance().getProgramMap();
		applicationNumberForm.setProgramMap(programMap);
		//applicationNumberForm.setSelectedProgram(applicationNumberForm.getSelectedProgram());
		if(applicationNumberForm.getSelectedProgram()!=null && applicationNumberForm.getSelectedProgram().length>0){
			Set<Integer> ids = new HashSet<Integer>();
			for (int i = 0; i < applicationNumberForm.getSelectedProgram().length; i++) {
				if(applicationNumberForm.getSelectedProgram()[i] != null){
					ids.add(Integer.parseInt(applicationNumberForm.getSelectedProgram()[i]));
				}
			}
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(ids);
			applicationNumberForm.setCourseMap(courseMap);
			
		}
		

		log.debug("leaving setCourseList in Action");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setCourseEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside setCourseEntry in Action");
		ApplicationNumberForm applicationNumberForm = (ApplicationNumberForm) form;
		setUserId(request, applicationNumberForm);  //setting userID for updating last changed details
		try{
			setApplicationToRequest(request, applicationNumberForm);
			setCourseList(applicationNumberForm);
			
//			applicationNumberForm.setSelectedCourse(null);
//			applicationNumberForm.setSelectedCourseIdNos(null);
//			applicationNumberForm.setCourseApplicationNumberList(null);
//			applicationNumberForm.setCurrentOfflineApplnNo(null);
//			applicationNumberForm.setCurrentOnlineApplnNo(null);
		}catch (Exception e) {
			log.error("error in initApplicationNumber method...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				applicationNumberForm.setErrorMessage(msg);
				applicationNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward(CMSConstants.APPLICATION_NUMBER_ENTRY);
	}
}
