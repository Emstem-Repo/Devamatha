package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.InterviewScoreSheetForm;
import com.kp.cms.handlers.admin.InterviewTemplateHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.InterviewScoreSheetHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.utilities.CommonUtil;

public class InterviewScoreSheetAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(ScoreSheetAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initScoreSheet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Score Sheet Batch input");
		InterviewScoreSheetForm interviewScoreSheetForm=(InterviewScoreSheetForm)form;
		interviewScoreSheetForm.resetFields();
		setRequiredDatatoForm(interviewScoreSheetForm, request);
		log.info("Exit Score Sheet Batch input");
		
		return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(InterviewScoreSheetForm scoreSheetForm, HttpServletRequest request) throws Exception {

		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			scoreSheetForm.setProgramTypeList(programTypeList);
		}
		Map collegeMap;
		Map<Integer,String> courseMap;
		if(scoreSheetForm.getCourseId()!=null && scoreSheetForm.getYear()!=null &&
			scoreSheetForm.getCourseId().length()>0 && scoreSheetForm.getYear().length()>0){
			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCourse
			(Integer.valueOf(scoreSheetForm.getCourseId()),Integer.valueOf(scoreSheetForm.getYear()));
			request.setAttribute("interviewMap", collegeMap);
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(scoreSheetForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		else if(scoreSheetForm.getProgramId()!= null && scoreSheetForm.getYear() != null &&
			scoreSheetForm.getProgramId().length() >0 && scoreSheetForm.getYear().length()>0){
			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByProgram
			(Integer.valueOf(scoreSheetForm.getProgramId()),Integer.valueOf(scoreSheetForm.getYear()));
			request.setAttribute("interviewMap", collegeMap);
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(scoreSheetForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
	
//		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
//		if(programTypeList != null){
//			interviewScoreSheetForm.setProgramTypeList(programTypeList);
//		}
//		Map collegeMap = new HashMap();
//		Map<Integer,String> courseMap = new HashMap<Integer,String>();
//		if(interviewScoreSheetForm.getCourseId()!=null && interviewScoreSheetForm.getYear()!=null &&
//				interviewScoreSheetForm.getCourseId().length()>0 && interviewScoreSheetForm.getYear().length()>0){
//			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCourse
//			(Integer.valueOf(interviewScoreSheetForm.getCourseId()),Integer.valueOf(interviewScoreSheetForm.getYear()));
//			request.setAttribute("interviewMap", collegeMap);
//		}
//		if(interviewScoreSheetForm.getProgramId()!= null &&
//				interviewScoreSheetForm.getProgramId().length() >0 ){
//			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
//					Integer.valueOf(interviewScoreSheetForm.getProgramId()));
//			request.setAttribute("coursesMap", courseMap);
//		}
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		InterviewScoreSheetForm interviewScoreSheetForm=(InterviewScoreSheetForm)form;
		 ActionErrors errors = interviewScoreSheetForm.validate(mapping, request);
		validateTime(interviewScoreSheetForm, errors);
		interviewScoreSheetForm.setPrint(false);
		if (errors.isEmpty()) {
			try {
				Map<Integer,String> courseMap=InterviewScoreSheetHandler.getInstance().getInterviewProgramCourseMap(interviewScoreSheetForm);
				Map<Integer,String> templateMap=InterviewScoreSheetHandler.getInstance().getTemplateMap(interviewScoreSheetForm,"Personal Interview");
				validateTemplateConfiguration(interviewScoreSheetForm,courseMap,templateMap,errors);
//				List<GroupTemplateInterview> list=InterviewTemplateHandler.getInstance().checkDuplicate(Integer.parseInt(interviewScoreSheetForm.getCourseId()),"Personal Interview", 
//						Integer.parseInt(interviewScoreSheetForm.getProgramId()),Integer.parseInt(interviewScoreSheetForm.getInterviewType()),0,Integer.parseInt(interviewScoreSheetForm.getYear()));
//				if(list==null || list.isEmpty()){
//					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interview.template.not.configured"));
//				}
				if (errors.isEmpty()) {
				InterviewScoreSheetHandler scoreSheetHandler=InterviewScoreSheetHandler.getInstance();
				List<String> selectedCandidates=scoreSheetHandler.getListOfCandidates(interviewScoreSheetForm, request,templateMap);
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(interviewScoreSheetForm, request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
				} 
				int size=selectedCandidates.size();
				interviewScoreSheetForm.setTotalStudents(size);
				interviewScoreSheetForm.resetFields();
				interviewScoreSheetForm.setPrint(true);
				interviewScoreSheetForm.setMessageList(selectedCandidates);
				}else{
					addErrors(request, errors);
					setRequiredDatatoForm(interviewScoreSheetForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				interviewScoreSheetForm.setErrorMessage(msg);
				interviewScoreSheetForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(interviewScoreSheetForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
	}
	/**
	 * @param interviewScoreSheetForm
	 * @param courseMap
	 * @param templateMap
	 */
	private void validateTemplateConfiguration(InterviewScoreSheetForm interviewScoreSheetForm,Map<Integer, String> courseMap, Map<Integer, String> templateMap,ActionErrors errors) throws Exception {
		String [] tempArray = interviewScoreSheetForm.getInterviewType();
		StringBuilder err = new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			String intType =tempArray[i];
			if(!templateMap.containsKey(Integer.parseInt(intType))){
				err.append(courseMap.get(Integer.parseInt(intType)));
			}
		}
		if(!err.toString().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interview.program.course.template.not.configured",err));
		}
	}

	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(InterviewScoreSheetForm interviewScoreSheetForm, ActionErrors errors) {
		
		if(interviewScoreSheetForm.getInterviewStartDate()!=null && !StringUtils.isEmpty(interviewScoreSheetForm.getInterviewStartDate())&& !CommonUtil.isValidDate(interviewScoreSheetForm.getInterviewStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(interviewScoreSheetForm.getInterviewEndDate()!=null && !StringUtils.isEmpty(interviewScoreSheetForm.getInterviewEndDate())&& !CommonUtil.isValidDate(interviewScoreSheetForm.getInterviewEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getInterviewStartDate()) && CommonUtil.checkForEmpty(interviewScoreSheetForm.getInterviewEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(interviewScoreSheetForm.getInterviewStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(interviewScoreSheetForm.getInterviewEndDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getStartingTimeHours())){
			if(!StringUtils.isNumeric(interviewScoreSheetForm.getStartingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getStartingTimeMins())){
			if(!StringUtils.isNumeric(interviewScoreSheetForm.getStartingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getStartingTimeHours())){
			if(Integer.parseInt(interviewScoreSheetForm.getStartingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getStartingTimeMins())){
			if(Integer.parseInt(interviewScoreSheetForm.getStartingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getEndingTimeHours())){
			if(!StringUtils.isNumeric(interviewScoreSheetForm.getEndingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getEndingTimeMins())){
			if(!StringUtils.isNumeric(interviewScoreSheetForm.getEndingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getEndingTimeHours())){
			if(Integer.parseInt(interviewScoreSheetForm.getEndingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewScoreSheetForm.getEndingTimeMins())){
			if(Integer.parseInt(interviewScoreSheetForm.getEndingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
	public ActionForward printScoreSheetResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Score Sheet Batch input");
		log.info("Exit Score Sheet Batch input");
		
		return mapping.findForward(CMSConstants.SCORE_SHEET_Print);
	}
}
