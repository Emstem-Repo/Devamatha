package com.kp.cms.helpers.admission;

import com.kp.cms.bo.admin.Organisation;
import javax.servlet.http.HttpSession;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.utilities.NumberToWordConvertor;
import java.util.StringTokenizer;
import java.math.BigInteger;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import java.util.TreeSet;
import java.util.Collections;
import java.util.HashSet;
import com.kp.cms.handlers.admission.TransferCertificateHandler;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.CharacterAndConduct;
import java.util.Date;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import java.util.Set;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.transactionsimpl.admission.TransferCertificateTransactionImpl;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.to.admission.TCDetailsTO;
import java.util.Iterator;
import com.kp.cms.transactions.admission.ITCDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.TCDetailsTransactionImpl;
import java.util.ArrayList;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.bo.admin.Student;
import java.util.List;
import com.kp.cms.forms.admission.TCDetailsForm;

public class TCDetailsHelper
{
    private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
    private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
    private static volatile TCDetailsHelper tCDetailsHelper;
    
    static {
        TCDetailsHelper.tCDetailsHelper = null;
    }
    
    private TCDetailsHelper() {
    }
    
    public static TCDetailsHelper getInstance() {
        if (TCDetailsHelper.tCDetailsHelper == null) {
            TCDetailsHelper.tCDetailsHelper = new TCDetailsHelper();
        }
        return TCDetailsHelper.tCDetailsHelper;
    }
    
    public String monthInWords(final String month) {
        if (month.equalsIgnoreCase("1")) {
            return "January";
        }
        if (month.equalsIgnoreCase("2")) {
            return "February";
        }
        if (month.equalsIgnoreCase("3")) {
            return "March";
        }
        if (month.equalsIgnoreCase("4")) {
            return "April";
        }
        if (month.equalsIgnoreCase("5")) {
            return "May";
        }
        if (month.equalsIgnoreCase("6")) {
            return "June";
        }
        if (month.equalsIgnoreCase("7")) {
            return "July";
        }
        if (month.equalsIgnoreCase("8")) {
            return "August";
        }
        if (month.equalsIgnoreCase("9")) {
            return "September";
        }
        if (month.equalsIgnoreCase("10")) {
            return "October";
        }
        if (month.equalsIgnoreCase("11")) {
            return "November";
        }
        if (month.equalsIgnoreCase("12")) {
            return "December";
        }
        return month;
    }
    
    public String getSearchQuery(final TCDetailsForm tcDetailsForm) throws Exception {
        String query = "from Student s where  s.admAppln.isCancelled=0  and s.isActive=1 and s.classSchemewise.id=" + tcDetailsForm.getClassId();
        if (tcDetailsForm.getRegisterNo() != null && !tcDetailsForm.getRegisterNo().isEmpty()) {
            query = String.valueOf(query) + " and s.registerNo='" + tcDetailsForm.getRegisterNo() + "'";
        }
        return String.valueOf(query) + " order by s.admAppln.personalData.firstName";
    }
    
    public List<BoardDetailsTO> convertBotoToList(final List<Student> studentList) throws Exception {
        final List<BoardDetailsTO> boardDetailsTOs = new ArrayList<BoardDetailsTO>();
        final ITCDetailsTransaction tcTransaction = TCDetailsTransactionImpl.getInstance();
        if (studentList != null && !studentList.isEmpty()) {
            for (final Student student : studentList) {
                final BoardDetailsTO boardDetailsTO = new BoardDetailsTO();
                boardDetailsTO.setRollNo(student.getRollNo());
                boardDetailsTO.setStudentId(student.getId());
                boardDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
                final boolean showPrint = tcTransaction.checkTcAvailable(student.getId());
                boardDetailsTO.setShowPrint(showPrint);
                boardDetailsTOs.add(boardDetailsTO);
            }
        }
        return boardDetailsTOs;
    }
    
    public String getSearchQueryForTCDetails(final TCDetailsForm tcDetailsForm) throws Exception {
        final String query = " from Student s where s.id=" + tcDetailsForm.getStudentId();
        return query;
    }
    
    public void convertBotoForm(final Student student, final TCDetailsForm tcDetailsForm) throws Exception {
        final TCDetailsTO to = new TCDetailsTO();
        to.setRegisterNo(student.getRegisterNo());
        to.setStudentId(student.getId());
        to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
        to.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
        to.setAdmissionNo(student.getAdmAppln().getAdmissionNumber());
        if (student.getAdmAppln().getAdmissionDate() != null) {
            to.setAdmissionDate(CommonUtil.formatDate(student.getAdmAppln().getAdmissionDate(), "dd/MM/yyyy"));
        }
        final Set<StudentTCDetails> tcDetails = (Set<StudentTCDetails>)student.getStudentTCDetails();
        if (!tcDetails.isEmpty()) {
            final Iterator<StudentTCDetails> it = tcDetails.iterator();
            if (it.hasNext()) {
                final StudentTCDetails tcDetail = it.next();
                to.setTcNo(tcDetail.getTcNo());
                to.setAdmissionNo(tcDetail.getAdmissionNo());
            }
        }
        final ITransferCertificateTransaction transferCertificate = (ITransferCertificateTransaction)new TransferCertificateTransactionImpl();
        final int termNumber = transferCertificate.getClassTermNumber(Integer.parseInt(tcDetailsForm.getClassId()));
        final String semNo = DownloadHallTicketHelper.semMap.get(String.valueOf(termNumber));
        final String className = student.getAdmAppln().getCourseBySelectedCourseId().getName();
        to.setClassOfLeaving(String.valueOf(className) + " " + semNo + " Semester");
        to.setClassId(student.getClassSchemewise().getClasses().getId());
        final String subjects = TCDetailsTransactionImpl.getInstance().getSubjectsForAllStudentsByClass(tcDetailsForm);
        final List<CurriculumSchemeDuration> curriculumSchemeDuration = TCDetailsTransactionImpl.getInstance().getCurriculumSchemeDuration(tcDetailsForm.getClassId(), student.getId());
        to.setSubjectPassed(subjects);
        if (curriculumSchemeDuration != null && !curriculumSchemeDuration.isEmpty()) {
            final Iterator<CurriculumSchemeDuration> itr = curriculumSchemeDuration.iterator();
            while (itr.hasNext()) {
                CurriculumSchemeDuration csd = new CurriculumSchemeDuration();
                csd = itr.next();
                final ExamDefinitionBO examName = TCDetailsTransactionImpl.getInstance().getStudentExamName(student.getId(), csd);
                if (examName != null) {
                    to.setPublicExamName(examName.getName());
                    final String month = this.monthInWords(examName.getMonth());
                    to.setExamMonth(month);
                    to.setExamYear(examName.getYear());
                    break;
                }
                to.setPublicExamName("");
            }
        }
        if (student.getStudentTCDetails() != null && !student.getStudentTCDetails().isEmpty()) {
            final Iterator<StudentTCDetails> itr2 = student.getStudentTCDetails().iterator();
            if (itr2.hasNext()) {
                final StudentTCDetails bo = itr2.next();
                to.setId(bo.getId());
                to.setFeePaid(bo.getFeePaid());
                to.setScholarship(bo.getScholarship());
                to.setPassed(bo.getPassed());
                to.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateOfApplication()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                to.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateOfLeaving()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                to.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateOfIssue()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                to.setReasonOfLeaving(bo.getReasonOfLeaving());
                if (bo.getCharacterAndConduct() != null) {
                    to.setCharacterId(String.valueOf(bo.getCharacterAndConduct().getId()));
                }
                to.setMonth(bo.getMonth());
                to.setYear(String.valueOf(bo.getYear()));
                to.setSubjectPassed(bo.getSubjectPassed());
                to.setPublicExamName(bo.getPublicExaminationName());
                to.setShowRegisterNo(bo.getShowRegNo());
                to.setClassOfLeaving(bo.getClassOfLeaving());
                if (bo.getClasssubjectOfJoining() != null) {
                    to.setClasssubjectOfJoining(bo.getClasssubjectOfJoining());
                }
                if (bo.getPromotionToNextClass() != null) {
                    to.setPromotionToNextClass(bo.getPromotionToNextClass());
                }
                if (bo.getExamMonth() != null) {
                    to.setExamMonth(bo.getExamMonth());
                }
                if (bo.getExamYear() != null) {
                    to.setExamYear(bo.getExamYear());
                }
                to.setClassId(bo.getStudent().getClassSchemewise().getClasses().getId());
                to.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
                final String dob = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy");
                to.setDateOfBirth(dob);
                to.setClassId(bo.getClasses().getId());
            }
        }
        else {
            to.setFeePaid("yes");
            to.setPassed("yes");
            to.setScholarship("no");
            to.setShowRegisterNo("yes");
            to.setYear(tcDetailsForm.getYear());
            to.setMonth(tcDetailsForm.getMonth());
        }
        tcDetailsForm.setTcDetailsTO(to);
    }
    
