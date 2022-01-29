package com.kp.cms.actions.admission;

import java.util.Calendar;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ConductCertificateDetailsForm;
import com.kp.cms.handlers.admission.ConductCertificateDetailsHandler;
import com.kp.cms.handlers.admission.ConductCertificateHandler;
import com.kp.cms.handlers.admission.TCDetailsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings({"deprecation"})
public class ConductCertificateDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ConductCertificateDetailsAction.class);
	
	public ActionForward initCCDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initTCDetails of TCDetailsAction");
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		try {
			 conductCertificateDetailsForm.resetFields();			
			// Setting the classMap to request scope			
			setClassMapToRequest(request, conductCertificateDetailsForm);
			setDataToForm(conductCertificateDetailsForm);
		} catch (Exception e) {
			log.error("Error occured in initTCDetails of TCDetailsAction",e);
			String msg = super.handleApplicationException(e);
			conductCertificateDetailsForm.setErrorMessage(msg);
			conductCertificateDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initTCDetails of TCDetailsAction");
		return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
	}
	public void setClassMapToRequest(HttpServletRequest request,ConductCertificateDetailsForm conductCertificateDetailsForm ) throws Exception {
		log.info("Entering into setClassMapToRequest of TCDetailsAction");
		try {
			if (conductCertificateDetailsForm.getAcademicYear() == null
					|| conductCertificateDetailsForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
				request.setAttribute("classMap", classMap);
				conductCertificateDetailsForm.setClassMap(classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(conductCertificateDetailsForm.getAcademicYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
						.getClassesByYear(year);
				ConductCertificateDetailsHandler.getInstance().getAllStudentTCDetailsByClass(conductCertificateDetailsForm);;
				conductCertificateDetailsForm.setClassMap(classMap);
				request.setAttribute("classMap", classMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in  TCDetailsAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of TCDetailsAction");
	}
	
	private void setDataToForm(ConductCertificateDetailsForm conductCertificateDetailsForm ) throws Exception {
		List<CharacterAndConductTO> list=TCDetailsHandler.getInstance().getCharacterAndConductList();
		conductCertificateDetailsForm.setList(list);
		
	}
	
	public ActionForward updateCandidateDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = conductCertificateDetailsForm.validate(mapping, request);
		Boolean saved = false;
		setUserId(request, conductCertificateDetailsForm);
		if (errors.isEmpty()) {
			//Checking Duplication
			/*if(!ConductCertificateDetailsHandler.getInstance().verifyStudentsAllSaved(conductCertificateDetailsForm))
			{
				ActionMessage message = new ActionMessage("TC Details not added for Students ");
				messages.add("messages", message);
				saveMessages(request, messages);
				conductCertificateDetailsForm.resetFields();
				return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
			}*/
			//Verifying Students are present or not
			if(!ConductCertificateDetailsHandler.getInstance().verifyStudentsPresent(conductCertificateDetailsForm))
			{
				ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.added.successfully");
				messages.add("messages", message);
				saveMessages(request, messages);
				conductCertificateDetailsForm.resetFields();
				return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
			}
			
			/*if(!ConductCertificateDetailsHandler.getInstance().verifyGeneratedTCNumbers(conductCertificateDetailsForm))
			{
				errors.add("error", new ActionError("knowledgepro.admin.TCNumberAlreadyExists"));
				saveErrors(request, errors);
				tcDetailsForm.resetFields();
				return mapping.findForward(CMSConstants.INIT_TC_DETAILS);
			}*/
			saved=ConductCertificateDetailsHandler.getInstance().getUpdateStatus(conductCertificateDetailsForm);
			if(saved){
				ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.added.successfully");
				messages.add("messages", message);
				saveMessages(request, messages);
				conductCertificateDetailsForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admission.tc.details.added.failure"));
				saveErrors(request, errors);
				
			}
		} else {
			addErrors(request, errors);
			setClassMapToRequest(request, conductCertificateDetailsForm);
			setDataToForm(conductCertificateDetailsForm);
			conductCertificateDetailsForm.resetFields();
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
		}
		
		setClassMapToRequest(request, conductCertificateDetailsForm);
		setDataToForm(conductCertificateDetailsForm);
		conductCertificateDetailsForm.resetFields();
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
	}
	
	public ActionForward updateCandidateDetailsEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = conductCertificateDetailsForm.validate(mapping, request);
		Boolean upadated = false;
		setUserId(request, conductCertificateDetailsForm);
		if (errors.isEmpty()) {
			//Checking Duplication
			if(!ConductCertificateDetailsHandler.getInstance().verifyStudentsAllSaved(conductCertificateDetailsForm))
			{
				errors.add("error", new ActionError("knowledgepro.admin.StudentExist"));
				saveErrors(request, errors);
				conductCertificateDetailsForm.resetFields();
				return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
			}
			//Verifying Students are present or not
			if(!ConductCertificateDetailsHandler.getInstance().verifyStudentsPresent(conductCertificateDetailsForm))
			{
				errors.add("error", new ActionError("knowledgepro.admin.StudentNotAvailable"));
				saveErrors(request, errors);
				conductCertificateDetailsForm.resetFields();
				return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
			}
			
			/*if(!ConductCertificateDetailsHandler.getInstance().verifyGeneratedTCNumbers(conductCertificateDetailsForm))
			{
				errors.add("error", new ActionError("knowledgepro.admin.TCNumberAlreadyExists"));
				saveErrors(request, errors);
				tcDetailsForm.resetFields();
				return mapping.findForward(CMSConstants.INIT_TC_DETAILS);
			}*/
			upadated=ConductCertificateDetailsHandler.getInstance().getUpdateStatusEdit(conductCertificateDetailsForm);
			if(upadated){
				List<BoardDetailsTO> boardList = ConductCertificateDetailsHandler.getInstance().getListOfCandidates(conductCertificateDetailsForm);
				conductCertificateDetailsForm.setBoardList(boardList);
				ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.updated.successfully");
				messages.add("messages", message);
				saveMessages(request, messages);
				conductCertificateDetailsForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admission.tc.details.updated.failure"));
				saveErrors(request, errors);
				
			}
		} else {
			addErrors(request, errors);
			setClassMapToRequest(request, conductCertificateDetailsForm);
			setDataToForm(conductCertificateDetailsForm);
			conductCertificateDetailsForm.resetFields();
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
		}
		
		setClassMapToRequest(request, conductCertificateDetailsForm);
		setDataToForm(conductCertificateDetailsForm);
		conductCertificateDetailsForm.resetFields();
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.CC_DETAILS_RESULT);
	}
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		 ActionErrors errors = conductCertificateDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				String courseId = ConductCertificateDetailsHandler.getInstance().getCourseId(conductCertificateDetailsForm.getClassId());
				String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(conductCertificateDetailsForm.getClassId()),"Classes",true,"course.program.programType.id");
				conductCertificateDetailsForm.setProgramTypeId(programTypeId);
				conductCertificateDetailsForm.setCourseId(courseId);
				List<BoardDetailsTO> boardList = ConductCertificateDetailsHandler.getInstance().getListOfCandidates(conductCertificateDetailsForm);
				if (boardList.isEmpty()) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setClassMapToRequest(request, conductCertificateDetailsForm);
					setDataToForm(conductCertificateDetailsForm);
					log.info("Exit getCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
				}
				conductCertificateDetailsForm.setBoardList(boardList);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				conductCertificateDetailsForm.setErrorMessage(msg);
				conductCertificateDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setClassMapToRequest(request, conductCertificateDetailsForm);
			setDataToForm(conductCertificateDetailsForm);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.CC_DETAILS_RESULT);
	}
	
	public ActionForward getDetailsForAllStudentsByClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		//conductCertificateDetailsForm.reset();
		//TCDetailsHandler.getInstance().getAllStudentTCDetailsByClass(conductCertificateDetailsForm);
		setClassMapToRequest(request, conductCertificateDetailsForm);
		setDataToForm(conductCertificateDetailsForm);
		return mapping.findForward(CMSConstants.INIT_CC_DETAILS);
	}
	
	public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		 ActionErrors errors = conductCertificateDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				//Check TC Duplication for Student
				//TCDetailsHandler.getInstance().checkDuplicationForTcStudent(tcDetailsForm);
				ConductCertificateDetailsHandler.getInstance().getStudentTCDetails(conductCertificateDetailsForm);
				setClassMapToRequest(request, conductCertificateDetailsForm);
				setDataToForm(conductCertificateDetailsForm);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				conductCertificateDetailsForm.setErrorMessage(msg);
				conductCertificateDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.CC_DETAILS_RESULT);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.CC_DETAILS);
	}
	
	public ActionForward printCC(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		ConductCertificateDetailsForm conductCertificateDetailsForm = (ConductCertificateDetailsForm) form;
		try
		{		
			ActionErrors errors = conductCertificateDetailsForm.validate(mapping, request);
			if (errors.isEmpty())
			{
				List<PrintTcDetailsTo>studentList= null;				
				studentList=ConductCertificateDetailsHandler.getInstance().getStudentsByClass(request,conductCertificateDetailsForm);				
				conductCertificateDetailsForm.setStudentList(studentList);
				conductCertificateDetailsForm.resetFields();
			}
			else
			{
				addErrors(request, errors);
			}
		}
		catch (Exception e) {
			 String msg = super.handleApplicationException(e);
			 conductCertificateDetailsForm.setErrorMessage(msg);
			 conductCertificateDetailsForm.setErrorStack(e.getMessage());
	         return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return mapping.findForward(CMSConstants.PRINT_CC);		
	}	

}
