package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmployeeSearchForm;
import com.kp.cms.to.employee.EmployeeSearchTO;
import com.kp.cms.transactions.employee.IEmployeeSearchTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeSearchTxnImpl;


public class EmployeeSearchHandler {

	private static volatile EmployeeSearchHandler employeeSearchHandler = null;

	private EmployeeSearchHandler() {
	}

	public static EmployeeSearchHandler getInstance() {
		if (employeeSearchHandler == null) {
			employeeSearchHandler = new EmployeeSearchHandler();
		}
		return employeeSearchHandler;
	}
	private static final Log log = LogFactory.getLog(EmployeeSearchHandler.class);
	
	public List<EmployeeSearchTO> getSearchBy(EmployeeSearchForm searchForm) throws Exception {
		log.info("entering of getSearchBy in EmployeeSearchHandler class..");
		IEmployeeSearchTransaction searchTransaction = new EmployeeSearchTxnImpl();
		List<Object[]> list = searchTransaction.getEmployeeDetails(searchCritera(searchForm));
		List<EmployeeSearchTO> empList = populateBOtoTO(list);
		log.info("exit of getSearchBy in EmployeeSearchHandler class..");
		return empList;
	}
	
	
	/**
	 * Used to prepare the query
	 */
	public String searchCritera(EmployeeSearchForm searchForm)throws Exception{
		log.info("entering of searchCritera in EmployeeSearchHandler class..");
		String commonSearch = commonSearch();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(commonSearch);
		
		if(searchForm.getSearchBy().length() > 0){
			if(searchForm.getSearchBy().equals(CMSConstants.SEARCH_EMP_DEPARTMENT)){
				String dept= " e.department.name like '"+ searchForm.getSearchFor().trim()+"%'";
				buffer.append(dept);
			}
			else if(searchForm.getSearchBy().equals(CMSConstants.SEARCH_EMP_EMPID)){
				String empId = " e.code= '"+ searchForm.getSearchFor().trim()+"'";
				buffer.append(empId);
			}
			else if(searchForm.getSearchBy().equals(CMSConstants.SEARCH_EMP_EMPNAME)){
				
				String empName = " e.firstName like '"+ searchForm.getSearchFor().trim()+"%'";
				buffer.append(empName);
			}
		}	
		buffer.append(" and e.isActive = 1 order by e.id");
		log.info("exit of searchCritera in EmployeeSearchHandler class..");
		return buffer.toString();
	}
	
	public static String commonSearch()throws Exception{
		
		String search = "select e.id," +
				" e.code," +
				" e.firstName," +
				" e.middleName," +
				" e.lastName," +
				" e.department.name," +
				" empJob.jobTitle," +
				" empJob.employmentStatus," +
				" e.currentAddressMobile1," +
				" e.currentAddressMobile2," +
				" e.currentAddressMobile3," +
				" e.currentAddressHomeTelephone1," +
				" e.currentAddressHomeTelephone2," +
				" e.currentAddressHomeTelephone3," +
				" e.permanentAddressMobile1," +
				" e.permanentAddressMobile2," +
				" e.permanentAddressMobile3," +
				" e.permanentAddressHomeTelephone1," +
				" e.permanentAddressHomeTelephone2," +
				" e.permanentAddressHomeTelephone3" +
				" from Employee e" +
				" left join e.empJobs empJob" +
				" where";
		return search;
	}
	
	public List<EmployeeSearchTO> populateBOtoTO(List<Object[]> empBOList) throws Exception {
		log.info("entering of populateBOtoTO in EmployeeSearchHandler class..");
		EmployeeSearchTO employeeSearchTO = null;
		List<EmployeeSearchTO> empSearchList = new ArrayList<EmployeeSearchTO>();
		if(empBOList != null && !empBOList.isEmpty()){
			Iterator<Object[]> iterator = empBOList.iterator();
			while (iterator.hasNext()) {
				Object[] object =  iterator.next();
				int empId = 0;
				String empCode = "";
				String deptName = "";
				String firstname = "";
				String middlename = "";
				String lastname = "";
				String empStatus = "";
				String contactMobExt = "";
				String jobTitle = "";
				
				employeeSearchTO =  new EmployeeSearchTO();
				
				if(object[0]!=null){
					empId = (Integer)object[0];
					employeeSearchTO.setEmpId(empId);
				}
				if(object[1]!=null){
					empCode = (String)object[1];
					employeeSearchTO.setEmpCode(empCode);
				}
				if(object[2]!=null){
					firstname = (String)object[2];
				}
				if(object[3]!=null){
					middlename = (String)object[3];
				}
				if(object[4]!=null){
					lastname = (String)object[4];
				}
				employeeSearchTO.setEmpName(firstname + " " + middlename + " " + lastname);
				if(object[5]!=null){
					deptName = (String)object[5];
					employeeSearchTO.setDepartmentName(deptName);
				}
				if(object[6]!=null){
					jobTitle = (String)object[6];
					employeeSearchTO.setJobTitle(jobTitle);
				}
				if(object[7]!=null){
					empStatus = (String)object[7];
					employeeSearchTO.setJobStatus(empStatus);
				}
				employeeSearchTO.setJobTitle(jobTitle);
				employeeSearchTO.setJobStatus(empStatus);
				if(object[8]!=null && !object[8].toString().isEmpty() || object[9]!=null && !object[9].toString().isEmpty() || object[10]!=null && !object[10].toString().isEmpty()){
					contactMobExt = object[8].toString()+"-"+object[9].toString()+"-"+object[10].toString();
					employeeSearchTO.setContactMobileExt(contactMobExt);
				}else
				if(object[11]!=null && !object[11].toString().isEmpty() || object[12]!=null && !object[12].toString().isEmpty() || object[13]!=null && !object[13].toString().isEmpty()){
					contactMobExt = object[11].toString()+"-"+object[12].toString()+"-"+object[13].toString();
					employeeSearchTO.setContactMobileExt(String.valueOf(contactMobExt));
				}else
					if(object[14]!=null && !object[14].toString().isEmpty() || object[15]!=null && !object[15].toString().isEmpty() || object[16]!=null && !object[16].toString().isEmpty()){
						contactMobExt = object[14].toString()+"-"+object[15].toString()+"-"+object[16].toString();
						employeeSearchTO.setContactMobileExt(String.valueOf(contactMobExt));
					}else
						if(object[17]!=null && !object[17].toString().isEmpty() || object[18]!=null && !object[18].toString().isEmpty() || object[19]!=null && !object[19].toString().isEmpty()){
							contactMobExt = object[17].toString()+"-"+object[18].toString()+"-"+object[19].toString();
							employeeSearchTO.setContactMobileExt(String.valueOf(contactMobExt));
						}
				empSearchList.add(employeeSearchTO);
			}				
		}
		log.info("exit of populateBOtoTO in EmployeeSearchHandler class..");
		return empSearchList;
	}
}