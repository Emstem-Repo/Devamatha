package com.kp.cms.actions.timetable;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.timetable.ViewTeacherWiseTimeTableForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.timetable.ViewTeacherWiseTimeTableHandler;
import com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions;
import com.kp.cms.transactionsimpl.timetable.ViewTeacherWiseTimeTableTxnImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ViewTeacherWiseTimeTableAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static final String INIT_TEACHER_WISE_TIME_TABLE = "initTeacherWiseTimeTable";
	public static final String INIT_STUDENT_TIME_TABLE = "initStudentTimeTable";
	public static final String INIT_CLASS_TIME_TABLE = "initClassTimeTable";
	public static final String INIT_DEPARTMENT_TIME_TABLE = "initDepartmentTimeTable";
	public static final String DEPARTMENT_TIME_TABLE = "departmentTimeTable";
	
	/** init method for Teacher  Time Table.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewTeacherWiseTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ViewTeacherWiseTimeTableForm objForm = (ViewTeacherWiseTimeTableForm)form;
		objForm.reset();
		try{
			setUserId(request, objForm);
			
//			get the Time Table details of Login User.
			
			ViewTeacherWiseTimeTableHandler.getInstance().getTeacherWiseTimeTableDetails(objForm);
			IViewTeacherWiseTimeTableTransactions transaction = ViewTeacherWiseTimeTableTxnImpl.getInstance();
			String PeriodCount=transaction.getHoursCount(objForm.getUserId());
			objForm.setPeriodCound(PeriodCount);
			
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(INIT_TEACHER_WISE_TIME_TABLE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewStudentTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ViewTeacherWiseTimeTableForm objForm = (ViewTeacherWiseTimeTableForm)form;
		HttpSession session = request.getSession(true);
		objForm.reset();
		try{
//			get the studentId & classId from the Session.
			
			String classId = session.getAttribute("ClassId").toString();
			String studentId = session.getAttribute("studentid").toString();
			
//			get the Time Table of Login Student .
			
			ViewTeacherWiseTimeTableHandler.getInstance().getStudentTimeTableDetails(studentId,classId,objForm);
			
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(INIT_STUDENT_TIME_TABLE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewClassTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ViewTeacherWiseTimeTableForm objForm = (ViewTeacherWiseTimeTableForm)form;
		objForm.reset();
		try{
			setUserId(request, objForm);
			setRequiredDatatoForm(objForm);
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(INIT_CLASS_TIME_TABLE);
	}
	/**
	 * @param timeTableForClassForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm( ViewTeacherWiseTimeTableForm objForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}// end
		if(objForm.getYear()!=null && !objForm.getYear().isEmpty())
			currentYear=Integer.parseInt(objForm.getYear());
		
		Map<Integer, String> classMap=CommonAjaxHandler.getInstance().getClassesByTeacher(Integer.valueOf(objForm.getUserId()),String.valueOf(currentYear));
		objForm.setClassMap(classMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewClassTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ViewTeacherWiseTimeTableForm objForm = (ViewTeacherWiseTimeTableForm)form;
		try{
			objForm.setFlag("true");
			ViewTeacherWiseTimeTableHandler.getInstance().getClassTimeTableData(objForm);
			int currentYear =  0;
			if(objForm.getYear()!=null && !objForm.getYear().isEmpty())
				currentYear=Integer.parseInt(objForm.getYear());
			
			Map<Integer, String> classMap=CommonAjaxHandler.getInstance().getClassesByTeacher(Integer.valueOf(objForm.getUserId()),String.valueOf(currentYear));
			objForm.setClassMap(classMap);
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(INIT_CLASS_TIME_TABLE);
	}
	
	public ActionForward initViewDeptWiseTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ViewTeacherWiseTimeTableForm objForm = (ViewTeacherWiseTimeTableForm)form;
		objForm.reset();
		try{
			IViewTeacherWiseTimeTableTransactions transaction = ViewTeacherWiseTimeTableTxnImpl.getInstance();
			Map<Integer,String> deptMap=transaction.getDeptList(objForm);
			objForm.setDepartmentMap(deptMap);
			
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(INIT_DEPARTMENT_TIME_TABLE);
	}
	
	public ActionForward viewDeptTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ViewTeacherWiseTimeTableForm objForm = (ViewTeacherWiseTimeTableForm)form;
		try{
			ViewTeacherWiseTimeTableHandler.getInstance().getDeptTimeTableData(objForm);;
			int currentYear =  0;
			if(objForm.getYear()!=null && !objForm.getYear().isEmpty())
				currentYear=Integer.parseInt(objForm.getYear());
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(DEPARTMENT_TIME_TABLE);
	}
}
