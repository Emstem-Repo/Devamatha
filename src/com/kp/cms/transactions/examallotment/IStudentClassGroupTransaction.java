package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.GroupClasses;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.forms.examallotment.StudentClassGroupForm;

public interface IStudentClassGroupTransaction {

	public List<EmployeeWorkLocationBO> getWorkLocations() throws Exception;

	public List<ClassGroup> getClassGroupList() throws Exception;

	public List<Classes> getclassesByYearAndLocationId(StudentClassGroupForm classGroupForm) throws Exception;

	public List<Student> getStudentDetailsByClasses(StudentClassGroupForm classGroupForm,List<Integer> selectedClassList) throws Exception;

	public boolean addStudentToClassGroup(List<GroupClasses> groupClassList)throws Exception;

	public List<GroupClasses> getStudentClassGroup(int year) throws Exception;

	public List<StudentClassGroup> getStudentFromGroupClassByClasses(List<Integer> selectedClassList) throws Exception;

	public List<GroupClasses> getGroupClassesByGroupId(int groupId)throws Exception;

	public GroupClasses getGroupClassByGroupIdAndClassId(String classGroup,String classId)throws Exception;

	public List<StudentClassGroup> getStudentFromGroupClassByClassesAndGroupId(List<Integer> selectedClassList, String classGroup)throws Exception;

	public boolean updateStudentToClassGroup(List<GroupClasses> groupClassList)throws Exception;

	public List<Integer> getStudentFromOtherGroupClass(List<Integer> classIdList,List<Integer> studentIdList)throws Exception;

	public boolean deleteGroupClassStudent(List<GroupClasses> groupClassList2)throws Exception;

}
