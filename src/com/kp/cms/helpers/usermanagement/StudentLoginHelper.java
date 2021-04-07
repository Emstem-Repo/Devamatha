package com.kp.cms.helpers.usermanagement;

import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.to.admission.AdmApplnTO;
import java.text.DecimalFormat;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admin.DocTypeExams;
import org.apache.commons.lang.WordUtils;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.State;
import org.apache.commons.lang.StringUtils;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.bo.admin.EdnQualification;
import java.text.SimpleDateFormat;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import com.kp.cms.bo.admin.DisciplineAndAchivement;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.transactions.usermanagement.IStudentLoginTransaction;
import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.transactionsimpl.usermanagement.StudentLoginTransactionImpl;
import java.math.BigDecimal;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.handlers.usermanagement.StudentLoginHandler;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import java.util.Collections;
import com.kp.cms.transactions.admin.StudentLoginTO;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TTSubjectBatch;
import com.kp.cms.to.timetable.TTSubjectBatchTo;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.to.exam.GrievanceStatusTo;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.helpers.exam.NewSupplementaryImpApplicationHelper;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import java.util.Map;
import javax.servlet.http.HttpSession;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.fee.FeePaymentDetailFeeGroupTO;
import com.kp.cms.to.fee.PrintChalanTO;
import java.util.HashMap;
import java.util.HashSet;
import com.kp.cms.bo.admin.FeePaymentApplicantDetails;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.Iterator;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.utilities.CommonUtil;
import java.util.ArrayList;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.bo.admin.FeePayment;
import java.util.List;
import com.kp.cms.forms.usermanagement.LoginForm;
import java.util.Date;
import com.kp.cms.bo.admin.PersonalData;

public class StudentLoginHelper
{
    private static volatile StudentLoginHelper studentLoginHelper;
    private static final String OTHER = "Other";
    static int count;
    
    static {
        StudentLoginHelper.studentLoginHelper = null;
        StudentLoginHelper.count = 0;
    }
    
    public static StudentLoginHelper getInstance() {
        if (StudentLoginHelper.studentLoginHelper == null) {
            return StudentLoginHelper.studentLoginHelper = new StudentLoginHelper();
        }
        return StudentLoginHelper.studentLoginHelper;
    }
    
    public PersonalData copyFormToBO(final String mobileNo, final String userId, final PersonalData data) throws Exception {
        if (mobileNo != null && !mobileNo.isEmpty()) {
            data.setMobileNo2(mobileNo);
        }
        data.setLastModifiedDate(new Date());
        data.setModifiedBy(userId);
        return data;
    }
    
    public LoginForm copyBoToForm(final PersonalData personalData) throws Exception {
        final LoginForm loginForm = new LoginForm();
        if (personalData != null && personalData.getMobileNo2() != null && !personalData.getMobileNo2().isEmpty()) {
            loginForm.setMobileNo(personalData.getMobileNo2());
        }
        return loginForm;
    }
    
