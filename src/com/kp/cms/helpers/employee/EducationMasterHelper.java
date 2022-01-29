package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.forms.employee.EducationMasterForm;
import com.kp.cms.to.employee.EmpEducationMasterTO;

public class EducationMasterHelper {
	private static final Log log = LogFactory.getLog(EducationMasterHelper.class);
	public static volatile EducationMasterHelper educationMasterHelper = null;

	public static EducationMasterHelper getInstance() {
		if (educationMasterHelper == null) {
			educationMasterHelper = new EducationMasterHelper();
			return educationMasterHelper;
		}
		return educationMasterHelper;
	}
	
	/**
	 * 
	 * @param educationMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpEducationMaster copyDataFromFormToBO(EducationMasterForm educationMasterForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		EmpEducationMaster educationMaster = new EmpEducationMaster();
		EmpQualificationLevel qualificationLevel = new EmpQualificationLevel();
		if(educationMasterForm.getId() != 0) {
			educationMaster.setId(educationMasterForm.getId());
		}
		
		educationMaster.setName(educationMasterForm.getEducation());
		if(educationMasterForm.getQualificationId()!= null && !educationMasterForm.getQualificationId().trim().isEmpty()){
			qualificationLevel.setId(Integer.parseInt(educationMasterForm.getQualificationId()));
		}
		educationMaster.setEmpQualificationLevel(qualificationLevel);
		educationMaster.setIsActive(true);
		educationMaster.setCreatedBy(educationMasterForm.getUserId());
		educationMaster.setModifiedBy(educationMasterForm.getUserId());
		educationMaster.setCreatedDate(new Date());
		educationMaster.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return educationMaster;
	}
	/**
	 * 
	 * @param educationList
	 *            this will copy the EmpEducationMaster BO to TO
	 * @return educationTOList having EmpEducationMasterTO objects.
	 */
	public List<EmpEducationMasterTO> copyEducationBosToTos(List<EmpEducationMaster> educationList) {
		log.debug("inside copyEducationBosToTos");
		List<EmpEducationMasterTO> educationTOList = new ArrayList<EmpEducationMasterTO>();
		Iterator<EmpEducationMaster> iterator = educationList.iterator();
		EmpEducationMaster empEducationMaster;
		EmpEducationMasterTO educationMasterTO;
		EmpQualificationLevel empQualificationLevel;
		while (iterator.hasNext()) {
			educationMasterTO = new EmpEducationMasterTO();
			empEducationMaster = (EmpEducationMaster) iterator.next();
			educationMasterTO.setId(empEducationMaster.getId());
			if(empEducationMaster.getName()!= null && !empEducationMaster.getName().trim().isEmpty()){
				educationMasterTO.setName(empEducationMaster.getName());
			}
			if(empEducationMaster.getEmpQualificationLevel()!= null){
				empQualificationLevel = new EmpQualificationLevel(); 
				empQualificationLevel.setId(empEducationMaster.getEmpQualificationLevel().getId());
				empQualificationLevel.setName(empEducationMaster.getEmpQualificationLevel().getName());
				educationMasterTO.setEmpQualificationLevel(empQualificationLevel);
			}
			educationTOList.add(educationMasterTO);
		}
		log.debug("leaving copyInvCounterBosToTos");
		return educationTOList;
	}
	
	
}
