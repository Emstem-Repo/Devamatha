package com.kp.cms.actions.admission;

import java.util.Calendar;
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
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.forms.admission.StudentCertificateCourseForm;
import com.kp.cms.handlers.admission.StudentCertificateCourseHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.to.admission.StudentCertificateCourseTO;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.transactionsimpl.admission.CertificateCourseEntryTxnImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class StudentCertificateCourseAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CertificateCourseEntryTxnImpl.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCertificateCourses(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		HttpSession session = request.getSession();
		try {
			courseForm.setPrintCourse(null);
			String studentid =  session.getAttribute("studentId").toString();
			initCertificateCourses(studentid, courseForm);
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE);
		
	}
	public ActionForward showStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		String studentid = courseForm.getStudendId();
		if(studentid==null || studentid.isEmpty())
			studentid=request.getSession().getAttribute("studentId").toString();
		int cid=courseForm.getCertificateCourseId();
		if(cid==0)
			cid=Integer.parseInt(request.getSession().getAttribute("certifiedId").toString());
		try {
			setUserId(request, courseForm);
			StudentTO studentTO = StudentCertificateCourseHandler.getInstance().getStudentDetails(Integer.parseInt(studentid));
			courseForm.setStudentTO(studentTO);
			CertificateCourseEntryForm certificateCourseEntryForm= new CertificateCourseEntryForm();
			certificateCourseEntryForm.setId(cid);
			ICertificateCourseEntryTxn transaction=CertificateCourseEntryTxnImpl.getInstance();
			CertificateCourse bo=transaction.editCertificateCourse(certificateCourseEntryForm);
//			List<CertificateCourseTeacherTO> teacherList = StudentCertificateCourseHandler.getInstance().getCertificateCourseTeacher(courseForm.getCertificateCourseId());
//			if(teacherList!= null && teacherList.size() > 0){
//				courseForm.setCertificateCourseTeacherTO(teacherList.get(0));
//			}
			CertificateCourseTeacherTO to=new CertificateCourseTeacherTO();
			to.setTeacherName(bo.getUsers().getUserName());
			courseForm.setCertificateCourseTeacherTO(to);
			courseForm.setStudendId(null);
			/* setting certificateCourseName to the form by sudhir */
			courseForm.setCertificateCourseName(bo.getCertificateCourseName());
			/* --------------------*/
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_PRINT);
		
	}
	
	public ActionForward saveCertificateCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		String studentid = courseForm.getStudendId();
		String regNo = courseForm.getRegisterNO();
		Integer schemeNo= Integer.parseInt(courseForm.getSemester());
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request,courseForm);//setting the userId to Form
		try {
			List<CertificateCourseTO> mandatoryCourseTOList = courseForm.getMandatorycourseList();
			boolean isSelected = false;
			if(mandatoryCourseTOList!= null && mandatoryCourseTOList.size() > 0){
				Iterator<CertificateCourseTO> itr = mandatoryCourseTOList.iterator();
				while (itr.hasNext()) {
					CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
							.next();
					if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
						continue;
					}
					courseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
					courseForm.setCertificateCourseId(certificateCourseTO.getId());
					isSelected = true;
				}
			}
			
			if(isSelected){
				if(courseForm.getOptionalCourseId()!=null && !courseForm.getOptionalCourseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.admission.optionalOrMandatory"));
					saveErrors(request, errors);
					setCertificateCourses(studentid, courseForm);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
				}
			}else{
				if(courseForm.getOptionalCourseId()!=null && !courseForm.getOptionalCourseId().isEmpty()){
					courseForm.setCertificateCourseId(Integer.parseInt(courseForm.getOptionalCourseId()));
					isSelected=true;
				}
			}
			if(!isSelected){
				errors.add("error", new ActionError("knowledgepro.admission.no.certificate.course.selected"));
				saveErrors(request, errors);
				setCertificateCourses(studentid, courseForm);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
			}
			Integer CurrentSchemeNo = StudentCertificateCourseHandler.getInstance().getSchemeNoByStudentId(Integer.parseInt(studentid));
			//if(schemeNo == CurrentSchemeNo)
			//{
			if(schemeNo < CurrentSchemeNo)
		    {
				errors.add("error", new ActionError("knowledgepro.admission.certificate.course.cannot.apply.previous",regNo));
				saveErrors(request, errors);
				setCertificateCourses(studentid, courseForm);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
		     }
			if(StudentCertificateCourseHandler.getInstance().isAppliedForSemester(Integer.parseInt(studentid),schemeNo))
				{
					errors.add("error", new ActionError("knowledgepro.admission.certificate.course.already.applied.reg",regNo));
					saveErrors(request, errors);
					setCertificateCourses(studentid, courseForm);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
				}
			//}
			if(CurrentSchemeNo%2!=0){
				if(schemeNo==(CurrentSchemeNo+1))
				{
					if(StudentCertificateCourseHandler.getInstance().isAppliedForSemester(Integer.parseInt(studentid),schemeNo)){
						errors.add("error", new ActionError("knowledgepro.admission.certificate.course.already.applied.reg",regNo));
						saveErrors(request, errors);
						setCertificateCourses(studentid, courseForm);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
					}
				}
			    else if(schemeNo>(CurrentSchemeNo+1))
			    {
					errors.add("error", new ActionError("knowledgepro.admission.certificate.course.cannot.apply",regNo));
					saveErrors(request, errors);
					setCertificateCourses(studentid, courseForm);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
			     }
			}
			boolean isAdded = false;
			boolean count = StudentCertificateCourseHandler.getInstance().studentCertificateCourseCount(courseForm, schemeNo);
			if(count){
				isAdded= StudentCertificateCourseHandler.getInstance().saveCertificateCourse(courseForm, Integer.parseInt(studentid), schemeNo);
			}else{
				errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
				saveErrors(request, errors);
				setCertificateCourses(studentid, courseForm);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
			}
			String print=courseForm.getPrintCourse();
			courseForm.resetFields();
			if(isAdded){
				ActionMessage message = new ActionMessage("knowledgepro.adminssion.student.certificate.course.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				courseForm.setPrintCourse(print);
			}
		
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showTeacherDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		try { 
			List<CertificateCourseTeacherTO> teacherList = StudentCertificateCourseHandler.getInstance().getCertificateCourseTeacher(courseForm.getCertificateCourseId());
			request.setAttribute("teacherList", teacherList);
			Double feeAmt = StudentCertificateCourseHandler.getInstance().getFeeAmount(courseForm.getCertificateCourseId());
			courseForm.setFeeAmount(feeAmt.toString());
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.SHOW_CERTIFICATE_COURSE_DETAILS);
		
	}
	/**
	 * 
	 * @param studentId
	 * @param courseForm
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void initCertificateCourses(String studentid, StudentCertificateCourseForm courseForm) throws NumberFormatException, Exception{
		Program program = StudentCertificateCourseHandler.getInstance().getProgramByStudentId(Integer.parseInt(studentid));
		List<Integer> appliedList = StudentCertificateCourseHandler.getInstance().getStudentCertificateCourseDetails(Integer.parseInt(studentid));
		if(program!= null){
			courseForm.setMandatorycourseList(StudentCertificateCourseHandler.getInstance().
						getCertificateMandatoryCourses(program.getProgramType().getId(),
								program.getId(), program.getStream(), appliedList));
			courseForm.setOptionalCourseList(StudentCertificateCourseHandler.getInstance().
					getCertificateOptionalCourses(program.getProgramType().getId(),
							program.getId(), program.getStream(), appliedList));
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
	public ActionForward initStudentCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		courseForm.resetFields();
		
		
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentCertificateCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		 ActionErrors errors = courseForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
				courseForm.setStudendId(null);
				String studentId=StudentCertificateCourseHandler.getInstance().getCertificateCourseStudentId(courseForm.getRegisterNO());
				if(studentId!=null && !studentId.isEmpty()){
					setCertificateCourses(studentId, courseForm);
					courseForm.setStudendId(studentId);
					String regNo= courseForm.getRegisterNO();
					Integer schemeNo=Integer.parseInt(courseForm.getSemester());
					Integer CurrentSchemeNo = StudentCertificateCourseHandler.getInstance().getSchemeNoByStudentId(Integer.parseInt(studentId));
					
					if(schemeNo< CurrentSchemeNo)
				    {
						errors.add("error", new ActionError("knowledgepro.admission.certificate.course.cannot.apply.previous",regNo));
						saveErrors(request, errors);
						setCertificateCourses(studentId, courseForm);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
				     }
					if(schemeNo>(CurrentSchemeNo+1))
				    {
						errors.add("error", new ActionError("knowledgepro.admission.certificate.course.cannot.apply",regNo));
						saveErrors(request, errors);
						setCertificateCourses(studentId, courseForm);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
				     }
					
				}else{
					errors.add("errors",  new ActionError("knowledgepro.admin.regno.certificate.course.valid"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
			}
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_PAGE1);
		
	}
	/**
	 * @param studentId
	 * @param courseForm
	 * @throws Exception
	 */
	private void setCertificateCourses(String studentId,StudentCertificateCourseForm courseForm) throws Exception{
		StudentCertificateCourseHandler.getInstance().getCertificateCourseAccourdingToStudentId(studentId,courseForm);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCancelledStudentCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;// type casting the Action Form to Required FOrm
		courseForm.resetFields();// Clearing the data from Form for input screen
		return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT);// redireciting to the input jsp
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCancelledStudentCertificateCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;// type casting the Action Form to Required FOrm
		 ActionErrors errors = courseForm.validate(mapping, request);// calling the validation frame work for input data
		try {
			if(errors.isEmpty()){// checking whether any validation messages are there or not .If errors is empty
				courseForm.setStudendId(null);// Clearing the Student Id 
				boolean isValidStudent=StudentCertificateCourseHandler.getInstance().checkRegisterNoIsValid(courseForm.getRegisterNO());
				if(!isValidStudent){// if Register No is not valid add error and redirect to input jsp
					errors.add("errors",  new ActionError("knowledgepro.admin.regno.certificate.course.valid"));//adding the error in errors Object
					saveErrors(request, errors);// saving the errors For displaying in UI
					return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT);// Redirecting to the input JSP
				}
				boolean isContain=StudentCertificateCourseHandler.getInstance().checkStudentContainsCertificateCourse(courseForm);// Checking whether the student is having certificate Course for current semester
				if(!isContain){//If Student not Contain Certificate Course add error msg in error object
					errors.add("errors",  new ActionError("knowledgepro.admission.student.not.contain.certificateCourse"));//adding the error in errors Object
					saveErrors(request, errors);// saving the errors For displaying in UI
					return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT);// Redirecting to the input JSP
				}
						
			}else{// if errors are not empty
				saveErrors(request, errors);// saving the errors For displaying in UI
				return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT);// Redirecting to the input JSP
			}
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT_RESULT);// Redirecting to the second page if student contains Certificate Course
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelledCertificateCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;// type casting the Action Form to Required FOrm
		ActionErrors errors = new ActionErrors();// Creating the ActionErrors Object for adding validation for input Data
		ActionMessages messages=new ActionMessages();// Creating the ActionMessages Object for adding message 
		setUserId(request,courseForm);//setting the userId to Form
		try {
			List<StudentCertificateCourseTO> studentCertificateCourseTOs = courseForm.getStudentCertificateCourse();
			boolean isSelected = false;
			if(studentCertificateCourseTOs!= null && studentCertificateCourseTOs.size() > 0){
				Iterator<StudentCertificateCourseTO> itr = studentCertificateCourseTOs.iterator();
				while (itr.hasNext()) {
					StudentCertificateCourseTO certificateCourseTO = (StudentCertificateCourseTO) itr.next();
					if(certificateCourseTO.getChecked() == null || !certificateCourseTO.getChecked().equalsIgnoreCase("on")){
						continue;
					}
					isSelected = true;
				}
			}
			if(!isSelected){
				errors.add("error", new ActionError("knowledgepro.admission.no.certificate.course.selected"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT_RESULT);
			}
			
			
			
			boolean isUpdated = StudentCertificateCourseHandler.getInstance().updateCertificateCourse(courseForm);
			if(isUpdated){
				ActionMessage message = new ActionMessage("knowledgepro.adminssion.student.certificate.course.cancelled.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				courseForm.resetFields();
			}
			
		} catch (Exception e) {		
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT);
		
	}
	
	public ActionForward getSchemeNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		try {
		String studentId=StudentCertificateCourseHandler.getInstance().getCertificateCourseStudentId(courseForm.getRegisterNO());
		Integer CurrentSchemeNo = StudentCertificateCourseHandler.getInstance().getSchemeNoByStudentId(Integer.parseInt(studentId));
		if(CurrentSchemeNo%2!=0)
			courseForm.setSemester(String.valueOf(CurrentSchemeNo+1));
		else
			courseForm.setSemester(String.valueOf(CurrentSchemeNo));
		//courseForm.setRegisterNO(RegNo);
		}
		
		catch (Exception e) {
			log.error("Error occured in initCurriculumScheme of CurriculumSchemeAction",e);
			String msg = "Register Number is not Valid";
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_STUDENT);
	}
	
	
	public ActionForward initSubjectGroupAssignment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		try {
			courseForm.resetSubGrpAssig();
			Map<Integer, String> courseMap=null;
			
			//String academicYear= academicYear(cal.get(cal.YEAR));	
			//String academicYear= String.valueOf(cal.get(cal.YEAR));	
			int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
			courseForm.setAcademicYear(String.valueOf(academicYear));
			String SemType="ODD";
			 courseMap = StudentCertificateCourseHandler.getInstance().getCertificateCourseList(courseForm);
	         courseForm.setCourseMap(courseMap);
		}
		catch(Exception e) {
			String msg = "data not Valid";
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.Certificate_Subject_Group_Name);
		
	}
	
	public ActionForward getSubjectCodeName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		try {
		String selectedCourse=courseForm.getCertificateCourse();
		StudentCertificateCourseHandler.getInstance().getCertificateCodeName(selectedCourse, courseForm);
		}
		
		catch (Exception e) {
			String msg = "Select a Certificate Course";
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.Certificate_Subject_Group_Name);
	}
	
	
	public ActionForward SubmitSubjectCodeName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		setUserId(request, courseForm);
		String userId=courseForm.getUserId();		
		ActionErrors errors = new ActionErrors();
		courseForm.validate(mapping, request);
		String SubjectCode=courseForm.getSubjectCode();
		String SubjectName= courseForm.getSubjectName();
		String SubjectGrpName= courseForm.getSubjectGroupName();
		try 
		{
			if(SubjectCode == null || SubjectCode.isEmpty())
			{			
			errors.add("error", new ActionError("knowledgepro.certificate.course.subjectCode"));
			}
			if(SubjectName == null || SubjectName.isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.certificate.course.subjectName"));
			}
			if(SubjectGrpName == null || SubjectGrpName.isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.certificate.course.subjectGroupName"));
			}
			if(!errors.isEmpty()){// if errors are not empty
				saveErrors(request, errors);// saving the errors For displaying in UI
				return mapping.findForward(CMSConstants.Certificate_Subject_Group_Name);// Redirecting to the input JSP
			}	
		if(errors.isEmpty())
		{
		String selCertifCourse=courseForm.getCertificateCourse();
		List<StudentCertificateCourseTO> list= StudentCertificateCourseHandler.getInstance().getCertificateCourseStudentList(selCertifCourse);
		StudentCertificateCourseHandler.getInstance().insertSubGrpToSubjectGroupTbl(list,courseForm, userId);
		/*if (result) {
			errors.add("error", new ActionError("knowledgepro.certificate.course.subjectGroupExists"));
			saveErrors(request, errors);
		}
		if(!errors.isEmpty()){// if errors are not empty
			saveErrors(request, errors);// saving the errors For displaying in UI
			return mapping.findForward(CMSConstants.Certificate_Subject_Group_Name);
		}	
	if(errors.isEmpty())
	{*/
		List<StudentCertificateCourseTO> Sublist= StudentCertificateCourseHandler.getInstance().getAdmApplnIdList(selCertifCourse);
		StudentCertificateCourseHandler.getInstance().insertAdmApplnId(Sublist, courseForm, userId);
		List<StudentCertificateCourseTO> StudList= StudentCertificateCourseHandler.getInstance().getCurriculmdIdList(selCertifCourse);
		StudentCertificateCourseHandler.getInstance().insertCurriculumId(StudList, courseForm, userId);
		StudentCertificateCourseHandler.getInstance().getSubjectGroupId(list, courseForm, userId);
		//}
	}
		}catch (Exception e) {
			String msg = "Select a Certificate Course";
			courseForm.setErrorMessage(msg);
			courseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.Certificate_Subject_Group_Name_Inserted);
	}
}
