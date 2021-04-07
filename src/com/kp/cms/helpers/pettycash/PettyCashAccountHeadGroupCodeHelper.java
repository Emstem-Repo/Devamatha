package com.kp.cms.helpers.pettycash;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadGroupCodeForm;

public class PettyCashAccountHeadGroupCodeHelper {
	
	private static volatile PettyCashAccountHeadGroupCodeHelper pettyCashAccountHeadGroupCodeHelper = null;
	private static final Log log = LogFactory.getLog(PettyCashAccountHeadGroupCodeHelper.class);
	private PettyCashAccountHeadGroupCodeHelper() {

	}
	public static PettyCashAccountHeadGroupCodeHelper getInstance() {
		if (pettyCashAccountHeadGroupCodeHelper == null) {
			pettyCashAccountHeadGroupCodeHelper = new PettyCashAccountHeadGroupCodeHelper();
		}
		return pettyCashAccountHeadGroupCodeHelper;
	}
	
	public PcAccHeadGroup createBoObjcet(PettyCashAccountHeadGroupCodeForm pettyCashAccHeadGroupCodeForm,PcAccHeadGroup pcHeadGroup,String mode)
	{
		log.info("entering into createBoObjcet in PettyCashAccountHeadGroupCodeHelper..");
		pcHeadGroup.setCode(pettyCashAccHeadGroupCodeForm.getGroupCode());
		pcHeadGroup.setName(pettyCashAccHeadGroupCodeForm.getGroupName());
		pcHeadGroup.setIsActive(!(mode.equalsIgnoreCase("delete")));
		
		if(mode.equalsIgnoreCase("Add"))
		{
		pcHeadGroup.setCreatedBy(pettyCashAccHeadGroupCodeForm.getUserId());
		pcHeadGroup.setCreatedDate(new Date());
		pcHeadGroup.setModifiedBy(pettyCashAccHeadGroupCodeForm.getUserId());
		pcHeadGroup.setLastModifiedDate(new Date());
		}
		else
		{
			pcHeadGroup.setModifiedBy(pettyCashAccHeadGroupCodeForm.getUserId());
			pcHeadGroup.setLastModifiedDate(new Date());
		}
		log.info("leaving from createBoObjcet in PettyCashAccountHeadGroupCodeHelper..");
		return pcHeadGroup;
	}
	
	
	public PettyCashAccountHeadGroupCodeForm createFormObject(PettyCashAccountHeadGroupCodeForm pcAccHeadGroupCodeForm,PcAccHeadGroup pcHeadGroup)
	{
		log.info("entering into createFormObject in PettyCashAccountHeadGroupCodeHelper..");
		pcAccHeadGroupCodeForm.setGroupCode(pcHeadGroup.getCode());
		pcAccHeadGroupCodeForm.setGroupName(pcHeadGroup.getName());
		pcAccHeadGroupCodeForm.setCreatedBy(pcHeadGroup.getCreatedBy());
		pcAccHeadGroupCodeForm.setCreatedDate(pcHeadGroup.getCreatedDate());
		log.info("leaving from createFormObject in PettyCashAccountHeadGroupCodeHelper..");
		return pcAccHeadGroupCodeForm;
	}

}