    public StudentTCDetails convertTotoBo(final TCDetailsForm tcDetailsForm) throws Exception {
        final TCDetailsTO to = tcDetailsForm.getTcDetailsTO();
        final StudentTCDetails bo = new StudentTCDetails();
        if (to != null) {
            if (to.getId() > 0) {
                bo.setId(to.getId());
            }
            else {
                bo.setCreatedBy(tcDetailsForm.getUserId());
                bo.setCreatedDate(new Date());
            }
            bo.setModifiedBy(tcDetailsForm.getUserId());
            bo.setLastModifiedDate(new Date());
            bo.setPassed(to.getPassed());
            bo.setFeePaid(to.getFeePaid());
            bo.setScholarship(to.getScholarship());
            bo.setReasonOfLeaving(to.getReasonOfLeaving());
            bo.setIsActive(Boolean.valueOf(true));
            bo.setDateOfApplication((Date)CommonUtil.ConvertStringToSQLDate(to.getDateOfApplication()));
            bo.setDateOfLeaving((Date)CommonUtil.ConvertStringToSQLDate(to.getDateOfLeaving()));
            bo.setDateOfIssue((Date)CommonUtil.ConvertStringToSQLDate(to.getDateOfIssue()));
            final Student student = new Student();
            student.setId(to.getStudentId());
            bo.setStudent(student);
            if (to.getCharacterId() != null && !to.getCharacterId().isEmpty()) {
                final CharacterAndConduct conduct = new CharacterAndConduct();
                conduct.setId(Integer.parseInt(to.getCharacterId()));
                bo.setCharacterAndConduct(conduct);
            }
            bo.setMonth(to.getMonth());
            bo.setYear(Integer.valueOf(Integer.parseInt(to.getYear())));
            bo.setSubjectPassed(to.getSubjectPassed());
            bo.setPublicExaminationName(to.getPublicExamName());
            bo.setShowRegNo(to.getShowRegisterNo());
            bo.setClassOfLeaving(to.getClassOfLeaving());
            if (to.getClasssubjectOfJoining() != null) {
                bo.setClasssubjectOfJoining(to.getClasssubjectOfJoining());
            }
            if (to.getPromotionToNextClass() != null) {
                bo.setPromotionToNextClass(to.getPromotionToNextClass());
            }
            if (to.getExamMonth() != null) {
                final String month = this.monthInWords(to.getExamMonth());
                bo.setExamMonth(month);
            }
            if (to.getExamYear() != null) {
                bo.setExamYear(to.getExamYear());
            }
            bo.setStudentName(to.getStudentName());
            bo.setDateOfBirth((Date)CommonUtil.ConvertStringToSQLDate(to.getDateOfBirth()));
            final Classes cls = new Classes();
            cls.setId((int)to.getClassId());
            bo.setClasses(cls);
            if (to.getTcNo() != null && !to.getTcNo().isEmpty()) {
                bo.setTcNo(to.getTcNo());
            }
            if (to.getDateOfLeaving() != null) {
                bo.setTcDate((Date)CommonUtil.ConvertStringToSQLDate(to.getDateOfLeaving()));
            }
            bo.setAdmissionNo(to.getAdmissionNo());
            bo.setAdmissionDate(to.getAdmissionDate());
        }
        return bo;
    }
    
    public List<CharacterAndConductTO> convertBoListToTOList(final List<CharacterAndConduct> list) throws Exception {
        final List<CharacterAndConductTO> finalList = new ArrayList<CharacterAndConductTO>();
        if (list != null && !list.isEmpty()) {
            for (final CharacterAndConduct bo : list) {
                final CharacterAndConductTO to = new CharacterAndConductTO();
                to.setId(bo.getId());
                to.setName(bo.getName());
                finalList.add(to);
            }
        }
        return finalList;
    }
    
