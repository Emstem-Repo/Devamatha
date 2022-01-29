package com.kp.cms.transactions.admin;
import com.kp.cms.bo.admin.Student;
import java.util.*;

public interface ISendBulkSmsToStudentParents {
	List<Student> getStudents(int year, int progid,Set<Integer> list) throws Exception;
	public List<Student> getAllStudents(int accYear,int programType)
	throws Exception;
}
