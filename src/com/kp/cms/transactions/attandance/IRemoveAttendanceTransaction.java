package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceStudent;

public interface IRemoveAttendanceTransaction {

	List<AttendanceStudent> getAttStudentList(String query) throws Exception;

	boolean saveAttendanceReEntry(List<Integer> finalList, String userId) throws Exception;

}
