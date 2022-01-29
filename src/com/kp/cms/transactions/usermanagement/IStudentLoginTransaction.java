package com.kp.cms.transactions.usermanagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.InternalExamGrievanceBo;
import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.usermanagement.LoginForm;

public interface IStudentLoginTransaction {

	public boolean saveMobileNo(PersonalData personalData)throws Exception;

	public PersonalData getStudentPersonalData(int personalId)throws Exception;

	public List<FeePayment> getStudentPaymentMode(int id) throws Exception;

	public FeePayment getFeePaymentDetailsForEdit(int parseInt,int financialYear) throws Exception;

	public EvaStudentFeedbackOpenConnection isFacultyFeedbackAvailable(int id, Map<Integer,String> specializationIds)throws Exception;

	public Map<Integer,String> getSpecializationIds(int studentId)throws Exception;

	public boolean checkHonoursCourse(int studentId, int courseId) throws Exception;

	public boolean checkMandatoryCertificateCourse(AdmAppln admAppln)throws Exception;
	
	public boolean checkingStudentIsAppliedForSAPExam(int studentId)throws Exception;
	
	public UploadSAPMarksBo getSAPExamResults(String studentId)throws Exception;

	public Map<Integer, String> getSubjectsListForStudent(LoginForm loginForm) throws Exception;
	
	public String getQueryForStudentInternalMarksStudentLogin(LoginForm form, int academicYearOfSemester) throws Exception;

	public List<Object[]> getDataForStudentInternalMarksStudentLogin(String queryForMarks) throws Exception;
	
	public boolean saveGrievance(List<GrievanceRemarkBo> grievanceRemarkBoList) throws Exception;
	
	public Integer getHodIdBySubjectId(Integer subjectId) throws Exception ;
	
	public GrievanceRemarkBo checkDuplicates(int examId,int studentId,int subjectId) throws Exception ;
	
	public GrievanceNumber getGrievancePrifixByYear(String year) throws Exception;
	
	public boolean updateGrievanceMaster(GrievanceNumber grievanceNumber,Integer curentNo) throws Exception;
	
	public List<GrievanceRemarkBo> getGrievanceList(LoginForm loginForm)throws Exception;
	
	public GrievanceRemarkBo getGrievanceNoByStuentId(int studentId) throws Exception ;
	
	public String getQueryForStudentInternalMarksStudentLoginGrievance(LoginForm form,String classid) throws Exception;
	
	public boolean saveInternalGrievance(List<InternalExamGrievanceBo> grievanceRemarkBoList) throws Exception ;

	public boolean saveServiceLearningActivity(ServiceLearningActivityBo serviceLearningActivityBo) throws Exception ;
	
	public List<ServiceLearningActivityBo> getServiceLearningActivity(LoginForm loginForm )throws Exception;
	
	public boolean isDupServiceLearningActivity(ServiceLearningActivityBo serviceLearningActivityBo) throws Exception ;

	public List getDataForQuery(String listQuery)throws Exception;

	public boolean checkTimeTableHistory(String classId)throws Exception;

	public List<Object[]> getPaymentDetails(int categoryId, int courseId, int termNo, Integer academicYear) throws Exception;

	public FeeCategory getDetails(int feeCatId) throws Exception;

	public FeeHeading getHeadingStu(int feeheading) throws Exception;

	public Student getStudentDetails(String studentid) throws Exception;

	public String generateCandidateRefNoOnline(CandidatePGIDetails bo, LoginForm loginForm) throws Exception;

	public boolean updateReceivedStatusStudentLogin(CandidatePGIDetails bo,
			LoginForm loginForm, Student student) throws Exception;

	public boolean addFeePaymentDetail(FeePayment feePayment) throws Exception;

	public Object checkCourseAidedOrNotStudent(String courseId) throws Exception;

	public FeeBillNumber getFeeBillNoYear1(int id,
			Object checkCourseAidedOrNotStudent) throws Exception;

	public boolean updateFeeBillNumberStudent1(FeeBillNumber billNumber) throws Exception;

	public boolean getFeePaymentDetailsOfStudent(LoginForm loginForm,
			HttpServletRequest request) throws Exception;

	public List<FeePayment> getPaymentList(String studentid, String classId) throws Exception;

	public FeePayment getDetailsOfstudent(String studentid, String classId) throws Exception;

	public CandidatePGIDetails getStatus(String studentName, String status, String mode) throws Exception;

	public FeeBillNumber getlBillNo(FeeFinancialYear feeFinancialYear) throws Exception;

	public boolean updateFeeBillNo(FeeBillNumber bo) throws Exception;

	public SemesterWiseCourseBO isSemesterWise(int courseId) throws Exception;

	public List<Object[]> getPaymentDetailsSemesterWise(int categoryId,
			int courseId, int year, int semNo, Integer academicYear) throws Exception;

	public CandidatePGIDetails getDataTxn(String admAppln) throws Exception;

	public FeePayment getRecord(int appnId) throws Exception;

	public List<FeePaymentDetail> getDataForStudent(String studentid, String classId) throws Exception;

	List<DocChecklist> getExamtypes(int courseId, int year) throws Exception;

	public boolean saveAppBO(AdmAppln appBO) throws ApplicationException, BusinessException;

}
