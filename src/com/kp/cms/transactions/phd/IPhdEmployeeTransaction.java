package com.kp.cms.transactions.phd;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.forms.phd.PhdEmployeeForms;
import com.kp.cms.to.employee.EmpQualificationLevelTo;

public interface IPhdEmployeeTransaction {

	Map<String, String> getDesignationMap() throws Exception;

	Map<String, String> getReligionMap() throws Exception;

	Map<String, String> getDepartmentMap() throws Exception;

	Map<String, String> getCountryMap() throws Exception;
	
	Map<String, String> getStatesMap() throws Exception;

	Map<String, String> getNationalityMap() throws Exception;

	Map<String, String> getQualificationMap() throws Exception;

	List<EmpQualificationLevelTo> getQualificationFixedMap() throws Exception;

	Map<String, String> guideShipMap() throws Exception;

	boolean savePhdEmployee(PhdEmployee employee) throws Exception;

	List<PhdEmployee> getPhdDetailsList(PhdEmployeeForms objform) throws Exception;

	PhdEmployee getPhdEmployeeById(int id) throws Exception;

	boolean deletePhdemployee(int id) throws Exception;

	Map<String, String> getQualificationLevelMap() throws Exception;


}
