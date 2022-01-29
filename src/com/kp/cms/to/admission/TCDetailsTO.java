package com.kp.cms.to.admission;

import java.io.Serializable;

public class TCDetailsTO implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int studentId;
    private String studentName;
    private String registerNo;
    private String conductId;
    private String passed;
    private String feePaid;
    private String dateOfApplication;
    private String reasonOfLeaving;
    private String dateOfLeaving;
    private String scholarship;
    private String characterId;
    private String month;
    private String year;
    private String eligible;
    private String subjectStudied;
    private String subjectPassed;
    private String publicExamName;
    private String showRegisterNo;
    private String feePaidUni;
    private String classOfLeaving;
    private String classsubjectOfJoining;
    private String promotionToNextClass;
    private String secondLanguage;
    private String admissionNo;
    private String examMonth;
    private Integer examYear;
    private Integer classId;
    private String course;
    private String dateOfBirth;
    private String rollNo;
    private String tcNo;
    private String admissionDate;
    private String dateOfIssue;
    
    public String getRollNo() {
        return this.rollNo;
    }
    
    public void setRollNo(final String rollNo) {
        this.rollNo = rollNo;
    }
    
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getCourse() {
        return this.course;
    }
    
    public void setCourse(final String course) {
        this.course = course;
    }
    
    public Integer getClassId() {
        return this.classId;
    }
    
    public void setClassId(final Integer classId) {
        this.classId = classId;
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
    
    public int getStudentId() {
        return this.studentId;
    }
    
    public void setStudentId(final int studentId) {
        this.studentId = studentId;
    }
    
    public String getRegisterNo() {
        return this.registerNo;
    }
    
    public void setRegisterNo(final String registerNo) {
        this.registerNo = registerNo;
    }
    
    public String getConductId() {
        return this.conductId;
    }
    
    public void setConductId(final String conductId) {
        this.conductId = conductId;
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
    
    public String getDateOfApplication() {
        return this.dateOfApplication;
    }
    
    public void setDateOfApplication(final String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }
    
    public String getReasonOfLeaving() {
        return this.reasonOfLeaving;
    }
    
    public void setReasonOfLeaving(final String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
    }
    
    public String getDateOfLeaving() {
        return this.dateOfLeaving;
    }
    
    public void setDateOfLeaving(final String dateOfLeaving) {
        this.dateOfLeaving = dateOfLeaving;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getScholarship() {
        return this.scholarship;
    }
    
    public void setScholarship(final String scholarship) {
        this.scholarship = scholarship;
    }
    
    public String getCharacterId() {
        return this.characterId;
    }
    
    public void setCharacterId(final String characterId) {
        this.characterId = characterId;
    }
    
    public String getMonth() {
        return this.month;
    }
    
    public void setMonth(final String month) {
        this.month = month;
    }
    
    public String getYear() {
        return this.year;
    }
    
    public void setYear(final String year) {
        this.year = year;
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
    
    public void setSubjectPassed(final String s) {
        this.subjectPassed = s;
    }
    
    public String getPublicExamName() {
        return this.publicExamName;
    }
    
    public void setPublicExamName(final String publicExamName) {
        this.publicExamName = publicExamName;
    }
    
    public String getShowRegisterNo() {
        return this.showRegisterNo;
    }
    
    public void setShowRegisterNo(final String showRegisterNo) {
        this.showRegisterNo = showRegisterNo;
    }
    
    public String getFeePaidUni() {
        return this.feePaidUni;
    }
    
    public void setFeePaidUni(final String feePaidUni) {
        this.feePaidUni = feePaidUni;
    }
    
    public void setClassOfLeaving(final String classOfLeaving) {
        this.classOfLeaving = classOfLeaving;
    }
    
    public String getClassOfLeaving() {
        return this.classOfLeaving;
    }
    
    public String getStudentName() {
        return this.studentName;
    }
    
    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }
    
    public String getSecondLanguage() {
        return this.secondLanguage;
    }
    
    public void setSecondLanguage(final String secondLanguage) {
        this.secondLanguage = secondLanguage;
    }
    
    public String getAdmissionNo() {
        return this.admissionNo;
    }
    
    public void setAdmissionNo(final String admissionNo) {
        this.admissionNo = admissionNo;
    }
    
    public String getClasssubjectOfJoining() {
        return this.classsubjectOfJoining;
    }
    
    public void setClasssubjectOfJoining(final String classsubjectOfJoining) {
        this.classsubjectOfJoining = classsubjectOfJoining;
    }
    
    public String getPromotionToNextClass() {
        return this.promotionToNextClass;
    }
    
    public void setPromotionToNextClass(final String promotionToNextClass) {
        this.promotionToNextClass = promotionToNextClass;
    }
    
    public String getTcNo() {
        return this.tcNo;
    }
    
    public void setTcNo(final String tcNo) {
        this.tcNo = tcNo;
    }
    
    public String getDateOfIssue() {
        return this.dateOfIssue;
    }
    
    public void setDateOfIssue(final String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
    
    public String getAdmissionDate() {
        return this.admissionDate;
    }
    
    public void setAdmissionDate(final String admissionDate) {
        this.admissionDate = admissionDate;
    }
}