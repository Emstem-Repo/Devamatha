package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.admission.DDStatusForm;

public interface IDDStatusTransaction {

	boolean getAlreadyEntered(String query) throws Exception;

	boolean checkStudent(String query) throws Exception;

	AdmAppln updateStatus(String alreadyEnteredQuery,DDStatusForm dDStatusForm) throws Exception;

	boolean getAlreadyEntered1(String query) throws Exception;

	AdmAppln updateStatus1(String query, DDStatusForm dDStatusForm)	throws Exception;

	boolean updateChallanStatusOnCourse(DDStatusForm dDStatusForm)	throws Exception;
	
	List<AdmAppln> getStudentsChallanStatusOnCourse(DDStatusForm dDStatusForm) throws Exception;
	
	boolean updateDDStatusOnCourse(DDStatusForm dDStatusForm)	throws Exception;
	
	List<AdmAppln> getStudentsDDStatusOnCourse(DDStatusForm dDStatusForm) throws Exception;
	
	List<AdmAppln> getStudentsChallanDtailsOnDate(DDStatusForm dDStatusForm) throws Exception;
	
	boolean updateChallanUploadProcess(DDStatusForm dDStatusForm)	throws Exception;
	
	Integer ChallanNotVerifiedCount(DDStatusForm ddform) throws Exception;
	
	Integer ChallanVerifiedCount(DDStatusForm ddform) throws Exception;
	
	List<ExamRegularApplication> getStudentsChallanStatusOnCourseForExam(DDStatusForm ddForm) throws Exception;
	
	boolean updateChallanStatusOnCourseForExam(DDStatusForm ddForm)	throws Exception;
	
	public List<ExamRegularApplication> getStudentsChallanDtailsOnDateForExam(DDStatusForm ddForm) throws Exception;
	
	public boolean updateChallanUploadProcessForExam(DDStatusForm ddForm)	throws Exception;
	
	public List<ExamSupplementaryImprovementApplicationBO> getSupplementaryStudentWise(ExamRegularApplication examRegApp) throws Exception;
	
	public List<ExamSupplementaryImprovementApplicationBO> getSupplementaryFees(ExamRegularApplication examRegApp) throws Exception;
	
	public Double getFeesUpload(ExamRegularApplication examRegApp) throws Exception;
	
	List<ExamRevaluationApp> getStudentsChallanStatusOnCourseForRevaluation(DDStatusForm ddForm) throws Exception;

	boolean updateChallanStatusOnCourseForRevaluation(DDStatusForm ddForm)	throws Exception;

	public List<ExamRevaluationApp> getStudentsChallanDtailsOnDateForRevaluation(DDStatusForm ddForm) throws Exception;

	public boolean updateChallanUploadProcessForRevaluation(DDStatusForm ddForm)throws Exception;

	public List<FeePayment> getStudentsChallanDtailsOnDateForFee(DDStatusForm ddForm) throws Exception;

	public boolean updateChallanUploadProcessForFee(DDStatusForm ddForm)	throws Exception;
}
