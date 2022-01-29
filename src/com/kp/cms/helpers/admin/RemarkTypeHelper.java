package com.kp.cms.helpers.admin;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.forms.admin.RemarkTypeForm;
import com.kp.cms.to.admin.RemarkTypeTO;

public class RemarkTypeHelper {
	private static Log log = LogFactory.getLog(RemarkTypeHelper.class);
	public static volatile RemarkTypeHelper remarkTypeHelper = null;
	public static RemarkTypeHelper getInstance() {
	      if(remarkTypeHelper == null) {
	    	  remarkTypeHelper = new RemarkTypeHelper();
	    	  return remarkTypeHelper;
	      }
	      return remarkTypeHelper;
	}

	/**
	 * 
	 * @param remarkTypeList
	 * @return
	 */
	
	public List<RemarkTypeTO> copyRemarkTypeBosToTos(List<RemarkType> remarkTypeList) {
		List<RemarkTypeTO> remarkTypeTOList = new ArrayList<RemarkTypeTO>();
		Iterator<RemarkType> iterator = remarkTypeList.iterator();
		RemarkType remarkType;
		RemarkTypeTO remarkTypeTO;
		while (iterator.hasNext()) {
			remarkTypeTO = new RemarkTypeTO();
			remarkType = (RemarkType) iterator.next();
			remarkTypeTO.setId(remarkType.getId());
			remarkTypeTO.setRemarkType(remarkType.getRemarkType());
			remarkTypeTO.setMaxOccurrences(Integer.toString(remarkType.getMaxOccurrences()));
			remarkTypeTO.setColor(remarkType.getColor());
			remarkTypeTOList.add(remarkTypeTO);
		}
		log.debug("leaving copyRemarkTypeBosToTos");
		return remarkTypeTOList;
	}

	/**
	 * 
	 * @param remarkTypeForm
	 * @return
	 * @throws Exception
	 */
	public RemarkType copyDataFromFormToBO(RemarkTypeForm remarkTypeForm) throws Exception{
		RemarkType remarkType = new RemarkType();
		if(remarkTypeForm.getId() != 0) {
			remarkType.setId(remarkTypeForm.getId());
		}
		remarkType.setRemarkType(remarkTypeForm.getRemarkType());
		if(remarkTypeForm.getMaxOccurrences()!= null){
			remarkType.setMaxOccurrences(Integer.parseInt(remarkTypeForm.getMaxOccurrences()));
		}
		
		remarkType.setIsActive(true);
		remarkType.setCreatedBy(remarkTypeForm.getUserId());
		remarkType.setModifiedBy(remarkTypeForm.getUserId());
		remarkType.setCreatedDate(new Date());
		remarkType.setLastModifiedDate(new Date());
		remarkType.setColor(remarkTypeForm.getColor());
		log.debug("leaving copyDataFromFormToBO");
		return remarkType;
		}

}



