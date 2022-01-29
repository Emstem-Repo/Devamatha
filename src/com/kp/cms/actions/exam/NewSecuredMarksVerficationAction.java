package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.kp.cms.forms.exam.NewSecuredMarksVerficationForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksVerficationHandler;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class NewSecuredMarksVerficationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewSecuredMarksVerficationAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSecuredMarksVerification(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initSecuredMarksEntry input");
		NewSecuredMarksVerficationForm newSecuredMarksVerficationForm = (NewSecuredMarksVerficationForm) form;// Type casting the Action form to Required Form
		newSecuredMarksVerficationForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(newSecuredMarksVerficationForm, request);// setting the requested data to form
		log.info("Exit initSecuredMarksEntry input");
		
		return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_INPUT);
	}

	/**
	 * @param newSecuredMarksVerficationForm
	 * @param request
	 */
	private void setRequiredDatatoForm(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm,HttpServletRequest request) throws Exception{
//		Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(newSecuredMarksVerficationForm.getExamType());// getting exam list based on exam Type
//		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
//		newSecuredMarksVerficationForm.setExamNameList(examNameMap);// setting the examNameMap to form
		
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(newSecuredMarksVerficationForm.getAcademicYear()!=null && !newSecuredMarksVerficationForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(newSecuredMarksVerficationForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(newSecuredMarksVerficationForm.getExamType(),year); 
		newSecuredMarksVerficationForm.setExamNameList(examMap);// setting the examNameMap to form
		
		
		//Newly Added For Making default Current Exam
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(newSecuredMarksVerficationForm.getExamType());
		if((newSecuredMarksVerficationForm.getExamId()==null || newSecuredMarksVerficationForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			newSecuredMarksVerficationForm.setExamId(currentExam);
		
		if(newSecuredMarksVerficationForm.getExamId()!=null && !newSecuredMarksVerficationForm.getExamId().isEmpty()){
			Map<Integer, String> subjectMap=NewSecuredMarksEntryHandler.getInstance().getSubjects(newSecuredMarksVerficationForm.getExamId(),newSecuredMarksVerficationForm.getDisplaySubType(),newSecuredMarksVerficationForm.getExamType(),String.valueOf(year));
			//newSecuredMarksVerficationForm.setSubjectList(subjectList);
			newSecuredMarksVerficationForm.setSubjectMap(subjectMap);
		}
		
		if(newSecuredMarksVerficationForm.getSubjectId()!=null && !newSecuredMarksVerficationForm.getSubjectId().isEmpty()){
			String value = "";
			Map<String, String> subjectTypeMap = new HashMap<String, String>();
			int subjectId = Integer.parseInt(newSecuredMarksVerficationForm.getSubjectId());
			value = CommonAjaxHandler.getInstance().getSubjectsTypeBySubjectId(subjectId);
			if (value.equalsIgnoreCase("T")) {
				subjectTypeMap.put("T", "Theory");
			}
			if (value.equalsIgnoreCase("P")) {
				subjectTypeMap.put("P", "Practical");
			}
			if (value.equalsIgnoreCase("B")) {
				subjectTypeMap.put("T", "Theory");
				subjectTypeMap.put("P", "Practical");
				value = "t";
			}
			if(newSecuredMarksVerficationForm.getSubjectType()==null || newSecuredMarksVerficationForm.getSubjectType().isEmpty())
			newSecuredMarksVerficationForm.setSubjectType(value.toUpperCase());
			newSecuredMarksVerficationForm.setSubjectTypeList(subjectTypeMap);
			int examId = Integer.parseInt(newSecuredMarksVerficationForm.getExamId());

			int subjectTypeId = 0;
			
			if (newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("t"))
				subjectTypeId = 1;
			else if (newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("p"))
				subjectTypeId = 0;
			ExamSecuredMarksEntryHandler securedhandler = new ExamSecuredMarksEntryHandler();
			Map<Integer, String>  evaluatorTypeMap = securedhandler.getEvaluatorType(subjectId,subjectTypeId, examId);
			if(evaluatorTypeMap!=null && !evaluatorTypeMap.isEmpty() && !newSecuredMarksVerficationForm.getExamType().equalsIgnoreCase("Internal")){
				newSecuredMarksVerficationForm.setValidationET("yes");
				newSecuredMarksVerficationForm.setDisplayET("yes");
			}else{
				newSecuredMarksVerficationForm.setValidationET("no");
				newSecuredMarksVerficationForm.setDisplayET("no");
			}
			newSecuredMarksVerficationForm.setEvaluatorTypeMap(evaluatorTypeMap);
			
			Map<Integer, String> answerScriptTypeMap = securedhandler.get_answerScript_type(subjectId, subjectTypeId, examId);
			newSecuredMarksVerficationForm.setAnswerScriptTypeMap(answerScriptTypeMap);
			if(!answerScriptTypeMap.isEmpty()  && !newSecuredMarksVerficationForm.getExamType().equalsIgnoreCase("Internal")){
				newSecuredMarksVerficationForm.setValidationAST("yes");
				newSecuredMarksVerficationForm.setDisplayAST("yes");
			}else{
				newSecuredMarksVerficationForm.setValidationAST("no");
				newSecuredMarksVerficationForm.setDisplayAST("no");
			}
		}else{
			newSecuredMarksVerficationForm.setSubjectType(null);
			newSecuredMarksVerficationForm.setSubjectTypeList(null);
			newSecuredMarksVerficationForm.setEvaluatorTypeMap(null);
			newSecuredMarksVerficationForm.setAnswerScriptTypeMap(null);
			newSecuredMarksVerficationForm.setValidationET("no");
			newSecuredMarksVerficationForm.setDisplayET("yes");
			newSecuredMarksVerficationForm.setValidationAST("no");
			newSecuredMarksVerficationForm.setDisplayAST("yes");
		}
//		if(newSecuredMarksVerficationForm.getCheckBox()!=null && !newSecuredMarksVerficationForm.getCheckBox().isEmpty() && newSecuredMarksVerficationForm.getCheckBox().equalsIgnoreCase("off"))
//			newSecuredMarksVerficationForm.setDummyCheckBox(false);
//		else {
//			newSecuredMarksVerficationForm.setCheckBox(null);
//			newSecuredMarksVerficationForm.setDummyCheckBox(true);
//		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		NewSecuredMarksVerficationForm newSecuredMarksVerficationForm= (NewSecuredMarksVerficationForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSecuredMarksVerficationForm.validate(mapping, request);
		validateExamMarksEntry(newSecuredMarksVerficationForm,errors);
		if (errors.isEmpty()) {
			try {
				List<StudentMarksTO> studentList=NewSecuredMarksVerficationHandler.getInstance().getStudentForInput(newSecuredMarksVerficationForm);// getting the student list for input search
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newSecuredMarksVerficationForm, request);			
					return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_INPUT);
				}
				setRequiredDatatoForm(newSecuredMarksVerficationForm, request);
				newSecuredMarksVerficationForm.setStudentList(studentList);
				setMainStudentList(newSecuredMarksVerficationForm);
				setNamesToForm(newSecuredMarksVerficationForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSecuredMarksVerficationForm.setErrorMessage(msg);
				newSecuredMarksVerficationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSecuredMarksVerficationForm, request);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_INPUT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_RESULT);
	}
	
	/**
	 * @param newSecuredMarksVerficationForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateExamMarksEntry(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm, ActionErrors errors) throws Exception {
		if(newSecuredMarksVerficationForm.getValidationAST()!=null && newSecuredMarksVerficationForm.getValidationAST().equals("yes")){
			if(!CommonUtil.checkForEmpty(newSecuredMarksVerficationForm.getAnswerScriptType())){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required", "AnswerScript"));
			}
		}
		if(newSecuredMarksVerficationForm.getValidationET()!=null && newSecuredMarksVerficationForm.getValidationET().equals("yes")){
			if(!CommonUtil.checkForEmpty(newSecuredMarksVerficationForm.getEvaluatorType())){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Evaluator"));
			}
		}
		if(newSecuredMarksVerficationForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(newSecuredMarksVerficationForm.getSchemeNo()==null || newSecuredMarksVerficationForm.getSchemeNo().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Scheme No"));
		}
	}
	
	/**
	 * @param newSecuredMarksVerficationForm
	 * @throws Exception
	 */
	private void setMainStudentList(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception{
		List<StudentMarksTO> mainList=new ArrayList<StudentMarksTO>();
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		mainList.add(new StudentMarksTO());
		newSecuredMarksVerficationForm.setMainList(mainList);
	}
	
	/**
	 * @param newSecuredMarksVerficationForm
	 */
	private void setNamesToForm(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm)throws Exception {
		
		newSecuredMarksVerficationForm.setSubjectCode(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSecuredMarksVerficationForm.getSubjectId()),"Subject",true,"code"));
		newSecuredMarksVerficationForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSecuredMarksVerficationForm.getExamId()),"ExamDefinitionBO",true,"examCode"));
		newSecuredMarksVerficationForm.setSubjectName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSecuredMarksVerficationForm.getSubjectId()),"Subject",true,"name"));
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("validate student reg.no and duplicate entry");
		NewSecuredMarksVerficationForm newSecuredMarksVerficationForm= (NewSecuredMarksVerficationForm) form;// Type casting the Action form to Required Form
		Iterator<StudentMarksTO> itr = newSecuredMarksVerficationForm.getStudentList().iterator();
		boolean isValideStudent=false;
		String msg = "";
		String regMsg="";
		boolean isPresent = false;
		StudentMarksTO to=null;
		if(newSecuredMarksVerficationForm.getRegNo()!=null && !newSecuredMarksVerficationForm.getRegNo().isEmpty()){
			while (itr.hasNext()) {
				StudentMarksTO studentMarksTO = (StudentMarksTO) itr.next();
				if(newSecuredMarksVerficationForm.getRegNo()!=null && newSecuredMarksVerficationForm.getRegNo().equalsIgnoreCase(studentMarksTO.getRegisterNo())){
					isValideStudent = true;
					if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("t")){
						if(studentMarksTO.getTheoryMarks()!=null && !studentMarksTO.getTheoryMarks().isEmpty()){
							isPresent=true;
						}
					}
					if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("p")){
						if(studentMarksTO.getPracticalMarks()!=null && !studentMarksTO.getPracticalMarks().isEmpty()){
							isPresent=true;
						}
					}
					if(isValideStudent && isPresent){
						to=studentMarksTO;
						if(newSecuredMarksVerficationForm.getCheckBox().equalsIgnoreCase("yes"))
							regMsg=to.getRegisterNo()+"  "+to.getName();
						else
							regMsg=to.getName();
						
						if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("t")){
							to.setMarksAbscent(to.getTheoryMarks());
						}
						if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("p")){
							to.setMarksAbscent(to.getPracticalMarks());
						}
						if(studentMarksTO.getMistake()!=null && studentMarksTO.getMistake().equalsIgnoreCase("on"))
							to.setMistake("on");
						else
							to.setMistake("off");
					}
					break;
				}
			}
		}
		if(!isValideStudent){
			msg = "No Record Found";
		}else if(!isPresent){
			msg ="Marks not Presented for Selected subject Type";
		}
		if(!msg.isEmpty() ){
			request.setAttribute("msg",msg);
		}else{
			List<StudentMarksTO> list=new ArrayList<StudentMarksTO>();
			list.add(to);
			request.setAttribute("list",list);
			if(!regMsg.isEmpty())
				request.setAttribute("regMsg",regMsg);
		}
		return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_AJAX);
	}
	
	
