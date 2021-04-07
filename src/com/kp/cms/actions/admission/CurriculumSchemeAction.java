package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.CurriculumSchemeForm;
import com.kp.cms.handlers.admin.CourseSchemeHandler;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.handlers.admission.CurriculumSchemeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.CourseSchemeTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admission.CurriculumSchemeDurationTO;
import com.kp.cms.to.admission.CurriculumSchemeTO;
import com.kp.cms.transactions.admin.ICourseSchemeTransaction;
import com.kp.cms.transactionsimpl.admin.CourseSchemeTransactionImpl;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


@SuppressWarnings("deprecation")
public class CurriculumSchemeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CurriculumSchemeAction.class);
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final String CURRICULUMSCHEME = "curriculumScheme";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will gets the courseSchemeID and courseSchemeName along with courseSchemeID
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initCurriculumScheme(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Initialize Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm = (CurriculumSchemeForm) form;
		try {	
			//Assigns all the curriculums available to form
			assignListToForm(curriculumSchemeForm);
			
		}
		catch (Exception e) {
			log.error("Error occured in initCurriculumScheme of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		curriculumSchemeForm.clear();
		log.info("Exit Curriculumschemeinit");
		return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);		
	}

	public ActionForward FilterListToYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Initialize Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm = (CurriculumSchemeForm) form;
		try {	
			//Assigns all the curriculums available to form
			
			
			assignListToForm(curriculumSchemeForm);
	
		}
		catch (Exception e) {
			log.error("Error occured in initCurriculumScheme of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		curriculumSchemeForm.clear();
	
		log.info("Exit Curriculumschemeinit");
		return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);		
	}
	
	
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method displays the CurriculumScheme Entry based on the no.
	 *         of courses Also returns courseSchemeID and courseSchemeId and
	 *         SubjectGroupID & SubjectGroupId
	 *         
	 * Capturing the form data and adding to CurriculumScheme BO object and setting the session scope
	 * @throws Exception
	 */

	public ActionForward showCurriculumSchemeOnCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CurriculumSchemeForm curriculumSchemeForm = (CurriculumSchemeForm) form;
		 ActionErrors errors = curriculumSchemeForm.validate(mapping, request);		
		try {
			if (errors.isEmpty()) {
			/**
				 * Getting courseId & Year from Formbean and checking that into Database whether Curriculum Scheme for the same is added or not
				 * (Duplicate record check). If exists then add the appropriate error message
				 */
					int courseId=Integer.parseInt(curriculumSchemeForm.getCourse());
					int year=Integer.parseInt(curriculumSchemeForm.getYear());
					List<CurriculumScheme> isExistCourseId=CurriculumSchemeHandler.getInstance().isExistCourseId(courseId, year);
						if(!isExistCourseId.isEmpty()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EXISTS));
							saveErrors(request, errors);
							assignListToForm(curriculumSchemeForm);
							return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);				
							}						
				// Capturing the form data and adding to CurriculumScheme BO object and storing it to session scope
				CourseScheme courseScheme=new CourseScheme();
				CurriculumScheme curriculumScheme = new CurriculumScheme();
				curriculumScheme.setNoScheme(Integer.parseInt(curriculumSchemeForm.getNoOfScheme()));
				curriculumScheme.setYear(Integer.parseInt(curriculumSchemeForm.getYear()));
				curriculumScheme.setCreatedDate(new Date());
				curriculumScheme.setLastModifiedDate(new Date());
				curriculumScheme.setCreatedBy(curriculumSchemeForm.getUserId());
				curriculumScheme.setModifiedBy(curriculumSchemeForm.getUserId());
				courseScheme.setId(Integer.parseInt(curriculumSchemeForm.getSchemeId()));
				curriculumScheme.setCourseScheme(courseScheme);
				Course course=new Course();
				course.setId(Integer.parseInt(curriculumSchemeForm.getCourse()));
				curriculumScheme.setCourse(course);
				HttpSession session = request.getSession(true);
				session.setAttribute(CURRICULUMSCHEME, curriculumScheme);			
				
				/**
				 * Getting the no. of scheme value selected in the UI and based on that creating 
				 * that many Curriculumscheme TOs and finally adding to a list
				 */
				int noOfScheme = Integer.parseInt(curriculumSchemeForm.getNoOfScheme());
				List<CurriculumSchemeTO> toList = new ArrayList<CurriculumSchemeTO>();
				
				int temp= 0;
				for (int i = 0; i < noOfScheme; i++) {
					CurriculumSchemeTO curriculumSchemeTO = new CurriculumSchemeTO();
					if(temp == 0){
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					curriculumSchemeTO.setYear(currentYear);
					}
					else
						curriculumSchemeTO.setYear(curriculumScheme.getYear());	
					toList.add(curriculumSchemeTO);
					curriculumSchemeForm.setCurriculumSchemeDetails(toList);
					temp++;
					}
			 //Calling the Subject Group Handler method and getting all the subject names  based on the courseId (If exists)
				
				List<SubjectGroupTO> subjectGroupList = SubjectGroupHandler.getInstance().getSubjectGroupDetails(courseId);
