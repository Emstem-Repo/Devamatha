package com.kp.cms.actions.exam;

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
import com.kp.cms.forms.exam.ModerationMarksEntryForm;
import com.kp.cms.forms.exam.NewStudentMarksCorrectionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ModerationMarksEntryHandler;
import com.kp.cms.handlers.exam.NewStudentMarksCorrectionHandler;
import com.kp.cms.to.exam.ModerationMarksEntryTo;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.PropertyUtil;

public class ModerationMarksEntryAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(NewStudentMarksCorrectionAction.class);

	

	public ActionForward initModeration(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Moderation input");
		ModerationMarksEntryForm moderationForm = (ModerationMarksEntryForm) form;// Type casting the Action form to Required Form
		moderationForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(moderationForm);// setting the requested data to form
		log.info("Exit Moderation input");
		
		return mapping.findForward(CMSConstants.STUDENT_MODERATION);
	}

	private void setRequiredDatatoForm(ModerationMarksEntryForm moderationForm) throws Exception{
		if(moderationForm.getExamType()!=null && !moderationForm.getExamType().isEmpty()){
//			Map<Integer, String> examMap = CommonAjaxHandler.getInstance().getExamNameByExamType(newStudentMarksCorrectionForm.getExamType());
			
			
			int year=0;
			year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(moderationForm.getAcademicYear()!=null && !moderationForm.getAcademicYear().isEmpty()){
				year=Integer.parseInt(moderationForm.getAcademicYear());
			}
			if(year==0){
				Calendar calendar = Calendar.getInstance();
				year = calendar.get(Calendar.YEAR);
			}
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(moderationForm.getExamType(),year); 
			
			if(examMap!=null && !examMap.isEmpty()){
				moderationForm.setExamMap(examMap);
				if(moderationForm.getExamId()==null && moderationForm.getExamId().isEmpty())
					moderationForm.setExamName(examMap.get(moderationForm.getExamId()));
			}
			String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(moderationForm.getExamType());
			if((moderationForm.getExamId()==null && moderationForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
				moderationForm.setExamId(currentExam);
			HashMap<String, String> markTypeMap = new HashMap<String, String>();
			if (moderationForm.getExamType().equalsIgnoreCase("Internal")) {
				markTypeMap = new ExamGenHandler().getInternalExamType();
			} else {
				markTypeMap.put("Internal overAll", "Internal overAll");
				markTypeMap.put("Regular overAll", "Regular overAll");
			}
			moderationForm.setMarkTypeMap(markTypeMap);
		}else{
			moderationForm.setExamMap(null);
			moderationForm.setExamId(null);
			moderationForm.setMarkTypeMap(null);
		}
			
	}
	
	public ActionForward initializeData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModerationMarksEntryForm moderationForm =  (ModerationMarksEntryForm) form;
	    ActionErrors errors = new ActionErrors();
	    errors = moderationForm.validate(mapping, request);
	    setRequiredNamesToForm(moderationForm);
		moderationForm.setRegNo(null);
		moderationForm.resetFields1();
		try {
		    if (errors.isEmpty()) {
			    boolean checkMarksEntry=ModerationMarksEntryHandler.getInstance().checkFinalMark(moderationForm);//checking whether marks are there in final marks table
				if(!checkMarksEntry){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);	
					setRequiredDatatoForm(moderationForm);	
					return mapping.findForward(CMSConstants.STUDENT_MODERATION);
				}
			} else {
			addErrors(request, errors);		
			log.info("Exit NewStudentMarksCorrectionAction - getDataForExam errors not empty ");
			setRequiredDatatoForm(moderationForm);	
			return mapping.findForward(CMSConstants.STUDENT_MODERATION);
		   }
		 }  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			moderationForm.setErrorMessage(msg);
			moderationForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		 
		setRequiredDatatoForm(moderationForm);	
		return mapping.findForward(CMSConstants.STUDENT_MODERATION_SELECT);
	}
	
	private void setRequiredNamesToForm(ModerationMarksEntryForm moderationForm) throws NumberFormatException, Exception {
		if(moderationForm.getExamId()!=null && !StringUtils.isEmpty(moderationForm.getExamId())){
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(moderationForm.getExamType()
					,Integer.parseInt(moderationForm.getAcademicYear()));
			moderationForm.setExamName(examMap.get(Integer.parseInt(moderationForm.getExamId())));
		}
			
		
	}

	public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response ) throws Exception{
		
		ModerationMarksEntryForm moderationForm =  (ModerationMarksEntryForm) form;
	    ActionErrors errors = new ActionErrors();
	    
	    try{
	    	boolean check = ModerationMarksEntryHandler.getInstance().getStudentDetails(moderationForm);
	    	if(!check){
	    		errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setRequiredDatatoForm(moderationForm);			
				return mapping.findForward(CMSConstants.STUDENT_MODERATION_SELECT);
	    	}
	    	
	    	
	    }catch (Exception e) {
	    	String msg = super.handleApplicationException(e);
			moderationForm.setErrorMessage(msg);
			moderationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    return mapping.findForward(CMSConstants.STUDENT_MODERATION_SELECT);
	}
	
	public ActionForward saveChangedMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		ModerationMarksEntryForm moderationForm = (ModerationMarksEntryForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = moderationForm.validate(mapping, request);
		validateStudentMarks(moderationForm,errors);
		setUserId(request, moderationForm);
		if (errors.isEmpty()) {
			try {
				boolean isSaved=ModerationMarksEntryHandler.getInstance().saveChangedMarks(moderationForm);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Student Marks "));
					saveMessages(request, messages);
					moderationForm.resetFields1();
					moderationForm.setRegNo(null);
					moderationForm.setRollNo(null);
				}
			}  catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				moderationForm.setErrorMessage(msg);
				moderationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit moderation action - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_MODERATION_SELECT);
		}
		log.info("Entered moderation action - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_MODERATION_SELECT);
	}
	
	
	private void validateStudentMarks(ModerationMarksEntryForm mForm,ActionErrors errors) throws Exception {
		List<ModerationMarksEntryTo> toList=mForm.getMarksList();
		String academicYear =null;
		if(mForm.getExamType().equals("Supplementary")){
			String supplQuery=" select EXAM_definition.academic_year from EXAM_marks_entry "+
				" inner join classes ON EXAM_marks_entry.class_id = classes.id "+
				" inner join student ON EXAM_marks_entry.student_id = student.id "+
				" inner join EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id "+
				" inner join exam_type ON EXAM_definition.exam_type_id = exam_type.id "+
				" where  exam_type.id=1 "+
				" and student.id="+ mForm.getStudentId() +
				" and classes.id="+mForm.getClassId()+
				" group by EXAM_definition.id ";
				academicYear = PropertyUtil.getDataBySql(supplQuery);
				mForm.setAcademicYear(academicYear);
		}
		
		Map<Integer,SubjectMarksTO> maxMarks=ModerationMarksEntryHandler.getInstance().getMaxMarksMap(mForm);
		Iterator<ModerationMarksEntryTo> itr=toList.iterator();
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
		String subjectName="";
		
		
		while (itr.hasNext()) {
			ModerationMarksEntryTo to = (ModerationMarksEntryTo) itr.next();
			boolean isTheoryChanged=false;
			boolean isPracticalChanged=false;
			
			//if(to.getComments()==null || to.getComments().isEmpty())
					//errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.comment.subjectwise",to.getSubjectName()));
			
				SubjectMarksTO subTo=maxMarks.get(to.getSubjectId());
				if(subTo==null){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.notDefined",to.getSubjectName()));
				}else{
					if(to.getPracticalMarks()!=null && !StringUtils.isEmpty(to.getPracticalMarks())){
						if(!CommonUtil.isValidDecimal(to.getPracticalMarks())){
							if(!examAbscentCode.contains(to.getPracticalMarks()))
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.valid.marks.subject",to.getSubjectName()));
						}else{
							if(Double.parseDouble(to.getPracticalMarks())>Double.parseDouble(subTo.getFinalPracticalMarks())){
								errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.max.marks.subject",to.getSubjectName()));
							}
						}
					}
					if(to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()){
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