package com.kp.cms.actions.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.NewInternalMarksEntryForm;
import com.kp.cms.handlers.exam.NewInternalMarksEntryHandler;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.utilities.CommonUtil;
 

public class NewInternalMarksEntryAction extends BaseDispatchAction{
	NewInternalMarksEntryHandler internalMarksEntryHandler = NewInternalMarksEntryHandler.getInstance();
	
	private static final Log log = LogFactory.getLog(NewInternalMarksEntryAction.class);	
	/** Initial method of CIA Marks Entry . These method will display the list of results of the Internal Exam Details.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInternalMarksEntry(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewInternalMarksEntryForm internalMarksEntryForm = (NewInternalMarksEntryForm) form;
		resetFormData(internalMarksEntryForm);
		setUserId(request, internalMarksEntryForm);
		try {
//			set the Internal Exam Details of a particular User to the Form.
			setInternalMarksEntryDataToForm(internalMarksEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			internalMarksEntryForm.setErrorMessage(msg);
			internalMarksEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.NEW_INTERNAL_MARKS_ENTRY);
	}

	/**
	 * @param internalMarksEntryForm
	 */
	private void resetFormData(NewInternalMarksEntryForm internalMarksEntryForm) {
		internalMarksEntryForm.setSubjectCode(null);
		internalMarksEntryForm.setExamId(null);
		internalMarksEntryForm.setBatchId(null);
		internalMarksEntryForm.setBatchName(null);
		internalMarksEntryForm.setStudentList(null);
		internalMarksEntryForm.setMaxMarks(null);
		internalMarksEntryForm.setTheoryExamMarksDetails(null);
		internalMarksEntryForm.setPracticalExamMarksDetails(null);
		internalMarksEntryForm.setExamMap(null);
		internalMarksEntryForm.setClassNames(null);
		internalMarksEntryForm.setPrintConfirmation(null);
	}

