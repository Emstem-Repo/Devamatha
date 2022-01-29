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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.forms.exam.ConsolidateMarksCardForm;
import com.kp.cms.forms.exam.ConsolidateMarksCardSiNoForm;
import com.kp.cms.forms.exam.MarksCardForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ApproveLeaveHandler;
import com.kp.cms.handlers.exam.ConsolidateMarksCardSiNoHandler;
import com.kp.cms.handlers.exam.MarksCardHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ConsolidateMarksCardSiNoTO;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;
import com.kp.cms.transactions.exam.IMarksCardTransaction;
import com.kp.cms.transactionsimpl.exam.MarksCardTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ConsolidateMarksCardAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(MarksCardSiNoAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initConsolidateMarksCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.debug("entered into marks card init screen");
		
		ConsolidateMarksCardForm consolidateMarksCardForm = (ConsolidateMarksCardForm)form;
		consolidateMarksCardForm.reset();
		
		try {
			
			setClassMapToForm(consolidateMarksCardForm);
			setData(consolidateMarksCardForm);
		} catch (Exception e) {
			log
					.error("Error while initializing approve leave"
							+ e.getMessage());
			String msg = super.handleApplicationException(e);
			consolidateMarksCardForm.setErrorMessage(msg);
			consolidateMarksCardForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of initApproveLeave of ApproveLeaveAction class.");
		return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_CARD_GENERATE);
	}
	
	
	public void setClassMapToForm(ConsolidateMarksCardForm consolidateMarksCardForm)
	throws Exception {
log.info("entering into setClassMapToForm of ApproveLeaveAction class.");
Calendar calendar = Calendar.getInstance();
int currentYear = calendar.get(Calendar.YEAR);

consolidateMarksCardForm.setYear(String.valueOf(currentYear));

int year=CurrentAcademicYear.getInstance().getAcademicyear();
if(year!=0){
	currentYear=year;
}
consolidateMarksCardForm.setYear(String.valueOf(currentYear));

Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getPrograms(
		currentYear);
consolidateMarksCardForm.setClassMap(classMap);

log.info("exit of setClassMapToForm of ApproveLeaveAction class.");
}

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward generateMarksCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.debug("Enter into MArks Card Generate Screen");
		ConsolidateMarksCardForm consolidateMarksCardForm = (ConsolidateMarksCardForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors = consolidateMarksCardForm.validate(mapping, request);
		try{
			if(consolidateMarksCardForm.getProgramTypeId()==null||consolidateMarksCardForm.getProgramTypeId().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.admin.programtype.required"));
				saveErrors(request, errors);
			}
			
			if(consolidateMarksCardForm.getProgramId()==null||consolidateMarksCardForm.getProgramId().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.admin.program.required"));
				saveErrors(request, errors);
			}
			
			if(consolidateMarksCardForm.getCourseId()==null||consolidateMarksCardForm.getCourseId().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.admin.course.required"));
				saveErrors(request, errors);
			}
			
			
			if(errors.isEmpty()){
				
							
							int currentNO = MarksCardHandler.getInstance().getConsolidateCurrentNO(consolidateMarksCardForm);
							if(currentNO != -1){
								IMarksCardTransaction transaction = new MarksCardTransactionImpl();
								List<Integer> studentId = transaction.getStudentDetails(consolidateMarksCardForm);
								Map<Integer, String> studentMaksCardNO = transaction.getStudentDetailList(consolidateMarksCardForm);
							
								
								
								
								if(!studentMaksCardNO.isEmpty())
								{
									ActionMessage message = new ActionMessage("knowledgepro.admin.markscard.duplicate.regNo");
									messages.add(CMSConstants.MESSAGES, message);
									saveMessages(request, messages);
									setData(consolidateMarksCardForm);
//									
								return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_CARD_GENERATE);
								}
								
								if(!studentId.isEmpty())
								{
								
										boolean isStudentUpdate = MarksCardHandler.getInstance().consolidateupdateStudent(consolidateMarksCardForm,currentNO);
							
										
										if(isStudentUpdate){
											String totalCount = Integer.toString(currentNO+Integer.parseInt(consolidateMarksCardForm.getTotalCount()));
											boolean isUpdate = MarksCardHandler.getInstance().updatecount(totalCount,consolidateMarksCardForm);
											ActionMessage message = new ActionMessage("knowledgepro.admin.markscard.no.generate.success");
											messages.add(CMSConstants.MESSAGES, message);
											saveMessages(request, messages);
											consolidateMarksCardForm.reset();
											setData(consolidateMarksCardForm);
											return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_CARD_GENERATE);
									}
									
								}
								else{
									errors.add("errors", new ActionError(CMSConstants.MARKSCARD_STUDENT_NOT_PRESENT));
									saveErrors(request, errors);
									consolidateMarksCardForm.reset();
									setData(consolidateMarksCardForm);
									return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_CARD_GENERATE);
								}
							}
							else
							{
								ActionMessage message = new ActionMessage("knowledgepro.admin.markscard.duplicate.regNo");
								messages.add(CMSConstants.MESSAGES, message);
								saveMessages(request, messages);
								setData(consolidateMarksCardForm);
							}
								
							
			}else {
				saveErrors(request, errors);
				consolidateMarksCardForm.reset();
				setData(consolidateMarksCardForm);
				
				return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_CARD_GENERATE);
					}
			
		}
		catch (DataNotFoundException e1) {
			errors.add("errors", new ActionError(CMSConstants.MARKSCARD_SI_NOT_AVAILABLE));
			saveErrors(request, errors);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionMessage message = new ActionMessage("knowledgepro.admin.markscard.duplicate.regNo");
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
//			
		}
		return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_CARD_GENERATE);
	}


	private void setDataToForm(MarksCardForm marksCardForm)throws Exception {
		// TODO Auto-generated method stub
		if(marksCardForm.getExamType()!=null && !StringUtils.isEmpty(marksCardForm.getExamType())){
			Map<Integer, String> listExamName = CommonAjaxHandler.getInstance().getExamNameByAcademicYearAndExamType(marksCardForm.getYear(),marksCardForm.getExamType());
			marksCardForm.setListExamName(listExamName);
		}
		if(marksCardForm.getExamName()!=null && !StringUtils.isEmpty(marksCardForm.getExamName())){
			Map<Integer, String> listClassName = CommonAjaxHandler.getInstance().getClassNameByExamName(Integer.parseInt(marksCardForm.getExamName()));
			marksCardForm.setListClassName(listClassName);
		}
		
	}
	private void setData(ConsolidateMarksCardForm consolidateMarksCardForm) throws Exception {
		// TODO Auto-generated method stub
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		consolidateMarksCardForm.setProgramTypeList(programTypeList);
	}


	/*private void validateRegNo(ConsolidateMarksCardForm consolidateMarksCardForm,HttpServletRequest request,ActionErrors errors)throws Exception {
		// TODO Auto-generated method stub
		boolean isAvailable = MarksCardHandler.getInstance().checkRegNo(consolidateMarksCardForm);
		if(!isAvailable){
			errors.add("errors", new ActionError(CMSConstants.MARKS_CARD_GENERATED_ALREADY_REG));
			saveErrors(request, errors);
		}
	}*/
}
