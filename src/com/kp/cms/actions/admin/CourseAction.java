package com.kp.cms.actions.admin;

import java.util.Collections;
import java.util.Comparator;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.CurrencyMasterHandler;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.RoomTypeHandler;
import com.kp.cms.handlers.studentfeedback.EvaStudentFeedBackQuestionHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.to.admission.PrintShortageAttendanceTo;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;


@SuppressWarnings("deprecation")
public class CourseAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CourseAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will set program type list, admitted through list, course
	 *            list
	 * 
	 * @return to Forward courseEntry page.
	 * @throws Exception
	 */
	public ActionForward initCourses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		CourseForm courseForm = (CourseForm) form;
		try {
			
			String formName = mapping.getName();
			//It use for Help,Don't Remove
			HttpSession session=request.getSession();
			session.setAttribute("field","Courses");
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setProgramTypeListToRequest(request);  //setting programType List to request to populate in combo
			setAdmittedThroughListToRequest(request, courseForm);  //setting admitted through list to request for seat allocation 
			setCourseListToRequest(request);   //setting courseList for displaying all the records in UI
			courseForm.setLocationId(null);
			setWorkLocationMap(courseForm);
			setUserId(request, courseForm); 
			
			//setting userID for updating last changed details
		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				courseForm.setErrorMessage(msg);
				courseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (request.getSession().getAttribute("baseActionForm") != null) {
			request.getSession().removeAttribute("baseActionForm");
		}
		request.setAttribute("courseOperation", "insert");
		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}

	private void setWorkLocationMap(CourseForm courseForm) throws Exception{
		Map<Integer,String> workLocationMap=CourseHandler.getInstance().getWorkLocationMap();
		if(workLocationMap!=null){
			courseForm.setWorkLocationMap(workLocationMap);
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Course
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward addCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		CourseForm courseForm = (CourseForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = courseForm.validate(mapping, request);
		boolean isAdded;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				setCourseListToRequest(request);
				if(courseForm.getCourseCode().trim().isEmpty()){   //if only space is entered then assigning null. otherwise space will get added in the table
					courseForm.setCourseCode(null);
				}
				if(courseForm.getCourseName().trim().isEmpty()){
					courseForm.setCourseName(null);
				}
				if(courseForm.getPayCode().trim().isEmpty()){
					courseForm.setPayCode(null);
				}
				return mapping.findForward(CMSConstants.COURSE_ENTRY);
			}
			
			//maxintake & total seat allocation should be equal.  if it is not equal then showing message
			if(courseForm.getTotal() > 0 && maxIntakeNotValid(courseForm, request, mapping)){
				return mapping.findForward(CMSConstants.COURSE_ENTRY);
			}
			isAdded = CourseHandler.getInstance().addCourse(courseForm, "Add");  //Add parameter is using to identify add/edit

			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			setWorkLocationMap(courseForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.course.code.name.exists"));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			setprogramMapToRequest(request, courseForm);
			return mapping.findForward(CMSConstants.COURSE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.COURSE_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			setprogramMapToRequest(request, courseForm);
			setWorkLocationMap(courseForm);
			return mapping.findForward(CMSConstants.COURSE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of course page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				courseForm.setErrorMessage(msg);
				courseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.course.addsuccess", courseForm.getCourseName());
			messages.add("messages", message);
			saveMessages(request, messages);
			setAdmittedThroughListToRequest(request, courseForm);
			courseForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.course.addfailure", courseForm.getCourseName()));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			return mapping.findForward(CMSConstants.COURSE_ENTRY);
		}

		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will edit Course
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward editCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		CourseForm courseForm = (CourseForm) form;
		assignDataToForm(request, courseForm);  // assigning data to form in edit option
		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}
	/**
	 * setting data to form for edit
	 * @param courseForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(CourseForm courseForm, HttpServletRequest request) throws Exception {
		courseForm.setLocationId(null);
		int tempCourseId = Integer.parseInt(request.getParameter("courseId"));
		int totalseats = 0;
		List<SeatAllocationTO> tempseatalloclist = courseForm.getSeatAllocationList();
		List<CourseTO> courseList = CourseHandler.getInstance().getCourse(tempCourseId);  //getting course list based on the id and setting to form
		Iterator<CourseTO> courseItr = courseList.iterator();
		while (courseItr.hasNext()) {
			CourseTO courseTO = (CourseTO) courseItr.next();
			courseForm.setLocationId(String.valueOf(courseTO.getLocationId()));
			if(courseTO.getCurrencyId()!=null){
			if(courseTO.getCurrencyId().getId()>0){
			int currency=courseTO.getCurrencyId().getId();
			courseForm.setCurrencyId(Integer.toString(currency));}
			}if(courseTO.getIntApplicationFees()!=null){
			courseForm.setIntApplicationFees(courseTO.getIntApplicationFees().toString());
			}
			courseForm.setCommencementDate(courseTO.getCommencementDate());
			int progTypeId = courseTO.getProgramTo().getProgramTypeTo().getProgramTypeId();
			int programId = courseTO.getProgramTo().getId();
			courseForm.setProgramTypeId(Integer.toString(progTypeId));
			courseForm.setProgramId(Integer.toString(programId));
			courseForm.setCourseName(courseTO.getName());
			if(courseTO.getDateTime()!=null)
				courseForm.setDateTime(courseTO.getDateTime());
			if(courseTO.getGeneralFee()!=null)
				courseForm.setGeneralFee(courseTO.getGeneralFee());
			if(courseTO.getCasteFee()!=null)
				courseForm.setCasteFee(courseTO.getCasteFee());

			courseForm.setCourseCode(courseTO.getCode());
			courseForm.setMaxIntake(Integer.toString(courseTO.getMaxInTake()));
			courseForm.setIsAutonomous(courseTO.getIsAutonomous());
			courseForm.setSeatAllocationList(courseTO.getSeatAllocation());
			courseForm.setOrigCourseCode(courseForm.getCourseCode());
			courseForm.setOrigCourseName(courseTO.getName());
			courseForm.setOrigProgTypeId(Integer.toString(courseTO.getProgramTo().getProgramTypeTo().getProgramTypeId()));
			courseForm.setOrigProgId(Integer.toString(courseTO.getProgramTo().getId()));
			if(courseTO.isWorkExpMandatory())
				courseForm.setIsWorkExpMandatory("true");
			else
				courseForm.setIsWorkExpMandatory("false");
			if(courseTO.isBankIncludeSection())
				courseForm.setBankIncludeSection("true");
			else
				courseForm.setBankIncludeSection("false");
			if(courseTO.isAppearInOnline())
				courseForm.setIsAppearInOnline("true");
			else
				courseForm.setIsAppearInOnline("false");
			if(courseTO.isApplicationProcessSms())
				courseForm.setIsApplicationProcessSms("true");
			else 
				courseForm.setIsApplicationProcessSms("false");
			
			if(courseTO.isOnlyForApplication())
				courseForm.setOnlyForApplication("true");
			else 
				courseForm.setOnlyForApplication("false");
			
			if (courseTO.getAmount() != null) {
				courseForm.setAmount(courseTO.getAmount().toString());
			}
			courseForm.setIsWorkExperienceRequired(courseTO.getIsWorkExperienceRequired());
			courseForm.setPayCode(courseTO.getPayCode());
			courseForm.setIsDetailMarkPresent(courseTO.getIsDetailMarkPrint());
			List<SeatAllocationTO> seatalloclist = courseTO.getSeatAllocation();
			Iterator<SeatAllocationTO> itr = seatalloclist.iterator();
			while (itr.hasNext()) {
				SeatAllocationTO seatallocationto = (SeatAllocationTO) itr.next();
				totalseats = totalseats + seatallocationto.getNoofSeats();
			}
			courseForm.setTotal(totalseats);

			seatalloclist = courseTO.getSeatAllocation();
			Iterator<SeatAllocationTO> tempseatalloclistitr = tempseatalloclist.iterator();
			while (tempseatalloclistitr.hasNext()) {
				SeatAllocationTO tempseatalloc = (SeatAllocationTO) tempseatalloclistitr.next();
				itr = seatalloclist.iterator();
				while (itr.hasNext()) {
					SeatAllocationTO seatallocationto = (SeatAllocationTO) itr.next();
					if (seatallocationto.getAdmittedThroughId() == tempseatalloc.getAdmittedThroughId()) {
						tempseatalloc.setId(seatallocationto.getId());
						tempseatalloc.setNoofSeats(seatallocationto.getNoofSeats());
						tempseatalloc.setCourseId(seatallocationto.getCourseId());
					}

				}

			}
			courseForm.setSeatAllocationList(tempseatalloclist);
			courseForm.setCertificateCourseName(courseTO.getCertificateCourseName());
			if(courseTO.getCourseMarksCard()!=null && !courseTO.getCourseMarksCard().isEmpty())
			{
				courseForm.setCourseMarksCard(courseTO.getCourseMarksCard());
			}
			if(courseTO.getBankName()!=null && !courseTO.getBankName().isEmpty())
			{
				courseForm.setBankName(courseTO.getBankName());
			}
			if(courseTO.getBankNameFull()!=null && !courseTO.getBankNameFull().isEmpty())
			{
				courseForm.setBankNameFull(courseTO.getBankNameFull());
			}
			if(courseTO.getNoOfMidsemAttempts()!=0){
				courseForm.setNoOfMidSemAttempts(String.valueOf(courseTO.getNoOfMidsemAttempts()));
			}
			}

		request.setAttribute("seatAllocationList", courseForm.getSeatAllocationList());
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update Course
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward updateCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {

		CourseForm courseForm = (CourseForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = courseForm.validate(mapping, request);
		boolean isAdded; //= false;

		try {
			if (isCancelled(request)) {
				assignDataToForm(request, courseForm);
				return mapping.findForward(CMSConstants.COURSE_ENTRY);
			}

			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				setCourseListToRequest(request);
				setprogramMapToRequest(request, courseForm);
				if(courseForm.getCourseCode().trim().isEmpty()){  //if only space is entered then assigning null. otherwise space will get added in the table
					courseForm.setCourseCode(null);
				}
				if(courseForm.getCourseName().trim().isEmpty()){
					courseForm.setCourseName(null);
				}
				if(courseForm.getPayCode().trim().isEmpty()){
					courseForm.setPayCode(null);
				}
				request.setAttribute("courseOperation", "edit");
				return mapping.findForward(CMSConstants.COURSE_ENTRY);
			}
			//validation checking for maxintake
			if(courseForm.getTotal() > 0 && maxIntakeNotValid(courseForm, request, mapping)){
				setProgramTypeListToRequest(request);
				setCourseListToRequest(request);
				setprogramMapToRequest(request, courseForm);
				request.setAttribute("courseOperation", "edit");
				return mapping.findForward(CMSConstants.COURSE_ENTRY);
			}
			isAdded = CourseHandler.getInstance().addCourse(courseForm, "Edit");  //edit parameter is using to identify edit/add. because same method is calling in add also
			setprogramMapToRequest(request, courseForm);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.course.code.name.exists"));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			setprogramMapToRequest(request, courseForm);
			request.setAttribute("courseOperation", "edit");
			return mapping.findForward(CMSConstants.COURSE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.COURSE_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			setprogramMapToRequest(request, courseForm);
			request.setAttribute("courseOperation", "edit");
			return mapping.findForward(CMSConstants.COURSE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of course page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("courseOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				courseForm.setErrorMessage(msg);
				courseForm.setErrorStack(e.getMessage());
				request.setAttribute("courseOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.course.updatesuccess", courseForm
							.getCourseName());
			messages.add("messages", message);
			saveMessages(request, messages);
			setAdmittedThroughListToRequest(request, courseForm);
			courseForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.course.updatefailure", courseForm
							.getCourseName()));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setprogramMapToRequest(request, courseForm);
			setCourseListToRequest(request);
			request.setAttribute("courseOperation", "edit");
			return mapping.findForward(CMSConstants.COURSE_ENTRY);
		}
		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing course
	 * @return ActionForward This action method will called when particular
	 *         Course need to be deleted based on the course id.
	 * @throws Exception
	 */
	public ActionForward deleteCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseForm courseForm = (CourseForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (courseForm.getCourseId() != null) {
				int CourseId = Integer.parseInt(courseForm.getCourseId());
				isDeleted = CourseHandler.getInstance().deleteCourse(CourseId, false, courseForm);
			}
		} catch (Exception e) {
			log.error("error in final submit of course page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				courseForm.setErrorMessage(msg);
				courseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setProgramTypeListToRequest(request);
		setAdmittedThroughListToRequest(request, courseForm);
		setCourseListToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.course.deletesuccess", courseForm.getCourseName());
			messages.add("messages", message);
			saveMessages(request, messages);
			courseForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.course.deletefailure", courseForm.getCourseName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}
	/**
	 * validation checking for maxintake. total seatallocation & maxintake should be equal
	 * @param courseForm
	 * @param request
	 * @param mapping
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean maxIntakeNotValid(CourseForm courseForm,
			HttpServletRequest request, ActionMapping mapping) throws Exception {

		if ((courseForm.getMaxIntake() == null)	|| (courseForm.getMaxIntake().isEmpty())) {
			return false;
		}

		int maxIntake = Integer.parseInt(courseForm.getMaxIntake());
		int allotedseat = courseForm.getTotal();
		ActionErrors errors = new ActionErrors();
		if (allotedseat != maxIntake) {	
			errors.add("error", new ActionError("knowledgepro.admin.course.valid.maxintake"));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			return true;
		} else if (courseForm.getTotal() <= 0) {
			errors.add("error", new ActionError("knowledgepro.admin.course.valid.seatalloc.total"));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			setCourseListToRequest(request);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param request
	 *            This method sets the program type list to Request useful in
	 *            populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the program list to Request useful in
	 *            populating in program selection.
	 * @throws Exception
	 */
	public void setProgramListToRequest(HttpServletRequest request)	throws Exception {
		List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
		request.setAttribute("programList", programList);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the Admitted Through list to Request useful
	 *            in populating Course entry Seat Allocation.
	 * @throws Exception
	 */
	public void setAdmittedThroughListToRequest(HttpServletRequest request,	CourseForm courseForm) throws Exception {
		List<SeatAllocationTO> seatAllocationList = AdmittedThroughHandler.getInstance().addSeatAllocationWithAdmittedThrough();
		courseForm.setSeatAllocationList(seatAllocationList);

	}

	/**
	 * 
	 * @param request
	 *            This method sets the course list to Request. To display in the
	 *            UI
	 * @throws Exception
	 */
	public void setCourseListToRequest(HttpServletRequest request) throws Exception {
		List<CourseTO> courseList = CourseHandler.getInstance().getCourse(0);
		setFeedBackGroupToRequest(request);
		request.setAttribute("courseList", courseList);
	}
	private void setFeedBackGroupToRequest(HttpServletRequest request)  throws Exception{
        List<CurrencyMasterTO> currencyList = CurrencyMasterHandler.getInstance().getCurrencyMasterDetails();
        request.getSession().setAttribute("currencyList", currencyList);
    }

	/**
	 * using to load progams in the UI
	 * @param request
	 * @param courseForm
	 */
	public void setprogramMapToRequest(HttpServletRequest request, CourseForm courseForm) {
		if (courseForm.getProgramTypeId() != null
				&& !(courseForm.getProgramTypeId().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(courseForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
			request.setAttribute("courseOperation", "validation");
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will activate the course
	 * @return ActionForward This action method will called when particular
	 *         course need to be recovered based on the code and name.
	 * @throws Exception
	 */
	public ActionForward activateCourse(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		CourseForm courseForm = (CourseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (courseForm.getDuplId() != 0) {
				int duplCourseId = courseForm.getDuplId();
				isActivated = CourseHandler.getInstance().deleteCourse(duplCourseId, true, courseForm);
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(
					CMSConstants.COURSE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setProgramTypeListToRequest(request);
		setCourseListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(
					CMSConstants.COURSE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		request.setAttribute("courseOperation", "add");
		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}

	/**
	 * setting data to form
	 * @param request
	 * @param courseForm
	 * @throws Exception
	 */
	public void assignDataToForm(HttpServletRequest request, CourseForm courseForm) throws Exception {
		setAdmittedThroughListToRequest(request, courseForm);
		setProgramTypeListToRequest(request);
		setCourseListToRequest(request);
		setRequiredDataToForm(courseForm, request);
		setWorkLocationMap(courseForm);
		Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(courseForm.getProgramTypeId()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseOperation", "edit");
	}
	
	public ActionForward getDepartments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseForm courseForm = (CourseForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			List<CourseTO> deptList=CourseHandler.getInstance().getDepartments(courseForm);
			if (deptList.isEmpty()) {
				errors.add("error", new ActionError("knowledgepro.fee.No.honors.entry"));
				saveErrors(request, errors);
			}
			Collections.sort(deptList, new Comparator<CourseTO>(){
				public int compare(CourseTO c1, CourseTO c2) {
					int nameDiff = c1.getDeptName().compareTo(c2.getDeptName());
				    if(nameDiff != 0) {
				      return nameDiff;
				    }
				    return 0;
				}
			});
			
			courseForm.setDeptList(deptList);
		} catch (Exception e) {
			log.error("Error occured in deleteImageInAddMode of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return  mapping.findForward(CMSConstants.INIT_ASSIGN_DEPARTMENT);
	}
	
	
	public ActionForward saveSelectedDept(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		CourseForm courseForm = (CourseForm) form;
		setUserId(request,courseForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				//courseForm.setFeeSelected(false);
				isAdded = CourseHandler.getInstance().addAssignDept(courseForm);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.admin.assign.dept.addsuccess"));
					saveMessages(request, messages);
			   } else {
				   if(!courseForm.isSelected()){
				    errors.add("error", new ActionError( "knowledgepro.admin.assign.dept.notSelected"));
					addErrors(request, errors);
					saveErrors(request, errors);
				    return  mapping.findForward(CMSConstants.INIT_ASSIGN_DEPARTMENT);
				    }
				    else
				    {
					errors.add("error", new ActionError( "knowledgepro.admin.assign.dept.addfailure"));
					addErrors(request, errors);
				    }
		     	}
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				courseForm.setErrorMessage(msg);
				courseForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ASSIGN_DEPARTMENT);
		}
		setProgramTypeListToRequest(request);  //setting programType List to request to populate in combo
		setAdmittedThroughListToRequest(request, courseForm);  //setting admitted through list to request for seat allocation 
		setCourseListToRequest(request); 
		return mapping.findForward(CMSConstants.COURSE_ENTRY);
	}

}
