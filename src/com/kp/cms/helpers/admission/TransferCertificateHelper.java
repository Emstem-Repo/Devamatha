package com.kp.cms.helpers.admission;

import java.math.BigInteger;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import java.util.TreeSet;
import java.util.Collections;
import com.kp.cms.utilities.NumberToWordConvertor;
import java.util.StringTokenizer;
import com.kp.cms.bo.admin.Organisation;
import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.HashSet;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import java.util.Date;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.handlers.admission.TransferCertificateHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.bo.admin.Student;
import java.util.Iterator;
import java.util.ArrayList;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.forms.admission.TransferCertificateForm;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admin.StudentTCDetails;
import java.util.List;
import com.kp.cms.transactionsimpl.admission.TransferCertificateTransactionImpl;
import java.util.HashMap;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import java.util.Map;

public class TransferCertificateHelper
{
    private static Map<String, String> monthMap;
    public static volatile TransferCertificateHelper certificateHelper;
    ITransferCertificateTransaction transferCertificate;
    
    static {
        TransferCertificateHelper.monthMap = null;
        (TransferCertificateHelper.monthMap = new HashMap<String, String>()).put("JAN", "JANUARY");
        TransferCertificateHelper.monthMap.put("FEB", "FEBRUARY");
        TransferCertificateHelper.monthMap.put("MAR", "MARCH");
        TransferCertificateHelper.monthMap.put("APR", "APRIL");
        TransferCertificateHelper.monthMap.put("MAY", "MAY");
        TransferCertificateHelper.monthMap.put("JUN", "JUNE");
        TransferCertificateHelper.monthMap.put("JUL", "JULY");
        TransferCertificateHelper.monthMap.put("AUG", "AUGUST");
        TransferCertificateHelper.monthMap.put("SEPT", "SEPTEMBER");
        TransferCertificateHelper.monthMap.put("OCT", "OCTOBER");
        TransferCertificateHelper.monthMap.put("NOV", "NOVEMBER");
        TransferCertificateHelper.monthMap.put("DEC", "DECEMBER");
        TransferCertificateHelper.certificateHelper = null;
    }
    
    public TransferCertificateHelper() {
        this.transferCertificate = (ITransferCertificateTransaction)new TransferCertificateTransactionImpl();
    }
    
    public static TransferCertificateHelper getInstance() {
        if (TransferCertificateHelper.certificateHelper == null) {
            TransferCertificateHelper.certificateHelper = new TransferCertificateHelper();
        }
        return TransferCertificateHelper.certificateHelper;
    }
    
    public List<TCDetailsTO> convertStudentTcDetailsBoToTo(final List<StudentTCDetails> studentBoList, final List<TCNumber> number, final List<StudentTCDetails> studentsGotTcList, final HttpServletRequest request, final TransferCertificateForm form, final List<Integer> discontinuedStudentId) throws Exception {
        final List<TCDetailsTO> to = new ArrayList<TCDetailsTO>();
        if (studentBoList != null) {
            for (final StudentTCDetails bo : studentBoList) {
                final TCDetailsTO detailsTo = new TCDetailsTO();
                detailsTo.setStudentId(bo.getStudent().getId());
                detailsTo.setStudentName(bo.getStudentName());
                detailsTo.setRegisterNo(bo.getStudent().getRegisterNo());
                detailsTo.setClassId(bo.getStudent().getClassSchemewise().getClasses().getId());
                detailsTo.setCourse(bo.getStudent().getClassSchemewise().getClasses().getCourse().getName());
                to.add(detailsTo);
            }
        }
        return to;
    }
    