    public List<FeePaymentTO> convertFeePaymentBOtoTO(final List<FeePayment> feeList) throws Exception {
        List<FeePaymentTO> feeToList = null;
        if (feeList != null && !feeList.isEmpty()) {
            feeToList = new ArrayList<FeePaymentTO>();
            for (final FeePayment feePayment : feeList) {
                final FeePaymentTO to = new FeePaymentTO();
                to.setChallenPrintedDate((feePayment.getChallenPrintedDate() != null) ? CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()), "dd-MMM-yyyy", "dd/MM/yyyy") : "");
                to.setBillNo(feePayment.getBillNo());
                final Set<FeePaymentDetail> feeDetail = (Set<FeePaymentDetail>)feePayment.getFeePaymentDetails();
                for (final FeePaymentDetail feePaymentDetail : feeDetail) {
                    feePaymentDetail.getFeeFinancialYear();
                }
                feeToList.add(to);
            }
        }
        return feeToList;
    }
    
    public void copyFeeChallanPrintData(final FeePayment feePayment, final LoginForm loginForm, final HttpServletRequest request) throws Exception {
        if (feePayment != null) {
            if (feePayment.getStudent().getAdmAppln() != null) {
                loginForm.setApplnNo(String.valueOf(feePayment.getStudent().getAdmAppln().getApplnNo()));
            }
            if (feePayment.getStudent().getRegisterNo() != null && !feePayment.getStudent().getRegisterNo().isEmpty()) {
                loginForm.setApplnNo(String.valueOf(feePayment.getStudent().getRegisterNo()));
            }
            loginForm.setBillNo(feePayment.getBillNo());
            loginForm.setChallanPrintedDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
            boolean isFirstYear = false;
            if (feePayment.getStudent() != null) {
                final Student student = feePayment.getStudent();
                final StringBuffer studentName = new StringBuffer();
                if (student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
                    studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
                    studentName.append(" ");
                }
                if (student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
                    studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
                    studentName.append(" ");
                }
                if (student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getLastName() != null) {
                    studentName.append(student.getAdmAppln().getPersonalData().getLastName());
                    studentName.append(" ");
                }
                loginForm.setStudentName(studentName.toString());
                final Set<FeePaymentApplicantDetails> details = (Set<FeePaymentApplicantDetails>)feePayment.getFeePaymentApplicantDetailses();
                for (final FeePaymentApplicantDetails applicantDetails : details) {
                    if (applicantDetails.getSemesterNo() == 1) {
                        isFirstYear = true;
                        break;
                    }
                }
            }
            loginForm.setConcessionReferenceNo(feePayment.getConcessionVoucherNo());
            if (feePayment.getCurrency() != null && feePayment.getCurrency().getCurrencyCode() != null) {
                loginForm.setCurrencyCode(feePayment.getCurrency().getCurrencyCode());
            }
            if (feePayment.getStudent() != null && feePayment.getStudent().getAdmAppln() != null && feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId() != null && feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode() != null) {
                String className = "";
                if (isFirstYear) {
                    className = "I ";
                }
                className = String.valueOf(className) + feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode();
                loginForm.setClassName(className);
            }
            if (feePayment.getStudent().getClassSchemewise() != null && feePayment.getStudent().getClassSchemewise().getClasses() != null) {
                loginForm.setClassName(feePayment.getStudent().getClassSchemewise().getClasses().getName());
            }
            if (feePayment.getChallanCreatedTime() != null) {
                loginForm.setChalanCreatedTime(feePayment.getChallanCreatedTime().toString());
            }
            final HttpSession session = request.getSession(false);
            if (feePayment.getFeePaymentMode() != null && feePayment.getFeePaymentMode().getName() != null) {
                loginForm.setPaymentMode(feePayment.getFeePaymentMode().getName());
            }
            final Set<Integer> sems = new HashSet<Integer>();
            String schemeNo = "";
            final StringBuffer schemeBuf = new StringBuffer();
            if (feePayment.getFeePaymentApplicantDetailses().size() != 0) {
                for (final FeePaymentApplicantDetails applicantDetails2 : feePayment.getFeePaymentApplicantDetailses()) {
                    if (!sems.contains(applicantDetails2.getSemesterNo())) {
                        sems.add(applicantDetails2.getSemesterNo());
                        schemeBuf.append(applicantDetails2.getSemesterNo()).append('/');
                    }
                }
            }
            final int length = schemeBuf.length();
            schemeNo = schemeBuf.substring(0, length - 1);
            loginForm.setSemister(schemeNo);
            final Set<FeePaymentDetail> feePaymentList = (Set<FeePaymentDetail>)feePayment.getFeePaymentDetails();
            final Iterator<FeePaymentDetail> itr3 = feePaymentList.iterator();
            final Map<Integer, Double> accountWiseNonOptionalAmount = new HashMap<Integer, Double>();
            final Map<Integer, Double> accountWiseOptionalAmount = new HashMap<Integer, Double>();
            final Map<Integer, Double> accountWiseTotalAmount = new HashMap<Integer, Double>();
            final Map<Integer, Double> accountWiseConcessionAmount = new HashMap<Integer, Double>();
            final Map<Integer, String> allFeeAccountMap = new HashMap<Integer, String>();
            final List<PrintChalanTO> printChalanList = new ArrayList<PrintChalanTO>();
            int totalAmt = 0;
            int count = 1;
            final StringBuffer accwiseTotalString = new StringBuffer();
            Double grandTotal = 0.0;
            final List<Integer> currList = new ArrayList<Integer>();
            while (itr3.hasNext()) {
                totalAmt = 0;
                final FeePaymentDetail feePaymentDetail = itr3.next();
                allFeeAccountMap.put(feePaymentDetail.getFeeAccount().getId(), String.valueOf(feePaymentDetail.getFeeAccount().getName()) + "(" + feePaymentDetail.getFeeAccount().getCode() + ")");
                if (feePaymentDetail.getTotalNonAdditionalAmount() != null) {
                    accountWiseNonOptionalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalNonAdditionalAmount().doubleValue());
                }
                if (feePaymentDetail.getTotalAdditionalAmount() != null) {
                    accountWiseOptionalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalAdditionalAmount().doubleValue());
                }
                accountWiseTotalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalAmount().doubleValue());
                accountWiseConcessionAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getConcessionAmount().doubleValue());
                final PrintChalanTO printChalanTO = new PrintChalanTO();
                printChalanTO.setPrintAccountName(feePaymentDetail.getFeeAccount().getPrintAccountName());
                if (feePaymentDetail.getFeeAccount().getCode().equalsIgnoreCase("993")) {
                    printChalanTO.setAccId(feePaymentDetail.getFeeAccount().getId());
                }
                if (feePaymentDetail.getTotalNonAdditionalAmount() != null) {
                    printChalanTO.setNonAdditionalAmount(Integer.toString(feePaymentDetail.getTotalNonAdditionalAmount().intValue()));
                    totalAmt = feePaymentDetail.getTotalNonAdditionalAmount().intValue();
                }
                if (feePaymentDetail.getTotalAdditionalAmount() != null) {
                    printChalanTO.setAdditionalAMount(Integer.toString(feePaymentDetail.getTotalAdditionalAmount().intValue()));
                    totalAmt += feePaymentDetail.getTotalAdditionalAmount().intValue();
                }
                if (feePaymentDetail.getTotalAmount() != null) {
                    printChalanTO.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
                }
                if (feePaymentDetail.getFeeAccount() != null && feePaymentDetail.getFeeAccount().getBankInformation() != null) {
                    printChalanTO.setBankInfo(feePaymentDetail.getFeeAccount().getBankInformation());
                }
                feePaymentDetail.getCurrency();
                final List<FeePaymentDetailFeeGroupTO> additionalList = new ArrayList<FeePaymentDetailFeeGroupTO>();
                final FeePaymentDetailFeeGroupTO feeDetailFeeGroupTO = new FeePaymentDetailFeeGroupTO();
                additionalList.add(feeDetailFeeGroupTO);
            }
            final String verified = "";
            final int deducationAmt = 0;
            final int netAmt = totalAmt - deducationAmt;
            final String amtInWord = String.valueOf(CommonUtil.numberToWord(netAmt)) + " Only ";
            if (netAmt != 0) {
                if (!accwiseTotalString.toString().trim().isEmpty()) {
                    accwiseTotalString.append(" + ");
                }
                accwiseTotalString.append(Integer.toString(netAmt));
            }
            grandTotal += new Double(netAmt);
            List<String> descList = new ArrayList<String>();
            final List<String> bankInfoList = new ArrayList<String>();
            final char slashN = '\n';
            final char slashR = '\r';
            descList = new ArrayList<String>();
            ++count;
        }
    }
    
    public List<OnlinePaymentReciepts> getOnlineReciepts(final int studentId) throws Exception {
        final String query = "from OnlinePaymentReciepts o where o.isPaymentFailed=0 and o.student.id=" + studentId;
        final INewExamMarksEntryTransaction txn = (INewExamMarksEntryTransaction)NewExamMarksEntryTransactionImpl.getInstance();
        final List<OnlinePaymentReciepts> list = (List<OnlinePaymentReciepts>)txn.getDataForQuery(query);
        return list;
    }
    
    public List<OnlinePaymentRecieptsTo> convertOnlineRecieptToToList(final List<OnlinePaymentReciepts> list, final HttpServletRequest request) throws Exception {
        final List<OnlinePaymentRecieptsTo> toList = new ArrayList<OnlinePaymentRecieptsTo>();
        OnlinePaymentRecieptsTo to = null;
        int count = 1;
        final List<GroupTemplate> templateList = (List<GroupTemplate>)TemplateHandler.getInstance().getDuplicateCheckList(0, "Online payment Receipts");
        for (final OnlinePaymentReciepts bo : list) {
            to = new OnlinePaymentRecieptsTo();
            to.setId(bo.getId());
            to.setCount(count);
            to.setRecieptNo((int)bo.getRecieptNo());
            to.setTransactionDate(CommonUtil.formatSqlDate1(bo.getTransactionDate().toString()));
            to.setApplicationFor(bo.getApplicationFor());
            to.setMsg(NewSupplementaryImpApplicationHelper.getInstance().getPrintData(bo, (List)templateList, request));
            ++count;
            toList.add(to);
        }
        return toList;
    }
    
    public MarksCardTO convertInternalMarksBotoTo(final List<Object[]> internalMarksCardData) throws Exception {
        MarksCardTO to = null;
        final List<SubjectTO> subList = new ArrayList<SubjectTO>();
        final Iterator<Object[]> itr = internalMarksCardData.iterator();
        double overall_tot = 0.0;
        double overall_tot_max = 0.0;
        double attend_max = 0.0;
        double attend_obt = 0.0;
        while (itr.hasNext()) {
            final Object[] obj = itr.next();
            if (to == null) {
                int cid = 0;
                if (obj.length == 48 && obj[47].toString() != null) {
                    cid = Integer.parseInt(obj[47].toString());
                }
                to = new MarksCardTO();
                if (cid != 0) {
                    to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid, "Course", true, "name"));
                }
                if (obj[0] != null) {
                    to.setStudentId(Integer.parseInt(obj[0].toString()));
                }
                if (obj[1] != null) {
                    to.setRegNo(obj[1].toString());
                }
                if (obj[14] != null) {
                    to.setStudentName(obj[14].toString());
                }
                if (obj[17] != null) {
                    to.setSemNo(obj[17].toString());
                }
                if (obj[6] != null) {
                    to.setSubjectName(obj[6].toString());
                }
                if (obj[42] != null && obj[46] != null) {
                    to.setAttendancePercentage(obj[42].toString());
                }
                if (obj[31] != null) {
                    overall_tot = Double.parseDouble(obj[39].toString());
                    attend_obt = Double.parseDouble(obj[31].toString());
                    to.setAttendancePercentageCon(obj[31].toString());
                    overall_tot_max += Double.parseDouble(obj[43].toString());
                    if (obj[8].toString().equalsIgnoreCase("T")) {
                        attend_max = Double.parseDouble(obj[27].toString()) - overall_tot_max;
                    }
                    else if (obj[8].toString().equalsIgnoreCase("P")) {
                        attend_max = Double.parseDouble(obj[45].toString()) - overall_tot_max;
                    }
                }
                else if (obj[39] != null) {
                    overall_tot = Double.parseDouble(obj[39].toString());
                    to.setTotalMarksAwarded(obj[39].toString());
                    overall_tot_max += Double.parseDouble(obj[43].toString());
                }
                final SubjectTO subjectTo = new SubjectTO();
                subjectTo.setExamName(obj[4].toString());
                subjectTo.setCiaMarksAwarded(obj[28].toString());
                subjectTo.setCiaMaxMarks(obj[44].toString());
                subjectTo.setConvertedMark(obj[38].toString());
                to.setAttendancePercentageConMax(String.valueOf(attend_max));
                subjectTo.setConvertedMarkMax(obj[43].toString());
                if (obj[6] != null) {
                    subjectTo.setSubjectName(obj[6].toString());
                }
                subList.add(subjectTo);
            }
            else {
                final SubjectTO subjectTo2 = new SubjectTO();
                subjectTo2.setExamName(obj[4].toString());
                subjectTo2.setCiaMarksAwarded(obj[28].toString());
                subjectTo2.setCiaMaxMarks(obj[44].toString());
                if (obj[39] != null) {
                    overall_tot = Double.parseDouble(obj[39].toString());
                }
                subjectTo2.setConvertedMark(obj[38].toString());
                overall_tot_max += Double.parseDouble(obj[43].toString());
                if (obj[8].toString().equalsIgnoreCase("T")) {
                    attend_max = Double.parseDouble(obj[27].toString()) - overall_tot_max;
                }
                else if (obj[8].toString().equalsIgnoreCase("P")) {
                    attend_max = Double.parseDouble(obj[45].toString()) - overall_tot_max;
                }
                to.setAttendancePercentageConMax(String.valueOf(attend_max));
                subjectTo2.setConvertedMarkMax(obj[43].toString());
                if (obj[6] != null) {
                    subjectTo2.setSubjectName(obj[6].toString());
                }
                if (obj[42] != null && obj[46] != null) {
                    to.setAttendancePercentage(obj[42].toString());
                }
                subList.add(subjectTo2);
            }
        }
        to.setTotalMarksAwarded(String.valueOf(Math.round(overall_tot)));
        to.setTotalMaxmarks(String.valueOf(Math.round(overall_tot_max + attend_max)));
        to.setMainList((List)subList);
        return to;
    }
    
    public MarksCardTO convertInternalMarksBotoToGrievance(final List<Object[]> internalMarksCardData) throws Exception {
        MarksCardTO to = null;
        final List<SubjectTO> subList = new ArrayList<SubjectTO>();
        final Iterator<Object[]> itr = internalMarksCardData.iterator();
        double overall_tot = 0.0;
        double overall_tot_max = 0.0;
        double attend_max = 0.0;
        double attend_obt = 0.0;
        while (itr.hasNext()) {
            final Object[] obj = itr.next();
            if (to == null) {
                int cid = 0;
                if (obj.length == 48 && obj[47].toString() != null) {
                    cid = Integer.parseInt(obj[47].toString());
                }
                to = new MarksCardTO();
                if (cid != 0) {
                    to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid, "Course", true, "name"));
                }
                if (obj[0] != null) {
                    to.setStudentId(Integer.parseInt(obj[0].toString()));
                }
                if (obj[1] != null) {
                    to.setRegNo(obj[1].toString());
                }
                if (obj[14] != null) {
                    to.setStudentName(obj[14].toString());
                }
                if (obj[17] != null) {
                    to.setSemNo(obj[17].toString());
                }
                if (obj[6] != null) {
                    to.setSubjectName(obj[6].toString());
                }
                if (obj[42] != null && obj[46] != null) {
                    to.setAttendancePercentage(obj[42].toString());
                }
                if (obj[31] != null) {
                    if (obj[39] != null) {
                        overall_tot = Double.parseDouble(obj[39].toString());
                    }
                    attend_obt = Double.parseDouble(obj[31].toString());
                    to.setAttendancePercentageCon(obj[31].toString());
                    overall_tot_max += Double.parseDouble(obj[43].toString());
                    if (obj[8].toString().equalsIgnoreCase("T")) {
                        attend_max = Double.parseDouble(obj[27].toString()) - overall_tot_max;
                    }
                    else if (obj[8].toString().equalsIgnoreCase("P")) {
                        attend_max = Double.parseDouble(obj[45].toString()) - overall_tot_max;
                    }
                }
                else if (obj[39] != null) {
                    overall_tot = Double.parseDouble(obj[39].toString());
                    to.setTotalMarksAwarded(obj[39].toString());
                    overall_tot_max += Double.parseDouble(obj[43].toString());
                }
                final SubjectTO subjectTo = new SubjectTO();
                subjectTo.setExamName(obj[4].toString());
                subjectTo.setCiaMarksAwarded(obj[28].toString());
                subjectTo.setCiaMaxMarks(obj[44].toString());
                subjectTo.setConvertedMark(obj[38].toString());
                to.setAttendancePercentageConMax(String.valueOf(attend_max));
                subjectTo.setConvertedMarkMax(obj[43].toString());
                subjectTo.setId(Integer.parseInt(obj[5].toString()));
                if (obj[6] != null) {
                    subjectTo.setSubjectName(obj[6].toString());
                }
                subList.add(subjectTo);
            }
            else {
                final SubjectTO subjectTo2 = new SubjectTO();
                subjectTo2.setExamName(obj[4].toString());
                subjectTo2.setCiaMarksAwarded(obj[28].toString());
                subjectTo2.setCiaMaxMarks(obj[44].toString());
                overall_tot = Double.parseDouble(obj[39].toString());
                subjectTo2.setConvertedMark(obj[38].toString());
                overall_tot_max += Double.parseDouble(obj[43].toString());
                if (obj[8].toString().equalsIgnoreCase("T")) {
                    attend_max = Double.parseDouble(obj[27].toString()) - overall_tot_max;
                }
                else if (obj[8].toString().equalsIgnoreCase("P")) {
                    attend_max = Double.parseDouble(obj[45].toString()) - overall_tot_max;
                }
                to.setAttendancePercentageConMax(String.valueOf(attend_max));
                subjectTo2.setConvertedMarkMax(obj[43].toString());
                subjectTo2.setId(Integer.parseInt(obj[5].toString()));
                if (obj[6] != null) {
                    subjectTo2.setSubjectName(obj[6].toString());
                }
                if (obj[42] != null && obj[46] != null) {
                    to.setAttendancePercentage(obj[42].toString());
                }
                subList.add(subjectTo2);
            }
        }
        to.setTotalMarksAwarded(String.valueOf(Math.round(overall_tot)));
        to.setTotalMaxmarks(String.valueOf(Math.round(overall_tot_max + attend_max)));
        to.setMainList((List)subList);
        return to;
    }
    
    public List<GrievanceStatusTo> convertBotoTo(final List<GrievanceRemarkBo> grievanceNumberList) throws Exception {
        final List<GrievanceStatusTo> grievanceStatusToList = new ArrayList<GrievanceStatusTo>();
        if (grievanceNumberList != null) {
            for (final GrievanceRemarkBo bo : grievanceNumberList) {
                final GrievanceStatusTo to = new GrievanceStatusTo();
                to.setGrievanceSerialNo(bo.getGrievanceSerialNo());
                to.setCourseTitle(bo.getSubject().getName());
                to.setCourseCode(bo.getSubject().getCode());
                if (bo.getIsHodApproved()) {
                    to.setHodStatus("Approved");
                }
                if (bo.getIsHodRejected()) {
                    to.setHodStatus("Rejected");
                }
                if (bo.getIsHodPending()) {
                    to.setHodStatus("Pending");
                }
                if (bo.getIsControlerApproved()) {
                    to.setControlerStatus("Approved");
                }
                if (bo.getIsControlerRejected()) {
                    to.setControlerStatus("Rejected");
                }
                if (bo.getIsControlerPending()) {
                    to.setControlerStatus("Pending");
                }
                if (bo.getHodRemark() != null) {
                    to.setHodRemark(bo.getHodRemark());
                }
                else {
                    to.setHodRemark("-");
                }
                if (bo.getControlerRemark() != null) {
                    to.setControlerRemark(bo.getControlerRemark());
                }
                else {
                    to.setControlerRemark("-");
                }
                grievanceStatusToList.add(to);
            }
        }
        return grievanceStatusToList;
    }
    
    public Map<String, Map<Integer, TimeTablePeriodTo>> convertBoListToMap(final List<TTClasses> boList, final LoginForm loginForm) throws Exception {
        final Map<String, Map<Integer, TimeTablePeriodTo>> map = new HashMap<String, Map<Integer, TimeTablePeriodTo>>();
        Map<Integer, TimeTablePeriodTo> periodMap = null;
        TimeTablePeriodTo to = null;
        List<TTSubjectBatchTo> subjectList = null;
        TTSubjectBatchTo suBatchTo = null;
        String periodName = "";
        if (boList != null && !boList.isEmpty()) {
            for (final TTClasses bo : boList) {
                loginForm.setTtClassId(bo.getId());
                if (bo.getIsApproved() != null && bo.getIsApproved()) {
                    loginForm.setFinalApprove("on");
                }
                else {
                    loginForm.setFinalApprove("off");
                }
                final Set<TTPeriodWeek> periodSet = (Set<TTPeriodWeek>)bo.getTtPeriodWeeks();
                if (periodSet != null && !periodSet.isEmpty()) {
                    for (final TTPeriodWeek periodBo : periodSet) {
                        if (map.containsKey(periodBo.getWeekDay())) {
                            periodMap = map.remove(periodBo.getWeekDay());
                        }
                        else {
                            periodMap = new HashMap<Integer, TimeTablePeriodTo>();
                        }
                        if (periodMap.containsKey(periodBo.getPeriod().getId())) {
                            to = periodMap.remove(periodBo.getPeriod().getId());
                        }
                        else {
                            to = new TimeTablePeriodTo();
                            to.setId(periodBo.getId());
                        }
                        if (to.getSubjectList() != null) {
                            subjectList = (List<TTSubjectBatchTo>)to.getSubjectList();
                        }
                        else {
                            subjectList = new ArrayList<TTSubjectBatchTo>();
                        }
                        final StringBuffer subjectNames = new StringBuffer();
                        final Set<TTSubjectBatch> subjectSet = (Set<TTSubjectBatch>)periodBo.getTtSubjectBatchs();
                        if (subjectSet != null && !subjectSet.isEmpty()) {
                            final Iterator<TTSubjectBatch> subjectItr = subjectSet.iterator();
                            int deleteCount = 1;
                            while (subjectItr.hasNext()) {
                                final TTSubjectBatch subjectBatchBo = subjectItr.next();
                                if (subjectBatchBo.getIsActive()) {
                                    if (subjectNames.length() != 0) {
                                        subjectNames.append(",");
                                    }
                                    if (subjectBatchBo.getSubject() != null) {
                                        subjectNames.append(subjectBatchBo.getSubject().getName());
                                        subjectNames.append(" (" + subjectBatchBo.getSubject().getCode() + ")");
                                    }
                                    else if (subjectBatchBo.getActivity() != null) {
                                        subjectNames.append(subjectBatchBo.getActivity().getName());
                                    }
                                }
                                else if (subjectNames.length() == 0) {
                                    periodName = periodBo.getPeriod().getPeriodName();
                                }
                                suBatchTo = new TTSubjectBatchTo();
                                if (subjectBatchBo.getIsActive()) {
                                    suBatchTo.setId(subjectBatchBo.getId());
                                    suBatchTo.setDeleteCount(deleteCount);
                                    suBatchTo.setIsActive(subjectBatchBo.getIsActive());
                                    suBatchTo.setOrigIsActive(subjectBatchBo.getIsActive());
                                    ++deleteCount;
                                    if (subjectBatchBo.getAttendanceType() != null) {
                                        suBatchTo.setAttendanceType(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
                                        suBatchTo.setAttendanceTypeId(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
                                        suBatchTo.setOrigAttendanceTypeId(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
                                        suBatchTo.setOrigAttendanceType(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
                                        suBatchTo.setAttTypeName(subjectBatchBo.getAttendanceType().getName());
                                    }
                                    if (subjectBatchBo.getSubject() != null) {
                                        suBatchTo.setSubjectId(String.valueOf(subjectBatchBo.getSubject().getId()));
                                        suBatchTo.setOrigSubjectId(String.valueOf(subjectBatchBo.getSubject().getId()));
                                        suBatchTo.setSubjectName(subjectBatchBo.getSubject().getCode());
                                    }
                                    if (subjectBatchBo.getActivity() != null) {
                                        suBatchTo.setActivityId(String.valueOf(subjectBatchBo.getActivity().getId()));
                                        suBatchTo.setOrigActivityId(String.valueOf(subjectBatchBo.getActivity().getId()));
                                        suBatchTo.setActivityName(subjectBatchBo.getActivity().getName());
                                    }
                                    if (subjectBatchBo.getBatch() != null) {
                                        suBatchTo.setBatchId(String.valueOf(subjectBatchBo.getBatch().getId()));
                                        suBatchTo.setOrigBatchId(String.valueOf(subjectBatchBo.getBatch().getId()));
                                        suBatchTo.setBatchName(subjectBatchBo.getBatch().getBatchName());
                                    }
                                    suBatchTo.setRoomNo(subjectBatchBo.getRoomNo());
                                    suBatchTo.setOrigRoomNo(subjectBatchBo.getRoomNo());
                                    suBatchTo.setIsActive(subjectBatchBo.getIsActive());
                                    final Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();
                                    final Set<TTUsers> userSet = (Set<TTUsers>)subjectBatchBo.getTtUsers();
                                    String userName = "";
                                    if (userSet != null && !userSet.isEmpty()) {
                                        final Iterator<TTUsers> userItr = userSet.iterator();
                                        final List<String> userList = new ArrayList<String>();
                                        while (userItr.hasNext()) {
                                            final TTUsers ttUsers = userItr.next();
                                            userMap.put(ttUsers.getUsers().getId(), ttUsers.getId());
                                            if (userName.isEmpty()) {
                                                userName = ((ttUsers.getUsers().getEmployee() != null) ? ttUsers.getUsers().getEmployee().getFirstName() : ((ttUsers.getUsers().getGuest() != null) ? ttUsers.getUsers().getGuest().getFirstName() : ""));
                                            }
                                            else {
                                                userName = String.valueOf(userName) + "," + ((ttUsers.getUsers().getEmployee() != null) ? ttUsers.getUsers().getEmployee().getFirstName() : ((ttUsers.getUsers().getGuest() != null) ? ttUsers.getUsers().getGuest().getFirstName() : ""));
                                            }
                                            userList.add(String.valueOf(ttUsers.getUsers().getId()));
                                        }
                                        final String[] userids = new String[userList.size()];
                                        final Iterator<String> periodSetIterator = userList.iterator();
                                        int count = 0;
                                        while (periodSetIterator.hasNext()) {
                                            userids[count++] = periodSetIterator.next();
                                        }
                                        suBatchTo.setUserId(userids);
                                        suBatchTo.setOrigUserId(userids);
                                        suBatchTo.setUserMap((Map)userMap);
                                        suBatchTo.setUserName(userName);
                                    }
                                    else {
                                        final String[] userids2 = { loginForm.getUserId() };
                                        suBatchTo.setUserId(userids2);
                                        suBatchTo.setOrigUserId(userids2);
                                        suBatchTo.setUserName(userName);
                                    }
                                    subjectList.add(suBatchTo);
                                }
                            }
                        }
                        to.setSubjectList((List)subjectList);
                        if (subjectNames.toString() != null && !subjectNames.toString().isEmpty()) {
                            to.setPeriodName(subjectNames.toString());
                        }
                        else {
                            to.setPeriodName(periodName);
                        }
                        to.setStartTime(periodBo.getPeriod().getStartTime());
                        to.setPeriodId(periodBo.getPeriod().getId());
                        periodMap.put(periodBo.getPeriod().getId(), to);
                        map.put(periodBo.getWeekDay(), periodMap);
                    }
                }
            }
        }
        return map;
    }
    
    public List<StudentLoginTO> getTimeTableForInputClass(final LoginForm loginForm, final Map<String, Map<Integer, TimeTablePeriodTo>> ttMap, final List<Period> periodList) {
        final List<StudentLoginTO> classTos = new ArrayList<StudentLoginTO>();
        StudentLoginTO classTo = new StudentLoginTO();
        classTo.setPosition(1);
        classTo.setWeek("Monday");
        if (ttMap.get(classTo.getWeek()) != null) {
            this.setPeriodListToClassTo(ttMap.get(classTo.getWeek()), classTo, periodList);
            classTos.add(classTo);
        }
        classTo = new StudentLoginTO();
        classTo.setPosition(2);
        classTo.setWeek("Tuesday");
        if (ttMap.get(classTo.getWeek()) != null) {
            this.setPeriodListToClassTo(ttMap.get(classTo.getWeek()), classTo, periodList);
            classTos.add(classTo);
        }
        classTo = new StudentLoginTO();
        classTo.setPosition(3);
        classTo.setWeek("Wednesday");
        if (ttMap.get(classTo.getWeek()) != null) {
            this.setPeriodListToClassTo(ttMap.get(classTo.getWeek()), classTo, periodList);
            classTos.add(classTo);
        }
        classTo = new StudentLoginTO();
        classTo.setPosition(4);
        classTo.setWeek("Thursday");
        if (ttMap.get(classTo.getWeek()) != null) {
            this.setPeriodListToClassTo(ttMap.get(classTo.getWeek()), classTo, periodList);
            classTos.add(classTo);
        }
        classTo = new StudentLoginTO();
        classTo.setPosition(5);
        classTo.setWeek("Friday");
        if (ttMap.get(classTo.getWeek()) != null) {
            this.setPeriodListToClassTo(ttMap.get(classTo.getWeek()), classTo, periodList);
            classTos.add(classTo);
        }
        classTo = new StudentLoginTO();
        classTo.setPosition(6);
        classTo.setWeek("Saturday");
        if (ttMap.get(classTo.getWeek()) != null) {
            this.setPeriodListToClassTo(ttMap.get(classTo.getWeek()), classTo, periodList);
            classTos.add(classTo);
        }
        return classTos;
    }
    
    private void setPeriodListToClassTo(final Map<Integer, TimeTablePeriodTo> map, final StudentLoginTO classTo, final List<Period> periodList) {
        if (periodList != null && !periodList.isEmpty()) {
            final Iterator<Period> itr = periodList.iterator();
            TimeTablePeriodTo to = null;
            int periodChecking = 0;
            final List<TimeTablePeriodTo> periodToList = new ArrayList<TimeTablePeriodTo>();
            while (itr.hasNext()) {
                final Period period = itr.next();
                ++StudentLoginHelper.count;
                if (map != null && map.containsKey(period.getId())) {
                    to = map.get(period.getId());
                }
                else {
                    ++periodChecking;
                    to = new TimeTablePeriodTo();
                    to.setPeriodId(period.getId());
                    to.setPeriodName(period.getPeriodName());
                    to.setStartTime(period.getStartTime());
                }
                if (classTo.getWeek() == "Monday") {
                    classTo.setShowWeek("Day1");
                }
                else if (classTo.getWeek() == "Tuesday") {
                    classTo.setShowWeek("Day2");
                }
                else if (classTo.getWeek() == "Wednesday") {
                    classTo.setShowWeek("Day3");
                }
                else if (classTo.getWeek() == "Thursday") {
                    classTo.setShowWeek("Day4");
                }
                else if (classTo.getWeek() == "Friday") {
                    classTo.setShowWeek("Day5");
                }
                else if (classTo.getWeek() == "Saturday") {
                    classTo.setShowWeek("Day6");
                }
                to.setCount(StudentLoginHelper.count);
                periodToList.add(to);
            }
            Collections.sort(periodToList);
            classTo.setTimeTablePeriodTos((List)periodToList);
        }
    }
    
    public CandidatePGIDetails convertToBo(final LoginForm loginForm) throws Exception {
        final CandidatePGIDetails bo = new CandidatePGIDetails();
        final StringBuilder temp = new StringBuilder();
        if (!loginForm.getCourseId().equalsIgnoreCase("21")) {
            temp.append("BMCSL").append("|").append(loginForm.getTxnid()).append("|").append(loginForm.getAmount()).append("|").append(loginForm.getStatus()).append("|").append("B184M9847C987S6L");
        }
        else {
            temp.append("BMIML").append("|").append(loginForm.getTxnid()).append("|").append(loginForm.getAmount()).append("|").append(loginForm.getStatus()).append("|").append("B11M98I45M65826L");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++  this is data before hash alogoritham ++++++++++++++++++++++++++++++" + temp.toString());
        final String hash = StudentLoginHandler.getInstance().sha1("SHA-1", temp.toString());
        System.out.println("+++++++++++++++++++++++++++++++++++  this is data of after  hash  generation ++++++++++++++++++++++++++++++" + hash);
        System.out.println("+++++++++++++++++++++++++++++++++++  this is data of pay u hash ++++++++++++++++++++++++++++++" + loginForm.getHash());
        if (loginForm.getHash() != null && hash != null && !loginForm.getHash().equals(hash)) {
            throw new BusinessException();
        }
        bo.setTxnRefNo(loginForm.getTxnid());
        bo.setTxnAmount(new BigDecimal(Integer.parseInt(loginForm.getAmount()) / 100));
        bo.setMode("Online");
        if (loginForm.getStatus().equalsIgnoreCase("S")) {
            bo.setTxnStatus("Success");
        }
        else {
            bo.setTxnStatus("Pending");
        }
        bo.setTxnDate((Date)CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy")));
        loginForm.setTxnRefNo(loginForm.getTxnid());
        loginForm.setPgiStatus("Payment Successful");
        loginForm.setTxnAmt(loginForm.getAmount());
        if (loginForm.getStatus().equalsIgnoreCase("S")) {
            loginForm.setIsTnxStatusSuccess(Boolean.valueOf(true));
        }
        else {
            loginForm.setIsTnxStatusSuccess(Boolean.valueOf(false));
        }
        loginForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy"));
        return bo;
    }
    
    public boolean convertTOtoBo(final LoginForm loginForm, final HttpServletRequest request) throws Exception {
        final HttpSession session = request.getSession();
        final List<Object[]> list = (List<Object[]>)session.getAttribute("paymentList");
        final IStudentLoginTransaction transaction = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        boolean isadded = false;
        double fine = 0.0;
        final boolean isPartialPayment = false;
        for (final Object[] obj : list) {
            final FeePayment feePayment = new FeePayment();
            final Student stu = new Student();
            final String studentid = (String)session.getAttribute("studentid");
            final Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
            feePayment.setApplicationNo(String.valueOf(student.getAdmAppln().getApplnNo()));
            feePayment.setRegistrationNo(student.getRegisterNo());
            feePayment.setRollNo(student.getRollNo());
            feePayment.setTotalAmount(new BigDecimal(loginForm.getAmount()));
            feePayment.setIsChallenPrinted(Boolean.valueOf(true));
            final Classes classes = new Classes();
            classes.setId(Integer.parseInt(loginForm.getClassId()));
            feePayment.setIsFeePaid(Boolean.valueOf(true));
            if (feePayment.getTotalAmount().equals(feePayment.getTotalFeePaid())) {
                Date paidDate = new Date();
                if (loginForm.getTxnDate() != null) {
                    paidDate = CommonUtil.ConvertStringToSQLDate(loginForm.getTxnDate());
                }
                feePayment.setIsCompletlyPaid(Boolean.valueOf(true));
                feePayment.setIsFeePaid(Boolean.valueOf(true));
                feePayment.setFeePaidDate(paidDate);
            }
            fine = 0.0;
            final double paidAmount = 0.0;
            final Course course = new Course();
            if (loginForm.getCourseId() != null) {
                course.setId(Integer.parseInt(loginForm.getCourseId()));
                feePayment.setCourse(course);
            }
            feePayment.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
            if (student.getId() != 0) {
                final Student student2 = new Student();
                student2.setId(student.getId());
                feePayment.setStudent(student);
            }
            feePayment.setIsCancelChallan(Boolean.valueOf(false));
            feePayment.setChallanCreatedTime(new Date());
            final boolean isBillNoSave = true;
            final Set<FeePaymentDetail> feePayDetailSet = new HashSet<FeePaymentDetail>();
            final double paidAmt = 0.0;
            final double diffAccAmount = 0.0;
            final boolean isPaid = false;
            final FeePaymentDetail feePaymentDetail = new FeePaymentDetail();
            final FeeHeading feeHeading = new FeeHeading();
            final FeeAccount feeAccount = new FeeAccount();
            final double feepaid = Double.parseDouble(loginForm.getAmount()) / 100.0;
            feePaymentDetail.setAmountPaid(new BigDecimal(feepaid));
            feeHeading.setId(Integer.parseInt(obj[2].toString()));
            feePaymentDetail.setFeeHeading(feeHeading);
            if (loginForm.getTermNo() != 0) {
                feePaymentDetail.setSemesterNo(Integer.valueOf(loginForm.getTermNo()));
            }
            feePayment.setFeePaymentDetails((Set)feePayDetailSet);
            feePayment.setCreatedBy(loginForm.getUserId());
            feePayment.setCreatedDate(new Date());
            isadded = transaction.addFeePaymentDetail(feePayment);
        }
        return isadded;
    }
    
    public FeePayment getFeePaymentDetailsOfStudent(final LoginForm loginForm, final HttpServletRequest request) throws Exception {
        final FeePayment feePayment = new FeePayment();
        final HttpSession session = request.getSession();
        final String studentid = (String)session.getAttribute("studentid");
        final Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
        final Student student2 = new Student();
        student2.setId(loginForm.getStudentId());
        feePayment.setStudent(student2);
        final FeeFinancialYear feeFinancialYear = new FeeFinancialYear();
        feeFinancialYear.setId((int)FeePaymentHandler.getInstance().getFinancialYearId());
        feePayment.setAmountFinancialYear(feeFinancialYear);
        final Course course = new Course();
        course.setId(Integer.parseInt(loginForm.getCourseId()));
        feePayment.setCourse(course);
        final Classes classes = new Classes();
        classes.setId(Integer.parseInt(loginForm.getClassId()));
        feePayment.setClasses(classes);
        feePayment.setApplicationNo(String.valueOf(student.getAdmAppln().getApplnNo()));
        feePayment.setRegistrationNo(loginForm.getRegNo());
        feePayment.setRollNo(student.getRollNo());
        feePayment.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
        feePayment.setChallanCreatedTime(new Date());
        feePayment.setIsCancelChallan(Boolean.valueOf(false));
        feePayment.setIsFeePaid(Boolean.valueOf(true));
        feePayment.setFeePaidDate(new Date());
        feePayment.setCreatedBy(loginForm.getUserId());
        feePayment.setCreatedDate(new Date());
        final Set<FeePaymentDetail> feePaymentDetails = new HashSet<FeePaymentDetail>();
        final List<Object[]> list = (List<Object[]>)session.getAttribute("paymentList");
        final Iterator<Object[]> keyValItr = list.iterator();
        double totalPaid = 0.0;
        final double totalFine = 0.0;
        final double totalAmount = 0.0;
        double balanceAmount = 0.0;
        while (keyValItr.hasNext()) {
            final Object[] obj = keyValItr.next();
            final FeePaymentDetail feePaymentDetail = new FeePaymentDetail();
            feePaymentDetail.setSemesterNo(Integer.valueOf(loginForm.getTermNo()));
            feePaymentDetail.setPaidDate((Date)CommonUtil.ConvertStringToSQLDate(loginForm.getTxnDate()));
            final FeeHeading feeHeading = new FeeHeading();
            feeHeading.setId(Integer.parseInt(obj[2].toString()));
            feePaymentDetail.setFeeHeading(feeHeading);
            final double feepaid = Double.parseDouble(loginForm.getAmount()) / 100.0;
            feePaymentDetail.setAmountPaid(new BigDecimal(feepaid));
            final String assignedAmt = (String)session.getAttribute("assignedAmount");
            if (balanceAmount == 0.0) {
                balanceAmount = Double.parseDouble(assignedAmt) - feepaid;
            }
            else {
                balanceAmount -= feepaid;
            }
            feePaymentDetail.setFeeFinancialYear(feeFinancialYear);
            feePaymentDetail.setAmountBalance(new BigDecimal(balanceAmount));
            feePaymentDetail.setTotalAmount(new BigDecimal(assignedAmt));
            feePaymentDetail.setFeePayment(feePayment);
            feePaymentDetails.add(feePaymentDetail);
            totalPaid += feepaid;
            feePayment.setTotalAmount(new BigDecimal(assignedAmt));
            feePayment.setTotalFeePaid(new BigDecimal(feepaid));
            feePayment.setTotalBalanceAmount(new BigDecimal(Double.parseDouble(assignedAmt) - totalPaid));
            feePayment.setFeePaymentDetails((Set)feePaymentDetails);
        }
        return feePayment;
    }
    
    public List<DisciplineAndAchivementTo> convertDispBoTo(final List<DisciplineAndAchivement> boList) {
        final List<DisciplineAndAchivementTo> toList = new ArrayList<DisciplineAndAchivementTo>();
        for (final DisciplineAndAchivement bo : boList) {
            final Date date = bo.getDate();
            final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            final String strDate = formatter.format(date);
            final DisciplineAndAchivementTo to = new DisciplineAndAchivementTo();
            to.setId(bo.getId());
            to.setDescryption(bo.getDescryption());
            to.setDate(strDate);
            if (bo.getFileName() != null) {
                to.setFileName(bo.getFileName());
            }
            toList.add(to);
        }
        return toList;
    }
    
    public EdnQualificationTO convertEdnBoToTo(final EdnQualification bo) {
        if (bo != null) {
            final EdnQualificationTO to = new EdnQualificationTO();
            to.setInstitutionName(bo.getInstitutionNameOthers());
        }
        return null;
    }
    
    public Set<EdnQualification> createMarksBo(final AdmAppln appBO, final LoginForm loginForm) throws Exception {
        Set<EdnQualification> qualificationSet = null;
        final AdmApplnTO applicantDetail = loginForm.getApplicantDetails();
        qualificationSet = new HashSet<EdnQualification>();
        final EdnQualificationTO qualificationTO = loginForm.getEdnQualification();
        final EdnQualification bo = new EdnQualification();
        if (qualificationTO.getId() != 0) {
            bo.setId(qualificationTO.getId());
        }
        bo.setCreatedBy(qualificationTO.getCreatedBy());
        bo.setCreatedDate(qualificationTO.getCreatedDate());
        bo.setModifiedBy(appBO.getModifiedBy());
        bo.setLastModifiedDate(new Date());
        if (qualificationTO.getStateId() != null && !StringUtils.isEmpty(qualificationTO.getStateId()) && StringUtils.isNumeric(qualificationTO.getStateId())) {
            final State st = new State();
            st.setId(Integer.parseInt(qualificationTO.getStateId()));
            bo.setState(st);
        }
        else if (qualificationTO.getStateId() != null && !StringUtils.isEmpty(qualificationTO.getStateId()) && qualificationTO.getStateId().equalsIgnoreCase("OUTSIDEINDIA")) {
            bo.setState((State)null);
            bo.setIsOutsideIndia(Boolean.valueOf(true));
        }
        if (qualificationTO.getInstitutionId() != null && !StringUtils.isEmpty(qualificationTO.getInstitutionId()) && StringUtils.isNumeric(qualificationTO.getInstitutionId())) {
            if (Integer.parseInt(qualificationTO.getInstitutionId()) != 0) {
                final College col = new College();
                col.setId(Integer.parseInt(qualificationTO.getInstitutionId()));
                bo.setCollege(col);
            }
            else {
                bo.setCollege((College)null);
            }
        }
        else if (qualificationTO.getOtherInstitute() != null) {
            bo.setInstitutionNameOthers(WordUtils.capitalize(qualificationTO.getOtherInstitute().toLowerCase()));
        }
        if (qualificationTO.getSelectedExamId() != null && !StringUtils.isEmpty(qualificationTO.getSelectedExamId()) && StringUtils.isNumeric(qualificationTO.getSelectedExamId())) {
            final DocTypeExams exmBo = new DocTypeExams();
            exmBo.setId(Integer.parseInt(qualificationTO.getSelectedExamId()));
            bo.setDocTypeExams(exmBo);
        }
        if (qualificationTO.getDocName() == "Class 12" || qualificationTO.getDocName().equalsIgnoreCase("Class 12")) {
            if (qualificationTO.getMarksObtained() != null && !StringUtils.isEmpty(qualificationTO.getMarksObtained()) && CommonUtil.isValidDecimal(qualificationTO.getMarksObtained())) {
                bo.setMarksObtained(new BigDecimal(qualificationTO.getMarksObtained()));
            }
            if (qualificationTO.getTotalMarks() != null && !StringUtils.isEmpty(qualificationTO.getTotalMarks()) && CommonUtil.isValidDecimal(qualificationTO.getTotalMarks())) {
                bo.setTotalMarks(new BigDecimal(qualificationTO.getTotalMarks()));
            }
        }
        if (qualificationTO.getPercentage() != null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())) {
            bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
        }
        else {
            bo.setPercentage(new BigDecimal(0));
        }
        if (qualificationTO.getUniversityId() != null && !StringUtils.isEmpty(qualificationTO.getUniversityId()) && StringUtils.isNumeric(qualificationTO.getUniversityId())) {
            if (Integer.parseInt(qualificationTO.getUniversityId()) != 0) {
                final University un = new University();
                un.setId(Integer.parseInt(qualificationTO.getUniversityId()));
                bo.setUniversity(un);
            }
            else {
                bo.setUniversity((University)null);
            }
        }
        else if (qualificationTO.getUniversityOthers() != null) {
            bo.setUniversityOthers(WordUtils.capitalize(qualificationTO.getUniversityOthers().toLowerCase()));
        }
        bo.setNoOfAttempts(Integer.valueOf(qualificationTO.getNoOfAttempts()));
        bo.setYearPassing(Integer.valueOf(qualificationTO.getYearPassing()));
        bo.setMonthPassing(Integer.valueOf(qualificationTO.getMonthPassing()));
        bo.setPreviousRegNo(qualificationTO.getPreviousRegNo());
        if (qualificationTO.getDocCheckListId() != 0) {
            final DocChecklist docList = new DocChecklist();
            docList.setId(qualificationTO.getDocCheckListId());
            bo.setDocChecklist(docList);
        }
        else {
            bo.setDocChecklist((DocChecklist)null);
        }
        final Set<CandidateMarks> detailMarks = new HashSet<CandidateMarks>();
        if (!qualificationTO.isConsolidated() && !qualificationTO.isSemesterWise()) {
            final CandidateMarks detailMark = new CandidateMarks();
            final CandidateMarkTO detailMarkto = qualificationTO.getDetailmark();
            if (detailMarkto != null) {
                if (detailMarkto.getId() != 0) {
                    detailMark.setId(detailMarkto.getId());
                }
                detailMark.setCreatedBy(detailMarkto.getCreatedBy());
                detailMark.setCreatedDate(detailMarkto.getCreatedDate());
                detailMark.setModifiedBy(appBO.getModifiedBy());
                detailMark.setLastModifiedDate(new Date());
                double totalmark = 0.0;
                double totalobtained = 0.0;
                double percentage = 0.0;
                final int doctypeId = CMSConstants.CLASS12_DOCTYPEID;
                if (qualificationTO.getDocTypeId() == doctypeId) {
                    if (loginForm.getAdmSubjectLangMap() != null) {
                        this.setDetailSubjectMarkBOClass12(bo, qualificationTO, detailMark, detailMarkto, loginForm, qualificationTO.getDocTypeId());
                    }
                    if (detailMarkto.getTotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalMarks())) {
                        detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarks()));
                        totalmark = detailMark.getTotalMarks().doubleValue();
                    }
                    if (detailMarkto.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarks())) {
                        detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarks()));
                        totalobtained = detailMark.getTotalObtainedMarks().doubleValue();
                    }
                    if (totalmark != 0.0 && totalobtained != 0.0) {
                        percentage = totalobtained / totalmark * 100.0;
                    }
                    final DecimalFormat df = new DecimalFormat("#.##");
                    bo.setTotalMarks(new BigDecimal(new Double(df.format(totalmark)).toString()));
                    bo.setMarksObtained(new BigDecimal(new Double(df.format(totalobtained)).toString()));
                    bo.setPercentage(new BigDecimal(new Double(df.format(percentage)).toString()));
                }
                final int doctypeId2 = CMSConstants.DEGREE_DOCTYPEID;
                if (qualificationTO.getDocTypeId() == doctypeId2 && loginForm.getPatternofStudy() != null) {
                    bo.setUgPattern(loginForm.getPatternofStudy());
                    qualificationTO.setUgPattern(loginForm.getPatternofStudy());
                    this.setDetailSubjectMarkBODegree(bo, qualificationTO, detailMark, detailMarkto, loginForm, qualificationTO.getDocTypeId(), loginForm.getPatternofStudy());
                    if (loginForm.getPatternofStudy().equalsIgnoreCase("CBCSS") || loginForm.getPatternofStudy().equalsIgnoreCase("CBCSS NEW")) {
                        if (detailMarkto.getTotalMarksCGPA() != null && !StringUtils.isEmpty(detailMarkto.getTotalMarksCGPA()) && CommonUtil.isValidDecimal(detailMarkto.getTotalMarksCGPA())) {
                            detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarksCGPA()));
                            totalmark = detailMark.getTotalMarks().doubleValue();
                        }
                        if (detailMarkto.getTotalObtainedMarksCGPA() != null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarksCGPA()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarksCGPA())) {
                            detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarksCGPA()));
                            totalobtained = detailMark.getTotalObtainedMarks().doubleValue();
                        }
                    }
                    else {
                        if (detailMarkto.getTotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalMarks())) {
                            detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarks()));
                            totalmark = detailMark.getTotalMarks().doubleValue();
                        }
                        if (detailMarkto.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarks())) {
                            detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarks()));
                            totalobtained = detailMark.getTotalObtainedMarks().doubleValue();
                        }
                    }
                    if (totalmark != 0.0 && totalobtained != 0.0) {
                        percentage = totalobtained / totalmark * 100.0;
                    }
                    final DecimalFormat df2 = new DecimalFormat("#.###");
                    bo.setTotalMarks(new BigDecimal(new Double(df2.format(totalmark)).toString()));
                    bo.setMarksObtained(new BigDecimal(new Double(df2.format(totalobtained)).toString()));
                    bo.setPercentage(new BigDecimal(new Double(df2.format(percentage)).toString()));
                }
                if (qualificationTO.getDocTypeId() != doctypeId ) {
                    detailMark.setSubject1(detailMarkto.getSubject1());
                    detailMark.setSubject2(detailMarkto.getSubject2());
                    detailMark.setSubject3(detailMarkto.getSubject3());
                    detailMark.setSubject4(detailMarkto.getSubject4());
                    detailMark.setSubject5(detailMarkto.getSubject5());
                    detailMark.setSubject6(detailMarkto.getSubject6());
                    detailMark.setSubject7(detailMarkto.getSubject7());
                    detailMark.setSubject8(detailMarkto.getSubject8());
                    detailMark.setSubject9(detailMarkto.getSubject9());
                    detailMark.setSubject10(detailMarkto.getSubject10());
                    detailMark.setSubject11(detailMarkto.getSubject11());
                    detailMark.setSubject12(detailMarkto.getSubject12());
                    detailMark.setSubject13(detailMarkto.getSubject13());
                    detailMark.setSubject14(detailMarkto.getSubject14());
                    detailMark.setSubject15(detailMarkto.getSubject15());
                    detailMark.setSubject16(detailMarkto.getSubject16());
                    detailMark.setSubject17(detailMarkto.getSubject17());
                    detailMark.setSubject18(detailMarkto.getSubject18());
                    detailMark.setSubject19(detailMarkto.getSubject19());
                    detailMark.setSubject20(detailMarkto.getSubject20());
                    if (detailMarkto.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1TotalMarks())) {
                        detailMark.setSubject1TotalMarks(new BigDecimal(detailMarkto.getSubject1TotalMarks()));
                    }
                    if (detailMarkto.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2TotalMarks())) {
                        detailMark.setSubject2TotalMarks(new BigDecimal(detailMarkto.getSubject2TotalMarks()));
                    }
                    if (detailMarkto.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3TotalMarks())) {
                        detailMark.setSubject3TotalMarks(new BigDecimal(detailMarkto.getSubject3TotalMarks()));
                    }
                    if (detailMarkto.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4TotalMarks())) {
                        detailMark.setSubject4TotalMarks(new BigDecimal(detailMarkto.getSubject4TotalMarks()));
                    }
                    if (detailMarkto.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5TotalMarks())) {
                        detailMark.setSubject5TotalMarks(new BigDecimal(detailMarkto.getSubject5TotalMarks()));
                    }
                    if (detailMarkto.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6TotalMarks())) {
                        detailMark.setSubject6TotalMarks(new BigDecimal(detailMarkto.getSubject6TotalMarks()));
                    }
                    if (detailMarkto.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7TotalMarks())) {
                        detailMark.setSubject7TotalMarks(new BigDecimal(detailMarkto.getSubject7TotalMarks()));
                    }
                    if (detailMarkto.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8TotalMarks())) {
                        detailMark.setSubject8TotalMarks(new BigDecimal(detailMarkto.getSubject8TotalMarks()));
                    }
                    if (detailMarkto.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9TotalMarks())) {
                        detailMark.setSubject9TotalMarks(new BigDecimal(detailMarkto.getSubject9TotalMarks()));
                    }
                    if (detailMarkto.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10TotalMarks())) {
                        detailMark.setSubject10TotalMarks(new BigDecimal(detailMarkto.getSubject10TotalMarks()));
                    }
                    if (detailMarkto.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject11TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11TotalMarks())) {
                        detailMark.setSubject11TotalMarks(new BigDecimal(detailMarkto.getSubject11TotalMarks()));
                    }
                    if (detailMarkto.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject12TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12TotalMarks())) {
                        detailMark.setSubject12TotalMarks(new BigDecimal(detailMarkto.getSubject12TotalMarks()));
                    }
                    if (detailMarkto.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject13TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13TotalMarks())) {
                        detailMark.setSubject13TotalMarks(new BigDecimal(detailMarkto.getSubject13TotalMarks()));
                    }
                    if (detailMarkto.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject14TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14TotalMarks())) {
                        detailMark.setSubject14TotalMarks(new BigDecimal(detailMarkto.getSubject14TotalMarks()));
                    }
                    if (detailMarkto.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject15TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15TotalMarks())) {
                        detailMark.setSubject15TotalMarks(new BigDecimal(detailMarkto.getSubject15TotalMarks()));
                    }
                    if (detailMarkto.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject16TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16TotalMarks())) {
                        detailMark.setSubject16TotalMarks(new BigDecimal(detailMarkto.getSubject16TotalMarks()));
                    }
                    if (detailMarkto.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject17TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17TotalMarks())) {
                        detailMark.setSubject17TotalMarks(new BigDecimal(detailMarkto.getSubject17TotalMarks()));
                    }
                    if (detailMarkto.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject18TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18TotalMarks())) {
                        detailMark.setSubject18TotalMarks(new BigDecimal(detailMarkto.getSubject18TotalMarks()));
                    }
                    if (detailMarkto.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject19TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19TotalMarks())) {
                        detailMark.setSubject19TotalMarks(new BigDecimal(detailMarkto.getSubject19TotalMarks()));
                    }
                    if (detailMarkto.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject20TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20TotalMarks())) {
                        detailMark.setSubject20TotalMarks(new BigDecimal(detailMarkto.getSubject20TotalMarks()));
                    }
                    if (detailMarkto.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1ObtainedMarks())) {
                        detailMark.setSubject1ObtainedMarks(new BigDecimal(detailMarkto.getSubject1ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2ObtainedMarks())) {
                        detailMark.setSubject2ObtainedMarks(new BigDecimal(detailMarkto.getSubject2ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3ObtainedMarks())) {
                        detailMark.setSubject3ObtainedMarks(new BigDecimal(detailMarkto.getSubject3ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4ObtainedMarks())) {
                        detailMark.setSubject4ObtainedMarks(new BigDecimal(detailMarkto.getSubject4ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5ObtainedMarks())) {
                        detailMark.setSubject5ObtainedMarks(new BigDecimal(detailMarkto.getSubject5ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6ObtainedMarks())) {
                        detailMark.setSubject6ObtainedMarks(new BigDecimal(detailMarkto.getSubject6ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7ObtainedMarks())) {
                        detailMark.setSubject7ObtainedMarks(new BigDecimal(detailMarkto.getSubject7ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8ObtainedMarks())) {
                        detailMark.setSubject8ObtainedMarks(new BigDecimal(detailMarkto.getSubject8ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9ObtainedMarks())) {
                        detailMark.setSubject9ObtainedMarks(new BigDecimal(detailMarkto.getSubject9ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10ObtainedMarks())) {
                        detailMark.setSubject10ObtainedMarks(new BigDecimal(detailMarkto.getSubject10ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject11ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11ObtainedMarks())) {
                        detailMark.setSubject11ObtainedMarks(new BigDecimal(detailMarkto.getSubject11ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject12ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12ObtainedMarks())) {
                        detailMark.setSubject12ObtainedMarks(new BigDecimal(detailMarkto.getSubject12ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject13ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13ObtainedMarks())) {
                        detailMark.setSubject13ObtainedMarks(new BigDecimal(detailMarkto.getSubject13ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject14ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14ObtainedMarks())) {
                        detailMark.setSubject14ObtainedMarks(new BigDecimal(detailMarkto.getSubject14ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject15ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15ObtainedMarks())) {
                        detailMark.setSubject15ObtainedMarks(new BigDecimal(detailMarkto.getSubject15ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject16ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16ObtainedMarks())) {
                        detailMark.setSubject16ObtainedMarks(new BigDecimal(detailMarkto.getSubject16ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject17ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17ObtainedMarks())) {
                        detailMark.setSubject17ObtainedMarks(new BigDecimal(detailMarkto.getSubject17ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject18ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18ObtainedMarks())) {
                        detailMark.setSubject18ObtainedMarks(new BigDecimal(detailMarkto.getSubject18ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject19ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19ObtainedMarks())) {
                        detailMark.setSubject19ObtainedMarks(new BigDecimal(detailMarkto.getSubject19ObtainedMarks()));
                    }
                    if (detailMarkto.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getSubject20ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20ObtainedMarks())) {
                        detailMark.setSubject20ObtainedMarks(new BigDecimal(detailMarkto.getSubject20ObtainedMarks()));
                    }
                    if (detailMarkto.getTotalMarks() != null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && StringUtils.isNumeric(detailMarkto.getTotalMarks())) {
                        detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarks()));
                        totalmark = detailMark.getTotalMarks().doubleValue();
                    }
                    if (detailMarkto.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getTotalObtainedMarks())) {
                        detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarks()));
                        totalobtained = detailMark.getTotalObtainedMarks().doubleValue();
                    }
                    if (totalmark != 0.0 && totalobtained != 0.0) {
                        percentage = totalobtained / totalmark * 100.0;
                    }
                    bo.setTotalMarks(new BigDecimal(totalmark));
                    bo.setMarksObtained(new BigDecimal(totalobtained));
                    bo.setPercentage(new BigDecimal(percentage));
                }
                detailMarks.add(detailMark);
            }
            bo.setCandidateMarkses((Set)detailMarks);
        }
        qualificationSet.add(bo);
        appBO.getPersonalData().setEdnQualifications((Set)qualificationSet);
        return qualificationSet;
    }
    
    private void setDetailSubjectMarkBOClass12(final EdnQualification ednQualification, final EdnQualificationTO ednQualificationTO, final CandidateMarks candidateMarks, final CandidateMarkTO candidateMarkTO, final LoginForm admForm, final int docName) throws Exception {
        final Map<Integer, String> admlangmap = (Map<Integer, String>)admForm.getAdmSubjectLangMap();
        final Map<Integer, String> admsubmap = (Map<Integer, String>)admForm.getAdmSubjectMap();
        final Set<AdmSubjectMarkForRank> submarksSet = new HashSet<AdmSubjectMarkForRank>();
        int submarkCount = 0;
        if (candidateMarkTO.getSubjectid1() != null && !candidateMarkTO.getSubjectid1().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid1(), candidateMarkTO.getSubjectmarkid1(), candidateMarkTO.getSubjectOther1(), candidateMarkTO.getSubject1TotalMarks(), candidateMarkTO.getSubject1ObtainedMarks(), candidateMarkTO.getSubject1Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid1(candidateMarkTO.getSubjectid1());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                    candidateMarks.setSubject1(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                    candidateMarks.setSubject1(s);
                }
                if (candidateMarkTO.getSubjectOther1() != null && !candidateMarkTO.getSubjectOther1().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther1(candidateMarkTO.getSubjectOther1());
                }
                if (candidateMarkTO.getSubject1TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1TotalMarks())) {
                    candidateMarks.setSubject1TotalMarks(new BigDecimal(candidateMarkTO.getSubject1TotalMarks()));
                }
                if (candidateMarkTO.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1ObtainedMarks())) {
                    candidateMarks.setSubject1ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject1ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid1("");
        }
        if (candidateMarkTO.getSubjectid2() != null && !candidateMarkTO.getSubjectid2().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid2(), candidateMarkTO.getSubjectmarkid2(), candidateMarkTO.getSubjectOther2(), candidateMarkTO.getSubject2TotalMarks(), candidateMarkTO.getSubject2ObtainedMarks(), candidateMarkTO.getSubject2Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid2(candidateMarkTO.getSubjectid2());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                    candidateMarks.setSubject2(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                    candidateMarks.setSubject2(s);
                }
                if (candidateMarkTO.getSubjectOther2() != null && !candidateMarkTO.getSubjectOther2().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther2(candidateMarkTO.getSubjectOther2());
                }
                if (candidateMarkTO.getSubject2TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2TotalMarks())) {
                    candidateMarks.setSubject2TotalMarks(new BigDecimal(candidateMarkTO.getSubject2TotalMarks()));
                }
                if (candidateMarkTO.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2ObtainedMarks())) {
                    candidateMarks.setSubject2ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject2ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid2("");
        }
        if (candidateMarkTO.getSubjectid3() != null && !candidateMarkTO.getSubjectid3().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid3(), candidateMarkTO.getSubjectmarkid3(), candidateMarkTO.getSubjectOther3(), candidateMarkTO.getSubject3TotalMarks(), candidateMarkTO.getSubject3ObtainedMarks(), candidateMarkTO.getSubject3Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid3(candidateMarkTO.getSubjectid3());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                    candidateMarks.setSubject3(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                    candidateMarks.setSubject3(s);
                }
                if (candidateMarkTO.getSubjectOther3() != null && !candidateMarkTO.getSubjectOther3().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther3(candidateMarkTO.getSubjectOther3());
                }
                if (candidateMarkTO.getSubject3TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3TotalMarks())) {
                    candidateMarks.setSubject3TotalMarks(new BigDecimal(candidateMarkTO.getSubject3TotalMarks()));
                }
                if (candidateMarkTO.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3ObtainedMarks())) {
                    candidateMarks.setSubject3ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject3ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid3("");
        }
        if (candidateMarkTO.getSubjectid4() != null && !candidateMarkTO.getSubjectid4().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid4(), candidateMarkTO.getSubjectmarkid4(), candidateMarkTO.getSubjectOther4(), candidateMarkTO.getSubject4TotalMarks(), candidateMarkTO.getSubject4ObtainedMarks(), candidateMarkTO.getSubject4Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid4(candidateMarkTO.getSubjectid4());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                    candidateMarks.setSubject4(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                    candidateMarks.setSubject4(s);
                }
                if (candidateMarkTO.getSubjectOther4() != null && !candidateMarkTO.getSubjectOther4().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther4(candidateMarkTO.getSubjectOther4());
                }
                if (candidateMarkTO.getSubject4TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4TotalMarks())) {
                    candidateMarks.setSubject4TotalMarks(new BigDecimal(candidateMarkTO.getSubject4TotalMarks()));
                }
                if (candidateMarkTO.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4ObtainedMarks())) {
                    candidateMarks.setSubject4ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject4ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid4("");
        }
        if (candidateMarkTO.getSubjectid5() != null && !candidateMarkTO.getSubjectid5().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid5(), candidateMarkTO.getSubjectmarkid5(), candidateMarkTO.getSubjectOther5(), candidateMarkTO.getSubject5TotalMarks(), candidateMarkTO.getSubject5ObtainedMarks(), candidateMarkTO.getSubject5Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid5(candidateMarkTO.getSubjectid5());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                    candidateMarks.setSubject5(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                    candidateMarks.setSubject5(s);
                }
                if (candidateMarkTO.getSubjectOther5() != null && !candidateMarkTO.getSubjectOther5().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther5(candidateMarkTO.getSubjectOther5());
                }
                if (candidateMarkTO.getSubject5TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5TotalMarks())) {
                    candidateMarks.setSubject5TotalMarks(new BigDecimal(candidateMarkTO.getSubject5TotalMarks()));
                }
                if (candidateMarkTO.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5ObtainedMarks())) {
                    candidateMarks.setSubject5ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject5ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid5("");
        }
        if (candidateMarkTO.getSubjectid6() != null && !candidateMarkTO.getSubjectid6().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid6(), candidateMarkTO.getSubjectmarkid6(), candidateMarkTO.getSubjectOther6(), candidateMarkTO.getSubject6TotalMarks(), candidateMarkTO.getSubject6ObtainedMarks(), candidateMarkTO.getSubject6Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid6(candidateMarkTO.getSubjectid6());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                    candidateMarks.setSubject6(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                    candidateMarks.setSubject6(s);
                }
                if (candidateMarkTO.getSubjectOther6() != null && !candidateMarkTO.getSubjectOther6().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther6(candidateMarkTO.getSubjectOther6());
                }
                if (candidateMarkTO.getSubject6TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6TotalMarks())) {
                    candidateMarks.setSubject6TotalMarks(new BigDecimal(candidateMarkTO.getSubject6TotalMarks()));
                }
                if (candidateMarkTO.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6ObtainedMarks())) {
                    candidateMarks.setSubject6ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject6ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid6("");
        }
        if (candidateMarkTO.getSubjectid7() != null && !candidateMarkTO.getSubjectid7().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid7(), candidateMarkTO.getSubjectmarkid7(), candidateMarkTO.getSubjectOther7(), candidateMarkTO.getSubject7TotalMarks(), candidateMarkTO.getSubject7ObtainedMarks(), candidateMarkTO.getSubject7Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid7(candidateMarkTO.getSubjectid7());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                    candidateMarks.setSubject7(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                    candidateMarks.setSubject7(s);
                }
                if (candidateMarkTO.getSubjectOther7() != null && !candidateMarkTO.getSubjectOther7().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther7(candidateMarkTO.getSubjectOther7());
                }
                if (candidateMarkTO.getSubject7TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7TotalMarks())) {
                    candidateMarks.setSubject7TotalMarks(new BigDecimal(candidateMarkTO.getSubject7TotalMarks()));
                }
                if (candidateMarkTO.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7ObtainedMarks())) {
                    candidateMarks.setSubject7ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject7ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid7("");
        }
        if (candidateMarkTO.getSubjectid8() != null && !candidateMarkTO.getSubjectid8().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid8(), candidateMarkTO.getSubjectmarkid8(), candidateMarkTO.getSubjectOther8(), candidateMarkTO.getSubject8TotalMarks(), candidateMarkTO.getSubject8ObtainedMarks(), candidateMarkTO.getSubject8Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid8(candidateMarkTO.getSubjectid8());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                    candidateMarks.setSubject8(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                    candidateMarks.setSubject8(s);
                }
                if (candidateMarkTO.getSubjectOther8() != null && !candidateMarkTO.getSubjectOther8().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther8(candidateMarkTO.getSubjectOther8());
                }
                if (candidateMarkTO.getSubject8TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8TotalMarks())) {
                    candidateMarks.setSubject8TotalMarks(new BigDecimal(candidateMarkTO.getSubject8TotalMarks()));
                }
                if (candidateMarkTO.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8ObtainedMarks())) {
                    candidateMarks.setSubject8ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject8ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid8("");
        }
        if (candidateMarkTO.getSubjectid9() != null && !candidateMarkTO.getSubjectid9().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid9(), candidateMarkTO.getSubjectmarkid9(), candidateMarkTO.getSubjectOther9(), candidateMarkTO.getSubject9TotalMarks(), candidateMarkTO.getSubject9ObtainedMarks(), candidateMarkTO.getSubject9Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid9(candidateMarkTO.getSubjectid9());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                    candidateMarks.setSubject9(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                    candidateMarks.setSubject9(s);
                }
                if (candidateMarkTO.getSubjectOther9() != null && !candidateMarkTO.getSubjectOther9().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther9(candidateMarkTO.getSubjectOther9());
                }
                if (candidateMarkTO.getSubject9TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9TotalMarks())) {
                    candidateMarks.setSubject9TotalMarks(new BigDecimal(candidateMarkTO.getSubject9TotalMarks()));
                }
                if (candidateMarkTO.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9ObtainedMarks())) {
                    candidateMarks.setSubject9ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject9ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid9("");
        }
        if (candidateMarkTO.getSubjectid10() != null && !candidateMarkTO.getSubjectid10().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid10(), candidateMarkTO.getSubjectmarkid10(), candidateMarkTO.getSubjectOther10(), candidateMarkTO.getSubject10TotalMarks(), candidateMarkTO.getSubject10ObtainedMarks(), candidateMarkTO.getSubject10Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid10(candidateMarkTO.getSubjectid10());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                    candidateMarks.setSubject10(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                    candidateMarks.setSubject10(s);
                }
                if (candidateMarkTO.getSubjectOther10() != null && !candidateMarkTO.getSubjectOther10().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther10(candidateMarkTO.getSubjectOther10());
                }
                if (candidateMarkTO.getSubject10TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10TotalMarks())) {
                    candidateMarks.setSubject10TotalMarks(new BigDecimal(candidateMarkTO.getSubject10TotalMarks()));
                }
                if (candidateMarkTO.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10ObtainedMarks())) {
                    candidateMarks.setSubject10ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject10ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid10("");
        }
        if (candidateMarkTO.getSubjectid11() != null && !candidateMarkTO.getSubjectid11().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid11(), candidateMarkTO.getSubjectmarkid11(), candidateMarkTO.getSubjectOther11(), candidateMarkTO.getSubject11TotalMarks(), candidateMarkTO.getSubject11ObtainedMarks(), candidateMarkTO.getSubject11Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid11(candidateMarkTO.getSubjectid11());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                    candidateMarks.setSubject11(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                    candidateMarks.setSubject11(s);
                }
                if (candidateMarkTO.getSubjectOther11() != null && !candidateMarkTO.getSubjectOther11().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther11(candidateMarkTO.getSubjectOther11());
                }
                if (candidateMarkTO.getSubject11TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject11TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11TotalMarks())) {
                    candidateMarks.setSubject11TotalMarks(new BigDecimal(candidateMarkTO.getSubject11TotalMarks()));
                }
                if (candidateMarkTO.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject11ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11ObtainedMarks())) {
                    candidateMarks.setSubject11ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject11ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid11("");
        }
        if (candidateMarkTO.getSubjectid12() != null && !candidateMarkTO.getSubjectid12().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid12(), candidateMarkTO.getSubjectmarkid12(), candidateMarkTO.getSubjectOther12(), candidateMarkTO.getSubject12TotalMarks(), candidateMarkTO.getSubject12ObtainedMarks(), candidateMarkTO.getSubject12Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid12(candidateMarkTO.getSubjectid12());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                    candidateMarks.setSubject12(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                    candidateMarks.setSubject12(s);
                }
                if (candidateMarkTO.getSubjectOther12() != null && !candidateMarkTO.getSubjectOther12().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther12(candidateMarkTO.getSubjectOther12());
                }
                if (candidateMarkTO.getSubject12TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject12TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12TotalMarks())) {
                    candidateMarks.setSubject12TotalMarks(new BigDecimal(candidateMarkTO.getSubject12TotalMarks()));
                }
                if (candidateMarkTO.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject12ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12ObtainedMarks())) {
                    candidateMarks.setSubject12ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject12ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid12("");
        }
        if (candidateMarkTO.getSubjectid13() != null && !candidateMarkTO.getSubjectid13().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid13(), candidateMarkTO.getSubjectmarkid13(), candidateMarkTO.getSubjectOther13(), candidateMarkTO.getSubject13TotalMarks(), candidateMarkTO.getSubject13ObtainedMarks(), candidateMarkTO.getSubject13Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid13(candidateMarkTO.getSubjectid13());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                    candidateMarks.setSubject13(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                    candidateMarks.setSubject13(s);
                }
                if (candidateMarkTO.getSubjectOther13() != null && !candidateMarkTO.getSubjectOther13().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther13(candidateMarkTO.getSubjectOther13());
                }
                if (candidateMarkTO.getSubject13TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject13TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13TotalMarks())) {
                    candidateMarks.setSubject13TotalMarks(new BigDecimal(candidateMarkTO.getSubject13TotalMarks()));
                }
                if (candidateMarkTO.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject13ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13ObtainedMarks())) {
                    candidateMarks.setSubject13ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject13ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid13("");
        }
        if (candidateMarkTO.getSubjectid14() != null && !candidateMarkTO.getSubjectid14().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid14(), candidateMarkTO.getSubjectmarkid14(), candidateMarkTO.getSubjectOther14(), candidateMarkTO.getSubject14TotalMarks(), candidateMarkTO.getSubject14ObtainedMarks(), candidateMarkTO.getSubject14Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid14(candidateMarkTO.getSubjectid14());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                    candidateMarks.setSubject14(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                    candidateMarks.setSubject14(s);
                }
                if (candidateMarkTO.getSubjectOther14() != null && !candidateMarkTO.getSubjectOther14().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther14(candidateMarkTO.getSubjectOther14());
                }
                if (candidateMarkTO.getSubject14TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject14TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14TotalMarks())) {
                    candidateMarks.setSubject14TotalMarks(new BigDecimal(candidateMarkTO.getSubject14TotalMarks()));
                }
                if (candidateMarkTO.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject14ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14ObtainedMarks())) {
                    candidateMarks.setSubject14ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject14ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid14("");
        }
        if (candidateMarkTO.getSubjectid15() != null && !candidateMarkTO.getSubjectid15().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid15(), candidateMarkTO.getSubjectmarkid15(), candidateMarkTO.getSubjectOther15(), candidateMarkTO.getSubject15TotalMarks(), candidateMarkTO.getSubject15ObtainedMarks(), candidateMarkTO.getSubject15Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid15(candidateMarkTO.getSubjectid15());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                    candidateMarks.setSubject15(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                    candidateMarks.setSubject15(s);
                }
                if (candidateMarkTO.getSubjectOther15() != null && !candidateMarkTO.getSubjectOther15().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther15(candidateMarkTO.getSubjectOther15());
                }
                if (candidateMarkTO.getSubject15TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject15TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15TotalMarks())) {
                    candidateMarks.setSubject15TotalMarks(new BigDecimal(candidateMarkTO.getSubject15TotalMarks()));
                }
                if (candidateMarkTO.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject15ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15ObtainedMarks())) {
                    candidateMarks.setSubject15ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject15ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid15("");
        }
        if (candidateMarkTO.getSubjectid16() != null && !candidateMarkTO.getSubjectid16().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid16(), candidateMarkTO.getSubjectmarkid16(), candidateMarkTO.getSubjectOther16(), candidateMarkTO.getSubject16TotalMarks(), candidateMarkTO.getSubject16ObtainedMarks(), candidateMarkTO.getSubject16Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid16(candidateMarkTO.getSubjectid16());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                    candidateMarks.setSubject16(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                    candidateMarks.setSubject16(s);
                }
                if (candidateMarkTO.getSubjectOther16() != null && !candidateMarkTO.getSubjectOther16().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther16(candidateMarkTO.getSubjectOther16());
                }
                if (candidateMarkTO.getSubject16TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject16TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16TotalMarks())) {
                    candidateMarks.setSubject16TotalMarks(new BigDecimal(candidateMarkTO.getSubject16TotalMarks()));
                }
                if (candidateMarkTO.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject16ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16ObtainedMarks())) {
                    candidateMarks.setSubject16ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject16ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid16("");
        }
        if (candidateMarkTO.getSubjectid17() != null && !candidateMarkTO.getSubjectid17().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid17(), candidateMarkTO.getSubjectmarkid17(), candidateMarkTO.getSubjectOther17(), candidateMarkTO.getSubject17TotalMarks(), candidateMarkTO.getSubject17ObtainedMarks(), candidateMarkTO.getSubject17Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid17(candidateMarkTO.getSubjectid17());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                    candidateMarks.setSubject17(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                    candidateMarks.setSubject17(s);
                }
                if (candidateMarkTO.getSubjectOther17() != null && !candidateMarkTO.getSubjectOther17().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther17(candidateMarkTO.getSubjectOther17());
                }
                if (candidateMarkTO.getSubject17TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject17TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17TotalMarks())) {
                    candidateMarks.setSubject17TotalMarks(new BigDecimal(candidateMarkTO.getSubject17TotalMarks()));
                }
                if (candidateMarkTO.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject17ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17ObtainedMarks())) {
                    candidateMarks.setSubject17ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject17ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid17("");
        }
        if (candidateMarkTO.getSubjectid18() != null && !candidateMarkTO.getSubjectid18().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid18(), candidateMarkTO.getSubjectmarkid18(), candidateMarkTO.getSubjectOther18(), candidateMarkTO.getSubject18TotalMarks(), candidateMarkTO.getSubject18ObtainedMarks(), candidateMarkTO.getSubject18Credit(), admForm);
            candidateMarks.setSubjectid18(candidateMarkTO.getSubjectid18());
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                    candidateMarks.setSubject18(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                    candidateMarks.setSubject18(s);
                }
                if (candidateMarkTO.getSubjectOther18() != null && !candidateMarkTO.getSubjectOther18().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther18(candidateMarkTO.getSubjectOther18());
                }
                if (candidateMarkTO.getSubject18TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject18TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18TotalMarks())) {
                    candidateMarks.setSubject18TotalMarks(new BigDecimal(candidateMarkTO.getSubject18TotalMarks()));
                }
                if (candidateMarkTO.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject18ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18ObtainedMarks())) {
                    candidateMarks.setSubject18ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject18ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid18("");
        }
        if (candidateMarkTO.getSubjectid19() != null && !candidateMarkTO.getSubjectid19().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid19(), candidateMarkTO.getSubjectmarkid19(), candidateMarkTO.getSubjectOther19(), candidateMarkTO.getSubject19TotalMarks(), candidateMarkTO.getSubject19ObtainedMarks(), candidateMarkTO.getSubject19Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid19(candidateMarkTO.getSubjectid19());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                    candidateMarks.setSubject19(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                    candidateMarks.setSubject19(s);
                }
                if (candidateMarkTO.getSubjectOther19() != null && !candidateMarkTO.getSubjectOther19().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther19(candidateMarkTO.getSubjectOther19());
                }
                if (candidateMarkTO.getSubject19TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject19TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19TotalMarks())) {
                    candidateMarks.setSubject19TotalMarks(new BigDecimal(candidateMarkTO.getSubject19TotalMarks()));
                }
                if (candidateMarkTO.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject19ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19ObtainedMarks())) {
                    candidateMarks.setSubject19ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject19ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid19("");
        }
        if (candidateMarkTO.getSubjectid20() != null && !candidateMarkTO.getSubjectid20().equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid20(), candidateMarkTO.getSubjectmarkid20(), candidateMarkTO.getSubjectOther20(), candidateMarkTO.getSubject20TotalMarks(), candidateMarkTO.getSubject20ObtainedMarks(), candidateMarkTO.getSubject20Credit(), admForm);
            if (admSubjectMarkForRank != null) {
                submarksSet.add(admSubjectMarkForRank);
                candidateMarks.setSubjectid20(candidateMarkTO.getSubjectid20());
                if (admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                    final String s = admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                    candidateMarks.setSubject20(s);
                }
                else if (admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                    final String s = admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                    candidateMarks.setSubject20(s);
                }
                if (candidateMarkTO.getSubjectOther20() != null && !candidateMarkTO.getSubjectOther20().equalsIgnoreCase("")) {
                    candidateMarks.setSubjectOther20(candidateMarkTO.getSubjectOther20());
                }
                if (candidateMarkTO.getSubject20TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject20TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20TotalMarks())) {
                    candidateMarks.setSubject20TotalMarks(new BigDecimal(candidateMarkTO.getSubject20TotalMarks()));
                }
                if (candidateMarkTO.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject20ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20ObtainedMarks())) {
                    candidateMarks.setSubject20ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject20ObtainedMarks()));
                }
            }
            else {
                ++submarkCount;
            }
        }
        else {
            ++submarkCount;
            candidateMarkTO.setSubjectid20("");
        }
        if (submarkCount != 20) {
            ednQualification.setAdmSubjectMarkForRank((Set)submarksSet);
        }
    }
    
    private AdmSubjectMarkForRank getAdmSubjectMarkForRank(final int docname, final CandidateMarkTO candidateMarkTO, final String subjectId, final String subjectMarkId, final String subjectOther, final String subjectTotalMark, final String subjectObtainMark, final String subjectCredit, final LoginForm admForm) {
        if (subjectId != null && !subjectId.equalsIgnoreCase("") && subjectTotalMark != null && !subjectTotalMark.equalsIgnoreCase("") && subjectObtainMark != null && !subjectObtainMark.equalsIgnoreCase("")) {
            final AdmSubjectMarkForRank admSubjectMarkForRank = new AdmSubjectMarkForRank();
            if (subjectMarkId != null && !subjectMarkId.equalsIgnoreCase("")) {
                admSubjectMarkForRank.setId(Integer.parseInt(subjectMarkId));
            }
            final AdmSubjectForRank admSubjectForRank = new AdmSubjectForRank();
            admSubjectForRank.setId(Integer.parseInt(subjectId));
            admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
            admSubjectMarkForRank.setObtainedmark(subjectObtainMark);
            admSubjectMarkForRank.setMaxmark(subjectTotalMark);
            admSubjectMarkForRank.setIsActive(Boolean.valueOf(true));
            admSubjectMarkForRank.setCreatedDate(new Date());
            admSubjectMarkForRank.setCreatedBy(admForm.getUserId());
            admSubjectMarkForRank.setLastModifiedDate(new Date());
            admSubjectMarkForRank.setModifiedBy(admForm.getUserId());
            final int doctypeId = CMSConstants.CLASS12_DOCTYPEID;
            if (docname == doctypeId) {
                final Double obtmark = Double.parseDouble(subjectObtainMark);
                final Double maxmark = Double.parseDouble(subjectTotalMark);
                final Double conmark = obtmark / maxmark * 200.0;
                final DecimalFormat df = new DecimalFormat("#.###");
                admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
            }
            final int doctypeId2 = CMSConstants.DEGREE_DOCTYPEID;
            return admSubjectMarkForRank;
        }
        return null;
    }
    
    public void setDetailMarkSubjectBOtoTO(final CandidateMarks detailMarkBO, final CandidateMarkTO markTO, final EdnQualification ednQualificationBO) {
        Set<AdmSubjectMarkForRank> submarkListClass = new HashSet<AdmSubjectMarkForRank>();
        submarkListClass = (Set<AdmSubjectMarkForRank>)ednQualificationBO.getAdmSubjectMarkForRank();
        if (submarkListClass.size() != 0) {
            for (final AdmSubjectMarkForRank admSubjectMarkForRank : submarkListClass) {
                if (detailMarkBO.getSubjectid1() != null && !detailMarkBO.getSubjectid1().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid1()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid1(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject1(detailMarkBO.getSubject1());
                    markTO.setSubjectOther1(detailMarkBO.getSubjectOther1());
                    if (detailMarkBO.getSubject1Credit() != null) {
                        markTO.setSubject1Credit(detailMarkBO.getSubject1Credit().toString());
                    }
                    if (detailMarkBO.getSubject1TotalMarks() != null && detailMarkBO.getSubject1TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO.getSubject1TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject1ObtainedMarks() != null && detailMarkBO.getSubject1ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO.getSubject1ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid1(detailMarkBO.getSubjectid1());
                }
                else if (detailMarkBO.getSubjectid2() != null && !detailMarkBO.getSubjectid2().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid2()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid2(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject2(detailMarkBO.getSubject2());
                    markTO.setSubjectOther2(detailMarkBO.getSubjectOther2());
                    if (detailMarkBO.getSubject2Credit() != null) {
                        markTO.setSubject2Credit(detailMarkBO.getSubject2Credit().toString());
                    }
                    if (detailMarkBO.getSubject2TotalMarks() != null && detailMarkBO.getSubject2TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO.getSubject2TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject2ObtainedMarks() != null && detailMarkBO.getSubject2ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject2ObtainedMarks(String.valueOf(detailMarkBO.getSubject2ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid2(detailMarkBO.getSubjectid2());
                }
                else if (detailMarkBO.getSubjectid3() != null && !detailMarkBO.getSubjectid3().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid3()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid3(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject3(detailMarkBO.getSubject3());
                    markTO.setSubjectOther3(detailMarkBO.getSubjectOther3());
                    if (detailMarkBO.getSubject3Credit() != null) {
                        markTO.setSubject3Credit(detailMarkBO.getSubject3Credit().toString());
                    }
                    if (detailMarkBO.getSubject3TotalMarks() != null && detailMarkBO.getSubject3TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO.getSubject3TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject3ObtainedMarks() != null && detailMarkBO.getSubject3ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO.getSubject3ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid3(detailMarkBO.getSubjectid3());
                }
                else if (detailMarkBO.getSubjectid4() != null && !detailMarkBO.getSubjectid4().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid4()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid4(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject4(detailMarkBO.getSubject4());
                    markTO.setSubjectOther4(detailMarkBO.getSubjectOther4());
                    if (detailMarkBO.getSubject4Credit() != null) {
                        markTO.setSubject4Credit(detailMarkBO.getSubject4Credit().toString());
                    }
                    if (detailMarkBO.getSubject4TotalMarks() != null && detailMarkBO.getSubject4TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO.getSubject4TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject4ObtainedMarks() != null && detailMarkBO.getSubject4ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO.getSubject4ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid4(detailMarkBO.getSubjectid4());
                }
                else if (detailMarkBO.getSubjectid5() != null && !detailMarkBO.getSubjectid5().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid5()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid5(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject5(detailMarkBO.getSubject5());
                    markTO.setSubjectOther5(detailMarkBO.getSubjectOther5());
                    if (detailMarkBO.getSubject5Credit() != null) {
                        markTO.setSubject5Credit(detailMarkBO.getSubject5Credit().toString());
                    }
                    if (detailMarkBO.getSubject5TotalMarks() != null && detailMarkBO.getSubject5TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO.getSubject5TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject5ObtainedMarks() != null && detailMarkBO.getSubject5ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO.getSubject5ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid5(detailMarkBO.getSubjectid5());
                }
                else if (detailMarkBO.getSubjectid6() != null && !detailMarkBO.getSubjectid6().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid6()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid6(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject6(detailMarkBO.getSubject6());
                    markTO.setSubjectOther6(detailMarkBO.getSubjectOther6());
                    if (detailMarkBO.getSubject6Credit() != null) {
                        markTO.setSubject6Credit(detailMarkBO.getSubject6Credit().toString());
                    }
                    if (detailMarkBO.getSubject6TotalMarks() != null && detailMarkBO.getSubject6TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO.getSubject6TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject6ObtainedMarks() != null && detailMarkBO.getSubject6ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO.getSubject6ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid6(detailMarkBO.getSubjectid6());
                }
                else if (detailMarkBO.getSubjectid7() != null && !detailMarkBO.getSubjectid7().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid7()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid7(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject7(detailMarkBO.getSubject7());
                    markTO.setSubjectOther7(detailMarkBO.getSubjectOther7());
                    if (detailMarkBO.getSubject7Credit() != null) {
                        markTO.setSubject7Credit(detailMarkBO.getSubject7Credit().toString());
                    }
                    if (detailMarkBO.getSubject7TotalMarks() != null && detailMarkBO.getSubject7TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO.getSubject7TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject7ObtainedMarks() != null && detailMarkBO.getSubject7ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO.getSubject7ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid7(detailMarkBO.getSubjectid7());
                }
                else if (detailMarkBO.getSubjectid8() != null && !detailMarkBO.getSubjectid8().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid8()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid8(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject8(detailMarkBO.getSubject8());
                    markTO.setSubjectOther8(detailMarkBO.getSubjectOther8());
                    if (detailMarkBO.getSubject8Credit() != null) {
                        markTO.setSubject8Credit(detailMarkBO.getSubject8Credit().toString());
                    }
                    if (detailMarkBO.getSubject8TotalMarks() != null && detailMarkBO.getSubject8TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO.getSubject8TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject8ObtainedMarks() != null && detailMarkBO.getSubject8ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO.getSubject8ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid8(detailMarkBO.getSubjectid8());
                }
                else if (detailMarkBO.getSubjectid9() != null && !detailMarkBO.getSubjectid9().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid9()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid9(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject9(detailMarkBO.getSubject9());
                    markTO.setSubjectOther9(detailMarkBO.getSubjectOther9());
                    if (detailMarkBO.getSubject9Credit() != null) {
                        markTO.setSubject9Credit(detailMarkBO.getSubject9Credit().toString());
                    }
                    if (detailMarkBO.getSubject9TotalMarks() != null && detailMarkBO.getSubject9TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO.getSubject9TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject9ObtainedMarks() != null && detailMarkBO.getSubject9ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO.getSubject9ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid9(detailMarkBO.getSubjectid9());
                }
                else if (detailMarkBO.getSubjectid10() != null && !detailMarkBO.getSubjectid10().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid10()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid10(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject10(detailMarkBO.getSubject10());
                    markTO.setSubjectOther10(detailMarkBO.getSubjectOther10());
                    if (detailMarkBO.getSubject10Credit() != null) {
                        markTO.setSubject10Credit(detailMarkBO.getSubject10Credit().toString());
                    }
                    if (detailMarkBO.getSubject10TotalMarks() != null && detailMarkBO.getSubject10TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO.getSubject10TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject10ObtainedMarks() != null && detailMarkBO.getSubject10ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO.getSubject10ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid10(detailMarkBO.getSubjectid10());
                }
                else if (detailMarkBO.getSubjectid11() != null && !detailMarkBO.getSubjectid11().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid11()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid11(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject11(detailMarkBO.getSubject11());
                    markTO.setSubjectOther11(detailMarkBO.getSubjectOther11());
                    if (detailMarkBO.getSubject11TotalMarks() != null && detailMarkBO.getSubject11TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO.getSubject11TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject11ObtainedMarks() != null && detailMarkBO.getSubject11ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO.getSubject11ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid11(detailMarkBO.getSubjectid11());
                }
                else if (detailMarkBO.getSubjectid12() != null && !detailMarkBO.getSubjectid12().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid12()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid12(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject12(detailMarkBO.getSubject12());
                    markTO.setSubjectOther12(detailMarkBO.getSubjectOther12());
                    if (detailMarkBO.getSubject12TotalMarks() != null && detailMarkBO.getSubject12TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO.getSubject12TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject12ObtainedMarks() != null && detailMarkBO.getSubject12ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO.getSubject12ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid12(detailMarkBO.getSubjectid12());
                }
                else if (detailMarkBO.getSubjectid13() != null && !detailMarkBO.getSubjectid13().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid13()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid13(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject13(detailMarkBO.getSubject13());
                    markTO.setSubjectOther13(detailMarkBO.getSubjectOther13());
                    if (detailMarkBO.getSubject13TotalMarks() != null && detailMarkBO.getSubject13TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO.getSubject13TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject13ObtainedMarks() != null && detailMarkBO.getSubject13ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO.getSubject13ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid13(detailMarkBO.getSubjectid13());
                }
                else if (detailMarkBO.getSubjectid14() != null && !detailMarkBO.getSubjectid14().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid14()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid14(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject14(detailMarkBO.getSubject14());
                    markTO.setSubjectOther14(detailMarkBO.getSubjectOther14());
                    if (detailMarkBO.getSubject14TotalMarks() != null && detailMarkBO.getSubject14TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO.getSubject14TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject14ObtainedMarks() != null && detailMarkBO.getSubject14ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO.getSubject14ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid14(detailMarkBO.getSubjectid14());
                }
                else if (detailMarkBO.getSubjectid15() != null && !detailMarkBO.getSubjectid15().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid15()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid15(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject15(detailMarkBO.getSubject15());
                    markTO.setSubjectOther15(detailMarkBO.getSubjectOther15());
                    if (detailMarkBO.getSubject15TotalMarks() != null && detailMarkBO.getSubject15TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO.getSubject15TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject15ObtainedMarks() != null && detailMarkBO.getSubject15ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO.getSubject15ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid15(detailMarkBO.getSubjectid15());
                }
                else if (detailMarkBO.getSubjectid16() != null && !detailMarkBO.getSubjectid16().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid16()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid16(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject16(detailMarkBO.getSubject16());
                    markTO.setSubjectOther16(detailMarkBO.getSubjectOther16());
                    if (detailMarkBO.getSubject16TotalMarks() != null && detailMarkBO.getSubject16TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO.getSubject16TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject16ObtainedMarks() != null && detailMarkBO.getSubject16ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO.getSubject16ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid16(detailMarkBO.getSubjectid16());
                }
                else if (detailMarkBO.getSubjectid17() != null && !detailMarkBO.getSubjectid17().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid17()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid17(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject17(detailMarkBO.getSubject17());
                    markTO.setSubjectOther17(detailMarkBO.getSubjectOther17());
                    if (detailMarkBO.getSubject17TotalMarks() != null && detailMarkBO.getSubject17TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO.getSubject17TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject17ObtainedMarks() != null && detailMarkBO.getSubject17ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO.getSubject17ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid17(detailMarkBO.getSubjectid17());
                }
                else if (detailMarkBO.getSubjectid18() != null && !detailMarkBO.getSubjectid18().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid18()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid18(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject18(detailMarkBO.getSubject18());
                    markTO.setSubjectOther18(detailMarkBO.getSubjectOther18());
                    if (detailMarkBO.getSubject18TotalMarks() != null && detailMarkBO.getSubject18TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO.getSubject18TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject18ObtainedMarks() != null && detailMarkBO.getSubject18ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO.getSubject18ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid18(detailMarkBO.getSubjectid18());
                }
                else if (detailMarkBO.getSubjectid19() != null && !detailMarkBO.getSubjectid19().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid19()) == admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                    markTO.setSubjectmarkid19(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject19(detailMarkBO.getSubject19());
                    markTO.setSubjectOther19(detailMarkBO.getSubjectOther19());
                    if (detailMarkBO.getSubject19TotalMarks() != null && detailMarkBO.getSubject19TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO.getSubject19TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject19ObtainedMarks() != null && detailMarkBO.getSubject19ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO.getSubject19ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid19(detailMarkBO.getSubjectid19());
                }
                else {
                    if (detailMarkBO.getSubjectid20() == null || detailMarkBO.getSubjectid20().equalsIgnoreCase("") || Integer.parseInt(detailMarkBO.getSubjectid20()) != admSubjectMarkForRank.getAdmSubjectForRank().getId()) {
                        continue;
                    }
                    markTO.setSubjectmarkid20(new StringBuilder(String.valueOf(admSubjectMarkForRank.getId())).toString());
                    markTO.setSubject20(detailMarkBO.getSubject20());
                    markTO.setSubjectOther20(detailMarkBO.getSubjectOther20());
                    if (detailMarkBO.getSubject20TotalMarks() != null && detailMarkBO.getSubject20TotalMarks().floatValue() != 0.0f) {
                        markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO.getSubject20TotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getSubject20ObtainedMarks() != null && detailMarkBO.getSubject20ObtainedMarks().floatValue() != 0.0f) {
                        markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO.getSubject20ObtainedMarks().floatValue()));
                    }
                    markTO.setSubjectid20(detailMarkBO.getSubjectid20());
                }
            }
        }
    }
    
    public void convertDetailMarkBOtoTO(final CandidateMarks detailMarkBO, final CandidateMarkTO markTO, final EdnQualification ednQualificationBO) throws Exception {
        if (detailMarkBO != null) {
            if (detailMarkBO.getDetailedSubjects1() != null) {
                final DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
                detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects1().getId());
                markTO.setSubject1(detailMarkBO.getDetailedSubjects1().getSubjectName());
                markTO.setSubject1Mandatory(true);
                markTO.setDetailedSubjects1(detailedSubjectsTO);
            }
            else if (detailMarkBO.getSubject1() != null && detailMarkBO.getSubject1().length() != 0) {
                final DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
                detailedSubjectsTO.setId(-1);
                markTO.setDetailedSubjects1(detailedSubjectsTO);
                markTO.setSubject1(detailMarkBO.getSubject1());
            }
            if (detailMarkBO.getId() != 0) {
                markTO.setId(detailMarkBO.getId());
            }
            markTO.setCreatedBy(detailMarkBO.getCreatedBy());
            markTO.setCreatedDate(detailMarkBO.getCreatedDate());
            final int doctypeId = CMSConstants.CLASS12_DOCTYPEID;
            if (ednQualificationBO.getDocChecklist().getDocType().getId() == doctypeId) {
                this.setDetailMarkSubjectBOtoTO(detailMarkBO, markTO, ednQualificationBO);
                if (detailMarkBO.getTotalMarks() != null && detailMarkBO.getTotalMarks().floatValue() != 0.0f) {
                    markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
                }
                if (detailMarkBO.getTotalObtainedMarks() != null && detailMarkBO.getTotalObtainedMarks().floatValue() != 0.0f) {
                    markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
                }
                final String language = "Language";
                final Map<Integer, String> admsubjectLangMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassLangSubjects(ednQualificationBO.getDocChecklist().getDocType().getName(), language);
                final String strValue = "English";
                String intKey = null;
                for (final Map.Entry entry : admsubjectLangMap.entrySet()) {
                    if (strValue.equals(entry.getValue())) {
                        intKey = entry.getKey().toString();
                        if (detailMarkBO.getSubjectid1() != null && !detailMarkBO.getSubjectid1().equalsIgnoreCase("")) {
                            intKey = detailMarkBO.getSubjectid1();
                        }
                        markTO.setSubjectid1(intKey);
                        break;
                    }
                }
            }
            final int doctypeId2 = CMSConstants.DEGREE_DOCTYPEID;
            if (ednQualificationBO.getDocChecklist().getDocType().getId() == doctypeId2 && ednQualificationBO.getUgPattern() != null) {
                this.setDetailMarkSubjectBOtoTO(detailMarkBO, markTO, ednQualificationBO);
                if (ednQualificationBO.getUgPattern().equalsIgnoreCase("CBCSS") || ednQualificationBO.getUgPattern().equalsIgnoreCase("CBCSS NEW")) {
                    if (detailMarkBO.getTotalMarks() != null && detailMarkBO.getTotalMarks().floatValue() != 0.0f) {
                        markTO.setTotalMarksCGPA(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getTotalObtainedMarks() != null && detailMarkBO.getTotalObtainedMarks().floatValue() != 0.0f) {
                        markTO.setTotalObtainedMarksCGPA(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
                    }
                }
                else {
                    if (detailMarkBO.getTotalMarks() != null && detailMarkBO.getTotalMarks().floatValue() != 0.0f) {
                        markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
                    }
                    if (detailMarkBO.getTotalObtainedMarks() != null && detailMarkBO.getTotalObtainedMarks().floatValue() != 0.0f) {
                        markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
                    }
                }
                final String Common = "Common";
                final Map<Integer, String> admCommonMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationBO.getDocChecklist().getDocType().getName(), Common);
                final String strValue2 = "English";
                String intKey2 = null;
                for (final Map.Entry entry2 : admCommonMap.entrySet()) {
                    if (strValue2.equals(entry2.getValue())) {
                        intKey2 = entry2.getKey().toString();
                        if (detailMarkBO.getSubjectid6() != null && !detailMarkBO.getSubjectid6().equalsIgnoreCase("")) {
                            intKey2 = detailMarkBO.getSubjectid6();
                        }
                        markTO.setSubjectid6(intKey2);
                        intKey2 = entry2.getKey().toString();
                        if (detailMarkBO.getSubjectid16() != null && !detailMarkBO.getSubjectid16().equalsIgnoreCase("")) {
                            intKey2 = detailMarkBO.getSubjectid16();
                        }
                        markTO.setSubjectid16(intKey2);
                        break;
                    }
                }
            }
            else if (ednQualificationBO.getDocChecklist().getDocType().getId() == doctypeId2) {
                final String Common = "Common";
                final Map<Integer, String> admCommonMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationBO.getDocChecklist().getDocType().getName(), Common);
                final String strValue2 = "English";
                String intKey2 = null;
                for (final Map.Entry entry2 : admCommonMap.entrySet()) {
                    if (strValue2.equals(entry2.getValue())) {
                        intKey2 = entry2.getKey().toString();
                        if (detailMarkBO.getSubjectid6() != null && !detailMarkBO.getSubjectid6().equalsIgnoreCase("")) {
                            intKey2 = detailMarkBO.getSubjectid6();
                        }
                        markTO.setSubjectid6(intKey2);
                        intKey2 = entry2.getKey().toString();
                        if (detailMarkBO.getSubjectid16() != null && !detailMarkBO.getSubjectid16().equalsIgnoreCase("")) {
                            intKey2 = detailMarkBO.getSubjectid16();
                        }
                        markTO.setSubjectid16(intKey2);
                        break;
                    }
                }
            }
            if (ednQualificationBO.getDocChecklist().getDocType().getId() != doctypeId && ednQualificationBO.getDocChecklist().getDocType().getId() != doctypeId2) {
                markTO.setSubject1(detailMarkBO.getSubject1());
                markTO.setSubject2(detailMarkBO.getSubject2());
                markTO.setSubject3(detailMarkBO.getSubject3());
                markTO.setSubject4(detailMarkBO.getSubject4());
                markTO.setSubject5(detailMarkBO.getSubject5());
                markTO.setSubject6(detailMarkBO.getSubject6());
                markTO.setSubject7(detailMarkBO.getSubject7());
                markTO.setSubject8(detailMarkBO.getSubject8());
                markTO.setSubject9(detailMarkBO.getSubject9());
                markTO.setSubject10(detailMarkBO.getSubject10());
                markTO.setSubject11(detailMarkBO.getSubject11());
                markTO.setSubject12(detailMarkBO.getSubject12());
                markTO.setSubject13(detailMarkBO.getSubject13());
                markTO.setSubject14(detailMarkBO.getSubject14());
                markTO.setSubject15(detailMarkBO.getSubject15());
                markTO.setSubject16(detailMarkBO.getSubject16());
                markTO.setSubject17(detailMarkBO.getSubject17());
                markTO.setSubject18(detailMarkBO.getSubject18());
                markTO.setSubject19(detailMarkBO.getSubject19());
                markTO.setSubject20(detailMarkBO.getSubject20());
                if (detailMarkBO.getSubject1TotalMarks() != null && detailMarkBO.getSubject1TotalMarks().intValue() != 0) {
                    markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO.getSubject1TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject2TotalMarks() != null && detailMarkBO.getSubject2TotalMarks().intValue() != 0) {
                    markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO.getSubject2TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject3TotalMarks() != null && detailMarkBO.getSubject3TotalMarks().intValue() != 0) {
                    markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO.getSubject3TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject4TotalMarks() != null && detailMarkBO.getSubject4TotalMarks().intValue() != 0) {
                    markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO.getSubject4TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject5TotalMarks() != null && detailMarkBO.getSubject5TotalMarks().intValue() != 0) {
                    markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO.getSubject5TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject6TotalMarks() != null && detailMarkBO.getSubject6TotalMarks().intValue() != 0) {
                    markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO.getSubject6TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject7TotalMarks() != null && detailMarkBO.getSubject7TotalMarks().intValue() != 0) {
                    markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO.getSubject7TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject8TotalMarks() != null && detailMarkBO.getSubject8TotalMarks().intValue() != 0) {
                    markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO.getSubject8TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject9TotalMarks() != null && detailMarkBO.getSubject9TotalMarks().intValue() != 0) {
                    markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO.getSubject9TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject10TotalMarks() != null && detailMarkBO.getSubject10TotalMarks().intValue() != 0) {
                    markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO.getSubject10TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject11TotalMarks() != null && detailMarkBO.getSubject11TotalMarks().intValue() != 0) {
                    markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO.getSubject11TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject12TotalMarks() != null && detailMarkBO.getSubject12TotalMarks().intValue() != 0) {
                    markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO.getSubject12TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject13TotalMarks() != null && detailMarkBO.getSubject13TotalMarks().intValue() != 0) {
                    markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO.getSubject13TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject14TotalMarks() != null && detailMarkBO.getSubject14TotalMarks().intValue() != 0) {
                    markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO.getSubject14TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject15TotalMarks() != null && detailMarkBO.getSubject15TotalMarks().intValue() != 0) {
                    markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO.getSubject15TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject16TotalMarks() != null && detailMarkBO.getSubject16TotalMarks().intValue() != 0) {
                    markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO.getSubject16TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject17TotalMarks() != null && detailMarkBO.getSubject17TotalMarks().intValue() != 0) {
                    markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO.getSubject17TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject18TotalMarks() != null && detailMarkBO.getSubject18TotalMarks().intValue() != 0) {
                    markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO.getSubject18TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject19TotalMarks() != null && detailMarkBO.getSubject19TotalMarks().intValue() != 0) {
                    markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO.getSubject19TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject20TotalMarks() != null && detailMarkBO.getSubject20TotalMarks().intValue() != 0) {
                    markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO.getSubject20TotalMarks().intValue()));
                }
                if (detailMarkBO.getSubject1ObtainedMarks() != null && detailMarkBO.getSubject1ObtainedMarks().intValue() != 0) {
                    markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO.getSubject1ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject2ObtainedMarks() != null && detailMarkBO.getSubject2ObtainedMarks().intValue() != 0) {
                    markTO.setSubject2ObtainedMarks(String.valueOf(detailMarkBO.getSubject2ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject3ObtainedMarks() != null && detailMarkBO.getSubject3ObtainedMarks().intValue() != 0) {
                    markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO.getSubject3ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject4ObtainedMarks() != null && detailMarkBO.getSubject4ObtainedMarks().intValue() != 0) {
                    markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO.getSubject4ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject5ObtainedMarks() != null && detailMarkBO.getSubject5ObtainedMarks().intValue() != 0) {
                    markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO.getSubject5ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject6ObtainedMarks() != null && detailMarkBO.getSubject6ObtainedMarks().intValue() != 0) {
                    markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO.getSubject6ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject7ObtainedMarks() != null && detailMarkBO.getSubject7ObtainedMarks().intValue() != 0) {
                    markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO.getSubject7ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject8ObtainedMarks() != null && detailMarkBO.getSubject8ObtainedMarks().intValue() != 0) {
                    markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO.getSubject8ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject9ObtainedMarks() != null && detailMarkBO.getSubject9ObtainedMarks().intValue() != 0) {
                    markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO.getSubject9ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject10ObtainedMarks() != null && detailMarkBO.getSubject10ObtainedMarks().intValue() != 0) {
                    markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO.getSubject10ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject11ObtainedMarks() != null && detailMarkBO.getSubject11ObtainedMarks().intValue() != 0) {
                    markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO.getSubject11ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject12ObtainedMarks() != null && detailMarkBO.getSubject12ObtainedMarks().intValue() != 0) {
                    markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO.getSubject12ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject13ObtainedMarks() != null && detailMarkBO.getSubject13ObtainedMarks().intValue() != 0) {
                    markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO.getSubject13ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject14ObtainedMarks() != null && detailMarkBO.getSubject14ObtainedMarks().intValue() != 0) {
                    markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO.getSubject14ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject15ObtainedMarks() != null && detailMarkBO.getSubject15ObtainedMarks().intValue() != 0) {
                    markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO.getSubject15ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject16ObtainedMarks() != null && detailMarkBO.getSubject16ObtainedMarks().intValue() != 0) {
                    markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO.getSubject16ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject17ObtainedMarks() != null && detailMarkBO.getSubject17ObtainedMarks().intValue() != 0) {
                    markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO.getSubject17ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject18ObtainedMarks() != null && detailMarkBO.getSubject18ObtainedMarks().intValue() != 0) {
                    markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO.getSubject18ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject19ObtainedMarks() != null && detailMarkBO.getSubject19ObtainedMarks().intValue() != 0) {
                    markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO.getSubject19ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getSubject20ObtainedMarks() != null && detailMarkBO.getSubject20ObtainedMarks().intValue() != 0) {
                    markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO.getSubject20ObtainedMarks().intValue()));
                }
                if (detailMarkBO.getTotalMarks() != null && detailMarkBO.getTotalMarks().intValue() != 0) {
                    markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().intValue()));
                }
                if (detailMarkBO.getTotalObtainedMarks() != null && detailMarkBO.getTotalObtainedMarks().intValue() != 0) {
                    markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().intValue()));
                }
            }
        }
    }
    
    public EdnQualificationTO prepareEduQualificationMap(final Set<EdnQualification> ednQualifications, final LoginForm admForm) throws Exception {
        if (ednQualifications != null && !ednQualifications.isEmpty()) {
            final Iterator<EdnQualification> iterator = ednQualifications.iterator();
            final List<UniversityTO> universityList = (List<UniversityTO>)UniversityHandler.getInstance().getUniversity();
            final List<DocChecklist> exambos = (List<DocChecklist>)admForm.getDocCheckList();
            int doctype = 0;
            int ExamDoctype = 0;
            String ExamDoctypeName = null;
            if (Integer.parseInt(admForm.getProgramTypeId()) == 1) {
                doctype = 3;
                ExamDoctype = 9;
                ExamDoctypeName = "Class 12";
            }
            else {
                doctype = 2;
                ExamDoctype = 6;
                ExamDoctypeName = "DEG";
            }
            while (iterator.hasNext()) {
                final EdnQualification ednQualificationBO = iterator.next();
                if (ednQualificationBO.getDocTypeExams() != null && ednQualificationBO.getDocTypeExams().getId() == doctype) {
                    final EdnQualificationTO ednQualificationTO = new EdnQualificationTO();
                    for (final DocChecklist docChecklist : exambos) {
                        if (docChecklist.getDocType().getId() == ExamDoctype) {
                            ednQualificationBO.setDocChecklist(docChecklist);
                        }
                    }
                    if (ednQualificationBO.getId() != 0) {
                        ednQualificationTO.setId(ednQualificationBO.getId());
                    }
                    ednQualificationTO.setCreatedBy(ednQualificationBO.getCreatedBy());
                    ednQualificationTO.setCreatedDate(ednQualificationBO.getCreatedDate());
                    if (ednQualificationBO.getState() != null) {
                        ednQualificationTO.setStateId(String.valueOf(ednQualificationBO.getState().getId()));
                        ednQualificationTO.setStateName(ednQualificationBO.getState().getName());
                    }
                    else if (ednQualificationBO.getIsOutsideIndia() != null && ednQualificationBO.getIsOutsideIndia()) {
                        ednQualificationTO.setStateId("OUTSIDEINDIA");
                        ednQualificationTO.setStateName("OUTSIDEINDIA");
                    }
                    if (ednQualificationBO.getDocTypeExams() != null) {
                        ednQualificationTO.setSelectedExamId(String.valueOf(ednQualificationBO.getDocTypeExams().getId()));
                        if (ednQualificationBO.getDocTypeExams().getName() != null) {
                            ednQualificationTO.setSelectedExamName(String.valueOf(ednQualificationBO.getDocTypeExams().getName()));
                        }
                        if (ednQualificationBO.getDocTypeExams().getDocType() != null) {
                            ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocTypeExams().getDocType().getDisplayOrder());
                        }
                    }
                    if (ednQualificationBO.getDocChecklist() != null && ednQualificationBO.getDocChecklist().getDocType() != null) {
                        final AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
                        final List<DocTypeExamsTO> examtos = (List<DocTypeExamsTO>)handler.getDocExamsByID(ednQualificationBO.getDocChecklist().getDocType().getId());
                        ednQualificationTO.setExamTos((List)examtos);
                        if (examtos != null && !examtos.isEmpty()) {
                            ednQualificationTO.setExamRequired(true);
                        }
                        if (ednQualificationBO.getDocChecklist().getDocType() != null) {
                            ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocChecklist().getDocType().getDisplayOrder());
                        }
                    }
                    if (ednQualificationBO.getDocChecklist() != null && ednQualificationBO.getDocChecklist().getDocType() != null) {
                        ednQualificationTO.setDocCheckListId(ednQualificationBO.getDocChecklist().getId());
                        ednQualificationTO.setDocName(ednQualificationBO.getDocChecklist().getDocType().getName());
                        ednQualificationTO.setDocTypeId(ednQualificationBO.getDocChecklist().getDocType().getId());
                        ednQualificationTO.setSemesterWise(false);
                        ednQualificationTO.setConsolidated(true);
                        if (ednQualificationBO.getDocChecklist() != null && ednQualificationBO.getDocChecklist().getIsActive() && ednQualificationBO.getDocChecklist().getIsMarksCard() && !ednQualificationBO.getDocChecklist().getIsConsolidatedMarks() && !ednQualificationBO.getDocChecklist().getIsSemesterWise()) {
                            ednQualificationTO.setConsolidated(false);
                            final Set<CandidateMarks> detailMarks = (Set<CandidateMarks>)ednQualificationBO.getCandidateMarkses();
                            if (detailMarks != null && !detailMarks.isEmpty()) {
                                CandidateMarks detailMarkBO = null;
                                final Iterator<CandidateMarks> markItr = detailMarks.iterator();
                                while (markItr.hasNext()) {
                                    detailMarkBO = markItr.next();
                                    final CandidateMarkTO markTO = new CandidateMarkTO();
                                    this.convertDetailMarkBOtoTO(detailMarkBO, markTO, ednQualificationBO);
                                    final int doctypeId1 = CMSConstants.DEGREE_DOCTYPEID;
                                    ednQualificationTO.setDetailmark(markTO);
                                }
                            }
                            else {
                                ednQualificationTO.setDetailmark((CandidateMarkTO)null);
                            }
                        }
                    }
                    if (ednQualificationBO.getUniversityOthers() != null && !ednQualificationBO.getUniversityOthers().isEmpty()) {
                        ednQualificationTO.setUniversityId("Other");
                        ednQualificationTO.setUniversityOthers(ednQualificationBO.getUniversityOthers());
                        ednQualificationTO.setUniversityName(ednQualificationBO.getUniversityOthers());
                    }
                    else if (ednQualificationBO.getUniversity() != null && ednQualificationBO.getUniversity().getId() != 0) {
                        ednQualificationTO.setUniversityId(String.valueOf(ednQualificationBO.getUniversity().getId()));
                        ednQualificationTO.setUniversityName(ednQualificationBO.getUniversity().getName());
                    }
                    if (ednQualificationBO.getInstitutionNameOthers() != null && !ednQualificationBO.getInstitutionNameOthers().isEmpty()) {
                        ednQualificationTO.setInstitutionId("Other");
                        ednQualificationTO.setInstitutionName(ednQualificationBO.getInstitutionNameOthers());
                        ednQualificationTO.setOtherInstitute(ednQualificationBO.getInstitutionNameOthers());
                    }
                    else if (ednQualificationBO.getCollege() != null && ednQualificationBO.getCollege().getId() != 0) {
                        ednQualificationTO.setInstitutionId(String.valueOf(ednQualificationBO.getCollege().getId()));
                        ednQualificationTO.setInstitutionName(ednQualificationBO.getCollege().getName());
                    }
                    if (ednQualificationBO.getYearPassing() != null) {
                        ednQualificationTO.setYearPassing((int)ednQualificationBO.getYearPassing());
                    }
                    if (ednQualificationBO.getMonthPassing() != null) {
                        ednQualificationTO.setMonthPassing((int)ednQualificationBO.getMonthPassing());
                    }
                    ednQualificationTO.setPreviousRegNo(ednQualificationBO.getPreviousRegNo());
                    if (ednQualificationBO.getMarksObtained() != null) {
                        ednQualificationTO.setMarksObtained(String.valueOf(String.valueOf(ednQualificationBO.getMarksObtained().doubleValue())));
                    }
                    if (ednQualificationBO.getTotalMarks() != null) {
                        ednQualificationTO.setTotalMarks(String.valueOf(String.valueOf(ednQualificationBO.getTotalMarks().doubleValue())));
                    }
                    if (ednQualificationBO.getNoOfAttempts() != null) {
                        ednQualificationTO.setNoOfAttempts((int)ednQualificationBO.getNoOfAttempts());
                    }
                    if (ednQualificationBO.getPercentage() != null) {
                        ednQualificationTO.setPercentage(String.valueOf(ednQualificationBO.getPercentage()));
                    }
                    if (ednQualificationTO.getUniversityId() != null) {
                        ednQualificationTO.getUniversityId().equalsIgnoreCase("Other");
                    }
                    final List<UniversityTO> universityTempList = new ArrayList<UniversityTO>();
                    if (universityList != null && !universityList.isEmpty()) {
                        for (final UniversityTO universityTO : universityList) {
                            if (universityTO.getDocTypeId() == ednQualificationTO.getDocTypeId()) {
                                universityTempList.add(universityTO);
                            }
                        }
                    }
                    ednQualificationTO.setUniversityList((List)universityTempList);
                    if (universityList != null && !universityList.isEmpty() && universityList != null && ednQualificationTO.getUniversityId() != null && !ednQualificationTO.getUniversityId().equalsIgnoreCase("Other") && ednQualificationTO.getInstitutionId() != null && !ednQualificationTO.getInstitutionId().equalsIgnoreCase("Other")) {
                        for (final UniversityTO unTO : universityList) {
                            if (unTO.getId() == Integer.parseInt(ednQualificationTO.getUniversityId())) {
                                ednQualificationTO.setInstituteList(unTO.getCollegeTos());
                            }
                        }
                    }
                    return ednQualificationTO;
                }
            }
        }
        return null;
    }
    
    private void setDetailSubjectMarkBODegree(final EdnQualification ednQualification, final EdnQualificationTO ednQualificationTO, final CandidateMarks candidateMarks, final CandidateMarkTO candidateMarkTO, final LoginForm admForm, final int docName, final String patternofStudy) throws Exception {
        final Map<Integer, String> admCoreMap = (Map<Integer, String>)admForm.getAdmCoreMap();
        final Map<Integer, String> admComplMap = (Map<Integer, String>)admForm.getAdmComplMap();
        final Map<Integer, String> admCommonMap = (Map<Integer, String>)admForm.getAdmCommonMap();
        final Map<Integer, String> admopenMap = (Map<Integer, String>)admForm.getAdmMainMap();
        final Map<Integer, String> admSubMap = (Map<Integer, String>)admForm.getAdmSubMap();
        final Set<AdmSubjectMarkForRank> submarksSet = new HashSet<AdmSubjectMarkForRank>();
        int submarkCount = 0;
        if (patternofStudy.equalsIgnoreCase("CBCSS") || patternofStudy.equalsIgnoreCase("CBCSS NEW")) {
            if (candidateMarkTO.getSubjectid1() != null && !candidateMarkTO.getSubjectid1().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid1(), candidateMarkTO.getSubjectmarkid1(), candidateMarkTO.getSubjectOther1(), candidateMarkTO.getSubject1TotalMarks(), candidateMarkTO.getSubject1ObtainedMarks(), candidateMarkTO.getSubject1Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid1(candidateMarkTO.getSubjectid1());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                        candidateMarks.setSubject1(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                        candidateMarks.setSubject1(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                        candidateMarks.setSubject1(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                        candidateMarks.setSubject1(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
                        candidateMarks.setSubject1(s);
                    }
                    if (candidateMarkTO.getSubjectOther1() != null && !candidateMarkTO.getSubjectOther1().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther1(candidateMarkTO.getSubjectOther1());
                    }
                    if (candidateMarkTO.getSubject1Credit() != null && !candidateMarkTO.getSubject1Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject1Credit(new BigDecimal(candidateMarkTO.getSubject1Credit()));
                    }
                    if (candidateMarkTO.getSubject1TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1TotalMarks())) {
                        candidateMarks.setSubject1TotalMarks(new BigDecimal(candidateMarkTO.getSubject1TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1ObtainedMarks())) {
                        candidateMarks.setSubject1ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject1ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid1("");
            }
            if (candidateMarkTO.getSubjectid2() != null && !candidateMarkTO.getSubjectid2().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid2(), candidateMarkTO.getSubjectmarkid2(), candidateMarkTO.getSubjectOther2(), candidateMarkTO.getSubject2TotalMarks(), candidateMarkTO.getSubject2ObtainedMarks(), candidateMarkTO.getSubject2Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid2(candidateMarkTO.getSubjectid2());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                        candidateMarks.setSubject2(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                        candidateMarks.setSubject2(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                        candidateMarks.setSubject2(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                        candidateMarks.setSubject2(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
                        candidateMarks.setSubject2(s);
                    }
                    if (candidateMarkTO.getSubjectOther2() != null && !candidateMarkTO.getSubjectOther2().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther2(candidateMarkTO.getSubjectOther2());
                    }
                    if (candidateMarkTO.getSubject2Credit() != null && !candidateMarkTO.getSubject2Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject2Credit(new BigDecimal(candidateMarkTO.getSubject2Credit()));
                    }
                    if (candidateMarkTO.getSubject2TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2TotalMarks())) {
                        candidateMarks.setSubject2TotalMarks(new BigDecimal(candidateMarkTO.getSubject2TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2ObtainedMarks())) {
                        candidateMarks.setSubject2ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject2ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid2("");
            }
            if (candidateMarkTO.getSubjectid3() != null && !candidateMarkTO.getSubjectid3().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid3(), candidateMarkTO.getSubjectmarkid3(), candidateMarkTO.getSubjectOther3(), candidateMarkTO.getSubject3TotalMarks(), candidateMarkTO.getSubject3ObtainedMarks(), candidateMarkTO.getSubject3Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid3(candidateMarkTO.getSubjectid3());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                        candidateMarks.setSubject3(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                        candidateMarks.setSubject3(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                        candidateMarks.setSubject3(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                        candidateMarks.setSubject3(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
                        candidateMarks.setSubject3(s);
                    }
                    if (candidateMarkTO.getSubjectOther3() != null && !candidateMarkTO.getSubjectOther3().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther3(candidateMarkTO.getSubjectOther3());
                    }
                    if (candidateMarkTO.getSubject3Credit() != null && !candidateMarkTO.getSubject3Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject3Credit(new BigDecimal(candidateMarkTO.getSubject3Credit()));
                    }
                    if (candidateMarkTO.getSubject3TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3TotalMarks())) {
                        candidateMarks.setSubject3TotalMarks(new BigDecimal(candidateMarkTO.getSubject3TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3ObtainedMarks())) {
                        candidateMarks.setSubject3ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject3ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid3("");
            }
            if (candidateMarkTO.getSubjectid4() != null && !candidateMarkTO.getSubjectid4().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid4(), candidateMarkTO.getSubjectmarkid4(), candidateMarkTO.getSubjectOther4(), candidateMarkTO.getSubject4TotalMarks(), candidateMarkTO.getSubject4ObtainedMarks(), candidateMarkTO.getSubject4Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid4(candidateMarkTO.getSubjectid4());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                        candidateMarks.setSubject4(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                        candidateMarks.setSubject4(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                        candidateMarks.setSubject4(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                        candidateMarks.setSubject4(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
                        candidateMarks.setSubject4(s);
                    }
                    if (candidateMarkTO.getSubjectOther4() != null && !candidateMarkTO.getSubjectOther4().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther4(candidateMarkTO.getSubjectOther4());
                    }
                    if (candidateMarkTO.getSubject4Credit() != null && !candidateMarkTO.getSubject4Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject4Credit(new BigDecimal(candidateMarkTO.getSubject4Credit()));
                    }
                    if (candidateMarkTO.getSubject4TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4TotalMarks())) {
                        candidateMarks.setSubject4TotalMarks(new BigDecimal(candidateMarkTO.getSubject4TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4ObtainedMarks())) {
                        candidateMarks.setSubject4ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject4ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid4("");
            }
            if (candidateMarkTO.getSubjectid5() != null && !candidateMarkTO.getSubjectid5().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid5(), candidateMarkTO.getSubjectmarkid5(), candidateMarkTO.getSubjectOther5(), candidateMarkTO.getSubject5TotalMarks(), candidateMarkTO.getSubject5ObtainedMarks(), candidateMarkTO.getSubject5Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid5(candidateMarkTO.getSubjectid5());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                        candidateMarks.setSubject5(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                        candidateMarks.setSubject5(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                        candidateMarks.setSubject5(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                        candidateMarks.setSubject5(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
                        candidateMarks.setSubject5(s);
                    }
                    if (candidateMarkTO.getSubjectOther5() != null && !candidateMarkTO.getSubjectOther5().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther5(candidateMarkTO.getSubjectOther5());
                    }
                    if (candidateMarkTO.getSubject5Credit() != null && !candidateMarkTO.getSubject5Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject5Credit(new BigDecimal(candidateMarkTO.getSubject5Credit()));
                    }
                    if (candidateMarkTO.getSubject5TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5TotalMarks())) {
                        candidateMarks.setSubject5TotalMarks(new BigDecimal(candidateMarkTO.getSubject5TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5ObtainedMarks())) {
                        candidateMarks.setSubject5ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject5ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid5("");
            }
            if (candidateMarkTO.getSubjectid6() != null && !candidateMarkTO.getSubjectid6().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid6(), candidateMarkTO.getSubjectmarkid6(), candidateMarkTO.getSubjectOther6(), candidateMarkTO.getSubject6TotalMarks(), candidateMarkTO.getSubject6ObtainedMarks(), candidateMarkTO.getSubject6Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid6(candidateMarkTO.getSubjectid6());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                        candidateMarks.setSubject6(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                        candidateMarks.setSubject6(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                        candidateMarks.setSubject6(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                        candidateMarks.setSubject6(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
                        candidateMarks.setSubject6(s);
                    }
                    if (candidateMarkTO.getSubjectOther6() != null && !candidateMarkTO.getSubjectOther6().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther6(candidateMarkTO.getSubjectOther6());
                    }
                    if (candidateMarkTO.getSubject6Credit() != null && !candidateMarkTO.getSubject6Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject6Credit(new BigDecimal(candidateMarkTO.getSubject6Credit()));
                    }
                    if (candidateMarkTO.getSubject6TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6TotalMarks())) {
                        candidateMarks.setSubject6TotalMarks(new BigDecimal(candidateMarkTO.getSubject6TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6ObtainedMarks())) {
                        candidateMarks.setSubject6ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject6ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid6("");
            }
            if (candidateMarkTO.getSubjectid7() != null && !candidateMarkTO.getSubjectid7().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid7(), candidateMarkTO.getSubjectmarkid7(), candidateMarkTO.getSubjectOther7(), candidateMarkTO.getSubject7TotalMarks(), candidateMarkTO.getSubject7ObtainedMarks(), candidateMarkTO.getSubject7Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid7(candidateMarkTO.getSubjectid7());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                        candidateMarks.setSubject7(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                        candidateMarks.setSubject7(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                        candidateMarks.setSubject7(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                        candidateMarks.setSubject7(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
                        candidateMarks.setSubject7(s);
                    }
                    if (candidateMarkTO.getSubjectOther7() != null && !candidateMarkTO.getSubjectOther7().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther7(candidateMarkTO.getSubjectOther7());
                    }
                    if (candidateMarkTO.getSubject7Credit() != null && !candidateMarkTO.getSubject7Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject7Credit(new BigDecimal(candidateMarkTO.getSubject7Credit()));
                    }
                    if (candidateMarkTO.getSubject7TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7TotalMarks())) {
                        candidateMarks.setSubject7TotalMarks(new BigDecimal(candidateMarkTO.getSubject7TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7ObtainedMarks())) {
                        candidateMarks.setSubject7ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject7ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid7("");
            }
            if (candidateMarkTO.getSubjectid8() != null && !candidateMarkTO.getSubjectid8().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid8(), candidateMarkTO.getSubjectmarkid8(), candidateMarkTO.getSubjectOther8(), candidateMarkTO.getSubject8TotalMarks(), candidateMarkTO.getSubject8ObtainedMarks(), candidateMarkTO.getSubject8Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid8(candidateMarkTO.getSubjectid8());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                        candidateMarks.setSubject8(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                        candidateMarks.setSubject8(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                        candidateMarks.setSubject8(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                        candidateMarks.setSubject8(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
                        candidateMarks.setSubject8(s);
                    }
                    if (candidateMarkTO.getSubjectOther8() != null && !candidateMarkTO.getSubjectOther8().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther8(candidateMarkTO.getSubjectOther8());
                    }
                    if (candidateMarkTO.getSubject8Credit() != null && !candidateMarkTO.getSubject8Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject8Credit(new BigDecimal(candidateMarkTO.getSubject8Credit()));
                    }
                    if (candidateMarkTO.getSubject8TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8TotalMarks())) {
                        candidateMarks.setSubject8TotalMarks(new BigDecimal(candidateMarkTO.getSubject8TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8ObtainedMarks())) {
                        candidateMarks.setSubject8ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject8ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid8("");
            }
            if (candidateMarkTO.getSubjectid9() != null && !candidateMarkTO.getSubjectid9().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid9(), candidateMarkTO.getSubjectmarkid9(), candidateMarkTO.getSubjectOther9(), candidateMarkTO.getSubject9TotalMarks(), candidateMarkTO.getSubject9ObtainedMarks(), candidateMarkTO.getSubject9Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid9(candidateMarkTO.getSubjectid9());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                        candidateMarks.setSubject9(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                        candidateMarks.setSubject9(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                        candidateMarks.setSubject9(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                        candidateMarks.setSubject9(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
                        candidateMarks.setSubject9(s);
                    }
                    if (candidateMarkTO.getSubjectOther9() != null && !candidateMarkTO.getSubjectOther9().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther9(candidateMarkTO.getSubjectOther9());
                    }
                    if (candidateMarkTO.getSubject9Credit() != null && !candidateMarkTO.getSubject9Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject9Credit(new BigDecimal(candidateMarkTO.getSubject9Credit()));
                    }
                    if (candidateMarkTO.getSubject9TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9TotalMarks())) {
                        candidateMarks.setSubject9TotalMarks(new BigDecimal(candidateMarkTO.getSubject9TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9ObtainedMarks())) {
                        candidateMarks.setSubject9ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject9ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid9("");
            }
            if (candidateMarkTO.getSubjectid10() != null && !candidateMarkTO.getSubjectid10().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid10(), candidateMarkTO.getSubjectmarkid10(), candidateMarkTO.getSubjectOther10(), candidateMarkTO.getSubject10TotalMarks(), candidateMarkTO.getSubject10ObtainedMarks(), candidateMarkTO.getSubject10Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid10(candidateMarkTO.getSubjectid10());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                        candidateMarks.setSubject10(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                        candidateMarks.setSubject10(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                        candidateMarks.setSubject10(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                        candidateMarks.setSubject10(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
                        candidateMarks.setSubject10(s);
                    }
                    if (candidateMarkTO.getSubjectOther10() != null && !candidateMarkTO.getSubjectOther10().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther10(candidateMarkTO.getSubjectOther10());
                    }
                    if (candidateMarkTO.getSubject10Credit() != null && !candidateMarkTO.getSubject10Credit().equalsIgnoreCase("")) {
                        candidateMarks.setSubject10Credit(new BigDecimal(candidateMarkTO.getSubject10Credit()));
                    }
                    if (candidateMarkTO.getSubject10TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10TotalMarks())) {
                        candidateMarks.setSubject10TotalMarks(new BigDecimal(candidateMarkTO.getSubject10TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10ObtainedMarks())) {
                        candidateMarks.setSubject10ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject10ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid10("");
            }
        }
        else {
            if (candidateMarkTO.getSubjectid11() != null && !candidateMarkTO.getSubjectid11().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid11(), candidateMarkTO.getSubjectmarkid11(), candidateMarkTO.getSubjectOther11(), candidateMarkTO.getSubject11TotalMarks(), candidateMarkTO.getSubject11ObtainedMarks(), candidateMarkTO.getSubject11Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid11(candidateMarkTO.getSubjectid11());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                        candidateMarks.setSubject11(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                        candidateMarks.setSubject11(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                        candidateMarks.setSubject11(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                        candidateMarks.setSubject11(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
                        candidateMarks.setSubject11(s);
                    }
                    if (candidateMarkTO.getSubjectOther11() != null && !candidateMarkTO.getSubjectOther11().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther11(candidateMarkTO.getSubjectOther11());
                    }
                    if (candidateMarkTO.getSubject11TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject11TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11TotalMarks())) {
                        candidateMarks.setSubject11TotalMarks(new BigDecimal(candidateMarkTO.getSubject11TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject11ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11ObtainedMarks())) {
                        candidateMarks.setSubject11ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject11ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid11("");
            }
            if (candidateMarkTO.getSubjectid12() != null && !candidateMarkTO.getSubjectid12().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid12(), candidateMarkTO.getSubjectmarkid12(), candidateMarkTO.getSubjectOther12(), candidateMarkTO.getSubject12TotalMarks(), candidateMarkTO.getSubject12ObtainedMarks(), candidateMarkTO.getSubject12Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid12(candidateMarkTO.getSubjectid12());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                        candidateMarks.setSubject12(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                        candidateMarks.setSubject12(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                        candidateMarks.setSubject12(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                        candidateMarks.setSubject12(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
                        candidateMarks.setSubject12(s);
                    }
                    if (candidateMarkTO.getSubjectOther12() != null && !candidateMarkTO.getSubjectOther12().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther12(candidateMarkTO.getSubjectOther12());
                    }
                    if (candidateMarkTO.getSubject12TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject12TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12TotalMarks())) {
                        candidateMarks.setSubject12TotalMarks(new BigDecimal(candidateMarkTO.getSubject12TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject12ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12ObtainedMarks())) {
                        candidateMarks.setSubject12ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject12ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid12("");
            }
            if (candidateMarkTO.getSubjectid13() != null && !candidateMarkTO.getSubjectid13().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid13(), candidateMarkTO.getSubjectmarkid13(), candidateMarkTO.getSubjectOther13(), candidateMarkTO.getSubject13TotalMarks(), candidateMarkTO.getSubject13ObtainedMarks(), candidateMarkTO.getSubject13Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid13(candidateMarkTO.getSubjectid13());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                        candidateMarks.setSubject13(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                        candidateMarks.setSubject13(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                        candidateMarks.setSubject13(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                        candidateMarks.setSubject13(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
                        candidateMarks.setSubject13(s);
                    }
                    if (candidateMarkTO.getSubjectOther13() != null && !candidateMarkTO.getSubjectOther13().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther13(candidateMarkTO.getSubjectOther13());
                    }
                    if (candidateMarkTO.getSubject13TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject13TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13TotalMarks())) {
                        candidateMarks.setSubject13TotalMarks(new BigDecimal(candidateMarkTO.getSubject13TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject13ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13ObtainedMarks())) {
                        candidateMarks.setSubject13ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject13ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid13("");
            }
            if (candidateMarkTO.getSubjectid14() != null && !candidateMarkTO.getSubjectid14().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid14(), candidateMarkTO.getSubjectmarkid14(), candidateMarkTO.getSubjectOther14(), candidateMarkTO.getSubject14TotalMarks(), candidateMarkTO.getSubject14ObtainedMarks(), candidateMarkTO.getSubject14Credit(), admForm);
                candidateMarks.setSubjectid14(candidateMarkTO.getSubjectid14());
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                        candidateMarks.setSubject14(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                        candidateMarks.setSubject14(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                        candidateMarks.setSubject14(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                        candidateMarks.setSubject14(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
                        candidateMarks.setSubject14(s);
                    }
                    if (candidateMarkTO.getSubjectOther14() != null && !candidateMarkTO.getSubjectOther14().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther14(candidateMarkTO.getSubjectOther14());
                    }
                    if (candidateMarkTO.getSubject14TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject14TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14TotalMarks())) {
                        candidateMarks.setSubject14TotalMarks(new BigDecimal(candidateMarkTO.getSubject14TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject14ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14ObtainedMarks())) {
                        candidateMarks.setSubject14ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject14ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid14("");
            }
            if (candidateMarkTO.getSubjectid15() != null && !candidateMarkTO.getSubjectid15().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid15(), candidateMarkTO.getSubjectmarkid15(), candidateMarkTO.getSubjectOther15(), candidateMarkTO.getSubject15TotalMarks(), candidateMarkTO.getSubject15ObtainedMarks(), candidateMarkTO.getSubject15Credit(), admForm);
                candidateMarks.setSubjectid15(candidateMarkTO.getSubjectid15());
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                        candidateMarks.setSubject15(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                        candidateMarks.setSubject15(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                        candidateMarks.setSubject15(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                        candidateMarks.setSubject15(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
                        candidateMarks.setSubject15(s);
                    }
                    if (candidateMarkTO.getSubjectOther15() != null && !candidateMarkTO.getSubjectOther15().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther15(candidateMarkTO.getSubjectOther15());
                    }
                    if (candidateMarkTO.getSubject15TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject15TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15TotalMarks())) {
                        candidateMarks.setSubject15TotalMarks(new BigDecimal(candidateMarkTO.getSubject15TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject15ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15ObtainedMarks())) {
                        candidateMarks.setSubject15ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject15ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid15("");
            }
            if (candidateMarkTO.getSubjectid16() != null && !candidateMarkTO.getSubjectid16().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid16(), candidateMarkTO.getSubjectmarkid16(), candidateMarkTO.getSubjectOther16(), candidateMarkTO.getSubject16TotalMarks(), candidateMarkTO.getSubject16ObtainedMarks(), candidateMarkTO.getSubject16Credit(), admForm);
                candidateMarks.setSubjectid16(candidateMarkTO.getSubjectid16());
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                        candidateMarks.setSubject16(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                        candidateMarks.setSubject16(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                        candidateMarks.setSubject16(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                        candidateMarks.setSubject16(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
                        candidateMarks.setSubject16(s);
                    }
                    if (candidateMarkTO.getSubjectOther16() != null && !candidateMarkTO.getSubjectOther16().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther16(candidateMarkTO.getSubjectOther16());
                    }
                    if (candidateMarkTO.getSubject16TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject16TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16TotalMarks())) {
                        candidateMarks.setSubject16TotalMarks(new BigDecimal(candidateMarkTO.getSubject16TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject16ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16ObtainedMarks())) {
                        candidateMarks.setSubject16ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject16ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid16("");
            }
            if (candidateMarkTO.getSubjectid17() != null && !candidateMarkTO.getSubjectid17().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid17(), candidateMarkTO.getSubjectmarkid17(), candidateMarkTO.getSubjectOther17(), candidateMarkTO.getSubject17TotalMarks(), candidateMarkTO.getSubject17ObtainedMarks(), candidateMarkTO.getSubject17Credit(), admForm);
                candidateMarks.setSubjectid17(candidateMarkTO.getSubjectid17());
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                        candidateMarks.setSubject17(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                        candidateMarks.setSubject17(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                        candidateMarks.setSubject17(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                        candidateMarks.setSubject17(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
                        candidateMarks.setSubject17(s);
                    }
                    if (candidateMarkTO.getSubjectOther17() != null && !candidateMarkTO.getSubjectOther17().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther17(candidateMarkTO.getSubjectOther17());
                    }
                    if (candidateMarkTO.getSubject17TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject17TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17TotalMarks())) {
                        candidateMarks.setSubject17TotalMarks(new BigDecimal(candidateMarkTO.getSubject17TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject17ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17ObtainedMarks())) {
                        candidateMarks.setSubject17ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject17ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid17("");
            }
            if (candidateMarkTO.getSubjectid18() != null && !candidateMarkTO.getSubjectid18().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid18(), candidateMarkTO.getSubjectmarkid18(), candidateMarkTO.getSubjectOther18(), candidateMarkTO.getSubject18TotalMarks(), candidateMarkTO.getSubject18ObtainedMarks(), candidateMarkTO.getSubject18Credit(), admForm);
                candidateMarks.setSubjectid18(candidateMarkTO.getSubjectid18());
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                        candidateMarks.setSubject18(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                        candidateMarks.setSubject18(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                        candidateMarks.setSubject18(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                        candidateMarks.setSubject18(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
                        candidateMarks.setSubject18(s);
                    }
                    if (candidateMarkTO.getSubjectOther18() != null && !candidateMarkTO.getSubjectOther18().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther18(candidateMarkTO.getSubjectOther18());
                    }
                    if (candidateMarkTO.getSubject18TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject18TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18TotalMarks())) {
                        candidateMarks.setSubject18TotalMarks(new BigDecimal(candidateMarkTO.getSubject18TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject18ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18ObtainedMarks())) {
                        candidateMarks.setSubject18ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject18ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid18("");
            }
            if (candidateMarkTO.getSubjectid19() != null && !candidateMarkTO.getSubjectid19().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid19(), candidateMarkTO.getSubjectmarkid19(), candidateMarkTO.getSubjectOther19(), candidateMarkTO.getSubject19TotalMarks(), candidateMarkTO.getSubject19ObtainedMarks(), candidateMarkTO.getSubject19Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid19(candidateMarkTO.getSubjectid19());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                        candidateMarks.setSubject19(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                        candidateMarks.setSubject19(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                        candidateMarks.setSubject19(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                        candidateMarks.setSubject19(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
                        candidateMarks.setSubject19(s);
                    }
                    if (candidateMarkTO.getSubjectOther19() != null && !candidateMarkTO.getSubjectOther19().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther19(candidateMarkTO.getSubjectOther19());
                    }
                    if (candidateMarkTO.getSubject19TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject19TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19TotalMarks())) {
                        candidateMarks.setSubject19TotalMarks(new BigDecimal(candidateMarkTO.getSubject19TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject19ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19ObtainedMarks())) {
                        candidateMarks.setSubject19ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject19ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid19("");
            }
            if (candidateMarkTO.getSubjectid20() != null && !candidateMarkTO.getSubjectid20().equalsIgnoreCase("")) {
                final AdmSubjectMarkForRank admSubjectMarkForRank = this.getAdmSubjectMarkForRank(docName, candidateMarkTO, candidateMarkTO.getSubjectid20(), candidateMarkTO.getSubjectmarkid20(), candidateMarkTO.getSubjectOther20(), candidateMarkTO.getSubject20TotalMarks(), candidateMarkTO.getSubject20ObtainedMarks(), candidateMarkTO.getSubject20Credit(), admForm);
                if (admSubjectMarkForRank != null) {
                    submarksSet.add(admSubjectMarkForRank);
                    candidateMarks.setSubjectid20(candidateMarkTO.getSubjectid20());
                    if (admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                        final String s = admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                        candidateMarks.setSubject20(s);
                    }
                    else if (admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                        final String s = admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                        candidateMarks.setSubject20(s);
                    }
                    else if (admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                        final String s = admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                        candidateMarks.setSubject20(s);
                    }
                    else if (admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                        final String s = admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                        candidateMarks.setSubject20(s);
                    }
                    else if (admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))) {
                        final String s = admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
                        candidateMarks.setSubject20(s);
                    }
                    if (candidateMarkTO.getSubjectOther20() != null && !candidateMarkTO.getSubjectOther20().equalsIgnoreCase("")) {
                        candidateMarks.setSubjectOther20(candidateMarkTO.getSubjectOther20());
                    }
                    if (candidateMarkTO.getSubject20TotalMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject20TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20TotalMarks())) {
                        candidateMarks.setSubject20TotalMarks(new BigDecimal(candidateMarkTO.getSubject20TotalMarks()));
                    }
                    if (candidateMarkTO.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(candidateMarkTO.getSubject20ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20ObtainedMarks())) {
                        candidateMarks.setSubject20ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject20ObtainedMarks()));
                    }
                }
                else {
                    ++submarkCount;
                }
            }
            else {
                ++submarkCount;
                candidateMarkTO.setSubjectid20("");
            }
        }
        if (submarkCount != 10) {
            ednQualification.setAdmSubjectMarkForRank((Set)submarksSet);
        }
        ednQualification.setUgPattern(admForm.getPatternofStudy());
    }
}