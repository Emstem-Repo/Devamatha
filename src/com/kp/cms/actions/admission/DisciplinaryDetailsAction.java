package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admission.DisciplinaryDetailsHandler;
import com.kp.cms.handlers.exam.DownloadHallTicketHandler;
import com.kp.cms.helpers.admission.DisciplinaryDetailsHelper;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.CourseDetailsTO;
import com.kp.cms.to.admission.RemarcksTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamStudentPreviousClassTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactionsimpl.admission.DisciplinaryDetailsImpl;
import com.kp.cms.transactionsimpl.admission.StudentEditTransactionImpl;
import com.kp.cms.utilities.MarkComparator;

@SuppressWarnings("unused")
public class DisciplinaryDetailsAction extends BaseDispatchAction {
	DisciplinaryDetailsHandler handler = new DisciplinaryDetailsHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(DisciplinaryDetailsAction.class);
	private static final String IMAGELIST = "imageList";
	private static final String EDITCOUNTID = "editcountID";
	private static final String previousSemester = "previousSemester";
	private static final String SemesterNo = "SemesterNo";
	private static final String ClassId = "ClassId";
	private static final String examId="examId";
	private static final String MESSAGE_KEY = "messages";
	private static final String studId = "studId";
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDisciplinaryDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Disciplinary Details action");

		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		cleanupFormFromSession(objForm);
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		objForm.setIsCjc(isCjc);
		if(request.getSession().getAttribute("DisplinaryPhotoBytes")!=null){
			request.getSession().removeAttribute("DisplinaryPhotoBytes");
		}
		objForm.setRollRegNo("");
		objForm.setTempRegRollNo(null);
		objForm.setTempFirstName(null);
		objForm.setTempName(null);
		objForm.setRemarks("");
		objForm.setRegularMarkFlag(0);
		return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS);
	}

	
	public ActionForward getSearchedStudents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		ActionMessages errors = objForm.validate(mapping, request);
		try {
			cleanupFormFromSession(objForm);
			if(request.getSession().getAttribute("DisplinaryPhotoBytes")!=null){
				request.getSession().removeAttribute("DisplinaryPhotoBytes");
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.DESCIPLINARY_DETAILS);
			}
			objForm.setRollRegNo(objForm.getTempRegRollNo());
			objForm.setTempName(objForm.getTempFirstName());
			
		/*	objForm.setTempRegRollNo(objForm.getRollRegNo());
			objForm.setTempFirstName(objForm.getTempName());*/
					
			boolean isActive = false;
			if(objForm.getRollRegNo()!=null && !objForm.getRollRegNo().isEmpty()){
				isActive = handler.checkStudentIsActive(objForm);
			}
			if(isActive){
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_STUDENT_ISACTIVE, objForm.getRemarks());
				errors.add(DisciplinaryDetailsAction.MESSAGE_KEY, message);
				saveErrors(request, errors);
				objForm.setRemarks(null);
				return mapping
						.findForward(CMSConstants.DESCIPLINARY_DETAILS);
			}
			List<StudentTO> studenttoList = handler.getSearchedStudents(objForm);
			if (studenttoList == null || studenttoList.isEmpty()) {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(DisciplinaryDetailsAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.DESCIPLINARY_DETAILS);

			}
			objForm.setStudentTOList(studenttoList);
		} catch (ApplicationException e) {
			log.error("error in getSearchedStudents...", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			throw e;

		}
		log.info("exit getSearchedStudents..");
		return mapping.findForward(CMSConstants.STUDENT_VIEW_LIST);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward getDisciplinaryDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init room type action");
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		cleanupFormFromSession(objForm);
		if(request.getSession().getAttribute("DisplinaryPhotoBytes")!=null){
			request.getSession().removeAttribute("DisplinaryPhotoBytes");
		}
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		objForm.setIsCjc(isCjc);
		StudentEditHelper helper = StudentEditHelper.getInstance();
		StudentEditTransactionImpl impl = new StudentEditTransactionImpl();
		HttpSession session = request.getSession(true);
		errors.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		Integer studentId=null;
		if (errors.isEmpty()) {
			studentId=handler.validate1(objForm.getRollRegNo(),objForm.getTempApplicationNo());
			if(studentId!= null){
				objForm.setStudentId(studentId.toString());
			}
			if (studentId==null) {
				errors.add(CMSConstants.ERROR, new ActionError(
						"knowledgepro.exam.reJoin.rollNoReg.notexists",objForm.getRollRegNo()));
			}
		}
		saveErrors(request, errors);
		
		if (errors.isEmpty()) {
			setUserId(request, objForm);
			objForm = handler.getDisciplinaryDetails(objForm,studentId,request);
			List<ExamStudentPreviousClassTo>  classDetailsTo = helper
			.convertBOToTO_viewHistory_ClassGroupDetailsView(impl
					.viewHistory_ClassGroupDetailsView(studentId));
			Collections.sort(classDetailsTo);
			if(classDetailsTo!=null)
			session.setAttribute("SEMLIST", classDetailsTo);
			
			
		//Mary code for marks	
						
			int classId= DownloadHallTicketHandler.getInstance().getClassId(studentId, objForm);
			int marksCardClassId1 = DownloadHallTicketHandler.getInstance().getClassIds(studentId, classId, false, "Marks Card");
			int semesterNo = DownloadHallTicketHandler.getInstance().getTermNumber(marksCardClassId1);
			
			int count=1;
			String publishedfor= "Marks Card";
		
			MarksCardTO marksCardTo=null;
			MarksCardTO marksCardSemExamList=null;
			objForm.setSemesterYearNo(semesterNo);
			
			marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(objForm);
			
			if(marksCardTo!=null)	
			{
			objForm.setMarksCardTo(marksCardTo);
			List<MarksCardTO> newMarkscardTo = new ArrayList<MarksCardTO>();
			newMarkscardTo.add(marksCardTo);
			if(newMarkscardTo!=null)
				session.setAttribute("MARKSCARDTO", newMarkscardTo);
			}
			 List<MarksCardTO> SemExamListTo=(DownloadHallTicketHandler.getInstance().getExamSemList(objForm));
			if(SemExamListTo!=null)	
			{
			Collections.sort(SemExamListTo,new MarkComparator());
			if(SemExamListTo!=null)
			session.setAttribute("EXAMSEMLIST", SemExamListTo);
			}
		//Sup Marks Card
			MarksCardTO SupMCSemExamList=null;
			
             List<MarksCardTO> SupSemList=(DownloadHallTicketHandler.getInstance().getSupExamSemList(objForm));
          //  Collections.sort(SupSemList, new MarkComparator());
			if(SupSemList!= null)
			session.setAttribute("SUPEXAMSEMLIST", SupSemList);
			
			// for CJC marks card......
			 if(objForm.getIsCjc().equals(true))
			  {
			   List<MarksCardTO> CjcSemExamListTo=(DownloadHallTicketHandler.getInstance().getCjcExamSemList(objForm));
				if(CjcSemExamListTo!=null)	
				{
					Collections.sort(CjcSemExamListTo,new MarkComparator());
					if(CjcSemExamListTo!=null)
					session.setAttribute("CJCEXAMSEMLIST", CjcSemExamListTo);
				}
			  }
			// for the student is final year or not and showing status
			 boolean finalYear=DisciplinaryDetailsHandler.getInsatnce().getStudentFinalYearStatus(objForm);
			 if(finalYear){
				 objForm.setCheckFinalYear("yes");
				 boolean status=DisciplinaryDetailsHandler.getInsatnce().getCourseCompletionStatus(objForm.getStudentId());
				 if(status){
					 objForm.setStatus("Completed");
					DisciplinaryDetailsHandler.getInsatnce().getLastExamNameAndMonthAndYear(SupSemList,SemExamListTo,objForm);
				 }else{
					 objForm.setStatus("Pending");
				 }
			 }else{
				 objForm.setCheckFinalYear("no");
			 }
			 //for the hostel details of student
		 DisciplinaryDetailsHandler.getInsatnce().getHostelDetailsForStudent(objForm);
		 /* if(objForm.getIsCjc().equals(true))
		  {
							MarksCardTO cjcMarksCardTo=null;
							MarksCardTO cjcMarksCardList=null;
							cjcMarksCardTo=DownloadHallTicketHandler.getInstance().getCjcMarksCard(objForm);
						
							if(cjcMarksCardTo!=null)	
							{
							objForm.setCjcMarksCardTo(cjcMarksCardTo);
							List<MarksCardTO> newCjcMarkscardTo = new ArrayList<MarksCardTO>();
							newCjcMarkscardTo.add(cjcMarksCardTo);
							if(newCjcMarkscardTo!=null)
								session.setAttribute("CJCMARKSCARDTO", newCjcMarkscardTo);
							}
			}*/
			 if(objForm.getIsCjc().equals(true))
			  {
				 return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY_CJC);
			  }
			 else
			 {
				 return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY);
			 }
		   
		} else {
			return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS);
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDisciplinaryDetailsPreviousSemesters(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		String previousSemester1 = request
		.getParameter(DisciplinaryDetailsAction.previousSemester);
        HttpSession session = request.getSession(false);
       
	    if (previousSemester1 != null) {
		session.setAttribute(DisciplinaryDetailsAction.previousSemester,
				previousSemester1);
		int previousSem = Integer.parseInt(previousSemester1);
		
		String classesId = request
		.getParameter(DisciplinaryDetailsAction.ClassId);

		if (classesId != null) {
			session
			.setAttribute(DisciplinaryDetailsAction.ClassId,
					classesId);
			
}
		int classId = Integer.parseInt(classesId);
	   
		errors.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		Integer studentId=null;
		if (errors.isEmpty()) {
			studentId=handler.validate(objForm.getRollRegNo());
			if(studentId!= null){
				objForm.setStudentId(studentId.toString());
			}
		}
		saveErrors(request, errors);
		
		if (errors.isEmpty()) {
			setUserId(request, objForm);
			List<CourseDetailsTO> listCourseDetails=(handler.getDisciplinaryDetailsSemester(objForm,studentId, classId, previousSem, request));
			objForm.setListCourseDetails(listCourseDetails);
			//modified by giri
			if(listCourseDetails!=null && !listCourseDetails.isEmpty()){
				request.setAttribute("Attendance", listCourseDetails);
				request.setAttribute("Exam", listCourseDetails);
			}
		}
	
		return mapping.findForward(CMSConstants.AJAX_PREVIOUSATTENDANCE_DISPLAY);
		} else {
			return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS);
		}
	}
	
	
	/**34
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRemarksPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init room type action");
		//DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		errors.clear();
		return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_ADD_REMARKS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addRemarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init room type action");
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		errors.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		DisciplinaryDetailsHelper helper = new DisciplinaryDetailsHelper();
		DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
		if (errors.isEmpty()) {
			setUserId(request, objForm);
			handler.addRemarks(Integer.parseInt(objForm.getStudentId()),objForm.getUserId(),objForm.getRemarks());
			if(request.getSession().getAttribute("DisplinaryPhotoBytes")!=null){
				request.getSession().removeAttribute("DisplinaryPhotoBytes");
			}
			objForm.setRollRegNo("");
			objForm.setRemarks("");
			if(objForm.getStudentId()!= null){
				objForm.setListRemarcks(helper.convertBOToTORemarks(impl.getRemarcks(Integer.parseInt(objForm.getStudentId()))));
			}
			 if(objForm.getIsCjc().equals(true))
			  {
				 return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY_CJC);
			  }
			 else
			 {
				 return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY);
			 }
			//return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS);
			
			
		}
		return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_ADD_REMARKS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDetailMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewDetailMarkPage...");
		DisciplinaryDetailsForm disciplinaryDetailsForm=(DisciplinaryDetailsForm)form;
		try {

			String indexString = request
					.getParameter(DisciplinaryDetailsAction.EDITCOUNTID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null) {
					session.setAttribute(DisciplinaryDetailsAction.EDITCOUNTID,
							indexString);
					int index = Integer.parseInt(indexString);
					List<EdnQualificationTO> quals = disciplinaryDetailsForm.getEduList();
							
					if (quals != null) {
						Iterator<EdnQualificationTO> qualItr = quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
									.next();
							if (qualTO.getId() == index) {
								if (qualTO.getDetailmark() != null) {
									disciplinaryDetailsForm.setDetailMark(qualTO
											.getDetailmark());
								} else {
									disciplinaryDetailsForm
											.setDetailMark(new CandidateMarkTO());
								}
							}
						}
					}
				} else
					session.removeAttribute(DisciplinaryDetailsAction.EDITCOUNTID);
			}
			request.setAttribute("disciplinaryDetailsForm", disciplinaryDetailsForm);
		} catch (Exception e) {
			log.error("error in viewDetailMarkPage...", e);
			throw e;

		}
		log.info("exit viewDetailMarkPage...");

		return mapping
				.findForward(CMSConstants.STUDENTEDIT_VIEW_DETAIL_MARK_PAGE);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSemesterMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewSemesterMarkPage...");
		DisciplinaryDetailsForm disciplinaryDetailsForm=(DisciplinaryDetailsForm)form;
		try {

			String indexString = request
					.getParameter(DisciplinaryDetailsAction.EDITCOUNTID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null) {
					session.setAttribute(DisciplinaryDetailsAction.EDITCOUNTID,
							indexString);
					int index = Integer.parseInt(indexString);
					List<EdnQualificationTO> quals = disciplinaryDetailsForm.getEduList();
					if (quals != null) {
						Iterator<EdnQualificationTO> qualItr = quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
									.next();
							if (qualTO.getId() == index) {
								if (qualTO.getSemesterList() != null) {
									List<ApplicantMarkDetailsTO> semList = new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize = semList.size();
									int sizeDiff = CMSConstants.MAX_CANDIDATE_SEMESTERS
											- listSize;
									if (sizeDiff > 0) {
										for (int cnt = listSize + 1; cnt <= CMSConstants.MAX_CANDIDATE_SEMESTERS; cnt++) {
											ApplicantMarkDetailsTO markTo = new ApplicantMarkDetailsTO();
											markTo.setSemesterNo(cnt);
											markTo.setSemesterName("Semester"
													+ cnt);
											semList.add(markTo);
										}
									}
									Collections.sort(semList);
									disciplinaryDetailsForm.setSemesterList(semList);
								} else {
									disciplinaryDetailsForm
											.setSemesterList(new ArrayList<ApplicantMarkDetailsTO>());
								}
								disciplinaryDetailsForm.setIsLanguageWiseMarks(String
										.valueOf(qualTO.isLanguage()));

							}
						}
					}
				} else
					session.removeAttribute(DisciplinaryDetailsAction.EDITCOUNTID);
			}
			request.setAttribute("disciplinaryDetailsForm", disciplinaryDetailsForm);
		} catch (Exception e) {
			log.error("error in viewSemesterMarkPage...", e);
			throw e;

		}
		log.info("exit viewSemesterMarkPage...");

		return mapping.findForward(CMSConstants.STUDENTEDIT_VIEW_SEM_MARK_PAGE);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the absence information period details for the student
	 * @throws Exception
	 */
	public ActionForward getAbsencePeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		DisciplinaryDetailsForm disciplinaryDetailsForm=(DisciplinaryDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			disciplinaryDetailsForm.setSubjectAttendance("Yes");
			double absent=Double.valueOf(disciplinaryDetailsForm.getClassesAbsent());
			double cocurricular=Double.valueOf(disciplinaryDetailsForm.getCocurricularLeave());
			
			if(absent == 0 && (String.valueOf(cocurricular)!=null && cocurricular>0))
			{
				DisciplinaryDetailsHandler handler=new DisciplinaryDetailsHandler();
				List<PeriodTO> periodList = handler.getAbsencePeriodDetails(disciplinaryDetailsForm);
				disciplinaryDetailsForm.setPeriodList(periodList);
			}
			else if (absent>0) {
				DisciplinaryDetailsHandler handler=new DisciplinaryDetailsHandler();
				List<PeriodTO> periodList = handler.getAbsencePeriodDetails(disciplinaryDetailsForm);
				disciplinaryDetailsForm.setPeriodList(periodList);
			} else {
				disciplinaryDetailsForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			disciplinaryDetailsForm.setErrorMessage(msg);
			disciplinaryDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS);	
	}
	//Mary code starts
	public ActionForward getStudentMarksCard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		try {
			MarksCardTO marksCardTo1 = null;
			HttpSession session = request.getSession(false);
		//	Integer regularMarkFlag = 1;
			String Semester = request .getParameter(DisciplinaryDetailsAction.SemesterNo);
			if (Semester != null) {
				session.setAttribute(DisciplinaryDetailsAction.SemesterNo, Semester);
				String publishedfor = "Marks Card";
				objForm.setSemesterYearNo(Integer.parseInt(Semester));
			}
			/*String ExamId=request.getParameter(DisciplinaryDetailsAction.examId);
			if(ExamId!=null)
			{
				session.setAttribute(DisciplinaryDetailsAction.examId,
						ExamId);
				objForm.setExamIDForMCard(Integer.parseInt(ExamId));
			}*/
			marksCardTo1 = DownloadHallTicketHandler.getInstance() .getMarksCardForPG(objForm);
			objForm.setMarksCardTo1(marksCardTo1);
			if(marksCardTo1!= null){
				request.setAttribute("MarksCard", marksCardTo1);
			}else{
				request.setAttribute("msg", "Exam results are not published or this student results are blocked");
			}
			//session.setAttribute("regularMarkFlag", regularMarkFlag);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward(CMSConstants.MARKS_CARD_DISPLAY_AJAX);
	}

	
	
	
	
	
	public ActionForward getStudentSupMarksCard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		try {
		MarksCardTO marksCardToSup = null;
		HttpSession session = request.getSession(false);
		String Semester = request
				.getParameter(DisciplinaryDetailsAction.SemesterNo);
		
		if (Semester != null) {
			session
					.setAttribute(DisciplinaryDetailsAction.SemesterNo,
							Semester);
			objForm.setSemesterYearNo(Integer.parseInt(Semester));
		}
		String ExamId=request.getParameter(DisciplinaryDetailsAction.examId);
		if(ExamId!=null)
		{
			session.setAttribute(DisciplinaryDetailsAction.examId,
					ExamId);
			objForm.setSupExamId(Integer.parseInt(ExamId));
		}
		marksCardToSup = DownloadHallTicketHandler.getInstance()
				.getSupMarksCardForPGView(objForm);
		objForm.setMarksCardToSup(marksCardToSup);
		if(marksCardToSup!=null){
			request.setAttribute("MarksCardSup", marksCardToSup);
			List<MarksCardTO> newMarkscardSupTo = new ArrayList<MarksCardTO>();
			newMarkscardSupTo.add(marksCardToSup);
			if(newMarkscardSupTo!=null)
			session.setAttribute("MARKSCARDTOSUP", newMarkscardSupTo);
		}else{
			request.setAttribute("msg", "Exam results are not published or this student results are blocked");
		}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward(CMSConstants.SUP_MARKS_CARD_DISPLAY_AJAX);
	}

	public ActionForward getStudentCjcMarksCard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		try {
		MarksCardTO marksCardToCjc = null;
		HttpSession session = request.getSession(false);
		String classId = request
				.getParameter(DisciplinaryDetailsAction.ClassId);
		
		if (classId != null) {
			session
					.setAttribute(DisciplinaryDetailsAction.ClassId,
							classId);
			objForm.setClassIdCjc(Integer.parseInt(classId));
		}
		String ExamId=request.getParameter(DisciplinaryDetailsAction.examId);
		if(ExamId!=null)
		{
			session.setAttribute(DisciplinaryDetailsAction.examId,
					ExamId);
			objForm.setExamIdCjc(Integer.parseInt(ExamId));
		}
		marksCardToCjc = DownloadHallTicketHandler.getInstance()
				.getCjcMarksCard(objForm);
		objForm.setCjcMarksCardTo(marksCardToCjc);
		request.setAttribute("MarksCardCjc", marksCardToCjc);
		/*List<MarksCardTO> newMarkscardCjcTo = new ArrayList<MarksCardTO>();
		newMarkscardCjcTo.add(marksCardToCjc);
		if(newMarkscardCjcTo!=null)
		session.setAttribute("MARKSCARDTOCJC", newMarkscardCjcTo);*/
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward(CMSConstants.CJC_MARKS_CARD_DISPLAY_AJAX);
	}

	
	
	

	private void cleanupFormFromSession(DisciplinaryDetailsForm objForm) {
		log.info("enter cleanupFormFromSession...");
		objForm.setAccoId(0);
		objForm.setClassesAbsent(null);
		objForm.setClassName(null);
		objForm.setMarks(null);
		objForm.setMarksCardTo1(null);
		objForm.setMarksCardTo(null);
		objForm.setMarksCardToSup(null);
		objForm.setCjcMarksCardTo(null);
		objForm.setDetentionDate(null);
		objForm.setDetentionReason(null);
		objForm.setDiscontinuedDate(null);
		objForm.setDiscontinuedReason(null);
		objForm.setEduList(null);
		objForm.setFeeGroupId(null);
		objForm.setListFees(null);
		objForm.setRecommendedBy(null);
		objForm.setSubjectAttendance(null);
		objForm.setRejoinDate(null);
		objForm.setRejoinReason(null);
		objForm.setSupExamId(0);
		objForm.setSemesterYearNo(0);
		objForm.setExamIdCjc(0);
		objForm.setClassIdCjc(0);
		objForm.setStatus(null);
		objForm.setLastExamName(null);
		objForm.setExamMonthYear(null);
		objForm.setCheckFinalYear(null);
		objForm.setCheckHostelAdmission(null);
		objForm.setRoomNo(null);
		objForm.setBedNo(null);
		objForm.setBlock(null);
		objForm.setUnit(null);
		objForm.setHostelName(null);
		objForm.setHostelTOList(null);
		objForm.setHlStudentAttendanceToList(null);
		objForm.setHlDisciplinaryDetailsTOList(null);
		objForm.setFineEntryToList(null);
		log.info("exit cleanupFormFromSession...");
	}
	//Mary Code ends
	
	public ActionForward editRemarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into editRemarks action");
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		int remarksId = objForm.getRemarkid();
		//ActionErrors errors = objForm.validate(mapping, request);
		RemarcksTO remarcksTO = handler.getEditRemarks(remarksId);
		objForm.setRemarks(remarcksTO.getDetails());
		request.setAttribute("remarksOperation", "edit");
		return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_ADD_REMARKS);
	}
	
	public ActionForward updateRemarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("entering into updateRemarks action");
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		errors.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		DisciplinaryDetailsHelper helper = new DisciplinaryDetailsHelper();
		DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
		if (errors.isEmpty()) {
			setUserId(request, objForm);
			handler.updateRemarks(objForm.getRemarkid(),objForm.getRemarks(),objForm.getUserId());
			
			objForm.setRollRegNo("");
			objForm.setRemarks("");
			if(objForm.getStudentId()!= null){
				objForm.setListRemarcks(helper.convertBOToTORemarks(impl.getRemarcks(Integer.parseInt(objForm.getStudentId()))));
			}
			if(objForm.getIsCjc().equals(true))
			  {
				 return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY_CJC);
			  }
			 else
			 {
				 return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY);
			 }
		}
		if (!errors.isEmpty()) {
			request.setAttribute("remarksOperation", "edit");
		}
		return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_ADD_REMARKS);
	}
	public ActionForward getAbscentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		try {
			String studentId=objForm.getStudentId();
			List<StudentAttendanceTO> attList=handler.getAttendanceDataByStudent(studentId,objForm);
			objForm.setAttList(attList);
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		 return mapping.findForward(CMSConstants.DISPLAY_ABSCENT_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHostelDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String studentId = request
		.getParameter(DisciplinaryDetailsAction.studId);
       // HttpSession session = request.getSession(false);
	    if (studentId != null) {
	    	 DisciplinaryDetailsHandler.getInsatnce().getHostelDetails(studentId,request);
	    	return mapping.findForward(CMSConstants.AJAX_HOSTEL_DETAILS);
		} else {
			return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS);
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewStudentDetailsForHostelReAdmission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DisciplinaryDetailsForm objForm = (DisciplinaryDetailsForm) form;
		StudentEditHelper helper = StudentEditHelper.getInstance();
		StudentEditTransactionImpl impl = new StudentEditTransactionImpl();
		HttpSession session = request.getSession(true);
		try{
			Integer studentId=null;
			if(request.getSession().getAttribute("DisplinaryPhotoBytes")!=null){
				request.getSession().removeAttribute("DisplinaryPhotoBytes");
			}
			objForm.setRollRegNo(objForm.getTempRegRollNo());
			objForm.setTempName(objForm.getTempFirstName());
			boolean isActive = false;
			if(objForm.getRollRegNo()!=null && !objForm.getRollRegNo().isEmpty()){
				isActive = handler.checkStudentIsActive(objForm);
			}
			if(!isActive){
				/*List<StudentTO> studenttoList = handler.getSearchedStudents(objForm);
				if(studenttoList!=null && !studenttoList.isEmpty()){
					objForm.setStudentTOList(studenttoList);
				}*/
				studentId=handler.validate1(objForm.getRollRegNo(),objForm.getTempApplicationNo());
				if(studentId!= null){
					objForm.setStudentId(studentId.toString());
				}
				setUserId(request, objForm);
				objForm = handler.getDisciplinaryDetails(objForm,studentId,request);
				List<ExamStudentPreviousClassTo>  classDetailsTo = helper
				.convertBOToTO_viewHistory_ClassGroupDetailsView(impl
						.viewHistory_ClassGroupDetailsView(studentId));
				Collections.sort(classDetailsTo);
				if(classDetailsTo!=null)
				session.setAttribute("SEMLIST", classDetailsTo);
				
				
				int classId= DownloadHallTicketHandler.getInstance().getClassId(studentId, objForm);
				int marksCardClassId1 = DownloadHallTicketHandler.getInstance().getClassIds(studentId, classId, false, "Marks Card");
				int semesterNo = DownloadHallTicketHandler.getInstance().getTermNumber(marksCardClassId1);
				
				int count=1;
				String publishedfor= "Marks Card";
			
				MarksCardTO marksCardTo=null;
				MarksCardTO marksCardSemExamList=null;
				objForm.setSemesterYearNo(semesterNo);
				
				marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(objForm);
				
				if(marksCardTo!=null)	
				{
				objForm.setMarksCardTo(marksCardTo);
				List<MarksCardTO> newMarkscardTo = new ArrayList<MarksCardTO>();
				newMarkscardTo.add(marksCardTo);
				if(newMarkscardTo!=null)
					session.setAttribute("MARKSCARDTO", newMarkscardTo);
				}
				 List<MarksCardTO> SemExamListTo=(DownloadHallTicketHandler.getInstance().getExamSemList(objForm));
				if(SemExamListTo!=null)	
				{
				Collections.sort(SemExamListTo,new MarkComparator());
				if(SemExamListTo!=null)
				session.setAttribute("EXAMSEMLIST", SemExamListTo);
				}
			//Sup Marks Card
				MarksCardTO SupMCSemExamList=null;
				
	             List<MarksCardTO> SupSemList=(DownloadHallTicketHandler.getInstance().getSupExamSemList(objForm));
	          //  Collections.sort(SupSemList, new MarkComparator());
				if(SupSemList!= null)
				session.setAttribute("SUPEXAMSEMLIST", SupSemList);
				
				// for CJC marks card......
				 if(objForm.getIsCjc().equals(true))
				  {
				   List<MarksCardTO> CjcSemExamListTo=(DownloadHallTicketHandler.getInstance().getCjcExamSemList(objForm));
					if(CjcSemExamListTo!=null)	
					{
						Collections.sort(CjcSemExamListTo,new MarkComparator());
						if(CjcSemExamListTo!=null)
						session.setAttribute("CJCEXAMSEMLIST", CjcSemExamListTo);
					}
				  }
				// for the student is final year or not and showing status
				 boolean finalYear=DisciplinaryDetailsHandler.getInsatnce().getStudentFinalYearStatus(objForm);
				 if(finalYear){
					 objForm.setCheckFinalYear("yes");
					 boolean status=DisciplinaryDetailsHandler.getInsatnce().getCourseCompletionStatus(objForm.getStudentId());
					 if(status){
						 objForm.setStatus("Completed");
						DisciplinaryDetailsHandler.getInsatnce().getLastExamNameAndMonthAndYear(SupSemList,SemExamListTo,objForm);
					 }else{
						 objForm.setStatus("Pending");
					 }
				 }else{
					 objForm.setCheckFinalYear("no");
				 }
				 //for the hostel details of student
			 DisciplinaryDetailsHandler.getInsatnce().getHostelDetailsForStudent(objForm);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.DESCIPLINARY_DETAILS_DISPLAY_FOR_HOSTEL_READMISSION);
	}
	
	
	
	public ActionForward getAmAbsenceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		DisciplinaryDetailsForm disciplinaryDetailsForm=(DisciplinaryDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			//disciplinaryDetailsForm.setSubjectAttendance("Yes");
			//double absent=Double.valueOf(disciplinaryDetailsForm.getClassesAbsent());
			//double cocurricular=Double.valueOf(disciplinaryDetailsForm.getCocurricularLeave());
			
			if(disciplinaryDetailsForm.getAmList().size()==0){
			
				disciplinaryDetailsForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			disciplinaryDetailsForm.setErrorMessage(msg);
			disciplinaryDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS_AM);	
	}
	
	
	public ActionForward getPmAbsenceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		DisciplinaryDetailsForm disciplinaryDetailsForm=(DisciplinaryDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			//disciplinaryDetailsForm.setSubjectAttendance("Yes");
			//double absent=Double.valueOf(disciplinaryDetailsForm.getClassesAbsent());
			//double cocurricular=Double.valueOf(disciplinaryDetailsForm.getCocurricularLeave());
			
			if(disciplinaryDetailsForm.getPmList().size()==0){
				disciplinaryDetailsForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			disciplinaryDetailsForm.setErrorMessage(msg);
			disciplinaryDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS_PM);	
	}
	
	
	
	
}
