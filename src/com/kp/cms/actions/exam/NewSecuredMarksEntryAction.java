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
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class NewSecuredMarksEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewSecuredMarksEntryAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSecuredMarksEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initSecuredMarksEntry input");
		NewSecuredMarksEntryForm newSecuredMarksEntryForm = (NewSecuredMarksEntryForm) form;// Type casting the Action form to Required Form
		newSecuredMarksEntryForm.resetFields();//Reseting the fields for input jsp
		if(newSecuredMarksEntryForm.getExamType() == null || newSecuredMarksEntryForm.getExamType().isEmpty()){
			newSecuredMarksEntryForm.setExamType("Regular");
		}
		setRequiredDatatoForm(newSecuredMarksEntryForm, request);// setting the requested data to form
		log.info("Exit initSecuredMarksEntry input");
		
		return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_INPUT);
	}

	/**
	 * @param newSecuredMarksEntryForm
	 * @param request
	 */
	private void setRequiredDatatoForm(NewSecuredMarksEntryForm newSecuredMarksEntryForm,HttpServletRequest request) throws Exception{
//		Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(newSecuredMarksEntryForm.getExamType());// getting exam list based on exam Type
//		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
//		newSecuredMarksEntryForm.setExamNameList(examNameMap);// setting the examNameMap to form
		
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(newSecuredMarksEntryForm.getAcademicYear()!=null && !newSecuredMarksEntryForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(newSecuredMarksEntryForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(newSecuredMarksEntryForm.getExamType(),year); 
		newSecuredMarksEntryForm.setExamNameList(examMap);// setting the examNameMap to form
		//Newly Added For Making default Current Exam
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(newSecuredMarksEntryForm.getExamType());
		if((newSecuredMarksEntryForm.getExamId()==null || newSecuredMarksEntryForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			newSecuredMarksEntryForm.setExamId(currentExam);
		
		if(newSecuredMarksEntryForm.getExamId()!=null && !newSecuredMarksEntryForm.getExamId().isEmpty()){
			
			Map<Integer, String> subjectMap=NewSecuredMarksEntryHandler.getInstance().getSubjects(newSecuredMarksEntryForm.getExamId(),newSecuredMarksEntryForm.getDisplaySubType(),newSecuredMarksEntryForm.getExamType(),newSecuredMarksEntryForm.getAcademicYear());
			//newSecuredMarksEntryForm.setSubjectList(subjectList);
			newSecuredMarksEntryForm.setSubjectMap(subjectMap);
		}
		
		if(newSecuredMarksEntryForm.getSubjectId()!=null && !newSecuredMarksEntryForm.getSubjectId().isEmpty()){
			String value = "";
			Map<String, String> subjectTypeMap = new HashMap<String, String>();
			int subjectId = Integer.parseInt(newSecuredMarksEntryForm.getSubjectId());
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
			if(newSecuredMarksEntryForm.getSubjectType()==null || newSecuredMarksEntryForm.getSubjectType().isEmpty())
			newSecuredMarksEntryForm.setSubjectType(value.toUpperCase());
			newSecuredMarksEntryForm.setSubjectTypeList(subjectTypeMap);
			int examId = Integer.parseInt(newSecuredMarksEntryForm.getExamId());

			int subjectTypeId = 0;
			
			if (newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t"))
				subjectTypeId = 1;
			else if (newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p"))
				subjectTypeId = 0;
			ExamSecuredMarksEntryHandler securedhandler = new ExamSecuredMarksEntryHandler();
			Map<Integer, String>  evaluatorTypeMap = securedhandler.getEvaluatorType(subjectId,subjectTypeId, examId);
			if(evaluatorTypeMap!=null && !evaluatorTypeMap.isEmpty() && !newSecuredMarksEntryForm.getExamType().equalsIgnoreCase("Internal")){
				newSecuredMarksEntryForm.setValidationET("yes");
				newSecuredMarksEntryForm.setDisplayET("yes");
			}else{
				newSecuredMarksEntryForm.setValidationET("no");
				newSecuredMarksEntryForm.setDisplayET("no");
			}
			newSecuredMarksEntryForm.setEvaluatorTypeMap(evaluatorTypeMap);
			
			Map<Integer, String> answerScriptTypeMap = securedhandler.get_answerScript_type(subjectId, subjectTypeId, examId);
			newSecuredMarksEntryForm.setAnswerScriptTypeMap(answerScriptTypeMap);
			if(!answerScriptTypeMap.isEmpty()  && !newSecuredMarksEntryForm.getExamType().equalsIgnoreCase("Internal")){
				newSecuredMarksEntryForm.setValidationAST("yes");
				newSecuredMarksEntryForm.setDisplayAST("yes");
			}else{
				newSecuredMarksEntryForm.setValidationAST("no");
				newSecuredMarksEntryForm.setDisplayAST("no");
			}
		}else{
			newSecuredMarksEntryForm.setSubjectType(null);
			newSecuredMarksEntryForm.setSubjectTypeList(null);
			newSecuredMarksEntryForm.setEvaluatorTypeMap(null);
			newSecuredMarksEntryForm.setAnswerScriptTypeMap(null);
			newSecuredMarksEntryForm.setValidationET("no");
			newSecuredMarksEntryForm.setDisplayET("yes");
			newSecuredMarksEntryForm.setValidationAST("no");
			newSecuredMarksEntryForm.setDisplayAST("yes");
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
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		NewSecuredMarksEntryForm newSecuredMarksEntryForm = (NewSecuredMarksEntryForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSecuredMarksEntryForm.validate(mapping, request);
		validateExamMarksEntry(newSecuredMarksEntryForm,errors);
		if (errors.isEmpty()) {
			try {
				// Commented Because Of Change In Requirement
				/*boolean checkSecuredMarksEntry=NewSecuredMarksEntryHandler.getInstance().checkMarksEnteredThroughMarksEntry(newSecuredMarksEntryForm);//checking whether marks entered through secured marks entry
				if(checkSecuredMarksEntry){// if the marks are entered through secured marks entry display message in first screen
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.marksentry.marks.through.marksEntry"));
					addErrors(request, errors);
					setRequiredDatatoForm(newSecuredMarksEntryForm, request);			
					return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_INPUT);
				}*/
				List<StudentMarksTO> studentList=NewSecuredMarksEntryHandler.getInstance().getStudentForInput(newSecuredMarksEntryForm);// getting the student list for input search
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newSecuredMarksEntryForm, request);			
					return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_INPUT);
				}
				setRequiredDatatoForm(newSecuredMarksEntryForm, request);
				Collections.sort(studentList);
				newSecuredMarksEntryForm.setStudentList(studentList);
				setMainStudentList(newSecuredMarksEntryForm);
				setNamesToForm(newSecuredMarksEntryForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSecuredMarksEntryForm.setErrorMessage(msg);
				newSecuredMarksEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSecuredMarksEntryForm, request);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_INPUT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_RESULT);
	}
	
	/**
	 * @param newSecuredMarksEntryForm
	 */
	private void setNamesToForm(NewSecuredMarksEntryForm newSecuredMarksEntryForm)throws Exception {
		
		newSecuredMarksEntryForm.setSubjectCode(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSecuredMarksEntryForm.getSubjectId()),"Subject",true,"code"));
		newSecuredMarksEntryForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSecuredMarksEntryForm.getExamId()),"ExamDefinitionBO",true,"name"));
		newSecuredMarksEntryForm.setSubjectName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSecuredMarksEntryForm.getSubjectId()),"Subject",true,"name"));
		
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @throws Exception
	 */
	private void setMainStudentList(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
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
		newSecuredMarksEntryForm.setMainList(mainList);
	}

	/**
	 * @param newSecuredMarksEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateExamMarksEntry(NewSecuredMarksEntryForm newSecuredMarksEntryForm, ActionErrors errors) throws Exception {
		if(newSecuredMarksEntryForm.getValidationAST()!=null && newSecuredMarksEntryForm.getValidationAST().equals("yes")){
			if(!CommonUtil.checkForEmpty(newSecuredMarksEntryForm.getAnswerScriptType())){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required", "AnswerScript"));
			}
		}
		if(newSecuredMarksEntryForm.getValidationET()!=null && newSecuredMarksEntryForm.getValidationET().equals("yes")){
			if(!CommonUtil.checkForEmpty(newSecuredMarksEntryForm.getEvaluatorType())){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Evaluator"));
			}
		}
		
		if(newSecuredMarksEntryForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(newSecuredMarksEntryForm.getSchemeNo()==null || newSecuredMarksEntryForm.getSchemeNo().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Scheme No"));
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
	public ActionForward checkStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("validate student reg.no and duplicate entry");
		NewSecuredMarksEntryForm newSecuredMarksEntryForm = (NewSecuredMarksEntryForm) form;// Type casting the Action form to Required Form
		Iterator<StudentMarksTO> itr = newSecuredMarksEntryForm.getStudentList().iterator();
		boolean isValideStudent=false;
		String msg = "";
		boolean isDuplicate = false;
		boolean isAbsent = false;
		StudentMarksTO to=null;
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		if(newSecuredMarksEntryForm.getRegNo()!=null && !newSecuredMarksEntryForm.getRegNo().isEmpty()){

			while (itr.hasNext()) {
				StudentMarksTO studentMarksTO = (StudentMarksTO) itr.next();
				if(newSecuredMarksEntryForm.getRegNo()!=null && newSecuredMarksEntryForm.getRegNo().equalsIgnoreCase(studentMarksTO.getRegisterNo())){
					isValideStudent = true;
					if(studentMarksTO.getIsTheory()){
						if(studentMarksTO.getTheoryMarks()!=null && !studentMarksTO.getTheoryMarks().isEmpty()){
							if(!examAbscentCode.contains(studentMarksTO.getTheoryMarks())){
								isDuplicate = true;
							}else{
								isAbsent = true;
							}
						}
					}
					if(studentMarksTO.getIsPractical()){
						if(studentMarksTO.getPracticalMarks()!=null && !studentMarksTO.getPracticalMarks().isEmpty()){
							if(!examAbscentCode.contains(studentMarksTO.getPracticalMarks())){
								isDuplicate = true;
							}else{
								isAbsent = true;
							}
						}
					}
					if(isValideStudent && !isDuplicate){
						if(isAbsent){
							to=studentMarksTO;
							if(to.getIsTheory())
								to.setMarksAbscent(to.getTheoryMarks());
							if(to.getIsPractical())
								to.setMarksAbscent(to.getPracticalMarks());
							to.setRetest("true");
						}else{
							to=studentMarksTO;
						}
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
			List<StudentMarksTO> list=new ArrayList<StudentMarksTO>();
			list.add(to);
			request.setAttribute("list",list);
		}
		return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_AJAX);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateStudentMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewSecuredMarksEntryForm newSecuredMarksEntryForm = (NewSecuredMarksEntryForm) form;// Type casting the Action form to Required Form
		boolean isValideMark = false;
		String msg="";
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		BigDecimal diffEvalu=CMSConstants.EXAM_DIFF_PERCENTAGE;
		double difPer=0;
		if(diffEvalu!=null)
			difPer=diffEvalu.doubleValue();
		String eval="";
		if(newSecuredMarksEntryForm.getExamMark()!=null && !newSecuredMarksEntryForm.getExamMark().isEmpty()){
			if(!StringUtils.isNumeric(newSecuredMarksEntryForm.getExamMark())){
				if(examAbscentCode!=null && !examAbscentCode.contains(newSecuredMarksEntryForm.getExamMark())){
					msg = "Not valid marks";
				}else{
					if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
						Map<Integer,Map<Integer,String>> evaMap=newSecuredMarksEntryForm.getEvaMap();
						if(evaMap!=null){
							if(evaMap.containsKey(Integer.parseInt(newSecuredMarksEntryForm.getStudentId()))){
								Map<Integer,String> map=evaMap.get(Integer.parseInt(newSecuredMarksEntryForm.getStudentId()));
								for (Map.Entry<Integer, String> entry : map.entrySet()) {
									// logic we should implement
									if(StringUtils.isNumeric(entry.getValue())){
										if(eval.isEmpty())
											eval=entry.getValue();
										else
											eval=eval+","+entry.getValue();
									}
								}
								request.setAttribute("eval", eval);
							}
						}
					}
					isValideMark = true;
				}
			}else{
				Double maxMark=NewSecuredMarksEntryHandler.getInstance().getMaxMarkOfSubject(newSecuredMarksEntryForm);
				if(maxMark==null){
					msg="No Subject Rules Setting Defined";
				}else if(Double.parseDouble(newSecuredMarksEntryForm.getExamMark())>maxMark){
					msg = "Mark Exceeded :"+maxMark+" is Max Marks";
				}else {
					if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
						Map<Integer,Map<Integer,String>> evaMap=newSecuredMarksEntryForm.getEvaMap();
						if(evaMap!=null){
							if(evaMap.containsKey(Integer.parseInt(newSecuredMarksEntryForm.getStudentId()))){
								Map<Integer,String> map=evaMap.get(Integer.parseInt(newSecuredMarksEntryForm.getStudentId()));
								for (Map.Entry<Integer, String> entry : map.entrySet()) {
									if(StringUtils.isNumeric(entry.getValue()) && diffEvalu!=null){
										double diffMarks=getDifference(Double.parseDouble(entry.getValue()),Double.parseDouble(newSecuredMarksEntryForm.getExamMark()));
										if(diffMarks>=difPer){
											if(eval.isEmpty())
												eval=entry.getValue();
											else
												eval=eval+","+entry.getValue();
										}
									}else{
										if(eval.isEmpty())
											eval=entry.getValue();
										else
											eval=eval+","+entry.getValue();
									}
								}
								request.setAttribute("eval", eval);
							}
						}
					}
					isValideMark = true;
				}
			}
		}
		if(isValideMark)
			request.setAttribute("msg","");
		else
			request.setAttribute("msg", msg);
		return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_MARKS_AJAX);
	}
	/**
	 * @param prevMarks
	 * @param examMark
	 * @return
	 */
	private double getDifference(double prevMarks, double examMark) {

		double diff=((Math.max(prevMarks,examMark)-Math.min(prevMarks,examMark))/Math.max(prevMarks,examMark))*100;
		return diff;

	}

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
		
		NewSecuredMarksEntryForm newSecuredMarksEntryForm = (NewSecuredMarksEntryForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSecuredMarksEntryForm.validate(mapping, request);
		setUserId(request,newSecuredMarksEntryForm);
		validationSecuredMarksEntry(newSecuredMarksEntryForm,errors);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated =NewSecuredMarksEntryHandler.getInstance().saveMarks(newSecuredMarksEntryForm);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Marks"));
					saveMessages(request, messages);
					newSecuredMarksEntryForm.setMainList(null);
					List<StudentMarksTO> studentList=NewSecuredMarksEntryHandler.getInstance().getStudentForInput(newSecuredMarksEntryForm);// getting the student list for input search
					newSecuredMarksEntryForm.setStudentList(studentList);
					setMainStudentList(newSecuredMarksEntryForm);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Marks"));
					addErrors(request, errors);
				}
				setRequiredDatatoForm(newSecuredMarksEntryForm, request);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSecuredMarksEntryForm.setErrorMessage(msg);
				newSecuredMarksEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
