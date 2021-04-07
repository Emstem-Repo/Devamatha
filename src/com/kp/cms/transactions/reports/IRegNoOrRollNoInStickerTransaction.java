package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IRegNoOrRollNoInStickerTransaction {
	public List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo, int progTypeId) throws Exception;
	public List<Student> getRequiredRollNos(String rollNoFrom, String rollNoTo, int progTypeId) throws Exception;
}
