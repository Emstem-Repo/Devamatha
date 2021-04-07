package com.kp.cms.actions.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseHandler;
import com.kp.cms.handlers.exam.RevaluationOrRetotallingMarksEntryHandler;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseTo;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class RevaluationOrRetotalingMarksEntryForSubjectWiseAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(RevaluationOrRetotalingMarksEntryForSubjectWiseAction.class);
	public ActionForward initRevaluationOrRetotalingMarksEntryForSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initRevaluationOrRetotalingMarksEntryForSubject input");
		RevaluationOrRetotalingMarksEntryForSubjectWiseForm revaluationForm = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
		revaluationForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(revaluationForm, request);// setting the requested data to form
		log.info("Exit initRevaluationOrRetotalingMarksEntryForSubject input");
		
		return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE);
	}
	
	private void setRequiredDatatoForm(RevaluationOrRetotalingMarksEntryForSubjectWiseForm revaluationForm,HttpServletRequest request) throws Exception{
//		Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(revaluationForm.getExamType());// getting exam list based on exam Type
//		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
//		revaluationForm.setExamNameList(examNameMap);// setting the examNameMap to form
		
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(revaluationForm.getAcademicYear()!=null && !revaluationForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(revaluationForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(revaluationForm.getExamType(),year); 
		revaluationForm.setExamNameList(examMap);// setting the examNameMap to form
		//Newly Added For Making default Current Exam
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(revaluationForm.getExamType());
		if((revaluationForm.getExamId()==null || revaluationForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			revaluationForm.setExamId(currentExam);
		
		if(revaluationForm.getExamId()!=null && !revaluationForm.getExamId().isEmpty()){
			ArrayList<KeyValueTO> subjectList=CommonAjaxExamHandler.getInstance().getSubjectFromRevaluationOrRetotaling(revaluationForm.getDisplaySubType(), Integer.parseInt(revaluationForm.getExamId()));
			revaluationForm.setSubjectList(subjectList);
		}
		
		if(revaluationForm.getSubjectId()!=null && !revaluationForm.getSubjectId().isEmpty()){
			int subjectId = Integer.parseInt(revaluationForm.getSubjectId());
			int examId = Integer.parseInt(revaluationForm.getExamId());
			int subjectTypeId = 1;
			ExamSecuredMarksEntryHandler securedhandler = new ExamSecuredMarksEntryHandler();
			Map<Integer, String>  evaluatorTypeMap = securedhandler.getEvaluatorType(subjectId,subjectTypeId, examId);
			if(evaluatorTypeMap!=null && !evaluatorTypeMap.isEmpty() && !revaluationForm.getExamType().equalsIgnoreCase("Internal")){
				revaluationForm.setDisplayET("yes");
				revaluationForm.setValidationET("yes");
			}else{
				revaluationForm.setDisplayET("no");
				revaluationForm.setValidationET("no");
			}
			revaluationForm.setEvaluatorTypeMap(evaluatorTypeMap);
		}else{
			revaluationForm.setSubjectType(null);
			revaluationForm.setEvaluatorTypeMap(null);
			revaluationForm.setDisplayET("yes");
		}
	}
	
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = Form.validate(mapping, request);
		validateExamMarksEntry(Form,errors);
		if (errors.isEmpty()) {
			try {
				
				List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> studentDetailsList=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getStudentDetails(Form);
				if(studentDetailsList==null || studentDetailsList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.application.not.found"));
					saveErrors(request, errors);
					//Form.resetFields();
					setRequiredDatatoForm(Form,request);	
					return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE);
				}
				setRequiredDatatoForm(Form,request);
				Collections.sort(studentDetailsList);
				Form.setStudentList(studentDetailsList);
				setMainStudentList(Form);
				setNamesToForm(Form);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(Form,request);
			log.info("Exit RevaluationOrRetotallingMarksEntryAction - getCandidateRecord errors not empty ");
			return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE);
		}
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE_RESULT);
	}
	private void setMainStudentList(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception{
		List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> mainList=new ArrayList<RevaluationOrRetotalingMarksEntryForSubjectWiseTo>();
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		mainList.add(new RevaluationOrRetotalingMarksEntryForSubjectWiseTo());
		form.setMainList(mainList);
	}
private void setNamesToForm(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form)throws Exception {
		
	form.setSubjectCode(RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getPropertyValue(Integer.parseInt(form.getSubjectId()),"Subject",true,"code"));
	form.setExamName(RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getPropertyValue(Integer.parseInt(form.getExamId()),"ExamDefinitionBO",true,"name"));
	form.setSubjectName(RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getPropertyValue(Integer.parseInt(form.getSubjectId()),"Subject",true,"name"));
		
	}

private void validateExamMarksEntry(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form, ActionErrors errors) throws Exception {
	if(form.getValidationET()!=null && form.getValidationET().equals("yes")){
		if(!CommonUtil.checkForEmpty(form.getEvaluatorType())){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required","Evaluator"));
		}
	}
}

public ActionForward checkStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	log.info("validate student reg.no and duplicate entry");
	RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
	Iterator<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> itr = Form.getStudentList().iterator();
	boolean isValideStudent=false;
	String msg = "";
	boolean isDuplicate = false;
	RevaluationOrRetotalingMarksEntryForSubjectWiseTo to1=null;
	if(Form.getRegNo()!=null && !Form.getRegNo().isEmpty()){
		while (itr.hasNext()) {
			RevaluationOrRetotalingMarksEntryForSubjectWiseTo to = (RevaluationOrRetotalingMarksEntryForSubjectWiseTo) itr.next();
			if(Form.getRegNo()!=null && Form.getRegNo().equalsIgnoreCase(to.getRegisterNo())){
				isValideStudent = true;
				if(Form.getEvaluatorType()!=null && !Form.getEvaluatorType().isEmpty()){
					if((to.getMarks1()!=null && !to.getMarks1().isEmpty()) && (Form.getEvaluatorType().equalsIgnoreCase("1"))){
							isDuplicate = true;
					}
				 
				if((to.getMarks2()!=null && !to.getMarks2().isEmpty()) && (Form.getEvaluatorType().equalsIgnoreCase("2"))){
					isDuplicate = true;
					}
				}else{
				if(to.getMarks()!=null && !to.getMarks().isEmpty()){
					isDuplicate = true;
					}
				}
				if(isValideStudent && !isDuplicate){
						to1=to;
				}
				break;
			}
		}
	}
	if(!isValideStudent){
		
		msg = "No Record Found";
	}
	if(isDuplicate){
		msg = "Duplicate Entry";
	}
	if(!msg.isEmpty()){
		request.setAttribute("msg",msg);
	}else{
		List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> list=new ArrayList<RevaluationOrRetotalingMarksEntryForSubjectWiseTo>();
		list.add(to1);
		request.setAttribute("list",list);
	}
	return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_SUBJECTWISE_AJAX);
}

