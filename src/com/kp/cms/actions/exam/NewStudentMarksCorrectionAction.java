package com.kp.cms.actions.exam;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.NewStudentMarksCorrectionForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewStudentMarksCorrectionHandler;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.PropertyUtil;

public class NewStudentMarksCorrectionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewStudentMarksCorrectionAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initVerifyUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initStudentMarksCorrection input");
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		Properties prop = new Properties();
		InputStream in = NewStudentMarksCorrectionAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        Properties prop1 = new Properties();
        InputStream in1 = NewStudentMarksCorrectionAction.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop1.load(in1);
        if(prop.getProperty("knowledgepro.exam.studentMarksCorrection.enableAdditionalSecurity")!=null
        		&& prop.getProperty("knowledgepro.exam.studentMarksCorrection.enableAdditionalSecurity").equalsIgnoreCase("true")){
			String randPass=PasswordGenerator.getPassword();
			HttpSession session=request.getSession();
			String userId=session.getAttribute("uid").toString();
			session.setAttribute("verifyPassword", userId+"_"+randPass);
			sendPasswordBySMS(randPass,newStudentMarksCorrectionForm,prop.getProperty("knowledgepro.exam.studentMarksCorrection.CEO.ID"),prop1);
			sendMailToCEO(randPass,prop);
        }else{
        	return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_VERIFICATION_RESULT);
        }
		log.info("Exit initStudentMarksCorrection input");
		
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_VERIFICATION);
	}
	/**
	 * @param randPass
	 * @param prop
	 * @throws Exception
	 */
	private void sendMailToCEO(String randPass, Properties prop) throws Exception {
		String ceoId=prop.getProperty("knowledgepro.exam.studentMarksCorrection.CEO.ID");
		String toAddress=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(ceoId),"Employee",true,"workEmail");
		if(toAddress!=null && !toAddress.isEmpty()){
			String subject="Verification Password For Student Marks Correction";
			String msg="Hi!,<br>" +
					"Verification Password For Student Marks Correction .The Password Is "+randPass +".<br>Thanks,<br>Christ University Admin.<br>";
			AdmissionFormHandler.getInstance().sendMail(toAddress, subject, msg);
		}
	}
	/**
	 * @param randPass
	 * @param newStudentMarksCorrectionForm
	 */
	private void sendPasswordBySMS(String randPass, NewStudentMarksCorrectionForm newStudentMarksCorrectionForm,String ceoId,Properties prop) throws Exception {
		String mobileNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(ceoId),"Employee",true,"currentAddressMobile1");
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.SMS_TEMPLATE_VERIFY);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.SMS_TEMPLATE_VERIFY_PASSWORD, randPass);
		if(mobileNo.length()==10)
			mobileNo="91"+mobileNo;
		
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			PropertyUtil.getInstance().save(mob);
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
	public ActionForward verifyUserPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		newStudentMarksCorrectionForm.getVerifyPassword();
		setUserId(request,newStudentMarksCorrectionForm);
		HttpSession session=request.getSession();
		if(session.getAttribute("verifyPassword").toString().equals(newStudentMarksCorrectionForm.getUserId()+"_"+newStudentMarksCorrectionForm.getVerifyPassword())){
			return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_VERIFICATION_RESULT);
		}else{
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.studentMarksCorrection.not.valid.password"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_VERIFICATION);
		}
	}
	
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentMarksCorrection(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initStudentMarksCorrection input");
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		newStudentMarksCorrectionForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(newStudentMarksCorrectionForm);// setting the requested data to form
		log.info("Exit initStudentMarksCorrection input");
		
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION);
	}

	/**
	 * @param newStudentMarksCorrectionForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		if(newStudentMarksCorrectionForm.getExamType()!=null && !newStudentMarksCorrectionForm.getExamType().isEmpty()){
//			Map<Integer, String> examMap = CommonAjaxHandler.getInstance().getExamNameByExamType(newStudentMarksCorrectionForm.getExamType());
			
			
			int year=0;
			year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(newStudentMarksCorrectionForm.getAcademicYear()!=null && !newStudentMarksCorrectionForm.getAcademicYear().isEmpty()){
				year=Integer.parseInt(newStudentMarksCorrectionForm.getAcademicYear());
			}
			if(year==0){
				Calendar calendar = Calendar.getInstance();
				year = calendar.get(Calendar.YEAR);
			}
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(newStudentMarksCorrectionForm.getExamType(),year); 
			
			if(examMap!=null && !examMap.isEmpty())
				newStudentMarksCorrectionForm.setExamMap(examMap);
			String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(newStudentMarksCorrectionForm.getExamType());
			if((newStudentMarksCorrectionForm.getExamId()==null && newStudentMarksCorrectionForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
				newStudentMarksCorrectionForm.setExamId(currentExam);
			HashMap<String, String> markTypeMap = new HashMap<String, String>();
			if (newStudentMarksCorrectionForm.getExamType().equalsIgnoreCase("Internal")) {
				markTypeMap = new ExamGenHandler().getInternalExamType();
			} else {
				markTypeMap.put("Internal overAll", "Internal overAll");
				markTypeMap.put("Regular overAll", "Regular overAll");
			}
			newStudentMarksCorrectionForm.setMarkTypeMap(markTypeMap);
		}else{
			newStudentMarksCorrectionForm.setExamMap(null);
			newStudentMarksCorrectionForm.setExamId(null);
			newStudentMarksCorrectionForm.setMarkTypeMap(null);
		}
		if(newStudentMarksCorrectionForm.getExamId()!=null && !newStudentMarksCorrectionForm.getExamId().isEmpty()
				&& newStudentMarksCorrectionForm.getSchemeNo()!=null && !newStudentMarksCorrectionForm.getSchemeNo().trim().isEmpty()){
			if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
				Map<Integer, String> answerScriptType = ExamMarksEntryHandler.getInstance().getListanswerScriptType(null,
						Integer.parseInt(newStudentMarksCorrectionForm.getSchemeNo()), null,null, Integer.parseInt(newStudentMarksCorrectionForm.getExamId()));
				if(!answerScriptType.isEmpty()){
					newStudentMarksCorrectionForm.setAnswerScriptType(answerScriptType);
					newStudentMarksCorrectionForm.setDispAST("yes");
				}else{
					newStudentMarksCorrectionForm.setAnswerScriptType(null);
					newStudentMarksCorrectionForm.setDispAST("no");
				}
				Map<Integer, String> evaluatorMap = ExamMarksEntryHandler.getInstance().get_evaluatorList_ruleSettings(null,
						Integer.parseInt(newStudentMarksCorrectionForm.getSchemeNo()), null,null, Integer.parseInt(newStudentMarksCorrectionForm.getExamId()));
				if(evaluatorMap!=null && !evaluatorMap.isEmpty()){
					newStudentMarksCorrectionForm.setEvaluatorMap(evaluatorMap);
					newStudentMarksCorrectionForm.setDispET("yes");
				}else{
					newStudentMarksCorrectionForm.setEvaluatorMap(null);
					newStudentMarksCorrectionForm.setDispET("no");
				}
			}else{
				if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
					newStudentMarksCorrectionForm.setDispET("yes");
					newStudentMarksCorrectionForm.setDispAST("yes");
				}else{
					newStudentMarksCorrectionForm.setEvaluatorMap(null);
					newStudentMarksCorrectionForm.setDispET("no");
					newStudentMarksCorrectionForm.setAnswerScriptType(null);
					newStudentMarksCorrectionForm.setDispAST("no");
				}
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
	public ActionForward getDataForExam(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewStudentMarksCorrectionAction - getDataForExam");
		
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = newStudentMarksCorrectionForm.validate(mapping, request);
		newStudentMarksCorrectionForm.resetFields1();
		newStudentMarksCorrectionForm.setRegNo(null);
		newStudentMarksCorrectionForm.setRollNo(null);
		if (errors.isEmpty()) {
			try {
				boolean checkMarksEntry=NewStudentMarksCorrectionHandler.getInstance().checkMarksEnteredForExam(newStudentMarksCorrectionForm);//checking whether marks entered through secured marks entry
				if(!checkMarksEntry){// if the marks are entered through secured marks entry display message in first screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newStudentMarksCorrectionForm);			
					return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION);
				}
				setRequiredDatatoForm(newStudentMarksCorrectionForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newStudentMarksCorrectionForm.setErrorMessage(msg);
				newStudentMarksCorrectionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newStudentMarksCorrectionForm);			
			log.info("Exit NewStudentMarksCorrectionAction - getDataForExam errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION);
		}
		setNamesToForm(newStudentMarksCorrectionForm);
		log.info("Entered NewStudentMarksCorrectionAction - getDataForExam");
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
	}
	/**
	 * @param newExamMarksEntryForm
	 */
	private void setNamesToForm(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm)throws Exception {
		newStudentMarksCorrectionForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newStudentMarksCorrectionForm.getExamId()),"ExamDefinitionBO",true,"examCode"));
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewStudentMarksCorrectionAction - getStudentDetails");
		
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		newStudentMarksCorrectionForm.resetFields1();
		validateStudentDetails(newStudentMarksCorrectionForm,errors);
		if (errors.isEmpty()) {
			try {
				boolean checkStudent=NewStudentMarksCorrectionHandler.getInstance().checkStudentIsValid(newStudentMarksCorrectionForm);
				if(!checkStudent){// if the marks are entered through secured marks entry display message in first screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newStudentMarksCorrectionForm);			
					return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
				}else{
					List<NewStudentMarkCorrectionTo> marksList=NewStudentMarksCorrectionHandler.getInstance().getSubjectListOfStudent(newStudentMarksCorrectionForm);
					newStudentMarksCorrectionForm.setMarksList(marksList);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newStudentMarksCorrectionForm.setErrorMessage(msg);
				newStudentMarksCorrectionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newStudentMarksCorrectionForm);			
			log.info("Exit NewStudentMarksCorrectionAction - getStudentDetails errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
		}
		setNamesToForm(newStudentMarksCorrectionForm);
		log.info("Entered NewStudentMarksCorrectionAction - getStudentDetails");
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
	}

	/**
	 * @param newStudentMarksCorrectionForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateStudentDetails(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm,ActionErrors errors) throws Exception{
		if((newStudentMarksCorrectionForm.getRegNo()==null || newStudentMarksCorrectionForm.getRegNo().isEmpty()) && (newStudentMarksCorrectionForm.getRollNo()==null || newStudentMarksCorrectionForm.getRollNo().isEmpty())){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required"," Register No or Roll No"));
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
	public ActionForward saveChangedMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newStudentMarksCorrectionForm.validate(mapping, request);
		validateStudentMarks(newStudentMarksCorrectionForm,errors);
		setUserId(request, newStudentMarksCorrectionForm);
		if (errors.isEmpty()) {
			try {
				boolean isSaved=NewStudentMarksCorrectionHandler.getInstance().saveChangedMarks(newStudentMarksCorrectionForm);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Student Marks "));
					saveMessages(request, messages);
					newStudentMarksCorrectionForm.resetFields1();
					newStudentMarksCorrectionForm.setRegNo(null);
					newStudentMarksCorrectionForm.setRollNo(null);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newStudentMarksCorrectionForm.setErrorMessage(msg);
				newStudentMarksCorrectionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
	}

	/**
	 * @param newStudentMarksCorrectionForm
	 * @param errors
	 */
	private void validateStudentMarks(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm,ActionErrors errors) throws Exception {
		List<NewStudentMarkCorrectionTo> toList=newStudentMarksCorrectionForm.getMarksList();
		Map<Integer,SubjectMarksTO> maxMarks=NewStudentMarksCorrectionHandler.getInstance().getMaxMarksMap(newStudentMarksCorrectionForm);
		Iterator<NewStudentMarkCorrectionTo> itr=toList.iterator();
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		String subjectName="";
		
		
		while (itr.hasNext()) {
			NewStudentMarkCorrectionTo to = (NewStudentMarkCorrectionTo) itr.next();
			boolean isTheoryChanged=false;
			boolean isPracticalChanged=false;
			if(to.getIsTheory()){
				if(to.getOldTheoryMarks()!=null && !to.getTheoryMarks().equals(to.getOldTheoryMarks())){
					isTheoryChanged=true;
				}
			}
			if(to.getIsPractical()){
				if(to.getOldPracticalMarks()!=null && !to.getPracticalMarks().equals(to.getOldPracticalMarks())){
					isPracticalChanged=true;
				}
			}
			if(isPracticalChanged || isTheoryChanged){
				if(to.getComments()==null || to.getComments().isEmpty())
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.comment.subjectwise",to.getSubjectName()));
				
				if(to.getGracing()==null || !to.getGracing().equalsIgnoreCase("on")){
					newStudentMarksCorrectionForm.setVerifyGracing(true);
					if(subjectName == null || subjectName.isEmpty()){
						subjectName=to.getSubjectCode()+"-"+to.getSubjectName();
					}else{
						subjectName=subjectName+","+to.getSubjectCode()+"-"+to.getSubjectName();
					}
					errors.add(CMSConstants.ERROR,new ActionError(""));
				}
				
				
				SubjectMarksTO subTo=maxMarks.get(to.getSubjectId());
				if(subTo==null){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.notDefined",to.getSubjectName()));
				}else{
					if(isPracticalChanged && to.getPracticalMarks()!=null){
						if(!CommonUtil.isValidDecimal(to.getPracticalMarks())){
							if(!examAbscentCode.contains(to.getPracticalMarks()))
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.valid.marks.subject",to.getSubjectName()));
						}else{
							if(Double.parseDouble(to.getPracticalMarks())>Double.parseDouble(subTo.getFinalPracticalMarks())){
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.subject",to.getSubjectName()));
							}
						}
					}
					if(isTheoryChanged  && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(to.getTheoryMarks())){
							if(!examAbscentCode.contains(to.getTheoryMarks()))
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.valid.marks.subject",to.getSubjectName()));
						}else{
							if(Double.parseDouble(to.getTheoryMarks())>Double.parseDouble(subTo.getFinalTheoryMarks())){
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.subject",to.getSubjectName()));
							}
						}
					}
				}
			}
		}
		newStudentMarksCorrectionForm.setSubjectname(subjectName);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewOldMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - viewOldMarks");
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		try {
			List<StudentMarkDetailsTO> oldMarksList=NewStudentMarksCorrectionHandler.getInstance().getStudentOldMarks(newStudentMarksCorrectionForm);
			newStudentMarksCorrectionForm.setOldMarksList(oldMarksList);
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			newStudentMarksCorrectionForm.setErrorMessage(msg);
			newStudentMarksCorrectionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Entered NewExamMarksEntryAction - viewOldMarks");
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_VIEW);
	}
	
	
	public ActionForward saveChangedMarksAfterVerifiedGracing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		NewStudentMarksCorrectionForm newStudentMarksCorrectionForm = (NewStudentMarksCorrectionForm) form;// Type casting the Action form to Required Form
		newStudentMarksCorrectionForm.setVerifyGracing(false);
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newStudentMarksCorrectionForm.validate(mapping, request);
		validateStudentMarksAfterVerifiedGracing(newStudentMarksCorrectionForm,errors);
		setUserId(request, newStudentMarksCorrectionForm);
		if (errors.isEmpty()) {
			try {
				boolean isSaved=NewStudentMarksCorrectionHandler.getInstance().saveChangedMarks(newStudentMarksCorrectionForm);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Student Marks "));
					saveMessages(request, messages);
					newStudentMarksCorrectionForm.resetFields1();
					newStudentMarksCorrectionForm.setRegNo(null);
					newStudentMarksCorrectionForm.setRollNo(null);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newStudentMarksCorrectionForm.setErrorMessage(msg);
				newStudentMarksCorrectionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_MARKS_CORRECTION_RESULT);
	}
	
	private void validateStudentMarksAfterVerifiedGracing(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm,ActionErrors errors) throws Exception {
		List<NewStudentMarkCorrectionTo> toList=newStudentMarksCorrectionForm.getMarksList();
		Map<Integer,SubjectMarksTO> maxMarks=NewStudentMarksCorrectionHandler.getInstance().getMaxMarksMap(newStudentMarksCorrectionForm);
		Iterator<NewStudentMarkCorrectionTo> itr=toList.iterator();
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		while (itr.hasNext()) {
			NewStudentMarkCorrectionTo to = (NewStudentMarkCorrectionTo) itr.next();
			boolean isTheoryChanged=false;
			boolean isPracticalChanged=false;
			if(to.getIsTheory()){
				if(to.getOldTheoryMarks()!=null && !to.getTheoryMarks().equals(to.getOldTheoryMarks())){
					isTheoryChanged=true;
				}
			}
			if(to.getIsPractical()){
				if(to.getOldPracticalMarks()!=null && !to.getPracticalMarks().equals(to.getOldPracticalMarks())){
					isPracticalChanged=true;
				}
			}
			if(isPracticalChanged || isTheoryChanged){
				if(to.getComments()==null || to.getComments().isEmpty())
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.comment.subjectwise",to.getSubjectName()));
				
				SubjectMarksTO subTo=maxMarks.get(to.getSubjectId());
				if(subTo==null){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.notDefined",to.getSubjectName()));
				}else{
					if(isPracticalChanged && to.getPracticalMarks()!=null){
						if(!CommonUtil.isValidDecimal(to.getPracticalMarks())){
							if(!examAbscentCode.contains(to.getPracticalMarks()))
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.valid.marks.subject",to.getSubjectName()));
						}else{
							if(Double.parseDouble(to.getPracticalMarks())>Double.parseDouble(subTo.getFinalPracticalMarks())){
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.subject",to.getSubjectName()));
							}
						}
					}
					if(isTheoryChanged  && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(to.getTheoryMarks())){
							if(!examAbscentCode.contains(to.getTheoryMarks()))
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.valid.marks.subject",to.getSubjectName()));
						}else{
							if(Double.parseDouble(to.getTheoryMarks())>Double.parseDouble(subTo.getFinalTheoryMarks())){
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.subject",to.getSubjectName()));
							}
						}
					}
				}
			}
		}
	}
}
