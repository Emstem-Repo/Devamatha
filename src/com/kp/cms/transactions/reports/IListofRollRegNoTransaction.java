package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IListofRollRegNoTransaction {
	public List<Student> getListofRollRegdNos(int progTypeId, int classId) throws Exception;

}
