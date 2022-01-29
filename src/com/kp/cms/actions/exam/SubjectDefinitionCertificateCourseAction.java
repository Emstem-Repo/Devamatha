package com.kp.cms.actions.exam;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.SubjectDefinitionCertificateCourseHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO;

@SuppressWarnings("deprecation")
public class SubjectDefinitionCertificateCourseAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	SubjectDefinitionCertificateCourseHandler handler = new SubjectDefinitionCertificateCourseHandler();

	public ActionForward initSubDefCertificateCourseWise(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		resetForm(objform,request);
		objform.resetFields();
		return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
	}

	public ActionForward subDefCertificateCourseWiseCancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		selectCheckedUnchecked(objform);
		return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
	}
	private void resetForm(ExamSubjectDefCourseForm objform,
			HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		session.removeAttribute("EditTO");
		setUserId(request, objform);
		setProgramtypelist(objform); 
		objform.setCourse(null);
		objform.setProgramTypeId(null);
		objform.setProgramId(null);
		objform.setScheme(null);
		objform.setSchemeType(null);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchPhdDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	   throws Exception{
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		ActionErrors errors=objform.validate(mapping, request);
		if(errors.isEmpty()){
	      try {
		  objform.setListSubjects(null);
		  setDataToList(objform,errors);
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
		  }
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
	}
	/**
	 * @param objform
	 * @param errors
	 * @throws Exception
	 */
	private void setDataToList(ExamSubjectDefCourseForm objform,ActionErrors errors) throws Exception{
		   List<ExamSubDefinitionCourseWiseTO> subRulList=handler.searchSubCerDetails(objform);
		   if(subRulList!=null && !subRulList.isEmpty()){
		   objform.setListSubjects(subRulList);
		   objform.setCountcheck(subRulList.size());
		   }else{
			    errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
		   }
	}
	private ExamSubjectDefCourseForm retainValues(
			ExamSubjectDefCourseForm objform,HttpServletRequest request) {
		objform = handler.retainAllValues(objform,request);
		return objform;
	}

	public ActionForward setExamUnvSubCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);

		saveErrors(request, errors);
		setUserId(request, objform);
		HttpSession session = request.getSession(false);
		 int schemeNo = 0;
		 int schemeId = 0;
		 int academicYear = 0;
		 int courseId = 0;
	if (errors.isEmpty()) {
		 Iterator<ExamSubDefinitionCourseWiseTO> itr=objform.getListSubjects().iterator();
		 boolean flag=false;
		 while (itr.hasNext()) {
			
			ExamSubDefinitionCourseWiseTO subjectRuleTo = (ExamSubDefinitionCourseWiseTO) itr.next();
			if(subjectRuleTo.getChecked()!=null && !subjectRuleTo.getChecked().isEmpty()){
				flag=true;
				handler.getSubjectsForInput(subjectRuleTo.getSubjectId(),objform);
				if(objform.getAllDatas()!=null && objform.getAllDatas().size()<=0){
					errors.add("error", new ActionError("knowledgepro.exam.subject.certificate.course.notDefined",subjectRuleTo.getCerCourseName()));
					selectCheckedUnchecked(objform);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
				}
				Iterator<Object[]> irr=objform.getAllDatas().iterator();
				while (irr.hasNext()) {
					Object[] values =(Object[])irr.next();
					objform.setCourseId(values[0].toString());
					schemeNo=Integer.parseInt(values[1].toString());
					schemeId=Integer.parseInt(values[4].toString());
					//courseName=values[3].toString();
					
					academicYear = (objform.getAcademicYear() != null
							&& objform.getAcademicYear().trim().length() > 0 ? Integer
							.parseInt(objform.getAcademicYear()) : 0);
					courseId = (objform.getCourseId() != null
							&& objform.getCourseId().trim().length() > 0 ? Integer
							.parseInt(objform.getCourseId()) : 0);

					if (session.getAttribute("EditTO") == null) {
						ExamSubDefinitionCourseWiseTO subdefTO = new ExamSubDefinitionCourseWiseTO();
						subdefTO.setAcademicYear(academicYear);
						subdefTO.setSchemeId(schemeId);
						subdefTO.setSchemeNo(schemeNo);
						subdefTO.setCourseId(courseId);
						session.setAttribute("EditTO", subdefTO);
					}
					String isTheoryOrPractical =handler.checkIfTheoryOrPractical(subjectRuleTo.getSubjectId());

					if (isTheoryOrPractical != null && isTheoryOrPractical.length() > 0
							&& isTheoryOrPractical.equalsIgnoreCase("t")) {
						objform.setIsTheoryOrPractical("t");

					} else if (isTheoryOrPractical != null
							&& isTheoryOrPractical.length() > 0
							&& isTheoryOrPractical.equalsIgnoreCase("p")) {
						objform.setIsTheoryOrPractical("p");
					} else {
						objform.setIsTheoryOrPractical("b");
					}
					objform.setListSubjectSection(handler.getSubjectSectioneList());
		//			handler.ViewCertificateCourse(courseId, schemeId, schemeNo, academicYear,objform,subjectRuleTo.getSubjectId());
				}
			}
		}if(!flag){
			 errors.add("error", new ActionError("knowledgepro.exam.subjectrule.not.selected"));
			 saveErrors(request, errors);
			 return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
		  }	objform.setAcademicYear(objform.getAcademicYear());
			objform.setAcademicYear_value(handler.getacademicYear(academicYear));
			objform.setCourseName(handler.getCourseName(courseId));
			objform.setSchemeName(Integer.toString(schemeNo));
			objform.setSchemeId(Integer.toString(schemeId));
			objform.setSchemeNo(Integer.toString(schemeNo));
			objform.setCourseId(objform.getCourseId());

			if (objform.getListSubjects().size() == 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
//				setprogramMapToRequest(objform, request);
//				retainValues(objform,request);
				resetForm(objform, request);
				return mapping
						.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
			}

			return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE_RESULT);

		} else {

			objform = retainValues(objform,request);
			objform.setAcademicYear(objform.getAcademicYear());
			setprogramMapToRequest(objform, request);
			return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);
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
	public ActionForward submitEdittedData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSubjectDefCourseForm objform = (ExamSubjectDefCourseForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String subjectOrder = null;
		int schemeNo = 0;
		int schemeId = 0;
		int academicYear = 0;
		int courseId = 0;
		try {

			if (errors.isEmpty()) {
				HttpSession session = request.getSession(false);
				 Iterator<ExamSubDefinitionCourseWiseTO> itr=objform.getListSubjects().iterator();
				 boolean flag=false;
				 while (itr.hasNext()) {
					
					ExamSubDefinitionCourseWiseTO subjectRuleTo = (ExamSubDefinitionCourseWiseTO) itr.next();
					if(subjectRuleTo.getChecked()!=null && !subjectRuleTo.getChecked().isEmpty()){
						flag=true;
						handler.getSubjectsForInput(subjectRuleTo.getSubjectId(),objform);
						
						Iterator<Object[]> irr=objform.getAllDatas().iterator();
						while (irr.hasNext()) {
							Object[] values =(Object[])irr.next();
							objform.setCourseId(values[0].toString());
							schemeNo=Integer.parseInt(values[1].toString());
							schemeId=Integer.parseInt(values[4].toString());
							//courseName=values[3].toString();
							
							academicYear = (objform.getAcademicYear() != null
									&& objform.getAcademicYear().trim().length() > 0 ? Integer
									.parseInt(objform.getAcademicYear()) : 0);
							courseId = (objform.getCourseId() != null
									&& objform.getCourseId().trim().length() > 0 ? Integer
									.parseInt(objform.getCourseId()) : 0);

				if (session.getAttribute("EditTO") == null) {
					ExamSubDefinitionCourseWiseTO subdefTO = new ExamSubDefinitionCourseWiseTO();
					subdefTO.setAcademicYear(academicYear);
					subdefTO.setSchemeId(schemeId);
					subdefTO.setSchemeNo(schemeNo);
					subdefTO.setCourseId(courseId);
					session.setAttribute("EditTO", subdefTO);
				}
				subjectOrder = objform.getSubjectOrder();
				int tempSchemeNo = 0;
				if (session.getAttribute("EditTO") != null) {
					ExamSubDefinitionCourseWiseTO subdefTOFromSession = (ExamSubDefinitionCourseWiseTO) session
							.getAttribute("EditTO");
					tempSchemeNo = subdefTOFromSession.getSchemeNo();
				}
				
				handler.UpdateCertificateCourse(courseId, schemeId, schemeNo, academicYear,objform,subjectRuleTo.getSubjectId());
				resetForm(objform,request);
				if (session.getAttribute("EditTO") != null) {
					ExamSubDefinitionCourseWiseTO subdefTOFromSession = (ExamSubDefinitionCourseWiseTO) session
							.getAttribute("EditTO");
					academicYear = subdefTOFromSession.getAcademicYear();
					schemeNo = subdefTOFromSession.getSchemeNo();
					schemeId = subdefTOFromSession.getSchemeId();
					courseId = subdefTOFromSession.getCourseId();
					objform.setAcademicYear_value(handler
							.getacademicYear(academicYear));
					objform.setCourseName(handler.getCourseName(courseId));
					objform.setSchemeName(Integer.toString(schemeNo));
					objform.setCourseId(Integer.toString(courseId));

			      	}
				}
			 }
		   }
				 ActionMessage message = new ActionMessage("knowledgepro.exam.subjectDefinitionCourseWise.success");
					errors.add("messages", message);
					saveMessages(request, errors);
					 objform.resetFields();
					resetForm(objform,request);
		return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE);

		 }

		}

		catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.subject.exists"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE_RESULT);
		}catch (Exception e1) {
			errors.add("error", new ActionError("knowledgepro.exam.subject.Adding.Errors"));
	       saveErrors(request, errors);
	       return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE_RESULT);
         }
