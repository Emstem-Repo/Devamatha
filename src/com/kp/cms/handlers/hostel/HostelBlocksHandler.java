package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.HostelBlocksForm;
import com.kp.cms.helpers.hostel.HostelBlocksHelper;
import com.kp.cms.to.hostel.HostelBlocksTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelBlocksTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelBlocksTransactionImpl;

public class HostelBlocksHandler {
	private static final Log log = LogFactory.getLog(HostelBlocksHandler.class);
	private static volatile HostelBlocksHandler hostelBlocksHandler = null;
	
	private HostelBlocksHandler() {
	}
	
	public static HostelBlocksHandler getInstance() {
		if (hostelBlocksHandler == null) {
			hostelBlocksHandler = new HostelBlocksHandler();
		}
		return hostelBlocksHandler;
	}
	
	IHostelBlocksTransaction transaction=new HostelBlocksTransactionImpl();
	
	public List<HostelBlocksTO> getHostelBlocksDetails()throws Exception
	{
		log.info("Inside of getHostelBlocksDetails of HostelBlocksHandler");
		List<HlBlocks> blocksList =transaction.getHostelBlocksDetails();
		if(blocksList!=null && !blocksList.isEmpty()){
			return HostelBlocksHelper.getInstance().pupulateHlBlocksBOtoTO(blocksList);
		}
		log.info("Leaving from getHostelBlocksDetails of HostelBlocksHandler");
		return new ArrayList<HostelBlocksTO>();
	}
	
	public boolean addHostelBlocks(HostelBlocksForm hostelBlocksForm)throws Exception
	{
		log.info("Inside addHostelBlocks of HostelBlocksHandler");
		HostelBlocksTO hostelBlocksTO=new HostelBlocksTO();
		if(hostelBlocksForm!=null){

			hostelBlocksTO.setName(hostelBlocksForm.getName());
			HostelTO hostelTO=new HostelTO();
			hostelTO.setId(Integer.parseInt(hostelBlocksForm.getHostelId()));
			hostelBlocksTO.setHostelTO(hostelTO);
			hostelBlocksTO.setCreatedBy(hostelBlocksForm.getUserId());
			hostelBlocksTO.setCreatedDate(new Date());
			hostelBlocksTO.setModifiedBy(hostelBlocksForm.getUserId());
			hostelBlocksTO.setLastModifiedDate(new Date());
		}
		HlBlocks hlBlocks=HostelBlocksHelper.getInstance().populateTOtoBO(hostelBlocksTO);
		if(hlBlocks!=null){
			return transaction.addHostelBlocks(hlBlocks);
		}
		log.info("Leaving of addHostelBlocks of HostelBlocksHandler");
		return false;
	}
	
	public HlBlocks checkForDuplicateonName(String hostelId, String name)throws Exception
	{
		log.info("Inside into checkForDuplicateonName of HostelBlocksHandler");
		return transaction.checkForDuplicateonName(hostelId, name);
	}
	
	public boolean deleteHostelBlocks(int hostelBlocksId, String userId)throws Exception{
		log.info("Inside of deleteHostelBlocks of HostelBlocksHandler");
		if(transaction!=null){
			return transaction.deleteHostelBlocks(hostelBlocksId, userId);
		}
		log.info("Leaving of deleteHostelBlocks of HostelBlocksHandler");
		return false;
	}
	
	public boolean updateHostelBlocks(HostelBlocksForm byForm)throws Exception
	{
		log.info("Inside of updateHostelBlocks of HostelBlocksHandler");
		HostelBlocksTO byTO=new HostelBlocksTO();
		String name="";
		boolean orgName=false;
		name=byForm.getName();
		orgName = transaction.checkForDuplicateonName1(name, byForm);
		if (orgName){
			throw new DuplicateException();
		}
		else{
		if(byForm!=null){
			byTO.setId(byForm.getId());
			byTO.setName(byForm.getName());
			byTO.setModifiedBy(byForm.getUserId());
			byTO.setLastModifiedDate(new Date());
			
			if(byForm.getHostelId()!=null && !byForm.getHostelId().isEmpty()){
				HostelTO hostelTO=new HostelTO();
				hostelTO.setId(Integer.parseInt(byForm.getHostelId()));
				byTO.setHostelTO(hostelTO);
			}
		}
		if(byTO!=null){
			HlBlocks hlBlocks=HostelBlocksHelper.getInstance().populateTotoBoUpdate(byTO);
			if(transaction!=null){
				return transaction.updateHostelBlocks(hlBlocks);				
			}
		}
		log.info("Leaving of updateHostelBlocks of HostelBlocksHandler");
		return false;
		}
	}
	
	public boolean reActivateHostelBlocks(String name, String userId)throws Exception
	{
	log.info("Inside into reActivateHostelBlocks of HostelBlocksHandler");
	if(transaction!=null){
		return transaction.reActivateHostelBlocks(name, userId);
	}
	log.info("Leaving of reActivateHostelBlocks of HostelBlocksHandler");
	return false;
	}
	
	public HostelBlocksTO getDetailsonId(int blkId) throws Exception{
		log.info("Inside into getDetailsonId of HostelBlocksHandler");
		HlBlocks hlBlocks=transaction.getDetailsonId(blkId);
		log.info("End of getDetailsonId of HostelBlocksHandler");
		return HostelBlocksHelper.getInstance().populateBotoToEdit(hlBlocks);
	}

}
