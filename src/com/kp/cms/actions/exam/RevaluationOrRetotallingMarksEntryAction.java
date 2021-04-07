package com.kp.cms.actions.exam;

import java.util.Calendar;
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


import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewExamMarksEntryHandler;
import com.kp.cms.handlers.exam.RevaluationOrRetotallingMarksEntryHandler;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


public class RevaluationOrRetotallingMarksEntryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(RevaluationOrRetotallingMarksEntryAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRevaluationOrRetotallingMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initRevaluationOrRetotallingMarks input");
		RevaluationOrRetotallingMarksEntryForm Form = (RevaluationOrRetotallingMarksEntryForm) form;// Type casting the Action form to Required Form
		Form.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(Form);// setting the requested data to form
		log.info("Exit initRevaluationOrRetotallingMarks input");
		
		return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
	}
	/**
	 * @param form
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(RevaluationOrRetotallingMarksEntryForm form ) throws Exception{
		//ExamMarksEntryHandler securedhandler = ExamMarksEntryHandler.getInstance();
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(form .getYear()!=null && !form.getYear().isEmpty()){
			year=Integer.parseInt(form.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(form.getExamType(),year); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		form.setExamNameList(examNameMap);// setting the examNameMap to form
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidateRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		RevaluationOrRetotallingMarksEntryForm Form = (RevaluationOrRetotallingMarksEntryForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = Form.validate(mapping, request);
		validateStudentRegOrRollNo(Form,errors);
		if (errors.isEmpty()) {
			try {
				boolean isValidStudent=RevaluationOrRetotallingMarksEntryHandler.getInstance().checkValidStudentRegNo(Form);
				if(!isValidStudent){
					errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Register No"));
					saveErrors(request, errors);
					setRequiredDatatoForm(Form);			
					return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
				}
				List<RevaluationOrRetotallingMarksEntryTo> studentDetailsList=RevaluationOrRetotallingMarksEntryHandler.getInstance().getStudentDetails(Form);
				if(studentDetailsList==null || studentDetailsList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.application.not.found"));
					saveErrors(request, errors);
					//Form.resetFields();
					setRequiredDatatoForm(Form);	
					return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
				}
				Form.setStudentDetailsList(studentDetailsList);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit RevaluationOrRetotallingMarksEntryAction - getCandidateRecord errors not empty ");
			return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
		}
		Form.setFlag(true);
		Form.setFlag1(true);
		request.setAttribute("count", Form.getCount());
		setRequiredDatatoForm(Form);
		log.info("Entered RevaluationOrRetotallingMarksEntryAction - getCandidateRecord");
		return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
	}
	
	
	/**
	 * @param form
	 * @param errors
	 * @throws Exception
	 */
	private void validateStudentRegOrRollNo(RevaluationOrRetotallingMarksEntryForm form ,ActionErrors errors) throws Exception{
		if(form.getRegisterNo()==null || form.getRegisterNo().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.registerNo.requred"));
		}
	}
	public ActionForward saveSelectedSubjectData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ChangeSubjectAction - saveSelectedSubjectData");
		RevaluationOrRetotallingMarksEntryForm Form = (RevaluationOrRetotallingMarksEntryForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request,Form);
		validateMaxMarks(Form,errors);
		if (errors.isEmpty()) {
				try {
				boolean isSaved=RevaluationOrRetotallingMarksEntryHandler.getInstance().saveSelectedSubjectData(Form);
				if(isSaved==false ){
					//errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.revalution.retotaling.marks.update.fail"));
					//saveErrors(request, errors);
				}else{
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.exam.revalution.retotaling.marks.update.success"));
					saveMessages(request, messages);
				
				}
			}catch(BusinessException e) {
				//errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.revalution.retotaling.enter.two.evl.marks"));
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.revalution.retotaling.enter.all.evl.marks"));
				saveErrors(request, errors);
					return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
				} 
				catch (Exception exception) {
					String msg = super.handleApplicationException(exception);
					Form.setErrorMessage(msg);
					Form.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
		}
			Form.resetFields();
		log.info("Exit ChangeSubjectAction - saveSelectedSubjectData");
		return mapping.findForward(CMSConstants.REVALUTION_RETOTALING_MARKS);
	}
	
	
	private void validateMaxMarks(RevaluationOrRetotallingMarksEntryForm form,ActionErrors errors) throws Exception{
		//Double maxMark=RevaluationOrRetotallingMarksEntryHandler.getInstance().getMaxMarkOfSubject(form);
		
				List<RevaluationOrRetotallingMarksEntryTo> list=form.getStudentDetailsList();
				Iterator<RevaluationOrRetotallingMarksEntryTo> itr=list.iterator();
				
				
				List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
				while (itr.hasNext()) {
					RevaluationOrRetotallingMarksEntryTo to = (RevaluationOrRetotallingMarksEntryTo) itr.next();
					if(to.getChecked1()!=null && to.getChecked1().equalsIgnoreCase("on") ){
					if((to.getMarks()!=null && !to.getMarks().isEmpty())){
						Double maxMark=RevaluationOrRetotallingMarksEntryHandler.getInstance().getMaxMarkOfSubject(form,to.getSubjectId());
						String reg="";
						if(maxMark==null){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
						}else{
						if(!StringUtils.isNumeric(to.getMarks())){
							if(examAbscentCode!=null && !examAbscentCode.contains(to.getMarks())){
								String str=to.getName();
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
							}
						}else{
							double marks=Double.parseDouble(to.getMarks());
							if(marks>maxMark){
								if(reg.isEmpty()){
									reg=to.getName();
								}
							}
						}
					  }
						if(!reg.isEmpty()){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
						}
					  }else {
						  //marks1 validation
								  if((to.getMarks1()!=null && !to.getMarks1().isEmpty())){
									Double maxMark=RevaluationOrRetotallingMarksEntryHandler.getInstance().getMaxMarkOfSubject(form,to.getSubjectId());
									String reg="";
									if(maxMark==null){
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
									}else{
									if(!StringUtils.isNumeric(to.getMarks1())){
										if(examAbscentCode!=null && !examAbscentCode.contains(to.getMarks1())){
											String str=to.getName();
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid.evaluator1",str));
										}
									}else{
										double marks=Double.parseDouble(to.getMarks1());
										if(marks>maxMark){
											if(reg.isEmpty()){
												reg=to.getName();
											}
										}
									}
								  }
									if(!reg.isEmpty()){
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject.evaluator1",reg));
									}
								  }
						  
						  //marks2 validation
									  if((to.getMarks2()!=null && !to.getMarks2().isEmpty())){
										Double maxMark=RevaluationOrRetotallingMarksEntryHandler.getInstance().getMaxMarkOfSubject(form,to.getSubjectId());
										String reg="";
										if(maxMark==null){
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
										}else{
										if(!StringUtils.isNumeric(to.getMarks2())){
											if(examAbscentCode!=null && !examAbscentCode.contains(to.getMarks())){
												String str=to.getName();
												errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid.evaluator2",str));
											}
										}else{
											double marks=Double.parseDouble(to.getMarks2());
											if(marks>maxMark){
												if(reg.isEmpty()){
													reg=to.getName();
												}
											}
										}
									  }
										if(!reg.isEmpty()){
											errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject.evaluator2",reg));
										}
									  }
									  
							//new
						 //marks3 validation
									if((to.getMarks3()!=null && !to.getMarks3().isEmpty())){
										Double maxMark=RevaluationOrRetotallingMarksEntryHandler.getInstance().getMaxMarkOfSubject(form,to.getSubjectId());
										String reg="";
										if(maxMark==null){
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
										}else{
											if(!StringUtils.isNumeric(to.getMarks3())){
												if(examAbscentCode!=null && !examAbscentCode.contains(to.getMarks())){
															String str=to.getName();
															errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid.evaluator3",str));
												}
											}else{
														double marks=Double.parseDouble(to.getMarks3());
														if(marks>maxMark){
															if(reg.isEmpty()){
																reg=to.getName();
															}
														}
													}
										 }
										if(!reg.isEmpty()){
														errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject.evaluator3",reg));
										}
									}
									
							//====================== over		
									
					  		}
					}
				}
		}
}