public ActionForward validateStudentMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
	String msg="";
	List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
	if(Form.getExamMark()!=null && !Form.getExamMark().isEmpty()){
		if(!StringUtils.isNumeric(Form.getExamMark())){
			if(examAbscentCode!=null && !examAbscentCode.contains(Form.getExamMark())){
				msg = "Not valid marks";
			}
		}else{
			Double maxMark=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getMaxMarkOfSubject(Form);
			if(maxMark==null){
				msg="No Subject Rules Setting Defined";
			}else if(Double.parseDouble(Form.getExamMark())>maxMark){
				msg = "Mark Exceeded :"+maxMark+" is Max Marks";
			}
		}
	}
	if(msg!=null && !msg.isEmpty())
		request.setAttribute("msg", msg);
	return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_SUBJECTWISE_FOR_MARKS_AJAX);
}

public ActionForward saveMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	
	log.info("Entered RevaluationOrRetotalingMarksEntryForSubjectWiseAction - saveMarks");
	
	RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
	ActionMessages messages=new ActionMessages();
	ActionErrors errors = Form.validate(mapping, request);
	setUserId(request,Form);
	validationSecuredMarksEntry(Form,errors);
	if (errors.isEmpty()) {
		try {
			boolean isUpdated =RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().saveMarks(Form);
			if (isUpdated) {
				messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Marks"));
				saveMessages(request, messages);
				Form.setMainList(null);
				List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> studentDetailsList=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getStudentDetails(Form);
				Form.setStudentList(studentDetailsList);
				setMainStudentList(Form);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Marks"));
				addErrors(request, errors);
			}
			setRequiredDatatoForm(Form, request);
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			Form.setErrorMessage(msg);
			Form.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	} else {
		addErrors(request, errors);
//		setRequiredDatatoForm(newSecuredMarksEntryForm, request);
		log.info("Exit RevaluationOrRetotalingMarksEntryForSubjectWiseAction - saveMarks errors not empty ");
		return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE_RESULT);
	}
	log.info("Entered RevaluationOrRetotalingMarksEntryForSubjectWiseAction - saveMarks");
	return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE_RESULT);
}

	public  void validationSecuredMarksEntry( RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form, ActionErrors errors) throws Exception {
	List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> list=Form.getMainList();
	for (RevaluationOrRetotalingMarksEntryForSubjectWiseTo to: list) {
		boolean isValideMark = false;
		String msg="";
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		if(to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()){
			if(!StringUtils.isNumeric(to.getTheoryMarks())){
				if(examAbscentCode!=null && !examAbscentCode.contains(to.getTheoryMarks())){
					msg = "Not valid marks";
				}
			}else{
				Double maxMark=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getMaxMarkOfSubjectValidation(Form,to.getCourseId(),to.getSchemeNo());
				if(maxMark==null){
					msg="No Subject Rules Setting Defined";
				}else if(Double.parseDouble(Form.getExamMark())>maxMark){
					msg = "Mark Exceeded :"+maxMark+" is Max Marks";
				}
			}
		}else if(to.getRegisterNo()!=null && !to.getRegisterNo().isEmpty()){
			msg="Please Enter The Marks";
		}
		
		if(!msg.isEmpty())
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message",msg+" for  this"+to.getRegisterNo()));
		}
	}
	
	
	public ActionForward getCandidatesForThirdEvaluation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = Form.validate(mapping, request);
		validateEvaluatorType(Form,errors);
		if (errors.isEmpty()) {
			try {
				
				List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> studentDetailsList=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getStudentDetails(Form);
				if(studentDetailsList==null || studentDetailsList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.application.not.found"));
					saveErrors(request, errors);
					//Form.resetFields();
					setRequiredDatatoForm(Form,request);	
					return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE);
				}
				setRequiredDatatoForm(Form,request);
				Collections.sort(studentDetailsList);
				Form.setStudentList(studentDetailsList);
				setMainStudentList(Form);
				setNamesToForm(Form);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
 			setRequiredDatatoForm(Form,request);
			log.info("Exit RevaluationOrRetotallingMarksEntryAction - getCandidateRecord errors not empty ");
			return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE);
		}
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_THIRDEVALUATION);
	}
	
	public ActionForward checkStudentThirdEvlMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("validate student reg.no and duplicate entry");
		RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
		Iterator<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> itr = Form.getStudentList().iterator();
		boolean isValideStudent=false;
		String msg = "";
		boolean isDuplicate = false;
		RevaluationOrRetotalingMarksEntryForSubjectWiseTo to1=null;
		if(Form.getRegNo()!=null && !Form.getRegNo().isEmpty()){
			while (itr.hasNext()) {
				RevaluationOrRetotalingMarksEntryForSubjectWiseTo to = (RevaluationOrRetotalingMarksEntryForSubjectWiseTo) itr.next();
				if(Form.getRegNo()!=null && Form.getRegNo().equalsIgnoreCase(to.getRegisterNo())){
					boolean isApplied=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().isStudentAppliedForThirdEvl(Form,to.getExamRevaluationAppId());
					if(isApplied){
					isValideStudent = true;
					}else{
						msg = "Third evaluation not requested ";
					}
						if(to.getThirdEvlMarks()!=null && !to.getThirdEvlMarks().isEmpty()){
								isDuplicate = true;
						}
					if(isValideStudent && !isDuplicate){
							to1=to;
					}
					break;
				}
			}
		}
		if(!isValideStudent && msg.isEmpty()){
			
			msg = "No Record Found";
		}
		if(isDuplicate){
			msg = "Duplicate Entry";
		}
		if(!msg.isEmpty()){
			request.setAttribute("msg",msg);
		}else{
			List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> list=new ArrayList<RevaluationOrRetotalingMarksEntryForSubjectWiseTo>();
			list.add(to1);
			request.setAttribute("list",list);
		}
		return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_SUBJECTWISE_AJAX);
	}
	
	
	
	public ActionForward saveThirdEvlMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered RevaluationOrRetotalingMarksEntryForSubjectWiseAction - saveMarks");
		
		RevaluationOrRetotalingMarksEntryForSubjectWiseForm Form = (RevaluationOrRetotalingMarksEntryForSubjectWiseForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = Form.validate(mapping, request);
		setUserId(request,Form);
		validationSecuredMarksEntry(Form,errors);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated =RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().saveMarksThirdEvlmarks(Form);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Marks"));
					saveMessages(request, messages);
					Form.setMainList(null);
					List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> studentDetailsList=RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.getInstance().getStudentDetails(Form);
					Form.setStudentList(studentDetailsList);
					setMainStudentList(Form);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Marks"));
					addErrors(request, errors);
				}
				setRequiredDatatoForm(Form, request);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
//			setRequiredDatatoForm(newSecuredMarksEntryForm, request);
			log.info("Exit RevaluationOrRetotalingMarksEntryForSubjectWiseAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_THIRDEVALUATION);
		}
		log.info("Entered RevaluationOrRetotalingMarksEntryForSubjectWiseAction - saveMarks");
		return mapping.findForward(CMSConstants.REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_THIRDEVALUATION);
	}
	private void validateEvaluatorType(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form, ActionErrors errors) throws Exception {
		if(form.getEvaluatorType().equalsIgnoreCase("1") || form.getEvaluatorType().equalsIgnoreCase("2")){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.select.revaluation.or.retotaling"));
		}
	}
}