//			setRequiredDatatoForm(newSecuredMarksEntryForm, request);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		return mapping.findForward(CMSConstants.SECURED_MARKS_ENTRY_RESULT);
	}

	/**
	 * @param newSecuredMarksEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validationSecuredMarksEntry( NewSecuredMarksEntryForm newSecuredMarksEntryForm, ActionErrors errors) throws Exception {
		List<StudentMarksTO> list=newSecuredMarksEntryForm.getMainList();
		for (StudentMarksTO to: list) {
			boolean isValideMark = false;
			String msg="";
			List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
			BigDecimal diffEvalu=CMSConstants.EXAM_DIFF_PERCENTAGE;
			double difPer=0;
			if(diffEvalu!=null)
				difPer=diffEvalu.doubleValue();
			String eval="";
			String examMarks="";
			if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t"))
				examMarks=to.getTheoryMarks();
			else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p"))
				examMarks=to.getTheoryMarks();
			
			if(examMarks!=null && !examMarks.isEmpty()){
				//added by mehaboob
				Double maxMark=NewSecuredMarksEntryHandler.getInstance().getMaxMarkOfSubject(newSecuredMarksEntryForm);
				//end
				if(!StringUtils.isNumeric(examMarks)){
					if(examAbscentCode!=null && !examAbscentCode.contains(examMarks)){
						msg = "Not valide marks";
					}else{
						if(!to.isMarksValidationCompleted()){
							if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
								Map<Integer,Map<Integer,String>> evaMap=newSecuredMarksEntryForm.getEvaMap();
								if(evaMap!=null){
									if(evaMap.containsKey(to.getStudentId())){
										Map<Integer,String> map=evaMap.get(to.getStudentId());
										for (Map.Entry<Integer, String> entry : map.entrySet()) {
											// logic we should implement
											if(StringUtils.isNumeric(entry.getValue())){
												if(eval.isEmpty())
													eval=entry.getValue();
												else
													eval=eval+","+entry.getValue();
											}
										}
										if(!eval.isEmpty())
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message",eval+" for "+to.getRegisterNo()));
									}
								}
							}
						}
						isValideMark = true;
					}
				}else{
					//commented by mehaboob
					//Double maxMark=NewSecuredMarksEntryHandler.getInstance().getMaxMarkOfSubject(newSecuredMarksEntryForm);
					if(maxMark==null){
						msg="No Subject Rules Setting Defined";
					}else if(Double.parseDouble(examMarks)>maxMark){
						msg = "Mark Exceeded :"+maxMark+" is Max Marks";
					}else {
						if(!to.isMarksValidationCompleted()){
							if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
								Map<Integer,Map<Integer,String>> evaMap=newSecuredMarksEntryForm.getEvaMap();
								if(evaMap!=null){
									if(evaMap.containsKey(to.getStudentId())){
										Map<Integer,String> map=evaMap.get(to.getStudentId());
										for (Map.Entry<Integer, String> entry : map.entrySet()) {
											if(StringUtils.isNumeric(entry.getValue()) && diffEvalu!=null){
												double diffMarks=getDifference(Double.parseDouble(entry.getValue()),Double.parseDouble(examMarks));
												if(diffMarks>=difPer){
													if(eval.isEmpty())
														eval=entry.getValue();
													else
														eval=eval+","+entry.getValue();
												}
											}else{
												if(eval.isEmpty())
													eval=entry.getValue();
												else
													eval=eval+","+entry.getValue();
											}
										}
										if(!eval.isEmpty())
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message",eval+" for "+to.getRegisterNo()));
									}
								}
							}
						}
						isValideMark = true;
					}
				}
				newSecuredMarksEntryForm.setMaxMarks(maxMark.intValue());
			}
			
			if(!msg.isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message",msg+" for "+to.getRegisterNo()));
		}
		
	}
}