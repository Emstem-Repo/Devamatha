package com.kp.cms.handlers.hostel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.hostel.FineEntryBo;
import com.kp.cms.bo.hostel.HostelVisitorsInfoBo;
import com.kp.cms.forms.hostel.FineEntryForm;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.helpers.hostel.FineEntryHelper;
import com.kp.cms.helpers.hostel.HostelVisitorsInfoHelper;
import com.kp.cms.to.hostel.FineEntryTo;
import com.kp.cms.transactions.hostel.IFineEntryTransaction;
import com.kp.cms.transactionsimpl.hostel.FineEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class FineEntryHandler {
	public static volatile FineEntryHandler fineEntryHandler = null;
	IFineEntryTransaction transaction=FineEntryTransactionImpl.getInstance();
	private static Log log = LogFactory.getLog(FineEntryHandler.class);
	public static FineEntryHandler getInstance() {
		if (fineEntryHandler == null) {
			fineEntryHandler = new FineEntryHandler();
			return fineEntryHandler;
		}
		return fineEntryHandler;
	}
	/**
	 * getting fineCategory list
	 * @return
	 * @throws Exception
	 */
public Map<String, String> getFineCategoryList()throws Exception{
	Map<String, String> categoryMap=transaction.getFineCategoryList();
	categoryMap=(HashMap<String, String>) CommonUtil.sortMapByValue(categoryMap);
		return categoryMap;
	}
/**
 * adding the fine entry details
 * @param
 */
public boolean addFineEntry(FineEntryForm fineEntryForm,String add) throws Exception{
	FineEntryBo fineEntryBo=FineEntryHelper.getInstance().convertFormToBo(fineEntryForm,add);
	boolean	flag=transaction.addFineEntry(fineEntryBo,add);
	return flag;
}
/**
 * get FineEntryList
 * @param fineCategoryForm
 * @return
 * @throws Exception
 */
public List<FineEntryTo> getFineEntryList(FineEntryForm fineEntryForm)throws Exception{
	List<FineEntryBo> fineCategoryBos = transaction.getFineEntry();
	List<FineEntryTo> listFineEntryTos=FineEntryHelper.getInstance().ConvertBosListToTosList(fineCategoryBos);
	return listFineEntryTos;
}
/**
 * deleting visitors info
 * @param biometricForm
 * @return
 * @throws Exception
 */
public boolean deleteFineEntry(FineEntryForm fineEntryForm)throws Exception{
	boolean isDeleted=transaction.deleteFineEntry(fineEntryForm.getId());
	return isDeleted;
}
/**
 * edit Fine Entry
 * @param biometricDetailsForm
 * @throws Exception
 */
public void editFineEntry(FineEntryForm fineEntryForm)throws Exception{
	FineEntryBo fineEntryBo=transaction.getFineEntryById(fineEntryForm.getId());
	FineEntryHelper.getInstance().setBotoForm(fineEntryForm, fineEntryBo);
}
/**
 * update the FineEntry when paid
 * @param biometricForm
 * @return
 * @throws Exception
 */
public boolean updateWhenPaidTheFine(FineEntryForm fineEntryForm)throws Exception{
	boolean ispaid=transaction.updateWhenPaidTheFine(fineEntryForm.getId(),fineEntryForm.getPaid());
	return ispaid;
}
/**
 * set the fine entry details to print
 */
public void setTheFineDetailsToPrint(FineEntryForm fineEntryForm,String add)throws Exception{
	FineEntryBo fineEntryBo=transaction.getLastFineEntryBo(fineEntryForm,add);
	if(fineEntryBo!=null){
		String name=fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getFirstName();
		if(fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null && !fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
			name=" "+fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getMiddleName();
		}
		if(fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getLastName()!=null && !fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getLastName().isEmpty()){
			name=" "+fineEntryBo.getHlAdmissionId().getStudentId().getAdmAppln().getPersonalData().getLastName();
		}
		fineEntryForm.setpStudentName(name);
		fineEntryForm.setpAmount(fineEntryBo.getAmount());
		fineEntryForm.setpCategory(fineEntryBo.getFineCategoryId().getName());
		fineEntryForm.setpRegisterNo(fineEntryBo.getHlAdmissionId().getStudentId().getRegisterNo());
		fineEntryForm.setpHostel(fineEntryBo.getHlAdmissionId().getHostelId().getName());
	}
}
/**
 * @param fineEntryForm
 * @return
 * @throws Exception
 */
public List<FineEntryTo> getSearchFineEntryListForRegNo(FineEntryForm fineEntryForm) throws Exception{
	List<FineEntryBo> fineEntryBosList = transaction.getSearchFineEntryListForRegNo(fineEntryForm);
	List<FineEntryTo> listFineEntryTos=FineEntryHelper.getInstance().ConvertBosListToTosList(fineEntryBosList);
	return listFineEntryTos;
}
}
