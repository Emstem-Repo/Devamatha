package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.NewInternalMarksSupplementaryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamInternalMarkSupplementaryHandler;
import com.kp.cms.handlers.exam.NewExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewInternalMarksSupplementaryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class NewInternalMarksSupplementaryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewInternalMarksSupplementaryAction.class);
	
	/**
	 * Method to set the required data to the form to display it in InternalMarksSupplementay.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInternalMarkSupplementary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initExamMarksEntry input");
		NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm = (NewInternalMarksSupplementaryForm) form;// Type casting the Action form to Required Form
		newInternalMarksSupplementaryForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(newInternalMarksSupplementaryForm);// setting the requested data to form
		log.info("Exit initExamMarksEntry input");
		
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY);
	}

	public ActionForward initInternalMarkSupplementaryRetain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initExamMarksEntry input");
		NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm = (NewInternalMarksSupplementaryForm) form;// Type casting the Action form to Required Form
		newInternalMarksSupplementaryForm.resetRegisterNoOnly();//Reseting the fields for input jsp
		setRequiredDatatoForm(newInternalMarksSupplementaryForm);// setting the requested data to form
		log.info("Exit initExamMarksEntry input");
		
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY);
	}
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		// new academic year parameter added - Smitha
		int year=0;
		String schemeNo="";
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(newInternalMarksSupplementaryForm.getYear()!=null && !newInternalMarksSupplementaryForm.getYear().isEmpty()){
			year=Integer.parseInt(newInternalMarksSupplementaryForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
		List<KeyValueTO> examList=ExamInternalMarkSupplementaryHandler.getInstance().getSupplementaryExamName(year);
		if(examList!=null)
			newInternalMarksSupplementaryForm.setExamList(examList);
		if(newInternalMarksSupplementaryForm.getExamId()!=null && !newInternalMarksSupplementaryForm.getExamId().isEmpty()){
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByExamName(newInternalMarksSupplementaryForm.getExamId());
			newInternalMarksSupplementaryForm.setCourseMap(courseMap);
			if(newInternalMarksSupplementaryForm.getCourseId()!=null && !newInternalMarksSupplementaryForm.getCourseId().isEmpty()){
				Map<String, String> schemeMap = CommonAjaxHandler.getInstance().getSchemeNoByExamIdCourseId(Integer.parseInt(newInternalMarksSupplementaryForm.getExamId()), Integer.parseInt(newInternalMarksSupplementaryForm.getCourseId()));
				newInternalMarksSupplementaryForm.setSchemeMap(schemeMap);
				newInternalMarksSupplementaryForm.setSchemeNo(newInternalMarksSupplementaryForm.getOriginalSchemeNo());
			}
		}else{
			newInternalMarksSupplementaryForm.setCourseMap(null);
			newInternalMarksSupplementaryForm.setSchemeMap(null);
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
	public ActionForward getStudentMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm = (NewInternalMarksSupplementaryForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = newInternalMarksSupplementaryForm.validate(mapping, request);
		validateStudentRegOrRollNo(newInternalMarksSupplementaryForm,errors);
		if (errors.isEmpty()) {
			try {
				String schemeNo=newInternalMarksSupplementaryForm.getSchemeNo();
				// If no record comes to put the value to form
				newInternalMarksSupplementaryForm.setOriginalSchemeNo(schemeNo);
				String schemes[] = newInternalMarksSupplementaryForm.getSchemeNo().split("_");
				newInternalMarksSupplementaryForm.setSchemeNo(schemes[1]);
				boolean isValidStudent=NewInternalMarksSupplementaryHandler.getInstance().checkValidStudentRegNo(newInternalMarksSupplementaryForm);
				if(!isValidStudent){
					errors.add(CMSConstants.ERROR, new ActionError("errors.invalid"," Register No or Roll No "));
					saveErrors(request, errors);
					setRequiredDatatoForm(newInternalMarksSupplementaryForm);			
					return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY);
				}
				List<ExamInternalMarksSupplementaryTO> stuMarksList=NewInternalMarksSupplementaryHandler.getInstance().getStudentMarksBySupplementaryApplication(newInternalMarksSupplementaryForm);
				if(stuMarksList==null || stuMarksList.isEmpty()){// if student list is empty display no record found in the input screen
					if(newInternalMarksSupplementaryForm.getMsg()!=null && !newInternalMarksSupplementaryForm.getMsg().isEmpty())
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message",newInternalMarksSupplementaryForm.getMsg()));
					else
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					newInternalMarksSupplementaryForm.setSchemeNo(schemeNo);//setting back the previous value
					setRequiredDatatoForm(newInternalMarksSupplementaryForm);			
					return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY);
				}
				newInternalMarksSupplementaryForm.setStuMarkList(stuMarksList);
				setNamesToForm(newInternalMarksSupplementaryForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newInternalMarksSupplementaryForm.setErrorMessage(msg);
				newInternalMarksSupplementaryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newInternalMarksSupplementaryForm);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY_RESULT);
	}

	/**
	 * @param newInternalMarksSupplementaryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateStudentRegOrRollNo(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm,ActionErrors errors) throws Exception{
		if((newInternalMarksSupplementaryForm.getRegisterNo()==null || newInternalMarksSupplementaryForm.getRegisterNo().isEmpty()) && (newInternalMarksSupplementaryForm.getRollNo()==null || newInternalMarksSupplementaryForm.getRollNo().isEmpty())){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.reJoin.registerNo.requred"));
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
		
		NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm = (NewInternalMarksSupplementaryForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newInternalMarksSupplementaryForm.validate(mapping, request);
		validateStudentMarks(newInternalMarksSupplementaryForm,errors);
		setUserId(request, newInternalMarksSupplementaryForm);
		if (errors.isEmpty()) {
			try {
				boolean isSaved=NewInternalMarksSupplementaryHandler.getInstance().saveChangedMarks(newInternalMarksSupplementaryForm);
				if(isSaved){
					ActionMessage message = new ActionMessage("knowledgepro.exam.ExamInternalMark.added");
					messages.add("messages", message);
					saveMessages(request, messages);
					newInternalMarksSupplementaryForm.resetRegisterNoOnly();
					setRequiredDatatoForm(newInternalMarksSupplementaryForm);
					newInternalMarksSupplementaryForm.setSchemeNo(newInternalMarksSupplementaryForm.getOriginalSchemeNo());
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newInternalMarksSupplementaryForm.setErrorMessage(msg);
				newInternalMarksSupplementaryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newInternalMarksSupplementaryForm);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_SUPPLEMENTARY);
	}

	/**
	 * @param newInternalMarksSupplementaryForm
	 * @param errors
	 * @throws Exce
	 */
	private void validateStudentMarks(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm,ActionErrors errors) throws Exception{
		List<ExamInternalMarksSupplementaryTO> subjects = newInternalMarksSupplementaryForm.getStuMarkList();
		String subject="";
		for(ExamInternalMarksSupplementaryTO to : subjects) {
			List<Object[]> list = NewInternalMarksSupplementaryHandler.getInstance().getMaxMarkOfSubject(to,newInternalMarksSupplementaryForm);
			Double theoryMaxMarks=0.0;
			Double practicalMaxMarks=0.0;
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(to.getIsTheoryPrac()!=null){
						if(to.getIsTheoryPrac().equalsIgnoreCase("T")){
							if(objects[1]!=null)
								theoryMaxMarks=new Double(objects[1].toString());
						}else if(to.getIsTheoryPrac().equalsIgnoreCase("P")){
							if(objects[2]!=null)
								practicalMaxMarks=new Double(objects[2].toString());
						}else if(to.getIsTheoryPrac().equalsIgnoreCase("B")){
							if(to.getTheoryTotalSubInternalMarks() != null && !to.getTheoryTotalSubInternalMarks().trim().isEmpty()){
								if(objects[1]!=null)
									theoryMaxMarks=new Double(objects[1].toString());
							}
							if(to.getPracticalTotalSubInternalMarks() != null &&  !to.getPracticalTotalSubInternalMarks().trim().isEmpty()){
								if(objects[2]!=null)
									practicalMaxMarks=new Double(objects[2].toString());
							}
						}
					}
				}
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.notDefined"));
			}
			
			if(to.getIsTheoryPrac().equalsIgnoreCase("T")) {
				if(to.getTheoryTotalSubInternalMarks() != null && !to.getTheoryTotalSubInternalMarks().trim().isEmpty()){
					double marks=Double.parseDouble(to.getTheoryTotalSubInternalMarks());
					if(marks>theoryMaxMarks){
						if(subject.isEmpty()){
							subject=to.getSubjectCode();
						}else{
							subject=subject+","+to.getSubjectCode();
						}
					}
				}
				if (to.getTheoryTotalSubInternalMarks() != null	&& splCharValidation(to.getTheoryTotalSubInternalMarks())) {
					errors.add("error", new ActionError("knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
					break;
				}
			}
			if (to.getIsTheoryPrac().equalsIgnoreCase("p")) {
				if(to.getPracticalTotalSubInternalMarks() != null &&  !to.getPracticalTotalSubInternalMarks().trim().isEmpty()){
					double marks=Double.parseDouble(to.getPracticalTotalSubInternalMarks());
					if(marks>practicalMaxMarks){
						if(subject.isEmpty()){
							subject=to.getSubjectCode();
						}else{
							subject=subject+","+to.getSubjectCode();
						}
					}
				}
				if (to.getPracticalTotalSubInternalMarks() != null && splCharValidation(to.getPracticalTotalSubInternalMarks())) {
					errors.add("error",new ActionError("knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
					break;
				}
			} else {
				if(to.getTheoryTotalSubInternalMarks() != null && !to.getTheoryTotalSubInternalMarks().trim().isEmpty()){
					double marks=Double.parseDouble(to.getTheoryTotalSubInternalMarks());
					if(marks>theoryMaxMarks){
						if(subject.isEmpty()){
							subject=to.getSubjectCode();
						}else{
							subject=subject+","+to.getSubjectCode();
						}
					}
				}
				if(to.getPracticalTotalSubInternalMarks() != null &&  !to.getPracticalTotalSubInternalMarks().trim().isEmpty()){
					double marks=Double.parseDouble(to.getPracticalTotalSubInternalMarks());
					if(marks>practicalMaxMarks){
						if(subject.isEmpty()){
							subject=to.getSubjectCode();
						}else{
							subject=subject+","+to.getSubjectCode();
						}
					}
				}
				if (to.getTheoryMarks() != null && splCharValidation(to.getTheoryMarks())) {
					errors.add("error",new ActionError("knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
					break;
				}
				if (to.getPracticalMarks() != null && splCharValidation(to.getPracticalMarks())) {
					errors.add("error",new ActionError("knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
					break;
				}
			}
		}
		if(!subject.isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.max.marks.subject",subject));
		}
	}
	/**
	 * @param name
	 * @return
	 */
	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9]\\.+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();

		return haveSplChar;

	}
	
	/**
	 * @param newExamMarksEntryForm
	 */
	private void setNamesToForm(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm)throws Exception {
		
		newInternalMarksSupplementaryForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newInternalMarksSupplementaryForm.getExamId()),"ExamDefinitionBO",true,"examCode"));
		newInternalMarksSupplementaryForm.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newInternalMarksSupplementaryForm.getCourseId()),"Course",true,"name"));
		
	}
}
