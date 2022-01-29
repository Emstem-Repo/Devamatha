package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GuidelinesChecklist;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.forms.admin.GuideLinesCheckListForm;
import com.kp.cms.to.admin.GuideLinesCheckListTO;
import com.kp.cms.to.admin.OrganizationTO;

public class GuideLinesCheckListHelper {
	public static volatile GuideLinesCheckListHelper guideLinesCheckListHelper = null;
	private static final Log log = LogFactory.getLog(GuideLinesCheckListHelper.class);
	

	public static GuideLinesCheckListHelper getInstance() {
		if (guideLinesCheckListHelper == null) {
			guideLinesCheckListHelper = new GuideLinesCheckListHelper();
			return guideLinesCheckListHelper;
		}
		return guideLinesCheckListHelper;
	}

	
	/**
	 * 
	 * @param guForm
	 * @return
	 * @throws Exception
	 */

	public GuidelinesChecklist populateGuideLinesCheckListFormForm(GuideLinesCheckListForm guForm) throws Exception {
		GuidelinesChecklist guidelinesChecklist = new GuidelinesChecklist();
		Organisation organisation = new Organisation();
		guidelinesChecklist.setId(guForm.getId());
		guidelinesChecklist.setDescription(guForm.getDescription());
		if(guForm.getOrganizationId()!= null){
			organisation.setId(Integer.parseInt(guForm.getOrganizationId()));
			guidelinesChecklist.setOrganisation(organisation);
		}
		guidelinesChecklist.setIsActive(true);
		
		log.debug("leaving populateSubReligionDataFormForm in Helper");
		return guidelinesChecklist;
	}

	/**
	 * 
	 * @param guideCheckList
	 * @return
	 */
	public List<GuideLinesCheckListTO> copyCheckListBosToTos(List<GuidelinesChecklist> guideCheckList) {
		List<GuideLinesCheckListTO> guideToList = new ArrayList<GuideLinesCheckListTO>();
		Iterator<GuidelinesChecklist> iterator = guideCheckList.iterator();
		GuidelinesChecklist guidelinesChecklist;
		GuideLinesCheckListTO guideLinesCheckListTO;
		OrganizationTO organizationTO;
		while (iterator.hasNext()) {
			guideLinesCheckListTO = new GuideLinesCheckListTO();
			organizationTO = new OrganizationTO();
			guidelinesChecklist = (GuidelinesChecklist) iterator.next();
			guideLinesCheckListTO.setId(guidelinesChecklist.getId());
			guideLinesCheckListTO.setDescription(guidelinesChecklist.getDescription());
			organizationTO.setId(guidelinesChecklist.getOrganisation().getId());
			organizationTO.setName(guidelinesChecklist.getOrganisation().getName());
			guideLinesCheckListTO.setOrganizationTO(organizationTO);
			
			guideToList.add(guideLinesCheckListTO);
		}
		log.debug("leaving copyCheckListBosToTos in Helper");
		return guideToList;
	}


}
