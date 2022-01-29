package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.forms.inventory.InventoryLocationForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvCategoryTo;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.transactionsimpl.inventory.InventoryLocationTransactionImpl;

public class InventoryLocationHelper {
	private static final Log log = LogFactory.getLog(InventoryLocationTransactionImpl.class);	
	public static volatile InventoryLocationHelper inventoryLocationHandler = null;

	public static InventoryLocationHelper getInstance() {
		if (inventoryLocationHandler == null) {
			inventoryLocationHandler = new InventoryLocationHelper();
		}
		return inventoryLocationHandler;
	}
	
	/**
	 * 
	 * @param emplBoList
	 * @return
	 */
	public Map<Integer, String> copyEmployeeBoToMap(List<Employee> emplBoList){
		Employee employee = null;
		Map<Integer, String> employeeToMap = new HashMap<Integer, String>();
		Iterator<Employee> i = emplBoList.iterator();
		StringBuffer empName;
		StringBuffer empNameWithCode;
		while (i.hasNext()) {
			employee = (Employee) i.next();
			empName = new StringBuffer();
			empNameWithCode = new StringBuffer();
			empName.append(employee.getFirstName());
			/*if(employee.getMiddleName()!= null && !employee.getMiddleName().trim().isEmpty()){
				if(empName.toString()!= null && !empName.toString().trim().isEmpty()){
					empName.append(" ");
				}
				empName.append(employee.getMiddleName());
			}
			if(employee.getLastName()!= null && !employee.getLastName().trim().isEmpty()){
				if(empName.toString()!= null && !empName.toString().trim().isEmpty()){
					empName.append(" ");
				}
				empName.append(employee.getLastName());
			}*/
			empNameWithCode.append(empName.toString()); 	
			if(employee.getCode()!= null && !employee.getCode().trim().isEmpty()){
				empNameWithCode.append("(" + employee.getCode() + ")");
			}
			if(employee.getDepartment()!=null)
				empNameWithCode.append("(" + employee.getDepartment().getName() + ")");
				
			employeeToMap.put(employee.getId(), empNameWithCode.toString());
	
		}
		return employeeToMap;	
	}
	
	
	/**
	 * 
	 * @param locationForm
	 * @return
	 * @throws Exception
	 */
	public InvLocation copyDataFromFormToBO(InventoryLocationForm locationForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		InvLocation invLocation = new InvLocation();
		if(locationForm.getId() != 0) {
			invLocation.setId(locationForm.getId());
		}
		
		if(locationForm.getLocation()!= null){
			invLocation.setName(locationForm.getLocation());
		}
		InvCampus campus= new InvCampus();
		campus.setId(Integer.parseInt(locationForm.getInvCampusId()));
		invLocation.setInvCampusId(campus);
		invLocation.setIsActive(true);
		invLocation.setCreatedBy(locationForm.getUserId());
		invLocation.setModifiedBy(locationForm.getUserId());
		invLocation.setCreatedDate(new Date());
		invLocation.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return invLocation;
	}
	/**
	 * 
	 * @param locationBoList
	 * @return
	 */
	public List<InvLocationTO> copyLocationBoToTO(List<InvLocation> locationBoList){
		InvLocation location = null;
		InvLocationTO locationTO;
		EmployeeTO employeeTO;
		List<InvLocationTO> locationList = new ArrayList<InvLocationTO>();
		Iterator<InvLocation> i = locationBoList.iterator();
		StringBuffer empName;
		while (i.hasNext()) {
			empName = new StringBuffer("");
			locationTO = new InvLocationTO();
			location = (InvLocation) i.next();
			locationTO.setId(location.getId());
			locationTO.setName(location.getName());
			employeeTO = new EmployeeTO();
			if(location.getInvCampusId()!=null && location.getInvCampusId().getName()!=null && !location.getInvCampusId().getName().isEmpty())
			{
				locationTO.setInvCampusName(location.getInvCampusId().getName());
				locationTO.setInvCampusId(location.getInvCampusId().getId());
			}
			if(location.getEmployee()!= null){
				if(location.getEmployee().getFirstName()!= null){
					empName.append(location.getEmployee().getFirstName());
				}
				/*if(location.getEmployee().getMiddleName()!= null){
					if(empName.toString()!= null && !empName.toString().trim().isEmpty()){
						empName.append(" ");
					}
					empName.append(location.getEmployee().getMiddleName());
				}
				if(location.getEmployee().getLastName()!= null){
					if(empName.toString()!= null && !empName.toString().trim().isEmpty()){
						empName.append(" ");employee_id
					}
					empName.append(location.getEmployee().getLastName());
				}*/
				employeeTO.setName(empName.toString());
				employeeTO.setId(location.getEmployee().getId());
			}
			else
			{
				employeeTO.setName("");
			}
			locationTO.setEmployeeTO(employeeTO);
			locationList.add(locationTO);
	
		}
		return locationList;	
	}

	public List<InvCampusTo> convetBosToTos(List<InvCampus> campusList) {
		List<InvCampusTo> campusToList=new ArrayList<InvCampusTo>();
			Iterator <InvCampus> iterator=campusList.iterator();
			while(iterator.hasNext())
			{
				InvCampus invCampus = (InvCampus) iterator.next();
				InvCampusTo invCampusTo = new InvCampusTo();
				invCampusTo.setId(invCampus.getId());
				invCampusTo.setCampusName(invCampus.getName());
				campusToList.add(invCampusTo);
			}
		Collections.sort(campusToList);
		return campusToList;
	}	
}
