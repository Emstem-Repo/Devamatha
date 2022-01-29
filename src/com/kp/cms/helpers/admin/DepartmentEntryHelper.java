	package com.kp.cms.helpers.admin;
	import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.to.admin.DepartmentEntryTO;

	public class DepartmentEntryHelper {
		public static volatile DepartmentEntryHelper departmentEntryHelper=null;
		public static final Log log = LogFactory.getLog(DepartmentEntryHelper.class);
		public static DepartmentEntryHelper getInstance(){
			if(departmentEntryHelper == null){
				departmentEntryHelper = new DepartmentEntryHelper();
				return departmentEntryHelper;
			}
			return departmentEntryHelper;
		}
		/** converting Data from BO to To list.
		 * @param departments
		 * @return
		 */
		public List<DepartmentEntryTO> copyBoDataToTO(List<Department> departments)throws Exception {
			List<DepartmentEntryTO> departmentTO = new ArrayList<DepartmentEntryTO>();
			if(departments!=null){
				Iterator<Department> iterator = departments.iterator();
				while (iterator.hasNext()) {
					Department department = (Department) iterator.next();
					DepartmentEntryTO to = new DepartmentEntryTO();
					to.setId(department.getId());
					to.setName(department.getName());
					if(department.getWebId()!=null){
					to.setWebId(Integer.toString(department.getWebId()));
					}
					//added by smitha
					to.setCode(department.getCode()!=null?department.getCode():"");
					to.setEmail(department.getEmail());
					if(department.getIsAcademic()){
						to.setIsAcademic("Yes");
					}else{
						to.setIsAcademic("No");
					}
					if(department.getEmployeeStreamBO()!= null){
						if(department.getEmployeeStreamBO().getName()!=null){
							if(!department.getEmployeeStreamBO().getName().isEmpty()){
								to.setEmpStreamId(department.getEmployeeStreamBO().getId());
								to.setEmpStreamName(department.getEmployeeStreamBO().getName());
							}
						}
					}
					departmentTO.add(to);
				}
			}
			log.error("ending of copyBoDataToTO method in DepartmentEntryHelper");
			return departmentTO;
		}
		/**
		 * @param departmentEntryForm
		 * @return
		 */
		public Department populateDepartmentDateFromFrom(DepartmentEntryForm departmentEntryForm) {
			Department department= new Department();
			if(departmentEntryForm.getEmpStreamId()!=null && !departmentEntryForm.getEmpStreamId().isEmpty()){
				EmployeeStreamBO streamBO = new EmployeeStreamBO();
				streamBO.setId(Integer.parseInt(departmentEntryForm.getEmpStreamId()));
				department.setId(departmentEntryForm.getId());
				department.setName(departmentEntryForm.getName());
				department.setCode(departmentEntryForm.getCode());
				department.setWebId(Integer.parseInt(departmentEntryForm.getWebId()));
				department.setIsAcademic(departmentEntryForm.getIsAcademic().equalsIgnoreCase("true")? true:false);
				department.setEmployeeStreamBO(streamBO);
				department.setIsActive(true);
				department.setEmail(departmentEntryForm.getEmail());
			}else{
				department.setId(departmentEntryForm.getId());
				department.setName(departmentEntryForm.getName());
				department.setWebId(Integer.parseInt(departmentEntryForm.getWebId()));
				department.setIsAcademic(departmentEntryForm.getIsAcademic().equalsIgnoreCase("true")? true:false);
				department.setCode(departmentEntryForm.getCode());
				department.setIsActive(true);
				department.setEmail(departmentEntryForm.getEmail());
			}
			log.error("ending of populateDepartmentDateFromFrom method in DepartmentEntryHelper");
			return department;
		}
				
		/**
		 * @param departmentEntryForm
		 * @param department
		 */
		public void setDepartmentDetails(DepartmentEntryForm departmentEntryForm, Department department) {
			if(department!=null){
				departmentEntryForm.setName(department.getName());
				if(department.getEmployeeStreamBO() != null && department.getEmployeeStreamBO().getName() != null){
					departmentEntryForm.setEmpStreamName(department.getEmployeeStreamBO().getName());
				}
				if(department.getEmployeeStreamBO() != null && department.getEmployeeStreamBO().getId() != 0){
					departmentEntryForm.setEmpStreamId(String.valueOf(department.getEmployeeStreamBO().getId()));
				}
				departmentEntryForm.setCode(department.getCode());
				departmentEntryForm.setEmail(department.getEmail());
	    		if(department.getWebId()!=null){
	    			departmentEntryForm.setWebId(Integer.toString(department.getWebId()));
	    		}
				if(department.getIsAcademic()!=null ){
					
					String Value=String.valueOf(department.getIsAcademic());
					if(Value.equalsIgnoreCase("true"))
						departmentEntryForm.setIsAcademic("true");
					else
						departmentEntryForm.setIsAcademic("false");
					 
				}
	    	}
			log.error("ending of setDepartmentDetails method in DepartmentEntryHelper");
		}
		
	 
	}
