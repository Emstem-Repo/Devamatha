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
import com.kp.cms.forms.exam.RevaluationMarksUpdateForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.RevaluationMarksUpdateHandler;
import com.kp.cms.to.exam.RevaluationMarksUpdateTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


public class RevaluationMarksUpdateAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(RevaluationMarksUpdateAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRevaluationMarksUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initRevaluationMarksUpdate ");
		RevaluationMarksUpdateForm revaluationMarksUpdateForm  = (RevaluationMarksUpdateForm)form;
		revaluationMarksUpdateForm.resetFields();
		try{
			
			setRequiredDatatoForm(revaluationMarksUpdateForm);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			revaluationMarksUpdateForm.setErrorMessage(msg);
			revaluationMarksUpdateForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initRevaluationMarksUpdate ");
		
		return mapping.findForward("revaluationMarksUpdate");
	}
	
	/**
	 * @param form
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(RevaluationMarksUpdateForm form) throws Exception{
		//ExamMarksEntryHandler securedhandler = ExamMarksEntryHandler.getInstance();
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(form .getAcademicYear()!=null && !form.getAcademicYear().isEmpty()){
			year=Integer.parseInt(form.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(form.getExamType(),year); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		form.setExamNameMap(examNameMap);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidatesRecords(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered RevaluationMarksUpdateAction - getCandidateRecord");
		RevaluationMarksUpdateForm revaluationMarksUpdateForm  = (RevaluationMarksUpdateForm)form;
		ActionErrors errors = revaluationMarksUpdateForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			try {
				
				List<RevaluationMarksUpdateTo> studentDetailsList=RevaluationMarksUpdateHandler.getInstance().getStudentDetails(revaluationMarksUpdateForm);
				if(studentDetailsList==null || studentDetailsList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					//Form.resetFields();
					setRequiredDatatoForm(revaluationMarksUpdateForm);	
					return mapping.findForward("revaluationMarksUpdate");
				}
				revaluationMarksUpdateForm.setStudentDetailsList(studentDetailsList);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				revaluationMarksUpdateForm.setErrorMessage(msg);
				revaluationMarksUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit RevaluationMarksUpdateAction - getCandidatesRecords errors not empty ");
			return mapping.findForward("revaluationMarksUpdate");
		}
		revaluationMarksUpdateForm.setFlag(true);
		setRequiredDatatoForm(revaluationMarksUpdateForm);
		log.info("Entered RevaluationMarksUpdateAction - getCandidatesRecords");
		return mapping.findForward("revaluationMarksUpdate");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward thirdEvaluation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered RevaluationMarksUpdateAction - thirdEvaluation");
		RevaluationMarksUpdateForm revaluationMarksUpdateForm  = (RevaluationMarksUpdateForm)form;
			ActionErrors errors = new ActionErrors();
		setUserId(request,revaluationMarksUpdateForm);
		
		if (errors.isEmpty()) {
				try {
				RevaluationMarksUpdateHandler.getInstance().thirdEvaluation(revaluationMarksUpdateForm);
				/*if(isSaved==false ){
					//errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.revalution.retotaling.marks.update.fail"));
					//saveErrors(request, errors);
				}else{
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.exam.revalution.retotaling.marks.update.success"));
					saveMessages(request, messages);
				
				}*/
			}catch(BusinessException e) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.revalution.retotaling.enter.two.evl.marks"));
				saveErrors(request, errors);
					return mapping.findForward("revaluationMarksUpdate");
				} 
				catch (Exception exception) {
					String msg = super.handleApplicationException(exception);
					revaluationMarksUpdateForm.setErrorMessage(msg);
					revaluationMarksUpdateForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward("revaluationMarksUpdate");
		}
		List<RevaluationMarksUpdateTo> studentDetailsList=RevaluationMarksUpdateHandler.getInstance().getStudentDetails(revaluationMarksUpdateForm);
		revaluationMarksUpdateForm.setStudentDetailsList(studentDetailsList);
		setRequiredDatatoForm(revaluationMarksUpdateForm);
		log.info("Exit RevaluationMarksUpdateAction - thirdEvaluation");
		return mapping.findForward("revaluationMarksUpdate");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveChangedMarksForRetoaling(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered RevaluationMarksUpdateAction - saveChangedMarksForRetoaling");
		
		RevaluationMarksUpdateForm revaluationMarksUpdateForm  = (RevaluationMarksUpdateForm)form;
		ActionErrors errors = revaluationMarksUpdateForm.validate(mapping, request);
		//validateStudentMarks(newStudentMarksCorrectionForm,errors);
		validateMaxMarksForRetotaling(revaluationMarksUpdateForm,errors);
		setUserId(request, revaluationMarksUpdateForm);
		if (errors.isEmpty()) {
			try {
				
				Iterator<RevaluationMarksUpdateTo> iterator = revaluationMarksUpdateForm.getStudentDetailsList().iterator();
				while (iterator.hasNext()) {
					RevaluationMarksUpdateTo to = (RevaluationMarksUpdateTo) iterator.next();
				if((to.getStudentId()==revaluationMarksUpdateForm.getStudentid())&& (to.getClassId() == revaluationMarksUpdateForm.getClassid()
						&& (to.getSubjectId()== revaluationMarksUpdateForm.getSubjectid()))){
					to.setIsUpdated(true);
				}
				
				}
				RevaluationMarksUpdateHandler.getInstance().saveChangedMarks(revaluationMarksUpdateForm);
				// this method added by Nagarjuna.
				// to run the RegularOverAllProcess 
				RevaluationMarksUpdateHandler.getInstance().updateRegularOverAllProcessRetoaling(revaluationMarksUpdateForm);
				/*if(isSaved){
					//messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Student Marks "));
					//saveMessages(request, messages);
					//revaluationMarksUpdateForm.resetFields();
				}*/
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				revaluationMarksUpdateForm.setErrorMessage(msg);
				revaluationMarksUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit RevaluationMarksUpdateAction - saveChangedMarksForRetoaling errors not empty ");
			return mapping.findForward("revaluationMarksUpdate");
		}
		//List<RevaluationMarksUpdateTo> studentDetailsList=RevaluationMarksUpdateHandler.getInstance().getStudentDetails(revaluationMarksUpdateForm);
		//revaluationMarksUpdateForm.setStudentDetailsList(studentDetailsList);
		setRequiredDatatoForm(revaluationMarksUpdateForm);
		log.info("Entered NewExamMarksEntryAction - saveChangedMarksForRetoaling");
		return mapping.findForward("revaluationMarksUpdate");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveChangedMarksForRevaluation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered RevaluationMarksUpdateAction - saveChangedMarksForRevaluation");
		
		RevaluationMarksUpdateForm revaluationMarksUpdateForm  = (RevaluationMarksUpdateForm)form;
		ActionErrors errors = revaluationMarksUpdateForm.validate(mapping, request);
		validateMaxMarks(revaluationMarksUpdateForm,errors);
		if(revaluationMarksUpdateForm.getOption().equalsIgnoreCase("NotUpdated")){
			validateReasonForRevaluation(revaluationMarksUpdateForm,errors);
			}else if(revaluationMarksUpdateForm.getOption().equalsIgnoreCase("Requested_for_thirdEvaluation")){
				//validateReasonForRevaluation(revaluationMarksUpdateForm,errors);
			}
		setUserId(request, revaluationMarksUpdateForm);
		if (errors.isEmpty()) {
			try {
				RevaluationMarksUpdateHandler.getInstance().saveChangedMarksforRevaluation(revaluationMarksUpdateForm);
				// this method added by Nagarjuna.
				// to run the RegularOverAllProcess 
				RevaluationMarksUpdateHandler.getInstance().updateRegularOverAllProcess(revaluationMarksUpdateForm);
				
				/*if(isSaved){
					//messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Student Marks "));
					//saveMessages(request, messages);
					//revaluationMarksUpdateForm.resetFields();
				}*/
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				revaluationMarksUpdateForm.setErrorMessage(msg);
				revaluationMarksUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit RevaluationMarksUpdateAction - saveChangedMarksForRevaluation errors not empty ");
			return mapping.findForward("revaluationMarksUpdate");
		}
		List<RevaluationMarksUpdateTo> studentDetailsList=RevaluationMarksUpdateHandler.getInstance().getStudentDetails(revaluationMarksUpdateForm);
		revaluationMarksUpdateForm.setStudentDetailsList(studentDetailsList);
		setRequiredDatatoForm(revaluationMarksUpdateForm);
		log.info("Entered RevaluationMarksUpdateAction - saveChangedMarksForRevaluation");
		return mapping.findForward("revaluationMarksUpdate");
	}
	
	//validate the marks
	private void validateMaxMarks(RevaluationMarksUpdateForm form,ActionErrors errors) throws Exception{
				List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
					if((form.getAvgMarks()!=null && !form.getAvgMarks().isEmpty())){
						Double maxMark=RevaluationMarksUpdateHandler.getInstance().getMaxMarkOfSubject(form);
						String reg="";
						if(maxMark==null){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
						}else{
						if(!StringUtils.isNumeric(form.getAvgMarks())){
							if(examAbscentCode!=null && !examAbscentCode.contains(form.getAvgMarks())){
								String str=form.getSubjectname();
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
							}
						}else{
							double marks=Double.parseDouble(form.getAvgMarks());
							if(marks>maxMark){
								if(reg.isEmpty()){
									reg=form.getSubjectname();
								}
							}
						}
					  }
						if(!reg.isEmpty()){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
						}
					  }
					
				
		}
	private void validateReasonForRevaluation(RevaluationMarksUpdateForm form,ActionErrors errors) throws Exception{
	 if(form.getComment()!=null && !form.getComment().isEmpty()){
		 if(!form.getAvgMarks().equalsIgnoreCase(form.getOldAvgMarks())){
		 if(form.getComment().equalsIgnoreCase("Revaluation")){
			 errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.revaluation.or.retotalling.update.comments"));
		 	}
		 }
	 }else{
		 errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.revaluation.or.retotalling.update.comments"));
	 }
	}
	
	public ActionForward updateAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered RevaluationMarksUpdateAction - saveChangedMarksForRetoaling");
		
		RevaluationMarksUpdateForm revaluationMarksUpdateForm  = (RevaluationMarksUpdateForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = revaluationMarksUpdateForm.validate(mapping, request);
		validateMaxMarksForUpdateAll(revaluationMarksUpdateForm,errors);
		setUserId(request, revaluationMarksUpdateForm);
		if (errors.isEmpty()) {
			try {
				boolean  isSaved=RevaluationMarksUpdateHandler.getInstance().saveChangedMarksForUpdateAll(revaluationMarksUpdateForm);
				if(isSaved){
					RevaluationMarksUpdateHandler.getInstance().updateRegularOverAllProcessRetoalingForAll(revaluationMarksUpdateForm);
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Student Marks "));
					saveMessages(request, messages);
					//revaluationMarksUpdateForm.resetFields();
				}else{
					/*messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.exam.revaluation.marks.update.failure","Student Marks "));
					saveMessages(request, messages);*/
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				revaluationMarksUpdateForm.setErrorMessage(msg);
				revaluationMarksUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit RevaluationMarksUpdateAction - saveChangedMarksForRetoaling errors not empty ");
			return mapping.findForward("revaluationMarksUpdate");
		}
		//List<RevaluationMarksUpdateTo> studentDetailsList=RevaluationMarksUpdateHandler.getInstance().getStudentDetails(revaluationMarksUpdateForm);
		//revaluationMarksUpdateForm.setStudentDetailsList(studentDetailsList);
		Iterator<RevaluationMarksUpdateTo> iterator = revaluationMarksUpdateForm.getStudentDetailsList().iterator();
		while (iterator.hasNext()) {
			RevaluationMarksUpdateTo to = (RevaluationMarksUpdateTo) iterator.next();
			if(to.getMarks()!=null && !to.getMarks().isEmpty()){
				to.setIsUpdated(true);
			}
		}
		
		setRequiredDatatoForm(revaluationMarksUpdateForm);
		log.info("Entered NewExamMarksEntryAction - saveChangedMarksForRetoaling");
		return mapping.findForward("revaluationMarksUpdate");
	}
	
	private void validateMaxMarksForRetotaling(RevaluationMarksUpdateForm form,ActionErrors errors) throws Exception{
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		
			if((form.getNewMarks()!=null && !form.getNewMarks().isEmpty())){
				if(!form.getNewMarks().equalsIgnoreCase("null")){
				Double maxMark=RevaluationMarksUpdateHandler.getInstance().getMaxMarkOfSubject(form);
				String reg="";
				if(maxMark==null){
					String str=form.getSubjectname();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.retotaling.update.max.marks.notDefined",str));
				}else{
				if(!StringUtils.isNumeric(form.getNewMarks())){
					if(examAbscentCode!=null && !examAbscentCode.contains(form.getNewMarks())){
						String str=form.getSubjectname();
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
					}
				}else{
					double marks=Double.parseDouble(form.getNewMarks());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=form.getSubjectname();
						}
					}
				}
			  }
				if(!reg.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
				}
			  }
			}
			
			if((form.getNewMark1()!=null && !form.getNewMark1().isEmpty())){
				String reg="";
				if(!form.getNewMark1().equalsIgnoreCase("null")){
				Double maxMark=RevaluationMarksUpdateHandler.getInstance().getMaxMarkOfSubject(form);
				if(maxMark==null){
					String str=form.getSubjectname();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.retotaling.update.max.marks.notDefined",str));
				}else{
				if(!StringUtils.isNumeric(form.getNewMark1())){
					if(examAbscentCode!=null && !examAbscentCode.contains(form.getNewMark1())){
						String str=form.getSubjectname();
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
					}
				}else{
					double marks=Double.parseDouble(form.getNewMark1());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=form.getSubjectname();
						}
					}
				}
			  }
				if(!reg.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
				}
			  }
			}
			if((form.getNewMark2()!=null && !form.getNewMark2().isEmpty())){
				String reg="";
				if(!form.getNewMark2().equalsIgnoreCase("null")){
				Double maxMark=RevaluationMarksUpdateHandler.getInstance().getMaxMarkOfSubject(form);
				if(maxMark==null){
					String str=form.getSubjectname();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.retotaling.update.max.marks.notDefined",str));
				}else{
				if(!StringUtils.isNumeric(form.getNewMark2())){
					if(examAbscentCode!=null && !examAbscentCode.contains(form.getNewMark2())){
						String str=form.getSubjectname();
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
					}
				}else{
					double marks=Double.parseDouble(form.getNewMark2());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=form.getSubjectname();
						}
					}
				}
			  }
				if(!reg.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
				}
			  }
		}
	}
	private void validateMaxMarksForUpdateAll(RevaluationMarksUpdateForm form,ActionErrors errors) throws Exception{
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		
		
		Iterator<RevaluationMarksUpdateTo> iterator = form.getStudentDetailsList().iterator();
		while (iterator.hasNext()) {
			RevaluationMarksUpdateTo to = (RevaluationMarksUpdateTo) iterator.next();
			
			if((to.getNewMarks()!=null && !to.getNewMarks().isEmpty())){
				Double maxMark=RevaluationMarksUpdateHandler.getInstance().getSubjectMaxMarks(form,to.getCourseId(),to.getSchemeNumber(),to.getSubjectId());
				String reg="";
				if(maxMark==null){
					String str=form.getSubjectname();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.retotaling.update.max.marks.notDefined",str));
				}else{
				if(!StringUtils.isNumeric(to.getNewMarks())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getNewMarks())){
						String str=to.getSubjectCode();
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
					}
				}else{
					double marks=Double.parseDouble(to.getNewMarks());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getSubjectCode();
						}
					}
				}
			  }
				if(!reg.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
				}
			  }
		
			if((to.getNewMark1()!=null && !to.getNewMark1().isEmpty())){
				String reg="";
				Double maxMark=RevaluationMarksUpdateHandler.getInstance().getSubjectMaxMarks(form,to.getCourseId(),to.getSchemeNumber(),to.getSubjectId());
				if(maxMark==null){
					String str=form.getSubjectname();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.retotaling.update.max.marks.notDefined",str));
				}else{
				if(!StringUtils.isNumeric(to.getNewMark1())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getNewMark2())){
						String str=to.getSubjectCode();
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
					}
				}else{
					double marks=Double.parseDouble(to.getNewMark1());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getSubjectCode();
						}
					}
				}
			  }
				if(!reg.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
				}
			  }
		
			if((to.getNewMark2()!=null && !to.getNewMark2().isEmpty())){
				String reg="";
				Double maxMark=RevaluationMarksUpdateHandler.getInstance().getSubjectMaxMarks(form,to.getCourseId(),to.getSchemeNumber(),to.getSubjectId());
				if(maxMark==null){
					String str=form.getSubjectname();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.retotaling.update.max.marks.notDefined",str));
				}else{
				if(!StringUtils.isNumeric(to.getNewMark2())){
					if(examAbscentCode!=null && !examAbscentCode.contains(to.getNewMark2())){
						String str=to.getSubjectCode();
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.invalid",str));
					}
				}else{
					double marks=Double.parseDouble(to.getNewMark2());
					if(marks>maxMark){
						if(reg.isEmpty()){
							reg=to.getSubjectCode();
						}
					}
				}
			  }
				if(!reg.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",reg));
				}
			  }
			}
		}
}
