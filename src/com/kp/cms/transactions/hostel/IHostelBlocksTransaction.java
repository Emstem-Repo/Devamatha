package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.forms.hostel.HostelBlocksForm;

public interface IHostelBlocksTransaction {
	
	public HlBlocks checkForDuplicateonName(String hostelId, String name)throws Exception;
	
	public boolean addHostelBlocks(HlBlocks hlBlocks) throws Exception;
	
	public List<HlBlocks> getHostelBlocksDetails()throws Exception;
	
	public boolean deleteHostelBlocks(int id, String userId)throws Exception;
	
	public boolean updateHostelBlocks(HlBlocks hlBlocks)throws Exception;
	
	public boolean reActivateHostelBlocks(String name, String userId)throws Exception;
	
	public HlBlocks getDetailsonId(int id)throws Exception;
	
	public boolean checkForDuplicateonName1(String name, HostelBlocksForm hostelBlocksForm)throws Exception;
}
