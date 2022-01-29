package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.GensmartCardDataForm;

public interface IGensmartCardDataTransaction {

	List<Student> getStudentList(GensmartCardDataForm genscDataForm) throws Exception;

	Map<String, byte[]> getStudentPhotos(List<Integer> studentIds) throws Exception;

	String getCourseName(int courseId) throws Exception;

	void update(List<Integer> studentIds,String user) throws Exception;

	List<Integer> getStudentsWithoutPhotosAndRegNos(GensmartCardDataForm genscForm) throws Exception;

	Map<String, String> getDepartmentMap() throws Exception;

	List<Employee> getEmployeeList(GensmartCardDataForm genscDataForm) throws Exception;

	List<Integer> getEmployeesWithoutPhotosAndRegNos(GensmartCardDataForm genscDataForm) throws Exception;

	String getDepartmentName(int deptId) throws Exception;

	Map<String, byte[]> getEmployeePhotos(List<Integer> employeeIds) throws Exception;

	void updateEmployee(List<Integer> empIdToSetGenerated,String user) throws Exception;

}
