package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

public interface IPrintShortageAttendanceTransaction {

	List<Object[]> getListOfDetails(String listQuery) throws Exception;
	public Map<Integer,Integer> getMapOfStudent(String listQuery) throws Exception;
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception;
}