//	/**
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ActionForward validateStudentMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		NewSecuredMarksVerficationForm newSecuredMarksVerficationForm= (NewSecuredMarksVerficationForm) form;// Type casting the Action form to Required Form
//		boolean isValideMark = false;
//		String msg="";
//		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
//		if(newSecuredMarksVerficationForm.getExamMark()!=null && !newSecuredMarksVerficationForm.getExamMark().isEmpty()){
//			if(!StringUtils.isNumeric(newSecuredMarksVerficationForm.getExamMark())){
//				if(examAbscentCode!=null && !examAbscentCode.contains(newSecuredMarksVerficationForm.getExamMark())){
//					msg = "Not valide marks";
//				}
//			}else{
//				Double maxMark=NewSecuredMarksVerficationHandler.getInstance().getMaxMarkOfSubject(newSecuredMarksVerficationForm);
//				if(maxMark==null){
//					msg="No Subject Rules Setting Defined";
//				}else if(Double.parseDouble(newSecuredMarksVerficationForm.getExamMark())>maxMark){
//					msg = "Mark Exceeded :"+maxMark+" is Max Marks";
//				}else {
//					isValideMark = true;
//				}
//			}
//		}
//		if(isValideMark)
//			request.setAttribute("msg","");
//		else
//			request.setAttribute("msg", msg);
//		return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_MARKS_AJAX);
//	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		
		NewSecuredMarksVerficationForm newSecuredMarksVerficationForm= (NewSecuredMarksVerficationForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSecuredMarksVerficationForm.validate(mapping, request);
		setUserId(request,newSecuredMarksVerficationForm);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated =NewSecuredMarksVerficationHandler.getInstance().saveMarks(newSecuredMarksVerficationForm);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Marks"));
					saveMessages(request, messages);
					newSecuredMarksVerficationForm.setMainList(null);
					List<StudentMarksTO> studentList=NewSecuredMarksVerficationHandler.getInstance().getStudentForInput(newSecuredMarksVerficationForm);// getting the student list for input search
					newSecuredMarksVerficationForm.setStudentList(studentList);
					setMainStudentList(newSecuredMarksVerficationForm);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.updatesuccess","Marks"));
					addErrors(request, errors);
				}
				setRequiredDatatoForm(newSecuredMarksVerficationForm, request);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSecuredMarksVerficationForm.setErrorMessage(msg);
				newSecuredMarksVerficationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSecuredMarksVerficationForm, request);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		return mapping.findForward(CMSConstants.SECURED_MARKS_VERIFICATION_RESULT);
	}
}
