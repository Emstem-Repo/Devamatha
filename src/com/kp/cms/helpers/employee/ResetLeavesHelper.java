package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ResetLeavesForm;
import com.kp.cms.transactions.employee.IResetLeavesTransaction;

public class ResetLeavesHelper {
	/**
	 * Singleton object of ResetLeavesHelper
	 */
	private static volatile ResetLeavesHelper resetLeavesHelper = null;
	private static final Log log = LogFactory.getLog(ResetLeavesHelper.class);
	private ResetLeavesHelper() {
		
	}
	/**
	 * return singleton object of ResetLeavesHelper.
	 * @return
	 */
	public static ResetLeavesHelper getInstance() {
		if (resetLeavesHelper == null) {
			resetLeavesHelper = new ResetLeavesHelper();
		}
		return resetLeavesHelper;
	}
	/**
	 * @param resetLeavesForm
	 * @return
	 */
	public String getEmpQuery(ResetLeavesForm resetLeavesForm) {
		String query=" select e.employee" +
				" from EmpJob e where e.objEmployeeTypeBO.id =" +resetLeavesForm.getEmpTypeId()+
				" and e.objEmployeeTypeBO.isActive=1 and e.employee.isActive=1 group by e.employee.id";
		return query;
	}
	/**
	 * @param resetLeavesForm
	 * @return
	 * @throws Exception
	 */
	public String getEmpInitalizeQuery(ResetLeavesForm resetLeavesForm) throws Exception {
		int month=Integer.parseInt(resetLeavesForm.getMonth())+1;
		String query="from EmpInitializeLeaves e where e.isActive=1" +
				" and e.empTypeId.isActive=1" +
				" and e.empTypeId.id="+resetLeavesForm.getEmpTypeId()+" and YEAR(e.allotedDate)='"+resetLeavesForm.getAcademicYear()+"' and month(e.allotedDate)='"+month+"'";
		return query;
	}
	/**
	 * @param empList
	 * @param initList
	 * @param resetLeavesForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeave> getEmpLeaveList(List<Employee> empList,
			List<EmpInitializeLeaves> initList, ResetLeavesForm resetLeavesForm,IResetLeavesTransaction transaction) throws Exception{
		List<EmpLeave> finalList=new ArrayList<EmpLeave>();
		if(initList!=null && empList!=null){
			Iterator<EmpInitializeLeaves> empInitItr=initList.iterator();
			while (empInitItr.hasNext()) {
				EmpInitializeLeaves empInitializeLeaves = (EmpInitializeLeaves) empInitItr.next();
				Map<Integer, Integer> existedLeavesMap=transaction.getExistedLeavesForLeaveTypeAndEmpType(resetLeavesForm.getEmpTypeId(),empInitializeLeaves.getLeaveType().getId(),resetLeavesForm);
				Map<Integer,EmpLeave> empLeaveMap=resetLeavesForm.getEmpLeaveMap();
				Iterator<Employee> empItr=empList.iterator();
				while (empItr.hasNext()) {
					Employee employee = (Employee) empItr.next();
					EmpLeave e=null;
					if(existedLeavesMap.containsKey(employee.getId())){
						e=empLeaveMap.get(existedLeavesMap.get(employee.getId()));
						e.setCreatedBy(e.getCreatedBy());
						e.setCreatedDate(e.getCreatedDate());
						/*if(empInitializeLeaves.getIsInitializeRequired()){
							e.setLeavesAllocated(empInitializeLeaves.getAllotLeaves());
							e.setLeavesRemaining(empInitializeLeaves.getAllotLeaves());
							e.setLeavesSanctioned(0);
						}else{
							if(e.getLeavesAllocated()!=null){
								e.setLeavesAllocated(e.getLeavesAllocated()+empInitializeLeaves.getAllotLeaves());
							}
							else{
								e.setLeavesAllocated(empInitializeLeaves.getAllotLeaves());
							}
							if(e.getLeavesRemaining()!=null)
								e.setLeavesRemaining(e.getLeavesRemaining()+empInitializeLeaves.getAllotLeaves());
							else
								e.setLeavesRemaining(empInitializeLeaves.getAllotLeaves());
							if(e.getLeavesSanctioned()!=null)
								e.setLeavesSanctioned(e.getLeavesSanctioned());
							else
								e.setLeavesSanctioned(0);
						}*/
					}else{
						e=new EmpLeave();
						e.setCreatedBy(resetLeavesForm.getUserId());
						e.setCreatedDate(new Date());
					/*	e.setLeavesAllocated(empInitializeLeaves.getAllotLeaves());
						e.setLeavesRemaining(empInitializeLeaves.getAllotLeaves());
						e.setLeavesSanctioned(0);*/
					}
					e.setModifiedBy(resetLeavesForm.getUserId());
					e.setLastModifiedDate(new Date());
					e.setIsActive(true);
					e.setEmployee(employee);
					e.setEmpLeaveType(empInitializeLeaves.getLeaveType());
					finalList.add(e);
				}
			}
		}
		return finalList;
	}
}
