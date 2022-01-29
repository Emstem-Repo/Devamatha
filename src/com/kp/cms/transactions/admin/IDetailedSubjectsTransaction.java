package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.DetailedSubjects;

public interface IDetailedSubjectsTransaction {
	
	public List<DetailedSubjects> getAllDetailedSubjects() throws Exception;
	public boolean addDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception;
	public List<DetailedSubjects> getDetailedsubjectsByCourse(int courseId) throws Exception;
	public boolean deleteDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception;
	public boolean actiavateDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception;
	public boolean updateDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception;
	public List<DetailedSubjects> getDetailedsubjectsByCourseAndName(int courseId,String name) throws Exception;
	public DetailedSubjects getDetailedSubjectsId(int id) throws Exception;
}
