package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.ShiftEntry;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.forms.employee.ShiftEntryForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.to.employee.ShiftEntryTo;

public class ShiftEntryHelper {
	
	private static volatile ShiftEntryHelper instance=null;
	
	/**
	 * 
	 */
	private ShiftEntryHelper(){
		
	}

	/**
	 * @return
	 */
	public static ShiftEntryHelper getInstance(){
		if(instance==null){
			instance=new ShiftEntryHelper();
		}
		return instance;
	}


	/**
	 * @param list
	 * @param shiftEntryForm
	 */
	public void convetBoToForm(List<ShiftEntry> list, ShiftEntryForm shiftEntryForm) {
		if(list != null){
			List<ShiftEntryTo> tos = new ArrayList<ShiftEntryTo>(); 
			Iterator<ShiftEntry> iterator = list.iterator();
			String empId = "";
			while (iterator.hasNext()) {
				ShiftEntry shiftEntry = (ShiftEntry) iterator.next();
				ShiftEntryTo  to = new ShiftEntryTo();
				to.setId(shiftEntry.getId());
				to.setDay(shiftEntry.getDay());
				to.setTimeIn(shiftEntry.getTimeIn().substring(0, 2));
				to.setTimeInMin(shiftEntry.getTimeIn().substring(3, 5));
				to.setTimeOut(shiftEntry.getTimeOut().substring(0,2));
				to.setTimeOutMin(shiftEntry.getTimeOut().substring(3,5));
				empId = String.valueOf(shiftEntry.getEmployee().getId());
				tos.add(to);
			}
			shiftEntryForm.setEntryTos(tos);
			shiftEntryForm.setEmployeeId(empId);
		}
	}

	/**
	 * @param entryTos
	 * @param shiftEntryForm 
	 * @return
	 */
	public List<ShiftEntry> convertTOToBo(List<ShiftEntryTo> entryTos, ShiftEntryForm shiftEntryForm) {
		List<ShiftEntry> shiftEntries = new ArrayList<ShiftEntry>();
		if(entryTos != null){
			Iterator<ShiftEntryTo> iterator = entryTos.iterator();
			while (iterator.hasNext()) {
				ShiftEntryTo to = (ShiftEntryTo) iterator.next();
				ShiftEntry bo = new ShiftEntry();
				if(to.getId() >0){
					bo.setId(to.getId());
				}
				Employee employee = new Employee();
				if(shiftEntryForm.getEmployeeId() != null && !shiftEntryForm.getEmployeeId().isEmpty()){
					employee.setId(Integer.parseInt(shiftEntryForm.getEmployeeId()));
				}
				bo.setEmployee(employee);
				bo.setDay(to.getDay());
				if(to.getTimeIn() != null && !to.getTimeIn().isEmpty() && to.getTimeInMin() != null && !to.getTimeInMin().isEmpty()){
					String timeIn=to.getTimeIn()+":"+to.getTimeInMin();
					bo.setTimeIn(timeIn);
				}
				if(to.getTimeOut() != null && !to.getTimeOut().isEmpty() && to.getTimeOutMin() != null && !to.getTimeOutMin().isEmpty()){
					String timeOut=to.getTimeOut()+":"+to.getTimeOutMin();
					bo.setTimeOut(timeOut);
				}
				bo.setIsActive(true);
				bo.setCreatedDate(new Date());
				bo.setLastModifiedDate(new Date());
				bo.setCreatedBy(shiftEntryForm.getUserId());
				bo.setLastModifiedBy(shiftEntryForm.getUserId());
				shiftEntries.add(bo);
			}
		}
		return shiftEntries;
	}

	/**
	 * @param list
	 * @return
	 */
	public List<EmployeeTO> convetBoToTO(List<ShiftEntry> list) {
		List<EmployeeTO> tos = new ArrayList<EmployeeTO>();
		if(list != null ){
			Iterator<ShiftEntry> iterator = list.iterator();
			while (iterator.hasNext()) {
				ShiftEntry shiftEntry = (ShiftEntry) iterator.next();
				EmployeeTO to = new EmployeeTO();
				if(shiftEntry.getEmployee() != null && shiftEntry.getEmployee().getId()>0){
					to.setId(shiftEntry.getEmployee().getId());
				}
				if(shiftEntry.getEmployee() != null && shiftEntry.getEmployee().getFirstName() != null){
					to.setFirstName(shiftEntry.getEmployee().getFirstName());
				}
				tos.add(to);
			}
		}
		return tos;
	}
}
