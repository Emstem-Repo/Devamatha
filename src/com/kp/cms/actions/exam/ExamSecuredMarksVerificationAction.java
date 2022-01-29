package com.kp.cms.actions.exam;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.constants.CMSExamConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.ExamSecuredMarksVerificationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksVerificationHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.exam.ExamSecuredMarksVerificationTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamSecuredMarksVerificationAction extends BaseDispatchAction {
	ExamSecuredMarksVerificationHandler handler = new ExamSecuredMarksVerificationHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	// On - LOAD
	public ActionForward initExamSecuredMarksVerification(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSecuredMarksEntryHandler marksEntryHandler = ExamSecuredMarksEntryHandler
				.getInstance();
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm) form;
		errors.clear();
		objform.clearPage(mapping, request);
		objform.setsCodeName("sCode");
		objform.setExamType("Regular");
		setRequestedDataToForm(objform, request);
		int examId = 0;
		if(objform.getExamId()!= null &&!objform.getExamId().trim().isEmpty()){
			examId = Integer.parseInt(objform.getExamId());
		}
		objform.setSubjectList(marksEntryHandler.getSubjectCodeName(objform.getsCodeName(), examId));
		objform.setDummyCheckBox(true);
		return mapping
				.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION);
	}

	// To get the mark details GRID
	public ActionForward onSubmitGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateData(objform);
		saveErrors(request, errors);
		
		if (errors.isEmpty()) {

			if (objform.getCheckBox() != null
					&& objform.getCheckBox().equalsIgnoreCase("on")) {
				objform.setBoxCheck("true");
				objform.setCheckBox(null);
			} else {
				objform.setBoxCheck("false");
				objform.setCheckBox(null);
			}
			objform.setExamId(objform.getExamId());
			if (objform.getExamId() != null && objform.getExamId().length() > 0)
				objform.setExamName(handler.getExamNameByExamId(Integer
						.parseInt(objform.getExamId())));

			int subId = 0;
			if (objform.getSubject() != null
					&& objform.getSubject().length() > 0) {
				subId = Integer.parseInt(objform.getSubject());
				String sname = handler.getSubjectName(subId);
				objform.setSubjectName(sname);
				objform.setSubject(objform.getSubject());
				String subCode = handler.getSubjectCode(subId);
				objform.setSubjectCode(subCode);

			}
			if (objform.getSubjectType() != null
					&& objform.getSubjectType().length() > 0) {
				if (objform.getSubjectType().equalsIgnoreCase("T")) {
					objform.setSubjectType("Theory");
					objform.setSubjectTypeId("1");
				}
				if (objform.getSubjectType().equalsIgnoreCase("P"))
					objform.setSubjectType("Practical");
				objform.setSubjectTypeId("0");
			}
			objform.setEvaluatorTypeId(objform.getEvaluatorType());
			objform.setAnswerScriptTypeId(objform.getAnswerScriptType());
			ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
			int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase("Theory") ? 1 : 0;
			if (CommonUtil.checkForEmpty(objform.getSubject())) 
			{
				objform.setListEvaluatorType(entryHandler.getEvaluatorType(Integer.parseInt(objform.getSubject()),intSubjectTypeId, Integer.parseInt(objform.getExamId())));
				objform.setListAnswerScriptType(entryHandler.get_answerScript_type(Integer.parseInt(objform.getSubject()), intSubjectTypeId, Integer.parseInt(objform.getExamId())));
			}
			objform.setRegNoOrRollNumber(handler
					.getsecured_marks_entry_by_settings());
			objform.setListSingleStudents(handler.getSingleStudentMarks());
			return mapping
					.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION_SUBMIT);
		} else {
			objform = retainvalues(objform);
			request.setAttribute("retainValues", "retain");
			ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
			int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase("t") ? 1 : 0;
			if(objform.getSubject()!= null && !objform.getSubject().trim().isEmpty() &&
					objform.getSubjectType()!= null && !objform.getSubjectType().trim().isEmpty() &&
					objform.getExamId()!= null && !objform.getExamId().trim().isEmpty()){
				request.setAttribute("evMap",entryHandler.getEvaluatorType(Integer
						.parseInt(objform.getSubject()), intSubjectTypeId, Integer
						.parseInt(objform.getExamId())));
			}
			
			return mapping
					.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION);
		}
	}

	private ActionErrors validateData(ExamSecuredMarksVerificationForm objform) {
		if (objform.getExamType() != null
				&& !objform.getExamType().equalsIgnoreCase("Internal")) {
			if (objform.getValidationET().equalsIgnoreCase("yes")) {
				if (!CommonUtil.checkForEmpty(objform.getEvaluatorType())
						&& handler.getEvaluatorType_Status(
								objform.getSubject(), objform.getSubjectType(),
								/*objform.getExamName()*/objform.getExamId())) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.evaluatorTypeReq"));
				}
			}
			if (objform.getValidationAST().equalsIgnoreCase("yes")) {
				if (!CommonUtil.checkForEmpty(objform.getAnswerScriptType())
						&& handler.getAnswerScriptType_Status(objform
								.getSubject(), objform.getSubjectType(),
								/*objform.getExamName()*/objform.getExamId())) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.answerScriptTypeReq"));
				}
			}
		}
		return errors;
	}

	// On SUBMIT - To Add
	public ActionForward onSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm) form;
		errors.clear();
		messages.clear();
		
		int added = handler.addChanges(objform);
		if (added > 0) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
			messages.add("messages", message);
		}

		saveMessages(request, messages);
		objform.setListSingleStudents(handler.getSingleStudentMarks());
		
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION_SUBMIT);
		/*return initExamSecuredMarksVerification(mapping, form, request,
				response);*/
	}

	// Retain values
	private ExamSecuredMarksVerificationForm retainvalues(
			ExamSecuredMarksVerificationForm objform) throws Exception {
		objform = handler.retainAllValues(objform);

		return objform;
	}
	
	public void setRequestedDataToForm(ExamSecuredMarksVerificationForm objform,HttpServletRequest request) throws Exception{
		String currentExam = null;
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		//added by Smitha - for new academic year input addition
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
//		Map<Integer, String> examMap = CommonAjaxHandler.getInstance().getExamNameByExamType(objform.getExamType());
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		currentExam = examhandler.getCurrentExamName(objform.getExamType());
		if (currentExam != null) {
			objform.setExamId(currentExam);
			
			request.setAttribute("subjectType", currentExam);
		} else {
			request.setAttribute("subjectType", 0);
			objform.setExamId(null);
		}
		if(objform.getExamId()!=null && !objform.getExamId().isEmpty()&& objform.getsCodeName()!=null && !objform.getsCodeName().isEmpty()
				&& objform.getExamType()!=null && !objform.getExamType().isEmpty()){
		Map<Integer, String> subjectMap=NewSecuredMarksEntryHandler.getInstance().getSubjects(objform.getExamId(),objform.getsCodeName(),objform.getExamType(),String.valueOf(year));
		objform.setSubjectmap(subjectMap);
		}
		//objform.setExamNameList(examNameMap);
		if(examMap!= null){
			request.setAttribute("examMap", examMap);
			objform.setExamNameList(examMap);
		}
		if (currentExam != null) {
			objform.setExamId(currentExam);
		}
		else
		{
			objform.setExamId(null);	
		}
		
	}
	
	// On - LOAD
	public ActionForward initExamSecuredMarksVerificationByPrinter(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm) form;
		errors.clear();
		objform.clearPage(mapping, request);
		objform.setsCodeName("sCode");
		objform.setExamType("Regular");
		setRequestedDataToForm(objform, request);
		/*int examId = 0;
		if(objform.getExamId()!= null &&!objform.getExamId().trim().isEmpty()){
			examId = Integer.parseInt(objform.getExamId());
		}*/
		/*code commented by chandra 
		objform.setSubjectList(marksEntryHandler.getSubjectCodeName(objform.getsCodeName(), examId));
		*/
		objform.setDummyCheckBox(true);
		return mapping
				.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION1);
	}

	// To get the mark details GRID
	public ActionForward onSubmitGrid1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateData(objform);
		if(objform.getExamType()!=null && !objform.getExamType().isEmpty() && objform.getExamType().equalsIgnoreCase("Supplementary")){
			if(objform.getSchemeNo()==null || objform.getSchemeNo().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Scheme No"));
			}
		}
		saveErrors(request, errors);
		
		if (errors.isEmpty()) {

			if (objform.getCheckBox() != null
					&& objform.getCheckBox().equalsIgnoreCase("on")) {
				objform.setBoxCheck("true");
				objform.setCheckBox(null);
			} else {
				objform.setBoxCheck("false");
				objform.setCheckBox(null);
			}
			objform.setExamId(objform.getExamId());
			if (objform.getExamId() != null && objform.getExamId().length() > 0)
				objform.setExamName(handler.getExamNameByExamId(Integer
						.parseInt(objform.getExamId())));

			int subId = 0;
			if (objform.getSubject() != null
					&& objform.getSubject().length() > 0) {
				subId = Integer.parseInt(objform.getSubject());
				String sname = handler.getSubjectName(subId);
				objform.setSubjectName(sname);
				objform.setSubject(objform.getSubject());
				String subCode = handler.getSubjectCode(subId);
				objform.setSubjectCode(subCode);

			}
			if (objform.getSubjectType() != null
					&& objform.getSubjectType().length() > 0) {
				if (objform.getSubjectType().equalsIgnoreCase("T")) {
					objform.setSubjectType("Theory");
					objform.setSubjectTypeId("1");
				}
				if (objform.getSubjectType().equalsIgnoreCase("P"))
					objform.setSubjectType("Practical");
				objform.setSubjectTypeId("0");
			}
			objform.setEvaluatorTypeId(objform.getEvaluatorType());
			objform.setAnswerScriptTypeId(objform.getAnswerScriptType());
			ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
			int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase("Theory") ? 1 : 0;
			if (CommonUtil.checkForEmpty(objform.getSubject())) 
			{
				objform.setListEvaluatorType(entryHandler.getEvaluatorType(Integer.parseInt(objform.getSubject()),intSubjectTypeId, Integer.parseInt(objform.getExamId())));
				objform.setListAnswerScriptType(entryHandler.get_answerScript_type(Integer.parseInt(objform.getSubject()), intSubjectTypeId, Integer.parseInt(objform.getExamId())));
			}
			objform.setRegNoOrRollNumber(handler
					.getsecured_marks_entry_by_settings());
			objform.setListSingleStudents(handler.getSingleStudentMarks());
			return mapping
					.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION_SUBMIT1);
		} else {
			objform = retainvalues(objform);
			request.setAttribute("retainValues", "retain");
			ExamSecuredMarksEntryHandler entryHandler = new ExamSecuredMarksEntryHandler();
			int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase("t") ? 1 : 0;
			if(objform.getSubject()!= null && !objform.getSubject().trim().isEmpty() &&
					objform.getSubjectType()!= null && !objform.getSubjectType().trim().isEmpty() &&
					objform.getExamId()!= null && !objform.getExamId().trim().isEmpty()){
				request.setAttribute("evMap",entryHandler.getEvaluatorType(Integer
						.parseInt(objform.getSubject()), intSubjectTypeId, Integer
						.parseInt(objform.getExamId())));
			}
			
			return mapping
					.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION1);
		}
	}
	
	// On SUBMIT - To Add
	public ActionForward onSubmit1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm) form;
		errors.clear();
		messages.clear();
		errors=objform.validate(mapping, request);
		try{
			setUserId(request, objform);
			validateMarks(objform,errors);
			if(errors.isEmpty()){
				String added = handler.addChangesByPrinter(objform);
				if (added.isEmpty()) {
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
					messages.add("messages", message);
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.studentAttendance.registerNo.marksExists",added));
					saveErrors(request, errors);
				}

				saveMessages(request, messages);
				objform.setListSingleStudents(handler.getSingleStudentMarks());
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION_SUBMIT1);
			}
			
		}catch (Exception e) {
			//log.error("Error occured in caste Entry Action", e);
		}
		
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_VERIFICATION_SUBMIT1);
		/*return initExamSecuredMarksVerification(mapping, form, request,
				response);*/
	}	
	/**
	 * @param objform
	 * @param errors 
	 * @param request
	 * @throws Exception
	 */
	private void validateMarks(ExamSecuredMarksVerificationForm objform, ActionErrors errors ) throws Exception{
		

		boolean isValideMark = false;
		String msg="";
		String reg="";
		String duplicatemsg="";
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		BigDecimal diffEvalu=CMSConstants.EXAM_DIFF_PERCENTAGE;
		double difPer=0;
		if(diffEvalu!=null)
			difPer=diffEvalu.doubleValue();
		String eval="";
		if(objform.getListSingleStudents() != null && !objform.getListSingleStudents().isEmpty()){
			Iterator<ExamSecuredMarksVerificationTO> iterator = objform.getListSingleStudents().iterator();
			if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty()){
				handler.getEvaMap(objform);
			}
			Map<String, String> stuMap = new HashMap<String, String>();
			while (iterator.hasNext()) {
				ExamSecuredMarksVerificationTO to = iterator.next();
				if(to.getRegNo() != null && !to.getRegNo().isEmpty()){
					if(!stuMap.containsKey(to.getRegNo())){
						Double maxMark=handler.getMaxMarkOfSubject(objform,to.getRegNo());
						if(maxMark==null){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.not.defined"));
						}
						if(to.getTheoryMarks() !=null && !to.getTheoryMarks().isEmpty()){
							if(!StringUtils.isNumeric(to.getTheoryMarks())){
								if(examAbscentCode!=null && !examAbscentCode.contains(to.getTheoryMarks())){
									if(msg.isEmpty()){
										msg = to.getRegNo();
									}else{
										msg = msg +","+to.getRegNo();
									}
								}else{
									if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty()){
										Map<Integer,Map<Integer,String>> evaMap=objform.getEvaMap();
										if(evaMap!=null){
											if(evaMap.containsKey(Integer.parseInt(objform.getStudentId()))){
												Map<Integer,String> map=evaMap.get(Integer.parseInt(objform.getStudentId()));
												for (Map.Entry<Integer, String> entry : map.entrySet()) {
													// logic we should implement
													if(StringUtils.isNumeric(entry.getValue())){
														if(eval.isEmpty())
															eval=entry.getValue();
														else
															eval=eval+","+entry.getValue();
													}
												}
//										request.setAttribute("eval", eval);
											}
										}
									}
									isValideMark = true;
								}
							}else{
								if(maxMark==null){
									errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
								}else if(Double.parseDouble(to.getTheoryMarks())>maxMark){
									if(reg.isEmpty()){
										reg=to.getRegNo();
									}else{
										reg=reg+","+to.getRegNo();
									}
								}else {
									if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty()){
										Map<Integer,Map<Integer,String>> evaMap=objform.getEvaMap();
										if(evaMap!=null){
											if(evaMap.containsKey(Integer.parseInt(objform.getStudentId()))){
												Map<Integer,String> map=evaMap.get(Integer.parseInt(objform.getStudentId()));
												for (Map.Entry<Integer, String> entry : map.entrySet()) {
													if(StringUtils.isNumeric(entry.getValue()) && diffEvalu!=null){
														double diffMarks=getDifference(Double.parseDouble(entry.getValue()),Double.parseDouble(to.getTheoryMarks()));
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
//										request.setAttribute("eval", eval);
											}
										}
									}
									isValideMark = true;
								}
							}
						}
					}else{
						if(duplicatemsg.isEmpty())
							duplicatemsg = to.getRegNo();
						else
							duplicatemsg = duplicatemsg + ", "+to.getRegNo();
					}
					stuMap.put(to.getRegNo(), to.getRegNo());
				}
				
			}
		}
		if(!duplicatemsg.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.duplicate.registerNo",duplicatemsg));
		}
		if(!reg.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.registerNo",reg));
		}
		if(!msg.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.msg",msg));
		}
		
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

	//For Printing...
	public ActionForward printRS(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		ExamSecuredMarksVerificationForm objform = (ExamSecuredMarksVerificationForm)form;
		try
		{
			ActionErrors errors = objform.validate(mapping, request);
			ExamSecuredMarksVerificationHandler securedHandler = new ExamSecuredMarksVerificationHandler();
			HttpSession session=request.getSession(true);  
			
		       if(objform.getRegisterNo() != null){
		    	   int schemeNo=0;
					if(objform.getSchemeNo()!=null && !objform.getSchemeNo().trim().isEmpty() && objform.getSchemeNo().trim().length()>0 && StringUtils.isNumeric(objform.getSchemeNo()))
						schemeNo=Integer.parseInt(objform.getSchemeNo());
		    	   ExamSecuredMarksVerificationTO val = securedHandler .get_securedMarkDetails3(objform.getRegisterNo(),schemeNo);
		    	   String studentFullName = null;
		    	   if(val.getStudentName() != null &&val.getStudentName().trim().length() > 0){
		    		   studentFullName = val.getStudentName()+" ";
	    		   }
		    	   if(val.getMiddleName() != null &&val.getMiddleName().trim().length() > 0){
		    		   studentFullName = val.getMiddleName()+" ";
	    		   }
	    		   if(val.getLastName() != null &&val.getLastName().trim().length() > 0){
	    			   studentFullName = val.getLastName()+" ";
	    		   }
		    	   if(val.getRegNo() != null && studentFullName != null){
		    		   
		    		   objform.setNameOfStudent(studentFullName.trim());
		    	   }
		    	   String classname=session.getAttribute("className").toString();
		    	   String className=securedHandler.getclassNameByClassId(Integer.parseInt(classname));
		    	   if(className!=null && !className.isEmpty()){
		    		   objform.setClassName(className);
		    	   }
		    	   //session.setAttribute("className", null);
		       }
				
				if (errors.isEmpty()){
			}
			else
			{
				addErrors(request, errors);
			}
			
		}
		catch (Exception e) {
			 //String msg = super.handleApplicationException(e);
			 return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(objform.getRegisterNo()!= null){
			request.setAttribute("STUDENTREGVALUES", objform);
			return mapping.findForward(CMSExamConstants.EXAM_SECURED_MARKS_VERIFICATION_PRINTER);
		}
			else
			return mapping.findForward(CMSExamConstants.EXAM_SECURED_MARKS_VERIFICATION_SUBMIT1);
	}
	
	/**
	 * This method will set the user in to the form.
	 * @param request
	 * @param form
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}
}
