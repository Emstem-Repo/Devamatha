package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewProcessForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.InterviewTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.admission.InterviewHelper;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * @author kalyan.c This class is implemented for Interview Card Generation
 *         Process
 */
@SuppressWarnings("deprecation")
public class InterviewProcessAction extends BaseDispatchAction {

	private static Log log = LogFactory.getLog(InterviewProcessAction.class);

	/**
	 * This method is used to display interview process jsp.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward initInterviewProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initInterviewProcess..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","selection_process_workflow");
		
		ActionErrors errors = new ActionErrors();
		try {

			if (CommonUtil.checkForEmpty(interviewForm.getCandidateCount())) {
				if (interviewForm.getCandidateCount().equalsIgnoreCase("0")) {
					interviewForm.resetCandidateCount(mapping, request);
					errors.add("error", new ActionError(
							CMSConstants.KNOWLEDGEPRO_INTERVIEW_NOCANDIDATES));
					saveErrors(request, errors);
				}
			}
			interviewForm.setSingleGroup("single");
			if (errors.isEmpty()) {
				interviewForm.resetInterviewProcess();
			}
			setRequestedDataToForm(interviewForm,request);
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewForm.setErrorMessage(msg);
				interviewForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}

		}

		log.info("exit initInterviewProcess..");
		return mapping.findForward(CMSConstants.INIT_INTERVIEW_PROCESS);
	}

	private void setRequestedDataToForm(InterviewProcessForm interviewForm,HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			interviewForm.setProgramTypeList(programTypeList);
		}
		Map collegeMap;
		Map<Integer,String> courseMap;
		if(interviewForm.getCourseId()!=null && interviewForm.getYears()!=null &&
				interviewForm.getCourseId().length()>0 && interviewForm.getYears().length()>0){
			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCourse
			(Integer.valueOf(interviewForm.getCourseId()),Integer.valueOf(interviewForm.getYears()));
			request.setAttribute("interviewMap", collegeMap);
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(interviewForm.getProgram()));
			request.setAttribute("coursesMap", courseMap);
		}
		else if(interviewForm.getProgram()!= null && interviewForm.getYears() != null &&
				interviewForm.getProgram().length() >0 && interviewForm.getYears().length()>0){
			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByProgram
			(Integer.valueOf(interviewForm.getProgram()),Integer.valueOf(interviewForm.getYears()));
			request.setAttribute("interviewMap", collegeMap);
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(interviewForm.getProgram()));
			request.setAttribute("coursesMap", courseMap);
		}
	}

	/**
	 * This method is used to add interview details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward submitInterviewProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		log.info("entered submitInterviewProcess..");
		InterviewProcessForm objForm = (InterviewProcessForm) form;
		InterviewTypeHandler ihandler = InterviewTypeHandler.getInstance();
		try {
			ActionErrors errors = objForm.validate(mapping, request);
			if (errors.isEmpty()) {
				if (Integer.parseInt(objForm.getNoOfCandidates()) > Integer
						.parseInt(objForm.getNoOfCandidates_orig())) {
					errors.add(CMSConstants.ERROR, new ActionError(
							CMSConstants.NO_OF_CANDIDATES_INVALID, objForm
									.getNoOfCandidates_orig()));
				}
			}
			errors = validateForNumaric(objForm, errors);
			errors = validateTime(objForm, errors);
			errors = validateInterviewDate(objForm, errors);
			int courseId = 0;
			int programId = 0;
			if (objForm.getProgram() != null
					&& !objForm.getProgram().trim().isEmpty()) {
				programId = Integer.parseInt(objForm.getProgram());
			}
			if (objForm.getCourseId() != null
					&& !objForm.getCourseId().trim().isEmpty()) {
				courseId = Integer.parseInt(objForm.getCourseId());
			}
			List<GroupTemplate> mailTemplate = InterviewHelper
					.getInterviewScheduleMailTemplate(courseId, programId);

			if (mailTemplate == null
					|| (mailTemplate != null && mailTemplate.isEmpty())) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.interviewprocessmailtemplate"));

			}
			boolean isAdded = false;
			if (errors.isEmpty()) {
				String[] st1 = request.getParameterValues("interviewType");
				ArrayList<Integer> interviewTypeList = new ArrayList<Integer>();
				if (st1.length != 0) {
					interviewTypeList = new ArrayList<Integer>();
					for (int x = 0; x < st1.length; x++) {
						interviewTypeList.add(Integer.parseInt(st1[x]));
					}
				}

				objForm.setInterviewTypeList(interviewTypeList);
				isAdded = ihandler.persistInterview(objForm, request);

			} else {
				String [] tempArray = request.getParameterValues("interviewType");
				
				StringBuilder intType = new StringBuilder();
				if(tempArray!=null){
					for(int i=0;i<tempArray.length;i++){
						 intType.append(tempArray[i]);
						 if(i<(tempArray.length-1)){
							 intType.append(",");
						 }
					}
				}
				objForm.setHiddenInterviewType(intType.toString());
				saveErrors(request, errors);
				setExamCenterMapToRequest(request, objForm);
				setRequestedDataToForm(objForm, request);
				return mapping.findForward(CMSConstants.INIT_INTERVIEW_PROCESS);
			}

			if (isAdded) {
				// successfully added interview
				objForm.resetInterviewProcess();
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_INTERVIEW_ADDSUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);

			} else {
				// failure
				errors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_INTERVIEW_ADDFAILURE));
				saveErrors(request, errors);
			}
		} catch (ApplicationException ae) {
			log
					.info("error occured in submitInterviewProcess method of InterviewProcessAction class..");
			String msg = super.handleApplicationException(ae);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log
					.info("error occured in submitInterviewProcess method of InterviewProcessAction class..");
			throw e;
		}

		log.info("exit submitInterviewProcess..");
		setRequestedDataToForm(objForm, request);
		return mapping.findForward(CMSConstants.INIT_INTERVIEW_PROCESS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInterviewProcessGenerate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initInterviewProcessGenerate..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		try {
			if (interviewForm.getInterviewType() != null
					&& !interviewForm.getInterviewType().isEmpty()) {
				InterviewProgramCourse interviewProgramCourse = InterviewTypeHandler
						.getInstance().getProgramDetails(
								Integer.parseInt(interviewForm
										.getInterviewType()));
				int courseId = InterviewHelper.getProgramDetails(
						interviewProgramCourse, interviewForm);
				List studentList = InterviewTypeHandler.getInstance()
						.getStudentsList(interviewForm, courseId);

				if (studentList != null) {
					interviewForm.setStudentList(studentList);
				}
			}
		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			interviewForm.setErrorMessage(msg);
			interviewForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}

		log.info("exit initInterviewProcessGenerate..");
		return mapping
				.findForward(CMSConstants.INIT_INTERVIEW_PROCESS_GENERATE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInterviewProcessGenerate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitInterviewProcessGenerate..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		try {

		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewForm.setErrorMessage(msg);
				interviewForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.info("exit submitInterviewProcessGenerate..");
		return mapping
				.findForward(CMSConstants.INIT_INTERVIEW_PROCESS_GENERATE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             This method is used to search page of print interview card
	 */
	public ActionForward initPrintInterviewProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initPrintInterviewProcess..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		try {
			// Get PogramType
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler
					.getInstance().getProgramType();
			if (programTypeList != null) {
				interviewForm.setProgramTypeList(programTypeList);
			}
			interviewForm.resetInterviewPrintCard(mapping, request);
		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			interviewForm.setErrorMessage(msg);
			interviewForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}
		cleanupSessionData(request);
		log.info("exit initPrintInterviewProcess..");
		return mapping.findForward(CMSConstants.INIT_PRINT_INTERVIEW_PROCESS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             This method will display the result of the search for print
	 *             interview card
	 */
	public ActionForward submitPrintInterviewProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitPrintInterviewProcess..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			interviewForm.setPrintChallan(CMSConstants.KNOWLEDGEPRO_FALSE);

			if (errors.isEmpty()) {
				int applicationNo = 0;
				int interviewTypeid = 0;
				int programid = 0;
				int programtypeid = 0;
				int courseid = 0;
				int year = 0;
				if (CommonUtil.checkForEmpty(interviewForm.getSearchApplNo())) {
					if (StringUtils.isNumeric(interviewForm.getSearchApplNo())) {
						applicationNo = Integer.parseInt(interviewForm
								.getSearchApplNo());
					} else {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.ADMISSION_CANCEL_APPNUMBER_INT));
						addErrors(request, errors);
						return mapping
								.findForward(CMSConstants.INIT_PRINT_INTERVIEW_PROCESS);

					}

				}
				if (CommonUtil.checkForEmpty(interviewForm.getInterviewType())) {
					interviewTypeid = Integer.parseInt(interviewForm
							.getInterviewType());
				}
				if (CommonUtil.checkForEmpty(interviewForm.getProgramType())) {
					programtypeid = Integer.parseInt(interviewForm
							.getProgramType());
				}
				if (CommonUtil.checkForEmpty(interviewForm.getProgram())) {
					programid = Integer.parseInt(interviewForm.getProgram());
				}
				if (CommonUtil.checkForEmpty(interviewForm.getCourse())) {
					courseid = Integer.parseInt(interviewForm.getCourse());
				}
				if (CommonUtil.checkForEmpty(interviewForm.getYears())) {
					year = Integer.parseInt(interviewForm.getYears());
				}