    public List<PrintTcDetailsTo> convertStudentBoToTo(final List<Student> studentBoList, final List<Student> studentsGotTcList, final HttpServletRequest request, final TransferCertificateForm form, final List<Integer> discontinuedStudentId) throws Exception {
        final int termNumber = this.transferCertificate.getClassTermNumber(Integer.parseInt(form.getClasses()));
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
        if (studentBoList != null && studentBoList.size() != 0) {
            for (final Student student : studentBoList) {
                if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                    if (form.getTcType().equalsIgnoreCase("normal") && discontinuedStudentId.contains(student.getId())) {
                        continue;
                    }
                    if (form.getTcType().equalsIgnoreCase("Discontinued") && !discontinuedStudentId.contains(student.getId())) {
                        continue;
                    }
                }
                if (form.getDuplicate().equalsIgnoreCase("yes") && student.getTcNo() != null) {
                    final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                    if (student.getStudentTCDetails().size() == 0) {
                        continue;
                    }
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
                        detailsTo.setSubjectsPart2(this.transferCertificate.getStudentSubject(student.getAdmAppln().getApplicantSubjectGroups(), student.getAdmAppln().getCourseBySelectedCourseId().getId(), Integer.parseInt(form.getYear())));
                    }
                    tcDetailSet = new HashSet<StudentTCDetails>();
                    tcDetailSet.add(tcDetails);
                    student.setStudentTCDetails((Set)tcDetailSet);
                    if (student.getAdmAppln().getIsCancelled() || !student.getIsAdmitted()) {
                        continue;
                    }
                    studentsGotTcList.add(student);
                    studentToList.add(detailsTo);
                }
                else {
                    if (!form.getDuplicate().equalsIgnoreCase("no")) {
                        continue;
                    }
                    final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                    if (student.getStudentTCDetails().size() == 0) {
                        continue;
                    }
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
                        detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
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
                        detailsTo.setSubjectsPart2(this.transferCertificate.getStudentSubject(student.getAdmAppln().getApplicantSubjectGroups(), student.getAdmAppln().getCourseBySelectedCourseId().getId(), Integer.parseInt(form.getYear())));
                    }
                    tcDetailSet = new HashSet<StudentTCDetails>();
                    tcDetailSet.add(tcDetails);
                    student.setStudentTCDetails((Set)tcDetailSet);
                    if (student.getAdmAppln().getIsCancelled() || !student.getIsAdmitted()) {
                        continue;
                    }
                    studentsGotTcList.add(student);
                    studentToList.add(detailsTo);
                }
            }
        }
        return studentToList;
    }
    
    private String getSlno(final int slNo, final String prefix, final String year) {
        String strSlNo = new StringBuilder().append(slNo).toString();
        String result = "";
        if (strSlNo.length() == 1) {
            strSlNo = "000" + strSlNo;
        }
        else if (strSlNo.length() == 2) {
            strSlNo = "00" + strSlNo;
        }
        else if (strSlNo.length() == 3) {
            strSlNo = "0" + strSlNo;
        }
        final String nextYear = new StringBuilder().append(String.valueOf(Integer.parseInt(year) + 1)).toString();
        result = String.valueOf(prefix) + strSlNo + "/" + year + "-" + nextYear.substring(2);
        return result;
    }
    
    private String convertIntegerToWord(final String dobFigures) {
        String inWords = "";
        final StringTokenizer str = new StringTokenizer(dobFigures, "/");
        final int date = Integer.parseInt(str.nextToken());
        final int month = Integer.parseInt(str.nextToken());
        final String year = str.nextToken();
        final String year2 = year.substring(0, 2);
        final String year3 = year.substring(2);
        inWords = String.valueOf(NumberToWordConvertor.getDate(date)) + "," + CommonUtil.getMonthForNumber(month) + "," + NumberToWordConvertor.convertNumber(year2).toUpperCase() + " " + NumberToWordConvertor.convertNumber(year3);
        final char firstChar = inWords.charAt(0);
        String result = "";
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
    
    public List<PrintTcDetailsTo> convertStudentBoToToForReprint(final List<Student> studentBoList, final HttpServletRequest request, final List<Integer> discontinuedStudentsList, final TransferCertificateForm form) throws Exception {
        final List<PrintTcDetailsTo> studentToList = new ArrayList<PrintTcDetailsTo>();
        byte[] logo = null;
        final HttpSession session = request.getSession(false);
        final Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
        if (organisation != null) {
            logo = organisation.getTopbar();
        }
        if (session != null) {
            session.setAttribute("LogoBytes", (Object)logo);
        }
        if (studentBoList != null) {
            for (final Student student : studentBoList) {
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
                    final int termNumber = this.transferCertificate.getClassTermNumber(Integer.parseInt(String.valueOf(student.getClassSchemewise().getId())));
                    final String semNo = DownloadHallTicketHelper.semMap.get(String.valueOf(termNumber));
                    final int year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
                    if (oldClassName != null && !oldClassName.trim().isEmpty()) {
                        detailsTo.setAdmissionClass(oldClassName);
                    }
                    else {
                        detailsTo.setAdmissionClass(detailsTo.getClassName());
                    }
                    final String className = student.getAdmAppln().getCourseBySelectedCourseId().getName();
                    detailsTo.setClassName(String.valueOf(semNo) + " " + className);
                    detailsTo.setAdmissionClass(String.valueOf(semNo) + " " + className);
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
                    if (form.getTcReDate() != null && !form.getTcReDate().isEmpty()) {
                        detailsTo.setTcDate(form.getTcReDate());
                    }
                    else {
                        detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    for (final StudentTCDetails tcDetails : student.getStudentTCDetails()) {
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
                        if (tcDetails.getDateOfIssue() != null) {
                            detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        }
                        String examRegNo = "";
                        if (student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()) {
                            examRegNo = student.getExamRegisterNo();
                        }
                        else {
                            examRegNo = student.getRegisterNo();
                        }
                        if (student.getTcType() != null && student.getTcType().equalsIgnoreCase("Discontinued")) {
                            detailsTo.setRegMonthYear("--------");
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
                            final Integer year2 = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
                            final String yr = String.valueOf(year2);
                            detailsTo.setAcademicYear(yr);
                        }
                        if (discontinuedStudentsList.contains(student.getId())) {
                            if (tcDetails.getPublicExaminationName() != null) {
                                detailsTo.setPublicExamName(tcDetails.getPublicExaminationName());
                            }
                            detailsTo.setTcType("Discontinued");
                        }
                        else if (tcDetails.getPublicExaminationName() != null) {
                            detailsTo.setPublicExamName(tcDetails.getPublicExaminationName());
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
                    detailsTo.setSubjectsPart2(this.transferCertificate.getStudentSubject(student.getAdmAppln().getApplicantSubjectGroups(), student.getAdmAppln().getCourseBySelectedCourseId().getId(), year));
                    detailsTo.setTcNo(student.getTcNo());
                    detailsTo.setSlNo(student.getSlNo());
                    detailsTo.setMcNo(student.getMcNo());
                    studentToList.add(detailsTo);
                }
            }
        }
        return studentToList;
    }
    
    public String getQueryForInputSearch(final TransferCertificateForm certificateForm) throws Exception {
        final StringBuffer query = new StringBuffer("select s.student from StudentTCDetails s where s.student.admAppln.isSelected=1  and s.student.admAppln.isApproved=1 ");
        if (certificateForm.getClasses() != null && !certificateForm.getClasses().trim().isEmpty()) {
            query.append(" and s.student.classSchemewise.id=" + certificateForm.getClasses());
        }
        if (certificateForm.getFromUsn() != null && !certificateForm.getFromUsn().trim().isEmpty()) {
            query.append(" and s.student.registerNo>='" + certificateForm.getFromUsn() + "'");
        }
        if (certificateForm.getToUsn() != null && !certificateForm.getToUsn().trim().isEmpty()) {
            query.append(" and s.student.registerNo<='" + certificateForm.getToUsn() + "'");
        }
        if (certificateForm.getDuplicate().equalsIgnoreCase("no")) {
            query.append(" and s.student.tcNo is null");
        }
        return query.toString();
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
    
    public List<TCDetailsTO> convertStudentTcDetailsBoToToOnlyTC(final List<StudentTCDetails> studentBoList, final List<TCNumber> number, final List<Student> studentsGotTcList, final HttpServletRequest request, final TransferCertificateForm form) throws Exception {
        final List<TCDetailsTO> to = new ArrayList<TCDetailsTO>();
        if (studentBoList != null) {
            for (final StudentTCDetails bo : studentBoList) {
                final TCDetailsTO detailsTo = new TCDetailsTO();
                detailsTo.setStudentId(bo.getStudent().getId());
                detailsTo.setStudentName(bo.getStudentName());
                detailsTo.setRegisterNo(bo.getStudent().getRegisterNo());
                detailsTo.setClassId(bo.getStudent().getClassSchemewise().getClasses().getId());
                detailsTo.setCourse(bo.getStudent().getClassSchemewise().getClasses().getCourse().getName());
                to.add(detailsTo);
            }
        }
        return to;
    }
    
    public List<PrintTcDetailsTo> convertStudentBoToToOnlyTC(final List<Student> studentBoList, final List<Student> studentsGotTcList, final HttpServletRequest request, final TransferCertificateForm form) throws Exception {
        final Student student2 = new Student();
        final List<PrintTcDetailsTo> studentToList = new ArrayList<PrintTcDetailsTo>();
        if (studentBoList != null && studentBoList.size() != 0) {
            for (final Student student3 : studentBoList) {
                final Integer admittedSem = TransferCertificateHandler.getInstance().getAdmittedSemester(student3.getId());
                final BigInteger rejoinYear = TransferCertificateHandler.getInstance().getRejoinYear(student3.getId());
                if (form.getDuplicate().equalsIgnoreCase("yes") && student3.getTcNo() != null) {
                    final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                    if (student3.getStudentTCDetails().size() == 0) {
                        continue;
                    }
                    detailsTo.setStudentNo(student3.getStudentNo());
                    detailsTo.setRegNo(student3.getRegisterNo());
                    detailsTo.setCourse(student3.getAdmAppln().getCourseBySelectedCourseId().getName());
                    String schemeInWords = this.getSchemeinWords(student3.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName(), student3.getClassSchemewise().getClasses().getTermNumber());
                    String className = "";
                    if (schemeInWords != null) {
                        className = schemeInWords.toUpperCase();
                    }
                    if (student3.getClassSchemewise().getClasses().getCourse() != null && student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        className = String.valueOf(className) + " " + student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                    }
                    detailsTo.setClassName(className);
                    final String courseScheme = TransferCertificateHandler.getInstance().getCourseScheme(student3.getAdmAppln().getCourseBySelectedCourseId().getId(), student3.getAdmAppln().getAppliedYear());
                    schemeInWords = this.getSchemeinWords(courseScheme, admittedSem);
                    className = "";
                    if (schemeInWords != null) {
                        className = schemeInWords.toUpperCase();
                    }
                    if (student3.getClassSchemewise().getClasses().getCourse() != null && student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("St Berchmans")) {
                            if (student3.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 3 || student3.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 9) {
                                className = student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                                form.setFlag(true);
                            }
                            else {
                                className = String.valueOf(className) + " " + student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                            }
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                            className = String.valueOf(className) + " " + student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                        }
                    }
                    detailsTo.setAdmissionClass(className);
                    if (student3.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student3.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (form.getTcDate() != null && !form.getTcDate().isEmpty()) {
                        student3.setTcDate(CommonUtil.ConvertStringToDate(form.getTcDate()));
                    }
                    else {
                        student3.setTcDate(new Date());
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student3.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    if (rejoinYear != null) {
                        detailsTo.setAdmittedYear(String.valueOf(student3.getRegisterNo()) + "/" + rejoinYear + "-" + (student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    else {
                        detailsTo.setAdmittedYear(String.valueOf(student3.getRegisterNo()) + "/" + student3.getAdmAppln().getAppliedYear().toString() + "-" + (student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    Set<StudentTCDetails> tcDetailSet = (Set<StudentTCDetails>)student3.getStudentTCDetails();
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
                        if (student3.getExamRegisterNo() != null && !student3.getExamRegisterNo().trim().isEmpty()) {
                            examRegNo = student3.getExamRegisterNo();
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Christ")) {
                            examRegNo = student3.getRegisterNo();
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
                        if (student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null) {
                            final Integer year = student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
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
                            student3.setTcNo(tcDetails.getTcNo());
                        }
                        if (tcDetails.getDateOfLeaving() != null) {
                            student3.setTcDate(tcDetails.getDateOfLeaving());
                        }
                        if (tcDetails.getAdmissionNo() != null) {
                            detailsTo.setAdmissionnumber(tcDetails.getAdmissionNo());
                        }
                    }
                    tcDetailSet = new HashSet<StudentTCDetails>();
                    tcDetailSet.add(tcDetails);
                    student3.setStudentTCDetails((Set)tcDetailSet);
                    final StringBuffer detainedRegNos = new StringBuffer();
                    final Set<String> detainedRegNoSet = new TreeSet<String>(Collections.reverseOrder());
                    final Set<ExamStudentDetentionRejoinDetails> detentionRejoinSet = (Set<ExamStudentDetentionRejoinDetails>)student3.getExamStudentDetentionRejoinDetails();
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
                    if (student3.getAdmAppln().getIsCancelled() || !student3.getIsAdmitted()) {
                        continue;
                    }
                    studentsGotTcList.add(student3);
                    studentToList.add(detailsTo);
                }
                else {
                    if (!form.getDuplicate().equalsIgnoreCase("no")) {
                        continue;
                    }
                    final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                    if (student3.getStudentTCDetails().size() == 0) {
                        continue;
                    }
                    detailsTo.setStudentNo(student3.getStudentNo());
                    detailsTo.setRegNo(student3.getRegisterNo());
                    detailsTo.setCourse(student3.getAdmAppln().getCourseBySelectedCourseId().getName());
                    final String dobInWords2 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student3.getAdmAppln().getPersonalData().getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    detailsTo.setDobWords(dobInWords2);
                    String schemeInWords2 = this.getSchemeinWords(student3.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName(), student3.getClassSchemewise().getClasses().getTermNumber());
                    String className2 = "";
                    if (schemeInWords2 != null) {
                        className2 = schemeInWords2;
                    }
                    if (student3.getClassSchemewise().getClasses().getCourse() != null && student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        className2 = String.valueOf(className2) + " " + student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                    }
                    detailsTo.setClassName(className2);
                    final String courseScheme2 = TransferCertificateHandler.getInstance().getCourseScheme(student3.getAdmAppln().getCourseBySelectedCourseId().getId(), student3.getAdmAppln().getAppliedYear());
                    schemeInWords2 = this.getSchemeinWords(courseScheme2, admittedSem);
                    className2 = "";
                    if (schemeInWords2 != null) {
                        className2 = schemeInWords2;
                    }
                    if (student3.getClassSchemewise().getClasses().getCourse() != null && student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("St Josephs")) {
                            if (student3.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 3 || student3.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 9) {
                                className2 = student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard().toUpperCase();
                                form.setFlag(true);
                            }
                            else {
                                className2 = String.valueOf(className2) + " " + student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                            }
                        }
                        else if (form.getToCollege() != null && form.getToCollege().equalsIgnoreCase("Cjc")) {
                            className2 = String.valueOf(className2) + " " + student3.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                        }
                    }
                    detailsTo.setAdmissionClass(className2);
                    if (student3.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student3.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (student3.getTcDate() == null) {
                    	student3.setTcDate(new Date());
                    }
                    detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student3.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    if (rejoinYear != null) {
                        detailsTo.setAdmittedYear(String.valueOf(student3.getRegisterNo()) + "/" + rejoinYear + "-" + (student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    else {
                        detailsTo.setAdmittedYear(String.valueOf(student3.getRegisterNo()) + "/" + student3.getAdmAppln().getAppliedYear().toString() + "-" + (student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    Set<StudentTCDetails> tcDetailSet2 = (Set<StudentTCDetails>)student3.getStudentTCDetails();
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
                        detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails2.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        String examRegNo2 = "";
                        if (student3.getExamRegisterNo() != null && !student3.getExamRegisterNo().trim().isEmpty()) {
                            examRegNo2 = student3.getExamRegisterNo();
                        }
                        else {
                            examRegNo2 = student3.getRegisterNo();
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
                        if (student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null) {
                            final Integer year2 = student3.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
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
                            student3.setTcNo(tcDetails2.getTcNo());
                        }
                        if (tcDetails2.getDateOfLeaving() != null) {
                            student3.setTcDate(tcDetails2.getDateOfLeaving());
                        }
                    }
                    tcDetailSet2 = new HashSet<StudentTCDetails>();
                    tcDetailSet2.add(tcDetails2);
                    student3.setStudentTCDetails((Set)tcDetailSet2);
                    final StringBuffer detainedRegNos2 = new StringBuffer();
                    final Set<String> detainedRegNoSet2 = new TreeSet<String>(Collections.reverseOrder());
                    final Set<ExamStudentDetentionRejoinDetails> detentionRejoinSet2 = (Set<ExamStudentDetentionRejoinDetails>)student3.getExamStudentDetentionRejoinDetails();
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
                    if (student3.getAdmAppln().getIsCancelled() || !student3.getIsAdmitted()) {
                        continue;
                    }
                    studentsGotTcList.add(student3);
                    studentToList.add(detailsTo);
                }
            }
        }
        return studentToList;
    }
    
    private String convertBirthDateToWord(final String dobFigures) {
        String inWords = "";
        final StringTokenizer str = new StringTokenizer(dobFigures, "/");
        final int date = Integer.parseInt(str.nextToken());
        final int month = Integer.parseInt(str.nextToken());
        final String year = str.nextToken();
        final String year1 = year.substring(0, 2);
        final String year2 = year.substring(2);
        if (Integer.parseInt(year)<2010 && Integer.parseInt(year)>1999) {
			inWords=NumberToWordConvertor.getDate(date)+" "+CommonUtil.getMonthForNumber(month)+" "+NumberToWordConvertor.convertNumber(year).toUpperCase();
		}else{
			inWords=NumberToWordConvertor.getDate(date)+" "+CommonUtil.getMonthForNumber(month)+" "+NumberToWordConvertor.convertNumber(year1).toUpperCase()+" "+NumberToWordConvertor.convertNumber(year2);
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
    
    public List<PrintTcDetailsTo> convertStudentBoToToForReprintOnlyTc(final List<Student> studentBoList, final HttpServletRequest request, final TransferCertificateForm certificateForm) throws Exception {
        final List<PrintTcDetailsTo> studentToList = new ArrayList<PrintTcDetailsTo>();
        if (studentBoList != null) {
            for (final Student student : studentBoList) {
                final PrintTcDetailsTo detailsTo = new PrintTcDetailsTo();
                if (student.getStudentTCDetails().size() != 0) {
                    final Integer admittedSem = TransferCertificateHandler.getInstance().getAdmittedSemester(student.getId());
                    final BigInteger rejoinYear = TransferCertificateHandler.getInstance().getRejoinYear(student.getId());
                    detailsTo.setStudentNo(student.getStudentNo());
                    detailsTo.setRegNo(student.getRegisterNo());
                    detailsTo.setCourse(student.getAdmAppln().getCourseBySelectedCourseId().getName());
                    detailsTo.setAdmissionnumber(student.getAdmAppln().getAdmissionNumber());
                    String schemeInWords = this.getSchemeinWords(student.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName(), student.getClassSchemewise().getClasses().getTermNumber());
                    String className = "";
                    if (schemeInWords != null) {
                        className = schemeInWords;
                    }
                    if (student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        className = String.valueOf(className) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                    }
                    detailsTo.setClassName(className);
                    final String courseScheme = TransferCertificateHandler.getInstance().getCourseScheme(student.getAdmAppln().getCourseBySelectedCourseId().getId(), student.getAdmAppln().getAppliedYear());
                    schemeInWords = this.getSchemeinWords(courseScheme, admittedSem);
                    className = "";
                    if (schemeInWords != null) {
                        className = schemeInWords;
                    }
                    if (student.getClassSchemewise().getClasses().getCourse() != null && student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard() != null) {
                        if (certificateForm.getToCollege() != null && certificateForm.getToCollege().equalsIgnoreCase("St Berchmans")) {
                            if (student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 3 || student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId() == 9) {
                                className = student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                                certificateForm.setFlag(true);
                            }
                            else {
                                className = String.valueOf(className) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                            }
                        }
                        else if (certificateForm.getToCollege() != null && certificateForm.getToCollege().equalsIgnoreCase("Cjc")) {
                            className = String.valueOf(className) + " " + student.getClassSchemewise().getClasses().getCourse().getCourseMarksCard();
                        }
                    }
                    detailsTo.setAdmissionClass(className);
                    if (student.getAdmAppln().getAdmissionDate() != null) {
                        detailsTo.setDateOfAdmission(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getAdmissionDate()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                    }
                    if (certificateForm.getTcReDate() != null && !certificateForm.getTcReDate().isEmpty()) {
                        detailsTo.setTcDate(certificateForm.getTcReDate());
                    }
                    else {
                        detailsTo.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getTcDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                    }
                    if (rejoinYear != null) {
                        detailsTo.setAdmittedYear(String.valueOf(student.getRegisterNo()) + "/" + rejoinYear + "-" + (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    else {
                        detailsTo.setAdmittedYear(String.valueOf(student.getRegisterNo()) + "/" + student.getAdmAppln().getAppliedYear().toString() + "-" + (student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1));
                    }
                    for (final StudentTCDetails tcDetails : student.getStudentTCDetails()) {
                        detailsTo.setStudentName(tcDetails.getStudentName());
                        detailsTo.setDobFigures(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd-MM-yyyy"));
                        final String dobInWords1 = this.convertBirthDateToWord(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        detailsTo.setDobWords(dobInWords1);
                        detailsTo.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfLeaving()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        if (tcDetails.getClassOfLeaving() != null && !tcDetails.getClassOfLeaving().isEmpty()) {
                            detailsTo.setClassOfLeaving(tcDetails.getClassOfLeaving());
                        }
                        detailsTo.setLeavingYear(detailsTo.getDateOfLeaving().substring(6));
                        if (tcDetails.getPassed() != null && tcDetails.getPassed().equalsIgnoreCase("no")) {
                            detailsTo.setPassed(tcDetails.getSubjectPassed());
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
                            detailsTo.setConduct(tcDetails.getCharacterAndConduct().getName());
                        }
                        detailsTo.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfApplication()), "dd-MMM-yyyy", "dd/MM/yyyy"));
                        if (tcDetails.getDateOfIssue() != null) {
                            detailsTo.setDateOfIssue(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(tcDetails.getDateOfIssue()), "dd-MMM-yyyy", "dd MMMMM yyyy"));
                        }
                        String examRegNo = "";
                        if (student.getExamRegisterNo() != null && !student.getExamRegisterNo().trim().isEmpty()) {
                            examRegNo = student.getExamRegisterNo();
                        }
                        else {
                            examRegNo = student.getRegisterNo();
                        }
                        if (student.getTcType() != null && student.getTcType().equalsIgnoreCase("Discontinued")) {
                            detailsTo.setRegMonthYear("-------");
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
                        if (tcDetails.getIsFeePaidUni() != null && tcDetails.getIsFeePaidUni()) {
                            detailsTo.setFeeDueToUni("Yes");
                        }
                        else {
                            detailsTo.setFeeDueToUni("No");
                        }
                        if (tcDetails.getClassOfLeaving() != null && !tcDetails.getClassOfLeaving().isEmpty()) {
                            detailsTo.setClassOfLeaving(tcDetails.getClassOfLeaving());
                        }
                        if (tcDetails.getPublicExaminationName() != null) {
                            detailsTo.setPublicExamName(tcDetails.getPublicExaminationName());
                        }
                    }
                    detailsTo.setTcNo(student.getTcNo());
                    detailsTo.setTcType(student.getTcType());
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
                    student.setTcNo(detailsTo.getTcNo());
                    student.setTcType(detailsTo.getTcType());
                    studentToList.add(detailsTo);
                }
            }
        }
        return studentToList;
    }
    
    private String convertIntegerToWordForCJC(final String dobFigures) {
        String inWords = "";
        final StringTokenizer str = new StringTokenizer(dobFigures, "-");
        final int date = Integer.parseInt(str.nextToken());
        final int month = Integer.parseInt(str.nextToken());
        final String year = str.nextToken();
        final String year2 = year.substring(0, 2);
        final String year3 = year.substring(2);
        inWords = String.valueOf(NumberToWordConvertor.getDate(date)) + ", " + CommonUtil.getMonthForNumber(month) + ", " + NumberToWordConvertor.convertNumber(year2).toUpperCase() + " HUNDRED " + NumberToWordConvertor.convertNumber(year3);
        final char firstChar = inWords.charAt(0);
        String result = "";
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
    
    public List<TCDetailsTO> convertStudentTcDetailsBoToToForReprintOnlyTc(final List<StudentTCDetails> studentBo, final HttpServletRequest request, final TransferCertificateForm certificateForm) {
        final List<TCDetailsTO> to = new ArrayList<TCDetailsTO>();
        if (studentBo != null) {
            for (final StudentTCDetails bo : studentBo) {
                final TCDetailsTO detailsTo = new TCDetailsTO();
                detailsTo.setStudentId(bo.getStudent().getId());
                detailsTo.setStudentName(bo.getStudentName());
                detailsTo.setRollNo(bo.getStudent().getRollNo());
                detailsTo.setClassId(bo.getStudent().getClassSchemewise().getClasses().getId());
                detailsTo.setCourse(bo.getStudent().getClassSchemewise().getClasses().getCourse().getName());
                to.add(detailsTo);
            }
        }
        return to;
    }
}