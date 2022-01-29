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
import com.kp.cms.bo.admin.Period;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.ClassWiseAttendanceStatusForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ClassWiseAttendanceStatusHandler;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.transactions.attandance.IClassWiseAttendanceStatusTransaction;
import com.kp.cms.transactionsimpl.attendance.ClassWiseAttendanceStatusTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ClassWiseAttendanceStatusAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(ClassWiseAttendanceStatusAction.class);

	public ActionForward initClassWiseAttendanceStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse responce) throws Exception {
		log
				.debug("call of initClassWiseAttendanceStatus method in ClassWiseAttendanceStatusAction.class");
		ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm = (ClassWiseAttendanceStatusForm) form;
		try {
			classWiseAttendanceStatusForm.resetFields();
			request.getSession().setAttribute("PeriodList",null);
			request.getSession().setAttribute("ClassWiseAttendanceStatusDetails",null);
			setRequiredDatatoForm(classWiseAttendanceStatusForm, request);
		} catch (Exception e) {
			e.printStackTrace();
			log
					.error("Error in initClassWiseAttendanceStatus method in ClassWiseAttendanceStatusAction.class");
			log.error("Error is .." + e.getMessage());
			String msg = super.handleApplicationException(e);
			classWiseAttendanceStatusForm.setErrorMessage(msg);
			classWiseAttendanceStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
				.debug("end of initClassWiseAttendanceStatus method in ClassWiseAttendanceStatusAction.class");
		return mapping.findForward(CMSConstants.CLASSWISE_ATTENDANCE_STATUS);
	}

	public ActionForward showClassWiseAttendanceStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse responce) throws Exception {
		log
				.debug("call of showClassWiseAttendanceStatus method in ClassWiseAttendanceStatusAction.class");
		ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm = (ClassWiseAttendanceStatusForm) form;
		ActionErrors errors = classWiseAttendanceStatusForm.validate(mapping,
				request);
		if (errors.isEmpty()) {
			try {
				request.getSession().setAttribute("PeriodList",null);
				request.getSession().setAttribute("ClassWiseAttendanceStatusDetails",null);
				boolean isDatesIsBetweenCoCurriculumSchemeDuration = ClassWiseAttendanceStatusHandler
						.getInstance()
						.checkingDatesInCoCurriculumSchemeDuration(
								classWiseAttendanceStatusForm);
				
				if(!isDatesIsBetweenCoCurriculumSchemeDuration){
					errors.add("error", new ActionError("knowledgepro.attendance.classWiseAttendance.check"));
					saveErrors(request, errors);
					setRequiredDatatoForm(classWiseAttendanceStatusForm,request);
					return mapping.findForward(CMSConstants.CLASSWISE_ATTENDANCE_STATUS);
				}
				setRequiredDatatoForm(classWiseAttendanceStatusForm, request);
				IClassWiseAttendanceStatusTransaction impl=ClassWiseAttendanceStatusTransactionImpl.getInstance();
				List<String> periodList=impl.getPeriodNamesUsingClassId(classWiseAttendanceStatusForm);
				classWiseAttendanceStatusForm.setPeriodList(periodList);
				Map<String, Map<String,List<AttendanceTO>>> classWiseAttendanceStatusDetails=ClassWiseAttendanceStatusHandler.getAttendanceDetailsClassWise(classWiseAttendanceStatusForm, request);
				classWiseAttendanceStatusForm.setClassName(ClassWiseAttendanceStatusHandler.getInstance().getClassName(classWiseAttendanceStatusForm));
				if (classWiseAttendanceStatusDetails.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(classWiseAttendanceStatusForm, request);
					log.info("Exit  Deatils - getAttendanceDetailsClassWise size 0");
					return mapping.findForward(CMSConstants.CLASSWISE_ATTENDANCE_STATUS);
				} 
				request.getSession().setAttribute("PeriodList",periodList);
				request.getSession().setAttribute("ClassWiseAttendanceStatusDetails", classWiseAttendanceStatusDetails);
				

			} catch (Exception e) {
				e.printStackTrace();
				log
						.error("Error in showClassWiseAttendanceStatus method in ClassWiseAttendanceStatusAction.class");
				log.error("Error is .." + e.getMessage());
				String msg = super.handleApplicationException(e);
				classWiseAttendanceStatusForm.setErrorMessage(msg);
				classWiseAttendanceStatusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequiredDatatoForm(classWiseAttendanceStatusForm, request);
			return mapping
					.findForward(CMSConstants.CLASSWISE_ATTENDANCE_STATUS);
		}

		return mapping
				.findForward(CMSConstants.CLASSWISE_ATTENDANCE_STATUS);
	}

	public void setRequiredDatatoForm(
			ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm,
			HttpServletRequest request) {
		log
				.debug("call of setRequiredDatatoForm method in ClassWiseAttendanceStatusAction.class");
		Map<Integer, String> semisterMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
			if (classWiseAttendanceStatusForm.getYear() != null
					&& !classWiseAttendanceStatusForm.getYear().isEmpty()) {
				currentYear = Integer.parseInt(classWiseAttendanceStatusForm
						.getYear());
			}
			semisterMap = CommonAjaxHandler.getInstance().getSemisterByYear(
					currentYear);
			request.setAttribute("semisterMap", semisterMap);
			classWiseAttendanceStatusForm.setSemisterMap(semisterMap);
		} catch (Exception e) {
			log.debug(e.getMessage());
			log
					.error("Error occured in setRequiredDatatoForm of ClassWiseAttendanceStatusAction.class");
		}
		log
				.debug("end of setRequiredDatatoForm method in ClassWiseAttendanceStatusAction.class");

	}

}
