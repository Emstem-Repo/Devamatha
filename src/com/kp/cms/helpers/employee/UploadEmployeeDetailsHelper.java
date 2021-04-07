package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.to.employee.UploadEmployeeDetailsTo;
import com.kp.cms.utilities.CommonUtil;

public class UploadEmployeeDetailsHelper {
	
	private static volatile UploadEmployeeDetailsHelper instance=null;
	
	/**
	 * 
	 */
	private UploadEmployeeDetailsHelper(){}
	
	
	/**
	 * @return
	 */
	public static UploadEmployeeDetailsHelper getInstance(){
		if(instance==null){
			instance=new UploadEmployeeDetailsHelper();
		}
		return instance;
	}


	public List<Employee> convertToListToBoList(List<UploadEmployeeDetailsTo> result, String userid, Map<String, Employee> employeeMap) throws Exception{
		// TODO Auto-generated method stub
		List<Employee> employeeList=new ArrayList<Employee>();
		Employee employee=null;
		
		if(result!=null){
			Iterator<UploadEmployeeDetailsTo> iterator=result.iterator();
			while(iterator.hasNext()){
				UploadEmployeeDetailsTo uploadEmployeeDetailsTo=iterator.next();
				if(uploadEmployeeDetailsTo!=null && employeeMap!=null ){
					if(uploadEmployeeDetailsTo.getEid()!=null && !uploadEmployeeDetailsTo.getEid().isEmpty())
					employee=employeeMap.get(uploadEmployeeDetailsTo.getEid());
					if(uploadEmployeeDetailsTo.getEid()!=null && !uploadEmployeeDetailsTo.getEid().isEmpty()){
						employee.setId(Integer.parseInt(uploadEmployeeDetailsTo.getEid()));
					}else{
						employee=new Employee();
						//employee.setActive(true);
					}
					
					if(uploadEmployeeDetailsTo.getName()!=null && !uploadEmployeeDetailsTo.getName().isEmpty()){
						employee.setFirstName(uploadEmployeeDetailsTo.getName());
					//	employee.setMiddleName(null);
						//employee.setLastName(null);
					}
					
					if(uploadEmployeeDetailsTo.getEmailId()!=null && !uploadEmployeeDetailsTo.getEmailId().isEmpty()){
						//employee.setEmail(uploadEmployeeDetailsTo.getEmailId());
						employee.setWorkEmail(uploadEmployeeDetailsTo.getEmailId());
						
					}
					
					if(uploadEmployeeDetailsTo.getDob()!=null && !uploadEmployeeDetailsTo.getDob().isEmpty()){
						employee.setDob(CommonUtil.ConvertStringToDate(uploadEmployeeDetailsTo.getDob()));
					}
					
					if(uploadEmployeeDetailsTo.getBloodGroup()!=null && !uploadEmployeeDetailsTo.getBloodGroup().isEmpty()){
						employee.setBloodGroup(uploadEmployeeDetailsTo.getBloodGroup());
					}
					
					if(uploadEmployeeDetailsTo.getMobile()!=null && !uploadEmployeeDetailsTo.getMobile().isEmpty()){
						//employee.setPhone2(uploadEmployeeDetailsTo.getMobile());
						employee.setCurrentAddressMobile1(uploadEmployeeDetailsTo.getMobile());
					}
					
					if(uploadEmployeeDetailsTo.getDeptId()!=null && !uploadEmployeeDetailsTo.getDeptId().isEmpty()){
						Department department=new Department();
						department.setId(Integer.parseInt(uploadEmployeeDetailsTo.getDeptId()));
						employee.setDepartment(department);
					}
					
					if(uploadEmployeeDetailsTo.getDesignationId()!=null && !uploadEmployeeDetailsTo.getDesignationId().isEmpty()){
						Designation designation=new Designation();
						designation.setId(Integer.parseInt(uploadEmployeeDetailsTo.getDesignationId()));
						employee.setDesignation(designation);
					}
					
					if(uploadEmployeeDetailsTo.getPermanentAddressLine1()!=null && !uploadEmployeeDetailsTo.getPermanentAddressLine1().isEmpty()){
						employee.setPermanentAddressLine1(uploadEmployeeDetailsTo.getPermanentAddressLine1());
					}
					
					if(uploadEmployeeDetailsTo.getPermanentAddressLine2()!=null && !uploadEmployeeDetailsTo.getPermanentAddressLine2().isEmpty()){
						employee.setPermanentAddressLine2(uploadEmployeeDetailsTo.getPermanentAddressLine2());
					}
					
					if(uploadEmployeeDetailsTo.getPermanentCountryId()!=null && !uploadEmployeeDetailsTo.getPermanentCountryId().isEmpty()){
						Country country=new Country();
						country.setId(Integer.parseInt(uploadEmployeeDetailsTo.getPermanentCountryId()));
						employee.setCountryByPermanentAddressCountryId(country);
					}
					
					if(uploadEmployeeDetailsTo.getPermanentStateId()!=null && !uploadEmployeeDetailsTo.getPermanentStateId().isEmpty()){
						State state=new State();
						state.setId(Integer.parseInt(uploadEmployeeDetailsTo.getPermanentStateId()));
						employee.setStateByPermanentAddressStateId(state);
					}
					
					if(uploadEmployeeDetailsTo.getPermanentCity()!=null && !uploadEmployeeDetailsTo.getPermanentCity().isEmpty()){
						employee.setPermanentAddressCity(uploadEmployeeDetailsTo.getPermanentCity());
					}
					
					if(uploadEmployeeDetailsTo.getPermanentPinCode()!=null && !uploadEmployeeDetailsTo.getPermanentPinCode().isEmpty()){
						employee.setPermanentAddressZip(uploadEmployeeDetailsTo.getPermanentPinCode());
					}
					
					if(uploadEmployeeDetailsTo.getCurrentAddressLine1()!=null && !uploadEmployeeDetailsTo.getCurrentAddressLine1().isEmpty()){
						employee.setCommunicationAddressLine1(uploadEmployeeDetailsTo.getCurrentAddressLine1());
					}
					
					if(uploadEmployeeDetailsTo.getCurrentAddressLine2()!=null && !uploadEmployeeDetailsTo.getCurrentAddressLine2().isEmpty()){
						employee.setCommunicationAddressLine2(uploadEmployeeDetailsTo.getCurrentAddressLine2());
					}
					
					if(uploadEmployeeDetailsTo.getCurrentCountryId()!=null && !uploadEmployeeDetailsTo.getCurrentCountryId().isEmpty()){
						Country country=new Country();
						country.setId(Integer.parseInt(uploadEmployeeDetailsTo.getCurrentCountryId()));
						employee.setCountryByCommunicationAddressCountryId(country);
					}
					
					if(uploadEmployeeDetailsTo.getCurrentStateId()!=null && !uploadEmployeeDetailsTo.getCurrentStateId().isEmpty()){
						State state=new State();
						state.setId(Integer.parseInt(uploadEmployeeDetailsTo.getCurrentStateId()));
						employee.setStateByCommunicationAddressStateId(state);
					}
					
					if(uploadEmployeeDetailsTo.getCurrentCity()!=null && !uploadEmployeeDetailsTo.getCurrentCity().isEmpty()){
						employee.setCommunicationAddressCity(uploadEmployeeDetailsTo.getCurrentCity());
					}
					
					if(uploadEmployeeDetailsTo.getCurrentPinCode()!=null && !uploadEmployeeDetailsTo.getCurrentPinCode().isEmpty()){
						employee.setCommunicationAddressZip(uploadEmployeeDetailsTo.getCurrentPinCode());
					}
					
					if(uploadEmployeeDetailsTo.getFingerPrintId()!=null && !uploadEmployeeDetailsTo.getFingerPrintId().isEmpty()){
						employee.setFingerPrintId(uploadEmployeeDetailsTo.getFingerPrintId());
					}
					if(uploadEmployeeDetailsTo.getEmpTypeId()!=null && !uploadEmployeeDetailsTo.getEmpTypeId().isEmpty()){
						EmpType empType=new EmpType();
						empType.setId(Integer.parseInt(uploadEmployeeDetailsTo.getEmpTypeId()));
						employee.setEmptype(empType);
					}
					if(uploadEmployeeDetailsTo.getIsTeachingStaff()!=null && !uploadEmployeeDetailsTo.getIsTeachingStaff().isEmpty()){
						employee.setTeachingStaff(uploadEmployeeDetailsTo.getIsTeachingStaff().equalsIgnoreCase("1")?true:false);
					}
					if(uploadEmployeeDetailsTo.getWorkLocationId()!=null && !uploadEmployeeDetailsTo.getWorkLocationId().isEmpty()){
						EmployeeWorkLocationBO work=new EmployeeWorkLocationBO();
						work.setId(Integer.parseInt(uploadEmployeeDetailsTo.getWorkLocationId()));
						employee.setWorkLocationId(work);
					}
					if(uploadEmployeeDetailsTo.getStreamId()!=null && !uploadEmployeeDetailsTo.getStreamId().isEmpty()){
						EmployeeStreamBO stream=new EmployeeStreamBO();
						stream.setId(Integer.parseInt(uploadEmployeeDetailsTo.getStreamId()));
						employee.setStreamId(stream);
					}
				/*	if(uploadEmployeeDetailsTo.getGrade()!=null && !uploadEmployeeDetailsTo.getGrade().isEmpty()){
						employee.setGrade(uploadEmployeeDetailsTo.getGrade());
					}*/
					if(uploadEmployeeDetailsTo.getHighestQualification()!=null && !uploadEmployeeDetailsTo.getHighestQualification().isEmpty()){
						employee.setHighQualifForAlbum(uploadEmployeeDetailsTo.getHighestQualification());
					}
					if(uploadEmployeeDetailsTo.getReservationCategory()!=null && !uploadEmployeeDetailsTo.getReservationCategory().isEmpty()){
						employee.setReservationCategory(uploadEmployeeDetailsTo.getReservationCategory());
					}
					if(uploadEmployeeDetailsTo.getJoiningDate()!=null && !uploadEmployeeDetailsTo.getJoiningDate().isEmpty()){
					    employee.setDoj(CommonUtil.ConvertStringToDate(uploadEmployeeDetailsTo.getJoiningDate()));
					}
					if(uploadEmployeeDetailsTo.getDateOfRetirement()!=null && !uploadEmployeeDetailsTo.getDateOfRetirement().isEmpty()){
						employee.setRetirementDate(CommonUtil.ConvertStringToDate(uploadEmployeeDetailsTo.getDateOfRetirement()));
					}
					if(uploadEmployeeDetailsTo.getJobTitleId()!=null && !uploadEmployeeDetailsTo.getJobTitleId().isEmpty()){
						EmpJobTitle job=new EmpJobTitle();
						job.setId(Integer.parseInt(uploadEmployeeDetailsTo.getJobTitleId()));
						employee.setTitleId(job);
					}
					if(uploadEmployeeDetailsTo.getBankAccountNo()!=null && !uploadEmployeeDetailsTo.getBankAccountNo().isEmpty()){
						employee.setBankAccNo(uploadEmployeeDetailsTo.getBankAccountNo());
					}
					if(uploadEmployeeDetailsTo.getGender()!=null && !uploadEmployeeDetailsTo.getGender().isEmpty()){
						employee.setGender(uploadEmployeeDetailsTo.getGender());
					}
					employee.setCreatedBy(userid);
					employee.setModifiedBy(userid);
					employee.setCreatedDate(new Date());
					employee.setLastModifiedDate(new Date());
					employee.setActive(true);
					employee.setIsActive(true);
					employeeList.add(employee);
				}
			}
		}
		return employeeList;
	}

}