	/**
	 * @param internalMarksEntryForm
	 * @throws Exception
	 */
	private void setInternalMarksEntryDataToForm( NewInternalMarksEntryForm internalMarksEntryForm) throws Exception{
		internalMarksEntryHandler.getInternalMarksDetails(internalMarksEntryForm);
	}
	/** These method is used to display the NO. of Students of Batch (or) Class for Theory Internal Subjects, 
	 * To whom the teacher has to enter the marks.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTheoryInternalStudents(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewInternalMarksEntryForm objform = (NewInternalMarksEntryForm) form;
		HttpSession session = request.getSession();
		session.setAttribute("Theory", "Theory");
		try {
			List<InternalMarksEntryTO> examDetails = objform
					.getTheoryExamMarksDetails();
			objform.setStudentList(null);
			objform.setBatchName(null);
			if (examDetails != null) {
				Iterator<InternalMarksEntryTO> iterator = examDetails
						.iterator();
				while (iterator.hasNext()) {
					InternalMarksEntryTO internalMarksEntryTO = iterator.next();
					if ((!objform.getBatchId().equalsIgnoreCase("0")
							&& !String.valueOf( internalMarksEntryTO.getBatchId()) .equalsIgnoreCase("0") && objform
							.getBatchId().equalsIgnoreCase(
									String.valueOf(internalMarksEntryTO
											.getBatchId())))
							&& objform.getExamId().equalsIgnoreCase(
									String.valueOf(internalMarksEntryTO
											.getExamId()))) {
						objform.setSubjectType("1");
						/*-------- set batch student details to form -------------*/
						setStudentExamMarksDetailsTOForm(objform,
								internalMarksEntryTO);
						

					} else if (objform.getBatchId().equalsIgnoreCase("0")) {
						if (objform.getSubjectId() != null
								&& objform.getClassId() != null
								&& objform.getExamId() != null) {
							if (internalMarksEntryTO.getSubId() == Integer
									.parseInt(objform.getSubjectId())
									&& internalMarksEntryTO.getClassId() == Integer
											.parseInt(objform.getClassId())
									&& internalMarksEntryTO.getExamId() == Integer
											.parseInt(objform.getExamId())) {
								objform.setSubjectType("1");
								/*-------- set class student details to form -------------*/
								setStudentExamMarksDetailsTOForm(objform,
										internalMarksEntryTO);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.NEW_INTERNAL_MARKS_ENTRY_RESULT);
	}
	/** These method is used to display the Student NO. of Students of Batch (or) Class for Practical Internal Subjects 
	 * , To whom the teacher has to enter the marks.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewPracticalInternalStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewInternalMarksEntryForm objform = (NewInternalMarksEntryForm) form;
		try {
			List<InternalMarksEntryTO> examDetails = objform .getPracticalExamMarksDetails();
			objform.setStudentList(null);
			// HttpSession session=request.getSession();
			if (examDetails != null) {
				Iterator<InternalMarksEntryTO> iterator = examDetails .iterator();
				while (iterator.hasNext()) {
					InternalMarksEntryTO internalMarksEntryTO = iterator.next();
					if (objform.getSubjectId() != null
							&& objform.getClassId() != null
							&& objform.getExamId() != null
							&& objform.getBatchId() != null) {
						if (internalMarksEntryTO.getSubId() == Integer .parseInt(objform.getSubjectId())
								&& internalMarksEntryTO.getClassId() == Integer .parseInt(objform.getClassId())
								&& internalMarksEntryTO.getExamId() == Integer .parseInt(objform.getExamId())
								&& internalMarksEntryTO.getBatchId() == Integer .parseInt(objform.getBatchId())) {
							objform.setSubjectType("0");
							/*-------- set class student details to form -------------*/
							setStudentExamMarksDetailsTOForm(objform, internalMarksEntryTO);
						}
					}
				}
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		return mapping.findForward(CMSConstants.NEW_INTERNAL_MARKS_ENTRY_RESULT);
	}
	/** These method is used to display the particular exam details in the screen. 
	 * @param internalMarksEntryTO
	 * @throws Exception
	 */
	private void setStudentExamMarksDetailsTOForm( NewInternalMarksEntryForm objform,
			InternalMarksEntryTO internalMarksEntryTO) throws Exception {
		objform.setSubjectCode(internalMarksEntryTO.getSubjectCode());
		objform.setSubjectName(internalMarksEntryTO.getSubjectName());
		objform.setExamName(internalMarksEntryTO.getExamName());
		objform.setClassName(internalMarksEntryTO.getClassName());
		objform.setCourseId(internalMarksEntryTO.getCourseId());
		objform.setSchemeNo(internalMarksEntryTO.getSchemeNo());
		objform.setSubjectId(String.valueOf(internalMarksEntryTO.getSubId()));
		objform.setExamId(String.valueOf(internalMarksEntryTO.getExamId()));
		objform.setClassId(String.valueOf(internalMarksEntryTO.getClassId()));
		objform.setBatchId(String.valueOf(internalMarksEntryTO.getBatchId()));
		objform.setBatchName(internalMarksEntryTO.getBatchName());
		objform.setStudentList(internalMarksEntryTO.getMarksList());
		objform.setClassNames(internalMarksEntryTO.getClassName());
		Double maxMark = internalMarksEntryHandler.getMaxMarkOfSubject(objform);
		if (maxMark != null)
			objform.setMaxMarks(maxMark.toString());
		else
			objform.setMaxMarks(null);

	}
	/** These Action  method is used to save the marks entered by the teacher into the database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveInternalExamMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewInternalMarksEntryForm objform = (NewInternalMarksEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objform.validate(mapping, request);
		validateMaxMarks(objform, errors);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated = internalMarksEntryHandler .saveInternalMarks(objform);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError( "knowledgepro.admin.addsuccess", "Marks"));
					saveMessages(request, messages);
					objform.resetFields();
					/*
					 * if(objform.isForTeachers()){
					 * internalMarksEntryHandler.setRequiredDataToForm(objform);
					 * }else{ setInternalMarksEntryDataToForm(objform); }
					 */
					setInternalMarksEntryDataToForm(objform);
				} else {
					errors.add(CMSConstants.ERROR, new ActionError( "kknowledgepro.admin.addfailure", "Marks"));
					addErrors(request, errors);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			// setRequiredDatatoForm(newExamMarksEntryForm, request);
			return mapping .findForward(CMSConstants.NEW_INTERNAL_MARKS_ENTRY_RESULT);
		}
		return mapping .findForward(CMSConstants.NEW_INTERNAL_MARKS_ENTRY);
	}


	/** These method is used to validate the marks entered by the teachers.
	 * @param objform
	 * @param errors
	 * @throws Exception
	 */
	private void validateMaxMarks(NewInternalMarksEntryForm objform,
			ActionErrors errors) throws Exception {
		Double maxMark = internalMarksEntryHandler.getMaxMarkOfSubject(objform);
		if (maxMark == null) {
			errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.exam.max.marks.notDefined"));
		} else {
			List<StudentMarksTO> list = objform.getStudentList();
			Iterator<StudentMarksTO> itr = list.iterator();
			String reg = "";
			String regValid = "";
			List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
			boolean err = false;
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
				/*
				 * if(to.getIsTheory()){ if(to.getTheoryMarks()==null ||
				 * to.getTheoryMarks().isEmpty()){ err=true; } }else
				 * if(to.getIsPractical()){ if(to.getPracticalMarks()==null ||
				 * to.getPracticalMarks().isEmpty()){ err=true; } }
				 */

				if ((to.getIsTheory() && to.getTheoryMarks() != null && !to
						.getTheoryMarks().isEmpty())) {
					if (!StringUtils.isNumeric(to.getTheoryMarks())) {
						if (examAbscentCode != null && !examAbscentCode.contains(to .getTheoryMarks())) {
							if (regValid.isEmpty()) {
								regValid = to.getRegisterNo();
							} else {
								regValid = regValid + "," + to.getRegisterNo();
							}
						}
					} else {
						double marks = Double.parseDouble(to.getTheoryMarks());
						if (marks > maxMark) {
							if (reg.isEmpty()) {
								reg = to.getRegisterNo();
							} else {
								reg = reg + "," + to.getRegisterNo();
							}
						}
					}

				} else if (to.getIsPractical()
						&& to.getPracticalMarks() != null && !to.getPracticalMarks().isEmpty()) {
					if (!StringUtils.isNumeric(to.getPracticalMarks())) {
						if (examAbscentCode != null
								&& !examAbscentCode.contains(to .getPracticalMarks())) {
							if (regValid.isEmpty()) {
								regValid = to.getRegisterNo();
							} else {
								regValid = regValid + "," + to.getRegisterNo();
							}
						}
					} else {
						double marks = Double.parseDouble(to .getPracticalMarks());
						if (marks > maxMark) {
							if (reg.isEmpty()) {
								reg = to.getRegisterNo();
							} else {
								reg = reg + "," + to.getRegisterNo();
							}
						}
					}
				}
			}
			if (err) {
				errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.exam.marks.defined"));
			}
			if (!reg.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.exam.max.marks.registerNo", reg));
			}
			if (!regValid.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.exam.max.marks.registerNo.validMarks", regValid));
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
	 */
	public ActionForward printReportInternalMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewInternalMarksEntryForm objform = (NewInternalMarksEntryForm) form;
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		String mode="";
		if(session.getAttribute("Theory") != null)
			mode = session.getAttribute("Theory").toString();
		Iterator<StudentMarksTO> itr =objform.getStudentList().iterator();
		while (itr.hasNext()) {
			StudentMarksTO studentMarksTO = (StudentMarksTO) itr.next();
			if(mode.equalsIgnoreCase("Theory")){
			if(studentMarksTO.getTheoryMarks() != null && !studentMarksTO.getTheoryMarks().equalsIgnoreCase(studentMarksTO.getOldTheoryMarks())){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.internal.theory.mark.print"));
				saveErrors(request, errors);
				break;
		       }
			}else{
				if(studentMarksTO.getPracticalMarks() != null && !studentMarksTO.getPracticalMarks().equalsIgnoreCase(studentMarksTO.getOldPracticalMarks())){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.internal.theory.mark.print"));
					saveErrors(request, errors);
					break;
			       }
			}
		}
		if(errors.isEmpty()){
			String mobileNo=internalMarksEntryHandler.getTeacherMobileNoByUserId(session.getAttribute("uid").toString());
			if(mobileNo!=null && !mobileNo.isEmpty()){
				objform.setTeacherMobileNo(mobileNo);
			}else{
				objform.setTeacherMobileNo(null);
			}
			objform.setPrintConfirmation("Yes");
		}else{
			objform.setPrintConfirmation("No");
		}
		/*session.setAttribute("ExamId", request.getParameter("examId"));
		session.setAttribute("SubjectId",request.getParameter("subjectId"));
		session.setAttribute("ClassesId",request.getParameter("classId"));
		session.setAttribute("UserId", session.getAttribute("uid"));
		String batchId = request.getParameter("batchId");
		if(batchId!=null && !batchId.isEmpty() && !batchId.equalsIgnoreCase("0")){
			session.setAttribute("BatchId",request.getParameter("batchId"));
		}else{
			session.setAttribute("BatchId",0);
		}*/
		return mapping.findForward(CMSConstants.NEW_INTERNAL_MARKS_ENTRY_RESULT);
	}
	
	public ActionForward printReportInternalMarksAfterConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering the printReportInternalMarksAfterConfirm");
		NewInternalMarksEntryForm objform = (NewInternalMarksEntryForm) form;
		Date date=new Date();
		objform.setCurrentDate(CommonUtil.formatDates(date));
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_REPORT_PRINT_AFTER_CONFIRM);
	}
}
