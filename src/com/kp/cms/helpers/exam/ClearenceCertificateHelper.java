package com.kp.cms.helpers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.ClearenceCertificateForm;

public class ClearenceCertificateHelper {
	/**
	 * Singleton object of ClearenceCertificateHelper
	 */
	private static volatile ClearenceCertificateHelper clearenceCertificateHelper = null;
	private static final Log log = LogFactory.getLog(ClearenceCertificateHelper.class);
	private ClearenceCertificateHelper() {
		
	}
	/**
	 * return singleton object of ClearenceCertificateHelper.
	 * @return
	 */
	public static ClearenceCertificateHelper getInstance() {
		if (clearenceCertificateHelper == null) {
			clearenceCertificateHelper = new ClearenceCertificateHelper();
		}
		return clearenceCertificateHelper;
	}
	/**
	 * @param clearenceCertificateForm
	 * @return
	 * @throws Exception
	 */
	public String getCurrentClassStudentsQuery( ClearenceCertificateForm clearenceCertificateForm) throws Exception {
		String query="select s.id from Student s where (s.isHide = 0 or s.isHide is null) and s.admAppln.isCancelled=0 and s.classSchemewise.classes.id="+clearenceCertificateForm.getClassId(); 
		if(clearenceCertificateForm.getRegNoFrom()!=null && !clearenceCertificateForm.getRegNoFrom().isEmpty()){
			query=query+" and s.registerNo>='"+clearenceCertificateForm.getRegNoFrom()+"'";
		}
		if(clearenceCertificateForm.getRegNoTo()!=null && !clearenceCertificateForm.getRegNoTo().isEmpty()){
			query=query+" and s.registerNo<='"+clearenceCertificateForm.getRegNoTo()+"'";
		}
		return query;
	}
	/**
	 * @param clearenceCertificateForm
	 * @return
	 * @throws Exception
	 */
	public String getPreviousClassStudentsQuery( ClearenceCertificateForm clearenceCertificateForm) throws Exception {
		String query="select s.student.id from StudentPreviousClassHistory s where (s.student.isHide = 0 or s.student.isHide is null) and s.student.admAppln.isCancelled=0" +
		" and s.classes.id="+clearenceCertificateForm.getClassId();
		if(clearenceCertificateForm.getRegNoFrom()!=null && !clearenceCertificateForm.getRegNoFrom().isEmpty()){
			query=query+" and s.student.registerNo>='"+clearenceCertificateForm.getRegNoFrom()+"'";
		}
		if(clearenceCertificateForm.getRegNoTo()!=null && !clearenceCertificateForm.getRegNoTo().isEmpty()){
			query=query+" and s.student.registerNo<='"+clearenceCertificateForm.getRegNoTo()+"'";
		}
		return query;
	}
	/**
	 * @param clearenceCertificateForm
	 * @return
	 * @throws Exception
	 */
	public String getBlockedStudentsQuery(ClearenceCertificateForm clearenceCertificateForm) throws Exception {
		String query="from ExamBlockUnblockHallTicketBO e where (e.studentUtilBO.isHide = 0 or e.studentUtilBO.isHide is null) and e.studentUtilBO.admApplnUtilBO.isCancelled=0 and e.examId="+clearenceCertificateForm.getExamId()+" and e.hallTktOrMarksCard='"+clearenceCertificateForm.getHallTicketOrMarksCard()+"'";
		if(clearenceCertificateForm.getRegNoFrom()!=null && !clearenceCertificateForm.getRegNoFrom().isEmpty()){
			query=query+" and e.studentUtilBO.registerNo='"+clearenceCertificateForm.getRegNoFrom()+"'";
		}
		if(clearenceCertificateForm.getRegNoTo()!=null && !clearenceCertificateForm.getRegNoTo().isEmpty()){
			query=query+" and e.studentUtilBO.registerNo<='"+clearenceCertificateForm.getRegNoTo()+"'";
		}
		return query;
	}
	/**
	 * @param clearenceCertificateForm
	 * @return
	 * @throws Exception
	 */
	public String getBlockedStudentsQueryForOtherExam(ClearenceCertificateForm clearenceCertificateForm) throws Exception {
		String query="select e1.studentId,e1.examDefinitionUtilBO.name from ExamBlockUnblockHallTicketBO e1 where e1.examId <> "+clearenceCertificateForm.getExamId()+" and e1.hallTktOrMarksCard='"+clearenceCertificateForm.getHallTicketOrMarksCard()+"' and e1.studentId in(select e.studentId "+getBlockedStudentsQuery(clearenceCertificateForm) +")";
		return query;
	}
}
