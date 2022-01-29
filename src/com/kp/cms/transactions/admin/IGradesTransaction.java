package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Grade;
import com.kp.cms.forms.admin.GradesForm;

public interface IGradesTransaction  {
	public List<Grade> geGrades() throws Exception;
	public boolean addGrades(Grade grade) throws Exception;	
	public boolean deleteGrade(int id,Boolean activate,GradesForm gradesForm) throws Exception;
	public boolean updateGrades(Grade grade);
	public Grade isGradeDuplcated(Grade duplGrade) throws Exception;
	public Grade isMarkDuplcated(Grade duplGrade) throws Exception;
}
