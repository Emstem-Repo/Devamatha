package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;

public interface INewSupplementaryImpApplicationTransaction {

	boolean saveSupplementarys( List<StudentSupplementaryImprovementApplication> boList) throws Exception;

	boolean deleteSupplementaryImpApp(String query) throws Exception;

	String getOldRegNo(int id, Integer termNumber) throws Exception;

	void updateAndGenerateRecieptNoOnlinePaymentReciept(OnlinePaymentReciepts onlinePaymentReciepts) throws Exception;

	List getSubjectsListForStudent(Student student,int academicYear) throws Exception;

	public boolean checkAttendanceAvailability(int studentId, int classId) throws Exception;
	
	public boolean checkCondonationAvailability(int studentId, int classId) throws Exception;
			
	public boolean checkDuplication(NewSupplementaryImpApplicationForm form) throws Exception;
				
	public boolean addAppliedStudent(ExamRegularApplication obj) throws Exception;
	
	public List getRegularApp(NewSupplementaryImpApplicationForm form)throws Exception;
	
	public boolean checkDuplicationRegularExamApp(NewSupplementaryImpApplicationForm form)throws Exception;
	
	public boolean checkSubmitRegApp(NewSupplementaryImpApplicationForm form)throws Exception;
	
	public boolean checkSubmitSuppApp(NewSupplementaryImpApplicationForm form)throws Exception;
	
	List getSubjectsListForRevaluation(Student student,int examId,int classid) throws Exception;
	
	boolean saveRevaluationApps(List<ExamRevaluationApplicationNew> boList)throws Exception;
	
	public boolean saveRevaluationAppWithChallanNos( ExamRevaluationApp examRevaluationApp) throws Exception;

	public ExamRevaluationApp checkDuplicationForRevaluation(NewSupplementaryImpApplicationForm form) throws Exception;
	
	public boolean saveRevaluationRecords( List<ExamRevaluationApplicationNew> boList) throws Exception;
	
    public boolean saveRevaluationAppRecords(ExamRevaluationApp bo) throws Exception ;
    
    boolean deleteRevaluationApp(String query) throws Exception;
    
    public boolean addAmountToExamRegApp(NewSupplementaryImpApplicationForm form,double amount)throws Exception;
    
	public List getSupplSubjectsListForRevaluation(Student student,int examId,int classid)
	throws Exception;

}
