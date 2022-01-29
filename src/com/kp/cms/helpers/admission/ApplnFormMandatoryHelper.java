package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplnMandatoryBO;
import com.kp.cms.forms.admission.ApplnMandatoryForm;
import com.kp.cms.to.admission.ApplnMandatoryTO;

public class ApplnFormMandatoryHelper {
	private static volatile ApplnFormMandatoryHelper applnFormMandatoryHelper = null;
	private static final Log log = LogFactory.getLog(ApplnFormMandatoryHelper.class);
	private ApplnFormMandatoryHelper() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static ApplnFormMandatoryHelper getInstance() {
		if (applnFormMandatoryHelper == null) {
			applnFormMandatoryHelper = new ApplnFormMandatoryHelper();
		}
		return applnFormMandatoryHelper;
	}
	
	public List<ApplnMandatoryTO> copyFromBotoTo(List<ApplnMandatoryBO> boList) throws Exception{
		List<ApplnMandatoryTO> to = new ArrayList<ApplnMandatoryTO>();
		Iterator<ApplnMandatoryBO> itr = boList.iterator();
		while (itr.hasNext()) {
			ApplnMandatoryBO applnMandatoryBO = (ApplnMandatoryBO) itr.next();
			ApplnMandatoryTO applnMandatoryTO = new ApplnMandatoryTO();
			applnMandatoryTO.setId(applnMandatoryBO.getId());
			applnMandatoryTO.setDisplayName(applnMandatoryBO.getDisplayName());
			applnMandatoryTO.setPropertyName(applnMandatoryBO.getPropertyName());
			if(applnMandatoryBO.getValidate()){
				applnMandatoryTO.setValidate("yes");
			}
			else{
				applnMandatoryTO.setValidate("no");
			}
			to.add(applnMandatoryTO);
		}
		return to;
	}
	
	public List<ApplnMandatoryBO> copyFormtoBo(ApplnMandatoryForm applnMandatoryForm){
		List<ApplnMandatoryBO> boList = new ArrayList<ApplnMandatoryBO>();
		List<ApplnMandatoryTO> applnMandatoryTOs = applnMandatoryForm.getList();
		Iterator<ApplnMandatoryTO> itr = applnMandatoryTOs.iterator();
		while (itr.hasNext()) {
			ApplnMandatoryTO applnMandatoryTO = (ApplnMandatoryTO) itr.next();
			ApplnMandatoryBO applnMandatoryBO = new ApplnMandatoryBO();
			applnMandatoryBO.setId(applnMandatoryTO.getId());
			applnMandatoryBO.setDisplayName(applnMandatoryTO.getDisplayName());
			applnMandatoryBO.setPropertyName(applnMandatoryTO.getPropertyName());
			if(applnMandatoryTO.getValidate() != null && applnMandatoryTO.getValidate().equalsIgnoreCase("yes")){
				applnMandatoryBO.setValidate(true);
			}
			else{
				applnMandatoryBO.setValidate(false);
			}
			boList.add(applnMandatoryBO);
		}
		return boList;
	}
}
