package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.hostel.BiometricBo;
import com.kp.cms.bo.hostel.HostelVisitorsInfoBo;
import com.kp.cms.forms.hostel.BiometricForm;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.helpers.hostel.BiometricHelper;
import com.kp.cms.helpers.hostel.HostelVisitorsInfoHelper;
import com.kp.cms.to.hostel.HostelVisitorsInfoTo;
import com.kp.cms.transactions.hostel.IHostelVisitorsInfoTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelVisitorsInfoTransactionImpl;

public class HostelVisitorsInfoHandler {
	private static Log log = LogFactory.getLog(HostelVisitorsInfoHandler.class);
	IHostelVisitorsInfoTransaction transaction=HostelVisitorsInfoTransactionImpl.getInstance();
	/**
	 * instance()
	 */
	public static volatile HostelVisitorsInfoHandler hostelVisitorsInfoHandler = null;
	public static HostelVisitorsInfoHandler getInstance() {
		if (hostelVisitorsInfoHandler == null) {
			hostelVisitorsInfoHandler = new HostelVisitorsInfoHandler();
			return hostelVisitorsInfoHandler;
		}
		return hostelVisitorsInfoHandler;
	}
	/**
	 * adding the visitors details
	 * @param
	 */
	public boolean addVisitorsInformation(HostelVisitorsInfoForm hostelVisitorsInfoForm,String hlAdmissionId) throws Exception{
		HostelVisitorsInfoBo hostelVisitorsInfoBo=HostelVisitorsInfoHelper.getInstance().convertFormToBo(hostelVisitorsInfoForm,hlAdmissionId);
		boolean flag=transaction.addHostelVisitorsInfo(hostelVisitorsInfoBo);
		return flag;
		
	}
	public List<HostelVisitorsInfoTo> getVisitorsList(String regNo,HostelVisitorsInfoForm hostelVisitorsInfoForm)throws Exception{
		List<HostelVisitorsInfoBo> listVisitorsInfoBos=transaction.getVisitorsList(regNo);
		List<HostelVisitorsInfoTo> listVisitorsInfoTos=null;
		if(listVisitorsInfoBos!=null && !listVisitorsInfoBos.isEmpty()){
		listVisitorsInfoTos=HostelVisitorsInfoHelper.getInstance().convertBoListToToList(listVisitorsInfoBos);
		}
		return listVisitorsInfoTos;
	}
	/**
	 * deleting visitors info
	 * @param biometricForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteVisitorsInfo(HostelVisitorsInfoForm hostelVisitorsInfoForm)throws Exception{
		boolean isDeleted=transaction.deleteVisitorsInfo(hostelVisitorsInfoForm.getId());
		return isDeleted;
	}
	/**
	 * edit hostel visitors information
	 * @param biometricDetailsForm
	 * @throws Exception
	 */
	public void editVisitorsInfo(HostelVisitorsInfoForm hostelVisitorsInfoForm)throws Exception{
		HostelVisitorsInfoBo hostelVisitorsInfoBo=transaction.getVisitorsInfoById(hostelVisitorsInfoForm.getId());
		HostelVisitorsInfoHelper.getInstance().setBotoForm(hostelVisitorsInfoForm, hostelVisitorsInfoBo);
	}
	/**
	 * update the visitors details
	 * @param
	 */
	public boolean updateVisitorsInformation(HostelVisitorsInfoForm hostelVisitorsInfoForm) throws Exception{
		HostelVisitorsInfoBo hostelVisitorsInfoBo=transaction.getVisitorsInfoById(hostelVisitorsInfoForm.getId());
		HostelVisitorsInfoBo hostelVisitorsInfoBo1=HostelVisitorsInfoHelper.getInstance().convertUpdateFormToBo(hostelVisitorsInfoForm,hostelVisitorsInfoBo);
		boolean flag=transaction.updateHostelVisitorsInfo(hostelVisitorsInfoBo1);
		return flag;
		
	}

}
