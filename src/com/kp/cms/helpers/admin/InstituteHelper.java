package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.University;
import com.kp.cms.forms.admin.InstituteForm;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.UniversityTO;


public class InstituteHelper {
	private static final Log log = LogFactory.getLog(InstituteHelper.class);
	public static volatile InstituteHelper instituteHelper = null;
	   public static InstituteHelper getInstance() {
		      if(instituteHelper == null) {
		    	  instituteHelper = new InstituteHelper();
		    	  return instituteHelper;
		      }
		      return instituteHelper;
	   }
	   /**
	    * 
	    * @param instituteList
	    * @return
	    */
		
		public List<CollegeTO> copyCollegeBosToTos(List<College> instituteList) {
			List<CollegeTO> instituteTOList = new ArrayList<CollegeTO>();
			Iterator<College> iterator = instituteList.iterator();
			College college;
			CollegeTO collegeTO;
			UniversityTO universityTO;
			while (iterator.hasNext()) {
				collegeTO = new CollegeTO();
				universityTO = new UniversityTO();
				college = (College) iterator.next();
				collegeTO.setId(college.getId());
				collegeTO.setName(college.getName());
				universityTO.setId(college.getUniversity().getId());
				universityTO.setName(college.getUniversity().getName());
				collegeTO.setUniversityTO(universityTO);
				instituteTOList.add(collegeTO);
			}
			log.debug("leaving copyCollegeBosToTos");
			return instituteTOList;
		}
		/**
		 * 
		 * @param instituteForm
		 *        Creates State BO from instituteForm.
		 * @return College BO Object.
		 * @throws Exception may throw Number format Exception while copying.
		 */
		public College populateStatesDataFormForm(InstituteForm instituteForm) throws Exception{
		    College college = new College();
		    University university = new University();
		    if(instituteForm.getId() != null && instituteForm.getId().length() != 0) {
		    	college.setId(Integer.parseInt(instituteForm.getId()));
		      }
		    college.setName(instituteForm.getName().trim());
		    if(instituteForm.getUniversityId() != null && instituteForm.getUniversityId().length() != 0) {
		    	university.setId(Integer.parseInt(instituteForm.getUniversityId()));
		     }
		     college.setUniversity(university);
		     college.setIsActive(true);
		     log.debug("leaving populateStatesDataFormForm");
		     return college;
		}

		
}
