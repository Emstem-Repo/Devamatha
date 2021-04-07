package com.kp.cms.actions.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.InternalMarksEntryForm;
import com.kp.cms.forms.reports.BirtFeeReportForm;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;

public class BirtFeeReportAction  extends BaseDispatchAction{

	
	
	private static final Log log = LogFactory.getLog(BirtFeeReportAction.class);


	public ActionForward initBirtFeeReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtFeeReport. of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc())
			return mapping.findForward(CMSConstants.BIRT_FEE_REPORT_CJC);
		else
		return mapping.findForward(CMSConstants.BIRT_FEE_REPORT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initbirtAttendanceReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtFeeReport. of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc())
			return mapping.findForward(CMSConstants.BIRT_ATTENDANCE_REPORT_CJC);
		else
		return mapping.findForward(CMSConstants.BIRT_ATTENDANCE_REPORT);
	}
	
	
	
	public ActionForward showFeeDivisionReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		
		
		return mapping.findForward(CMSConstants.BIRT_FEE_REPORT_RESULT);
	}
	
	public ActionForward initBirtExamReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtFeeReport. of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc()){
			return mapping.findForward(CMSConstants.BIRT_EXAM_REPORT_CJC);
		}else
		return mapping.findForward(CMSConstants.BIRT_EXAM_REPORT);
	}
	
	
	
	
	
	public ActionForward showExamDivisionReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		return mapping.findForward(CMSConstants.BIRT_EXAM_REPORT_RESULT);
	}
	
	
	public ActionForward initBirtAdmissionReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtFeeReport. of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc()){
			return mapping.findForward(CMSConstants.BIRT_ADMISSION_REPORT_CJC);
		}else
				return mapping.findForward(CMSConstants.BIRT_ADMISSION_REPORT);
	
	}
	public ActionForward admissionBirtReportResult(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		
		
		return mapping.findForward(CMSConstants.BIRT_FEE_REPORT_RESULT);
	}
	
	public ActionForward admissionBirtReportResultForStudent(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		return mapping.findForward(CMSConstants.BIRT_FEE_REPORT_RESULT_STUDENT);
	}
	
	public ActionForward initBirtFacultyReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtFeeReport. of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc()){
			return mapping.findForward(CMSConstants.BIRT_FACULTY_REPORT_CJC);
		}else
		return mapping.findForward(CMSConstants.BIRT_FACULTY_REPORT);
	}
	
	public ActionForward showFacultyReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		
		
		return mapping.findForward(CMSConstants.BIRT_FACULTY_REPORT_RESULT);
	}
	public ActionForward pgMarksCardForStudent(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		return mapping.findForward(CMSConstants.BIRT_PG_MARKS_CARD);
	}
	
	public ActionForward initSupplimentaryReports(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		if(birtFeeReportForm.isCjc())
			return mapping.findForward(CMSConstants.BIRT_SUPPLIMENTARYREPORTS_CJC);
		else
			return mapping.findForward(CMSConstants.BIRT_SUPPLIMENTARYREPORTS);
	}
	public ActionForward showSupplimentaryReports(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		
		
		return mapping.findForward(CMSConstants.BIRT_SUPPLIMENTARYREPORT_RESULT);
	}
	public ActionForward initBirtPreExamReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtPreExamReport of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc())
			return mapping.findForward(CMSConstants.BIRT_PRE_EXAM_REPORT_CJC);
		else
			return mapping.findForward(CMSConstants.BIRT_PRE_EXAM_REPORT);
	
	}
	
	public ActionForward suplementaryHallTicket(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		return mapping.findForward(CMSConstants.BIRT_SUP_HALL_TICKET);
	}
	
	public ActionForward initCounsellorReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		return mapping.findForward(CMSConstants.BIRT_COUNSELLOR_REPORT);
	}
	public ActionForward supMarksCardForStudent(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		request.setAttribute("count",birtFeeReportForm.getCount());
		return mapping.findForward(CMSConstants.BIRT_SUP_MARKS_CARD);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMarksVerificationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initBirtFeeReport. of BirtFeeReportAction");	
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		if(birtFeeReportForm.isCjc())
			return mapping.findForward(CMSConstants.BIRT_MARKS_VERIFICATION_REPORT_CJC);
		else
			return mapping.findForward(CMSConstants.BIRT_MARKS_VERIFICATION_REPORT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMarksVerificationReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		request.setAttribute("reportName",birtFeeReportForm.getReportName());
		return mapping.findForward(CMSConstants.BIRT_MARKS_VERIFICATION_REPORT_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.INIT_REPORT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initBirtReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward(CMSConstants.BIRT_REPORT_LINK);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initBirtOverAllReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward(CMSConstants.BIRT_OVERALL_REPORT);
	}
	
	public ActionForward initBirtAbsenceReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward(CMSConstants.BIRT_ABSENCE_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward internalMarksReports(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("ExamId", request.getParameter("examId"));
		session.setAttribute("SubjectId",request.getParameter("subjectId"));
		session.setAttribute("ClassesId",request.getParameter("classId"));
		return mapping.findForward(CMSConstants.INTERNAL_MARKS_REPORT_PRINT);
	}
	public ActionForward initBirtViewStudentFeedBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		HttpSession session=request.getSession();
		setUserId(request, birtFeeReportForm);
		session.setAttribute("userId", birtFeeReportForm.getUserId());
		return mapping.findForward(CMSConstants.BIRT_VIEW_STUDENT_FEEDBACK);
	}
	
	public ActionForward initBirtViewStudentFeedBackByDepartment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		HttpSession session=request.getSession();
		setUserId(request, birtFeeReportForm);
		String depatrmentId=birtFeeReportForm.getUserId();
		ILoginTransaction loginTransaction=new LoginTransactionImpl();
		String strDepartmentId=loginTransaction.getDepartmentByUserId(depatrmentId);
		session.setAttribute("department", strDepartmentId);
		return mapping.findForward(CMSConstants.BIRT_VIEW_STUDENT_FEEDBACK_BY_DEPARTMENT);
	}
	public ActionForward initBirtCiaMarksHod(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		HttpSession session=request.getSession();
		setUserId(request, birtFeeReportForm);
		session.setAttribute("userId", birtFeeReportForm.getUserId());
		return mapping.findForward(CMSConstants.BIRT_CIAMARKS_HOD);	
	}
	public ActionForward initBirtAbsenceReportsLinkForStudentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward(CMSConstants.BIRT_ABSENCE_REPORT_LINK_STUDENT_DETAILS);
	}
	public ActionForward initBirtViewPeerFeedback(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		BirtFeeReportForm birtFeeReportForm = (BirtFeeReportForm)form;
		HttpSession session=request.getSession();
		setUserId(request, birtFeeReportForm);
		session.setAttribute("userId", birtFeeReportForm.getUserId());
		return mapping.findForward(CMSConstants.BIRT_VIEW_PEER_FEEDBACK);	
	}
	
	
}
