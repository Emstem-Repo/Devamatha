package com.kp.cms.helpers.serviceLearning;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;

public class DepartmentNameEntryHelper {
	
	private static final Log log = LogFactory.getLog(DepartmentNameEntryHelper.class);
	private static DepartmentNameEntryHelper departmentNameEntryHelper= null;
	public static DepartmentNameEntryHelper getInstance() {
		      if(departmentNameEntryHelper == null) {
		    	  departmentNameEntryHelper = new DepartmentNameEntryHelper();
		    	  return departmentNameEntryHelper;
		      }
	return departmentNameEntryHelper;
	}
	
	public static List<DepartmentNameEntryTo> convertBOsToTos(List<DepartmentNameEntry> hostelNameEntryBOList) {
		List<DepartmentNameEntryTo> departmentNameEntryToList = new ArrayList<DepartmentNameEntryTo>();
		
		if (hostelNameEntryBOList != null) {

			Iterator<DepartmentNameEntry> iterator = hostelNameEntryBOList.iterator();
			while (iterator.hasNext()) {
				DepartmentNameEntry departmentNameEntryBo = (DepartmentNameEntry) iterator.next();
				
				DepartmentNameEntryTo departmentNameEntryTo = new DepartmentNameEntryTo();
				
				departmentNameEntryTo.setId(String.valueOf(departmentNameEntryBo.getId()));
				departmentNameEntryTo.setDepartmentName(departmentNameEntryBo.getDepartmentName());
				departmentNameEntryToList.add(departmentNameEntryTo);
				

			}
		}
		return departmentNameEntryToList;
	}
	
	public DepartmentNameEntry addDepartmentNameEntry(
			DepartmentNameEntryForm departmentNameEntryForm , String mode) {
		// TODO Auto-generated method stub
		
		DepartmentNameEntry departmentNameEntry=new DepartmentNameEntry();
		departmentNameEntry.setDepartmentName(departmentNameEntryForm.getDepartmentName());
		departmentNameEntry.setCreatedDate(new Date());
		departmentNameEntry.setIsActive(true);
		departmentNameEntry.setCreatedBy(departmentNameEntryForm.getUserId());
		
	    if (mode.equalsIgnoreCase("Edit")) {
	    	departmentNameEntry.setLastModifiedDate(new java.util.Date());
	    	departmentNameEntry.setModifiedBy(departmentNameEntryForm.getUserId());
	    	departmentNameEntry.setId(Integer.parseInt(departmentNameEntryForm.getId()));

		}
	   return departmentNameEntry;
	}
	
	public void setDepartmentNameEntryDetails(DepartmentNameEntryForm departmentNameEntryForm,
			DepartmentNameEntry departmentNameEntry) {
		
		if (departmentNameEntry != null) {
			
			departmentNameEntryForm.setDepartmentName(departmentNameEntry.getDepartmentName());
			departmentNameEntryForm.setId(String.valueOf(departmentNameEntry.getId()));
			
		}
		log.error("ending of setDepartmentDetails method in DepartmentEntryHelper");
	}

	
	

}
