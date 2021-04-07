package com.kp.cms.actions.exam;

import java.util.Collections;
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
import com.kp.cms.forms.exam.InternalMarksEntryForm;
import com.kp.cms.handlers.exam.InternalMarksEntryHandler;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.StudentMarksTO;

/**
 * @author Nagarjun
 *
 */
@SuppressWarnings("deprecation")
public class InternalMarksEntryAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(InternalMarksEntryAction.class);
	InternalMarksEntryHandler handler = new InternalMarksEntryHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMarksEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		resetFormData(objform);
		setUserId(request,objform);
		try{
			setDatatoForm(objform,request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY);
	}


	/**
	 * @param objform
	 */
	private void resetFormData(InternalMarksEntryForm objform){
		objform.setTeacherId(null);
		objform.setForTeachers(false);
		objform.setHodView(false);
		objform.setEmpType(null);
		objform.setExamMap(null);
		objform.setExamDeatails(null);
		objform.setPracticalDeatails(null);
	}


	/**
	 * @param objform
	 * @param request
	 */
	private void setDatatoForm(InternalMarksEntryForm objform,
			HttpServletRequest request) throws Exception{
		 handler.getCurrentExamDetails(objform);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		HttpSession session=request.getSession();
		session.setAttribute("Theory", "Theory");
		try{
			List<InternalMarksEntryTO> examDetails = objform.getExamDeatails();
			objform.setStudentList(null);
			objform.setBatchName(null);
			if(examDetails != null){
				Iterator<InternalMarksEntryTO> iterator = examDetails.iterator();
				while (iterator.hasNext()) {
					InternalMarksEntryTO internalMarksEntryTO =  iterator.next();
					/* code added by sudhir*/
					if((!objform.getBatchId().equalsIgnoreCase("0") && !String.valueOf(internalMarksEntryTO.getBatchId()).equalsIgnoreCase("0")
							&& objform.getBatchId().equalsIgnoreCase(String.valueOf(internalMarksEntryTO.getBatchId())))
							&& objform.getExamId().equalsIgnoreCase(String.valueOf(internalMarksEntryTO.getExamId()))){
						List<StudentMarksTO> marksList = internalMarksEntryTO.getMarksList();
						objform.setSubjectType("1");
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
						objform.setStudentList(marksList);
						Double maxMark=handler.getMaxMarkOfSubject(objform);
						if(maxMark != null)
							objform.setMaxMarks(maxMark.toString());
						else
							objform.setMaxMarks(null);
						objform.setBatchName(internalMarksEntryTO.getBatchName());
					}else if(objform.getBatchId().equalsIgnoreCase("0")){
						/* code added by sudhir*/
						if(objform.getSubjectId() != null && objform.getClassId() != null && objform.getExamId() != null){
							if(internalMarksEntryTO.getSubId() == Integer.parseInt(objform.getSubjectId()) && 
									internalMarksEntryTO.getClassId() == Integer.parseInt(objform.getClassId()) && 
									internalMarksEntryTO.getExamId() == Integer.parseInt(objform.getExamId())){
								List<StudentMarksTO> marksList = internalMarksEntryTO.getMarksList();
								objform.setSubjectType("1");
								objform.setSubjectCode(internalMarksEntryTO.getSubjectCode());
								objform.setSubjectName(internalMarksEntryTO.getSubjectName());
								objform.setExamName(internalMarksEntryTO.getExamName());
								objform.setClassName(internalMarksEntryTO.getClassName());
								objform.setCourseId(internalMarksEntryTO.getCourseId());
								objform.setSchemeNo(internalMarksEntryTO.getSchemeNo());
								objform.setSubjectId(String.valueOf(internalMarksEntryTO.getSubId()));
								objform.setExamId(String.valueOf(internalMarksEntryTO.getExamId()));
								objform.setClassId(String.valueOf(internalMarksEntryTO.getClassId()));
								Double maxMark=handler.getMaxMarkOfSubject(objform);
								if(maxMark != null)
									objform.setMaxMarks(maxMark.toString());
								else
									objform.setMaxMarks(null);
								/* code added by sudhir*/
								objform.setBatchId(String.valueOf(internalMarksEntryTO.getBatchId()));
								/* -------------------*/
								objform.setStudentList(marksList);
							}
						}
					}
				}
			}
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY_RESULT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewStudentDetailsPractical(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		try{
			List<InternalMarksEntryTO> examDetails = objform.getPracticalDeatails();
			objform.setStudentList(null);
			HttpSession session=request.getSession();
			if(examDetails != null){
				Iterator<InternalMarksEntryTO> iterator = examDetails.iterator();
				while (iterator.hasNext()) {
					InternalMarksEntryTO internalMarksEntryTO =  iterator.next();
					if(objform.getSubjectId() != null && objform.getClassId() != null && objform.getExamId() != null && objform.getBatchId()!=null){
						if(internalMarksEntryTO.getSubId() == Integer.parseInt(objform.getSubjectId()) && 
								internalMarksEntryTO.getClassId() == Integer.parseInt(objform.getClassId()) && 
								internalMarksEntryTO.getExamId() == Integer.parseInt(objform.getExamId()) && internalMarksEntryTO.getBatchId()==Integer.parseInt(objform.getBatchId())){
							List<StudentMarksTO> marksList = internalMarksEntryTO.getMarksList();
							objform.setSubjectType("0");
							objform.setSubjectCode(internalMarksEntryTO.getSubjectCode());
							objform.setSubjectName(internalMarksEntryTO.getSubjectName());
							objform.setExamName(internalMarksEntryTO.getExamName());
							objform.setClassName(internalMarksEntryTO.getClassName());
							objform.setCourseId(internalMarksEntryTO.getCourseId());
							objform.setBatchName(internalMarksEntryTO.getBatchName());
							objform.setSchemeNo(internalMarksEntryTO.getSchemeNo());
							objform.setSubjectId(String.valueOf(internalMarksEntryTO.getSubId()));
							objform.setExamId(String.valueOf(internalMarksEntryTO.getExamId()));
							objform.setClassId(String.valueOf(internalMarksEntryTO.getClassId()));
							if(marksList != null)
								Collections.sort(marksList);
							objform.setStudentList(marksList);
							session.setAttribute("marksList", marksList);
							Double maxMark=handler.getMaxMarkOfSubject(objform);
							if(maxMark != null)
								objform.setMaxMarks(maxMark.toString());
							else
								objform.setMaxMarks(null);
						}
					}
				}
			}
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY_RESULT);
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered InternalMarksEntryAction - saveMarks");
		
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = objform.validate(mapping, request);
		setUserId(request,objform);
		validateMaxMarks(objform,errors);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated =handler.saveMarks(objform);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Marks"));
					saveMessages(request, messages);
					objform.resetFields();
					if(objform.isForTeachers()){
						handler.setRequiredDataToForm(objform);
					}else{
						setDatatoForm(objform,request);
					}
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Marks"));
					addErrors(request, errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
//			setRequiredDatatoForm(newExamMarksEntryForm, request);
			log.info("Exit InternalMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY_RESULT);
		}
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY);
	}
	/**
	 * @param newExamMarksEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateMaxMarks(InternalMarksEntryForm objform,ActionErrors errors) throws Exception{
		Double maxMark=handler.getMaxMarkOfSubject(objform);
		if(maxMark==null){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
		}else{
		List<StudentMarksTO> list=objform.getStudentList();
		Iterator<StudentMarksTO> itr=list.iterator();
		String reg="";
		String regValid="";
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		boolean err=false;
		while (itr.hasNext()) {
			StudentMarksTO to = (StudentMarksTO) itr.next();
		/*	if(to.getIsTheory()){
				if(to.getTheoryMarks()==null || to.getTheoryMarks().isEmpty()){
					err=true;
				}
			}else if(to.getIsPractical()){
				if(to.getPracticalMarks()==null || to.getPracticalMarks().isEmpty()){
					err=true;
				}
			}*/
			
			if((to.getIsTheory() && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty())){
				if(!StringUtils.isNumeric(to.getTheoryMarks())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getTheoryMarks())){
						if(regValid.isEmpty()){
							regValid=to.getRegisterNo();
						}else{
							regValid=regValid+","+to.getRegisterNo();
						}
					}
				}else{
					double marks=Double.parseDouble(to.getTheoryMarks());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getRegisterNo();
						}else{
							reg=reg+","+to.getRegisterNo();
						}
					}
				}
				
			}else if(to.getIsPractical() && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty()){
				if(!StringUtils.isNumeric(to.getPracticalMarks())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getPracticalMarks())){
						if(regValid.isEmpty()){
							regValid=to.getRegisterNo();
						}else{
							regValid=regValid+","+to.getRegisterNo();
						}
					}
				}else{
					double marks=Double.parseDouble(to.getPracticalMarks());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getRegisterNo();
						}else{
							reg=reg+","+to.getRegisterNo();
						}
					}
				}
			}
		}
		if(err){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.marks.defined"));
		}
		if(!reg.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.registerNo",reg));
		}
		if(!regValid.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.registerNo.validMarks",regValid));
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
	public ActionForward initMarksEntryForTeachers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		setUserId(request,objform);
		objform.setHodView(false);
		try{
			handler.setDataTOForm(objform);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CIA_TEACHER_MARK_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewExamDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request,objform);
		try{
			if(objform.getTeacherId() == null || objform.getTeacherId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.marks.entry.teacher.required"));
				saveErrors(request, errors);
			}
			if(errors.isEmpty()){
				handler.setRequiredDataToForm(objform);
			}else{
				return mapping.findForward(CMSConstants.CIA_TEACHER_MARK_ENTRY);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkModifiedTheory (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
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
				return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY_RESULT);
		       }
			}else{
				if(studentMarksTO.getPracticalMarks() != null && !studentMarksTO.getPracticalMarks().equalsIgnoreCase(studentMarksTO.getOldPracticalMarks())){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.internal.theory.mark.print"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY_RESULT);
			       }
			}
		}
		session.setAttribute("ExamId", request.getParameter("examId"));
		session.setAttribute("SubjectId",request.getParameter("subjectId"));
		session.setAttribute("ClassesId",request.getParameter("classId"));
		if(objform.getEmpType()!=null && objform.getEmpType().equalsIgnoreCase("HOD")){
			session.setAttribute("UserId", objform.getEmpId());
		}else{
			session.setAttribute("UserId", session.getAttribute("uid"));
		}
		/*code added by sudhir */
		String batchId = request.getParameter("batchId");
		if(batchId!=null && !batchId.isEmpty() && !batchId.equalsIgnoreCase("0")){
			session.setAttribute("BatchId",request.getParameter("batchId"));
		}else{
			
			session.setAttribute("BatchId",0);
		}
		/*-------------------- */
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_REPORT_PRINT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHodView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		objform.setEmpId(null);
		setUserId(request,objform);
		try{
			handler.getInputDetails(objform);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CIA_MARK_HOD_VIEW);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTeacherExamDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InternalMarksEntryForm objform = (InternalMarksEntryForm) form;
		ActionErrors errors = new ActionErrors();
		objform.setExamMap(null);
		setUserId(request,objform);
		try{
			if(objform.getEmpId() == null || objform.getEmpId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.marks.entry.teacher.required"));
				saveErrors(request, errors);
			}
			if(errors.isEmpty()){
				handler.setTeacherDataToForm(objform);
			}else{
				return mapping.findForward(CMSConstants.CIA_TEACHER_MARK_ENTRY);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objform.setErrorMessage(msg);
			objform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_ENTRY_HOD_VIEW);
	}
}