    public void convertBotoFormAllStudentsByClass(final String subjects, final ExamDefinitionBO bo, final TCDetailsForm tcDetailsForm,StudentTCDetails tcDetails) throws NumberFormatException, Exception {
        final TCDetailsTO to = new TCDetailsTO();
        if (bo != null) {
            final String month = this.monthInWords(bo.getMonth());
            to.setExamMonth(month);
            to.setExamYear(bo.getYear());
            tcDetailsForm.setPublicExamName(bo.getName());
        }
        to.setClassOfLeaving(tcDetailsForm.getClassName());
        to.setSubjectPassed(subjects);
        final ITransferCertificateTransaction transferCertificate = (ITransferCertificateTransaction)new TransferCertificateTransactionImpl();
        final int termNumber = transferCertificate.getClassTermNumber(Integer.parseInt(tcDetailsForm.getClassId()));
        final String semNo = DownloadHallTicketHelper.semMap.get(String.valueOf(termNumber));
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final Classes classes = transaction.getClasses(tcDetailsForm);
        to.setClassOfLeaving(String.valueOf(classes.getCourse().getName()) + " " + semNo + " Semester");
        to.setClassId(Integer.parseInt(tcDetailsForm.getClassId()));

        if (tcDetails != null) {
        	tcDetailsForm.setFeePaid(tcDetails.getFeePaid());
        	tcDetailsForm.setScholarship(tcDetails.getScholarship());
        	tcDetailsForm.setPassed(tcDetails.getPassed());
        	tcDetailsForm.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfApplication()), "dd-MMM-yyyy", "dd/MM/yyyy"));
        	tcDetailsForm.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfLeaving()), "dd-MMM-yyyy", "dd/MM/yyyy"));
        	tcDetailsForm.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfIssue()), "dd-MMM-yyyy", "dd/MM/yyyy"));
        	tcDetailsForm.setReasonOfLeaving(tcDetails.getReasonOfLeaving());
            if (tcDetails.getCharacterAndConduct() != null) {
            	tcDetailsForm.setCharacterId(String.valueOf(tcDetails.getCharacterAndConduct().getId()));
            }
            tcDetailsForm.setMonth(tcDetails.getMonth());
            tcDetailsForm.setYear(String.valueOf(tcDetails.getYear()));
            tcDetailsForm.setPublicExamName(tcDetails.getPublicExaminationName());
            tcDetailsForm.setShowRegisterNo(tcDetails.getShowRegNo());
            if (tcDetails.getClasssubjectOfJoining() != null) {
            	tcDetails.setClasssubjectOfJoining(tcDetails.getClasssubjectOfJoining());
            }
    }
        
        
        tcDetailsForm.setTcDetailsTO(to);
    }
    
    public boolean checkForExistingTCNumbersInOtherClasses(final String prefix, int startingNumber, final List<Student> studentOfThatClass,TCDetailsForm tcDetailsForm) throws Exception {
        final StringBuffer tcNumbersToBeVerified = new StringBuffer();
        final String accr = String.valueOf(tcDetailsForm.getAcademicYear()).substring(2);
        final String finAccyr = String.valueOf(accr) + "-" + (Integer.parseInt(accr) + 1);
        for (int i = 0; i < studentOfThatClass.size(); ++i) {
            tcNumbersToBeVerified.append("'" + prefix + startingNumber++ +"/"+ finAccyr + "',");
        }
        //tcNumbersToBeVerified.deleteCharAt(tcNumbersToBeVerified.length()-1);
        final String sqlQuery = "select student.id as sid from stu_tc_details stutc inner join student student ON stutc.student_id = student.id inner join class_schemewise class_schemewise ON student.class_schemewise_id = class_schemewise.id inner join classes classes ON class_schemewise.class_id = classes.id where (stutc.tc_no in (" + tcNumbersToBeVerified.toString().substring(0, tcNumbersToBeVerified.length() - 1) + ") " + "|| student.tc_no in " + "(" + tcNumbersToBeVerified.toString().substring(0, tcNumbersToBeVerified.length() - 1) + ")) " + " and classes.id <> " + studentOfThatClass.get(0).getClassSchemewise().getClasses().getId();
        final ITCDetailsTransaction tx = TCDetailsTransactionImpl.getInstance();
        
      /*  final String query="select tc.tcNo from StudentTCDetails tc where tc.tcNo not in("+tcNumbersToBeVerified+")";
        return tx.verifyGeneratedTCNumbers(query);*/
      return tx.verifyGeneratedTCNumbers(sqlQuery);
    }
    
    public boolean checkForExistingTCNumbersInOtherClasses(final TCDetailsForm tcDetailsForm) throws Exception {
        final String sqlQuery = "select student.id as sid from stu_tc_details stutc inner join student student ON stutc.student_id = student.id where ((stutc.tc_no like '" + tcDetailsForm.getTcDetailsTO().getTcNo() + "') || (student.tc_no like '" + tcDetailsForm.getTcDetailsTO().getTcNo() + "'))" + " and student.id <> " + tcDetailsForm.getStudentId();
        final ITCDetailsTransaction tx = TCDetailsTransactionImpl.getInstance();
        return tx.verifyGeneratedTCNumbers(sqlQuery);
    }
    
    public List<PrintTcDetailsTo> convertStudentBoToToOnlyTC(final Student student, final List<Student> studentsGotTcList, final HttpServletRequest request, final TCDetailsForm form) throws Exception {
        final Student student2 = new Student();
        final List<PrintTcDetailsTo> studentToList = new ArrayList<PrintTcDetailsTo>();
        if (student != null) {
            final Integer admittedSem = TransferCertificateHandler.getInstance().getAdmittedSemester(student.getId());
            final BigInteger rejoinYear = TransferCertificateHandler.getInstance().getRejoinYear(student.getId());
            if (form.getDuplicate().equalsIgnoreCase("yes") && student.getTcNo() != null) {
                final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                if (student.getStudentTCDetails().size() != 0) {
                    detailsTo.setStudentNo(student.getStudentNo());
                    detailsTo.setRegNo(student.getRegisterNo());
                    detailsTo.setCourse(student.getAdmAppln().getCourseBySelectedCourseId().getName());
                    String schemeInWords = this.getSchemeinWords(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName(), student.getClassSchemewise().getClasses().getTermNumber());
                    String className = "";
                    if (schemeInWords != null) {
                        className = schemeInWords.toUpperCase();
                    }
                    if (student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        className = String.valueOf(className) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                    }
                    detailsTo.setClassName(className);
                    final String courseScheme = TransferCertificateHandler.getInstance().getCourseScheme(student.getAdmAppln().getCourseBySelectedCourseId().getId(), student.getAdmAppln().getAppliedYear());
                    schemeInWords = this.getSchemeinWords(courseScheme, admittedSem);
                    className = "";
                    if (schemeInWords != null) {
                        className = schemeInWords.toUpperCase();
                    }
                    if (student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("St Berchmans")) {
                            if (student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 3 || student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 9) {
                                className = student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                                form.setFlag(true);
                            }
                            else {
                                className = String.valueOf(className) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                            }
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                            className = String.valueOf(className) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                        }
                    }
                    detailsTo.setAdmissionClass(className);
                    if (student.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (form.getTcDate() != null && !form.getTcDate().isEmpty()) {
                        student.setTcDate(CommonUtil.ConvertStringToDate(form.getTcDate()));
                    }
                    else {
                        student.setTcDate(new Date());
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    if (rejoinYear != null) {
                        detailsTo.setAdmittedYear(String.valueOf(student.getRegisterNo()) + "/" + rejoinYear + "-" + (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    else {
                        detailsTo.setAdmittedYear(String.valueOf(student.getRegisterNo()) + "/" + student.getAdmAppln().getAppliedYear().toString() + "-" + (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    Set<StudentTCDetails> tcDetailSet = (Set<StudentTCDetails>)student.getStudentTCDetails();
                    final Iterator<StudentTCDetails> itr = tcDetailSet.iterator();
                    StudentTCDetails tcDetails = null;
                    while (itr.hasNext()) {
                        tcDetails = itr.next();
                        detailsTo.setStudentName(tcDetails.getStudentName());
                        detailsTo.setDobFigures(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd-MM-yyyy"));
                        final String dobInWords1 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        detailsTo.setDobWords(dobInWords1);
                        detailsTo.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfLeaving()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        detailsTo.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfLeaving()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        detailsTo.setLeavingYear(detailsTo.getDateOfLeaving().substring(6));
                        if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("no")) {
                            detailsTo.setPassed(tcDetails.getSubjectPassed());
                        }
                        else if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("yes")) {
                            detailsTo.setPassed("YES");
                        }
                        if (tcDetails.getFeePaid() != null && tcDetails.getFeePaid().equalsIgnoreCase("yes")) {
                            detailsTo.setFeePaid("YES");
                        }
                        else if (tcDetails.getFeePaid() != null && tcDetails.getFeePaid().equalsIgnoreCase("no")) {
                            detailsTo.setFeePaid("NO");
                        }
                        if (tcDetails.getCharacterAndConduct() != null) {
                            detailsTo.setConduct(tcDetails.getCharacterAndConduct().getName());
                        }
                        detailsTo.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfApplication()), "dd-MMM-yyyy", "dd-MM-yyyy"));
                        if (tcDetails.getDateOfIssue() != null) {
                            detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        }
                        String examRegNo = "";
                        if (student.getExamRegisterNo() != null && !student.getExamRegisterNo().trim().isEmpty()) {
                            examRegNo = student.getExamRegisterNo();
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Christ")) {
                            examRegNo = student.getRegisterNo();
                        }
                        if (form.getTcType() != null && form.getTcType().equalsIgnoreCase("Discontinued")) {
                            detailsTo.setRegMonthYear("---------");
                        }
                        else {
                            detailsTo.setRegMonthYear(examRegNo);
                        }
                        if (tcDetails.getScholarship() != null && tcDetails.getScholarship().equals("yes")) {
                            detailsTo.setScholarship("YES");
                        }
                        else if (tcDetails.getScholarship() != null && tcDetails.getScholarship().equals("no")) {
                            detailsTo.setScholarship("NO");
                        }
                        if (tcDetails.getSubjectPassed() != null) {
                            detailsTo.setSubjectsStudied(tcDetails.getSubjectPassed());
                        }
                        detailsTo.setReason(tcDetails.getReasonOfLeaving());
                        if (tcDetails.getEligible() != null) {
                            detailsTo.setEligible(tcDetails.getEligible());
                        }
                        if (tcDetails.getPromotionToNextClass() != null) {
                            detailsTo.setPromotionToNextClass(tcDetails.getPromotionToNextClass());
                        }
                        if (student2.getRollNo() != null) {
                            detailsTo.setRollNo(student2.getRollNo());
                        }
                        if (tcDetails.getYear() != null) {
                            detailsTo.setExamyear((int)tcDetails.getYear());
                        }
                        if (tcDetails.getExamMonth() != null) {
                            detailsTo.setExammonth(tcDetails.getExamMonth());
                        }
                        if (tcDetails.getExamYear() != null) {
                            detailsTo.setExamyear((int)tcDetails.getExamYear());
                        }
                        if (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null) {
                            final Integer year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
                            final String yr = String.valueOf(year);
                            detailsTo.setAcademicYear(yr);
                        }
                        if (tcDetails.getIsFeePaidUni() != null && tcDetails.getIsFeePaidUni()) {
                            detailsTo.setFeeDueToUni("YES");
                        }
                        else {
                            detailsTo.setFeeDueToUni("NO");
                        }
                        if (tcDetails.getClassOfLeaving() != null && !tcDetails.getClassOfLeaving().isEmpty()) {
                            detailsTo.setClassOfLeaving(tcDetails.getClassOfLeaving());
                        }
                        if (tcDetails.getPublicExaminationName() != null) {
                            detailsTo.setPublicExamName(tcDetails.getPublicExaminationName());
                        }
                        if (tcDetails.getTcNo() != null && !tcDetails.getTcNo().isEmpty()) {
                            detailsTo.setTcNo(tcDetails.getTcNo());
                            student.setTcNo(tcDetails.getTcNo());
                        }
                        if (tcDetails.getDateOfLeaving() != null) {
                            student.setTcDate(tcDetails.getDateOfLeaving());
                        }
                        if (tcDetails.getAdmissionNo() != null) {
                            detailsTo.setAdmissionnumber(tcDetails.getAdmissionNo());
                        }
                    }
                    tcDetailSet = new HashSet<StudentTCDetails>();
                    tcDetailSet.add(tcDetails);
                    student.setStudentTCDetails((Set)tcDetailSet);
                    final StringBuffer detainedRegNos = new StringBuffer();
                    final Set<String> detainedRegNoSet = new TreeSet<String>(Collections.reverseOrder());
                    final Set<ExamStudentDetentionRejoinDetails> detentionRejoinSet = (Set<ExamStudentDetentionRejoinDetails>)student.getExamStudentDetentionRejoinDetails();
                    for (final ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails : detentionRejoinSet) {
                        if (examStudentDetentionRejoinDetails.getRejoin() != null && examStudentDetentionRejoinDetails.getRejoin()) {
                            detainedRegNoSet.add(examStudentDetentionRejoinDetails.getRegisterNo());
                        }
                    }
                    if (!detainedRegNoSet.isEmpty()) {
                        for (final String string : detainedRegNoSet) {
                            if (detainedRegNos.length() > 0) {
                                detainedRegNos.append(", " + string);
                            }
                            else {
                                detainedRegNos.append(string);
                            }
                        }
                    }
                    if (detainedRegNos.length() > 0) {
                        detailsTo.setDetainedRegNos(detainedRegNos.toString());
                    }
                    if (!student.getAdmAppln().getIsCancelled() && student.getIsAdmitted()) {
                        studentsGotTcList.add(student);
                        studentToList.add(detailsTo);
                    }
                }
            }
            else if (form.getDuplicate().equalsIgnoreCase("no")) {
                final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                if (student.getStudentTCDetails().size() != 0) {
                    detailsTo.setStudentNo(student.getStudentNo());
                    detailsTo.setRegNo(student.getRegisterNo());
                    detailsTo.setCourse(student.getAdmAppln().getCourseBySelectedCourseId().getName());
                    final String dobInWords2 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    detailsTo.setDobWords(dobInWords2);
                    String schemeInWords2 = this.getSchemeinWords(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName(), student.getClassSchemewise().getClasses().getTermNumber());
                    String className2 = "";
                    if (schemeInWords2 != null) {
                        className2 = schemeInWords2;
                    }
                    if (student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        className2 = String.valueOf(className2) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                    }
                    detailsTo.setClassName(className2);
                    final String courseScheme2 = TransferCertificateHandler.getInstance().getCourseScheme(student.getAdmAppln().getCourseBySelectedCourseId().getId(), student.getAdmAppln().getAppliedYear());
                    schemeInWords2 = this.getSchemeinWords(courseScheme2, admittedSem);
                    className2 = "";
                    if (schemeInWords2 != null) {
                        className2 = schemeInWords2;
                    }
                    if (student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("St Josephs")) {
                            if (student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 3 || student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 9) {
                                className2 = student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                                form.setFlag(true);
                            }
                            else {
                                className2 = String.valueOf(className2) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                            }
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                            className2 = String.valueOf(className2) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                        }
                    }
                    detailsTo.setAdmissionClass(className2);
                    if (student.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (form.getTcDate() != null && !form.getTcDate().isEmpty()) {
                        student.setTcDate(CommonUtil.ConvertStringToDate(form.getTcDate()));
                    }
                    else {
                        student.setTcDate(new Date());
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    if (rejoinYear != null) {
                        detailsTo.setAdmittedYear(String.valueOf(student.getRegisterNo()) + "/" + rejoinYear + "-" + (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    else {
                        detailsTo.setAdmittedYear(String.valueOf(student.getRegisterNo()) + "/" + student.getAdmAppln().getAppliedYear().toString() + "-" + (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    Set<StudentTCDetails> tcDetailSet2 = (Set<StudentTCDetails>)student.getStudentTCDetails();
                    final Iterator<StudentTCDetails> itr2 = tcDetailSet2.iterator();
                    StudentTCDetails tcDetails2 = null;
                    while (itr2.hasNext()) {
                        tcDetails2 = itr2.next();
                        detailsTo.setStudentName(tcDetails2.getStudentName());
                        detailsTo.setDobFigures(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails2.getDateOfBirth()), "dd-MMM-yyyy", "dd-MM-yyyy"));
                        final String dobInWords3 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails2.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        detailsTo.setDobWords(dobInWords3);
                        detailsTo.setAdmissionnumber(tcDetails2.getAdmissionNo());
                        detailsTo.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails2.getDateOfLeaving()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        detailsTo.setLeavingYear(detailsTo.getDateOfLeaving().substring(6));
                        if (tcDetails2.getPassed() != null && tcDetails2.getPassed().equalsIgnoreCase("no")) {
                            detailsTo.setPassed(tcDetails2.getSubjectPassed());
                        }
                        else if (tcDetails2.getPassed() != null && tcDetails2.getPassed().equalsIgnoreCase("yes")) {
                            detailsTo.setPassed("Yes");
                        }
                        if (tcDetails2.getFeePaid() != null && tcDetails2.getFeePaid().equalsIgnoreCase("yes")) {
                            detailsTo.setFeePaid("Yes");
                        }
                        else if (tcDetails2.getFeePaid() != null && tcDetails2.getFeePaid().equalsIgnoreCase("no")) {
                            detailsTo.setFeePaid("No");
                        }
                        if (tcDetails2.getCharacterAndConduct() != null) {
                            detailsTo.setConduct(tcDetails2.getCharacterAndConduct().getName());
                        }
                        detailsTo.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails2.getDateOfApplication()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        if (tcDetails2.getDateOfIssue() != null) {
                            detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails2.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        }
                        String examRegNo2 = "";
                        if (student.getExamRegisterNo() != null && !student.getExamRegisterNo().trim().isEmpty()) {
                            examRegNo2 = student.getExamRegisterNo();
                        }
                        else {
                            examRegNo2 = student.getRegisterNo();
                        }
                        if (form.getTcType() != null && form.getTcType().equalsIgnoreCase("Discontinued")) {
                            detailsTo.setRegMonthYear("---------");
                        }
                        else {
                            detailsTo.setRegMonthYear(examRegNo2);
                        }
                        if (tcDetails2.getScholarship() != null && tcDetails2.getScholarship().equals("yes")) {
                            detailsTo.setScholarship("Yes");
                        }
                        else if (tcDetails2.getScholarship() != null && tcDetails2.getScholarship().equals("no")) {
                            detailsTo.setScholarship("No");
                        }
                        if (tcDetails2.getSubjectPassed() != null) {
                            detailsTo.setSubjectsStudied(tcDetails2.getSubjectPassed());
                        }
                        detailsTo.setReason(tcDetails2.getReasonOfLeaving());
                        if (tcDetails2.getEligible() != null) {
                            detailsTo.setEligible(tcDetails2.getEligible());
                        }
                        if (tcDetails2.getPromotionToNextClass() != null) {
                            detailsTo.setPromotionToNextClass(tcDetails2.getPromotionToNextClass());
                        }
                        if (student2.getRollNo() != null) {
                            detailsTo.setRollNo(student2.getRollNo());
                        }
                        if (tcDetails2.getYear() != null) {
                            detailsTo.setExamyear((int)tcDetails2.getYear());
                        }
                        if (tcDetails2.getExamMonth() != null) {
                            detailsTo.setExammonth(tcDetails2.getExamMonth());
                        }
                        if (tcDetails2.getExamYear() != null) {
                            detailsTo.setExamyear((int)tcDetails2.getExamYear());
                        }
                        if (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null) {
                            final Integer year2 = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
                            final String yr2 = String.valueOf(year2);
                            detailsTo.setAcademicYear(yr2);
                        }
                        if (tcDetails2.getIsFeePaidUni() != null && tcDetails2.getIsFeePaidUni()) {
                            detailsTo.setFeeDueToUni("Yes");
                        }
                        else {
                            detailsTo.setFeeDueToUni("No");
                        }
                        if (tcDetails2.getClassOfLeaving() != null && !tcDetails2.getClassOfLeaving().isEmpty()) {
                            detailsTo.setClassOfLeaving(tcDetails2.getClassOfLeaving());
                        }
                        if (tcDetails2.getPublicExaminationName() != null) {
                            detailsTo.setPublicExamName(tcDetails2.getPublicExaminationName());
                        }
                        if (tcDetails2.getTcNo() != null && !tcDetails2.getTcNo().isEmpty()) {
                            detailsTo.setTcNo(tcDetails2.getTcNo());
                            student.setTcNo(tcDetails2.getTcNo());
                        }
                        if (tcDetails2.getDateOfLeaving() != null) {
                            student.setTcDate(tcDetails2.getDateOfLeaving());
                        }
                    }
                    tcDetailSet2 = new HashSet<StudentTCDetails>();
                    tcDetailSet2.add(tcDetails2);
                    student.setStudentTCDetails((Set)tcDetailSet2);
                    final StringBuffer detainedRegNos2 = new StringBuffer();
                    final Set<String> detainedRegNoSet2 = new TreeSet<String>(Collections.reverseOrder());
                    final Set<ExamStudentDetentionRejoinDetails> detentionRejoinSet2 = (Set<ExamStudentDetentionRejoinDetails>)student.getExamStudentDetentionRejoinDetails();
                    for (final ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails2 : detentionRejoinSet2) {
                        if (examStudentDetentionRejoinDetails2.getRejoin() != null && examStudentDetentionRejoinDetails2.getRejoin()) {
                            detainedRegNoSet2.add(examStudentDetentionRejoinDetails2.getRegisterNo());
                        }
                    }
                    if (!detainedRegNoSet2.isEmpty()) {
                        for (final String string2 : detainedRegNoSet2) {
                            if (detainedRegNos2.length() > 0) {
                                detainedRegNos2.append(", " + string2);
                            }
                            else {
                                detainedRegNos2.append(string2);
                            }
                        }
                    }
                    if (detainedRegNos2.length() > 0) {
                        detailsTo.setDetainedRegNos(detainedRegNos2.toString());
                    }
                    if (!student.getAdmAppln().getIsCancelled() && student.getIsAdmitted()) {
                        studentsGotTcList.add(student);
                        studentToList.add(detailsTo);
                    }
                }
            }
        }
        return studentToList;
    }
    
    public String getSchemeinWords(final String courseScheme, final Integer schemeNo) throws Exception {
        String year = "";
        if (courseScheme.equalsIgnoreCase("Semester")) {
            if (schemeNo == 1 || schemeNo == 2) {
                year = "First";
            }
            else if (schemeNo == 3 || schemeNo == 4) {
                year = "Second";
            }
            else if (schemeNo == 5 || schemeNo == 6) {
                year = "Third";
            }
            else if (schemeNo == 7 || schemeNo == 8) {
                year = "Fourth";
            }
            else if (schemeNo == 9 || schemeNo == 10) {
                year = "Fifth";
            }
        }
        else if (courseScheme.equalsIgnoreCase("Trimester")) {
            if (schemeNo == 1 || schemeNo == 2 || schemeNo == 3) {
                year = "First";
            }
            else if (schemeNo == 4 || schemeNo == 5 || schemeNo == 6) {
                year = "Second";
            }
            else if (schemeNo == 7 || schemeNo == 8 || schemeNo == 9) {
                year = "Third";
            }
            else if (schemeNo == 10 || schemeNo == 11 || schemeNo == 12) {
                year = "Fourth";
            }
        }
        else if (courseScheme.equalsIgnoreCase("Year")) {
            if (schemeNo == 1) {
                year = "First";
            }
            else if (schemeNo == 2) {
                year = "Second";
            }
            else if (schemeNo == 3) {
                year = "Third";
            }
            else if (schemeNo == 4) {
                year = "Fourth";
            }
            else if (schemeNo == 5) {
                year = "Fifth";
            }
        }
        return year;
    }
    
    private String convertBirthDateToWord(final String dobFigures) {
        String inWords = "";
        final StringTokenizer str = new StringTokenizer(dobFigures, "/");
        final int date = Integer.parseInt(str.nextToken());
        final int month = Integer.parseInt(str.nextToken());
        final String year = str.nextToken();
        final String year2 = year.substring(0, 2);
        final String year3 = year.substring(2);
        final int yearInt = Integer.parseInt(year);
        if (yearInt < 2000) {
            inWords = String.valueOf(NumberToWordConvertor.getDate(date)) + " " + CommonUtil.getMonthForNumber(month) + " " + NumberToWordConvertor.convertNumber(year2).toUpperCase() + " " + NumberToWordConvertor.convertNumber(year3);
        }
        else {
            inWords = String.valueOf(NumberToWordConvertor.getDate(date)) + " " + CommonUtil.getMonthForNumber(month) + " " + NumberToWordConvertor.convertNumber(year).toUpperCase();
        }
        String result = "";
        final char firstChar = inWords.charAt(0);
        result = String.valueOf(result) + Character.toUpperCase(firstChar);
        for (int i = 1; i < inWords.length(); ++i) {
            final char currentChar = inWords.charAt(i);
            final char previousChar = inWords.charAt(i - 1);
            if (previousChar == ' ') {
                result = String.valueOf(result) + Character.toUpperCase(currentChar);
            }
            else {
                result = String.valueOf(result) + Character.toLowerCase(currentChar);
            }
        }
        return result;
    }
    
    public List<PrintTcDetailsTo> convertStudentBoToTo(final Student student, final List<Student> studentsGotTcList, final HttpServletRequest request, final TCDetailsForm form, final List<Integer> discontinuedStudentId) throws Exception {
        final ITransferCertificateTransaction transferCertificate = (ITransferCertificateTransaction)new TransferCertificateTransactionImpl();
        final int termNumber = transferCertificate.getClassTermNumber(Integer.parseInt(form.getClasses()));
        final String semNo = DownloadHallTicketHelper.semMap.get(String.valueOf(termNumber));
        final List<PrintTcDetailsTo> studentToList = new ArrayList<PrintTcDetailsTo>();
        if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Christ")) {
            byte[] logo = null;
            final HttpSession session = request.getSession(false);
            final Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
            if (organisation != null) {
                logo = organisation.getTopbar();
            }
            if (session != null) {
                session.setAttribute("LogoBytes", (Object)logo);
            }
        }
        if (student != null && ((form.getTcType().equalsIgnoreCase("normal") && discontinuedStudentId.contains(student.getId())) || (form.getTcType().equalsIgnoreCase("Discontinued") && !discontinuedStudentId.contains(student.getId())))) {
            if (form.getDuplicate().equalsIgnoreCase("yes") && student.getTcNo() != null) {
                final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                if (student.getStudentTCDetails().size() != 0) {
                    final String oldClassName = TransferCertificateHandler.getInstance().getOldClassNameByStudentId(student.getId());
                    detailsTo.setStudentNo(student.getStudentNo());
                    detailsTo.setRegNo(student.getRegisterNo());
                    detailsTo.setCourse(student.getAdmAppln().getCourseBySelectedCourseId().getName());
                    detailsTo.setSex(student.getAdmAppln().getPersonalData().getGender());
                    if (student.getAdmAppln().getPersonalData().getReligion() != null) {
                        detailsTo.setReligion(student.getAdmAppln().getPersonalData().getReligion().getName().toUpperCase());
                    }
                    else if (student.getAdmAppln().getPersonalData().getReligionOthers() != null) {
                        detailsTo.setReligion(student.getAdmAppln().getPersonalData().getReligionSectionOthers().toUpperCase());
                    }
                    detailsTo.setNationality(student.getAdmAppln().getPersonalData().getNationality().getName().toUpperCase());
                    detailsTo.setFatherName(student.getAdmAppln().getPersonalData().getFatherName());
                    detailsTo.setMotherName(student.getAdmAppln().getPersonalData().getMotherName());
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        String className = "";
                        className = student.getAdmAppln().getCourseBySelectedCourseId().getName();
                        detailsTo.setClassName(String.valueOf(semNo) + " " + className);
                    }
                    if (oldClassName != null && !oldClassName.trim().isEmpty()) {
                        detailsTo.setAdmissionClass(oldClassName);
                    }
                    else {
                        detailsTo.setAdmissionClass(detailsTo.getClassName());
                    }
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        String className = "";
                        className = student.getAdmAppln().getCourseBySelectedCourseId().getName();
                        detailsTo.setAdmissionClass(String.valueOf(semNo) + " " + className);
                    }
                    String caste = "";
                    String subCaste = "";
                    if (student.getAdmAppln().getPersonalData().getReligionSectionOthers() != null && !student.getAdmAppln().getPersonalData().getReligionSectionOthers().isEmpty()) {
                        subCaste = student.getAdmAppln().getPersonalData().getReligionSectionOthers();
                    }
                    else if (student.getAdmAppln().getPersonalData().getReligionSection() != null) {
                        subCaste = student.getAdmAppln().getPersonalData().getReligionSection().getName();
                    }
                    if (student.getAdmAppln().getPersonalData().getCaste() != null) {
                        caste = student.getAdmAppln().getPersonalData().getCaste().getName();
                        if (caste.equalsIgnoreCase("sc") || caste.equalsIgnoreCase("st") || caste.equalsIgnoreCase("Nomadic Tribe") || caste.equalsIgnoreCase("Semi-Nomadic Tribe") || caste.equalsIgnoreCase("CAT I") || caste.equalsIgnoreCase("CAT IIIB") || caste.equalsIgnoreCase("CAT IIIA") || caste.equalsIgnoreCase("CAT IIA") || caste.equalsIgnoreCase("CAT IIB")) {
                            if (!subCaste.isEmpty()) {
                                detailsTo.setCaste("YES,  " + caste.toUpperCase() + " (" + subCaste.toUpperCase() + ")");
                            }
                            else {
                                detailsTo.setCaste("YES,  " + caste.toUpperCase());
                            }
                        }
                        else {
                            detailsTo.setCaste("NO");
                        }
                    }
                    if (!subCaste.isEmpty()) {
                        detailsTo.setReligion(String.valueOf(detailsTo.getReligion()) + " - " + subCaste.toUpperCase());
                    }
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        if (student.getAdmAppln().getAdmissionDate() != null) {
                            detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        }
                    }
                    else if (student.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (form.getTcDate() != null && !form.getTcDate().isEmpty()) {
                        student.setTcDate(CommonUtil.ConvertStringToDate(form.getTcDate()));
                    }
                    else {
                        student.setTcDate(new Date());
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    Set<StudentTCDetails> tcDetailSet = (Set<StudentTCDetails>)student.getStudentTCDetails();
                    final Iterator<StudentTCDetails> itr = tcDetailSet.iterator();
                    StudentTCDetails tcDetails = null;
                    while (itr.hasNext()) {
                        tcDetails = itr.next();
                        detailsTo.setStudentName(tcDetails.getStudentName());
                        detailsTo.setDobFigures(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd-MM-yyyy"));
                        final String dobInWords1 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        detailsTo.setDobWords(dobInWords1);
                        detailsTo.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfLeaving()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        detailsTo.setLeavingYear(detailsTo.getDateOfLeaving().substring(6));
                        if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("no")) {
                            detailsTo.setPassed(String.valueOf(tcDetails.getPassed().toUpperCase()) + "; " + tcDetails.getSubjectPassed().toUpperCase());
                        }
                        else if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("yes")) {
                            detailsTo.setPassed("Yes");
                        }
                        if (tcDetails.getFeePaid() != null && tcDetails.getFeePaid().equalsIgnoreCase("yes")) {
                            detailsTo.setFeePaid("Yes");
                        }
                        else if (tcDetails.getFeePaid() != null && tcDetails.getFeePaid().equalsIgnoreCase("no")) {
                            detailsTo.setFeePaid("No");
                        }
                        if (tcDetails.getCharacterAndConduct() != null) {
                            detailsTo.setConduct(tcDetails.getCharacterAndConduct().getName().toUpperCase());
                        }
                        detailsTo.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfApplication()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        if (tcDetails.getDateOfIssue() != null) {
                            detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        }
                        String examRegNo = "";
                        if (student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()) {
                            examRegNo = student.getExamRegisterNo();
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Christ")) {
                            examRegNo = student.getRegisterNo();
                        }
                        if (form.getTcType() != null && form.getTcType().equalsIgnoreCase("Discontinued")) {
                            detailsTo.setRegMonthYear("---------");
                        }
                        else {
                            detailsTo.setRegMonthYear(examRegNo);
                        }
                        if (tcDetails.getScholarship() != null && tcDetails.getScholarship().equals("yes")) {
                            detailsTo.setScholarship("Yes");
                        }
                        else if (tcDetails.getScholarship() != null && tcDetails.getScholarship().equals("no")) {
                            detailsTo.setScholarship("No");
                        }
                        if (tcDetails.getSubjectPassed() != null) {
                            detailsTo.setSubjectsStudied(tcDetails.getSubjectPassed());
                        }
                        detailsTo.setReason(tcDetails.getReasonOfLeaving());
                        if (tcDetails.getPromotionToNextClass() != null) {
                            detailsTo.setPromotionToNextClass(tcDetails.getPromotionToNextClass());
                        }
                        if (student.getRollNo() != null) {
                            detailsTo.setRollNo(student.getRollNo());
                        }
                        if (tcDetails.getYear() != null) {
                            detailsTo.setExamyear((int)tcDetails.getYear());
                        }
                        if (tcDetails.getExamMonth() != null) {
                            detailsTo.setExammonth(tcDetails.getExamMonth());
                        }
                        if (tcDetails.getExamYear() != null) {
                            detailsTo.setExamyear((int)tcDetails.getExamYear());
                        }
                        if (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null) {
                            final Integer year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
                            final String yr = String.valueOf(year);
                            detailsTo.setAcademicYear(yr);
                        }
                        if (tcDetails.getEligible() != null) {
                            detailsTo.setEligible(tcDetails.getEligible());
                        }
                        if (discontinuedStudentId.contains(student.getId())) {
                            detailsTo.setPublicExamName("DISCONTINUED");
                        }
                        else if (tcDetails.getPublicExaminationName() != null) {
                            detailsTo.setPublicExamName(tcDetails.getPublicExaminationName());
                        }
                        if (tcDetails.getClassOfLeaving() != null && !tcDetails.getClassOfLeaving().isEmpty()) {
                            detailsTo.setClassOfLeaving(tcDetails.getClassOfLeaving());
                        }
                        if (tcDetails.getTcNo() != null && !tcDetails.getTcNo().isEmpty()) {
                            detailsTo.setTcNo(tcDetails.getTcNo());
                            student.setTcNo(tcDetails.getTcNo());
                        }
                        if (tcDetails.getDateOfLeaving() != null) {
                            student.setTcDate(tcDetails.getDateOfLeaving());
                        }
                    }
                    String part1 = "";
                    String part2 = "";
                    String secondLanguage = "";
                    if (student.getAdmAppln().getApplicantSubjectGroups() != null) {
                        for (final ApplicantSubjectGroup group : student.getAdmAppln().getApplicantSubjectGroups()) {
                            for (final SubjectGroupSubjects groupSubjects : group.getSubjectGroup().getSubjectGroupSubjectses()) {
                                if (!groupSubjects.getIsActive()) {
                                    continue;
                                }
                                if (groupSubjects.getSubject().getCode().equals("VED") || groupSubjects.getSubject().getCode().equals("CA") || groupSubjects.getSubject().getName().toLowerCase().indexOf("remidial") != -1 || groupSubjects.getSubject().getName().toLowerCase().indexOf("aeee") != -1 || groupSubjects.getSubject().getName().toLowerCase().indexOf("cet") != -1) {
                                    continue;
                                }
                                if (groupSubjects.getSubject().getIsSecondLanguage() || groupSubjects.getSubject().getName().toLowerCase().indexOf("english") != -1) {
                                    if (!groupSubjects.getSubject().getIsSecondLanguage()) {
                                        if (!part1.isEmpty()) {
                                            part1 = String.valueOf(part1) + ", " + groupSubjects.getSubject().getName().toUpperCase();
                                        }
                                        else {
                                            part1 = String.valueOf(part1) + groupSubjects.getSubject().getName().toUpperCase();
                                        }
                                    }
                                    else if (!secondLanguage.isEmpty()) {
                                        secondLanguage = String.valueOf(secondLanguage) + ", " + groupSubjects.getSubject().getName().toUpperCase();
                                    }
                                    else {
                                        secondLanguage = String.valueOf(secondLanguage) + groupSubjects.getSubject().getName().toUpperCase();
                                    }
                                }
                                else if (!part2.isEmpty()) {
                                    part2 = String.valueOf(part2) + ", " + groupSubjects.getSubject().getName().toUpperCase();
                                }
                                else {
                                    part2 = String.valueOf(part2) + groupSubjects.getSubject().getName().toUpperCase();
                                }
                            }
                        }
                    }
                    if (secondLanguage.isEmpty()) {
                        detailsTo.setSubjectsPart1(part1);
                    }
                    else {
                        detailsTo.setSubjectsPart1(String.valueOf(part1) + ", " + secondLanguage);
                    }
                    detailsTo.setSubjectsPart2(part2);
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        detailsTo.setSubjectsPart2(transferCertificate.getStudentSubject(student.getAdmAppln().getApplicantSubjectGroups(), student.getAdmAppln().getCourseBySelectedCourseId().getId(), Integer.parseInt(form.getYear())));
                    }
                    tcDetailSet = new HashSet<StudentTCDetails>();
                    tcDetailSet.add(tcDetails);
                    student.setStudentTCDetails((Set)tcDetailSet);
                    if (!student.getAdmAppln().getIsCancelled() && student.getIsAdmitted()) {
                        studentsGotTcList.add(student);
                        studentToList.add(detailsTo);
                    }
                }
            }
            else if (form.getDuplicate().equalsIgnoreCase("no")) {
                final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                if (student.getStudentTCDetails().size() != 0) {
                    final String oldClassName = TransferCertificateHandler.getInstance().getOldClassNameByStudentId(student.getId());
                    detailsTo.setStudentNo(student.getStudentNo());
                    detailsTo.setRegNo(student.getRegisterNo());
                    detailsTo.setCourse(student.getAdmAppln().getCourseBySelectedCourseId().getName());
                    detailsTo.setAdmissionnumber(student.getAdmAppln().getAdmissionNumber());
                    detailsTo.setSex(student.getAdmAppln().getPersonalData().getGender());
                    if (student.getAdmAppln().getPersonalData().getReligion() != null) {
                        detailsTo.setReligion(student.getAdmAppln().getPersonalData().getReligion().getName().toUpperCase());
                    }
                    else if (student.getAdmAppln().getPersonalData().getReligionOthers() != null) {
                        detailsTo.setReligion(student.getAdmAppln().getPersonalData().getReligionSectionOthers().toUpperCase());
                    }
                    detailsTo.setNationality(student.getAdmAppln().getPersonalData().getNationality().getName().toUpperCase());
                    detailsTo.setFatherName(student.getAdmAppln().getPersonalData().getFatherName());
                    detailsTo.setMotherName(student.getAdmAppln().getPersonalData().getMotherName());
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        String className = "";
                        className = student.getAdmAppln().getCourseBySelectedCourseId().getName();
                        detailsTo.setClassName(String.valueOf(semNo) + " " + className);
                    }
                    if (oldClassName != null && !oldClassName.trim().isEmpty()) {
                        detailsTo.setAdmissionClass(oldClassName);
                    }
                    else {
                        detailsTo.setAdmissionClass(detailsTo.getClassName());
                    }
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        String className = "";
                        className = student.getAdmAppln().getCourseBySelectedCourseId().getName();
                        detailsTo.setAdmissionClass(String.valueOf(semNo) + " " + className);
                    }
                    String caste = "";
                    String subCaste = "";
                    if (student.getAdmAppln().getPersonalData().getReligionSectionOthers() != null && !student.getAdmAppln().getPersonalData().getReligionSectionOthers().isEmpty()) {
                        subCaste = student.getAdmAppln().getPersonalData().getReligionSectionOthers();
                    }
                    else if (student.getAdmAppln().getPersonalData().getReligionSection() != null) {
                        subCaste = student.getAdmAppln().getPersonalData().getReligionSection().getName();
                    }
                    if (student.getAdmAppln().getPersonalData().getCaste() != null) {
                        caste = student.getAdmAppln().getPersonalData().getCaste().getName();
                        if (caste.equalsIgnoreCase("sc") || caste.equalsIgnoreCase("st") || caste.equalsIgnoreCase("Nomadic Tribe") || caste.equalsIgnoreCase("Semi-Nomadic Tribe") || caste.equalsIgnoreCase("CAT I") || caste.equalsIgnoreCase("CAT IIIB") || caste.equalsIgnoreCase("CAT IIIA") || caste.equalsIgnoreCase("CAT IIA") || caste.equalsIgnoreCase("CAT IIB")) {
                            if (!subCaste.isEmpty()) {
                                detailsTo.setCaste("YES,  " + caste.toUpperCase() + " (" + subCaste.toUpperCase() + ")");
                            }
                            else {
                                detailsTo.setCaste("YES,  " + caste.toUpperCase());
                            }
                        }
                        else {
                            detailsTo.setCaste("NO");
                        }
                    }
                    if (!subCaste.isEmpty()) {
                        detailsTo.setReligion(String.valueOf(detailsTo.getReligion()) + " - " + subCaste.toUpperCase());
                    }
                    if (student.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (form.getTcDate() != null && !form.getTcDate().isEmpty()) {
                        student.setTcDate(CommonUtil.ConvertStringToDate(form.getTcDate()));
                    }
                    else {
                        student.setTcDate(new Date());
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    Set<StudentTCDetails> tcDetailSet = (Set<StudentTCDetails>)student.getStudentTCDetails();
                    final Iterator<StudentTCDetails> itr = student.getStudentTCDetails().iterator();
                    StudentTCDetails tcDetails = null;
                    while (itr.hasNext()) {
                        tcDetails = itr.next();
                        detailsTo.setStudentName(tcDetails.getStudentName());
                        detailsTo.setDobFigures(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd-MM-yyyy"));
                        final String dobInWords1 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        detailsTo.setDobWords(dobInWords1);
                        detailsTo.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfLeaving()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        detailsTo.setLeavingYear(detailsTo.getDateOfLeaving().substring(6));
                        if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("no")) {
                            detailsTo.setPassed(String.valueOf(tcDetails.getPassed().toUpperCase()) + "; " + tcDetails.getSubjectPassed().toUpperCase());
                        }
                        else if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("yes")) {
                            detailsTo.setPassed("YES");
                        }
                        if (tcDetails.getFeePaid() != null && tcDetails.getFeePaid().equalsIgnoreCase("yes")) {
                            detailsTo.setFeePaid("YES");
                        }
                        else if (tcDetails.getFeePaid() != null && tcDetails.getFeePaid().equalsIgnoreCase("no")) {
                            detailsTo.setFeePaid("NO");
                        }
                        if (tcDetails.getCharacterAndConduct() != null) {
                            detailsTo.setConduct(tcDetails.getCharacterAndConduct().getName().toUpperCase());
                        }
                        detailsTo.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfApplication()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        String examRegNo = "";
                        if (student.getExamRegisterNo() != null) {
                            examRegNo = student.getExamRegisterNo();
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Christ")) {
                            examRegNo = student.getRegisterNo();
                        }
                        if (form.getTcType() != null && form.getTcType().equalsIgnoreCase("Discontinued")) {
                            detailsTo.setRegMonthYear("---------");
                        }
                        else {
                            detailsTo.setRegMonthYear(examRegNo);
                        }
                        if (tcDetails.getScholarship() != null && tcDetails.getScholarship().equals("yes")) {
                            detailsTo.setScholarship("YES");
                        }
                        else if (tcDetails.getScholarship() != null && tcDetails.getScholarship().equals("no")) {
                            detailsTo.setScholarship("NO");
                        }
                        if (tcDetails.getSubjectPassed() != null) {
                            detailsTo.setSubjectsStudied(tcDetails.getSubjectPassed());
                        }
                        detailsTo.setReason(tcDetails.getReasonOfLeaving());
                        if (tcDetails.getEligible() != null) {
                            detailsTo.setEligible(tcDetails.getEligible());
                        }
                        if (tcDetails.getPromotionToNextClass() != null) {
                            detailsTo.setPromotionToNextClass(tcDetails.getPromotionToNextClass());
                        }
                        if (student.getRollNo() != null) {
                            detailsTo.setRollNo(student.getRollNo());
                        }
                        if (tcDetails.getYear() != null) {
                            detailsTo.setExamyear((int)tcDetails.getYear());
                        }
                        if (tcDetails.getExamMonth() != null) {
                            detailsTo.setExammonth(tcDetails.getExamMonth());
                        }
                        if (tcDetails.getExamYear() != null) {
                            detailsTo.setExamyear((int)tcDetails.getExamYear());
                        }
                        if (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null) {
                            final Integer year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
                            final String yr = String.valueOf(year);
                            detailsTo.setAcademicYear(yr);
                        }
                        if (discontinuedStudentId.contains(student.getId())) {
                            detailsTo.setPublicExamName("DISCONTINUED");
                        }
                        else if (tcDetails.getPublicExaminationName() != null) {
                            detailsTo.setPublicExamName(tcDetails.getPublicExaminationName());
                        }
                        if (tcDetails.getClassOfLeaving() != null && !tcDetails.getClassOfLeaving().isEmpty()) {
                            detailsTo.setClassOfLeaving(tcDetails.getClassOfLeaving());
                        }
                        if (tcDetails.getTcNo() != null && !tcDetails.getTcNo().isEmpty()) {
                            detailsTo.setTcNo(tcDetails.getTcNo());
                            student.setTcNo(tcDetails.getTcNo());
                        }
                        if (tcDetails.getDateOfLeaving() != null) {
                            student.setTcDate(tcDetails.getDateOfLeaving());
                        }
                    }
                    String part1 = "";
                    String part2 = "";
                    String secondLanguage = "";
                    if (student.getAdmAppln().getApplicantSubjectGroups() != null) {
                        for (final ApplicantSubjectGroup group : student.getAdmAppln().getApplicantSubjectGroups()) {
                            for (final SubjectGroupSubjects groupSubjects : group.getSubjectGroup().getSubjectGroupSubjectses()) {
                                if (!groupSubjects.getIsActive()) {
                                    continue;
                                }
                                if (groupSubjects.getSubject().getCode().equals("VED") || groupSubjects.getSubject().getCode().equals("CA") || groupSubjects.getSubject().getName().toLowerCase().indexOf("remidial") != -1 || groupSubjects.getSubject().getName().toLowerCase().indexOf("aeee") != -1 || groupSubjects.getSubject().getName().toLowerCase().indexOf("cet") != -1) {
                                    continue;
                                }
                                if (groupSubjects.getSubject().getIsSecondLanguage() || groupSubjects.getSubject().getName().toLowerCase().indexOf("english") != -1) {
                                    if (!groupSubjects.getSubject().getIsSecondLanguage()) {
                                        if (!part1.isEmpty()) {
                                            part1 = String.valueOf(part1) + ", " + groupSubjects.getSubject().getName().toUpperCase();
                                        }
                                        else {
                                            part1 = String.valueOf(part1) + groupSubjects.getSubject().getName().toUpperCase();
                                        }
                                    }
                                    else if (!secondLanguage.isEmpty()) {
                                        secondLanguage = String.valueOf(secondLanguage) + ", " + groupSubjects.getSubject().getName().toUpperCase();
                                    }
                                    else {
                                        secondLanguage = String.valueOf(secondLanguage) + groupSubjects.getSubject().getName().toUpperCase();
                                    }
                                }
                                else if (!part2.isEmpty()) {
                                    part2 = String.valueOf(part2) + ", " + groupSubjects.getSubject().getName().toUpperCase();
                                }
                                else {
                                    part2 = String.valueOf(part2) + groupSubjects.getSubject().getName().toUpperCase();
                                }
                            }
                        }
                    }
                    if (secondLanguage.isEmpty()) {
                        detailsTo.setSubjectsPart1(part1);
                    }
                    else {
                        detailsTo.setSubjectsPart1(String.valueOf(part1) + ", " + secondLanguage);
                    }
                    detailsTo.setSubjectsPart2(part2);
                    if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                        detailsTo.setSubjectsPart2(transferCertificate.getStudentSubject(student.getAdmAppln().getApplicantSubjectGroups(), student.getAdmAppln().getCourseBySelectedCourseId().getId(), Integer.parseInt(form.getYear())));
                    }
                    tcDetailSet = new HashSet<StudentTCDetails>();
                    tcDetailSet.add(tcDetails);
                    student.setStudentTCDetails((Set)tcDetailSet);
                    if (!student.getAdmAppln().getIsCancelled() && student.getIsAdmitted()) {
                        studentsGotTcList.add(student);
                        studentToList.add(detailsTo);
                    }
                }
            }
        }
        return studentToList;
    }
}