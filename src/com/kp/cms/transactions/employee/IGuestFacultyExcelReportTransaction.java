package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.GuestFaculty;

public interface IGuestFacultyExcelReportTransaction {

	List<GuestFaculty> getSearchedEmployee(StringBuffer query)throws Exception;
}
