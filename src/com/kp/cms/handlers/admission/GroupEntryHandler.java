package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.GroupEntryForm;
import com.kp.cms.helpers.admission.GroupEntryHelper;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;

public class GroupEntryHandler {
	/**
	 * Singleton object of GroupEntryHandler
	 */
	private static volatile GroupEntryHandler groupEntryHandler = null;
	private static final Log log = LogFactory.getLog(GroupEntryHandler.class);
	private GroupEntryHandler() {
		
	}
	/**
	 * return singleton object of GroupEntryHandler.
	 * @return
	 */
	public static GroupEntryHandler getInstance() {
		if (groupEntryHandler == null) {
			groupEntryHandler = new GroupEntryHandler();
		}
		return groupEntryHandler;
	}
	ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CCGroupTo> getGroupList() throws Exception{
		log.info("Entered into getGroupList");
		
		List<CCGroup> boList=transaction.getMasterEntryData(CCGroup.class);
		
		log.info("Exit from getGroupList");
		return GroupEntryHelper.getInstance().convertBoListToToList(boList);
	}
	/**
	 * @param groupEntryForm
	 * @param string
	 * @return
	 */
	public boolean addOrUpdateGroup(GroupEntryForm groupEntryForm, String mode) throws Exception {
		CCGroup ccGroup=(CCGroup)transaction.isDuplicated(CCGroup.class,groupEntryForm.getName());
		if(ccGroup!=null){
			if(ccGroup.getId()!=groupEntryForm.getId() && ccGroup.getIsActive())
				throw new DuplicateException();
			else if(ccGroup.getId()!=groupEntryForm.getId() && !ccGroup.getIsActive()){
				groupEntryForm.setId(ccGroup.getId());   
				throw new ReActivateException();
			}
		}
		
		CCGroup bo=GroupEntryHelper.getInstance().convertFormToBo(groupEntryForm,mode);
		if(mode.equalsIgnoreCase("update"))
			return PropertyUtil.getInstance().update(bo);
		else
			return PropertyUtil.getInstance().save(bo);
	}
	/**
	 * @param groupEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOrReactivateGroup(GroupEntryForm groupEntryForm, String mode) throws Exception {
		return PropertyUtil.getInstance().deleteOrReactivate("CCGroup",groupEntryForm.getId(), mode,groupEntryForm.getUserId());
	}
}
