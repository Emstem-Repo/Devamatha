package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;

/**
 * 
 * @author  Date 17/01/09 Helper class for Sub Religion master entry
 */

public class SubReligionHelper {
	public static volatile SubReligionHelper subReligionHelper = null;
	private static final Log log = LogFactory.getLog(SubReligionHelper.class);
	

	public static SubReligionHelper getInstance() {
		if (subReligionHelper == null) {
			subReligionHelper = new SubReligionHelper();
			return subReligionHelper;
		}
		return subReligionHelper;
	}

	/**
	 * 
	 * @param SubReligionForm creates BO from SubReligionForm 
	 *            
	 * @return religionSection BO object
	 */

	public ReligionSection populateSubReligionDataFormForm(SubReligionForm subReligionForm) throws Exception {
		ReligionSection religionSection = new ReligionSection();
		/*Religion religion = new Religion();
		 religionSection.setReligion(religion);
		 religion.setId(Integer.parseInt(subReligionForm.getReligionId()));
			*/
		
		religionSection.setName(subReligionForm.getSubReligionName());
		
		religionSection.setId(subReligionForm.getSubReligionId());
		
		religionSection.setIsActive(true);
		log.debug("leaving populateSubReligionDataFormForm in Helper");
		return religionSection;
	}

	/**
	 * 
	 * @param subReligionList
	 *           this will copy the ReligionSection BO to TO
	 * @return subReligionToList having subReligionTO objects.
	 */
	public List<ReligionSectionTO> copySubReligionBosToTos(List<ReligionSection> subReligionList) {
		List<ReligionSectionTO> subReligionToList = new ArrayList<ReligionSectionTO>();
		Iterator<ReligionSection> iterator = subReligionList.iterator();
		ReligionSection religionSection;
		ReligionSectionTO religionSectionTO;
		ReligionTO religionTO;
		while (iterator.hasNext()) {
			religionSectionTO = new ReligionSectionTO();
			
			//religionTO = new ReligionTO();
			//religionTO.setReligionId(religionSection.getReligion().getId());
			//religionTO.setReligionName(religionSection.getReligion().getName());
			//religionSectionTO.setReligionTO(religionTO);
			
			religionSection = (ReligionSection) iterator.next();
			religionSectionTO.setId(religionSection.getId());
			religionSectionTO.setName(religionSection.getName());
			subReligionToList.add(religionSectionTO);
		}
		log.debug("leaving copySubReligionBosToTos in Helper");
		return subReligionToList;
	}

}
