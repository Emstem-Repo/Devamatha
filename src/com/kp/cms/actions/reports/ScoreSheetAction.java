package com.kp.cms.actions.reports;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.reports.ScoreSheetForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.ScoreSheetHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Action class for Score Sheet
 */
@SuppressWarnings("deprecation")
public class ScoreSheetAction extends BaseDispatchAction{

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
		ScoreSheetForm scoreSheetForm = (ScoreSheetForm) form;
		scoreSheetForm.resetFields();
		setRequiredDatatoForm(scoreSheetForm, request);
		log.info("Exit Score Sheet Batch input");
		
		return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ScoreSheetForm scoreSheetForm, HttpServletRequest request) throws Exception {
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
		if (scoreSheetForm.getProgramId() != null && !scoreSheetForm.getProgramId().isEmpty()) {
			Map<Integer, String> examCenterMap = new HashMap<Integer, String>();
			int pid = Integer.parseInt(scoreSheetForm.getProgramId() );
			examCenterMap = CommonAjaxHandler.getInstance().getExamCentersByProgram(pid);
			if(examCenterMap!=null && !examCenterMap.isEmpty())
			scoreSheetForm.setExamCenterMap(examCenterMap);
	}
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
		
		ScoreSheetForm scoreSheetForm = (ScoreSheetForm) form;
		 ActionErrors errors = scoreSheetForm.validate(mapping, request);
		validateTime(scoreSheetForm, errors);
		
		if (errors.isEmpty()) {
		
			try {
				
				ScoreSheetHandler scoreSheetHandler=ScoreSheetHandler.getInstance();
				List<ScoreSheetTO> selectedCandidates = scoreSheetHandler.getListOfCandidates(scoreSheetForm, request);
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(scoreSheetForm, request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
				} 
				int size=selectedCandidates.size();
				scoreSheetForm.setTotalStudents(size);
				request.getSession().setAttribute("SelectedCandidates", selectedCandidates);
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				scoreSheetForm.setErrorMessage(msg);
				scoreSheetForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(scoreSheetForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.SCORE_SHEET_INPUT);
		}
		
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.SCORE_SHEET_RESULT);
	}
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(ScoreSheetForm scoreSheetForm, ActionErrors errors) {
		
		if(scoreSheetForm.getInterviewStartDate()!=null && !StringUtils.isEmpty(scoreSheetForm.getInterviewStartDate())&& !CommonUtil.isValidDate(scoreSheetForm.getInterviewStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(scoreSheetForm.getInterviewEndDate()!=null && !StringUtils.isEmpty(scoreSheetForm.getInterviewEndDate())&& !CommonUtil.isValidDate(scoreSheetForm.getInterviewEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getInterviewStartDate()) && CommonUtil.checkForEmpty(scoreSheetForm.getInterviewEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(scoreSheetForm.getInterviewStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(scoreSheetForm.getInterviewEndDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getStartingTimeHours())){
			if(!StringUtils.isNumeric(scoreSheetForm.getStartingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getStartingTimeMins())){
			if(!StringUtils.isNumeric(scoreSheetForm.getStartingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(scoreSheetForm.getStartingTimeHours())){
			if(Integer.parseInt(scoreSheetForm.getStartingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getStartingTimeMins())){
			if(Integer.parseInt(scoreSheetForm.getStartingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getEndingTimeHours())){
			if(!StringUtils.isNumeric(scoreSheetForm.getEndingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getEndingTimeMins())){
			if(!StringUtils.isNumeric(scoreSheetForm.getEndingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(scoreSheetForm.getEndingTimeHours())){
			if(Integer.parseInt(scoreSheetForm.getEndingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(scoreSheetForm.getEndingTimeMins())){
			if(Integer.parseInt(scoreSheetForm.getEndingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
	public ActionForward printScoreSheetResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Score Sheet Batch input");
		/*ScoreSheetForm scoreSheetForm = (ScoreSheetForm) form;
		scoreSheetForm.resetFields();
		setRequiredDatatoForm(scoreSheetForm);*/
		log.info("Exit Score Sheet Batch input");
		
		return mapping.findForward(CMSConstants.SCORE_SHEET_Print);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExamCentersByProgramId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ScoreSheetForm scoreSheetForm = (ScoreSheetForm) form;
		int pid;
		Map<Integer, String> examCenterMap = new HashMap<Integer, String>();
		if (scoreSheetForm.getProgramid() != null
				&& scoreSheetForm.getProgramid().length() != 0) {
			try {
				pid = Integer.parseInt(scoreSheetForm.getProgramid());
				// The below map contains key as id and value as name of examCenterName.
				examCenterMap = CommonAjaxHandler.getInstance().getExamCentersByProgram(
						pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		
		request.setAttribute("optionMap", examCenterMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
}
