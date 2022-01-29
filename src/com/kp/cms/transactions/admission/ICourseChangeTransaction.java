package com.kp.cms.transactions.admission;


import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;

public interface ICourseChangeTransaction {
	public boolean updateSelectedCourse(String selectedCourseID, String applicationNo, String year, String ModifiedBy) throws Exception;
	public AdmAppln getAdmAppln(int applnNo, String year) throws Exception;
	public List<Course> getCoursesByProgramType(int programTypeId)throws Exception;	
}
