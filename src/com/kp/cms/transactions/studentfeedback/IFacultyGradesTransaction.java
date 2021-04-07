package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.studentfeedback.FacultyGrades;
import com.kp.cms.forms.studentfeedback.FacultyGradesForm;

public interface IFacultyGradesTransaction {
	
	public boolean addFacultyGrades(FacultyGrades facultyGrades)throws Exception;
	
	public List<FacultyGrades> getFacultyGradesDetails()throws Exception;
	
	public boolean deleteFacultyGrades(int id, String userId)throws Exception;
	
	public boolean updateFacultyGrades(FacultyGrades facultyGrades)throws Exception;
	
	public boolean reActivateFacultyGrades(String grade, String userId)throws Exception;
	
	public FacultyGrades getDetailsonId(int id)throws Exception ;

	public FacultyGrades checkForDuplicateonGrade(String grade)throws Exception;
	
	public boolean checkForDuplicateonGrade1(String grade, ActionErrors errors, FacultyGradesForm facultyGradesForm)throws Exception;

}
