package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.to.admin.AdmittedThroughTO;

public class AdmittedThroughHelper {
	public static volatile AdmittedThroughHelper admittedThroughHelper = null;
	public static final Log log = LogFactory.getLog(AdmittedThroughHelper.class);
    private AdmittedThroughHelper(){
    	
    }
	public static AdmittedThroughHelper getInstance() {
		if (admittedThroughHelper == null) {
			admittedThroughHelper = new AdmittedThroughHelper();
			return admittedThroughHelper;
		}
		return admittedThroughHelper;
	}

	/**
	 * Creating TO's from BO
	 * @param admittedThroughList
	 * @return
	 */
	public List<AdmittedThroughTO> copyAdmittedThroughBosToTos(List<AdmittedThrough> admittedThroughList) {
		List<AdmittedThroughTO> admittedThroughTOList = new ArrayList<AdmittedThroughTO>();
		Iterator<AdmittedThrough> i = admittedThroughList.iterator();
		AdmittedThrough admittedThrough;
		AdmittedThroughTO admittedThroughTO;
		while (i.hasNext()) {
			admittedThroughTO = new AdmittedThroughTO();
			admittedThrough = i.next();
			admittedThroughTO.setId(admittedThrough.getId());
			admittedThroughTO.setName(admittedThrough.getName());
			admittedThroughTO.setAdmittedThroughCode(admittedThrough.getAdmittedThroughCode());
			admittedThroughTOList.add(admittedThroughTO);
		}
		log.error("ending of copyAdmittedThroughBosToTos method in AdmittedThroughHelper");
		return admittedThroughTOList;
	}
	
	/**
	 * 
	 * @param  AdmittedThroughForm creates BO from admittedThroughForm 
	 *            
	 * @return AdmittedThrough BO object
	 */

	public AdmittedThrough populateAdmittedThroughDataFormForm(
			AdmittedThroughForm admittedThroughForm ) throws Exception {
		AdmittedThrough admittedThrough = new AdmittedThrough();
		admittedThrough.setId(admittedThroughForm.getAdmittedThroughId());
		admittedThrough.setName(admittedThroughForm.getAdmittedThrough().trim());
		admittedThrough.setIsActive(true);  //in add and edit we can set this as true
		admittedThrough.setAdmittedThroughCode(admittedThroughForm.getAdmittedThroughCode());
		log.error("ending of populateAdmittedThroughDataFormForm method in AdmittedThroughHelper");
		return admittedThrough;
	}
	

}
