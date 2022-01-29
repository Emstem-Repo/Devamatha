package com.kp.cms.actions.admission;

import java.util.HashMap;
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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.NewStudentCertificateCourseForm;
import com.kp.cms.handlers.admission.NewStudentCertificateCourseHandler;
import com.kp.cms.handlers.admission.StudentCertificateCourseHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class NewStudentCertificateCourseAction extends BaseDispatchAction {
	public static final String FROM_DATEFORMAT="dd/MM/yyyy";
	public static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(NewStudentCertificateCourseAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentCertificateCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initStudentCertificateCourse");
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		newStudentCertificateCourseForm.resetFields();
		setUserId(request, newStudentCertificateCourseForm);
		log.info("Exit from initStudentCertificateCourse");
		return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_INIT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSchemeNoForCertificateCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initStudentCertificateCourse");
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		if(newStudentCertificateCourseForm.getRegNo()!=null && !newStudentCertificateCourseForm.getRegNo().trim().isEmpty()){
			setSchemeNoAndSchemeMapByRegNo(newStudentCertificateCourseForm);
		}
		if(newStudentCertificateCourseForm.getSchemeNo()!=null){
		request.setAttribute("SchemeNo", newStudentCertificateCourseForm.getSchemeNo());
		request.setAttribute("SchemeMap",newStudentCertificateCourseForm.getSchemeMap());
		}else
		request.setAttribute("msg","Please Enter Valid Reg No");
		log.info("Exit from initStudentCertificateCourse");
		return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_AJAX_RESPONSE);
	}

	/**
	 * @param regNo
	 * @throws Exception
	 */
	private void setSchemeNoAndSchemeMapByRegNo(NewStudentCertificateCourseForm newStudentCertificateCourseForm) throws Exception {
		String schemeNo=NewStudentCertificateCourseHandler.getInstance().getStudentSchemeNoByRegNo(newStudentCertificateCourseForm.getRegNo());
		if(schemeNo!=null && !schemeNo.isEmpty()){
			String query="select concat(s.id,'') from Student s where s.registerNo='"+newStudentCertificateCourseForm.getRegNo()+"'";
			String studentId=PropertyUtil.getDataForUnique(query);
			newStudentCertificateCourseForm.setStudentId(Integer.parseInt(studentId));
			String detainQuery = "select s.student.id from ExamStudentDetentionRejoinDetails s "
					+ "where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null) and s.student.id="
					+ studentId;
			List list = PropertyUtil.getInstance().getListOfData(detainQuery);
			if(list!=null && !list.isEmpty()){
				newStudentCertificateCourseForm.setStudentId(0);
				newStudentCertificateCourseForm.setSchemeNo(null);
				newStudentCertificateCourseForm.setSchemeMap(null);
			}else{
			int semNo=Integer.parseInt(schemeNo);
			Map<Integer,Integer> schemeMap=new HashMap<Integer, Integer>();
			schemeMap.put(semNo, semNo);
			if(semNo%2!=0)
			schemeMap.put(semNo+1,semNo+1);
			newStudentCertificateCourseForm.setSchemeNo(schemeNo);
			newStudentCertificateCourseForm.setSchemeMap(schemeMap);
			}
		}else{
			newStudentCertificateCourseForm.setStudentId(0);
			newStudentCertificateCourseForm.setSchemeNo(null);
			newStudentCertificateCourseForm.setSchemeMap(null);
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
	public ActionForward getCertificateCourses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewStudentCertificateCourse - getCandidates");
		
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		 ActionErrors errors = newStudentCertificateCourseForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				NewStudentCertificateCourseHandler.getInstance().getCertificateCourseByStudent(newStudentCertificateCourseForm);
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newStudentCertificateCourseForm.setErrorMessage(msg);
				newStudentCertificateCourseForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setSchemeNoAndSchemeMapByRegNo(newStudentCertificateCourseForm);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_INIT);
		}
		log.info("Entered NewStudentCertificateCourse - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCertificateCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request,newStudentCertificateCourseForm);//setting the userId to Form
		try {
			List<CertificateCourseTO> mandatoryCourseTOList = newStudentCertificateCourseForm.getMandatorycourseList();
			boolean isSelected = false;
			if(mandatoryCourseTOList!= null && mandatoryCourseTOList.size() > 0){
				Iterator<CertificateCourseTO> itr = mandatoryCourseTOList.iterator();
				while (itr.hasNext()) {
					CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
							.next();
					if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
						continue;
					}
					newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
					newStudentCertificateCourseForm.setCertificateCourseId(certificateCourseTO.getId());
					isSelected = true;
				}
			}
			if(newStudentCertificateCourseForm.getOptionalCourseList()!= null && newStudentCertificateCourseForm.getOptionalCourseList().size() > 0){
				Iterator<CertificateCourseTO> itr = newStudentCertificateCourseForm.getOptionalCourseList().iterator();
				while (itr.hasNext()) {
					CertificateCourseTO certificateCourseTO =itr .next();
					if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
						continue;
					}
					newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
					newStudentCertificateCourseForm.setOptionalId(String.valueOf(certificateCourseTO.getId()));
				}
			}
			
			
			if(isSelected){
				if(newStudentCertificateCourseForm.getOptionalId()!=null && !newStudentCertificateCourseForm.getOptionalId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.admission.optionalOrMandatory"));
					saveErrors(request, errors);
					
					return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_RESULT);
				}
			}else{
				if(newStudentCertificateCourseForm.getOptionalId()!=null && !newStudentCertificateCourseForm.getOptionalId().isEmpty()){
					newStudentCertificateCourseForm.setCertificateCourseId(Integer.parseInt(newStudentCertificateCourseForm.getOptionalId()));
					isSelected=true;
				}
			}
			boolean isExtra=false;
				List<CertificateCourseTO> extraCourseTOList = newStudentCertificateCourseForm.getExtraCurricularCourseList();
				if(extraCourseTOList!= null && extraCourseTOList.size() > 0){
					Iterator<CertificateCourseTO> itr = extraCourseTOList.iterator();
					while (itr.hasNext()) {
						CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
								.next();
						if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
							continue;
						}
						newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
						newStudentCertificateCourseForm.setCertificateCourseId(certificateCourseTO.getId());
						isExtra = true;
					}
				}
			validateDuplicate(newStudentCertificateCourseForm,errors,isSelected,isExtra);	
			if(isExtra && isSelected){
				errors.add("error", new ActionError("knowledgepro.admission.optionalOrMandatory"));
			}
			if(!isSelected && !isExtra){
				errors.add("error", new ActionError("knowledgepro.admission.no.certificate.course.selected"));
			}
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_RESULT);
			}
			Double feeAmt = StudentCertificateCourseHandler.getInstance().getFeeAmount(newStudentCertificateCourseForm.getCertificateCourseId());
			String certificateCourseName = NewSecuredMarksEntryHandler.getInstance().getPropertyValue(newStudentCertificateCourseForm.getCertificateCourseId(),"CertificateCourse",true,"certificateCourseName");
			
			newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseName);
			newStudentCertificateCourseForm.setFeeAmt(feeAmt);
			boolean isAdded = NewStudentCertificateCourseHandler.getInstance().saveCertificateCourse(newStudentCertificateCourseForm);
			
			String print=newStudentCertificateCourseForm.getPrintCourse();
			if(print.equalsIgnoreCase("true")){
				request.getSession().setAttribute("studentId",newStudentCertificateCourseForm.getStudentId());
				request.getSession().setAttribute("certifiedId",newStudentCertificateCourseForm.getCertificateCourseId());
			}
			if(isAdded){
				NewStudentCertificateCourseHandler.getInstance().sendSMSToStudent(newStudentCertificateCourseForm.getStudentId(),CMSConstants.STUDENT_CERTIFICATE_SMS_TEMPLATE);
				newStudentCertificateCourseForm.resetFields();
				ActionMessage message = new ActionMessage("knowledgepro.adminssion.student.certificate.course.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				newStudentCertificateCourseForm.setPrintCourse(print);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message", newStudentCertificateCourseForm.getMsg()));
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_RESULT);
			}
		
		}catch (ReActivateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_RESULT);
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			newStudentCertificateCourseForm.setErrorMessage(msg);
			newStudentCertificateCourseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
		return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_COURSE_INIT);
	}
	
	/**
	 * @param newStudentCertificateCourseForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateDuplicate( NewStudentCertificateCourseForm newStudentCertificateCourseForm, ActionErrors errors,boolean isSelected,boolean isExtra) throws Exception {
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		if(isExtra){
			String extraCurricularQuery="from StudentCertificateCourse s where s.isCancelled=0 and s.isExtraCurricular=1 and s.student.id="+newStudentCertificateCourseForm.getStudentId();
			List<StudentCertificateCourse> extraCurricularList=txn.getDataForQuery(extraCurricularQuery);
			if(extraCurricularList!=null && !extraCurricularList.isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","You have already applied for ExtraCurricular CertificateCourse:"+extraCurricularList.get(0).getCertificateCourse().getCertificateCourseName()));
			}
		}
		if(isSelected){
			String certificateCourseAppliedQuery="from StudentCertificateCourse s where s.isCancelled=0 and s.isExtraCurricular=0 and s.schemeNo="+newStudentCertificateCourseForm.getSchemeNo()+" and s.student.id="+newStudentCertificateCourseForm.getStudentId();
			List<StudentCertificateCourse> certificateAppliedList=txn.getDataForQuery(certificateCourseAppliedQuery);
			if(certificateAppliedList!=null && !certificateAppliedList.isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","You have already applied for CertificateCourse:"+certificateAppliedList.get(0).getCertificateCourse().getCertificateCourseName()));
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
	public ActionForward initCertificateCourseForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initStudentCertificateCourse");
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		newStudentCertificateCourseForm.resetFields();
		HttpSession session=request.getSession(true);
		newStudentCertificateCourseForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
		newStudentCertificateCourseForm.setRegNo(session.getAttribute("studentRegNo").toString());
		int semNo=Integer.parseInt(session.getAttribute("studentsemNo").toString());
		
		if(CMSConstants.CERTIFICATE_NEXT_SEM)
			semNo=semNo+1;
		if(semNo%2==0)
			newStudentCertificateCourseForm.setSemType("EVEN");
		else
			newStudentCertificateCourseForm.setSemType("ODD");
			
		newStudentCertificateCourseForm.setSchemeNo(String.valueOf(semNo));
		newStudentCertificateCourseForm.setOnline(true);
		log.info("Exit from initStudentCertificateCourse");
		return mapping.findForward(CMSConstants.INIT_STUDENT_CERTIFICATE_COURSE_STUDENT_LOGIN);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCertificateCourseForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewStudentCertificateCourse - getCertificateCourseForStudentLogin");
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		try {
			NewStudentCertificateCourseHandler.getInstance().getCertificateCourseByStudent(newStudentCertificateCourseForm);
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			newStudentCertificateCourseForm.setErrorMessage(msg);
			newStudentCertificateCourseForm.setErrorStack(exception.getMessage());
			newStudentCertificateCourseForm.setApplyWithoutPayment(false);
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_CERTIFICATE_COURSE_RESULT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCertificateCourseForStudentLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request,newStudentCertificateCourseForm);//setting the userId to Form
		try {
			List<CertificateCourseTO> mandatoryCourseTOList = newStudentCertificateCourseForm.getMandatorycourseList();
			boolean isSelected = false;
			if(mandatoryCourseTOList!= null && mandatoryCourseTOList.size() > 0){
				Iterator<CertificateCourseTO> itr = mandatoryCourseTOList.iterator();
				while (itr.hasNext()) {
					CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
					.next();
					if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
						continue;
					}
					newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
					newStudentCertificateCourseForm.setCertificateCourseId(certificateCourseTO.getId());
					isSelected = true;
				}
			}
			if(newStudentCertificateCourseForm.getOptionalCourseList()!= null && newStudentCertificateCourseForm.getOptionalCourseList().size() > 0){
				Iterator<CertificateCourseTO> itr = newStudentCertificateCourseForm.getOptionalCourseList().iterator();
				while (itr.hasNext()) {
					CertificateCourseTO certificateCourseTO =itr .next();
					if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
						continue;
					}
					newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
					newStudentCertificateCourseForm.setOptionalId(String.valueOf(certificateCourseTO.getId()));
				}
			}
			
			if(isSelected){
				if(newStudentCertificateCourseForm.getOptionalId()!=null && !newStudentCertificateCourseForm.getOptionalId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.admission.optionalOrMandatory"));
					saveErrors(request, errors);
					newStudentCertificateCourseForm.setApplyWithoutPayment(false);
					return mapping.findForward(CMSConstants.STUDENT_LOGIN_CERTIFICATE_COURSE_RESULT);
				}
			}else{
				if(newStudentCertificateCourseForm.getOptionalId()!=null && !newStudentCertificateCourseForm.getOptionalId().isEmpty()){
					newStudentCertificateCourseForm.setCertificateCourseId(Integer.parseInt(newStudentCertificateCourseForm.getOptionalId()));
					isSelected=true;
				}
			}
			boolean isExtra=false;
			List<CertificateCourseTO> extraCourseTOList = newStudentCertificateCourseForm.getExtraCurricularCourseList();
			if(extraCourseTOList!= null && extraCourseTOList.size() > 0){
				Iterator<CertificateCourseTO> itr = extraCourseTOList.iterator();
				while (itr.hasNext()) {
					CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
					.next();
					if(certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck().equalsIgnoreCase("on")){
						continue;
					}
					newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseTO.getCourseName());
					newStudentCertificateCourseForm.setCertificateCourseId(certificateCourseTO.getId());
					isExtra = true;
				}
			}
			validateDuplicate(newStudentCertificateCourseForm,errors,isSelected,isExtra);	
			if(isExtra && isSelected){
				errors.add("error", new ActionError("knowledgepro.admission.optionalOrMandatory"));
			}
			if(!isSelected && !isExtra){
				errors.add("error", new ActionError("knowledgepro.admission.no.certificate.course.selected"));
			}
			Double feeAmt = StudentCertificateCourseHandler.getInstance().getFeeAmount(newStudentCertificateCourseForm.getCertificateCourseId());
			if(errors.isEmpty())
			if(feeAmt==null || feeAmt==0 )
				errors.add("error",new ActionError("knowledgepro.admission.empty.err.message","Unable to submit the application, kindly contact support@christuniversity.in"));
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				newStudentCertificateCourseForm.setApplyWithoutPayment(false);
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_CERTIFICATE_COURSE_RESULT);
			}
			String certificateCourseName = NewSecuredMarksEntryHandler.getInstance().getPropertyValue(newStudentCertificateCourseForm.getCertificateCourseId(),"CertificateCourse",true,"certificateCourseName");
			
			newStudentCertificateCourseForm.setCertificateCourseName(certificateCourseName);
			newStudentCertificateCourseForm.setFeeAmt(feeAmt);
			if(newStudentCertificateCourseForm.isApplyWithoutPayment()){
				return	verifyAndSaveForStudentLogin(mapping, newStudentCertificateCourseForm, request, response);
			}
			else{
				ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
				Student student=(Student) txn.getMasterEntryDataById(Student.class,newStudentCertificateCourseForm.getStudentId());
				if(student!=null){
					newStudentCertificateCourseForm.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
					newStudentCertificateCourseForm.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
					newStudentCertificateCourseForm.setRegNo(student.getRegisterNo());
					newStudentCertificateCourseForm.setDob(null);
					newStudentCertificateCourseForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), SQL_DATEFORMAT,FROM_DATEFORMAT));
				}
			}
			
			
			/*boolean isAdded = NewStudentCertificateCourseHandler.getInstance().saveCertificateCourse(newStudentCertificateCourseForm);
			
			String print=newStudentCertificateCourseForm.getPrintCourse();
			if(print.equalsIgnoreCase("true")){
				request.getSession().setAttribute("certifiedId",newStudentCertificateCourseForm.getCertificateCourseId());
			}
			newStudentCertificateCourseForm.resetFields();
			if(isAdded){
				NewStudentCertificateCourseHandler.getInstance().sendSMSToStudent(newStudentCertificateCourseForm.getStudentId());
				ActionMessage message = new ActionMessage("knowledgepro.adminssion.student.certificate.course.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				newStudentCertificateCourseForm.setPrintCourse(print);
				HttpSession session=request.getSession(true);
				newStudentCertificateCourseForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
				newStudentCertificateCourseForm.setRegNo(session.getAttribute("studentRegNo").toString());
				int semNo=Integer.parseInt(session.getAttribute("studentsemNo").toString());
				if(CMSConstants.CERTIFICATE_NEXT_SEM)
					semNo=semNo+1;
				if(semNo%2==0)
					newStudentCertificateCourseForm.setSemType("EVEN");
				else
					newStudentCertificateCourseForm.setSemType("ODD");
				newStudentCertificateCourseForm.setSchemeNo(String.valueOf(semNo));
				newStudentCertificateCourseForm.setCurrentlyApplied(true);
			}*/
			
		}catch (ReActivateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
			saveErrors(request, errors);
			newStudentCertificateCourseForm.setApplyWithoutPayment(false);
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_CERTIFICATE_COURSE_RESULT);
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			newStudentCertificateCourseForm.setErrorMessage(msg);
			newStudentCertificateCourseForm.setErrorStack(e.getMessage());
			newStudentCertificateCourseForm.setApplyWithoutPayment(false);
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_SMART_CARD_ENQUIRY);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyStudentSmartCardForStudentLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request,newStudentCertificateCourseForm);//setting the userId to Form
		try {
			boolean isValidSmartCard=NewStudentCertificateCourseHandler.getInstance().verifySmartCard(newStudentCertificateCourseForm);
			if(!isValidSmartCard){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
			}
			if(newStudentCertificateCourseForm.getDob()!=null){
				if(!newStudentCertificateCourseForm.getDob().equalsIgnoreCase(newStudentCertificateCourseForm.getOriginalDob()))
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter Valid Date Of Birth"));
			}
			
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_SMART_CARD_ENQUIRY);
			}
			
		}catch (ReActivateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SMART_CARD_ENQUIRY);
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			newStudentCertificateCourseForm.setErrorMessage(msg);
			newStudentCertificateCourseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_SMART_CARD_ENQUIRY_1);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyAndSaveForStudentLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewStudentCertificateCourseForm newStudentCertificateCourseForm = (NewStudentCertificateCourseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request,newStudentCertificateCourseForm);//setting the userId to Form
		boolean applyWithoutPayment=newStudentCertificateCourseForm.isApplyWithoutPayment();
		try {
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				newStudentCertificateCourseForm.setApplyWithoutPayment(false);
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_SMART_CARD_ENQUIRY);
			}
			boolean isAdded = NewStudentCertificateCourseHandler.getInstance().saveCertificateCourse(newStudentCertificateCourseForm);
			
			String print=newStudentCertificateCourseForm.getPrintCourse();
			if(print.equalsIgnoreCase("true")){
				request.getSession().setAttribute("certifiedId",newStudentCertificateCourseForm.getCertificateCourseId());
			}
			String msg=newStudentCertificateCourseForm.getMsg();
			newStudentCertificateCourseForm.resetFields();
			if(isAdded){
				ActionMessage message;
				NewStudentCertificateCourseHandler.getInstance().sendSMSToStudent(newStudentCertificateCourseForm.getStudentId(),CMSConstants.STUDENT_CERTIFICATE_SMS_TEMPLATE);
				 message = new ActionMessage("knowledgepro.adminssion.student.certificate.course.success");
				 messages.add("messages", message);
				 if(applyWithoutPayment){
					 newStudentCertificateCourseForm.setDisplayChallan(false);
					 message = new ActionMessage("knowledgepro.adminssion.student.certificate.course.success.without.payment");
					 messages.add("messages", message);
				} else{
					newStudentCertificateCourseForm.setDisplayChallan(true);
				}
				saveMessages(request, messages);
				newStudentCertificateCourseForm.setPrintCourse(print);
			}else{
				if(applyWithoutPayment){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","certificate course submission was not successfull"));
				}
				else{
					if(msg==null || msg.isEmpty()){
					msg="Payment Failed";
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","certificate course submission was not successfull, Reason:"+msg));
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
				}
				addErrors(request, errors);
			}
			HttpSession session=request.getSession(true);
			newStudentCertificateCourseForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
			newStudentCertificateCourseForm.setRegNo(session.getAttribute("studentRegNo").toString());
			int semNo=Integer.parseInt(session.getAttribute("studentsemNo").toString());
			if(CMSConstants.CERTIFICATE_NEXT_SEM)
				semNo=semNo+1;
			if(semNo%2==0)
				newStudentCertificateCourseForm.setSemType("EVEN");
			else
				newStudentCertificateCourseForm.setSemType("ODD");
			newStudentCertificateCourseForm.setSchemeNo(String.valueOf(semNo));
			newStudentCertificateCourseForm.setCurrentlyApplied(true);
			newStudentCertificateCourseForm.setOnline(true);
		}catch (ReActivateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
			saveErrors(request, errors);
			newStudentCertificateCourseForm.setApplyWithoutPayment(false);
			if(applyWithoutPayment)
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_CERTIFICATE_COURSE_RESULT);
			else
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SMART_CARD_ENQUIRY);
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			newStudentCertificateCourseForm.setErrorMessage(msg);
			newStudentCertificateCourseForm.setErrorStack(e.getMessage());
			newStudentCertificateCourseForm.setApplyWithoutPayment(false);
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		
		return getCertificateCourseForStudentLogin(mapping, newStudentCertificateCourseForm, request, response);
	}

}