/*	//	ExamSubDefinitionCourseWiseTO to = handler.createFormObjcet(objform);
	//	objform.setSubjectName(to.getSubjectName());
	//	objform.setSubjectCode(to.getSubjectCode());
		objform.setListSubjectSection(handler.getSubjectSectioneList());
		String isTheoryOrPractical = handler.checkIfTheoryOrPractical(to
				.getSubjectId());

		if (isTheoryOrPractical != null && isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("t")) {
			objform.setIsTheoryOrPractical("t");

		} else if (isTheoryOrPractical != null
				&& isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("p")) {
			objform.setIsTheoryOrPractical("p");
		} else {
			objform.setIsTheoryOrPractical("b");
		}*/

		return mapping.findForward(CMSConstants.SUB_DEF_CERTIFICATE_COURSE_WISE_RESULT);

	}
	/**
	 * 
	 * @param examSubjectDefCourseForm
	 */
	public void setprogramMapToRequest(ExamSubjectDefCourseForm examSubjectDefCourseForm, HttpServletRequest request) {
		if (examSubjectDefCourseForm.getProgramTypeId() != null && (!examSubjectDefCourseForm.getProgramTypeId().isEmpty())) {
			 Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(examSubjectDefCourseForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
			//examSubjectDefCourseForm.setProgramList(programMap);
		}
	}
	/**
	 * 
	 * @param examSubjectDefCourseForm
	 * @throws Exception
	 */
	public void setProgramtypelist(ExamSubjectDefCourseForm examSubjectDefCourseForm) throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		if (programTypeList != null) {
			examSubjectDefCourseForm.setProgramTypeList(programTypeList);
		}
	}
	
	

	private void selectCheckedUnchecked(ExamSubjectDefCourseForm objform) {
		Iterator<ExamSubDefinitionCourseWiseTO> itr=objform.getListSubjects().iterator();
        while (itr.hasNext()) {
        	ExamSubDefinitionCourseWiseTO subjectRuleTo = (ExamSubDefinitionCourseWiseTO) itr.next();
	    if(subjectRuleTo.getChecked()!=null && !subjectRuleTo.getChecked().isEmpty()){
	       subjectRuleTo.setTempChecked("on");
	       subjectRuleTo.setChecked(null);
	    }else{
	    	subjectRuleTo.setTempChecked(null);
		    subjectRuleTo.setChecked(null);
	       }
        }		
	}
}
