package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.forms.admin.CourseForm;

public interface ICourseTransaction {
	
	public Map getSubjectGroupsByCourse(int courseId);
	public boolean addCourse(Course course, String mode) throws Exception;
	public List<Course> getCourse(int id) throws Exception;
	public boolean deleteCourse(int courseId, Boolean activate, CourseForm courseForm) throws Exception;
	public boolean isCourseCodeDuplcated(String courseCode) throws Exception;
	public boolean isCourseNameDuplcated(CourseForm courseForm) throws Exception;
	public Course isCourseToBeActivated(CourseForm courseForm) throws Exception;	
	
	//Used to get all the active courses
	public List<Course> getActiveCourses()throws Exception;
	public List<Course> getCourses()throws Exception;
	public List<Department> getDepartment() throws Exception;
	public boolean saveDepts(List<CourseToDepartment> bolist) throws Exception;
	public Map<Integer,Integer> getCourseDepartmentList(CourseForm courseForm)throws Exception;
	public List<Course> getCoursesForAdmission(int programTypeId, boolean isAided) throws Exception;
}
