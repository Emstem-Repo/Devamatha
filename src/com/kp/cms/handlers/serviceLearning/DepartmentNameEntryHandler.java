package com.kp.cms.handlers.serviceLearning;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.handlers.admin.ActivityLearningHandler;
import com.kp.cms.helpers.admin.ActivityLearningHelper;
import com.kp.cms.helpers.serviceLearning.DepartmentNameEntryHelper;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.transactions.admin.IActivityLearningTransaction;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.transactions.serviceLearning.IDepartmentNameEntryTransaction;
import com.kp.cms.transactionsimpl.admin.ActivityLearningImpl;
import com.kp.cms.transactionsimpl.serviceLearning.DepartmentNameEntryImpl;

public class DepartmentNameEntryHandler {

	private static volatile DepartmentNameEntryHandler departmentNameEntryHandler = null;
	private DepartmentNameEntryHandler(){

	}
	public static DepartmentNameEntryHandler getInstance() {
		if (departmentNameEntryHandler == null) {
			departmentNameEntryHandler = new DepartmentNameEntryHandler();
		}
		return departmentNameEntryHandler;
	}

	public List<DepartmentNameEntryTo> getDepartmentNames() throws Exception{
		IDepartmentNameEntryTransaction transaction = (IDepartmentNameEntryTransaction) new DepartmentNameEntryImpl();
		List<DepartmentNameEntry> departmentNameEntryList = transaction.getDepartmentNames();
		List<DepartmentNameEntryTo> hostelNameEntryToList = DepartmentNameEntryHelper.convertBOsToTos(departmentNameEntryList);
		return hostelNameEntryToList;
	}

	public boolean addDepartmentNameEntry(DepartmentNameEntryForm departmentNameEntryForm,String mode,HttpServletRequest request) throws Exception{


		// TODO Auto-generated method stub
		IDepartmentNameEntryTransaction transaction = (IDepartmentNameEntryTransaction) new DepartmentNameEntryImpl();
		DepartmentNameEntry departmentNameEntry = null;


		DepartmentNameEntry dupDepartmentEntry= transaction.isDuplicateDepartmentNameEntryDetail(departmentNameEntryForm, mode);
		if(dupDepartmentEntry!=null && dupDepartmentEntry.getIsActive()==true)
		{
			throw new DuplicateException();
		}
		if(dupDepartmentEntry!=null && dupDepartmentEntry.getIsActive()==false){
			request.getSession().setAttribute("DepartmentNameEntry",dupDepartmentEntry);

			departmentNameEntryForm.setDupId(String.valueOf(dupDepartmentEntry.getId()));
			throw new ReActivateException();
		}
		else
		{
			departmentNameEntry=DepartmentNameEntryHelper.getInstance().addDepartmentNameEntry(departmentNameEntryForm, mode);
		}
		if(mode.equals("Add")){
			departmentNameEntry.setCreatedBy(departmentNameEntryForm.getUserId());
			departmentNameEntry.setModifiedBy(departmentNameEntryForm.getUserId());
			departmentNameEntry.setLastModifiedDate(new Date());
			departmentNameEntry.setCreatedDate(new Date());
			departmentNameEntry.setIsActive(true);
		}else{ // Edit
			departmentNameEntry.setLastModifiedDate(new Date());
			departmentNameEntry.setModifiedBy(departmentNameEntryForm.getUserId());
		}
		boolean isadded=transaction.addDepartmentNameEntry(departmentNameEntry, mode);
		return isadded;

	}
	
	public boolean deleteDepartmentNameEntry(int id,boolean activate,DepartmentNameEntryForm departmentNameEntryForm) throws Exception{
		boolean isDeleted=false;
		IDepartmentNameEntryTransaction transaction = (IDepartmentNameEntryTransaction) new DepartmentNameEntryImpl();
	    isDeleted=transaction.deleteDepartmentNameEntry(id, activate, departmentNameEntryForm);
		return isDeleted;
	}
	public void editDepartmentNameEntry(DepartmentNameEntryForm departmentNameEntryForm) throws Exception{
		IDepartmentNameEntryTransaction transaction = (IDepartmentNameEntryTransaction) new DepartmentNameEntryImpl();
		if(((departmentNameEntryForm.getId())!= null && !departmentNameEntryForm.getId().equalsIgnoreCase(""))){
			DepartmentNameEntry departmentNameEntry = transaction.getDepartmentNameEntryDetails(departmentNameEntryForm.getId());
			DepartmentNameEntryHelper.getInstance().setDepartmentNameEntryDetails(departmentNameEntryForm, departmentNameEntry);
		}
	}
	
	public boolean reActivateDepartmentNameEntry(DepartmentNameEntry departmentNameEntry,String userId) throws Exception{
		IDepartmentNameEntryTransaction transaction = (IDepartmentNameEntryTransaction) new DepartmentNameEntryImpl();
		boolean isHostelNameEntry=transaction.reActivateDepartmentNameEntry(departmentNameEntry, userId);
		return isHostelNameEntry;
	}
	public List<ExtraCreditActivityTypeTo> getActivityNames() throws Exception{
		IActivityLearningTransaction transaction = new ActivityLearningImpl();
		List<ExtraCreditActivityTypeTo> list = ActivityLearningHelper.convertBotoTos(transaction.getActivities());
		return list;
	}






}
