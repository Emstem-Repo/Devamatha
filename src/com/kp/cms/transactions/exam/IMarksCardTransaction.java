package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCardNoGen;
import com.kp.cms.bo.exam.MarksCardNoGen;
import com.kp.cms.bo.exam.MarksCardSiNo;
import com.kp.cms.bo.exam.MarksCardSiNoGen;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.forms.exam.ConsolidateMarksCardForm;
import com.kp.cms.forms.exam.MarksCardForm;

public interface IMarksCardTransaction {

	int getStudentCount(MarksCardForm marksCardForm) throws Exception;

	int getCurrentNO(MarksCardForm marksCardForm)throws Exception;

	boolean getUpdateSI(String totalCount,MarksCardForm marksCardForm) throws Exception;

	int getAcademicId(String year)throws Exception;

	List<Integer> getStudentId(MarksCardForm marksCardForm)throws Exception;

	boolean updateStudent(List<MarksCardNoGen> boList)throws Exception;

	Map<Integer, String> getStudentList(MarksCardForm marksCardForm)throws Exception;

	boolean checkRegNo(MarksCardForm marksCardForm)throws Exception;

	void updateSingleStudent(MarksCardNoGen bo)throws Exception;

	Student getStudentBoDetails(int studentId)throws Exception;

	MarksCardNoGen getMarksCardNoGen(MarksCardForm marksCardForm,List<Integer> studentId)throws Exception;
	
	public int getConsolidateCurrentNO(ConsolidateMarksCardForm consolidateMarksCardForm)throws Exception;
	public List<Integer> getStudentDetails(ConsolidateMarksCardForm consolidateMarksCardForm)throws Exception;
	public Map<Integer, String> getStudentDetailList(ConsolidateMarksCardForm consolidateMarksCardForm)throws Exception;
	public boolean updateConsolidate(List<ConsolidateMarksCardNoGen> boList)throws Exception;
	public boolean getUpdate(String totalCount,ConsolidateMarksCardForm consolidateMarksCardForm) throws Exception;
	
	boolean updateStudent1(List<MarksCardSiNoGen> boList)throws Exception;
	List<StudentFinalMarkDetails> getStudentIdForExamType(MarksCardForm marksCardForm)throws Exception;
	public boolean validateGenerateNoForExamType(MarksCardForm marksCardForm)throws Exception;
	Map<Integer, MarksCardSiNoGen> getStudentList1(MarksCardForm marksCardForm)throws Exception;
	boolean updateStudentForExamType(MarksCardForm marksCardForm)throws Exception;
	public List<MarksCardSiNoGen> getDataAvailable(MarksCardForm marksCardForm)throws Exception;
	public boolean updateSingleStudentDuplicate(MarksCardSiNoGen bo)throws Exception;
	
	public Classes getClassBO(int classId)throws Exception;
	
	// by Arun Sudhakaran
	public int getProgramTypeAndScheme(MarksCardForm marksCardForm) throws Exception;
	public int getCurrentNO(int academicYear, int programType, String schemes) throws Exception;
	public List<Integer> getClassIds(int academicYear, String schemes) throws Exception;
	public List<StudentFinalMarkDetails> getStudentId(MarksCardForm marksCardForm, String classIds)throws Exception;
	public Map<Integer, String> getStudentList(MarksCardForm marksCardForm, String classIds)throws Exception;
	public List<StudentFinalMarkDetails> getStudentIdForExamType(MarksCardForm marksCardForm, String classIds)throws Exception;
	public boolean validateGenerateNoForExamType(MarksCardForm marksCardForm, String classIds)throws Exception;
	public Map<Integer, MarksCardSiNoGen> getStudentList1(MarksCardForm marksCardForm, String classIds)throws Exception;
	public List<MarksCardSiNoGen> getDataAvailable(MarksCardForm marksCardForm, String classIds)throws Exception;
	public boolean isMarksCardNumberGenerated(MarksCardForm marksCardForm, String classIds) throws Exception;
	public Map<Integer, String> getClassIds(MarksCardForm marksCardForm) throws Exception;
	public Set<Integer> getClassIdsByExamScehemeAcademicYear(MarksCardForm marksCardForm) throws Exception;
}