				List applicationList = InterviewTypeHandler.getInstance()
						.getByStudentDetails(
								applicationNo,
								interviewTypeid,
								programtypeid,
								programid,
								courseid,
								year,
								CommonUtil.ConvertStringToSQLDate(interviewForm
										.getBirthDate()),
								interviewForm.getSrartName());
				interviewForm.setApplicationList(applicationList);

				if (applicationList.isEmpty()) {
					ActionMessage message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_INTERVIEW_NORECORDS);
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			} else {

				// Get PogramType
				List<ProgramTypeTO> programTypeList = ProgramTypeHandler
						.getInstance().getProgramType();
				if (programTypeList != null) {
					interviewForm.setProgramTypeList(programTypeList);
				}
				interviewForm.resetInterviewPrintCard(mapping, request);
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.INIT_PRINT_INTERVIEW_PROCESS);

			}
		} catch (ApplicationException ae) {
			String msg = super.handleApplicationException(ae);
			interviewForm.setErrorMessage(msg);
			interviewForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			throw e;
		}
		cleanupSessionData(request);
		log.info("exit submitPrintInterviewProcess..");
		return mapping.findForward(CMSConstants.SUBMIT_PRINT_INTERVIEW_PROCESS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             This method will print interview card
	 */
	public ActionForward printInterviewProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered printInterviewProcess..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		ActionErrors errors = new ActionErrors();

		validatePrintICard(interviewForm, errors);
		if (errors.isEmpty()) {
			try {
				interviewForm.setPrintChallan(CMSConstants.KNOWLEDGEPRO_TRUE);
				if (interviewForm.getSelectedCandidates() != null) {
					Organisation organisation = OrganizationHandler
							.getInstance().getRequiredFile();
					if (organisation != null) {
						List interviewCardTOList = InterviewTypeHandler
								.getInstance().getInterviewCard(
										interviewForm.getSelectedCandidates(),
										organisation, interviewForm);
						interviewForm.setStudentList(interviewCardTOList);
						// set photo to session
						if (organisation.getLogoContentType() != null) {
							HttpSession session = request.getSession(false);
							if (session != null) {
								session.setAttribute(
										CMSConstants.KNOWLEDGEPRO_LOGO,
										organisation.getLogo());
								session.setAttribute(
										CMSConstants.KNOWLEDGEPRO_TOPBAR,
										organisation.getTopbar());
							}
						}
					}
				}
				interviewForm.resetSelctedCandidates(mapping, request);
			} catch (ApplicationException ae) {
				String msg = super.handleApplicationException(ae);
				interviewForm.setErrorMessage(msg);
				interviewForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception e) {
				throw e;
			}
		} else {
			interviewForm.setPrintChallan(CMSConstants.KNOWLEDGEPRO_FALSE);
			saveErrors(request, errors);
			interviewForm.resetSelctedCandidates(mapping, request);
			return mapping
					.findForward(CMSConstants.SUBMIT_PRINT_INTERVIEW_PROCESS);
		}
		log.info("exit printInterviewProcess..");
		return mapping.findForward(CMSConstants.SUBMIT_PRINT_INTERVIEW_PROCESS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             This method will print interview card for Notice Board
	 */
	public ActionForward printInterviewNotice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered printInterviewProcess..");
		InterviewProcessForm interviewForm = (InterviewProcessForm) form;
		ActionErrors errors = new ActionErrors();
		validatePrintNoticeBoard(interviewForm, errors);
		if (errors.isEmpty()) {
			try {
				interviewForm.setPrintChallan(CMSConstants.KNOWLEDGEPRO_TRUE);
				if (interviewForm.getSelectedCandidates() != null) {
					List interviewCardTOList = InterviewTypeHandler
							.getInstance().getInterviewCardList(
									interviewForm.getSelectedCandidates());
					interviewForm.setStudentList(interviewCardTOList);
				}
				interviewForm.resetSelctedCandidates(mapping, request);
			} catch (ApplicationException ae) {
				String msg = super.handleApplicationException(ae);
				interviewForm.setErrorMessage(msg);
				interviewForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception e) {
				throw e;
			}
		} else {
			interviewForm.setPrintChallan(CMSConstants.KNOWLEDGEPRO_FALSE);
			saveErrors(request, errors);
			return mapping
					.findForward(CMSConstants.SUBMIT_PRINT_INTERVIEW_PROCESS);
		}

		log.info("exit printInterviewProcess..");
		return mapping.findForward(CMSConstants.SUBMIT_PRINT_INTERVIEW_PROCESS);
	}

	/**
	 * @param interForm
	 * @param errors
	 *            This method is used to validate numerics
	 */
	private ActionErrors validateForNumaric(InterviewProcessForm interForm,
			ActionErrors errors) {

		if (CommonUtil.checkForEmpty(interForm.getStartHours())) {
			if (!StringUtils.isNumeric(interForm.getStartHours())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getStartMins())) {
			if (!StringUtils.isNumeric(interForm.getStartMins())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getEndHours())) {
			if (!StringUtils.isNumeric(interForm.getEndHours())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getEndMins())) {
			if (!StringUtils.isNumeric(interForm.getEndMins())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getIntervalHours())) {
			if (!StringUtils.isNumeric(interForm.getIntervalHours())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getIntervalMins())) {
			if (!StringUtils.isNumeric(interForm.getIntervalMins())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromHours())) {
			if (!StringUtils.isNumeric(interForm.getBreakFromHours())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromMins())) {
			if (!StringUtils.isNumeric(interForm.getBreakFromMins())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToHours())) {
			if (!StringUtils.isNumeric(interForm.getBreakToHours())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToMins())) {
			if (!StringUtils.isNumeric(interForm.getBreakToMins())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromHoursTwo())) {
			if (!StringUtils.isNumeric(interForm.getBreakFromHoursTwo())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromMinsTwo())) {
			if (!StringUtils.isNumeric(interForm.getBreakFromMinsTwo())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToHoursTwo())) {
			if (!StringUtils.isNumeric(interForm.getBreakToHoursTwo())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToMinsTwo())) {
			if (!StringUtils.isNumeric(interForm.getBreakToMinsTwo())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromHoursThree())) {
			if (!StringUtils.isNumeric(interForm.getBreakFromHoursThree())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromMinsThree())) {
			if (!StringUtils.isNumeric(interForm.getBreakFromMinsThree())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToHoursThree())) {
			if (!StringUtils.isNumeric(interForm.getBreakToHoursThree())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToMinsThree())) {
			if (!StringUtils.isNumeric(interForm.getBreakToMinsThree())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC) != null
						&& !errors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}
		}
		return errors;
	}

	private ActionErrors validateTime(InterviewProcessForm interForm,
			ActionErrors errors) {

		if (CommonUtil.checkForEmpty(interForm.getStartHours())) {
			if (Integer.parseInt(interForm.getStartHours()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getStartMins())) {
			if (Integer.parseInt(interForm.getStartMins()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getEndHours())) {
			if (Integer.parseInt(interForm.getEndHours()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getEndMins())) {
			if (Integer.parseInt(interForm.getEndMins()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getIntervalHours())) {
			if (Integer.parseInt(interForm.getIntervalHours()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}
		if (CommonUtil.checkForEmpty(interForm.getIntervalMins())) {
			if (Integer.parseInt(interForm.getIntervalMins()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromHours())) {
			if (Integer.parseInt(interForm.getBreakFromHours()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromMins())) {
			if (Integer.parseInt(interForm.getBreakFromMins()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToHours())) {
			if (Integer.parseInt(interForm.getBreakToHours()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToMins())) {
			if (Integer.parseInt(interForm.getBreakToMins()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromHoursTwo())) {
			if (Integer.parseInt(interForm.getBreakFromHoursTwo()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromMinsTwo())) {
			if (Integer.parseInt(interForm.getBreakFromMinsTwo()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToHoursTwo())) {
			if (Integer.parseInt(interForm.getBreakToHoursTwo()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToMinsTwo())) {
			if (Integer.parseInt(interForm.getBreakToMinsTwo()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromHoursThree())) {
			if (Integer.parseInt(interForm.getBreakFromHoursThree()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakFromMinsThree())) {
			if (Integer.parseInt(interForm.getBreakFromMinsThree()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToHoursThree())) {
			if (Integer.parseInt(interForm.getBreakToHoursThree()) >= 24) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}

		if (CommonUtil.checkForEmpty(interForm.getBreakToMinsThree())) {
			if (Integer.parseInt(interForm.getBreakToMinsThree()) >= 60) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT) != null
						&& !errors.get(
								CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)
								.hasNext()) {
					errors
							.add(
									CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}
		}
		return errors;

	}

	/**
	 * @param interForm
	 * @param errors
	 *            This method is used to validate date
	 */
	private ActionErrors validateInterviewDate(InterviewProcessForm interForm,
			ActionErrors errors) {

		if (interForm.getDatesOfInterview() != null) {
			String dates[] = CommonUtil.getDates(interForm
					.getDatesOfInterview());
			if (dates != null) {
				for (int i = 0; i < dates.length; i++) {
					if (!CommonUtil.isValidDate(dates[i])) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID,
											new ActionError(
													CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
						}
					} else {

						if (!validatePastDate(dates[i])) {
							if (errors
									.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE) != null
									&& !errors
											.get(
													CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE)
											.hasNext()) {
								errors
										.add(
												CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE,
												new ActionError(
														CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE));
							}

						}
					}
				}
			}
		}
		return errors;
	}

	/**
	 * past date validation
	 * 
	 * @param dateString
	 * @return This method is used to validate past date
	 */
	private boolean validatePastDate(String dateString) {
		log.info("enter validatePastDate..");
		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateString, "dd/MM/yyyy", "MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();

		log.info("exit validatePastDate..");
		if (date.compareTo(origdate) < 0)
			return false;
		else
			return true;

	}

	/**
	 * @param interForm
	 * @param errors
	 *            This method is used to validate required fields like program
	 *            type program and course
	 */
	private void validateInterviewCard(InterviewProcessForm interForm,
			ActionErrors errors) {
		if (errors == null)
			errors = new ActionErrors();
		if (!CommonUtil.checkForEmpty(interForm.getProgramType())
				| !CommonUtil.checkForEmpty(interForm.getProgram())
				| !CommonUtil.checkForEmpty(interForm.getCourse())) {

			errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_PROGRAMTYPE_REQUIRED));
			errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_PROGRAM_REQUIRED));
			errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_COURSE_REQUIRED));

		}
	}

	/**
	 * @param interForm
	 * @param errors
	 *            This method is used to validate print interview card
	 */
	private void validatePrintICard(InterviewProcessForm interForm,
			ActionErrors errors) {
		if (errors == null)
			errors = new ActionErrors();

		if (interForm.getSelectedCandidates() == null) {

			errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_CHECKBOX_SELECT));
		}
	}

	/**
	 * @param interForm
	 * @param errors
	 *            This method is used to validate Interview card for Notice
	 *            Board
	 */
	private void validatePrintNoticeBoard(InterviewProcessForm interForm,
			ActionErrors errors) {
		if (errors == null) {
			errors = new ActionErrors();
		}
		if (interForm.getSelectedCandidates() == null) {

			errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_CHECKBOX_SELECT));
		}
	}

	/**
	 * @param request
	 *            This method will clean up the session
	 */
	private void cleanupSessionData(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(CMSConstants.KNOWLEDGEPRO_LOGO) != null) {
				session.removeAttribute(CMSConstants.KNOWLEDGEPRO_LOGO);
			}
			if (session.getAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR) != null) {
				session.removeAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR);
			}

		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             This method is used to navigate to different pages depending
	 *             on the validation
	 */
	public ActionForward dispalyChallan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InterviewProcessForm interviewForm = (InterviewProcessForm) form;

		if (interviewForm.getSelectedType() != null
				&& (interviewForm.getSelectedType().equals("1"))) {
			return mapping.findForward(CMSConstants.INTERVIEW_CARD);
		} else if (interviewForm.getSelectedType() != null
				&& (interviewForm.getSelectedType().equals("2"))) {
			return mapping.findForward(CMSConstants.INTERVIEW_CARD_NOTICE);
		}
		return mapping.findForward(CMSConstants.SUBMIT_PRINT_INTERVIEW_PROCESS);
	}
	
	/**
	 * 
	 * @param request
	 * @param interviewProcessForm
	 */
	public void setExamCenterMapToRequest(HttpServletRequest request, InterviewProcessForm interviewProcessForm) {
		if (interviewProcessForm.getProgramId() != null
				&& !(interviewProcessForm.getProgramId().isEmpty())) {
			Map<Integer, String> examCenterMap = CommonAjaxHandler.getInstance().getExamCenterForProgram(Integer.parseInt(interviewProcessForm.getProgramId()));
			request.setAttribute("examCenterMap", examCenterMap);
		}
	}
	

}
