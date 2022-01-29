package com.kp.cms.transactions.sap;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.sap.SapRegistration;

public interface IAssignSapKeyTransaction {

	List<SapRegistration> getRegisteredStudentsForSap(Date convertStringToSQLDate, Date convertStringToSQLDate2,String status, String classId)throws Exception;

	Map<Integer,List<Integer>> getKeys()throws Exception;

	boolean updateSapRegistration(List<SapRegistration> sapRegistrations)throws Exception;

	List<SapRegistration> getStudentsWhoHadUpdatedSapKeys(
			List<Integer> updatedStudents)throws Exception;

	Map<Integer, SapRegistration> getSapRegistration()throws Exception;

	List<Integer> getAdminApplIds()throws Exception;

	Map<Integer, String> getclassMap()throws Exception;

}
