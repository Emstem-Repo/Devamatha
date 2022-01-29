package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.forms.admission.GroupEntryForm;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.utilities.CommonUtil;

public class GroupEntryHelper {
	/**
	 * Singleton object of GroupEntryHelper
	 */
	private static volatile GroupEntryHelper groupEntryHelper = null;
	private static final Log log = LogFactory.getLog(GroupEntryHelper.class);
	private GroupEntryHelper() {
		
	}
	/**
	 * return singleton object of GroupEntryHelper.
	 * @return
	 */
	public static GroupEntryHelper getInstance() {
		if (groupEntryHelper == null) {
			groupEntryHelper = new GroupEntryHelper();
		}
		return groupEntryHelper;
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public List<CCGroupTo> convertBoListToToList(List<CCGroup> boList) throws Exception {
		List<CCGroupTo> toList=new ArrayList<CCGroupTo>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<CCGroup> itr=boList.iterator();
			CCGroupTo to=null;
			while (itr.hasNext()) {
				CCGroup bo=itr.next();
				if(bo.getIsActive()){
					to=new CCGroupTo();
					to.setId(bo.getId());
					to.setName(bo.getName());
					to.setStartDate(CommonUtil.formatSqlDate(bo.getStartDate().toString()));
					to.setEndDate(CommonUtil.formatSqlDate(bo.getEndDate().toString()));
					toList.add(to);
				}
			}
		}
		Collections.sort(toList);
		return toList;
	}
	/**
	 * @param groupEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public CCGroup convertFormToBo(GroupEntryForm groupEntryForm, String mode) throws Exception {
		CCGroup bo=new CCGroup();
		if(mode.equalsIgnoreCase("update"))
			bo.setId(groupEntryForm.getId());
		else{
			bo.setCreatedDate(new Date());
			bo.setCreatedBy(groupEntryForm.getUserId());
		}
		bo.setIsActive(true);
		bo.setModifiedBy(groupEntryForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setName(groupEntryForm.getName().trim());
		if(groupEntryForm.getStartDate()!=null && !groupEntryForm.getStartDate().trim().isEmpty())
			bo.setStartDate(CommonUtil.ConvertStringToSQLDate(groupEntryForm.getStartDate()));
		if(groupEntryForm.getEndDate()!=null && !groupEntryForm.getEndDate().trim().isEmpty())
			bo.setEndDate(CommonUtil.ConvertStringToSQLDate(groupEntryForm.getEndDate()));
		
		return bo;
	}
}
