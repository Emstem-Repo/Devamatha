package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.forms.admin.ExtraCurricularActivitiesForm;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.OrganizationTO;


public class ExtraCurricularActivitiesHelper {
	private static final Log log = LogFactory.getLog(ExtraCurricularActivitiesHelper.class);
	public static volatile ExtraCurricularActivitiesHelper extraCurricularActivitiesHelper = null;
	   public static ExtraCurricularActivitiesHelper getInstance() {
		      if(extraCurricularActivitiesHelper == null) {
		    	  extraCurricularActivitiesHelper = new ExtraCurricularActivitiesHelper();
		    	  return extraCurricularActivitiesHelper;
		      }
		      return extraCurricularActivitiesHelper;
	   }
		/***
		 * 		
		 * @param activityList
		 * @return
		 */
		public List<ExtracurricularActivityTO> copyActivityBosToTos(List<ExtracurricularActivity> activityList) {
			log.debug("inside copyActivityBosToTos");
			List<ExtracurricularActivityTO> activityTOList = new ArrayList<ExtracurricularActivityTO>();
			Iterator<ExtracurricularActivity> iterator = activityList.iterator();
			ExtracurricularActivity extracurricularActivity;
			ExtracurricularActivityTO extracurricularActivityTO;
			OrganizationTO organizationTO;
			while (iterator.hasNext()) {
				extracurricularActivityTO = new ExtracurricularActivityTO();
				organizationTO = new OrganizationTO();
				extracurricularActivity = (ExtracurricularActivity) iterator.next();
				extracurricularActivityTO.setId(extracurricularActivity.getId());
				extracurricularActivityTO.setName(extracurricularActivity.getName());
				if(extracurricularActivity.getOrganisation()!= null){
					organizationTO.setId(extracurricularActivity.getOrganisation().getId());
					organizationTO.setOrganizationName(extracurricularActivity.getOrganisation().getName()!=null ? extracurricularActivity.getOrganisation().getName():null);
				}
				extracurricularActivityTO.setOrganizationTO(organizationTO);
				activityTOList.add(extracurricularActivityTO);
			}
			log.debug("leaving copyActivityBosToTos");
			return activityTOList;
		}
		/**
		 * 
		 * @param extraCurricularActivitiesForm
		 *        Creates Activity BO from extraCurricularActivitiesForm.
		 * @return ExtracurricularActivity BO Object.
		 * @throws Exception may throw Number format Exception while copying.
		 */
		public ExtracurricularActivity populateActivityDataFromForm(ExtraCurricularActivitiesForm extraCurricularActivitiesForm) throws Exception{
			log.debug("inside populateActivityDataFromForm");
		    ExtracurricularActivity extracurricularActivity = new ExtracurricularActivity();
		    Organisation organisation = new Organisation();
		    if(extraCurricularActivitiesForm.getId() != null && extraCurricularActivitiesForm.getId().length() != 0) {
		    	extracurricularActivity.setId(Integer.parseInt(extraCurricularActivitiesForm.getId()));
		      }
		    extracurricularActivity.setName(extraCurricularActivitiesForm.getName().trim());
		    if(extraCurricularActivitiesForm.getOrgId() != null && extraCurricularActivitiesForm.getOrgId().length() != 0) {
		    	organisation.setId(Integer.parseInt(extraCurricularActivitiesForm.getOrgId()));
		     }
		     extracurricularActivity.setOrganisation(organisation);
		     extracurricularActivity.setIsActive(true);
		     log.debug("leaving populateStatesDataFormForm");
		     return extracurricularActivity;
		}

		
}
