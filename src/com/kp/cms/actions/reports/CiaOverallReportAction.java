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
import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.CiaOverallReportForm;
import com.kp.cms.handlers.reports.CiaOverallReportHandler;

public class CiaOverallReportAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CiaOverallReportAction.class);
	
	public ActionForward getStudentOverAllMarksDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CiaOverallReportForm overallReportForm = (CiaOverallReportForm) form;
		HttpSession session = request.getSession(true);
		try {
			int studentId = 0;
			if((String) session.getAttribute("studentid")!= null){
				studentId = Integer.parseInt((String) session.getAttribute("studentid"));
			}
			
			int curClassId = CiaOverallReportHandler.getInstance().getClassId(studentId);
			ExamPublishExamResultsBO examPublishExamResultsBO =  CiaOverallReportHandler.getInstance().getPublishedClassId(studentId, curClassId);
			int classId = 0;
			if(examPublishExamResultsBO!= null){
				classId = examPublishExamResultsBO.getClassId();
				if(examPublishExamResultsBO.getClassUtilBO()!= null){
					overallReportForm.setClassName(examPublishExamResultsBO.getClassUtilBO().getName());
				}
			}
			
			int examId = 0;
			if(classId > 0){
				examId = CiaOverallReportHandler.getInstance().getExamIdByClassId(classId);
			}
			if(examId > 0){
				overallReportForm.setCiaOverAllList(CiaOverallReportHandler.getInstance().getCiaOverAllMarks(studentId, classId, examId));
			}
		
		} catch (Exception e) {		
			log.error("Error while in Cia Overall Report"+e.getMessage());
			String msg = super.handleApplicationException(e);
			overallReportForm.setErrorMessage(msg);
			overallReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CIA_OVER_ALL_REPORT);
		
	
	}

}
