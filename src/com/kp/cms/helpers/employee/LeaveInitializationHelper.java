package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.LeaveInitializationForm;

public class LeaveInitializationHelper {
	/**
	 * Singleton object of LeaveInitializationHelper
	 */
	private static volatile LeaveInitializationHelper leaveInitializationHelper = null;
	private static final Log log = LogFactory.getLog(LeaveInitializationHelper.class);
	private LeaveInitializationHelper() {
		
	}
	/**
	 * return singleton object of LeaveInitializationHelper.
	 * @return
	 */
	public static LeaveInitializationHelper getInstance() {
		if (leaveInitializationHelper == null) {
			leaveInitializationHelper = new LeaveInitializationHelper();
		}
		return leaveInitializationHelper;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getEmployeeTypeQuery( String month) throws Exception {
		String query="from EmpType e where e.isActive=1 ";
		if(!month.trim().isEmpty()){
			query=query+" and e.leaveInitializeMonth='"+month+"'";
		}
		return query;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> convertBotoMap(List<EmpType> list) throws Exception{
		Map<Integer, String> empType=new HashMap<Integer, String>();
		if(list!=null && !list.isEmpty()){
			Iterator<EmpType> itr=list.iterator();
			while (itr.hasNext()) {
				EmpType bo = (EmpType) itr.next();
				empType.put(bo.getId(),bo.getName());
			}
		}
		return empType;
	}
	/**
	 * @param employeeTypeId
	 * @return
	 * @throws Exception
	 */
	public String getEmployeeForEmpType(String employeeTypeId) throws Exception{
		String query=" select e From Employee e where e.isActive=1 and e.emptype.id="+employeeTypeId;
		return query;
	}
	/**
	 * @param employeeTypeId
	 * @return
	 * @throws Exception
	 */
	public String getEmpLeaveAllotementForEmpType(String employeeTypeId) throws Exception{
		String query="from EmpLeaveAllotment e where e.isActive=1 and e.empType.id="+employeeTypeId;
		return query;
	}
	/**
	 * @param empList
	 * @param allotmentList
	 * @param leaveInitializationForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeave> convertTotoBoList(List<Employee> empList, List<EmpLeaveAllotment> allotmentList, 
			LeaveInitializationForm leaveInitializationForm,Map<String,EmpLeave> map,Map<Integer,EmpLeave> oldAlLeave,int accumulateLeaveTypeId) throws Exception {
		Map<Integer,EmpLeave> newAlLeave=new HashMap<Integer, EmpLeave>();
		List<EmpLeave> boList=new ArrayList<EmpLeave>();
		if(empList!=null && !empList.isEmpty()){
			Iterator<Employee> itr=empList.iterator();
			while (itr.hasNext()) {
				Employee employee = itr.next();
				if(allotmentList!=null && !allotmentList.isEmpty()){
					Iterator<EmpLeaveAllotment> aitr=allotmentList.iterator();
					while (aitr.hasNext()) {
						EmpLeaveAllotment allotment = aitr .next();
						EmpLeave bo=new EmpLeave();
						bo.setEmployee(employee);
						bo.setIsActive(true);
						bo.setEmpLeaveType(allotment.getEmpLeaveType());
						bo.setCreatedBy(leaveInitializationForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(leaveInitializationForm.getUserId());
						bo.setLastModifiedDate(new Date());
						bo.setYear(Integer.parseInt(leaveInitializationForm.getYear()));
						bo.setMonth(leaveInitializationForm.getMonth());
						if(!allotment.getInitRequired()){
							if(map.containsKey(employee.getId()+"_"+allotment.getEmpLeaveType().getId())){
								EmpLeave oldBo=map.get(employee.getId()+"_"+allotment.getEmpLeaveType().getId());
								bo.setLeavesAllocated(oldBo.getLeavesRemaining()+Double.parseDouble(allotment.getAllottedLeave().toString()));
								bo.setLeavesRemaining(oldBo.getLeavesRemaining()+Double.parseDouble(allotment.getAllottedLeave().toString()));
								bo.setLeavesSanctioned(0.0);
							}else{
								bo.setLeavesAllocated(Double.parseDouble(allotment.getAllottedLeave().toString()));
								bo.setLeavesRemaining(Double.parseDouble(allotment.getAllottedLeave().toString()));
								bo.setLeavesSanctioned(0.0);
							}
						}else{
							if(map.containsKey(employee.getId()+"_"+allotment.getEmpLeaveType().getId())){
								EmpLeave alEmpLeave=null;
								if(newAlLeave.containsKey(employee.getId())){
									alEmpLeave=newAlLeave.remove(employee.getId());
								}else{
									alEmpLeave=new EmpLeave();
									if(oldAlLeave.containsKey(employee.getId())){
										EmpLeave oldAlEmpLeave=oldAlLeave.remove(employee.getId());
										alEmpLeave.setLeavesAllocated(oldAlEmpLeave.getLeavesAllocated());
										alEmpLeave.setLeavesRemaining(oldAlEmpLeave.getLeavesRemaining());
										alEmpLeave.setLeavesSanctioned(oldAlEmpLeave.getLeavesSanctioned());
									}else{
										alEmpLeave.setLeavesAllocated(0.0);
										alEmpLeave.setLeavesRemaining(0.0);
										alEmpLeave.setLeavesSanctioned(0.0);
									}
								}
								alEmpLeave.setEmployee(employee);
								if(accumulateLeaveTypeId != 0){
									EmpLeaveType empLeaveType=new EmpLeaveType();
									empLeaveType.setId(accumulateLeaveTypeId);
									alEmpLeave.setEmpLeaveType(empLeaveType);
								}
								alEmpLeave.setCreatedBy(leaveInitializationForm.getUserId());
								alEmpLeave.setCreatedDate(new Date());
								alEmpLeave.setIsActive(true);
								alEmpLeave.setModifiedBy(leaveInitializationForm.getUserId());
								alEmpLeave.setLastModifiedDate(new Date());
								alEmpLeave.setYear(Integer.parseInt(leaveInitializationForm.getYear()));
								alEmpLeave.setMonth(leaveInitializationForm.getMonth());
								EmpLeave oldBo=map.get(employee.getId()+"_"+allotment.getEmpLeaveType().getId());
								int noOfAccumulatedLeaves=allotment.getNoOfAccumulatedLeave();
								double finalNoOfAccumulatedLeaves=0;
								if(oldBo.getLeavesRemaining()<=noOfAccumulatedLeaves){
									finalNoOfAccumulatedLeaves=oldBo.getLeavesRemaining();
								}else{
									finalNoOfAccumulatedLeaves=noOfAccumulatedLeaves;
								}
								alEmpLeave.setLeavesAllocated(alEmpLeave.getLeavesAllocated()+finalNoOfAccumulatedLeaves);
								alEmpLeave.setLeavesRemaining(alEmpLeave.getLeavesRemaining()+finalNoOfAccumulatedLeaves);
								alEmpLeave.setLeavesSanctioned(0.0);
								newAlLeave.put(employee.getId(),alEmpLeave);
							}
							bo.setLeavesAllocated(Double.parseDouble(allotment.getAllottedLeave().toString()));
							bo.setLeavesRemaining(Double.parseDouble(allotment.getAllottedLeave().toString()));
							bo.setLeavesSanctioned(0.0);
						}
						boList.add(bo);
					}
				}
			}
		}
		if(!newAlLeave.isEmpty()){
			boList.addAll(newAlLeave.values());
		}
		return boList;
	}
}
