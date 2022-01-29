package com.kp.cms.bo.admin;

import java.util.Date;
import java.io.Serializable;

public class StudentTCDetails implements Serializable
{
    private int id;
    private Student student;
    private CharacterAndConduct characterAndConduct;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;
    private String passed;
    private String feePaid;
    private Date dateOfApplication;
    private Date dateOfLeaving;
    private String reasonOfLeaving;
    private String month;
    private Integer year;
    private String scholarship;
    private String eligible;
    private String subjectStudied;
    private String subjectPassed;
    private String publicExaminationName;
    private String showRegNo;
    private Boolean isFeePaidUni;
    private String classOfLeaving;
    private String promotionToNextClass;
    private String classsubjectOfJoining;
    private String examMonth;
    private Integer examYear;
    private String studentName;
    private Date dateOfBirth;
    private Classes classes;
    private String tcNo;
    private Date tcDate;
    private String admissionNo;
    private String admissionDate;
    private Date dateOfIssue;
    
    public String getStudentName() {
        return this.studentName;
    }
    
    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }
    
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(final Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public StudentTCDetails() {
    }
    
    public StudentTCDetails(final int id, final Student student, final CharacterAndConduct characterAndConduct, final String createdBy, final Date createdDate, final String modifiedBy, final Date lastModifiedDate, final Boolean isActive, final String passed, final String feePaid, final Date dateOfApplication, final Date dateOfLeaving, final String reasonOfLeaving, final String month, final Integer year, final String scholarship, final String eligible, final String subjectStudied, final String subjectPassed, final String publicExaminationName, final String showRegNo, final Boolean isFeePaidUni, final String classOfLeaving, final String promotionToNextClass, final String classsubjectOfJoining, final String examMonth, final Integer examYear, final String studentName, final Date dateOfBirth, final Date dateOfIssue) {
        this.id = id;
        this.student = student;
        this.characterAndConduct = characterAndConduct;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.isActive = isActive;
        this.passed = passed;
        this.feePaid = feePaid;
        this.dateOfApplication = dateOfApplication;
        this.dateOfLeaving = dateOfLeaving;
        this.reasonOfLeaving = reasonOfLeaving;
        this.month = month;
        this.year = year;
        this.scholarship = scholarship;
        this.eligible = eligible;
        this.subjectStudied = subjectStudied;
        this.subjectPassed = subjectPassed;
        this.publicExaminationName = publicExaminationName;
        this.showRegNo = showRegNo;
        this.isFeePaidUni = isFeePaidUni;
        this.classOfLeaving = classOfLeaving;
        this.promotionToNextClass = promotionToNextClass;
        this.classsubjectOfJoining = classsubjectOfJoining;
        this.examMonth = examMonth;
        this.examYear = examYear;
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfIssue = dateOfIssue;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public Student getStudent() {
        return this.student;
    }
    
    public void setStudent(final Student student) {
        this.student = student;
    }
    
    public CharacterAndConduct getCharacterAndConduct() {
        return this.characterAndConduct;
    }
    
    public void setCharacterAndConduct(final CharacterAndConduct characterAndConduct) {
        this.characterAndConduct = characterAndConduct;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }
    
    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public Boolean getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }
    
    public String getPassed() {
        return this.passed;
    }
    
    public void setPassed(final String passed) {
        this.passed = passed;
    }
    
    public String getFeePaid() {
        return this.feePaid;
    }
    
    public void setFeePaid(final String feePaid) {
        this.feePaid = feePaid;
    }
    
    public Date getDateOfApplication() {
        return this.dateOfApplication;
    }
    
    public void setDateOfApplication(final Date dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }
    
    public Date getDateOfLeaving() {
        return this.dateOfLeaving;
    }
    
    public void setDateOfLeaving(final Date dateOfLeaving) {
        this.dateOfLeaving = dateOfLeaving;
    }
    
    public String getReasonOfLeaving() {
        return this.reasonOfLeaving;
    }
    
    public void setReasonOfLeaving(final String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
    }
    
    public String getMonth() {
        return this.month;
    }
    
    public void setMonth(final String month) {
        this.month = month;
    }
    
    public Integer getYear() {
        return this.year;
    }
    
    public void setYear(final Integer year) {
        this.year = year;
    }
    
    public String getScholarship() {
        return this.scholarship;
    }
    
    public void setScholarship(final String scholarship) {
        this.scholarship = scholarship;
    }
    
    public String getEligible() {
        return this.eligible;
    }
    
    public void setEligible(final String eligible) {
        this.eligible = eligible;
    }
    
    public String getSubjectStudied() {
        return this.subjectStudied;
    }
    
    public void setSubjectStudied(final String subjectStudied) {
        this.subjectStudied = subjectStudied;
    }
    
    public String getSubjectPassed() {
        return this.subjectPassed;
    }
    
    public void setSubjectPassed(final String subjectPassed) {
        this.subjectPassed = subjectPassed;
    }
    
    public String getPublicExaminationName() {
        return this.publicExaminationName;
    }
    
    public void setPublicExaminationName(final String publicExaminationName) {
        this.publicExaminationName = publicExaminationName;
    }
    
    public String getShowRegNo() {
        return this.showRegNo;
    }
    
    public void setShowRegNo(final String showRegNo) {
        this.showRegNo = showRegNo;
    }
    
    public Boolean getIsFeePaidUni() {
        return this.isFeePaidUni;
    }
    
    public void setIsFeePaidUni(final Boolean isFeePaidUni) {
        this.isFeePaidUni = isFeePaidUni;
    }
    
    public void setClassOfLeaving(final String classOfLeaving) {
        this.classOfLeaving = classOfLeaving;
    }
    
    public String getClassOfLeaving() {
        return this.classOfLeaving;
    }
    
    public String getPromotionToNextClass() {
        return this.promotionToNextClass;
    }
    
    public void setPromotionToNextClass(final String promotionToNextClass) {
        this.promotionToNextClass = promotionToNextClass;
    }
    
    public String getClasssubjectOfJoining() {
        return this.classsubjectOfJoining;
    }
    
    public void setClasssubjectOfJoining(final String classsubjectOfJoining) {
        this.classsubjectOfJoining = classsubjectOfJoining;
    }
    
    public String getExamMonth() {
        return this.examMonth;
    }
    
    public void setExamMonth(final String examMonth) {
        this.examMonth = examMonth;
    }
    
    public Integer getExamYear() {
        return this.examYear;
    }
    
    public void setExamYear(final Integer i) {
        this.examYear = i;
    }
    
    public Classes getClasses() {
        return this.classes;
    }
    
    public void setClasses(final Classes classes) {
        this.classes = classes;
    }
    
    public String getTcNo() {
        return this.tcNo;
    }
    
    public void setTcNo(final String tcNo) {
        this.tcNo = tcNo;
    }
    
    public Date getTcDate() {
        return this.tcDate;
    }
    
    public void setTcDate(final Date tcDate) {
        this.tcDate = tcDate;
    }
    
    public String getAdmissionNo() {
        return this.admissionNo;
    }
    
    public void setAdmissionNo(final String admissionNo) {
        this.admissionNo = admissionNo;
    }
    
    public Date getDateOfIssue() {
        return this.dateOfIssue;
    }
    
    public void setDateOfIssue(final Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
    
    public String getAdmissionDate() {
        return this.admissionDate;
    }
    
    public void setAdmissionDate(final String admissionDate) {
        this.admissionDate = admissionDate;
    }
}