//				Collections.sort(subjectGroupList, new SubjectGroupComparator());
				if(!subjectGroupList.isEmpty()){
				curriculumSchemeForm.setSubjectGroupList(subjectGroupList);
				}
				/**
				 * Else add appropriate error message
				 */
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SUBJECTGROUP_EMPTY));
					saveErrors(request, errors);
					assignListToForm(curriculumSchemeForm);
					return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);							
				}
			} else {
				saveErrors(request, errors);
				assignListToForm(curriculumSchemeForm);
				return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
			}
		} catch (Exception e) {
			log.error("Error occured in showCurriculumSchemeOnCourse of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit showCurriculumSchemeOnCourse");
		return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method saves a curricular scheme for a course and year based on the startdate and
	 *         end date and subjects
	 * @throws Exception
	 */
	
	public ActionForward setCurriculumScheme(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Inside setCurriculumScheme");
		CurriculumSchemeForm curriculumSchemeForm = (CurriculumSchemeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = curriculumSchemeForm.validate(mapping, request);
		HttpSession httpSession = request.getSession(false);
		try{
		if(errors.isEmpty())
		{
			//Below condition works while clicking reset button in save mode
			if(isCancelled(request)){
				/**
				 * Getting courseId & Year from Formbean and checking that into Database whether Curriculum Scheme for the same is added or not
				 * (Duplicate record check). If exists then add the appropriate error message
				 */
						int courseId=Integer.parseInt(curriculumSchemeForm.getCourse());
						int year=Integer.parseInt(curriculumSchemeForm.getYear());
						List<CurriculumScheme> isExistCourseId=CurriculumSchemeHandler.getInstance().isExistCourseId(courseId, year);
							if(!isExistCourseId.isEmpty()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EXISTS));
								saveErrors(request, errors);
								assignListToForm(curriculumSchemeForm);
								return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);				
								}										
					/**
					 * Getting the no. of scheme value selected in the UI and based on that creating 
					 * that many Curriculumscheme TOs and finally adding to a list
					 */
					int noOfScheme = Integer.parseInt(curriculumSchemeForm.getNoOfScheme());
					List<CurriculumSchemeTO> toList = new ArrayList<CurriculumSchemeTO>();
					
					int temp= 0;
					for (int i = 0; i < noOfScheme; i++) {
						CurriculumSchemeTO curriculumSchemeTO = new CurriculumSchemeTO();
						if(temp == 0){
						Calendar calendar = Calendar.getInstance();
						int currentYear = calendar.get(Calendar.YEAR);
						curriculumSchemeTO.setYear(currentYear);
						}
						toList.add(curriculumSchemeTO);
						curriculumSchemeForm.setCurriculumSchemeDetails(toList);
						temp++;
						}
				 //Calling the Subject Group Handler method and getting all the subject names  based on the courseId (If exists)					
					List<SubjectGroupTO> subjectGroupList = SubjectGroupHandler.getInstance().getSubjectGroupDetails(courseId);
//					Collections.sort(subjectGroupList, new SubjectGroupComparator());
					if(!subjectGroupList.isEmpty()){
					curriculumSchemeForm.setSubjectGroupList(subjectGroupList);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
					}
					/**
					 * Else add appropriate error message
					 */
					else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SUBJECTGROUP_EMPTY));
						saveErrors(request, errors);					
						assignListToForm(curriculumSchemeForm);
						return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);							
					}
				
			}
		setUserId(request, curriculumSchemeForm);
		
		/**
		 * 
		 * Getting courseId & Year from session and checking that into Database whether Curriculum Scheme for the same is added or not
		 * (Duplicate record check).
		 */
			CurriculumScheme curriculumScheme = (CurriculumScheme) httpSession.getAttribute(CURRICULUMSCHEME);
			int courseId=curriculumScheme.getCourse().getId();
			int year=curriculumScheme.getYear();
			List<CurriculumScheme> isExistCourseId=CurriculumSchemeHandler.getInstance().isExistCourseId(courseId, year);
			if(!isExistCourseId.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EXISTS));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);				
				}			
		/**
		 * Capturing a list of type curriculumSchemeTO  from formbean and validating all the fields	
		 */
		List<CurriculumSchemeTO> curricumschemedetails = curriculumSchemeForm.getCurriculumSchemeDetails();
		Iterator<CurriculumSchemeTO> iterator=curricumschemedetails.iterator();
		Date prevdate=null;	
		int previousYear=0;
		int temp=0;
		while (iterator.hasNext()) {
			CurriculumSchemeTO curriculumSchemeTO = iterator.next();
			temp++;
			//Iterating the list and only the the first semester is mandatory
			if(temp==1){
			boolean isValidStartDate;
			boolean isValidEndDate;
			if(curriculumSchemeTO.getStartDate()!=null && curriculumSchemeTO.getEndDate()!=null && !curriculumSchemeTO.getStartDate().isEmpty()
			&& !curriculumSchemeTO.getEndDate().isEmpty()){
				//Check for valid date format(Only accepts DD/MM/YYYY)
				isValidStartDate = CommonUtil.isValidDate(curriculumSchemeTO.getStartDate().trim());
				isValidEndDate = CommonUtil.isValidDate(curriculumSchemeTO.getEndDate().trim());
				if(!isValidStartDate || !isValidEndDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
				}
			}
		
			if(curriculumSchemeTO!=null && !curriculumSchemeTO.getStartDate().isEmpty() && 
			!curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()!=null)
				{
				String sdate[]=curriculumSchemeTO.getStartDate().split("/");		
				String eDate[]=curriculumSchemeTO.getEndDate().split("/");
				/**
				 * Getting the curriculum scheme year from Bo object and Validating if user entered past date in Curriculum scheme
				 * duration page as compared to curriculum scheme entry page. Add appropriate error message
				 */			
				int curriculumYear=curriculumScheme.getYear(); 
				int startDurationYear=Integer.parseInt(sdate[2]);
				int endDurationYear=Integer.parseInt(eDate[2]);
				//Check for the valid year
				if(startDurationYear < curriculumYear || endDurationYear < curriculumYear)
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_VALIDATE_DURATIONDATE));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
				}
					if(curriculumSchemeTO.getYear()!= 0 && curriculumSchemeTO.getYear()< startDurationYear){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR ));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);			
					}
					if(curriculumSchemeTO.getYear()!= 0 && curriculumSchemeTO.getYear()!= startDurationYear && curriculumSchemeTO.getYear()!= endDurationYear){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_VALID));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
					}
					if(curriculumSchemeTO.getYear()!= 0 && curriculumSchemeTO.getYear() < curriculumYear){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_COMPARE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
					}
				}
			
			/**
			 * Checking if none of any subject is selected. Add the appropriate error message
			 */
			if(curriculumSchemeTO.getSubjectGroup()==null && curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
			}
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()==null){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SDATEEDATE_SUBGROUP));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
			}
			
			/**
			 * Checking the start date and enddate and year fields if all are not selected and add appropriate error message
			 */
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATE_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_BOTHDATE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STARTDATE_YEAR));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			
			/**
			 * Checking if StartDate & Subject Group is not selected and add the appropriate error message
			 */
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STDATE_SUBJECT_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE_YEAR));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			
			/**
			 * Checking if EndDate & Subject Group is not selected and add the appropriate error message
			 */
			if(curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE_SUBJECT_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getYear()==0 && curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_SUBJECT));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			/**
			 * Validating if both Start Date and End Date is same and add appropriate error message
			 */
			
			if(curriculumSchemeTO.getStartDate().equals(curriculumSchemeTO.getEndDate()))
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATESAME));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			
			/**
			 * Checking if the startdate only is empty. Add appropriate error message
			 */
			if(curriculumSchemeTO.getStartDate().isEmpty())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STARTDATE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
			}
			/**
			 * Checking if the enddate only is empty. Add appropriate error message
			 */
			if(curriculumSchemeTO.getEndDate().isEmpty())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
			}
			if(curriculumSchemeTO.getYear()==0){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
			}
			/**
			 * Checking the subject if it is not selected and validating it and add appropriate error message
			 */
			if(curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SUBJECTEMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
			}
			//Check for the semester academic year (Can not be small as compared to last semester)
			
			if(curriculumSchemeTO.getYear() < previousYear){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_COMPARE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
			}
			/**
			 * Compare the two dates and validates that (Start date can not be greater than  End Date)
			 * and also the semester dates. Add the appropriate error messsage
			 * Also a semester startdate can not be same as the last semester enddate
			 */
			
			if(!curriculumSchemeTO.getStartDate().isEmpty() && !curriculumSchemeTO.getEndDate().isEmpty())
			{				
				String stdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeTO.getStartDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
				String edate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
				Date startdate=new Date(stdate);
				Date enddate=new Date(edate);
				
				if(startdate.compareTo(enddate)==1)
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATECOMPARE));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
				}
				
				if(prevdate!=null)
					{
						if(prevdate.compareTo(startdate)==1)
						{
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATE));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
						}
						if(prevdate.compareTo(startdate)==0)
						{
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATESAME));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
						}
					}
				String previousdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
				prevdate=new Date(previousdate);
				
				startdate=null;
				enddate=null;
			}
			previousYear = curriculumSchemeTO.getYear();
			
		}
		if(temp!=1 && temp > 1){
			if(curriculumSchemeTO!=null && !curriculumSchemeTO.getStartDate().isEmpty() || 
			!curriculumSchemeTO.getEndDate().isEmpty() || curriculumSchemeTO.getSubjectGroup()!=null
			|| curriculumSchemeTO.getYear()!= 0){
			
			boolean isValidStartDate = false;
			boolean isValidEndDate = false;
			if(curriculumSchemeTO.getStartDate()!=null && curriculumSchemeTO.getEndDate()!=null && !curriculumSchemeTO.getStartDate().isEmpty()
			&& !curriculumSchemeTO.getEndDate().isEmpty()){
				isValidStartDate = CommonUtil.isValidDate(curriculumSchemeTO.getStartDate().trim());
				isValidEndDate = CommonUtil.isValidDate(curriculumSchemeTO.getEndDate().trim());
				if(!isValidStartDate || !isValidEndDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
				}
			}
			
			if(curriculumSchemeTO!=null && !curriculumSchemeTO.getStartDate().isEmpty() && 
			!curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()!=null)
				{
				String sdate[]=curriculumSchemeTO.getStartDate().split("/");		
				String eDate[]=curriculumSchemeTO.getEndDate().split("/");
				/**
				 * Getting the curriculum scheme year from Bo object and Validating if user entered past date in Curriculum scheme
				 * duration page as compared to curriculum scheme entry page. Add appropriate error message
				 */			
				int curriculumYear=curriculumScheme.getYear(); 
				int startDurationYear=Integer.parseInt(sdate[2]);
				int endDurationYear=Integer.parseInt(eDate[2]);
				
				if(startDurationYear < curriculumYear || endDurationYear < curriculumYear)
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_VALIDATE_DURATIONDATE));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
				}
					if(curriculumSchemeTO.getYear()!= 0 && curriculumSchemeTO.getYear() < curriculumYear){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_COMPARE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
					}
					int tempStartDate = startDurationYear-1;
					int tempEndDate = endDurationYear-1;
					if(curriculumSchemeTO.getYear()!= 0 && curriculumSchemeTO.getYear()!= startDurationYear && curriculumSchemeTO.getYear()!= endDurationYear
					&& curriculumSchemeTO.getYear()!= tempStartDate && curriculumSchemeTO.getYear()!= tempEndDate){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_VALID));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
					}
				}
			
			/**
			 * Checking if none of any subject is selected. Add the appropriate error message
			 */
			if(curriculumSchemeTO.getSubjectGroup()==null && curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
			}
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()==null){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SDATEEDATE_SUBGROUP));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
			}
			
			/**
			 * Checking the start date and enddate and year fields if all are not selected and add appropriate error message
			 */
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATE_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getEndDate().isEmpty())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_BOTHDATE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STARTDATE_YEAR));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			
			/**
			 * Checking if StartDate & Subject Group is not selected and add the appropriate error message
			 */
			if(curriculumSchemeTO.getStartDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STDATE_SUBJECT_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getYear()==0)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE_YEAR));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			
			/**
			 * Checking if EndDate & Subject Group is not selected and add the appropriate error message
			 */
			if(curriculumSchemeTO.getEndDate().isEmpty() && curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE_SUBJECT_EMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			if(curriculumSchemeTO.getYear()==0 && curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_SUBJECT));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			/**
			 * Validating if both Start Date and End Date is same and add appropriate error message
			 */
			
			if(curriculumSchemeTO.getStartDate().equals(curriculumSchemeTO.getEndDate()))
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATESAME));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);
			}
			
			/**
			 * Checking if the startdate only is empty. Add appropriate error message
			 */
			if(curriculumSchemeTO.getStartDate().isEmpty())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STARTDATE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
			}
			/**
			 * Checking if the enddate only is empty. Add appropriate error message
			 */
			if(curriculumSchemeTO.getEndDate().isEmpty())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
			}
			if(curriculumSchemeTO.getYear()==0){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
			}
			/**
			 * Checking the subject if it is not selected and validating it and add appropriate error message
			 */
			if(curriculumSchemeTO.getSubjectGroup()==null)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SUBJECTEMPTY));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);				
			}
			//Check for the semester academic year (Can not be small as compared to last semester)
			
			if(curriculumSchemeTO.getYear() < previousYear){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_COMPARE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
			}
			/**
			 * Compare the two dates and validates that (Start date can not be greater than  End Date)
			 * and also the semester dates. Add the appropriate error messsage
			 * Also a semester startdate can not be same as the last semester enddate
			 */
			
			if(!curriculumSchemeTO.getStartDate().isEmpty() && !curriculumSchemeTO.getEndDate().isEmpty())
			{				
				String stdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeTO.getStartDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
				String edate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
				Date startdate=new Date(stdate);
				Date enddate=new Date(edate);
				
				if(startdate.compareTo(enddate)==1)
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATECOMPARE));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
				}
				
				if(prevdate!=null)
					{
						if(prevdate.compareTo(startdate)==1)
						{
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATE));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
						}
						if(prevdate.compareTo(startdate)==0)
						{
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATESAME));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);	
						}
					}
				String previousdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
				prevdate=new Date(previousdate);
				
				startdate=null;
				enddate=null;
			}
			previousYear = curriculumSchemeTO.getYear();
	
			}
		}
		}	
		boolean isCurriculumSchemeAdded;
		//Adds Curriculumscheme for the selected course and year
		isCurriculumSchemeAdded = CurriculumSchemeHandler.getInstance().setCurriculumScheme(curricumschemedetails, httpSession, curriculumSchemeForm.getUserId());
		if(isCurriculumSchemeAdded)
		{
		//If curriculumscheme successfully added
		messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMISSION_CURRICULUMSCHEME_ADD_SUCCESS));
		saveMessages(request, messages);
		curriculumSchemeForm.clear();
		assignListToForm(curriculumSchemeForm);
		return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
		}
		else{
			//else failed to add curriculumscheme
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ADD_FAILED));
			saveErrors(request, errors);
			curriculumSchemeForm.clear();
			assignListToForm(curriculumSchemeForm);
			return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
		}
		}
		}
		catch (Exception e) {
			log.error("Error occured in setCurriculumScheme of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit setCurriculumScheme");
		return mapping.findForward(CMSConstants.SHOW_CURRICULUMSCHEME_ON_COURSE);		
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Deletes a Currciculumscheme record from DB
	 * @throws Exception
	 */
	public ActionForward deleteCurriculumScheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deleteCurriculumScheme of Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm=(CurriculumSchemeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = curriculumSchemeForm.validate(mapping, request);		
		try
		{
		if(errors.isEmpty())
		{
		HttpSession session = request.getSession(false);
		setUserId(request, curriculumSchemeForm);
		int id=curriculumSchemeForm.getId();
		String userId=curriculumSchemeForm.getUserId();			
		boolean isCurriculumSchemeDeleted;
		isCurriculumSchemeDeleted=CurriculumSchemeHandler.getInstance().deleteCurriculumScheme(id, userId);
		if(isCurriculumSchemeDeleted){
			messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMISSION_CURRICULUMSCHEME_DELETE_SUCCESS));
			saveMessages(request, messages);
			curriculumSchemeForm.clear();
			if(session!=null){
				session.removeAttribute(CURRICULUMSCHEME);
			}
			assignListToForm(curriculumSchemeForm);
			return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
		}
		else{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DELETE_FAILED));
			saveErrors(request, errors);
			curriculumSchemeForm.clear();
			assignListToForm(curriculumSchemeForm);
			return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
		}
		}
		}
		catch(Exception e)
		{
			log.error("Error occured in deleteCurriculumScheme of CurriculumSchemeAction",e);
			if (e instanceof ApplicationException) {
				assignListToForm(curriculumSchemeForm);
				return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(curriculumSchemeForm);
				return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
			}
		}
		log.info("Leaving into  deleteCurriculumScheme of Curriculumscheme entry");
		return mapping.findForward(CMSConstants.DISPLAY_CURRICULUMSCHEME_DETAILS);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Displays the informations which is to be update
	 * @throws Exception
	 */

	public ActionForward editCurriculumScheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  editCurriculumScheme of Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm=(CurriculumSchemeForm)form;
		 ActionErrors errors = curriculumSchemeForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){	
			int curriculumId=curriculumSchemeForm.getId();
			
			CurriculumSchemeTO curriculumSchemeTO=CurriculumSchemeHandler.getInstance().getCurriculumSchemeDetailsOnId(curriculumId, curriculumSchemeForm, true);
				if(curriculumSchemeTO!=null){
					List<CurriculumSchemeDurationTO>durationList = curriculumSchemeTO.getCurriculumDurationList();
					curriculumSchemeForm.setDurationList(durationList);
				}
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			setProgramMapToRequest(request, curriculumSchemeForm);
			setCourseMapToRequest(request, curriculumSchemeForm);
			assignListToForm(curriculumSchemeForm);
			}
		}
		catch (Exception e) {
			log.error("Error occured in editCurriculumScheme of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		saveErrors(request, errors);
		setProgramMapToRequest(request, curriculumSchemeForm);
		setCourseMapToRequest(request, curriculumSchemeForm);
		assignListToForm(curriculumSchemeForm);
		log.info("Leaving into  editCurriculumScheme of Curriculumscheme entry");
		return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Updates a record in DB with the edited values of startdate and enddate
	 * @throws Exception
	 */
	public ActionForward updateCurriculumScheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateCurriculumScheme of Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm=(CurriculumSchemeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{			
			//Works only if the reset button is clicked in update mode
			if(isCancelled(request)){				
				int curriculumId=curriculumSchemeForm.getId();				
				CurriculumSchemeTO curriculumSchemeTO=CurriculumSchemeHandler.getInstance().getCurriculumSchemeDetailsOnId(curriculumId, curriculumSchemeForm,false);
					List<CurriculumSchemeDurationTO>durationList = curriculumSchemeTO.getCurriculumDurationList();			
						int oldNoOfScheme = curriculumSchemeForm.getOldNoOfScheme();
						int noOfScheme = Integer.parseInt(curriculumSchemeForm.getNoOfScheme());
						//Works for same no. of scheme
						if(oldNoOfScheme == noOfScheme){
							curriculumSchemeForm.setDurationList(durationList);
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
						}
						//Works only when more no. of schemes selected during update mode
						else if(noOfScheme > oldNoOfScheme){										
							int moreSchemes = noOfScheme-oldNoOfScheme;
							CurriculumSchemeDurationTO curriculumSchemeDurationTO = null;
							int i = 0;
							String[] selectedSubjects = new String[0];
							for(i=0; i<moreSchemes ; i++){
								curriculumSchemeDurationTO = new CurriculumSchemeDurationTO();
								curriculumSchemeDurationTO.setSemester(++oldNoOfScheme);
								curriculumSchemeDurationTO.setSubjectGroups(selectedSubjects);
								durationList.add(curriculumSchemeDurationTO);
								curriculumSchemeForm.setDurationList(durationList);
							}
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
						}
						else{
							//Works only when no. of schemes reduced during update mode
							int sizeDiff = oldNoOfScheme - noOfScheme;
							int maxSemNo = oldNoOfScheme - sizeDiff;
							if(durationList!=null && !durationList.isEmpty()){
								Iterator<CurriculumSchemeDurationTO> iterator = durationList.iterator();
								while (iterator.hasNext()) {
									CurriculumSchemeDurationTO curriculumSchemeDurationTO = (CurriculumSchemeDurationTO) iterator.next();
									if(curriculumSchemeDurationTO.getSemester()> maxSemNo){
										iterator.remove();
									}
								}
								curriculumSchemeForm.setDurationList(durationList);
							}
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
						}
					}
		setUserId(request, curriculumSchemeForm);
		
		int schemeId = Integer.parseInt(curriculumSchemeForm.getSchemeId());
		int courseId = Integer.parseInt(curriculumSchemeForm.getCourse());
		
		//Check for the valid course while update
		List<Course> courseList = CourseTransactionImpl.getInstance().getCourse(courseId);
		if(courseList == null){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_COURSE_INVALID));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}
		//Check for the valid scheme update  
		ICourseSchemeTransaction courseSchemeTransaction = new CourseSchemeTransactionImpl();
		CourseScheme courseScheme = courseSchemeTransaction.getNameOnId(schemeId);
		if(courseScheme==null || courseScheme!=null && !courseScheme.getIsActive()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_COURSESCHEME_INVALID));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}
		
		/**
		 * Checks for the semester dates with curriculumscheme entry date
		 * Iterating the set of CurriculumschemeDurationTO and checking for the valid semester dates
		 */
		int year=Integer.parseInt(curriculumSchemeForm.getYear());
		Date prevdate=null;
		int prevoiusYear=0; 
		//Get the duration List from formbean, validate and send handler for update
		List<CurriculumSchemeDurationTO> durationList = curriculumSchemeForm.getDurationList();
		CurriculumSchemeDurationTO curriculumSchemeDurationTO = null;
		int temp=0;
		//Iterating the list and checks only the 1st scheme entry is mandatory
		if(durationList!=null && !durationList.isEmpty()){
		Iterator<CurriculumSchemeDurationTO> iterator = durationList.iterator();
		while (iterator.hasNext()) {
		curriculumSchemeDurationTO = iterator.next();
		temp++;
		boolean isValidStartDate;
		boolean isValidEndDate;
		if(temp == 1){		
		if(curriculumSchemeDurationTO.getStartDate()!=null && !curriculumSchemeDurationTO.getStartDate().isEmpty()
		&& curriculumSchemeDurationTO.getEndDate()!=null && !curriculumSchemeDurationTO.getEndDate().isEmpty()){
			//Check for valid date format(Only accepts DD/MM/YYYY)
			isValidStartDate = CommonUtil.isValidDate(curriculumSchemeDurationTO.getStartDate().trim());
			isValidEndDate = CommonUtil.isValidDate(curriculumSchemeDurationTO.getEndDate().trim());
			if(!isValidStartDate || !isValidEndDate){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);		
			}
		}	
		if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
		curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
		curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
		errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EMPTY));
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}
		if(curriculumSchemeDurationTO.getStartDate()!=null && curriculumSchemeDurationTO.getEndDate()==null || 
		!curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
		curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_COURSESCHEME_EDYEARSG));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}
		if(curriculumSchemeDurationTO.getStartDate()!=null && curriculumSchemeDurationTO.getEndDate()!=null && 
		!curriculumSchemeDurationTO.getStartDate().isEmpty() && !curriculumSchemeDurationTO.getEndDate().isEmpty() &&
		curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_SUBJECT));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
				}		
		if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null && 
				curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
				curriculumSchemeDurationTO.getSelectedIndex() == -1){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SDATEEDATE_SUBGROUP));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
				}
		if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
			curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
			curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATE_EMPTY));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
			}
		if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
		curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_BOTHDATE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}
		if(curriculumSchemeDurationTO.getStartDate()==null || curriculumSchemeDurationTO.getStartDate().isEmpty() ){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STARTDATE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
		}
		if(curriculumSchemeDurationTO.getEndDate()==null || curriculumSchemeDurationTO.getEndDate().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
		}
		if(curriculumSchemeDurationTO.getAcademicYear()==0){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}
		if(curriculumSchemeDurationTO.getSelectedIndex() == -1){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SUBJECTEMPTY));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
		}		
		if(curriculumSchemeDurationTO.getStartDate()!=null && curriculumSchemeDurationTO.getEndDate()!=null && !curriculumSchemeDurationTO.getStartDate().isEmpty() && !curriculumSchemeDurationTO.getEndDate().isEmpty())
			{
			String startDate[]=curriculumSchemeDurationTO.getStartDate().split("/");
			String endDate[]=curriculumSchemeDurationTO.getEndDate().split("/");
			int startDurationYear=Integer.parseInt(startDate[2]);
			int endDurationYear=Integer.parseInt(endDate[2]);
			/**
			 * Checking for the date field validations
			 */
				if(startDurationYear < year || endDurationYear < year){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_VALIDATE_DURATIONDATE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
				}
				if(curriculumSchemeDurationTO.getAcademicYear()!=0 && curriculumSchemeDurationTO.getAcademicYear() < startDurationYear){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
				}
				if(curriculumSchemeDurationTO.getAcademicYear()!=0 && curriculumSchemeDurationTO.getAcademicYear()!= startDurationYear && curriculumSchemeDurationTO.getAcademicYear()!= endDurationYear){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_VALID));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
				}
				if(curriculumSchemeDurationTO.getAcademicYear()!=0 && curriculumSchemeDurationTO.getAcademicYear() < prevoiusYear){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_COMPARE));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
				}			
				if(curriculumSchemeDurationTO.getStartDate().equals(curriculumSchemeDurationTO.getEndDate())){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATESAME));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
				}
					String stdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeDurationTO.getStartDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
					String edate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeDurationTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
					Date startdate=new Date(stdate);
					Date enddate=new Date(edate);
					// Validating if the startdate is greater than enddate then show error message
					if(startdate.compareTo(enddate)==1)
					{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATECOMPARE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
					}					
					if(prevdate!=null)
						{
							//Validating if the current semester startdate is less than last semester enddate
							if(prevdate.compareTo(startdate)==1)
							{
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATE));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
							}
							//Validating if the current semester startdate is equals to last semester enddate
							if(prevdate.compareTo(startdate)==0)
							{
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATESAME));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
							}
						}
					String previousdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeDurationTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
					prevdate=new Date(previousdate);
					startdate=null;
					enddate=null;		
			}
		prevoiusYear = curriculumSchemeDurationTO.getAcademicYear();
		}
		if(temp!=1 && temp>1){		
			if(curriculumSchemeDurationTO.getStartDate()!=null && !curriculumSchemeDurationTO.getStartDate().isEmpty()
			&& curriculumSchemeDurationTO.getEndDate()!=null && !curriculumSchemeDurationTO.getEndDate().isEmpty()){
						//Check for valid date format(Only accepts DD/MM/YYYY)
						isValidStartDate = CommonUtil.isValidDate(curriculumSchemeDurationTO.getStartDate().trim());
						isValidEndDate = CommonUtil.isValidDate(curriculumSchemeDurationTO.getEndDate().trim());
						if(!isValidStartDate || !isValidEndDate){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);		
						}
					}	
			if(curriculumSchemeDurationTO!=null && curriculumSchemeDurationTO.getStartDate()!=null && !curriculumSchemeDurationTO.getStartDate().isEmpty() || 
				curriculumSchemeDurationTO.getEndDate()!=null && !curriculumSchemeDurationTO.getEndDate().isEmpty() || curriculumSchemeDurationTO.getSelectedIndex()!= -1
				|| curriculumSchemeDurationTO.getAcademicYear()!= 0){				
					if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
					curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
					curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EMPTY));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					if(curriculumSchemeDurationTO.getStartDate()!=null && curriculumSchemeDurationTO.getEndDate()==null || 
					!curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
					curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_COURSESCHEME_EDYEARSG));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					if(curriculumSchemeDurationTO.getStartDate()!=null && curriculumSchemeDurationTO.getEndDate()!=null && 
					!curriculumSchemeDurationTO.getStartDate().isEmpty() && !curriculumSchemeDurationTO.getEndDate().isEmpty() &&
					curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_SUBJECT));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
							}
					if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
						curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
						curriculumSchemeDurationTO.getAcademicYear()==0 && curriculumSchemeDurationTO.getSelectedIndex() == -1){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EMPTY));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
						}
					if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
					curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
					curriculumSchemeDurationTO.getSelectedIndex() == -1){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SDATEEDATE_SUBGROUP));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
					curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty() &&
					curriculumSchemeDurationTO.getAcademicYear() == 0){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATE_EMPTY));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					if(curriculumSchemeDurationTO.getStartDate()==null && curriculumSchemeDurationTO.getEndDate()==null || 
					curriculumSchemeDurationTO.getStartDate().isEmpty() && curriculumSchemeDurationTO.getEndDate().isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_BOTHDATE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					if(curriculumSchemeDurationTO.getStartDate()==null || curriculumSchemeDurationTO.getStartDate().isEmpty() ){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_STARTDATE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
					}
					if(curriculumSchemeDurationTO.getEndDate()==null || curriculumSchemeDurationTO.getEndDate().isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ENDDATE));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
					}
					if(curriculumSchemeDurationTO.getAcademicYear()==0){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					if(curriculumSchemeDurationTO.getSelectedIndex() == -1){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SUBJECTEMPTY));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}					
					if(curriculumSchemeDurationTO.getStartDate()!=null && curriculumSchemeDurationTO.getEndDate()!=null && !curriculumSchemeDurationTO.getStartDate().isEmpty() && !curriculumSchemeDurationTO.getEndDate().isEmpty())
						{
						String startDate[]=curriculumSchemeDurationTO.getStartDate().split("/");
						String endDate[]=curriculumSchemeDurationTO.getEndDate().split("/");
						int startDurationYear=Integer.parseInt(startDate[2]);
						int endDurationYear=Integer.parseInt(endDate[2]);
						/**
						 * Checking for the date field validations
						 */
							if(startDurationYear < year || endDurationYear < year){
									errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_VALIDATE_DURATIONDATE));
									saveErrors(request, errors);
									return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
							}
							if(curriculumSchemeDurationTO.getAcademicYear()!=0 && curriculumSchemeDurationTO.getAcademicYear() < year){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_YEAR_COMPARE));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
							}
							int tempStartDate = startDurationYear-1;
							int tempEndDate = endDurationYear - 1;
							if(curriculumSchemeDurationTO.getAcademicYear()!=0 && curriculumSchemeDurationTO.getAcademicYear()!= startDurationYear 
							&& curriculumSchemeDurationTO.getAcademicYear()!= endDurationYear && curriculumSchemeDurationTO.getAcademicYear()!= tempStartDate 
							&& curriculumSchemeDurationTO.getAcademicYear()!= tempEndDate){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_VALID));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
							}
							if(curriculumSchemeDurationTO.getAcademicYear()!=0 && curriculumSchemeDurationTO.getAcademicYear() < prevoiusYear){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_COMPARE));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
							}			
							if(curriculumSchemeDurationTO.getStartDate().equals(curriculumSchemeDurationTO.getEndDate())){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATESAME));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);			
							}
								String stdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeDurationTO.getStartDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
								String edate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeDurationTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
								Date startdate=new Date(stdate);
								Date enddate=new Date(edate);
								// Validating if the startdate is greater than enddate then show error message
								if(startdate.compareTo(enddate)==1)
								{
									errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATECOMPARE));
									saveErrors(request, errors);
									return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
								}								
								if(prevdate!=null)
									{
										//Validating if the current semester startdate is less than last semester enddate
										if(prevdate.compareTo(startdate)==1)
										{
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATE));
											saveErrors(request, errors);
											return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
										}
										//Validating if the current semester startdate is equals to last semester enddate
										if(prevdate.compareTo(startdate)==0)
										{
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATESAME));
											saveErrors(request, errors);
											return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);	
										}
									}
								String previousdate=CommonUtil.ConvertStringToDateFormat(curriculumSchemeDurationTO.getEndDate(), CMSConstants.DEST_DATE,DATE_FORMAT);
								prevdate=new Date(previousdate);
								startdate=null;
								enddate=null;		
						}
					prevoiusYear = curriculumSchemeDurationTO.getAcademicYear();
				}
				}
		}		
		/*
		 * If no validation fails then update the curriculumscheme
		 */
		boolean isUpdated;
		isUpdated=CurriculumSchemeHandler.getInstance().updateCurriculumScheme(curriculumSchemeForm);
			//If updated successfully add the message
			if(isUpdated)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMISSION_CURRICULUMSCHEME_UPDATE_SUCCESS));
				saveMessages(request, messages);
				curriculumSchemeForm.clear();
				return mapping.findForward(CMSConstants.DISPLAY_CURRICULUMSCHEME_DETAILS);
			}
			//If updation failed add the error message
			if(!isUpdated)
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_UPDATE_FAILED));
				saveErrors(request, errors);
				curriculumSchemeForm.clear();
				return mapping.findForward(CMSConstants.DISPLAY_CURRICULUMSCHEME_DETAILS);
			}	
		
		}
		}
		catch (Exception e) {
			log.error("Error occured in updateCurriculumScheme of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into updateCurriculumScheme of Curriculumscheme entry");
		return mapping.findForward(CMSConstants.DISPLAY_CURRICULUMSCHEME_DETAILS);
	}
	
	/**
	 * Used while showing in UI
	 * Assigns list to form
	 * All the curriculumscheme details 
	 */
	
	public void assignListToForm(BaseActionForm form)throws Exception{
		log.info("Entering into assignListToForm of Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm=(CurriculumSchemeForm)form;
			List<CourseSchemeTO> courseSchemeList = CourseSchemeHandler.getInstance().getCourseScheme();
			curriculumSchemeForm.setCourseSchemeList(courseSchemeList);	
			
			if (curriculumSchemeForm.getYear() == null
					|| curriculumSchemeForm.getYear().isEmpty()) 
			{
				/*Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}*/
				List<CurriculumSchemeTO> curriculumSchemeDetailsList = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDetails();
				curriculumSchemeForm.setSchemeDetails(curriculumSchemeDetailsList);
			} 
			else 
			{
				int year = Integer.parseInt(curriculumSchemeForm.getYear().trim());
				List<CurriculumSchemeTO> curriculumSchemeDetailsList = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDetailsYearwise(year);
				curriculumSchemeForm.setSchemeDetails(curriculumSchemeDetailsList);	
				
				
			}
			
			
			log.info("Leaving into assignListToForm of Curriculumscheme entry");
		}
	

	/**
	 * Loads the curriculumschemedetails for Update mode
	 */
	

	public ActionForward loadCurriculumDetailsForUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into loadCurriculumDetailsForUpdate of Curriculumscheme entry");
		CurriculumSchemeForm curriculumSchemeForm = (CurriculumSchemeForm)form;
		 ActionErrors errors = curriculumSchemeForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				int courseId = Integer.parseInt(curriculumSchemeForm.getCourse());
				int oldCourseId = curriculumSchemeForm.getOldCourseId();
				final int year = Integer.parseInt(curriculumSchemeForm.getYear());
				final int oldYear = curriculumSchemeForm.getOldYear();
				
				int id=curriculumSchemeForm.getId();
				
				CurriculumSchemeTO curriculumSchemeTO=CurriculumSchemeHandler.getInstance().getCurriculumSchemeDetailsOnId(id, curriculumSchemeForm,false);
					List<CurriculumSchemeDurationTO>durationList = curriculumSchemeTO.getCurriculumDurationList();
				//Checking if user have changed the course and year. Checks for the duplicate records
				if(courseId != oldCourseId || year != oldYear){
					List<CurriculumScheme> isExistCourseId=CurriculumSchemeHandler.getInstance().isExistCourseId(courseId, year);
					if(isExistCourseId != null && !isExistCourseId.isEmpty()){	
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_EXISTS));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);				
						}
					else{
						int oldNoOfScheme = curriculumSchemeForm.getOldNoOfScheme();
						int noOfScheme = Integer.parseInt(curriculumSchemeForm.getNoOfScheme());
						//Works when no. of scheme is same
						if(oldNoOfScheme == noOfScheme){
							curriculumSchemeForm.setDurationList(durationList);
							return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
						}
						//Works when more no. of schemes selected during update mode
						else if(noOfScheme > oldNoOfScheme){										
							int moreSchemes = noOfScheme-oldNoOfScheme;
							CurriculumSchemeDurationTO curriculumSchemeDurationTO = null;
							int i = 0;
							String[] selectedSubjects = new String[0];
							for(i=0; i<moreSchemes ; i++){
								curriculumSchemeDurationTO = new CurriculumSchemeDurationTO();
								curriculumSchemeDurationTO.setSemester(++oldNoOfScheme);
								curriculumSchemeDurationTO.setSubjectGroups(selectedSubjects);																
								durationList.add(curriculumSchemeDurationTO);
								curriculumSchemeForm.setDurationList(durationList);
							}
						}
						else{
							int sizeDiff = oldNoOfScheme - noOfScheme;
							int maxSemNo = oldNoOfScheme - sizeDiff;
							if(durationList!=null && !durationList.isEmpty()){
								Iterator<CurriculumSchemeDurationTO> iterator = durationList.iterator();
								while (iterator.hasNext()) {
									CurriculumSchemeDurationTO curriculumSchemeDurationTO = (CurriculumSchemeDurationTO) iterator.next();
									if(curriculumSchemeDurationTO.getSemester()> maxSemNo){
										iterator.remove();
									}
								}
								curriculumSchemeForm.setDurationList(durationList);
							}
						}
					}
				}
				else{
					int oldNoOfScheme = curriculumSchemeForm.getOldNoOfScheme();
					int noOfScheme = Integer.parseInt(curriculumSchemeForm.getNoOfScheme());
					
					if(oldNoOfScheme == noOfScheme){
						curriculumSchemeForm.setDurationList(durationList);
						return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
					}
					else if(noOfScheme > oldNoOfScheme){										
						int moreSchemes = noOfScheme-oldNoOfScheme;
						CurriculumSchemeDurationTO curriculumSchemeDurationTO = null;
						int i = 0;
						String[] selectedSubjects = new String[0];
						for(i=0; i<moreSchemes ; i++){
							curriculumSchemeDurationTO = new CurriculumSchemeDurationTO();
							curriculumSchemeDurationTO.setSemester(++oldNoOfScheme);
							curriculumSchemeDurationTO.setSubjectGroups(selectedSubjects);
							durationList.add(curriculumSchemeDurationTO);
						}
						curriculumSchemeForm.setDurationList(durationList);
					}
					else{
						int sizeDiff = oldNoOfScheme - noOfScheme;
						int maxSemNo = oldNoOfScheme - sizeDiff;
						if(durationList!=null && !durationList.isEmpty()){
							Iterator<CurriculumSchemeDurationTO> iterator = durationList.iterator();
							while (iterator.hasNext()) {
								CurriculumSchemeDurationTO curriculumSchemeDurationTO = (CurriculumSchemeDurationTO) iterator.next();
								if(curriculumSchemeDurationTO.getSemester()> maxSemNo){
									iterator.remove();
								}
							}
							curriculumSchemeForm.setDurationList(durationList);
						}
					}
				}
			}
			else{
					setProgramMapToRequest(request, curriculumSchemeForm);
					setCourseMapToRequest(request, curriculumSchemeForm);
					assignListToForm(curriculumSchemeForm);
					saveErrors(request, errors);
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.INIT_CURRICULUMSCHEME);
			}
		} catch (Exception e) {
			log.error("Error occured in loadCurriculumDetailsForUpdate of CurriculumSchemeAction",e);
			String msg = super.handleApplicationException(e);
			curriculumSchemeForm.setErrorMessage(msg);
			curriculumSchemeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into loadCurriculumDetailsForUpdate of Curriculumscheme entry");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.UPDATE_CURRICULUMSCHEME);
	}
	
	/**
	 * Method to set all active programs to the session scope
	 */
	public void setProgramMapToRequest(HttpServletRequest request,
			CurriculumSchemeForm curriculumSchemeForm) throws Exception{
		log.info("Entering into setProgramMapToRequest of Curriculumscheme entry");
		if (curriculumSchemeForm.getProgramType() != null && (!curriculumSchemeForm.getProgramType().equals(""))) {
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(curriculumSchemeForm.getProgramType()));
			HttpSession session=request.getSession(false);
			session.setAttribute("programMap", programMap);			
		}
		log.info("Leaving into setProgramMapToRequest of Curriculumscheme entry");
	}
	/**
	 * Method to set all active courses to the session scope
	 */
	public void setCourseMapToRequest(HttpServletRequest request,
			CurriculumSchemeForm curriculumSchemeForm)throws Exception {
		log.info("Entering into setCourseMapToRequest of Curriculumscheme entry");
		if (curriculumSchemeForm.getProgram() != null && (!curriculumSchemeForm.getProgram().equals(""))) {
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(curriculumSchemeForm.getProgram()));
			HttpSession session=request.getSession(false);
			session.setAttribute("courseMap", courseMap);			
		}
		log.info("Leaving into setCourseMapToRequest of Curriculumscheme entry");
	}
}
