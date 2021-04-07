
package com.kp.cms.transactions.admission;

import com.kp.cms.exceptions.ApplicationException;
import java.util.Date;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.admin.Student;
import java.util.List;

public interface ITCDetailsTransaction
{
    List<Student> getStudentDetails(final String p0) throws Exception;
    
    Student getStudentTCDetails(final String p0) throws Exception;
    
    boolean saveStudentTCDetails(final StudentTCDetails p0) throws Exception;
    
    List<CharacterAndConduct> getAllCharacterAndConduct() throws Exception;
    
    Boolean updateStudentTCDetails(final TCDetailsForm p0) throws Exception;
    
    List<SubjectGroup> getStudentSubjectGroup(final int p0) throws Exception;
    
    List<CurriculumSchemeDuration> getCurriculumSchemeDuration(final String p0, final int p1) throws Exception;
    
    List getStudentSubjects(final int p0) throws Exception;
    
    ExamDefinitionBO getStudentExamName(final int p0, final CurriculumSchemeDuration p1) throws Exception;
    
    String getSubjectsForAllStudentsByClass(final TCDetailsForm p0) throws Exception;
    
    ExamDefinitionBO getExamForAllStudentsByClass(final TCDetailsForm p0);
    
    Classes getClasses(final TCDetailsForm p0);
    
    boolean verifyGeneratedTCNumbers(final String p0) throws Exception;
    
    List getStudentFromTC(final int p0) throws Exception;
    
    boolean checkDuplicationOfTc(final TCDetailsForm p0) throws Exception;
    
    StudentTCDetails getStudentFromTcDetails(final TCDetailsForm p0) throws Exception;
    
    Student getStudentList(final String p0) throws Exception;
    
    void updateStudentsTcNo(final List<Student> p0) throws Exception;
    
    List<Integer> getDiscontinuedStudentId() throws Exception;
    
    boolean checkTcAvailable(final int p0) throws Exception;
    
    int getAdmApplnDetails(final TCDetailsForm p0) throws Exception;
    
    Date getDate(final int p0) throws Exception;
    
    String getStudentSubjectCode(final String p0) throws ApplicationException;
    
    StudentTCDetails getStudentTCDetailsByClass(final String classId) throws Exception;
}