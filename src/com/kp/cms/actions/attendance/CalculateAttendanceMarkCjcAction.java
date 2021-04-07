package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.HashMap;
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
import com.kp.cms.actions.exam.NewUpdateProccessAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.CalculateAttendanceMarkCjcForm;
import com.kp.cms.handlers.attendance.CalculateAttendanceMarkHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.NewUpdateProccessHandler;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class CalculateAttendanceMarkCjcAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CalculateAttendanceMarkCjcAction.class);
	
	/**
	 * Method to set the required data to the form to display it in updateProcess.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUpdateProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initExamMarksEntry input");
		CalculateAttendanceMarkCjcForm calAttMarkCjcForm = (CalculateAttendanceMarkCjcForm) form;// Type casting the Action form to Required Form
		calAttMarkCjcForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(calAttMarkCjcForm, request);// setting the requested data to form
		log.info("Exit initExamMarksEntry input");
		return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_INIT);
	}
	
	
	
	
	/**
	 * @param calAttMarkCjcForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(CalculateAttendanceMarkCjcForm calAttMarkCjcForm,HttpServletRequest request) throws Exception{
		//ExamGenHandler handler = ExamGenHandler.getInstance();
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(calAttMarkCjcForm.getAcademicYear()!=null && !calAttMarkCjcForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(calAttMarkCjcForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
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
	public ActionForward getClasses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		CalculateAttendanceMarkCjcForm calAttMarkCjcForm = (CalculateAttendanceMarkCjcForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = calAttMarkCjcForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<ClassesTO> classesList=CalculateAttendanceMarkHandler.getInstance().getClasses(calAttMarkCjcForm);
				if (classesList.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(calAttMarkCjcForm, request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_INIT);
				} 
				calAttMarkCjcForm.setClassesList(classesList);
				//setNamesToForm(calAttMarkCjcForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				calAttMarkCjcForm.setErrorMessage(msg);
				calAttMarkCjcForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(calAttMarkCjcForm, request);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_INIT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_RESULT);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		CalculateAttendanceMarkCjcForm calAttMarkCjcForm = (CalculateAttendanceMarkCjcForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = calAttMarkCjcForm.validate(mapping, request);
		setUserId(request,calAttMarkCjcForm);
		if (errors.isEmpty()) {
			try {
				if (errors.isEmpty()) {
					boolean isUpdated =CalculateAttendanceMarkHandler.getInstance().updateProcess(calAttMarkCjcForm);
					if (isUpdated) {
						calAttMarkCjcForm.resetFields();
						messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.calculate.attendance.marks.success"));
						saveMessages(request, messages);
					}else{
					//	String errorMsg=getErrorMessage(calAttMarkCjcForm.getErrorList());
						//errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.noOfEval.update.process",errorMsg));
					//	errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.update.process.failure",calAttMarkCjcForm.getProcessName()));
						addErrors(request, errors);
					}
				}else {
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_RESULT);
					}
				setRequiredDatatoForm(calAttMarkCjcForm, request);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				calAttMarkCjcForm.setErrorMessage(msg);
				calAttMarkCjcForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(calAttMarkCjcForm, request);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		return mapping.findForward(CMSConstants.CALCULATE_ATTENDANCEMARK_INIT);
	}
	
	
}
