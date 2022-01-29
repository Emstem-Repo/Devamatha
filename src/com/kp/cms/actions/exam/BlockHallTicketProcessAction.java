package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.exam.BlockHallTicketProcessForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.BlockHallTicketProcessHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class BlockHallTicketProcessAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(BlockHallTicketProcessAction.class);
	
	/**
	 * Method to set the required data to the form to display it in initBlockHallTicket.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initBlockHallTicketProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initBlockHallTicketProcess");
		BlockHallTicketProcessForm blockHallTicketProcessForm = (BlockHallTicketProcessForm) form;
		blockHallTicketProcessForm.resetFields();
		setRequiredDatatoForm(blockHallTicketProcessForm);
		log.info("Exit initBlockHallTicketProcess");
		
		return mapping.findForward(CMSConstants.INIT_BLOCK_HALL_TICKET_PROCESS);
	}

	/**
	 * @param blockHallTicketProcessForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(BlockHallTicketProcessForm blockHallTicketProcessForm) throws Exception {
		//added by Smitha - for new academic year input addition
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(blockHallTicketProcessForm.getYear()!=null && !blockHallTicketProcessForm.getYear().isEmpty()){
			year=Integer.parseInt(blockHallTicketProcessForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		//Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType("Regular");// getting exam list based on exam Type
		Map<Integer,String> examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear("Regular",year); 
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		blockHallTicketProcessForm.setExamNameList(examNameMap);// setting the examNameMap to form
		
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			blockHallTicketProcessForm.setProgramTypeList(programTypeList);
		}
		if(blockHallTicketProcessForm.getProgramTypeId()!=null && !blockHallTicketProcessForm.getProgramTypeId().isEmpty()){
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(blockHallTicketProcessForm.getProgramTypeId()));
			blockHallTicketProcessForm.setProgramMap(programMap);
		}else
			blockHallTicketProcessForm.setProgramMap(null);
			
		if(blockHallTicketProcessForm.getProgramTypeId()!=null && !blockHallTicketProcessForm.getProgramTypeId().isEmpty()){
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.valueOf(blockHallTicketProcessForm.getProgramId()));
			blockHallTicketProcessForm.setCourseMap(courseMap);
		}else
			blockHallTicketProcessForm.setCourseMap(null);
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
		BlockHallTicketProcessForm blockHallTicketProcessForm = (BlockHallTicketProcessForm) form;
		 ActionErrors errors = blockHallTicketProcessForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentsAttendanceReportTO> list = BlockHallTicketProcessHandler.getInstance().getListOfCandidates(blockHallTicketProcessForm);
				if (list.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(blockHallTicketProcessForm);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_BLOCK_HALL_TICKET_PROCESS);
				} 
				blockHallTicketProcessForm.setStudentList(list);
				Collections.sort(list);
				setNamesToForm(blockHallTicketProcessForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				blockHallTicketProcessForm.setErrorMessage(msg);
				blockHallTicketProcessForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(blockHallTicketProcessForm);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_BLOCK_HALL_TICKET_PROCESS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.BLOCK_HALL_TICKET_PROCESS_RESULT);
	}
	/**
	 * @param newExamMarksEntryForm
	 */
	private void setNamesToForm(BlockHallTicketProcessForm blockHallTicketProcessForm )throws Exception {
		blockHallTicketProcessForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(blockHallTicketProcessForm.getExamId()),"ExamDefinitionBO",true,"name"));
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
	public ActionForward saveBlockHallTickets(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered StudentSpecialPromotionAction - updatePromtionProcess");
		
		BlockHallTicketProcessForm blockHallTicketProcessForm = (BlockHallTicketProcessForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = blockHallTicketProcessForm.validate(mapping, request);
		validatePromotionProcess(blockHallTicketProcessForm,errors);
		setUserId(request,blockHallTicketProcessForm);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated = BlockHallTicketProcessHandler.getInstance().saveBlockHallTicketProcess(blockHallTicketProcessForm);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Hall Tickets Blocked"));
					saveMessages(request, messages);
					blockHallTicketProcessForm.resetFields();
					
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Hall Tickets Blocked"));
					addErrors(request, errors);
				}
				setRequiredDatatoForm(blockHallTicketProcessForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				blockHallTicketProcessForm.setErrorMessage(msg);
				blockHallTicketProcessForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(blockHallTicketProcessForm);
			log.info("Exit StudentSpecialPromotionAction - updatePromtionProcess errors not empty ");
			return mapping.findForward(CMSConstants.BLOCK_HALL_TICKET_PROCESS_RESULT);
		}
		log.info("Entered StudentSpecialPromotionAction - updatePromtionProcess");
		return mapping.findForward(CMSConstants.INIT_BLOCK_HALL_TICKET_PROCESS);
	}

	/**
	 * @param promotionProcessForm
	 * @param errors
	 * @throws Exception
	 */
	private void validatePromotionProcess(BlockHallTicketProcessForm promotionProcessForm, ActionErrors errors) throws Exception{
		boolean isChecked=false;
		List<StudentsAttendanceReportTO> list=promotionProcessForm.getStudentList();
		Iterator<StudentsAttendanceReportTO> itr=list.iterator();
		while (itr.hasNext()) {
			StudentsAttendanceReportTO to = itr.next();
			if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
				isChecked=true;
				break;
			}
		}
		if(!isChecked){
			errors.add(CMSConstants.ERROR,new ActionError("interviewProcessForm.checkbox.select"));
		}
	}
}
