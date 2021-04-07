package com.kp.cms.handlers.usermanagement;

import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeFinancialYear;
import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.bo.admin.Period;
import java.util.Collections;
import com.kp.cms.transactions.admin.StudentLoginTO;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.transactions.serviceLearning.IProgrammeEntryTransaction;
import com.kp.cms.helpers.serviceLearning.ProgrammeEntryHelper;
import com.kp.cms.transactionsimpl.serviceLearning.ProgrammeEntryTransactionImpl;
import com.kp.cms.transactions.serviceLearning.IServiceLearningMarksEntryTransaction;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.transactionsimpl.serviceLearning.ServiceLearningMarksEntryTransactionImpl;
import com.kp.cms.to.serviceLearning.ServiceLearningMarksEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import java.util.ArrayList;
import com.kp.cms.to.serviceLearning.ServiceLearningActivityTo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.bo.exam.InternalExamGrievanceBo;
import com.kp.cms.transactions.IGrievanceStudentTransaction;
import com.kp.cms.helpers.exam.GrievanceStudentHelper;
import com.kp.cms.transactionsimpl.exam.GrievanceStudentTransactionImpl;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.to.exam.GrievanceStatusTo;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.bo.admin.AdmAppln;
import java.util.Iterator;
import java.io.FileOutputStream;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.StudentLogin;
import java.util.HashMap;
import com.kp.cms.handlers.exam.DownloadHallTicketHandler;
import com.kp.cms.utilities.OnlinePaymentUtils;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import java.util.Date;
import java.math.BigDecimal;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import java.util.Map;
import javax.servlet.http.HttpSession;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.CommonUtil;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.bo.admin.FeePayment;
import java.util.List;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.transactions.usermanagement.IStudentLoginTransaction;
import com.kp.cms.helpers.usermanagement.StudentLoginHelper;
import com.kp.cms.transactionsimpl.usermanagement.StudentLoginTransactionImpl;
import com.kp.cms.transactionsimpl.admin.CertificateRequestOnlineImpl;
import com.kp.cms.transactions.admin.ICertificateRequestOnlineTransaction;

public class StudentLoginHandler
{
    private static volatile StudentLoginHandler studentLoginHandler;
    ICertificateRequestOnlineTransaction txn;
    
    static {
        StudentLoginHandler.studentLoginHandler = null;
    }
    
    public StudentLoginHandler() {
        this.txn = (ICertificateRequestOnlineTransaction)new CertificateRequestOnlineImpl();
    }
    
    public static StudentLoginHandler getInstance() {
        if (StudentLoginHandler.studentLoginHandler == null) {
            return StudentLoginHandler.studentLoginHandler = new StudentLoginHandler();
        }
        return StudentLoginHandler.studentLoginHandler;
    }
    
    public boolean saveMobileNo(final String mobileNo, final String userId, final int personalId) throws Exception {
        boolean isAdded = false;
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final PersonalData studentData = iStudentLoginTransaction.getStudentPersonalData(personalId);
        final PersonalData personalData = StudentLoginHelper.getInstance().copyFormToBO(mobileNo, userId, studentData);
        isAdded = iStudentLoginTransaction.saveMobileNo(personalData);
        return isAdded;
    }
    
