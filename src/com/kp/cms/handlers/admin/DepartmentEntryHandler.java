	package com.kp.cms.handlers.admin;

	import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.DepartmentEntryAction;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.helpers.admin.DepartmentEntryHelper;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;

	public class DepartmentEntryHandler {
		public static volatile DepartmentEntryHandler departmentEntryHandler = null;
		private static final Log log = LogFactory.getLog(DepartmentEntryHandler.class);
		public static DepartmentEntryHandler getInstance(){
			if(departmentEntryHandler == null){
				departmentEntryHandler = new DepartmentEntryHandler();
				return departmentEntryHandler;
			}
			return departmentEntryHandler;
		}
		/** list of Department Entry's getting from Bo.
		 * @return
		 * @throws Exception 
		 */
		public List<DepartmentEntryTO> getDepartmentFields() throws Exception {
			List<DepartmentEntryTO> departmentEntryTOs = null;
			IDepartmentEntryTransaction departmentEntryTransaction=DepartmentEntryTransactionImpl.getInstance();
			List<Department> departments = departmentEntryTransaction.getDepartmentFields();
			departmentEntryTOs=DepartmentEntryHelper.getInstance().copyBoDataToTO(departments);
			log.error("ending of getDepartmentFields method in DepartmentEntryHandler");
			return departmentEntryTOs;
		}
		/**
		 * @param departmentEntryForm
		 * @return
		 */
		public boolean addDepartmentEntry(DepartmentEntryForm departmentEntryForm,String mode)throws Exception {
			boolean isAdded=false;
			IDepartmentEntryTransaction departmentEntryTransaction=DepartmentEntryTransactionImpl.getInstance();
			Boolean originalNotChanged=false;
			String depart="";
			String OrgDepartment="";
			String code="";
			String orgCode="";
			if(departmentEntryForm.getName()!=null && !departmentEntryForm.getName().isEmpty()){
				depart=departmentEntryForm.getName().trim();
			}
			if(departmentEntryForm.getOrgName()!=null && !departmentEntryForm.getOrgName().isEmpty()){
				OrgDepartment=departmentEntryForm.getOrgName().trim();
			}
		
			// for code duplicate check
			if(departmentEntryForm.getCode()!=null && !departmentEntryForm.getCode().isEmpty()){
				code=departmentEntryForm.getCode().trim();
			}
			if(departmentEntryForm.getOrgCode()!=null && !departmentEntryForm.getOrgCode().isEmpty()){
				orgCode=departmentEntryForm.getOrgCode().trim();
			}
			if(code.equalsIgnoreCase(orgCode) && depart.equalsIgnoreCase(OrgDepartment)){
				originalNotChanged = true;
			}
			if(mode.equals("Add")){
				originalNotChanged= false;
			}
			if(!originalNotChanged){
				Department dupliDepartmentBO=DepartmentEntryHelper.getInstance().populateDepartmentDateFromFrom(departmentEntryForm);
				Department dupliDepartment=departmentEntryTransaction.isDuplicateDepartmentEntry(dupliDepartmentBO);
				if(dupliDepartment!=null && dupliDepartment.getIsActive() == true && dupliDepartment.getId()!=dupliDepartmentBO.getId() ){
					throw new DuplicateException();
				}
				if(dupliDepartment!=null && dupliDepartment.getIsActive() == false){
					departmentEntryForm.setDupId(dupliDepartment.getId());
					throw new ReActivateException();
				}
				//duplicate check for code
				
				Department dupliDepartmentCode=departmentEntryTransaction.isDuplicateDepartmentCodeEntry(dupliDepartmentBO);
				if(dupliDepartmentCode!=null && dupliDepartmentCode.getIsActive() == true && dupliDepartmentCode.getId()!=dupliDepartmentBO.getId()){
					throw new DuplicateException();
				}
				if(dupliDepartmentCode!=null && dupliDepartmentCode.getIsActive() == false){
					departmentEntryForm.setDupId(dupliDepartmentCode.getId());
					throw new ReActivateException();
				}
			}
			Department department=DepartmentEntryHelper.getInstance().populateDepartmentDateFromFrom(departmentEntryForm);
			if(mode.equals("Add")){
				department.setCreatedBy(departmentEntryForm.getUserId());
				department.setModifiedBy(departmentEntryForm.getUserId());
				department.setLastModifiedDate(new Date());
				department.setCreatedDate(new Date());
			}else{ // Edit
				department.setLastModifiedDate(new Date());
				department.setModifiedBy(departmentEntryForm.getUserId());
			}
			isAdded=departmentEntryTransaction.addDepartment(department,mode);
			log.error("ending of getDepartmentFields method in DepartmentEntryHandler");
			return isAdded;
			
		}
		/**
		 * @param departmentId
		 * @param departmentEntryForm 
		 * @param activate 
		 * @return
		 * @throws Exception 
		 */
		public boolean deleteDepartmentEntry(int departmentId, boolean activate, DepartmentEntryForm departmentEntryForm) throws Exception {
			boolean isDeleted=false;
			IDepartmentEntryTransaction departmentEntryTransaction=DepartmentEntryTransactionImpl.getInstance();
			
			isDeleted=departmentEntryTransaction.deleteDepartmentEntry(departmentId,activate,departmentEntryForm);
			log.error("ending of getDepartmentFields method in DepartmentEntryHandler");
			return isDeleted;
		}
		/**
		 * @return
		 * @throws Exception
		 */
		public Map<Integer, String> getEmpStream() throws Exception{
			IDepartmentEntryTransaction departmentEntryTransaction=DepartmentEntryTransactionImpl.getInstance();
			log.error("ending of getDepartmentFields method in DepartmentEntryHandler");
			return departmentEntryTransaction.getEmpStream();
		}
		
		public void editDepartmentEntry(DepartmentEntryForm departmentEntryForm) throws Exception{
			IDepartmentEntryTransaction transaction=new DepartmentEntryTransactionImpl();
			Department department=transaction.getDepartmentdetails(departmentEntryForm.getId());
			DepartmentEntryHelper.getInstance().setDepartmentDetails(departmentEntryForm,department);
			log.error("ending of editDepartmentEntry method in DepartmentEntryHandler");
						
		}
			
}
