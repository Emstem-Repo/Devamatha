package com.kp.cms.transactions.pettycash;

import java.util.List;
import com.kp.cms.bo.admin.PcAccHeadGroup;


public interface IPettyCashAccountHeadGroupCode {
	
	public List<PcAccHeadGroup> getAllpettyCashAccHeadGroupCode() throws Exception;
	public boolean manageAccountHeadGroupCode(PcAccHeadGroup pcAccHeadGroup,String mode) throws Exception;
	public PcAccHeadGroup getPettyCashAccGroupCode(Integer id) throws Exception;
	public PcAccHeadGroup existanceCheck(String  groupCode) throws Exception;
}
