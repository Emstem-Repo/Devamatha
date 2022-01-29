package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.OpenSyllabusEntry;
import com.kp.cms.forms.admin.OpenSyllabusEntryForm;
import com.kp.cms.to.admin.OpenSyllabusEntryTo;
import com.kp.cms.transactions.admin.IOpenSyllabusEntryTrans;
import com.kp.cms.transactionsimpl.admin.OpenSyllabusEntryTransImpl;
import com.kp.cms.utilities.CommonUtil;

public class OpenSyllabusEntryHandler {
	IOpenSyllabusEntryTrans transaction=OpenSyllabusEntryTransImpl.getInstance();
	public static volatile OpenSyllabusEntryHandler openSyllabusEntryHandler=null;
	//private constructor
	private OpenSyllabusEntryHandler(){
		
	}
	//singleton object
	public static OpenSyllabusEntryHandler getInstance(){
		if(openSyllabusEntryHandler==null){
			openSyllabusEntryHandler=new OpenSyllabusEntryHandler();
			return openSyllabusEntryHandler;
		}
		return openSyllabusEntryHandler;
	}
	/**
	 * checking the duplicate of open syllabus entry based on batch,start and end dates
	 * @param openEntryForm
	 * @param mode 
	 * @return
	 */
	public boolean checkDuplicate(OpenSyllabusEntryForm openEntryForm, String mode) throws Exception{
		boolean flag=transaction.checkDuplicate(openEntryForm,mode);
		return flag;
	}
	/**
	 * add 
	 * @param openEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean add(OpenSyllabusEntryForm openEntryForm) throws Exception{
		OpenSyllabusEntry openSyllabusEntry=new OpenSyllabusEntry();
		openSyllabusEntry.setBatch(Integer.parseInt(openEntryForm.getBatch()));
		openSyllabusEntry.setStartDate(CommonUtil.ConvertStringToDate(openEntryForm.getStartDate()));
		openSyllabusEntry.setEndDate(CommonUtil.ConvertStringToDate(openEntryForm.getEndDate()));
		openSyllabusEntry.setCreatedBy(openEntryForm.getUserId());
		openSyllabusEntry.setModifiedBy(openEntryForm.getUserId());
		openSyllabusEntry.setCreatedDate(new Date());
		openSyllabusEntry.setLastModifiedDate(new Date());
		openSyllabusEntry.setIsActive(true);
		boolean flag=transaction.save(openSyllabusEntry);
		return flag;
	}
	public void edit(OpenSyllabusEntryForm openEntryForm) throws Exception{
		OpenSyllabusEntry openSyllabusEntry=transaction.getOpenSyllabusEntry(openEntryForm.getId());
		openEntryForm.setBatch(String.valueOf(openSyllabusEntry.getBatch()));
		openEntryForm.setTempBatch(String.valueOf(openSyllabusEntry.getBatch()));
		openEntryForm.setStartDate(CommonUtil.formatDates(openSyllabusEntry.getStartDate()));
		openEntryForm.setEndDate(CommonUtil.formatDates(openSyllabusEntry.getEndDate()));
		openEntryForm.setTempStartDate(CommonUtil.formatDates(openSyllabusEntry.getStartDate()));
		openEntryForm.setTempEndDate(CommonUtil.formatDates(openSyllabusEntry.getEndDate()));
	}
	public boolean delete(OpenSyllabusEntryForm openEntryForm) throws Exception{
		OpenSyllabusEntry openSyllabusEntry=transaction.getOpenSyllabusEntry(openEntryForm.getId());
		openSyllabusEntry.setCreatedBy(openEntryForm.getUserId());
		openSyllabusEntry.setLastModifiedDate(new Date());
		openSyllabusEntry.setIsActive(false);
		boolean flag=transaction.update(openSyllabusEntry);
		return flag;
	}
	public boolean update(OpenSyllabusEntryForm openEntryForm) throws Exception{
		OpenSyllabusEntry openSyllabusEntry=transaction.getOpenSyllabusEntry(openEntryForm.getId());
		openSyllabusEntry.setBatch(Integer.parseInt(openEntryForm.getBatch()));
		openSyllabusEntry.setStartDate(CommonUtil.ConvertStringToDate(openEntryForm.getStartDate()));
		openSyllabusEntry.setEndDate(CommonUtil.ConvertStringToDate(openEntryForm.getEndDate()));
		openSyllabusEntry.setCreatedBy(openEntryForm.getUserId());
		openSyllabusEntry.setLastModifiedDate(new Date());
		boolean flag=transaction.update(openSyllabusEntry);
		return flag;
	}
	public void getAllOpenSyllabusEntries(OpenSyllabusEntryForm openEntryForm) throws Exception{
		List<OpenSyllabusEntry> list=transaction.getAllOpenSyllabusEntries();
		List<OpenSyllabusEntryTo> tosList=null;
		if(list!=null && !list.isEmpty()){
			tosList=new ArrayList<OpenSyllabusEntryTo>();
			OpenSyllabusEntryTo openSyllabusEntryTo=null;
			for (OpenSyllabusEntry openSyllabusEntry : list) {
				openSyllabusEntryTo=new OpenSyllabusEntryTo();
				openSyllabusEntryTo.setId(openSyllabusEntry.getId());
				openSyllabusEntryTo.setStartDate(CommonUtil.formatDates(openSyllabusEntry.getStartDate()));
				openSyllabusEntryTo.setEndDate(CommonUtil.formatDates(openSyllabusEntry.getEndDate()));
				openSyllabusEntryTo.setBatch(String.valueOf(openSyllabusEntry.getBatch()));
				tosList.add(openSyllabusEntryTo);
			}
		}
		openEntryForm.setList(tosList);
		
	}
	public boolean validateDates(OpenSyllabusEntryForm openEntryForm) throws Exception{
		boolean flag=false;
		Date startDate=CommonUtil.ConvertStringToDate(openEntryForm.getStartDate());
		Date endDate=CommonUtil.ConvertStringToDate(openEntryForm.getEndDate());
		if(startDate.after(endDate)){
			flag=true;
		}
		return flag;
	}
}
