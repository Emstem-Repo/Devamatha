package com.kp.cms.actions.admission;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentSearchForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.StudentSearchHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.StudentSearchTO;
import com.kp.cms.utilities.StudentSearchComparator;

/**
 *  Performs interview selection related actions.
 */
public class StudentSearchAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(StudentSearchAction.class);
	
	/**
	 * Initializes Interview selection action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initStudentSearch of StudentSearchAction class.");
		StudentSearchForm studentSearchForm = (StudentSearchForm) form;
		studentSearchForm.resetFields();
		setRequiredDatatoForm(request,studentSearchForm);
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","selection_process");
		
		log.info("exit of initStudentSearch of StudentSearchAction class.");
		return mapping.findForward(CMSConstants.STUDENT_SEARCH_ENTRY);
	}
	
	/**
	 * Performs cancel interview selection action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelStudentSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initStudentSearch of StudentSearchAction class.");
		StudentSearchForm studentSearchForm = (StudentSearchForm) form;
		studentSearchForm.resetFields();
		setRequiredDatatoForm(request,studentSearchForm);
		log.info("exit of initStudentSearch of StudentSearchAction class.");
		return mapping.findForward(CMSConstants.STUDENT_SEARCH_ENTRY);
	}

	/**
	 * Get the results for interview selection based on search criteria.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentSearchResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getStudentSearchResults of StudentSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = studentSearchForm.validate(mapping, request);
		if(studentSearchForm.getInterviewType()==null || studentSearchForm.getInterviewType().length==0 ){
			if (errors.get(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED)!=null && !errors.get(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED);
				errors.add(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED, error);
			}
		}else{
			boolean empty=false;
			for(int i=0;i<studentSearchForm.getInterviewType().length;i++){
				if(studentSearchForm.getInterviewType()[i]==null || 
						StringUtils.isEmpty(studentSearchForm.getInterviewType()[i])){
					empty=true;
				}
			}
			if(empty){
				if (errors.get(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED)!=null && !errors.get(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED);
					errors.add(CMSConstants.INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED, error);
				}
			}
		}
		if (errors.isEmpty()) {
			try {
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getStudentSearchResults(
								(StudentSearchForm) form);
				Collections.sort(searchTO,new StudentSearchComparator());
				studentSearchForm.setStudentSearch(searchTO);
				if (searchTO.isEmpty()) {
					message = new ActionMessage(
							"knowledgepro.admission.noresultsfound");
					messages.add(CMSConstants.MESSAGES, message);
					addErrors(request, messages);
				}
				
				log.info("exit of getStudentSearchResults of StudentSearchAction class.");
				return mapping.findForward(CMSConstants.STUDENT_SEARCH_RESULTS);
			} catch (Exception e) {
				log.error("Error while initializing selection list "+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(request,studentSearchForm);
			return mapping.findForward(CMSConstants.STUDENT_SEARCH_ENTRY);
		}
	}

	/**
	 * Persist the selected candidates for the interview to the database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSelectedStudentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitSelectedStudentList of StudentSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		setUserId(request, studentSearchForm);
		if (studentSearchForm.getSelectedCandidates() == null) {
			message = new ActionMessage(
					"knowledgepro.admission.selectcandidate");
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);

		} else {

			try {
				StudentSearchHandler.getInstance().updateSelectedCandidates(
						studentSearchForm.getSelectedCandidates(),
						studentSearchForm.getStudentSearch(),
						studentSearchForm.getUserId());
				if(studentSearchForm.getOrderBy()!=null && studentSearchForm.getOrderBy()){
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getStudentSearchResults(
								(StudentSearchForm) form);
				Collections.sort(searchTO,new StudentSearchComparator());
				studentSearchForm.setStudentSearch(searchTO);
				}
				if(studentSearchForm.getOrderBy()!=null && !studentSearchForm.getOrderBy()){
					List<StudentSearchTO> searchTO = StudentSearchHandler
					.getInstance().getStudentSearchResults(
							(StudentSearchForm) form);
					StudentSearchComparator comparator=new StudentSearchComparator();
					comparator.setCompare(1);
					Collections.sort(searchTO,comparator);
					studentSearchForm.setStudentSearch(searchTO);
				}
				
				message = new ActionMessage("knowledgepro.admission.addtolist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error("Error while submitting selection list "+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

			}

		}

		studentSearchForm.setSelectedCandidates(null);
		log.info("exit of submitSelectedStudentList of StudentSearchAction class.");
		return mapping.findForward(CMSConstants.STUDENT_SEARCH_RESULTS);

	}

	/**
	 * Get the selected candidates search results.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSelectedCandidatesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getSelectedCandidatesList of StudentSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);
		try {
			if(studentSearchForm.getOrderBy()!=null && studentSearchForm.getOrderBy()){
			List<StudentSearchTO> searchTO = StudentSearchHandler.getInstance()
					.getSelectedStudents((StudentSearchForm) form);
			Collections.sort(searchTO,new StudentSearchComparator());
			studentSearchForm.setStudentSearch(searchTO);
			}
			if(studentSearchForm.getOrderBy()!=null && !studentSearchForm.getOrderBy()){
				List<StudentSearchTO> searchTO = StudentSearchHandler
				.getInstance().getStudentSearchResults(
						(StudentSearchForm) form);
				StudentSearchComparator comparator=new StudentSearchComparator();
				comparator.setCompare(1);
				Collections.sort(searchTO,comparator);
				studentSearchForm.setStudentSearch(searchTO);
			}
		
			log.info("exit of getSelectedCandidatesList of StudentSearchAction class.");
			return mapping.findForward("selectedStudentResults");
		} catch (Exception e) {

			log.error("Error while getting selection list "+e.getMessage());
			String msg = super.handleApplicationException(e);
			studentSearchForm.setErrorMessage(msg);
			studentSearchForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}

	/**
	 * removes and persist the selected candidates from the search list.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeSelectedCandidates(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into removeSelectedCandidates of StudentSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		if (studentSearchForm.getSelectedCandidates() == null) {
			message = new ActionMessage(
					"knowledgepro.admission.selectcandidate");
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);

		} else {
			try {
				StudentSearchHandler.getInstance().removeSelectedCandidates(
						studentSearchForm.getSelectedCandidates());
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getSelectedStudents(
								(StudentSearchForm) form);
				Collections.sort(searchTO,new StudentSearchComparator());
				studentSearchForm.setStudentSearch(searchTO);
				message = new ActionMessage(
						"knowledgepro.admission.addremovefromlist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			} catch (Exception e) {

				log.error("Error while removing selection list "
						+ e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

			}

		}
		studentSearchForm.setSelectedCandidates(null);
		log.info("exit of removeSelectedCandidates of StudentSearchAction class.");
		return mapping.findForward("selectedStudentResults");
	}

	/**
	 * set the required data to the request scope.
	 * @param studentSearchForm
	 * @param request
	 */
	private void setRequiredDatatoForm(HttpServletRequest request,StudentSearchForm studentSearchForm) throws Exception{
		log.info("entering into setRequiredDatatoForm of StudentSearchAction class.");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
		studentSearchForm.setCasteList(casteList);
		List<ReligionTO> religionList = ReligionHandler.getInstance().getReligion();
		studentSearchForm.setReligionList(religionList);
		List<NationalityTO> nationTOs=AdmissionFormHandler.getInstance().getNationalities();
		studentSearchForm.setNationTOs(nationTOs);
		List<UniversityTO> universities = UniversityHandler.getInstance().getUniversity();
		studentSearchForm.setUniversities(universities);
		List<CountryTO> countries=CountryHandler.getInstance().getCountries();
		studentSearchForm.setCountries(countries);
		if(studentSearchForm.getReligionId()!=null && !studentSearchForm.getReligionId().isEmpty()){
			Map<Integer, String> subreligionMap = CommonAjaxHandler.getInstance().getSubReligionByReligion(Integer.parseInt(studentSearchForm.getReligionId()));
			request.setAttribute("subReligionMap", subreligionMap);
		}
		if(studentSearchForm.getUniversity()!=null && !studentSearchForm.getUniversity().isEmpty()){
			Map<Integer, String> collegeMap= CommonAjaxHandler.getInstance().getCollegeByUniversity(Integer.parseInt(studentSearchForm.getUniversity()));
			request.setAttribute("instituteMap", collegeMap);
		}
		if(studentSearchForm.getBirthCountry()!=null && !studentSearchForm.getBirthCountry().isEmpty()){
			Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(studentSearchForm.getBirthCountry()));
			request.setAttribute("birthState", stateMap);
		}
	
		AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
		List<ResidentCategoryTO> residentCategory=formhandler.getResidentTypes();
		studentSearchForm.setResidentCategory(residentCategory);
		log.info("exit of setRequiredDatatoForm of StudentSearchAction class.");
	}
	
	
	
	public ActionForward getApplicantStatusDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentSearchForm studentSearchForm = (StudentSearchForm) form;
		try {
			String applicationNumber = studentSearchForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(studentSearchForm.getApplicationYear().trim());
			int courseId = Integer.parseInt(studentSearchForm.getCourseId().trim());

			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicantStatusDetails(applicationNumber, applicationYear, courseId,request);
			studentSearchForm.setStatus(applicantDetails.getStatus());
			studentSearchForm.setShortStatus(applicantDetails.getShortStatus());
			
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.SELECTION_PROCESS_STATUS);
	}
	
	public void getApplicantStatusDetails() throws Exception {

		StudentSearchForm studentSearchForm = new StudentSearchForm();
		try {
			String applicationNumber = studentSearchForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(studentSearchForm.getApplicationYear().trim());
			int courseId = Integer.parseInt(studentSearchForm.getCourseId().trim());

			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicantStatusDetails(applicationNumber, applicationYear, courseId);
			studentSearchForm.setStatus(applicantDetails.getStatus().trim());
			studentSearchForm.setShortStatus(applicantDetails.getShortStatus().trim());
			
		} catch (Exception e) {
			
			
				throw e;
			}
		}
		
	
}