    public LoginForm getMobileNo(LoginForm loginForm) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final int personalId = loginForm.getPersonalDateId();
        final PersonalData studentData = iStudentLoginTransaction.getStudentPersonalData(personalId);
        loginForm = StudentLoginHelper.getInstance().copyBoToForm(studentData);
        return loginForm;
    }
    
    public boolean getStudentPaymentMode(final int id, final LoginForm loginForm) throws Exception {
        boolean isSmartCardPayment = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final List<FeePayment> feeList = (List<FeePayment>)transaction.getStudentPaymentMode(id);
        if (feeList != null && !feeList.isEmpty()) {
            isSmartCardPayment = true;
        }
        final List<FeePaymentTO> feePaymentToList = (List<FeePaymentTO>)StudentLoginHelper.getInstance().convertFeePaymentBOtoTO((List)feeList);
        loginForm.setFeeToList((List)feePaymentToList);
        return isSmartCardPayment;
    }
    
    public void getChallanData(final LoginForm loginForm, final HttpServletRequest request) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        int financialYear = 0;
        if (loginForm.getFinancialYear() != null) {
            financialYear = Integer.parseInt(loginForm.getFinancialYear());
        }
        final FeePayment feePayment = transaction.getFeePaymentDetailsForEdit(Integer.parseInt(loginForm.getBillNo()), financialYear);
        StudentLoginHelper.getInstance().copyFeeChallanPrintData(feePayment, loginForm, request);
    }
    
    public int getStudentGroupIdForCourse(final int id) throws Exception {
        final String groupId = PropertyUtil.getDataForUnique("select concat(c.ccGroup.id,'') from CCGroupCourse c where c.isActive=1 and c.ccGroup.isActive=1 and c.course.id=" + id + " and '" + CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()) + "' between c.ccGroup.startDate and c.ccGroup.endDate");
        if (groupId != null && !groupId.isEmpty()) {
            return Integer.parseInt(groupId);
        }
        return 0;
    }
    
    public boolean checkOnlineReciepts(final int id) throws Exception {
        final List<OnlinePaymentReciepts> list = (List<OnlinePaymentReciepts>)StudentLoginHelper.getInstance().getOnlineReciepts(id);
        return !list.isEmpty();
    }
    
    public List<OnlinePaymentRecieptsTo> getOnlinePaymentReciepts(final int studentId, final HttpServletRequest request) throws Exception {
        final List<OnlinePaymentReciepts> list = (List<OnlinePaymentReciepts>)StudentLoginHelper.getInstance().getOnlineReciepts(studentId);
        return (List<OnlinePaymentRecieptsTo>)StudentLoginHelper.getInstance().convertOnlineRecieptToToList((List)list, request);
    }
    
    public boolean isFacultyFeedbackAvailable(final int id, final HttpSession session, final Map<Integer, String> specializationIds) throws Exception {
        boolean isAvailable = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final EvaStudentFeedbackOpenConnection connections = transaction.isFacultyFeedbackAvailable(id, (Map)specializationIds);
        session.setAttribute("SESSIONID", (Object)null);
        session.setAttribute("ISEXITEVAL", (Object)null);
        if (connections != null && !connections.toString().isEmpty()) {
            session.setAttribute("SESSIONID", (Object)connections.getFeedbackSession().getId());
            session.setAttribute("ISEXITEVAL", (Object)connections.getIsExitEval());
            isAvailable = true;
        }
        return isAvailable;
    }
    
    public boolean verifySmartCard(final String smartCardNo, final int studentId) throws Exception {
        final String query = "select s.smartCardNo from Student s where s.id=" + studentId + " and s.smartCardNo like '%" + smartCardNo + "'";
        final INewExamMarksEntryTransaction txn = (INewExamMarksEntryTransaction)NewExamMarksEntryTransactionImpl.getInstance();
        final List list = txn.getDataForQuery(query);
        return list != null && !list.isEmpty();
    }
    
    public boolean saveSupplementaryApplicationForStudentLogin(final LoginForm loginForm) throws Exception {
        final OnlinePaymentReciepts onlinePaymentReciepts = new OnlinePaymentReciepts();
        final PcFinancialYear pcFinancialYear = new PcFinancialYear();
        pcFinancialYear.setId(loginForm.getFinId());
        onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
        final Student student = new Student();
        student.setId(loginForm.getStudentId());
        onlinePaymentReciepts.setStudent(student);
        onlinePaymentReciepts.setTotalAmount(new BigDecimal(loginForm.getTotalFees()));
        onlinePaymentReciepts.setApplicationFor("Revaluation/Retotalling Application");
        onlinePaymentReciepts.setCreatedBy(loginForm.getUserId());
        onlinePaymentReciepts.setCreatedDate(new Date());
        onlinePaymentReciepts.setLastModifiedDate(new Date());
        onlinePaymentReciepts.setModifiedBy(loginForm.getUserId());
        onlinePaymentReciepts.setTransactionDate(new Date());
        onlinePaymentReciepts.setIsActive(Boolean.valueOf(true));
        PropertyUtil.getInstance().save((Object)onlinePaymentReciepts);
        boolean isPaymentSuccess = false;
        if (onlinePaymentReciepts.getId() > 0) {
            loginForm.setOnlinePaymentId(onlinePaymentReciepts.getId());
            final String registerNo = NewSecuredMarksEntryHandler.getInstance().getPropertyValue(loginForm.getStudentId(), "Student", true, "registerNo");
            OnlinePaymentUtils.dedactAmountFromAccount(registerNo, onlinePaymentReciepts.getId(), loginForm.getTotalFees(), onlinePaymentReciepts);
            if (!onlinePaymentReciepts.getIsPaymentFailed()) {
                isPaymentSuccess = true;
                this.txn.updateAndGenerateRecieptNoOnlinePaymentReciept(onlinePaymentReciepts);
            }
            else {
                loginForm.setMsg(onlinePaymentReciepts.getStatus());
            }
        }
        if (isPaymentSuccess) {
            final boolean isUpdate = DownloadHallTicketHandler.getInstance().saveRevaluationData(loginForm);
            return isUpdate;
        }
        return false;
    }
    
    public Map<Integer, String> getSpecializationIds(final LoginForm loginForm) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        Map<Integer, String> map = new HashMap<Integer, String>();
        if (loginForm.getStudentId() != 0) {
            final int studentId = loginForm.getStudentId();
            map = (Map<Integer, String>)transaction.getSpecializationIds(studentId);
        }
        return map;
    }
    
    public boolean checkHonoursCourse(final int studentId, final int courseId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        return transaction.checkHonoursCourse(studentId, courseId);
    }
    
    public void setStudentDetailsToForm(final StudentLogin studentLogin, final HttpSession session, final LoginForm loginForm) throws Exception {
        if (studentLogin.getStudent() != null && studentLogin.getStudent().getAdmAppln() != null && studentLogin.getStudent().getAdmAppln().getApplnDocs() != null) {
            for (final ApplnDoc doc : studentLogin.getStudent().getAdmAppln().getApplnDocs()) {
                if (doc.getName() != null && doc.getIsPhoto() && session != null) {
                    final FileOutputStream fos = new FileOutputStream(String.valueOf(CMSConstants.STUDENT_PHOTO_PATH) + "images/StudentPhotos/" + studentLogin.getStudent().getId() + ".jpg");
                    fos.write(doc.getDocument());
                    fos.close();
                    CMSConstants.STUDENT_IMAGE = "images/StudentPhotos/" + studentLogin.getStudent().getId() + ".jpg";
                }
            }
        }
        loginForm.setDisplaySem1and2("false");
        if (studentLogin.getStudent() != null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId() != null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType() != null) {
            loginForm.setProgramTypeId(String.valueOf(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()));
        }
        if (studentLogin.getStudent().getAdmAppln().getAppliedYear() != null && studentLogin.getStudent().getAdmAppln().getAppliedYear() > 0) {
            loginForm.setBatch(String.valueOf(studentLogin.getStudent().getAdmAppln().getAppliedYear()));
        }
        if (studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth() != null) {
            loginForm.setDateOfBirth(CommonUtil.getStringDate(studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()));
        }
        if (studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth() != null) {
            loginForm.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
        }
        if (studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail() != null) {
            loginForm.setContactMail(studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail());
        }
        if (studentLogin.getStudent().getBankAccNo() != null) {
            loginForm.setBankAccNo(studentLogin.getStudent().getBankAccNo());
        }
        if (studentLogin.getStudent().getRegisterNo() != null) {
            loginForm.setRegNo(studentLogin.getStudent().getRegisterNo());
        }
        if (studentLogin.getStudent() != null && studentLogin.getStudent().getAdmAppln() != null && studentLogin.getStudent().getAdmAppln().getPersonalData() != null) {
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getFatherName() != null) {
                loginForm.setFatherName(studentLogin.getStudent().getAdmAppln().getPersonalData().getFatherName());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getMotherName() != null) {
                loginForm.setMotherName(studentLogin.getStudent().getAdmAppln().getPersonalData().getMotherName());
            }
            String studentName = "";
            String phoneNo = "";
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName() != null) {
                studentName = studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName();
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getMiddleName() != null) {
                studentName = String.valueOf(studentName) + " " + studentLogin.getStudent().getAdmAppln().getPersonalData().getMiddleName();
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getLastName() != null) {
                studentName = String.valueOf(studentName) + " " + studentLogin.getStudent().getAdmAppln().getPersonalData().getLastName();
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine1() != null) {
                loginForm.setCurrentAddress1(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine1());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine2() != null) {
                loginForm.setCurrentAddress2(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine2());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByCurrentAddressCityId() != null) {
                loginForm.setCurrentCity(studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByCurrentAddressCityId());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId() != null && studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName() != null) {
                loginForm.setCurrentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId() == null && studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers() != null && !studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers().isEmpty()) {
                loginForm.setCurrentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressZipCode() != null) {
                loginForm.setCurrentPincode(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressZipCode());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine1() != null) {
                loginForm.setPermanentAddress1(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine1());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine2() != null) {
                loginForm.setPermanentAddress2(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine2());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId() != null) {
                loginForm.setPermanentCity(studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId() != null && studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName() != null) {
                loginForm.setPermanentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId() == null && studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers() != null && !studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers().isEmpty()) {
                loginForm.setPermanentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressZipCode() != null) {
                loginForm.setPermanentPincode(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressZipCode());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getNationality() != null && studentLogin.getStudent().getAdmAppln().getPersonalData().getNationality().getName() != null) {
                loginForm.setNationality(studentLogin.getStudent().getAdmAppln().getPersonalData().getNationality().getName());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getBloodGroup() != null) {
                loginForm.setBloodGroup(studentLogin.getStudent().getAdmAppln().getPersonalData().getBloodGroup());
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo1() != null) {
                phoneNo = studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo1();
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo2() != null) {
                phoneNo = String.valueOf(phoneNo) + "-" + studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo2();
            }
            if (studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo3() != null) {
                phoneNo = String.valueOf(phoneNo) + "-" + studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo3();
            }
            loginForm.setPhNo1(phoneNo);
            loginForm.setStudentName(studentName);
            loginForm.setStudentId(studentLogin.getStudent().getId());
            loginForm.setCourseId(String.valueOf(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId()));
            loginForm.setYear(Integer.toString(studentLogin.getStudent().getAdmAppln().getAppliedYear()));
            loginForm.setEnteredDob((String)null);
        }
        if (studentLogin.getStudent() != null && studentLogin.getStudent().getClassSchemewise() != null && studentLogin.getStudent().getClassSchemewise().getClasses() != null && studentLogin.getStudent().getClassSchemewise().getClasses().getName() != null) {
            loginForm.setClassName(studentLogin.getStudent().getClassSchemewise().getClasses().getName());
            loginForm.setClassId(String.valueOf(studentLogin.getStudent().getClassSchemewise().getClasses().getId()));
            loginForm.setTermNo((int)studentLogin.getStudent().getClassSchemewise().getClasses().getTermNumber());
        }
        if (studentLogin.getStudent().getAdmAppln().getPersonalData().getMobileNo2() != null) {
            loginForm.setMobileNo(studentLogin.getStudent().getAdmAppln().getPersonalData().getMobileNo2());
        }
        if (studentLogin.getStudent().getAdmAppln().getPersonalData().getId() != 0) {
            loginForm.setPersonalDateId(studentLogin.getStudent().getAdmAppln().getPersonalData().getId());
        }
        if (studentLogin.getStudent().getAdmAppln().getPersonalData().getUniversityEmail() != null) {
            loginForm.setUnivEmailId(studentLogin.getStudent().getAdmAppln().getPersonalData().getUniversityEmail());
        }
    }
    
    public boolean checkCourseIsMandatory(final AdmAppln admAppln) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final boolean isCourseMandatory = transaction.checkMandatoryCertificateCourse(admAppln);
        return isCourseMandatory;
    }
    
    public boolean isAppliedForSAPExam(final int studentId) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final boolean isApplied = iStudentLoginTransaction.checkingStudentIsAppliedForSAPExam(studentId);
        return isApplied;
    }
    
    public UploadSAPMarksBo getSAPExamResuls(final String studentId) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final UploadSAPMarksBo bo = iStudentLoginTransaction.getSAPExamResults(studentId);
        return bo;
    }
    
    public void displayInternalMarkForStudent(final LoginForm loginForm) {
    }
    
    public Map<Integer, String> getSubjectsListForStudent(final LoginForm loginForm) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final Map<Integer, String> subjectMap = (Map<Integer, String>)iStudentLoginTransaction.getSubjectsListForStudent(loginForm);
        return subjectMap;
    }
    
    public MarksCardTO getStudentInternalMarksStudentLogin(final LoginForm form) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        MarksCardTO to = null;
        final Integer academicYearForSem = Integer.parseInt(CommonAjaxHandler.getInstance().getAcademicYearForClass(form.getStudentId(), form.getTermNo()));
        final String queryForMarks = iStudentLoginTransaction.getQueryForStudentInternalMarksStudentLogin(form, (int)academicYearForSem);
        final List<Object[]> internalMarksCardData = (List<Object[]>)iStudentLoginTransaction.getDataForStudentInternalMarksStudentLogin(queryForMarks);
        if (internalMarksCardData.size() != 0) {
            to = StudentLoginHelper.getInstance().convertInternalMarksBotoTo((List)internalMarksCardData);
        }
        return to;
    }
    
    public MarksCardTO getStudentInternalMarksStudentLoginGrievance(final LoginForm form, final String classid) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        MarksCardTO to = null;
        final String queryForMarks = iStudentLoginTransaction.getQueryForStudentInternalMarksStudentLoginGrievance(form, classid);
        final List<Object[]> internalMarksCardData = (List<Object[]>)iStudentLoginTransaction.getDataForStudentInternalMarksStudentLogin(queryForMarks);
        if (internalMarksCardData.size() != 0) {
            to = StudentLoginHelper.getInstance().convertInternalMarksBotoToGrievance((List)internalMarksCardData);
        }
        return to;
    }
    
    public boolean saveGrievance(final List<GrievanceRemarkBo> grievanceRemarkBoList) throws Exception {
        boolean isSaved = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        isSaved = transaction.saveGrievance((List)grievanceRemarkBoList);
        return isSaved;
    }
    
    public Integer getHodIdBySubjectId(final int subjectId) throws Exception {
        Integer hodId = 0;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        hodId = transaction.getHodIdBySubjectId(Integer.valueOf(subjectId));
        return hodId;
    }
    
    public GrievanceRemarkBo checkDuplicates(final int examId, final int studentId, final int subjectId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final GrievanceRemarkBo dupGrievanceRemarkBo = transaction.checkDuplicates(examId, studentId, subjectId);
        return dupGrievanceRemarkBo;
    }
    
    public GrievanceNumber getGrievancePrifixByYear(final String year) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final GrievanceNumber grievanceNumber = transaction.getGrievancePrifixByYear(year);
        return grievanceNumber;
    }
    
    public boolean updateGrievanceMaster(final GrievanceNumber grievanceNumber, final Integer curentNo) throws Exception {
        boolean isUpdated = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        isUpdated = transaction.updateGrievanceMaster(grievanceNumber, curentNo);
        return isUpdated;
    }
    
    public List<GrievanceStatusTo> getGrievanceList(final LoginForm loginForm) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final List<GrievanceRemarkBo> grievanceNumberList = (List<GrievanceRemarkBo>)transaction.getGrievanceList(loginForm);
        final List<GrievanceStatusTo> grievanceStatusToList = (List<GrievanceStatusTo>)StudentLoginHelper.getInstance().convertBotoTo((List)grievanceNumberList);
        return grievanceStatusToList;
    }
    
    public List<GrievanceStudentTo> getStudentDetails(final LoginForm loginForm) throws Exception {
        final IGrievanceStudentTransaction iGrievanceStudentTransaction = (IGrievanceStudentTransaction)GrievanceStudentTransactionImpl.getInstance();
        final List<GrievanceRemarkBo> grievanceRemarkBoList = (List<GrievanceRemarkBo>)iGrievanceStudentTransaction.getStudentDetails(loginForm);
        final List<GrievanceStudentTo> grievanceStudentToList = (List<GrievanceStudentTo>)GrievanceStudentHelper.getInstance().convertBotoGrievanceTo((List)grievanceRemarkBoList, loginForm);
        return grievanceStudentToList;
    }
    
    public GrievanceRemarkBo getGrievanceNoByStuentId(final int studentId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final GrievanceRemarkBo dupGrievanceRemarkBo = transaction.getGrievanceNoByStuentId(studentId);
        return dupGrievanceRemarkBo;
    }
    
    public boolean saveInternalGrievance(final List<InternalExamGrievanceBo> internalExamGrievanceBoList) throws Exception {
        boolean isSaved = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        isSaved = transaction.saveInternalGrievance((List)internalExamGrievanceBoList);
        return isSaved;
    }
    
    public boolean saveServiceLearningActivity(final ServiceLearningActivityBo serviceLearningActivityBo) throws Exception {
        boolean isAdded = false;
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        isAdded = iStudentLoginTransaction.saveServiceLearningActivity(serviceLearningActivityBo);
        return isAdded;
    }
    
    public boolean isDupServiceLearningActivity(final ServiceLearningActivityBo serviceLearningActivityBo) throws Exception {
        boolean isDuplicate = false;
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        isDuplicate = iStudentLoginTransaction.isDupServiceLearningActivity(serviceLearningActivityBo);
        return isDuplicate;
    }
    
    public List<ServiceLearningActivityTo> convertBotoTo(final LoginForm loginForm) throws Exception {
        final IStudentLoginTransaction iStudentLoginTransaction = (IStudentLoginTransaction)StudentLoginTransactionImpl.getInstance();
        final List<ServiceLearningActivityTo> serviceLearningActivityToList = new ArrayList<ServiceLearningActivityTo>();
        final List<ServiceLearningActivityBo> serviceLearningActivityBoList = (List<ServiceLearningActivityBo>)iStudentLoginTransaction.getServiceLearningActivity(loginForm);
        for (final ServiceLearningActivityBo serviceLearningActivityBo : serviceLearningActivityBoList) {
            final ServiceLearningActivityTo serviceLearningActivityTo = new ServiceLearningActivityTo();
            final DepartmentNameEntryTo departmentNameEntryTo = new DepartmentNameEntryTo();
            final ProgrammeEntryTo programmeEntryTo = new ProgrammeEntryTo();
            serviceLearningActivityTo.setLearningAndActivity(serviceLearningActivityBo.getLearningAndActivity());
            serviceLearningActivityTo.setAttendedHours(serviceLearningActivityBo.getAttendedHours());
            serviceLearningActivityTo.setStudentId(String.valueOf(serviceLearningActivityBo.getStudent().getId()));
            departmentNameEntryTo.setDepartmentName(serviceLearningActivityBo.getDepartmentNameEntry().getDepartmentName());
            programmeEntryTo.setProgrammeName(serviceLearningActivityBo.getProgrammeEntryBo().getProgrammeName());
            serviceLearningActivityTo.setDepartmentNameEntryTo(departmentNameEntryTo);
            serviceLearningActivityTo.setProgrammeEntryTo(programmeEntryTo);
            serviceLearningActivityToList.add(serviceLearningActivityTo);
        }
        return serviceLearningActivityToList;
    }
    
    public List<ServiceLearningMarksEntryTo> getStudentDetailsById(final LoginForm loginForm) throws Exception {
        final IServiceLearningMarksEntryTransaction transaction = (IServiceLearningMarksEntryTransaction)new ServiceLearningMarksEntryTransactionImpl();
        final List<ServiceLearningMarksEntryTo> serviceLearningMarksEntryToList = new ArrayList<ServiceLearningMarksEntryTo>();
        final List<ServiceLearningMarksEntryBo> serviceLearningMarksEntryBoList = (List<ServiceLearningMarksEntryBo>)transaction.getStudentDetailsById(loginForm.getStudentId());
        double totalCredits = 0.0;
        if (serviceLearningMarksEntryBoList != null) {
            for (final ServiceLearningMarksEntryBo bo : serviceLearningMarksEntryBoList) {
                final ServiceLearningMarksEntryTo to = new ServiceLearningMarksEntryTo();
                to.setProgramName(bo.getProgrammeEntryBo().getProgrammeName());
                to.setDepttName(bo.getDepartmentNameEntry().getDepartmentName());
                to.setCredit(bo.getMark());
                totalCredits += Double.parseDouble(bo.getMark());
                loginForm.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
                loginForm.setRegNo(bo.getStudent().getRegisterNo());
                loginForm.setCourseName(bo.getStudent().getClassSchemewise().getClasses().getCourse().getName());
                loginForm.setYear(String.valueOf(bo.getStudent().getAdmAppln().getAppliedYear()));
                final ServiceLearningActivityBo bo2 = transaction.getServiceLearningActivity(bo.getProgrammeEntryBo().getId(), bo.getDepartmentNameEntry().getId(), bo.getStudent().getId());
                if (bo2 != null) {
                    to.setLearningAndOutcome(bo2.getLearningAndActivity());
                }
                serviceLearningMarksEntryToList.add(to);
            }
        }
        loginForm.setOverallMark(String.valueOf(totalCredits));
        return serviceLearningMarksEntryToList;
    }
    
    public List<ProgrammeEntryTo> getProgrammeNameEntry() throws Exception {
        final IProgrammeEntryTransaction transaction = (IProgrammeEntryTransaction)new ProgrammeEntryTransactionImpl();
        final List<ProgrammeEntryBo> programmeEntryBoList = (List<ProgrammeEntryBo>)transaction.getProgrammeNameEntryRestrictionByDate();
        ProgrammeEntryHelper.getInstance();
        final List<ProgrammeEntryTo> programmeEntryToList = (List<ProgrammeEntryTo>)ProgrammeEntryHelper.convertBOsToTos((List)programmeEntryBoList);
        return programmeEntryToList;
    }
    
    public List<StudentLoginTO> getTimeTableForInputClass(final LoginForm loginForm) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final String priodListQuery = "from Period p where p.isActive=1 and p.classSchemewise.id=" + loginForm.getClassId();
        final List<Period> periodList = (List<Period>)transaction.getDataForQuery(priodListQuery);
        if (periodList == null && periodList.isEmpty()) {
            loginForm.setMsg("Periods Must be Entered For A Given Class");
            return null;
        }
        Collections.sort(periodList);
        loginForm.setPeriodList((List)periodList);
        final String timeTableQuery = "from TTClasses tc where tc.isActive=1 and tc.classSchemewise.classes.isActive=1 and tc.classSchemewise.id=" + loginForm.getClassId();
        final List<TTClasses> boList = (List<TTClasses>)transaction.getDataForQuery(timeTableQuery);
        final Map<String, Map<Integer, TimeTablePeriodTo>> ttMap = (Map<String, Map<Integer, TimeTablePeriodTo>>)StudentLoginHelper.getInstance().convertBoListToMap((List)boList, loginForm);
        Map<Integer, String> subjectMap = new HashMap<Integer, String>();
        if (loginForm.getClassId() != null && loginForm.getClassId().length() != 0) {
            final ClassSchemewise classSchemewise = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(Integer.parseInt(loginForm.getClassId()));
            if (classSchemewise.getCurriculumSchemeDuration().getAcademicYear() != null && classSchemewise.getClasses().getCourse().getId() != 0 && classSchemewise.getClasses().getTermNumber() != 0) {
                final int year = classSchemewise.getCurriculumSchemeDuration().getAcademicYear();
                final int courseId = classSchemewise.getClasses().getCourse().getId();
                final int term = classSchemewise.getClasses().getTermNumber();
                subjectMap = (Map<Integer, String>)CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYear(courseId, year, term);
            }
        }
        return (List<StudentLoginTO>)StudentLoginHelper.getInstance().getTimeTableForInputClass(loginForm, (Map)ttMap, (List)periodList);
    }
    
    public Boolean checkTimeTableClassHistory(final String classId) throws Exception {
        boolean timeTableClassHistory = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        timeTableClassHistory = transaction.checkTimeTableHistory(classId);
        return timeTableClassHistory;
    }
    
    public List<Object[]> getPaymentDetails(final int categoryId, final int courseId, final int termNo, final Integer academicYear) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final List<Object[]> list = (List<Object[]>)transaction.getPaymentDetails(categoryId, courseId, termNo, academicYear);
        return list;
    }
    
    public FeeCategory getFeeCat(final int feeCatId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final FeeCategory cat = transaction.getDetails(feeCatId);
        return cat;
    }
    
    public FeeHeading getHeadings(final int feeheading) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final FeeHeading heading = transaction.getHeadingStu(feeheading);
        return heading;
    }
    
    public Student getStudentById(final String studentid) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final Student student = transaction.getStudentDetails(studentid);
        return student;
    }
    
    public String getParameterForPGIOnline(final LoginForm loginForm, final Student student) throws Exception {
        final CandidatePGIDetails bo = new CandidatePGIDetails();
        bo.setCandidateName(student.getAdmAppln().getPersonalData().getFirstName());
        bo.setCandidateDob(student.getAdmAppln().getPersonalData().getDateOfBirth());
        if (loginForm.getCourseId() != null && !loginForm.getCourseId().isEmpty()) {
            final Course course = new Course();
            course.setId(Integer.parseInt(loginForm.getCourseId()));
            bo.setCourse(course);
        }
        bo.setTxnStatus("Pending");
        if (loginForm.getAmount() != null && !loginForm.getAmount().isEmpty()) {
            bo.setTxnAmount(new BigDecimal(loginForm.getAmount()));
        }
        bo.setMobileNo1(student.getAdmAppln().getPersonalData().getMobileNo1());
        bo.setMobileNo2(student.getAdmAppln().getPersonalData().getMobileNo2());
        bo.setEmail(student.getAdmAppln().getPersonalData().getEmail());
        final ResidentCategory rc = new ResidentCategory();
        rc.setId(student.getAdmAppln().getPersonalData().getResidentCategory().getId());
        bo.setRefundGenerated(Boolean.valueOf(false));
        bo.setResidentCategory(rc);
        bo.setCreatedBy(loginForm.getUserId());
        bo.setCreatedDate(new Date());
        bo.setLastModifiedDate(new Date());
        bo.setModifiedBy(loginForm.getUserId());
        final int admAplnId = student.getAdmAppln().getId();
        final AdmAppln appln = new AdmAppln();
        appln.setId(admAplnId);
        bo.setAdmAppln(appln);
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final String candidateRefNo = transaction.generateCandidateRefNoOnline(bo, loginForm);
        final StringBuilder temp = new StringBuilder();
        final String productinfo = "productinfo";
        loginForm.setRefNo(candidateRefNo);
        loginForm.setProductinfo(productinfo);
        if (candidateRefNo != null && !candidateRefNo.isEmpty()) {
            if (!loginForm.getCourseId().equalsIgnoreCase("21")) {
                temp.append("BMCSL").append("|").append("BMCS1469#").append("|").append("BMCSL" + candidateRefNo).append("|").append(Double.parseDouble(loginForm.getAmount()) * 100.0).append("|").append('A').append("|").append("B184M9847C987S6L");
            }
            else {
                temp.append("BMIML").append("|").append("BMIM1469#").append("|").append("BMIML" + candidateRefNo).append("|").append(Double.parseDouble(loginForm.getAmount()) * 100.0).append("|").append('A').append("|").append("B11M98I45M65826L");
            }
        }
        final String hash = this.sha1("SHA-1", temp.toString());
        loginForm.setTest(temp.toString());
        System.out.println("===============sending hash============" + hash);
        System.out.println("===============sending temp============" + (Object)temp);
        return hash;
    }
    
    public String sha1(final String type, final String str) {
        MessageDigest digest = null;
        final String myString = str;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(myString.getBytes("UTF-8"));
        }
        catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return new String(Base64.encodeBase64(digest.digest()));
    }
    
    public boolean updateResponse(final LoginForm loginForm, final Student student) throws Exception {
        boolean isUpdated = false;
        final CandidatePGIDetails bo = StudentLoginHelper.getInstance().convertToBo(loginForm);
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        isUpdated = transaction.updateReceivedStatusStudentLogin(bo, loginForm, student);
        return isUpdated;
    }
    
    public boolean savePaymentsDetails(final LoginForm loginForm, final HttpServletRequest request) throws Exception {
        boolean isSaved = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        isSaved = transaction.getFeePaymentDetailsOfStudent(loginForm, request);
        return isSaved;
    }
    
    public List<FeePayment> getList(final String studentid, final String classId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final List<FeePayment> feeList = (List<FeePayment>)transaction.getPaymentList(studentid, classId);
        return feeList;
    }
    
    public FeePayment getPaymentRecordForstudent(final String studentid, final String classId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final FeePayment paymentDetails = transaction.getDetailsOfstudent(studentid, classId);
        return paymentDetails;
    }
    
    public CandidatePGIDetails getPaymentStatus(final String studentName, final String status, final String mode) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final CandidatePGIDetails pgiDetails = transaction.getStatus(studentName, status, mode);
        return pgiDetails;
    }
    
    public void updateBillNo(final FeeFinancialYear feeFinancialYear) throws Exception {
        boolean isUpdated = false;
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final FeeBillNumber bo = transaction.getlBillNo(feeFinancialYear);
        int billNo = Integer.parseInt(bo.getCurrentBillNo());
        ++billNo;
        bo.setCurrentBillNo(String.valueOf(billNo));
        bo.setCreatedDate(new Date());
        bo.setLastModifiedDate(new Date());
        isUpdated = transaction.updateFeeBillNo(bo);
    }
    
    public boolean checkIsSemesterWise(final int courseId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final SemesterWiseCourseBO bo = transaction.isSemesterWise(courseId);
        boolean isSemesterWise = false;
        if (bo != null) {
            isSemesterWise = true;
        }
        return isSemesterWise;
    }
    
    public List<Object[]> getPaymentDetailsSemesterWise(final int categoryId, final int courseId, final int year, final int semNo, final Integer academicYear) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final List<Object[]> list = (List<Object[]>)transaction.getPaymentDetailsSemesterWise(categoryId, courseId, year, semNo, academicYear);
        return list;
    }
    
    public CandidatePGIDetails getTxnData(final String admAppln) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final CandidatePGIDetails details = transaction.getDataTxn(admAppln);
        return details;
    }
    
    public FeePayment getPaymentData(final int appnId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final FeePayment pFeePayment = transaction.getRecord(appnId);
        return pFeePayment;
    }
    
    public List<FeePaymentDetail> getPaymentDetailsForstudent(final String studentid, final String classId) throws Exception {
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        final List<FeePaymentDetail> detail = (List<FeePaymentDetail>)transaction.getDataForStudent(studentid, classId);
        return detail;
    }
}